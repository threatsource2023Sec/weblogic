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

public class TangosolQueryCacheBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private TangosolQueryCacheBean beanTreeNode;

   public TangosolQueryCacheBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (TangosolQueryCacheBean)btn;
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

   public boolean getClearOnClose() {
      return this.beanTreeNode.getClearOnClose();
   }

   public void setClearOnClose(boolean value) {
      this.beanTreeNode.setClearOnClose(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ClearOnClose", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getTangosolCacheType() {
      return this.beanTreeNode.getTangosolCacheType();
   }

   public void setTangosolCacheType(String value) {
      this.beanTreeNode.setTangosolCacheType(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "TangosolCacheType", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getTangosolCacheName() {
      return this.beanTreeNode.getTangosolCacheName();
   }

   public void setTangosolCacheName(String value) {
      this.beanTreeNode.setTangosolCacheName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "TangosolCacheName", (Object)null, (Object)null));
      this.setModified(true);
   }
}
