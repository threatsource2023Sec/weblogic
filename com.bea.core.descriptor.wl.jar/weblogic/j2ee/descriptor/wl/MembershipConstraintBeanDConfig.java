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

public class MembershipConstraintBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private MembershipConstraintBean beanTreeNode;

   public MembershipConstraintBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (MembershipConstraintBean)btn;
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
      DDBean[] ddbs = this.getDDBean().getChildBean(this.applyNamespace("membership-rule"));
      if (ddbs != null && ddbs.length > 0) {
         this.beanTreeNode.setMembershipRule(ddbs[0].getText());
         if (debug) {
            Debug.say("inited with MembershipRule with " + ddbs[0].getText());
         }
      }

      ddbs = this.getDDBean().getChildBean(this.applyNamespace("member-name"));
      if (ddbs != null && ddbs.length > 0) {
         String[] s = new String[ddbs.length];

         for(int i = 0; i < ddbs.length; ++i) {
            s[i] = ddbs[i].getText();
         }

         this.beanTreeNode.setMemberNames(s);
      }

   }

   public boolean hasCustomInit() {
      return true;
   }

   public DConfigBean createDConfigBean(DDBean ddb, DConfigBean parent) throws ConfigurationException {
      return null;
   }

   public String keyPropertyValue() {
      return this.getMembershipRule();
   }

   public void initKeyPropertyValue(String value) {
      this.setMembershipRule(value);
   }

   public String getDCBProperties() {
      StringBuffer sb = new StringBuffer();
      sb.append("MembershipRule: ");
      sb.append(this.beanTreeNode.getMembershipRule());
      sb.append("\n");
      return sb.toString();
   }

   public void validate() throws ConfigurationException {
   }

   public String getMembershipRule() {
      return this.beanTreeNode.getMembershipRule();
   }

   public void setMembershipRule(String value) {
      this.beanTreeNode.setMembershipRule(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "MembershipRule", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String[] getMemberNames() {
      return this.beanTreeNode.getMemberNames();
   }

   public void setMemberNames(String[] value) {
      this.beanTreeNode.setMemberNames(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "MemberNames", (Object)null, (Object)null));
      this.setModified(true);
   }
}
