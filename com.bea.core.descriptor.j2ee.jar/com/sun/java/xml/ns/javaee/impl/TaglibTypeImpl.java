package com.sun.java.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.sun.java.xml.ns.javaee.PathType;
import com.sun.java.xml.ns.javaee.String;
import com.sun.java.xml.ns.javaee.TaglibType;
import javax.xml.namespace.QName;

public class TaglibTypeImpl extends XmlComplexContentImpl implements TaglibType {
   private static final long serialVersionUID = 1L;
   private static final QName TAGLIBURI$0 = new QName("http://java.sun.com/xml/ns/javaee", "taglib-uri");
   private static final QName TAGLIBLOCATION$2 = new QName("http://java.sun.com/xml/ns/javaee", "taglib-location");
   private static final QName ID$4 = new QName("", "id");

   public TaglibTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getTaglibUri() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(TAGLIBURI$0, 0);
         return target == null ? null : target;
      }
   }

   public void setTaglibUri(String taglibUri) {
      this.generatedSetterHelperImpl(taglibUri, TAGLIBURI$0, 0, (short)1);
   }

   public String addNewTaglibUri() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(TAGLIBURI$0);
         return target;
      }
   }

   public PathType getTaglibLocation() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PathType target = null;
         target = (PathType)this.get_store().find_element_user(TAGLIBLOCATION$2, 0);
         return target == null ? null : target;
      }
   }

   public void setTaglibLocation(PathType taglibLocation) {
      this.generatedSetterHelperImpl(taglibLocation, TAGLIBLOCATION$2, 0, (short)1);
   }

   public PathType addNewTaglibLocation() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PathType target = null;
         target = (PathType)this.get_store().add_element_user(TAGLIBLOCATION$2);
         return target;
      }
   }

   public java.lang.String getId() {
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

   public void setId(java.lang.String id) {
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
