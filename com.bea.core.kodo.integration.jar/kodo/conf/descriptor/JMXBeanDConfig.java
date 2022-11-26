package kodo.conf.descriptor;

import java.util.ArrayList;
import java.util.List;
import javax.enterprise.deploy.model.DDBean;
import javax.enterprise.deploy.spi.DConfigBean;
import javax.enterprise.deploy.spi.exceptions.ConfigurationException;
import weblogic.deploy.api.internal.utils.Debug;
import weblogic.deploy.api.spi.config.BasicDConfigBean;
import weblogic.descriptor.DescriptorBean;

public class JMXBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private JMXBean beanTreeNode;

   public JMXBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (JMXBean)btn;
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

   public NoneJMXBean getNoneJMX() {
      return this.beanTreeNode.getNoneJMX();
   }

   public LocalJMXBean getLocalJMX() {
      return this.beanTreeNode.getLocalJMX();
   }

   public GUIJMXBean getGUIJMX() {
      return this.beanTreeNode.getGUIJMX();
   }

   public JMX2JMXBean getJMX2JMX() {
      return this.beanTreeNode.getJMX2JMX();
   }

   public MX4J1JMXBean getMX4J1JMX() {
      return this.beanTreeNode.getMX4J1JMX();
   }

   public WLS81JMXBean getWLS81JMX() {
      return this.beanTreeNode.getWLS81JMX();
   }
}
