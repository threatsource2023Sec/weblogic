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

public class DeliveryFailureParamsBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private DeliveryFailureParamsBean beanTreeNode;

   public DeliveryFailureParamsBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (DeliveryFailureParamsBean)btn;
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

   public DestinationBean getErrorDestination() {
      return this.beanTreeNode.getErrorDestination();
   }

   public void setErrorDestination(DestinationBean value) {
      this.beanTreeNode.setErrorDestination(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ErrorDestination", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getRedeliveryLimit() {
      return this.beanTreeNode.getRedeliveryLimit();
   }

   public void setRedeliveryLimit(int value) {
      this.beanTreeNode.setRedeliveryLimit(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "RedeliveryLimit", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getExpirationPolicy() {
      return this.beanTreeNode.getExpirationPolicy();
   }

   public void setExpirationPolicy(String value) {
      this.beanTreeNode.setExpirationPolicy(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ExpirationPolicy", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getExpirationLoggingPolicy() {
      return this.beanTreeNode.getExpirationLoggingPolicy();
   }

   public void setExpirationLoggingPolicy(String value) {
      this.beanTreeNode.setExpirationLoggingPolicy(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ExpirationLoggingPolicy", (Object)null, (Object)null));
      this.setModified(true);
   }

   public TemplateBean getTemplateBean() {
      return this.beanTreeNode.getTemplateBean();
   }
}
