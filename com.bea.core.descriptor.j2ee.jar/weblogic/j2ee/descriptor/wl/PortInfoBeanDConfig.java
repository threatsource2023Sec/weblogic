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

public class PortInfoBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private PortInfoBean beanTreeNode;

   public PortInfoBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (PortInfoBean)btn;
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
      return this.getPortName();
   }

   public void initKeyPropertyValue(String value) {
      this.setPortName(value);
   }

   public String getDCBProperties() {
      StringBuffer sb = new StringBuffer();
      sb.append("PortName: ");
      sb.append(this.beanTreeNode.getPortName());
      sb.append("\n");
      return sb.toString();
   }

   public void validate() throws ConfigurationException {
   }

   public String getPortName() {
      return this.beanTreeNode.getPortName();
   }

   public void setPortName(String value) {
      this.beanTreeNode.setPortName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "PortName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public PropertyNamevalueBean[] getStubProperties() {
      return this.beanTreeNode.getStubProperties();
   }

   public PropertyNamevalueBean[] getCallProperties() {
      return this.beanTreeNode.getCallProperties();
   }

   public WSATConfigBean getWSATConfig() {
      return this.beanTreeNode.getWSATConfig();
   }

   public OperationInfoBean[] getOperations() {
      return this.beanTreeNode.getOperations();
   }

   public OwsmPolicyBean[] getOwsmPolicy() {
      return this.beanTreeNode.getOwsmPolicy();
   }
}
