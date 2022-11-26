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

public class FieldGroupBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private FieldGroupBean beanTreeNode;

   public FieldGroupBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (FieldGroupBean)btn;
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
      return this.getGroupName();
   }

   public void initKeyPropertyValue(String value) {
      this.setGroupName(value);
   }

   public String getDCBProperties() {
      StringBuffer sb = new StringBuffer();
      sb.append("GroupName: ");
      sb.append(this.beanTreeNode.getGroupName());
      sb.append("\n");
      return sb.toString();
   }

   public void validate() throws ConfigurationException {
   }

   public String getGroupName() {
      return this.beanTreeNode.getGroupName();
   }

   public void setGroupName(String value) {
      this.beanTreeNode.setGroupName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "GroupName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String[] getCmpFields() {
      return this.beanTreeNode.getCmpFields();
   }

   public void setCmpFields(String[] value) {
      this.beanTreeNode.setCmpFields(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "CmpFields", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String[] getCmrFields() {
      return this.beanTreeNode.getCmrFields();
   }

   public void setCmrFields(String[] value) {
      this.beanTreeNode.setCmrFields(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "CmrFields", (Object)null, (Object)null));
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
