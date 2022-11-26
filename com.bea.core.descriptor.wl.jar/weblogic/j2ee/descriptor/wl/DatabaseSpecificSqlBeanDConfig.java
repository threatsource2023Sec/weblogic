package weblogic.j2ee.descriptor.wl;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.deploy.model.DDBean;
import javax.enterprise.deploy.spi.DConfigBean;
import javax.enterprise.deploy.spi.exceptions.ConfigurationException;
import weblogic.deploy.api.internal.utils.Debug;
import weblogic.deploy.api.spi.config.BasicDConfigBean;
import weblogic.descriptor.DescriptorBean;

public class DatabaseSpecificSqlBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private DatabaseSpecificSqlBean beanTreeNode;

   public DatabaseSpecificSqlBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (DatabaseSpecificSqlBean)btn;
      this.beanTree = btn;
      this.parent = (BasicDConfigBean)parent;
      this.initXpaths();
      this.customInit();
   }

   private void initXpaths() throws ConfigurationException {
      List xlist = new ArrayList();
      this.xpaths = (String[])((String[])xlist.toArray(new String[0]));
   }

   private void customInit() throws ConfigurationException {
   }

   public DConfigBean createDConfigBean(DDBean ddb, DConfigBean parent) throws ConfigurationException {
      return null;
   }

   public String keyPropertyValue() {
      return null;
   }

   public void initKeyPropertyValue(String value) {
   }

   public String getDCBProperties() {
      StringBuffer sb = new StringBuffer();
      return sb.toString();
   }

   public void validate() throws ConfigurationException {
   }

   public String getDatabaseType() {
      return this.beanTreeNode.getDatabaseType();
   }

   public void setDatabaseType(String value) {
      this.beanTreeNode.setDatabaseType(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DatabaseType", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getSql() {
      return this.beanTreeNode.getSql();
   }

   public void setSql(String value) {
      this.beanTreeNode.setSql(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Sql", (Object)null, (Object)null));
      this.setModified(true);
   }
}
