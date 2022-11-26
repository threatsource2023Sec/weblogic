package weblogic.management.descriptors.ejb20;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.descriptors.ejb11.CMPFieldMBean;
import weblogic.management.descriptors.ejb11.EJBRefMBean;
import weblogic.management.descriptors.ejb11.EnvEntryMBean;
import weblogic.management.descriptors.ejb11.SecurityRoleRefMBean;
import weblogic.management.tools.ToXML;

public class EntityMBeanImpl extends XMLElementMBeanDelegate implements EntityMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_ejbRefs = false;
   private List ejbRefs;
   private boolean isSet_queries = false;
   private List queries;
   private boolean isSet_description = false;
   private String description;
   private boolean isSet_ejbLocalRefs = false;
   private List ejbLocalRefs;
   private boolean isSet_reentrant = false;
   private boolean reentrant;
   private boolean isSet_remote = false;
   private String remote;
   private boolean isSet_cmpVersion = false;
   private String cmpVersion = "2.x";
   private boolean isSet_securityRoleRefs = false;
   private List securityRoleRefs;
   private boolean isSet_displayName = false;
   private String displayName;
   private boolean isSet_envEntries = false;
   private List envEntries;
   private boolean isSet_localHome = false;
   private String localHome;
   private boolean isSet_home = false;
   private String home;
   private boolean isSet_resourceEnvRefs = false;
   private List resourceEnvRefs;
   private boolean isSet_local = false;
   private String local;
   private boolean isSet_persistenceType = false;
   private String persistenceType;
   private boolean isSet_largeIcon = false;
   private String largeIcon;
   private boolean isSet_smallIcon = false;
   private String smallIcon;
   private boolean isSet_resourceRefs = false;
   private List resourceRefs;
   private boolean isSet_securityIdentity = false;
   private SecurityIdentityMBean securityIdentity;
   private boolean isSet_ejbName = false;
   private String ejbName;
   private boolean isSet_primkeyField = false;
   private String primkeyField;
   private boolean isSet_primKeyClass = false;
   private String primKeyClass;
   private boolean isSet_ejbClass = false;
   private String ejbClass;
   private boolean isSet_abstractSchemaName = false;
   private String abstractSchemaName;
   private boolean isSet_cmpFields = false;
   private List cmpFields;

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

   public QueryMBean[] getQueries() {
      if (this.queries == null) {
         return new QueryMBean[0];
      } else {
         QueryMBean[] result = new QueryMBean[this.queries.size()];
         result = (QueryMBean[])((QueryMBean[])this.queries.toArray(result));
         return result;
      }
   }

   public void setQueries(QueryMBean[] value) {
      QueryMBean[] _oldVal = null;
      if (this.changeSupport != null) {
         _oldVal = this.getQueries();
      }

      this.isSet_queries = true;
      if (this.queries == null) {
         this.queries = Collections.synchronizedList(new ArrayList());
      } else {
         this.queries.clear();
      }

      if (null != value) {
         for(int i = 0; i < value.length; ++i) {
            this.queries.add(value[i]);
         }
      }

      if (this.changeSupport != null) {
         this.checkChange("Queries", _oldVal, this.getQueries());
      }

   }

   public void addQuery(QueryMBean value) {
      this.isSet_queries = true;
      if (this.queries == null) {
         this.queries = Collections.synchronizedList(new ArrayList());
      }

      this.queries.add(value);
   }

   public void removeQuery(QueryMBean value) {
      if (this.queries != null) {
         this.queries.remove(value);
      }
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

   public boolean isReentrant() {
      return this.reentrant;
   }

   public void setReentrant(boolean value) {
      boolean old = this.reentrant;
      this.reentrant = value;
      this.isSet_reentrant = true;
      this.checkChange("reentrant", old, this.reentrant);
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

   public String getCMPVersion() {
      return this.cmpVersion;
   }

   public void setCMPVersion(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.cmpVersion;
      this.cmpVersion = value;
      this.isSet_cmpVersion = value != null;
      this.checkChange("cmpVersion", old, this.cmpVersion);
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

   public String getLocalHome() {
      return this.localHome;
   }

   public void setLocalHome(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.localHome;
      this.localHome = value;
      this.isSet_localHome = value != null;
      this.checkChange("localHome", old, this.localHome);
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

   public String getLocal() {
      return this.local;
   }

   public void setLocal(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.local;
      this.local = value;
      this.isSet_local = value != null;
      this.checkChange("local", old, this.local);
   }

   public String getPersistenceType() {
      return this.persistenceType;
   }

   public void setPersistenceType(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.persistenceType;
      this.persistenceType = value;
      this.isSet_persistenceType = value != null;
      this.checkChange("persistenceType", old, this.persistenceType);
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

   public String getPrimkeyField() {
      return this.primkeyField;
   }

   public void setPrimkeyField(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.primkeyField;
      this.primkeyField = value;
      this.isSet_primkeyField = value != null;
      this.checkChange("primkeyField", old, this.primkeyField);
   }

   public String getPrimKeyClass() {
      return this.primKeyClass;
   }

   public void setPrimKeyClass(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.primKeyClass;
      this.primKeyClass = value;
      this.isSet_primKeyClass = value != null;
      this.checkChange("primKeyClass", old, this.primKeyClass);
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

   public String getAbstractSchemaName() {
      return this.abstractSchemaName;
   }

   public void setAbstractSchemaName(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.abstractSchemaName;
      this.abstractSchemaName = value;
      this.isSet_abstractSchemaName = value != null;
      this.checkChange("abstractSchemaName", old, this.abstractSchemaName);
   }

   public CMPFieldMBean[] getCMPFields() {
      if (this.cmpFields == null) {
         return new CMPFieldMBean[0];
      } else {
         CMPFieldMBean[] result = new CMPFieldMBean[this.cmpFields.size()];
         result = (CMPFieldMBean[])((CMPFieldMBean[])this.cmpFields.toArray(result));
         return result;
      }
   }

   public void setCMPFields(CMPFieldMBean[] value) {
      CMPFieldMBean[] _oldVal = null;
      if (this.changeSupport != null) {
         _oldVal = this.getCMPFields();
      }

      this.isSet_cmpFields = true;
      if (this.cmpFields == null) {
         this.cmpFields = Collections.synchronizedList(new ArrayList());
      } else {
         this.cmpFields.clear();
      }

      if (null != value) {
         for(int i = 0; i < value.length; ++i) {
            this.cmpFields.add(value[i]);
         }
      }

      if (this.changeSupport != null) {
         this.checkChange("CMPFields", _oldVal, this.getCMPFields());
      }

   }

   public void addCMPField(CMPFieldMBean value) {
      this.isSet_cmpFields = true;
      if (this.cmpFields == null) {
         this.cmpFields = Collections.synchronizedList(new ArrayList());
      }

      this.cmpFields.add(value);
   }

   public void removeCMPField(CMPFieldMBean value) {
      if (this.cmpFields != null) {
         this.cmpFields.remove(value);
      }
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<entity");
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

      if (null != this.getLocalHome()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<local-home>").append(this.getLocalHome()).append("</local-home>\n");
      }

      if (null != this.getLocal()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<local>").append(this.getLocal()).append("</local>\n");
      }

      if (null != this.getEJBClass()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<ejb-class>").append(this.getEJBClass()).append("</ejb-class>\n");
      }

      if (null != this.getPersistenceType()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<persistence-type>").append(this.getPersistenceType()).append("</persistence-type>\n");
      }

      if (null != this.getPrimKeyClass()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<prim-key-class>").append(this.getPrimKeyClass()).append("</prim-key-class>\n");
      }

      result.append(ToXML.indent(indentLevel + 2)).append("<reentrant>").append(ToXML.capitalize(Boolean.valueOf(this.isReentrant()).toString())).append("</reentrant>\n");
      if ((this.isSet_cmpVersion || !"2.x".equals(this.getCMPVersion())) && null != this.getCMPVersion()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<cmp-version>").append(this.getCMPVersion()).append("</cmp-version>\n");
      }

      if (null != this.getAbstractSchemaName()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<abstract-schema-name>").append(this.getAbstractSchemaName()).append("</abstract-schema-name>\n");
      }

      int i;
      if (null != this.getCMPFields()) {
         for(i = 0; i < this.getCMPFields().length; ++i) {
            result.append(this.getCMPFields()[i].toXML(indentLevel + 2));
         }
      }

      if (null != this.getPrimkeyField()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<primkey-field>").append(this.getPrimkeyField()).append("</primkey-field>\n");
      }

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

      if (null != this.getSecurityRoleRefs()) {
         for(i = 0; i < this.getSecurityRoleRefs().length; ++i) {
            result.append(this.getSecurityRoleRefs()[i].toXML(indentLevel + 2));
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

      if (null != this.getQueries()) {
         for(i = 0; i < this.getQueries().length; ++i) {
            result.append(this.getQueries()[i].toXML(indentLevel + 2));
         }
      }

      result.append(ToXML.indent(indentLevel)).append("</entity>\n");
      return result.toString();
   }
}
