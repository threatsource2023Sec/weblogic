package com.bea.x2004.x03.wlw.externalConfig.impl;

import com.bea.x2004.x03.wlw.externalConfig.AnnotatedClassBean;
import com.bea.x2004.x03.wlw.externalConfig.AnnotationDefinitionBean;
import com.bea.x2004.x03.wlw.externalConfig.AnnotationManifestBean;
import com.bea.x2004.x03.wlw.externalConfig.EnumDefinitionBean;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class AnnotationManifestBeanImpl extends XmlComplexContentImpl implements AnnotationManifestBean {
   private static final long serialVersionUID = 1L;
   private static final QName ANNOTATEDCLASS$0 = new QName("http://www.bea.com/2004/03/wlw/external-config/", "annotated-class");
   private static final QName ANNOTATIONDEFINITION$2 = new QName("http://www.bea.com/2004/03/wlw/external-config/", "annotation-definition");
   private static final QName ENUMDEFINITION$4 = new QName("http://www.bea.com/2004/03/wlw/external-config/", "enum-definition");
   private static final QName VERSION$6 = new QName("http://www.bea.com/2004/03/wlw/external-config/", "version");

   public AnnotationManifestBeanImpl(SchemaType sType) {
      super(sType);
   }

   public AnnotatedClassBean[] getAnnotatedClassArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(ANNOTATEDCLASS$0, targetList);
         AnnotatedClassBean[] result = new AnnotatedClassBean[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public AnnotatedClassBean getAnnotatedClassArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AnnotatedClassBean target = null;
         target = (AnnotatedClassBean)this.get_store().find_element_user(ANNOTATEDCLASS$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfAnnotatedClassArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ANNOTATEDCLASS$0);
      }
   }

   public void setAnnotatedClassArray(AnnotatedClassBean[] annotatedClassArray) {
      this.check_orphaned();
      this.arraySetterHelper(annotatedClassArray, ANNOTATEDCLASS$0);
   }

   public void setAnnotatedClassArray(int i, AnnotatedClassBean annotatedClass) {
      this.generatedSetterHelperImpl(annotatedClass, ANNOTATEDCLASS$0, i, (short)2);
   }

   public AnnotatedClassBean insertNewAnnotatedClass(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AnnotatedClassBean target = null;
         target = (AnnotatedClassBean)this.get_store().insert_element_user(ANNOTATEDCLASS$0, i);
         return target;
      }
   }

   public AnnotatedClassBean addNewAnnotatedClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AnnotatedClassBean target = null;
         target = (AnnotatedClassBean)this.get_store().add_element_user(ANNOTATEDCLASS$0);
         return target;
      }
   }

   public void removeAnnotatedClass(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ANNOTATEDCLASS$0, i);
      }
   }

   public AnnotationDefinitionBean[] getAnnotationDefinitionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(ANNOTATIONDEFINITION$2, targetList);
         AnnotationDefinitionBean[] result = new AnnotationDefinitionBean[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public AnnotationDefinitionBean getAnnotationDefinitionArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AnnotationDefinitionBean target = null;
         target = (AnnotationDefinitionBean)this.get_store().find_element_user(ANNOTATIONDEFINITION$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfAnnotationDefinitionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ANNOTATIONDEFINITION$2);
      }
   }

   public void setAnnotationDefinitionArray(AnnotationDefinitionBean[] annotationDefinitionArray) {
      this.check_orphaned();
      this.arraySetterHelper(annotationDefinitionArray, ANNOTATIONDEFINITION$2);
   }

   public void setAnnotationDefinitionArray(int i, AnnotationDefinitionBean annotationDefinition) {
      this.generatedSetterHelperImpl(annotationDefinition, ANNOTATIONDEFINITION$2, i, (short)2);
   }

   public AnnotationDefinitionBean insertNewAnnotationDefinition(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AnnotationDefinitionBean target = null;
         target = (AnnotationDefinitionBean)this.get_store().insert_element_user(ANNOTATIONDEFINITION$2, i);
         return target;
      }
   }

   public AnnotationDefinitionBean addNewAnnotationDefinition() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AnnotationDefinitionBean target = null;
         target = (AnnotationDefinitionBean)this.get_store().add_element_user(ANNOTATIONDEFINITION$2);
         return target;
      }
   }

   public void removeAnnotationDefinition(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ANNOTATIONDEFINITION$2, i);
      }
   }

   public EnumDefinitionBean[] getEnumDefinitionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(ENUMDEFINITION$4, targetList);
         EnumDefinitionBean[] result = new EnumDefinitionBean[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public EnumDefinitionBean getEnumDefinitionArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EnumDefinitionBean target = null;
         target = (EnumDefinitionBean)this.get_store().find_element_user(ENUMDEFINITION$4, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfEnumDefinitionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ENUMDEFINITION$4);
      }
   }

   public void setEnumDefinitionArray(EnumDefinitionBean[] enumDefinitionArray) {
      this.check_orphaned();
      this.arraySetterHelper(enumDefinitionArray, ENUMDEFINITION$4);
   }

   public void setEnumDefinitionArray(int i, EnumDefinitionBean enumDefinition) {
      this.generatedSetterHelperImpl(enumDefinition, ENUMDEFINITION$4, i, (short)2);
   }

   public EnumDefinitionBean insertNewEnumDefinition(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EnumDefinitionBean target = null;
         target = (EnumDefinitionBean)this.get_store().insert_element_user(ENUMDEFINITION$4, i);
         return target;
      }
   }

   public EnumDefinitionBean addNewEnumDefinition() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EnumDefinitionBean target = null;
         target = (EnumDefinitionBean)this.get_store().add_element_user(ENUMDEFINITION$4);
         return target;
      }
   }

   public void removeEnumDefinition(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ENUMDEFINITION$4, i);
      }
   }

   public String getVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(VERSION$6, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(VERSION$6, 0);
         return target;
      }
   }

   public boolean isSetVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(VERSION$6) != 0;
      }
   }

   public void setVersion(String version) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(VERSION$6, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(VERSION$6);
         }

         target.setStringValue(version);
      }
   }

   public void xsetVersion(XmlString version) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(VERSION$6, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(VERSION$6);
         }

         target.set(version);
      }
   }

   public void unsetVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(VERSION$6, 0);
      }
   }
}
