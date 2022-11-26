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

public class AnnotatedMethodBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private AnnotatedMethodBean beanTreeNode;
   private List annotationsDConfig = new ArrayList();

   public AnnotatedMethodBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (AnnotatedMethodBean)btn;
      this.beanTree = btn;
      this.parent = (BasicDConfigBean)parent;
      this.initXpaths();
      this.customInit();
   }

   private void initXpaths() throws ConfigurationException {
      List xlist = new ArrayList();
      xlist.add(this.getParentXpath(this.applyNamespace("annotation/annotation-class-name")));
      this.xpaths = (String[])((String[])xlist.toArray(new String[0]));
   }

   private void customInit() throws ConfigurationException {
      DDBean[] ddbs = this.getDDBean().getChildBean(this.applyNamespace("method-name"));
      if (ddbs != null && ddbs.length > 0) {
         this.beanTreeNode.setMethodName(ddbs[0].getText());
         if (debug) {
            Debug.say("inited with MethodName with " + ddbs[0].getText());
         }
      }

      ddbs = this.getDDBean().getChildBean(this.applyNamespace("paramter-type"));
      if (ddbs != null && ddbs.length > 0) {
         String[] s = new String[ddbs.length];

         for(int i = 0; i < ddbs.length; ++i) {
            s[i] = ddbs[i].getText();
         }

         this.beanTreeNode.setParamterTypes(s);
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
         AnnotationInstanceBean btn = null;
         AnnotationInstanceBean[] list = this.beanTreeNode.getAnnotations();
         if (list == null) {
            this.beanTreeNode.createAnnotation();
            list = this.beanTreeNode.getAnnotations();
         }

         String keyName = this.lastElementOf(this.applyNamespace("annotation/annotation-class-name"));
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

            btn = this.beanTreeNode.createAnnotation();
            newDCB = true;
         }

         retBean = new AnnotationInstanceBeanDConfig(ddb, (DescriptorBean)btn, parent);
         ((AnnotationInstanceBeanDConfig)retBean).initKeyPropertyValue(key);
         if (!retBean.hasCustomInit()) {
            retBean.setParentPropertyName("Annotations");
         }

         if (debug) {
            Debug.say("dcb dump: " + retBean.toString());
         }

         this.annotationsDConfig.add(retBean);
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
      return this.getMethodKey();
   }

   public void initKeyPropertyValue(String value) {
      this.setMethodKey(value);
   }

   public String getDCBProperties() {
      StringBuffer sb = new StringBuffer();
      sb.append("MethodKey: ");
      sb.append(this.beanTreeNode.getMethodKey());
      sb.append("\n");
      return sb.toString();
   }

   public void validate() throws ConfigurationException {
   }

   public String getMethodKey() {
      return this.beanTreeNode.getMethodKey();
   }

   public void setMethodKey(String value) {
      this.beanTreeNode.setMethodKey(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "MethodKey", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getMethodName() {
      return this.beanTreeNode.getMethodName();
   }

   public void setMethodName(String value) {
      this.beanTreeNode.setMethodName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "MethodName", (Object)null, (Object)null));
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

   public String[] getParamterTypes() {
      return this.beanTreeNode.getParamterTypes();
   }

   public void setParamterTypes(String[] value) {
      this.beanTreeNode.setParamterTypes(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ParamterTypes", (Object)null, (Object)null));
      this.setModified(true);
   }
}
