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

public class ResourceAdapterSecurityBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private ResourceAdapterSecurityBean beanTreeNode;

   public ResourceAdapterSecurityBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (ResourceAdapterSecurityBean)btn;
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

   public AnonPrincipalBean getDefaultPrincipalName() {
      return this.beanTreeNode.getDefaultPrincipalName();
   }

   public AnonPrincipalBean getManageAsPrincipalName() {
      return this.beanTreeNode.getManageAsPrincipalName();
   }

   public AnonPrincipalCallerBean getRunAsPrincipalName() {
      return this.beanTreeNode.getRunAsPrincipalName();
   }

   public AnonPrincipalCallerBean getRunWorkAsPrincipalName() {
      return this.beanTreeNode.getRunWorkAsPrincipalName();
   }

   public SecurityWorkContextBean getSecurityWorkContext() {
      return this.beanTreeNode.getSecurityWorkContext();
   }

   public boolean isSecurityWorkContextSet() {
      return this.beanTreeNode.isSecurityWorkContextSet();
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
