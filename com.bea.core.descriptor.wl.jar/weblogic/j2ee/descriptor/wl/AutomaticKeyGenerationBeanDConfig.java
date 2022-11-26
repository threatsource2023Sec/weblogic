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

public class AutomaticKeyGenerationBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private AutomaticKeyGenerationBean beanTreeNode;

   public AutomaticKeyGenerationBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (AutomaticKeyGenerationBean)btn;
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

   public String getGeneratorType() {
      return this.beanTreeNode.getGeneratorType();
   }

   public void setGeneratorType(String value) {
      this.beanTreeNode.setGeneratorType(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "GeneratorType", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getGeneratorName() {
      return this.beanTreeNode.getGeneratorName();
   }

   public void setGeneratorName(String value) {
      this.beanTreeNode.setGeneratorName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "GeneratorName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getKeyCacheSize() {
      return this.beanTreeNode.getKeyCacheSize();
   }

   public void setKeyCacheSize(int value) {
      this.beanTreeNode.setKeyCacheSize(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "KeyCacheSize", (Object)null, (Object)null));
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

   public boolean getSelectFirstSequenceKeyBeforeUpdate() {
      return this.beanTreeNode.getSelectFirstSequenceKeyBeforeUpdate();
   }

   public void setSelectFirstSequenceKeyBeforeUpdate(boolean value) {
      this.beanTreeNode.setSelectFirstSequenceKeyBeforeUpdate(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SelectFirstSequenceKeyBeforeUpdate", (Object)null, (Object)null));
      this.setModified(true);
   }
}
