package com.oracle.xmlns.weblogic.weblogicEjbJar.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.oracle.xmlns.weblogic.weblogicEjbJar.ClientAuthenticationType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.IdentityAssertionType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.IiopSecurityDescriptorType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.TransportRequirementsType;
import javax.xml.namespace.QName;

public class IiopSecurityDescriptorTypeImpl extends XmlComplexContentImpl implements IiopSecurityDescriptorType {
   private static final long serialVersionUID = 1L;
   private static final QName TRANSPORTREQUIREMENTS$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "transport-requirements");
   private static final QName CLIENTAUTHENTICATION$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "client-authentication");
   private static final QName IDENTITYASSERTION$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "identity-assertion");
   private static final QName ID$6 = new QName("", "id");

   public IiopSecurityDescriptorTypeImpl(SchemaType sType) {
      super(sType);
   }

   public TransportRequirementsType getTransportRequirements() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TransportRequirementsType target = null;
         target = (TransportRequirementsType)this.get_store().find_element_user(TRANSPORTREQUIREMENTS$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetTransportRequirements() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TRANSPORTREQUIREMENTS$0) != 0;
      }
   }

   public void setTransportRequirements(TransportRequirementsType transportRequirements) {
      this.generatedSetterHelperImpl(transportRequirements, TRANSPORTREQUIREMENTS$0, 0, (short)1);
   }

   public TransportRequirementsType addNewTransportRequirements() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TransportRequirementsType target = null;
         target = (TransportRequirementsType)this.get_store().add_element_user(TRANSPORTREQUIREMENTS$0);
         return target;
      }
   }

   public void unsetTransportRequirements() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TRANSPORTREQUIREMENTS$0, 0);
      }
   }

   public ClientAuthenticationType getClientAuthentication() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ClientAuthenticationType target = null;
         target = (ClientAuthenticationType)this.get_store().find_element_user(CLIENTAUTHENTICATION$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetClientAuthentication() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CLIENTAUTHENTICATION$2) != 0;
      }
   }

   public void setClientAuthentication(ClientAuthenticationType clientAuthentication) {
      this.generatedSetterHelperImpl(clientAuthentication, CLIENTAUTHENTICATION$2, 0, (short)1);
   }

   public ClientAuthenticationType addNewClientAuthentication() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ClientAuthenticationType target = null;
         target = (ClientAuthenticationType)this.get_store().add_element_user(CLIENTAUTHENTICATION$2);
         return target;
      }
   }

   public void unsetClientAuthentication() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CLIENTAUTHENTICATION$2, 0);
      }
   }

   public IdentityAssertionType getIdentityAssertion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         IdentityAssertionType target = null;
         target = (IdentityAssertionType)this.get_store().find_element_user(IDENTITYASSERTION$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetIdentityAssertion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(IDENTITYASSERTION$4) != 0;
      }
   }

   public void setIdentityAssertion(IdentityAssertionType identityAssertion) {
      this.generatedSetterHelperImpl(identityAssertion, IDENTITYASSERTION$4, 0, (short)1);
   }

   public IdentityAssertionType addNewIdentityAssertion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         IdentityAssertionType target = null;
         target = (IdentityAssertionType)this.get_store().add_element_user(IDENTITYASSERTION$4);
         return target;
      }
   }

   public void unsetIdentityAssertion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(IDENTITYASSERTION$4, 0);
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
