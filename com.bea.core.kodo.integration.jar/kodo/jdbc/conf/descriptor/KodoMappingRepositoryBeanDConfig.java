package kodo.jdbc.conf.descriptor;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.deploy.model.DDBean;
import javax.enterprise.deploy.spi.DConfigBean;
import javax.enterprise.deploy.spi.exceptions.ConfigurationException;
import weblogic.deploy.api.internal.utils.Debug;
import weblogic.deploy.api.spi.config.BasicDConfigBean;
import weblogic.descriptor.DescriptorBean;

public class KodoMappingRepositoryBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private KodoMappingRepositoryBean beanTreeNode;

   public KodoMappingRepositoryBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (KodoMappingRepositoryBean)btn;
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

   public int getResolve() {
      return this.beanTreeNode.getResolve();
   }

   public void setResolve(int value) {
      this.beanTreeNode.setResolve(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Resolve", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getValidate() {
      return this.beanTreeNode.getValidate();
   }

   public void setValidate(int value) {
      this.beanTreeNode.setValidate(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Validate", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getSourceMode() {
      return this.beanTreeNode.getSourceMode();
   }

   public void setSourceMode(int value) {
      this.beanTreeNode.setSourceMode(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SourceMode", (Object)null, (Object)null));
      this.setModified(true);
   }
}
