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

public class StartupBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private StartupBean beanTreeNode;

   public StartupBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (StartupBean)btn;
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

   public String getStartupClass() {
      return this.beanTreeNode.getStartupClass();
   }

   public void setStartupClass(String value) {
      this.beanTreeNode.setStartupClass(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "StartupClass", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getStartupUri() {
      return this.beanTreeNode.getStartupUri();
   }

   public void setStartupUri(String value) {
      this.beanTreeNode.setStartupUri(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "StartupUri", (Object)null, (Object)null));
      this.setModified(true);
   }
}
