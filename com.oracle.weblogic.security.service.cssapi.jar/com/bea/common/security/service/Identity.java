package com.bea.common.security.service;

import javax.security.auth.Subject;
import weblogic.security.auth.callback.IdentityDomainNames;

public interface Identity {
   Subject getSubject();

   boolean isAnonymous();

   String getUsername();

   IdentityDomainNames getUser();
}
