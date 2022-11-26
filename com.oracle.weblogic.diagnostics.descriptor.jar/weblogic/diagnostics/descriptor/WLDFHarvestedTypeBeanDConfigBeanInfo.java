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

public class WLDFHarvestedTypeBeanDConfigBeanInfo extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private WLDFHarvestedTypeBean beanTreeNode;

   public WLDFHarvestedTypeBeanDConfigBeanInfo(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (WLDFHarvestedTypeBean)btn;
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

   public String getName() {
      return this.beanTreeNode.getName();
   }

   public void setName(String value) {
      this.beanTreeNode.setName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Name", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isEnabled() {
      return this.beanTreeNode.isEnabled();
   }

   public void setEnabled(boolean value) {
      this.beanTreeNode.setEnabled(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Enabled", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isKnownType() {
      return this.beanTreeNode.isKnownType();
   }

   public void setKnownType(boolean value) {
      this.beanTreeNode.setKnownType(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "KnownType", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String[] getHarvestedAttributes() {
      return this.beanTreeNode.getHarvestedAttributes();
   }

   public void setHarvestedAttributes(String[] value) {
      this.beanTreeNode.setHarvestedAttributes(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "HarvestedAttributes", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String[] getHarvestedInstances() {
      return this.beanTreeNode.getHarvestedInstances();
   }

   public void setHarvestedInstances(String[] value) {
      this.beanTreeNode.setHarvestedInstances(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "HarvestedInstances", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getNamespace() {
      return this.beanTreeNode.getNamespace();
   }

   public void setNamespace(String value) {
      this.beanTreeNode.setNamespace(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Namespace", (Object)null, (Object)null));
      this.setModified(true);
   }
}
