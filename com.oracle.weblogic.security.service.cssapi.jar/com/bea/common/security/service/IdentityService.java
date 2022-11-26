package com.bea.common.security.service;

import javax.security.auth.Subject;

public interface IdentityService {
   Identity getIdentityFromSubject(Subject var1);

   Identity getAnonymousIdentity();

   Identity getCurrentIdentity();
}
