package com.bea.xbean.xb.xsdschema.impl;

import com.bea.xbean.xb.xsdschema.Annotated;
import com.bea.xbean.xb.xsdschema.AnnotationDocument;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import javax.xml.namespace.QName;

public class AnnotatedImpl extends OpenAttrsImpl implements Annotated {
   private static final QName ANNOTATION$0 = new QName("http://www.w3.org/2001/XMLSchema", "annotation");
   private static final QName ID$2 = new QName("", "id");

   public AnnotatedImpl(SchemaType sType) {
      super(sType);
   }

   public AnnotationDocument.Annotation getAnnotation() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AnnotationDocument.Annotation target = null;
         target = (AnnotationDocument.Annotation)this.get_store().find_element_user((QName)ANNOTATION$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetAnnotation() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ANNOTATION$0) != 0;
      }
   }

   public void setAnnotation(AnnotationDocument.Annotation annotation) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AnnotationDocument.Annotation target = null;
         target = (AnnotationDocument.Annotation)this.get_store().find_element_user((QName)ANNOTATION$0, 0);
         if (target == null) {
            target = (AnnotationDocument.Annotation)this.get_store().add_element_user(ANNOTATION$0);
         }

         target.set(annotation);
      }
   }

   public AnnotationDocument.Annotation addNewAnnotation() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AnnotationDocument.Annotation target = null;
         target = (AnnotationDocument.Annotation)this.get_store().add_element_user(ANNOTATION$0);
         return target;
      }
   }

   public void unsetAnnotation() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element((QName)ANNOTATION$0, 0);
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
