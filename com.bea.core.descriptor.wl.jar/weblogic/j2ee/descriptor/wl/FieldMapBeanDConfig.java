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

public class FieldMapBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private FieldMapBean beanTreeNode;

   public FieldMapBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (FieldMapBean)btn;
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
      return this.getCmpField();
   }

   public void initKeyPropertyValue(String value) {
      this.setCmpField(value);
   }

   public String getDCBProperties() {
      StringBuffer sb = new StringBuffer();
      sb.append("CmpField: ");
      sb.append(this.beanTreeNode.getCmpField());
      sb.append("\n");
      return sb.toString();
   }

   public void validate() throws ConfigurationException {
   }

   public String getCmpField() {
      return this.beanTreeNode.getCmpField();
   }

   public void setCmpField(String value) {
      this.beanTreeNode.setCmpField(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "CmpField", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getDbmsColumn() {
      return this.beanTreeNode.getDbmsColumn();
   }

   public void setDbmsColumn(String value) {
      this.beanTreeNode.setDbmsColumn(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DbmsColumn", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getDbmsColumnType() {
      return this.beanTreeNode.getDbmsColumnType();
   }

   public void setDbmsColumnType(String value) {
      this.beanTreeNode.setDbmsColumnType(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DbmsColumnType", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isDbmsDefaultValue() {
      return this.beanTreeNode.isDbmsDefaultValue();
   }

   public void setDbmsDefaultValue(boolean value) {
      this.beanTreeNode.setDbmsDefaultValue(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DbmsDefaultValue", (Object)null, (Object)null));
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

   public String getId() {
      return this.beanTreeNode.getId();
   }

   public void setId(String value) {
      this.beanTreeNode.setId(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Id", (Object)null, (Object)null));
      this.setModified(true);
   }
}
