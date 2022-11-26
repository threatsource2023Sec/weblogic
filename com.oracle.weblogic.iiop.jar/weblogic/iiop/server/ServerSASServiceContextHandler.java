package weblogic.iiop.server;

import java.rmi.RemoteException;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import javax.security.auth.login.LoginException;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.MARSHAL;
import weblogic.corba.cos.security.GSSUtil;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.iiop.IIOPLogger;
import weblogic.iiop.contexts.ContextError;
import weblogic.iiop.contexts.EstablishContext;
import weblogic.iiop.contexts.GSSUPDecodeException;
import weblogic.iiop.contexts.GSSUPImpl;
import weblogic.iiop.contexts.IdentityToken;
import weblogic.iiop.contexts.MessageInContext;
import weblogic.iiop.contexts.SASServiceContext;
import weblogic.iiop.contexts.ServiceContextList;
import weblogic.iiop.messages.ReplyMessage;
import weblogic.iiop.messages.RequestMessage;
import weblogic.iiop.protocol.CorbaOutputStream;
import weblogic.protocol.configuration.ChannelHelper;
import weblogic.rmi.facades.RmiSecurityFacade;
import weblogic.security.SimpleCallbackHandler;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.auth.login.PasswordCredential;
import weblogic.security.service.InvalidParameterException;
import weblogic.security.service.PrincipalAuthenticator;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.utils.Debug;
import weblogic.utils.DebugCategory;
import weblogic.utils.Hex;

public class ServerSASServiceContextHandler {
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final DebugCategory debugSecurity = Debug.getCategory("weblogic.iiop.security");
   private static final DebugLogger debugIIOPSecurity = DebugLogger.getDebugLogger("DebugIIOPSecurity");
   private final ServerEndPoint endPoint;
   public AuthenticatedSubject subject;

   public ServerSASServiceContextHandler(ServerEndPoint endPoint) {
      this.endPoint = endPoint;
   }

   public boolean handleSASRequest(RequestMessage req, SASServiceContext serviceContext) {
      boolean failureReplySent = false;
      ContextError contextError = null;
      switch (serviceContext.getMsgType()) {
         case 0:
            if (!"_non_existent".equals(req.getOperationName())) {
               contextError = this.handleEstablishContext(req, this.endPoint, (EstablishContext)serviceContext.getBody());
               if (contextError == null) {
                  serviceContext.setSubject(this.subject);
               }
            }
            break;
         case 1:
         case 2:
         case 3:
         case 4:
         default:
            throw new MARSHAL("Unsupported Request CSI MsgType.");
         case 5:
            contextError = this.handleMessageInContext(this.endPoint, (MessageInContext)serviceContext.getBody());
            if (contextError == null) {
               serviceContext.setSubject(this.subject);
            }
      }

      if (contextError != null) {
         SASServiceContext errorCtx = new SASServiceContext(contextError);
         ServiceContextList ctxList = new ServiceContextList();
         ctxList.addServiceContext(errorCtx);
         ReplyMessage reply = new ReplyMessage(req, ctxList, 2);
         CorbaOutputStream os = reply.marshalTo(this.endPoint.createOutputStream());
         os.write_string("IDL:omg.org/CORBA/NO_PERMISSION:1.0");
         os.write_long(0);
         os.write_long(CompletionStatus.COMPLETED_NO.value());

         try {
            this.endPoint.send(os);
         } catch (RemoteException var10) {
            throw new MARSHAL("Sending reply on SAS failure");
         }

         failureReplySent = true;
      }

      return failureReplySent;
   }

   public ContextError handleEstablishContext(RequestMessage req, ServerEndPoint endPoint, EstablishContext establishContext) {
      ContextError contextError = this.handleEstablishContext(endPoint, establishContext);
      req.addOutboundServiceContext(SASServiceContext.createCompleteEstablishContext(establishContext));
      return contextError;
   }

   public ContextError handleMessageInContext(ServerEndPoint endPoint, MessageInContext msgCtx) {
      SecurityContext secCtx = endPoint.getSecurityContext(msgCtx.getClientContextId());
      if (secCtx == null) {
         return new ContextError(msgCtx.getClientContextId(), 4, 1, (byte[])null);
      } else {
         this.setSubject(secCtx.getSubject());
         if (msgCtx.isDiscardContext()) {
            endPoint.removeSecurityContext(msgCtx.getClientContextId());
         }

         return null;
      }
   }

   private void setSubject(AuthenticatedSubject subject) {
      this.subject = subject;
   }

   public ContextError handleEstablishContext(final ServerEndPoint endPoint, EstablishContext establishCtx) {
      if (debugIIOPSecurity.isDebugEnabled() || debugSecurity.isEnabled()) {
         log("handleEstablishContext");
      }

      if (this.isStateful(establishCtx)) {
         SecurityContext oldContext = endPoint.getSecurityContext(establishCtx.getClientContextId());
         if (oldContext != null) {
            return this.handlePreviouslyEstablishedContext(establishCtx, oldContext);
         }
      }

      byte[] clientAuthToken = establishCtx.getClientAuthenticationToken();
      if (clientAuthToken != null) {
         try {
            GSSUPImpl gssUp = new GSSUPImpl(clientAuthToken);
            PrincipalAuthenticator pa = RmiSecurityFacade.getPrincipalAuthenticator(KERNEL_ID, "weblogicDEFAULT");
            SimpleCallbackHandler handler = new SimpleCallbackHandler(gssUp.getUserName(), gssUp.getPasswordChars());
            AuthenticatedSubject subject = pa.authenticate(handler, endPoint.getConnection().getContextHandler());
            addPrivateCredential(subject, new PasswordCredential(gssUp.getUserName(), gssUp.getPasswordChars()));
            if (ChannelHelper.isLocalAdminChannelEnabled() && RmiSecurityFacade.isUserAnAdministrator(subject) && !ChannelHelper.isAdminChannel(endPoint.getServerChannel())) {
               return new ContextError(establishCtx.getClientContextId(), 1, 1, (byte[])null);
            }

            this.setSubject(subject);
         } catch (GSSUPDecodeException var18) {
            return new ContextError(establishCtx.getClientContextId(), 2, 1, (byte[])null);
         } catch (LoginException var19) {
            return new ContextError(establishCtx.getClientContextId(), 1, 1, (byte[])null);
         }
      }

      final IdentityToken identityToken = establishCtx.getIdentityToken();
      if (identityToken != null) {
         String realmName = "weblogicDEFAULT";
         final PrincipalAuthenticator pa = RmiSecurityFacade.getPrincipalAuthenticator(KERNEL_ID, realmName);
         int identityType = identityToken.getIdentityType();
         AuthenticatedSubject assertAsSubject = this.getSubjectForIdentityAssertion(this.subject);
         switch (identityType) {
            case 0:
               break;
            case 1:
               try {
                  this.subject = (AuthenticatedSubject)SecurityServiceManager.runAs(KERNEL_ID, assertAsSubject, new PrivilegedExceptionAction() {
                     public Object run() throws LoginException {
                        return pa.assertIdentity("CSI.ITTAnonymous", identityToken.getAnonymous(), endPoint.getConnection().getContextHandler());
                     }
                  });
               } catch (PrivilegedActionException var24) {
                  LoginException le = (LoginException)var24.getException();
                  if (debugIIOPSecurity.isDebugEnabled() || debugSecurity.isEnabled()) {
                     log("failed identity assertion - use connection subject " + le);
                  }

                  this.subject = null;
               }
               break;
            case 2:
               byte[] principalName = identityToken.getPrincipalName();
               final String userName = GSSUtil.extractGSSUPGSSNTExportedName(principalName);
               if (userName == null) {
                  if (debugSecurity.isEnabled() || debugIIOPSecurity.isDebugEnabled()) {
                     IIOPLogger.logDebugSecurity("Unsupported CSIv2 mechanism");
                  }

                  return new ContextError(establishCtx.getClientContextId(), 2, 1, (byte[])null);
               }

               int idx = userName.indexOf(64);
               if (idx >= 0) {
                  userName = userName.substring(0, idx);

                  try {
                     pa = RmiSecurityFacade.getPrincipalAuthenticator(KERNEL_ID, "weblogicDEFAULT");
                  } catch (InvalidParameterException var23) {
                     if (debugIIOPSecurity.isDebugEnabled() || debugSecurity.isEnabled()) {
                        log("Assert identity realm not found: weblogicDEFAULT");
                     }
                  }
               }

               if (debugIIOPSecurity.isDebugEnabled() || debugSecurity.isEnabled()) {
                  log("Assert identity: " + userName);
               }

               try {
                  this.subject = (AuthenticatedSubject)SecurityServiceManager.runAs(KERNEL_ID, assertAsSubject, new PrivilegedExceptionAction() {
                     public AuthenticatedSubject run() throws LoginException {
                        return pa.assertIdentity("CSI.PrincipalName", userName, endPoint.getConnection().getContextHandler());
                     }
                  });
                  break;
               } catch (PrivilegedActionException var22) {
                  LoginException le = (LoginException)var22.getException();
                  if (debugSecurity.isEnabled() || debugIIOPSecurity.isDebugEnabled()) {
                     log("failed identity assertion prin " + le);
                  }

                  return new ContextError(establishCtx.getClientContextId(), 2, 1, (byte[])null);
               }
            case 3:
            case 5:
            case 6:
            case 7:
            default:
               if (debugSecurity.isEnabled() || debugIIOPSecurity.isDebugEnabled()) {
                  IIOPLogger.logDebugSecurity("Unsupported CSIv2 mechanism");
               }

               return new ContextError(establishCtx.getClientContextId(), 1, 1, (byte[])null);
            case 4:
               byte[] certChain = identityToken.getCertChain();
               final X509Certificate[] certs = GSSUtil.getX509CertChain(certChain);
               if (certs == null) {
                  if (debugSecurity.isEnabled() || debugIIOPSecurity.isDebugEnabled()) {
                     IIOPLogger.logDebugSecurity("CSIv2 certification chain not found");
                  }

                  return new ContextError(establishCtx.getClientContextId(), 1, 1, "CSIv2 certification chain not found".getBytes());
               }

               if (debugIIOPSecurity.isDebugEnabled() || debugSecurity.isEnabled()) {
                  log("Assert identity chain: " + Arrays.toString(certs));
               }

               try {
                  this.subject = (AuthenticatedSubject)SecurityServiceManager.runAs(KERNEL_ID, assertAsSubject, new PrivilegedExceptionAction() {
                     public Object run() throws LoginException {
                        return pa.assertIdentity("CSI.X509CertChain", certs, endPoint.getConnection().getContextHandler());
                     }
                  });
                  break;
               } catch (PrivilegedActionException var21) {
                  LoginException le = (LoginException)var21.getException();
                  if (debugSecurity.isEnabled() || debugIIOPSecurity.isDebugEnabled()) {
                     log("failed identity assertion cert chain " + le);
                  }

                  return new ContextError(establishCtx.getClientContextId(), 2, 1, (byte[])null);
               }
            case 8:
               final byte[] distinguishedName = identityToken.getDistinguishedName();
               if (distinguishedName == null) {
                  if (debugSecurity.isEnabled() || debugIIOPSecurity.isDebugEnabled()) {
                     IIOPLogger.logDebugSecurity("CSIv2 distinguished name not found");
                  }

                  return new ContextError(establishCtx.getClientContextId(), 2, 1, (byte[])null);
               }

               if (debugIIOPSecurity.isDebugEnabled() || debugSecurity.isEnabled()) {
                  log("Assert identity distinguished: " + Hex.asHex(distinguishedName));
               }

               try {
                  this.subject = (AuthenticatedSubject)SecurityServiceManager.runAs(KERNEL_ID, assertAsSubject, new PrivilegedExceptionAction() {
                     public Object run() throws LoginException {
                        return pa.assertIdentity("CSI.DistinguishedName", distinguishedName, endPoint.getConnection().getContextHandler());
                     }
                  });
               } catch (PrivilegedActionException var20) {
                  LoginException le = (LoginException)var20.getException();
                  if (debugSecurity.isEnabled() || debugIIOPSecurity.isDebugEnabled()) {
                     log("failed identity assertion dist name " + le);
                  }

                  return new ContextError(establishCtx.getClientContextId(), 2, 1, (byte[])null);
               }
         }
      }

      if (this.isStateful(establishCtx)) {
         if (this.subject == null) {
            this.subject = RmiSecurityFacade.getAnonymousSubject();
         }

         SecurityContext secCtx = new SecurityContext(establishCtx.getClientContextId(), establishCtx, this.subject);
         endPoint.putSecurityContext(establishCtx.getClientContextId(), secCtx);
      }

      return null;
   }

   private boolean isStateful(EstablishContext establishCtx) {
      return establishCtx.getClientContextId() != 0L;
   }

   private ContextError handlePreviouslyEstablishedContext(EstablishContext establishCtx, SecurityContext oldContext) {
      if (this.isMismatchedIdentity(establishCtx.getIdentityToken(), oldContext)) {
         return new ContextError(establishCtx.getClientContextId(), 3, 1, (byte[])null);
      } else if (this.isMismatchedAuthentication(establishCtx.getClientAuthenticationToken(), oldContext)) {
         return new ContextError(establishCtx.getClientContextId(), 3, 1, (byte[])null);
      } else {
         this.setSubject(oldContext.getSubject());
         return null;
      }
   }

   private boolean isMismatchedIdentity(IdentityToken identityToken, SecurityContext oldContext) {
      return identityToken != null && !identityToken.equals(oldContext.getEstablishContext().getIdentityToken());
   }

   private boolean isMismatchedAuthentication(byte[] clientAuthToken, SecurityContext oldContext) {
      return clientAuthToken != null && !Arrays.equals(clientAuthToken, oldContext.getEstablishContext().getClientAuthenticationToken());
   }

   private static boolean addPrivateCredential(AuthenticatedSubject subject, Object passwordCred) {
      return subject.getPrivateCredentials(KERNEL_ID).add(passwordCred);
   }

   private AuthenticatedSubject getSubjectForIdentityAssertion(AuthenticatedSubject subject) {
      if (null == subject) {
         return RmiSecurityFacade.getCurrentSubject(KERNEL_ID);
      } else {
         return subject.equals(KERNEL_ID) ? RmiSecurityFacade.getAnonymousSubject() : subject;
      }
   }

   private static void log(String msg) {
      IIOPLogger.logDebugSecurity("<SASServiceContext>: " + msg);
   }
}
