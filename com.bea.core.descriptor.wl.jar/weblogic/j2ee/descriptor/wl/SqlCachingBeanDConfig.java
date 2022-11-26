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

public class SqlCachingBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private SqlCachingBean beanTreeNode;

   public SqlCachingBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (SqlCachingBean)btn;
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
      return this.getSqlCachingName();
   }

   public void initKeyPropertyValue(String value) {
      this.setSqlCachingName(value);
   }

   public String getDCBProperties() {
      StringBuffer sb = new StringBuffer();
      sb.append("SqlCachingName: ");
      sb.append(this.beanTreeNode.getSqlCachingName());
      sb.append("\n");
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

   public String getSqlCachingName() {
      return this.beanTreeNode.getSqlCachingName();
   }

   public void setSqlCachingName(String value) {
      this.beanTreeNode.setSqlCachingName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SqlCachingName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public EmptyBean[] getResultColumns() {
      return this.beanTreeNode.getResultColumns();
   }

   public TableBean[] getTables() {
      return this.beanTreeNode.getTables();
   }
}
