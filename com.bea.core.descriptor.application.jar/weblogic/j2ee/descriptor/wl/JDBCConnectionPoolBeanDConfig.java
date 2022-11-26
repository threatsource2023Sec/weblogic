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

public class JDBCConnectionPoolBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private JDBCConnectionPoolBean beanTreeNode;

   public JDBCConnectionPoolBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (JDBCConnectionPoolBean)btn;
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
      return this.getDataSourceJNDIName();
   }

   public void initKeyPropertyValue(String value) {
      this.setDataSourceJNDIName(value);
   }

   public String getDCBProperties() {
      StringBuffer sb = new StringBuffer();
      sb.append("DataSourceJNDIName: ");
      sb.append(this.beanTreeNode.getDataSourceJNDIName());
      sb.append("\n");
      return sb.toString();
   }

   public void validate() throws ConfigurationException {
   }

   public String getDataSourceJNDIName() {
      return this.beanTreeNode.getDataSourceJNDIName();
   }

   public void setDataSourceJNDIName(String value) {
      this.beanTreeNode.setDataSourceJNDIName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DataSourceJNDIName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public ConnectionFactoryBean getConnectionFactory() {
      return this.beanTreeNode.getConnectionFactory();
   }

   public ApplicationPoolParamsBean getPoolParams() {
      return this.beanTreeNode.getPoolParams();
   }

   public DriverParamsBean getDriverParams() {
      return this.beanTreeNode.getDriverParams();
   }

   public String getAclName() {
      return this.beanTreeNode.getAclName();
   }

   public void setAclName(String value) {
      this.beanTreeNode.setAclName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "AclName", (Object)null, (Object)null));
      this.setModified(true);
   }
}
