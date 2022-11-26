package org.jcp.xmlns.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import org.jcp.xmlns.xml.ns.javaee.AuthenticationMechanismType;
import org.jcp.xmlns.xml.ns.javaee.ConnectionDefinitionType;
import org.jcp.xmlns.xml.ns.javaee.OutboundResourceadapterType;
import org.jcp.xmlns.xml.ns.javaee.TransactionSupportType;
import org.jcp.xmlns.xml.ns.javaee.TrueFalseType;

public class OutboundResourceadapterTypeImpl extends XmlComplexContentImpl implements OutboundResourceadapterType {
   private static final long serialVersionUID = 1L;
   private static final QName CONNECTIONDEFINITION$0 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "connection-definition");
   private static final QName TRANSACTIONSUPPORT$2 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "transaction-support");
   private static final QName AUTHENTICATIONMECHANISM$4 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "authentication-mechanism");
   private static final QName REAUTHENTICATIONSUPPORT$6 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "reauthentication-support");
   private static final QName ID$8 = new QName("", "id");

   public OutboundResourceadapterTypeImpl(SchemaType sType) {
      super(sType);
   }

   public ConnectionDefinitionType[] getConnectionDefinitionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(CONNECTIONDEFINITION$0, targetList);
         ConnectionDefinitionType[] result = new ConnectionDefinitionType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ConnectionDefinitionType getConnectionDefinitionArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConnectionDefinitionType target = null;
         target = (ConnectionDefinitionType)this.get_store().find_element_user(CONNECTIONDEFINITION$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfConnectionDefinitionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONNECTIONDEFINITION$0);
      }
   }

   public void setConnectionDefinitionArray(ConnectionDefinitionType[] connectionDefinitionArray) {
      this.check_orphaned();
      this.arraySetterHelper(connectionDefinitionArray, CONNECTIONDEFINITION$0);
   }

   public void setConnectionDefinitionArray(int i, ConnectionDefinitionType connectionDefinition) {
      this.generatedSetterHelperImpl(connectionDefinition, CONNECTIONDEFINITION$0, i, (short)2);
   }

   public ConnectionDefinitionType insertNewConnectionDefinition(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConnectionDefinitionType target = null;
         target = (ConnectionDefinitionType)this.get_store().insert_element_user(CONNECTIONDEFINITION$0, i);
         return target;
      }
   }

   public ConnectionDefinitionType addNewConnectionDefinition() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConnectionDefinitionType target = null;
         target = (ConnectionDefinitionType)this.get_store().add_element_user(CONNECTIONDEFINITION$0);
         return target;
      }
   }

   public void removeConnectionDefinition(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONNECTIONDEFINITION$0, i);
      }
   }

   public TransactionSupportType getTransactionSupport() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TransactionSupportType target = null;
         target = (TransactionSupportType)this.get_store().find_element_user(TRANSACTIONSUPPORT$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetTransactionSupport() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TRANSACTIONSUPPORT$2) != 0;
      }
   }

   public void setTransactionSupport(TransactionSupportType transactionSupport) {
      this.generatedSetterHelperImpl(transactionSupport, TRANSACTIONSUPPORT$2, 0, (short)1);
   }

   public TransactionSupportType addNewTransactionSupport() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TransactionSupportType target = null;
         target = (TransactionSupportType)this.get_store().add_element_user(TRANSACTIONSUPPORT$2);
         return target;
      }
   }

   public void unsetTransactionSupport() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TRANSACTIONSUPPORT$2, 0);
      }
   }

   public AuthenticationMechanismType[] getAuthenticationMechanismArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(AUTHENTICATIONMECHANISM$4, targetList);
         AuthenticationMechanismType[] result = new AuthenticationMechanismType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public AuthenticationMechanismType getAuthenticationMechanismArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AuthenticationMechanismType target = null;
         target = (AuthenticationMechanismType)this.get_store().find_element_user(AUTHENTICATIONMECHANISM$4, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfAuthenticationMechanismArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(AUTHENTICATIONMECHANISM$4);
      }
   }

   public void setAuthenticationMechanismArray(AuthenticationMechanismType[] authenticationMechanismArray) {
      this.check_orphaned();
      this.arraySetterHelper(authenticationMechanismArray, AUTHENTICATIONMECHANISM$4);
   }

   public void setAuthenticationMechanismArray(int i, AuthenticationMechanismType authenticationMechanism) {
      this.generatedSetterHelperImpl(authenticationMechanism, AUTHENTICATIONMECHANISM$4, i, (short)2);
   }

   public AuthenticationMechanismType insertNewAuthenticationMechanism(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AuthenticationMechanismType target = null;
         target = (AuthenticationMechanismType)this.get_store().insert_element_user(AUTHENTICATIONMECHANISM$4, i);
         return target;
      }
   }

   public AuthenticationMechanismType addNewAuthenticationMechanism() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AuthenticationMechanismType target = null;
         target = (AuthenticationMechanismType)this.get_store().add_element_user(AUTHENTICATIONMECHANISM$4);
         return target;
      }
   }

   public void removeAuthenticationMechanism(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(AUTHENTICATIONMECHANISM$4, i);
      }
   }

   public TrueFalseType getReauthenticationSupport() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(REAUTHENTICATIONSUPPORT$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetReauthenticationSupport() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(REAUTHENTICATIONSUPPORT$6) != 0;
      }
   }

   public void setReauthenticationSupport(TrueFalseType reauthenticationSupport) {
      this.generatedSetterHelperImpl(reauthenticationSupport, REAUTHENTICATIONSUPPORT$6, 0, (short)1);
   }

   public TrueFalseType addNewReauthenticationSupport() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(REAUTHENTICATIONSUPPORT$6);
         return target;
      }
   }

   public void unsetReauthenticationSupport() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(REAUTHENTICATIONSUPPORT$6, 0);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$8);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$8);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$8) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$8);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$8);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$8);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$8);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$8);
      }
   }
}
