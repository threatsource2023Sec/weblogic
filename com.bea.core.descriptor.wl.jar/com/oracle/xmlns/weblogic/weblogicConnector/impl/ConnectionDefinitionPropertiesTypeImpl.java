package com.oracle.xmlns.weblogic.weblogicConnector.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.oracle.xmlns.weblogic.weblogicConnector.ConfigPropertiesType;
import com.oracle.xmlns.weblogic.weblogicConnector.ConnectionDefinitionPropertiesType;
import com.oracle.xmlns.weblogic.weblogicConnector.LoggingType;
import com.oracle.xmlns.weblogic.weblogicConnector.PoolParamsType;
import com.oracle.xmlns.weblogic.weblogicConnector.TrueFalseType;
import com.sun.java.xml.ns.javaee.AuthenticationMechanismType;
import com.sun.java.xml.ns.javaee.ResAuthType;
import com.sun.java.xml.ns.javaee.TransactionSupportType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class ConnectionDefinitionPropertiesTypeImpl extends XmlComplexContentImpl implements ConnectionDefinitionPropertiesType {
   private static final long serialVersionUID = 1L;
   private static final QName POOLPARAMS$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "pool-params");
   private static final QName LOGGING$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "logging");
   private static final QName TRANSACTIONSUPPORT$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "transaction-support");
   private static final QName AUTHENTICATIONMECHANISM$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "authentication-mechanism");
   private static final QName REAUTHENTICATIONSUPPORT$8 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "reauthentication-support");
   private static final QName PROPERTIES$10 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "properties");
   private static final QName RESAUTH$12 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "res-auth");
   private static final QName ID$14 = new QName("", "id");

   public ConnectionDefinitionPropertiesTypeImpl(SchemaType sType) {
      super(sType);
   }

   public PoolParamsType getPoolParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PoolParamsType target = null;
         target = (PoolParamsType)this.get_store().find_element_user(POOLPARAMS$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetPoolParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(POOLPARAMS$0) != 0;
      }
   }

   public void setPoolParams(PoolParamsType poolParams) {
      this.generatedSetterHelperImpl(poolParams, POOLPARAMS$0, 0, (short)1);
   }

   public PoolParamsType addNewPoolParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PoolParamsType target = null;
         target = (PoolParamsType)this.get_store().add_element_user(POOLPARAMS$0);
         return target;
      }
   }

   public void unsetPoolParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(POOLPARAMS$0, 0);
      }
   }

   public LoggingType getLogging() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LoggingType target = null;
         target = (LoggingType)this.get_store().find_element_user(LOGGING$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetLogging() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LOGGING$2) != 0;
      }
   }

   public void setLogging(LoggingType logging) {
      this.generatedSetterHelperImpl(logging, LOGGING$2, 0, (short)1);
   }

   public LoggingType addNewLogging() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LoggingType target = null;
         target = (LoggingType)this.get_store().add_element_user(LOGGING$2);
         return target;
      }
   }

   public void unsetLogging() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LOGGING$2, 0);
      }
   }

   public TransactionSupportType getTransactionSupport() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TransactionSupportType target = null;
         target = (TransactionSupportType)this.get_store().find_element_user(TRANSACTIONSUPPORT$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetTransactionSupport() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TRANSACTIONSUPPORT$4) != 0;
      }
   }

   public void setTransactionSupport(TransactionSupportType transactionSupport) {
      this.generatedSetterHelperImpl(transactionSupport, TRANSACTIONSUPPORT$4, 0, (short)1);
   }

   public TransactionSupportType addNewTransactionSupport() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TransactionSupportType target = null;
         target = (TransactionSupportType)this.get_store().add_element_user(TRANSACTIONSUPPORT$4);
         return target;
      }
   }

   public void unsetTransactionSupport() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TRANSACTIONSUPPORT$4, 0);
      }
   }

   public AuthenticationMechanismType[] getAuthenticationMechanismArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(AUTHENTICATIONMECHANISM$6, targetList);
         AuthenticationMechanismType[] result = new AuthenticationMechanismType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public AuthenticationMechanismType getAuthenticationMechanismArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AuthenticationMechanismType target = null;
         target = (AuthenticationMechanismType)this.get_store().find_element_user(AUTHENTICATIONMECHANISM$6, i);
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
         return this.get_store().count_elements(AUTHENTICATIONMECHANISM$6);
      }
   }

   public void setAuthenticationMechanismArray(AuthenticationMechanismType[] authenticationMechanismArray) {
      this.check_orphaned();
      this.arraySetterHelper(authenticationMechanismArray, AUTHENTICATIONMECHANISM$6);
   }

   public void setAuthenticationMechanismArray(int i, AuthenticationMechanismType authenticationMechanism) {
      this.generatedSetterHelperImpl(authenticationMechanism, AUTHENTICATIONMECHANISM$6, i, (short)2);
   }

   public AuthenticationMechanismType insertNewAuthenticationMechanism(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AuthenticationMechanismType target = null;
         target = (AuthenticationMechanismType)this.get_store().insert_element_user(AUTHENTICATIONMECHANISM$6, i);
         return target;
      }
   }

   public AuthenticationMechanismType addNewAuthenticationMechanism() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AuthenticationMechanismType target = null;
         target = (AuthenticationMechanismType)this.get_store().add_element_user(AUTHENTICATIONMECHANISM$6);
         return target;
      }
   }

   public void removeAuthenticationMechanism(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(AUTHENTICATIONMECHANISM$6, i);
      }
   }

   public TrueFalseType getReauthenticationSupport() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(REAUTHENTICATIONSUPPORT$8, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetReauthenticationSupport() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(REAUTHENTICATIONSUPPORT$8) != 0;
      }
   }

   public void setReauthenticationSupport(TrueFalseType reauthenticationSupport) {
      this.generatedSetterHelperImpl(reauthenticationSupport, REAUTHENTICATIONSUPPORT$8, 0, (short)1);
   }

   public TrueFalseType addNewReauthenticationSupport() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(REAUTHENTICATIONSUPPORT$8);
         return target;
      }
   }

   public void unsetReauthenticationSupport() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(REAUTHENTICATIONSUPPORT$8, 0);
      }
   }

   public ConfigPropertiesType getProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConfigPropertiesType target = null;
         target = (ConfigPropertiesType)this.get_store().find_element_user(PROPERTIES$10, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PROPERTIES$10) != 0;
      }
   }

   public void setProperties(ConfigPropertiesType properties) {
      this.generatedSetterHelperImpl(properties, PROPERTIES$10, 0, (short)1);
   }

   public ConfigPropertiesType addNewProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConfigPropertiesType target = null;
         target = (ConfigPropertiesType)this.get_store().add_element_user(PROPERTIES$10);
         return target;
      }
   }

   public void unsetProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PROPERTIES$10, 0);
      }
   }

   public ResAuthType getResAuth() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResAuthType target = null;
         target = (ResAuthType)this.get_store().find_element_user(RESAUTH$12, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetResAuth() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(RESAUTH$12) != 0;
      }
   }

   public void setResAuth(ResAuthType resAuth) {
      this.generatedSetterHelperImpl(resAuth, RESAUTH$12, 0, (short)1);
   }

   public ResAuthType addNewResAuth() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResAuthType target = null;
         target = (ResAuthType)this.get_store().add_element_user(RESAUTH$12);
         return target;
      }
   }

   public void unsetResAuth() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RESAUTH$12, 0);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$14);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$14);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$14) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$14);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$14);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$14);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$14);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$14);
      }
   }
}
