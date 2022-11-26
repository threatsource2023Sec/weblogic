package weblogic.j2ee.descriptor.wl;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.deploy.model.DDBean;
import javax.enterprise.deploy.spi.DConfigBean;
import javax.enterprise.deploy.spi.exceptions.ConfigurationException;
import weblogic.deploy.api.internal.utils.Debug;
import weblogic.deploy.api.spi.config.BasicDConfigBean;
import weblogic.deploy.api.spi.config.BasicDConfigBeanRoot;
import weblogic.descriptor.DescriptorBean;

public class SimpleTypeDefinitionBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private SimpleTypeDefinitionBean beanTreeNode;
   private MemberConstraintBeanDConfig constraintDConfig;

   public SimpleTypeDefinitionBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (SimpleTypeDefinitionBean)btn;
      this.beanTree = btn;
      this.parent = (BasicDConfigBean)parent;
      this.initXpaths();
      this.customInit();
   }

   private void initXpaths() throws ConfigurationException {
      List xlist = new ArrayList();
      xlist.add(this.applyNamespace("constraint"));
      this.xpaths = (String[])((String[])xlist.toArray(new String[0]));
   }

   private void customInit() throws ConfigurationException {
      DDBean[] ddbs = this.getDDBean().getChildBean(this.applyNamespace("base-type"));
      if (ddbs != null && ddbs.length > 0) {
         this.beanTreeNode.setBaseType(ddbs[0].getText());
         if (debug) {
            Debug.say("inited with BaseType with " + ddbs[0].getText());
         }
      }

      ddbs = this.getDDBean().getChildBean(this.applyNamespace("requires-encryption"));
      if (ddbs != null && ddbs.length > 0) {
         this.beanTreeNode.setRequiresEncryption(Boolean.valueOf(ddbs[0].getText()));
         if (debug) {
            Debug.say("inited with RequiresEncryption with " + ddbs[0].getText());
         }
      }

      ddbs = this.getDDBean().getChildBean(this.applyNamespace("default-value"));
      if (ddbs != null && ddbs.length > 0) {
         String[] s = new String[ddbs.length];

         for(int i = 0; i < ddbs.length; ++i) {
            s[i] = ddbs[i].getText();
         }

         this.beanTreeNode.setDefaultValue(s);
      }

   }

   public boolean hasCustomInit() {
      return true;
   }

   public DConfigBean createDConfigBean(DDBean ddb, DConfigBean parent) throws ConfigurationException {
      if (debug) {
         Debug.say("Creating child DCB for <" + ddb.getXpath() + ">");
      }

      boolean newDCB = false;
      BasicDConfigBean retBean = null;
      String xpath = ddb.getXpath();
      int xpathIndex = 0;
      if (this.lastElementOf(this.xpaths[xpathIndex++]).equals(this.lastElementOf(xpath))) {
         MemberConstraintBean btn = this.beanTreeNode.getConstraint();
         if (btn == null) {
            if (debug) {
               Debug.say("creating new dcb element");
            }

            btn = this.beanTreeNode.createConstraint();
            newDCB = true;
         }

         this.constraintDConfig = new MemberConstraintBeanDConfig(ddb, (DescriptorBean)btn, parent);
         retBean = this.constraintDConfig;
         if (!retBean.hasCustomInit()) {
            retBean.setParentPropertyName("Constraint");
         }
      } else if (debug) {
         Debug.say("Ignoring " + ddb.getXpath());

         for(int i = 0; i < this.xpaths.length; ++i) {
            Debug.say("xpaths[" + i + "]=" + this.xpaths[i]);
         }
      }

      if (retBean != null) {
         this.addDConfigBean(retBean);
         if (newDCB) {
            retBean.setModified(true);

            Object p;
            for(p = retBean; ((BasicDConfigBean)p).getParent() != null; p = ((BasicDConfigBean)p).getParent()) {
            }

            ((BasicDConfigBeanRoot)p).registerAsListener(retBean.getDescriptorBean());
         }

         this.processDCB(retBean, newDCB);
      }

      return retBean;
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

   public String getBaseType() {
      return this.beanTreeNode.getBaseType();
   }

   public void setBaseType(String value) {
      this.beanTreeNode.setBaseType(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "BaseType", (Object)null, (Object)null));
      this.setModified(true);
   }

   public MemberConstraintBeanDConfig getConstraint() {
      return this.constraintDConfig;
   }

   public void setConstraint(MemberConstraintBeanDConfig value) {
      this.constraintDConfig = value;
      this.firePropertyChange(new PropertyChangeEvent(this, "Constraint", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getRequiresEncryption() {
      return this.beanTreeNode.getRequiresEncryption();
   }

   public void setRequiresEncryption(boolean value) {
      this.beanTreeNode.setRequiresEncryption(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "RequiresEncryption", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String[] getDefaultValue() {
      return this.beanTreeNode.getDefaultValue();
   }

   public void setDefaultValue(String[] value) {
      this.beanTreeNode.setDefaultValue(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DefaultValue", (Object)null, (Object)null));
      this.setModified(true);
   }
}
