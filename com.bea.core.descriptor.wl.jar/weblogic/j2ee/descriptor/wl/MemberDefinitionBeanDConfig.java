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

public class MemberDefinitionBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private MemberDefinitionBean beanTreeNode;
   private EnumRefBeanDConfig enumRefDConfig;
   private SimpleTypeDefinitionBeanDConfig simpleTypeDefinitionDConfig;

   public MemberDefinitionBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (MemberDefinitionBean)btn;
      this.beanTree = btn;
      this.parent = (BasicDConfigBean)parent;
      this.initXpaths();
      this.customInit();
   }

   private void initXpaths() throws ConfigurationException {
      List xlist = new ArrayList();
      xlist.add(this.applyNamespace("enum-ref"));
      xlist.add(this.applyNamespace("simple-type-definition"));
      this.xpaths = (String[])((String[])xlist.toArray(new String[0]));
   }

   private void customInit() throws ConfigurationException {
      DDBean[] ddbs = this.getDDBean().getChildBean(this.applyNamespace("is-array"));
      if (ddbs != null && ddbs.length > 0) {
         this.beanTreeNode.setIsArray(Boolean.valueOf(ddbs[0].getText()));
         if (debug) {
            Debug.say("inited with IsArray with " + ddbs[0].getText());
         }
      }

      ddbs = this.getDDBean().getChildBean(this.applyNamespace("is-required"));
      if (ddbs != null && ddbs.length > 0) {
         this.beanTreeNode.setIsRequired(Boolean.valueOf(ddbs[0].getText()));
         if (debug) {
            Debug.say("inited with IsRequired with " + ddbs[0].getText());
         }
      }

      ddbs = this.getDDBean().getChildBean(this.applyNamespace("annotation-ref"));
      if (ddbs != null && ddbs.length > 0) {
         this.beanTreeNode.setAnnotationRef(ddbs[0].getText());
         if (debug) {
            Debug.say("inited with AnnotationRef with " + ddbs[0].getText());
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
         EnumRefBean btn = this.beanTreeNode.getEnumRef();
         if (btn == null) {
            if (debug) {
               Debug.say("creating new dcb element");
            }

            btn = this.beanTreeNode.createEnumRef();
            newDCB = true;
         }

         this.enumRefDConfig = new EnumRefBeanDConfig(ddb, (DescriptorBean)btn, parent);
         retBean = this.enumRefDConfig;
         if (!((BasicDConfigBean)retBean).hasCustomInit()) {
            ((BasicDConfigBean)retBean).setParentPropertyName("EnumRef");
         }
      } else if (this.lastElementOf(this.xpaths[xpathIndex++]).equals(this.lastElementOf(xpath))) {
         SimpleTypeDefinitionBean btn = this.beanTreeNode.getSimpleTypeDefinition();
         if (btn == null) {
            if (debug) {
               Debug.say("creating new dcb element");
            }

            btn = this.beanTreeNode.createSimpleTypeDefinition();
            newDCB = true;
         }

         this.simpleTypeDefinitionDConfig = new SimpleTypeDefinitionBeanDConfig(ddb, (DescriptorBean)btn, parent);
         retBean = this.simpleTypeDefinitionDConfig;
         if (!((BasicDConfigBean)retBean).hasCustomInit()) {
            ((BasicDConfigBean)retBean).setParentPropertyName("SimpleTypeDefinition");
         }
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

   public boolean getIsArray() {
      return this.beanTreeNode.getIsArray();
   }

   public void setIsArray(boolean value) {
      this.beanTreeNode.setIsArray(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "IsArray", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getIsRequired() {
      return this.beanTreeNode.getIsRequired();
   }

   public void setIsRequired(boolean value) {
      this.beanTreeNode.setIsRequired(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "IsRequired", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getAnnotationRef() {
      return this.beanTreeNode.getAnnotationRef();
   }

   public void setAnnotationRef(String value) {
      this.beanTreeNode.setAnnotationRef(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "AnnotationRef", (Object)null, (Object)null));
      this.setModified(true);
   }

   public EnumRefBeanDConfig getEnumRef() {
      return this.enumRefDConfig;
   }

   public void setEnumRef(EnumRefBeanDConfig value) {
      this.enumRefDConfig = value;
      this.firePropertyChange(new PropertyChangeEvent(this, "EnumRef", (Object)null, (Object)null));
      this.setModified(true);
   }

   public SimpleTypeDefinitionBeanDConfig getSimpleTypeDefinition() {
      return this.simpleTypeDefinitionDConfig;
   }

   public void setSimpleTypeDefinition(SimpleTypeDefinitionBeanDConfig value) {
      this.simpleTypeDefinitionDConfig = value;
      this.firePropertyChange(new PropertyChangeEvent(this, "SimpleTypeDefinition", (Object)null, (Object)null));
      this.setModified(true);
   }
}
