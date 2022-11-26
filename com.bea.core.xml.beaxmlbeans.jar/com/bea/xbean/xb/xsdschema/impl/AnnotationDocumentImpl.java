package com.bea.xbean.xb.xsdschema.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xbean.xb.xsdschema.AnnotationDocument;
import com.bea.xbean.xb.xsdschema.AppinfoDocument;
import com.bea.xbean.xb.xsdschema.DocumentationDocument;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class AnnotationDocumentImpl extends XmlComplexContentImpl implements AnnotationDocument {
   private static final QName ANNOTATION$0 = new QName("http://www.w3.org/2001/XMLSchema", "annotation");

   public AnnotationDocumentImpl(SchemaType sType) {
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

   public static class AnnotationImpl extends OpenAttrsImpl implements AnnotationDocument.Annotation {
      private static final QName APPINFO$0 = new QName("http://www.w3.org/2001/XMLSchema", "appinfo");
      private static final QName DOCUMENTATION$2 = new QName("http://www.w3.org/2001/XMLSchema", "documentation");
      private static final QName ID$4 = new QName("", "id");

      public AnnotationImpl(SchemaType sType) {
         super(sType);
      }

      public AppinfoDocument.Appinfo[] getAppinfoArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            List targetList = new ArrayList();
            this.get_store().find_all_element_users((QName)APPINFO$0, targetList);
            AppinfoDocument.Appinfo[] result = new AppinfoDocument.Appinfo[targetList.size()];
            targetList.toArray(result);
            return result;
         }
      }

      public AppinfoDocument.Appinfo getAppinfoArray(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            AppinfoDocument.Appinfo target = null;
            target = (AppinfoDocument.Appinfo)this.get_store().find_element_user(APPINFO$0, i);
            if (target == null) {
               throw new IndexOutOfBoundsException();
            } else {
               return target;
            }
         }
      }

      public int sizeOfAppinfoArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(APPINFO$0);
         }
      }

      public void setAppinfoArray(AppinfoDocument.Appinfo[] appinfoArray) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.arraySetterHelper(appinfoArray, APPINFO$0);
         }
      }

      public void setAppinfoArray(int i, AppinfoDocument.Appinfo appinfo) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            AppinfoDocument.Appinfo target = null;
            target = (AppinfoDocument.Appinfo)this.get_store().find_element_user(APPINFO$0, i);
            if (target == null) {
               throw new IndexOutOfBoundsException();
            } else {
               target.set(appinfo);
            }
         }
      }

      public AppinfoDocument.Appinfo insertNewAppinfo(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            AppinfoDocument.Appinfo target = null;
            target = (AppinfoDocument.Appinfo)this.get_store().insert_element_user(APPINFO$0, i);
            return target;
         }
      }

      public AppinfoDocument.Appinfo addNewAppinfo() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            AppinfoDocument.Appinfo target = null;
            target = (AppinfoDocument.Appinfo)this.get_store().add_element_user(APPINFO$0);
            return target;
         }
      }

      public void removeAppinfo(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(APPINFO$0, i);
         }
      }

      public DocumentationDocument.Documentation[] getDocumentationArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            List targetList = new ArrayList();
            this.get_store().find_all_element_users((QName)DOCUMENTATION$2, targetList);
            DocumentationDocument.Documentation[] result = new DocumentationDocument.Documentation[targetList.size()];
            targetList.toArray(result);
            return result;
         }
      }

      public DocumentationDocument.Documentation getDocumentationArray(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            DocumentationDocument.Documentation target = null;
            target = (DocumentationDocument.Documentation)this.get_store().find_element_user(DOCUMENTATION$2, i);
            if (target == null) {
               throw new IndexOutOfBoundsException();
            } else {
               return target;
            }
         }
      }

      public int sizeOfDocumentationArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(DOCUMENTATION$2);
         }
      }

      public void setDocumentationArray(DocumentationDocument.Documentation[] documentationArray) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.arraySetterHelper(documentationArray, DOCUMENTATION$2);
         }
      }

      public void setDocumentationArray(int i, DocumentationDocument.Documentation documentation) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            DocumentationDocument.Documentation target = null;
            target = (DocumentationDocument.Documentation)this.get_store().find_element_user(DOCUMENTATION$2, i);
            if (target == null) {
               throw new IndexOutOfBoundsException();
            } else {
               target.set(documentation);
            }
         }
      }

      public DocumentationDocument.Documentation insertNewDocumentation(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            DocumentationDocument.Documentation target = null;
            target = (DocumentationDocument.Documentation)this.get_store().insert_element_user(DOCUMENTATION$2, i);
            return target;
         }
      }

      public DocumentationDocument.Documentation addNewDocumentation() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            DocumentationDocument.Documentation target = null;
            target = (DocumentationDocument.Documentation)this.get_store().add_element_user(DOCUMENTATION$2);
            return target;
         }
      }

      public void removeDocumentation(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(DOCUMENTATION$2, i);
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
}
