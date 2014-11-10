package org.mule.examples;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockftpserver.fake.FakeFtpServer;
import org.mockftpserver.fake.UserAccount;
import org.mockftpserver.fake.filesystem.DirectoryEntry;
import org.mockftpserver.fake.filesystem.FileEntry;
import org.mockftpserver.fake.filesystem.FileSystem;
import org.mockftpserver.fake.filesystem.UnixFakeFileSystem;
import org.mule.tck.junit4.FunctionalTestCase;
import org.mule.util.FileUtils;

public class PollingARemoteFtpDirectoryEveryHourForNewFilesTest extends FunctionalTestCase {

    private static FakeFtpServer fakeFtpServer;
    
    @Override
    protected String getConfigResources() {
        return "polling_a_remote_ftp_directory_every_hour_for_new_files.xml";
    }

    @BeforeClass
    public static void setupDirectories() throws Exception {
        File dataDirectory = new File("./data");
        if (dataDirectory.exists()) {
            FileUtils.deleteDirectory(dataDirectory);
        }
        dataDirectory.mkdirs();
        new File("./data/sales/statistics").mkdirs();
        new File("./data/in").mkdirs();
        startServer();
                
    }
    
    @Override
    protected void doTearDown() throws Exception {
        stopServer();
    }

    @Test
    public void testCanGetFiles() throws Exception {
        assertEquals(1, FileUtils.listFiles(new File("./data/sales/statistics"), new String[]{"dat"}, false).size());
    }
    
    static void startServer() {
        fakeFtpServer = new FakeFtpServer();
        fakeFtpServer.setServerControlPort(9879);
        fakeFtpServer.addUserAccount(new UserAccount("joe", "123456", "/"));

        FileSystem fileSystem = new UnixFakeFileSystem();
        fileSystem.add(new DirectoryEntry("/data/prancingdonkey/catalog"));
        fileSystem.add(new FileEntry("/ftp/incoming/file1.txt", "MULEINACTION"));
        fakeFtpServer.setFileSystem(fileSystem);
        fakeFtpServer.start();
    }

    void stopServer() {
        fakeFtpServer.stop();
    }

}
