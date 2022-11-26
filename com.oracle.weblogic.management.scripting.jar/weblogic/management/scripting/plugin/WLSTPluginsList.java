package weblogic.management.scripting.plugin;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public interface WLSTPluginsList {
   List SUBSYSTEM_LIST = Collections.unmodifiableList(Arrays.asList("weblogic.diagnostics.accessor.AccessorPlugin", "weblogic.store.admintool.StoreAdminWLSTPlugin"));
}
