package p9yp9y.vaos.editor;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Resource;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;

import p9yp9y.vaos.VaosWindowApplication;

public class EditorApplication extends VaosWindowApplication {

    public void main(String[] arg0) {
        TextArea textArea = new TextArea();
        textArea.setSizeFull();
        VerticalLayout contentLayout = new VerticalLayout(textArea);
        contentLayout.setSizeFull();
        setContent(contentLayout);
    }

	@Override
    public String getApplicationName() {
        return "Editor";
    }

	@Override
	public Resource getApplicationIcon() {
		return VaadinIcons.BOOK;
	}
}