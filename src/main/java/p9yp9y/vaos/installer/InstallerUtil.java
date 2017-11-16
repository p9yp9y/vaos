package p9yp9y.vaos.installer;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;

import p9yp9y.vaos.VaosApplication;

public class InstallerUtil {
	public void start(URL[] jarUrls, String className, String[] args) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		URLClassLoader child = new URLClassLoader(jarUrls, this.getClass().getClassLoader());
		Class classToLoad = Class.forName(className, true, child);
		VaosApplication instance = (VaosApplication) classToLoad.newInstance();
		instance.main(args);
	}
}
