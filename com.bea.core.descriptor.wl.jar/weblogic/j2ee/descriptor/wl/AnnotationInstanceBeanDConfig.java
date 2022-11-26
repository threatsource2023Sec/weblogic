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

public class AnnotationInstanceBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private AnnotationInstanceBean beanTreeNode;
   private List membersDConfig = new ArrayList();
   private List arrayMembersDConfig = new ArrayList();
   private List nestedAnnotationsDConfig = new ArrayList();
   private List nestedAnnotationArraysDConfig = new ArrayList();

   public AnnotationInstanceBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (AnnotationInstanceBean)btn;
      this.beanTree = btn;
      this.parent = (BasicDConfigBean)parent;
      this.initXpaths();
      this.customInit();
   }

   private void initXpaths() throws ConfigurationException {
      List xlist = new ArrayList();
      xlist.add(this.getParentXpath(this.applyNamespace("member/member-name")));
      xlist.add(this.getParentXpath(this.applyNamespace("array-member/member-name")));
      xlist.add(this.getParentXpath(this.applyNamespace("nested-annotation/member-name")));
      xlist.add(this.getParentXpath(this.applyNamespace("nested-annotation-array/member-name")));
      this.xpaths = (String[])((String[])xlist.toArray(new String[0]));
   }

   private void customInit() throws ConfigurationException {
      DDBean[] ddbs = this.getDDBean().getChildBean(this.applyNamespace("annotation-class-name"));
      if (ddbs != null && ddbs.length > 0) {
         this.beanTreeNode.setAnnotationClassName(ddbs[0].getText());
         if (debug) {
            Debug.say("inited with AnnotationClassName with " + ddbs[0].getText());
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
      String key;
      String keyName;
      int i;
      if (this.lastElementOf(this.xpaths[xpathIndex++]).equals(this.lastElementOf(xpath))) {
         MemberBean btn = null;
         MemberBean[] list = this.beanTreeNode.getMembers();
         if (list == null) {
            this.beanTreeNode.createMember();
            list = this.beanTreeNode.getMembers();
         }

         keyName = this.lastElementOf(this.applyNamespace("member/member-name"));
         this.setKeyName(keyName);
         key = this.getDDKey(ddb, keyName);
         if (debug) {
            Debug.say("Using keyName: " + keyName + ", key: " + key);
         }

         for(i = 0; i < list.length; ++i) {
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

            btn = this.beanTreeNode.createMember();
            newDCB = true;
         }

         retBean = new MemberBeanDConfig(ddb, (DescriptorBean)btn, parent);
         ((MemberBeanDConfig)retBean).initKeyPropertyValue(key);
         if (!((BasicDConfigBean)retBean).hasCustomInit()) {
            ((BasicDConfigBean)retBean).setParentPropertyName("Members");
         }

         if (debug) {
            Debug.say("dcb dump: " + ((BasicDConfigBean)retBean).toString());
         }

         this.membersDConfig.add(retBean);
      } else if (this.lastElementOf(this.xpaths[xpathIndex++]).equals(this.lastElementOf(xpath))) {
         ArrayMemberBean btn = null;
         ArrayMemberBean[] list = this.beanTreeNode.getArrayMembers();
         if (list == null) {
            this.beanTreeNode.createArrayMember();
            list = this.beanTreeNode.getArrayMembers();
         }

         keyName = this.lastElementOf(this.applyNamespace("array-member/member-name"));
         this.setKeyName(keyName);
         key = this.getDDKey(ddb, keyName);
         if (debug) {
            Debug.say("Using keyName: " + keyName + ", key: " + key);
         }

         for(i = 0; i < list.length; ++i) {
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

            btn = this.beanTreeNode.createArrayMember();
            newDCB = true;
         }

         retBean = new ArrayMemberBeanDConfig(ddb, (DescriptorBean)btn, parent);
         ((ArrayMemberBeanDConfig)retBean).initKeyPropertyValue(key);
         if (!((BasicDConfigBean)retBean).hasCustomInit()) {
            ((BasicDConfigBean)retBean).setParentPropertyName("ArrayMembers");
         }

         if (debug) {
            Debug.say("dcb dump: " + ((BasicDConfigBean)retBean).toString());
         }

         this.arrayMembersDConfig.add(retBean);
      } else if (this.lastElementOf(this.xpaths[xpathIndex++]).equals(this.lastElementOf(xpath))) {
         NestedAnnotationBean btn = null;
         NestedAnnotationBean[] list = this.beanTreeNode.getNestedAnnotations();
         if (list == null) {
            this.beanTreeNode.createNestedAnnotation();
            list = this.beanTreeNode.getNestedAnnotations();
         }

         keyName = this.lastElementOf(this.applyNamespace("nested-annotation/member-name"));
         this.setKeyName(keyName);
         key = this.getDDKey(ddb, keyName);
         if (debug) {
            Debug.say("Using keyName: " + keyName + ", key: " + key);
         }

         for(i = 0; i < list.length; ++i) {
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

            btn = this.beanTreeNode.createNestedAnnotation();
            newDCB = true;
         }

         retBean = new NestedAnnotationBeanDConfig(ddb, (DescriptorBean)btn, parent);
         ((NestedAnnotationBeanDConfig)retBean).initKeyPropertyValue(key);
         if (!((BasicDConfigBean)retBean).hasCustomInit()) {
            ((BasicDConfigBean)retBean).setParentPropertyName("NestedAnnotations");
         }

         if (debug) {
            Debug.say("dcb dump: " + ((BasicDConfigBean)retBean).toString());
         }

         this.nestedAnnotationsDConfig.add(retBean);
      } else if (this.lastElementOf(this.xpaths[xpathIndex++]).equals(this.lastElementOf(xpath))) {
         NestedAnnotationArrayBean btn = null;
         NestedAnnotationArrayBean[] list = this.beanTreeNode.getNestedAnnotationArrays();
         if (list == null) {
            this.beanTreeNode.createNestedAnnotationArray();
            list = this.beanTreeNode.getNestedAnnotationArrays();
         }

         keyName = this.lastElementOf(this.applyNamespace("nested-annotation-array/member-name"));
         this.setKeyName(keyName);
         key = this.getDDKey(ddb, keyName);
         if (debug) {
            Debug.say("Using keyName: " + keyName + ", key: " + key);
         }

         for(i = 0; i < list.length; ++i) {
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

            btn = this.beanTreeNode.createNestedAnnotationArray();
            newDCB = true;
         }

         retBean = new NestedAnnotationArrayBeanDConfig(ddb, (DescriptorBean)btn, parent);
         ((NestedAnnotationArrayBeanDConfig)retBean).initKeyPropertyValue(key);
         if (!((BasicDConfigBean)retBean).hasCustomInit()) {
            ((BasicDConfigBean)retBean).setParentPropertyName("NestedAnnotationArrays");
         }

         if (debug) {
            Debug.say("dcb dump: " + ((BasicDConfigBean)retBean).toString());
         }

         this.nestedAnnotationArraysDConfig.add(retBean);
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

   public MemberBeanDConfig[] getMembers() {
      return (MemberBeanDConfig[])((MemberBeanDConfig[])this.membersDConfig.toArray(new MemberBeanDConfig[0]));
   }

   void addMemberBean(MemberBeanDConfig value) {
      this.addToList(this.membersDConfig, "MemberBean", value);
   }

   void removeMemberBean(MemberBeanDConfig value) {
      this.removeFromList(this.membersDConfig, "MemberBean", value);
   }

   public ArrayMemberBeanDConfig[] getArrayMembers() {
      return (ArrayMemberBeanDConfig[])((ArrayMemberBeanDConfig[])this.arrayMembersDConfig.toArray(new ArrayMemberBeanDConfig[0]));
   }

   void addArrayMemberBean(ArrayMemberBeanDConfig value) {
      this.addToList(this.arrayMembersDConfig, "ArrayMemberBean", value);
   }

   void removeArrayMemberBean(ArrayMemberBeanDConfig value) {
      this.removeFromList(this.arrayMembersDConfig, "ArrayMemberBean", value);
   }

   public NestedAnnotationBeanDConfig[] getNestedAnnotations() {
      return (NestedAnnotationBeanDConfig[])((NestedAnnotationBeanDConfig[])this.nestedAnnotationsDConfig.toArray(new NestedAnnotationBeanDConfig[0]));
   }

   void addNestedAnnotationBean(NestedAnnotationBeanDConfig value) {
      this.addToList(this.nestedAnnotationsDConfig, "NestedAnnotationBean", value);
   }

   void removeNestedAnnotationBean(NestedAnnotationBeanDConfig value) {
      this.removeFromList(this.nestedAnnotationsDConfig, "NestedAnnotationBean", value);
   }

   public NestedAnnotationArrayBeanDConfig[] getNestedAnnotationArrays() {
      return (NestedAnnotationArrayBeanDConfig[])((NestedAnnotationArrayBeanDConfig[])this.nestedAnnotationArraysDConfig.toArray(new NestedAnnotationArrayBeanDConfig[0]));
   }

   void addNestedAnnotationArrayBean(NestedAnnotationArrayBeanDConfig value) {
      this.addToList(this.nestedAnnotationArraysDConfig, "NestedAnnotationArrayBean", value);
   }

   void removeNestedAnnotationArrayBean(NestedAnnotationArrayBeanDConfig value) {
      this.removeFromList(this.nestedAnnotationArraysDConfig, "NestedAnnotationArrayBean", value);
   }

   public String getShortDescription() {
      return this.beanTreeNode.getShortDescription();
   }
}
