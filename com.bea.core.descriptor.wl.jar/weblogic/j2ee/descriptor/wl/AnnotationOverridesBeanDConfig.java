package weblogic.j2ee.descriptor.wl;

import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.deploy.model.DDBean;
import javax.enterprise.deploy.model.DDBeanRoot;
import javax.enterprise.deploy.spi.DConfigBean;
import javax.enterprise.deploy.spi.exceptions.ConfigurationException;
import javax.enterprise.deploy.spi.exceptions.InvalidModuleException;
import weblogic.deploy.api.internal.utils.Debug;
import weblogic.deploy.api.spi.WebLogicDeploymentConfiguration;
import weblogic.deploy.api.spi.config.BasicDConfigBean;
import weblogic.deploy.api.spi.config.BasicDConfigBeanRoot;
import weblogic.deploy.api.spi.config.DescriptorParser;
import weblogic.deploy.api.spi.config.DescriptorSupport;
import weblogic.descriptor.DescriptorBean;

public class AnnotationOverridesBeanDConfig extends BasicDConfigBeanRoot {
   private static final boolean debug = Debug.isDebug("config");
   private AnnotationOverridesBean beanTreeNode;
   private List annotatedClassesDConfig = new ArrayList();
   private List annotationDefinitionsDConfig = new ArrayList();
   private List enumDefinitionsDConfig = new ArrayList();

   public AnnotationOverridesBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (AnnotationOverridesBean)btn;
      this.beanTree = btn;
      this.parent = (BasicDConfigBean)parent;
      this.initXpaths();
      this.customInit();
   }

   public AnnotationOverridesBeanDConfig(DDBeanRoot root, WebLogicDeploymentConfiguration dc, DescriptorBean beanTree, String name, DescriptorSupport ds) throws InvalidModuleException, IOException {
      super(root, dc, name, ds);
      this.beanTree = beanTree;
      this.beanTreeNode = (AnnotationOverridesBean)beanTree;

      try {
         this.initXpaths();
         this.customInit();
      } catch (ConfigurationException var7) {
         throw new InvalidModuleException(var7.toString());
      }
   }

   public AnnotationOverridesBeanDConfig(DDBeanRoot root, WebLogicDeploymentConfiguration dc, String name, DescriptorSupport ds) throws InvalidModuleException, IOException {
      super(root, dc, name, ds);

      try {
         this.initXpaths();
         if (debug) {
            Debug.say("Creating new root object");
         }

         this.beanTree = DescriptorParser.getWLSEditableDescriptorBean(AnnotationOverridesBean.class);
         this.beanTreeNode = (AnnotationOverridesBean)this.beanTree;
         this.customInit();
      } catch (ConfigurationException var6) {
         throw new InvalidModuleException(var6.toString());
      }
   }

   private void initXpaths() throws ConfigurationException {
      List xlist = new ArrayList();
      xlist.add(this.getParentXpath(this.applyNamespace("annotation-manifest/annotated-class/annotated-class-name")));
      xlist.add(this.getParentXpath(this.applyNamespace("annotation-manifest/annotation-definition/annotation-class-name")));
      xlist.add(this.getParentXpath(this.applyNamespace("annotation-manifest/enum-definition/enum-class-name")));
      this.xpaths = (String[])((String[])xlist.toArray(new String[0]));
   }

   private void customInit() throws ConfigurationException {
      DDBean[] ddbs = this.getDDBean().getChildBean(this.applyNamespace("annotation-manifest/version"));
      if (ddbs != null && ddbs.length > 0) {
         this.beanTreeNode.setVersion(ddbs[0].getText());
         if (debug) {
            Debug.say("inited with Version with " + ddbs[0].getText());
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
         AnnotatedClassBean btn = null;
         AnnotatedClassBean[] list = this.beanTreeNode.getAnnotatedClasses();
         if (list == null) {
            this.beanTreeNode.createAnnotatedClass();
            list = this.beanTreeNode.getAnnotatedClasses();
         }

         keyName = this.lastElementOf(this.applyNamespace("annotation-manifest/annotated-class/annotated-class-name"));
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

            btn = this.beanTreeNode.createAnnotatedClass();
            newDCB = true;
         }

         retBean = new AnnotatedClassBeanDConfig(ddb, (DescriptorBean)btn, parent);
         ((AnnotatedClassBeanDConfig)retBean).initKeyPropertyValue(key);
         if (!((BasicDConfigBean)retBean).hasCustomInit()) {
            ((BasicDConfigBean)retBean).setParentPropertyName("AnnotatedClasses");
         }

         if (debug) {
            Debug.say("dcb dump: " + ((BasicDConfigBean)retBean).toString());
         }

         this.annotatedClassesDConfig.add(retBean);
      } else if (this.lastElementOf(this.xpaths[xpathIndex++]).equals(this.lastElementOf(xpath))) {
         AnnotationDefinitionBean btn = null;
         AnnotationDefinitionBean[] list = this.beanTreeNode.getAnnotationDefinitions();
         if (list == null) {
            this.beanTreeNode.createAnnotationDefinition();
            list = this.beanTreeNode.getAnnotationDefinitions();
         }

         keyName = this.lastElementOf(this.applyNamespace("annotation-manifest/annotation-definition/annotation-class-name"));
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

            btn = this.beanTreeNode.createAnnotationDefinition();
            newDCB = true;
         }

         retBean = new AnnotationDefinitionBeanDConfig(ddb, (DescriptorBean)btn, parent);
         ((AnnotationDefinitionBeanDConfig)retBean).initKeyPropertyValue(key);
         if (!((BasicDConfigBean)retBean).hasCustomInit()) {
            ((BasicDConfigBean)retBean).setParentPropertyName("AnnotationDefinitions");
         }

         if (debug) {
            Debug.say("dcb dump: " + ((BasicDConfigBean)retBean).toString());
         }

         this.annotationDefinitionsDConfig.add(retBean);
      } else if (this.lastElementOf(this.xpaths[xpathIndex++]).equals(this.lastElementOf(xpath))) {
         EnumDefinitionBean btn = null;
         EnumDefinitionBean[] list = this.beanTreeNode.getEnumDefinitions();
         if (list == null) {
            this.beanTreeNode.createEnumDefinition();
            list = this.beanTreeNode.getEnumDefinitions();
         }

         keyName = this.lastElementOf(this.applyNamespace("annotation-manifest/enum-definition/enum-class-name"));
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

            btn = this.beanTreeNode.createEnumDefinition();
            newDCB = true;
         }

         retBean = new EnumDefinitionBeanDConfig(ddb, (DescriptorBean)btn, parent);
         ((EnumDefinitionBeanDConfig)retBean).initKeyPropertyValue(key);
         if (!((BasicDConfigBean)retBean).hasCustomInit()) {
            ((BasicDConfigBean)retBean).setParentPropertyName("EnumDefinitions");
         }

         if (debug) {
            Debug.say("dcb dump: " + ((BasicDConfigBean)retBean).toString());
         }

         this.enumDefinitionsDConfig.add(retBean);
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

   public AnnotatedClassBeanDConfig[] getAnnotatedClasses() {
      return (AnnotatedClassBeanDConfig[])((AnnotatedClassBeanDConfig[])this.annotatedClassesDConfig.toArray(new AnnotatedClassBeanDConfig[0]));
   }

   void addAnnotatedClassBean(AnnotatedClassBeanDConfig value) {
      this.addToList(this.annotatedClassesDConfig, "AnnotatedClassBean", value);
   }

   void removeAnnotatedClassBean(AnnotatedClassBeanDConfig value) {
      this.removeFromList(this.annotatedClassesDConfig, "AnnotatedClassBean", value);
   }

   public AnnotationDefinitionBeanDConfig[] getAnnotationDefinitions() {
      return (AnnotationDefinitionBeanDConfig[])((AnnotationDefinitionBeanDConfig[])this.annotationDefinitionsDConfig.toArray(new AnnotationDefinitionBeanDConfig[0]));
   }

   void addAnnotationDefinitionBean(AnnotationDefinitionBeanDConfig value) {
      this.addToList(this.annotationDefinitionsDConfig, "AnnotationDefinitionBean", value);
   }

   void removeAnnotationDefinitionBean(AnnotationDefinitionBeanDConfig value) {
      this.removeFromList(this.annotationDefinitionsDConfig, "AnnotationDefinitionBean", value);
   }

   public EnumDefinitionBeanDConfig[] getEnumDefinitions() {
      return (EnumDefinitionBeanDConfig[])((EnumDefinitionBeanDConfig[])this.enumDefinitionsDConfig.toArray(new EnumDefinitionBeanDConfig[0]));
   }

   void addEnumDefinitionBean(EnumDefinitionBeanDConfig value) {
      this.addToList(this.enumDefinitionsDConfig, "EnumDefinitionBean", value);
   }

   void removeEnumDefinitionBean(EnumDefinitionBeanDConfig value) {
      this.removeFromList(this.enumDefinitionsDConfig, "EnumDefinitionBean", value);
   }

   public long getUpdateCount() {
      return this.beanTreeNode.getUpdateCount();
   }

   public void setUpdateCount(long value) {
      this.beanTreeNode.setUpdateCount(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "UpdateCount", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getVersion() {
      return this.beanTreeNode.getVersion();
   }

   public void setVersion(String value) {
      this.beanTreeNode.setVersion(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Version", (Object)null, (Object)null));
      this.setModified(true);
   }

   public DConfigBean[] getSecondaryDescriptors() {
      return super.getSecondaryDescriptors();
   }
}
