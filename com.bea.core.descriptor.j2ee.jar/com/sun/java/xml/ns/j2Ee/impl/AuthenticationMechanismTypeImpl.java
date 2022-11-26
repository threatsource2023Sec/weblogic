package com.sun.java.xml.ns.j2Ee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.sun.java.xml.ns.j2Ee.AuthenticationMechanismType;
import com.sun.java.xml.ns.j2Ee.CredentialInterfaceType;
import com.sun.java.xml.ns.j2Ee.DescriptionType;
import com.sun.java.xml.ns.j2Ee.XsdStringType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class AuthenticationMechanismTypeImpl extends XmlComplexContentImpl implements AuthenticationMechanismType {
   private static final long serialVersionUID = 1L;
   private static final QName DESCRIPTION$0 = new QName("http://java.sun.com/xml/ns/j2ee", "description");
   private static final QName AUTHENTICATIONMECHANISMTYPE$2 = new QName("http://java.sun.com/xml/ns/j2ee", "authentication-mechanism-type");
   private static final QName CREDENTIALINTERFACE$4 = new QName("http://java.sun.com/xml/ns/j2ee", "credential-interface");
   private static final QName ID$6 = new QName("", "id");

   public AuthenticationMechanismTypeImpl(SchemaType sType) {
      super(sType);
   }

   public DescriptionType[] getDescriptionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(DESCRIPTION$0, targetList);
         DescriptionType[] result = new DescriptionType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public DescriptionType getDescriptionArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DescriptionType target = null;
         target = (DescriptionType)this.get_store().find_element_user(DESCRIPTION$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfDescriptionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DESCRIPTION$0);
      }
   }

   public void setDescriptionArray(DescriptionType[] descriptionArray) {
      this.check_orphaned();
      this.arraySetterHelper(descriptionArray, DESCRIPTION$0);
   }

   public void setDescriptionArray(int i, DescriptionType description) {
      this.generatedSetterHelperImpl(description, DESCRIPTION$0, i, (short)2);
   }

   public DescriptionType insertNewDescription(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DescriptionType target = null;
         target = (DescriptionType)this.get_store().insert_element_user(DESCRIPTION$0, i);
         return target;
      }
   }

   public DescriptionType addNewDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DescriptionType target = null;
         target = (DescriptionType)this.get_store().add_element_user(DESCRIPTION$0);
         return target;
      }
   }

   public void removeDescription(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DESCRIPTION$0, i);
      }
   }

   public XsdStringType getAuthenticationMechanismType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().find_element_user(AUTHENTICATIONMECHANISMTYPE$2, 0);
         return target == null ? null : target;
      }
   }

   public void setAuthenticationMechanismType(XsdStringType authenticationMechanismType) {
      this.generatedSetterHelperImpl(authenticationMechanismType, AUTHENTICATIONMECHANISMTYPE$2, 0, (short)1);
   }

   public XsdStringType addNewAuthenticationMechanismType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().add_element_user(AUTHENTICATIONMECHANISMTYPE$2);
         return target;
      }
   }

   public CredentialInterfaceType getCredentialInterface() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CredentialInterfaceType target = null;
         target = (CredentialInterfaceType)this.get_store().find_element_user(CREDENTIALINTERFACE$4, 0);
         return target == null ? null : target;
      }
   }

   public void setCredentialInterface(CredentialInterfaceType credentialInterface) {
      this.generatedSetterHelperImpl(credentialInterface, CREDENTIALINTERFACE$4, 0, (short)1);
   }

   public CredentialInterfaceType addNewCredentialInterface() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CredentialInterfaceType target = null;
         target = (CredentialInterfaceType)this.get_store().add_element_user(CREDENTIALINTERFACE$4);
         return target;
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$6);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$6);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$6) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$6);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$6);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$6);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$6);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$6);
      }
   }
}
