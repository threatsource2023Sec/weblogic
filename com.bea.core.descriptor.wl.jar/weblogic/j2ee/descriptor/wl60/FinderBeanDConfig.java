package weblogic.j2ee.descriptor.wl60;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.deploy.model.DDBean;
import javax.enterprise.deploy.spi.DConfigBean;
import javax.enterprise.deploy.spi.exceptions.ConfigurationException;
import weblogic.deploy.api.internal.utils.Debug;
import weblogic.deploy.api.spi.config.BasicDConfigBean;
import weblogic.descriptor.DescriptorBean;

public class FinderBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private FinderBean beanTreeNode;

   public FinderBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (FinderBean)btn;
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

   public String getFinderName() {
      return this.beanTreeNode.getFinderName();
   }

   public void setFinderName(String value) {
      this.beanTreeNode.setFinderName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "FinderName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String[] getFinderParams() {
      return this.beanTreeNode.getFinderParams();
   }

   public void setFinderParams(String[] value) {
      this.beanTreeNode.setFinderParams(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "FinderParams", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getFinderQuery() {
      return this.beanTreeNode.getFinderQuery();
   }

   public void setFinderQuery(String value) {
      this.beanTreeNode.setFinderQuery(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "FinderQuery", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getFinderSql() {
      return this.beanTreeNode.getFinderSql();
   }

   public void setFinderSql(String value) {
      this.beanTreeNode.setFinderSql(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "FinderSql", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isFindForUpdate() {
      return this.beanTreeNode.isFindForUpdate();
   }

   public void setFindForUpdate(boolean value) {
      this.beanTreeNode.setFindForUpdate(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "FindForUpdate", (Object)null, (Object)null));
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
