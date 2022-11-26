package com.sun.java.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.sun.java.xml.ns.javaee.DispatcherType;
import com.sun.java.xml.ns.javaee.FilterMappingType;
import com.sun.java.xml.ns.javaee.FilterNameType;
import com.sun.java.xml.ns.javaee.ServletNameType;
import com.sun.java.xml.ns.javaee.UrlPatternType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class FilterMappingTypeImpl extends XmlComplexContentImpl implements FilterMappingType {
   private static final long serialVersionUID = 1L;
   private static final QName FILTERNAME$0 = new QName("http://java.sun.com/xml/ns/javaee", "filter-name");
   private static final QName URLPATTERN$2 = new QName("http://java.sun.com/xml/ns/javaee", "url-pattern");
   private static final QName SERVLETNAME$4 = new QName("http://java.sun.com/xml/ns/javaee", "servlet-name");
   private static final QName DISPATCHER$6 = new QName("http://java.sun.com/xml/ns/javaee", "dispatcher");
   private static final QName ID$8 = new QName("", "id");

   public FilterMappingTypeImpl(SchemaType sType) {
      super(sType);
   }

   public FilterNameType getFilterName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FilterNameType target = null;
         target = (FilterNameType)this.get_store().find_element_user(FILTERNAME$0, 0);
         return target == null ? null : target;
      }
   }

   public void setFilterName(FilterNameType filterName) {
      this.generatedSetterHelperImpl(filterName, FILTERNAME$0, 0, (short)1);
   }

   public FilterNameType addNewFilterName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FilterNameType target = null;
         target = (FilterNameType)this.get_store().add_element_user(FILTERNAME$0);
         return target;
      }
   }

   public UrlPatternType[] getUrlPatternArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(URLPATTERN$2, targetList);
         UrlPatternType[] result = new UrlPatternType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public UrlPatternType getUrlPatternArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         UrlPatternType target = null;
         target = (UrlPatternType)this.get_store().find_element_user(URLPATTERN$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfUrlPatternArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(URLPATTERN$2);
      }
   }

   public void setUrlPatternArray(UrlPatternType[] urlPatternArray) {
      this.check_orphaned();
      this.arraySetterHelper(urlPatternArray, URLPATTERN$2);
   }

   public void setUrlPatternArray(int i, UrlPatternType urlPattern) {
      this.generatedSetterHelperImpl(urlPattern, URLPATTERN$2, i, (short)2);
   }

   public UrlPatternType insertNewUrlPattern(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         UrlPatternType target = null;
         target = (UrlPatternType)this.get_store().insert_element_user(URLPATTERN$2, i);
         return target;
      }
   }

   public UrlPatternType addNewUrlPattern() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         UrlPatternType target = null;
         target = (UrlPatternType)this.get_store().add_element_user(URLPATTERN$2);
         return target;
      }
   }

   public void removeUrlPattern(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(URLPATTERN$2, i);
      }
   }

   public ServletNameType[] getServletNameArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(SERVLETNAME$4, targetList);
         ServletNameType[] result = new ServletNameType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ServletNameType getServletNameArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServletNameType target = null;
         target = (ServletNameType)this.get_store().find_element_user(SERVLETNAME$4, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfServletNameArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SERVLETNAME$4);
      }
   }

   public void setServletNameArray(ServletNameType[] servletNameArray) {
      this.check_orphaned();
      this.arraySetterHelper(servletNameArray, SERVLETNAME$4);
   }

   public void setServletNameArray(int i, ServletNameType servletName) {
      this.generatedSetterHelperImpl(servletName, SERVLETNAME$4, i, (short)2);
   }

   public ServletNameType insertNewServletName(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServletNameType target = null;
         target = (ServletNameType)this.get_store().insert_element_user(SERVLETNAME$4, i);
         return target;
      }
   }

   public ServletNameType addNewServletName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServletNameType target = null;
         target = (ServletNameType)this.get_store().add_element_user(SERVLETNAME$4);
         return target;
      }
   }

   public void removeServletName(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SERVLETNAME$4, i);
      }
   }

   public DispatcherType[] getDispatcherArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(DISPATCHER$6, targetList);
         DispatcherType[] result = new DispatcherType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public DispatcherType getDispatcherArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DispatcherType target = null;
         target = (DispatcherType)this.get_store().find_element_user(DISPATCHER$6, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfDispatcherArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DISPATCHER$6);
      }
   }

   public void setDispatcherArray(DispatcherType[] dispatcherArray) {
      this.check_orphaned();
      this.arraySetterHelper(dispatcherArray, DISPATCHER$6);
   }

   public void setDispatcherArray(int i, DispatcherType dispatcher) {
      this.generatedSetterHelperImpl(dispatcher, DISPATCHER$6, i, (short)2);
   }

   public DispatcherType insertNewDispatcher(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DispatcherType target = null;
         target = (DispatcherType)this.get_store().insert_element_user(DISPATCHER$6, i);
         return target;
      }
   }

   public DispatcherType addNewDispatcher() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DispatcherType target = null;
         target = (DispatcherType)this.get_store().add_element_user(DISPATCHER$6);
         return target;
      }
   }

   public void removeDispatcher(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DISPATCHER$6, i);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$8);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$8);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$8) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$8);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$8);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$8);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$8);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$8);
      }
   }
}
