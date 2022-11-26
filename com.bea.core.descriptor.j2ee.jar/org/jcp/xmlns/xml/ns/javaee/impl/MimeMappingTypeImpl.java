package org.jcp.xmlns.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import javax.xml.namespace.QName;
import org.jcp.xmlns.xml.ns.javaee.MimeMappingType;
import org.jcp.xmlns.xml.ns.javaee.MimeTypeType;
import org.jcp.xmlns.xml.ns.javaee.String;

public class MimeMappingTypeImpl extends XmlComplexContentImpl implements MimeMappingType {
   private static final long serialVersionUID = 1L;
   private static final QName EXTENSION$0 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "extension");
   private static final QName MIMETYPE$2 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "mime-type");
   private static final QName ID$4 = new QName("", "id");

   public MimeMappingTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getExtension() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(EXTENSION$0, 0);
         return target == null ? null : target;
      }
   }

   public void setExtension(String extension) {
      this.generatedSetterHelperImpl(extension, EXTENSION$0, 0, (short)1);
   }

   public String addNewExtension() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(EXTENSION$0);
         return target;
      }
   }

   public MimeTypeType getMimeType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MimeTypeType target = null;
         target = (MimeTypeType)this.get_store().find_element_user(MIMETYPE$2, 0);
         return target == null ? null : target;
      }
   }

   public void setMimeType(MimeTypeType mimeType) {
      this.generatedSetterHelperImpl(mimeType, MIMETYPE$2, 0, (short)1);
   }

   public MimeTypeType addNewMimeType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MimeTypeType target = null;
         target = (MimeTypeType)this.get_store().add_element_user(MIMETYPE$2);
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
