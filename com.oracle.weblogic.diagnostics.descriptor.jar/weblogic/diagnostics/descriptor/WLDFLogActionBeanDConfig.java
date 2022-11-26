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

public class WLDFLogActionBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private WLDFLogActionBean beanTreeNode;

   public WLDFLogActionBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (WLDFLogActionBean)btn;
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

   public String getSeverity() {
      return this.beanTreeNode.getSeverity();
   }

   public void setSeverity(String value) {
      this.beanTreeNode.setSeverity(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Severity", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getMessage() {
      return this.beanTreeNode.getMessage();
   }

   public void setMessage(String value) {
      this.beanTreeNode.setMessage(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Message", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getSubsystemName() {
      return this.beanTreeNode.getSubsystemName();
   }

   public void setSubsystemName(String value) {
      this.beanTreeNode.setSubsystemName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SubsystemName", (Object)null, (Object)null));
      this.setModified(true);
   }
}
