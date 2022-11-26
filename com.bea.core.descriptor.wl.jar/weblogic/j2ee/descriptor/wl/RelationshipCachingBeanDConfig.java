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

public class RelationshipCachingBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private RelationshipCachingBean beanTreeNode;

   public RelationshipCachingBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (RelationshipCachingBean)btn;
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
      return this.getCachingName();
   }

   public void initKeyPropertyValue(String value) {
      this.setCachingName(value);
   }

   public String getDCBProperties() {
      StringBuffer sb = new StringBuffer();
      sb.append("CachingName: ");
      sb.append(this.beanTreeNode.getCachingName());
      sb.append("\n");
      return sb.toString();
   }

   public void validate() throws ConfigurationException {
   }

   public String getCachingName() {
      return this.beanTreeNode.getCachingName();
   }

   public void setCachingName(String value) {
      this.beanTreeNode.setCachingName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "CachingName", (Object)null, (Object)null));
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
