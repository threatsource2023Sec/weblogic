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

public class EnumDefinitionBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private EnumDefinitionBean beanTreeNode;

   public EnumDefinitionBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (EnumDefinitionBean)btn;
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
      DDBean[] ddbs = this.getDDBean().getChildBean(this.applyNamespace("enum-value"));
      if (ddbs != null && ddbs.length > 0) {
         String[] s = new String[ddbs.length];

         for(int i = 0; i < ddbs.length; ++i) {
            s[i] = ddbs[i].getText();
         }

         this.beanTreeNode.setEnumValues(s);
      }

   }

   public boolean hasCustomInit() {
      return true;
   }

   public DConfigBean createDConfigBean(DDBean ddb, DConfigBean parent) throws ConfigurationException {
      return null;
   }

   public String keyPropertyValue() {
      return this.getEnumClassName();
   }

   public void initKeyPropertyValue(String value) {
      this.setEnumClassName(value);
   }

   public String getDCBProperties() {
      StringBuffer sb = new StringBuffer();
      sb.append("EnumClassName: ");
      sb.append(this.beanTreeNode.getEnumClassName());
      sb.append("\n");
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

   public String[] getEnumValues() {
      return this.beanTreeNode.getEnumValues();
   }

   public void setEnumValues(String[] value) {
      this.beanTreeNode.setEnumValues(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "EnumValues", (Object)null, (Object)null));
      this.setModified(true);
   }
}
