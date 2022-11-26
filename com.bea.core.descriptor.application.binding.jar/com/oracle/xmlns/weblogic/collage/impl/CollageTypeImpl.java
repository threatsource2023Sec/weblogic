package com.oracle.xmlns.weblogic.collage.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.collage.CollageType;
import com.oracle.xmlns.weblogic.collage.MappingType;
import com.oracle.xmlns.weblogic.collage.PatternsetType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class CollageTypeImpl extends XmlComplexContentImpl implements CollageType {
   private static final long serialVersionUID = 1L;
   private static final QName MAPPINGSTYLE$0 = new QName("http://xmlns.oracle.com/weblogic/collage", "mapping-style");
   private static final QName PATTERNSET$2 = new QName("http://xmlns.oracle.com/weblogic/collage", "patternset");
   private static final QName MAPPING$4 = new QName("http://xmlns.oracle.com/weblogic/collage", "mapping");
   private static final QName PATHTOWRITABLEROOT$6 = new QName("", "path-to-writable-root");

   public CollageTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getMappingStyle() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MAPPINGSTYLE$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetMappingStyle() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(MAPPINGSTYLE$0, 0);
         return target;
      }
   }

   public boolean isSetMappingStyle() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MAPPINGSTYLE$0) != 0;
      }
   }

   public void setMappingStyle(String mappingStyle) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MAPPINGSTYLE$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(MAPPINGSTYLE$0);
         }

         target.setStringValue(mappingStyle);
      }
   }

   public void xsetMappingStyle(XmlString mappingStyle) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(MAPPINGSTYLE$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(MAPPINGSTYLE$0);
         }

         target.set(mappingStyle);
      }
   }

   public void unsetMappingStyle() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MAPPINGSTYLE$0, 0);
      }
   }

   public PatternsetType[] getPatternsetArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(PATTERNSET$2, targetList);
         PatternsetType[] result = new PatternsetType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public PatternsetType getPatternsetArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PatternsetType target = null;
         target = (PatternsetType)this.get_store().find_element_user(PATTERNSET$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfPatternsetArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PATTERNSET$2);
      }
   }

   public void setPatternsetArray(PatternsetType[] patternsetArray) {
      this.check_orphaned();
      this.arraySetterHelper(patternsetArray, PATTERNSET$2);
   }

   public void setPatternsetArray(int i, PatternsetType patternset) {
      this.generatedSetterHelperImpl(patternset, PATTERNSET$2, i, (short)2);
   }

   public PatternsetType insertNewPatternset(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PatternsetType target = null;
         target = (PatternsetType)this.get_store().insert_element_user(PATTERNSET$2, i);
         return target;
      }
   }

   public PatternsetType addNewPatternset() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PatternsetType target = null;
         target = (PatternsetType)this.get_store().add_element_user(PATTERNSET$2);
         return target;
      }
   }

   public void removePatternset(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PATTERNSET$2, i);
      }
   }

   public MappingType[] getMappingArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(MAPPING$4, targetList);
         MappingType[] result = new MappingType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public MappingType getMappingArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MappingType target = null;
         target = (MappingType)this.get_store().find_element_user(MAPPING$4, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfMappingArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MAPPING$4);
      }
   }

   public void setMappingArray(MappingType[] mappingArray) {
      this.check_orphaned();
      this.arraySetterHelper(mappingArray, MAPPING$4);
   }

   public void setMappingArray(int i, MappingType mapping) {
      this.generatedSetterHelperImpl(mapping, MAPPING$4, i, (short)2);
   }

   public MappingType insertNewMapping(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MappingType target = null;
         target = (MappingType)this.get_store().insert_element_user(MAPPING$4, i);
         return target;
      }
   }

   public MappingType addNewMapping() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MappingType target = null;
         target = (MappingType)this.get_store().add_element_user(MAPPING$4);
         return target;
      }
   }

   public void removeMapping(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MAPPING$4, i);
      }
   }

   public String getPathToWritableRoot() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(PATHTOWRITABLEROOT$6);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetPathToWritableRoot() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(PATHTOWRITABLEROOT$6);
         return target;
      }
   }

   public boolean isSetPathToWritableRoot() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(PATHTOWRITABLEROOT$6) != null;
      }
   }

   public void setPathToWritableRoot(String pathToWritableRoot) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(PATHTOWRITABLEROOT$6);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(PATHTOWRITABLEROOT$6);
         }

         target.setStringValue(pathToWritableRoot);
      }
   }

   public void xsetPathToWritableRoot(XmlString pathToWritableRoot) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(PATHTOWRITABLEROOT$6);
         if (target == null) {
            target = (XmlString)this.get_store().add_attribute_user(PATHTOWRITABLEROOT$6);
         }

         target.set(pathToWritableRoot);
      }
   }

   public void unsetPathToWritableRoot() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(PATHTOWRITABLEROOT$6);
      }
   }
}
