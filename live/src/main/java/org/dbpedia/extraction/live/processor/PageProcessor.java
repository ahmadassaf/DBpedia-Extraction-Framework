package org.dbpedia.extraction.live.processor;

<<<<<<< HEAD
import org.apache.log4j.Logger;
=======
import org.dbpedia.extraction.util.Language;
import org.slf4j.Logger;
>>>>>>> 39911a3fdbc3e198f3ea8707670c016878426b4a
import org.dbpedia.extraction.live.core.LiveOptions;
import org.dbpedia.extraction.live.extraction.LiveExtractionConfigLoader;
import org.dbpedia.extraction.live.queue.LiveQueue;
import org.dbpedia.extraction.live.queue.LiveQueueItem;
import org.dbpedia.extraction.live.queue.LiveQueuePriority;
import org.dbpedia.extraction.live.storage.JSONCache;


/**
 * Created by IntelliJ IDEA.
 * User: Mohamed Morsey
 * Date: Jul 28, 2010
 * Time: 3:43:57 PM
 * This class dequeues the item at the front of the priority pageQueue and processes it i.e. apply the extraction
 * process on it.
 */
public class PageProcessor extends Thread{

    private static Logger logger = Logger.getLogger(PageProcessor.class);
    private volatile boolean keepRunning = true;

    public PageProcessor(String name){
        this.setName("PageProcessor_" + name);
    }

    public PageProcessor(){
        this("PageProcessor");
    }

    public void startProcessor() {
        if (keepRunning == true) {
            start();
        }
    }

    public void stopProcessor() {
        keepRunning = false;
    }


    private void processPage(LiveQueueItem item){
        processPage(item, false);
    }

    private void processPageFromTitle(LiveQueueItem item){
        processPage(item, true);
    }

    private void processPage(LiveQueueItem item, boolean isTitle) {
        try{
<<<<<<< HEAD
            Boolean extracted = LiveExtractionConfigLoader.extractPage(
                    item,
                    LiveOptions.options.get("localApiURL"),
                    LiveOptions.options.get("language"));
=======
            Boolean extracted = false;
            if (isTitle) {
                extracted = LiveExtractionConfigLoader.extractPageFromTitle(
                        item,
                        Language.apply(LiveOptions.language).apiUri(),
                        LiveOptions.language);
            } else {
                extracted = LiveExtractionConfigLoader.extractPage(
                        item,
                        Language.apply(LiveOptions.language).apiUri(),
                        LiveOptions.language);
            }
>>>>>>> 39911a3fdbc3e198f3ea8707670c016878426b4a

            if (!extracted)
                JSONCache.setErrorOnCache(item.getItemID(), -1);
        }
        catch(Exception exp){
            logger.error("Error in processing page number " + item.getItemID() + ", and the reason is " + exp.getMessage(), exp);
            JSONCache.setErrorOnCache(item.getItemID(), -2);
        }
    }


    public void run(){
<<<<<<< HEAD
        while(keepRunning){
            try{
                LiveQueueItem page = LiveQueue.take();
=======
        LiveQueueItem currentPage = new LiveQueueItem(0,"");
        LiveQueueItem lastPage = null;
        while(keepRunning){
            try{
                LiveQueueItem page = LiveQueue.take();
                if (page.equals(lastPage)) {
                    logger.info("Ignoring duplicatre page {} ({}) with priority {}", page.getItemName(), page.getItemID(), page.getPriority());
                    continue;
                }
                lastPage = page;
                currentPage = page;
>>>>>>> 2dfd3a4888d6f710311813ddd6f9ddffeea46195
                // If a mapping page set extractor to reload mappings and ontology
                if (page.getPriority() == LiveQueuePriority.MappingPriority) {
                    LiveExtractionConfigLoader.reload(page.getStatQueueAdd());
                }
                if (page.isDeleted() == true) {
                    JSONCache.deleteCacheItem(page.getItemID(),LiveExtractionConfigLoader.policies());
                    logger.info("Deleted page with ID: " + page.getItemID() + " (" + page.getItemName() + ")");
                }
                else {
                    if (!page.getItemName().isEmpty()) {
                        processPageFromTitle(page);
                    } else {
                        processPage(page);
                    }
                }
            }
            catch (Exception exp){
                logger.error("Failed to process page: " + exp.getMessage());
            }
        }
    }
}
