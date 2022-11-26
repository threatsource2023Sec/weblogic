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

public class ThresholdParamsBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private ThresholdParamsBean beanTreeNode;

   public ThresholdParamsBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (ThresholdParamsBean)btn;
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

   public long getBytesHigh() {
      return this.beanTreeNode.getBytesHigh();
   }

   public void setBytesHigh(long value) {
      this.beanTreeNode.setBytesHigh(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "BytesHigh", (Object)null, (Object)null));
      this.setModified(true);
   }

   public long getBytesLow() {
      return this.beanTreeNode.getBytesLow();
   }

   public void setBytesLow(long value) {
      this.beanTreeNode.setBytesLow(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "BytesLow", (Object)null, (Object)null));
      this.setModified(true);
   }

   public long getMessagesHigh() {
      return this.beanTreeNode.getMessagesHigh();
   }

   public void setMessagesHigh(long value) {
      this.beanTreeNode.setMessagesHigh(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "MessagesHigh", (Object)null, (Object)null));
      this.setModified(true);
   }

   public long getMessagesLow() {
      return this.beanTreeNode.getMessagesLow();
   }

   public void setMessagesLow(long value) {
      this.beanTreeNode.setMessagesLow(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "MessagesLow", (Object)null, (Object)null));
      this.setModified(true);
   }

   public TemplateBean getTemplateBean() {
      return this.beanTreeNode.getTemplateBean();
   }
}
