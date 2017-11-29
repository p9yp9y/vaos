package p9yp9y.vaos.installer;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.Set;
import java.util.jar.JarEntry;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;

import p9yp9y.vaos.VaosApplication;
import p9yp9y.vaos.io.GitUtil;

public class InstallerUtil {
	public VaosApplication install(String gitUrl) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, InvalidRemoteException, TransportException, IOException, GitAPIException, NoSuchFieldException {
		Set<URL> jarUrls = GitUtil.build(gitUrl);	
		return install(jarUrls.iterator().next());
	}
	
	public VaosApplication install(URL jarUrl) throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		JarURLConnection uc = (JarURLConnection)jarUrl.openConnection();
		
		Enumeration<JarEntry> entries = uc.getJarFile().entries();
				
		while (entries.hasMoreElements()) {
			JarEntry e = entries.nextElement();
			String name = e.getName();
		}
		
		ClassLoader cLoader = new URLClassLoader(new URL[] {jarUrl}, null);
		
//		Class<?> classToLoad = classes.stream().filter(c -> c.isAnnotationPresent(VaosMainApplication.class)).findFirst().get();

//		VaosApplication instance = (VaosApplication) classToLoad.newInstance();
		Class<?> classToLoad = Class.forName("p9yp9y.vaos.hello.HelloApplication", true, cLoader);
		return (VaosApplication) classToLoad.newInstance();
	}

	public void start(VaosApplication vaosApplication, String[] args) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		vaosApplication.main(args);
	}
}
