package com.sun.java.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.sun.java.xml.ns.javaee.LocaleEncodingMappingListType;
import com.sun.java.xml.ns.javaee.LocaleEncodingMappingType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class LocaleEncodingMappingListTypeImpl extends XmlComplexContentImpl implements LocaleEncodingMappingListType {
   private static final long serialVersionUID = 1L;
   private static final QName LOCALEENCODINGMAPPING$0 = new QName("http://java.sun.com/xml/ns/javaee", "locale-encoding-mapping");
   private static final QName ID$2 = new QName("", "id");

   public LocaleEncodingMappingListTypeImpl(SchemaType sType) {
      super(sType);
   }

   public LocaleEncodingMappingType[] getLocaleEncodingMappingArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(LOCALEENCODINGMAPPING$0, targetList);
         LocaleEncodingMappingType[] result = new LocaleEncodingMappingType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public LocaleEncodingMappingType getLocaleEncodingMappingArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LocaleEncodingMappingType target = null;
         target = (LocaleEncodingMappingType)this.get_store().find_element_user(LOCALEENCODINGMAPPING$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfLocaleEncodingMappingArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LOCALEENCODINGMAPPING$0);
      }
   }

   public void setLocaleEncodingMappingArray(LocaleEncodingMappingType[] localeEncodingMappingArray) {
      this.check_orphaned();
      this.arraySetterHelper(localeEncodingMappingArray, LOCALEENCODINGMAPPING$0);
   }

   public void setLocaleEncodingMappingArray(int i, LocaleEncodingMappingType localeEncodingMapping) {
      this.generatedSetterHelperImpl(localeEncodingMapping, LOCALEENCODINGMAPPING$0, i, (short)2);
   }

   public LocaleEncodingMappingType insertNewLocaleEncodingMapping(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LocaleEncodingMappingType target = null;
         target = (LocaleEncodingMappingType)this.get_store().insert_element_user(LOCALEENCODINGMAPPING$0, i);
         return target;
      }
   }

   public LocaleEncodingMappingType addNewLocaleEncodingMapping() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LocaleEncodingMappingType target = null;
         target = (LocaleEncodingMappingType)this.get_store().add_element_user(LOCALEENCODINGMAPPING$0);
         return target;
      }
   }

   public void removeLocaleEncodingMapping(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LOCALEENCODINGMAPPING$0, i);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$2);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$2);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$2) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$2);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$2);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$2);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$2);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$2);
      }
   }
}
