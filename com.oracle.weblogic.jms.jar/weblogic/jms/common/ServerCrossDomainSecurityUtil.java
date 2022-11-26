package weblogic.jms.common;

import java.io.IOException;
import java.rmi.Remote;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.naming.NamingException;
import weblogic.jms.JMSLogger;
import weblogic.jms.dispatcher.JMSDispatcher;
import weblogic.messaging.dispatcher.Dispatcher;
import weblogic.messaging.dispatcher.DispatcherProxy;
import weblogic.messaging.dispatcher.DispatcherRemote;
import weblogic.messaging.dispatcher.DispatcherWrapperState;
import weblogic.messaging.dispatcher.Request;
import weblogic.protocol.ServerIdentity;
import weblogic.rmi.extensions.RemoteHelper;
import weblogic.rmi.extensions.server.RemoteDomainSecurityHelper;
import weblogic.rmi.spi.HostID;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.subject.AbstractSubject;
import weblogic.security.subject.SubjectManager;

public final class ServerCrossDomainSecurityUtil implements CrossDomainSecurityUtil {
   private final AbstractSubject anonymous = SubjectManager.getSubjectManager().getAnonymousSubject();
   private static Set crossDomainSecurityFailure = Collections.synchronizedSet(new HashSet());

   public AbstractSubject getSubjectFromListener(CDSListListener listener) throws NamingException, IOException {
      AbstractSubject subject = null;

      try {
         subject = listener.getForeignSubject();
      } catch (java.lang.IllegalStateException var4) {
      }

      if (subject == null) {
         subject = listener.getSubject();
      }

      String providerURL = listener.getProviderURL();
      if (JMSDebug.JMSCrossDomainSecurity.isDebugEnabled()) {
         JMSDebug.JMSCrossDomainSecurity.debug("getSubjectFromListener() listener's url = " + providerURL + " listener's subject = " + subject + " isLocal  = " + listener.isLocal());
      }

      if (!listener.isLocal() && providerURL != null && providerURL.trim().length() != 0) {
         if (subject != null && this.isKernelIdentity((AuthenticatedSubject)subject) && this.isRemoteDomain(listener.getProviderURL())) {
            subject = this.getRemoteSubjectFromCM(listener.getProviderURL());
         }

         if (subject == null || this.isKernelIdentity((AuthenticatedSubject)subject)) {
            subject = this.anonymous;
         }

         if (JMSDebug.JMSCrossDomainSecurity.isDebugEnabled()) {
            JMSDebug.JMSCrossDomainSecurity.debug("Final subject for URL " + providerURL + " is " + subject);
         }

         return subject;
      } else {
         if (subject == null) {
            subject = CrossDomainSecurityManager.getCurrentSubject();
         }

         if (JMSDebug.JMSCrossDomainSecurity.isDebugEnabled()) {
            JMSDebug.JMSCrossDomainSecurity.debug("getSubjectFromListener() final subject = " + subject);
         }

         return subject;
      }
   }

   public AbstractSubject getRemoteSubject(String url, AbstractSubject suggestedLocalSubject) {
      AbstractSubject subject = null;

      try {
         subject = this.getRemoteSubjectFromCM(url);
         if (subject != null) {
            return subject;
         }

         if (CrossDomainSecurityManager.getCrossDomainSecurityUtil().isRemoteDomain(url)) {
            return this.anonymous;
         }
      } catch (IOException var5) {
         return this.anonymous;
      } catch (NamingException var6) {
         return this.anonymous;
      }

      if (suggestedLocalSubject != null) {
         subject = suggestedLocalSubject;
      } else {
         subject = CrossDomainSecurityManager.getCurrentSubject();
      }

      if (subject == null || this.isKernelIdentity((AuthenticatedSubject)subject)) {
         subject = this.anonymous;
      }

      return subject;
   }

   private AbstractSubject getRemoteSubjectFromCM(String url) throws IOException, NamingException {
      return url != null && url.trim().length() != 0 ? RemoteDomainSecurityHelper.getSubject(url) : null;
   }

   public AbstractSubject getRemoteSubject(JMSDispatcher dispatcher) throws JMSException {
      return this.getRemoteSubject(dispatcher, CrossDomainSecurityManager.getCurrentSubject(), false);
   }

   public AbstractSubject getRemoteSubject(JMSDispatcher dispatcher, AbstractSubject suggestedSubject, boolean suggestedSubjectGoodForRemoteDomain) throws JMSException {
      AbstractSubject currentSubject = CrossDomainSecurityManager.getCurrentSubject();
      if (JMSDebug.JMSCrossDomainSecurity.isDebugEnabled()) {
         JMSDebug.JMSCrossDomainSecurity.debug("getRemoteSubject from dispatcher: isLocal = " + dispatcher.isLocal() + " currentSubject = " + currentSubject + " suggestedSubject = " + suggestedSubject + " dispatcher " + dispatcher + " suggestedSubjectGoodForRemoteDomain = " + suggestedSubjectGoodForRemoteDomain);
      }

      if (dispatcher.isLocal()) {
         return suggestedSubject != null ? suggestedSubject : currentSubject;
      } else {
         Dispatcher disp = dispatcher.getDelegate();
         Remote dispatcherRemote = RemoteHelper.getRemote(disp);
         if (JMSDebug.JMSCrossDomainSecurity.isDebugEnabled()) {
            JMSDebug.JMSCrossDomainSecurity.debug("Remote = " + dispatcherRemote);
         }

         if (dispatcherRemote != null && (dispatcherRemote instanceof DispatcherProxy || disp instanceof DispatcherWrapperState)) {
            AbstractSubject subject = null;
            if (dispatcherRemote instanceof DispatcherProxy) {
               if (suggestedSubject != null && (suggestedSubjectGoodForRemoteDomain || !RemoteDomainSecurityHelper.isRemoteDomain(((DispatcherProxy)dispatcherRemote).getRJVM()))) {
                  subject = suggestedSubject;
               }

               if (subject == null || this.isKernelIdentity((AuthenticatedSubject)subject)) {
                  try {
                     subject = RemoteDomainSecurityHelper.getSubject(((DispatcherProxy)dispatcherRemote).getRJVM());
                  } catch (IOException var9) {
                     throw new JMSException(var9);
                  }
               }
            } else {
               subject = suggestedSubject;
            }

            if (subject == null || this.isKernelIdentity((AuthenticatedSubject)subject)) {
               subject = this.anonymous;
            }

            if (JMSDebug.JMSCrossDomainSecurity.isDebugEnabled()) {
               JMSDebug.JMSCrossDomainSecurity.debug("final subject = " + subject);
            }

            return (AbstractSubject)subject;
         } else {
            return suggestedSubject != null ? suggestedSubject : currentSubject;
         }
      }
   }

   private void checkRole(DispatcherProxy dispatcherProxy, Request request) throws JMSException {
      if (JMSDebug.JMSCrossDomainSecurity.isDebugEnabled()) {
         int methodId = request.getMethodId();
         if (methodId == 18455 || methodId == 18711 || methodId == 18967 || methodId == 4 || methodId == 15616) {
            String str = null;
            switch (request.getMethodId()) {
               case 4:
                  str = "JMSSessionRequest:";
                  break;
               case 15616:
                  str = "JMSPushMessageRequest:";
                  break;
               case 18455:
                  str = "DDMembershipRequest:";
                  break;
               case 18711:
                  str = "DDMembershipPushRequest:";
                  break;
               case 18967:
                  str = "DDMembershipCancalRequest:";
            }

            if (JMSDebug.JMSCrossDomainSecurity.isDebugEnabled()) {
               JMSDebug.JMSCrossDomainSecurity.debug("Processing " + str + " dispatcherProxy = " + dispatcherProxy + " isCollocatd = " + request.isCollocated());
            }
         }
      }

      AbstractSubject subject = CrossDomainSecurityManager.getCurrentSubject();
      int result = RemoteDomainSecurityHelper.acceptRemoteDomainCall(dispatcherProxy.getRJVM().getHostID(), (AuthenticatedSubject)subject);
      if (JMSDebug.JMSCrossDomainSecurity.isDebugEnabled() && JMSDebug.JMSCrossDomainSecurity.isDebugEnabled()) {
         JMSDebug.JMSCrossDomainSecurity.debug("Verifying subject = " + subject + " acceptRemoteDomainCall()= " + result);
      }

      if (result == 1) {
         if (request.getMethodId() == 18711 || request.getMethodId() == 18967) {
            HostID hostID = dispatcherProxy.getRJVM().getHostID();
            String domainName = ((ServerIdentity)hostID).getDomainName();
            String destinationName = null;
            if (request.getMethodId() == 18711) {
               destinationName = ((DDMembershipPushRequest)request).getDDJndiName();
            }

            if (request.getMethodId() == 18967) {
               destinationName = ((DDMembershipCancelRequest)request).getDDJndiName();
            }

            String errMsg = new String("");
            if (domainName != null) {
               errMsg = "remote domain = " + domainName;
            }

            if (destinationName != null) {
               errMsg = errMsg + ", destination = " + destinationName;
            }

            if (!crossDomainSecurityFailure.contains(errMsg)) {
               crossDomainSecurityFailure.add(errMsg);
               JMSLogger.logCrossDomainSecurityFailureInCDS(errMsg);
            }
         }

         throw new JMSException("User <" + subject + "> does not have permission for cross-domain communication");
      }
   }

   public void checkRole(JMSDispatcher dispatcher, Request request) throws JMSException {
      DispatcherProxy dispatcherProxy = this.getDispatcherProxy(dispatcher);
      if (dispatcherProxy != null) {
         this.checkRole(dispatcherProxy, request);
      }
   }

   public void checkRole(DispatcherRemote dispatcherRemote, Request request) throws JMSException {
      if (!request.isCollocated() && dispatcherRemote instanceof DispatcherProxy) {
         this.checkRole((DispatcherProxy)dispatcherRemote, request);
      }
   }

   public boolean isRemoteDomain(String url) throws IOException {
      return RemoteDomainSecurityHelper.isRemoteDomain(url);
   }

   public boolean isRemoteDomain(JMSDispatcher dispatcher) throws IOException {
      DispatcherProxy dispatcherProxy = this.getDispatcherProxy(dispatcher);
      return dispatcherProxy == null ? false : RemoteDomainSecurityHelper.isRemoteDomain(dispatcherProxy.getRJVM());
   }

   private DispatcherProxy getDispatcherProxy(JMSDispatcher dispatcher) {
      if (dispatcher.isLocal()) {
         return null;
      } else {
         Dispatcher disp = dispatcher.getDelegate();
         Remote dispatcherRemote = RemoteHelper.getRemote(disp);
         return !(dispatcherRemote instanceof DispatcherProxy) ? null : (DispatcherProxy)dispatcherRemote;
      }
   }

   public boolean isKernelIdentity(AbstractSubject subject) {
      if (!(subject instanceof AuthenticatedSubject)) {
         return false;
      } else {
         return SecurityServiceManager.isKernelIdentity((AuthenticatedSubject)subject) || SecurityServiceManager.isServerIdentity((AuthenticatedSubject)subject);
      }
   }

   public boolean ifRemoteSubjectExists(String url) {
      AuthenticatedSubject sub = null;

      try {
         if (url != null && url.trim().length() > 0) {
            sub = RemoteDomainSecurityHelper.getSubject(url);
         }
      } catch (Exception var4) {
      }

      return sub != null;
   }
}
