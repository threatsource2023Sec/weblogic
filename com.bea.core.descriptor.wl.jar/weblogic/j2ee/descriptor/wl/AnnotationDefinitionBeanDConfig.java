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

public class AnnotationDefinitionBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private AnnotationDefinitionBean beanTreeNode;
   private MembershipConstraintBeanDConfig membershipConstraintDConfig;
   private List memberDefinitionsDConfig = new ArrayList();

   public AnnotationDefinitionBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (AnnotationDefinitionBean)btn;
      this.beanTree = btn;
      this.parent = (BasicDConfigBean)parent;
      this.initXpaths();
      this.customInit();
   }

   private void initXpaths() throws ConfigurationException {
      List xlist = new ArrayList();
      xlist.add(this.applyNamespace("membership-constraint"));
      xlist.add(this.getParentXpath(this.applyNamespace("member-definition/member-name")));
      this.xpaths = (String[])((String[])xlist.toArray(new String[0]));
   }

   private void customInit() throws ConfigurationException {
      DDBean[] ddbs = this.getDDBean().getChildBean(this.applyNamespace("allowed-on-declaration"));
      if (ddbs != null && ddbs.length > 0) {
         this.beanTreeNode.setAllowedOnDeclaration(Boolean.valueOf(ddbs[0].getText()));
         if (debug) {
            Debug.say("inited with AllowedOnDeclaration with " + ddbs[0].getText());
         }
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
         MembershipConstraintBean btn = this.beanTreeNode.getMembershipConstraint();
         if (btn == null) {
            if (debug) {
               Debug.say("creating new dcb element");
            }

            btn = this.beanTreeNode.createMembershipConstraint();
            newDCB = true;
         }

         this.membershipConstraintDConfig = new MembershipConstraintBeanDConfig(ddb, (DescriptorBean)btn, parent);
         retBean = this.membershipConstraintDConfig;
         if (!((BasicDConfigBean)retBean).hasCustomInit()) {
            ((BasicDConfigBean)retBean).setParentPropertyName("MembershipConstraint");
         }
      } else if (this.lastElementOf(this.xpaths[xpathIndex++]).equals(this.lastElementOf(xpath))) {
         MemberDefinitionBean btn = null;
         MemberDefinitionBean[] list = this.beanTreeNode.getMemberDefinitions();
         if (list == null) {
            this.beanTreeNode.createMemberDefinition();
            list = this.beanTreeNode.getMemberDefinitions();
         }

         String keyName = this.lastElementOf(this.applyNamespace("member-definition/member-name"));
         this.setKeyName(keyName);
         String key = this.getDDKey(ddb, keyName);
         if (debug) {
            Debug.say("Using keyName: " + keyName + ", key: " + key);
         }

         for(int i = 0; i < list.length; ++i) {
            btn = list[i];
            if (this.isMatch((DescriptorBean)btn, ddb, key)) {
               break;
            }

            btn = null;
         }

         if (btn == null) {
            if (debug) {
               Debug.say("creating new dcb element");
            }

            btn = this.beanTreeNode.createMemberDefinition();
            newDCB = true;
         }

         retBean = new MemberDefinitionBeanDConfig(ddb, (DescriptorBean)btn, parent);
         ((MemberDefinitionBeanDConfig)retBean).initKeyPropertyValue(key);
         if (!((BasicDConfigBean)retBean).hasCustomInit()) {
            ((BasicDConfigBean)retBean).setParentPropertyName("MemberDefinitions");
         }

         if (debug) {
            Debug.say("dcb dump: " + ((BasicDConfigBean)retBean).toString());
         }

         this.memberDefinitionsDConfig.add(retBean);
      } else if (debug) {
         Debug.say("Ignoring " + ddb.getXpath());

         for(int i = 0; i < this.xpaths.length; ++i) {
            Debug.say("xpaths[" + i + "]=" + this.xpaths[i]);
         }
      }

      if (retBean != null) {
         this.addDConfigBean((DConfigBean)retBean);
         if (newDCB) {
            ((BasicDConfigBean)retBean).setModified(true);

            Object p;
            for(p = retBean; ((BasicDConfigBean)p).getParent() != null; p = ((BasicDConfigBean)p).getParent()) {
            }

            ((BasicDConfigBeanRoot)p).registerAsListener(((BasicDConfigBean)retBean).getDescriptorBean());
         }

         this.processDCB((BasicDConfigBean)retBean, newDCB);
      }

      return (DConfigBean)retBean;
   }

   public String keyPropertyValue() {
      return this.getAnnotationClassName();
   }

   public void initKeyPropertyValue(String value) {
      this.setAnnotationClassName(value);
   }

   public String getDCBProperties() {
      StringBuffer sb = new StringBuffer();
      sb.append("AnnotationClassName: ");
      sb.append(this.beanTreeNode.getAnnotationClassName());
      sb.append("\n");
      return sb.toString();
   }

   public void validate() throws ConfigurationException {
   }

   public String getAnnotationClassName() {
      return this.beanTreeNode.getAnnotationClassName();
   }

   public void setAnnotationClassName(String value) {
      this.beanTreeNode.setAnnotationClassName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "AnnotationClassName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public MembershipConstraintBeanDConfig getMembershipConstraint() {
      return this.membershipConstraintDConfig;
   }

   public void setMembershipConstraint(MembershipConstraintBeanDConfig value) {
      this.membershipConstraintDConfig = value;
      this.firePropertyChange(new PropertyChangeEvent(this, "MembershipConstraint", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getAllowedOnDeclaration() {
      return this.beanTreeNode.getAllowedOnDeclaration();
   }

   public void setAllowedOnDeclaration(boolean value) {
      this.beanTreeNode.setAllowedOnDeclaration(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "AllowedOnDeclaration", (Object)null, (Object)null));
      this.setModified(true);
   }

   public MemberDefinitionBeanDConfig[] getMemberDefinitions() {
      return (MemberDefinitionBeanDConfig[])((MemberDefinitionBeanDConfig[])this.memberDefinitionsDConfig.toArray(new MemberDefinitionBeanDConfig[0]));
   }

   void addMemberDefinitionBean(MemberDefinitionBeanDConfig value) {
      this.addToList(this.memberDefinitionsDConfig, "MemberDefinitionBean", value);
   }

   void removeMemberDefinitionBean(MemberDefinitionBeanDConfig value) {
      this.removeFromList(this.memberDefinitionsDConfig, "MemberDefinitionBean", value);
   }
}
