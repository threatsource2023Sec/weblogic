package weblogic.security.service;

import com.bea.common.security.jdkutils.JAASConfiguration;
import com.bea.common.security.service.ChallengeIdentityAssertionService;
import com.bea.common.security.service.IdentityAssertionService;
import com.bea.common.security.service.IdentityImpersonationService;
import com.bea.common.security.service.JAASAuthenticationService;
import com.bea.common.security.service.PrincipalValidationService;
import com.bea.security.css.CSS;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.Configuration;
import javax.security.auth.login.LoginException;
import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.security.ProviderMBean;
import weblogic.management.security.RealmMBean;
import weblogic.management.security.authentication.IdentityAsserterMBean;
import weblogic.management.security.authentication.UserLockoutManagerMBean;
import weblogic.rmi.spi.HostID;
import weblogic.security.SecurityLogger;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.internal.SecurityMessage;
import weblogic.security.service.internal.ServletAuthenticationFilterService;
import weblogic.security.service.internal.UserLockoutAdministrationService;
import weblogic.security.service.internal.UserLockoutCoordinationService;
import weblogic.security.service.internal.WLSIdentityImpl;
import weblogic.security.service.internal.WSPasswordDigestService;
import weblogic.security.shared.LoggerWrapper;
import weblogic.security.spi.AuthenticationProviderV2;
import weblogic.security.spi.ChallengeIdentityAsserterV2;
import weblogic.security.spi.DigestNotAvailableException;
import weblogic.security.spi.IdentityAsserterV2;
import weblogic.security.spi.IdentityAssertionException;
import weblogic.utils.StringUtils;

public class PrincipalAuthenticatorImpl implements SecurityService, PrincipalAuthenticator {
   private PrincipalValidationService pvalService = null;
   private JAASAuthenticationService jaasAtnService = null;
   private IdentityAssertionService iaService = null;
   private ChallengeIdentityAssertionService chIAService = null;
   private IdentityImpersonationService imService = null;
   private ServletAuthenticationFilterService filtService = null;
   private WSPasswordDigestService digService = null;
   private UserLockoutAdministrationService ulaService = null;
   private UserLockoutCoordinationService ulcService = null;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   protected static final String assertionTag = "$$ASSERTION$$";
   private static LoggerWrapper log = LoggerWrapper.getInstance("SecurityAtn");
   private Map assertionEncodingMap;
   private volatile Map[] assertionEncodingPrecedence;
   private DescriptorBean bean;
   private BeanUpdateListener beanListener;
   private UserLockoutManager userLockoutManager;
   private static final String SUN_CONFIG_FILE = "com.sun.security.auth.login.ConfigFile";
   private static final String IBM_CONFIG_FILE = "com.ibm.security.auth.login.ConfigFile";
   private static final String AUTH_CONFIG_FILE = "weblogic.security.authentication.Configuration";

   private void assertNotUsingCommon() {
      throw new AssertionError("This code should never be called when using common security");
   }

   public void initialize(RealmServices realmServices, ProviderMBean[] providerMBean) {
      if (providerMBean != null && providerMBean.length != 0) {
         RealmMBean realmMBean = providerMBean[0].getRealm();
         if (log.isDebugEnabled()) {
            log.debug("PrincipalAuthenticator will use common security for ATN");
         }

         String userLockoutManagerMBean;
         try {
            userLockoutManagerMBean = System.getProperty("java.security.auth.login.config");
            if (userLockoutManagerMBean != null && log.isDebugEnabled()) {
               log.debug("Registering JAAS configs java.security.auth.login.config: " + userLockoutManagerMBean);
            }

            if (realmMBean.isDefaultRealm()) {
               Configuration config = this.loadConfFileForJVM("com.sun.security.auth.login.ConfigFile");
               if (config != null) {
                  JAASConfiguration.registerConfiguration(config);
               }
            }

            CSS css = realmServices.getCSS();
            this.jaasAtnService = (JAASAuthenticationService)css.getService("JAASAuthenticationService");
            this.iaService = (IdentityAssertionService)css.getService("IdentityAssertionService");
            this.chIAService = (ChallengeIdentityAssertionService)css.getService("ChallengeIdentityAssertionService");
            this.imService = (IdentityImpersonationService)css.getService("ImpersonationService");
            this.pvalService = (PrincipalValidationService)css.getService("PrincipalValidationService");
            this.filtService = (ServletAuthenticationFilterService)css.getService("ServletAuthenticationFilterService");
            this.digService = (WSPasswordDigestService)css.getService("WSPasswordDigestService");
            this.ulaService = (UserLockoutAdministrationService)css.getService("UserLockoutAdministrationService");
            this.ulcService = (UserLockoutCoordinationService)css.getService("UserLockoutCoordinationService");
            this.calculateLegacyAssertionEncodingMap(providerMBean);
            this.calculateAssertionsEncodingPrecedence(realmMBean);
            if (log.isDebugEnabled()) {
               log.debug("PrincipalAuthenticator Assertions Encoding Map: " + this.assertionEncodingMap.toString());
               log.debug("PrincipalAuthenticator Assertions Encoding Precedence: " + Arrays.toString(this.assertionEncodingPrecedence));
            }

            this.createBeanUpdateListener(realmMBean);
         } catch (Exception var6) {
            if (log.isDebugEnabled()) {
               SecurityLogger.logStackTrace(var6);
            }

            SecurityServiceRuntimeException ssre = new SecurityServiceRuntimeException(SecurityLogger.getExceptionObtainingService("Common JAASAuthenticationService", var6.toString()));
            ssre.initCause(var6);
            throw ssre;
         }

         userLockoutManagerMBean = null;
         if (realmMBean != null) {
            UserLockoutManagerMBean userLockoutManagerMBean = realmMBean.getUserLockoutManager();
            if (userLockoutManagerMBean != null) {
               UserLockoutManagerImpl ulmImpl = new UserLockoutManagerImpl();
               ulmImpl.init(realmServices, userLockoutManagerMBean);
               this.userLockoutManager = ulmImpl;
            }
         }

      } else {
         throw new SecurityServiceRuntimeException(SecurityLogger.getNoAuthMBeansInvConfig());
      }
   }

   private Configuration loadConfFileForJVM(String configFile) {
      Configuration jaasConfFile = null;

      try {
         jaasConfFile = (Configuration)this.getClass().getClassLoader().loadClass(configFile).newInstance();
         JAASConfiguration.setJAASConfigFile(configFile);
      } catch (InstantiationException var4) {
         if (log.isDebugEnabled()) {
            log.debug("Reflection issue, check message for details: " + var4.getMessage());
         }
      } catch (IllegalAccessException var5) {
         if (log.isDebugEnabled()) {
            log.debug("Reflection issue, check message for details: " + var5.getMessage());
         }
      } catch (ClassNotFoundException var6) {
         if (configFile.equals("com.sun.security.auth.login.ConfigFile")) {
            jaasConfFile = this.loadConfFileForJVM("com.ibm.security.auth.login.ConfigFile");
            return jaasConfFile;
         }

         if (configFile.equals("com.ibm.security.auth.login.ConfigFile")) {
            configFile = System.getProperty("weblogic.security.authentication.Configuration");
            if (configFile != null && configFile.length() >= 0) {
               jaasConfFile = this.loadConfFileForJVM(configFile);
               return jaasConfFile;
            }
         }

         if (log.isDebugEnabled()) {
            log.debug("Failed to load SUN, IBM or custom JAAS file..  " + configFile);
         }

         SecurityLogger.logWarningFailedToLoadJAASConfiguration();
      } catch (SecurityException var7) {
         if (var7.getCause() instanceof IOException) {
            if (log.isDebugEnabled()) {
               log.debug("Setting JAAS config file: " + configFile);
            }

            JAASConfiguration.setJAASConfigFile(configFile);
         } else if (log.isDebugEnabled()) {
            log.debug("Failed to load configuration: " + var7.getMessage());
         }
      } catch (IllegalArgumentException var8) {
         if (log.isDebugEnabled()) {
            log.debug("Failed to load configuration: " + var8.getMessage());
            log.debug("Setting JAAS config file " + configFile);
         }

         JAASConfiguration.setJAASConfigFile(configFile);
      }

      return jaasConfFile;
   }

   public void start() {
   }

   public void suspend() {
   }

   public void shutdown() {
      this.pvalService = null;
      this.jaasAtnService = null;
      this.iaService = null;
      this.chIAService = null;
      this.imService = null;
      this.filtService = null;
      this.digService = null;
      this.ulcService = null;
      this.ulaService = null;
      this.removeBeanUpdateListener();
   }

   public PrincipalAuthenticatorImpl() {
      this.assertionEncodingMap = new TreeMap(String.CASE_INSENSITIVE_ORDER);
      this.assertionEncodingPrecedence = null;
      this.bean = null;
      this.beanListener = null;
   }

   public PrincipalAuthenticatorImpl(RealmServices realmServices, ProviderMBean[] providerMBean) {
      this.assertionEncodingMap = new TreeMap(String.CASE_INSENSITIVE_ORDER);
      this.assertionEncodingPrecedence = null;
      this.bean = null;
      this.beanListener = null;
      this.initialize(realmServices, providerMBean);
   }

   public AuthenticatedSubject authenticate(CallbackHandler callbackHandler, ContextHandler contextHandler) throws LoginException {
      if (callbackHandler == null) {
         throw new LoginException(SecurityLogger.getNoCallbackHandlerSuppliedPA());
      } else {
         if (log.isDebugEnabled()) {
            log.debug("PrincipalAuthenticator.authenticate");
         }

         if (log.isDebugEnabled()) {
            log.debug("PrincipalAuthenticator.authenticate will use common security service");
         }

         return IdentityUtility.identityToAuthenticatedSubject(this.jaasAtnService.authenticate(callbackHandler, contextHandler));
      }
   }

   public AuthenticatedSubject authenticate(CallbackHandler callbackHandler) throws LoginException {
      return this.authenticate(callbackHandler, (ContextHandler)null);
   }

   public Map getAssertionsEncodingMap() {
      return this.assertionEncodingMap;
   }

   public Map[] getAssertionsEncodingPrecedence() {
      return this.assertionEncodingPrecedence;
   }

   public boolean doesTokenTypeRequireBase64Decoding(String tokenType) {
      Boolean value = (Boolean)this.assertionEncodingMap.get(tokenType);
      if (value == null) {
         throw new IllegalArgumentException(SecurityLogger.getUnknownTokenType(tokenType));
      } else {
         return value;
      }
   }

   public boolean doesTokenRequireBase64Decoding(Object o) {
      return (Boolean)o;
   }

   public AuthenticatedSubject assertIdentity(String tokenType, Object token) throws LoginException {
      return this.assertIdentity(tokenType, token, (ContextHandler)null);
   }

   public AuthenticatedSubject assertIdentity(String tokenType, Object token, ContextHandler contextHandler) throws LoginException {
      if (log.isDebugEnabled()) {
         log.debug("PrincipalAuthenticator.assertIdentity - Token Type: " + tokenType);
      }

      if (tokenType == null) {
         throw new LoginException(SecurityLogger.getNullTokenTypeParam());
      } else if (token == null) {
         throw new LoginException(SecurityLogger.getNullTokenParam());
      } else {
         if (log.isDebugEnabled()) {
            log.debug("PrincipalAuthenticator.assertIdentity using common security");
         }

         return IdentityUtility.identityToAuthenticatedSubject(this.iaService.assertIdentity(tokenType, token, contextHandler));
      }
   }

   public boolean isTokenTypeSupported(String tokenType) {
      if (tokenType == null) {
         return false;
      } else {
         if (log.isDebugEnabled()) {
            log.debug("PrincipalAuthenticator.isTokenSupported using common security");
         }

         return this.iaService.isTokenTypeSupported(tokenType);
      }
   }

   public AuthenticatedSubject impersonateIdentity(String username) throws LoginException {
      return this.impersonateIdentity(username, (ContextHandler)null);
   }

   public AuthenticatedSubject impersonateIdentity(String username, ContextHandler contextHandler) throws LoginException {
      if (log.isDebugEnabled()) {
         log.debug("PrincipalAuthenticator.impersonateIdentity using common security");
      }

      return this.impersonateIdentity(new ImpersonationCallbackHandler(username, contextHandler), false, contextHandler);
   }

   public AuthenticatedSubject impersonateIdentity(CallbackHandler cbh, boolean virtual, ContextHandler contextHandler) throws LoginException {
      if (log.isDebugEnabled()) {
         log.debug("PrincipalAuthenticator.impersonateIdentity using common security");
      }

      return IdentityUtility.identityToAuthenticatedSubject(this.imService.impersonateIdentity(cbh, virtual, contextHandler));
   }

   public boolean validateIdentity(AuthenticatedSubject subject) {
      if (subject == null) {
         return false;
      } else {
         if (log.isDebugEnabled()) {
            log.debug("PrincipalAuthenticator.validateIdentity");
         }

         if (log.isDebugEnabled()) {
            log.debug("PrincipalAuthenticator.validateIdentity will use common security service");
         }

         return this.pvalService.validate(subject.getSubject().getPrincipals(), subject.getSerializedIdentityDomain());
      }
   }

   public Object getChallengeToken(String tokenType, ContextHandler handler) throws IdentityAssertionException {
      if (log.isDebugEnabled()) {
         log.debug("PrincipalAuthenticator.getChallengeToken will use common security service");
      }

      return this.chIAService.getChallengeToken(tokenType, handler);
   }

   public Object getChallengeToken(String tokenType) throws IdentityAssertionException {
      return this.getChallengeToken(tokenType, (ContextHandler)null);
   }

   public ChallengeContext assertChallengeIdentity(String tokenType, Object token, ContextHandler contextHandler) throws LoginException {
      if (log.isDebugEnabled()) {
         log.debug("PrincipalAuthenticator.assertChallengeIdentity - Token Type: " + tokenType);
      }

      if (log.isDebugEnabled()) {
         log.debug("PrincipalAuthenticator.assertChallengeIdentity will use common security service");
      }

      return new CommonChallengeContextImpl(this.chIAService.assertChallengeIdentity(tokenType, token, contextHandler));
   }

   public void continueChallengeIdentity(ChallengeContext context, String tokenType, Object token, ContextHandler handler) throws LoginException {
      if (log.isDebugEnabled()) {
         log.debug("PrincipalAuthenticator.continueChallengeIdentity - Token Type: " + tokenType);
      }

      if (log.isDebugEnabled()) {
         log.debug("PrincipalAuthenticator.assertChallengeIdentity will use common security service");
      }

      if (!(context instanceof CommonChallengeContextImpl)) {
         throw new LoginException(SecurityLogger.getNotInstanceof("CommonChallengeContextImpl"));
      } else {
         ((CommonChallengeContextImpl)context).continueChallengeIdentity(tokenType, token, handler);
      }
   }

   public Filter[] getServletAuthenticationFilters(ServletContext ctx) throws ServletException {
      if (log.isDebugEnabled()) {
         log.debug("Beginning getServletAuthenticationFilters.");
      }

      if (log.isDebugEnabled()) {
         log.debug("PrincipalAuthenticator.getServletAuthenticationFilters will use WLS-specific security service");
      }

      return this.filtService.getServletAuthenticationFilters(ctx);
   }

   public void destroyServletAuthenticationFilters(Filter[] filters) {
      this.filtService.destroyServletAuthenticationFilters(filters);
   }

   public byte[] getPasswordDigest(String username, byte[] nonce, String created) throws DigestNotAvailableException {
      if (log.isDebugEnabled()) {
         log.debug("PrincipalAuthenticator.getPasswordDigest will use WLS-specific security service");
      }

      return this.digService.getPasswordDigest(username, nonce, created);
   }

   public byte[] getDerivedKey(String username, byte[] salt, int iteration) throws DigestNotAvailableException {
      if (log.isDebugEnabled()) {
         log.debug("PrincipalAuthenticator.getDerivedKey will use WLS-specific security service");
      }

      return this.digService.getDerivedKey(username, salt, iteration);
   }

   protected AuthenticationProviderV2[] getProviderList() {
      this.assertNotUsingCommon();
      return null;
   }

   public UserLockoutManager getUserLockoutManager() {
      return this.userLockoutManager;
   }

   public UserLockoutAdministrationService getUserLockoutAdministrationService() {
      return this.ulaService;
   }

   public void receiveSecurityMessageCommon(HostID sender, SecurityMessage sm) {
      this.ulcService.processSecurityMessage(sm.nextSeqNo(), sm.record());
   }

   private ChallengeIdentityAsserterV2 getChallengeIdentityAsserter(IdentityAsserterV2 asserter) {
      this.assertNotUsingCommon();
      return null;
   }

   private String[] getBASE64EncodingExceptions(IdentityAsserterMBean asserter) {
      try {
         Method method = asserter.getClass().getMethod("getBASE64EncodingExceptions");
         if (method != null) {
            if (log.isDebugEnabled()) {
               log.debug("Asserter " + asserter.getClass() + " has method getBASE64EncodingExceptions.");
            }

            Object ret = method.invoke(asserter);
            if (ret instanceof String[]) {
               return (String[])((String[])ret);
            }
         }
      } catch (NoSuchMethodException var4) {
      } catch (Exception var5) {
         if (log.isDebugEnabled()) {
            log.debug("PrincipalAuthenticatorImpl.getBASE64EncodingExceptions() catch exception!", var5);
         }
      }

      return new String[0];
   }

   private void calculateLegacyAssertionEncodingMap(ProviderMBean[] providerMBean) {
      for(int i = 0; i < providerMBean.length; ++i) {
         if (providerMBean[i] instanceof IdentityAsserterMBean) {
            IdentityAsserterMBean asserter = (IdentityAsserterMBean)providerMBean[i];
            String[] activeType = asserter.getActiveTypes();

            for(int j = 0; activeType != null && j < activeType.length; ++j) {
               if (!this.assertionEncodingMap.containsKey(activeType[j])) {
                  if (asserter.getBase64DecodingRequired() && !Arrays.asList(this.getBASE64EncodingExceptions(asserter)).contains(activeType[j])) {
                     this.assertionEncodingMap.put(activeType[j], Boolean.TRUE);
                  } else {
                     this.assertionEncodingMap.put(activeType[j], Boolean.FALSE);
                  }
               }
            }
         }
      }

   }

   private void calculateAssertionsEncodingPrecedence(RealmMBean realmMBean) {
      String[] iaHeaderNamePrecedence = realmMBean.getIdentityAssertionHeaderNamePrecedence();
      if (log.isDebugEnabled()) {
         log.debug("PrincipalAuthenticatorImpl.calculateAssertionsEncodingPrecedence() from: " + Arrays.toString(iaHeaderNamePrecedence));
      }

      if (iaHeaderNamePrecedence != null && iaHeaderNamePrecedence.length != 0) {
         ArrayList orderedList = new ArrayList();

         for(int i = 0; i < iaHeaderNamePrecedence.length; ++i) {
            String entry = iaHeaderNamePrecedence[i].trim();
            if (!entry.isEmpty()) {
               String[] headerEntry = StringUtils.split(entry, ':');
               String header = headerEntry[0].trim();
               String scheme = headerEntry[1].trim();
               if (!header.isEmpty()) {
                  Map mapEntry = new HashMap();
                  mapEntry.put("header", header);
                  if (!scheme.isEmpty()) {
                     mapEntry.put("scheme", scheme);
                  }

                  orderedList.add(mapEntry);
               }
            }
         }

         if (orderedList.size() <= 0) {
            this.assertionEncodingPrecedence = null;
         } else {
            this.assertionEncodingPrecedence = (Map[])orderedList.toArray(new Map[orderedList.size()]);
         }
      } else {
         this.assertionEncodingPrecedence = null;
      }
   }

   private void createBeanUpdateListener(RealmMBean realmMBean) {
      this.beanListener = new BeanUpdateListener() {
         public void prepareUpdate(BeanUpdateEvent event) throws BeanUpdateRejectedException {
         }

         public void activateUpdate(BeanUpdateEvent event) {
            if (event.getProposedBean() instanceof RealmMBean) {
               boolean processedPrecedence = false;
               RealmMBean mBean = (RealmMBean)event.getProposedBean();
               BeanUpdateEvent.PropertyUpdate[] updates = event.getUpdateList();

               for(int i = 0; i < updates.length; ++i) {
                  String propertyName = updates[i].getPropertyName();
                  if (!processedPrecedence && propertyName.equalsIgnoreCase("IdentityAssertionHeaderNamePrecedence")) {
                     processedPrecedence = true;
                     PrincipalAuthenticatorImpl.this.calculateAssertionsEncodingPrecedence(mBean);
                     if (PrincipalAuthenticatorImpl.log.isDebugEnabled()) {
                        PrincipalAuthenticatorImpl.log.debug("PrincipalAuthenticator activateUpdate() completed, the Assertions Encoding Precedence: " + Arrays.toString(PrincipalAuthenticatorImpl.this.assertionEncodingPrecedence));
                     }
                  }
               }

            }
         }

         public void rollbackUpdate(BeanUpdateEvent event) {
         }
      };
      this.bean = realmMBean;
      this.bean.addBeanUpdateListener(this.beanListener);
   }

   private void removeBeanUpdateListener() {
      if (this.bean != null && this.beanListener != null) {
         this.bean.removeBeanUpdateListener(this.beanListener);
      }

      this.beanListener = null;
      this.bean = null;
   }

   private static final class CommonChallengeContextImpl implements ChallengeContext, Serializable {
      private ChallengeIdentityAssertionService.ChallengeContext commonContext;

      private CommonChallengeContextImpl(ChallengeIdentityAssertionService.ChallengeContext commonContext) {
         this.commonContext = commonContext;
      }

      public boolean hasChallengeIdentityCompleted() {
         return this.commonContext.hasChallengeIdentityCompleted();
      }

      public AuthenticatedSubject getAuthenticatedSubject() {
         WLSIdentityImpl identity = (WLSIdentityImpl)this.commonContext.getIdentity();
         return identity == null ? null : identity.getAuthenticatedSubject();
      }

      public Object getChallengeToken() {
         return this.commonContext.getChallengeToken();
      }

      private void continueChallengeIdentity(String tokenType, Object token, ContextHandler handler) throws LoginException {
         this.commonContext.continueChallengeIdentity(tokenType, token, handler);
      }

      // $FF: synthetic method
      CommonChallengeContextImpl(ChallengeIdentityAssertionService.ChallengeContext x0, Object x1) {
         this(x0);
      }
   }
}
