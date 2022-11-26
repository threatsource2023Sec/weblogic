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

public class EnumRefBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private EnumRefBean beanTreeNode;

   public EnumRefBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (EnumRefBean)btn;
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
      DDBean[] ddbs = this.getDDBean().getChildBean(this.applyNamespace("enum-class-name"));
      if (ddbs != null && ddbs.length > 0) {
         this.beanTreeNode.setEnumClassName(ddbs[0].getText());
         if (debug) {
            Debug.say("inited with EnumClassName with " + ddbs[0].getText());
         }
      }

      ddbs = this.getDDBean().getChildBean(this.applyNamespace("default-value"));
      if (ddbs != null && ddbs.length > 0) {
         String[] s = new String[ddbs.length];

         for(int i = 0; i < ddbs.length; ++i) {
            s[i] = ddbs[i].getText();
         }

         this.beanTreeNode.setDefaultValues(s);
      }

   }

   public boolean hasCustomInit() {
      return true;
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

   public String getEnumClassName() {
      return this.beanTreeNode.getEnumClassName();
   }

   public void setEnumClassName(String value) {
      this.beanTreeNode.setEnumClassName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "EnumClassName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String[] getDefaultValues() {
      return this.beanTreeNode.getDefaultValues();
   }

   public void setDefaultValues(String[] value) {
      this.beanTreeNode.setDefaultValues(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DefaultValues", (Object)null, (Object)null));
      this.setModified(true);
   }
}
