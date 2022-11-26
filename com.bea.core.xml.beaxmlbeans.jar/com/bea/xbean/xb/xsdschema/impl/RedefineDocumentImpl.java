package com.bea.xbean.xb.xsdschema.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xbean.xb.xsdschema.AnnotationDocument;
import com.bea.xbean.xb.xsdschema.NamedAttributeGroup;
import com.bea.xbean.xb.xsdschema.NamedGroup;
import com.bea.xbean.xb.xsdschema.RedefineDocument;
import com.bea.xbean.xb.xsdschema.TopLevelComplexType;
import com.bea.xbean.xb.xsdschema.TopLevelSimpleType;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlAnyURI;
import com.bea.xml.XmlID;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class RedefineDocumentImpl extends XmlComplexContentImpl implements RedefineDocument {
   private static final QName REDEFINE$0 = new QName("http://www.w3.org/2001/XMLSchema", "redefine");

   public RedefineDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public RedefineDocument.Redefine getRedefine() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         RedefineDocument.Redefine target = null;
         target = (RedefineDocument.Redefine)this.get_store().find_element_user((QName)REDEFINE$0, 0);
         return target == null ? null : target;
      }
   }

   public void setRedefine(RedefineDocument.Redefine redefine) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         RedefineDocument.Redefine target = null;
         target = (RedefineDocument.Redefine)this.get_store().find_element_user((QName)REDEFINE$0, 0);
         if (target == null) {
            target = (RedefineDocument.Redefine)this.get_store().add_element_user(REDEFINE$0);
         }

         target.set(redefine);
      }
   }

   public RedefineDocument.Redefine addNewRedefine() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         RedefineDocument.Redefine target = null;
         target = (RedefineDocument.Redefine)this.get_store().add_element_user(REDEFINE$0);
         return target;
      }
   }

   public static class RedefineImpl extends OpenAttrsImpl implements RedefineDocument.Redefine {
      private static final QName ANNOTATION$0 = new QName("http://www.w3.org/2001/XMLSchema", "annotation");
      private static final QName SIMPLETYPE$2 = new QName("http://www.w3.org/2001/XMLSchema", "simpleType");
      private static final QName COMPLEXTYPE$4 = new QName("http://www.w3.org/2001/XMLSchema", "complexType");
      private static final QName GROUP$6 = new QName("http://www.w3.org/2001/XMLSchema", "group");
      private static final QName ATTRIBUTEGROUP$8 = new QName("http://www.w3.org/2001/XMLSchema", "attributeGroup");
      private static final QName SCHEMALOCATION$10 = new QName("", "schemaLocation");
      private static final QName ID$12 = new QName("", "id");

      public RedefineImpl(SchemaType sType) {
         super(sType);
      }

      public AnnotationDocument.Annotation[] getAnnotationArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            List targetList = new ArrayList();
            this.get_store().find_all_element_users((QName)ANNOTATION$0, targetList);
            AnnotationDocument.Annotation[] result = new AnnotationDocument.Annotation[targetList.size()];
            targetList.toArray(result);
            return result;
         }
      }

      public AnnotationDocument.Annotation getAnnotationArray(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            AnnotationDocument.Annotation target = null;
            target = (AnnotationDocument.Annotation)this.get_store().find_element_user(ANNOTATION$0, i);
            if (target == null) {
               throw new IndexOutOfBoundsException();
            } else {
               return target;
            }
         }
      }

      public int sizeOfAnnotationArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(ANNOTATION$0);
         }
      }

      public void setAnnotationArray(AnnotationDocument.Annotation[] annotationArray) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.arraySetterHelper(annotationArray, ANNOTATION$0);
         }
      }

      public void setAnnotationArray(int i, AnnotationDocument.Annotation annotation) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            AnnotationDocument.Annotation target = null;
            target = (AnnotationDocument.Annotation)this.get_store().find_element_user(ANNOTATION$0, i);
            if (target == null) {
               throw new IndexOutOfBoundsException();
            } else {
               target.set(annotation);
            }
         }
      }

      public AnnotationDocument.Annotation insertNewAnnotation(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            AnnotationDocument.Annotation target = null;
            target = (AnnotationDocument.Annotation)this.get_store().insert_element_user(ANNOTATION$0, i);
            return target;
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

      public void removeAnnotation(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(ANNOTATION$0, i);
         }
      }

      public TopLevelSimpleType[] getSimpleTypeArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            List targetList = new ArrayList();
            this.get_store().find_all_element_users((QName)SIMPLETYPE$2, targetList);
            TopLevelSimpleType[] result = new TopLevelSimpleType[targetList.size()];
            targetList.toArray(result);
            return result;
         }
      }

      public TopLevelSimpleType getSimpleTypeArray(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            TopLevelSimpleType target = null;
            target = (TopLevelSimpleType)this.get_store().find_element_user(SIMPLETYPE$2, i);
            if (target == null) {
               throw new IndexOutOfBoundsException();
            } else {
               return target;
            }
         }
      }

      public int sizeOfSimpleTypeArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(SIMPLETYPE$2);
         }
      }

      public void setSimpleTypeArray(TopLevelSimpleType[] simpleTypeArray) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.arraySetterHelper(simpleTypeArray, SIMPLETYPE$2);
         }
      }

      public void setSimpleTypeArray(int i, TopLevelSimpleType simpleType) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            TopLevelSimpleType target = null;
            target = (TopLevelSimpleType)this.get_store().find_element_user(SIMPLETYPE$2, i);
            if (target == null) {
               throw new IndexOutOfBoundsException();
            } else {
               target.set(simpleType);
            }
         }
      }

      public TopLevelSimpleType insertNewSimpleType(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            TopLevelSimpleType target = null;
            target = (TopLevelSimpleType)this.get_store().insert_element_user(SIMPLETYPE$2, i);
            return target;
         }
      }

      public TopLevelSimpleType addNewSimpleType() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            TopLevelSimpleType target = null;
            target = (TopLevelSimpleType)this.get_store().add_element_user(SIMPLETYPE$2);
            return target;
         }
      }

      public void removeSimpleType(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(SIMPLETYPE$2, i);
         }
      }

      public TopLevelComplexType[] getComplexTypeArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            List targetList = new ArrayList();
            this.get_store().find_all_element_users((QName)COMPLEXTYPE$4, targetList);
            TopLevelComplexType[] result = new TopLevelComplexType[targetList.size()];
            targetList.toArray(result);
            return result;
         }
      }

      public TopLevelComplexType getComplexTypeArray(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            TopLevelComplexType target = null;
            target = (TopLevelComplexType)this.get_store().find_element_user(COMPLEXTYPE$4, i);
            if (target == null) {
               throw new IndexOutOfBoundsException();
            } else {
               return target;
            }
         }
      }

      public int sizeOfComplexTypeArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(COMPLEXTYPE$4);
         }
      }

      public void setComplexTypeArray(TopLevelComplexType[] complexTypeArray) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.arraySetterHelper(complexTypeArray, COMPLEXTYPE$4);
         }
      }

      public void setComplexTypeArray(int i, TopLevelComplexType complexType) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            TopLevelComplexType target = null;
            target = (TopLevelComplexType)this.get_store().find_element_user(COMPLEXTYPE$4, i);
            if (target == null) {
               throw new IndexOutOfBoundsException();
            } else {
               target.set(complexType);
            }
         }
      }

      public TopLevelComplexType insertNewComplexType(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            TopLevelComplexType target = null;
            target = (TopLevelComplexType)this.get_store().insert_element_user(COMPLEXTYPE$4, i);
            return target;
         }
      }

      public TopLevelComplexType addNewComplexType() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            TopLevelComplexType target = null;
            target = (TopLevelComplexType)this.get_store().add_element_user(COMPLEXTYPE$4);
            return target;
         }
      }

      public void removeComplexType(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(COMPLEXTYPE$4, i);
         }
      }

      public NamedGroup[] getGroupArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            List targetList = new ArrayList();
            this.get_store().find_all_element_users((QName)GROUP$6, targetList);
            NamedGroup[] result = new NamedGroup[targetList.size()];
            targetList.toArray(result);
            return result;
         }
      }

      public NamedGroup getGroupArray(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            NamedGroup target = null;
            target = (NamedGroup)this.get_store().find_element_user(GROUP$6, i);
            if (target == null) {
               throw new IndexOutOfBoundsException();
            } else {
               return target;
            }
         }
      }

      public int sizeOfGroupArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(GROUP$6);
         }
      }

      public void setGroupArray(NamedGroup[] groupArray) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.arraySetterHelper(groupArray, GROUP$6);
         }
      }

      public void setGroupArray(int i, NamedGroup group) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            NamedGroup target = null;
            target = (NamedGroup)this.get_store().find_element_user(GROUP$6, i);
            if (target == null) {
               throw new IndexOutOfBoundsException();
            } else {
               target.set(group);
            }
         }
      }

      public NamedGroup insertNewGroup(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            NamedGroup target = null;
            target = (NamedGroup)this.get_store().insert_element_user(GROUP$6, i);
            return target;
         }
      }

      public NamedGroup addNewGroup() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            NamedGroup target = null;
            target = (NamedGroup)this.get_store().add_element_user(GROUP$6);
            return target;
         }
      }

      public void removeGroup(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(GROUP$6, i);
         }
      }

      public NamedAttributeGroup[] getAttributeGroupArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            List targetList = new ArrayList();
            this.get_store().find_all_element_users((QName)ATTRIBUTEGROUP$8, targetList);
            NamedAttributeGroup[] result = new NamedAttributeGroup[targetList.size()];
            targetList.toArray(result);
            return result;
         }
      }

      public NamedAttributeGroup getAttributeGroupArray(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            NamedAttributeGroup target = null;
            target = (NamedAttributeGroup)this.get_store().find_element_user(ATTRIBUTEGROUP$8, i);
            if (target == null) {
               throw new IndexOutOfBoundsException();
            } else {
               return target;
            }
         }
      }

      public int sizeOfAttributeGroupArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(ATTRIBUTEGROUP$8);
         }
      }

      public void setAttributeGroupArray(NamedAttributeGroup[] attributeGroupArray) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.arraySetterHelper(attributeGroupArray, ATTRIBUTEGROUP$8);
         }
      }

      public void setAttributeGroupArray(int i, NamedAttributeGroup attributeGroup) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            NamedAttributeGroup target = null;
            target = (NamedAttributeGroup)this.get_store().find_element_user(ATTRIBUTEGROUP$8, i);
            if (target == null) {
               throw new IndexOutOfBoundsException();
            } else {
               target.set(attributeGroup);
            }
         }
      }

      public NamedAttributeGroup insertNewAttributeGroup(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            NamedAttributeGroup target = null;
            target = (NamedAttributeGroup)this.get_store().insert_element_user(ATTRIBUTEGROUP$8, i);
            return target;
         }
      }

      public NamedAttributeGroup addNewAttributeGroup() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            NamedAttributeGroup target = null;
            target = (NamedAttributeGroup)this.get_store().add_element_user(ATTRIBUTEGROUP$8);
            return target;
         }
      }

      public void removeAttributeGroup(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(ATTRIBUTEGROUP$8, i);
         }
      }

      public String getSchemaLocation() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(SCHEMALOCATION$10);
            return target == null ? null : target.getStringValue();
         }
      }

      public XmlAnyURI xgetSchemaLocation() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlAnyURI target = null;
            target = (XmlAnyURI)this.get_store().find_attribute_user(SCHEMALOCATION$10);
            return target;
         }
      }

      public void setSchemaLocation(String schemaLocation) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(SCHEMALOCATION$10);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_attribute_user(SCHEMALOCATION$10);
            }

            target.setStringValue(schemaLocation);
         }
      }

      public void xsetSchemaLocation(XmlAnyURI schemaLocation) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlAnyURI target = null;
            target = (XmlAnyURI)this.get_store().find_attribute_user(SCHEMALOCATION$10);
            if (target == null) {
               target = (XmlAnyURI)this.get_store().add_attribute_user(SCHEMALOCATION$10);
            }

            target.set(schemaLocation);
         }
      }

      public String getId() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(ID$12);
            return target == null ? null : target.getStringValue();
         }
      }

      public XmlID xgetId() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlID target = null;
            target = (XmlID)this.get_store().find_attribute_user(ID$12);
            return target;
         }
      }

      public boolean isSetId() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().find_attribute_user(ID$12) != null;
         }
      }

      public void setId(String id) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(ID$12);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_attribute_user(ID$12);
            }

            target.setStringValue(id);
         }
      }

      public void xsetId(XmlID id) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlID target = null;
            target = (XmlID)this.get_store().find_attribute_user(ID$12);
            if (target == null) {
               target = (XmlID)this.get_store().add_attribute_user(ID$12);
            }

            target.set(id);
         }
      }

      public void unsetId() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_attribute(ID$12);
         }
      }
   }
}
