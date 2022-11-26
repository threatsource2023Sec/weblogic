package com.oracle.xmlns.weblogic.weblogicEjbJar.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.oracle.xmlns.weblogic.weblogicEjbJar.ClientCertAuthenticationType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.ConfidentialityType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.IntegrityType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.TransportRequirementsType;
import javax.xml.namespace.QName;

public class TransportRequirementsTypeImpl extends XmlComplexContentImpl implements TransportRequirementsType {
   private static final long serialVersionUID = 1L;
   private static final QName INTEGRITY$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "integrity");
   private static final QName CONFIDENTIALITY$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "confidentiality");
   private static final QName CLIENTCERTAUTHENTICATION$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "client-cert-authentication");
   private static final QName ID$6 = new QName("", "id");

   public TransportRequirementsTypeImpl(SchemaType sType) {
      super(sType);
   }

   public IntegrityType getIntegrity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         IntegrityType target = null;
         target = (IntegrityType)this.get_store().find_element_user(INTEGRITY$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetIntegrity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(INTEGRITY$0) != 0;
      }
   }

   public void setIntegrity(IntegrityType integrity) {
      this.generatedSetterHelperImpl(integrity, INTEGRITY$0, 0, (short)1);
   }

   public IntegrityType addNewIntegrity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         IntegrityType target = null;
         target = (IntegrityType)this.get_store().add_element_user(INTEGRITY$0);
         return target;
      }
   }

   public void unsetIntegrity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(INTEGRITY$0, 0);
      }
   }

   public ConfidentialityType getConfidentiality() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConfidentialityType target = null;
         target = (ConfidentialityType)this.get_store().find_element_user(CONFIDENTIALITY$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetConfidentiality() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONFIDENTIALITY$2) != 0;
      }
   }

   public void setConfidentiality(ConfidentialityType confidentiality) {
      this.generatedSetterHelperImpl(confidentiality, CONFIDENTIALITY$2, 0, (short)1);
   }

   public ConfidentialityType addNewConfidentiality() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConfidentialityType target = null;
         target = (ConfidentialityType)this.get_store().add_element_user(CONFIDENTIALITY$2);
         return target;
      }
   }

   public void unsetConfidentiality() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONFIDENTIALITY$2, 0);
      }
   }

   public ClientCertAuthenticationType getClientCertAuthentication() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ClientCertAuthenticationType target = null;
         target = (ClientCertAuthenticationType)this.get_store().find_element_user(CLIENTCERTAUTHENTICATION$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetClientCertAuthentication() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CLIENTCERTAUTHENTICATION$4) != 0;
      }
   }

   public void setClientCertAuthentication(ClientCertAuthenticationType clientCertAuthentication) {
      this.generatedSetterHelperImpl(clientCertAuthentication, CLIENTCERTAUTHENTICATION$4, 0, (short)1);
   }

   public ClientCertAuthenticationType addNewClientCertAuthentication() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ClientCertAuthenticationType target = null;
         target = (ClientCertAuthenticationType)this.get_store().add_element_user(CLIENTCERTAUTHENTICATION$4);
         return target;
      }
   }

   public void unsetClientCertAuthentication() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CLIENTCERTAUTHENTICATION$4, 0);
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
