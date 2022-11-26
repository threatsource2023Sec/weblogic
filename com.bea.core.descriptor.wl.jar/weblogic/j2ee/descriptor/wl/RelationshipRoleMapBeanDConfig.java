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

public class RelationshipRoleMapBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private RelationshipRoleMapBean beanTreeNode;

   public RelationshipRoleMapBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (RelationshipRoleMapBean)btn;
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

   public String getForeignKeyTable() {
      return this.beanTreeNode.getForeignKeyTable();
   }

   public void setForeignKeyTable(String value) {
      this.beanTreeNode.setForeignKeyTable(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ForeignKeyTable", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getPrimaryKeyTable() {
      return this.beanTreeNode.getPrimaryKeyTable();
   }

   public void setPrimaryKeyTable(String value) {
      this.beanTreeNode.setPrimaryKeyTable(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "PrimaryKeyTable", (Object)null, (Object)null));
      this.setModified(true);
   }

   public ColumnMapBean[] getColumnMaps() {
      return this.beanTreeNode.getColumnMaps();
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
