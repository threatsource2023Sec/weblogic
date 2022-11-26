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

public class SAFErrorHandlingBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private SAFErrorHandlingBean beanTreeNode;

   public SAFErrorHandlingBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (SAFErrorHandlingBean)btn;
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

   public String getPolicy() {
      return this.beanTreeNode.getPolicy();
   }

   public void setPolicy(String value) {
      this.beanTreeNode.setPolicy(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Policy", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getLogFormat() {
      return this.beanTreeNode.getLogFormat();
   }

   public void setLogFormat(String value) {
      this.beanTreeNode.setLogFormat(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "LogFormat", (Object)null, (Object)null));
      this.setModified(true);
   }

   public SAFDestinationBean getSAFErrorDestination() {
      return this.beanTreeNode.getSAFErrorDestination();
   }

   public void setSAFErrorDestination(SAFDestinationBean value) {
      this.beanTreeNode.setSAFErrorDestination(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SAFErrorDestination", (Object)null, (Object)null));
      this.setModified(true);
   }
}
