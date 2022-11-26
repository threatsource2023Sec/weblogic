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

public class ContextCaseBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private ContextCaseBean beanTreeNode;

   public ContextCaseBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (ContextCaseBean)btn;
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
      return this.getRequestClassName();
   }

   public void initKeyPropertyValue(String value) {
      this.setRequestClassName(value);
   }

   public String getDCBProperties() {
      StringBuffer sb = new StringBuffer();
      sb.append("RequestClassName: ");
      sb.append(this.beanTreeNode.getRequestClassName());
      sb.append("\n");
      return sb.toString();
   }

   public void validate() throws ConfigurationException {
   }

   public String getUserName() {
      return this.beanTreeNode.getUserName();
   }

   public void setUserName(String value) {
      this.beanTreeNode.setUserName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "UserName", (Object)null, (Object)null));
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

   public String getRequestClassName() {
      return this.beanTreeNode.getRequestClassName();
   }

   public void setRequestClassName(String value) {
      this.beanTreeNode.setRequestClassName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "RequestClassName", (Object)null, (Object)null));
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

   public ResponseTimeRequestClassBean getResponseTimeRequestClass() {
      return this.beanTreeNode.getResponseTimeRequestClass();
   }

   public FairShareRequestClassBean getFairShareRequestClass() {
      return this.beanTreeNode.getFairShareRequestClass();
   }
}
