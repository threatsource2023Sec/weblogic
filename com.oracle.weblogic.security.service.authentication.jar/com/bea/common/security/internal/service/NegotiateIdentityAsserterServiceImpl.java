package com.bea.common.security.internal.service;

import com.bea.common.engine.ServiceConfigurationException;
import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.engine.ServiceLifecycleSpi;
import com.bea.common.engine.Services;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.internal.utils.Delegator;
import com.bea.common.security.internal.utils.negotiate.NegotiateToken;
import com.bea.common.security.internal.utils.negotiate.SPNEGONegotiateToken;
import com.bea.common.security.service.ChallengeIdentityAssertionService;
import com.bea.common.security.service.Identity;
import com.bea.common.security.service.NegotiateIdentityAsserterService;
import com.bea.common.security.service.SessionService;
import com.bea.common.security.servicecfg.NegotiateIdentityAsserterServiceConfig;
import com.bea.security.utils.negotiate.CredentialObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.StringTokenizer;
import javax.security.auth.login.LoginException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import weblogic.security.service.ContextHandler;
import weblogic.security.spi.IdentityAssertionException;

public class NegotiateIdentityAsserterServiceImpl implements ServiceLifecycleSpi, NegotiateIdentityAsserterService {
   private LoggerSpi logger;
   private ChallengeIdentityAssertionService ciaService;
   private SessionService sessionService;
   private boolean formBasedAuthEnabled;
   private static final String WEBAPP_AUTHBASIC_NAME = "BASIC";
   private static final String WEBAPP_AUTHFORM_NAME = "FORM";
   private static final String WEBAPP_AUTHCERT_NAME = "CLIENT_CERT";
   private static final String WEBAPP_AUTHDIGEST_NAME = "DIGEST";
   private static final int WEBAPP_UNKNOWNAUTH = -1;
   private static final int WEBAPP_NOAUTH = 0;
   private static final int WEBAPP_AUTHBASIC = 1;
   private static final int WEBAPP_AUTHFORM = 2;
   private static final int WEBAPP_AUTHCERT = 3;
   private static final int WEBAPP_AUTHDIGEST = 4;
   private static final String ATTRIBUTE_NEGOTIATING = NegotiateIdentityAsserterServiceImpl.class.getName() + ".negotiateRequested";
   private static final String ATTRIBUTE_CONTEXT = NegotiateIdentityAsserterServiceImpl.class.getName() + ".negotiateContext";
   private static final String HEADER_AUTHORIZATION = "Authorization";
   private static final String HEADER_WWW_AUTHENTICATE = "WWW-Authenticate";
   private static final String BASIC = "Basic";
   private static final String NEGOTIATE = "Negotiate";

   public Object init(Object config, Services services) throws ServiceInitializationException {
      this.logger = ((LoggerService)services.getService(LoggerService.SERVICE_NAME)).getLogger("com.bea.common.security.service.NegotiateIdentityAsserterService");
      String method = this.getClass().getName() + ".init()";
      if (this.logger.isDebugEnabled()) {
         this.logger.debug(method);
      }

      if (config != null && config instanceof NegotiateIdentityAsserterServiceConfig) {
         NegotiateIdentityAsserterServiceConfig myconfig = (NegotiateIdentityAsserterServiceConfig)config;
         this.ciaService = (ChallengeIdentityAssertionService)this.safeGetService(services, myconfig.getChallengeIdentityAssertionServiceName(), method);
         this.sessionService = (SessionService)this.safeGetService(services, myconfig.getSessionServiceName(), method);
         return Delegator.getProxy(NegotiateIdentityAsserterService.class, this);
      } else {
         throw new ServiceConfigurationException(ServiceLogger.getExpectedConfigurationNotSupplied(method, "NegotiateIdentityAsserterServiceConfig"));
      }
   }

   private Object safeGetService(Services services, String name, String method) throws ServiceInitializationException {
      Object service = services.getService(name);
      if (service == null) {
         throw new ServiceConfigurationException(ServiceLogger.getServiceNotFound(name, method));
      } else {
         return service;
      }
   }

   public void shutdown() {
      if (this.logger.isDebugEnabled()) {
         this.logger.debug(this.getClass().getName() + ".shutdown()");
      }

   }

   public void process(ServletRequest req, ServletResponse res, FilterChain ch, NegotiateIdentityAsserterService.NegotiateIdentityAsserterCallback callback) throws IOException, ServletException {
      if (this.logger.isDebugEnabled()) {
         this.logger.debug("NegotiateIdentityAsserterServiceImpl.process() called");
      }

      if (req instanceof HttpServletRequest && res instanceof HttpServletResponse) {
         NegotiateHandler handler = new NegotiateHandler((HttpServletRequest)req, (HttpServletResponse)res, callback);
         if (!handler.process()) {
            this.callChain(req, res, ch);
         }

      } else {
         if (this.logger.isDebugEnabled()) {
            this.logger.debug("Not HTTP request/response Negotiate filter skipping");
         }

         this.callChain(req, res, ch);
      }
   }

   public void setFormBasedAuthEnabled(boolean enabled) {
      this.formBasedAuthEnabled = enabled;
   }

   private void callChain(ServletRequest req, ServletResponse res, FilterChain ch) throws IOException, ServletException {
      if (ch != null) {
         if (this.logger.isDebugEnabled()) {
            this.logger.debug("Passing to next filter in the chain");
         }

         ch.doFilter(req, res);
      } else if (this.logger.isDebugEnabled()) {
         this.logger.debug("No filter chain to pass to");
      }

   }

   private class DefaultNegotiateIdentityAsserterCallback implements NegotiateIdentityAsserterService.NegotiateIdentityAsserterCallback {
      private HttpSession session;

      public DefaultNegotiateIdentityAsserterCallback(HttpSession session) {
         this.session = session;
      }

      public String getWebAppAuthType(HttpServletRequest request) {
         return null;
      }

      public boolean isAlreadyAuthenticated() {
         return NegotiateIdentityAsserterServiceImpl.this.sessionService.getIdentity(this.session) != null;
      }

      public void userAuthenticated(Identity identity, HttpServletRequest request) {
         NegotiateIdentityAsserterServiceImpl.this.sessionService.setIdentity(this.session, identity);
      }
   }

   private class NegotiateHandler {
      private boolean hasAuthorizationHeader = false;
      private NegotiateToken negotiateToken = null;
      private HttpServletRequest hreq = null;
      private HttpServletResponse hres = null;
      private HttpSession session = null;
      private NegotiateIdentityAsserterService.NegotiateIdentityAsserterCallback callback;
      private List webAppAuthTypes = new ArrayList();

      public NegotiateHandler(HttpServletRequest req, HttpServletResponse resp, NegotiateIdentityAsserterService.NegotiateIdentityAsserterCallback callback) {
         this.hreq = req;
         this.hres = resp;
         this.session = req.getSession();
         if (callback == null) {
            this.callback = NegotiateIdentityAsserterServiceImpl.this.new DefaultNegotiateIdentityAsserterCallback(this.session);
         } else {
            this.callback = callback;
         }

      }

      public boolean process() throws IOException, ServletException {
         if (this.callback.isAlreadyAuthenticated()) {
            return false;
         } else {
            this.updateWebAppAuthTypes();
            if (!NegotiateIdentityAsserterServiceImpl.this.formBasedAuthEnabled && this.webAppAuthTypes.contains(2)) {
               if (NegotiateIdentityAsserterServiceImpl.this.logger.isDebugEnabled()) {
                  NegotiateIdentityAsserterServiceImpl.this.logger.debug("WebApp uses FORM auth and Negotiate filter is configured to skip FORM auth requests");
               }

               return false;
            } else {
               this.processHeaders();
               if (!this.isNegotiating() || this.hasAuthorizationHeader && this.negotiateToken != null) {
                  if (!this.hasAuthorizationHeader) {
                     return this.sendInitialChallenge();
                  } else {
                     return this.negotiateToken != null ? this.assertChallengeContext() : false;
                  }
               } else {
                  if (NegotiateIdentityAsserterServiceImpl.this.logger.isDebugEnabled()) {
                     NegotiateIdentityAsserterServiceImpl.this.logger.debug("Request doesn't have Negotiate response, Negotiate filter ignoring");
                  }

                  return false;
               }
            }
         }
      }

      private void logRequestHeaders() {
         if (NegotiateIdentityAsserterServiceImpl.this.logger.isDebugEnabled()) {
            NegotiateIdentityAsserterServiceImpl.this.logger.debug("All request headers:");
            Enumeration headerNames = this.hreq.getHeaderNames();
            if (headerNames == null) {
               NegotiateIdentityAsserterServiceImpl.this.logger.debug("No request header names were found");
            } else {
               while(headerNames.hasMoreElements()) {
                  String headerName = (String)headerNames.nextElement();
                  if (headerName != null) {
                     String headerValue = this.hreq.getHeader(headerName);
                     NegotiateIdentityAsserterServiceImpl.this.logger.debug("  Header: " + headerName + " : " + (headerValue == null ? "" : headerValue));
                  } else {
                     NegotiateIdentityAsserterServiceImpl.this.logger.debug("  null headerName found");
                  }
               }

            }
         }
      }

      private void processHeaders() {
         this.logRequestHeaders();
         Enumeration values = this.hreq.getHeaders("Authorization");
         if (values == null) {
            if (NegotiateIdentityAsserterServiceImpl.this.logger.isDebugEnabled()) {
               NegotiateIdentityAsserterServiceImpl.this.logger.debug("Authorization Header not found.");
            }

         } else {
            while(values.hasMoreElements()) {
               String current = (String)values.nextElement();
               if (current != null) {
                  if (NegotiateIdentityAsserterServiceImpl.this.logger.isDebugEnabled()) {
                     NegotiateIdentityAsserterServiceImpl.this.logger.debug("    processing header: " + current);
                  }

                  StringTokenizer st = new StringTokenizer(current);
                  String token = st.nextToken();
                  if ("Negotiate".equalsIgnoreCase(token)) {
                     this.hasAuthorizationHeader = true;
                     if (st.hasMoreTokens()) {
                        this.negotiateToken = NegotiateToken.getInstance(st.nextToken(), NegotiateIdentityAsserterServiceImpl.this.logger);
                     }

                     if (this.negotiateToken != null) {
                        if (this.negotiateToken.getTokenType() == 1) {
                           if (NegotiateIdentityAsserterServiceImpl.this.logger.isDebugEnabled()) {
                              NegotiateIdentityAsserterServiceImpl.this.logger.debug("Found Negotiate with SPNEGO token");
                           }

                           return;
                        }

                        if (NegotiateIdentityAsserterServiceImpl.this.logger.isDebugEnabled()) {
                           NegotiateIdentityAsserterServiceImpl.this.logger.debug("Token not supported by Negotiate Filter, ignoring: " + this.negotiateToken.getTokenTypeName());
                        }

                        this.negotiateToken = null;
                     }
                  } else if (NegotiateIdentityAsserterServiceImpl.this.logger.isDebugEnabled()) {
                     NegotiateIdentityAsserterServiceImpl.this.logger.debug("Authorization header for " + token + " not supported by Negotiate filter, ignoring");
                  }
               }
            }

         }
      }

      private boolean sendInitialChallenge() throws IOException {
         if (this.hres.isCommitted()) {
            if (NegotiateIdentityAsserterServiceImpl.this.logger.isDebugEnabled()) {
               NegotiateIdentityAsserterServiceImpl.this.logger.debug("Response was already committed, not responding with WWW-Authenticate: Negotiate");
            }

            return false;
         } else {
            try {
               NegotiateIdentityAsserterServiceImpl.this.ciaService.getChallengeToken("WWW-Authenticate.Negotiate");
               if (NegotiateIdentityAsserterServiceImpl.this.logger.isDebugEnabled()) {
                  NegotiateIdentityAsserterServiceImpl.this.logger.debug("Unauthorized, sending WWW-Authenticate: Negotiate");
               }

               this.session.setAttribute(NegotiateIdentityAsserterServiceImpl.ATTRIBUTE_NEGOTIATING, Boolean.TRUE);
               this.hres.addHeader("WWW-Authenticate", "Negotiate");
               if (this.webAppAuthTypes.contains(1)) {
                  if (NegotiateIdentityAsserterServiceImpl.this.logger.isDebugEnabled()) {
                     NegotiateIdentityAsserterServiceImpl.this.logger.debug("Including header for basic auth for enabling fallback if negotiate not supported");
                  }

                  this.hres.addHeader("WWW-Authenticate", "Basic");
               }

               this.hres.sendError(401);
               return true;
            } catch (IdentityAssertionException var2) {
               if (NegotiateIdentityAsserterServiceImpl.this.logger.isDebugEnabled()) {
                  NegotiateIdentityAsserterServiceImpl.this.logger.debug("Failure getting the initial Challenge Token", var2);
               }
            } catch (IllegalStateException var3) {
               if (NegotiateIdentityAsserterServiceImpl.this.logger.isDebugEnabled()) {
                  NegotiateIdentityAsserterServiceImpl.this.logger.debug("Failure setting session attribute or response", var3);
               }
            }

            return false;
         }
      }

      private boolean assertChallengeContext() throws IOException {
         ChallengeIdentityAssertionService.ChallengeContext theContext = (ChallengeIdentityAssertionService.ChallengeContext)this.session.getAttribute(NegotiateIdentityAsserterServiceImpl.ATTRIBUTE_CONTEXT);

         try {
            if (theContext == null) {
               theContext = NegotiateIdentityAsserterServiceImpl.this.ciaService.assertChallengeIdentity("Authorization.Negotiate", this.negotiateToken, (ContextHandler)null);
            } else {
               theContext.continueChallengeIdentity("Authorization.Negotiate", this.negotiateToken, (ContextHandler)null);
            }
         } catch (LoginException var6) {
            if (NegotiateIdentityAsserterServiceImpl.this.logger.isDebugEnabled()) {
               NegotiateIdentityAsserterServiceImpl.this.logger.debug("Exception when asserting ChallengeIdentity", var6);
            }

            return false;
         }

         if (theContext == null) {
            if (NegotiateIdentityAsserterServiceImpl.this.logger.isDebugEnabled()) {
               NegotiateIdentityAsserterServiceImpl.this.logger.debug("No ChallengeContext");
            }

            return false;
         } else {
            if (theContext.hasChallengeIdentityCompleted()) {
               if (NegotiateIdentityAsserterServiceImpl.this.logger.isDebugEnabled()) {
                  NegotiateIdentityAsserterServiceImpl.this.logger.debug("Challenge Identity has completed");
               }

               try {
                  Identity id = theContext.getIdentity();
                  SPNEGONegotiateToken spnegoToken = (SPNEGONegotiateToken)this.negotiateToken;
                  CredentialObject delegatedCredential = spnegoToken.getDelegatedCredential();
                  if (delegatedCredential != null) {
                     id.getSubject().getPrivateCredentials().add(delegatedCredential);
                  } else if (NegotiateIdentityAsserterServiceImpl.this.logger.isDebugEnabled()) {
                     NegotiateIdentityAsserterServiceImpl.this.logger.debug("No delegated subject was stored to the user identity.");
                  }

                  this.callback.userAuthenticated(theContext.getIdentity(), this.hreq);
                  if (spnegoToken.getContextFlagMutual()) {
                     if (NegotiateIdentityAsserterServiceImpl.this.logger.isDebugEnabled()) {
                        NegotiateIdentityAsserterServiceImpl.this.logger.debug("Context was marked for mutual auth, looking for output token to send");
                     }

                     String outputToken = spnegoToken.getOutputToken();
                     if (outputToken != null) {
                        if (NegotiateIdentityAsserterServiceImpl.this.logger.isDebugEnabled()) {
                           NegotiateIdentityAsserterServiceImpl.this.logger.debug("Context was marked for mutual auth, sending trailing SPNEGO NegTokenTarg token in WWW-Authenticate: Negotiate");
                        }

                        this.session.setAttribute(NegotiateIdentityAsserterServiceImpl.ATTRIBUTE_CONTEXT, theContext);
                        this.hres.addHeader("WWW-Authenticate", "Negotiate " + outputToken);
                     } else if (NegotiateIdentityAsserterServiceImpl.this.logger.isDebugEnabled()) {
                        NegotiateIdentityAsserterServiceImpl.this.logger.debug("Mutual auth was indicated, but no trailing token was found to send. Initiator may reject results");
                     }
                  } else if (NegotiateIdentityAsserterServiceImpl.this.logger.isDebugEnabled()) {
                     NegotiateIdentityAsserterServiceImpl.this.logger.debug("Mutual auth is not set, no trailing token needs to be sent");
                  }
               } catch (IllegalStateException var7) {
                  if (NegotiateIdentityAsserterServiceImpl.this.logger.isDebugEnabled()) {
                     NegotiateIdentityAsserterServiceImpl.this.logger.debug("Failed to get the authenticated subject", var7);
                  }
               }
            } else {
               if (!this.hres.isCommitted()) {
                  if (NegotiateIdentityAsserterServiceImpl.this.logger.isDebugEnabled()) {
                     NegotiateIdentityAsserterServiceImpl.this.logger.debug("continuing challenge, sending WWW-Authenticate: Negotiate");
                  }

                  Object challenge = theContext.getChallengeToken();
                  this.session.setAttribute(NegotiateIdentityAsserterServiceImpl.ATTRIBUTE_CONTEXT, theContext);
                  this.hres.addHeader("WWW-Authenticate", "Negotiate " + challenge);
                  if (this.webAppAuthTypes.contains(1)) {
                     if (NegotiateIdentityAsserterServiceImpl.this.logger.isDebugEnabled()) {
                        NegotiateIdentityAsserterServiceImpl.this.logger.debug("Including header for basic auth for enabling fallback if negotiate not supported");
                     }

                     this.hres.addHeader("WWW-Authenticate", "Basic");
                  }

                  this.hres.sendError(401);
                  return true;
               }

               if (NegotiateIdentityAsserterServiceImpl.this.logger.isDebugEnabled()) {
                  NegotiateIdentityAsserterServiceImpl.this.logger.debug("Unable to continue challenge, response already was committed");
               }
            }

            return false;
         }
      }

      private void updateWebAppAuthTypes() {
         String authType = this.callback.getWebAppAuthType(this.hreq);
         if (authType != null && !authType.isEmpty()) {
            String[] authTypes = authType.split(",");
            String[] var3 = authTypes;
            int var4 = authTypes.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               String expectedAuthType = var3[var5];
               if (expectedAuthType.equals("FORM")) {
                  if (NegotiateIdentityAsserterServiceImpl.this.logger.isDebugEnabled()) {
                     NegotiateIdentityAsserterServiceImpl.this.logger.debug("FORM auth type found for webapp");
                  }

                  this.webAppAuthTypes.add(2);
               } else if (expectedAuthType.equals("BASIC")) {
                  if (NegotiateIdentityAsserterServiceImpl.this.logger.isDebugEnabled()) {
                     NegotiateIdentityAsserterServiceImpl.this.logger.debug("BASIC auth type found for webapp");
                  }

                  this.webAppAuthTypes.add(1);
               } else if (expectedAuthType.equals("CLIENT_CERT")) {
                  if (NegotiateIdentityAsserterServiceImpl.this.logger.isDebugEnabled()) {
                     NegotiateIdentityAsserterServiceImpl.this.logger.debug("CERT auth type found for webapp");
                  }

                  this.webAppAuthTypes.add(3);
               } else if (expectedAuthType.equals("DIGEST")) {
                  if (NegotiateIdentityAsserterServiceImpl.this.logger.isDebugEnabled()) {
                     NegotiateIdentityAsserterServiceImpl.this.logger.debug("DIGEST auth type found for webapp");
                  }

                  this.webAppAuthTypes.add(4);
               } else {
                  if (NegotiateIdentityAsserterServiceImpl.this.logger.isDebugEnabled()) {
                     NegotiateIdentityAsserterServiceImpl.this.logger.debug("Auth type found for webapp didn't match known types: " + expectedAuthType);
                  }

                  this.webAppAuthTypes.add(-1);
               }
            }

         } else {
            if (NegotiateIdentityAsserterServiceImpl.this.logger.isDebugEnabled()) {
               NegotiateIdentityAsserterServiceImpl.this.logger.debug("No auth type found for webapp");
            }

            this.webAppAuthTypes.add(0);
         }
      }

      private boolean isNegotiating() {
         Boolean negotiating = (Boolean)this.session.getAttribute(NegotiateIdentityAsserterServiceImpl.ATTRIBUTE_NEGOTIATING);
         if (NegotiateIdentityAsserterServiceImpl.this.logger.isDebugEnabled()) {
            NegotiateIdentityAsserterServiceImpl.this.logger.debug("Negotiate filter:" + (negotiating == null ? " new session, no negotiation has started" : " existing session, negotiation was started"));
         }

         return negotiating != null;
      }
   }
}
