package weblogic.connector.security.outbound;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NameNotFoundException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.ManagedConnectionFactory;
import javax.resource.spi.SecurityException;
import javax.resource.spi.security.GenericCredential;
import javax.resource.spi.security.PasswordCredential;
import javax.security.auth.Subject;
import org.ietf.jgss.GSSCredential;
import org.ietf.jgss.GSSException;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.connector.ConnectorLogger;
import weblogic.connector.common.Debug;
import weblogic.connector.external.AuthMechInfo;
import weblogic.connector.external.OutboundInfo;
import weblogic.connector.external.RAInfo;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.AuthorizationManager;
import weblogic.security.service.ContextHandler;
import weblogic.security.service.CredentialManager;
import weblogic.security.service.EISResource;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.service.SecurityService.ServiceType;
import weblogic.security.utils.ResourceIDDContextWrapper;

public final class SecurityContext {
   public static final String SHARED_APPNAME = "WEBLOGIC_SHAREDAPP";
   private static final String ipAnonymousConnectionsName = "weblogic_ra_anonymous";
   private static final String ipInitialConnectionsName = "weblogic_ra_initial";
   private static final String ipDefaultConnectionsName = "weblogic_ra_default";
   private AuthenticatedSubject currentSubject = null;
   private AuthorizationManager am = null;
   private ConnectionRequestInfo clientInfo;
   private boolean isContainerManaged;
   private boolean shareable;
   private boolean isShareAllowedByPool;
   private String poolName;
   private Subject rpSubject;
   private OutboundInfo outboundInfo;
   private EISResource globalEISRes;
   private EISResource poolEISRes;
   private static Context initialContext;

   public SecurityContext(OutboundInfo outboundInfo, String applicationId, String componentName, String poolName, boolean isShareAllowedByPool, ManagedConnectionFactory mcf, ConnectionRequestInfo cxInfo, boolean initial, AuthenticatedSubject kernelId) throws SecurityException {
      this.initialize(outboundInfo, applicationId, componentName, poolName, isShareAllowedByPool, mcf, cxInfo, initial, kernelId);
   }

   private void initialize(OutboundInfo outboundInfo, String applicationId, String componentName, String poolName, boolean isShareAllowedByPool, ManagedConnectionFactory mcf, ConnectionRequestInfo cxInfo, boolean initial, AuthenticatedSubject kernelId) throws SecurityException {
      this.am = (AuthorizationManager)SecurityServiceManager.getSecurityService(kernelId, "weblogicDEFAULT", ServiceType.AUTHORIZE);
      this.currentSubject = SecurityServiceManager.getCurrentSubject(kernelId);
      this.outboundInfo = outboundInfo;
      this.poolName = poolName;
      this.clientInfo = cxInfo;
      this.isContainerManaged = true;
      this.isShareAllowedByPool = isShareAllowedByPool;
      this.shareable = true;
      this.rpSubject = null;
      this.globalEISRes = getGlobalEISResource(applicationId, componentName, outboundInfo.getRAInfo());
      this.poolEISRes = getPoolEISResource(applicationId, componentName, outboundInfo);
      if (Debug.isSecurityCtxEnabled()) {
         Debug.securityCtx("For pool '" + poolName + "' initializing SecurityContext with AppName = " + ApplicationVersionUtils.getDisplayName(this.globalEISRes.getApplicationName()) + ", ModuleName = " + this.globalEISRes.getModuleName() + ", EIS Type = " + this.globalEISRes.getType() + ", DestinationId = " + this.poolEISRes.getDestinationId() + ", Global ResourceId = " + this.globalEISRes.toString() + ", Pool ResourceId = " + this.poolEISRes.toString());
      }

      this.initSubject(mcf, initial, kernelId);
   }

   private void initSubject(ManagedConnectionFactory mcf, boolean initial, AuthenticatedSubject kernelId) throws SecurityException {
      Vector creds = this.getCredentials(initial, kernelId);
      if (creds != null && creds.size() > 0) {
         this.rpSubject = new Subject();
         Object cred = null;

         for(int idx = 0; idx < creds.size(); ++idx) {
            cred = creds.get(idx);
            final ResourcePrincipal rp;
            if (cred instanceof PasswordCredential) {
               final PasswordCredential pc = (PasswordCredential)cred;
               pc.setManagedConnectionFactory(mcf);
               rp = new ResourcePrincipal(pc.getUserName(), new String(pc.getPassword()));
               if (Debug.isSecurityCtxEnabled()) {
                  this.debug("Adding resource principal Username: " + pc.getUserName());
               }

               AccessController.doPrivileged(new PrivilegedAction() {
                  public Object run() {
                     SecurityContext.this.rpSubject.getPrincipals().add(rp);
                     SecurityContext.this.rpSubject.getPrivateCredentials().add(pc);
                     return null;
                  }
               });
            } else if (cred instanceof GenericCredential) {
               final GenericCredential gc = (GenericCredential)cred;
               rp = new ResourcePrincipal(gc.getName(), "");
               if (Debug.isSecurityCtxEnabled()) {
                  this.debug("Adding resource principal Username: " + gc.getName());
               }

               AccessController.doPrivileged(new PrivilegedAction() {
                  public Object run() {
                     SecurityContext.this.rpSubject.getPrincipals().add(rp);
                     SecurityContext.this.rpSubject.getPrivateCredentials().add(gc);
                     return null;
                  }
               });
            } else if (cred instanceof GSSCredential) {
               final GSSCredential gc = (GSSCredential)cred;

               String credName;
               try {
                  credName = gc.getName().toString();
               } catch (GSSException var10) {
                  throw new SecurityException(var10);
               }

               final ResourcePrincipal rp = new ResourcePrincipal(credName, "");
               if (Debug.isSecurityCtxEnabled()) {
                  this.debug("Adding resource principal Username: " + credName);
               }

               AccessController.doPrivileged(new PrivilegedAction() {
                  public Object run() {
                     SecurityContext.this.rpSubject.getPrincipals().add(rp);
                     SecurityContext.this.rpSubject.getPrivateCredentials().add(gc);
                     return null;
                  }
               });
            } else if (Debug.isSecurityCtxEnabled()) {
               this.debug("An unsupported credential type was encountered and will be ignored:  " + cred.getClass().getName());
            }
         }

         this.setSubjectReadOnly(this.rpSubject);
      } else if (this.isContainerManaged && Debug.isSecurityCtxEnabled()) {
         Debug.logNoResourcePrincipalFound();
      }

   }

   private void logUsingAppManagedSecurity() {
      if (Debug.isSecurityCtxEnabled()) {
         Debug.securityCtx(" For pool '" + this.poolName + " ': establishing Security Context for Application Managed client");
         if (this.clientInfo == null) {
            Debug.logNoConnectionRequestInfo();
         }
      }

   }

   private void checkResourceReference() throws NamingException {
      Object resRefContext = null;
      String resRef = "java:/comp/env/wls-connector-resref";
      Context ctx = getInitialContext();

      try {
         resRefContext = ctx.lookup(resRef);
      } catch (NameNotFoundException var5) {
         if (Debug.isSecurityCtxEnabled()) {
            Debug.securityCtx("For pool '" + this.poolName + "' SecurityContext.checkResourceReference() couldn't find " + resRef + " for calling component");
         }

         return;
      }

      if (resRefContext == null && Debug.isSecurityCtxEnabled()) {
         Debug.securityCtx("For pool '" + this.poolName + "' SecurityContext.checkResourceReference() returned null entry for " + resRef + " of calling component");
      } else if (resRefContext != null) {
         this.processResourceReference((Context)resRefContext);
      }

   }

   private boolean processResourceReference(Context ctx) throws NamingException {
      boolean found = false;
      NamingEnumeration enum_ = ctx.list("");

      while(enum_.hasMore() && !found) {
         Object namingElement = enum_.next();
         NameClassPair pair = (NameClassPair)namingElement;
         if (pair.getClassName().endsWith("NamingNode")) {
            found = this.processResourceReference((Context)ctx.lookup(pair.getName()));
            if (found) {
               break;
            }
         } else if (pair.getName().endsWith("JNDI")) {
            String rrJNDI = pair.getName();
            if (Debug.isSecurityCtxEnabled()) {
               this.debug("Found JNDI entry \"" + rrJNDI + "\" in wls-connector-resref context -- looking it up...");
            }

            Object theRef = ctx.lookup(rrJNDI);
            String jndiName = theRef.toString();
            if (Debug.isSecurityCtxEnabled()) {
               this.debug("Lookup of \"" + rrJNDI + "\" yields: \"" + jndiName + "\", comparing with \"" + this.outboundInfo.getJndiName() + "\"");
            }

            if (jndiName != null && jndiName.equalsIgnoreCase(this.outboundInfo.getJndiName())) {
               if (Debug.isSecurityCtxEnabled()) {
                  this.debug("Found matching entry with jndiName: " + jndiName);
               }

               String resAuth = this.lookupResAttr(rrJNDI, ctx, "Auth");
               if (resAuth != null) {
                  this.isContainerManaged = !resAuth.equalsIgnoreCase("Application");
                  if (Debug.isSecurityCtxEnabled()) {
                     Debug.logRequestedSecurityType(jndiName, resAuth);
                  }
               }

               String sharingScope = this.lookupResAttr(rrJNDI, ctx, "SharingScope");
               String resRef;
               String resRefName;
               if (sharingScope != null) {
                  boolean isShareableReference = sharingScope.equalsIgnoreCase("Shareable");
                  if (isShareableReference && !this.isShareAllowedByPool) {
                     resRef = ctx.getNameInNamespace() + "/" + rrJNDI;
                     resRefName = getResRefName(resRef) == null ? "UNKNOWN" : getResRefName(resRef);
                     String callerName = getCallerName(resRef) == null ? "UNKNOWN" : getCallerName(resRef);
                     ConnectorLogger.logShareableRefToUnshareablePool(jndiName, resRefName, callerName);
                     isShareableReference = false;
                     sharingScope = "Unshareable";
                  }

                  this.setShareable(isShareableReference);
                  if (Debug.isSecurityCtxEnabled()) {
                     Debug.logRequestedSharingScope(jndiName, sharingScope);
                  }
               } else if (!this.isShareAllowedByPool) {
                  String resRef = ctx.getNameInNamespace() + "/" + rrJNDI;
                  resRef = getResRefName(resRef) == null ? "UNKNOWN" : getResRefName(resRef);
                  resRefName = getCallerName(resRef) == null ? "UNKNOWN" : getCallerName(resRef);
                  ConnectorLogger.logUnknownShareableRefToUnshareablePool(jndiName, resRef, resRefName);
                  this.setShareable(false);
               } else {
                  this.setShareable(true);
               }

               found = true;
               break;
            }

            if (Debug.isSecurityCtxEnabled()) {
               this.debug("Skipping non-matching JNDIName");
            }
         } else if (Debug.isSecurityCtxEnabled()) {
            this.debug("Skipping non-JNDI Entry in context");
         }
      }

      return found;
   }

   public static String getCallerName(String ref) {
      if (ref == null) {
         return null;
      } else {
         String s;
         int i;
         if (ref.indexOf("/webapp/") >= 0) {
            s = ref.substring(ref.indexOf("/webapp/") + 8);
            i = s.indexOf("/");
            return i > 0 ? s.substring(0, i) : null;
         } else if (ref.indexOf("/ejb/") >= 0) {
            s = ref.substring(ref.indexOf("/ejb/") + 5);
            i = s.indexOf("/");
            return i > 0 ? s.substring(0, i) : null;
         } else {
            return null;
         }
      }
   }

   public static String getResRefName(String ref) {
      if (ref == null) {
         return null;
      } else if (ref.indexOf("/wls-connector-resref/") >= 0) {
         String name = ref.substring(ref.indexOf("/wls-connector-resref/") + "/wls-connector-resref/".length());
         return name.endsWith("JNDI") ? name.substring(0, name.lastIndexOf("JNDI")) : name;
      } else {
         return null;
      }
   }

   private String lookupResAttr(String rrJNDI, Context ctx, String attr) {
      Object attrEntry = null;
      String attrValue = null;
      String rrAttr = rrJNDI.substring(0, rrJNDI.length() - 4) + attr;
      if (Debug.isSecurityCtxEnabled()) {
         this.debug("Now looking up: \"" + rrAttr + "\" ...");
      }

      try {
         attrEntry = ctx.lookup(rrAttr);
      } catch (NamingException var8) {
      }

      if (attrEntry != null) {
         attrValue = attrEntry.toString();
      }

      return attrValue;
   }

   private void debug(String msg) {
      Debug.securityCtx("For pool '" + this.poolName + "' " + msg);
   }

   private void setShareable(boolean shareable) {
      if (Debug.isSecurityCtxEnabled()) {
         Debug.println((Object)this, (String)(".setShareable() setting shareable to " + shareable));
      }

      this.shareable = shareable;
   }

   private void setSubjectReadOnly(final Subject subject) {
      AccessController.doPrivileged(new PrivilegedAction() {
         public Object run() {
            try {
               subject.setReadOnly();
            } catch (java.lang.SecurityException var2) {
               SecurityContext.this.debug("WARNING:  Failed to modify Subject to be read-only:  " + var2);
            }

            return null;
         }
      });
   }

   private static EISResource getGlobalEISResource(String resAppId, String resModName, RAInfo raInfo) {
      String resEisType = "";
      if (resAppId == null || resAppId.length() == 0) {
         resAppId = "WEBLOGIC_SHAREDAPP";
      }

      if (raInfo != null && raInfo.getEisType() != null) {
         resEisType = raInfo.getEisType();
      }

      return new EISResource(resAppId, resModName, resEisType);
   }

   public static String getGlobalEISResourceId(String resAppId, String resModName, RAInfo raInfo) {
      return getGlobalEISResource(resAppId, resModName, raInfo).toString();
   }

   private static EISResource getPoolEISResource(String resAppId, String resModName, OutboundInfo outboundInfo) {
      String resEisType = "";
      String resKey = "";
      if (resAppId == null || resAppId.length() == 0) {
         resAppId = "WEBLOGIC_SHAREDAPP";
      }

      if (outboundInfo != null && outboundInfo.getEisType() != null) {
         resEisType = outboundInfo.getEisType();
      }

      if (outboundInfo != null && outboundInfo.getJndiName() != null) {
         resKey = outboundInfo.getJndiName();
      }

      return new EISResource(resAppId, resModName, resEisType, resKey);
   }

   public static String getPoolEISResourceId(String resAppId, String resModName, OutboundInfo outboundInfo) {
      return getPoolEISResource(resAppId, resModName, outboundInfo).toString();
   }

   public static String getEISResourceId(String resAppId, String resModName, String resEisType, String resKey) {
      EISResource eisResource;
      if (resKey == null) {
         eisResource = new EISResource(resAppId, resModName, resEisType);
      } else {
         eisResource = new EISResource(resAppId, resModName, resEisType, resKey);
      }

      return eisResource.toString();
   }

   public boolean isAccessAllowed() {
      boolean isAllowed = this.am.isAccessAllowed(this.currentSubject, this.globalEISRes, new ResourceIDDContextWrapper());
      if (!isAllowed) {
         Debug.logAccessDeniedWarning(this.poolName, ApplicationVersionUtils.getDisplayName(this.globalEISRes.getApplicationName()), this.globalEISRes.getModuleName(), this.globalEISRes.getEISName());
      }

      return isAllowed;
   }

   public boolean isContainerManaged() {
      return this.isContainerManaged;
   }

   public boolean isEmptyContext() {
      return this.rpSubject == null && this.clientInfo == null;
   }

   public ConnectionRequestInfo getClientInfo() {
      return this.clientInfo;
   }

   public Subject getSubject() {
      return this.rpSubject;
   }

   public boolean isShareable() {
      if (Debug.isSecurityCtxEnabled()) {
         Debug.println((Object)this, (String)(".isShareable() = " + this.shareable));
      }

      return this.shareable;
   }

   private Vector getInitialCredentials(AuthenticatedSubject kernelId) {
      if (Debug.isSecurityCtxEnabled()) {
         this.debug("Looking up credentials for initial connections");
      }

      Vector creds = this.getCredentials("weblogic_ra_initial", kernelId);
      if (creds != null && creds.size() != 0) {
         if (Debug.isSecurityCtxEnabled()) {
            this.debug("Using provided credentials for initial connections.");
         }
      } else {
         if (Debug.isSecurityCtxEnabled()) {
            this.debug("No credentials explicitly provided for initial connections.  Will attempt to find default credentials.");
         }

         creds = this.getDefaultCredentials(kernelId);
      }

      return creds;
   }

   private Vector getAnonymousCredentials(AuthenticatedSubject kernelId) {
      if (Debug.isSecurityCtxEnabled()) {
         this.debug("No authenticated user, so looking up anonymous credentials");
      }

      Vector creds = this.getCredentials("weblogic_ra_anonymous", kernelId);
      if (creds != null && creds.size() != 0) {
         if (Debug.isSecurityCtxEnabled()) {
            this.debug("Using provided credentials for anonymous users");
         }
      } else if (Debug.isSecurityCtxEnabled()) {
         this.debug("No credentials provided for anonymous users.  Will try to find default credentials.");
      }

      return creds;
   }

   private Vector getDefaultCredentials(AuthenticatedSubject kernelId) {
      if (Debug.isSecurityCtxEnabled()) {
         this.debug("Looking up default credentials");
      }

      Vector creds = this.getCredentials("weblogic_ra_default", kernelId);
      if (Debug.isSecurityCtxEnabled()) {
         if (creds != null && creds.size() != 0) {
            this.debug("Using provided default credentials");
         } else {
            this.debug("No default credentials are provided");
         }
      }

      return creds;
   }

   private Vector getNonInitialCredentials(AuthenticatedSubject kernelId) {
      Vector creds = null;

      try {
         this.checkResourceReference();
      } catch (NamingException var4) {
         Debug.logContextProcessingError(var4);
      }

      if (this.outboundInfo.getResAuth() != null) {
         this.isContainerManaged = this.outboundInfo.getResAuth().equalsIgnoreCase("Container");
      }

      if (this.isContainerManaged) {
         if (this.currentSubject != null && this.currentSubject.getPrincipals() != null && this.currentSubject.getPrincipals().size() != 0) {
            creds = this.getCredentials(this.currentSubject, kernelId);
         } else {
            creds = this.getAnonymousCredentials(kernelId);
         }

         if (creds == null || creds.size() == 0) {
            creds = this.getDefaultCredentials(kernelId);
         }
      } else {
         creds = null;
         this.logUsingAppManagedSecurity();
      }

      return creds;
   }

   private Vector getCredentials(boolean initialConnection, AuthenticatedSubject kernelId) {
      Vector creds;
      if (initialConnection) {
         creds = this.getInitialCredentials(kernelId);
      } else {
         creds = this.getNonInitialCredentials(kernelId);
      }

      return creds;
   }

   private Vector getCredentials(String initiatingPrincipal, AuthenticatedSubject kernelId) {
      return this.getTheCredentials(initiatingPrincipal, kernelId);
   }

   private Vector getCredentials(AuthenticatedSubject initiatingPrincipal, AuthenticatedSubject kernelId) {
      return this.getTheCredentials(initiatingPrincipal, kernelId);
   }

   private Vector getCredentials(CredentialManager cm, AuthenticatedSubject kernelId, String principal, AuthenticatedSubject as, EISResource res, String[] cedentialTypes) {
      if (cedentialTypes == null) {
         return null;
      } else {
         Vector ret = new Vector();
         String[] var8 = cedentialTypes;
         int var9 = cedentialTypes.length;

         for(int var10 = 0; var10 < var9; ++var10) {
            String ct = var8[var10];
            Object[] objs = null;
            if (as != null) {
               objs = cm.getCredentials(kernelId, as, res, (ContextHandler)null, ct);
            } else {
               objs = cm.getCredentials(kernelId, principal, res, (ContextHandler)null, ct);
            }

            if (objs != null) {
               Object[] var13 = objs;
               int var14 = objs.length;

               for(int var15 = 0; var15 < var14; ++var15) {
                  Object obj = var13[var15];
                  ret.add(obj);
               }
            }
         }

         return ret;
      }
   }

   private Vector getTheCredentials(Object initiatingPrincipal, AuthenticatedSubject kernelId) {
      Vector creds = null;
      if (initiatingPrincipal == null) {
         return null;
      } else if (this.getCredentialTypes() == null) {
         if (Debug.isSecurityCtxEnabled()) {
            this.debug("No credential types have been specified. Therefore no credentials can be attempted to be found.");
         }

         return null;
      } else {
         CredentialManager cm = (CredentialManager)SecurityServiceManager.getSecurityService(kernelId, "weblogicDEFAULT", ServiceType.CREDENTIALMANAGER);
         if (cm != null) {
            if (Debug.isSecurityCtxEnabled()) {
               this.debug("Looking up credentials for initiating principal:  " + this.getUserName(initiatingPrincipal));
            }

            if (initiatingPrincipal instanceof String) {
               creds = this.getCredentials(cm, kernelId, (String)initiatingPrincipal, (AuthenticatedSubject)null, this.poolEISRes, this.getCredentialTypes());
               if (creds == null || creds.size() == 0) {
                  if (Debug.isSecurityCtxEnabled()) {
                     this.debug("Matching credentials not found for the pool, checking global mappings");
                  }

                  creds = this.getCredentials(cm, kernelId, (String)initiatingPrincipal, (AuthenticatedSubject)null, this.globalEISRes, this.getCredentialTypes());
               }
            } else if (initiatingPrincipal instanceof AuthenticatedSubject) {
               creds = this.getCredentials(cm, kernelId, (String)null, (AuthenticatedSubject)initiatingPrincipal, this.poolEISRes, this.getCredentialTypes());
               if (creds == null || creds.size() == 0) {
                  if (Debug.isSecurityCtxEnabled()) {
                     this.debug("Matching credentials not found for the pool, checking global mappings");
                  }

                  creds = this.getCredentials(cm, kernelId, (String)null, (AuthenticatedSubject)initiatingPrincipal, this.globalEISRes, this.getCredentialTypes());
               }
            }

            if (Debug.isSecurityCtxEnabled()) {
               if (creds != null && creds.size() != 0) {
                  this.debug("Using provided credentials for initiating principal:  " + this.getUserName(initiatingPrincipal));
               } else {
                  this.debug("No credentials explicitly provided for initiating principal: " + this.getUserName(initiatingPrincipal) + ".  Will attempt to find default.");
               }
            }
         } else if (Debug.isSecurityCtxEnabled()) {
            this.debug("No Credential Manager configured.  Server will not be able to provide any credentials.");
         }

         return creds;
      }
   }

   private String getUserName(Object principal) {
      if (principal instanceof String) {
         return (String)principal;
      } else {
         return principal instanceof AuthenticatedSubject ? SubjectUtils.getUsername((AuthenticatedSubject)principal) : principal.toString();
      }
   }

   private String[] getCredentialTypes() {
      List authMechanismList = this.outboundInfo.getAuthenticationMechanisms();
      if (authMechanismList != null && authMechanismList.size() != 0) {
         String[] credTypes = new String[authMechanismList.size()];
         Iterator iter = authMechanismList.iterator();

         for(int idx = 0; iter.hasNext(); ++idx) {
            AuthMechInfo authMechanism = (AuthMechInfo)iter.next();
            credTypes[idx] = authMechanism.getType();
            if (credTypes[idx].equalsIgnoreCase("BasicPassword")) {
               credTypes[idx] = "weblogic.UserPassword";
            }
         }

         return credTypes;
      } else {
         if (Debug.isSecurityCtxEnabled()) {
            this.debug("No authentication mechanisms were specified. Therefore no credential types can be attempted to be found.");
         }

         return null;
      }
   }

   private static Context getInitialContext() throws NamingException {
      if (initialContext == null) {
         initialContext = new InitialContext();
      }

      return initialContext;
   }
}
