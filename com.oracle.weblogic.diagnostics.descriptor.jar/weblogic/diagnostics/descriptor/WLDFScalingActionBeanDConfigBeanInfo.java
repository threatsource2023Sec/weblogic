package weblogic.diagnostics.descriptor;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.deploy.model.DDBean;
import javax.enterprise.deploy.spi.DConfigBean;
import javax.enterprise.deploy.spi.exceptions.ConfigurationException;
import weblogic.deploy.api.internal.utils.Debug;
import weblogic.deploy.api.spi.config.BasicDConfigBean;
import weblogic.descriptor.DescriptorBean;

public abstract class WLDFScalingActionBeanDConfigBeanInfo extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private WLDFScalingActionBean beanTreeNode;

   public WLDFScalingActionBeanDConfigBeanInfo(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (WLDFScalingActionBean)btn;
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

   public String getClusterName() {
      return this.beanTreeNode.getClusterName();
   }

   public void setClusterName(String value) {
      this.beanTreeNode.setClusterName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ClusterName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getScalingSize() {
      return this.beanTreeNode.getScalingSize();
   }

   public void setScalingSize(int value) {
      this.beanTreeNode.setScalingSize(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ScalingSize", (Object)null, (Object)null));
      this.setModified(true);
   }
}
