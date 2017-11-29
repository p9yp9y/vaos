package git;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URL;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.junit.Test;

import p9yp9y.vaos.io.GitUtil;

public class JGitTest {

	@Test
	public void testBuild() throws InvalidRemoteException, TransportException, GitAPIException, IOException {
		URL[] res = GitUtil.build("https://github.com/p9yp9y/vaos-hello-app.git");
		
		assertEquals(1, res.length);
		
		assertEquals("file:/home/andris/workspace3/vaos/vaos-hello-app-0.0.7-SNAPSHOT.jar", res[0].toString());
		
	}
}	
