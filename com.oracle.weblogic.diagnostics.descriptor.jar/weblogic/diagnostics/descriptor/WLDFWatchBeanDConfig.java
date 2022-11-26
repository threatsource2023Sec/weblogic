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

public class WLDFWatchBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private WLDFWatchBean beanTreeNode;

   public WLDFWatchBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (WLDFWatchBean)btn;
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

   public String getRuleType() {
      return this.beanTreeNode.getRuleType();
   }

   public void setRuleType(String value) {
      this.beanTreeNode.setRuleType(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "RuleType", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getRuleExpression() {
      return this.beanTreeNode.getRuleExpression();
   }

   public void setRuleExpression(String value) {
      this.beanTreeNode.setRuleExpression(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "RuleExpression", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getExpressionLanguage() {
      return this.beanTreeNode.getExpressionLanguage();
   }

   public void setExpressionLanguage(String value) {
      this.beanTreeNode.setExpressionLanguage(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ExpressionLanguage", (Object)null, (Object)null));
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

   public String getAlarmType() {
      return this.beanTreeNode.getAlarmType();
   }

   public void setAlarmType(String value) {
      this.beanTreeNode.setAlarmType(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "AlarmType", (Object)null, (Object)null));
      this.setModified(true);
   }

   public WLDFScheduleBean getSchedule() {
      return this.beanTreeNode.getSchedule();
   }

   public int getAlarmResetPeriod() {
      return this.beanTreeNode.getAlarmResetPeriod();
   }

   public void setAlarmResetPeriod(int value) {
      this.beanTreeNode.setAlarmResetPeriod(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "AlarmResetPeriod", (Object)null, (Object)null));
      this.setModified(true);
   }

   public WLDFNotificationBean[] getNotifications() {
      return this.beanTreeNode.getNotifications();
   }

   public void setNotifications(WLDFNotificationBean[] value) {
      this.beanTreeNode.setNotifications(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Notifications", (Object)null, (Object)null));
      this.setModified(true);
   }
}
