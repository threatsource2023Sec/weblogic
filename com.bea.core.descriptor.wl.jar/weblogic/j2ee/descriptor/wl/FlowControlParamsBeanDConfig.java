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

public class FlowControlParamsBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private FlowControlParamsBean beanTreeNode;

   public FlowControlParamsBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (FlowControlParamsBean)btn;
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

   public int getFlowMinimum() {
      return this.beanTreeNode.getFlowMinimum();
   }

   public void setFlowMinimum(int value) {
      this.beanTreeNode.setFlowMinimum(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "FlowMinimum", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getFlowMaximum() {
      return this.beanTreeNode.getFlowMaximum();
   }

   public void setFlowMaximum(int value) {
      this.beanTreeNode.setFlowMaximum(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "FlowMaximum", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getFlowInterval() {
      return this.beanTreeNode.getFlowInterval();
   }

   public void setFlowInterval(int value) {
      this.beanTreeNode.setFlowInterval(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "FlowInterval", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getFlowSteps() {
      return this.beanTreeNode.getFlowSteps();
   }

   public void setFlowSteps(int value) {
      this.beanTreeNode.setFlowSteps(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "FlowSteps", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isFlowControlEnabled() {
      return this.beanTreeNode.isFlowControlEnabled();
   }

   public void setFlowControlEnabled(boolean value) {
      this.beanTreeNode.setFlowControlEnabled(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "FlowControlEnabled", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getOneWaySendMode() {
      return this.beanTreeNode.getOneWaySendMode();
   }

   public void setOneWaySendMode(String value) {
      this.beanTreeNode.setOneWaySendMode(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "OneWaySendMode", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getOneWaySendWindowSize() {
      return this.beanTreeNode.getOneWaySendWindowSize();
   }

   public void setOneWaySendWindowSize(int value) {
      this.beanTreeNode.setOneWaySendWindowSize(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "OneWaySendWindowSize", (Object)null, (Object)null));
      this.setModified(true);
   }
}
