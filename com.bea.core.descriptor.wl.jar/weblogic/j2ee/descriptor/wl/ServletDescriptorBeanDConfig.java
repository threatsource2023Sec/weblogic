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

public class ServletDescriptorBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private ServletDescriptorBean beanTreeNode;

   public ServletDescriptorBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (ServletDescriptorBean)btn;
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
      return this.getServletName();
   }

   public void initKeyPropertyValue(String value) {
      this.setServletName(value);
   }

   public String getDCBProperties() {
      StringBuffer sb = new StringBuffer();
      sb.append("ServletName: ");
      sb.append(this.beanTreeNode.getServletName());
      sb.append("\n");
      return sb.toString();
   }

   public void validate() throws ConfigurationException {
   }

   public String getServletName() {
      return this.beanTreeNode.getServletName();
   }

   public void setServletName(String value) {
      this.beanTreeNode.setServletName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ServletName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getRunAsPrincipalName() {
      return this.beanTreeNode.getRunAsPrincipalName();
   }

   public void setRunAsPrincipalName(String value) {
      this.beanTreeNode.setRunAsPrincipalName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "RunAsPrincipalName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getInitAsPrincipalName() {
      return this.beanTreeNode.getInitAsPrincipalName();
   }

   public void setInitAsPrincipalName(String value) {
      this.beanTreeNode.setInitAsPrincipalName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "InitAsPrincipalName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getDestroyAsPrincipalName() {
      return this.beanTreeNode.getDestroyAsPrincipalName();
   }

   public void setDestroyAsPrincipalName(String value) {
      this.beanTreeNode.setDestroyAsPrincipalName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DestroyAsPrincipalName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getDispatchPolicy() {
      return this.beanTreeNode.getDispatchPolicy();
   }

   public void setDispatchPolicy(String value) {
      this.beanTreeNode.setDispatchPolicy(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DispatchPolicy", (Object)null, (Object)null));
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
