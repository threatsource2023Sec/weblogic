package com.bea.security.saml2.artifact;

import com.bea.security.saml2.binding.BindingHandlerException;
import org.opensaml.saml.common.SAMLObject;

public interface ArtifactResolver {
   int REQUEST_MSG = 1;
   int RESPONSE_MSG = 2;

   SAMLObject resolve(int var1, String var2) throws BindingHandlerException;
}
