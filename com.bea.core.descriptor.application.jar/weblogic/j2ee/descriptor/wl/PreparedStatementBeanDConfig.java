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

public class PreparedStatementBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private PreparedStatementBean beanTreeNode;

   public PreparedStatementBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (PreparedStatementBean)btn;
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

   public boolean isProfilingEnabled() {
      return this.beanTreeNode.isProfilingEnabled();
   }

   public void setProfilingEnabled(boolean value) {
      this.beanTreeNode.setProfilingEnabled(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ProfilingEnabled", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getCacheProfilingThreshold() {
      return this.beanTreeNode.getCacheProfilingThreshold();
   }

   public void setCacheProfilingThreshold(int value) {
      this.beanTreeNode.setCacheProfilingThreshold(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "CacheProfilingThreshold", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getCacheSize() {
      return this.beanTreeNode.getCacheSize();
   }

   public void setCacheSize(int value) {
      this.beanTreeNode.setCacheSize(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "CacheSize", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isParameterLoggingEnabled() {
      return this.beanTreeNode.isParameterLoggingEnabled();
   }

   public void setParameterLoggingEnabled(boolean value) {
      this.beanTreeNode.setParameterLoggingEnabled(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ParameterLoggingEnabled", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getMaxParameterLength() {
      return this.beanTreeNode.getMaxParameterLength();
   }

   public void setMaxParameterLength(int value) {
      this.beanTreeNode.setMaxParameterLength(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "MaxParameterLength", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getCacheType() {
      return this.beanTreeNode.getCacheType();
   }

   public void setCacheType(int value) {
      this.beanTreeNode.setCacheType(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "CacheType", (Object)null, (Object)null));
      this.setModified(true);
   }
}
