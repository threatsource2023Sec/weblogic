package com.oracle.xmlns.weblogic.weblogicWebApp.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.oracle.xmlns.weblogic.weblogicWebApp.LocalPathType;
import com.oracle.xmlns.weblogic.weblogicWebApp.VirtualDirectoryMappingType;
import com.sun.java.xml.ns.javaee.UrlPatternType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class VirtualDirectoryMappingTypeImpl extends XmlComplexContentImpl implements VirtualDirectoryMappingType {
   private static final long serialVersionUID = 1L;
   private static final QName LOCALPATH$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "local-path");
   private static final QName URLPATTERN$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "url-pattern");
   private static final QName ID$4 = new QName("", "id");

   public VirtualDirectoryMappingTypeImpl(SchemaType sType) {
      super(sType);
   }

   public LocalPathType getLocalPath() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LocalPathType target = null;
         target = (LocalPathType)this.get_store().find_element_user(LOCALPATH$0, 0);
         return target == null ? null : target;
      }
   }

   public void setLocalPath(LocalPathType localPath) {
      this.generatedSetterHelperImpl(localPath, LOCALPATH$0, 0, (short)1);
   }

   public LocalPathType addNewLocalPath() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LocalPathType target = null;
         target = (LocalPathType)this.get_store().add_element_user(LOCALPATH$0);
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

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$4);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$4);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$4) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$4);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$4);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$4);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$4);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$4);
      }
   }
}
