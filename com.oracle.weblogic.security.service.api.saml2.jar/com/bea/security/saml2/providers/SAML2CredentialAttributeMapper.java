package com.bea.security.saml2.providers;

import java.util.Collection;
import javax.security.auth.Subject;
import weblogic.security.service.ContextHandler;

public interface SAML2CredentialAttributeMapper {
   Collection mapAttributes(Subject var1, ContextHandler var2);
}
