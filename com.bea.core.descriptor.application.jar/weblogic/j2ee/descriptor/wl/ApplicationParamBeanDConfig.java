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

public class ApplicationParamBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private ApplicationParamBean beanTreeNode;

   public ApplicationParamBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (ApplicationParamBean)btn;
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
      return this.getParamName();
   }

   public void initKeyPropertyValue(String value) {
      this.setParamName(value);
   }

   public String getDCBProperties() {
      StringBuffer sb = new StringBuffer();
      sb.append("ParamName: ");
      sb.append(this.beanTreeNode.getParamName());
      sb.append("\n");
      return sb.toString();
   }

   public void validate() throws ConfigurationException {
   }

   public String getDescription() {
      return this.beanTreeNode.getDescription();
   }

   public void setDescription(String value) {
      this.beanTreeNode.setDescription(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Description", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getParamName() {
      return this.beanTreeNode.getParamName();
   }

   public void setParamName(String value) {
      this.beanTreeNode.setParamName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ParamName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getParamValue() {
      return this.beanTreeNode.getParamValue();
   }

   public void setParamValue(String value) {
      this.beanTreeNode.setParamValue(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ParamValue", (Object)null, (Object)null));
      this.setModified(true);
   }
}
