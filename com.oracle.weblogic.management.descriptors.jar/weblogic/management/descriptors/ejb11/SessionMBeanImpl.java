package weblogic.management.descriptors.ejb11;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class SessionMBeanImpl extends XMLElementMBeanDelegate implements SessionMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_ejbRefs = false;
   private List ejbRefs;
   private boolean isSet_sessionType = false;
   private String sessionType;
   private boolean isSet_transactionType = false;
   private String transactionType;
   private boolean isSet_description = false;
   private String description;
   private boolean isSet_remote = false;
   private String remote;
   private boolean isSet_largeIcon = false;
   private String largeIcon;
   private boolean isSet_smallIcon = false;
   private String smallIcon;
   private boolean isSet_resourceRefs = false;
   private List resourceRefs;
   private boolean isSet_ejbName = false;
   private String ejbName;
   private boolean isSet_securityRoleRefs = false;
   private List securityRoleRefs;
   private boolean isSet_displayName = false;
   private String displayName;
   private boolean isSet_ejbClass = false;
   private String ejbClass;
   private boolean isSet_envEntries = false;
   private List envEntries;
   private boolean isSet_home = false;
   private String home;

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

   public String getSessionType() {
      return this.sessionType;
   }

   public void setSessionType(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.sessionType;
      this.sessionType = value;
      this.isSet_sessionType = value != null;
      this.checkChange("sessionType", old, this.sessionType);
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

   public String getRemote() {
      return this.remote;
   }

   public void setRemote(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.remote;
      this.remote = value;
      this.isSet_remote = value != null;
      this.checkChange("remote", old, this.remote);
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

   public ResourceRefMBean[] getResourceRefs() {
      if (this.resourceRefs == null) {
         return new ResourceRefMBean[0];
      } else {
         ResourceRefMBean[] result = new ResourceRefMBean[this.resourceRefs.size()];
         result = (ResourceRefMBean[])((ResourceRefMBean[])this.resourceRefs.toArray(result));
         return result;
      }
   }

   public void setResourceRefs(ResourceRefMBean[] value) {
      ResourceRefMBean[] _oldVal = null;
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

   public void addResourceRef(ResourceRefMBean value) {
      this.isSet_resourceRefs = true;
      if (this.resourceRefs == null) {
         this.resourceRefs = Collections.synchronizedList(new ArrayList());
      }

      this.resourceRefs.add(value);
   }

   public void removeResourceRef(ResourceRefMBean value) {
      if (this.resourceRefs != null) {
         this.resourceRefs.remove(value);
      }
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

   public SecurityRoleRefMBean[] getSecurityRoleRefs() {
      if (this.securityRoleRefs == null) {
         return new SecurityRoleRefMBean[0];
      } else {
         SecurityRoleRefMBean[] result = new SecurityRoleRefMBean[this.securityRoleRefs.size()];
         result = (SecurityRoleRefMBean[])((SecurityRoleRefMBean[])this.securityRoleRefs.toArray(result));
         return result;
      }
   }

   public void setSecurityRoleRefs(SecurityRoleRefMBean[] value) {
      SecurityRoleRefMBean[] _oldVal = null;
      if (this.changeSupport != null) {
         _oldVal = this.getSecurityRoleRefs();
      }

      this.isSet_securityRoleRefs = true;
      if (this.securityRoleRefs == null) {
         this.securityRoleRefs = Collections.synchronizedList(new ArrayList());
      } else {
         this.securityRoleRefs.clear();
      }

      if (null != value) {
         for(int i = 0; i < value.length; ++i) {
            this.securityRoleRefs.add(value[i]);
         }
      }

      if (this.changeSupport != null) {
         this.checkChange("SecurityRoleRefs", _oldVal, this.getSecurityRoleRefs());
      }

   }

   public void addSecurityRoleRef(SecurityRoleRefMBean value) {
      this.isSet_securityRoleRefs = true;
      if (this.securityRoleRefs == null) {
         this.securityRoleRefs = Collections.synchronizedList(new ArrayList());
      }

      this.securityRoleRefs.add(value);
   }

   public void removeSecurityRoleRef(SecurityRoleRefMBean value) {
      if (this.securityRoleRefs != null) {
         this.securityRoleRefs.remove(value);
      }
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

   public String getHome() {
      return this.home;
   }

   public void setHome(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.home;
      this.home = value;
      this.isSet_home = value != null;
      this.checkChange("home", old, this.home);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<session");
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

      if (null != this.getHome()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<home>").append(this.getHome()).append("</home>\n");
      }

      if (null != this.getRemote()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<remote>").append(this.getRemote()).append("</remote>\n");
      }

      if (null != this.getEJBClass()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<ejb-class>").append(this.getEJBClass()).append("</ejb-class>\n");
      }

      if (null != this.getSessionType()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<session-type>").append(this.getSessionType()).append("</session-type>\n");
      }

      if (null != this.getTransactionType()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<transaction-type>").append(this.getTransactionType()).append("</transaction-type>\n");
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

      if (null != this.getSecurityRoleRefs()) {
         for(i = 0; i < this.getSecurityRoleRefs().length; ++i) {
            result.append(this.getSecurityRoleRefs()[i].toXML(indentLevel + 2));
         }
      }

      if (null != this.getResourceRefs()) {
         for(i = 0; i < this.getResourceRefs().length; ++i) {
            result.append(this.getResourceRefs()[i].toXML(indentLevel + 2));
         }
      }

      result.append(ToXML.indent(indentLevel)).append("</session>\n");
      return result.toString();
   }
}
