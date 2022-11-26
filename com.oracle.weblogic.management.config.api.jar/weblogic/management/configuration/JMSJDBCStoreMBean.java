package weblogic.management.configuration;

/** @deprecated */
@Deprecated
public interface JMSJDBCStoreMBean extends JMSStoreMBean, GenericJDBCStoreMBean {
   void setDelegatedBean(JDBCStoreMBean var1);

   JDBCStoreMBean getDelegatedBean();
}
