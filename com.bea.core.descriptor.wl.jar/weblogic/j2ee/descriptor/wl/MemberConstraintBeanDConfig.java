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

public class MemberConstraintBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private MemberConstraintBean beanTreeNode;

   public MemberConstraintBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (MemberConstraintBean)btn;
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
      DDBean[] ddbs = this.getDDBean().getChildBean(this.applyNamespace("constraint-type"));
      if (ddbs != null && ddbs.length > 0) {
         this.beanTreeNode.setConstraintType(ddbs[0].getText());
         if (debug) {
            Debug.say("inited with ConstraintType with " + ddbs[0].getText());
         }
      }

      ddbs = this.getDDBean().getChildBean(this.applyNamespace("max-length"));
      if (ddbs != null && ddbs.length > 0) {
         this.beanTreeNode.setMaxLength(ddbs[0].getText());
         if (debug) {
            Debug.say("inited with MaxLength with " + ddbs[0].getText());
         }
      }

      ddbs = this.getDDBean().getChildBean(this.applyNamespace("min-value"));
      if (ddbs != null && ddbs.length > 0) {
         this.beanTreeNode.setMinValue(ddbs[0].getText());
         if (debug) {
            Debug.say("inited with MinValue with " + ddbs[0].getText());
         }
      }

      ddbs = this.getDDBean().getChildBean(this.applyNamespace("max-value"));
      if (ddbs != null && ddbs.length > 0) {
         this.beanTreeNode.setMaxValue(ddbs[0].getText());
         if (debug) {
            Debug.say("inited with MaxValue with " + ddbs[0].getText());
         }
      }

      ddbs = this.getDDBean().getChildBean(this.applyNamespace("scale"));
      if (ddbs != null && ddbs.length > 0) {
         this.beanTreeNode.setScale(Integer.valueOf(ddbs[0].getText()));
         if (debug) {
            Debug.say("inited with Scale with " + ddbs[0].getText());
         }
      }

   }

   public boolean hasCustomInit() {
      return true;
   }

   public DConfigBean createDConfigBean(DDBean ddb, DConfigBean parent) throws ConfigurationException {
      return null;
   }

   public String keyPropertyValue() {
      return this.getConstraintType();
   }

   public void initKeyPropertyValue(String value) {
      this.setConstraintType(value);
   }

   public String getDCBProperties() {
      StringBuffer sb = new StringBuffer();
      sb.append("ConstraintType: ");
      sb.append(this.beanTreeNode.getConstraintType());
      sb.append("\n");
      return sb.toString();
   }

   public void validate() throws ConfigurationException {
   }

   public String getConstraintType() {
      return this.beanTreeNode.getConstraintType();
   }

   public void setConstraintType(String value) {
      this.beanTreeNode.setConstraintType(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ConstraintType", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getMaxLength() {
      return this.beanTreeNode.getMaxLength();
   }

   public void setMaxLength(String value) {
      this.beanTreeNode.setMaxLength(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "MaxLength", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getMinValue() {
      return this.beanTreeNode.getMinValue();
   }

   public void setMinValue(String value) {
      this.beanTreeNode.setMinValue(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "MinValue", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getMaxValue() {
      return this.beanTreeNode.getMaxValue();
   }

   public void setMaxValue(String value) {
      this.beanTreeNode.setMaxValue(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "MaxValue", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getScale() {
      return this.beanTreeNode.getScale();
   }

   public void setScale(int value) {
      this.beanTreeNode.setScale(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Scale", (Object)null, (Object)null));
      this.setModified(true);
   }
}
