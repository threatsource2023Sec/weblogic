package weblogic.management.configuration;

public interface ForeignJNDIObjectMBean extends ConfigurationMBean {
   void setLocalJNDIName(String var1);

   String getLocalJNDIName();

   void setRemoteJNDIName(String var1);

   String getRemoteJNDIName();
}
