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

public class GzipCompressionBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private GzipCompressionBean beanTreeNode;

   public GzipCompressionBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (GzipCompressionBean)btn;
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

   public boolean isEnabled() {
      return this.beanTreeNode.isEnabled();
   }

   public void setEnabled(boolean value) {
      this.beanTreeNode.setEnabled(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Enabled", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isEnabledSet() {
      return this.beanTreeNode.isEnabledSet();
   }

   public long getMinContentLength() {
      return this.beanTreeNode.getMinContentLength();
   }

   public void setMinContentLength(long value) {
      this.beanTreeNode.setMinContentLength(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "MinContentLength", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isMinContentLengthSet() {
      return this.beanTreeNode.isMinContentLengthSet();
   }

   public String[] getContentType() {
      return this.beanTreeNode.getContentType();
   }

   public void setContentType(String[] value) {
      this.beanTreeNode.setContentType(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ContentType", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isContentTypeSet() {
      return this.beanTreeNode.isContentTypeSet();
   }
}
