package com.oracle.xmlns.weblogic.collage.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.collage.MappingType;
import com.oracle.xmlns.weblogic.collage.PatternType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class MappingTypeImpl extends XmlComplexContentImpl implements MappingType {
   private static final long serialVersionUID = 1L;
   private static final QName STYLE$0 = new QName("http://xmlns.oracle.com/weblogic/collage", "style");
   private static final QName URI$2 = new QName("http://xmlns.oracle.com/weblogic/collage", "uri");
   private static final QName PATH$4 = new QName("http://xmlns.oracle.com/weblogic/collage", "path");
   private static final QName PATTERN$6 = new QName("http://xmlns.oracle.com/weblogic/collage", "pattern");
   private static final QName INCLUDE$8 = new QName("http://xmlns.oracle.com/weblogic/collage", "include");
   private static final QName EXCLUDE$10 = new QName("http://xmlns.oracle.com/weblogic/collage", "exclude");

   public MappingTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getStyle() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(STYLE$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetStyle() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(STYLE$0, 0);
         return target;
      }
   }

   public boolean isSetStyle() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(STYLE$0) != 0;
      }
   }

   public void setStyle(String style) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(STYLE$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(STYLE$0);
         }

         target.setStringValue(style);
      }
   }

   public void xsetStyle(XmlString style) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(STYLE$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(STYLE$0);
         }

         target.set(style);
      }
   }

   public void unsetStyle() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(STYLE$0, 0);
      }
   }

   public String getUri() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(URI$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetUri() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(URI$2, 0);
         return target;
      }
   }

   public void setUri(String uri) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(URI$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(URI$2);
         }

         target.setStringValue(uri);
      }
   }

   public void xsetUri(XmlString uri) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(URI$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(URI$2);
         }

         target.set(uri);
      }
   }

   public String getPath() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PATH$4, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetPath() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PATH$4, 0);
         return target;
      }
   }

   public void setPath(String path) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PATH$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(PATH$4);
         }

         target.setStringValue(path);
      }
   }

   public void xsetPath(XmlString path) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PATH$4, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(PATH$4);
         }

         target.set(path);
      }
   }

   public PatternType[] getPatternArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(PATTERN$6, targetList);
         PatternType[] result = new PatternType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public PatternType getPatternArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PatternType target = null;
         target = (PatternType)this.get_store().find_element_user(PATTERN$6, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfPatternArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PATTERN$6);
      }
   }

   public void setPatternArray(PatternType[] patternArray) {
      this.check_orphaned();
      this.arraySetterHelper(patternArray, PATTERN$6);
   }

   public void setPatternArray(int i, PatternType pattern) {
      this.generatedSetterHelperImpl(pattern, PATTERN$6, i, (short)2);
   }

   public PatternType insertNewPattern(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PatternType target = null;
         target = (PatternType)this.get_store().insert_element_user(PATTERN$6, i);
         return target;
      }
   }

   public PatternType addNewPattern() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PatternType target = null;
         target = (PatternType)this.get_store().add_element_user(PATTERN$6);
         return target;
      }
   }

   public void removePattern(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PATTERN$6, i);
      }
   }

   public String[] getIncludeArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(INCLUDE$8, targetList);
         String[] result = new String[targetList.size()];
         int i = 0;

         for(int len = targetList.size(); i < len; ++i) {
            result[i] = ((SimpleValue)targetList.get(i)).getStringValue();
         }

         return result;
      }
   }

   public String getIncludeArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(INCLUDE$8, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target.getStringValue();
         }
      }
   }

   public XmlString[] xgetIncludeArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(INCLUDE$8, targetList);
         XmlString[] result = new XmlString[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public XmlString xgetIncludeArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(INCLUDE$8, i);
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
         return this.get_store().count_elements(INCLUDE$8);
      }
   }

   public void setIncludeArray(String[] includeArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(includeArray, INCLUDE$8);
      }
   }

   public void setIncludeArray(int i, String include) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(INCLUDE$8, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.setStringValue(include);
         }
      }
   }

   public void xsetIncludeArray(XmlString[] includeArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(includeArray, INCLUDE$8);
      }
   }

   public void xsetIncludeArray(int i, XmlString include) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(INCLUDE$8, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.set(include);
         }
      }
   }

   public void insertInclude(int i, String include) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = (SimpleValue)this.get_store().insert_element_user(INCLUDE$8, i);
         target.setStringValue(include);
      }
   }

   public void addInclude(String include) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().add_element_user(INCLUDE$8);
         target.setStringValue(include);
      }
   }

   public XmlString insertNewInclude(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().insert_element_user(INCLUDE$8, i);
         return target;
      }
   }

   public XmlString addNewInclude() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().add_element_user(INCLUDE$8);
         return target;
      }
   }

   public void removeInclude(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(INCLUDE$8, i);
      }
   }

   public String[] getExcludeArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(EXCLUDE$10, targetList);
         String[] result = new String[targetList.size()];
         int i = 0;

         for(int len = targetList.size(); i < len; ++i) {
            result[i] = ((SimpleValue)targetList.get(i)).getStringValue();
         }

         return result;
      }
   }

   public String getExcludeArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(EXCLUDE$10, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target.getStringValue();
         }
      }
   }

   public XmlString[] xgetExcludeArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(EXCLUDE$10, targetList);
         XmlString[] result = new XmlString[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public XmlString xgetExcludeArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(EXCLUDE$10, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfExcludeArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(EXCLUDE$10);
      }
   }

   public void setExcludeArray(String[] excludeArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(excludeArray, EXCLUDE$10);
      }
   }

   public void setExcludeArray(int i, String exclude) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(EXCLUDE$10, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.setStringValue(exclude);
         }
      }
   }

   public void xsetExcludeArray(XmlString[] excludeArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(excludeArray, EXCLUDE$10);
      }
   }

   public void xsetExcludeArray(int i, XmlString exclude) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(EXCLUDE$10, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.set(exclude);
         }
      }
   }

   public void insertExclude(int i, String exclude) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = (SimpleValue)this.get_store().insert_element_user(EXCLUDE$10, i);
         target.setStringValue(exclude);
      }
   }

   public void addExclude(String exclude) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().add_element_user(EXCLUDE$10);
         target.setStringValue(exclude);
      }
   }

   public XmlString insertNewExclude(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().insert_element_user(EXCLUDE$10, i);
         return target;
      }
   }

   public XmlString addNewExclude() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().add_element_user(EXCLUDE$10);
         return target;
      }
   }

   public void removeExclude(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(EXCLUDE$10, i);
      }
   }
}
