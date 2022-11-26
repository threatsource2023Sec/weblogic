package org.jcp.xmlns.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlID;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import org.jcp.xmlns.xml.ns.javaee.AdministeredObjectType;
import org.jcp.xmlns.xml.ns.javaee.ConnectionFactoryResourceType;
import org.jcp.xmlns.xml.ns.javaee.DataSourceType;
import org.jcp.xmlns.xml.ns.javaee.DescriptionType;
import org.jcp.xmlns.xml.ns.javaee.DisplayNameType;
import org.jcp.xmlns.xml.ns.javaee.EjbLocalRefType;
import org.jcp.xmlns.xml.ns.javaee.EjbRefType;
import org.jcp.xmlns.xml.ns.javaee.EmptyType;
import org.jcp.xmlns.xml.ns.javaee.EnvEntryType;
import org.jcp.xmlns.xml.ns.javaee.ErrorPageType;
import org.jcp.xmlns.xml.ns.javaee.FilterMappingType;
import org.jcp.xmlns.xml.ns.javaee.FilterType;
import org.jcp.xmlns.xml.ns.javaee.IconType;
import org.jcp.xmlns.xml.ns.javaee.JavaIdentifierType;
import org.jcp.xmlns.xml.ns.javaee.JmsConnectionFactoryType;
import org.jcp.xmlns.xml.ns.javaee.JmsDestinationType;
import org.jcp.xmlns.xml.ns.javaee.JspConfigType;
import org.jcp.xmlns.xml.ns.javaee.LifecycleCallbackType;
import org.jcp.xmlns.xml.ns.javaee.ListenerType;
import org.jcp.xmlns.xml.ns.javaee.LocaleEncodingMappingListType;
import org.jcp.xmlns.xml.ns.javaee.LoginConfigType;
import org.jcp.xmlns.xml.ns.javaee.MailSessionType;
import org.jcp.xmlns.xml.ns.javaee.MessageDestinationRefType;
import org.jcp.xmlns.xml.ns.javaee.MessageDestinationType;
import org.jcp.xmlns.xml.ns.javaee.MimeMappingType;
import org.jcp.xmlns.xml.ns.javaee.OrderingType;
import org.jcp.xmlns.xml.ns.javaee.ParamValueType;
import org.jcp.xmlns.xml.ns.javaee.PersistenceContextRefType;
import org.jcp.xmlns.xml.ns.javaee.PersistenceUnitRefType;
import org.jcp.xmlns.xml.ns.javaee.ResourceEnvRefType;
import org.jcp.xmlns.xml.ns.javaee.ResourceRefType;
import org.jcp.xmlns.xml.ns.javaee.SecurityConstraintType;
import org.jcp.xmlns.xml.ns.javaee.SecurityRoleType;
import org.jcp.xmlns.xml.ns.javaee.ServiceRefType;
import org.jcp.xmlns.xml.ns.javaee.ServletMappingType;
import org.jcp.xmlns.xml.ns.javaee.ServletType;
import org.jcp.xmlns.xml.ns.javaee.SessionConfigType;
import org.jcp.xmlns.xml.ns.javaee.WebAppVersionType;
import org.jcp.xmlns.xml.ns.javaee.WebFragmentType;
import org.jcp.xmlns.xml.ns.javaee.WelcomeFileListType;

public class WebFragmentTypeImpl extends XmlComplexContentImpl implements WebFragmentType {
   private static final long serialVersionUID = 1L;
   private static final QName NAME$0 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "name");
   private static final QName DESCRIPTION$2 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "description");
   private static final QName DISPLAYNAME$4 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "display-name");
   private static final QName ICON$6 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "icon");
   private static final QName DISTRIBUTABLE$8 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "distributable");
   private static final QName CONTEXTPARAM$10 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "context-param");
   private static final QName FILTER$12 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "filter");
   private static final QName FILTERMAPPING$14 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "filter-mapping");
   private static final QName LISTENER$16 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "listener");
   private static final QName SERVLET$18 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "servlet");
   private static final QName SERVLETMAPPING$20 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "servlet-mapping");
   private static final QName SESSIONCONFIG$22 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "session-config");
   private static final QName MIMEMAPPING$24 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "mime-mapping");
   private static final QName WELCOMEFILELIST$26 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "welcome-file-list");
   private static final QName ERRORPAGE$28 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "error-page");
   private static final QName JSPCONFIG$30 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "jsp-config");
   private static final QName SECURITYCONSTRAINT$32 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "security-constraint");
   private static final QName LOGINCONFIG$34 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "login-config");
   private static final QName SECURITYROLE$36 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "security-role");
   private static final QName ENVENTRY$38 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "env-entry");
   private static final QName EJBREF$40 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "ejb-ref");
   private static final QName EJBLOCALREF$42 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "ejb-local-ref");
   private static final QName SERVICEREF$44 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "service-ref");
   private static final QName RESOURCEREF$46 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "resource-ref");
   private static final QName RESOURCEENVREF$48 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "resource-env-ref");
   private static final QName MESSAGEDESTINATIONREF$50 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "message-destination-ref");
   private static final QName PERSISTENCECONTEXTREF$52 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "persistence-context-ref");
   private static final QName PERSISTENCEUNITREF$54 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "persistence-unit-ref");
   private static final QName POSTCONSTRUCT$56 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "post-construct");
   private static final QName PREDESTROY$58 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "pre-destroy");
   private static final QName DATASOURCE$60 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "data-source");
   private static final QName JMSCONNECTIONFACTORY$62 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "jms-connection-factory");
   private static final QName JMSDESTINATION$64 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "jms-destination");
   private static final QName MAILSESSION$66 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "mail-session");
   private static final QName CONNECTIONFACTORY$68 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "connection-factory");
   private static final QName ADMINISTEREDOBJECT$70 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "administered-object");
   private static final QName MESSAGEDESTINATION$72 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "message-destination");
   private static final QName LOCALEENCODINGMAPPINGLIST$74 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "locale-encoding-mapping-list");
   private static final QName ORDERING$76 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "ordering");
   private static final QName VERSION$78 = new QName("", "version");
   private static final QName ID$80 = new QName("", "id");
   private static final QName METADATACOMPLETE$82 = new QName("", "metadata-complete");

   public WebFragmentTypeImpl(SchemaType sType) {
      super(sType);
   }

   public JavaIdentifierType[] getNameArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(NAME$0, targetList);
         JavaIdentifierType[] result = new JavaIdentifierType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public JavaIdentifierType getNameArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JavaIdentifierType target = null;
         target = (JavaIdentifierType)this.get_store().find_element_user(NAME$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfNameArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(NAME$0);
      }
   }

   public void setNameArray(JavaIdentifierType[] nameArray) {
      this.check_orphaned();
      this.arraySetterHelper(nameArray, NAME$0);
   }

   public void setNameArray(int i, JavaIdentifierType name) {
      this.generatedSetterHelperImpl(name, NAME$0, i, (short)2);
   }

   public JavaIdentifierType insertNewName(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JavaIdentifierType target = null;
         target = (JavaIdentifierType)this.get_store().insert_element_user(NAME$0, i);
         return target;
      }
   }

   public JavaIdentifierType addNewName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JavaIdentifierType target = null;
         target = (JavaIdentifierType)this.get_store().add_element_user(NAME$0);
         return target;
      }
   }

   public void removeName(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(NAME$0, i);
      }
   }

   public DescriptionType[] getDescriptionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(DESCRIPTION$2, targetList);
         DescriptionType[] result = new DescriptionType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public DescriptionType getDescriptionArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DescriptionType target = null;
         target = (DescriptionType)this.get_store().find_element_user(DESCRIPTION$2, i);
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
         return this.get_store().count_elements(DESCRIPTION$2);
      }
   }

   public void setDescriptionArray(DescriptionType[] descriptionArray) {
      this.check_orphaned();
      this.arraySetterHelper(descriptionArray, DESCRIPTION$2);
   }

   public void setDescriptionArray(int i, DescriptionType description) {
      this.generatedSetterHelperImpl(description, DESCRIPTION$2, i, (short)2);
   }

   public DescriptionType insertNewDescription(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DescriptionType target = null;
         target = (DescriptionType)this.get_store().insert_element_user(DESCRIPTION$2, i);
         return target;
      }
   }

   public DescriptionType addNewDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DescriptionType target = null;
         target = (DescriptionType)this.get_store().add_element_user(DESCRIPTION$2);
         return target;
      }
   }

   public void removeDescription(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DESCRIPTION$2, i);
      }
   }

   public DisplayNameType[] getDisplayNameArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(DISPLAYNAME$4, targetList);
         DisplayNameType[] result = new DisplayNameType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public DisplayNameType getDisplayNameArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DisplayNameType target = null;
         target = (DisplayNameType)this.get_store().find_element_user(DISPLAYNAME$4, i);
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
         return this.get_store().count_elements(DISPLAYNAME$4);
      }
   }

   public void setDisplayNameArray(DisplayNameType[] displayNameArray) {
      this.check_orphaned();
      this.arraySetterHelper(displayNameArray, DISPLAYNAME$4);
   }

   public void setDisplayNameArray(int i, DisplayNameType displayName) {
      this.generatedSetterHelperImpl(displayName, DISPLAYNAME$4, i, (short)2);
   }

   public DisplayNameType insertNewDisplayName(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DisplayNameType target = null;
         target = (DisplayNameType)this.get_store().insert_element_user(DISPLAYNAME$4, i);
         return target;
      }
   }

   public DisplayNameType addNewDisplayName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DisplayNameType target = null;
         target = (DisplayNameType)this.get_store().add_element_user(DISPLAYNAME$4);
         return target;
      }
   }

   public void removeDisplayName(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DISPLAYNAME$4, i);
      }
   }

   public IconType[] getIconArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(ICON$6, targetList);
         IconType[] result = new IconType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public IconType getIconArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         IconType target = null;
         target = (IconType)this.get_store().find_element_user(ICON$6, i);
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
         return this.get_store().count_elements(ICON$6);
      }
   }

   public void setIconArray(IconType[] iconArray) {
      this.check_orphaned();
      this.arraySetterHelper(iconArray, ICON$6);
   }

   public void setIconArray(int i, IconType icon) {
      this.generatedSetterHelperImpl(icon, ICON$6, i, (short)2);
   }

   public IconType insertNewIcon(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         IconType target = null;
         target = (IconType)this.get_store().insert_element_user(ICON$6, i);
         return target;
      }
   }

   public IconType addNewIcon() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         IconType target = null;
         target = (IconType)this.get_store().add_element_user(ICON$6);
         return target;
      }
   }

   public void removeIcon(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ICON$6, i);
      }
   }

   public EmptyType[] getDistributableArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(DISTRIBUTABLE$8, targetList);
         EmptyType[] result = new EmptyType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public EmptyType getDistributableArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EmptyType target = null;
         target = (EmptyType)this.get_store().find_element_user(DISTRIBUTABLE$8, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfDistributableArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DISTRIBUTABLE$8);
      }
   }

   public void setDistributableArray(EmptyType[] distributableArray) {
      this.check_orphaned();
      this.arraySetterHelper(distributableArray, DISTRIBUTABLE$8);
   }

   public void setDistributableArray(int i, EmptyType distributable) {
      this.generatedSetterHelperImpl(distributable, DISTRIBUTABLE$8, i, (short)2);
   }

   public EmptyType insertNewDistributable(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EmptyType target = null;
         target = (EmptyType)this.get_store().insert_element_user(DISTRIBUTABLE$8, i);
         return target;
      }
   }

   public EmptyType addNewDistributable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EmptyType target = null;
         target = (EmptyType)this.get_store().add_element_user(DISTRIBUTABLE$8);
         return target;
      }
   }

   public void removeDistributable(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DISTRIBUTABLE$8, i);
      }
   }

   public ParamValueType[] getContextParamArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(CONTEXTPARAM$10, targetList);
         ParamValueType[] result = new ParamValueType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ParamValueType getContextParamArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ParamValueType target = null;
         target = (ParamValueType)this.get_store().find_element_user(CONTEXTPARAM$10, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfContextParamArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONTEXTPARAM$10);
      }
   }

   public void setContextParamArray(ParamValueType[] contextParamArray) {
      this.check_orphaned();
      this.arraySetterHelper(contextParamArray, CONTEXTPARAM$10);
   }

   public void setContextParamArray(int i, ParamValueType contextParam) {
      this.generatedSetterHelperImpl(contextParam, CONTEXTPARAM$10, i, (short)2);
   }

   public ParamValueType insertNewContextParam(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ParamValueType target = null;
         target = (ParamValueType)this.get_store().insert_element_user(CONTEXTPARAM$10, i);
         return target;
      }
   }

   public ParamValueType addNewContextParam() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ParamValueType target = null;
         target = (ParamValueType)this.get_store().add_element_user(CONTEXTPARAM$10);
         return target;
      }
   }

   public void removeContextParam(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONTEXTPARAM$10, i);
      }
   }

   public FilterType[] getFilterArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(FILTER$12, targetList);
         FilterType[] result = new FilterType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public FilterType getFilterArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FilterType target = null;
         target = (FilterType)this.get_store().find_element_user(FILTER$12, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfFilterArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(FILTER$12);
      }
   }

   public void setFilterArray(FilterType[] filterArray) {
      this.check_orphaned();
      this.arraySetterHelper(filterArray, FILTER$12);
   }

   public void setFilterArray(int i, FilterType filter) {
      this.generatedSetterHelperImpl(filter, FILTER$12, i, (short)2);
   }

   public FilterType insertNewFilter(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FilterType target = null;
         target = (FilterType)this.get_store().insert_element_user(FILTER$12, i);
         return target;
      }
   }

   public FilterType addNewFilter() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FilterType target = null;
         target = (FilterType)this.get_store().add_element_user(FILTER$12);
         return target;
      }
   }

   public void removeFilter(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FILTER$12, i);
      }
   }

   public FilterMappingType[] getFilterMappingArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(FILTERMAPPING$14, targetList);
         FilterMappingType[] result = new FilterMappingType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public FilterMappingType getFilterMappingArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FilterMappingType target = null;
         target = (FilterMappingType)this.get_store().find_element_user(FILTERMAPPING$14, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfFilterMappingArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(FILTERMAPPING$14);
      }
   }

   public void setFilterMappingArray(FilterMappingType[] filterMappingArray) {
      this.check_orphaned();
      this.arraySetterHelper(filterMappingArray, FILTERMAPPING$14);
   }

   public void setFilterMappingArray(int i, FilterMappingType filterMapping) {
      this.generatedSetterHelperImpl(filterMapping, FILTERMAPPING$14, i, (short)2);
   }

   public FilterMappingType insertNewFilterMapping(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FilterMappingType target = null;
         target = (FilterMappingType)this.get_store().insert_element_user(FILTERMAPPING$14, i);
         return target;
      }
   }

   public FilterMappingType addNewFilterMapping() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FilterMappingType target = null;
         target = (FilterMappingType)this.get_store().add_element_user(FILTERMAPPING$14);
         return target;
      }
   }

   public void removeFilterMapping(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FILTERMAPPING$14, i);
      }
   }

   public ListenerType[] getListenerArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(LISTENER$16, targetList);
         ListenerType[] result = new ListenerType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ListenerType getListenerArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ListenerType target = null;
         target = (ListenerType)this.get_store().find_element_user(LISTENER$16, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfListenerArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LISTENER$16);
      }
   }

   public void setListenerArray(ListenerType[] listenerArray) {
      this.check_orphaned();
      this.arraySetterHelper(listenerArray, LISTENER$16);
   }

   public void setListenerArray(int i, ListenerType listener) {
      this.generatedSetterHelperImpl(listener, LISTENER$16, i, (short)2);
   }

   public ListenerType insertNewListener(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ListenerType target = null;
         target = (ListenerType)this.get_store().insert_element_user(LISTENER$16, i);
         return target;
      }
   }

   public ListenerType addNewListener() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ListenerType target = null;
         target = (ListenerType)this.get_store().add_element_user(LISTENER$16);
         return target;
      }
   }

   public void removeListener(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LISTENER$16, i);
      }
   }

   public ServletType[] getServletArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(SERVLET$18, targetList);
         ServletType[] result = new ServletType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ServletType getServletArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServletType target = null;
         target = (ServletType)this.get_store().find_element_user(SERVLET$18, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfServletArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SERVLET$18);
      }
   }

   public void setServletArray(ServletType[] servletArray) {
      this.check_orphaned();
      this.arraySetterHelper(servletArray, SERVLET$18);
   }

   public void setServletArray(int i, ServletType servlet) {
      this.generatedSetterHelperImpl(servlet, SERVLET$18, i, (short)2);
   }

   public ServletType insertNewServlet(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServletType target = null;
         target = (ServletType)this.get_store().insert_element_user(SERVLET$18, i);
         return target;
      }
   }

   public ServletType addNewServlet() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServletType target = null;
         target = (ServletType)this.get_store().add_element_user(SERVLET$18);
         return target;
      }
   }

   public void removeServlet(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SERVLET$18, i);
      }
   }

   public ServletMappingType[] getServletMappingArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(SERVLETMAPPING$20, targetList);
         ServletMappingType[] result = new ServletMappingType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ServletMappingType getServletMappingArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServletMappingType target = null;
         target = (ServletMappingType)this.get_store().find_element_user(SERVLETMAPPING$20, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfServletMappingArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SERVLETMAPPING$20);
      }
   }

   public void setServletMappingArray(ServletMappingType[] servletMappingArray) {
      this.check_orphaned();
      this.arraySetterHelper(servletMappingArray, SERVLETMAPPING$20);
   }

   public void setServletMappingArray(int i, ServletMappingType servletMapping) {
      this.generatedSetterHelperImpl(servletMapping, SERVLETMAPPING$20, i, (short)2);
   }

   public ServletMappingType insertNewServletMapping(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServletMappingType target = null;
         target = (ServletMappingType)this.get_store().insert_element_user(SERVLETMAPPING$20, i);
         return target;
      }
   }

   public ServletMappingType addNewServletMapping() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServletMappingType target = null;
         target = (ServletMappingType)this.get_store().add_element_user(SERVLETMAPPING$20);
         return target;
      }
   }

   public void removeServletMapping(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SERVLETMAPPING$20, i);
      }
   }

   public SessionConfigType[] getSessionConfigArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(SESSIONCONFIG$22, targetList);
         SessionConfigType[] result = new SessionConfigType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public SessionConfigType getSessionConfigArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SessionConfigType target = null;
         target = (SessionConfigType)this.get_store().find_element_user(SESSIONCONFIG$22, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfSessionConfigArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SESSIONCONFIG$22);
      }
   }

   public void setSessionConfigArray(SessionConfigType[] sessionConfigArray) {
      this.check_orphaned();
      this.arraySetterHelper(sessionConfigArray, SESSIONCONFIG$22);
   }

   public void setSessionConfigArray(int i, SessionConfigType sessionConfig) {
      this.generatedSetterHelperImpl(sessionConfig, SESSIONCONFIG$22, i, (short)2);
   }

   public SessionConfigType insertNewSessionConfig(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SessionConfigType target = null;
         target = (SessionConfigType)this.get_store().insert_element_user(SESSIONCONFIG$22, i);
         return target;
      }
   }

   public SessionConfigType addNewSessionConfig() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SessionConfigType target = null;
         target = (SessionConfigType)this.get_store().add_element_user(SESSIONCONFIG$22);
         return target;
      }
   }

   public void removeSessionConfig(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SESSIONCONFIG$22, i);
      }
   }

   public MimeMappingType[] getMimeMappingArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(MIMEMAPPING$24, targetList);
         MimeMappingType[] result = new MimeMappingType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public MimeMappingType getMimeMappingArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MimeMappingType target = null;
         target = (MimeMappingType)this.get_store().find_element_user(MIMEMAPPING$24, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfMimeMappingArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MIMEMAPPING$24);
      }
   }

   public void setMimeMappingArray(MimeMappingType[] mimeMappingArray) {
      this.check_orphaned();
      this.arraySetterHelper(mimeMappingArray, MIMEMAPPING$24);
   }

   public void setMimeMappingArray(int i, MimeMappingType mimeMapping) {
      this.generatedSetterHelperImpl(mimeMapping, MIMEMAPPING$24, i, (short)2);
   }

   public MimeMappingType insertNewMimeMapping(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MimeMappingType target = null;
         target = (MimeMappingType)this.get_store().insert_element_user(MIMEMAPPING$24, i);
         return target;
      }
   }

   public MimeMappingType addNewMimeMapping() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MimeMappingType target = null;
         target = (MimeMappingType)this.get_store().add_element_user(MIMEMAPPING$24);
         return target;
      }
   }

   public void removeMimeMapping(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MIMEMAPPING$24, i);
      }
   }

   public WelcomeFileListType[] getWelcomeFileListArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(WELCOMEFILELIST$26, targetList);
         WelcomeFileListType[] result = new WelcomeFileListType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public WelcomeFileListType getWelcomeFileListArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WelcomeFileListType target = null;
         target = (WelcomeFileListType)this.get_store().find_element_user(WELCOMEFILELIST$26, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfWelcomeFileListArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(WELCOMEFILELIST$26);
      }
   }

   public void setWelcomeFileListArray(WelcomeFileListType[] welcomeFileListArray) {
      this.check_orphaned();
      this.arraySetterHelper(welcomeFileListArray, WELCOMEFILELIST$26);
   }

   public void setWelcomeFileListArray(int i, WelcomeFileListType welcomeFileList) {
      this.generatedSetterHelperImpl(welcomeFileList, WELCOMEFILELIST$26, i, (short)2);
   }

   public WelcomeFileListType insertNewWelcomeFileList(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WelcomeFileListType target = null;
         target = (WelcomeFileListType)this.get_store().insert_element_user(WELCOMEFILELIST$26, i);
         return target;
      }
   }

   public WelcomeFileListType addNewWelcomeFileList() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WelcomeFileListType target = null;
         target = (WelcomeFileListType)this.get_store().add_element_user(WELCOMEFILELIST$26);
         return target;
      }
   }

   public void removeWelcomeFileList(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(WELCOMEFILELIST$26, i);
      }
   }

   public ErrorPageType[] getErrorPageArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(ERRORPAGE$28, targetList);
         ErrorPageType[] result = new ErrorPageType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ErrorPageType getErrorPageArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ErrorPageType target = null;
         target = (ErrorPageType)this.get_store().find_element_user(ERRORPAGE$28, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfErrorPageArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ERRORPAGE$28);
      }
   }

   public void setErrorPageArray(ErrorPageType[] errorPageArray) {
      this.check_orphaned();
      this.arraySetterHelper(errorPageArray, ERRORPAGE$28);
   }

   public void setErrorPageArray(int i, ErrorPageType errorPage) {
      this.generatedSetterHelperImpl(errorPage, ERRORPAGE$28, i, (short)2);
   }

   public ErrorPageType insertNewErrorPage(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ErrorPageType target = null;
         target = (ErrorPageType)this.get_store().insert_element_user(ERRORPAGE$28, i);
         return target;
      }
   }

   public ErrorPageType addNewErrorPage() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ErrorPageType target = null;
         target = (ErrorPageType)this.get_store().add_element_user(ERRORPAGE$28);
         return target;
      }
   }

   public void removeErrorPage(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ERRORPAGE$28, i);
      }
   }

   public JspConfigType[] getJspConfigArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(JSPCONFIG$30, targetList);
         JspConfigType[] result = new JspConfigType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public JspConfigType getJspConfigArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JspConfigType target = null;
         target = (JspConfigType)this.get_store().find_element_user(JSPCONFIG$30, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfJspConfigArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(JSPCONFIG$30);
      }
   }

   public void setJspConfigArray(JspConfigType[] jspConfigArray) {
      this.check_orphaned();
      this.arraySetterHelper(jspConfigArray, JSPCONFIG$30);
   }

   public void setJspConfigArray(int i, JspConfigType jspConfig) {
      this.generatedSetterHelperImpl(jspConfig, JSPCONFIG$30, i, (short)2);
   }

   public JspConfigType insertNewJspConfig(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JspConfigType target = null;
         target = (JspConfigType)this.get_store().insert_element_user(JSPCONFIG$30, i);
         return target;
      }
   }

   public JspConfigType addNewJspConfig() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JspConfigType target = null;
         target = (JspConfigType)this.get_store().add_element_user(JSPCONFIG$30);
         return target;
      }
   }

   public void removeJspConfig(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(JSPCONFIG$30, i);
      }
   }

   public SecurityConstraintType[] getSecurityConstraintArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(SECURITYCONSTRAINT$32, targetList);
         SecurityConstraintType[] result = new SecurityConstraintType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public SecurityConstraintType getSecurityConstraintArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SecurityConstraintType target = null;
         target = (SecurityConstraintType)this.get_store().find_element_user(SECURITYCONSTRAINT$32, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfSecurityConstraintArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SECURITYCONSTRAINT$32);
      }
   }

   public void setSecurityConstraintArray(SecurityConstraintType[] securityConstraintArray) {
      this.check_orphaned();
      this.arraySetterHelper(securityConstraintArray, SECURITYCONSTRAINT$32);
   }

   public void setSecurityConstraintArray(int i, SecurityConstraintType securityConstraint) {
      this.generatedSetterHelperImpl(securityConstraint, SECURITYCONSTRAINT$32, i, (short)2);
   }

   public SecurityConstraintType insertNewSecurityConstraint(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SecurityConstraintType target = null;
         target = (SecurityConstraintType)this.get_store().insert_element_user(SECURITYCONSTRAINT$32, i);
         return target;
      }
   }

   public SecurityConstraintType addNewSecurityConstraint() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SecurityConstraintType target = null;
         target = (SecurityConstraintType)this.get_store().add_element_user(SECURITYCONSTRAINT$32);
         return target;
      }
   }

   public void removeSecurityConstraint(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SECURITYCONSTRAINT$32, i);
      }
   }

   public LoginConfigType[] getLoginConfigArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(LOGINCONFIG$34, targetList);
         LoginConfigType[] result = new LoginConfigType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public LoginConfigType getLoginConfigArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LoginConfigType target = null;
         target = (LoginConfigType)this.get_store().find_element_user(LOGINCONFIG$34, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfLoginConfigArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LOGINCONFIG$34);
      }
   }

   public void setLoginConfigArray(LoginConfigType[] loginConfigArray) {
      this.check_orphaned();
      this.arraySetterHelper(loginConfigArray, LOGINCONFIG$34);
   }

   public void setLoginConfigArray(int i, LoginConfigType loginConfig) {
      this.generatedSetterHelperImpl(loginConfig, LOGINCONFIG$34, i, (short)2);
   }

   public LoginConfigType insertNewLoginConfig(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LoginConfigType target = null;
         target = (LoginConfigType)this.get_store().insert_element_user(LOGINCONFIG$34, i);
         return target;
      }
   }

   public LoginConfigType addNewLoginConfig() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LoginConfigType target = null;
         target = (LoginConfigType)this.get_store().add_element_user(LOGINCONFIG$34);
         return target;
      }
   }

   public void removeLoginConfig(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LOGINCONFIG$34, i);
      }
   }

   public SecurityRoleType[] getSecurityRoleArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(SECURITYROLE$36, targetList);
         SecurityRoleType[] result = new SecurityRoleType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public SecurityRoleType getSecurityRoleArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SecurityRoleType target = null;
         target = (SecurityRoleType)this.get_store().find_element_user(SECURITYROLE$36, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfSecurityRoleArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SECURITYROLE$36);
      }
   }

   public void setSecurityRoleArray(SecurityRoleType[] securityRoleArray) {
      this.check_orphaned();
      this.arraySetterHelper(securityRoleArray, SECURITYROLE$36);
   }

   public void setSecurityRoleArray(int i, SecurityRoleType securityRole) {
      this.generatedSetterHelperImpl(securityRole, SECURITYROLE$36, i, (short)2);
   }

   public SecurityRoleType insertNewSecurityRole(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SecurityRoleType target = null;
         target = (SecurityRoleType)this.get_store().insert_element_user(SECURITYROLE$36, i);
         return target;
      }
   }

   public SecurityRoleType addNewSecurityRole() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SecurityRoleType target = null;
         target = (SecurityRoleType)this.get_store().add_element_user(SECURITYROLE$36);
         return target;
      }
   }

   public void removeSecurityRole(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SECURITYROLE$36, i);
      }
   }

   public EnvEntryType[] getEnvEntryArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(ENVENTRY$38, targetList);
         EnvEntryType[] result = new EnvEntryType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public EnvEntryType getEnvEntryArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EnvEntryType target = null;
         target = (EnvEntryType)this.get_store().find_element_user(ENVENTRY$38, i);
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
         return this.get_store().count_elements(ENVENTRY$38);
      }
   }

   public void setEnvEntryArray(EnvEntryType[] envEntryArray) {
      this.check_orphaned();
      this.arraySetterHelper(envEntryArray, ENVENTRY$38);
   }

   public void setEnvEntryArray(int i, EnvEntryType envEntry) {
      this.generatedSetterHelperImpl(envEntry, ENVENTRY$38, i, (short)2);
   }

   public EnvEntryType insertNewEnvEntry(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EnvEntryType target = null;
         target = (EnvEntryType)this.get_store().insert_element_user(ENVENTRY$38, i);
         return target;
      }
   }

   public EnvEntryType addNewEnvEntry() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EnvEntryType target = null;
         target = (EnvEntryType)this.get_store().add_element_user(ENVENTRY$38);
         return target;
      }
   }

   public void removeEnvEntry(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ENVENTRY$38, i);
      }
   }

   public EjbRefType[] getEjbRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(EJBREF$40, targetList);
         EjbRefType[] result = new EjbRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public EjbRefType getEjbRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbRefType target = null;
         target = (EjbRefType)this.get_store().find_element_user(EJBREF$40, i);
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
         return this.get_store().count_elements(EJBREF$40);
      }
   }

   public void setEjbRefArray(EjbRefType[] ejbRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(ejbRefArray, EJBREF$40);
   }

   public void setEjbRefArray(int i, EjbRefType ejbRef) {
      this.generatedSetterHelperImpl(ejbRef, EJBREF$40, i, (short)2);
   }

   public EjbRefType insertNewEjbRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbRefType target = null;
         target = (EjbRefType)this.get_store().insert_element_user(EJBREF$40, i);
         return target;
      }
   }

   public EjbRefType addNewEjbRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbRefType target = null;
         target = (EjbRefType)this.get_store().add_element_user(EJBREF$40);
         return target;
      }
   }

   public void removeEjbRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(EJBREF$40, i);
      }
   }

   public EjbLocalRefType[] getEjbLocalRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(EJBLOCALREF$42, targetList);
         EjbLocalRefType[] result = new EjbLocalRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public EjbLocalRefType getEjbLocalRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbLocalRefType target = null;
         target = (EjbLocalRefType)this.get_store().find_element_user(EJBLOCALREF$42, i);
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
         return this.get_store().count_elements(EJBLOCALREF$42);
      }
   }

   public void setEjbLocalRefArray(EjbLocalRefType[] ejbLocalRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(ejbLocalRefArray, EJBLOCALREF$42);
   }

   public void setEjbLocalRefArray(int i, EjbLocalRefType ejbLocalRef) {
      this.generatedSetterHelperImpl(ejbLocalRef, EJBLOCALREF$42, i, (short)2);
   }

   public EjbLocalRefType insertNewEjbLocalRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbLocalRefType target = null;
         target = (EjbLocalRefType)this.get_store().insert_element_user(EJBLOCALREF$42, i);
         return target;
      }
   }

   public EjbLocalRefType addNewEjbLocalRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbLocalRefType target = null;
         target = (EjbLocalRefType)this.get_store().add_element_user(EJBLOCALREF$42);
         return target;
      }
   }

   public void removeEjbLocalRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(EJBLOCALREF$42, i);
      }
   }

   public ServiceRefType[] getServiceRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(SERVICEREF$44, targetList);
         ServiceRefType[] result = new ServiceRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ServiceRefType getServiceRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServiceRefType target = null;
         target = (ServiceRefType)this.get_store().find_element_user(SERVICEREF$44, i);
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
         return this.get_store().count_elements(SERVICEREF$44);
      }
   }

   public void setServiceRefArray(ServiceRefType[] serviceRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(serviceRefArray, SERVICEREF$44);
   }

   public void setServiceRefArray(int i, ServiceRefType serviceRef) {
      this.generatedSetterHelperImpl(serviceRef, SERVICEREF$44, i, (short)2);
   }

   public ServiceRefType insertNewServiceRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServiceRefType target = null;
         target = (ServiceRefType)this.get_store().insert_element_user(SERVICEREF$44, i);
         return target;
      }
   }

   public ServiceRefType addNewServiceRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServiceRefType target = null;
         target = (ServiceRefType)this.get_store().add_element_user(SERVICEREF$44);
         return target;
      }
   }

   public void removeServiceRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SERVICEREF$44, i);
      }
   }

   public ResourceRefType[] getResourceRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(RESOURCEREF$46, targetList);
         ResourceRefType[] result = new ResourceRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ResourceRefType getResourceRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceRefType target = null;
         target = (ResourceRefType)this.get_store().find_element_user(RESOURCEREF$46, i);
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
         return this.get_store().count_elements(RESOURCEREF$46);
      }
   }

   public void setResourceRefArray(ResourceRefType[] resourceRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(resourceRefArray, RESOURCEREF$46);
   }

   public void setResourceRefArray(int i, ResourceRefType resourceRef) {
      this.generatedSetterHelperImpl(resourceRef, RESOURCEREF$46, i, (short)2);
   }

   public ResourceRefType insertNewResourceRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceRefType target = null;
         target = (ResourceRefType)this.get_store().insert_element_user(RESOURCEREF$46, i);
         return target;
      }
   }

   public ResourceRefType addNewResourceRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceRefType target = null;
         target = (ResourceRefType)this.get_store().add_element_user(RESOURCEREF$46);
         return target;
      }
   }

   public void removeResourceRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RESOURCEREF$46, i);
      }
   }

   public ResourceEnvRefType[] getResourceEnvRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(RESOURCEENVREF$48, targetList);
         ResourceEnvRefType[] result = new ResourceEnvRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ResourceEnvRefType getResourceEnvRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceEnvRefType target = null;
         target = (ResourceEnvRefType)this.get_store().find_element_user(RESOURCEENVREF$48, i);
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
         return this.get_store().count_elements(RESOURCEENVREF$48);
      }
   }

   public void setResourceEnvRefArray(ResourceEnvRefType[] resourceEnvRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(resourceEnvRefArray, RESOURCEENVREF$48);
   }

   public void setResourceEnvRefArray(int i, ResourceEnvRefType resourceEnvRef) {
      this.generatedSetterHelperImpl(resourceEnvRef, RESOURCEENVREF$48, i, (short)2);
   }

   public ResourceEnvRefType insertNewResourceEnvRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceEnvRefType target = null;
         target = (ResourceEnvRefType)this.get_store().insert_element_user(RESOURCEENVREF$48, i);
         return target;
      }
   }

   public ResourceEnvRefType addNewResourceEnvRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceEnvRefType target = null;
         target = (ResourceEnvRefType)this.get_store().add_element_user(RESOURCEENVREF$48);
         return target;
      }
   }

   public void removeResourceEnvRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RESOURCEENVREF$48, i);
      }
   }

   public MessageDestinationRefType[] getMessageDestinationRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(MESSAGEDESTINATIONREF$50, targetList);
         MessageDestinationRefType[] result = new MessageDestinationRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public MessageDestinationRefType getMessageDestinationRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationRefType target = null;
         target = (MessageDestinationRefType)this.get_store().find_element_user(MESSAGEDESTINATIONREF$50, i);
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
         return this.get_store().count_elements(MESSAGEDESTINATIONREF$50);
      }
   }

   public void setMessageDestinationRefArray(MessageDestinationRefType[] messageDestinationRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(messageDestinationRefArray, MESSAGEDESTINATIONREF$50);
   }

   public void setMessageDestinationRefArray(int i, MessageDestinationRefType messageDestinationRef) {
      this.generatedSetterHelperImpl(messageDestinationRef, MESSAGEDESTINATIONREF$50, i, (short)2);
   }

   public MessageDestinationRefType insertNewMessageDestinationRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationRefType target = null;
         target = (MessageDestinationRefType)this.get_store().insert_element_user(MESSAGEDESTINATIONREF$50, i);
         return target;
      }
   }

   public MessageDestinationRefType addNewMessageDestinationRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationRefType target = null;
         target = (MessageDestinationRefType)this.get_store().add_element_user(MESSAGEDESTINATIONREF$50);
         return target;
      }
   }

   public void removeMessageDestinationRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MESSAGEDESTINATIONREF$50, i);
      }
   }

   public PersistenceContextRefType[] getPersistenceContextRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(PERSISTENCECONTEXTREF$52, targetList);
         PersistenceContextRefType[] result = new PersistenceContextRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public PersistenceContextRefType getPersistenceContextRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceContextRefType target = null;
         target = (PersistenceContextRefType)this.get_store().find_element_user(PERSISTENCECONTEXTREF$52, i);
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
         return this.get_store().count_elements(PERSISTENCECONTEXTREF$52);
      }
   }

   public void setPersistenceContextRefArray(PersistenceContextRefType[] persistenceContextRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(persistenceContextRefArray, PERSISTENCECONTEXTREF$52);
   }

   public void setPersistenceContextRefArray(int i, PersistenceContextRefType persistenceContextRef) {
      this.generatedSetterHelperImpl(persistenceContextRef, PERSISTENCECONTEXTREF$52, i, (short)2);
   }

   public PersistenceContextRefType insertNewPersistenceContextRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceContextRefType target = null;
         target = (PersistenceContextRefType)this.get_store().insert_element_user(PERSISTENCECONTEXTREF$52, i);
         return target;
      }
   }

   public PersistenceContextRefType addNewPersistenceContextRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceContextRefType target = null;
         target = (PersistenceContextRefType)this.get_store().add_element_user(PERSISTENCECONTEXTREF$52);
         return target;
      }
   }

   public void removePersistenceContextRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PERSISTENCECONTEXTREF$52, i);
      }
   }

   public PersistenceUnitRefType[] getPersistenceUnitRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(PERSISTENCEUNITREF$54, targetList);
         PersistenceUnitRefType[] result = new PersistenceUnitRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public PersistenceUnitRefType getPersistenceUnitRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitRefType target = null;
         target = (PersistenceUnitRefType)this.get_store().find_element_user(PERSISTENCEUNITREF$54, i);
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
         return this.get_store().count_elements(PERSISTENCEUNITREF$54);
      }
   }

   public void setPersistenceUnitRefArray(PersistenceUnitRefType[] persistenceUnitRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(persistenceUnitRefArray, PERSISTENCEUNITREF$54);
   }

   public void setPersistenceUnitRefArray(int i, PersistenceUnitRefType persistenceUnitRef) {
      this.generatedSetterHelperImpl(persistenceUnitRef, PERSISTENCEUNITREF$54, i, (short)2);
   }

   public PersistenceUnitRefType insertNewPersistenceUnitRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitRefType target = null;
         target = (PersistenceUnitRefType)this.get_store().insert_element_user(PERSISTENCEUNITREF$54, i);
         return target;
      }
   }

   public PersistenceUnitRefType addNewPersistenceUnitRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitRefType target = null;
         target = (PersistenceUnitRefType)this.get_store().add_element_user(PERSISTENCEUNITREF$54);
         return target;
      }
   }

   public void removePersistenceUnitRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PERSISTENCEUNITREF$54, i);
      }
   }

   public LifecycleCallbackType[] getPostConstructArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(POSTCONSTRUCT$56, targetList);
         LifecycleCallbackType[] result = new LifecycleCallbackType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public LifecycleCallbackType getPostConstructArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LifecycleCallbackType target = null;
         target = (LifecycleCallbackType)this.get_store().find_element_user(POSTCONSTRUCT$56, i);
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
         return this.get_store().count_elements(POSTCONSTRUCT$56);
      }
   }

   public void setPostConstructArray(LifecycleCallbackType[] postConstructArray) {
      this.check_orphaned();
      this.arraySetterHelper(postConstructArray, POSTCONSTRUCT$56);
   }

   public void setPostConstructArray(int i, LifecycleCallbackType postConstruct) {
      this.generatedSetterHelperImpl(postConstruct, POSTCONSTRUCT$56, i, (short)2);
   }

   public LifecycleCallbackType insertNewPostConstruct(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LifecycleCallbackType target = null;
         target = (LifecycleCallbackType)this.get_store().insert_element_user(POSTCONSTRUCT$56, i);
         return target;
      }
   }

   public LifecycleCallbackType addNewPostConstruct() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LifecycleCallbackType target = null;
         target = (LifecycleCallbackType)this.get_store().add_element_user(POSTCONSTRUCT$56);
         return target;
      }
   }

   public void removePostConstruct(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(POSTCONSTRUCT$56, i);
      }
   }

   public LifecycleCallbackType[] getPreDestroyArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(PREDESTROY$58, targetList);
         LifecycleCallbackType[] result = new LifecycleCallbackType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public LifecycleCallbackType getPreDestroyArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LifecycleCallbackType target = null;
         target = (LifecycleCallbackType)this.get_store().find_element_user(PREDESTROY$58, i);
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
         return this.get_store().count_elements(PREDESTROY$58);
      }
   }

   public void setPreDestroyArray(LifecycleCallbackType[] preDestroyArray) {
      this.check_orphaned();
      this.arraySetterHelper(preDestroyArray, PREDESTROY$58);
   }

   public void setPreDestroyArray(int i, LifecycleCallbackType preDestroy) {
      this.generatedSetterHelperImpl(preDestroy, PREDESTROY$58, i, (short)2);
   }

   public LifecycleCallbackType insertNewPreDestroy(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LifecycleCallbackType target = null;
         target = (LifecycleCallbackType)this.get_store().insert_element_user(PREDESTROY$58, i);
         return target;
      }
   }

   public LifecycleCallbackType addNewPreDestroy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LifecycleCallbackType target = null;
         target = (LifecycleCallbackType)this.get_store().add_element_user(PREDESTROY$58);
         return target;
      }
   }

   public void removePreDestroy(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PREDESTROY$58, i);
      }
   }

   public DataSourceType[] getDataSourceArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(DATASOURCE$60, targetList);
         DataSourceType[] result = new DataSourceType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public DataSourceType getDataSourceArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DataSourceType target = null;
         target = (DataSourceType)this.get_store().find_element_user(DATASOURCE$60, i);
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
         return this.get_store().count_elements(DATASOURCE$60);
      }
   }

   public void setDataSourceArray(DataSourceType[] dataSourceArray) {
      this.check_orphaned();
      this.arraySetterHelper(dataSourceArray, DATASOURCE$60);
   }

   public void setDataSourceArray(int i, DataSourceType dataSource) {
      this.generatedSetterHelperImpl(dataSource, DATASOURCE$60, i, (short)2);
   }

   public DataSourceType insertNewDataSource(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DataSourceType target = null;
         target = (DataSourceType)this.get_store().insert_element_user(DATASOURCE$60, i);
         return target;
      }
   }

   public DataSourceType addNewDataSource() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DataSourceType target = null;
         target = (DataSourceType)this.get_store().add_element_user(DATASOURCE$60);
         return target;
      }
   }

   public void removeDataSource(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DATASOURCE$60, i);
      }
   }

   public JmsConnectionFactoryType[] getJmsConnectionFactoryArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(JMSCONNECTIONFACTORY$62, targetList);
         JmsConnectionFactoryType[] result = new JmsConnectionFactoryType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public JmsConnectionFactoryType getJmsConnectionFactoryArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JmsConnectionFactoryType target = null;
         target = (JmsConnectionFactoryType)this.get_store().find_element_user(JMSCONNECTIONFACTORY$62, i);
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
         return this.get_store().count_elements(JMSCONNECTIONFACTORY$62);
      }
   }

   public void setJmsConnectionFactoryArray(JmsConnectionFactoryType[] jmsConnectionFactoryArray) {
      this.check_orphaned();
      this.arraySetterHelper(jmsConnectionFactoryArray, JMSCONNECTIONFACTORY$62);
   }

   public void setJmsConnectionFactoryArray(int i, JmsConnectionFactoryType jmsConnectionFactory) {
      this.generatedSetterHelperImpl(jmsConnectionFactory, JMSCONNECTIONFACTORY$62, i, (short)2);
   }

   public JmsConnectionFactoryType insertNewJmsConnectionFactory(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JmsConnectionFactoryType target = null;
         target = (JmsConnectionFactoryType)this.get_store().insert_element_user(JMSCONNECTIONFACTORY$62, i);
         return target;
      }
   }

   public JmsConnectionFactoryType addNewJmsConnectionFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JmsConnectionFactoryType target = null;
         target = (JmsConnectionFactoryType)this.get_store().add_element_user(JMSCONNECTIONFACTORY$62);
         return target;
      }
   }

   public void removeJmsConnectionFactory(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(JMSCONNECTIONFACTORY$62, i);
      }
   }

   public JmsDestinationType[] getJmsDestinationArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(JMSDESTINATION$64, targetList);
         JmsDestinationType[] result = new JmsDestinationType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public JmsDestinationType getJmsDestinationArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JmsDestinationType target = null;
         target = (JmsDestinationType)this.get_store().find_element_user(JMSDESTINATION$64, i);
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
         return this.get_store().count_elements(JMSDESTINATION$64);
      }
   }

   public void setJmsDestinationArray(JmsDestinationType[] jmsDestinationArray) {
      this.check_orphaned();
      this.arraySetterHelper(jmsDestinationArray, JMSDESTINATION$64);
   }

   public void setJmsDestinationArray(int i, JmsDestinationType jmsDestination) {
      this.generatedSetterHelperImpl(jmsDestination, JMSDESTINATION$64, i, (short)2);
   }

   public JmsDestinationType insertNewJmsDestination(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JmsDestinationType target = null;
         target = (JmsDestinationType)this.get_store().insert_element_user(JMSDESTINATION$64, i);
         return target;
      }
   }

   public JmsDestinationType addNewJmsDestination() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JmsDestinationType target = null;
         target = (JmsDestinationType)this.get_store().add_element_user(JMSDESTINATION$64);
         return target;
      }
   }

   public void removeJmsDestination(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(JMSDESTINATION$64, i);
      }
   }

   public MailSessionType[] getMailSessionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(MAILSESSION$66, targetList);
         MailSessionType[] result = new MailSessionType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public MailSessionType getMailSessionArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MailSessionType target = null;
         target = (MailSessionType)this.get_store().find_element_user(MAILSESSION$66, i);
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
         return this.get_store().count_elements(MAILSESSION$66);
      }
   }

   public void setMailSessionArray(MailSessionType[] mailSessionArray) {
      this.check_orphaned();
      this.arraySetterHelper(mailSessionArray, MAILSESSION$66);
   }

   public void setMailSessionArray(int i, MailSessionType mailSession) {
      this.generatedSetterHelperImpl(mailSession, MAILSESSION$66, i, (short)2);
   }

   public MailSessionType insertNewMailSession(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MailSessionType target = null;
         target = (MailSessionType)this.get_store().insert_element_user(MAILSESSION$66, i);
         return target;
      }
   }

   public MailSessionType addNewMailSession() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MailSessionType target = null;
         target = (MailSessionType)this.get_store().add_element_user(MAILSESSION$66);
         return target;
      }
   }

   public void removeMailSession(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MAILSESSION$66, i);
      }
   }

   public ConnectionFactoryResourceType[] getConnectionFactoryArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(CONNECTIONFACTORY$68, targetList);
         ConnectionFactoryResourceType[] result = new ConnectionFactoryResourceType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ConnectionFactoryResourceType getConnectionFactoryArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConnectionFactoryResourceType target = null;
         target = (ConnectionFactoryResourceType)this.get_store().find_element_user(CONNECTIONFACTORY$68, i);
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
         return this.get_store().count_elements(CONNECTIONFACTORY$68);
      }
   }

   public void setConnectionFactoryArray(ConnectionFactoryResourceType[] connectionFactoryArray) {
      this.check_orphaned();
      this.arraySetterHelper(connectionFactoryArray, CONNECTIONFACTORY$68);
   }

   public void setConnectionFactoryArray(int i, ConnectionFactoryResourceType connectionFactory) {
      this.generatedSetterHelperImpl(connectionFactory, CONNECTIONFACTORY$68, i, (short)2);
   }

   public ConnectionFactoryResourceType insertNewConnectionFactory(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConnectionFactoryResourceType target = null;
         target = (ConnectionFactoryResourceType)this.get_store().insert_element_user(CONNECTIONFACTORY$68, i);
         return target;
      }
   }

   public ConnectionFactoryResourceType addNewConnectionFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConnectionFactoryResourceType target = null;
         target = (ConnectionFactoryResourceType)this.get_store().add_element_user(CONNECTIONFACTORY$68);
         return target;
      }
   }

   public void removeConnectionFactory(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONNECTIONFACTORY$68, i);
      }
   }

   public AdministeredObjectType[] getAdministeredObjectArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(ADMINISTEREDOBJECT$70, targetList);
         AdministeredObjectType[] result = new AdministeredObjectType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public AdministeredObjectType getAdministeredObjectArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AdministeredObjectType target = null;
         target = (AdministeredObjectType)this.get_store().find_element_user(ADMINISTEREDOBJECT$70, i);
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
         return this.get_store().count_elements(ADMINISTEREDOBJECT$70);
      }
   }

   public void setAdministeredObjectArray(AdministeredObjectType[] administeredObjectArray) {
      this.check_orphaned();
      this.arraySetterHelper(administeredObjectArray, ADMINISTEREDOBJECT$70);
   }

   public void setAdministeredObjectArray(int i, AdministeredObjectType administeredObject) {
      this.generatedSetterHelperImpl(administeredObject, ADMINISTEREDOBJECT$70, i, (short)2);
   }

   public AdministeredObjectType insertNewAdministeredObject(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AdministeredObjectType target = null;
         target = (AdministeredObjectType)this.get_store().insert_element_user(ADMINISTEREDOBJECT$70, i);
         return target;
      }
   }

   public AdministeredObjectType addNewAdministeredObject() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AdministeredObjectType target = null;
         target = (AdministeredObjectType)this.get_store().add_element_user(ADMINISTEREDOBJECT$70);
         return target;
      }
   }

   public void removeAdministeredObject(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ADMINISTEREDOBJECT$70, i);
      }
   }

   public MessageDestinationType[] getMessageDestinationArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(MESSAGEDESTINATION$72, targetList);
         MessageDestinationType[] result = new MessageDestinationType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public MessageDestinationType getMessageDestinationArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationType target = null;
         target = (MessageDestinationType)this.get_store().find_element_user(MESSAGEDESTINATION$72, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfMessageDestinationArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MESSAGEDESTINATION$72);
      }
   }

   public void setMessageDestinationArray(MessageDestinationType[] messageDestinationArray) {
      this.check_orphaned();
      this.arraySetterHelper(messageDestinationArray, MESSAGEDESTINATION$72);
   }

   public void setMessageDestinationArray(int i, MessageDestinationType messageDestination) {
      this.generatedSetterHelperImpl(messageDestination, MESSAGEDESTINATION$72, i, (short)2);
   }

   public MessageDestinationType insertNewMessageDestination(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationType target = null;
         target = (MessageDestinationType)this.get_store().insert_element_user(MESSAGEDESTINATION$72, i);
         return target;
      }
   }

   public MessageDestinationType addNewMessageDestination() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationType target = null;
         target = (MessageDestinationType)this.get_store().add_element_user(MESSAGEDESTINATION$72);
         return target;
      }
   }

   public void removeMessageDestination(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MESSAGEDESTINATION$72, i);
      }
   }

   public LocaleEncodingMappingListType[] getLocaleEncodingMappingListArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(LOCALEENCODINGMAPPINGLIST$74, targetList);
         LocaleEncodingMappingListType[] result = new LocaleEncodingMappingListType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public LocaleEncodingMappingListType getLocaleEncodingMappingListArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LocaleEncodingMappingListType target = null;
         target = (LocaleEncodingMappingListType)this.get_store().find_element_user(LOCALEENCODINGMAPPINGLIST$74, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfLocaleEncodingMappingListArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LOCALEENCODINGMAPPINGLIST$74);
      }
   }

   public void setLocaleEncodingMappingListArray(LocaleEncodingMappingListType[] localeEncodingMappingListArray) {
      this.check_orphaned();
      this.arraySetterHelper(localeEncodingMappingListArray, LOCALEENCODINGMAPPINGLIST$74);
   }

   public void setLocaleEncodingMappingListArray(int i, LocaleEncodingMappingListType localeEncodingMappingList) {
      this.generatedSetterHelperImpl(localeEncodingMappingList, LOCALEENCODINGMAPPINGLIST$74, i, (short)2);
   }

   public LocaleEncodingMappingListType insertNewLocaleEncodingMappingList(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LocaleEncodingMappingListType target = null;
         target = (LocaleEncodingMappingListType)this.get_store().insert_element_user(LOCALEENCODINGMAPPINGLIST$74, i);
         return target;
      }
   }

   public LocaleEncodingMappingListType addNewLocaleEncodingMappingList() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LocaleEncodingMappingListType target = null;
         target = (LocaleEncodingMappingListType)this.get_store().add_element_user(LOCALEENCODINGMAPPINGLIST$74);
         return target;
      }
   }

   public void removeLocaleEncodingMappingList(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LOCALEENCODINGMAPPINGLIST$74, i);
      }
   }

   public OrderingType[] getOrderingArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(ORDERING$76, targetList);
         OrderingType[] result = new OrderingType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public OrderingType getOrderingArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         OrderingType target = null;
         target = (OrderingType)this.get_store().find_element_user(ORDERING$76, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfOrderingArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ORDERING$76);
      }
   }

   public void setOrderingArray(OrderingType[] orderingArray) {
      this.check_orphaned();
      this.arraySetterHelper(orderingArray, ORDERING$76);
   }

   public void setOrderingArray(int i, OrderingType ordering) {
      this.generatedSetterHelperImpl(ordering, ORDERING$76, i, (short)2);
   }

   public OrderingType insertNewOrdering(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         OrderingType target = null;
         target = (OrderingType)this.get_store().insert_element_user(ORDERING$76, i);
         return target;
      }
   }

   public OrderingType addNewOrdering() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         OrderingType target = null;
         target = (OrderingType)this.get_store().add_element_user(ORDERING$76);
         return target;
      }
   }

   public void removeOrdering(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ORDERING$76, i);
      }
   }

   public WebAppVersionType.Enum getVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(VERSION$78);
         return target == null ? null : (WebAppVersionType.Enum)target.getEnumValue();
      }
   }

   public WebAppVersionType xgetVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WebAppVersionType target = null;
         target = (WebAppVersionType)this.get_store().find_attribute_user(VERSION$78);
         return target;
      }
   }

   public void setVersion(WebAppVersionType.Enum version) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(VERSION$78);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(VERSION$78);
         }

         target.setEnumValue(version);
      }
   }

   public void xsetVersion(WebAppVersionType version) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WebAppVersionType target = null;
         target = (WebAppVersionType)this.get_store().find_attribute_user(VERSION$78);
         if (target == null) {
            target = (WebAppVersionType)this.get_store().add_attribute_user(VERSION$78);
         }

         target.set(version);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$80);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$80);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$80) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$80);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$80);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$80);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$80);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$80);
      }
   }

   public boolean getMetadataComplete() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(METADATACOMPLETE$82);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetMetadataComplete() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_attribute_user(METADATACOMPLETE$82);
         return target;
      }
   }

   public boolean isSetMetadataComplete() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(METADATACOMPLETE$82) != null;
      }
   }

   public void setMetadataComplete(boolean metadataComplete) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(METADATACOMPLETE$82);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(METADATACOMPLETE$82);
         }

         target.setBooleanValue(metadataComplete);
      }
   }

   public void xsetMetadataComplete(XmlBoolean metadataComplete) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_attribute_user(METADATACOMPLETE$82);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_attribute_user(METADATACOMPLETE$82);
         }

         target.set(metadataComplete);
      }
   }

   public void unsetMetadataComplete() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(METADATACOMPLETE$82);
      }
   }
}
