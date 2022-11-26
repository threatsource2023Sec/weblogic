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

public class ConnectionDefinitionBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private ConnectionDefinitionBean beanTreeNode;

   public ConnectionDefinitionBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (ConnectionDefinitionBean)btn;
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
      return this.getConnectionFactoryInterface();
   }

   public void initKeyPropertyValue(String value) {
      this.setConnectionFactoryInterface(value);
   }

   public String getDCBProperties() {
      StringBuffer sb = new StringBuffer();
      sb.append("ConnectionFactoryInterface: ");
      sb.append(this.beanTreeNode.getConnectionFactoryInterface());
      sb.append("\n");
      return sb.toString();
   }

   public void validate() throws ConfigurationException {
   }

   public String getConnectionFactoryInterface() {
      return this.beanTreeNode.getConnectionFactoryInterface();
   }

   public void setConnectionFactoryInterface(String value) {
      this.beanTreeNode.setConnectionFactoryInterface(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ConnectionFactoryInterface", (Object)null, (Object)null));
      this.setModified(true);
   }

   public ConnectionDefinitionPropertiesBean getDefaultConnectionProperties() {
      return this.beanTreeNode.getDefaultConnectionProperties();
   }

   public boolean isDefaultConnectionPropertiesSet() {
      return this.beanTreeNode.isDefaultConnectionPropertiesSet();
   }

   public ConnectionInstanceBean[] getConnectionInstances() {
      return this.beanTreeNode.getConnectionInstances();
   }

   public String getId() {
      return this.beanTreeNode.getId();
   }

   public void setId(String value) {
      this.beanTreeNode.setId(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Id", (Object)null, (Object)null));
      this.setModified(true);
   }
}
