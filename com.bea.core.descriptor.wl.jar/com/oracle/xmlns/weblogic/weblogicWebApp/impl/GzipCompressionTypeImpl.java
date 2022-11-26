package com.oracle.xmlns.weblogic.weblogicWebApp.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlLong;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicWebApp.GzipCompressionType;
import com.oracle.xmlns.weblogic.weblogicWebApp.TrueFalseType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class GzipCompressionTypeImpl extends XmlComplexContentImpl implements GzipCompressionType {
   private static final long serialVersionUID = 1L;
   private static final QName ENABLED$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "enabled");
   private static final QName MINCONTENTLENGTH$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "min-content-length");
   private static final QName CONTENTTYPE$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "content-type");

   public GzipCompressionTypeImpl(SchemaType sType) {
      super(sType);
   }

   public TrueFalseType getEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(ENABLED$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ENABLED$0) != 0;
      }
   }

   public void setEnabled(TrueFalseType enabled) {
      this.generatedSetterHelperImpl(enabled, ENABLED$0, 0, (short)1);
   }

   public TrueFalseType addNewEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(ENABLED$0);
         return target;
      }
   }

   public void unsetEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ENABLED$0, 0);
      }
   }

   public long getMinContentLength() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MINCONTENTLENGTH$2, 0);
         return target == null ? 0L : target.getLongValue();
      }
   }

   public XmlLong xgetMinContentLength() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlLong target = null;
         target = (XmlLong)this.get_store().find_element_user(MINCONTENTLENGTH$2, 0);
         return target;
      }
   }

   public boolean isSetMinContentLength() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MINCONTENTLENGTH$2) != 0;
      }
   }

   public void setMinContentLength(long minContentLength) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MINCONTENTLENGTH$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(MINCONTENTLENGTH$2);
         }

         target.setLongValue(minContentLength);
      }
   }

   public void xsetMinContentLength(XmlLong minContentLength) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlLong target = null;
         target = (XmlLong)this.get_store().find_element_user(MINCONTENTLENGTH$2, 0);
         if (target == null) {
            target = (XmlLong)this.get_store().add_element_user(MINCONTENTLENGTH$2);
         }

         target.set(minContentLength);
      }
   }

   public void unsetMinContentLength() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MINCONTENTLENGTH$2, 0);
      }
   }

   public String[] getContentTypeArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(CONTENTTYPE$4, targetList);
         String[] result = new String[targetList.size()];
         int i = 0;

         for(int len = targetList.size(); i < len; ++i) {
            result[i] = ((SimpleValue)targetList.get(i)).getStringValue();
         }

         return result;
      }
   }

   public String getContentTypeArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONTENTTYPE$4, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target.getStringValue();
         }
      }
   }

   public XmlString[] xgetContentTypeArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(CONTENTTYPE$4, targetList);
         XmlString[] result = new XmlString[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public XmlString xgetContentTypeArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONTENTTYPE$4, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfContentTypeArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONTENTTYPE$4);
      }
   }

   public void setContentTypeArray(String[] contentTypeArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(contentTypeArray, CONTENTTYPE$4);
      }
   }

   public void setContentTypeArray(int i, String contentType) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONTENTTYPE$4, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.setStringValue(contentType);
         }
      }
   }

   public void xsetContentTypeArray(XmlString[] contentTypeArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(contentTypeArray, CONTENTTYPE$4);
      }
   }

   public void xsetContentTypeArray(int i, XmlString contentType) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONTENTTYPE$4, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.set(contentType);
         }
      }
   }

   public void insertContentType(int i, String contentType) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = (SimpleValue)this.get_store().insert_element_user(CONTENTTYPE$4, i);
         target.setStringValue(contentType);
      }
   }

   public void addContentType(String contentType) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().add_element_user(CONTENTTYPE$4);
         target.setStringValue(contentType);
      }
   }

   public XmlString insertNewContentType(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().insert_element_user(CONTENTTYPE$4, i);
         return target;
      }
   }

   public XmlString addNewContentType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().add_element_user(CONTENTTYPE$4);
         return target;
      }
   }

   public void removeContentType(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONTENTTYPE$4, i);
      }
   }
}
