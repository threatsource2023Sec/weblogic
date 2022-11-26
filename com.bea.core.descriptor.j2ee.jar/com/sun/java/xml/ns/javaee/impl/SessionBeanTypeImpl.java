package com.sun.java.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.sun.java.xml.ns.javaee.AroundInvokeType;
import com.sun.java.xml.ns.javaee.AroundTimeoutType;
import com.sun.java.xml.ns.javaee.AsyncMethodType;
import com.sun.java.xml.ns.javaee.ConcurrencyManagementTypeType;
import com.sun.java.xml.ns.javaee.ConcurrentMethodType;
import com.sun.java.xml.ns.javaee.DataSourceType;
import com.sun.java.xml.ns.javaee.DependsOnType;
import com.sun.java.xml.ns.javaee.DescriptionType;
import com.sun.java.xml.ns.javaee.DisplayNameType;
import com.sun.java.xml.ns.javaee.EjbClassType;
import com.sun.java.xml.ns.javaee.EjbLocalRefType;
import com.sun.java.xml.ns.javaee.EjbNameType;
import com.sun.java.xml.ns.javaee.EjbRefType;
import com.sun.java.xml.ns.javaee.EmptyType;
import com.sun.java.xml.ns.javaee.EnvEntryType;
import com.sun.java.xml.ns.javaee.FullyQualifiedClassType;
import com.sun.java.xml.ns.javaee.HomeType;
import com.sun.java.xml.ns.javaee.IconType;
import com.sun.java.xml.ns.javaee.InitMethodType;
import com.sun.java.xml.ns.javaee.LifecycleCallbackType;
import com.sun.java.xml.ns.javaee.LocalHomeType;
import com.sun.java.xml.ns.javaee.LocalType;
import com.sun.java.xml.ns.javaee.MessageDestinationRefType;
import com.sun.java.xml.ns.javaee.NamedMethodType;
import com.sun.java.xml.ns.javaee.PersistenceContextRefType;
import com.sun.java.xml.ns.javaee.PersistenceUnitRefType;
import com.sun.java.xml.ns.javaee.RemoteType;
import com.sun.java.xml.ns.javaee.RemoveMethodType;
import com.sun.java.xml.ns.javaee.ResourceEnvRefType;
import com.sun.java.xml.ns.javaee.ResourceRefType;
import com.sun.java.xml.ns.javaee.SecurityIdentityType;
import com.sun.java.xml.ns.javaee.SecurityRoleRefType;
import com.sun.java.xml.ns.javaee.ServiceRefType;
import com.sun.java.xml.ns.javaee.SessionBeanType;
import com.sun.java.xml.ns.javaee.SessionTypeType;
import com.sun.java.xml.ns.javaee.StatefulTimeoutType;
import com.sun.java.xml.ns.javaee.TimerType;
import com.sun.java.xml.ns.javaee.TransactionTypeType;
import com.sun.java.xml.ns.javaee.TrueFalseType;
import com.sun.java.xml.ns.javaee.XsdStringType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class SessionBeanTypeImpl extends XmlComplexContentImpl implements SessionBeanType {
   private static final long serialVersionUID = 1L;
   private static final QName DESCRIPTION$0 = new QName("http://java.sun.com/xml/ns/javaee", "description");
   private static final QName DISPLAYNAME$2 = new QName("http://java.sun.com/xml/ns/javaee", "display-name");
   private static final QName ICON$4 = new QName("http://java.sun.com/xml/ns/javaee", "icon");
   private static final QName EJBNAME$6 = new QName("http://java.sun.com/xml/ns/javaee", "ejb-name");
   private static final QName MAPPEDNAME$8 = new QName("http://java.sun.com/xml/ns/javaee", "mapped-name");
   private static final QName HOME$10 = new QName("http://java.sun.com/xml/ns/javaee", "home");
   private static final QName REMOTE$12 = new QName("http://java.sun.com/xml/ns/javaee", "remote");
   private static final QName LOCALHOME$14 = new QName("http://java.sun.com/xml/ns/javaee", "local-home");
   private static final QName LOCAL$16 = new QName("http://java.sun.com/xml/ns/javaee", "local");
   private static final QName BUSINESSLOCAL$18 = new QName("http://java.sun.com/xml/ns/javaee", "business-local");
   private static final QName BUSINESSREMOTE$20 = new QName("http://java.sun.com/xml/ns/javaee", "business-remote");
   private static final QName LOCALBEAN$22 = new QName("http://java.sun.com/xml/ns/javaee", "local-bean");
   private static final QName SERVICEENDPOINT$24 = new QName("http://java.sun.com/xml/ns/javaee", "service-endpoint");
   private static final QName EJBCLASS$26 = new QName("http://java.sun.com/xml/ns/javaee", "ejb-class");
   private static final QName SESSIONTYPE$28 = new QName("http://java.sun.com/xml/ns/javaee", "session-type");
   private static final QName STATEFULTIMEOUT$30 = new QName("http://java.sun.com/xml/ns/javaee", "stateful-timeout");
   private static final QName TIMEOUTMETHOD$32 = new QName("http://java.sun.com/xml/ns/javaee", "timeout-method");
   private static final QName TIMER$34 = new QName("http://java.sun.com/xml/ns/javaee", "timer");
   private static final QName INITONSTARTUP$36 = new QName("http://java.sun.com/xml/ns/javaee", "init-on-startup");
   private static final QName CONCURRENCYMANAGEMENTTYPE$38 = new QName("http://java.sun.com/xml/ns/javaee", "concurrency-management-type");
   private static final QName CONCURRENTMETHOD$40 = new QName("http://java.sun.com/xml/ns/javaee", "concurrent-method");
   private static final QName DEPENDSON$42 = new QName("http://java.sun.com/xml/ns/javaee", "depends-on");
   private static final QName INITMETHOD$44 = new QName("http://java.sun.com/xml/ns/javaee", "init-method");
   private static final QName REMOVEMETHOD$46 = new QName("http://java.sun.com/xml/ns/javaee", "remove-method");
   private static final QName ASYNCMETHOD$48 = new QName("http://java.sun.com/xml/ns/javaee", "async-method");
   private static final QName TRANSACTIONTYPE$50 = new QName("http://java.sun.com/xml/ns/javaee", "transaction-type");
   private static final QName AFTERBEGINMETHOD$52 = new QName("http://java.sun.com/xml/ns/javaee", "after-begin-method");
   private static final QName BEFORECOMPLETIONMETHOD$54 = new QName("http://java.sun.com/xml/ns/javaee", "before-completion-method");
   private static final QName AFTERCOMPLETIONMETHOD$56 = new QName("http://java.sun.com/xml/ns/javaee", "after-completion-method");
   private static final QName AROUNDINVOKE$58 = new QName("http://java.sun.com/xml/ns/javaee", "around-invoke");
   private static final QName AROUNDTIMEOUT$60 = new QName("http://java.sun.com/xml/ns/javaee", "around-timeout");
   private static final QName ENVENTRY$62 = new QName("http://java.sun.com/xml/ns/javaee", "env-entry");
   private static final QName EJBREF$64 = new QName("http://java.sun.com/xml/ns/javaee", "ejb-ref");
   private static final QName EJBLOCALREF$66 = new QName("http://java.sun.com/xml/ns/javaee", "ejb-local-ref");
   private static final QName SERVICEREF$68 = new QName("http://java.sun.com/xml/ns/javaee", "service-ref");
   private static final QName RESOURCEREF$70 = new QName("http://java.sun.com/xml/ns/javaee", "resource-ref");
   private static final QName RESOURCEENVREF$72 = new QName("http://java.sun.com/xml/ns/javaee", "resource-env-ref");
   private static final QName MESSAGEDESTINATIONREF$74 = new QName("http://java.sun.com/xml/ns/javaee", "message-destination-ref");
   private static final QName PERSISTENCECONTEXTREF$76 = new QName("http://java.sun.com/xml/ns/javaee", "persistence-context-ref");
   private static final QName PERSISTENCEUNITREF$78 = new QName("http://java.sun.com/xml/ns/javaee", "persistence-unit-ref");
   private static final QName POSTCONSTRUCT$80 = new QName("http://java.sun.com/xml/ns/javaee", "post-construct");
   private static final QName PREDESTROY$82 = new QName("http://java.sun.com/xml/ns/javaee", "pre-destroy");
   private static final QName DATASOURCE$84 = new QName("http://java.sun.com/xml/ns/javaee", "data-source");
   private static final QName POSTACTIVATE$86 = new QName("http://java.sun.com/xml/ns/javaee", "post-activate");
   private static final QName PREPASSIVATE$88 = new QName("http://java.sun.com/xml/ns/javaee", "pre-passivate");
   private static final QName SECURITYROLEREF$90 = new QName("http://java.sun.com/xml/ns/javaee", "security-role-ref");
   private static final QName SECURITYIDENTITY$92 = new QName("http://java.sun.com/xml/ns/javaee", "security-identity");
   private static final QName ID$94 = new QName("", "id");

   public SessionBeanTypeImpl(SchemaType sType) {
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

   public FullyQualifiedClassType[] getBusinessLocalArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(BUSINESSLOCAL$18, targetList);
         FullyQualifiedClassType[] result = new FullyQualifiedClassType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public FullyQualifiedClassType getBusinessLocalArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().find_element_user(BUSINESSLOCAL$18, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfBusinessLocalArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(BUSINESSLOCAL$18);
      }
   }

   public void setBusinessLocalArray(FullyQualifiedClassType[] businessLocalArray) {
      this.check_orphaned();
      this.arraySetterHelper(businessLocalArray, BUSINESSLOCAL$18);
   }

   public void setBusinessLocalArray(int i, FullyQualifiedClassType businessLocal) {
      this.generatedSetterHelperImpl(businessLocal, BUSINESSLOCAL$18, i, (short)2);
   }

   public FullyQualifiedClassType insertNewBusinessLocal(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().insert_element_user(BUSINESSLOCAL$18, i);
         return target;
      }
   }

   public FullyQualifiedClassType addNewBusinessLocal() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().add_element_user(BUSINESSLOCAL$18);
         return target;
      }
   }

   public void removeBusinessLocal(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(BUSINESSLOCAL$18, i);
      }
   }

   public FullyQualifiedClassType[] getBusinessRemoteArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(BUSINESSREMOTE$20, targetList);
         FullyQualifiedClassType[] result = new FullyQualifiedClassType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public FullyQualifiedClassType getBusinessRemoteArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().find_element_user(BUSINESSREMOTE$20, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfBusinessRemoteArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(BUSINESSREMOTE$20);
      }
   }

   public void setBusinessRemoteArray(FullyQualifiedClassType[] businessRemoteArray) {
      this.check_orphaned();
      this.arraySetterHelper(businessRemoteArray, BUSINESSREMOTE$20);
   }

   public void setBusinessRemoteArray(int i, FullyQualifiedClassType businessRemote) {
      this.generatedSetterHelperImpl(businessRemote, BUSINESSREMOTE$20, i, (short)2);
   }

   public FullyQualifiedClassType insertNewBusinessRemote(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().insert_element_user(BUSINESSREMOTE$20, i);
         return target;
      }
   }

   public FullyQualifiedClassType addNewBusinessRemote() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().add_element_user(BUSINESSREMOTE$20);
         return target;
      }
   }

   public void removeBusinessRemote(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(BUSINESSREMOTE$20, i);
      }
   }

   public EmptyType getLocalBean() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EmptyType target = null;
         target = (EmptyType)this.get_store().find_element_user(LOCALBEAN$22, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetLocalBean() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LOCALBEAN$22) != 0;
      }
   }

   public void setLocalBean(EmptyType localBean) {
      this.generatedSetterHelperImpl(localBean, LOCALBEAN$22, 0, (short)1);
   }

   public EmptyType addNewLocalBean() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EmptyType target = null;
         target = (EmptyType)this.get_store().add_element_user(LOCALBEAN$22);
         return target;
      }
   }

   public void unsetLocalBean() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LOCALBEAN$22, 0);
      }
   }

   public FullyQualifiedClassType getServiceEndpoint() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().find_element_user(SERVICEENDPOINT$24, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetServiceEndpoint() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SERVICEENDPOINT$24) != 0;
      }
   }

   public void setServiceEndpoint(FullyQualifiedClassType serviceEndpoint) {
      this.generatedSetterHelperImpl(serviceEndpoint, SERVICEENDPOINT$24, 0, (short)1);
   }

   public FullyQualifiedClassType addNewServiceEndpoint() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().add_element_user(SERVICEENDPOINT$24);
         return target;
      }
   }

   public void unsetServiceEndpoint() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SERVICEENDPOINT$24, 0);
      }
   }

   public EjbClassType getEjbClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbClassType target = null;
         target = (EjbClassType)this.get_store().find_element_user(EJBCLASS$26, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetEjbClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(EJBCLASS$26) != 0;
      }
   }

   public void setEjbClass(EjbClassType ejbClass) {
      this.generatedSetterHelperImpl(ejbClass, EJBCLASS$26, 0, (short)1);
   }

   public EjbClassType addNewEjbClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbClassType target = null;
         target = (EjbClassType)this.get_store().add_element_user(EJBCLASS$26);
         return target;
      }
   }

   public void unsetEjbClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(EJBCLASS$26, 0);
      }
   }

   public SessionTypeType getSessionType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SessionTypeType target = null;
         target = (SessionTypeType)this.get_store().find_element_user(SESSIONTYPE$28, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetSessionType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SESSIONTYPE$28) != 0;
      }
   }

   public void setSessionType(SessionTypeType sessionType) {
      this.generatedSetterHelperImpl(sessionType, SESSIONTYPE$28, 0, (short)1);
   }

   public SessionTypeType addNewSessionType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SessionTypeType target = null;
         target = (SessionTypeType)this.get_store().add_element_user(SESSIONTYPE$28);
         return target;
      }
   }

   public void unsetSessionType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SESSIONTYPE$28, 0);
      }
   }

   public StatefulTimeoutType getStatefulTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         StatefulTimeoutType target = null;
         target = (StatefulTimeoutType)this.get_store().find_element_user(STATEFULTIMEOUT$30, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetStatefulTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(STATEFULTIMEOUT$30) != 0;
      }
   }

   public void setStatefulTimeout(StatefulTimeoutType statefulTimeout) {
      this.generatedSetterHelperImpl(statefulTimeout, STATEFULTIMEOUT$30, 0, (short)1);
   }

   public StatefulTimeoutType addNewStatefulTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         StatefulTimeoutType target = null;
         target = (StatefulTimeoutType)this.get_store().add_element_user(STATEFULTIMEOUT$30);
         return target;
      }
   }

   public void unsetStatefulTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(STATEFULTIMEOUT$30, 0);
      }
   }

   public NamedMethodType getTimeoutMethod() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NamedMethodType target = null;
         target = (NamedMethodType)this.get_store().find_element_user(TIMEOUTMETHOD$32, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetTimeoutMethod() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TIMEOUTMETHOD$32) != 0;
      }
   }

   public void setTimeoutMethod(NamedMethodType timeoutMethod) {
      this.generatedSetterHelperImpl(timeoutMethod, TIMEOUTMETHOD$32, 0, (short)1);
   }

   public NamedMethodType addNewTimeoutMethod() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NamedMethodType target = null;
         target = (NamedMethodType)this.get_store().add_element_user(TIMEOUTMETHOD$32);
         return target;
      }
   }

   public void unsetTimeoutMethod() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TIMEOUTMETHOD$32, 0);
      }
   }

   public TimerType[] getTimerArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(TIMER$34, targetList);
         TimerType[] result = new TimerType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public TimerType getTimerArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TimerType target = null;
         target = (TimerType)this.get_store().find_element_user(TIMER$34, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfTimerArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TIMER$34);
      }
   }

   public void setTimerArray(TimerType[] timerArray) {
      this.check_orphaned();
      this.arraySetterHelper(timerArray, TIMER$34);
   }

   public void setTimerArray(int i, TimerType timer) {
      this.generatedSetterHelperImpl(timer, TIMER$34, i, (short)2);
   }

   public TimerType insertNewTimer(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TimerType target = null;
         target = (TimerType)this.get_store().insert_element_user(TIMER$34, i);
         return target;
      }
   }

   public TimerType addNewTimer() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TimerType target = null;
         target = (TimerType)this.get_store().add_element_user(TIMER$34);
         return target;
      }
   }

   public void removeTimer(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TIMER$34, i);
      }
   }

   public TrueFalseType getInitOnStartup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(INITONSTARTUP$36, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetInitOnStartup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(INITONSTARTUP$36) != 0;
      }
   }

   public void setInitOnStartup(TrueFalseType initOnStartup) {
      this.generatedSetterHelperImpl(initOnStartup, INITONSTARTUP$36, 0, (short)1);
   }

   public TrueFalseType addNewInitOnStartup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(INITONSTARTUP$36);
         return target;
      }
   }

   public void unsetInitOnStartup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(INITONSTARTUP$36, 0);
      }
   }

   public ConcurrencyManagementTypeType getConcurrencyManagementType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConcurrencyManagementTypeType target = null;
         target = (ConcurrencyManagementTypeType)this.get_store().find_element_user(CONCURRENCYMANAGEMENTTYPE$38, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetConcurrencyManagementType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONCURRENCYMANAGEMENTTYPE$38) != 0;
      }
   }

   public void setConcurrencyManagementType(ConcurrencyManagementTypeType concurrencyManagementType) {
      this.generatedSetterHelperImpl(concurrencyManagementType, CONCURRENCYMANAGEMENTTYPE$38, 0, (short)1);
   }

   public ConcurrencyManagementTypeType addNewConcurrencyManagementType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConcurrencyManagementTypeType target = null;
         target = (ConcurrencyManagementTypeType)this.get_store().add_element_user(CONCURRENCYMANAGEMENTTYPE$38);
         return target;
      }
   }

   public void unsetConcurrencyManagementType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONCURRENCYMANAGEMENTTYPE$38, 0);
      }
   }

   public ConcurrentMethodType[] getConcurrentMethodArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(CONCURRENTMETHOD$40, targetList);
         ConcurrentMethodType[] result = new ConcurrentMethodType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ConcurrentMethodType getConcurrentMethodArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConcurrentMethodType target = null;
         target = (ConcurrentMethodType)this.get_store().find_element_user(CONCURRENTMETHOD$40, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfConcurrentMethodArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONCURRENTMETHOD$40);
      }
   }

   public void setConcurrentMethodArray(ConcurrentMethodType[] concurrentMethodArray) {
      this.check_orphaned();
      this.arraySetterHelper(concurrentMethodArray, CONCURRENTMETHOD$40);
   }

   public void setConcurrentMethodArray(int i, ConcurrentMethodType concurrentMethod) {
      this.generatedSetterHelperImpl(concurrentMethod, CONCURRENTMETHOD$40, i, (short)2);
   }

   public ConcurrentMethodType insertNewConcurrentMethod(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConcurrentMethodType target = null;
         target = (ConcurrentMethodType)this.get_store().insert_element_user(CONCURRENTMETHOD$40, i);
         return target;
      }
   }

   public ConcurrentMethodType addNewConcurrentMethod() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConcurrentMethodType target = null;
         target = (ConcurrentMethodType)this.get_store().add_element_user(CONCURRENTMETHOD$40);
         return target;
      }
   }

   public void removeConcurrentMethod(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONCURRENTMETHOD$40, i);
      }
   }

   public DependsOnType getDependsOn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DependsOnType target = null;
         target = (DependsOnType)this.get_store().find_element_user(DEPENDSON$42, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetDependsOn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DEPENDSON$42) != 0;
      }
   }

   public void setDependsOn(DependsOnType dependsOn) {
      this.generatedSetterHelperImpl(dependsOn, DEPENDSON$42, 0, (short)1);
   }

   public DependsOnType addNewDependsOn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DependsOnType target = null;
         target = (DependsOnType)this.get_store().add_element_user(DEPENDSON$42);
         return target;
      }
   }

   public void unsetDependsOn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DEPENDSON$42, 0);
      }
   }

   public InitMethodType[] getInitMethodArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(INITMETHOD$44, targetList);
         InitMethodType[] result = new InitMethodType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public InitMethodType getInitMethodArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         InitMethodType target = null;
         target = (InitMethodType)this.get_store().find_element_user(INITMETHOD$44, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfInitMethodArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(INITMETHOD$44);
      }
   }

   public void setInitMethodArray(InitMethodType[] initMethodArray) {
      this.check_orphaned();
      this.arraySetterHelper(initMethodArray, INITMETHOD$44);
   }

   public void setInitMethodArray(int i, InitMethodType initMethod) {
      this.generatedSetterHelperImpl(initMethod, INITMETHOD$44, i, (short)2);
   }

   public InitMethodType insertNewInitMethod(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         InitMethodType target = null;
         target = (InitMethodType)this.get_store().insert_element_user(INITMETHOD$44, i);
         return target;
      }
   }

   public InitMethodType addNewInitMethod() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         InitMethodType target = null;
         target = (InitMethodType)this.get_store().add_element_user(INITMETHOD$44);
         return target;
      }
   }

   public void removeInitMethod(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(INITMETHOD$44, i);
      }
   }

   public RemoveMethodType[] getRemoveMethodArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(REMOVEMETHOD$46, targetList);
         RemoveMethodType[] result = new RemoveMethodType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public RemoveMethodType getRemoveMethodArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         RemoveMethodType target = null;
         target = (RemoveMethodType)this.get_store().find_element_user(REMOVEMETHOD$46, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfRemoveMethodArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(REMOVEMETHOD$46);
      }
   }

   public void setRemoveMethodArray(RemoveMethodType[] removeMethodArray) {
      this.check_orphaned();
      this.arraySetterHelper(removeMethodArray, REMOVEMETHOD$46);
   }

   public void setRemoveMethodArray(int i, RemoveMethodType removeMethod) {
      this.generatedSetterHelperImpl(removeMethod, REMOVEMETHOD$46, i, (short)2);
   }

   public RemoveMethodType insertNewRemoveMethod(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         RemoveMethodType target = null;
         target = (RemoveMethodType)this.get_store().insert_element_user(REMOVEMETHOD$46, i);
         return target;
      }
   }

   public RemoveMethodType addNewRemoveMethod() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         RemoveMethodType target = null;
         target = (RemoveMethodType)this.get_store().add_element_user(REMOVEMETHOD$46);
         return target;
      }
   }

   public void removeRemoveMethod(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(REMOVEMETHOD$46, i);
      }
   }

   public AsyncMethodType[] getAsyncMethodArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(ASYNCMETHOD$48, targetList);
         AsyncMethodType[] result = new AsyncMethodType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public AsyncMethodType getAsyncMethodArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AsyncMethodType target = null;
         target = (AsyncMethodType)this.get_store().find_element_user(ASYNCMETHOD$48, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfAsyncMethodArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ASYNCMETHOD$48);
      }
   }

   public void setAsyncMethodArray(AsyncMethodType[] asyncMethodArray) {
      this.check_orphaned();
      this.arraySetterHelper(asyncMethodArray, ASYNCMETHOD$48);
   }

   public void setAsyncMethodArray(int i, AsyncMethodType asyncMethod) {
      this.generatedSetterHelperImpl(asyncMethod, ASYNCMETHOD$48, i, (short)2);
   }

   public AsyncMethodType insertNewAsyncMethod(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AsyncMethodType target = null;
         target = (AsyncMethodType)this.get_store().insert_element_user(ASYNCMETHOD$48, i);
         return target;
      }
   }

   public AsyncMethodType addNewAsyncMethod() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AsyncMethodType target = null;
         target = (AsyncMethodType)this.get_store().add_element_user(ASYNCMETHOD$48);
         return target;
      }
   }

   public void removeAsyncMethod(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ASYNCMETHOD$48, i);
      }
   }

   public TransactionTypeType getTransactionType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TransactionTypeType target = null;
         target = (TransactionTypeType)this.get_store().find_element_user(TRANSACTIONTYPE$50, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetTransactionType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TRANSACTIONTYPE$50) != 0;
      }
   }

   public void setTransactionType(TransactionTypeType transactionType) {
      this.generatedSetterHelperImpl(transactionType, TRANSACTIONTYPE$50, 0, (short)1);
   }

   public TransactionTypeType addNewTransactionType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TransactionTypeType target = null;
         target = (TransactionTypeType)this.get_store().add_element_user(TRANSACTIONTYPE$50);
         return target;
      }
   }

   public void unsetTransactionType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TRANSACTIONTYPE$50, 0);
      }
   }

   public NamedMethodType getAfterBeginMethod() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NamedMethodType target = null;
         target = (NamedMethodType)this.get_store().find_element_user(AFTERBEGINMETHOD$52, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetAfterBeginMethod() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(AFTERBEGINMETHOD$52) != 0;
      }
   }

   public void setAfterBeginMethod(NamedMethodType afterBeginMethod) {
      this.generatedSetterHelperImpl(afterBeginMethod, AFTERBEGINMETHOD$52, 0, (short)1);
   }

   public NamedMethodType addNewAfterBeginMethod() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NamedMethodType target = null;
         target = (NamedMethodType)this.get_store().add_element_user(AFTERBEGINMETHOD$52);
         return target;
      }
   }

   public void unsetAfterBeginMethod() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(AFTERBEGINMETHOD$52, 0);
      }
   }

   public NamedMethodType getBeforeCompletionMethod() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NamedMethodType target = null;
         target = (NamedMethodType)this.get_store().find_element_user(BEFORECOMPLETIONMETHOD$54, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetBeforeCompletionMethod() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(BEFORECOMPLETIONMETHOD$54) != 0;
      }
   }

   public void setBeforeCompletionMethod(NamedMethodType beforeCompletionMethod) {
      this.generatedSetterHelperImpl(beforeCompletionMethod, BEFORECOMPLETIONMETHOD$54, 0, (short)1);
   }

   public NamedMethodType addNewBeforeCompletionMethod() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NamedMethodType target = null;
         target = (NamedMethodType)this.get_store().add_element_user(BEFORECOMPLETIONMETHOD$54);
         return target;
      }
   }

   public void unsetBeforeCompletionMethod() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(BEFORECOMPLETIONMETHOD$54, 0);
      }
   }

   public NamedMethodType getAfterCompletionMethod() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NamedMethodType target = null;
         target = (NamedMethodType)this.get_store().find_element_user(AFTERCOMPLETIONMETHOD$56, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetAfterCompletionMethod() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(AFTERCOMPLETIONMETHOD$56) != 0;
      }
   }

   public void setAfterCompletionMethod(NamedMethodType afterCompletionMethod) {
      this.generatedSetterHelperImpl(afterCompletionMethod, AFTERCOMPLETIONMETHOD$56, 0, (short)1);
   }

   public NamedMethodType addNewAfterCompletionMethod() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NamedMethodType target = null;
         target = (NamedMethodType)this.get_store().add_element_user(AFTERCOMPLETIONMETHOD$56);
         return target;
      }
   }

   public void unsetAfterCompletionMethod() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(AFTERCOMPLETIONMETHOD$56, 0);
      }
   }

   public AroundInvokeType[] getAroundInvokeArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(AROUNDINVOKE$58, targetList);
         AroundInvokeType[] result = new AroundInvokeType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public AroundInvokeType getAroundInvokeArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AroundInvokeType target = null;
         target = (AroundInvokeType)this.get_store().find_element_user(AROUNDINVOKE$58, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfAroundInvokeArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(AROUNDINVOKE$58);
      }
   }

   public void setAroundInvokeArray(AroundInvokeType[] aroundInvokeArray) {
      this.check_orphaned();
      this.arraySetterHelper(aroundInvokeArray, AROUNDINVOKE$58);
   }

   public void setAroundInvokeArray(int i, AroundInvokeType aroundInvoke) {
      this.generatedSetterHelperImpl(aroundInvoke, AROUNDINVOKE$58, i, (short)2);
   }

   public AroundInvokeType insertNewAroundInvoke(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AroundInvokeType target = null;
         target = (AroundInvokeType)this.get_store().insert_element_user(AROUNDINVOKE$58, i);
         return target;
      }
   }

   public AroundInvokeType addNewAroundInvoke() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AroundInvokeType target = null;
         target = (AroundInvokeType)this.get_store().add_element_user(AROUNDINVOKE$58);
         return target;
      }
   }

   public void removeAroundInvoke(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(AROUNDINVOKE$58, i);
      }
   }

   public AroundTimeoutType[] getAroundTimeoutArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(AROUNDTIMEOUT$60, targetList);
         AroundTimeoutType[] result = new AroundTimeoutType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public AroundTimeoutType getAroundTimeoutArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AroundTimeoutType target = null;
         target = (AroundTimeoutType)this.get_store().find_element_user(AROUNDTIMEOUT$60, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfAroundTimeoutArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(AROUNDTIMEOUT$60);
      }
   }

   public void setAroundTimeoutArray(AroundTimeoutType[] aroundTimeoutArray) {
      this.check_orphaned();
      this.arraySetterHelper(aroundTimeoutArray, AROUNDTIMEOUT$60);
   }

   public void setAroundTimeoutArray(int i, AroundTimeoutType aroundTimeout) {
      this.generatedSetterHelperImpl(aroundTimeout, AROUNDTIMEOUT$60, i, (short)2);
   }

   public AroundTimeoutType insertNewAroundTimeout(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AroundTimeoutType target = null;
         target = (AroundTimeoutType)this.get_store().insert_element_user(AROUNDTIMEOUT$60, i);
         return target;
      }
   }

   public AroundTimeoutType addNewAroundTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AroundTimeoutType target = null;
         target = (AroundTimeoutType)this.get_store().add_element_user(AROUNDTIMEOUT$60);
         return target;
      }
   }

   public void removeAroundTimeout(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(AROUNDTIMEOUT$60, i);
      }
   }

   public EnvEntryType[] getEnvEntryArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(ENVENTRY$62, targetList);
         EnvEntryType[] result = new EnvEntryType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public EnvEntryType getEnvEntryArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EnvEntryType target = null;
         target = (EnvEntryType)this.get_store().find_element_user(ENVENTRY$62, i);
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
         return this.get_store().count_elements(ENVENTRY$62);
      }
   }

   public void setEnvEntryArray(EnvEntryType[] envEntryArray) {
      this.check_orphaned();
      this.arraySetterHelper(envEntryArray, ENVENTRY$62);
   }

   public void setEnvEntryArray(int i, EnvEntryType envEntry) {
      this.generatedSetterHelperImpl(envEntry, ENVENTRY$62, i, (short)2);
   }

   public EnvEntryType insertNewEnvEntry(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EnvEntryType target = null;
         target = (EnvEntryType)this.get_store().insert_element_user(ENVENTRY$62, i);
         return target;
      }
   }

   public EnvEntryType addNewEnvEntry() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EnvEntryType target = null;
         target = (EnvEntryType)this.get_store().add_element_user(ENVENTRY$62);
         return target;
      }
   }

   public void removeEnvEntry(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ENVENTRY$62, i);
      }
   }

   public EjbRefType[] getEjbRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(EJBREF$64, targetList);
         EjbRefType[] result = new EjbRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public EjbRefType getEjbRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbRefType target = null;
         target = (EjbRefType)this.get_store().find_element_user(EJBREF$64, i);
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
         return this.get_store().count_elements(EJBREF$64);
      }
   }

   public void setEjbRefArray(EjbRefType[] ejbRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(ejbRefArray, EJBREF$64);
   }

   public void setEjbRefArray(int i, EjbRefType ejbRef) {
      this.generatedSetterHelperImpl(ejbRef, EJBREF$64, i, (short)2);
   }

   public EjbRefType insertNewEjbRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbRefType target = null;
         target = (EjbRefType)this.get_store().insert_element_user(EJBREF$64, i);
         return target;
      }
   }

   public EjbRefType addNewEjbRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbRefType target = null;
         target = (EjbRefType)this.get_store().add_element_user(EJBREF$64);
         return target;
      }
   }

   public void removeEjbRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(EJBREF$64, i);
      }
   }

   public EjbLocalRefType[] getEjbLocalRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(EJBLOCALREF$66, targetList);
         EjbLocalRefType[] result = new EjbLocalRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public EjbLocalRefType getEjbLocalRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbLocalRefType target = null;
         target = (EjbLocalRefType)this.get_store().find_element_user(EJBLOCALREF$66, i);
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
         return this.get_store().count_elements(EJBLOCALREF$66);
      }
   }

   public void setEjbLocalRefArray(EjbLocalRefType[] ejbLocalRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(ejbLocalRefArray, EJBLOCALREF$66);
   }

   public void setEjbLocalRefArray(int i, EjbLocalRefType ejbLocalRef) {
      this.generatedSetterHelperImpl(ejbLocalRef, EJBLOCALREF$66, i, (short)2);
   }

   public EjbLocalRefType insertNewEjbLocalRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbLocalRefType target = null;
         target = (EjbLocalRefType)this.get_store().insert_element_user(EJBLOCALREF$66, i);
         return target;
      }
   }

   public EjbLocalRefType addNewEjbLocalRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbLocalRefType target = null;
         target = (EjbLocalRefType)this.get_store().add_element_user(EJBLOCALREF$66);
         return target;
      }
   }

   public void removeEjbLocalRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(EJBLOCALREF$66, i);
      }
   }

   public ServiceRefType[] getServiceRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(SERVICEREF$68, targetList);
         ServiceRefType[] result = new ServiceRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ServiceRefType getServiceRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServiceRefType target = null;
         target = (ServiceRefType)this.get_store().find_element_user(SERVICEREF$68, i);
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
         return this.get_store().count_elements(SERVICEREF$68);
      }
   }

   public void setServiceRefArray(ServiceRefType[] serviceRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(serviceRefArray, SERVICEREF$68);
   }

   public void setServiceRefArray(int i, ServiceRefType serviceRef) {
      this.generatedSetterHelperImpl(serviceRef, SERVICEREF$68, i, (short)2);
   }

   public ServiceRefType insertNewServiceRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServiceRefType target = null;
         target = (ServiceRefType)this.get_store().insert_element_user(SERVICEREF$68, i);
         return target;
      }
   }

   public ServiceRefType addNewServiceRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServiceRefType target = null;
         target = (ServiceRefType)this.get_store().add_element_user(SERVICEREF$68);
         return target;
      }
   }

   public void removeServiceRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SERVICEREF$68, i);
      }
   }

   public ResourceRefType[] getResourceRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(RESOURCEREF$70, targetList);
         ResourceRefType[] result = new ResourceRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ResourceRefType getResourceRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceRefType target = null;
         target = (ResourceRefType)this.get_store().find_element_user(RESOURCEREF$70, i);
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
         return this.get_store().count_elements(RESOURCEREF$70);
      }
   }

   public void setResourceRefArray(ResourceRefType[] resourceRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(resourceRefArray, RESOURCEREF$70);
   }

   public void setResourceRefArray(int i, ResourceRefType resourceRef) {
      this.generatedSetterHelperImpl(resourceRef, RESOURCEREF$70, i, (short)2);
   }

   public ResourceRefType insertNewResourceRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceRefType target = null;
         target = (ResourceRefType)this.get_store().insert_element_user(RESOURCEREF$70, i);
         return target;
      }
   }

   public ResourceRefType addNewResourceRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceRefType target = null;
         target = (ResourceRefType)this.get_store().add_element_user(RESOURCEREF$70);
         return target;
      }
   }

   public void removeResourceRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RESOURCEREF$70, i);
      }
   }

   public ResourceEnvRefType[] getResourceEnvRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(RESOURCEENVREF$72, targetList);
         ResourceEnvRefType[] result = new ResourceEnvRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ResourceEnvRefType getResourceEnvRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceEnvRefType target = null;
         target = (ResourceEnvRefType)this.get_store().find_element_user(RESOURCEENVREF$72, i);
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
         return this.get_store().count_elements(RESOURCEENVREF$72);
      }
   }

   public void setResourceEnvRefArray(ResourceEnvRefType[] resourceEnvRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(resourceEnvRefArray, RESOURCEENVREF$72);
   }

   public void setResourceEnvRefArray(int i, ResourceEnvRefType resourceEnvRef) {
      this.generatedSetterHelperImpl(resourceEnvRef, RESOURCEENVREF$72, i, (short)2);
   }

   public ResourceEnvRefType insertNewResourceEnvRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceEnvRefType target = null;
         target = (ResourceEnvRefType)this.get_store().insert_element_user(RESOURCEENVREF$72, i);
         return target;
      }
   }

   public ResourceEnvRefType addNewResourceEnvRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceEnvRefType target = null;
         target = (ResourceEnvRefType)this.get_store().add_element_user(RESOURCEENVREF$72);
         return target;
      }
   }

   public void removeResourceEnvRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RESOURCEENVREF$72, i);
      }
   }

   public MessageDestinationRefType[] getMessageDestinationRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(MESSAGEDESTINATIONREF$74, targetList);
         MessageDestinationRefType[] result = new MessageDestinationRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public MessageDestinationRefType getMessageDestinationRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationRefType target = null;
         target = (MessageDestinationRefType)this.get_store().find_element_user(MESSAGEDESTINATIONREF$74, i);
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
         return this.get_store().count_elements(MESSAGEDESTINATIONREF$74);
      }
   }

   public void setMessageDestinationRefArray(MessageDestinationRefType[] messageDestinationRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(messageDestinationRefArray, MESSAGEDESTINATIONREF$74);
   }

   public void setMessageDestinationRefArray(int i, MessageDestinationRefType messageDestinationRef) {
      this.generatedSetterHelperImpl(messageDestinationRef, MESSAGEDESTINATIONREF$74, i, (short)2);
   }

   public MessageDestinationRefType insertNewMessageDestinationRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationRefType target = null;
         target = (MessageDestinationRefType)this.get_store().insert_element_user(MESSAGEDESTINATIONREF$74, i);
         return target;
      }
   }

   public MessageDestinationRefType addNewMessageDestinationRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationRefType target = null;
         target = (MessageDestinationRefType)this.get_store().add_element_user(MESSAGEDESTINATIONREF$74);
         return target;
      }
   }

   public void removeMessageDestinationRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MESSAGEDESTINATIONREF$74, i);
      }
   }

   public PersistenceContextRefType[] getPersistenceContextRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(PERSISTENCECONTEXTREF$76, targetList);
         PersistenceContextRefType[] result = new PersistenceContextRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public PersistenceContextRefType getPersistenceContextRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceContextRefType target = null;
         target = (PersistenceContextRefType)this.get_store().find_element_user(PERSISTENCECONTEXTREF$76, i);
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
         return this.get_store().count_elements(PERSISTENCECONTEXTREF$76);
      }
   }

   public void setPersistenceContextRefArray(PersistenceContextRefType[] persistenceContextRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(persistenceContextRefArray, PERSISTENCECONTEXTREF$76);
   }

   public void setPersistenceContextRefArray(int i, PersistenceContextRefType persistenceContextRef) {
      this.generatedSetterHelperImpl(persistenceContextRef, PERSISTENCECONTEXTREF$76, i, (short)2);
   }

   public PersistenceContextRefType insertNewPersistenceContextRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceContextRefType target = null;
         target = (PersistenceContextRefType)this.get_store().insert_element_user(PERSISTENCECONTEXTREF$76, i);
         return target;
      }
   }

   public PersistenceContextRefType addNewPersistenceContextRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceContextRefType target = null;
         target = (PersistenceContextRefType)this.get_store().add_element_user(PERSISTENCECONTEXTREF$76);
         return target;
      }
   }

   public void removePersistenceContextRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PERSISTENCECONTEXTREF$76, i);
      }
   }

   public PersistenceUnitRefType[] getPersistenceUnitRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(PERSISTENCEUNITREF$78, targetList);
         PersistenceUnitRefType[] result = new PersistenceUnitRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public PersistenceUnitRefType getPersistenceUnitRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitRefType target = null;
         target = (PersistenceUnitRefType)this.get_store().find_element_user(PERSISTENCEUNITREF$78, i);
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
         return this.get_store().count_elements(PERSISTENCEUNITREF$78);
      }
   }

   public void setPersistenceUnitRefArray(PersistenceUnitRefType[] persistenceUnitRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(persistenceUnitRefArray, PERSISTENCEUNITREF$78);
   }

   public void setPersistenceUnitRefArray(int i, PersistenceUnitRefType persistenceUnitRef) {
      this.generatedSetterHelperImpl(persistenceUnitRef, PERSISTENCEUNITREF$78, i, (short)2);
   }

   public PersistenceUnitRefType insertNewPersistenceUnitRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitRefType target = null;
         target = (PersistenceUnitRefType)this.get_store().insert_element_user(PERSISTENCEUNITREF$78, i);
         return target;
      }
   }

   public PersistenceUnitRefType addNewPersistenceUnitRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitRefType target = null;
         target = (PersistenceUnitRefType)this.get_store().add_element_user(PERSISTENCEUNITREF$78);
         return target;
      }
   }

   public void removePersistenceUnitRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PERSISTENCEUNITREF$78, i);
      }
   }

   public LifecycleCallbackType[] getPostConstructArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(POSTCONSTRUCT$80, targetList);
         LifecycleCallbackType[] result = new LifecycleCallbackType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public LifecycleCallbackType getPostConstructArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LifecycleCallbackType target = null;
         target = (LifecycleCallbackType)this.get_store().find_element_user(POSTCONSTRUCT$80, i);
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
         return this.get_store().count_elements(POSTCONSTRUCT$80);
      }
   }

   public void setPostConstructArray(LifecycleCallbackType[] postConstructArray) {
      this.check_orphaned();
      this.arraySetterHelper(postConstructArray, POSTCONSTRUCT$80);
   }

   public void setPostConstructArray(int i, LifecycleCallbackType postConstruct) {
      this.generatedSetterHelperImpl(postConstruct, POSTCONSTRUCT$80, i, (short)2);
   }

   public LifecycleCallbackType insertNewPostConstruct(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LifecycleCallbackType target = null;
         target = (LifecycleCallbackType)this.get_store().insert_element_user(POSTCONSTRUCT$80, i);
         return target;
      }
   }

   public LifecycleCallbackType addNewPostConstruct() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LifecycleCallbackType target = null;
         target = (LifecycleCallbackType)this.get_store().add_element_user(POSTCONSTRUCT$80);
         return target;
      }
   }

   public void removePostConstruct(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(POSTCONSTRUCT$80, i);
      }
   }

   public LifecycleCallbackType[] getPreDestroyArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(PREDESTROY$82, targetList);
         LifecycleCallbackType[] result = new LifecycleCallbackType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public LifecycleCallbackType getPreDestroyArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LifecycleCallbackType target = null;
         target = (LifecycleCallbackType)this.get_store().find_element_user(PREDESTROY$82, i);
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
         return this.get_store().count_elements(PREDESTROY$82);
      }
   }

   public void setPreDestroyArray(LifecycleCallbackType[] preDestroyArray) {
      this.check_orphaned();
      this.arraySetterHelper(preDestroyArray, PREDESTROY$82);
   }

   public void setPreDestroyArray(int i, LifecycleCallbackType preDestroy) {
      this.generatedSetterHelperImpl(preDestroy, PREDESTROY$82, i, (short)2);
   }

   public LifecycleCallbackType insertNewPreDestroy(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LifecycleCallbackType target = null;
         target = (LifecycleCallbackType)this.get_store().insert_element_user(PREDESTROY$82, i);
         return target;
      }
   }

   public LifecycleCallbackType addNewPreDestroy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LifecycleCallbackType target = null;
         target = (LifecycleCallbackType)this.get_store().add_element_user(PREDESTROY$82);
         return target;
      }
   }

   public void removePreDestroy(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PREDESTROY$82, i);
      }
   }

   public DataSourceType[] getDataSourceArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(DATASOURCE$84, targetList);
         DataSourceType[] result = new DataSourceType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public DataSourceType getDataSourceArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DataSourceType target = null;
         target = (DataSourceType)this.get_store().find_element_user(DATASOURCE$84, i);
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
         return this.get_store().count_elements(DATASOURCE$84);
      }
   }

   public void setDataSourceArray(DataSourceType[] dataSourceArray) {
      this.check_orphaned();
      this.arraySetterHelper(dataSourceArray, DATASOURCE$84);
   }

   public void setDataSourceArray(int i, DataSourceType dataSource) {
      this.generatedSetterHelperImpl(dataSource, DATASOURCE$84, i, (short)2);
   }

   public DataSourceType insertNewDataSource(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DataSourceType target = null;
         target = (DataSourceType)this.get_store().insert_element_user(DATASOURCE$84, i);
         return target;
      }
   }

   public DataSourceType addNewDataSource() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DataSourceType target = null;
         target = (DataSourceType)this.get_store().add_element_user(DATASOURCE$84);
         return target;
      }
   }

   public void removeDataSource(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DATASOURCE$84, i);
      }
   }

   public LifecycleCallbackType[] getPostActivateArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(POSTACTIVATE$86, targetList);
         LifecycleCallbackType[] result = new LifecycleCallbackType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public LifecycleCallbackType getPostActivateArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LifecycleCallbackType target = null;
         target = (LifecycleCallbackType)this.get_store().find_element_user(POSTACTIVATE$86, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfPostActivateArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(POSTACTIVATE$86);
      }
   }

   public void setPostActivateArray(LifecycleCallbackType[] postActivateArray) {
      this.check_orphaned();
      this.arraySetterHelper(postActivateArray, POSTACTIVATE$86);
   }

   public void setPostActivateArray(int i, LifecycleCallbackType postActivate) {
      this.generatedSetterHelperImpl(postActivate, POSTACTIVATE$86, i, (short)2);
   }

   public LifecycleCallbackType insertNewPostActivate(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LifecycleCallbackType target = null;
         target = (LifecycleCallbackType)this.get_store().insert_element_user(POSTACTIVATE$86, i);
         return target;
      }
   }

   public LifecycleCallbackType addNewPostActivate() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LifecycleCallbackType target = null;
         target = (LifecycleCallbackType)this.get_store().add_element_user(POSTACTIVATE$86);
         return target;
      }
   }

   public void removePostActivate(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(POSTACTIVATE$86, i);
      }
   }

   public LifecycleCallbackType[] getPrePassivateArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(PREPASSIVATE$88, targetList);
         LifecycleCallbackType[] result = new LifecycleCallbackType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public LifecycleCallbackType getPrePassivateArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LifecycleCallbackType target = null;
         target = (LifecycleCallbackType)this.get_store().find_element_user(PREPASSIVATE$88, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfPrePassivateArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PREPASSIVATE$88);
      }
   }

   public void setPrePassivateArray(LifecycleCallbackType[] prePassivateArray) {
      this.check_orphaned();
      this.arraySetterHelper(prePassivateArray, PREPASSIVATE$88);
   }

   public void setPrePassivateArray(int i, LifecycleCallbackType prePassivate) {
      this.generatedSetterHelperImpl(prePassivate, PREPASSIVATE$88, i, (short)2);
   }

   public LifecycleCallbackType insertNewPrePassivate(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LifecycleCallbackType target = null;
         target = (LifecycleCallbackType)this.get_store().insert_element_user(PREPASSIVATE$88, i);
         return target;
      }
   }

   public LifecycleCallbackType addNewPrePassivate() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LifecycleCallbackType target = null;
         target = (LifecycleCallbackType)this.get_store().add_element_user(PREPASSIVATE$88);
         return target;
      }
   }

   public void removePrePassivate(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PREPASSIVATE$88, i);
      }
   }

   public SecurityRoleRefType[] getSecurityRoleRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(SECURITYROLEREF$90, targetList);
         SecurityRoleRefType[] result = new SecurityRoleRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public SecurityRoleRefType getSecurityRoleRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SecurityRoleRefType target = null;
         target = (SecurityRoleRefType)this.get_store().find_element_user(SECURITYROLEREF$90, i);
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
         return this.get_store().count_elements(SECURITYROLEREF$90);
      }
   }

   public void setSecurityRoleRefArray(SecurityRoleRefType[] securityRoleRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(securityRoleRefArray, SECURITYROLEREF$90);
   }

   public void setSecurityRoleRefArray(int i, SecurityRoleRefType securityRoleRef) {
      this.generatedSetterHelperImpl(securityRoleRef, SECURITYROLEREF$90, i, (short)2);
   }

   public SecurityRoleRefType insertNewSecurityRoleRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SecurityRoleRefType target = null;
         target = (SecurityRoleRefType)this.get_store().insert_element_user(SECURITYROLEREF$90, i);
         return target;
      }
   }

   public SecurityRoleRefType addNewSecurityRoleRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SecurityRoleRefType target = null;
         target = (SecurityRoleRefType)this.get_store().add_element_user(SECURITYROLEREF$90);
         return target;
      }
   }

   public void removeSecurityRoleRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SECURITYROLEREF$90, i);
      }
   }

   public SecurityIdentityType getSecurityIdentity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SecurityIdentityType target = null;
         target = (SecurityIdentityType)this.get_store().find_element_user(SECURITYIDENTITY$92, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetSecurityIdentity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SECURITYIDENTITY$92) != 0;
      }
   }

   public void setSecurityIdentity(SecurityIdentityType securityIdentity) {
      this.generatedSetterHelperImpl(securityIdentity, SECURITYIDENTITY$92, 0, (short)1);
   }

   public SecurityIdentityType addNewSecurityIdentity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SecurityIdentityType target = null;
         target = (SecurityIdentityType)this.get_store().add_element_user(SECURITYIDENTITY$92);
         return target;
      }
   }

   public void unsetSecurityIdentity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SECURITYIDENTITY$92, 0);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$94);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$94);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$94) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$94);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$94);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$94);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$94);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$94);
      }
   }
}
