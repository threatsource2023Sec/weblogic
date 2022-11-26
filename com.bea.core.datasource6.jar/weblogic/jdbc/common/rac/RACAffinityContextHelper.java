package weblogic.jdbc.common.rac;

public interface RACAffinityContextHelper {
   Object createAffinityContext(String var1, String var2, String var3) throws RACAffinityContextException;

   Object createAffinityContext(String var1, String var2, String var3, boolean var4) throws RACAffinityContextException;

   String getDatabaseName(Object var1) throws RACAffinityContextException;

   String getServiceName(Object var1) throws RACAffinityContextException;

   String getInstanceName(Object var1) throws RACAffinityContextException;

   boolean isForInstanceAffinity(Object var1) throws RACAffinityContextException;

   RACInstance createRACInstance(Object var1) throws RACAffinityContextException;
}
