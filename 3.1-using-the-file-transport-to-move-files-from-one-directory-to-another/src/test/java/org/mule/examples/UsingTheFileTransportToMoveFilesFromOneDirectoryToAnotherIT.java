package org.mule.examples;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Collection;
import java.util.concurrent.CountDownLatch;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mule.tck.junit4.FunctionalTestCase;
import org.mule.util.FileUtils;

public class UsingTheFileTransportToMoveFilesFromOneDirectoryToAnotherIT extends FunctionalTestCase {

    CountDownLatch copyExpenseReportLatch;
    CountDownLatch callbackLatch;

    @BeforeClass
    public static void setupDirectories() throws Exception {
        File dataDirectory = new File("./data");
        if (dataDirectory.exists()) {
            FileUtils.deleteDirectory(dataDirectory);
        }
        dataDirectory.mkdirs();
        new File("./data/expenses/1/in").mkdirs();
        new File("./data/expenses/out").mkdirs();
        
    }
    
    @Override
    protected String getConfigResources() {
        return "using_the_file_transportto_move_files_from_one_directory_to_another.xml";
    }

    @Test
    public void testCanCopyExpenseReports() throws Exception {
        FileUtils.writeStringToFile(new File("./data/expenses/1/in", "expenses.xls"), "a crazy bar tab");
        Collection<File> files = FileUtils.listFiles(new File("./data/expenses"), new String[]{"xls"}, true);
        Thread.sleep(5000);
        assertEquals(1, files.size());
    }
    
}
