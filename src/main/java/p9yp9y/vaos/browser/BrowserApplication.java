package p9yp9y.vaos.browser;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Resource;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import p9yp9y.vaos.VaosWindowApplication;

public class BrowserApplication extends VaosWindowApplication {

	private TextField urlField;
	private BrowserFrame browser;

	public void main(String[] arg0) {
		urlField = new TextField();
		urlField.setValueChangeMode(ValueChangeMode.BLUR);
		urlField.addValueChangeListener(event -> {
			open(event.getValue());
		});
		urlField.setWidth(100.0f, Unit.PERCENTAGE);
		//urlField.setHeight(50.0f, Unit.PIXELS);
		browser = new BrowserFrame("Browser");
		browser.setSizeFull();
		browser.setCaption(null);
		VerticalLayout contentLayout = new VerticalLayout(urlField, browser);
		contentLayout.setSizeFull();
		contentLayout.setExpandRatio(browser, 1.0f);
		setContent(contentLayout);
		
		setWidth(750.0f, Unit.PIXELS);
		setHeight(500.0f, Unit.PIXELS);
		
		open("http://demo.vaadin.com/sampler/");
	}
	
	public void open(String url) {
		browser.setSource(new ExternalResource(url));
	}

	@Override
	public String getApplicationName() {
		return "Browser";
	}

	@Override
	public Resource getApplicationIcon() {
		return VaadinIcons.BROWSER;
	}
}