package com.oracle.xmlns.weblogic.weblogicConnector.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.oracle.xmlns.weblogic.weblogicConnector.AnonPrincipalType;
import com.oracle.xmlns.weblogic.weblogicConnector.InboundCallerPrincipalMappingType;
import com.oracle.xmlns.weblogic.weblogicConnector.InboundGroupPrincipalMappingType;
import com.oracle.xmlns.weblogic.weblogicConnector.SecurityWorkContextType;
import com.sun.java.xml.ns.javaee.String;
import com.sun.java.xml.ns.javaee.TrueFalseType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class SecurityWorkContextTypeImpl extends XmlComplexContentImpl implements SecurityWorkContextType {
   private static final long serialVersionUID = 1L;
   private static final QName INBOUNDMAPPINGREQUIRED$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "inbound-mapping-required");
   private static final QName CALLERPRINCIPALDEFAULTMAPPED$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "caller-principal-default-mapped");
   private static final QName CALLERPRINCIPALMAPPING$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "caller-principal-mapping");
   private static final QName GROUPPRINCIPALDEFAULTMAPPED$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "group-principal-default-mapped");
   private static final QName GROUPPRINCIPALMAPPING$8 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "group-principal-mapping");
   private static final QName ID$10 = new QName("", "id");

   public SecurityWorkContextTypeImpl(SchemaType sType) {
      super(sType);
   }

   public TrueFalseType getInboundMappingRequired() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(INBOUNDMAPPINGREQUIRED$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetInboundMappingRequired() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(INBOUNDMAPPINGREQUIRED$0) != 0;
      }
   }

   public void setInboundMappingRequired(TrueFalseType inboundMappingRequired) {
      this.generatedSetterHelperImpl(inboundMappingRequired, INBOUNDMAPPINGREQUIRED$0, 0, (short)1);
   }

   public TrueFalseType addNewInboundMappingRequired() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(INBOUNDMAPPINGREQUIRED$0);
         return target;
      }
   }

   public void unsetInboundMappingRequired() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(INBOUNDMAPPINGREQUIRED$0, 0);
      }
   }

   public AnonPrincipalType getCallerPrincipalDefaultMapped() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AnonPrincipalType target = null;
         target = (AnonPrincipalType)this.get_store().find_element_user(CALLERPRINCIPALDEFAULTMAPPED$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetCallerPrincipalDefaultMapped() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CALLERPRINCIPALDEFAULTMAPPED$2) != 0;
      }
   }

   public void setCallerPrincipalDefaultMapped(AnonPrincipalType callerPrincipalDefaultMapped) {
      this.generatedSetterHelperImpl(callerPrincipalDefaultMapped, CALLERPRINCIPALDEFAULTMAPPED$2, 0, (short)1);
   }

   public AnonPrincipalType addNewCallerPrincipalDefaultMapped() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AnonPrincipalType target = null;
         target = (AnonPrincipalType)this.get_store().add_element_user(CALLERPRINCIPALDEFAULTMAPPED$2);
         return target;
      }
   }

   public void unsetCallerPrincipalDefaultMapped() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CALLERPRINCIPALDEFAULTMAPPED$2, 0);
      }
   }

   public InboundCallerPrincipalMappingType[] getCallerPrincipalMappingArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(CALLERPRINCIPALMAPPING$4, targetList);
         InboundCallerPrincipalMappingType[] result = new InboundCallerPrincipalMappingType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public InboundCallerPrincipalMappingType getCallerPrincipalMappingArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         InboundCallerPrincipalMappingType target = null;
         target = (InboundCallerPrincipalMappingType)this.get_store().find_element_user(CALLERPRINCIPALMAPPING$4, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfCallerPrincipalMappingArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CALLERPRINCIPALMAPPING$4);
      }
   }

   public void setCallerPrincipalMappingArray(InboundCallerPrincipalMappingType[] callerPrincipalMappingArray) {
      this.check_orphaned();
      this.arraySetterHelper(callerPrincipalMappingArray, CALLERPRINCIPALMAPPING$4);
   }

   public void setCallerPrincipalMappingArray(int i, InboundCallerPrincipalMappingType callerPrincipalMapping) {
      this.generatedSetterHelperImpl(callerPrincipalMapping, CALLERPRINCIPALMAPPING$4, i, (short)2);
   }

   public InboundCallerPrincipalMappingType insertNewCallerPrincipalMapping(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         InboundCallerPrincipalMappingType target = null;
         target = (InboundCallerPrincipalMappingType)this.get_store().insert_element_user(CALLERPRINCIPALMAPPING$4, i);
         return target;
      }
   }

   public InboundCallerPrincipalMappingType addNewCallerPrincipalMapping() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         InboundCallerPrincipalMappingType target = null;
         target = (InboundCallerPrincipalMappingType)this.get_store().add_element_user(CALLERPRINCIPALMAPPING$4);
         return target;
      }
   }

   public void removeCallerPrincipalMapping(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CALLERPRINCIPALMAPPING$4, i);
      }
   }

   public String getGroupPrincipalDefaultMapped() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(GROUPPRINCIPALDEFAULTMAPPED$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetGroupPrincipalDefaultMapped() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(GROUPPRINCIPALDEFAULTMAPPED$6) != 0;
      }
   }

   public void setGroupPrincipalDefaultMapped(String groupPrincipalDefaultMapped) {
      this.generatedSetterHelperImpl(groupPrincipalDefaultMapped, GROUPPRINCIPALDEFAULTMAPPED$6, 0, (short)1);
   }

   public String addNewGroupPrincipalDefaultMapped() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(GROUPPRINCIPALDEFAULTMAPPED$6);
         return target;
      }
   }

   public void unsetGroupPrincipalDefaultMapped() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(GROUPPRINCIPALDEFAULTMAPPED$6, 0);
      }
   }

   public InboundGroupPrincipalMappingType[] getGroupPrincipalMappingArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(GROUPPRINCIPALMAPPING$8, targetList);
         InboundGroupPrincipalMappingType[] result = new InboundGroupPrincipalMappingType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public InboundGroupPrincipalMappingType getGroupPrincipalMappingArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         InboundGroupPrincipalMappingType target = null;
         target = (InboundGroupPrincipalMappingType)this.get_store().find_element_user(GROUPPRINCIPALMAPPING$8, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfGroupPrincipalMappingArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(GROUPPRINCIPALMAPPING$8);
      }
   }

   public void setGroupPrincipalMappingArray(InboundGroupPrincipalMappingType[] groupPrincipalMappingArray) {
      this.check_orphaned();
      this.arraySetterHelper(groupPrincipalMappingArray, GROUPPRINCIPALMAPPING$8);
   }

   public void setGroupPrincipalMappingArray(int i, InboundGroupPrincipalMappingType groupPrincipalMapping) {
      this.generatedSetterHelperImpl(groupPrincipalMapping, GROUPPRINCIPALMAPPING$8, i, (short)2);
   }

   public InboundGroupPrincipalMappingType insertNewGroupPrincipalMapping(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         InboundGroupPrincipalMappingType target = null;
         target = (InboundGroupPrincipalMappingType)this.get_store().insert_element_user(GROUPPRINCIPALMAPPING$8, i);
         return target;
      }
   }

   public InboundGroupPrincipalMappingType addNewGroupPrincipalMapping() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         InboundGroupPrincipalMappingType target = null;
         target = (InboundGroupPrincipalMappingType)this.get_store().add_element_user(GROUPPRINCIPALMAPPING$8);
         return target;
      }
   }

   public void removeGroupPrincipalMapping(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(GROUPPRINCIPALMAPPING$8, i);
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
