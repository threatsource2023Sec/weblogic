package kodo.conf.descriptor;

import java.util.ArrayList;
import java.util.List;
import javax.enterprise.deploy.model.DDBean;
import javax.enterprise.deploy.spi.DConfigBean;
import javax.enterprise.deploy.spi.exceptions.ConfigurationException;
import weblogic.deploy.api.internal.utils.Debug;
import weblogic.deploy.api.spi.config.BasicDConfigBean;
import weblogic.descriptor.DescriptorBean;

public class DataCachesBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private DataCachesBean beanTreeNode;

   public DataCachesBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (DataCachesBean)btn;
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

   public DefaultDataCacheBean[] getDefaultDataCache() {
      return this.beanTreeNode.getDefaultDataCache();
   }

   public KodoConcurrentDataCacheBean[] getKodoConcurrentDataCache() {
      return this.beanTreeNode.getKodoConcurrentDataCache();
   }

   public GemFireDataCacheBean[] getGemFireDataCache() {
      return this.beanTreeNode.getGemFireDataCache();
   }

   public LRUDataCacheBean[] getLRUDataCache() {
      return this.beanTreeNode.getLRUDataCache();
   }

   public TangosolDataCacheBean[] getTangosolDataCache() {
      return this.beanTreeNode.getTangosolDataCache();
   }

   public CustomDataCacheBean[] getCustomDataCache() {
      return this.beanTreeNode.getCustomDataCache();
   }

   public Class[] getDataCacheTypes() {
      return this.beanTreeNode.getDataCacheTypes();
   }

   public DataCacheBean[] getDataCaches() {
      return this.beanTreeNode.getDataCaches();
   }
}
