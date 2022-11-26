package com.bea.common.security.internal.service;

import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.engine.ServiceLifecycleSpi;
import com.bea.common.engine.Services;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.internal.utils.Delegator;
import com.bea.common.security.service.Identity;
import com.bea.common.security.service.LoginSession;
import com.bea.common.security.service.LoginSessionListener;
import com.bea.common.security.service.LoginSessionService;
import java.io.Serializable;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import javax.security.auth.Subject;
import weblogic.security.auth.callback.IdentityDomainNames;

public class LoginSessionServiceImpl implements ServiceLifecycleSpi, LoginSessionService {
   private LoggerSpi logger;
   private List ls;

   public Object init(Object config, Services services) throws ServiceInitializationException {
      this.logger = ((LoggerService)services.getService(LoggerService.SERVICE_NAME)).getLogger("com.bea.common.security.service.LoginSessionService");
      this.ls = new ArrayList();
      String method = this.getClass().getName() + ".init()";
      if (this.logger.isDebugEnabled()) {
         this.logger.debug(method);
      }

      return Delegator.getProxy(LoginSessionService.class, this);
   }

   public void shutdown() {
      if (this.logger.isDebugEnabled()) {
         this.logger.debug(this.getClass().getName() + ".shutdown()");
      }

   }

   public LoginSession create(Identity identity, Date authenticationTime, Object context) {
      if (authenticationTime == null) {
         authenticationTime = new Date();
      }

      if (this.logger.isDebugEnabled()) {
         this.logger.debug(this.getClass().getName() + ".create(" + identity + ", " + DateFormat.getDateTimeInstance(3, 3).format(authenticationTime) + ")");
      }

      LoginSession session = new LoginSessionImpl(identity, authenticationTime);
      List listeners;
      synchronized(this.ls) {
         listeners = this.ls;
      }

      ListIterator it = listeners.listIterator();

      do {
         if (!it.hasNext()) {
            return session;
         }
      } while(((LoginSessionListener)it.next()).sessionCreated(session, context));

      while(it.hasPrevious()) {
         ((LoginSessionListener)it.previous()).sessionTerminated(session, 3);
      }

      return null;
   }

   public void terminate(LoginSession session) {
      if (this.logger.isDebugEnabled()) {
         this.logger.debug(this.getClass().getName() + ".terminate(" + session.getIdentity() + ")");
      }

      this.endSession(session, 2);
   }

   private void endSession(LoginSession session, int reason) {
      List listeners;
      synchronized(this.ls) {
         listeners = this.ls;
      }

      ListIterator it = listeners.listIterator(listeners.size());

      while(it.hasPrevious()) {
         ((LoginSessionListener)it.previous()).sessionTerminated(session, reason);
      }

   }

   public void terminate(String sessionId) {
      throw new UnsupportedOperationException();
   }

   public void logout(LoginSession session) {
      if (this.logger.isDebugEnabled()) {
         this.logger.debug(this.getClass().getName() + ".logout(" + session.getIdentity() + ")");
      }

      this.endSession(session, 0);
   }

   public void logout(String sessionId) {
      throw new UnsupportedOperationException();
   }

   public LoginSession getSession(Identity identity) {
      return identity instanceof IdentityWrapperImpl ? ((IdentityWrapperImpl)identity).getSession() : null;
   }

   public LoginSession getSession(String sessionId) {
      throw new UnsupportedOperationException();
   }

   public void addListener(LoginSessionListener listener) {
      synchronized(this.ls) {
         if (!this.ls.contains(listener)) {
            List nl = new ArrayList(this.ls);
            nl.add(listener);
            this.ls = nl;
         }

      }
   }

   public void removeListener(LoginSessionListener listener) {
      synchronized(this.ls) {
         if (this.ls.contains(listener)) {
            List nl = new ArrayList(this.ls);
            nl.remove(listener);
            this.ls = nl;
         }

      }
   }

   private class LoginSessionImpl implements LoginSession {
      private Identity identity;
      private Date authenticationTime;

      public LoginSessionImpl(Identity identity, Date authenticationTime) {
         if (identity != null) {
            this.identity = new IdentityWrapperImpl(identity, this);
         }

         this.authenticationTime = authenticationTime;
      }

      public Identity getIdentity() {
         return this.identity;
      }

      public String getId() {
         throw new UnsupportedOperationException();
      }

      public Date getAuthenticationTime() {
         return this.authenticationTime;
      }
   }

   private static class IdentityWrapperImpl implements Identity, Serializable {
      private static final long serialVersionUID = 3060133472843343604L;
      private Identity inner;
      private LoginSession session;

      public IdentityWrapperImpl(Identity inner, LoginSession session) {
         this.inner = inner;
         this.session = session;
      }

      public Subject getSubject() {
         return this.inner.getSubject();
      }

      public boolean isAnonymous() {
         return this.inner.isAnonymous();
      }

      public String getUsername() {
         return this.inner.getUsername();
      }

      public LoginSession getSession() {
         return this.session;
      }

      public IdentityDomainNames getUser() {
         return this.inner.getUser();
      }
   }
}
