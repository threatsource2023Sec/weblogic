package oracle.jrockit.jfr.settings;

import com.oracle.jrockit.jfr.NoSuchEventException;
import java.util.Collection;
import java.util.List;

public interface EventSettings {
   EventSetting getSetting(int var1) throws NoSuchEventException;

   void putSettings(Collection var1);

   Collection getSettings();

   Collection getEventDefaults();

   List getEventDefaultSets();

   void addEventDefaultSet(EventDefaultSet var1);

   void replaceEventDefaultSets(Collection var1);
}
