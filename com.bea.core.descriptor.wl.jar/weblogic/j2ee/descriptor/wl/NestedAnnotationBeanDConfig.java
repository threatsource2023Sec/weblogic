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

public class NestedAnnotationBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private NestedAnnotationBean beanTreeNode;
   private AnnotationInstanceBeanDConfig annotationDConfig;

   public NestedAnnotationBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (NestedAnnotationBean)btn;
      this.beanTree = btn;
      this.parent = (BasicDConfigBean)parent;
      this.initXpaths();
      this.customInit();
   }

   private void initXpaths() throws ConfigurationException {
      List xlist = new ArrayList();
      xlist.add(this.applyNamespace("annotation"));
      this.xpaths = (String[])((String[])xlist.toArray(new String[0]));
   }

   private void customInit() throws ConfigurationException {
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
         AnnotationInstanceBean btn = this.beanTreeNode.getAnnotation();
         if (btn == null) {
            if (debug) {
               Debug.say("creating new dcb element");
            }

            btn = this.beanTreeNode.createAnnotation();
            newDCB = true;
         }

         this.annotationDConfig = new AnnotationInstanceBeanDConfig(ddb, (DescriptorBean)btn, parent);
         retBean = this.annotationDConfig;
         if (!retBean.hasCustomInit()) {
            retBean.setParentPropertyName("Annotation");
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
      return this.getMemberName();
   }

   public void initKeyPropertyValue(String value) {
      this.setMemberName(value);
   }

   public String getDCBProperties() {
      StringBuffer sb = new StringBuffer();
      sb.append("MemberName: ");
      sb.append(this.beanTreeNode.getMemberName());
      sb.append("\n");
      return sb.toString();
   }

   public void validate() throws ConfigurationException {
   }

   public String getMemberName() {
      return this.beanTreeNode.getMemberName();
   }

   public void setMemberName(String value) {
      this.beanTreeNode.setMemberName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "MemberName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public AnnotationInstanceBeanDConfig getAnnotation() {
      return this.annotationDConfig;
   }

   public void setAnnotation(AnnotationInstanceBeanDConfig value) {
      this.annotationDConfig = value;
      this.firePropertyChange(new PropertyChangeEvent(this, "Annotation", (Object)null, (Object)null));
      this.setModified(true);
   }
}
