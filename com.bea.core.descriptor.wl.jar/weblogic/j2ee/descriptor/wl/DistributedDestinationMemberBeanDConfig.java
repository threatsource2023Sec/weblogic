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

public class DistributedDestinationMemberBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private DistributedDestinationMemberBean beanTreeNode;

   public DistributedDestinationMemberBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (DistributedDestinationMemberBean)btn;
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

   public int getWeight() {
      return this.beanTreeNode.getWeight();
   }

   public void setWeight(int value) {
      this.beanTreeNode.setWeight(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Weight", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getPhysicalDestinationName() {
      return this.beanTreeNode.getPhysicalDestinationName();
   }

   public void setPhysicalDestinationName(String value) {
      this.beanTreeNode.setPhysicalDestinationName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "PhysicalDestinationName", (Object)null, (Object)null));
      this.setModified(true);
   }
}
