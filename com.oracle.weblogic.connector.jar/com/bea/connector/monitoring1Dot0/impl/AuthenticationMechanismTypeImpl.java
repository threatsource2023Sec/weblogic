package com.bea.connector.monitoring1Dot0.impl;

import com.bea.connector.monitoring1Dot0.AuthenticationMechanismType;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class AuthenticationMechanismTypeImpl extends XmlComplexContentImpl implements AuthenticationMechanismType {
   private static final long serialVersionUID = 1L;
   private static final QName DESCRIPTION$0 = new QName("http://www.bea.com/connector/monitoring1dot0", "description");
   private static final QName AUTHENTICATIONMECHANISMTYPE$2 = new QName("http://www.bea.com/connector/monitoring1dot0", "authentication-mechanism-type");
   private static final QName CREDENTIALINTERFACE$4 = new QName("http://www.bea.com/connector/monitoring1dot0", "credential-interface");

   public AuthenticationMechanismTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String[] getDescriptionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(DESCRIPTION$0, targetList);
         String[] result = new String[targetList.size()];
         int i = 0;

         for(int len = targetList.size(); i < len; ++i) {
            result[i] = ((SimpleValue)targetList.get(i)).getStringValue();
         }

         return result;
      }
   }

   public String getDescriptionArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DESCRIPTION$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target.getStringValue();
         }
      }
   }

   public XmlString[] xgetDescriptionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(DESCRIPTION$0, targetList);
         XmlString[] result = new XmlString[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public XmlString xgetDescriptionArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DESCRIPTION$0, i);
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

   public void setDescriptionArray(String[] descriptionArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(descriptionArray, DESCRIPTION$0);
      }
   }

   public void setDescriptionArray(int i, String description) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DESCRIPTION$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.setStringValue(description);
         }
      }
   }

   public void xsetDescriptionArray(XmlString[] descriptionArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(descriptionArray, DESCRIPTION$0);
      }
   }

   public void xsetDescriptionArray(int i, XmlString description) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DESCRIPTION$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.set(description);
         }
      }
   }

   public void insertDescription(int i, String description) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = (SimpleValue)this.get_store().insert_element_user(DESCRIPTION$0, i);
         target.setStringValue(description);
      }
   }

   public void addDescription(String description) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().add_element_user(DESCRIPTION$0);
         target.setStringValue(description);
      }
   }

   public XmlString insertNewDescription(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().insert_element_user(DESCRIPTION$0, i);
         return target;
      }
   }

   public XmlString addNewDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().add_element_user(DESCRIPTION$0);
         return target;
      }
   }

   public void removeDescription(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DESCRIPTION$0, i);
      }
   }

   public String getAuthenticationMechanismType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(AUTHENTICATIONMECHANISMTYPE$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetAuthenticationMechanismType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(AUTHENTICATIONMECHANISMTYPE$2, 0);
         return target;
      }
   }

   public void setAuthenticationMechanismType(String authenticationMechanismType) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(AUTHENTICATIONMECHANISMTYPE$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(AUTHENTICATIONMECHANISMTYPE$2);
         }

         target.setStringValue(authenticationMechanismType);
      }
   }

   public void xsetAuthenticationMechanismType(XmlString authenticationMechanismType) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(AUTHENTICATIONMECHANISMTYPE$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(AUTHENTICATIONMECHANISMTYPE$2);
         }

         target.set(authenticationMechanismType);
      }
   }

   public String getCredentialInterface() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CREDENTIALINTERFACE$4, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetCredentialInterface() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CREDENTIALINTERFACE$4, 0);
         return target;
      }
   }

   public void setCredentialInterface(String credentialInterface) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CREDENTIALINTERFACE$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CREDENTIALINTERFACE$4);
         }

         target.setStringValue(credentialInterface);
      }
   }

   public void xsetCredentialInterface(XmlString credentialInterface) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CREDENTIALINTERFACE$4, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CREDENTIALINTERFACE$4);
         }

         target.set(credentialInterface);
      }
   }
}
