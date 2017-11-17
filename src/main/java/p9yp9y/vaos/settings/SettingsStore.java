package p9yp9y.vaos.settings;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class SettingsStore implements Serializable {

    public Set<ApplicationInfo> applications = new HashSet<>();
}
