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

public class ForeignJNDIObjectBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private ForeignJNDIObjectBean beanTreeNode;

   public ForeignJNDIObjectBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (ForeignJNDIObjectBean)btn;
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
      sb.append("LocalJNDIName: ");
      sb.append(this.beanTreeNode.getLocalJNDIName());
      sb.append("\n");
      sb.append("RemoteJNDIName: ");
      sb.append(this.beanTreeNode.getRemoteJNDIName());
      sb.append("\n");
      return sb.toString();
   }

   public void validate() throws ConfigurationException {
   }

   public String getLocalJNDIName() {
      return this.beanTreeNode.getLocalJNDIName();
   }

   public void setLocalJNDIName(String value) {
      this.beanTreeNode.setLocalJNDIName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "LocalJNDIName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getRemoteJNDIName() {
      return this.beanTreeNode.getRemoteJNDIName();
   }

   public void setRemoteJNDIName(String value) {
      this.beanTreeNode.setRemoteJNDIName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "RemoteJNDIName", (Object)null, (Object)null));
      this.setModified(true);
   }
}
