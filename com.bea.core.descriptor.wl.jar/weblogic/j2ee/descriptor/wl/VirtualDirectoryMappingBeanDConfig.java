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
import weblogic.utils.StringUtils;

public class VirtualDirectoryMappingBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private VirtualDirectoryMappingBean beanTreeNode;

   public VirtualDirectoryMappingBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (VirtualDirectoryMappingBean)btn;
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
      return this._getKeyValue(this.getUrlPatterns());
   }

   public void initKeyPropertyValue(String value) {
      this.setUrlPatterns(StringUtils.splitCompletely(value, ",", false));
   }

   public String getDCBProperties() {
      StringBuffer sb = new StringBuffer();
      return sb.toString();
   }

   public void validate() throws ConfigurationException {
   }

   public String getLocalPath() {
      return this.beanTreeNode.getLocalPath();
   }

   public void setLocalPath(String value) {
      this.beanTreeNode.setLocalPath(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "LocalPath", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String[] getUrlPatterns() {
      return this.beanTreeNode.getUrlPatterns();
   }

   public void setUrlPatterns(String[] value) {
      this.beanTreeNode.setUrlPatterns(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "UrlPatterns", (Object)null, (Object)null));
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
