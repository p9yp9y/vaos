package git;

import java.io.IOException;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.junit.Test;

import p9yp9y.vaos.io.GitUtil;

public class JGitTest {

	@Test
	public void testClone() throws InvalidRemoteException, TransportException, GitAPIException, IOException {
		GitUtil.build("https://github.com/p9yp9y/vaos-hello-app.git");
	}
}	
