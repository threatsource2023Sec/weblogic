package com.bea.core.security.managers;

import java.util.Map;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.ContextHandler;
import weblogic.security.spi.AuditEvent;
import weblogic.security.spi.Direction;
import weblogic.security.spi.Resource;

public interface Manager {
   AuthenticatedSubject getCurrentIdentity();

   boolean isAccessAllowed(AuthenticatedSubject var1, Map var2, Resource var3, ContextHandler var4, Direction var5);

   void writeEvent(AuditEvent var1);

   Map getRoles(AuthenticatedSubject var1, Resource var2, ContextHandler var3);

   AuthenticatedSubject authenticate(CallbackHandler var1, ContextHandler var2) throws LoginException;

   Object[] getCredentials(AuthenticatedSubject var1, AuthenticatedSubject var2, Resource var3, ContextHandler var4, String var5);

   Object[] getCredentials(AuthenticatedSubject var1, String var2, Resource var3, ContextHandler var4, String var5);
}
