package kodo.conf.descriptor;

import java.util.ArrayList;
import java.util.List;
import javax.enterprise.deploy.model.DDBean;
import javax.enterprise.deploy.spi.DConfigBean;
import javax.enterprise.deploy.spi.exceptions.ConfigurationException;
import weblogic.deploy.api.internal.utils.Debug;
import weblogic.deploy.api.spi.config.BasicDConfigBean;
import weblogic.descriptor.DescriptorBean;

public class QueryCachesBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private QueryCachesBean beanTreeNode;

   public QueryCachesBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (QueryCachesBean)btn;
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

   public DefaultQueryCacheBean getDefaultQueryCache() {
      return this.beanTreeNode.getDefaultQueryCache();
   }

   public KodoConcurrentQueryCacheBean getKodoConcurrentQueryCache() {
      return this.beanTreeNode.getKodoConcurrentQueryCache();
   }

   public GemFireQueryCacheBean getGemFireQueryCache() {
      return this.beanTreeNode.getGemFireQueryCache();
   }

   public LRUQueryCacheBean getLRUQueryCache() {
      return this.beanTreeNode.getLRUQueryCache();
   }

   public TangosolQueryCacheBean getTangosolQueryCache() {
      return this.beanTreeNode.getTangosolQueryCache();
   }

   public DisabledQueryCacheBean getDisabledQueryCache() {
      return this.beanTreeNode.getDisabledQueryCache();
   }

   public CustomQueryCacheBean getCustomQueryCache() {
      return this.beanTreeNode.getCustomQueryCache();
   }

   public Class[] getQueryCacheTypes() {
      return this.beanTreeNode.getQueryCacheTypes();
   }

   public QueryCacheBean getQueryCache() {
      return this.beanTreeNode.getQueryCache();
   }
}
