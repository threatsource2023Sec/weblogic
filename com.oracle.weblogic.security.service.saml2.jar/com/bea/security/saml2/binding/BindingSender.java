package com.bea.security.saml2.binding;

import com.bea.security.saml2.providers.registry.Endpoint;
import com.bea.security.saml2.providers.registry.WebSSOPartner;
import java.security.Key;
import org.opensaml.saml.saml2.core.RequestAbstractType;
import org.opensaml.saml.saml2.core.StatusResponseType;

public interface BindingSender {
   void sendRequest(RequestAbstractType var1, Endpoint var2, WebSSOPartner var3, String var4, Key var5) throws BindingHandlerException;

   void sendResponse(StatusResponseType var1, Endpoint var2, WebSSOPartner var3, String var4, Key var5) throws BindingHandlerException;
}
