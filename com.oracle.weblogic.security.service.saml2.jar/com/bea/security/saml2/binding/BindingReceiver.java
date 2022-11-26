package com.bea.security.saml2.binding;

import java.security.PublicKey;
import org.opensaml.saml.saml2.core.RequestAbstractType;
import org.opensaml.saml.saml2.core.StatusResponseType;

public interface BindingReceiver {
   RequestAbstractType receiveRequest() throws BindingHandlerException;

   StatusResponseType receiveResponse() throws BindingHandlerException;

   boolean verifySignature(PublicKey var1) throws BindingHandlerException;

   String getRelayState();
}
