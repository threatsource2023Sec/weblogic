package com.sun.java.xml.ns.j2Ee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.sun.java.xml.ns.j2Ee.DescriptionType;
import com.sun.java.xml.ns.j2Ee.HttpMethodType;
import com.sun.java.xml.ns.j2Ee.String;
import com.sun.java.xml.ns.j2Ee.UrlPatternType;
import com.sun.java.xml.ns.j2Ee.WebResourceCollectionType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class WebResourceCollectionTypeImpl extends XmlComplexContentImpl implements WebResourceCollectionType {
   private static final long serialVersionUID = 1L;
   private static final QName WEBRESOURCENAME$0 = new QName("http://java.sun.com/xml/ns/j2ee", "web-resource-name");
   private static final QName DESCRIPTION$2 = new QName("http://java.sun.com/xml/ns/j2ee", "description");
   private static final QName URLPATTERN$4 = new QName("http://java.sun.com/xml/ns/j2ee", "url-pattern");
   private static final QName HTTPMETHOD$6 = new QName("http://java.sun.com/xml/ns/j2ee", "http-method");
   private static final QName ID$8 = new QName("", "id");

   public WebResourceCollectionTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getWebResourceName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(WEBRESOURCENAME$0, 0);
         return target == null ? null : target;
      }
   }

   public void setWebResourceName(String webResourceName) {
      this.generatedSetterHelperImpl(webResourceName, WEBRESOURCENAME$0, 0, (short)1);
   }

   public String addNewWebResourceName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(WEBRESOURCENAME$0);
         return target;
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

   public UrlPatternType[] getUrlPatternArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(URLPATTERN$4, targetList);
         UrlPatternType[] result = new UrlPatternType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public UrlPatternType getUrlPatternArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         UrlPatternType target = null;
         target = (UrlPatternType)this.get_store().find_element_user(URLPATTERN$4, i);
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
         return this.get_store().count_elements(URLPATTERN$4);
      }
   }

   public void setUrlPatternArray(UrlPatternType[] urlPatternArray) {
      this.check_orphaned();
      this.arraySetterHelper(urlPatternArray, URLPATTERN$4);
   }

   public void setUrlPatternArray(int i, UrlPatternType urlPattern) {
      this.generatedSetterHelperImpl(urlPattern, URLPATTERN$4, i, (short)2);
   }

   public UrlPatternType insertNewUrlPattern(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         UrlPatternType target = null;
         target = (UrlPatternType)this.get_store().insert_element_user(URLPATTERN$4, i);
         return target;
      }
   }

   public UrlPatternType addNewUrlPattern() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         UrlPatternType target = null;
         target = (UrlPatternType)this.get_store().add_element_user(URLPATTERN$4);
         return target;
      }
   }

   public void removeUrlPattern(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(URLPATTERN$4, i);
      }
   }

   public HttpMethodType[] getHttpMethodArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(HTTPMETHOD$6, targetList);
         HttpMethodType[] result = new HttpMethodType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public HttpMethodType getHttpMethodArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         HttpMethodType target = null;
         target = (HttpMethodType)this.get_store().find_element_user(HTTPMETHOD$6, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfHttpMethodArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(HTTPMETHOD$6);
      }
   }

   public void setHttpMethodArray(HttpMethodType[] httpMethodArray) {
      this.check_orphaned();
      this.arraySetterHelper(httpMethodArray, HTTPMETHOD$6);
   }

   public void setHttpMethodArray(int i, HttpMethodType httpMethod) {
      this.generatedSetterHelperImpl(httpMethod, HTTPMETHOD$6, i, (short)2);
   }

   public HttpMethodType insertNewHttpMethod(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         HttpMethodType target = null;
         target = (HttpMethodType)this.get_store().insert_element_user(HTTPMETHOD$6, i);
         return target;
      }
   }

   public HttpMethodType addNewHttpMethod() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         HttpMethodType target = null;
         target = (HttpMethodType)this.get_store().add_element_user(HTTPMETHOD$6);
         return target;
      }
   }

   public void removeHttpMethod(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(HTTPMETHOD$6, i);
      }
   }

   public java.lang.String getId() {
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

   public void setId(java.lang.String id) {
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
