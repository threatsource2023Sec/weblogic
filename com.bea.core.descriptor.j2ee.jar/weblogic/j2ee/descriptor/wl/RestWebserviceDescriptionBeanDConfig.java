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

public class RestWebserviceDescriptionBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private RestWebserviceDescriptionBean beanTreeNode;

   public RestWebserviceDescriptionBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (RestWebserviceDescriptionBean)btn;
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

   public String getApplicationClassName() {
      return this.beanTreeNode.getApplicationClassName();
   }

   public void setApplicationClassName(String value) {
      this.beanTreeNode.setApplicationClassName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ApplicationClassName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getServletName() {
      return this.beanTreeNode.getServletName();
   }

   public void setServletName(String value) {
      this.beanTreeNode.setServletName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ServletName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getFilterName() {
      return this.beanTreeNode.getFilterName();
   }

   public void setFilterName(String value) {
      this.beanTreeNode.setFilterName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "FilterName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String[] getApplicationBaseUris() {
      return this.beanTreeNode.getApplicationBaseUris();
   }

   public void setApplicationBaseUris(String[] value) {
      this.beanTreeNode.setApplicationBaseUris(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ApplicationBaseUris", (Object)null, (Object)null));
      this.setModified(true);
   }
}