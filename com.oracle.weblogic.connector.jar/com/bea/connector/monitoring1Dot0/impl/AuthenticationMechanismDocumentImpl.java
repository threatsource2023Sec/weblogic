package com.bea.connector.monitoring1Dot0.impl;

import com.bea.connector.monitoring1Dot0.AuthenticationMechanismDocument;
import com.bea.connector.monitoring1Dot0.AuthenticationMechanismType;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import javax.xml.namespace.QName;

public class AuthenticationMechanismDocumentImpl extends XmlComplexContentImpl implements AuthenticationMechanismDocument {
   private static final long serialVersionUID = 1L;
   private static final QName AUTHENTICATIONMECHANISM$0 = new QName("http://www.bea.com/connector/monitoring1dot0", "authentication-mechanism");

   public AuthenticationMechanismDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public AuthenticationMechanismType getAuthenticationMechanism() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AuthenticationMechanismType target = null;
         target = (AuthenticationMechanismType)this.get_store().find_element_user(AUTHENTICATIONMECHANISM$0, 0);
         return target == null ? null : target;
      }
   }

   public void setAuthenticationMechanism(AuthenticationMechanismType authenticationMechanism) {
      this.generatedSetterHelperImpl(authenticationMechanism, AUTHENTICATIONMECHANISM$0, 0, (short)1);
   }

   public AuthenticationMechanismType addNewAuthenticationMechanism() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AuthenticationMechanismType target = null;
         target = (AuthenticationMechanismType)this.get_store().add_element_user(AUTHENTICATIONMECHANISM$0);
         return target;
      }
   }
}
