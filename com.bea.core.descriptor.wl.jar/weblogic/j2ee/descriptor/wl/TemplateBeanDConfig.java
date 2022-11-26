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

public class TemplateBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private TemplateBean beanTreeNode;

   public TemplateBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (TemplateBean)btn;
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

   public String[] getDestinationKeys() {
      return this.beanTreeNode.getDestinationKeys();
   }

   public void setDestinationKeys(String[] value) {
      this.beanTreeNode.setDestinationKeys(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DestinationKeys", (Object)null, (Object)null));
      this.setModified(true);
   }

   public ThresholdParamsBean getThresholds() {
      return this.beanTreeNode.getThresholds();
   }

   public DeliveryParamsOverridesBean getDeliveryParamsOverrides() {
      return this.beanTreeNode.getDeliveryParamsOverrides();
   }

   public DeliveryFailureParamsBean getDeliveryFailureParams() {
      return this.beanTreeNode.getDeliveryFailureParams();
   }

   public MessageLoggingParamsBean getMessageLoggingParams() {
      return this.beanTreeNode.getMessageLoggingParams();
   }

   public String getAttachSender() {
      return this.beanTreeNode.getAttachSender();
   }

   public void setAttachSender(String value) {
      this.beanTreeNode.setAttachSender(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "AttachSender", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isProductionPausedAtStartup() {
      return this.beanTreeNode.isProductionPausedAtStartup();
   }

   public void setProductionPausedAtStartup(boolean value) {
      this.beanTreeNode.setProductionPausedAtStartup(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ProductionPausedAtStartup", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isInsertionPausedAtStartup() {
      return this.beanTreeNode.isInsertionPausedAtStartup();
   }

   public void setInsertionPausedAtStartup(boolean value) {
      this.beanTreeNode.setInsertionPausedAtStartup(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "InsertionPausedAtStartup", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isConsumptionPausedAtStartup() {
      return this.beanTreeNode.isConsumptionPausedAtStartup();
   }

   public void setConsumptionPausedAtStartup(boolean value) {
      this.beanTreeNode.setConsumptionPausedAtStartup(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ConsumptionPausedAtStartup", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getMaximumMessageSize() {
      return this.beanTreeNode.getMaximumMessageSize();
   }

   public void setMaximumMessageSize(int value) {
      this.beanTreeNode.setMaximumMessageSize(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "MaximumMessageSize", (Object)null, (Object)null));
      this.setModified(true);
   }

   public QuotaBean getQuota() {
      return this.beanTreeNode.getQuota();
   }

   public void setQuota(QuotaBean value) {
      this.beanTreeNode.setQuota(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Quota", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isDefaultUnitOfOrder() {
      return this.beanTreeNode.isDefaultUnitOfOrder();
   }

   public void setDefaultUnitOfOrder(boolean value) {
      this.beanTreeNode.setDefaultUnitOfOrder(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DefaultUnitOfOrder", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getSafExportPolicy() {
      return this.beanTreeNode.getSafExportPolicy();
   }

   public void setSafExportPolicy(String value) {
      this.beanTreeNode.setSafExportPolicy(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SafExportPolicy", (Object)null, (Object)null));
      this.setModified(true);
   }

   public MulticastParamsBean getMulticast() {
      return this.beanTreeNode.getMulticast();
   }

   public TopicSubscriptionParamsBean getTopicSubscriptionParams() {
      return this.beanTreeNode.getTopicSubscriptionParams();
   }

   public GroupParamsBean[] getGroupParams() {
      return this.beanTreeNode.getGroupParams();
   }

   public int getMessagingPerformancePreference() {
      return this.beanTreeNode.getMessagingPerformancePreference();
   }

   public void setMessagingPerformancePreference(int value) {
      this.beanTreeNode.setMessagingPerformancePreference(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "MessagingPerformancePreference", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getUnitOfWorkHandlingPolicy() {
      return this.beanTreeNode.getUnitOfWorkHandlingPolicy();
   }

   public void setUnitOfWorkHandlingPolicy(String value) {
      this.beanTreeNode.setUnitOfWorkHandlingPolicy(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "UnitOfWorkHandlingPolicy", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getIncompleteWorkExpirationTime() {
      return this.beanTreeNode.getIncompleteWorkExpirationTime();
   }

   public void setIncompleteWorkExpirationTime(int value) {
      this.beanTreeNode.setIncompleteWorkExpirationTime(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "IncompleteWorkExpirationTime", (Object)null, (Object)null));
      this.setModified(true);
   }
}
