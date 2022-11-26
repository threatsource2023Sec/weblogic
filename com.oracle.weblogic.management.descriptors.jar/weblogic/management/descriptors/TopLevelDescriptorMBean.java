package weblogic.management.descriptors;

import java.io.IOException;
import java.util.Properties;

public interface TopLevelDescriptorMBean extends BaseDescriptor {
   String CHANGELIST_FILENAME = "_wl_dynamic_change_list.properties";

   void validate() throws DescriptorValidationException;

   void usePersistenceDestination(String var1);

   void persist() throws IOException;

   void persist(Properties var1) throws IOException;
}
