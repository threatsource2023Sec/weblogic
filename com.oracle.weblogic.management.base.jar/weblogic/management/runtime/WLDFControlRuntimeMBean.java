package weblogic.management.runtime;

public interface WLDFControlRuntimeMBean extends RuntimeMBean {
   WLDFSystemResourceControlRuntimeMBean createSystemResourceControl(String var1, String var2);

   void destroySystemResourceControl(WLDFSystemResourceControlRuntimeMBean var1);

   WLDFSystemResourceControlRuntimeMBean lookupSystemResourceControl(String var1);

   WLDFSystemResourceControlRuntimeMBean[] getSystemResourceControls();
}
