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

public class AnnotatedFieldBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private AnnotatedFieldBean beanTreeNode;
   private List annotationsDConfig = new ArrayList();

   public AnnotatedFieldBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (AnnotatedFieldBean)btn;
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
      DDBean[] ddbs = this.getDDBean().getChildBean(this.applyNamespace("instance-type"));
      if (ddbs != null && ddbs.length > 0) {
         this.beanTreeNode.setInstanceType(ddbs[0].getText());
         if (debug) {
            Debug.say("inited with InstanceType with " + ddbs[0].getText());
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
      return this.getFieldName();
   }

   public void initKeyPropertyValue(String value) {
      this.setFieldName(value);
   }

   public String getDCBProperties() {
      StringBuffer sb = new StringBuffer();
      sb.append("FieldName: ");
      sb.append(this.beanTreeNode.getFieldName());
      sb.append("\n");
      return sb.toString();
   }

   public void validate() throws ConfigurationException {
   }

   public String getFieldName() {
      return this.beanTreeNode.getFieldName();
   }

   public void setFieldName(String value) {
      this.beanTreeNode.setFieldName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "FieldName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getInstanceType() {
      return this.beanTreeNode.getInstanceType();
   }

   public void setInstanceType(String value) {
      this.beanTreeNode.setInstanceType(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "InstanceType", (Object)null, (Object)null));
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
}
