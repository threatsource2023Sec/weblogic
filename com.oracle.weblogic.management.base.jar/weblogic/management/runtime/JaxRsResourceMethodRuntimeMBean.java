package weblogic.management.runtime;

public interface JaxRsResourceMethodRuntimeMBean extends JaxRsResourceMethodBaseRuntimeMBean {
   String getHttpMethod();

   String[] getConsumingMediaTypes();

   String[] getProducingMediaTypes();

   boolean isSubResource();

   boolean isSubResourceMethod();
}
