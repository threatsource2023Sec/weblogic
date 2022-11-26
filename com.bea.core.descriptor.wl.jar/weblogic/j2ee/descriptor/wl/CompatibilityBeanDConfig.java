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

public class CompatibilityBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private CompatibilityBean beanTreeNode;

   public CompatibilityBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (CompatibilityBean)btn;
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

   public boolean isSerializeByteArrayToOracleBlob() {
      return this.beanTreeNode.isSerializeByteArrayToOracleBlob();
   }

   public void setSerializeByteArrayToOracleBlob(boolean value) {
      this.beanTreeNode.setSerializeByteArrayToOracleBlob(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SerializeByteArrayToOracleBlob", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isSerializeCharArrayToBytes() {
      return this.beanTreeNode.isSerializeCharArrayToBytes();
   }

   public void setSerializeCharArrayToBytes(boolean value) {
      this.beanTreeNode.setSerializeCharArrayToBytes(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SerializeCharArrayToBytes", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isAllowReadonlyCreateAndRemove() {
      return this.beanTreeNode.isAllowReadonlyCreateAndRemove();
   }

   public void setAllowReadonlyCreateAndRemove(boolean value) {
      this.beanTreeNode.setAllowReadonlyCreateAndRemove(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "AllowReadonlyCreateAndRemove", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isDisableStringTrimming() {
      return this.beanTreeNode.isDisableStringTrimming();
   }

   public void setDisableStringTrimming(boolean value) {
      this.beanTreeNode.setDisableStringTrimming(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DisableStringTrimming", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isFindersReturnNulls() {
      return this.beanTreeNode.isFindersReturnNulls();
   }

   public void setFindersReturnNulls(boolean value) {
      this.beanTreeNode.setFindersReturnNulls(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "FindersReturnNulls", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isLoadRelatedBeansFromDbInPostCreate() {
      return this.beanTreeNode.isLoadRelatedBeansFromDbInPostCreate();
   }

   public void setLoadRelatedBeansFromDbInPostCreate(boolean value) {
      this.beanTreeNode.setLoadRelatedBeansFromDbInPostCreate(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "LoadRelatedBeansFromDbInPostCreate", (Object)null, (Object)null));
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
