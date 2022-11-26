package weblogic.j2ee.descriptor.wl;

import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.deploy.model.DDBean;
import javax.enterprise.deploy.model.DDBeanRoot;
import javax.enterprise.deploy.spi.DConfigBean;
import javax.enterprise.deploy.spi.exceptions.ConfigurationException;
import javax.enterprise.deploy.spi.exceptions.InvalidModuleException;
import weblogic.deploy.api.internal.utils.Debug;
import weblogic.deploy.api.spi.WebLogicDeploymentConfiguration;
import weblogic.deploy.api.spi.config.BasicDConfigBean;
import weblogic.deploy.api.spi.config.BasicDConfigBeanRoot;
import weblogic.deploy.api.spi.config.DescriptorParser;
import weblogic.deploy.api.spi.config.DescriptorSupport;
import weblogic.descriptor.DescriptorBean;

public class JDBCDataSourceBeanDConfig extends BasicDConfigBeanRoot {
   private static final boolean debug = Debug.isDebug("config");
   private JDBCDataSourceBean beanTreeNode;

   public JDBCDataSourceBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (JDBCDataSourceBean)btn;
      this.beanTree = btn;
      this.parent = (BasicDConfigBean)parent;
      this.initXpaths();
      this.customInit();
   }

   public JDBCDataSourceBeanDConfig(DDBeanRoot root, WebLogicDeploymentConfiguration dc, DescriptorBean beanTree, String name, DescriptorSupport ds) throws InvalidModuleException, IOException {
      super(root, dc, name, ds);
      this.beanTree = beanTree;
      this.beanTreeNode = (JDBCDataSourceBean)beanTree;

      try {
         this.initXpaths();
         this.customInit();
      } catch (ConfigurationException var7) {
         throw new InvalidModuleException(var7.toString());
      }
   }

   public JDBCDataSourceBeanDConfig(DDBeanRoot root, WebLogicDeploymentConfiguration dc, String name, DescriptorSupport ds) throws InvalidModuleException, IOException {
      super(root, dc, name, ds);

      try {
         this.initXpaths();
         if (debug) {
            Debug.say("Creating new root object");
         }

         this.beanTree = DescriptorParser.getWLSEditableDescriptorBean(JDBCDataSourceBean.class);
         this.beanTreeNode = (JDBCDataSourceBean)this.beanTree;
         this.customInit();
      } catch (ConfigurationException var6) {
         throw new InvalidModuleException(var6.toString());
      }
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
      return this.getName();
   }

   public void initKeyPropertyValue(String value) {
      this.setName(value);
   }

   public String getDCBProperties() {
      StringBuffer sb = new StringBuffer();
      sb.append("Name: ");
      sb.append(this.beanTreeNode.getName());
      sb.append("\n");
      return sb.toString();
   }

   public void validate() throws ConfigurationException {
   }

   public String getName() {
      return this.beanTreeNode.getName();
   }

   public void setName(String value) {
      this.beanTreeNode.setName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Name", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getDatasourceType() {
      return this.beanTreeNode.getDatasourceType();
   }

   public void setDatasourceType(String value) {
      this.beanTreeNode.setDatasourceType(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DatasourceType", (Object)null, (Object)null));
      this.setModified(true);
   }

   public JDBCPropertiesBean getInternalProperties() {
      return this.beanTreeNode.getInternalProperties();
   }

   public JDBCDriverParamsBean getJDBCDriverParams() {
      return this.beanTreeNode.getJDBCDriverParams();
   }

   public JDBCConnectionPoolParamsBean getJDBCConnectionPoolParams() {
      return this.beanTreeNode.getJDBCConnectionPoolParams();
   }

   public JDBCDataSourceParamsBean getJDBCDataSourceParams() {
      return this.beanTreeNode.getJDBCDataSourceParams();
   }

   public JDBCXAParamsBean getJDBCXAParams() {
      return this.beanTreeNode.getJDBCXAParams();
   }

   public JDBCOracleParamsBean getJDBCOracleParams() {
      return this.beanTreeNode.getJDBCOracleParams();
   }

   public String getVersion() {
      return this.beanTreeNode.getVersion();
   }

   public void setVersion(String value) {
      this.beanTreeNode.setVersion(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Version", (Object)null, (Object)null));
      this.setModified(true);
   }

   public long getId() {
      return this.beanTreeNode.getId();
   }

   public DConfigBean[] getSecondaryDescriptors() {
      return super.getSecondaryDescriptors();
   }
}
