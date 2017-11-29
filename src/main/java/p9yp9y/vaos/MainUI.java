package p9yp9y.vaos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import p9yp9y.vaos.browser.BrowserApplication;
import p9yp9y.vaos.editor.EditorApplication;
import p9yp9y.vaos.installer.InstallerApplication;
import p9yp9y.vaos.io.IOUtil;
import p9yp9y.vaos.settings.SettingsStore;

@Theme("mytheme")
@Push
public class MainUI extends UI {
    private HorizontalLayout topPanel;
    private HorizontalLayout appsPanel;
    private Button           clockButton;
    private SettingsStore    settingsStore;
    private MenuItem menuItem;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        try {
            loadSettingsStore();
            saveSettingsStore();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

        clockButton = new Button();

        final MenuBar barmenu = new MenuBar();
        menuItem = barmenu.addItem("", VaadinIcons.VAADIN_V, null);

        addWindowApplication(new InstallerApplication());
        addWindowApplication(new EditorApplication());
        addWindowApplication(new BrowserApplication());

        appsPanel = new HorizontalLayout();
        appsPanel.setSpacing(false);

        topPanel = new HorizontalLayout(barmenu, appsPanel, clockButton);
        topPanel.setExpandRatio(appsPanel, 1.0f);
        topPanel.setComponentAlignment(clockButton, Alignment.MIDDLE_RIGHT);
        topPanel.setMargin(false);
        topPanel.setSpacing(false);
        topPanel.addStyleName("menuBar");
        topPanel.setSizeUndefined();
        topPanel.setWidth(100.0f, Unit.PERCENTAGE);
        
        GridLayout contentLayout = new GridLayout();

        final VerticalLayout layout = new VerticalLayout(topPanel, contentLayout);
        layout.setMargin(false);
        layout.setSpacing(false);
        setContent(layout);

        startTime();

        //nodejs /home/andris/wetty/app.js -p 3001
    }

    private void addWindowApplication(VaosWindowApplication app) {
         MenuItem editor = menuItem.addItem(app.getName(), null, (i) -> {
            addWindow(app);
        });
    }

    private void saveSettingsStore() throws IOException {
        File f = getSettingsStoreFile();
        ObjectOutputStream ous = new ObjectOutputStream(new FileOutputStream(f));
        ous.writeObject(settingsStore);
        ous.close();
    }

    private SettingsStore loadSettingsStore() throws IOException, ClassNotFoundException {
        File f = getSettingsStoreFile();
        if (!f.exists()) {
            settingsStore = new SettingsStore();
        } else {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
            settingsStore = (SettingsStore)ois.readObject();
            ois.close();
        }
        return settingsStore;
    }

    private File getSettingsStoreFile() throws IOException {
        return IOUtil.getFile("settings.store");
    }

    private void addWindow(VaosWindowApplication subWindow) {
        subWindow.setCaption(subWindow.getName());
        Button b = new Button(subWindow.getCaption());
        topPanel.addComponent(b);
        b.addClickListener(l -> {
            subWindow.focus();
        });
        appsPanel.addComponent(b);

        subWindow.setPosition(200, 200);
        subWindow.setWidth(750, Unit.PIXELS);
        subWindow.setHeight(250, Unit.PIXELS);
        subWindow.addCloseListener(l -> {
            appsPanel.removeComponent(b);
        });
        getUI().addWindow(subWindow);
        subWindow.main(new String[]{});
        subWindow.focus();
    }

    private void startTime() {
        Timer timer = new Timer();
        // task will be scheduled after 5 sec delay
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                access(() -> {
                    clockButton.setCaption(new SimpleDateFormat("HH:mm:ss").format(new Date()));
                    startTime();
                });
            }
        }, 1000);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MainUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}