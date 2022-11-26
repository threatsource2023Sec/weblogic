package weblogic.management.configuration;

public interface CdiContainerMBean extends ConfigurationMBean {
   boolean IMPLICIT_BEAN_DISCOVERY_ENABLED_DEFAULT = true;
   String POLICY_ENABLED = "Enabled";
   String POLICY_DISABLED = "Disabled";
   String POLCY_DEFAULT = "Enabled";

   boolean isImplicitBeanDiscoveryEnabled();

   void setImplicitBeanDiscoveryEnabled(boolean var1);

   boolean isImplicitBeanDiscoveryEnabledSet();

   String getPolicy();

   void setPolicy(String var1);

   boolean isPolicySet();
}
