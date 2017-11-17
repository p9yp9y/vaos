package p9yp9y.vaos.editor;

import p9yp9y.vaos.VaosWindowApplication;

import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;

public class EditorApplication extends VaosWindowApplication {

    public void main(String[] arg0) {
        TextArea textArea = new TextArea();
        textArea.setSizeFull();
        VerticalLayout contentLayout = new VerticalLayout(textArea);
        contentLayout.setSizeFull();
        setContent(contentLayout);
    }

    public String getName() {
        return "Editor";
    }
}