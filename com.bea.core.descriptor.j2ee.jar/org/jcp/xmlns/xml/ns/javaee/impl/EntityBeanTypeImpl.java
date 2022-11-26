package org.jcp.xmlns.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import org.jcp.xmlns.xml.ns.javaee.AdministeredObjectType;
import org.jcp.xmlns.xml.ns.javaee.CmpFieldType;
import org.jcp.xmlns.xml.ns.javaee.CmpVersionType;
import org.jcp.xmlns.xml.ns.javaee.ConnectionFactoryResourceType;
import org.jcp.xmlns.xml.ns.javaee.DataSourceType;
import org.jcp.xmlns.xml.ns.javaee.DescriptionType;
import org.jcp.xmlns.xml.ns.javaee.DisplayNameType;
import org.jcp.xmlns.xml.ns.javaee.EjbClassType;
import org.jcp.xmlns.xml.ns.javaee.EjbLocalRefType;
import org.jcp.xmlns.xml.ns.javaee.EjbNameType;
import org.jcp.xmlns.xml.ns.javaee.EjbRefType;
import org.jcp.xmlns.xml.ns.javaee.EntityBeanType;
import org.jcp.xmlns.xml.ns.javaee.EnvEntryType;
import org.jcp.xmlns.xml.ns.javaee.FullyQualifiedClassType;
import org.jcp.xmlns.xml.ns.javaee.HomeType;
import org.jcp.xmlns.xml.ns.javaee.IconType;
import org.jcp.xmlns.xml.ns.javaee.JavaIdentifierType;
import org.jcp.xmlns.xml.ns.javaee.JmsConnectionFactoryType;
import org.jcp.xmlns.xml.ns.javaee.JmsDestinationType;
import org.jcp.xmlns.xml.ns.javaee.LifecycleCallbackType;
import org.jcp.xmlns.xml.ns.javaee.LocalHomeType;
import org.jcp.xmlns.xml.ns.javaee.LocalType;
import org.jcp.xmlns.xml.ns.javaee.MailSessionType;
import org.jcp.xmlns.xml.ns.javaee.MessageDestinationRefType;
import org.jcp.xmlns.xml.ns.javaee.PersistenceContextRefType;
import org.jcp.xmlns.xml.ns.javaee.PersistenceTypeType;
import org.jcp.xmlns.xml.ns.javaee.PersistenceUnitRefType;
import org.jcp.xmlns.xml.ns.javaee.QueryType;
import org.jcp.xmlns.xml.ns.javaee.RemoteType;
import org.jcp.xmlns.xml.ns.javaee.ResourceEnvRefType;
import org.jcp.xmlns.xml.ns.javaee.ResourceRefType;
import org.jcp.xmlns.xml.ns.javaee.SecurityIdentityType;
import org.jcp.xmlns.xml.ns.javaee.SecurityRoleRefType;
import org.jcp.xmlns.xml.ns.javaee.ServiceRefType;
import org.jcp.xmlns.xml.ns.javaee.String;
import org.jcp.xmlns.xml.ns.javaee.TrueFalseType;
import org.jcp.xmlns.xml.ns.javaee.XsdStringType;

public class EntityBeanTypeImpl extends XmlComplexContentImpl implements EntityBeanType {
   private static final long serialVersionUID = 1L;
   private static final QName DESCRIPTION$0 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "description");
   private static final QName DISPLAYNAME$2 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "display-name");
   private static final QName ICON$4 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "icon");
   private static final QName EJBNAME$6 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "ejb-name");
   private static final QName MAPPEDNAME$8 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "mapped-name");
   private static final QName HOME$10 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "home");
   private static final QName REMOTE$12 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "remote");
   private static final QName LOCALHOME$14 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "local-home");
   private static final QName LOCAL$16 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "local");
   private static final QName EJBCLASS$18 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "ejb-class");
   private static final QName PERSISTENCETYPE$20 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "persistence-type");
   private static final QName PRIMKEYCLASS$22 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "prim-key-class");
   private static final QName REENTRANT$24 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "reentrant");
   private static final QName CMPVERSION$26 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "cmp-version");
   private static final QName ABSTRACTSCHEMANAME$28 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "abstract-schema-name");
   private static final QName CMPFIELD$30 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "cmp-field");
   private static final QName PRIMKEYFIELD$32 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "primkey-field");
   private static final QName ENVENTRY$34 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "env-entry");
   private static final QName EJBREF$36 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "ejb-ref");
   private static final QName EJBLOCALREF$38 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "ejb-local-ref");
   private static final QName SERVICEREF$40 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "service-ref");
   private static final QName RESOURCEREF$42 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "resource-ref");
   private static final QName RESOURCEENVREF$44 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "resource-env-ref");
   private static final QName MESSAGEDESTINATIONREF$46 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "message-destination-ref");
   private static final QName PERSISTENCECONTEXTREF$48 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "persistence-context-ref");
   private static final QName PERSISTENCEUNITREF$50 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "persistence-unit-ref");
   private static final QName POSTCONSTRUCT$52 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "post-construct");
   private static final QName PREDESTROY$54 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "pre-destroy");
   private static final QName DATASOURCE$56 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "data-source");
   private static final QName JMSCONNECTIONFACTORY$58 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "jms-connection-factory");
   private static final QName JMSDESTINATION$60 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "jms-destination");
   private static final QName MAILSESSION$62 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "mail-session");
   private static final QName CONNECTIONFACTORY$64 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "connection-factory");
   private static final QName ADMINISTEREDOBJECT$66 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "administered-object");
   private static final QName SECURITYROLEREF$68 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "security-role-ref");
   private static final QName SECURITYIDENTITY$70 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "security-identity");
   private static final QName QUERY$72 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "query");
   private static final QName ID$74 = new QName("", "id");

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

   public XsdStringType getMappedName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().find_element_user(MAPPEDNAME$8, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetMappedName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MAPPEDNAME$8) != 0;
      }
   }

   public void setMappedName(XsdStringType mappedName) {
      this.generatedSetterHelperImpl(mappedName, MAPPEDNAME$8, 0, (short)1);
   }

   public XsdStringType addNewMappedName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().add_element_user(MAPPEDNAME$8);
         return target;
      }
   }

   public void unsetMappedName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MAPPEDNAME$8, 0);
      }
   }

   public HomeType getHome() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         HomeType target = null;
         target = (HomeType)this.get_store().find_element_user(HOME$10, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetHome() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(HOME$10) != 0;
      }
   }

   public void setHome(HomeType home) {
      this.generatedSetterHelperImpl(home, HOME$10, 0, (short)1);
   }

   public HomeType addNewHome() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         HomeType target = null;
         target = (HomeType)this.get_store().add_element_user(HOME$10);
         return target;
      }
   }

   public void unsetHome() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(HOME$10, 0);
      }
   }

   public RemoteType getRemote() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         RemoteType target = null;
         target = (RemoteType)this.get_store().find_element_user(REMOTE$12, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetRemote() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(REMOTE$12) != 0;
      }
   }

   public void setRemote(RemoteType remote) {
      this.generatedSetterHelperImpl(remote, REMOTE$12, 0, (short)1);
   }

   public RemoteType addNewRemote() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         RemoteType target = null;
         target = (RemoteType)this.get_store().add_element_user(REMOTE$12);
         return target;
      }
   }

   public void unsetRemote() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(REMOTE$12, 0);
      }
   }

   public LocalHomeType getLocalHome() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LocalHomeType target = null;
         target = (LocalHomeType)this.get_store().find_element_user(LOCALHOME$14, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetLocalHome() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LOCALHOME$14) != 0;
      }
   }

   public void setLocalHome(LocalHomeType localHome) {
      this.generatedSetterHelperImpl(localHome, LOCALHOME$14, 0, (short)1);
   }

   public LocalHomeType addNewLocalHome() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LocalHomeType target = null;
         target = (LocalHomeType)this.get_store().add_element_user(LOCALHOME$14);
         return target;
      }
   }

   public void unsetLocalHome() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LOCALHOME$14, 0);
      }
   }

   public LocalType getLocal() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LocalType target = null;
         target = (LocalType)this.get_store().find_element_user(LOCAL$16, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetLocal() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LOCAL$16) != 0;
      }
   }

   public void setLocal(LocalType local) {
      this.generatedSetterHelperImpl(local, LOCAL$16, 0, (short)1);
   }

   public LocalType addNewLocal() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LocalType target = null;
         target = (LocalType)this.get_store().add_element_user(LOCAL$16);
         return target;
      }
   }

   public void unsetLocal() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LOCAL$16, 0);
      }
   }

   public EjbClassType getEjbClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbClassType target = null;
         target = (EjbClassType)this.get_store().find_element_user(EJBCLASS$18, 0);
         return target == null ? null : target;
      }
   }

   public void setEjbClass(EjbClassType ejbClass) {
      this.generatedSetterHelperImpl(ejbClass, EJBCLASS$18, 0, (short)1);
   }

   public EjbClassType addNewEjbClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbClassType target = null;
         target = (EjbClassType)this.get_store().add_element_user(EJBCLASS$18);
         return target;
      }
   }

   public PersistenceTypeType getPersistenceType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceTypeType target = null;
         target = (PersistenceTypeType)this.get_store().find_element_user(PERSISTENCETYPE$20, 0);
         return target == null ? null : target;
      }
   }

   public void setPersistenceType(PersistenceTypeType persistenceType) {
      this.generatedSetterHelperImpl(persistenceType, PERSISTENCETYPE$20, 0, (short)1);
   }

   public PersistenceTypeType addNewPersistenceType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceTypeType target = null;
         target = (PersistenceTypeType)this.get_store().add_element_user(PERSISTENCETYPE$20);
         return target;
      }
   }

   public FullyQualifiedClassType getPrimKeyClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().find_element_user(PRIMKEYCLASS$22, 0);
         return target == null ? null : target;
      }
   }

   public void setPrimKeyClass(FullyQualifiedClassType primKeyClass) {
      this.generatedSetterHelperImpl(primKeyClass, PRIMKEYCLASS$22, 0, (short)1);
   }

   public FullyQualifiedClassType addNewPrimKeyClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().add_element_user(PRIMKEYCLASS$22);
         return target;
      }
   }

   public TrueFalseType getReentrant() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(REENTRANT$24, 0);
         return target == null ? null : target;
      }
   }

   public void setReentrant(TrueFalseType reentrant) {
      this.generatedSetterHelperImpl(reentrant, REENTRANT$24, 0, (short)1);
   }

   public TrueFalseType addNewReentrant() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(REENTRANT$24);
         return target;
      }
   }

   public CmpVersionType getCmpVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CmpVersionType target = null;
         target = (CmpVersionType)this.get_store().find_element_user(CMPVERSION$26, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetCmpVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CMPVERSION$26) != 0;
      }
   }

   public void setCmpVersion(CmpVersionType cmpVersion) {
      this.generatedSetterHelperImpl(cmpVersion, CMPVERSION$26, 0, (short)1);
   }

   public CmpVersionType addNewCmpVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CmpVersionType target = null;
         target = (CmpVersionType)this.get_store().add_element_user(CMPVERSION$26);
         return target;
      }
   }

   public void unsetCmpVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CMPVERSION$26, 0);
      }
   }

   public JavaIdentifierType getAbstractSchemaName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JavaIdentifierType target = null;
         target = (JavaIdentifierType)this.get_store().find_element_user(ABSTRACTSCHEMANAME$28, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetAbstractSchemaName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ABSTRACTSCHEMANAME$28) != 0;
      }
   }

   public void setAbstractSchemaName(JavaIdentifierType abstractSchemaName) {
      this.generatedSetterHelperImpl(abstractSchemaName, ABSTRACTSCHEMANAME$28, 0, (short)1);
   }

   public JavaIdentifierType addNewAbstractSchemaName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JavaIdentifierType target = null;
         target = (JavaIdentifierType)this.get_store().add_element_user(ABSTRACTSCHEMANAME$28);
         return target;
      }
   }

   public void unsetAbstractSchemaName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ABSTRACTSCHEMANAME$28, 0);
      }
   }

   public CmpFieldType[] getCmpFieldArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(CMPFIELD$30, targetList);
         CmpFieldType[] result = new CmpFieldType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public CmpFieldType getCmpFieldArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CmpFieldType target = null;
         target = (CmpFieldType)this.get_store().find_element_user(CMPFIELD$30, i);
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
         return this.get_store().count_elements(CMPFIELD$30);
      }
   }

   public void setCmpFieldArray(CmpFieldType[] cmpFieldArray) {
      this.check_orphaned();
      this.arraySetterHelper(cmpFieldArray, CMPFIELD$30);
   }

   public void setCmpFieldArray(int i, CmpFieldType cmpField) {
      this.generatedSetterHelperImpl(cmpField, CMPFIELD$30, i, (short)2);
   }

   public CmpFieldType insertNewCmpField(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CmpFieldType target = null;
         target = (CmpFieldType)this.get_store().insert_element_user(CMPFIELD$30, i);
         return target;
      }
   }

   public CmpFieldType addNewCmpField() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CmpFieldType target = null;
         target = (CmpFieldType)this.get_store().add_element_user(CMPFIELD$30);
         return target;
      }
   }

   public void removeCmpField(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CMPFIELD$30, i);
      }
   }

   public String getPrimkeyField() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(PRIMKEYFIELD$32, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetPrimkeyField() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PRIMKEYFIELD$32) != 0;
      }
   }

   public void setPrimkeyField(String primkeyField) {
      this.generatedSetterHelperImpl(primkeyField, PRIMKEYFIELD$32, 0, (short)1);
   }

   public String addNewPrimkeyField() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(PRIMKEYFIELD$32);
         return target;
      }
   }

   public void unsetPrimkeyField() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PRIMKEYFIELD$32, 0);
      }
   }

   public EnvEntryType[] getEnvEntryArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(ENVENTRY$34, targetList);
         EnvEntryType[] result = new EnvEntryType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public EnvEntryType getEnvEntryArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EnvEntryType target = null;
         target = (EnvEntryType)this.get_store().find_element_user(ENVENTRY$34, i);
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
         return this.get_store().count_elements(ENVENTRY$34);
      }
   }

   public void setEnvEntryArray(EnvEntryType[] envEntryArray) {
      this.check_orphaned();
      this.arraySetterHelper(envEntryArray, ENVENTRY$34);
   }

   public void setEnvEntryArray(int i, EnvEntryType envEntry) {
      this.generatedSetterHelperImpl(envEntry, ENVENTRY$34, i, (short)2);
   }

   public EnvEntryType insertNewEnvEntry(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EnvEntryType target = null;
         target = (EnvEntryType)this.get_store().insert_element_user(ENVENTRY$34, i);
         return target;
      }
   }

   public EnvEntryType addNewEnvEntry() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EnvEntryType target = null;
         target = (EnvEntryType)this.get_store().add_element_user(ENVENTRY$34);
         return target;
      }
   }

   public void removeEnvEntry(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ENVENTRY$34, i);
      }
   }

   public EjbRefType[] getEjbRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(EJBREF$36, targetList);
         EjbRefType[] result = new EjbRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public EjbRefType getEjbRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbRefType target = null;
         target = (EjbRefType)this.get_store().find_element_user(EJBREF$36, i);
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
         return this.get_store().count_elements(EJBREF$36);
      }
   }

   public void setEjbRefArray(EjbRefType[] ejbRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(ejbRefArray, EJBREF$36);
   }

   public void setEjbRefArray(int i, EjbRefType ejbRef) {
      this.generatedSetterHelperImpl(ejbRef, EJBREF$36, i, (short)2);
   }

   public EjbRefType insertNewEjbRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbRefType target = null;
         target = (EjbRefType)this.get_store().insert_element_user(EJBREF$36, i);
         return target;
      }
   }

   public EjbRefType addNewEjbRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbRefType target = null;
         target = (EjbRefType)this.get_store().add_element_user(EJBREF$36);
         return target;
      }
   }

   public void removeEjbRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(EJBREF$36, i);
      }
   }

   public EjbLocalRefType[] getEjbLocalRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(EJBLOCALREF$38, targetList);
         EjbLocalRefType[] result = new EjbLocalRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public EjbLocalRefType getEjbLocalRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbLocalRefType target = null;
         target = (EjbLocalRefType)this.get_store().find_element_user(EJBLOCALREF$38, i);
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
         return this.get_store().count_elements(EJBLOCALREF$38);
      }
   }

   public void setEjbLocalRefArray(EjbLocalRefType[] ejbLocalRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(ejbLocalRefArray, EJBLOCALREF$38);
   }

   public void setEjbLocalRefArray(int i, EjbLocalRefType ejbLocalRef) {
      this.generatedSetterHelperImpl(ejbLocalRef, EJBLOCALREF$38, i, (short)2);
   }

   public EjbLocalRefType insertNewEjbLocalRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbLocalRefType target = null;
         target = (EjbLocalRefType)this.get_store().insert_element_user(EJBLOCALREF$38, i);
         return target;
      }
   }

   public EjbLocalRefType addNewEjbLocalRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbLocalRefType target = null;
         target = (EjbLocalRefType)this.get_store().add_element_user(EJBLOCALREF$38);
         return target;
      }
   }

   public void removeEjbLocalRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(EJBLOCALREF$38, i);
      }
   }

   public ServiceRefType[] getServiceRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(SERVICEREF$40, targetList);
         ServiceRefType[] result = new ServiceRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ServiceRefType getServiceRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServiceRefType target = null;
         target = (ServiceRefType)this.get_store().find_element_user(SERVICEREF$40, i);
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
         return this.get_store().count_elements(SERVICEREF$40);
      }
   }

   public void setServiceRefArray(ServiceRefType[] serviceRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(serviceRefArray, SERVICEREF$40);
   }

   public void setServiceRefArray(int i, ServiceRefType serviceRef) {
      this.generatedSetterHelperImpl(serviceRef, SERVICEREF$40, i, (short)2);
   }

   public ServiceRefType insertNewServiceRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServiceRefType target = null;
         target = (ServiceRefType)this.get_store().insert_element_user(SERVICEREF$40, i);
         return target;
      }
   }

   public ServiceRefType addNewServiceRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServiceRefType target = null;
         target = (ServiceRefType)this.get_store().add_element_user(SERVICEREF$40);
         return target;
      }
   }

   public void removeServiceRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SERVICEREF$40, i);
      }
   }

   public ResourceRefType[] getResourceRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(RESOURCEREF$42, targetList);
         ResourceRefType[] result = new ResourceRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ResourceRefType getResourceRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceRefType target = null;
         target = (ResourceRefType)this.get_store().find_element_user(RESOURCEREF$42, i);
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
         return this.get_store().count_elements(RESOURCEREF$42);
      }
   }

   public void setResourceRefArray(ResourceRefType[] resourceRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(resourceRefArray, RESOURCEREF$42);
   }

   public void setResourceRefArray(int i, ResourceRefType resourceRef) {
      this.generatedSetterHelperImpl(resourceRef, RESOURCEREF$42, i, (short)2);
   }

   public ResourceRefType insertNewResourceRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceRefType target = null;
         target = (ResourceRefType)this.get_store().insert_element_user(RESOURCEREF$42, i);
         return target;
      }
   }

   public ResourceRefType addNewResourceRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceRefType target = null;
         target = (ResourceRefType)this.get_store().add_element_user(RESOURCEREF$42);
         return target;
      }
   }

   public void removeResourceRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RESOURCEREF$42, i);
      }
   }

   public ResourceEnvRefType[] getResourceEnvRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(RESOURCEENVREF$44, targetList);
         ResourceEnvRefType[] result = new ResourceEnvRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ResourceEnvRefType getResourceEnvRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceEnvRefType target = null;
         target = (ResourceEnvRefType)this.get_store().find_element_user(RESOURCEENVREF$44, i);
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
         return this.get_store().count_elements(RESOURCEENVREF$44);
      }
   }

   public void setResourceEnvRefArray(ResourceEnvRefType[] resourceEnvRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(resourceEnvRefArray, RESOURCEENVREF$44);
   }

   public void setResourceEnvRefArray(int i, ResourceEnvRefType resourceEnvRef) {
      this.generatedSetterHelperImpl(resourceEnvRef, RESOURCEENVREF$44, i, (short)2);
   }

   public ResourceEnvRefType insertNewResourceEnvRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceEnvRefType target = null;
         target = (ResourceEnvRefType)this.get_store().insert_element_user(RESOURCEENVREF$44, i);
         return target;
      }
   }

   public ResourceEnvRefType addNewResourceEnvRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceEnvRefType target = null;
         target = (ResourceEnvRefType)this.get_store().add_element_user(RESOURCEENVREF$44);
         return target;
      }
   }

   public void removeResourceEnvRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RESOURCEENVREF$44, i);
      }
   }

   public MessageDestinationRefType[] getMessageDestinationRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(MESSAGEDESTINATIONREF$46, targetList);
         MessageDestinationRefType[] result = new MessageDestinationRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public MessageDestinationRefType getMessageDestinationRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationRefType target = null;
         target = (MessageDestinationRefType)this.get_store().find_element_user(MESSAGEDESTINATIONREF$46, i);
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
         return this.get_store().count_elements(MESSAGEDESTINATIONREF$46);
      }
   }

   public void setMessageDestinationRefArray(MessageDestinationRefType[] messageDestinationRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(messageDestinationRefArray, MESSAGEDESTINATIONREF$46);
   }

   public void setMessageDestinationRefArray(int i, MessageDestinationRefType messageDestinationRef) {
      this.generatedSetterHelperImpl(messageDestinationRef, MESSAGEDESTINATIONREF$46, i, (short)2);
   }

   public MessageDestinationRefType insertNewMessageDestinationRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationRefType target = null;
         target = (MessageDestinationRefType)this.get_store().insert_element_user(MESSAGEDESTINATIONREF$46, i);
         return target;
      }
   }

   public MessageDestinationRefType addNewMessageDestinationRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationRefType target = null;
         target = (MessageDestinationRefType)this.get_store().add_element_user(MESSAGEDESTINATIONREF$46);
         return target;
      }
   }

   public void removeMessageDestinationRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MESSAGEDESTINATIONREF$46, i);
      }
   }

   public PersistenceContextRefType[] getPersistenceContextRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(PERSISTENCECONTEXTREF$48, targetList);
         PersistenceContextRefType[] result = new PersistenceContextRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public PersistenceContextRefType getPersistenceContextRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceContextRefType target = null;
         target = (PersistenceContextRefType)this.get_store().find_element_user(PERSISTENCECONTEXTREF$48, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfPersistenceContextRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PERSISTENCECONTEXTREF$48);
      }
   }

   public void setPersistenceContextRefArray(PersistenceContextRefType[] persistenceContextRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(persistenceContextRefArray, PERSISTENCECONTEXTREF$48);
   }

   public void setPersistenceContextRefArray(int i, PersistenceContextRefType persistenceContextRef) {
      this.generatedSetterHelperImpl(persistenceContextRef, PERSISTENCECONTEXTREF$48, i, (short)2);
   }

   public PersistenceContextRefType insertNewPersistenceContextRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceContextRefType target = null;
         target = (PersistenceContextRefType)this.get_store().insert_element_user(PERSISTENCECONTEXTREF$48, i);
         return target;
      }
   }

   public PersistenceContextRefType addNewPersistenceContextRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceContextRefType target = null;
         target = (PersistenceContextRefType)this.get_store().add_element_user(PERSISTENCECONTEXTREF$48);
         return target;
      }
   }

   public void removePersistenceContextRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PERSISTENCECONTEXTREF$48, i);
      }
   }

   public PersistenceUnitRefType[] getPersistenceUnitRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(PERSISTENCEUNITREF$50, targetList);
         PersistenceUnitRefType[] result = new PersistenceUnitRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public PersistenceUnitRefType getPersistenceUnitRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitRefType target = null;
         target = (PersistenceUnitRefType)this.get_store().find_element_user(PERSISTENCEUNITREF$50, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfPersistenceUnitRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PERSISTENCEUNITREF$50);
      }
   }

   public void setPersistenceUnitRefArray(PersistenceUnitRefType[] persistenceUnitRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(persistenceUnitRefArray, PERSISTENCEUNITREF$50);
   }

   public void setPersistenceUnitRefArray(int i, PersistenceUnitRefType persistenceUnitRef) {
      this.generatedSetterHelperImpl(persistenceUnitRef, PERSISTENCEUNITREF$50, i, (short)2);
   }

   public PersistenceUnitRefType insertNewPersistenceUnitRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitRefType target = null;
         target = (PersistenceUnitRefType)this.get_store().insert_element_user(PERSISTENCEUNITREF$50, i);
         return target;
      }
   }

   public PersistenceUnitRefType addNewPersistenceUnitRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitRefType target = null;
         target = (PersistenceUnitRefType)this.get_store().add_element_user(PERSISTENCEUNITREF$50);
         return target;
      }
   }

   public void removePersistenceUnitRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PERSISTENCEUNITREF$50, i);
      }
   }

   public LifecycleCallbackType[] getPostConstructArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(POSTCONSTRUCT$52, targetList);
         LifecycleCallbackType[] result = new LifecycleCallbackType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public LifecycleCallbackType getPostConstructArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LifecycleCallbackType target = null;
         target = (LifecycleCallbackType)this.get_store().find_element_user(POSTCONSTRUCT$52, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfPostConstructArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(POSTCONSTRUCT$52);
      }
   }

   public void setPostConstructArray(LifecycleCallbackType[] postConstructArray) {
      this.check_orphaned();
      this.arraySetterHelper(postConstructArray, POSTCONSTRUCT$52);
   }

   public void setPostConstructArray(int i, LifecycleCallbackType postConstruct) {
      this.generatedSetterHelperImpl(postConstruct, POSTCONSTRUCT$52, i, (short)2);
   }

   public LifecycleCallbackType insertNewPostConstruct(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LifecycleCallbackType target = null;
         target = (LifecycleCallbackType)this.get_store().insert_element_user(POSTCONSTRUCT$52, i);
         return target;
      }
   }

   public LifecycleCallbackType addNewPostConstruct() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LifecycleCallbackType target = null;
         target = (LifecycleCallbackType)this.get_store().add_element_user(POSTCONSTRUCT$52);
         return target;
      }
   }

   public void removePostConstruct(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(POSTCONSTRUCT$52, i);
      }
   }

   public LifecycleCallbackType[] getPreDestroyArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(PREDESTROY$54, targetList);
         LifecycleCallbackType[] result = new LifecycleCallbackType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public LifecycleCallbackType getPreDestroyArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LifecycleCallbackType target = null;
         target = (LifecycleCallbackType)this.get_store().find_element_user(PREDESTROY$54, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfPreDestroyArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PREDESTROY$54);
      }
   }

   public void setPreDestroyArray(LifecycleCallbackType[] preDestroyArray) {
      this.check_orphaned();
      this.arraySetterHelper(preDestroyArray, PREDESTROY$54);
   }

   public void setPreDestroyArray(int i, LifecycleCallbackType preDestroy) {
      this.generatedSetterHelperImpl(preDestroy, PREDESTROY$54, i, (short)2);
   }

   public LifecycleCallbackType insertNewPreDestroy(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LifecycleCallbackType target = null;
         target = (LifecycleCallbackType)this.get_store().insert_element_user(PREDESTROY$54, i);
         return target;
      }
   }

   public LifecycleCallbackType addNewPreDestroy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LifecycleCallbackType target = null;
         target = (LifecycleCallbackType)this.get_store().add_element_user(PREDESTROY$54);
         return target;
      }
   }

   public void removePreDestroy(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PREDESTROY$54, i);
      }
   }

   public DataSourceType[] getDataSourceArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(DATASOURCE$56, targetList);
         DataSourceType[] result = new DataSourceType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public DataSourceType getDataSourceArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DataSourceType target = null;
         target = (DataSourceType)this.get_store().find_element_user(DATASOURCE$56, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfDataSourceArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DATASOURCE$56);
      }
   }

   public void setDataSourceArray(DataSourceType[] dataSourceArray) {
      this.check_orphaned();
      this.arraySetterHelper(dataSourceArray, DATASOURCE$56);
   }

   public void setDataSourceArray(int i, DataSourceType dataSource) {
      this.generatedSetterHelperImpl(dataSource, DATASOURCE$56, i, (short)2);
   }

   public DataSourceType insertNewDataSource(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DataSourceType target = null;
         target = (DataSourceType)this.get_store().insert_element_user(DATASOURCE$56, i);
         return target;
      }
   }

   public DataSourceType addNewDataSource() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DataSourceType target = null;
         target = (DataSourceType)this.get_store().add_element_user(DATASOURCE$56);
         return target;
      }
   }

   public void removeDataSource(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DATASOURCE$56, i);
      }
   }

   public JmsConnectionFactoryType[] getJmsConnectionFactoryArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(JMSCONNECTIONFACTORY$58, targetList);
         JmsConnectionFactoryType[] result = new JmsConnectionFactoryType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public JmsConnectionFactoryType getJmsConnectionFactoryArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JmsConnectionFactoryType target = null;
         target = (JmsConnectionFactoryType)this.get_store().find_element_user(JMSCONNECTIONFACTORY$58, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfJmsConnectionFactoryArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(JMSCONNECTIONFACTORY$58);
      }
   }

   public void setJmsConnectionFactoryArray(JmsConnectionFactoryType[] jmsConnectionFactoryArray) {
      this.check_orphaned();
      this.arraySetterHelper(jmsConnectionFactoryArray, JMSCONNECTIONFACTORY$58);
   }

   public void setJmsConnectionFactoryArray(int i, JmsConnectionFactoryType jmsConnectionFactory) {
      this.generatedSetterHelperImpl(jmsConnectionFactory, JMSCONNECTIONFACTORY$58, i, (short)2);
   }

   public JmsConnectionFactoryType insertNewJmsConnectionFactory(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JmsConnectionFactoryType target = null;
         target = (JmsConnectionFactoryType)this.get_store().insert_element_user(JMSCONNECTIONFACTORY$58, i);
         return target;
      }
   }

   public JmsConnectionFactoryType addNewJmsConnectionFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JmsConnectionFactoryType target = null;
         target = (JmsConnectionFactoryType)this.get_store().add_element_user(JMSCONNECTIONFACTORY$58);
         return target;
      }
   }

   public void removeJmsConnectionFactory(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(JMSCONNECTIONFACTORY$58, i);
      }
   }

   public JmsDestinationType[] getJmsDestinationArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(JMSDESTINATION$60, targetList);
         JmsDestinationType[] result = new JmsDestinationType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public JmsDestinationType getJmsDestinationArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JmsDestinationType target = null;
         target = (JmsDestinationType)this.get_store().find_element_user(JMSDESTINATION$60, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfJmsDestinationArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(JMSDESTINATION$60);
      }
   }

   public void setJmsDestinationArray(JmsDestinationType[] jmsDestinationArray) {
      this.check_orphaned();
      this.arraySetterHelper(jmsDestinationArray, JMSDESTINATION$60);
   }

   public void setJmsDestinationArray(int i, JmsDestinationType jmsDestination) {
      this.generatedSetterHelperImpl(jmsDestination, JMSDESTINATION$60, i, (short)2);
   }

   public JmsDestinationType insertNewJmsDestination(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JmsDestinationType target = null;
         target = (JmsDestinationType)this.get_store().insert_element_user(JMSDESTINATION$60, i);
         return target;
      }
   }

   public JmsDestinationType addNewJmsDestination() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JmsDestinationType target = null;
         target = (JmsDestinationType)this.get_store().add_element_user(JMSDESTINATION$60);
         return target;
      }
   }

   public void removeJmsDestination(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(JMSDESTINATION$60, i);
      }
   }

   public MailSessionType[] getMailSessionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(MAILSESSION$62, targetList);
         MailSessionType[] result = new MailSessionType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public MailSessionType getMailSessionArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MailSessionType target = null;
         target = (MailSessionType)this.get_store().find_element_user(MAILSESSION$62, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfMailSessionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MAILSESSION$62);
      }
   }

   public void setMailSessionArray(MailSessionType[] mailSessionArray) {
      this.check_orphaned();
      this.arraySetterHelper(mailSessionArray, MAILSESSION$62);
   }

   public void setMailSessionArray(int i, MailSessionType mailSession) {
      this.generatedSetterHelperImpl(mailSession, MAILSESSION$62, i, (short)2);
   }

   public MailSessionType insertNewMailSession(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MailSessionType target = null;
         target = (MailSessionType)this.get_store().insert_element_user(MAILSESSION$62, i);
         return target;
      }
   }

   public MailSessionType addNewMailSession() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MailSessionType target = null;
         target = (MailSessionType)this.get_store().add_element_user(MAILSESSION$62);
         return target;
      }
   }

   public void removeMailSession(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MAILSESSION$62, i);
      }
   }

   public ConnectionFactoryResourceType[] getConnectionFactoryArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(CONNECTIONFACTORY$64, targetList);
         ConnectionFactoryResourceType[] result = new ConnectionFactoryResourceType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ConnectionFactoryResourceType getConnectionFactoryArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConnectionFactoryResourceType target = null;
         target = (ConnectionFactoryResourceType)this.get_store().find_element_user(CONNECTIONFACTORY$64, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfConnectionFactoryArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONNECTIONFACTORY$64);
      }
   }

   public void setConnectionFactoryArray(ConnectionFactoryResourceType[] connectionFactoryArray) {
      this.check_orphaned();
      this.arraySetterHelper(connectionFactoryArray, CONNECTIONFACTORY$64);
   }

   public void setConnectionFactoryArray(int i, ConnectionFactoryResourceType connectionFactory) {
      this.generatedSetterHelperImpl(connectionFactory, CONNECTIONFACTORY$64, i, (short)2);
   }

   public ConnectionFactoryResourceType insertNewConnectionFactory(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConnectionFactoryResourceType target = null;
         target = (ConnectionFactoryResourceType)this.get_store().insert_element_user(CONNECTIONFACTORY$64, i);
         return target;
      }
   }

   public ConnectionFactoryResourceType addNewConnectionFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConnectionFactoryResourceType target = null;
         target = (ConnectionFactoryResourceType)this.get_store().add_element_user(CONNECTIONFACTORY$64);
         return target;
      }
   }

   public void removeConnectionFactory(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONNECTIONFACTORY$64, i);
      }
   }

   public AdministeredObjectType[] getAdministeredObjectArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(ADMINISTEREDOBJECT$66, targetList);
         AdministeredObjectType[] result = new AdministeredObjectType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public AdministeredObjectType getAdministeredObjectArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AdministeredObjectType target = null;
         target = (AdministeredObjectType)this.get_store().find_element_user(ADMINISTEREDOBJECT$66, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfAdministeredObjectArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ADMINISTEREDOBJECT$66);
      }
   }

   public void setAdministeredObjectArray(AdministeredObjectType[] administeredObjectArray) {
      this.check_orphaned();
      this.arraySetterHelper(administeredObjectArray, ADMINISTEREDOBJECT$66);
   }

   public void setAdministeredObjectArray(int i, AdministeredObjectType administeredObject) {
      this.generatedSetterHelperImpl(administeredObject, ADMINISTEREDOBJECT$66, i, (short)2);
   }

   public AdministeredObjectType insertNewAdministeredObject(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AdministeredObjectType target = null;
         target = (AdministeredObjectType)this.get_store().insert_element_user(ADMINISTEREDOBJECT$66, i);
         return target;
      }
   }

   public AdministeredObjectType addNewAdministeredObject() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AdministeredObjectType target = null;
         target = (AdministeredObjectType)this.get_store().add_element_user(ADMINISTEREDOBJECT$66);
         return target;
      }
   }

   public void removeAdministeredObject(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ADMINISTEREDOBJECT$66, i);
      }
   }

   public SecurityRoleRefType[] getSecurityRoleRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(SECURITYROLEREF$68, targetList);
         SecurityRoleRefType[] result = new SecurityRoleRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public SecurityRoleRefType getSecurityRoleRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SecurityRoleRefType target = null;
         target = (SecurityRoleRefType)this.get_store().find_element_user(SECURITYROLEREF$68, i);
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
         return this.get_store().count_elements(SECURITYROLEREF$68);
      }
   }

   public void setSecurityRoleRefArray(SecurityRoleRefType[] securityRoleRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(securityRoleRefArray, SECURITYROLEREF$68);
   }

   public void setSecurityRoleRefArray(int i, SecurityRoleRefType securityRoleRef) {
      this.generatedSetterHelperImpl(securityRoleRef, SECURITYROLEREF$68, i, (short)2);
   }

   public SecurityRoleRefType insertNewSecurityRoleRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SecurityRoleRefType target = null;
         target = (SecurityRoleRefType)this.get_store().insert_element_user(SECURITYROLEREF$68, i);
         return target;
      }
   }

   public SecurityRoleRefType addNewSecurityRoleRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SecurityRoleRefType target = null;
         target = (SecurityRoleRefType)this.get_store().add_element_user(SECURITYROLEREF$68);
         return target;
      }
   }

   public void removeSecurityRoleRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SECURITYROLEREF$68, i);
      }
   }

   public SecurityIdentityType getSecurityIdentity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SecurityIdentityType target = null;
         target = (SecurityIdentityType)this.get_store().find_element_user(SECURITYIDENTITY$70, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetSecurityIdentity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SECURITYIDENTITY$70) != 0;
      }
   }

   public void setSecurityIdentity(SecurityIdentityType securityIdentity) {
      this.generatedSetterHelperImpl(securityIdentity, SECURITYIDENTITY$70, 0, (short)1);
   }

   public SecurityIdentityType addNewSecurityIdentity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SecurityIdentityType target = null;
         target = (SecurityIdentityType)this.get_store().add_element_user(SECURITYIDENTITY$70);
         return target;
      }
   }

   public void unsetSecurityIdentity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SECURITYIDENTITY$70, 0);
      }
   }

   public QueryType[] getQueryArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(QUERY$72, targetList);
         QueryType[] result = new QueryType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public QueryType getQueryArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         QueryType target = null;
         target = (QueryType)this.get_store().find_element_user(QUERY$72, i);
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
         return this.get_store().count_elements(QUERY$72);
      }
   }

   public void setQueryArray(QueryType[] queryArray) {
      this.check_orphaned();
      this.arraySetterHelper(queryArray, QUERY$72);
   }

   public void setQueryArray(int i, QueryType query) {
      this.generatedSetterHelperImpl(query, QUERY$72, i, (short)2);
   }

   public QueryType insertNewQuery(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         QueryType target = null;
         target = (QueryType)this.get_store().insert_element_user(QUERY$72, i);
         return target;
      }
   }

   public QueryType addNewQuery() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         QueryType target = null;
         target = (QueryType)this.get_store().add_element_user(QUERY$72);
         return target;
      }
   }

   public void removeQuery(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(QUERY$72, i);
      }
   }

   public java.lang.String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$74);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$74);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$74) != null;
      }
   }

   public void setId(java.lang.String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$74);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$74);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$74);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$74);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$74);
      }
   }
}
