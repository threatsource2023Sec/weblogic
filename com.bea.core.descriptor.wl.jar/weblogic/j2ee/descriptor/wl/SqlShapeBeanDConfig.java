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

public class SqlShapeBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private SqlShapeBean beanTreeNode;

   public SqlShapeBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (SqlShapeBean)btn;
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

   public String getDescription() {
      return this.beanTreeNode.getDescription();
   }

   public void setDescription(String value) {
      this.beanTreeNode.setDescription(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Description", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getSqlShapeName() {
      return this.beanTreeNode.getSqlShapeName();
   }

   public void setSqlShapeName(String value) {
      this.beanTreeNode.setSqlShapeName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SqlShapeName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public TableBean[] getTables() {
      return this.beanTreeNode.getTables();
   }

   public int getPassThroughColumns() {
      return this.beanTreeNode.getPassThroughColumns();
   }

   public void setPassThroughColumns(int value) {
      this.beanTreeNode.setPassThroughColumns(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "PassThroughColumns", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String[] getEjbRelationNames() {
      return this.beanTreeNode.getEjbRelationNames();
   }

   public void setEjbRelationNames(String[] value) {
      this.beanTreeNode.setEjbRelationNames(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "EjbRelationNames", (Object)null, (Object)null));
      this.setModified(true);
   }
}
