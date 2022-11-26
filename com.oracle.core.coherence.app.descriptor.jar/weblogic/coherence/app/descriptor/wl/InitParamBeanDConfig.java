package weblogic.coherence.app.descriptor.wl;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.deploy.model.DDBean;
import javax.enterprise.deploy.spi.DConfigBean;
import javax.enterprise.deploy.spi.exceptions.ConfigurationException;
import weblogic.deploy.api.internal.utils.Debug;
import weblogic.deploy.api.spi.config.BasicDConfigBean;
import weblogic.descriptor.DescriptorBean;

public class InitParamBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private InitParamBean beanTreeNode;

   public InitParamBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (InitParamBean)btn;
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

   public String getParamType() {
      return this.beanTreeNode.getParamType();
   }

   public void setParamType(String value) {
      this.beanTreeNode.setParamType(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ParamType", (Object)null, (Object)null));
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
