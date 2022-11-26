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

public class ServiceReferenceDescriptionBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private ServiceReferenceDescriptionBean beanTreeNode;

   public ServiceReferenceDescriptionBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (ServiceReferenceDescriptionBean)btn;
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
      return this.getServiceRefName();
   }

   public void initKeyPropertyValue(String value) {
      this.setServiceRefName(value);
   }

   public String getDCBProperties() {
      StringBuffer sb = new StringBuffer();
      sb.append("ServiceRefName: ");
      sb.append(this.beanTreeNode.getServiceRefName());
      sb.append("\n");
      return sb.toString();
   }

   public void validate() throws ConfigurationException {
   }

   public String getServiceRefName() {
      return this.beanTreeNode.getServiceRefName();
   }

   public void setServiceRefName(String value) {
      this.beanTreeNode.setServiceRefName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ServiceRefName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getWsdlUrl() {
      return this.beanTreeNode.getWsdlUrl();
   }

   public void setWsdlUrl(String value) {
      this.beanTreeNode.setWsdlUrl(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "WsdlUrl", (Object)null, (Object)null));
      this.setModified(true);
   }

   public PropertyNamevalueBean[] getCallProperties() {
      return this.beanTreeNode.getCallProperties();
   }

   public PortInfoBean[] getPortInfos() {
      return this.beanTreeNode.getPortInfos();
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
