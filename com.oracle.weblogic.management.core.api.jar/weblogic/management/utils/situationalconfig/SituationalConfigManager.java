package weblogic.management.utils.situationalconfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import org.jvnet.hk2.annotations.Contract;
import weblogic.descriptor.Descriptor;

@Contract
public interface SituationalConfigManager {
   boolean isInitialized();

   boolean hasActiveConfiguration();

   void reload() throws IOException;

   void initialize(long var1) throws Exception;

   void start() throws Exception;

   void findAndLoadSitConfigFiles() throws Exception;

   void expireSituationalConfig(SituationalConfigFile var1) throws Exception;

   void setPollInterval(long var1);

   void setupTimers() throws Exception;

   boolean isDeferringNonDynamicChanges();

   InputStream getSituationalConfigInputStream(String var1);

   void applyChangesToTemporaryDescriptors(Descriptor var1, HashMap var2) throws IOException;
}
