package com.bea.security.saml2.servlet;

public class SAML2NoAuthCheckFilter extends SAML2Filter {
   public boolean isUserAuthenticated() {
      return false;
   }
}
