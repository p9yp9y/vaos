package p9yp9y.vaos.installer;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;

import p9yp9y.vaos.VaosApplication;
import p9yp9y.vaos.VaosWindowApplication;

import com.vaadin.ui.Button;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class InstallerApplication extends VaosWindowApplication {

    public void main(String[] arg0) {
        TextField url = new TextField("URL");
        url.setValue("https://bintray.com/p9yp9y/vaos/download_file?file_path=p9yp9y%2Fvaos%2Fvaos-hello-app%2F0.0.3%2Fvaos-hello-app-0.0.3.jar");
        url.setWidth("900px");
        Button loadButton = new Button("Load");
        loadButton.addClickListener(e -> {
            try {
                URL[] jarUrls = new URL[]{new URL(url.getValue())};
                VaosApplication app = new InstallerUtil().install(jarUrls, "p9yp9y.vaos.hello.HelloApplication");
                app.main(new String[]{});
            } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException | MalformedURLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });
        VerticalLayout contentLayout = new VerticalLayout(url, loadButton);
        contentLayout.setSizeFull();
        setContent(contentLayout);
    }

    public String getName() {
        return "Installer";
    }
}
