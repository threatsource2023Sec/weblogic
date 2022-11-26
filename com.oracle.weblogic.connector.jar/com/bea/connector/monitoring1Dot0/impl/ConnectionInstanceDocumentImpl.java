package com.bea.connector.monitoring1Dot0.impl;

import com.bea.connector.monitoring1Dot0.AuthenticationMechanismType;
import com.bea.connector.monitoring1Dot0.ConfigPropertiesType;
import com.bea.connector.monitoring1Dot0.ConnectionInstanceDocument;
import com.bea.connector.monitoring1Dot0.ConnectionPoolParamsType;
import com.bea.connector.monitoring1Dot0.LoggingType;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlString;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class ConnectionInstanceDocumentImpl extends XmlComplexContentImpl implements ConnectionInstanceDocument {
   private static final long serialVersionUID = 1L;
   private static final QName CONNECTIONINSTANCE$0 = new QName("http://www.bea.com/connector/monitoring1dot0", "connection-instance");

   public ConnectionInstanceDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public ConnectionInstanceDocument.ConnectionInstance getConnectionInstance() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConnectionInstanceDocument.ConnectionInstance target = null;
         target = (ConnectionInstanceDocument.ConnectionInstance)this.get_store().find_element_user(CONNECTIONINSTANCE$0, 0);
         return target == null ? null : target;
      }
   }

   public void setConnectionInstance(ConnectionInstanceDocument.ConnectionInstance connectionInstance) {
      this.generatedSetterHelperImpl(connectionInstance, CONNECTIONINSTANCE$0, 0, (short)1);
   }

   public ConnectionInstanceDocument.ConnectionInstance addNewConnectionInstance() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConnectionInstanceDocument.ConnectionInstance target = null;
         target = (ConnectionInstanceDocument.ConnectionInstance)this.get_store().add_element_user(CONNECTIONINSTANCE$0);
         return target;
      }
   }

   public static class ConnectionInstanceImpl extends XmlComplexContentImpl implements ConnectionInstanceDocument.ConnectionInstance {
      private static final long serialVersionUID = 1L;
      private static final QName JNDINAME$0 = new QName("http://www.bea.com/connector/monitoring1dot0", "jndi-name");
      private static final QName DESCRIPTION$2 = new QName("http://www.bea.com/connector/monitoring1dot0", "description");
      private static final QName TRANSACTIONSUPPORT$4 = new QName("http://www.bea.com/connector/monitoring1dot0", "transaction-support");
      private static final QName POOLPARAMS$6 = new QName("http://www.bea.com/connector/monitoring1dot0", "pool-params");
      private static final QName LOGGING$8 = new QName("http://www.bea.com/connector/monitoring1dot0", "logging");
      private static final QName AUTHENTICATIONMECHANISM$10 = new QName("http://www.bea.com/connector/monitoring1dot0", "authentication-mechanism");
      private static final QName REAUTHENTICATIONSUPPORT$12 = new QName("http://www.bea.com/connector/monitoring1dot0", "reauthentication-support");
      private static final QName PROPERTIES$14 = new QName("http://www.bea.com/connector/monitoring1dot0", "properties");
      private static final QName RESAUTH$16 = new QName("http://www.bea.com/connector/monitoring1dot0", "res-auth");

      public ConnectionInstanceImpl(SchemaType sType) {
         super(sType);
      }

      public String getJndiName() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(JNDINAME$0, 0);
            return target == null ? null : target.getStringValue();
         }
      }

      public XmlString xgetJndiName() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_element_user(JNDINAME$0, 0);
            return target;
         }
      }

      public void setJndiName(String jndiName) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(JNDINAME$0, 0);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_element_user(JNDINAME$0);
            }

            target.setStringValue(jndiName);
         }
      }

      public void xsetJndiName(XmlString jndiName) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_element_user(JNDINAME$0, 0);
            if (target == null) {
               target = (XmlString)this.get_store().add_element_user(JNDINAME$0);
            }

            target.set(jndiName);
         }
      }

      public String getDescription() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(DESCRIPTION$2, 0);
            return target == null ? null : target.getStringValue();
         }
      }

      public XmlString xgetDescription() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_element_user(DESCRIPTION$2, 0);
            return target;
         }
      }

      public boolean isSetDescription() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(DESCRIPTION$2) != 0;
         }
      }

      public void setDescription(String description) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(DESCRIPTION$2, 0);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_element_user(DESCRIPTION$2);
            }

            target.setStringValue(description);
         }
      }

      public void xsetDescription(XmlString description) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_element_user(DESCRIPTION$2, 0);
            if (target == null) {
               target = (XmlString)this.get_store().add_element_user(DESCRIPTION$2);
            }

            target.set(description);
         }
      }

      public void unsetDescription() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(DESCRIPTION$2, 0);
         }
      }

      public String getTransactionSupport() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(TRANSACTIONSUPPORT$4, 0);
            return target == null ? null : target.getStringValue();
         }
      }

      public XmlString xgetTransactionSupport() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_element_user(TRANSACTIONSUPPORT$4, 0);
            return target;
         }
      }

      public void setTransactionSupport(String transactionSupport) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(TRANSACTIONSUPPORT$4, 0);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_element_user(TRANSACTIONSUPPORT$4);
            }

            target.setStringValue(transactionSupport);
         }
      }

      public void xsetTransactionSupport(XmlString transactionSupport) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_element_user(TRANSACTIONSUPPORT$4, 0);
            if (target == null) {
               target = (XmlString)this.get_store().add_element_user(TRANSACTIONSUPPORT$4);
            }

            target.set(transactionSupport);
         }
      }

      public ConnectionPoolParamsType getPoolParams() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            ConnectionPoolParamsType target = null;
            target = (ConnectionPoolParamsType)this.get_store().find_element_user(POOLPARAMS$6, 0);
            return target == null ? null : target;
         }
      }

      public boolean isSetPoolParams() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(POOLPARAMS$6) != 0;
         }
      }

      public void setPoolParams(ConnectionPoolParamsType poolParams) {
         this.generatedSetterHelperImpl(poolParams, POOLPARAMS$6, 0, (short)1);
      }

      public ConnectionPoolParamsType addNewPoolParams() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            ConnectionPoolParamsType target = null;
            target = (ConnectionPoolParamsType)this.get_store().add_element_user(POOLPARAMS$6);
            return target;
         }
      }

      public void unsetPoolParams() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(POOLPARAMS$6, 0);
         }
      }

      public LoggingType getLogging() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            LoggingType target = null;
            target = (LoggingType)this.get_store().find_element_user(LOGGING$8, 0);
            return target == null ? null : target;
         }
      }

      public boolean isSetLogging() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(LOGGING$8) != 0;
         }
      }

      public void setLogging(LoggingType logging) {
         this.generatedSetterHelperImpl(logging, LOGGING$8, 0, (short)1);
      }

      public LoggingType addNewLogging() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            LoggingType target = null;
            target = (LoggingType)this.get_store().add_element_user(LOGGING$8);
            return target;
         }
      }

      public void unsetLogging() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(LOGGING$8, 0);
         }
      }

      public AuthenticationMechanismType[] getAuthenticationMechanismArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            List targetList = new ArrayList();
            this.get_store().find_all_element_users(AUTHENTICATIONMECHANISM$10, targetList);
            AuthenticationMechanismType[] result = new AuthenticationMechanismType[targetList.size()];
            targetList.toArray(result);
            return result;
         }
      }

      public AuthenticationMechanismType getAuthenticationMechanismArray(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            AuthenticationMechanismType target = null;
            target = (AuthenticationMechanismType)this.get_store().find_element_user(AUTHENTICATIONMECHANISM$10, i);
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
            return this.get_store().count_elements(AUTHENTICATIONMECHANISM$10);
         }
      }

      public void setAuthenticationMechanismArray(AuthenticationMechanismType[] authenticationMechanismArray) {
         this.check_orphaned();
         this.arraySetterHelper(authenticationMechanismArray, AUTHENTICATIONMECHANISM$10);
      }

      public void setAuthenticationMechanismArray(int i, AuthenticationMechanismType authenticationMechanism) {
         this.generatedSetterHelperImpl(authenticationMechanism, AUTHENTICATIONMECHANISM$10, i, (short)2);
      }

      public AuthenticationMechanismType insertNewAuthenticationMechanism(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            AuthenticationMechanismType target = null;
            target = (AuthenticationMechanismType)this.get_store().insert_element_user(AUTHENTICATIONMECHANISM$10, i);
            return target;
         }
      }

      public AuthenticationMechanismType addNewAuthenticationMechanism() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            AuthenticationMechanismType target = null;
            target = (AuthenticationMechanismType)this.get_store().add_element_user(AUTHENTICATIONMECHANISM$10);
            return target;
         }
      }

      public void removeAuthenticationMechanism(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(AUTHENTICATIONMECHANISM$10, i);
         }
      }

      public boolean getReauthenticationSupport() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(REAUTHENTICATIONSUPPORT$12, 0);
            return target == null ? false : target.getBooleanValue();
         }
      }

      public XmlBoolean xgetReauthenticationSupport() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlBoolean target = null;
            target = (XmlBoolean)this.get_store().find_element_user(REAUTHENTICATIONSUPPORT$12, 0);
            return target;
         }
      }

      public boolean isSetReauthenticationSupport() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(REAUTHENTICATIONSUPPORT$12) != 0;
         }
      }

      public void setReauthenticationSupport(boolean reauthenticationSupport) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(REAUTHENTICATIONSUPPORT$12, 0);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_element_user(REAUTHENTICATIONSUPPORT$12);
            }

            target.setBooleanValue(reauthenticationSupport);
         }
      }

      public void xsetReauthenticationSupport(XmlBoolean reauthenticationSupport) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlBoolean target = null;
            target = (XmlBoolean)this.get_store().find_element_user(REAUTHENTICATIONSUPPORT$12, 0);
            if (target == null) {
               target = (XmlBoolean)this.get_store().add_element_user(REAUTHENTICATIONSUPPORT$12);
            }

            target.set(reauthenticationSupport);
         }
      }

      public void unsetReauthenticationSupport() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(REAUTHENTICATIONSUPPORT$12, 0);
         }
      }

      public ConfigPropertiesType getProperties() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            ConfigPropertiesType target = null;
            target = (ConfigPropertiesType)this.get_store().find_element_user(PROPERTIES$14, 0);
            return target == null ? null : target;
         }
      }

      public boolean isSetProperties() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(PROPERTIES$14) != 0;
         }
      }

      public void setProperties(ConfigPropertiesType properties) {
         this.generatedSetterHelperImpl(properties, PROPERTIES$14, 0, (short)1);
      }

      public ConfigPropertiesType addNewProperties() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            ConfigPropertiesType target = null;
            target = (ConfigPropertiesType)this.get_store().add_element_user(PROPERTIES$14);
            return target;
         }
      }

      public void unsetProperties() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(PROPERTIES$14, 0);
         }
      }

      public String getResAuth() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(RESAUTH$16, 0);
            return target == null ? null : target.getStringValue();
         }
      }

      public XmlString xgetResAuth() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_element_user(RESAUTH$16, 0);
            return target;
         }
      }

      public boolean isSetResAuth() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(RESAUTH$16) != 0;
         }
      }

      public void setResAuth(String resAuth) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(RESAUTH$16, 0);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_element_user(RESAUTH$16);
            }

            target.setStringValue(resAuth);
         }
      }

      public void xsetResAuth(XmlString resAuth) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_element_user(RESAUTH$16, 0);
            if (target == null) {
               target = (XmlString)this.get_store().add_element_user(RESAUTH$16);
            }

            target.set(resAuth);
         }
      }

      public void unsetResAuth() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(RESAUTH$16, 0);
         }
      }
   }
}
