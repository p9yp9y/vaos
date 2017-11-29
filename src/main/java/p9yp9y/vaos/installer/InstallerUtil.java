package p9yp9y.vaos.installer;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;

import org.apache.commons.io.FilenameUtils;
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
		Set<String> classSet = new HashSet<>();
				
		while (entries.hasMoreElements()) {
			JarEntry e = entries.nextElement();
			String name = e.getName();
			if (name.toLowerCase().endsWith(".class")) {
				classSet.add(FilenameUtils.removeExtension(name).replaceAll("\\||/", "."));
			}
		}
		
		ClassLoader cLoader = new URLClassLoader(new URL[] {jarUrl}, this.getClass().getClassLoader());
		String classNameToLoad = classSet.stream().filter(cn -> {
			try {
				return Class.forName(cn, true, cLoader).getClass().isInstance(Object.class);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				return false;
			}
		}).limit(1).findFirst().get();
		
		Class<?> classToLoad = Class.forName(classNameToLoad, true, cLoader);
		return (VaosApplication) classToLoad.newInstance();
	}

	public void start(VaosApplication vaosApplication, String[] args) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		vaosApplication.main(args);
	}
}
