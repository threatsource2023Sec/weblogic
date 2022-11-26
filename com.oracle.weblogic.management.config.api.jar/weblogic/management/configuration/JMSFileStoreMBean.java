package weblogic.management.configuration;

/** @deprecated */
@Deprecated
public interface JMSFileStoreMBean extends JMSStoreMBean, GenericFileStoreMBean {
   void setDelegatedBean(FileStoreMBean var1);

   FileStoreMBean getDelegatedBean();

   void setDelegatedJMSServer(JMSServerMBean var1);

   JMSServerMBean getDelegatedJMSServer();
}
