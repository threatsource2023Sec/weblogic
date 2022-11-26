package weblogic.management.utils.situationalconfig;

import java.io.InputStream;
import java.util.HashMap;
import weblogic.descriptor.Descriptor;

public interface SituationalConfigLoader {
   InputStream createFederatedStream(InputStream var1) throws Exception;

   boolean loadConfig(boolean var1) throws Exception;

   boolean loadConfig(boolean var1, Descriptor var2) throws Exception;

   boolean loadJMS(String var1, boolean var2, HashMap var3) throws Exception;

   boolean loadJDBC(String var1, boolean var2, HashMap var3) throws Exception;

   boolean loadDiagnostics(String var1, boolean var2, HashMap var3) throws Exception;

   boolean isSituationalConfigApplied();

   void setSituationalConfigApplied(boolean var1);
}
