package weblogic.jdbc.common.internal;

public interface AffinityContextHelper {
   String getKey(Object var1) throws AffinityContextException;

   String getKey(String var1, String var2);

   String[] getKeys();

   Object getAffinityContext(String var1);

   boolean setAffinityContext(String var1, Object var2);

   Object createAffinityContext(String var1, String var2, String var3) throws AffinityContextException;

   String getDatabaseName(Object var1) throws AffinityContextException;

   String getServiceName(Object var1) throws AffinityContextException;

   String getInstanceName(Object var1) throws AffinityContextException;

   boolean isApplicationContextAvailable();
}
