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

public class ApplicationSecurityRoleAssignmentBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private ApplicationSecurityRoleAssignmentBean beanTreeNode;

   public ApplicationSecurityRoleAssignmentBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (ApplicationSecurityRoleAssignmentBean)btn;
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

   public String[] getPrincipalNames() {
      return this.beanTreeNode.getPrincipalNames();
   }

   public void setPrincipalNames(String[] value) {
      this.beanTreeNode.setPrincipalNames(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "PrincipalNames", (Object)null, (Object)null));
      this.setModified(true);
   }

   public EmptyBean getExternallyDefined() {
      return this.beanTreeNode.getExternallyDefined();
   }
}
