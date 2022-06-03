package Language;

import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.ResourceBundle;

public class Languages {
//    private static ResourceBundle bundle = ResourceBundle.getBundle("resources");
    private static ObjectProperty<ResourceBundle> resources = new SimpleObjectProperty<>();

    public static void setResources(ResourceBundle bundle) {
        resources.set(bundle);
    }

    public static ResourceBundle getResources() {
        return resources.get();
    }

    public static StringBinding getString(String string) {
        return new StringBinding() {
            {
                bind(resources);
            }
            @Override
            protected String computeValue() {
                return getResources().getString(string);
            }
        };
    }






}
