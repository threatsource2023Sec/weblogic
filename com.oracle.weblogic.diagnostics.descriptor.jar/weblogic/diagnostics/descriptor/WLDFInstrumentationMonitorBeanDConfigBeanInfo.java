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

public class WLDFInstrumentationMonitorBeanDConfigBeanInfo extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private WLDFInstrumentationMonitorBean beanTreeNode;

   public WLDFInstrumentationMonitorBeanDConfigBeanInfo(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (WLDFInstrumentationMonitorBean)btn;
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

   public String getDescription() {
      return this.beanTreeNode.getDescription();
   }

   public void setDescription(String value) {
      this.beanTreeNode.setDescription(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Description", (Object)null, (Object)null));
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

   public String getDyeMask() {
      return this.beanTreeNode.getDyeMask();
   }

   public void setDyeMask(String value) {
      this.beanTreeNode.setDyeMask(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DyeMask", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isDyeFilteringEnabled() {
      return this.beanTreeNode.isDyeFilteringEnabled();
   }

   public void setDyeFilteringEnabled(boolean value) {
      this.beanTreeNode.setDyeFilteringEnabled(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DyeFilteringEnabled", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getProperties() {
      return this.beanTreeNode.getProperties();
   }

   public void setProperties(String value) {
      this.beanTreeNode.setProperties(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Properties", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String[] getActions() {
      return this.beanTreeNode.getActions();
   }

   public void setActions(String[] value) {
      this.beanTreeNode.setActions(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Actions", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getLocationType() {
      return this.beanTreeNode.getLocationType();
   }

   public void setLocationType(String value) {
      this.beanTreeNode.setLocationType(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "LocationType", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getPointcut() {
      return this.beanTreeNode.getPointcut();
   }

   public void setPointcut(String value) {
      this.beanTreeNode.setPointcut(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Pointcut", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String[] getIncludes() {
      return this.beanTreeNode.getIncludes();
   }

   public void setIncludes(String[] value) {
      this.beanTreeNode.setIncludes(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Includes", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String[] getExcludes() {
      return this.beanTreeNode.getExcludes();
   }

   public void setExcludes(String[] value) {
      this.beanTreeNode.setExcludes(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Excludes", (Object)null, (Object)null));
      this.setModified(true);
   }
}
