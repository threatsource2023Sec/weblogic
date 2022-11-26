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

public class GroupParamsBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private GroupParamsBean beanTreeNode;

   public GroupParamsBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (GroupParamsBean)btn;
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
      return this.getSubDeploymentName();
   }

   public void initKeyPropertyValue(String value) {
      this.setSubDeploymentName(value);
   }

   public String getDCBProperties() {
      StringBuffer sb = new StringBuffer();
      sb.append("SubDeploymentName: ");
      sb.append(this.beanTreeNode.getSubDeploymentName());
      sb.append("\n");
      return sb.toString();
   }

   public void validate() throws ConfigurationException {
   }

   public String getSubDeploymentName() {
      return this.beanTreeNode.getSubDeploymentName();
   }

   public void setSubDeploymentName(String value) {
      this.beanTreeNode.setSubDeploymentName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SubDeploymentName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public DestinationBean getErrorDestination() {
      return this.beanTreeNode.getErrorDestination();
   }

   public void setErrorDestination(DestinationBean value) {
      this.beanTreeNode.setErrorDestination(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ErrorDestination", (Object)null, (Object)null));
      this.setModified(true);
   }
}
