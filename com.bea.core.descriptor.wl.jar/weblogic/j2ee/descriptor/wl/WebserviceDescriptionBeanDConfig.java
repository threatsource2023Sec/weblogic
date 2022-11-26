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

public class WebserviceDescriptionBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private WebserviceDescriptionBean beanTreeNode;

   public WebserviceDescriptionBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (WebserviceDescriptionBean)btn;
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
      return this.getWebserviceDescriptionName();
   }

   public void initKeyPropertyValue(String value) {
      this.setWebserviceDescriptionName(value);
   }

   public String getDCBProperties() {
      StringBuffer sb = new StringBuffer();
      sb.append("WebserviceDescriptionName: ");
      sb.append(this.beanTreeNode.getWebserviceDescriptionName());
      sb.append("\n");
      return sb.toString();
   }

   public void validate() throws ConfigurationException {
   }

   public String getWebserviceDescriptionName() {
      return this.beanTreeNode.getWebserviceDescriptionName();
   }

   public void setWebserviceDescriptionName(String value) {
      this.beanTreeNode.setWebserviceDescriptionName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "WebserviceDescriptionName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getWebserviceType() {
      return this.beanTreeNode.getWebserviceType();
   }

   public void setWebserviceType(String value) {
      this.beanTreeNode.setWebserviceType(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "WebserviceType", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getWsdlPublishFile() {
      return this.beanTreeNode.getWsdlPublishFile();
   }

   public void setWsdlPublishFile(String value) {
      this.beanTreeNode.setWsdlPublishFile(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "WsdlPublishFile", (Object)null, (Object)null));
      this.setModified(true);
   }

   public PortComponentBean[] getPortComponents() {
      return this.beanTreeNode.getPortComponents();
   }
}
