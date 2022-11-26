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

public class WebserviceSecurityBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private WebserviceSecurityBean beanTreeNode;

   public WebserviceSecurityBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (WebserviceSecurityBean)btn;
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
      return this.getMbeanName();
   }

   public void initKeyPropertyValue(String value) {
      this.setMbeanName(value);
   }

   public String getDCBProperties() {
      StringBuffer sb = new StringBuffer();
      sb.append("MbeanName: ");
      sb.append(this.beanTreeNode.getMbeanName());
      sb.append("\n");
      return sb.toString();
   }

   public void validate() throws ConfigurationException {
   }

   public String getMbeanName() {
      return this.beanTreeNode.getMbeanName();
   }

   public void setMbeanName(String value) {
      this.beanTreeNode.setMbeanName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "MbeanName", (Object)null, (Object)null));
      this.setModified(true);
   }
}
