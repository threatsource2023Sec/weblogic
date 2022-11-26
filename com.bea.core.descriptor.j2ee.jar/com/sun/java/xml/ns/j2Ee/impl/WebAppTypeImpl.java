package com.sun.java.xml.ns.j2Ee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.sun.java.xml.ns.j2Ee.DescriptionType;
import com.sun.java.xml.ns.j2Ee.DisplayNameType;
import com.sun.java.xml.ns.j2Ee.EjbLocalRefType;
import com.sun.java.xml.ns.j2Ee.EjbRefType;
import com.sun.java.xml.ns.j2Ee.EmptyType;
import com.sun.java.xml.ns.j2Ee.EnvEntryType;
import com.sun.java.xml.ns.j2Ee.ErrorPageType;
import com.sun.java.xml.ns.j2Ee.FilterMappingType;
import com.sun.java.xml.ns.j2Ee.FilterType;
import com.sun.java.xml.ns.j2Ee.IconType;
import com.sun.java.xml.ns.j2Ee.JspConfigType;
import com.sun.java.xml.ns.j2Ee.ListenerType;
import com.sun.java.xml.ns.j2Ee.LocaleEncodingMappingListType;
import com.sun.java.xml.ns.j2Ee.LoginConfigType;
import com.sun.java.xml.ns.j2Ee.MessageDestinationRefType;
import com.sun.java.xml.ns.j2Ee.MessageDestinationType;
import com.sun.java.xml.ns.j2Ee.MimeMappingType;
import com.sun.java.xml.ns.j2Ee.ParamValueType;
import com.sun.java.xml.ns.j2Ee.ResourceEnvRefType;
import com.sun.java.xml.ns.j2Ee.ResourceRefType;
import com.sun.java.xml.ns.j2Ee.SecurityConstraintType;
import com.sun.java.xml.ns.j2Ee.SecurityRoleType;
import com.sun.java.xml.ns.j2Ee.ServiceRefType;
import com.sun.java.xml.ns.j2Ee.ServletMappingType;
import com.sun.java.xml.ns.j2Ee.ServletType;
import com.sun.java.xml.ns.j2Ee.SessionConfigType;
import com.sun.java.xml.ns.j2Ee.WebAppType;
import com.sun.java.xml.ns.j2Ee.WebAppVersionType;
import com.sun.java.xml.ns.j2Ee.WelcomeFileListType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class WebAppTypeImpl extends XmlComplexContentImpl implements WebAppType {
   private static final long serialVersionUID = 1L;
   private static final QName DESCRIPTION$0 = new QName("http://java.sun.com/xml/ns/j2ee", "description");
   private static final QName DISPLAYNAME$2 = new QName("http://java.sun.com/xml/ns/j2ee", "display-name");
   private static final QName ICON$4 = new QName("http://java.sun.com/xml/ns/j2ee", "icon");
   private static final QName DISTRIBUTABLE$6 = new QName("http://java.sun.com/xml/ns/j2ee", "distributable");
   private static final QName CONTEXTPARAM$8 = new QName("http://java.sun.com/xml/ns/j2ee", "context-param");
   private static final QName FILTER$10 = new QName("http://java.sun.com/xml/ns/j2ee", "filter");
   private static final QName FILTERMAPPING$12 = new QName("http://java.sun.com/xml/ns/j2ee", "filter-mapping");
   private static final QName LISTENER$14 = new QName("http://java.sun.com/xml/ns/j2ee", "listener");
   private static final QName SERVLET$16 = new QName("http://java.sun.com/xml/ns/j2ee", "servlet");
   private static final QName SERVLETMAPPING$18 = new QName("http://java.sun.com/xml/ns/j2ee", "servlet-mapping");
   private static final QName SESSIONCONFIG$20 = new QName("http://java.sun.com/xml/ns/j2ee", "session-config");
   private static final QName MIMEMAPPING$22 = new QName("http://java.sun.com/xml/ns/j2ee", "mime-mapping");
   private static final QName WELCOMEFILELIST$24 = new QName("http://java.sun.com/xml/ns/j2ee", "welcome-file-list");
   private static final QName ERRORPAGE$26 = new QName("http://java.sun.com/xml/ns/j2ee", "error-page");
   private static final QName JSPCONFIG$28 = new QName("http://java.sun.com/xml/ns/j2ee", "jsp-config");
   private static final QName SECURITYCONSTRAINT$30 = new QName("http://java.sun.com/xml/ns/j2ee", "security-constraint");
   private static final QName LOGINCONFIG$32 = new QName("http://java.sun.com/xml/ns/j2ee", "login-config");
   private static final QName SECURITYROLE$34 = new QName("http://java.sun.com/xml/ns/j2ee", "security-role");
   private static final QName ENVENTRY$36 = new QName("http://java.sun.com/xml/ns/j2ee", "env-entry");
   private static final QName EJBREF$38 = new QName("http://java.sun.com/xml/ns/j2ee", "ejb-ref");
   private static final QName EJBLOCALREF$40 = new QName("http://java.sun.com/xml/ns/j2ee", "ejb-local-ref");
   private static final QName SERVICEREF$42 = new QName("http://java.sun.com/xml/ns/j2ee", "service-ref");
   private static final QName RESOURCEREF$44 = new QName("http://java.sun.com/xml/ns/j2ee", "resource-ref");
   private static final QName RESOURCEENVREF$46 = new QName("http://java.sun.com/xml/ns/j2ee", "resource-env-ref");
   private static final QName MESSAGEDESTINATIONREF$48 = new QName("http://java.sun.com/xml/ns/j2ee", "message-destination-ref");
   private static final QName MESSAGEDESTINATION$50 = new QName("http://java.sun.com/xml/ns/j2ee", "message-destination");
   private static final QName LOCALEENCODINGMAPPINGLIST$52 = new QName("http://java.sun.com/xml/ns/j2ee", "locale-encoding-mapping-list");
   private static final QName VERSION$54 = new QName("", "version");
   private static final QName ID$56 = new QName("", "id");

   public WebAppTypeImpl(SchemaType sType) {
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

   public EmptyType[] getDistributableArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(DISTRIBUTABLE$6, targetList);
         EmptyType[] result = new EmptyType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public EmptyType getDistributableArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EmptyType target = null;
         target = (EmptyType)this.get_store().find_element_user(DISTRIBUTABLE$6, i);
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
         return this.get_store().count_elements(DISTRIBUTABLE$6);
      }
   }

   public void setDistributableArray(EmptyType[] distributableArray) {
      this.check_orphaned();
      this.arraySetterHelper(distributableArray, DISTRIBUTABLE$6);
   }

   public void setDistributableArray(int i, EmptyType distributable) {
      this.generatedSetterHelperImpl(distributable, DISTRIBUTABLE$6, i, (short)2);
   }

   public EmptyType insertNewDistributable(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EmptyType target = null;
         target = (EmptyType)this.get_store().insert_element_user(DISTRIBUTABLE$6, i);
         return target;
      }
   }

   public EmptyType addNewDistributable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EmptyType target = null;
         target = (EmptyType)this.get_store().add_element_user(DISTRIBUTABLE$6);
         return target;
      }
   }

   public void removeDistributable(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DISTRIBUTABLE$6, i);
      }
   }

   public ParamValueType[] getContextParamArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(CONTEXTPARAM$8, targetList);
         ParamValueType[] result = new ParamValueType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ParamValueType getContextParamArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ParamValueType target = null;
         target = (ParamValueType)this.get_store().find_element_user(CONTEXTPARAM$8, i);
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
         return this.get_store().count_elements(CONTEXTPARAM$8);
      }
   }

   public void setContextParamArray(ParamValueType[] contextParamArray) {
      this.check_orphaned();
      this.arraySetterHelper(contextParamArray, CONTEXTPARAM$8);
   }

   public void setContextParamArray(int i, ParamValueType contextParam) {
      this.generatedSetterHelperImpl(contextParam, CONTEXTPARAM$8, i, (short)2);
   }

   public ParamValueType insertNewContextParam(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ParamValueType target = null;
         target = (ParamValueType)this.get_store().insert_element_user(CONTEXTPARAM$8, i);
         return target;
      }
   }

   public ParamValueType addNewContextParam() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ParamValueType target = null;
         target = (ParamValueType)this.get_store().add_element_user(CONTEXTPARAM$8);
         return target;
      }
   }

   public void removeContextParam(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONTEXTPARAM$8, i);
      }
   }

   public FilterType[] getFilterArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(FILTER$10, targetList);
         FilterType[] result = new FilterType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public FilterType getFilterArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FilterType target = null;
         target = (FilterType)this.get_store().find_element_user(FILTER$10, i);
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
         return this.get_store().count_elements(FILTER$10);
      }
   }

   public void setFilterArray(FilterType[] filterArray) {
      this.check_orphaned();
      this.arraySetterHelper(filterArray, FILTER$10);
   }

   public void setFilterArray(int i, FilterType filter) {
      this.generatedSetterHelperImpl(filter, FILTER$10, i, (short)2);
   }

   public FilterType insertNewFilter(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FilterType target = null;
         target = (FilterType)this.get_store().insert_element_user(FILTER$10, i);
         return target;
      }
   }

   public FilterType addNewFilter() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FilterType target = null;
         target = (FilterType)this.get_store().add_element_user(FILTER$10);
         return target;
      }
   }

   public void removeFilter(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FILTER$10, i);
      }
   }

   public FilterMappingType[] getFilterMappingArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(FILTERMAPPING$12, targetList);
         FilterMappingType[] result = new FilterMappingType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public FilterMappingType getFilterMappingArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FilterMappingType target = null;
         target = (FilterMappingType)this.get_store().find_element_user(FILTERMAPPING$12, i);
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
         return this.get_store().count_elements(FILTERMAPPING$12);
      }
   }

   public void setFilterMappingArray(FilterMappingType[] filterMappingArray) {
      this.check_orphaned();
      this.arraySetterHelper(filterMappingArray, FILTERMAPPING$12);
   }

   public void setFilterMappingArray(int i, FilterMappingType filterMapping) {
      this.generatedSetterHelperImpl(filterMapping, FILTERMAPPING$12, i, (short)2);
   }

   public FilterMappingType insertNewFilterMapping(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FilterMappingType target = null;
         target = (FilterMappingType)this.get_store().insert_element_user(FILTERMAPPING$12, i);
         return target;
      }
   }

   public FilterMappingType addNewFilterMapping() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FilterMappingType target = null;
         target = (FilterMappingType)this.get_store().add_element_user(FILTERMAPPING$12);
         return target;
      }
   }

   public void removeFilterMapping(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FILTERMAPPING$12, i);
      }
   }

   public ListenerType[] getListenerArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(LISTENER$14, targetList);
         ListenerType[] result = new ListenerType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ListenerType getListenerArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ListenerType target = null;
         target = (ListenerType)this.get_store().find_element_user(LISTENER$14, i);
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
         return this.get_store().count_elements(LISTENER$14);
      }
   }

   public void setListenerArray(ListenerType[] listenerArray) {
      this.check_orphaned();
      this.arraySetterHelper(listenerArray, LISTENER$14);
   }

   public void setListenerArray(int i, ListenerType listener) {
      this.generatedSetterHelperImpl(listener, LISTENER$14, i, (short)2);
   }

   public ListenerType insertNewListener(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ListenerType target = null;
         target = (ListenerType)this.get_store().insert_element_user(LISTENER$14, i);
         return target;
      }
   }

   public ListenerType addNewListener() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ListenerType target = null;
         target = (ListenerType)this.get_store().add_element_user(LISTENER$14);
         return target;
      }
   }

   public void removeListener(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LISTENER$14, i);
      }
   }

   public ServletType[] getServletArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(SERVLET$16, targetList);
         ServletType[] result = new ServletType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ServletType getServletArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServletType target = null;
         target = (ServletType)this.get_store().find_element_user(SERVLET$16, i);
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
         return this.get_store().count_elements(SERVLET$16);
      }
   }

   public void setServletArray(ServletType[] servletArray) {
      this.check_orphaned();
      this.arraySetterHelper(servletArray, SERVLET$16);
   }

   public void setServletArray(int i, ServletType servlet) {
      this.generatedSetterHelperImpl(servlet, SERVLET$16, i, (short)2);
   }

   public ServletType insertNewServlet(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServletType target = null;
         target = (ServletType)this.get_store().insert_element_user(SERVLET$16, i);
         return target;
      }
   }

   public ServletType addNewServlet() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServletType target = null;
         target = (ServletType)this.get_store().add_element_user(SERVLET$16);
         return target;
      }
   }

   public void removeServlet(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SERVLET$16, i);
      }
   }

   public ServletMappingType[] getServletMappingArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(SERVLETMAPPING$18, targetList);
         ServletMappingType[] result = new ServletMappingType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ServletMappingType getServletMappingArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServletMappingType target = null;
         target = (ServletMappingType)this.get_store().find_element_user(SERVLETMAPPING$18, i);
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
         return this.get_store().count_elements(SERVLETMAPPING$18);
      }
   }

   public void setServletMappingArray(ServletMappingType[] servletMappingArray) {
      this.check_orphaned();
      this.arraySetterHelper(servletMappingArray, SERVLETMAPPING$18);
   }

   public void setServletMappingArray(int i, ServletMappingType servletMapping) {
      this.generatedSetterHelperImpl(servletMapping, SERVLETMAPPING$18, i, (short)2);
   }

   public ServletMappingType insertNewServletMapping(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServletMappingType target = null;
         target = (ServletMappingType)this.get_store().insert_element_user(SERVLETMAPPING$18, i);
         return target;
      }
   }

   public ServletMappingType addNewServletMapping() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServletMappingType target = null;
         target = (ServletMappingType)this.get_store().add_element_user(SERVLETMAPPING$18);
         return target;
      }
   }

   public void removeServletMapping(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SERVLETMAPPING$18, i);
      }
   }

   public SessionConfigType[] getSessionConfigArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(SESSIONCONFIG$20, targetList);
         SessionConfigType[] result = new SessionConfigType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public SessionConfigType getSessionConfigArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SessionConfigType target = null;
         target = (SessionConfigType)this.get_store().find_element_user(SESSIONCONFIG$20, i);
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
         return this.get_store().count_elements(SESSIONCONFIG$20);
      }
   }

   public void setSessionConfigArray(SessionConfigType[] sessionConfigArray) {
      this.check_orphaned();
      this.arraySetterHelper(sessionConfigArray, SESSIONCONFIG$20);
   }

   public void setSessionConfigArray(int i, SessionConfigType sessionConfig) {
      this.generatedSetterHelperImpl(sessionConfig, SESSIONCONFIG$20, i, (short)2);
   }

   public SessionConfigType insertNewSessionConfig(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SessionConfigType target = null;
         target = (SessionConfigType)this.get_store().insert_element_user(SESSIONCONFIG$20, i);
         return target;
      }
   }

   public SessionConfigType addNewSessionConfig() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SessionConfigType target = null;
         target = (SessionConfigType)this.get_store().add_element_user(SESSIONCONFIG$20);
         return target;
      }
   }

   public void removeSessionConfig(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SESSIONCONFIG$20, i);
      }
   }

   public MimeMappingType[] getMimeMappingArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(MIMEMAPPING$22, targetList);
         MimeMappingType[] result = new MimeMappingType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public MimeMappingType getMimeMappingArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MimeMappingType target = null;
         target = (MimeMappingType)this.get_store().find_element_user(MIMEMAPPING$22, i);
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
         return this.get_store().count_elements(MIMEMAPPING$22);
      }
   }

   public void setMimeMappingArray(MimeMappingType[] mimeMappingArray) {
      this.check_orphaned();
      this.arraySetterHelper(mimeMappingArray, MIMEMAPPING$22);
   }

   public void setMimeMappingArray(int i, MimeMappingType mimeMapping) {
      this.generatedSetterHelperImpl(mimeMapping, MIMEMAPPING$22, i, (short)2);
   }

   public MimeMappingType insertNewMimeMapping(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MimeMappingType target = null;
         target = (MimeMappingType)this.get_store().insert_element_user(MIMEMAPPING$22, i);
         return target;
      }
   }

   public MimeMappingType addNewMimeMapping() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MimeMappingType target = null;
         target = (MimeMappingType)this.get_store().add_element_user(MIMEMAPPING$22);
         return target;
      }
   }

   public void removeMimeMapping(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MIMEMAPPING$22, i);
      }
   }

   public WelcomeFileListType[] getWelcomeFileListArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(WELCOMEFILELIST$24, targetList);
         WelcomeFileListType[] result = new WelcomeFileListType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public WelcomeFileListType getWelcomeFileListArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WelcomeFileListType target = null;
         target = (WelcomeFileListType)this.get_store().find_element_user(WELCOMEFILELIST$24, i);
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
         return this.get_store().count_elements(WELCOMEFILELIST$24);
      }
   }

   public void setWelcomeFileListArray(WelcomeFileListType[] welcomeFileListArray) {
      this.check_orphaned();
      this.arraySetterHelper(welcomeFileListArray, WELCOMEFILELIST$24);
   }

   public void setWelcomeFileListArray(int i, WelcomeFileListType welcomeFileList) {
      this.generatedSetterHelperImpl(welcomeFileList, WELCOMEFILELIST$24, i, (short)2);
   }

   public WelcomeFileListType insertNewWelcomeFileList(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WelcomeFileListType target = null;
         target = (WelcomeFileListType)this.get_store().insert_element_user(WELCOMEFILELIST$24, i);
         return target;
      }
   }

   public WelcomeFileListType addNewWelcomeFileList() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WelcomeFileListType target = null;
         target = (WelcomeFileListType)this.get_store().add_element_user(WELCOMEFILELIST$24);
         return target;
      }
   }

   public void removeWelcomeFileList(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(WELCOMEFILELIST$24, i);
      }
   }

   public ErrorPageType[] getErrorPageArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(ERRORPAGE$26, targetList);
         ErrorPageType[] result = new ErrorPageType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ErrorPageType getErrorPageArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ErrorPageType target = null;
         target = (ErrorPageType)this.get_store().find_element_user(ERRORPAGE$26, i);
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
         return this.get_store().count_elements(ERRORPAGE$26);
      }
   }

   public void setErrorPageArray(ErrorPageType[] errorPageArray) {
      this.check_orphaned();
      this.arraySetterHelper(errorPageArray, ERRORPAGE$26);
   }

   public void setErrorPageArray(int i, ErrorPageType errorPage) {
      this.generatedSetterHelperImpl(errorPage, ERRORPAGE$26, i, (short)2);
   }

   public ErrorPageType insertNewErrorPage(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ErrorPageType target = null;
         target = (ErrorPageType)this.get_store().insert_element_user(ERRORPAGE$26, i);
         return target;
      }
   }

   public ErrorPageType addNewErrorPage() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ErrorPageType target = null;
         target = (ErrorPageType)this.get_store().add_element_user(ERRORPAGE$26);
         return target;
      }
   }

   public void removeErrorPage(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ERRORPAGE$26, i);
      }
   }

   public JspConfigType[] getJspConfigArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(JSPCONFIG$28, targetList);
         JspConfigType[] result = new JspConfigType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public JspConfigType getJspConfigArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JspConfigType target = null;
         target = (JspConfigType)this.get_store().find_element_user(JSPCONFIG$28, i);
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
         return this.get_store().count_elements(JSPCONFIG$28);
      }
   }

   public void setJspConfigArray(JspConfigType[] jspConfigArray) {
      this.check_orphaned();
      this.arraySetterHelper(jspConfigArray, JSPCONFIG$28);
   }

   public void setJspConfigArray(int i, JspConfigType jspConfig) {
      this.generatedSetterHelperImpl(jspConfig, JSPCONFIG$28, i, (short)2);
   }

   public JspConfigType insertNewJspConfig(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JspConfigType target = null;
         target = (JspConfigType)this.get_store().insert_element_user(JSPCONFIG$28, i);
         return target;
      }
   }

   public JspConfigType addNewJspConfig() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JspConfigType target = null;
         target = (JspConfigType)this.get_store().add_element_user(JSPCONFIG$28);
         return target;
      }
   }

   public void removeJspConfig(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(JSPCONFIG$28, i);
      }
   }

   public SecurityConstraintType[] getSecurityConstraintArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(SECURITYCONSTRAINT$30, targetList);
         SecurityConstraintType[] result = new SecurityConstraintType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public SecurityConstraintType getSecurityConstraintArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SecurityConstraintType target = null;
         target = (SecurityConstraintType)this.get_store().find_element_user(SECURITYCONSTRAINT$30, i);
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
         return this.get_store().count_elements(SECURITYCONSTRAINT$30);
      }
   }

   public void setSecurityConstraintArray(SecurityConstraintType[] securityConstraintArray) {
      this.check_orphaned();
      this.arraySetterHelper(securityConstraintArray, SECURITYCONSTRAINT$30);
   }

   public void setSecurityConstraintArray(int i, SecurityConstraintType securityConstraint) {
      this.generatedSetterHelperImpl(securityConstraint, SECURITYCONSTRAINT$30, i, (short)2);
   }

   public SecurityConstraintType insertNewSecurityConstraint(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SecurityConstraintType target = null;
         target = (SecurityConstraintType)this.get_store().insert_element_user(SECURITYCONSTRAINT$30, i);
         return target;
      }
   }

   public SecurityConstraintType addNewSecurityConstraint() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SecurityConstraintType target = null;
         target = (SecurityConstraintType)this.get_store().add_element_user(SECURITYCONSTRAINT$30);
         return target;
      }
   }

   public void removeSecurityConstraint(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SECURITYCONSTRAINT$30, i);
      }
   }

   public LoginConfigType[] getLoginConfigArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(LOGINCONFIG$32, targetList);
         LoginConfigType[] result = new LoginConfigType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public LoginConfigType getLoginConfigArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LoginConfigType target = null;
         target = (LoginConfigType)this.get_store().find_element_user(LOGINCONFIG$32, i);
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
         return this.get_store().count_elements(LOGINCONFIG$32);
      }
   }

   public void setLoginConfigArray(LoginConfigType[] loginConfigArray) {
      this.check_orphaned();
      this.arraySetterHelper(loginConfigArray, LOGINCONFIG$32);
   }

   public void setLoginConfigArray(int i, LoginConfigType loginConfig) {
      this.generatedSetterHelperImpl(loginConfig, LOGINCONFIG$32, i, (short)2);
   }

   public LoginConfigType insertNewLoginConfig(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LoginConfigType target = null;
         target = (LoginConfigType)this.get_store().insert_element_user(LOGINCONFIG$32, i);
         return target;
      }
   }

   public LoginConfigType addNewLoginConfig() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LoginConfigType target = null;
         target = (LoginConfigType)this.get_store().add_element_user(LOGINCONFIG$32);
         return target;
      }
   }

   public void removeLoginConfig(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LOGINCONFIG$32, i);
      }
   }

   public SecurityRoleType[] getSecurityRoleArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(SECURITYROLE$34, targetList);
         SecurityRoleType[] result = new SecurityRoleType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public SecurityRoleType getSecurityRoleArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SecurityRoleType target = null;
         target = (SecurityRoleType)this.get_store().find_element_user(SECURITYROLE$34, i);
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
         return this.get_store().count_elements(SECURITYROLE$34);
      }
   }

   public void setSecurityRoleArray(SecurityRoleType[] securityRoleArray) {
      this.check_orphaned();
      this.arraySetterHelper(securityRoleArray, SECURITYROLE$34);
   }

   public void setSecurityRoleArray(int i, SecurityRoleType securityRole) {
      this.generatedSetterHelperImpl(securityRole, SECURITYROLE$34, i, (short)2);
   }

   public SecurityRoleType insertNewSecurityRole(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SecurityRoleType target = null;
         target = (SecurityRoleType)this.get_store().insert_element_user(SECURITYROLE$34, i);
         return target;
      }
   }

   public SecurityRoleType addNewSecurityRole() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SecurityRoleType target = null;
         target = (SecurityRoleType)this.get_store().add_element_user(SECURITYROLE$34);
         return target;
      }
   }

   public void removeSecurityRole(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SECURITYROLE$34, i);
      }
   }

   public EnvEntryType[] getEnvEntryArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(ENVENTRY$36, targetList);
         EnvEntryType[] result = new EnvEntryType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public EnvEntryType getEnvEntryArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EnvEntryType target = null;
         target = (EnvEntryType)this.get_store().find_element_user(ENVENTRY$36, i);
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
         return this.get_store().count_elements(ENVENTRY$36);
      }
   }

   public void setEnvEntryArray(EnvEntryType[] envEntryArray) {
      this.check_orphaned();
      this.arraySetterHelper(envEntryArray, ENVENTRY$36);
   }

   public void setEnvEntryArray(int i, EnvEntryType envEntry) {
      this.generatedSetterHelperImpl(envEntry, ENVENTRY$36, i, (short)2);
   }

   public EnvEntryType insertNewEnvEntry(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EnvEntryType target = null;
         target = (EnvEntryType)this.get_store().insert_element_user(ENVENTRY$36, i);
         return target;
      }
   }

   public EnvEntryType addNewEnvEntry() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EnvEntryType target = null;
         target = (EnvEntryType)this.get_store().add_element_user(ENVENTRY$36);
         return target;
      }
   }

   public void removeEnvEntry(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ENVENTRY$36, i);
      }
   }

   public EjbRefType[] getEjbRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(EJBREF$38, targetList);
         EjbRefType[] result = new EjbRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public EjbRefType getEjbRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbRefType target = null;
         target = (EjbRefType)this.get_store().find_element_user(EJBREF$38, i);
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
         return this.get_store().count_elements(EJBREF$38);
      }
   }

   public void setEjbRefArray(EjbRefType[] ejbRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(ejbRefArray, EJBREF$38);
   }

   public void setEjbRefArray(int i, EjbRefType ejbRef) {
      this.generatedSetterHelperImpl(ejbRef, EJBREF$38, i, (short)2);
   }

   public EjbRefType insertNewEjbRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbRefType target = null;
         target = (EjbRefType)this.get_store().insert_element_user(EJBREF$38, i);
         return target;
      }
   }

   public EjbRefType addNewEjbRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbRefType target = null;
         target = (EjbRefType)this.get_store().add_element_user(EJBREF$38);
         return target;
      }
   }

   public void removeEjbRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(EJBREF$38, i);
      }
   }

   public EjbLocalRefType[] getEjbLocalRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(EJBLOCALREF$40, targetList);
         EjbLocalRefType[] result = new EjbLocalRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public EjbLocalRefType getEjbLocalRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbLocalRefType target = null;
         target = (EjbLocalRefType)this.get_store().find_element_user(EJBLOCALREF$40, i);
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
         return this.get_store().count_elements(EJBLOCALREF$40);
      }
   }

   public void setEjbLocalRefArray(EjbLocalRefType[] ejbLocalRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(ejbLocalRefArray, EJBLOCALREF$40);
   }

   public void setEjbLocalRefArray(int i, EjbLocalRefType ejbLocalRef) {
      this.generatedSetterHelperImpl(ejbLocalRef, EJBLOCALREF$40, i, (short)2);
   }

   public EjbLocalRefType insertNewEjbLocalRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbLocalRefType target = null;
         target = (EjbLocalRefType)this.get_store().insert_element_user(EJBLOCALREF$40, i);
         return target;
      }
   }

   public EjbLocalRefType addNewEjbLocalRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbLocalRefType target = null;
         target = (EjbLocalRefType)this.get_store().add_element_user(EJBLOCALREF$40);
         return target;
      }
   }

   public void removeEjbLocalRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(EJBLOCALREF$40, i);
      }
   }

   public ServiceRefType[] getServiceRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(SERVICEREF$42, targetList);
         ServiceRefType[] result = new ServiceRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ServiceRefType getServiceRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServiceRefType target = null;
         target = (ServiceRefType)this.get_store().find_element_user(SERVICEREF$42, i);
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
         return this.get_store().count_elements(SERVICEREF$42);
      }
   }

   public void setServiceRefArray(ServiceRefType[] serviceRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(serviceRefArray, SERVICEREF$42);
   }

   public void setServiceRefArray(int i, ServiceRefType serviceRef) {
      this.generatedSetterHelperImpl(serviceRef, SERVICEREF$42, i, (short)2);
   }

   public ServiceRefType insertNewServiceRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServiceRefType target = null;
         target = (ServiceRefType)this.get_store().insert_element_user(SERVICEREF$42, i);
         return target;
      }
   }

   public ServiceRefType addNewServiceRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServiceRefType target = null;
         target = (ServiceRefType)this.get_store().add_element_user(SERVICEREF$42);
         return target;
      }
   }

   public void removeServiceRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SERVICEREF$42, i);
      }
   }

   public ResourceRefType[] getResourceRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(RESOURCEREF$44, targetList);
         ResourceRefType[] result = new ResourceRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ResourceRefType getResourceRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceRefType target = null;
         target = (ResourceRefType)this.get_store().find_element_user(RESOURCEREF$44, i);
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
         return this.get_store().count_elements(RESOURCEREF$44);
      }
   }

   public void setResourceRefArray(ResourceRefType[] resourceRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(resourceRefArray, RESOURCEREF$44);
   }

   public void setResourceRefArray(int i, ResourceRefType resourceRef) {
      this.generatedSetterHelperImpl(resourceRef, RESOURCEREF$44, i, (short)2);
   }

   public ResourceRefType insertNewResourceRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceRefType target = null;
         target = (ResourceRefType)this.get_store().insert_element_user(RESOURCEREF$44, i);
         return target;
      }
   }

   public ResourceRefType addNewResourceRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceRefType target = null;
         target = (ResourceRefType)this.get_store().add_element_user(RESOURCEREF$44);
         return target;
      }
   }

   public void removeResourceRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RESOURCEREF$44, i);
      }
   }

   public ResourceEnvRefType[] getResourceEnvRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(RESOURCEENVREF$46, targetList);
         ResourceEnvRefType[] result = new ResourceEnvRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ResourceEnvRefType getResourceEnvRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceEnvRefType target = null;
         target = (ResourceEnvRefType)this.get_store().find_element_user(RESOURCEENVREF$46, i);
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
         return this.get_store().count_elements(RESOURCEENVREF$46);
      }
   }

   public void setResourceEnvRefArray(ResourceEnvRefType[] resourceEnvRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(resourceEnvRefArray, RESOURCEENVREF$46);
   }

   public void setResourceEnvRefArray(int i, ResourceEnvRefType resourceEnvRef) {
      this.generatedSetterHelperImpl(resourceEnvRef, RESOURCEENVREF$46, i, (short)2);
   }

   public ResourceEnvRefType insertNewResourceEnvRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceEnvRefType target = null;
         target = (ResourceEnvRefType)this.get_store().insert_element_user(RESOURCEENVREF$46, i);
         return target;
      }
   }

   public ResourceEnvRefType addNewResourceEnvRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceEnvRefType target = null;
         target = (ResourceEnvRefType)this.get_store().add_element_user(RESOURCEENVREF$46);
         return target;
      }
   }

   public void removeResourceEnvRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RESOURCEENVREF$46, i);
      }
   }

   public MessageDestinationRefType[] getMessageDestinationRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(MESSAGEDESTINATIONREF$48, targetList);
         MessageDestinationRefType[] result = new MessageDestinationRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public MessageDestinationRefType getMessageDestinationRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationRefType target = null;
         target = (MessageDestinationRefType)this.get_store().find_element_user(MESSAGEDESTINATIONREF$48, i);
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
         return this.get_store().count_elements(MESSAGEDESTINATIONREF$48);
      }
   }

   public void setMessageDestinationRefArray(MessageDestinationRefType[] messageDestinationRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(messageDestinationRefArray, MESSAGEDESTINATIONREF$48);
   }

   public void setMessageDestinationRefArray(int i, MessageDestinationRefType messageDestinationRef) {
      this.generatedSetterHelperImpl(messageDestinationRef, MESSAGEDESTINATIONREF$48, i, (short)2);
   }

   public MessageDestinationRefType insertNewMessageDestinationRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationRefType target = null;
         target = (MessageDestinationRefType)this.get_store().insert_element_user(MESSAGEDESTINATIONREF$48, i);
         return target;
      }
   }

   public MessageDestinationRefType addNewMessageDestinationRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationRefType target = null;
         target = (MessageDestinationRefType)this.get_store().add_element_user(MESSAGEDESTINATIONREF$48);
         return target;
      }
   }

   public void removeMessageDestinationRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MESSAGEDESTINATIONREF$48, i);
      }
   }

   public MessageDestinationType[] getMessageDestinationArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(MESSAGEDESTINATION$50, targetList);
         MessageDestinationType[] result = new MessageDestinationType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public MessageDestinationType getMessageDestinationArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationType target = null;
         target = (MessageDestinationType)this.get_store().find_element_user(MESSAGEDESTINATION$50, i);
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
         return this.get_store().count_elements(MESSAGEDESTINATION$50);
      }
   }

   public void setMessageDestinationArray(MessageDestinationType[] messageDestinationArray) {
      this.check_orphaned();
      this.arraySetterHelper(messageDestinationArray, MESSAGEDESTINATION$50);
   }

   public void setMessageDestinationArray(int i, MessageDestinationType messageDestination) {
      this.generatedSetterHelperImpl(messageDestination, MESSAGEDESTINATION$50, i, (short)2);
   }

   public MessageDestinationType insertNewMessageDestination(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationType target = null;
         target = (MessageDestinationType)this.get_store().insert_element_user(MESSAGEDESTINATION$50, i);
         return target;
      }
   }

   public MessageDestinationType addNewMessageDestination() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationType target = null;
         target = (MessageDestinationType)this.get_store().add_element_user(MESSAGEDESTINATION$50);
         return target;
      }
   }

   public void removeMessageDestination(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MESSAGEDESTINATION$50, i);
      }
   }

   public LocaleEncodingMappingListType[] getLocaleEncodingMappingListArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(LOCALEENCODINGMAPPINGLIST$52, targetList);
         LocaleEncodingMappingListType[] result = new LocaleEncodingMappingListType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public LocaleEncodingMappingListType getLocaleEncodingMappingListArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LocaleEncodingMappingListType target = null;
         target = (LocaleEncodingMappingListType)this.get_store().find_element_user(LOCALEENCODINGMAPPINGLIST$52, i);
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
         return this.get_store().count_elements(LOCALEENCODINGMAPPINGLIST$52);
      }
   }

   public void setLocaleEncodingMappingListArray(LocaleEncodingMappingListType[] localeEncodingMappingListArray) {
      this.check_orphaned();
      this.arraySetterHelper(localeEncodingMappingListArray, LOCALEENCODINGMAPPINGLIST$52);
   }

   public void setLocaleEncodingMappingListArray(int i, LocaleEncodingMappingListType localeEncodingMappingList) {
      this.generatedSetterHelperImpl(localeEncodingMappingList, LOCALEENCODINGMAPPINGLIST$52, i, (short)2);
   }

   public LocaleEncodingMappingListType insertNewLocaleEncodingMappingList(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LocaleEncodingMappingListType target = null;
         target = (LocaleEncodingMappingListType)this.get_store().insert_element_user(LOCALEENCODINGMAPPINGLIST$52, i);
         return target;
      }
   }

   public LocaleEncodingMappingListType addNewLocaleEncodingMappingList() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LocaleEncodingMappingListType target = null;
         target = (LocaleEncodingMappingListType)this.get_store().add_element_user(LOCALEENCODINGMAPPINGLIST$52);
         return target;
      }
   }

   public void removeLocaleEncodingMappingList(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LOCALEENCODINGMAPPINGLIST$52, i);
      }
   }

   public WebAppVersionType.Enum getVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(VERSION$54);
         return target == null ? null : (WebAppVersionType.Enum)target.getEnumValue();
      }
   }

   public WebAppVersionType xgetVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WebAppVersionType target = null;
         target = (WebAppVersionType)this.get_store().find_attribute_user(VERSION$54);
         return target;
      }
   }

   public void setVersion(WebAppVersionType.Enum version) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(VERSION$54);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(VERSION$54);
         }

         target.setEnumValue(version);
      }
   }

   public void xsetVersion(WebAppVersionType version) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WebAppVersionType target = null;
         target = (WebAppVersionType)this.get_store().find_attribute_user(VERSION$54);
         if (target == null) {
            target = (WebAppVersionType)this.get_store().add_attribute_user(VERSION$54);
         }

         target.set(version);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$56);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$56);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$56) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$56);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$56);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$56);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$56);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$56);
      }
   }
}
