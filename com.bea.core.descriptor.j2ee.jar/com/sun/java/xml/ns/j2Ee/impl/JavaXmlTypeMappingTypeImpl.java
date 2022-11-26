package com.sun.java.xml.ns.j2Ee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.sun.java.xml.ns.j2Ee.JavaTypeType;
import com.sun.java.xml.ns.j2Ee.JavaXmlTypeMappingType;
import com.sun.java.xml.ns.j2Ee.QnameScopeType;
import com.sun.java.xml.ns.j2Ee.String;
import com.sun.java.xml.ns.j2Ee.VariableMappingType;
import com.sun.java.xml.ns.j2Ee.XsdQNameType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class JavaXmlTypeMappingTypeImpl extends XmlComplexContentImpl implements JavaXmlTypeMappingType {
   private static final long serialVersionUID = 1L;
   private static final QName JAVATYPE$0 = new QName("http://java.sun.com/xml/ns/j2ee", "java-type");
   private static final QName ROOTTYPEQNAME$2 = new QName("http://java.sun.com/xml/ns/j2ee", "root-type-qname");
   private static final QName ANONYMOUSTYPEQNAME$4 = new QName("http://java.sun.com/xml/ns/j2ee", "anonymous-type-qname");
   private static final QName QNAMESCOPE$6 = new QName("http://java.sun.com/xml/ns/j2ee", "qname-scope");
   private static final QName VARIABLEMAPPING$8 = new QName("http://java.sun.com/xml/ns/j2ee", "variable-mapping");
   private static final QName ID$10 = new QName("", "id");

   public JavaXmlTypeMappingTypeImpl(SchemaType sType) {
      super(sType);
   }

   public JavaTypeType getJavaType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JavaTypeType target = null;
         target = (JavaTypeType)this.get_store().find_element_user(JAVATYPE$0, 0);
         return target == null ? null : target;
      }
   }

   public void setJavaType(JavaTypeType javaType) {
      this.generatedSetterHelperImpl(javaType, JAVATYPE$0, 0, (short)1);
   }

   public JavaTypeType addNewJavaType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JavaTypeType target = null;
         target = (JavaTypeType)this.get_store().add_element_user(JAVATYPE$0);
         return target;
      }
   }

   public XsdQNameType getRootTypeQname() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdQNameType target = null;
         target = (XsdQNameType)this.get_store().find_element_user(ROOTTYPEQNAME$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetRootTypeQname() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ROOTTYPEQNAME$2) != 0;
      }
   }

   public void setRootTypeQname(XsdQNameType rootTypeQname) {
      this.generatedSetterHelperImpl(rootTypeQname, ROOTTYPEQNAME$2, 0, (short)1);
   }

   public XsdQNameType addNewRootTypeQname() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdQNameType target = null;
         target = (XsdQNameType)this.get_store().add_element_user(ROOTTYPEQNAME$2);
         return target;
      }
   }

   public void unsetRootTypeQname() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ROOTTYPEQNAME$2, 0);
      }
   }

   public String getAnonymousTypeQname() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(ANONYMOUSTYPEQNAME$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetAnonymousTypeQname() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ANONYMOUSTYPEQNAME$4) != 0;
      }
   }

   public void setAnonymousTypeQname(String anonymousTypeQname) {
      this.generatedSetterHelperImpl(anonymousTypeQname, ANONYMOUSTYPEQNAME$4, 0, (short)1);
   }

   public String addNewAnonymousTypeQname() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(ANONYMOUSTYPEQNAME$4);
         return target;
      }
   }

   public void unsetAnonymousTypeQname() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ANONYMOUSTYPEQNAME$4, 0);
      }
   }

   public QnameScopeType getQnameScope() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         QnameScopeType target = null;
         target = (QnameScopeType)this.get_store().find_element_user(QNAMESCOPE$6, 0);
         return target == null ? null : target;
      }
   }

   public void setQnameScope(QnameScopeType qnameScope) {
      this.generatedSetterHelperImpl(qnameScope, QNAMESCOPE$6, 0, (short)1);
   }

   public QnameScopeType addNewQnameScope() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         QnameScopeType target = null;
         target = (QnameScopeType)this.get_store().add_element_user(QNAMESCOPE$6);
         return target;
      }
   }

   public VariableMappingType[] getVariableMappingArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(VARIABLEMAPPING$8, targetList);
         VariableMappingType[] result = new VariableMappingType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public VariableMappingType getVariableMappingArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         VariableMappingType target = null;
         target = (VariableMappingType)this.get_store().find_element_user(VARIABLEMAPPING$8, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfVariableMappingArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(VARIABLEMAPPING$8);
      }
   }

   public void setVariableMappingArray(VariableMappingType[] variableMappingArray) {
      this.check_orphaned();
      this.arraySetterHelper(variableMappingArray, VARIABLEMAPPING$8);
   }

   public void setVariableMappingArray(int i, VariableMappingType variableMapping) {
      this.generatedSetterHelperImpl(variableMapping, VARIABLEMAPPING$8, i, (short)2);
   }

   public VariableMappingType insertNewVariableMapping(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         VariableMappingType target = null;
         target = (VariableMappingType)this.get_store().insert_element_user(VARIABLEMAPPING$8, i);
         return target;
      }
   }

   public VariableMappingType addNewVariableMapping() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         VariableMappingType target = null;
         target = (VariableMappingType)this.get_store().add_element_user(VARIABLEMAPPING$8);
         return target;
      }
   }

   public void removeVariableMapping(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(VARIABLEMAPPING$8, i);
      }
   }

   public java.lang.String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$10);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$10);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$10) != null;
      }
   }

   public void setId(java.lang.String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$10);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$10);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$10);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$10);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$10);
      }
   }
}
