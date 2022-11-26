package weblogic.rmi.extensions.server;

import java.io.IOException;
import java.rmi.RemoteException;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Arrays;
import javax.resource.spi.security.PasswordCredential;
import weblogic.management.configuration.SecurityConfigurationMBean;
import weblogic.management.provider.ManagementService;
import weblogic.protocol.LocalServerIdentity;
import weblogic.protocol.Protocol;
import weblogic.protocol.ProtocolManager;
import weblogic.protocol.ServerIdentity;
import weblogic.rjvm.RJVM;
import weblogic.rmi.ConnectException;
import weblogic.rmi.extensions.RemoteHelper;
import weblogic.rmi.facades.RmiSecurityFacade;
import weblogic.rmi.spi.EndPoint;
import weblogic.rmi.spi.HostID;
import weblogic.rmi.spi.RMIRuntime;
import weblogic.security.acl.DefaultUserInfoImpl;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.acl.internal.AuthenticatedUser;
import weblogic.security.acl.internal.Security;
import weblogic.security.service.AdminResource;
import weblogic.security.service.AuthorizationManager;
import weblogic.security.service.ContextHandler;
import weblogic.security.service.CredentialManager;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.RemoteResource;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.service.SecurityService.ServiceType;
import weblogic.security.spi.Resource;
import weblogic.security.utils.ResourceIDDContextWrapper;

public final class RemoteDomainSecurityHelper {
   private static final boolean DEBUG = Boolean.getBoolean("weblogic.debug.DebugCrossDomainSecurity");
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final String CROSS_DOMAIN_PROTOCOL = "cross-domain-protocol";
   private static final String CROSS_DOMAIN_USER = "cross-domain";
   private static final String CROSS_DOMAIN_ADMIN_RESOURCE = "CrossDomain";
   public static final int ACCEPT_CALL = 0;
   public static final int REJECT_CALL = 1;
   public static final int UNDETERMINABLE = 2;

   private static boolean isCDSEnabled() {
      return RemoteDomainSecurityHelper.SINGLETON.secConfig.isCrossDomainSecurityEnabled();
   }

   public static AuthenticatedSubject getSubject(String url) throws IOException, RemoteException {
      if (DEBUG) {
         debug("[RemoteDomainSecurityHelper] getSubject: url= " + url);
      }

      if (isCDSEnabled() && isRemoteDomain(url)) {
         EndPoint endPoint = findOrCreateEndPointWithSubject(url, RmiSecurityFacade.getAnonymousSubject());
         return endPoint != null ? getSubjectInternal(endPoint) : null;
      } else {
         return null;
      }
   }

   public static AuthenticatedSubject getSubject2(String url) throws IOException {
      debug("getSubject2 url = " + url + ", - isCDSEnabled() = " + isCDSEnabled());
      if (!isCDSEnabled()) {
         return null;
      } else {
         String protocolName = url.substring(0, url.indexOf(58));
         Protocol p = ProtocolManager.getProtocolByName(protocolName);
         AuthenticatedSubject subject = SecurityServiceManager.getCurrentSubject(kernelId);
         if (!urlProtocolAndThreadQOSMatch(p, subject)) {
            throw new IOException("Unable to find connection for url " + url + ". subject usedto initiate connection does not match url protocol");
         } else {
            String modifiedUrl = convertAdminProtocol(url);
            EndPoint endPoint = RMIRuntime.findEndPoint(modifiedUrl);
            if (endPoint != null) {
               if (DEBUG) {
                  debug("[getSubject2] Found Existing endpoint " + endPoint);
                  debug("[getSubject2] isRemoteDomain() " + isRemoteDomain(endPoint));
               }

               return !isRemoteDomain(endPoint) ? null : getSubjectInternal(endPoint);
            } else {
               String realProtocolName = modifiedUrl.substring(0, modifiedUrl.indexOf(58));
               Protocol realProtocol = ProtocolManager.getProtocolByName(realProtocolName);
               AuthenticatedSubject newSubject = subject;
               boolean subjectQOSReset = false;
               if (!p.isSatisfactoryQOS(subject.getQOS())) {
                  newSubject = new AuthenticatedSubject(subject);
                  newSubject.setQOS(realProtocol.getQOS());
                  subjectQOSReset = true;
                  if (DEBUG) {
                     debug("[getSubject2] subject on thread was " + subject + ". It has been reset to " + newSubject);
                  }
               }

               AuthenticatedSubject var10;
               try {
                  endPoint = findOrCreateEndPointWithSubject(modifiedUrl, newSubject);
                  if (endPoint == null) {
                     throw new ConnectException("Unable to get endpoint for url = " + url);
                  }

                  if (DEBUG) {
                     debug("[getSubject2] new endpoint " + endPoint);
                  }

                  if (!isRemoteDomain(endPoint)) {
                     var10 = null;
                     return var10;
                  }

                  var10 = getSubjectInternal(endPoint);
               } finally {
                  if (endPoint != null && subjectQOSReset) {
                     if (DEBUG) {
                        debug("[getSubject2] Invoking disconnect on endpoint " + endPoint);
                     }

                     endPoint.disconnect("Component generated internal disconnect", true);
                  }

               }

               return var10;
            }
         }
      }
   }

   private static boolean urlProtocolAndThreadQOSMatch(Protocol p, AuthenticatedSubject subject) {
      if (DEBUG) {
         debug("[urlProtocolAndThreadQOSMatch protocol] = " + p + ", subject.QOS = " + subject.getQOS());
      }

      if (p.toByte() == 6 && subject.getQOS() == 102) {
         return false;
      } else {
         return p.toByte() != 0 && p.toByte() != 4 || subject.getQOS() == 101;
      }
   }

   private static String convertAdminProtocol(String url) {
      String modifiedURL = url;

      try {
         int idx = url.indexOf(58);
         String protocol = url.substring(0, idx);
         if ("admin".equals(protocol)) {
            modifiedURL = ProtocolManager.getDefaultAdminProtocol().getProtocolName() + url.substring(idx);
         }

         return modifiedURL;
      } catch (IndexOutOfBoundsException var4) {
         throw new AssertionError("unsupported protocol " + url, var4);
      }
   }

   public static AuthenticatedSubject getSubject(Object remote) throws RemoteException, IllegalArgumentException {
      if (!isCDSEnabled()) {
         return null;
      } else {
         AuthenticatedSubject result = getSubjectInternal(RemoteHelper.getEndPoint(remote));
         if (DEBUG) {
            debug("getSubject for  " + remote + " returned:" + result);
         }

         return result;
      }
   }

   public static AuthenticatedSubject getSubject(EndPoint endPoint) throws RemoteException {
      if (!isCDSEnabled()) {
         return null;
      } else {
         AuthenticatedSubject result = getSubjectInternal(endPoint);
         if (DEBUG) {
            debug("getSubject for  " + endPoint + " returned:" + result);
         }

         return result;
      }
   }

   private static AuthenticatedSubject getSubjectInternal(EndPoint endPoint) throws RemoteException {
      HostID hostID = endPoint.getHostID();
      if (!(hostID instanceof ServerIdentity)) {
         return null;
      } else {
         String domainName = ((ServerIdentity)hostID).getDomainName();
         if (domainName != null && !isDomainExcluded(domainName)) {
            PasswordCredential credential = getCredentials(domainName);
            if (DEBUG) {
               debug("getCredentials() returned " + credential);
            }

            if (credential == null) {
               return null;
            } else {
               AuthenticatedSubject as = SecurityServiceManager.getASFromAU(authenticate(endPoint, credential));
               if (DEBUG) {
                  debug("authenticate returned " + as);
               }

               return as;
            }
         } else {
            return null;
         }
      }
   }

   private static AuthenticatedUser authenticate(EndPoint endPoint, PasswordCredential credential) throws RemoteException {
      if (credential == null) {
         return null;
      } else {
         DefaultUserInfoImpl securityUser = new DefaultUserInfoImpl(credential.getUserName(), new String(credential.getPassword()));
         if (DEBUG) {
            debug(credential.getUserName() + " - " + new String(credential.getPassword()));
         }

         RJVM rjvm = (RJVM)endPoint;
         return Security.authenticate(securityUser, rjvm, ProtocolManager.getDefaultProtocol(), (String)null);
      }
   }

   private static PasswordCredential getCredentials(String domainName) {
      CredentialManager cm = RmiSecurityFacade.getCredentialManager(kernelId, "weblogicDEFAULT");
      AuthenticatedSubject subject = SecurityServiceManager.getCurrentSubject(kernelId);
      if (DEBUG) {
         debug("current subject=" + subject + ", domainName=" + domainName);
      }

      RemoteResource resource = new RemoteResource("cross-domain-protocol", domainName, (String)null, (String)null, (String)null);
      Object[] mappings = cm.getCredentials(kernelId, "cross-domain", resource, (ContextHandler)null, "weblogic.UserPassword");
      if (DEBUG) {
         debug("got mappings=" + Arrays.toString(mappings));
      }

      if (mappings == null) {
         return null;
      } else {
         if (DEBUG) {
            debug("got mappings length=" + mappings.length);
         }

         for(int i = 0; i < mappings.length; ++i) {
            Object cred = mappings[i];
            if (cred instanceof PasswordCredential) {
               if (DEBUG) {
                  debug("cred=" + cred);
               }

               return (PasswordCredential)cred;
            }
         }

         if (DEBUG) {
            debug("found no password credential !");
         }

         return null;
      }
   }

   public static int acceptRemoteDomainCall(HostID hostID, AuthenticatedSubject remoteSubject) {
      if (!isCDSEnabled()) {
         if (DEBUG) {
            debug("acceptRemoteDomainCall for " + remoteSubject + "= No CDS");
         }

         return 2;
      } else if (!(hostID instanceof ServerIdentity)) {
         if (DEBUG) {
            debug("acceptRemoteDomainCall for " + remoteSubject + "= Not ServerIdentity" + hostID);
         }

         return 2;
      } else {
         String domainName = ((ServerIdentity)hostID).getDomainName();
         if (domainName != null && !LocalServerIdentity.getIdentity().getDomainName().equals(domainName) && !isDomainExcluded(domainName)) {
            Resource resource = new AdminResource("CrossDomain", (String)null, (String)null);
            String realmName = SecurityServiceManager.getAdministrativeRealmName();
            AuthorizationManager am = (AuthorizationManager)SecurityServiceManager.getSecurityService(kernelId, realmName, ServiceType.AUTHORIZE);
            boolean allow = am.isAccessAllowed(remoteSubject, resource, new ResourceIDDContextWrapper());
            if (DEBUG) {
               debug("acceptRemoteDomainCall for " + remoteSubject + "=" + allow);
            }

            return allow ? 0 : 1;
         } else {
            if (DEBUG) {
               debug("acceptRemoteDomainCall for " + remoteSubject + "= UNDETERMINABLE");
            }

            return 2;
         }
      }
   }

   public static boolean isRemoteDomain(String url) throws IOException, RemoteException {
      if (DEBUG) {
         debug("[RemoteDomainSecurityHelper] isRemoteDomain: url= " + url);
      }

      if (url != null && url.length() != 0) {
         EndPoint endPoint = findOrCreateEndPointWithSubject(url, RmiSecurityFacade.getAnonymousSubject());
         return isRemoteDomain(endPoint);
      } else {
         return false;
      }
   }

   private static EndPoint findOrCreateEndPointWithSubject(String url, AuthenticatedSubject subject) throws IOException {
      final String modifiedURL = convertAdminProtocol(url);
      if (DEBUG) {
         debug("[RemoteDomainSecurityHelper] findOrCreateEndPointWithSubject: url= " + url);
      }

      if (DEBUG) {
         debug("[RemoteDomainSecurityHelper] findOrCreateEndPointWithSubject: modifiedURL= " + modifiedURL);
      }

      if (DEBUG) {
         debug("[RemoteDomainSecurityHelper] findOrCreateEndPointWithSubject: subject to use = " + subject);
      }

      EndPoint endPoint = null;
      if (RMIRuntime.supportServerURL(modifiedURL)) {
         try {
            endPoint = (EndPoint)SecurityServiceManager.runAs(kernelId, subject, new PrivilegedExceptionAction() {
               public Object run() throws IOException {
                  return RMIRuntime.findOrCreateEndPoint(modifiedURL);
               }
            });
         } catch (PrivilegedActionException var6) {
            Exception ex = var6.getException();
            if (ex instanceof IOException) {
               throw (IOException)ex;
            }

            if (ex instanceof RuntimeException) {
               throw (RuntimeException)ex;
            }

            throw new AssertionError("Unpredicted PrivilegedActionException is thrown", var6);
         }
      }

      if (DEBUG) {
         debug("[RemoteDomainSecurityHelper] findOrCreateEndPointWithSubject: url= " + modifiedURL + " endPoint==" + endPoint);
      }

      return endPoint;
   }

   public static boolean isRemoteDomain(EndPoint endPoint) {
      if (endPoint == null) {
         return false;
      } else {
         HostID hostID = endPoint.getHostID();
         if (!(hostID instanceof ServerIdentity)) {
            return false;
         } else {
            String domainName = ((ServerIdentity)hostID).getDomainName();
            if (domainName != null && !RemoteDomainSecurityHelper.SINGLETON.localName.equals(domainName)) {
               if (DEBUG) {
                  debug("[RemoteDomainSecurityHelper] isRemoteDomain: TRUE remote domainName= " + domainName + "!=" + RemoteDomainSecurityHelper.SINGLETON.localName);
               }

               return true;
            } else {
               if (DEBUG) {
                  debug("[RemoteDomainSecurityHelper] isRemoteDomain: FALSE remote domainName= " + domainName + "==" + RemoteDomainSecurityHelper.SINGLETON.localName);
               }

               return false;
            }
         }
      }
   }

   private static boolean isDomainExcluded(String domainName) {
      if (domainName == null) {
         return false;
      } else {
         String[] excludedDomainList = RemoteDomainSecurityHelper.SINGLETON.secConfig.getExcludedDomainNames();
         if (excludedDomainList == null) {
            return false;
         } else {
            for(int i = 0; i < excludedDomainList.length; ++i) {
               if (domainName.equals(excludedDomainList[i])) {
                  return true;
               }
            }

            return false;
         }
      }
   }

   private static void debug(String str) {
      if (DEBUG) {
         System.out.println("[RemoteDomainSecurityHelper] " + str);
      }

   }

   private static class SINGLETON {
      static SecurityConfigurationMBean secConfig;
      static String localName;

      static {
         secConfig = ManagementService.getRuntimeAccess(RemoteDomainSecurityHelper.kernelId).getDomain().getSecurityConfiguration();
         localName = LocalServerIdentity.getIdentity().getDomainName();
      }
   }
}
