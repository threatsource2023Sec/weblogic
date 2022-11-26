package weblogic.diagnostics.descriptor;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.deploy.model.DDBean;
import javax.enterprise.deploy.spi.DConfigBean;
import javax.enterprise.deploy.spi.exceptions.ConfigurationException;
import weblogic.deploy.api.internal.utils.Debug;
import weblogic.deploy.api.spi.config.BasicDConfigBean;
import weblogic.descriptor.DescriptorBean;

public class WLDFSMTPNotificationBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private WLDFSMTPNotificationBean beanTreeNode;

   public WLDFSMTPNotificationBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (WLDFSMTPNotificationBean)btn;
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

   public String getMailSessionJNDIName() {
      return this.beanTreeNode.getMailSessionJNDIName();
   }

   public void setMailSessionJNDIName(String value) {
      this.beanTreeNode.setMailSessionJNDIName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "MailSessionJNDIName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getSubject() {
      return this.beanTreeNode.getSubject();
   }

   public void setSubject(String value) {
      this.beanTreeNode.setSubject(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Subject", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getBody() {
      return this.beanTreeNode.getBody();
   }

   public void setBody(String value) {
      this.beanTreeNode.setBody(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Body", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String[] getRecipients() {
      return this.beanTreeNode.getRecipients();
   }

   public void setRecipients(String[] value) {
      this.beanTreeNode.setRecipients(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Recipients", (Object)null, (Object)null));
      this.setModified(true);
   }
}
