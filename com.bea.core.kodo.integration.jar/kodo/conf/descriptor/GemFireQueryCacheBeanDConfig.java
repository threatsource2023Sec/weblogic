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

public class GemFireQueryCacheBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private GemFireQueryCacheBean beanTreeNode;

   public GemFireQueryCacheBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (GemFireQueryCacheBean)btn;
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
      return this.getGemFireCacheName();
   }

   public void initKeyPropertyValue(String value) {
      this.setGemFireCacheName(value);
   }

   public String getDCBProperties() {
      StringBuffer sb = new StringBuffer();
      sb.append("GemFireCacheName: ");
      sb.append(this.beanTreeNode.getGemFireCacheName());
      sb.append("\n");
      return sb.toString();
   }

   public void validate() throws ConfigurationException {
   }

   public String getGemFireCacheName() {
      return this.beanTreeNode.getGemFireCacheName();
   }

   public void setGemFireCacheName(String value) {
      this.beanTreeNode.setGemFireCacheName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "GemFireCacheName", (Object)null, (Object)null));
      this.setModified(true);
   }
}
