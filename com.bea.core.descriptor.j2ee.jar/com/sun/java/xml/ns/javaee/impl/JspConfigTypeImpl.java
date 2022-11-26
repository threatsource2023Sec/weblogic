package com.sun.java.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.sun.java.xml.ns.javaee.JspConfigType;
import com.sun.java.xml.ns.javaee.JspPropertyGroupType;
import com.sun.java.xml.ns.javaee.TaglibType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class JspConfigTypeImpl extends XmlComplexContentImpl implements JspConfigType {
   private static final long serialVersionUID = 1L;
   private static final QName TAGLIB$0 = new QName("http://java.sun.com/xml/ns/javaee", "taglib");
   private static final QName JSPPROPERTYGROUP$2 = new QName("http://java.sun.com/xml/ns/javaee", "jsp-property-group");
   private static final QName ID$4 = new QName("", "id");

   public JspConfigTypeImpl(SchemaType sType) {
      super(sType);
   }

   public TaglibType[] getTaglibArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(TAGLIB$0, targetList);
         TaglibType[] result = new TaglibType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public TaglibType getTaglibArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TaglibType target = null;
         target = (TaglibType)this.get_store().find_element_user(TAGLIB$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfTaglibArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TAGLIB$0);
      }
   }

   public void setTaglibArray(TaglibType[] taglibArray) {
      this.check_orphaned();
      this.arraySetterHelper(taglibArray, TAGLIB$0);
   }

   public void setTaglibArray(int i, TaglibType taglib) {
      this.generatedSetterHelperImpl(taglib, TAGLIB$0, i, (short)2);
   }

   public TaglibType insertNewTaglib(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TaglibType target = null;
         target = (TaglibType)this.get_store().insert_element_user(TAGLIB$0, i);
         return target;
      }
   }

   public TaglibType addNewTaglib() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TaglibType target = null;
         target = (TaglibType)this.get_store().add_element_user(TAGLIB$0);
         return target;
      }
   }

   public void removeTaglib(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TAGLIB$0, i);
      }
   }

   public JspPropertyGroupType[] getJspPropertyGroupArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(JSPPROPERTYGROUP$2, targetList);
         JspPropertyGroupType[] result = new JspPropertyGroupType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public JspPropertyGroupType getJspPropertyGroupArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JspPropertyGroupType target = null;
         target = (JspPropertyGroupType)this.get_store().find_element_user(JSPPROPERTYGROUP$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfJspPropertyGroupArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(JSPPROPERTYGROUP$2);
      }
   }

   public void setJspPropertyGroupArray(JspPropertyGroupType[] jspPropertyGroupArray) {
      this.check_orphaned();
      this.arraySetterHelper(jspPropertyGroupArray, JSPPROPERTYGROUP$2);
   }

   public void setJspPropertyGroupArray(int i, JspPropertyGroupType jspPropertyGroup) {
      this.generatedSetterHelperImpl(jspPropertyGroup, JSPPROPERTYGROUP$2, i, (short)2);
   }

   public JspPropertyGroupType insertNewJspPropertyGroup(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JspPropertyGroupType target = null;
         target = (JspPropertyGroupType)this.get_store().insert_element_user(JSPPROPERTYGROUP$2, i);
         return target;
      }
   }

   public JspPropertyGroupType addNewJspPropertyGroup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JspPropertyGroupType target = null;
         target = (JspPropertyGroupType)this.get_store().add_element_user(JSPPROPERTYGROUP$2);
         return target;
      }
   }

   public void removeJspPropertyGroup(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(JSPPROPERTYGROUP$2, i);
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
