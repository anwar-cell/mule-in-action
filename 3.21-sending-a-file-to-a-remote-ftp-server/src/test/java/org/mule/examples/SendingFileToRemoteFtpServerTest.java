package org.mule.examples;

import static org.junit.Assert.assertNotNull;

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

public class SendingFileToRemoteFtpServerTest extends FunctionalTestCase {

    private static FakeFtpServer fakeFtpServer;

    @Override
    protected String getConfigResources() {
        return "sending_a_file_to_a_remote_ftp_server.xml";
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
    protected void doSetUp() throws Exception {
        super.doSetUp();
    }

    @Override
    protected void doTearDown() throws Exception {
        stopServer();
    }

    @Test
    public void testCanPutFiles() throws Exception {
        FileUtils.writeStringToFile(new File("./data/in/foo.txt"),"foo");        
        Thread.sleep(5000);
        assertNotNull(fakeFtpServer.getFileSystem().getEntry("/foo.txt"));
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
