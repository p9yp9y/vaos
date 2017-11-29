package p9yp9y.vaos.io;

import java.io.File;
import java.io.IOException;

import org.apache.maven.cli.MavenCli;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;

public class GitUtil {
	public static void build(String name, String url) throws IOException, InvalidRemoteException, TransportException, GitAPIException {
		File gitDir = IOUtil.getGitDirectory();
		File directory = new File(gitDir, name);
		if (directory.exists()) {
			PullResult git = Git.open(directory).pull().call();
		} else {
			Git git = Git.cloneRepository().setURI(url).setDirectory(directory).call();			
		}
		
		MavenCli cli = new MavenCli();
		cli.doMain(new String[]{"install", "-DskipTests"}, directory.toString(), System.out, System.err);
	}
}