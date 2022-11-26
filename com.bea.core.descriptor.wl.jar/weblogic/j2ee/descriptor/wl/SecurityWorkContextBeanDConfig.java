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

public class SecurityWorkContextBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private SecurityWorkContextBean beanTreeNode;

   public SecurityWorkContextBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (SecurityWorkContextBean)btn;
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

   public boolean isInboundMappingRequired() {
      return this.beanTreeNode.isInboundMappingRequired();
   }

   public void setInboundMappingRequired(boolean value) {
      this.beanTreeNode.setInboundMappingRequired(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "InboundMappingRequired", (Object)null, (Object)null));
      this.setModified(true);
   }

   public AnonPrincipalBean getCallerPrincipalDefaultMapped() {
      return this.beanTreeNode.getCallerPrincipalDefaultMapped();
   }

   public boolean isCallerPrincipalDefaultMappedSet() {
      return this.beanTreeNode.isCallerPrincipalDefaultMappedSet();
   }

   public InboundCallerPrincipalMappingBean[] getCallerPrincipalMappings() {
      return this.beanTreeNode.getCallerPrincipalMappings();
   }

   public String getGroupPrincipalDefaultMapped() {
      return this.beanTreeNode.getGroupPrincipalDefaultMapped();
   }

   public void setGroupPrincipalDefaultMapped(String value) {
      this.beanTreeNode.setGroupPrincipalDefaultMapped(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "GroupPrincipalDefaultMapped", (Object)null, (Object)null));
      this.setModified(true);
   }

   public InboundGroupPrincipalMappingBean[] getGroupPrincipalMappings() {
      return this.beanTreeNode.getGroupPrincipalMappings();
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
