package weblogic.management.descriptors.ejb20;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.descriptors.ejb11.EJBRefMBean;
import weblogic.management.descriptors.ejb11.EnvEntryMBean;
import weblogic.management.tools.ToXML;

public class MessageDrivenMBeanImpl extends XMLElementMBeanDelegate implements MessageDrivenMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_ejbRefs = false;
   private List ejbRefs;
   private boolean isSet_transactionType = false;
   private String transactionType;
   private boolean isSet_acknowledgeMode = false;
   private String acknowledgeMode;
   private boolean isSet_description = false;
   private String description;
   private boolean isSet_resourceEnvRefs = false;
   private List resourceEnvRefs;
   private boolean isSet_messageDrivenDestination = false;
   private MessageDrivenDestinationMBean messageDrivenDestination;
   private boolean isSet_ejbLocalRefs = false;
   private List ejbLocalRefs;
   private boolean isSet_largeIcon = false;
   private String largeIcon;
   private boolean isSet_subscriptionDurability = false;
   private String subscriptionDurability;
   private boolean isSet_smallIcon = false;
   private String smallIcon;
   private boolean isSet_resourceRefs = false;
   private List resourceRefs;
   private boolean isSet_securityIdentity = false;
   private SecurityIdentityMBean securityIdentity;
   private boolean isSet_ejbName = false;
   private String ejbName;
   private boolean isSet_displayName = false;
   private String displayName;
   private boolean isSet_ejbClass = false;
   private String ejbClass;
   private boolean isSet_envEntries = false;
   private List envEntries;
   private boolean isSet_messageSelector = false;
   private String messageSelector;

   public EJBRefMBean[] getEJBRefs() {
      if (this.ejbRefs == null) {
         return new EJBRefMBean[0];
      } else {
         EJBRefMBean[] result = new EJBRefMBean[this.ejbRefs.size()];
         result = (EJBRefMBean[])((EJBRefMBean[])this.ejbRefs.toArray(result));
         return result;
      }
   }

   public void setEJBRefs(EJBRefMBean[] value) {
      EJBRefMBean[] _oldVal = null;
      if (this.changeSupport != null) {
         _oldVal = this.getEJBRefs();
      }

      this.isSet_ejbRefs = true;
      if (this.ejbRefs == null) {
         this.ejbRefs = Collections.synchronizedList(new ArrayList());
      } else {
         this.ejbRefs.clear();
      }

      if (null != value) {
         for(int i = 0; i < value.length; ++i) {
            this.ejbRefs.add(value[i]);
         }
      }

      if (this.changeSupport != null) {
         this.checkChange("EJBRefs", _oldVal, this.getEJBRefs());
      }

   }

   public void addEJBRef(EJBRefMBean value) {
      this.isSet_ejbRefs = true;
      if (this.ejbRefs == null) {
         this.ejbRefs = Collections.synchronizedList(new ArrayList());
      }

      this.ejbRefs.add(value);
   }

   public void removeEJBRef(EJBRefMBean value) {
      if (this.ejbRefs != null) {
         this.ejbRefs.remove(value);
      }
   }

   public String getTransactionType() {
      return this.transactionType;
   }

   public void setTransactionType(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.transactionType;
      this.transactionType = value;
      this.isSet_transactionType = value != null;
      this.checkChange("transactionType", old, this.transactionType);
   }

   public String getAcknowledgeMode() {
      return this.acknowledgeMode;
   }

   public void setAcknowledgeMode(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.acknowledgeMode;
      this.acknowledgeMode = value;
      this.isSet_acknowledgeMode = value != null;
      this.checkChange("acknowledgeMode", old, this.acknowledgeMode);
   }

   public String getDescription() {
      return this.description;
   }

   public void setDescription(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.description;
      this.description = value;
      this.isSet_description = value != null;
      this.checkChange("description", old, this.description);
   }

   public ResourceEnvRefMBean[] getResourceEnvRefs() {
      if (this.resourceEnvRefs == null) {
         return new ResourceEnvRefMBean[0];
      } else {
         ResourceEnvRefMBean[] result = new ResourceEnvRefMBean[this.resourceEnvRefs.size()];
         result = (ResourceEnvRefMBean[])((ResourceEnvRefMBean[])this.resourceEnvRefs.toArray(result));
         return result;
      }
   }

   public void setResourceEnvRefs(ResourceEnvRefMBean[] value) {
      ResourceEnvRefMBean[] _oldVal = null;
      if (this.changeSupport != null) {
         _oldVal = this.getResourceEnvRefs();
      }

      this.isSet_resourceEnvRefs = true;
      if (this.resourceEnvRefs == null) {
         this.resourceEnvRefs = Collections.synchronizedList(new ArrayList());
      } else {
         this.resourceEnvRefs.clear();
      }

      if (null != value) {
         for(int i = 0; i < value.length; ++i) {
            this.resourceEnvRefs.add(value[i]);
         }
      }

      if (this.changeSupport != null) {
         this.checkChange("ResourceEnvRefs", _oldVal, this.getResourceEnvRefs());
      }

   }

   public void addResourceEnvRef(ResourceEnvRefMBean value) {
      this.isSet_resourceEnvRefs = true;
      if (this.resourceEnvRefs == null) {
         this.resourceEnvRefs = Collections.synchronizedList(new ArrayList());
      }

      this.resourceEnvRefs.add(value);
   }

   public void removeResourceEnvRef(ResourceEnvRefMBean value) {
      if (this.resourceEnvRefs != null) {
         this.resourceEnvRefs.remove(value);
      }
   }

   public MessageDrivenDestinationMBean getMessageDrivenDestination() {
      return this.messageDrivenDestination;
   }

   public void setMessageDrivenDestination(MessageDrivenDestinationMBean value) {
      MessageDrivenDestinationMBean old = this.messageDrivenDestination;
      this.messageDrivenDestination = value;
      this.isSet_messageDrivenDestination = value != null;
      this.checkChange("messageDrivenDestination", old, this.messageDrivenDestination);
   }

   public EJBLocalRefMBean[] getEJBLocalRefs() {
      if (this.ejbLocalRefs == null) {
         return new EJBLocalRefMBean[0];
      } else {
         EJBLocalRefMBean[] result = new EJBLocalRefMBean[this.ejbLocalRefs.size()];
         result = (EJBLocalRefMBean[])((EJBLocalRefMBean[])this.ejbLocalRefs.toArray(result));
         return result;
      }
   }

   public void setEJBLocalRefs(EJBLocalRefMBean[] value) {
      EJBLocalRefMBean[] _oldVal = null;
      if (this.changeSupport != null) {
         _oldVal = this.getEJBLocalRefs();
      }

      this.isSet_ejbLocalRefs = true;
      if (this.ejbLocalRefs == null) {
         this.ejbLocalRefs = Collections.synchronizedList(new ArrayList());
      } else {
         this.ejbLocalRefs.clear();
      }

      if (null != value) {
         for(int i = 0; i < value.length; ++i) {
            this.ejbLocalRefs.add(value[i]);
         }
      }

      if (this.changeSupport != null) {
         this.checkChange("EJBLocalRefs", _oldVal, this.getEJBLocalRefs());
      }

   }

   public void addEJBLocalRef(EJBLocalRefMBean value) {
      this.isSet_ejbLocalRefs = true;
      if (this.ejbLocalRefs == null) {
         this.ejbLocalRefs = Collections.synchronizedList(new ArrayList());
      }

      this.ejbLocalRefs.add(value);
   }

   public void removeEJBLocalRef(EJBLocalRefMBean value) {
      if (this.ejbLocalRefs != null) {
         this.ejbLocalRefs.remove(value);
      }
   }

   public String getLargeIcon() {
      return this.largeIcon;
   }

   public void setLargeIcon(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.largeIcon;
      this.largeIcon = value;
      this.isSet_largeIcon = value != null;
      this.checkChange("largeIcon", old, this.largeIcon);
   }

   public String getSubscriptionDurability() {
      return this.subscriptionDurability;
   }

   public void setSubscriptionDurability(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.subscriptionDurability;
      this.subscriptionDurability = value;
      this.isSet_subscriptionDurability = value != null;
      this.checkChange("subscriptionDurability", old, this.subscriptionDurability);
   }

   public String getSmallIcon() {
      return this.smallIcon;
   }

   public void setSmallIcon(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.smallIcon;
      this.smallIcon = value;
      this.isSet_smallIcon = value != null;
      this.checkChange("smallIcon", old, this.smallIcon);
   }

   public weblogic.management.descriptors.ejb11.ResourceRefMBean[] getResourceRefs() {
      if (this.resourceRefs == null) {
         return new weblogic.management.descriptors.ejb11.ResourceRefMBean[0];
      } else {
         weblogic.management.descriptors.ejb11.ResourceRefMBean[] result = new weblogic.management.descriptors.ejb11.ResourceRefMBean[this.resourceRefs.size()];
         result = (weblogic.management.descriptors.ejb11.ResourceRefMBean[])((weblogic.management.descriptors.ejb11.ResourceRefMBean[])this.resourceRefs.toArray(result));
         return result;
      }
   }

   public void setResourceRefs(weblogic.management.descriptors.ejb11.ResourceRefMBean[] value) {
      weblogic.management.descriptors.ejb11.ResourceRefMBean[] _oldVal = null;
      if (this.changeSupport != null) {
         _oldVal = this.getResourceRefs();
      }

      this.isSet_resourceRefs = true;
      if (this.resourceRefs == null) {
         this.resourceRefs = Collections.synchronizedList(new ArrayList());
      } else {
         this.resourceRefs.clear();
      }

      if (null != value) {
         for(int i = 0; i < value.length; ++i) {
            this.resourceRefs.add(value[i]);
         }
      }

      if (this.changeSupport != null) {
         this.checkChange("ResourceRefs", _oldVal, this.getResourceRefs());
      }

   }

   public void addResourceRef(weblogic.management.descriptors.ejb11.ResourceRefMBean value) {
      this.isSet_resourceRefs = true;
      if (this.resourceRefs == null) {
         this.resourceRefs = Collections.synchronizedList(new ArrayList());
      }

      this.resourceRefs.add(value);
   }

   public void removeResourceRef(weblogic.management.descriptors.ejb11.ResourceRefMBean value) {
      if (this.resourceRefs != null) {
         this.resourceRefs.remove(value);
      }
   }

   public SecurityIdentityMBean getSecurityIdentity() {
      return this.securityIdentity;
   }

   public void setSecurityIdentity(SecurityIdentityMBean value) {
      SecurityIdentityMBean old = this.securityIdentity;
      this.securityIdentity = value;
      this.isSet_securityIdentity = value != null;
      this.checkChange("securityIdentity", old, this.securityIdentity);
   }

   public String getEJBName() {
      return this.ejbName;
   }

   public void setEJBName(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.ejbName;
      this.ejbName = value;
      this.isSet_ejbName = value != null;
      this.checkChange("ejbName", old, this.ejbName);
   }

   public String getDisplayName() {
      return this.displayName;
   }

   public void setDisplayName(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.displayName;
      this.displayName = value;
      this.isSet_displayName = value != null;
      this.checkChange("displayName", old, this.displayName);
   }

   public String getEJBClass() {
      return this.ejbClass;
   }

   public void setEJBClass(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.ejbClass;
      this.ejbClass = value;
      this.isSet_ejbClass = value != null;
      this.checkChange("ejbClass", old, this.ejbClass);
   }

   public EnvEntryMBean[] getEnvEntries() {
      if (this.envEntries == null) {
         return new EnvEntryMBean[0];
      } else {
         EnvEntryMBean[] result = new EnvEntryMBean[this.envEntries.size()];
         result = (EnvEntryMBean[])((EnvEntryMBean[])this.envEntries.toArray(result));
         return result;
      }
   }

   public void setEnvEntries(EnvEntryMBean[] value) {
      EnvEntryMBean[] _oldVal = null;
      if (this.changeSupport != null) {
         _oldVal = this.getEnvEntries();
      }

      this.isSet_envEntries = true;
      if (this.envEntries == null) {
         this.envEntries = Collections.synchronizedList(new ArrayList());
      } else {
         this.envEntries.clear();
      }

      if (null != value) {
         for(int i = 0; i < value.length; ++i) {
            this.envEntries.add(value[i]);
         }
      }

      if (this.changeSupport != null) {
         this.checkChange("EnvEntries", _oldVal, this.getEnvEntries());
      }

   }

   public void addEnvEntry(EnvEntryMBean value) {
      this.isSet_envEntries = true;
      if (this.envEntries == null) {
         this.envEntries = Collections.synchronizedList(new ArrayList());
      }

      this.envEntries.add(value);
   }

   public void removeEnvEntry(EnvEntryMBean value) {
      if (this.envEntries != null) {
         this.envEntries.remove(value);
      }
   }

   public String getMessageSelector() {
      return this.messageSelector;
   }

   public void setMessageSelector(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.messageSelector;
      this.messageSelector = value;
      this.isSet_messageSelector = value != null;
      this.checkChange("messageSelector", old, this.messageSelector);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<message-driven");
      result.append(">\n");
      if (null != this.getDescription()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<description>").append("<![CDATA[" + this.getDescription() + "]]>").append("</description>\n");
      }

      if (null != this.getDisplayName()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<display-name>").append(this.getDisplayName()).append("</display-name>\n");
      }

      if (null != this.getSmallIcon()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<small-icon>").append(this.getSmallIcon()).append("</small-icon>\n");
      }

      if (null != this.getLargeIcon()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<large-icon>").append(this.getLargeIcon()).append("</large-icon>\n");
      }

      if (null != this.getEJBName()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<ejb-name>").append(this.getEJBName()).append("</ejb-name>\n");
      }

      if (null != this.getEJBClass()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<ejb-class>").append(this.getEJBClass()).append("</ejb-class>\n");
      }

      if (null != this.getTransactionType()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<transaction-type>").append(this.getTransactionType()).append("</transaction-type>\n");
      }

      if (null != this.getMessageSelector()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<message-selector>").append(this.getMessageSelector()).append("</message-selector>\n");
      }

      if (null != this.getAcknowledgeMode()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<acknowledge-mode>").append(this.getAcknowledgeMode()).append("</acknowledge-mode>\n");
      }

      if (null != this.getMessageDrivenDestination()) {
         result.append(this.getMessageDrivenDestination().toXML(indentLevel + 2)).append("\n");
      }

      int i;
      if (null != this.getEnvEntries()) {
         for(i = 0; i < this.getEnvEntries().length; ++i) {
            result.append(this.getEnvEntries()[i].toXML(indentLevel + 2));
         }
      }

      if (null != this.getEJBRefs()) {
         for(i = 0; i < this.getEJBRefs().length; ++i) {
            result.append(this.getEJBRefs()[i].toXML(indentLevel + 2));
         }
      }

      if (null != this.getEJBLocalRefs()) {
         for(i = 0; i < this.getEJBLocalRefs().length; ++i) {
            result.append(this.getEJBLocalRefs()[i].toXML(indentLevel + 2));
         }
      }

      if (null != this.getSecurityIdentity()) {
         result.append(this.getSecurityIdentity().toXML(indentLevel + 2)).append("\n");
      }

      if (null != this.getResourceRefs()) {
         for(i = 0; i < this.getResourceRefs().length; ++i) {
            result.append(this.getResourceRefs()[i].toXML(indentLevel + 2));
         }
      }

      if (null != this.getResourceEnvRefs()) {
         for(i = 0; i < this.getResourceEnvRefs().length; ++i) {
            result.append(this.getResourceEnvRefs()[i].toXML(indentLevel + 2));
         }
      }

      result.append(ToXML.indent(indentLevel)).append("</message-driven>\n");
      return result.toString();
   }
}
