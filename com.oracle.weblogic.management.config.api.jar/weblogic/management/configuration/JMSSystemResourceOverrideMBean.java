package weblogic.management.configuration;

public interface JMSSystemResourceOverrideMBean extends ConfigurationMBean {
   ForeignServerOverrideMBean[] getForeignServers();

   ForeignServerOverrideMBean createForeignServer(String var1);

   void destroyForeignServer(ForeignServerOverrideMBean var1);

   ForeignServerOverrideMBean lookupForeignServer(String var1);
}
