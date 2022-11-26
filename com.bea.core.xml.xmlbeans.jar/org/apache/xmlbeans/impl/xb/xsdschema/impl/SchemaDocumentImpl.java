package org.apache.xmlbeans.impl.xb.xsdschema.impl;

import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.SimpleValue;
import org.apache.xmlbeans.XmlAnyURI;
import org.apache.xmlbeans.XmlID;
import org.apache.xmlbeans.XmlLanguage;
import org.apache.xmlbeans.XmlToken;
import org.apache.xmlbeans.impl.values.XmlComplexContentImpl;
import org.apache.xmlbeans.impl.xb.xsdschema.AnnotationDocument;
import org.apache.xmlbeans.impl.xb.xsdschema.BlockSet;
import org.apache.xmlbeans.impl.xb.xsdschema.FormChoice;
import org.apache.xmlbeans.impl.xb.xsdschema.FullDerivationSet;
import org.apache.xmlbeans.impl.xb.xsdschema.ImportDocument;
import org.apache.xmlbeans.impl.xb.xsdschema.IncludeDocument;
import org.apache.xmlbeans.impl.xb.xsdschema.NamedAttributeGroup;
import org.apache.xmlbeans.impl.xb.xsdschema.NamedGroup;
import org.apache.xmlbeans.impl.xb.xsdschema.NotationDocument;
import org.apache.xmlbeans.impl.xb.xsdschema.RedefineDocument;
import org.apache.xmlbeans.impl.xb.xsdschema.SchemaDocument;
import org.apache.xmlbeans.impl.xb.xsdschema.TopLevelAttribute;
import org.apache.xmlbeans.impl.xb.xsdschema.TopLevelComplexType;
import org.apache.xmlbeans.impl.xb.xsdschema.TopLevelElement;
import org.apache.xmlbeans.impl.xb.xsdschema.TopLevelSimpleType;

public class SchemaDocumentImpl extends XmlComplexContentImpl implements SchemaDocument {
   private static final QName SCHEMA$0 = new QName("http://www.w3.org/2001/XMLSchema", "schema");

   public SchemaDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public SchemaDocument.Schema getSchema() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SchemaDocument.Schema target = null;
         target = (SchemaDocument.Schema)this.get_store().find_element_user((QName)SCHEMA$0, 0);
         return target == null ? null : target;
      }
   }

   public void setSchema(SchemaDocument.Schema schema) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SchemaDocument.Schema target = null;
         target = (SchemaDocument.Schema)this.get_store().find_element_user((QName)SCHEMA$0, 0);
         if (target == null) {
            target = (SchemaDocument.Schema)this.get_store().add_element_user(SCHEMA$0);
         }

         target.set(schema);
      }
   }

   public SchemaDocument.Schema addNewSchema() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SchemaDocument.Schema target = null;
         target = (SchemaDocument.Schema)this.get_store().add_element_user(SCHEMA$0);
         return target;
      }
   }

   public static class SchemaImpl extends OpenAttrsImpl implements SchemaDocument.Schema {
      private static final QName INCLUDE$0 = new QName("http://www.w3.org/2001/XMLSchema", "include");
      private static final QName IMPORT$2 = new QName("http://www.w3.org/2001/XMLSchema", "import");
      private static final QName REDEFINE$4 = new QName("http://www.w3.org/2001/XMLSchema", "redefine");
      private static final QName ANNOTATION$6 = new QName("http://www.w3.org/2001/XMLSchema", "annotation");
      private static final QName SIMPLETYPE$8 = new QName("http://www.w3.org/2001/XMLSchema", "simpleType");
      private static final QName COMPLEXTYPE$10 = new QName("http://www.w3.org/2001/XMLSchema", "complexType");
      private static final QName GROUP$12 = new QName("http://www.w3.org/2001/XMLSchema", "group");
      private static final QName ATTRIBUTEGROUP$14 = new QName("http://www.w3.org/2001/XMLSchema", "attributeGroup");
      private static final QName ELEMENT$16 = new QName("http://www.w3.org/2001/XMLSchema", "element");
      private static final QName ATTRIBUTE$18 = new QName("http://www.w3.org/2001/XMLSchema", "attribute");
      private static final QName NOTATION$20 = new QName("http://www.w3.org/2001/XMLSchema", "notation");
      private static final QName TARGETNAMESPACE$22 = new QName("", "targetNamespace");
      private static final QName VERSION$24 = new QName("", "version");
      private static final QName FINALDEFAULT$26 = new QName("", "finalDefault");
      private static final QName BLOCKDEFAULT$28 = new QName("", "blockDefault");
      private static final QName ATTRIBUTEFORMDEFAULT$30 = new QName("", "attributeFormDefault");
      private static final QName ELEMENTFORMDEFAULT$32 = new QName("", "elementFormDefault");
      private static final QName ID$34 = new QName("", "id");
      private static final QName LANG$36 = new QName("http://www.w3.org/XML/1998/namespace", "lang");

      public SchemaImpl(SchemaType sType) {
         super(sType);
      }

      public IncludeDocument.Include[] getIncludeArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            List targetList = new ArrayList();
            this.get_store().find_all_element_users((QName)INCLUDE$0, targetList);
            IncludeDocument.Include[] result = new IncludeDocument.Include[targetList.size()];
            targetList.toArray(result);
            return result;
         }
      }

      public IncludeDocument.Include getIncludeArray(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            IncludeDocument.Include target = null;
            target = (IncludeDocument.Include)this.get_store().find_element_user(INCLUDE$0, i);
            if (target == null) {
               throw new IndexOutOfBoundsException();
            } else {
               return target;
            }
         }
      }

      public int sizeOfIncludeArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(INCLUDE$0);
         }
      }

      public void setIncludeArray(IncludeDocument.Include[] includeArray) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.arraySetterHelper(includeArray, INCLUDE$0);
         }
      }

      public void setIncludeArray(int i, IncludeDocument.Include include) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            IncludeDocument.Include target = null;
            target = (IncludeDocument.Include)this.get_store().find_element_user(INCLUDE$0, i);
            if (target == null) {
               throw new IndexOutOfBoundsException();
            } else {
               target.set(include);
            }
         }
      }

      public IncludeDocument.Include insertNewInclude(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            IncludeDocument.Include target = null;
            target = (IncludeDocument.Include)this.get_store().insert_element_user(INCLUDE$0, i);
            return target;
         }
      }

      public IncludeDocument.Include addNewInclude() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            IncludeDocument.Include target = null;
            target = (IncludeDocument.Include)this.get_store().add_element_user(INCLUDE$0);
            return target;
         }
      }

      public void removeInclude(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(INCLUDE$0, i);
         }
      }

      public ImportDocument.Import[] getImportArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            List targetList = new ArrayList();
            this.get_store().find_all_element_users((QName)IMPORT$2, targetList);
            ImportDocument.Import[] result = new ImportDocument.Import[targetList.size()];
            targetList.toArray(result);
            return result;
         }
      }

      public ImportDocument.Import getImportArray(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            ImportDocument.Import target = null;
            target = (ImportDocument.Import)this.get_store().find_element_user(IMPORT$2, i);
            if (target == null) {
               throw new IndexOutOfBoundsException();
            } else {
               return target;
            }
         }
      }

      public int sizeOfImportArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(IMPORT$2);
         }
      }

      public void setImportArray(ImportDocument.Import[] ximportArray) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.arraySetterHelper(ximportArray, IMPORT$2);
         }
      }

      public void setImportArray(int i, ImportDocument.Import ximport) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            ImportDocument.Import target = null;
            target = (ImportDocument.Import)this.get_store().find_element_user(IMPORT$2, i);
            if (target == null) {
               throw new IndexOutOfBoundsException();
            } else {
               target.set(ximport);
            }
         }
      }

      public ImportDocument.Import insertNewImport(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            ImportDocument.Import target = null;
            target = (ImportDocument.Import)this.get_store().insert_element_user(IMPORT$2, i);
            return target;
         }
      }

      public ImportDocument.Import addNewImport() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            ImportDocument.Import target = null;
            target = (ImportDocument.Import)this.get_store().add_element_user(IMPORT$2);
            return target;
         }
      }

      public void removeImport(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(IMPORT$2, i);
         }
      }

      public RedefineDocument.Redefine[] getRedefineArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            List targetList = new ArrayList();
            this.get_store().find_all_element_users((QName)REDEFINE$4, targetList);
            RedefineDocument.Redefine[] result = new RedefineDocument.Redefine[targetList.size()];
            targetList.toArray(result);
            return result;
         }
      }

      public RedefineDocument.Redefine getRedefineArray(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            RedefineDocument.Redefine target = null;
            target = (RedefineDocument.Redefine)this.get_store().find_element_user(REDEFINE$4, i);
            if (target == null) {
               throw new IndexOutOfBoundsException();
            } else {
               return target;
            }
         }
      }

      public int sizeOfRedefineArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(REDEFINE$4);
         }
      }

      public void setRedefineArray(RedefineDocument.Redefine[] redefineArray) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.arraySetterHelper(redefineArray, REDEFINE$4);
         }
      }

      public void setRedefineArray(int i, RedefineDocument.Redefine redefine) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            RedefineDocument.Redefine target = null;
            target = (RedefineDocument.Redefine)this.get_store().find_element_user(REDEFINE$4, i);
            if (target == null) {
               throw new IndexOutOfBoundsException();
            } else {
               target.set(redefine);
            }
         }
      }

      public RedefineDocument.Redefine insertNewRedefine(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            RedefineDocument.Redefine target = null;
            target = (RedefineDocument.Redefine)this.get_store().insert_element_user(REDEFINE$4, i);
            return target;
         }
      }

      public RedefineDocument.Redefine addNewRedefine() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            RedefineDocument.Redefine target = null;
            target = (RedefineDocument.Redefine)this.get_store().add_element_user(REDEFINE$4);
            return target;
         }
      }

      public void removeRedefine(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(REDEFINE$4, i);
         }
      }

      public AnnotationDocument.Annotation[] getAnnotationArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            List targetList = new ArrayList();
            this.get_store().find_all_element_users((QName)ANNOTATION$6, targetList);
            AnnotationDocument.Annotation[] result = new AnnotationDocument.Annotation[targetList.size()];
            targetList.toArray(result);
            return result;
         }
      }

      public AnnotationDocument.Annotation getAnnotationArray(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            AnnotationDocument.Annotation target = null;
            target = (AnnotationDocument.Annotation)this.get_store().find_element_user(ANNOTATION$6, i);
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
            return this.get_store().count_elements(ANNOTATION$6);
         }
      }

      public void setAnnotationArray(AnnotationDocument.Annotation[] annotationArray) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.arraySetterHelper(annotationArray, ANNOTATION$6);
         }
      }

      public void setAnnotationArray(int i, AnnotationDocument.Annotation annotation) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            AnnotationDocument.Annotation target = null;
            target = (AnnotationDocument.Annotation)this.get_store().find_element_user(ANNOTATION$6, i);
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
            target = (AnnotationDocument.Annotation)this.get_store().insert_element_user(ANNOTATION$6, i);
            return target;
         }
      }

      public AnnotationDocument.Annotation addNewAnnotation() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            AnnotationDocument.Annotation target = null;
            target = (AnnotationDocument.Annotation)this.get_store().add_element_user(ANNOTATION$6);
            return target;
         }
      }

      public void removeAnnotation(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(ANNOTATION$6, i);
         }
      }

      public TopLevelSimpleType[] getSimpleTypeArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            List targetList = new ArrayList();
            this.get_store().find_all_element_users((QName)SIMPLETYPE$8, targetList);
            TopLevelSimpleType[] result = new TopLevelSimpleType[targetList.size()];
            targetList.toArray(result);
            return result;
         }
      }

      public TopLevelSimpleType getSimpleTypeArray(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            TopLevelSimpleType target = null;
            target = (TopLevelSimpleType)this.get_store().find_element_user(SIMPLETYPE$8, i);
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
            return this.get_store().count_elements(SIMPLETYPE$8);
         }
      }

      public void setSimpleTypeArray(TopLevelSimpleType[] simpleTypeArray) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.arraySetterHelper(simpleTypeArray, SIMPLETYPE$8);
         }
      }

      public void setSimpleTypeArray(int i, TopLevelSimpleType simpleType) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            TopLevelSimpleType target = null;
            target = (TopLevelSimpleType)this.get_store().find_element_user(SIMPLETYPE$8, i);
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
            target = (TopLevelSimpleType)this.get_store().insert_element_user(SIMPLETYPE$8, i);
            return target;
         }
      }

      public TopLevelSimpleType addNewSimpleType() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            TopLevelSimpleType target = null;
            target = (TopLevelSimpleType)this.get_store().add_element_user(SIMPLETYPE$8);
            return target;
         }
      }

      public void removeSimpleType(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(SIMPLETYPE$8, i);
         }
      }

      public TopLevelComplexType[] getComplexTypeArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            List targetList = new ArrayList();
            this.get_store().find_all_element_users((QName)COMPLEXTYPE$10, targetList);
            TopLevelComplexType[] result = new TopLevelComplexType[targetList.size()];
            targetList.toArray(result);
            return result;
         }
      }

      public TopLevelComplexType getComplexTypeArray(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            TopLevelComplexType target = null;
            target = (TopLevelComplexType)this.get_store().find_element_user(COMPLEXTYPE$10, i);
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
            return this.get_store().count_elements(COMPLEXTYPE$10);
         }
      }

      public void setComplexTypeArray(TopLevelComplexType[] complexTypeArray) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.arraySetterHelper(complexTypeArray, COMPLEXTYPE$10);
         }
      }

      public void setComplexTypeArray(int i, TopLevelComplexType complexType) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            TopLevelComplexType target = null;
            target = (TopLevelComplexType)this.get_store().find_element_user(COMPLEXTYPE$10, i);
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
            target = (TopLevelComplexType)this.get_store().insert_element_user(COMPLEXTYPE$10, i);
            return target;
         }
      }

      public TopLevelComplexType addNewComplexType() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            TopLevelComplexType target = null;
            target = (TopLevelComplexType)this.get_store().add_element_user(COMPLEXTYPE$10);
            return target;
         }
      }

      public void removeComplexType(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(COMPLEXTYPE$10, i);
         }
      }

      public NamedGroup[] getGroupArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            List targetList = new ArrayList();
            this.get_store().find_all_element_users((QName)GROUP$12, targetList);
            NamedGroup[] result = new NamedGroup[targetList.size()];
            targetList.toArray(result);
            return result;
         }
      }

      public NamedGroup getGroupArray(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            NamedGroup target = null;
            target = (NamedGroup)this.get_store().find_element_user(GROUP$12, i);
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
            return this.get_store().count_elements(GROUP$12);
         }
      }

      public void setGroupArray(NamedGroup[] groupArray) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.arraySetterHelper(groupArray, GROUP$12);
         }
      }

      public void setGroupArray(int i, NamedGroup group) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            NamedGroup target = null;
            target = (NamedGroup)this.get_store().find_element_user(GROUP$12, i);
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
            target = (NamedGroup)this.get_store().insert_element_user(GROUP$12, i);
            return target;
         }
      }

      public NamedGroup addNewGroup() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            NamedGroup target = null;
            target = (NamedGroup)this.get_store().add_element_user(GROUP$12);
            return target;
         }
      }

      public void removeGroup(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(GROUP$12, i);
         }
      }

      public NamedAttributeGroup[] getAttributeGroupArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            List targetList = new ArrayList();
            this.get_store().find_all_element_users((QName)ATTRIBUTEGROUP$14, targetList);
            NamedAttributeGroup[] result = new NamedAttributeGroup[targetList.size()];
            targetList.toArray(result);
            return result;
         }
      }

      public NamedAttributeGroup getAttributeGroupArray(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            NamedAttributeGroup target = null;
            target = (NamedAttributeGroup)this.get_store().find_element_user(ATTRIBUTEGROUP$14, i);
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
            return this.get_store().count_elements(ATTRIBUTEGROUP$14);
         }
      }

      public void setAttributeGroupArray(NamedAttributeGroup[] attributeGroupArray) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.arraySetterHelper(attributeGroupArray, ATTRIBUTEGROUP$14);
         }
      }

      public void setAttributeGroupArray(int i, NamedAttributeGroup attributeGroup) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            NamedAttributeGroup target = null;
            target = (NamedAttributeGroup)this.get_store().find_element_user(ATTRIBUTEGROUP$14, i);
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
            target = (NamedAttributeGroup)this.get_store().insert_element_user(ATTRIBUTEGROUP$14, i);
            return target;
         }
      }

      public NamedAttributeGroup addNewAttributeGroup() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            NamedAttributeGroup target = null;
            target = (NamedAttributeGroup)this.get_store().add_element_user(ATTRIBUTEGROUP$14);
            return target;
         }
      }

      public void removeAttributeGroup(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(ATTRIBUTEGROUP$14, i);
         }
      }

      public TopLevelElement[] getElementArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            List targetList = new ArrayList();
            this.get_store().find_all_element_users((QName)ELEMENT$16, targetList);
            TopLevelElement[] result = new TopLevelElement[targetList.size()];
            targetList.toArray(result);
            return result;
         }
      }

      public TopLevelElement getElementArray(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            TopLevelElement target = null;
            target = (TopLevelElement)this.get_store().find_element_user(ELEMENT$16, i);
            if (target == null) {
               throw new IndexOutOfBoundsException();
            } else {
               return target;
            }
         }
      }

      public int sizeOfElementArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(ELEMENT$16);
         }
      }

      public void setElementArray(TopLevelElement[] elementArray) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.arraySetterHelper(elementArray, ELEMENT$16);
         }
      }

      public void setElementArray(int i, TopLevelElement element) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            TopLevelElement target = null;
            target = (TopLevelElement)this.get_store().find_element_user(ELEMENT$16, i);
            if (target == null) {
               throw new IndexOutOfBoundsException();
            } else {
               target.set(element);
            }
         }
      }

      public TopLevelElement insertNewElement(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            TopLevelElement target = null;
            target = (TopLevelElement)this.get_store().insert_element_user(ELEMENT$16, i);
            return target;
         }
      }

      public TopLevelElement addNewElement() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            TopLevelElement target = null;
            target = (TopLevelElement)this.get_store().add_element_user(ELEMENT$16);
            return target;
         }
      }

      public void removeElement(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(ELEMENT$16, i);
         }
      }

      public TopLevelAttribute[] getAttributeArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            List targetList = new ArrayList();
            this.get_store().find_all_element_users((QName)ATTRIBUTE$18, targetList);
            TopLevelAttribute[] result = new TopLevelAttribute[targetList.size()];
            targetList.toArray(result);
            return result;
         }
      }

      public TopLevelAttribute getAttributeArray(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            TopLevelAttribute target = null;
            target = (TopLevelAttribute)this.get_store().find_element_user(ATTRIBUTE$18, i);
            if (target == null) {
               throw new IndexOutOfBoundsException();
            } else {
               return target;
            }
         }
      }

      public int sizeOfAttributeArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(ATTRIBUTE$18);
         }
      }

      public void setAttributeArray(TopLevelAttribute[] attributeArray) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.arraySetterHelper(attributeArray, ATTRIBUTE$18);
         }
      }

      public void setAttributeArray(int i, TopLevelAttribute attribute) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            TopLevelAttribute target = null;
            target = (TopLevelAttribute)this.get_store().find_element_user(ATTRIBUTE$18, i);
            if (target == null) {
               throw new IndexOutOfBoundsException();
            } else {
               target.set(attribute);
            }
         }
      }

      public TopLevelAttribute insertNewAttribute(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            TopLevelAttribute target = null;
            target = (TopLevelAttribute)this.get_store().insert_element_user(ATTRIBUTE$18, i);
            return target;
         }
      }

      public TopLevelAttribute addNewAttribute() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            TopLevelAttribute target = null;
            target = (TopLevelAttribute)this.get_store().add_element_user(ATTRIBUTE$18);
            return target;
         }
      }

      public void removeAttribute(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(ATTRIBUTE$18, i);
         }
      }

      public NotationDocument.Notation[] getNotationArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            List targetList = new ArrayList();
            this.get_store().find_all_element_users((QName)NOTATION$20, targetList);
            NotationDocument.Notation[] result = new NotationDocument.Notation[targetList.size()];
            targetList.toArray(result);
            return result;
         }
      }

      public NotationDocument.Notation getNotationArray(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            NotationDocument.Notation target = null;
            target = (NotationDocument.Notation)this.get_store().find_element_user(NOTATION$20, i);
            if (target == null) {
               throw new IndexOutOfBoundsException();
            } else {
               return target;
            }
         }
      }

      public int sizeOfNotationArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(NOTATION$20);
         }
      }

      public void setNotationArray(NotationDocument.Notation[] notationArray) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.arraySetterHelper(notationArray, NOTATION$20);
         }
      }

      public void setNotationArray(int i, NotationDocument.Notation notation) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            NotationDocument.Notation target = null;
            target = (NotationDocument.Notation)this.get_store().find_element_user(NOTATION$20, i);
            if (target == null) {
               throw new IndexOutOfBoundsException();
            } else {
               target.set(notation);
            }
         }
      }

      public NotationDocument.Notation insertNewNotation(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            NotationDocument.Notation target = null;
            target = (NotationDocument.Notation)this.get_store().insert_element_user(NOTATION$20, i);
            return target;
         }
      }

      public NotationDocument.Notation addNewNotation() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            NotationDocument.Notation target = null;
            target = (NotationDocument.Notation)this.get_store().add_element_user(NOTATION$20);
            return target;
         }
      }

      public void removeNotation(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(NOTATION$20, i);
         }
      }

      public String getTargetNamespace() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(TARGETNAMESPACE$22);
            return target == null ? null : target.getStringValue();
         }
      }

      public XmlAnyURI xgetTargetNamespace() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlAnyURI target = null;
            target = (XmlAnyURI)this.get_store().find_attribute_user(TARGETNAMESPACE$22);
            return target;
         }
      }

      public boolean isSetTargetNamespace() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().find_attribute_user(TARGETNAMESPACE$22) != null;
         }
      }

      public void setTargetNamespace(String targetNamespace) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(TARGETNAMESPACE$22);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_attribute_user(TARGETNAMESPACE$22);
            }

            target.setStringValue(targetNamespace);
         }
      }

      public void xsetTargetNamespace(XmlAnyURI targetNamespace) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlAnyURI target = null;
            target = (XmlAnyURI)this.get_store().find_attribute_user(TARGETNAMESPACE$22);
            if (target == null) {
               target = (XmlAnyURI)this.get_store().add_attribute_user(TARGETNAMESPACE$22);
            }

            target.set(targetNamespace);
         }
      }

      public void unsetTargetNamespace() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_attribute(TARGETNAMESPACE$22);
         }
      }

      public String getVersion() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(VERSION$24);
            return target == null ? null : target.getStringValue();
         }
      }

      public XmlToken xgetVersion() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlToken target = null;
            target = (XmlToken)this.get_store().find_attribute_user(VERSION$24);
            return target;
         }
      }

      public boolean isSetVersion() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().find_attribute_user(VERSION$24) != null;
         }
      }

      public void setVersion(String version) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(VERSION$24);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_attribute_user(VERSION$24);
            }

            target.setStringValue(version);
         }
      }

      public void xsetVersion(XmlToken version) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlToken target = null;
            target = (XmlToken)this.get_store().find_attribute_user(VERSION$24);
            if (target == null) {
               target = (XmlToken)this.get_store().add_attribute_user(VERSION$24);
            }

            target.set(version);
         }
      }

      public void unsetVersion() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_attribute(VERSION$24);
         }
      }

      public Object getFinalDefault() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(FINALDEFAULT$26);
            if (target == null) {
               target = (SimpleValue)this.get_default_attribute_value(FINALDEFAULT$26);
            }

            return target == null ? null : target.getObjectValue();
         }
      }

      public FullDerivationSet xgetFinalDefault() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            FullDerivationSet target = null;
            target = (FullDerivationSet)this.get_store().find_attribute_user(FINALDEFAULT$26);
            if (target == null) {
               target = (FullDerivationSet)this.get_default_attribute_value(FINALDEFAULT$26);
            }

            return target;
         }
      }

      public boolean isSetFinalDefault() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().find_attribute_user(FINALDEFAULT$26) != null;
         }
      }

      public void setFinalDefault(Object finalDefault) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(FINALDEFAULT$26);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_attribute_user(FINALDEFAULT$26);
            }

            target.setObjectValue(finalDefault);
         }
      }

      public void xsetFinalDefault(FullDerivationSet finalDefault) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            FullDerivationSet target = null;
            target = (FullDerivationSet)this.get_store().find_attribute_user(FINALDEFAULT$26);
            if (target == null) {
               target = (FullDerivationSet)this.get_store().add_attribute_user(FINALDEFAULT$26);
            }

            target.set(finalDefault);
         }
      }

      public void unsetFinalDefault() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_attribute(FINALDEFAULT$26);
         }
      }

      public Object getBlockDefault() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(BLOCKDEFAULT$28);
            if (target == null) {
               target = (SimpleValue)this.get_default_attribute_value(BLOCKDEFAULT$28);
            }

            return target == null ? null : target.getObjectValue();
         }
      }

      public BlockSet xgetBlockDefault() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            BlockSet target = null;
            target = (BlockSet)this.get_store().find_attribute_user(BLOCKDEFAULT$28);
            if (target == null) {
               target = (BlockSet)this.get_default_attribute_value(BLOCKDEFAULT$28);
            }

            return target;
         }
      }

      public boolean isSetBlockDefault() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().find_attribute_user(BLOCKDEFAULT$28) != null;
         }
      }

      public void setBlockDefault(Object blockDefault) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(BLOCKDEFAULT$28);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_attribute_user(BLOCKDEFAULT$28);
            }

            target.setObjectValue(blockDefault);
         }
      }

      public void xsetBlockDefault(BlockSet blockDefault) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            BlockSet target = null;
            target = (BlockSet)this.get_store().find_attribute_user(BLOCKDEFAULT$28);
            if (target == null) {
               target = (BlockSet)this.get_store().add_attribute_user(BLOCKDEFAULT$28);
            }

            target.set(blockDefault);
         }
      }

      public void unsetBlockDefault() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_attribute(BLOCKDEFAULT$28);
         }
      }

      public FormChoice.Enum getAttributeFormDefault() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(ATTRIBUTEFORMDEFAULT$30);
            if (target == null) {
               target = (SimpleValue)this.get_default_attribute_value(ATTRIBUTEFORMDEFAULT$30);
            }

            return target == null ? null : (FormChoice.Enum)target.getEnumValue();
         }
      }

      public FormChoice xgetAttributeFormDefault() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            FormChoice target = null;
            target = (FormChoice)this.get_store().find_attribute_user(ATTRIBUTEFORMDEFAULT$30);
            if (target == null) {
               target = (FormChoice)this.get_default_attribute_value(ATTRIBUTEFORMDEFAULT$30);
            }

            return target;
         }
      }

      public boolean isSetAttributeFormDefault() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().find_attribute_user(ATTRIBUTEFORMDEFAULT$30) != null;
         }
      }

      public void setAttributeFormDefault(FormChoice.Enum attributeFormDefault) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(ATTRIBUTEFORMDEFAULT$30);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_attribute_user(ATTRIBUTEFORMDEFAULT$30);
            }

            target.setEnumValue(attributeFormDefault);
         }
      }

      public void xsetAttributeFormDefault(FormChoice attributeFormDefault) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            FormChoice target = null;
            target = (FormChoice)this.get_store().find_attribute_user(ATTRIBUTEFORMDEFAULT$30);
            if (target == null) {
               target = (FormChoice)this.get_store().add_attribute_user(ATTRIBUTEFORMDEFAULT$30);
            }

            target.set(attributeFormDefault);
         }
      }

      public void unsetAttributeFormDefault() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_attribute(ATTRIBUTEFORMDEFAULT$30);
         }
      }

      public FormChoice.Enum getElementFormDefault() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(ELEMENTFORMDEFAULT$32);
            if (target == null) {
               target = (SimpleValue)this.get_default_attribute_value(ELEMENTFORMDEFAULT$32);
            }

            return target == null ? null : (FormChoice.Enum)target.getEnumValue();
         }
      }

      public FormChoice xgetElementFormDefault() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            FormChoice target = null;
            target = (FormChoice)this.get_store().find_attribute_user(ELEMENTFORMDEFAULT$32);
            if (target == null) {
               target = (FormChoice)this.get_default_attribute_value(ELEMENTFORMDEFAULT$32);
            }

            return target;
         }
      }

      public boolean isSetElementFormDefault() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().find_attribute_user(ELEMENTFORMDEFAULT$32) != null;
         }
      }

      public void setElementFormDefault(FormChoice.Enum elementFormDefault) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(ELEMENTFORMDEFAULT$32);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_attribute_user(ELEMENTFORMDEFAULT$32);
            }

            target.setEnumValue(elementFormDefault);
         }
      }

      public void xsetElementFormDefault(FormChoice elementFormDefault) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            FormChoice target = null;
            target = (FormChoice)this.get_store().find_attribute_user(ELEMENTFORMDEFAULT$32);
            if (target == null) {
               target = (FormChoice)this.get_store().add_attribute_user(ELEMENTFORMDEFAULT$32);
            }

            target.set(elementFormDefault);
         }
      }

      public void unsetElementFormDefault() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_attribute(ELEMENTFORMDEFAULT$32);
         }
      }

      public String getId() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(ID$34);
            return target == null ? null : target.getStringValue();
         }
      }

      public XmlID xgetId() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlID target = null;
            target = (XmlID)this.get_store().find_attribute_user(ID$34);
            return target;
         }
      }

      public boolean isSetId() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().find_attribute_user(ID$34) != null;
         }
      }

      public void setId(String id) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(ID$34);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_attribute_user(ID$34);
            }

            target.setStringValue(id);
         }
      }

      public void xsetId(XmlID id) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlID target = null;
            target = (XmlID)this.get_store().find_attribute_user(ID$34);
            if (target == null) {
               target = (XmlID)this.get_store().add_attribute_user(ID$34);
            }

            target.set(id);
         }
      }

      public void unsetId() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_attribute(ID$34);
         }
      }

      public String getLang() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(LANG$36);
            return target == null ? null : target.getStringValue();
         }
      }

      public XmlLanguage xgetLang() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlLanguage target = null;
            target = (XmlLanguage)this.get_store().find_attribute_user(LANG$36);
            return target;
         }
      }

      public boolean isSetLang() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().find_attribute_user(LANG$36) != null;
         }
      }

      public void setLang(String lang) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(LANG$36);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_attribute_user(LANG$36);
            }

            target.setStringValue(lang);
         }
      }

      public void xsetLang(XmlLanguage lang) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlLanguage target = null;
            target = (XmlLanguage)this.get_store().find_attribute_user(LANG$36);
            if (target == null) {
               target = (XmlLanguage)this.get_store().add_attribute_user(LANG$36);
            }

            target.set(lang);
         }
      }

      public void unsetLang() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_attribute(LANG$36);
         }
      }
   }
}
