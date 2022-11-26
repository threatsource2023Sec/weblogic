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

public class WLDFInstrumentationBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private WLDFInstrumentationBean beanTreeNode;

   public WLDFInstrumentationBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (WLDFInstrumentationBean)btn;
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

   public WLDFInstrumentationMonitorBean[] getWLDFInstrumentationMonitors() {
      return this.beanTreeNode.getWLDFInstrumentationMonitors();
   }
}
