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

public class WeblogicQueryBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private WeblogicQueryBean beanTreeNode;

   public WeblogicQueryBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (WeblogicQueryBean)btn;
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

   public QueryMethodBean getQueryMethod() {
      return this.beanTreeNode.getQueryMethod();
   }

   public EjbQlQueryBean getEjbQlQuery() {
      return this.beanTreeNode.getEjbQlQuery();
   }

   public SqlQueryBean getSqlQuery() {
      return this.beanTreeNode.getSqlQuery();
   }

   public int getMaxElements() {
      return this.beanTreeNode.getMaxElements();
   }

   public void setMaxElements(int value) {
      this.beanTreeNode.setMaxElements(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "MaxElements", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isIncludeUpdates() {
      return this.beanTreeNode.isIncludeUpdates();
   }

   public void setIncludeUpdates(boolean value) {
      this.beanTreeNode.setIncludeUpdates(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "IncludeUpdates", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isIncludeUpdatesSet() {
      return this.beanTreeNode.isIncludeUpdatesSet();
   }

   public boolean isSqlSelectDistinct() {
      return this.beanTreeNode.isSqlSelectDistinct();
   }

   public void setSqlSelectDistinct(boolean value) {
      this.beanTreeNode.setSqlSelectDistinct(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SqlSelectDistinct", (Object)null, (Object)null));
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

   public boolean getEnableQueryCaching() {
      return this.beanTreeNode.getEnableQueryCaching();
   }

   public void setEnableQueryCaching(boolean value) {
      this.beanTreeNode.setEnableQueryCaching(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "EnableQueryCaching", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getEnableEagerRefresh() {
      return this.beanTreeNode.getEnableEagerRefresh();
   }

   public void setEnableEagerRefresh(boolean value) {
      this.beanTreeNode.setEnableEagerRefresh(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "EnableEagerRefresh", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isIncludeResultCacheHint() {
      return this.beanTreeNode.isIncludeResultCacheHint();
   }

   public void setIncludeResultCacheHint(boolean value) {
      this.beanTreeNode.setIncludeResultCacheHint(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "IncludeResultCacheHint", (Object)null, (Object)null));
      this.setModified(true);
   }
}
