package org.dbpedia.extraction.live.publisher;

import org.dbpedia.extraction.live.core.LiveOptions;
import org.dbpedia.extraction.live.main.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * Date: Oct 31, 2010
 * Time: 10:59:53 AM
 * This class publishes the triples (added and deleted) to files in order to enable synchronizing our live end-point with
 * other end-points
 * It is originally developed by Claus Stadler   
 */

public class Publisher extends Thread{

    private static final Logger logger = LoggerFactory.getLogger(Publisher.class);

    private HashSet<String> addedTriples = new HashSet<String>();
    private HashSet<String> deletedTriples = new HashSet<String>();

    private long counter = 0;
    private HashSet<Long> pageCache = new HashSet<Long>();

<<<<<<< HEAD
    private String publishDiffBaseName = LiveOptions.options.get("publishDiffRepoPath");
=======
    private final String publishDiffBaseName = LiveOptions.options.get("publishDiffRepoPath");
>>>>>>> 2dfd3a4888d6f710311813ddd6f9ddffeea46195

    public Publisher(String name, int priority){
        this.setPriority(priority);
        this.setName(name);
        start();
    }

    public Publisher(String name){
        this(name, Thread.NORM_PRIORITY);
    }

    public Publisher(){
        this("Publisher", Thread.NORM_PRIORITY);
    }

    public void run()  {

<<<<<<< HEAD
        counter = 1;
=======
        final HashSet<Long> pageCache = new HashSet<Long>();

>>>>>>> 2dfd3a4888d6f710311813ddd6f9ddffeea46195
        while(true) {
            try {
                // Block until next pubData
                DiffData pubData = Main.publishingDataQueue.take();

<<<<<<< HEAD
                if (pageCache.contains(pubData.pageID) || counter % 300 == 0) {
=======
                // flush if
                // 1) we get the same page again (possible conflict in diff order
                // 2) we have more than MAX_CHANGE_SETS changesets in queue
                // 3) the diff exceeds a triple limit  MAX_QUEUE_SIZE
                if (pageCache.contains(pubData.pageID) ||
                        pageCache.size() > MAX_CHANGE_SETS ||
                        addedTriples.size() > MAX_QUEUE_SIZE ||
                        deletedTriples.size() > MAX_QUEUE_SIZE ||
                        reInsertedTriples.size() > MAX_QUEUE_SIZE ||
                        subjectsClear.size() > MAX_QUEUE_SIZE) {

                    pageCache.clear();
>>>>>>> 2dfd3a4888d6f710311813ddd6f9ddffeea46195
                    flush();
                    counter = 0;
                }
                bufferDiff(pubData);
                counter++;
                pageCache.add(pubData.pageID);
            } catch(Throwable t) {
                logger.error("An exception was encountered in the Publisher update loop", t);
            }

        }
    }

    private void bufferDiff(DiffData pubData) {
        if(pubData != null){
            addedTriples.addAll(pubData.toAdd);
            deletedTriples.addAll(pubData.toDelete);
        }
    }

    //TODO possible concurrency issues but look minor for now
    public void flush() throws IOException  {

        pageCache.clear();
        counter = 1;
        String fileName = publishDiffBaseName + "/" + PublisherService.getNextPublishPath();
        File parent = new File(fileName).getParentFile();

        if(parent != null)
            parent.mkdirs();
        StringBuilder addString = new StringBuilder();
        for (String s: addedTriples ) {
            addString.append(s);
        }
        RDFDiffWriter.write(addString.toString(), true, fileName, true);
        addedTriples.clear();

        StringBuilder delString = new StringBuilder();
        for (String s: deletedTriples ) {
            delString.append(s);
        }
        RDFDiffWriter.write(delString.toString(), false, fileName, true);
        deletedTriples.clear();
    }
}
