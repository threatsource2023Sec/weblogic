package com.oracle.xmlns.weblogic.weblogicApplication.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicApplication.EntityMappingType;
import com.oracle.xmlns.weblogic.weblogicApplication.ParserFactoryType;
import com.oracle.xmlns.weblogic.weblogicApplication.XmlType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class XmlTypeImpl extends XmlComplexContentImpl implements XmlType {
   private static final long serialVersionUID = 1L;
   private static final QName PARSERFACTORY$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "parser-factory");
   private static final QName ENTITYMAPPING$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "entity-mapping");

   public XmlTypeImpl(SchemaType sType) {
      super(sType);
   }

   public ParserFactoryType getParserFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ParserFactoryType target = null;
         target = (ParserFactoryType)this.get_store().find_element_user(PARSERFACTORY$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetParserFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PARSERFACTORY$0) != 0;
      }
   }

   public void setParserFactory(ParserFactoryType parserFactory) {
      this.generatedSetterHelperImpl(parserFactory, PARSERFACTORY$0, 0, (short)1);
   }

   public ParserFactoryType addNewParserFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ParserFactoryType target = null;
         target = (ParserFactoryType)this.get_store().add_element_user(PARSERFACTORY$0);
         return target;
      }
   }

   public void unsetParserFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PARSERFACTORY$0, 0);
      }
   }

   public EntityMappingType[] getEntityMappingArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(ENTITYMAPPING$2, targetList);
         EntityMappingType[] result = new EntityMappingType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public EntityMappingType getEntityMappingArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EntityMappingType target = null;
         target = (EntityMappingType)this.get_store().find_element_user(ENTITYMAPPING$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfEntityMappingArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ENTITYMAPPING$2);
      }
   }

   public void setEntityMappingArray(EntityMappingType[] entityMappingArray) {
      this.check_orphaned();
      this.arraySetterHelper(entityMappingArray, ENTITYMAPPING$2);
   }

   public void setEntityMappingArray(int i, EntityMappingType entityMapping) {
      this.generatedSetterHelperImpl(entityMapping, ENTITYMAPPING$2, i, (short)2);
   }

   public EntityMappingType insertNewEntityMapping(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EntityMappingType target = null;
         target = (EntityMappingType)this.get_store().insert_element_user(ENTITYMAPPING$2, i);
         return target;
      }
   }

   public EntityMappingType addNewEntityMapping() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EntityMappingType target = null;
         target = (EntityMappingType)this.get_store().add_element_user(ENTITYMAPPING$2);
         return target;
      }
   }

   public void removeEntityMapping(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ENTITYMAPPING$2, i);
      }
   }
}
