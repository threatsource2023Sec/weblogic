package weblogic.j2ee.descriptor.wl;

public interface JASPICProviderBean {
   boolean isEnabled();

   String getAuthConfigProviderName();

   void setAuthConfigProviderName(String var1);
}
