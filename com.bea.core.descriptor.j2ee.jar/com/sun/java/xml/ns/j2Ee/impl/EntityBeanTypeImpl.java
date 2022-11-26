package com.sun.java.xml.ns.j2Ee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.sun.java.xml.ns.j2Ee.CmpFieldType;
import com.sun.java.xml.ns.j2Ee.CmpVersionType;
import com.sun.java.xml.ns.j2Ee.DescriptionType;
import com.sun.java.xml.ns.j2Ee.DisplayNameType;
import com.sun.java.xml.ns.j2Ee.EjbClassType;
import com.sun.java.xml.ns.j2Ee.EjbLocalRefType;
import com.sun.java.xml.ns.j2Ee.EjbNameType;
import com.sun.java.xml.ns.j2Ee.EjbRefType;
import com.sun.java.xml.ns.j2Ee.EntityBeanType;
import com.sun.java.xml.ns.j2Ee.EnvEntryType;
import com.sun.java.xml.ns.j2Ee.FullyQualifiedClassType;
import com.sun.java.xml.ns.j2Ee.HomeType;
import com.sun.java.xml.ns.j2Ee.IconType;
import com.sun.java.xml.ns.j2Ee.JavaIdentifierType;
import com.sun.java.xml.ns.j2Ee.LocalHomeType;
import com.sun.java.xml.ns.j2Ee.LocalType;
import com.sun.java.xml.ns.j2Ee.MessageDestinationRefType;
import com.sun.java.xml.ns.j2Ee.PersistenceTypeType;
import com.sun.java.xml.ns.j2Ee.QueryType;
import com.sun.java.xml.ns.j2Ee.RemoteType;
import com.sun.java.xml.ns.j2Ee.ResourceEnvRefType;
import com.sun.java.xml.ns.j2Ee.ResourceRefType;
import com.sun.java.xml.ns.j2Ee.SecurityIdentityType;
import com.sun.java.xml.ns.j2Ee.SecurityRoleRefType;
import com.sun.java.xml.ns.j2Ee.ServiceRefType;
import com.sun.java.xml.ns.j2Ee.String;
import com.sun.java.xml.ns.j2Ee.TrueFalseType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class EntityBeanTypeImpl extends XmlComplexContentImpl implements EntityBeanType {
   private static final long serialVersionUID = 1L;
   private static final QName DESCRIPTION$0 = new QName("http://java.sun.com/xml/ns/j2ee", "description");
   private static final QName DISPLAYNAME$2 = new QName("http://java.sun.com/xml/ns/j2ee", "display-name");
   private static final QName ICON$4 = new QName("http://java.sun.com/xml/ns/j2ee", "icon");
   private static final QName EJBNAME$6 = new QName("http://java.sun.com/xml/ns/j2ee", "ejb-name");
   private static final QName HOME$8 = new QName("http://java.sun.com/xml/ns/j2ee", "home");
   private static final QName REMOTE$10 = new QName("http://java.sun.com/xml/ns/j2ee", "remote");
   private static final QName LOCALHOME$12 = new QName("http://java.sun.com/xml/ns/j2ee", "local-home");
   private static final QName LOCAL$14 = new QName("http://java.sun.com/xml/ns/j2ee", "local");
   private static final QName EJBCLASS$16 = new QName("http://java.sun.com/xml/ns/j2ee", "ejb-class");
   private static final QName PERSISTENCETYPE$18 = new QName("http://java.sun.com/xml/ns/j2ee", "persistence-type");
   private static final QName PRIMKEYCLASS$20 = new QName("http://java.sun.com/xml/ns/j2ee", "prim-key-class");
   private static final QName REENTRANT$22 = new QName("http://java.sun.com/xml/ns/j2ee", "reentrant");
   private static final QName CMPVERSION$24 = new QName("http://java.sun.com/xml/ns/j2ee", "cmp-version");
   private static final QName ABSTRACTSCHEMANAME$26 = new QName("http://java.sun.com/xml/ns/j2ee", "abstract-schema-name");
   private static final QName CMPFIELD$28 = new QName("http://java.sun.com/xml/ns/j2ee", "cmp-field");
   private static final QName PRIMKEYFIELD$30 = new QName("http://java.sun.com/xml/ns/j2ee", "primkey-field");
   private static final QName ENVENTRY$32 = new QName("http://java.sun.com/xml/ns/j2ee", "env-entry");
   private static final QName EJBREF$34 = new QName("http://java.sun.com/xml/ns/j2ee", "ejb-ref");
   private static final QName EJBLOCALREF$36 = new QName("http://java.sun.com/xml/ns/j2ee", "ejb-local-ref");
   private static final QName SERVICEREF$38 = new QName("http://java.sun.com/xml/ns/j2ee", "service-ref");
   private static final QName RESOURCEREF$40 = new QName("http://java.sun.com/xml/ns/j2ee", "resource-ref");
   private static final QName RESOURCEENVREF$42 = new QName("http://java.sun.com/xml/ns/j2ee", "resource-env-ref");
   private static final QName MESSAGEDESTINATIONREF$44 = new QName("http://java.sun.com/xml/ns/j2ee", "message-destination-ref");
   private static final QName SECURITYROLEREF$46 = new QName("http://java.sun.com/xml/ns/j2ee", "security-role-ref");
   private static final QName SECURITYIDENTITY$48 = new QName("http://java.sun.com/xml/ns/j2ee", "security-identity");
   private static final QName QUERY$50 = new QName("http://java.sun.com/xml/ns/j2ee", "query");
   private static final QName ID$52 = new QName("", "id");

   public EntityBeanTypeImpl(SchemaType sType) {
      super(sType);
   }

   public DescriptionType[] getDescriptionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(DESCRIPTION$0, targetList);
         DescriptionType[] result = new DescriptionType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public DescriptionType getDescriptionArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DescriptionType target = null;
         target = (DescriptionType)this.get_store().find_element_user(DESCRIPTION$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfDescriptionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DESCRIPTION$0);
      }
   }

   public void setDescriptionArray(DescriptionType[] descriptionArray) {
      this.check_orphaned();
      this.arraySetterHelper(descriptionArray, DESCRIPTION$0);
   }

   public void setDescriptionArray(int i, DescriptionType description) {
      this.generatedSetterHelperImpl(description, DESCRIPTION$0, i, (short)2);
   }

   public DescriptionType insertNewDescription(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DescriptionType target = null;
         target = (DescriptionType)this.get_store().insert_element_user(DESCRIPTION$0, i);
         return target;
      }
   }

   public DescriptionType addNewDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DescriptionType target = null;
         target = (DescriptionType)this.get_store().add_element_user(DESCRIPTION$0);
         return target;
      }
   }

   public void removeDescription(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DESCRIPTION$0, i);
      }
   }

   public DisplayNameType[] getDisplayNameArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(DISPLAYNAME$2, targetList);
         DisplayNameType[] result = new DisplayNameType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public DisplayNameType getDisplayNameArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DisplayNameType target = null;
         target = (DisplayNameType)this.get_store().find_element_user(DISPLAYNAME$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfDisplayNameArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DISPLAYNAME$2);
      }
   }

   public void setDisplayNameArray(DisplayNameType[] displayNameArray) {
      this.check_orphaned();
      this.arraySetterHelper(displayNameArray, DISPLAYNAME$2);
   }

   public void setDisplayNameArray(int i, DisplayNameType displayName) {
      this.generatedSetterHelperImpl(displayName, DISPLAYNAME$2, i, (short)2);
   }

   public DisplayNameType insertNewDisplayName(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DisplayNameType target = null;
         target = (DisplayNameType)this.get_store().insert_element_user(DISPLAYNAME$2, i);
         return target;
      }
   }

   public DisplayNameType addNewDisplayName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DisplayNameType target = null;
         target = (DisplayNameType)this.get_store().add_element_user(DISPLAYNAME$2);
         return target;
      }
   }

   public void removeDisplayName(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DISPLAYNAME$2, i);
      }
   }

   public IconType[] getIconArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(ICON$4, targetList);
         IconType[] result = new IconType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public IconType getIconArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         IconType target = null;
         target = (IconType)this.get_store().find_element_user(ICON$4, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfIconArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ICON$4);
      }
   }

   public void setIconArray(IconType[] iconArray) {
      this.check_orphaned();
      this.arraySetterHelper(iconArray, ICON$4);
   }

   public void setIconArray(int i, IconType icon) {
      this.generatedSetterHelperImpl(icon, ICON$4, i, (short)2);
   }

   public IconType insertNewIcon(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         IconType target = null;
         target = (IconType)this.get_store().insert_element_user(ICON$4, i);
         return target;
      }
   }

   public IconType addNewIcon() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         IconType target = null;
         target = (IconType)this.get_store().add_element_user(ICON$4);
         return target;
      }
   }

   public void removeIcon(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ICON$4, i);
      }
   }

   public EjbNameType getEjbName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbNameType target = null;
         target = (EjbNameType)this.get_store().find_element_user(EJBNAME$6, 0);
         return target == null ? null : target;
      }
   }

   public void setEjbName(EjbNameType ejbName) {
      this.generatedSetterHelperImpl(ejbName, EJBNAME$6, 0, (short)1);
   }

   public EjbNameType addNewEjbName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbNameType target = null;
         target = (EjbNameType)this.get_store().add_element_user(EJBNAME$6);
         return target;
      }
   }

   public HomeType getHome() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         HomeType target = null;
         target = (HomeType)this.get_store().find_element_user(HOME$8, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetHome() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(HOME$8) != 0;
      }
   }

   public void setHome(HomeType home) {
      this.generatedSetterHelperImpl(home, HOME$8, 0, (short)1);
   }

   public HomeType addNewHome() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         HomeType target = null;
         target = (HomeType)this.get_store().add_element_user(HOME$8);
         return target;
      }
   }

   public void unsetHome() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(HOME$8, 0);
      }
   }

   public RemoteType getRemote() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         RemoteType target = null;
         target = (RemoteType)this.get_store().find_element_user(REMOTE$10, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetRemote() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(REMOTE$10) != 0;
      }
   }

   public void setRemote(RemoteType remote) {
      this.generatedSetterHelperImpl(remote, REMOTE$10, 0, (short)1);
   }

   public RemoteType addNewRemote() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         RemoteType target = null;
         target = (RemoteType)this.get_store().add_element_user(REMOTE$10);
         return target;
      }
   }

   public void unsetRemote() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(REMOTE$10, 0);
      }
   }

   public LocalHomeType getLocalHome() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LocalHomeType target = null;
         target = (LocalHomeType)this.get_store().find_element_user(LOCALHOME$12, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetLocalHome() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LOCALHOME$12) != 0;
      }
   }

   public void setLocalHome(LocalHomeType localHome) {
      this.generatedSetterHelperImpl(localHome, LOCALHOME$12, 0, (short)1);
   }

   public LocalHomeType addNewLocalHome() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LocalHomeType target = null;
         target = (LocalHomeType)this.get_store().add_element_user(LOCALHOME$12);
         return target;
      }
   }

   public void unsetLocalHome() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LOCALHOME$12, 0);
      }
   }

   public LocalType getLocal() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LocalType target = null;
         target = (LocalType)this.get_store().find_element_user(LOCAL$14, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetLocal() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LOCAL$14) != 0;
      }
   }

   public void setLocal(LocalType local) {
      this.generatedSetterHelperImpl(local, LOCAL$14, 0, (short)1);
   }

   public LocalType addNewLocal() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LocalType target = null;
         target = (LocalType)this.get_store().add_element_user(LOCAL$14);
         return target;
      }
   }

   public void unsetLocal() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LOCAL$14, 0);
      }
   }

   public EjbClassType getEjbClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbClassType target = null;
         target = (EjbClassType)this.get_store().find_element_user(EJBCLASS$16, 0);
         return target == null ? null : target;
      }
   }

   public void setEjbClass(EjbClassType ejbClass) {
      this.generatedSetterHelperImpl(ejbClass, EJBCLASS$16, 0, (short)1);
   }

   public EjbClassType addNewEjbClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbClassType target = null;
         target = (EjbClassType)this.get_store().add_element_user(EJBCLASS$16);
         return target;
      }
   }

   public PersistenceTypeType getPersistenceType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceTypeType target = null;
         target = (PersistenceTypeType)this.get_store().find_element_user(PERSISTENCETYPE$18, 0);
         return target == null ? null : target;
      }
   }

   public void setPersistenceType(PersistenceTypeType persistenceType) {
      this.generatedSetterHelperImpl(persistenceType, PERSISTENCETYPE$18, 0, (short)1);
   }

   public PersistenceTypeType addNewPersistenceType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceTypeType target = null;
         target = (PersistenceTypeType)this.get_store().add_element_user(PERSISTENCETYPE$18);
         return target;
      }
   }

   public FullyQualifiedClassType getPrimKeyClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().find_element_user(PRIMKEYCLASS$20, 0);
         return target == null ? null : target;
      }
   }

   public void setPrimKeyClass(FullyQualifiedClassType primKeyClass) {
      this.generatedSetterHelperImpl(primKeyClass, PRIMKEYCLASS$20, 0, (short)1);
   }

   public FullyQualifiedClassType addNewPrimKeyClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().add_element_user(PRIMKEYCLASS$20);
         return target;
      }
   }

   public TrueFalseType getReentrant() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(REENTRANT$22, 0);
         return target == null ? null : target;
      }
   }

   public void setReentrant(TrueFalseType reentrant) {
      this.generatedSetterHelperImpl(reentrant, REENTRANT$22, 0, (short)1);
   }

   public TrueFalseType addNewReentrant() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(REENTRANT$22);
         return target;
      }
   }

   public CmpVersionType getCmpVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CmpVersionType target = null;
         target = (CmpVersionType)this.get_store().find_element_user(CMPVERSION$24, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetCmpVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CMPVERSION$24) != 0;
      }
   }

   public void setCmpVersion(CmpVersionType cmpVersion) {
      this.generatedSetterHelperImpl(cmpVersion, CMPVERSION$24, 0, (short)1);
   }

   public CmpVersionType addNewCmpVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CmpVersionType target = null;
         target = (CmpVersionType)this.get_store().add_element_user(CMPVERSION$24);
         return target;
      }
   }

   public void unsetCmpVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CMPVERSION$24, 0);
      }
   }

   public JavaIdentifierType getAbstractSchemaName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JavaIdentifierType target = null;
         target = (JavaIdentifierType)this.get_store().find_element_user(ABSTRACTSCHEMANAME$26, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetAbstractSchemaName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ABSTRACTSCHEMANAME$26) != 0;
      }
   }

   public void setAbstractSchemaName(JavaIdentifierType abstractSchemaName) {
      this.generatedSetterHelperImpl(abstractSchemaName, ABSTRACTSCHEMANAME$26, 0, (short)1);
   }

   public JavaIdentifierType addNewAbstractSchemaName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JavaIdentifierType target = null;
         target = (JavaIdentifierType)this.get_store().add_element_user(ABSTRACTSCHEMANAME$26);
         return target;
      }
   }

   public void unsetAbstractSchemaName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ABSTRACTSCHEMANAME$26, 0);
      }
   }

   public CmpFieldType[] getCmpFieldArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(CMPFIELD$28, targetList);
         CmpFieldType[] result = new CmpFieldType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public CmpFieldType getCmpFieldArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CmpFieldType target = null;
         target = (CmpFieldType)this.get_store().find_element_user(CMPFIELD$28, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfCmpFieldArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CMPFIELD$28);
      }
   }

   public void setCmpFieldArray(CmpFieldType[] cmpFieldArray) {
      this.check_orphaned();
      this.arraySetterHelper(cmpFieldArray, CMPFIELD$28);
   }

   public void setCmpFieldArray(int i, CmpFieldType cmpField) {
      this.generatedSetterHelperImpl(cmpField, CMPFIELD$28, i, (short)2);
   }

   public CmpFieldType insertNewCmpField(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CmpFieldType target = null;
         target = (CmpFieldType)this.get_store().insert_element_user(CMPFIELD$28, i);
         return target;
      }
   }

   public CmpFieldType addNewCmpField() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CmpFieldType target = null;
         target = (CmpFieldType)this.get_store().add_element_user(CMPFIELD$28);
         return target;
      }
   }

   public void removeCmpField(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CMPFIELD$28, i);
      }
   }

   public String getPrimkeyField() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(PRIMKEYFIELD$30, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetPrimkeyField() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PRIMKEYFIELD$30) != 0;
      }
   }

   public void setPrimkeyField(String primkeyField) {
      this.generatedSetterHelperImpl(primkeyField, PRIMKEYFIELD$30, 0, (short)1);
   }

   public String addNewPrimkeyField() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(PRIMKEYFIELD$30);
         return target;
      }
   }

   public void unsetPrimkeyField() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PRIMKEYFIELD$30, 0);
      }
   }

   public EnvEntryType[] getEnvEntryArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(ENVENTRY$32, targetList);
         EnvEntryType[] result = new EnvEntryType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public EnvEntryType getEnvEntryArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EnvEntryType target = null;
         target = (EnvEntryType)this.get_store().find_element_user(ENVENTRY$32, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfEnvEntryArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ENVENTRY$32);
      }
   }

   public void setEnvEntryArray(EnvEntryType[] envEntryArray) {
      this.check_orphaned();
      this.arraySetterHelper(envEntryArray, ENVENTRY$32);
   }

   public void setEnvEntryArray(int i, EnvEntryType envEntry) {
      this.generatedSetterHelperImpl(envEntry, ENVENTRY$32, i, (short)2);
   }

   public EnvEntryType insertNewEnvEntry(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EnvEntryType target = null;
         target = (EnvEntryType)this.get_store().insert_element_user(ENVENTRY$32, i);
         return target;
      }
   }

   public EnvEntryType addNewEnvEntry() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EnvEntryType target = null;
         target = (EnvEntryType)this.get_store().add_element_user(ENVENTRY$32);
         return target;
      }
   }

   public void removeEnvEntry(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ENVENTRY$32, i);
      }
   }

   public EjbRefType[] getEjbRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(EJBREF$34, targetList);
         EjbRefType[] result = new EjbRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public EjbRefType getEjbRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbRefType target = null;
         target = (EjbRefType)this.get_store().find_element_user(EJBREF$34, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfEjbRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(EJBREF$34);
      }
   }

   public void setEjbRefArray(EjbRefType[] ejbRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(ejbRefArray, EJBREF$34);
   }

   public void setEjbRefArray(int i, EjbRefType ejbRef) {
      this.generatedSetterHelperImpl(ejbRef, EJBREF$34, i, (short)2);
   }

   public EjbRefType insertNewEjbRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbRefType target = null;
         target = (EjbRefType)this.get_store().insert_element_user(EJBREF$34, i);
         return target;
      }
   }

   public EjbRefType addNewEjbRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbRefType target = null;
         target = (EjbRefType)this.get_store().add_element_user(EJBREF$34);
         return target;
      }
   }

   public void removeEjbRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(EJBREF$34, i);
      }
   }

   public EjbLocalRefType[] getEjbLocalRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(EJBLOCALREF$36, targetList);
         EjbLocalRefType[] result = new EjbLocalRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public EjbLocalRefType getEjbLocalRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbLocalRefType target = null;
         target = (EjbLocalRefType)this.get_store().find_element_user(EJBLOCALREF$36, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfEjbLocalRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(EJBLOCALREF$36);
      }
   }

   public void setEjbLocalRefArray(EjbLocalRefType[] ejbLocalRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(ejbLocalRefArray, EJBLOCALREF$36);
   }

   public void setEjbLocalRefArray(int i, EjbLocalRefType ejbLocalRef) {
      this.generatedSetterHelperImpl(ejbLocalRef, EJBLOCALREF$36, i, (short)2);
   }

   public EjbLocalRefType insertNewEjbLocalRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbLocalRefType target = null;
         target = (EjbLocalRefType)this.get_store().insert_element_user(EJBLOCALREF$36, i);
         return target;
      }
   }

   public EjbLocalRefType addNewEjbLocalRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbLocalRefType target = null;
         target = (EjbLocalRefType)this.get_store().add_element_user(EJBLOCALREF$36);
         return target;
      }
   }

   public void removeEjbLocalRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(EJBLOCALREF$36, i);
      }
   }

   public ServiceRefType[] getServiceRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(SERVICEREF$38, targetList);
         ServiceRefType[] result = new ServiceRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ServiceRefType getServiceRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServiceRefType target = null;
         target = (ServiceRefType)this.get_store().find_element_user(SERVICEREF$38, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfServiceRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SERVICEREF$38);
      }
   }

   public void setServiceRefArray(ServiceRefType[] serviceRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(serviceRefArray, SERVICEREF$38);
   }

   public void setServiceRefArray(int i, ServiceRefType serviceRef) {
      this.generatedSetterHelperImpl(serviceRef, SERVICEREF$38, i, (short)2);
   }

   public ServiceRefType insertNewServiceRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServiceRefType target = null;
         target = (ServiceRefType)this.get_store().insert_element_user(SERVICEREF$38, i);
         return target;
      }
   }

   public ServiceRefType addNewServiceRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServiceRefType target = null;
         target = (ServiceRefType)this.get_store().add_element_user(SERVICEREF$38);
         return target;
      }
   }

   public void removeServiceRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SERVICEREF$38, i);
      }
   }

   public ResourceRefType[] getResourceRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(RESOURCEREF$40, targetList);
         ResourceRefType[] result = new ResourceRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ResourceRefType getResourceRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceRefType target = null;
         target = (ResourceRefType)this.get_store().find_element_user(RESOURCEREF$40, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfResourceRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(RESOURCEREF$40);
      }
   }

   public void setResourceRefArray(ResourceRefType[] resourceRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(resourceRefArray, RESOURCEREF$40);
   }

   public void setResourceRefArray(int i, ResourceRefType resourceRef) {
      this.generatedSetterHelperImpl(resourceRef, RESOURCEREF$40, i, (short)2);
   }

   public ResourceRefType insertNewResourceRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceRefType target = null;
         target = (ResourceRefType)this.get_store().insert_element_user(RESOURCEREF$40, i);
         return target;
      }
   }

   public ResourceRefType addNewResourceRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceRefType target = null;
         target = (ResourceRefType)this.get_store().add_element_user(RESOURCEREF$40);
         return target;
      }
   }

   public void removeResourceRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RESOURCEREF$40, i);
      }
   }

   public ResourceEnvRefType[] getResourceEnvRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(RESOURCEENVREF$42, targetList);
         ResourceEnvRefType[] result = new ResourceEnvRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ResourceEnvRefType getResourceEnvRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceEnvRefType target = null;
         target = (ResourceEnvRefType)this.get_store().find_element_user(RESOURCEENVREF$42, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfResourceEnvRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(RESOURCEENVREF$42);
      }
   }

   public void setResourceEnvRefArray(ResourceEnvRefType[] resourceEnvRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(resourceEnvRefArray, RESOURCEENVREF$42);
   }

   public void setResourceEnvRefArray(int i, ResourceEnvRefType resourceEnvRef) {
      this.generatedSetterHelperImpl(resourceEnvRef, RESOURCEENVREF$42, i, (short)2);
   }

   public ResourceEnvRefType insertNewResourceEnvRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceEnvRefType target = null;
         target = (ResourceEnvRefType)this.get_store().insert_element_user(RESOURCEENVREF$42, i);
         return target;
      }
   }

   public ResourceEnvRefType addNewResourceEnvRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceEnvRefType target = null;
         target = (ResourceEnvRefType)this.get_store().add_element_user(RESOURCEENVREF$42);
         return target;
      }
   }

   public void removeResourceEnvRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RESOURCEENVREF$42, i);
      }
   }

   public MessageDestinationRefType[] getMessageDestinationRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(MESSAGEDESTINATIONREF$44, targetList);
         MessageDestinationRefType[] result = new MessageDestinationRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public MessageDestinationRefType getMessageDestinationRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationRefType target = null;
         target = (MessageDestinationRefType)this.get_store().find_element_user(MESSAGEDESTINATIONREF$44, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfMessageDestinationRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MESSAGEDESTINATIONREF$44);
      }
   }

   public void setMessageDestinationRefArray(MessageDestinationRefType[] messageDestinationRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(messageDestinationRefArray, MESSAGEDESTINATIONREF$44);
   }

   public void setMessageDestinationRefArray(int i, MessageDestinationRefType messageDestinationRef) {
      this.generatedSetterHelperImpl(messageDestinationRef, MESSAGEDESTINATIONREF$44, i, (short)2);
   }

   public MessageDestinationRefType insertNewMessageDestinationRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationRefType target = null;
         target = (MessageDestinationRefType)this.get_store().insert_element_user(MESSAGEDESTINATIONREF$44, i);
         return target;
      }
   }

   public MessageDestinationRefType addNewMessageDestinationRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationRefType target = null;
         target = (MessageDestinationRefType)this.get_store().add_element_user(MESSAGEDESTINATIONREF$44);
         return target;
      }
   }

   public void removeMessageDestinationRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MESSAGEDESTINATIONREF$44, i);
      }
   }

   public SecurityRoleRefType[] getSecurityRoleRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(SECURITYROLEREF$46, targetList);
         SecurityRoleRefType[] result = new SecurityRoleRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public SecurityRoleRefType getSecurityRoleRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SecurityRoleRefType target = null;
         target = (SecurityRoleRefType)this.get_store().find_element_user(SECURITYROLEREF$46, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfSecurityRoleRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SECURITYROLEREF$46);
      }
   }

   public void setSecurityRoleRefArray(SecurityRoleRefType[] securityRoleRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(securityRoleRefArray, SECURITYROLEREF$46);
   }

   public void setSecurityRoleRefArray(int i, SecurityRoleRefType securityRoleRef) {
      this.generatedSetterHelperImpl(securityRoleRef, SECURITYROLEREF$46, i, (short)2);
   }

   public SecurityRoleRefType insertNewSecurityRoleRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SecurityRoleRefType target = null;
         target = (SecurityRoleRefType)this.get_store().insert_element_user(SECURITYROLEREF$46, i);
         return target;
      }
   }

   public SecurityRoleRefType addNewSecurityRoleRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SecurityRoleRefType target = null;
         target = (SecurityRoleRefType)this.get_store().add_element_user(SECURITYROLEREF$46);
         return target;
      }
   }

   public void removeSecurityRoleRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SECURITYROLEREF$46, i);
      }
   }

   public SecurityIdentityType getSecurityIdentity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SecurityIdentityType target = null;
         target = (SecurityIdentityType)this.get_store().find_element_user(SECURITYIDENTITY$48, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetSecurityIdentity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SECURITYIDENTITY$48) != 0;
      }
   }

   public void setSecurityIdentity(SecurityIdentityType securityIdentity) {
      this.generatedSetterHelperImpl(securityIdentity, SECURITYIDENTITY$48, 0, (short)1);
   }

   public SecurityIdentityType addNewSecurityIdentity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SecurityIdentityType target = null;
         target = (SecurityIdentityType)this.get_store().add_element_user(SECURITYIDENTITY$48);
         return target;
      }
   }

   public void unsetSecurityIdentity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SECURITYIDENTITY$48, 0);
      }
   }

   public QueryType[] getQueryArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(QUERY$50, targetList);
         QueryType[] result = new QueryType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public QueryType getQueryArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         QueryType target = null;
         target = (QueryType)this.get_store().find_element_user(QUERY$50, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfQueryArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(QUERY$50);
      }
   }

   public void setQueryArray(QueryType[] queryArray) {
      this.check_orphaned();
      this.arraySetterHelper(queryArray, QUERY$50);
   }

   public void setQueryArray(int i, QueryType query) {
      this.generatedSetterHelperImpl(query, QUERY$50, i, (short)2);
   }

   public QueryType insertNewQuery(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         QueryType target = null;
         target = (QueryType)this.get_store().insert_element_user(QUERY$50, i);
         return target;
      }
   }

   public QueryType addNewQuery() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         QueryType target = null;
         target = (QueryType)this.get_store().add_element_user(QUERY$50);
         return target;
      }
   }

   public void removeQuery(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(QUERY$50, i);
      }
   }

   public java.lang.String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$52);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$52);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$52) != null;
      }
   }

   public void setId(java.lang.String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$52);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$52);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$52);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$52);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$52);
      }
   }
}
