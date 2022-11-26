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

public class LinkRefBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private LinkRefBean beanTreeNode;

   public LinkRefBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (LinkRefBean)btn;
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

   public String getConnectionFactoryName() {
      return this.beanTreeNode.getConnectionFactoryName();
   }

   public void setConnectionFactoryName(String value) {
      this.beanTreeNode.setConnectionFactoryName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ConnectionFactoryName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getRaLinkRef() {
      return this.beanTreeNode.getRaLinkRef();
   }

   public void setRaLinkRef(String value) {
      this.beanTreeNode.setRaLinkRef(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "RaLinkRef", (Object)null, (Object)null));
      this.setModified(true);
   }
}
