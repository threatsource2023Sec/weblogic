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

public class InboundGroupPrincipalMappingBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private InboundGroupPrincipalMappingBean beanTreeNode;

   public InboundGroupPrincipalMappingBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (InboundGroupPrincipalMappingBean)btn;
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
      return this.getEisGroupPrincipal();
   }

   public void initKeyPropertyValue(String value) {
      this.setEisGroupPrincipal(value);
   }

   public String getDCBProperties() {
      StringBuffer sb = new StringBuffer();
      sb.append("EisGroupPrincipal: ");
      sb.append(this.beanTreeNode.getEisGroupPrincipal());
      sb.append("\n");
      return sb.toString();
   }

   public void validate() throws ConfigurationException {
   }

   public String getEisGroupPrincipal() {
      return this.beanTreeNode.getEisGroupPrincipal();
   }

   public void setEisGroupPrincipal(String value) {
      this.beanTreeNode.setEisGroupPrincipal(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "EisGroupPrincipal", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getMappedGroupPrincipal() {
      return this.beanTreeNode.getMappedGroupPrincipal();
   }

   public void setMappedGroupPrincipal(String value) {
      this.beanTreeNode.setMappedGroupPrincipal(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "MappedGroupPrincipal", (Object)null, (Object)null));
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
