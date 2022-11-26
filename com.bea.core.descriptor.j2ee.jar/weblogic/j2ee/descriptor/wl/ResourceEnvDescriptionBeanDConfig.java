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

public class ResourceEnvDescriptionBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private ResourceEnvDescriptionBean beanTreeNode;

   public ResourceEnvDescriptionBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (ResourceEnvDescriptionBean)btn;
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
      return this.getResourceEnvRefName();
   }

   public void initKeyPropertyValue(String value) {
      this.setResourceEnvRefName(value);
   }

   public String getDCBProperties() {
      StringBuffer sb = new StringBuffer();
      sb.append("ResourceEnvRefName: ");
      sb.append(this.beanTreeNode.getResourceEnvRefName());
      sb.append("\n");
      sb.append("JNDIName: ");
      sb.append(this.beanTreeNode.getJNDIName());
      sb.append("\n");
      return sb.toString();
   }

   public void validate() throws ConfigurationException {
   }

   public String getResourceEnvRefName() {
      return this.beanTreeNode.getResourceEnvRefName();
   }

   public void setResourceEnvRefName(String value) {
      this.beanTreeNode.setResourceEnvRefName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ResourceEnvRefName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getJNDIName() {
      return this.beanTreeNode.getJNDIName();
   }

   public void setJNDIName(String value) {
      this.beanTreeNode.setJNDIName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "JNDIName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getResourceLink() {
      return this.beanTreeNode.getResourceLink();
   }

   public void setResourceLink(String value) {
      this.beanTreeNode.setResourceLink(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ResourceLink", (Object)null, (Object)null));
      this.setModified(true);
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
