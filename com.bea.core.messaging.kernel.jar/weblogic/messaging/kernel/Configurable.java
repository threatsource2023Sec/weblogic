package weblogic.messaging.kernel;

import java.util.Map;

public interface Configurable {
   String getName();

   Map getProperties();

   Object getProperty(String var1);

   int getIntProperty(String var1, int var2);

   long getLongProperty(String var1, long var2);

   boolean getBooleanProperty(String var1, boolean var2);

   String getStringProperty(String var1, String var2);

   void setProperties(Map var1) throws KernelException;

   void setProperty(String var1, Object var2) throws KernelException;
}
