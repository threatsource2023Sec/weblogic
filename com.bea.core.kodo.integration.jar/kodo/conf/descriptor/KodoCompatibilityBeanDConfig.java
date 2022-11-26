package kodo.conf.descriptor;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.deploy.model.DDBean;
import javax.enterprise.deploy.spi.DConfigBean;
import javax.enterprise.deploy.spi.exceptions.ConfigurationException;
import weblogic.deploy.api.internal.utils.Debug;
import weblogic.deploy.api.spi.config.BasicDConfigBean;
import weblogic.descriptor.DescriptorBean;

public class KodoCompatibilityBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private KodoCompatibilityBean beanTreeNode;

   public KodoCompatibilityBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (KodoCompatibilityBean)btn;
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

   public boolean getCopyObjectIds() {
      return this.beanTreeNode.getCopyObjectIds();
   }

   public void setCopyObjectIds(boolean value) {
      this.beanTreeNode.setCopyObjectIds(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "CopyObjectIds", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getCloseOnManagedCommit() {
      return this.beanTreeNode.getCloseOnManagedCommit();
   }

   public void setCloseOnManagedCommit(boolean value) {
      this.beanTreeNode.setCloseOnManagedCommit(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "CloseOnManagedCommit", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getValidateTrueChecksStore() {
      return this.beanTreeNode.getValidateTrueChecksStore();
   }

   public void setValidateTrueChecksStore(boolean value) {
      this.beanTreeNode.setValidateTrueChecksStore(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ValidateTrueChecksStore", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getValidateFalseReturnsHollow() {
      return this.beanTreeNode.getValidateFalseReturnsHollow();
   }

   public void setValidateFalseReturnsHollow(boolean value) {
      this.beanTreeNode.setValidateFalseReturnsHollow(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ValidateFalseReturnsHollow", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getStrictIdentityValues() {
      return this.beanTreeNode.getStrictIdentityValues();
   }

   public void setStrictIdentityValues(boolean value) {
      this.beanTreeNode.setStrictIdentityValues(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "StrictIdentityValues", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getQuotedNumbersInQueries() {
      return this.beanTreeNode.getQuotedNumbersInQueries();
   }

   public void setQuotedNumbersInQueries(boolean value) {
      this.beanTreeNode.setQuotedNumbersInQueries(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "QuotedNumbersInQueries", (Object)null, (Object)null));
      this.setModified(true);
   }
}
