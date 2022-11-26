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

public class RunAsRoleAssignmentBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private RunAsRoleAssignmentBean beanTreeNode;

   public RunAsRoleAssignmentBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (RunAsRoleAssignmentBean)btn;
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
      return this.getRoleName();
   }

   public void initKeyPropertyValue(String value) {
      this.setRoleName(value);
   }

   public String getDCBProperties() {
      StringBuffer sb = new StringBuffer();
      sb.append("RoleName: ");
      sb.append(this.beanTreeNode.getRoleName());
      sb.append("\n");
      return sb.toString();
   }

   public void validate() throws ConfigurationException {
   }

   public String getRoleName() {
      return this.beanTreeNode.getRoleName();
   }

   public void setRoleName(String value) {
      this.beanTreeNode.setRoleName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "RoleName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getRunAsPrincipalName() {
      return this.beanTreeNode.getRunAsPrincipalName();
   }

   public void setRunAsPrincipalName(String value) {
      this.beanTreeNode.setRunAsPrincipalName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "RunAsPrincipalName", (Object)null, (Object)null));
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
