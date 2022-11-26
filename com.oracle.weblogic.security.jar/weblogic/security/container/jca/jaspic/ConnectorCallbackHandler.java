package weblogic.security.container.jca.jaspic;

import java.lang.annotation.Annotation;
import java.security.Principal;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.login.LoginException;
import javax.security.auth.message.callback.CallerPrincipalCallback;
import javax.security.auth.message.callback.GroupPrincipalCallback;
import javax.security.auth.message.callback.PasswordValidationCallback;
import weblogic.kernel.KernelStatus;
import weblogic.security.BaseCallbackHandler;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.debug.SecurityDebugLogger;
import weblogic.security.debug.SecurityLogger;
import weblogic.security.jaspic.SecurityServices;
import weblogic.security.jaspic.SecurityServicesImpl;
import weblogic.security.principal.PrincipalFactory;
import weblogic.security.spi.WLSGroup;
import weblogic.security.spi.WLSUser;
import weblogic.security.utils.PartitionUtils;
import weblogic.server.GlobalServiceLocator;

public class ConnectorCallbackHandler extends BaseCallbackHandler {
   private boolean callerPrincipalCallbackHandled;
   private boolean groupPrincipalCallbackHandled;
   private boolean passwordValidationCallbackHandled;
   private boolean authenticationResult;
   private LoginException authenticationException;
   private AuthenticatedSubject authenticatedSubject;
   private String authenticatedUsername;
   private String callerPrincipalName;
   private boolean groupsWereNulled;
   private EISPrincipalMapper mapper;
   private boolean virtual;
   private SecurityServices security;
   private SecurityLogger logger;
   private String identityDomain;
   private PrincipalFactory pf;

   public ConnectorCallbackHandler(EISPrincipalMapper mapper, boolean virtual) {
      this(mapper, virtual, (SecurityServices)GlobalServiceLocator.getServiceLocator().getService(SecurityServicesImpl.class, new Annotation[0]), new SecurityDebugLogger("DebugSecurityAtn"), (String)null, true);
   }

   ConnectorCallbackHandler(EISPrincipalMapper mapper, boolean virtual, SecurityServices security, SecurityLogger logger, String identityDomain, boolean getIddFromContext) {
      this.callerPrincipalCallbackHandled = false;
      this.groupPrincipalCallbackHandled = false;
      this.passwordValidationCallbackHandled = false;
      this.authenticationResult = false;
      this.authenticationException = null;
      this.authenticatedSubject = null;
      this.authenticatedUsername = null;
      this.callerPrincipalName = null;
      this.groupsWereNulled = false;
      this.mapper = null;
      this.virtual = false;
      this.security = null;
      this.logger = null;
      this.identityDomain = null;
      this.pf = PrincipalFactory.getInstance();
      this.mapper = mapper;
      this.virtual = virtual;
      this.security = security;
      this.logger = logger;
      this.identityDomain = getIddFromContext ? this.getIdentityDomain() : identityDomain;
      this.addCallbackStrategies(new BaseCallbackHandler.CallbackStrategy[]{new JcaCallerPrincipalCallbackStrategy(), new JcaGroupPrincipalCallbackStrategy(), new JcaPasswordValidationCallbackStrategy()});
   }

   public AuthenticatedSubject setupExecutionSubject(Subject executionSubject) throws LoginException {
      String errMsg;
      if (this.passwordValidationCallbackHandled && this.mapper != null) {
         errMsg = "setupExecutionSubject: PasswordValidationCallback handled while EIS caller/group principal mapping in effect";
         if (this.logger.isDebugEnabled()) {
            this.logger.debug(errMsg);
         }

         throw new LoginException(errMsg);
      } else if (this.passwordValidationCallbackHandled && this.virtual) {
         errMsg = "setupExecutionSubject: PasswordValidationCallback handled while virtual users enabled";
         if (this.logger.isDebugEnabled()) {
            this.logger.debug(errMsg);
         }

         throw new LoginException(errMsg);
      } else if (this.passwordValidationCallbackHandled && !this.authenticationResult) {
         errMsg = "setupExecutionSubject: PasswordValidationCallback did not succeed, exception: " + (this.authenticationException == null ? "" : this.authenticationException.toString());
         if (this.logger.isDebugEnabled()) {
            this.logger.debug(errMsg, this.authenticationException);
         }

         throw new LoginException(errMsg);
      } else if (this.passwordValidationCallbackHandled && !this.callerPrincipalCallbackHandled) {
         errMsg = "setupExecutionSubject: PasswordValidationCallback handled but not CallerPrincipalCallback";
         if (this.logger.isDebugEnabled()) {
            this.logger.debug(errMsg);
         }

         throw new LoginException(errMsg);
      } else if (!this.passwordValidationCallbackHandled || this.authenticatedUsername != null && this.authenticatedUsername.equals(this.callerPrincipalName)) {
         if (this.groupPrincipalCallbackHandled && !this.callerPrincipalCallbackHandled) {
            errMsg = "setupExecutionSubject: GroupPrincipalCallback handled but not CallerPrincipalCallback";
            if (this.logger.isDebugEnabled()) {
               this.logger.debug(errMsg);
            }

            throw new LoginException(errMsg);
         } else {
            String username;
            if (this.callerPrincipalCallbackHandled) {
               if (this.callerPrincipalName == null) {
                  if (executionSubject.getPrincipals().size() > 0) {
                     errMsg = "setupExecutionSubject: Found principals in execution Subject after handling anonymous CallerPrincipalCallback";
                     if (this.logger.isDebugEnabled()) {
                        this.logger.debug(errMsg);
                     }

                     throw new LoginException(errMsg);
                  }
               } else {
                  int numPrin = executionSubject.getPrincipals(WLSUser.class).size();
                  if (numPrin != 1) {
                     username = "setupExecutionSubject: Expected one WLSUser principal, found " + numPrin;
                     if (this.logger.isDebugEnabled()) {
                        this.logger.debug(username);
                     }

                     throw new LoginException(username);
                  }
               }
            }

            if (!this.callerPrincipalCallbackHandled) {
               if (executionSubject.getPrincipals().size() == 1) {
                  Principal p = (Principal)executionSubject.getPrincipals().iterator().next();
                  if (this.logger.isDebugEnabled()) {
                     this.logger.debug("setupExecutionContext: Case A: Principal:" + p.getName());
                  }

                  username = p.getName();
                  if (username != null && username.equals("")) {
                     username = null;
                  }

                  if (this.mapper != null) {
                     String oldName = username;
                     username = this.mapper.mapCallerPrincipal(username);
                     if (this.logger.isDebugEnabled()) {
                        this.logger.debug("setupExecutionContext: mapped EIS username [" + oldName + "] to WLS caller principal: [" + username + "]");
                     }

                     if (username != null && username.equals("")) {
                        username = null;
                     }
                  }

                  removeAllPrincipals(executionSubject, (Class)null);
                  if (username != null) {
                     executionSubject.getPrincipals().add(this.pf.createWLSUser(username, this.identityDomain));
                  }
               } else {
                  if (!executionSubject.getPrincipals().isEmpty()) {
                     errMsg = "setupExecutionContext: invalid executionSubject with CallerPrincipalCallback not handled";
                     if (this.logger.isDebugEnabled()) {
                        this.logger.debug(errMsg);
                     }

                     throw new LoginException(errMsg);
                  }

                  if (this.logger.isDebugEnabled()) {
                     this.logger.debug("setupExecutionContext: Case B: will set as anonymous");
                  }
               }
            }

            AuthenticatedSubject subject = this.convertToAuthenticatedSubject(executionSubject, this.authenticatedSubject);
            if (this.security.isAdminUser(subject)) {
               username = "setupExecutionContext: flown in user cannot be an administrator or have administrative roles";
               if (this.logger.isDebugEnabled()) {
                  this.logger.debug(username);
               }

               throw new LoginException(username);
            } else {
               return subject;
            }
         }
      } else {
         errMsg = "setupExecutionSubject: User authenticated by PasswordValidationCallback doesn't match CallerPrincipalCallback principal";
         if (this.logger.isDebugEnabled()) {
            this.logger.debug(errMsg);
         }

         throw new LoginException(errMsg);
      }
   }

   private static void removeAllPrincipals(Subject subject, Class principalClass) {
      if (principalClass == null) {
         subject.getPrincipals().clear();
      } else {
         Set principals = subject.getPrincipals(principalClass);
         subject.getPrincipals().removeAll(principals);
      }

   }

   private String getUsername(AuthenticatedSubject as) {
      return this.getUsername(as.getSubject());
   }

   private String getUsername(Subject s) {
      Set users = s.getPrincipals(WLSUser.class);
      String username = null;
      if (users.size() == 1) {
         username = ((WLSUser)users.iterator().next()).getName();
      }

      if (this.logger.isDebugEnabled()) {
         this.logger.debug("getUsername: returning username '" + username + "'");
      }

      return username;
   }

   private AuthenticatedSubject convertToAuthenticatedSubject(Subject executionSubject, AuthenticatedSubject authenticatedSubject) throws LoginException {
      if (executionSubject.getPrincipals().size() == 0) {
         return new AuthenticatedSubject(executionSubject);
      } else if (this.virtual) {
         this.security.signPrincipals(executionSubject.getPrincipals());
         return new AuthenticatedSubject(executionSubject);
      } else {
         Subject authSubject = null;
         if (authenticatedSubject != null) {
            authSubject = authenticatedSubject.getSubject();
         } else {
            AuthenticatedSubject as = this.security.impersonate(((WLSUser)executionSubject.getPrincipals(WLSUser.class).iterator().next()).getName());
            authSubject = as.getSubject();
         }

         Set esUsers = executionSubject.getPrincipals(WLSUser.class);
         Set esGroups = executionSubject.getPrincipals(WLSGroup.class);
         executionSubject.getPrincipals().removeAll(esUsers);
         executionSubject.getPrincipals().removeAll(esGroups);
         Set esOthers = executionSubject.getPrincipals();
         if (esOthers.size() > 0) {
            this.security.signPrincipals(esOthers);
         }

         Set asPrincipals = authSubject.getPrincipals(Principal.class);
         Set asUsers = authSubject.getPrincipals(WLSUser.class);
         asPrincipals.removeAll(asUsers);
         if (esUsers.size() == 1 && asUsers.size() == 1 && ((WLSUser)esUsers.iterator().next()).getName().equals(((WLSUser)asUsers.iterator().next()).getName())) {
            executionSubject.getPrincipals().add(asUsers.iterator().next());
            Set asGroups = authSubject.getPrincipals(WLSGroup.class);
            asPrincipals.removeAll(asGroups);
            if (esGroups.size() == 0) {
               if (!this.groupsWereNulled) {
                  executionSubject.getPrincipals().addAll(asGroups);
               }
            } else {
               Iterator var10 = esGroups.iterator();

               while(var10.hasNext()) {
                  WLSGroup esg = (WLSGroup)var10.next();
                  boolean match = false;
                  Iterator var13 = asGroups.iterator();

                  while(var13.hasNext()) {
                     WLSGroup asg = (WLSGroup)var13.next();
                     if (esg.getName().equals(asg.getName())) {
                        executionSubject.getPrincipals().add(asg);
                        match = true;
                        break;
                     }
                  }

                  if (!match) {
                     String errMsg = "convertToAuthenticatedSubject: execution Subject contains groups not found in the authenticated/impersonated Subject";
                     if (this.logger.isDebugEnabled()) {
                        this.logger.debug(errMsg);
                     }

                     throw new LoginException(errMsg);
                  }
               }
            }

            if (asPrincipals.size() > 0) {
               executionSubject.getPrincipals().addAll(asPrincipals);
            }

            Set pubCreds = authSubject.getPublicCredentials();
            if (pubCreds.size() > 0) {
               executionSubject.getPublicCredentials().addAll(pubCreds);
            }

            Set privCreds = authSubject.getPrivateCredentials();
            if (privCreds.size() > 0) {
               executionSubject.getPrivateCredentials().addAll(privCreds);
            }

            return new AuthenticatedSubject(executionSubject);
         } else {
            String errMsg = "convertToAuthenticatedSubject: execution Subject username doesn't match authenticated/impersonated username";
            if (this.logger.isDebugEnabled()) {
               this.logger.debug(errMsg);
            }

            throw new LoginException(errMsg);
         }
      }
   }

   private String getIdentityDomain() {
      return KernelStatus.isServer() ? PartitionUtils.getCurrentIdentityDomain() : null;
   }

   private class JcaPasswordValidationCallbackStrategy implements BaseCallbackHandler.CallbackStrategy {
      private JcaPasswordValidationCallbackStrategy() {
      }

      public boolean mayHandle(Callback callback) {
         return callback instanceof PasswordValidationCallback;
      }

      private void handleError(String errorString) {
         ConnectorCallbackHandler.this.authenticatedSubject = null;
         ConnectorCallbackHandler.this.authenticatedUsername = null;
         ConnectorCallbackHandler.this.authenticationResult = false;
         ConnectorCallbackHandler.this.authenticationException = new LoginException(errorString);
      }

      public void handle(Callback callback) {
         PasswordValidationCallback pvc = (PasswordValidationCallback)callback;
         String username = pvc.getUsername();
         char[] password = pvc.getPassword();
         ConnectorCallbackHandler.this.passwordValidationCallbackHandled = true;
         pvc.setResult(false);
         if (ConnectorCallbackHandler.this.mapper != null) {
            if (ConnectorCallbackHandler.this.logger.isDebugEnabled()) {
               ConnectorCallbackHandler.this.logger.debug("PasswordValidationCallback: error: cannot use PasswordValidationCallback when EIS caller/group principal mapping in effect");
            }

            this.handleError("PasswordValidationCallback: error: cannot use PasswordValidationCallback when EIS caller/group principal mapping in effect");
         } else if (ConnectorCallbackHandler.this.virtual) {
            if (ConnectorCallbackHandler.this.logger.isDebugEnabled()) {
               ConnectorCallbackHandler.this.logger.debug("PasswordValidationCallback: error: cannot use PasswordValidationCallback when virtual users enabled");
            }

            this.handleError("PasswordValidationCallback: error: cannot use PasswordValidationCallback when virtual users enabled");
         } else if (username != null && !username.equals("")) {
            if (password != null && password.length != 0) {
               if (ConnectorCallbackHandler.this.logger.isDebugEnabled()) {
                  ConnectorCallbackHandler.this.logger.debug("PasswordValidationCallback: will authenticate username: " + username + "; password len: " + password.length);
               }

               try {
                  ConnectorCallbackHandler.this.authenticatedSubject = ConnectorCallbackHandler.this.security.authenticate(username, password);
                  ConnectorCallbackHandler.this.authenticationResult = true;
                  ConnectorCallbackHandler.this.authenticatedUsername = ConnectorCallbackHandler.this.getUsername(ConnectorCallbackHandler.this.authenticatedSubject);
               } catch (LoginException var6) {
                  ConnectorCallbackHandler.this.authenticatedSubject = null;
                  ConnectorCallbackHandler.this.authenticatedUsername = null;
                  ConnectorCallbackHandler.this.authenticationResult = false;
                  ConnectorCallbackHandler.this.authenticationException = var6;
               }

               pvc.setResult(ConnectorCallbackHandler.this.authenticationResult);
               if (ConnectorCallbackHandler.this.logger.isDebugEnabled()) {
                  if (ConnectorCallbackHandler.this.authenticationResult) {
                     ConnectorCallbackHandler.this.logger.debug("PasswordValidationCallback: authenticate ok for username: " + username);
                     ConnectorCallbackHandler.this.logger.debug("PasswordValidationCallback: authenticatedSubject is: " + ConnectorCallbackHandler.this.authenticatedSubject);
                  } else {
                     ConnectorCallbackHandler.this.logger.debug("PasswordValidationCallback: authenticate failed for username: " + username, ConnectorCallbackHandler.this.authenticationException);
                  }
               }
            } else {
               if (ConnectorCallbackHandler.this.logger.isDebugEnabled()) {
                  ConnectorCallbackHandler.this.logger.debug("PasswordValidationCallback: error: must have valid password: [" + (password == null ? null : "len=" + password.length) + "]");
               }

               this.handleError("PasswordValidationCallback: error: must have valid password: [" + (password == null ? null : "len=" + password.length) + "]");
            }
         } else {
            if (ConnectorCallbackHandler.this.logger.isDebugEnabled()) {
               ConnectorCallbackHandler.this.logger.debug("PasswordValidationCallback: error: must have valid username: [" + username + "]");
            }

            this.handleError("PasswordValidationCallback: error: must have valid username: [" + username + "]");
         }

      }

      // $FF: synthetic method
      JcaPasswordValidationCallbackStrategy(Object x1) {
         this();
      }
   }

   private class JcaGroupPrincipalCallbackStrategy implements BaseCallbackHandler.CallbackStrategy {
      private JcaGroupPrincipalCallbackStrategy() {
      }

      public boolean mayHandle(Callback callback) {
         return callback instanceof GroupPrincipalCallback;
      }

      public void handle(Callback callback) {
         GroupPrincipalCallback gpc = (GroupPrincipalCallback)callback;
         Subject subject = gpc.getSubject();
         String[] groups = gpc.getGroups();
         ConnectorCallbackHandler.this.groupPrincipalCallbackHandled = true;
         ConnectorCallbackHandler.this.groupsWereNulled = false;
         if (ConnectorCallbackHandler.this.logger.isDebugEnabled()) {
            ConnectorCallbackHandler.this.logger.debug("GroupPrincipalCallback: Subject:" + subject + "; Groups:" + Arrays.toString(groups));
         }

         if (groups == null) {
            ConnectorCallbackHandler.removeAllPrincipals(subject, WLSGroup.class);
            ConnectorCallbackHandler.this.groupsWereNulled = true;
         } else {
            boolean foundone = false;
            String[] var6 = groups;
            int var7 = groups.length;

            for(int var8 = 0; var8 < var7; ++var8) {
               String group = var6[var8];
               if (group != null && group.equals("")) {
                  group = null;
               }

               if (ConnectorCallbackHandler.this.mapper != null) {
                  String oldName = group;
                  group = ConnectorCallbackHandler.this.mapper.mapGroupPrincipal(group);
                  if (ConnectorCallbackHandler.this.logger.isDebugEnabled()) {
                     ConnectorCallbackHandler.this.logger.debug("GroupPrincipalCallback: mapped EIS group [" + oldName + "] to WLS group principal: [" + group + "]");
                  }

                  if (group != null && group.equals("")) {
                     group = null;
                  }
               }

               if (group != null) {
                  subject.getPrincipals().add(ConnectorCallbackHandler.this.pf.createWLSGroup(group, ConnectorCallbackHandler.this.identityDomain));
                  foundone = true;
               }
            }

            if (!foundone) {
               ConnectorCallbackHandler.removeAllPrincipals(subject, WLSGroup.class);
            }
         }

      }

      // $FF: synthetic method
      JcaGroupPrincipalCallbackStrategy(Object x1) {
         this();
      }
   }

   private class JcaCallerPrincipalCallbackStrategy implements BaseCallbackHandler.CallbackStrategy {
      private JcaCallerPrincipalCallbackStrategy() {
      }

      public boolean mayHandle(Callback callback) {
         return callback instanceof CallerPrincipalCallback;
      }

      public void handle(Callback callback) {
         CallerPrincipalCallback cpc = (CallerPrincipalCallback)callback;
         Subject subject = cpc.getSubject();
         String username = cpc.getName();
         ConnectorCallbackHandler.this.callerPrincipalCallbackHandled = true;
         if (ConnectorCallbackHandler.this.logger.isDebugEnabled()) {
            ConnectorCallbackHandler.this.logger.debug("CallerPrincipalCallback: Subject: " + subject + "; Principal: " + cpc.getPrincipal() + "; Name: " + cpc.getName());
         }

         if (username == null || username.equals("")) {
            Principal p = cpc.getPrincipal();
            if (p != null) {
               username = p.getName();
            }

            if (username != null && username.equals("")) {
               username = null;
            }
         }

         if (ConnectorCallbackHandler.this.mapper != null) {
            String oldName = username;
            username = ConnectorCallbackHandler.this.mapper.mapCallerPrincipal(username);
            if (ConnectorCallbackHandler.this.logger.isDebugEnabled()) {
               ConnectorCallbackHandler.this.logger.debug("CallerPrincipalCallback: mapped EIS username [" + oldName + "] to WLS caller principal: [" + username + "]");
            }

            if (username != null && username.equals("")) {
               username = null;
            }
         }

         ConnectorCallbackHandler.this.callerPrincipalName = username;
         if (username == null) {
            ConnectorCallbackHandler.removeAllPrincipals(subject, (Class)null);
         } else {
            ConnectorCallbackHandler.removeAllPrincipals(subject, WLSUser.class);
            subject.getPrincipals().add(ConnectorCallbackHandler.this.pf.createWLSUser(username, ConnectorCallbackHandler.this.identityDomain));
         }

      }

      // $FF: synthetic method
      JcaCallerPrincipalCallbackStrategy(Object x1) {
         this();
      }
   }

   public interface EISPrincipalMapper {
      String mapCallerPrincipal(String var1);

      String mapGroupPrincipal(String var1);
   }
}
