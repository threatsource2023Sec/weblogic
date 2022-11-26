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

public class WLDFWatchNotificationBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private WLDFWatchNotificationBean beanTreeNode;

   public WLDFWatchNotificationBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (WLDFWatchNotificationBean)btn;
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

   public boolean isEnabled() {
      return this.beanTreeNode.isEnabled();
   }

   public void setEnabled(boolean value) {
      this.beanTreeNode.setEnabled(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Enabled", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getSeverity() {
      return this.beanTreeNode.getSeverity();
   }

   public void setSeverity(String value) {
      this.beanTreeNode.setSeverity(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Severity", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getLogWatchSeverity() {
      return this.beanTreeNode.getLogWatchSeverity();
   }

   public void setLogWatchSeverity(String value) {
      this.beanTreeNode.setLogWatchSeverity(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "LogWatchSeverity", (Object)null, (Object)null));
      this.setModified(true);
   }

   public WLDFWatchBean[] getWatches() {
      return this.beanTreeNode.getWatches();
   }

   public WLDFNotificationBean[] getNotifications() {
      return this.beanTreeNode.getNotifications();
   }

   public WLDFActionBean[] getActions() {
      return this.beanTreeNode.getActions();
   }

   public WLDFImageNotificationBean[] getImageNotifications() {
      return this.beanTreeNode.getImageNotifications();
   }

   public WLDFJMSNotificationBean[] getJMSNotifications() {
      return this.beanTreeNode.getJMSNotifications();
   }

   public WLDFLogActionBean[] getLogActions() {
      return this.beanTreeNode.getLogActions();
   }

   public WLDFJMXNotificationBean[] getJMXNotifications() {
      return this.beanTreeNode.getJMXNotifications();
   }

   public WLDFSMTPNotificationBean[] getSMTPNotifications() {
      return this.beanTreeNode.getSMTPNotifications();
   }

   public WLDFSNMPNotificationBean[] getSNMPNotifications() {
      return this.beanTreeNode.getSNMPNotifications();
   }

   public WLDFRESTNotificationBean[] getRESTNotifications() {
      return this.beanTreeNode.getRESTNotifications();
   }

   public WLDFScaleUpActionBean[] getScaleUpActions() {
      return this.beanTreeNode.getScaleUpActions();
   }

   public WLDFScaleDownActionBean[] getScaleDownActions() {
      return this.beanTreeNode.getScaleDownActions();
   }

   public WLDFScriptActionBean[] getScriptActions() {
      return this.beanTreeNode.getScriptActions();
   }

   public WLDFHeapDumpActionBean[] getHeapDumpActions() {
      return this.beanTreeNode.getHeapDumpActions();
   }

   public WLDFThreadDumpActionBean[] getThreadDumpActions() {
      return this.beanTreeNode.getThreadDumpActions();
   }
}
