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

public class InboundCallerPrincipalMappingBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private InboundCallerPrincipalMappingBean beanTreeNode;

   public InboundCallerPrincipalMappingBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (InboundCallerPrincipalMappingBean)btn;
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
      return this.getEisCallerPrincipal();
   }

   public void initKeyPropertyValue(String value) {
      this.setEisCallerPrincipal(value);
   }

   public String getDCBProperties() {
      StringBuffer sb = new StringBuffer();
      sb.append("EisCallerPrincipal: ");
      sb.append(this.beanTreeNode.getEisCallerPrincipal());
      sb.append("\n");
      return sb.toString();
   }

   public void validate() throws ConfigurationException {
   }

   public String getEisCallerPrincipal() {
      return this.beanTreeNode.getEisCallerPrincipal();
   }

   public void setEisCallerPrincipal(String value) {
      this.beanTreeNode.setEisCallerPrincipal(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "EisCallerPrincipal", (Object)null, (Object)null));
      this.setModified(true);
   }

   public AnonPrincipalBean getMappedCallerPrincipal() {
      return this.beanTreeNode.getMappedCallerPrincipal();
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
