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

public class AnnotatedClassBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private AnnotatedClassBean beanTreeNode;
   private List annotationsDConfig = new ArrayList();
   private List fieldsDConfig = new ArrayList();
   private List methodsDConfig = new ArrayList();

   public AnnotatedClassBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (AnnotatedClassBean)btn;
      this.beanTree = btn;
      this.parent = (BasicDConfigBean)parent;
      this.initXpaths();
      this.customInit();
   }

   private void initXpaths() throws ConfigurationException {
      List xlist = new ArrayList();
      xlist.add(this.getParentXpath(this.applyNamespace("annotation/annotation-class-name")));
      xlist.add(this.getParentXpath(this.applyNamespace("field/field-name")));
      xlist.add(this.getParentXpath(this.applyNamespace("method/method-key")));
      this.xpaths = (String[])((String[])xlist.toArray(new String[0]));
   }

   private void customInit() throws ConfigurationException {
      DDBean[] ddbs = this.getDDBean().getChildBean(this.applyNamespace("annotated-class-name"));
      if (ddbs != null && ddbs.length > 0) {
         this.beanTreeNode.setAnnotatedClassName(ddbs[0].getText());
         if (debug) {
            Debug.say("inited with AnnotatedClassName with " + ddbs[0].getText());
         }
      }

      ddbs = this.getDDBean().getChildBean(this.applyNamespace("component-type"));
      if (ddbs != null && ddbs.length > 0) {
         this.beanTreeNode.setComponentType(ddbs[0].getText());
         if (debug) {
            Debug.say("inited with ComponentType with " + ddbs[0].getText());
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
         AnnotationInstanceBean btn = null;
         AnnotationInstanceBean[] list = this.beanTreeNode.getAnnotations();
         if (list == null) {
            this.beanTreeNode.createAnnotation();
            list = this.beanTreeNode.getAnnotations();
         }

         keyName = this.lastElementOf(this.applyNamespace("annotation/annotation-class-name"));
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

            btn = this.beanTreeNode.createAnnotation();
            newDCB = true;
         }

         retBean = new AnnotationInstanceBeanDConfig(ddb, (DescriptorBean)btn, parent);
         ((AnnotationInstanceBeanDConfig)retBean).initKeyPropertyValue(key);
         if (!((BasicDConfigBean)retBean).hasCustomInit()) {
            ((BasicDConfigBean)retBean).setParentPropertyName("Annotations");
         }

         if (debug) {
            Debug.say("dcb dump: " + ((BasicDConfigBean)retBean).toString());
         }

         this.annotationsDConfig.add(retBean);
      } else if (this.lastElementOf(this.xpaths[xpathIndex++]).equals(this.lastElementOf(xpath))) {
         AnnotatedFieldBean btn = null;
         AnnotatedFieldBean[] list = this.beanTreeNode.getFields();
         if (list == null) {
            this.beanTreeNode.createField();
            list = this.beanTreeNode.getFields();
         }

         keyName = this.lastElementOf(this.applyNamespace("field/field-name"));
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

            btn = this.beanTreeNode.createField();
            newDCB = true;
         }

         retBean = new AnnotatedFieldBeanDConfig(ddb, (DescriptorBean)btn, parent);
         ((AnnotatedFieldBeanDConfig)retBean).initKeyPropertyValue(key);
         if (!((BasicDConfigBean)retBean).hasCustomInit()) {
            ((BasicDConfigBean)retBean).setParentPropertyName("Fields");
         }

         if (debug) {
            Debug.say("dcb dump: " + ((BasicDConfigBean)retBean).toString());
         }

         this.fieldsDConfig.add(retBean);
      } else if (this.lastElementOf(this.xpaths[xpathIndex++]).equals(this.lastElementOf(xpath))) {
         AnnotatedMethodBean btn = null;
         AnnotatedMethodBean[] list = this.beanTreeNode.getMethods();
         if (list == null) {
            this.beanTreeNode.createMethod();
            list = this.beanTreeNode.getMethods();
         }

         keyName = this.lastElementOf(this.applyNamespace("method/method-key"));
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

            btn = this.beanTreeNode.createMethod();
            newDCB = true;
         }

         retBean = new AnnotatedMethodBeanDConfig(ddb, (DescriptorBean)btn, parent);
         ((AnnotatedMethodBeanDConfig)retBean).initKeyPropertyValue(key);
         if (!((BasicDConfigBean)retBean).hasCustomInit()) {
            ((BasicDConfigBean)retBean).setParentPropertyName("Methods");
         }

         if (debug) {
            Debug.say("dcb dump: " + ((BasicDConfigBean)retBean).toString());
         }

         this.methodsDConfig.add(retBean);
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
      return this.getAnnotatedClassName();
   }

   public void initKeyPropertyValue(String value) {
      this.setAnnotatedClassName(value);
   }

   public String getDCBProperties() {
      StringBuffer sb = new StringBuffer();
      sb.append("AnnotatedClassName: ");
      sb.append(this.beanTreeNode.getAnnotatedClassName());
      sb.append("\n");
      return sb.toString();
   }

   public void validate() throws ConfigurationException {
   }

   public String getAnnotatedClassName() {
      return this.beanTreeNode.getAnnotatedClassName();
   }

   public void setAnnotatedClassName(String value) {
      this.beanTreeNode.setAnnotatedClassName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "AnnotatedClassName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getComponentType() {
      return this.beanTreeNode.getComponentType();
   }

   public void setComponentType(String value) {
      this.beanTreeNode.setComponentType(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ComponentType", (Object)null, (Object)null));
      this.setModified(true);
   }

   public AnnotationInstanceBeanDConfig[] getAnnotations() {
      return (AnnotationInstanceBeanDConfig[])((AnnotationInstanceBeanDConfig[])this.annotationsDConfig.toArray(new AnnotationInstanceBeanDConfig[0]));
   }

   void addAnnotationInstanceBean(AnnotationInstanceBeanDConfig value) {
      this.addToList(this.annotationsDConfig, "AnnotationInstanceBean", value);
   }

   void removeAnnotationInstanceBean(AnnotationInstanceBeanDConfig value) {
      this.removeFromList(this.annotationsDConfig, "AnnotationInstanceBean", value);
   }

   public AnnotatedFieldBeanDConfig[] getFields() {
      return (AnnotatedFieldBeanDConfig[])((AnnotatedFieldBeanDConfig[])this.fieldsDConfig.toArray(new AnnotatedFieldBeanDConfig[0]));
   }

   void addAnnotatedFieldBean(AnnotatedFieldBeanDConfig value) {
      this.addToList(this.fieldsDConfig, "AnnotatedFieldBean", value);
   }

   void removeAnnotatedFieldBean(AnnotatedFieldBeanDConfig value) {
      this.removeFromList(this.fieldsDConfig, "AnnotatedFieldBean", value);
   }

   public AnnotatedMethodBeanDConfig[] getMethods() {
      return (AnnotatedMethodBeanDConfig[])((AnnotatedMethodBeanDConfig[])this.methodsDConfig.toArray(new AnnotatedMethodBeanDConfig[0]));
   }

   void addAnnotatedMethodBean(AnnotatedMethodBeanDConfig value) {
      this.addToList(this.methodsDConfig, "AnnotatedMethodBean", value);
   }

   void removeAnnotatedMethodBean(AnnotatedMethodBeanDConfig value) {
      this.removeFromList(this.methodsDConfig, "AnnotatedMethodBean", value);
   }

   public String getShortDescription() {
      return this.beanTreeNode.getShortDescription();
   }
}
