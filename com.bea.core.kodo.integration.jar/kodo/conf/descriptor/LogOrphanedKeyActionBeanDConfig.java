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

public class LogOrphanedKeyActionBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private LogOrphanedKeyActionBean beanTreeNode;

   public LogOrphanedKeyActionBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (LogOrphanedKeyActionBean)btn;
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

   public String getChannel() {
      return this.beanTreeNode.getChannel();
   }

   public void setChannel(String value) {
      this.beanTreeNode.setChannel(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Channel", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getLevel() {
      return this.beanTreeNode.getLevel();
   }

   public void setLevel(String value) {
      this.beanTreeNode.setLevel(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Level", (Object)null, (Object)null));
      this.setModified(true);
   }
}
