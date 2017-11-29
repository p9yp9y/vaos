package p9yp9y.vaos.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class IOUtil {
    public static File getFile(String fileName) throws IOException {
        String currentUsersHomeDir = System.getProperty("user.home");
        File vaosfiles = new File(currentUsersHomeDir, ".vaos");
        if (!vaosfiles.exists()) {
            Files.createDirectories(vaosfiles.toPath());
        }
        return new File(vaosfiles, fileName);
    }
    
    public static File getGitDirectory() throws IOException {
		 File d = IOUtil.getFile("git");
		 if (!d.exists()) {
			 d.mkdir();
		 }
		 return d;
    }
}
