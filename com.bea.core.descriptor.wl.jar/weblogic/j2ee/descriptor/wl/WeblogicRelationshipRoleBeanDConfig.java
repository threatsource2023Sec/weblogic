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
import weblogic.j2ee.descriptor.EmptyBean;

public class WeblogicRelationshipRoleBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private WeblogicRelationshipRoleBean beanTreeNode;

   public WeblogicRelationshipRoleBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (WeblogicRelationshipRoleBean)btn;
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
      return this.getRelationshipRoleName();
   }

   public void initKeyPropertyValue(String value) {
      this.setRelationshipRoleName(value);
   }

   public String getDCBProperties() {
      StringBuffer sb = new StringBuffer();
      sb.append("RelationshipRoleName: ");
      sb.append(this.beanTreeNode.getRelationshipRoleName());
      sb.append("\n");
      return sb.toString();
   }

   public void validate() throws ConfigurationException {
   }

   public String getRelationshipRoleName() {
      return this.beanTreeNode.getRelationshipRoleName();
   }

   public void setRelationshipRoleName(String value) {
      this.beanTreeNode.setRelationshipRoleName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "RelationshipRoleName", (Object)null, (Object)null));
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

   public RelationshipRoleMapBean getRelationshipRoleMap() {
      return this.beanTreeNode.getRelationshipRoleMap();
   }

   public EmptyBean getDbCascadeDelete() {
      return this.beanTreeNode.getDbCascadeDelete();
   }

   public boolean getEnableQueryCaching() {
      return this.beanTreeNode.getEnableQueryCaching();
   }

   public void setEnableQueryCaching(boolean value) {
      this.beanTreeNode.setEnableQueryCaching(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "EnableQueryCaching", (Object)null, (Object)null));
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
