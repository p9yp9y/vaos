package p9yp9y.vaos.installer;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;

import p9yp9y.vaos.VaosApplication;

public class InstallerUtil {
	public VaosApplication install(URL[] jarUrls, String className) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		URLClassLoader child = new URLClassLoader(jarUrls, this.getClass().getClassLoader());
		Class classToLoad = Class.forName(className, true, child);
		VaosApplication instance = (VaosApplication) classToLoad.newInstance();
        return instance;
	}
	
	public void start(VaosApplication vaosApplication, String[] args) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		vaosApplication.main(args);
	}
}
