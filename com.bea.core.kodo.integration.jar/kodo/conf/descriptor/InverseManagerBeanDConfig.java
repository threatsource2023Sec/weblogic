package kodo.conf.descriptor;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.deploy.model.DDBean;
import javax.enterprise.deploy.spi.DConfigBean;
import javax.enterprise.deploy.spi.exceptions.ConfigurationException;
import weblogic.deploy.api.internal.utils.Debug;
import weblogic.deploy.api.spi.config.BasicDConfigBean;
import weblogic.descriptor.DescriptorBean;

public class InverseManagerBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private InverseManagerBean beanTreeNode;

   public InverseManagerBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (InverseManagerBean)btn;
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

   public String getAction() {
      return this.beanTreeNode.getAction();
   }

   public void setAction(String value) {
      this.beanTreeNode.setAction(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Action", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getManageLRS() {
      return this.beanTreeNode.getManageLRS();
   }

   public void setManageLRS(boolean value) {
      this.beanTreeNode.setManageLRS(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ManageLRS", (Object)null, (Object)null));
      this.setModified(true);
   }
}
