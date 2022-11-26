package com.bea.common.security.saml.registry;

public interface SAMLAssertingPartyRuntime extends SAMLCommonPartnerRuntime {
   String getSourceIdHex();

   byte[] getSourceIdBytes();

   boolean isTrustedSender();
}
