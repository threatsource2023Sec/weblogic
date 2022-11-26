package com.bea.security.saml2.providers.registry;

import java.io.Serializable;

public class EndpointImpl implements Endpoint, Serializable {
   private String binding;
   private String location;

   public String getBinding() {
      return this.binding;
   }

   public void setBinding(String binding) {
      String convertedBinding = convertFromBindingURN(binding);
      if (convertedBinding != null) {
         binding = convertedBinding;
      }

      this.binding = binding;
   }

   public String getLocation() {
      return this.location;
   }

   public void setLocation(String location) {
      this.location = location;
   }

   private static String convertFromBindingURN(String protocolBinding) {
      if (protocolBinding == null) {
         return null;
      } else {
         String convertedBinding = null;
         if ("urn:oasis:names:tc:SAML:2.0:bindings:HTTP-Artifact".equals(protocolBinding)) {
            convertedBinding = "HTTP/Artifact";
         } else if ("urn:oasis:names:tc:SAML:2.0:bindings:HTTP-Redirect".equals(protocolBinding)) {
            convertedBinding = "HTTP/Redirect";
         } else if ("urn:oasis:names:tc:SAML:2.0:bindings:HTTP-POST".equals(protocolBinding)) {
            convertedBinding = "HTTP/POST";
         } else if ("urn:oasis:names:tc:SAML:2.0:bindings:SOAP".equals(protocolBinding)) {
            convertedBinding = "SOAP";
         }

         return convertedBinding;
      }
   }
}
