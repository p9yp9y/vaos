package p9yp9y.vaos.installer;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.jgit.api.errors.GitAPIException;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Resource;
import com.vaadin.ui.Button;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import p9yp9y.vaos.VaosApplication;
import p9yp9y.vaos.VaosWindowApplication;

public class InstallerApplication extends VaosWindowApplication {

    public void main(String[] arg0) {
        TextField url = new TextField("Git URL");
        url.setValue("https://github.com/p9yp9y/vaos-hello-app.git");
        url.setWidth(600, Unit.PIXELS);
        Button loadButton = new Button("Install");
        loadButton.addClickListener(l -> {
            try {
                VaosApplication app = new InstallerUtil().install(url.getValue());
                app.main(new String[]{});
            } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException | IOException | GitAPIException | NoSuchFieldException e) {
                e.printStackTrace();
            }
        });
        VerticalLayout contentLayout = new VerticalLayout(url, loadButton);
        setContent(contentLayout);
        contentLayout.setSizeUndefined();
        setSizeUndefined();
        setResizable(false);
    }

	@Override
    public String getApplicationName() {
        return "Installer";
    }

	@Override
	public Resource getApplicationIcon() {
		return VaadinIcons.PACKAGE;
	}
}
