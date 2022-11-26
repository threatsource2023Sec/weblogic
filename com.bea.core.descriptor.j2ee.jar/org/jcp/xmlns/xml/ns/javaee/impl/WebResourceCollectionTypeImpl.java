package org.jcp.xmlns.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import org.jcp.xmlns.xml.ns.javaee.DescriptionType;
import org.jcp.xmlns.xml.ns.javaee.HttpMethodType;
import org.jcp.xmlns.xml.ns.javaee.String;
import org.jcp.xmlns.xml.ns.javaee.UrlPatternType;
import org.jcp.xmlns.xml.ns.javaee.WebResourceCollectionType;

public class WebResourceCollectionTypeImpl extends XmlComplexContentImpl implements WebResourceCollectionType {
   private static final long serialVersionUID = 1L;
   private static final QName WEBRESOURCENAME$0 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "web-resource-name");
   private static final QName DESCRIPTION$2 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "description");
   private static final QName URLPATTERN$4 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "url-pattern");
   private static final QName HTTPMETHOD$6 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "http-method");
   private static final QName HTTPMETHODOMISSION$8 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "http-method-omission");
   private static final QName ID$10 = new QName("", "id");

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

   public java.lang.String[] getHttpMethodArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(HTTPMETHOD$6, targetList);
         java.lang.String[] result = new java.lang.String[targetList.size()];
         int i = 0;

         for(int len = targetList.size(); i < len; ++i) {
            result[i] = ((SimpleValue)targetList.get(i)).getStringValue();
         }

         return result;
      }
   }

   public java.lang.String getHttpMethodArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(HTTPMETHOD$6, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target.getStringValue();
         }
      }
   }

   public HttpMethodType[] xgetHttpMethodArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(HTTPMETHOD$6, targetList);
         HttpMethodType[] result = new HttpMethodType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public HttpMethodType xgetHttpMethodArray(int i) {
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

   public void setHttpMethodArray(java.lang.String[] httpMethodArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(httpMethodArray, HTTPMETHOD$6);
      }
   }

   public void setHttpMethodArray(int i, java.lang.String httpMethod) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(HTTPMETHOD$6, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.setStringValue(httpMethod);
         }
      }
   }

   public void xsetHttpMethodArray(HttpMethodType[] httpMethodArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(httpMethodArray, HTTPMETHOD$6);
      }
   }

   public void xsetHttpMethodArray(int i, HttpMethodType httpMethod) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         HttpMethodType target = null;
         target = (HttpMethodType)this.get_store().find_element_user(HTTPMETHOD$6, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.set(httpMethod);
         }
      }
   }

   public void insertHttpMethod(int i, java.lang.String httpMethod) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = (SimpleValue)this.get_store().insert_element_user(HTTPMETHOD$6, i);
         target.setStringValue(httpMethod);
      }
   }

   public void addHttpMethod(java.lang.String httpMethod) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().add_element_user(HTTPMETHOD$6);
         target.setStringValue(httpMethod);
      }
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

   public java.lang.String[] getHttpMethodOmissionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(HTTPMETHODOMISSION$8, targetList);
         java.lang.String[] result = new java.lang.String[targetList.size()];
         int i = 0;

         for(int len = targetList.size(); i < len; ++i) {
            result[i] = ((SimpleValue)targetList.get(i)).getStringValue();
         }

         return result;
      }
   }

   public java.lang.String getHttpMethodOmissionArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(HTTPMETHODOMISSION$8, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target.getStringValue();
         }
      }
   }

   public HttpMethodType[] xgetHttpMethodOmissionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(HTTPMETHODOMISSION$8, targetList);
         HttpMethodType[] result = new HttpMethodType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public HttpMethodType xgetHttpMethodOmissionArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         HttpMethodType target = null;
         target = (HttpMethodType)this.get_store().find_element_user(HTTPMETHODOMISSION$8, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfHttpMethodOmissionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(HTTPMETHODOMISSION$8);
      }
   }

   public void setHttpMethodOmissionArray(java.lang.String[] httpMethodOmissionArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(httpMethodOmissionArray, HTTPMETHODOMISSION$8);
      }
   }

   public void setHttpMethodOmissionArray(int i, java.lang.String httpMethodOmission) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(HTTPMETHODOMISSION$8, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.setStringValue(httpMethodOmission);
         }
      }
   }

   public void xsetHttpMethodOmissionArray(HttpMethodType[] httpMethodOmissionArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(httpMethodOmissionArray, HTTPMETHODOMISSION$8);
      }
   }

   public void xsetHttpMethodOmissionArray(int i, HttpMethodType httpMethodOmission) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         HttpMethodType target = null;
         target = (HttpMethodType)this.get_store().find_element_user(HTTPMETHODOMISSION$8, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.set(httpMethodOmission);
         }
      }
   }

   public void insertHttpMethodOmission(int i, java.lang.String httpMethodOmission) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = (SimpleValue)this.get_store().insert_element_user(HTTPMETHODOMISSION$8, i);
         target.setStringValue(httpMethodOmission);
      }
   }

   public void addHttpMethodOmission(java.lang.String httpMethodOmission) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().add_element_user(HTTPMETHODOMISSION$8);
         target.setStringValue(httpMethodOmission);
      }
   }

   public HttpMethodType insertNewHttpMethodOmission(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         HttpMethodType target = null;
         target = (HttpMethodType)this.get_store().insert_element_user(HTTPMETHODOMISSION$8, i);
         return target;
      }
   }

   public HttpMethodType addNewHttpMethodOmission() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         HttpMethodType target = null;
         target = (HttpMethodType)this.get_store().add_element_user(HTTPMETHODOMISSION$8);
         return target;
      }
   }

   public void removeHttpMethodOmission(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(HTTPMETHODOMISSION$8, i);
      }
   }

   public java.lang.String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$10);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$10);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$10) != null;
      }
   }

   public void setId(java.lang.String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$10);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$10);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$10);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$10);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$10);
      }
   }
}
