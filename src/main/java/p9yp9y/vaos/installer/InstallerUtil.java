package p9yp9y.vaos.installer;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;

import p9yp9y.vaos.VaosApplication;
import p9yp9y.vaos.io.GitUtil;

public class InstallerUtil {
	public VaosApplication install(String gitUrl, String className) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InvalidRemoteException, TransportException, IOException, GitAPIException {
		URL[] jarUrls = GitUtil.build(gitUrl);	
		URLClassLoader child = new URLClassLoader(jarUrls, this.getClass().getClassLoader());
		Class classToLoad = Class.forName(className, true, child);
		VaosApplication instance = (VaosApplication) classToLoad.newInstance();
        return instance;
	}
	
	public void start(VaosApplication vaosApplication, String[] args) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		vaosApplication.main(args);
	}
}
