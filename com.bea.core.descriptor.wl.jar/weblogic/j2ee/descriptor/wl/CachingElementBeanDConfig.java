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

public class CachingElementBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private CachingElementBean beanTreeNode;

   public CachingElementBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (CachingElementBean)btn;
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

   public String getCmrField() {
      return this.beanTreeNode.getCmrField();
   }

   public void setCmrField(String value) {
      this.beanTreeNode.setCmrField(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "CmrField", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getGroupName() {
      return this.beanTreeNode.getGroupName();
   }

   public void setGroupName(String value) {
      this.beanTreeNode.setGroupName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "GroupName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public CachingElementBean[] getCachingElements() {
      return this.beanTreeNode.getCachingElements();
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
