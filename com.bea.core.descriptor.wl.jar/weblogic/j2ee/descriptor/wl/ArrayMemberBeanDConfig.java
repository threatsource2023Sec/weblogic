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

public class ArrayMemberBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private ArrayMemberBean beanTreeNode;

   public ArrayMemberBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (ArrayMemberBean)btn;
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
      DDBean[] ddbs = this.getDDBean().getChildBean(this.applyNamespace("member-name"));
      if (ddbs != null && ddbs.length > 0) {
         this.beanTreeNode.setMemberName(ddbs[0].getText());
         if (debug) {
            Debug.say("inited with MemberName with " + ddbs[0].getText());
         }
      }

      ddbs = this.getDDBean().getChildBean(this.applyNamespace("member-value"));
      if (ddbs != null && ddbs.length > 0) {
         String[] s = new String[ddbs.length];

         for(int i = 0; i < ddbs.length; ++i) {
            s[i] = ddbs[i].getText();
         }

         this.beanTreeNode.setMemberValues(s);
      }

      ddbs = this.getDDBean().getChildBean(this.applyNamespace("requires-encryption"));
      if (ddbs != null && ddbs.length > 0) {
         this.beanTreeNode.setRequiresEncryption(Boolean.valueOf(ddbs[0].getText()));
         if (debug) {
            Debug.say("inited with RequiresEncryption with " + ddbs[0].getText());
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

   public String[] getMemberValues() {
      return this.beanTreeNode.getMemberValues();
   }

   public void setMemberValues(String[] value) {
      this.beanTreeNode.setMemberValues(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "MemberValues", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String[] getOverrideValues() {
      return this.beanTreeNode.getOverrideValues();
   }

   public void setOverrideValues(String[] value) {
      this.beanTreeNode.setOverrideValues(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "OverrideValues", (Object)null, (Object)null));
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

   public String[] getCleartextOverrideValues() {
      return this.beanTreeNode.getCleartextOverrideValues();
   }

   public void setCleartextOverrideValues(String[] value) {
      this.beanTreeNode.setCleartextOverrideValues(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "CleartextOverrideValues", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getSecuredOverrideValue() {
      return this.beanTreeNode.getSecuredOverrideValue();
   }

   public void setSecuredOverrideValue(String value) {
      this.beanTreeNode.setSecuredOverrideValue(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SecuredOverrideValue", (Object)null, (Object)null));
      this.setModified(true);
   }

   public byte[] getSecuredOverrideValueEncrypted() {
      return this.beanTreeNode.getSecuredOverrideValueEncrypted();
   }

   public void setSecuredOverrideValueEncrypted(byte[] value) {
      this.beanTreeNode.setSecuredOverrideValueEncrypted(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SecuredOverrideValueEncrypted", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getShortDescription() {
      return this.beanTreeNode.getShortDescription();
   }
}
