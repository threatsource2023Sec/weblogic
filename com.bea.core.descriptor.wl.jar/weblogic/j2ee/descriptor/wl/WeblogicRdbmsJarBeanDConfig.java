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

public class WeblogicRdbmsJarBeanDConfig extends BasicDConfigBeanRoot {
   private static final boolean debug = Debug.isDebug("config");
   private WeblogicRdbmsJarBean beanTreeNode;

   public WeblogicRdbmsJarBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (WeblogicRdbmsJarBean)btn;
      this.beanTree = btn;
      this.parent = (BasicDConfigBean)parent;
      this.initXpaths();
      this.customInit();
   }

   public WeblogicRdbmsJarBeanDConfig(DDBeanRoot root, WebLogicDeploymentConfiguration dc, DescriptorBean beanTree, String name, DescriptorSupport ds) throws InvalidModuleException, IOException {
      super(root, dc, name, ds);
      this.beanTree = beanTree;
      this.beanTreeNode = (WeblogicRdbmsJarBean)beanTree;

      try {
         this.initXpaths();
         this.customInit();
      } catch (ConfigurationException var7) {
         throw new InvalidModuleException(var7.toString());
      }
   }

   public WeblogicRdbmsJarBeanDConfig(DDBeanRoot root, WebLogicDeploymentConfiguration dc, String name, DescriptorSupport ds) throws InvalidModuleException, IOException {
      super(root, dc, name, ds);

      try {
         this.initXpaths();
         if (debug) {
            Debug.say("Creating new root object");
         }

         this.beanTree = DescriptorParser.getWLSEditableDescriptorBean(WeblogicRdbmsJarBean.class);
         this.beanTreeNode = (WeblogicRdbmsJarBean)this.beanTree;
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

   public WeblogicRdbmsBeanBean[] getWeblogicRdbmsBeans() {
      return this.beanTreeNode.getWeblogicRdbmsBeans();
   }

   public WeblogicRdbmsRelationBean[] getWeblogicRdbmsRelations() {
      return this.beanTreeNode.getWeblogicRdbmsRelations();
   }

   public boolean isOrderDatabaseOperations() {
      return this.beanTreeNode.isOrderDatabaseOperations();
   }

   public void setOrderDatabaseOperations(boolean value) {
      this.beanTreeNode.setOrderDatabaseOperations(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "OrderDatabaseOperations", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isEnableBatchOperations() {
      return this.beanTreeNode.isEnableBatchOperations();
   }

   public void setEnableBatchOperations(boolean value) {
      this.beanTreeNode.setEnableBatchOperations(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "EnableBatchOperations", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getCreateDefaultDbmsTables() {
      return this.beanTreeNode.getCreateDefaultDbmsTables();
   }

   public void setCreateDefaultDbmsTables(String value) {
      this.beanTreeNode.setCreateDefaultDbmsTables(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "CreateDefaultDbmsTables", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getValidateDbSchemaWith() {
      return this.beanTreeNode.getValidateDbSchemaWith();
   }

   public void setValidateDbSchemaWith(String value) {
      this.beanTreeNode.setValidateDbSchemaWith(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ValidateDbSchemaWith", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getDatabaseType() {
      return this.beanTreeNode.getDatabaseType();
   }

   public void setDatabaseType(String value) {
      this.beanTreeNode.setDatabaseType(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DatabaseType", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getDefaultDbmsTablesDdl() {
      return this.beanTreeNode.getDefaultDbmsTablesDdl();
   }

   public void setDefaultDbmsTablesDdl(String value) {
      this.beanTreeNode.setDefaultDbmsTablesDdl(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DefaultDbmsTablesDdl", (Object)null, (Object)null));
      this.setModified(true);
   }

   public CompatibilityBean getCompatibility() {
      return this.beanTreeNode.getCompatibility();
   }

   public String getId() {
      return this.beanTreeNode.getId();
   }

   public void setId(String value) {
      this.beanTreeNode.setId(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Id", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getVersion() {
      return this.beanTreeNode.getVersion();
   }

   public void setVersion(String value) {
      this.beanTreeNode.setVersion(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Version", (Object)null, (Object)null));
      this.setModified(true);
   }

   public DConfigBean[] getSecondaryDescriptors() {
      return super.getSecondaryDescriptors();
   }
}
