package weblogic.j2ee.descriptor.wl;

public interface CdiDescriptorBean {
   boolean isImplicitBeanDiscoveryEnabled();

   void setImplicitBeanDiscoveryEnabled(boolean var1);

   boolean isImplicitBeanDiscoveryEnabledSet();

   String getPolicy();

   void setPolicy(String var1);

   boolean isPolicySet();
}
