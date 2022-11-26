package kodo.conf.descriptor;

import java.util.ArrayList;
import java.util.List;
import javax.enterprise.deploy.model.DDBean;
import javax.enterprise.deploy.spi.DConfigBean;
import javax.enterprise.deploy.spi.exceptions.ConfigurationException;
import weblogic.deploy.api.internal.utils.Debug;
import weblogic.deploy.api.spi.config.BasicDConfigBean;
import weblogic.descriptor.DescriptorBean;

public class ProfilingBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private ProfilingBean beanTreeNode;

   public ProfilingBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (ProfilingBean)btn;
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

   public NoneProfilingBean getNoneProfiling() {
      return this.beanTreeNode.getNoneProfiling();
   }

   public LocalProfilingBean getLocalProfiling() {
      return this.beanTreeNode.getLocalProfiling();
   }

   public ExportProfilingBean getExportProfiling() {
      return this.beanTreeNode.getExportProfiling();
   }

   public GUIProfilingBean getGUIProfiling() {
      return this.beanTreeNode.getGUIProfiling();
   }

   public Class[] getProfilingTypes() {
      return this.beanTreeNode.getProfilingTypes();
   }

   public ProfilingBean getProfiling() {
      return this.beanTreeNode.getProfiling();
   }
}
