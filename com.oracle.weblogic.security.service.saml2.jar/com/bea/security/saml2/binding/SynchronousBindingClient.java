package com.bea.security.saml2.binding;

import org.opensaml.saml.common.SAMLObject;

public interface SynchronousBindingClient {
   SAMLObject sendAndReceive(SAMLObject var1) throws BindingHandlerException;
}
