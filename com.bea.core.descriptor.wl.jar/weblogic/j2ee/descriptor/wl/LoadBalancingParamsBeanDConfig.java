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

public class LoadBalancingParamsBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private LoadBalancingParamsBean beanTreeNode;

   public LoadBalancingParamsBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (LoadBalancingParamsBean)btn;
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

   public boolean isLoadBalancingEnabled() {
      return this.beanTreeNode.isLoadBalancingEnabled();
   }

   public void setLoadBalancingEnabled(boolean value) {
      this.beanTreeNode.setLoadBalancingEnabled(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "LoadBalancingEnabled", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isServerAffinityEnabled() {
      return this.beanTreeNode.isServerAffinityEnabled();
   }

   public void setServerAffinityEnabled(boolean value) {
      this.beanTreeNode.setServerAffinityEnabled(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ServerAffinityEnabled", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getProducerLoadBalancingPolicy() {
      return this.beanTreeNode.getProducerLoadBalancingPolicy();
   }

   public void setProducerLoadBalancingPolicy(String value) {
      this.beanTreeNode.setProducerLoadBalancingPolicy(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ProducerLoadBalancingPolicy", (Object)null, (Object)null));
      this.setModified(true);
   }
}
