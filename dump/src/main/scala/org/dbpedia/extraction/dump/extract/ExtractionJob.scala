package org.dbpedia.extraction.dump.extract

import org.dbpedia.extraction.destinations.Destination
import org.dbpedia.extraction.mappings.RootExtractor
import org.dbpedia.extraction.sources.{Source, WikiPage}
import org.dbpedia.extraction.wikiparser.{Namespace,WikiParser}
import java.util.concurrent.{ArrayBlockingQueue, TimeUnit}
import java.util.logging.{Level, Logger}
import scala.util.control.ControlThrowable
import java.io.File

/**
 * Executes a extraction.
 * TODO: use fork-join or other java.util.concurrent tools instead of plain threads.
 *
 * @param extractor The Extractor
 * @param source The extraction source
 * @param destination The extraction destination. Will be closed after the extraction has been finished.
 * @param label user readable label of this extraction job. Also used as file name, but space is replaced by underscores.
 */
class ExtractionJob(extractor : RootExtractor, source : Source, destination : Destination, val label : String = "Extraction Job") extends Thread
{
    private val logger = Logger.getLogger(classOf[ExtractionJob].getName)

    private val parser = WikiParser()

    val progress = new ExtractionProgress()

    // TODO: make size depend on CPUs?
    private val pageQueue = new ArrayBlockingQueue[WikiPage](20)
    
    override def run() : Unit =
    {
        logger.info(label + " started")
        
        // one thread per core sounds good. availableProcessors returns logical processors, 
        // not physical, which is good for us.
        val cpus = java.lang.Runtime.getRuntime.availableProcessors

        val extractionJobs = for(_ <- 1 to cpus) yield new ExtractionThread()

        try
        {
            progress.startTime.set(System.currentTimeMillis)

            //Start extraction jobs
            extractionJobs.foreach(_.start)

            //Start loading pages
            source.foreach(queuePage)
        }
        catch
        {
            case ex : ControlThrowable =>
            case ex : InterruptedException =>
            case ex => logger.log(Level.SEVERE, "Error reading pages. Shutting down...", ex)
        }
        finally
        {
            //Stop extraction jobs
            extractionJobs.foreach(_.done)
            extractionJobs.foreach(_.join)

            destination.close()

            logger.info(label + " finished")
        }
    }
    
    // Only extract from the following namespaces
    private val namespaces = Set(Namespace.Main, Namespace.File, Namespace.Category, Namespace.Template)

    private def queuePage(page : WikiPage)
    {
        // If we use XMLSource, we probably checked this already, but anyway... 
        if (! namespaces.contains(page.title.namespace)) return 

        pageQueue.put(page)
    }

    /**
     * An extraction thread.
     * Takes pages from a queue and extracts them.
     */
    private class ExtractionThread() extends Thread
    {
        @volatile private var running = true

        def done()
        {
            running = false
        }

        override def run() : Unit =
        {
            // Extract remaining pages:
            // - If the whole show is running, we wait for pages as long as we want
            // - If the master thread said we're done, we still keep going until the queue is empty
            while(running || ! pageQueue.isEmpty)
            {
                // Note: it would be nice if we could just take() and wait forever, but then
                // we might be left sleeping at the end when the queue becomes empty.
                // TODO: use interrupt(), but do it correctly this time
                val page = pageQueue.poll(100, TimeUnit.MILLISECONDS)
                if(page != null)
                {
                    extractPage(page)
                }
                else
                {
                    Thread.sleep(10)
                }
            }
        }

        private def extractPage(page : WikiPage) : Unit =
        {
            //Extract the page
            val success =
                try
                {
                    val graph = extractor(parser(page))
                    destination.write(graph)
                    progress.extractedPages.incrementAndGet

                    true
                }
                catch
                {
                    case ex : Exception =>
                    {
                        progress.failedPages.incrementAndGet
                        logger.log(Level.WARNING, "Error processing page '" + page.title + "'", ex)
                        false
                    }
                }
        }
    }
}