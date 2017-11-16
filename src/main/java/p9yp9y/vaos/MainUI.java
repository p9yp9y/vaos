package p9yp9y.vaos;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import p9yp9y.vaos.installer.InstallerUtil;

/**
 * This UI is the application entry point. A UI may either represent a browser
 * window (or tab) or some part of an HTML page where a Vaadin application is
 * embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is
 * intended to be overridden to add component to the user interface and
 * initialize non-component functionality.
 */
@Theme("mytheme")
@Push
public class MainUI extends UI {

	private TextArea textArea;
	private OutputStream out;
	private HorizontalLayout topPanel;
	private HorizontalLayout appsPanel;
	private Button clockButton;

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		final VerticalLayout layout = new VerticalLayout();
		layout.setMargin(false);
		layout.setSpacing(false);

		final VerticalLayout contentLayout = new VerticalLayout();

		clockButton = new Button();

		final MenuBar barmenu = new MenuBar();
		MenuItem menuItem = barmenu.addItem("Menu", null, null);
		MenuItem editor = menuItem.addItem("Editor", null, (i) -> {
			addWindow();
		});

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

		final TextField name = new TextField();
		textArea = new TextArea();
		name.setCaption("Type your name here:");

		Button button = new Button("Click Me");
		button.addClickListener(e -> {
			try {
				out.write(name.getValue().getBytes());
				out.write('\n');
				out.flush();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		
		TextField url = new TextField("URL");
		url.setValue("https://bintray.com/p9yp9y/vaos/download_file?file_path=p9yp9y%2Fvaos%2Fvaos-hello-app%2F0.0.1-SNAPSHOT%2Fvaos-hello-app-0.0.1-20171116.120548-1.jar");
		url.setWidth("900px");
		Button loadButton = new Button("Load");
		loadButton.addClickListener(e -> {
			try {
				URL[] jarUrls = new URL[] {new URL(url.getValue())};
				new InstallerUtil().start(jarUrls, "p9yp9y.vaos.hello.HelloApplication", new String[] {});
			} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		contentLayout.addComponent(name);
		contentLayout.addComponent(button);
		contentLayout.addComponent(url);
		contentLayout.addComponent(loadButton);

		layout.addComponents(topPanel, contentLayout);
		layout.setSizeFull();
		textArea.setSizeFull();
		textArea.setEnabled(false);

		setContent(layout);

//		try {
//			startXterm();
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		startTime();
	}

	private void addWindow() {
		Window subWindow = new Window("Editor");
		TextArea t = new TextArea();
		t.setSizeFull();
		VerticalLayout subContent = new VerticalLayout(t);
		subContent.setSizeFull();
		subWindow.setContent(subContent);
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

	private void startXterm() throws IOException {
		Runtime rt = Runtime.getRuntime();
		Process proc = rt.exec("bash");

		InputStream in = proc.getInputStream();
		out = proc.getOutputStream();
		InputStream err = proc.getErrorStream();

		startReadThread(in);
		startReadThread(err);
	}

	private Runnable startReadThread(InputStream in) {
		Runnable task = () -> {
			try {
				while (true) {
					int ch = in.read();
					if (ch > 0) {
						access(() -> {
							textArea.setValue(textArea.getValue() + (char) ch);
						});
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		};
		new Thread(task).start();
		return task;
	}

	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = MainUI.class, productionMode = false)
	public static class MyUIServlet extends VaadinServlet {
	}
}
