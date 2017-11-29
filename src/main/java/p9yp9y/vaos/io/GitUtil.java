package p9yp9y.vaos.io;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.maven.cli.MavenCli;
import org.codehaus.plexus.util.DirectoryScanner;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;

public class GitUtil {
	public static Set<URL> build(String url) throws IOException, InvalidRemoteException, TransportException, GitAPIException {
		File gitDir = IOUtil.getGitDirectory();
		File projectDir = new File(gitDir, url.replaceAll(":|/|\\|@", "/"));
		String targetDir = projectDir + "/target";
		if (projectDir.exists()) {
			PullResult git = Git.open(projectDir).pull().call();
		} else {
			Git git = Git.cloneRepository().setURI(url).setDirectory(projectDir).call();			
		}
		
		MavenCli cli = new MavenCli();
		cli.doMain(new String[]{"install", "-DskipTests"}, projectDir.toString(), System.out, System.err);
		
		DirectoryScanner scanner = new DirectoryScanner();
		scanner.setIncludes(new String[]{"**/*.jar"});
		scanner.setBasedir(targetDir);
		scanner.setCaseSensitive(false);
		scanner.scan();
		String[] files = scanner.getIncludedFiles();
		return Arrays.asList(files).stream().map(f -> {
			try {
				URL fileUrl = new File(targetDir, f).toURI().toURL();
				
				return new URL("jar", "", fileUrl.toString() + "!/");
			} catch (MalformedURLException e) {
				e.printStackTrace();
				return null;
			}

		}).collect(Collectors.toSet());
	}
}