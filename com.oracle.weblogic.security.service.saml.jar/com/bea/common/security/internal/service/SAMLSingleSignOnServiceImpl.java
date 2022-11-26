package com.bea.common.security.internal.service;

import com.bea.common.engine.ServiceConfigurationException;
import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.engine.ServiceLifecycleSpi;
import com.bea.common.engine.Services;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.internal.utils.Delegator;
import com.bea.common.security.legacy.spi.SAMLSingleSignOnServiceConfigInfoSpi;
import com.bea.common.security.saml.registry.SAMLAssertingPartyConfig;
import com.bea.common.security.saml.registry.SAMLRelyingPartyConfig;
import com.bea.common.security.saml.service.SAMLDestinationSiteHelper;
import com.bea.common.security.saml.service.SAMLSourceSiteHelper;
import com.bea.common.security.saml.utils.SAMLUtil;
import com.bea.common.security.service.AuditService;
import com.bea.common.security.service.CredentialMappingService;
import com.bea.common.security.service.Identity;
import com.bea.common.security.service.IdentityAssertionService;
import com.bea.common.security.service.IdentityService;
import com.bea.common.security.service.SAMLKeyService;
import com.bea.common.security.service.SAMLSingleSignOnService;
import com.bea.common.security.service.SessionService;
import com.bea.common.security.servicecfg.SAMLSingleSignOnServiceConfig;
import java.io.IOException;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SAMLSingleSignOnServiceImpl implements ServiceLifecycleSpi, SAMLSingleSignOnService {
   private static final String BASIC_AUTH_HEADER = "Authorization";
   private static final String BASIC_AUTH_TOKEN = "Basic";
   private static final String X509CERT_ATTRIBUTE = "javax.servlet.request.X509Certificate";
   private static boolean ALLOW_ACS_AUTH_SESSION_REDIRECT = Boolean.getBoolean("weblogic.security.saml.acs.allowAuthSessionRedirect");
   private LoggerSpi log;
   private AuditService auditService;
   private IdentityService identityService;
   private SessionService sessionService;
   private CredentialMappingService cmService;
   private IdentityAssertionService iaService;
   private SAMLKeyService keyService;
   private SAMLSingleSignOnServiceConfigInfoSpi ssoServiceConfig;
   private SAMLSourceSiteHelper ssHelper;
   private SAMLDestinationSiteHelper dsHelper;

   public Object init(Object config, Services dependentServices) throws ServiceInitializationException {
      LoggerService loggerService = (LoggerService)dependentServices.getService(LoggerService.SERVICE_NAME);
      this.log = loggerService.getLogger("SecuritySAMLService");
      boolean debug = this.log.isDebugEnabled();
      String method = this.getClass().getName() + ".init";
      if (debug) {
         this.log.debug(method);
      }

      if (config != null && config instanceof SAMLSingleSignOnServiceConfig) {
         SAMLSingleSignOnServiceConfig svcConfig = (SAMLSingleSignOnServiceConfig)config;
         this.auditService = (AuditService)this.getService(svcConfig.getAuditServiceName(), dependentServices, method);
         this.identityService = (IdentityService)this.getService(svcConfig.getIdentityServiceName(), dependentServices, method);
         this.sessionService = (SessionService)this.getService(svcConfig.getSessionServiceName(), dependentServices, method);
         if (svcConfig.getCredMappingServiceName() != null) {
            this.cmService = (CredentialMappingService)this.getService(svcConfig.getCredMappingServiceName(), dependentServices, method);
         }

         if (svcConfig.getIdentityAssertionServiceName() != null) {
            this.iaService = (IdentityAssertionService)this.getService(svcConfig.getIdentityAssertionServiceName(), dependentServices, method);
         }

         this.keyService = (SAMLKeyService)this.getService(svcConfig.getSAMLKeyServiceName(), dependentServices, method);
         this.ssoServiceConfig = svcConfig.getSAMLSingleSignOnServiceConfigInfo();

         try {
            this.ssHelper = new SAMLSourceSiteHelper(this.ssoServiceConfig, this.cmService, this.log, this.keyService);
            this.dsHelper = new SAMLDestinationSiteHelper(this.ssoServiceConfig, this.iaService, this.sessionService, this.log, this.keyService);
         } catch (Exception var8) {
            throw new ServiceInitializationException(var8);
         }

         return Delegator.getProxy(SAMLSingleSignOnService.class, this);
      } else {
         throw new ServiceConfigurationException(ServiceLogger.getExpectedConfigurationNotSupplied(method, "SAMLSingleSignOnServiceConfig"));
      }
   }

   public void shutdown() {
      if (this.log.isDebugEnabled()) {
         this.log.debug(this.getClass().getName() + ".shutdown");
      }

      if (this.ssoServiceConfig != null) {
         this.ssoServiceConfig.close();
      }

   }

   private Object getService(String serviceName, Services dependentServices, String method) throws ServiceInitializationException {
      Object service = dependentServices.getService(serviceName);
      if (this.log.isDebugEnabled()) {
         this.log.debug(method + " got " + dependentServices.getServiceLoggingName(serviceName));
      }

      return service;
   }

   private boolean checkACSAuthentication(HttpServletRequest req) throws ServletException, IOException {
      Identity currentUser = this.identityService.getCurrentIdentity();
      if (currentUser != null && !currentUser.isAnonymous()) {
         return true;
      } else {
         currentUser = this.sessionService.getIdentity(req.getSession());
         return currentUser != null && !currentUser.isAnonymous();
      }
   }

   private boolean checkRedirectTargetOnly(HttpServletRequest req) throws ServletException, IOException {
      String method = "SAMLSingleSignOnService.checkRedirectTargetOnly: ";
      boolean debug = this.log.isDebugEnabled();
      if (!ALLOW_ACS_AUTH_SESSION_REDIRECT) {
         if (debug) {
            this.log.debug(method + "ACS auth session redirect is not enabled");
         }

         return false;
      } else if (this.ssoServiceConfig.isACSRequiresSSL() && !req.isSecure()) {
         if (debug) {
            this.log.debug(method + "ACS requires SSL, but the request is not secure");
         }

         return false;
      } else {
         if (req.getMethod().compareToIgnoreCase("POST") == 0) {
            if (!this.ssoServiceConfig.isACSPostEnabled()) {
               if (debug) {
                  this.log.debug(method + "POST request while POST profile not enabled");
               }

               return false;
            }
         } else {
            if (req.getMethod().compareToIgnoreCase("GET") != 0) {
               if (debug) {
                  this.log.debug(method + "Got request with method '" + req.getMethod() + "'");
               }

               return false;
            }

            if (!this.ssoServiceConfig.isACSArtifactEnabled()) {
               if (debug) {
                  this.log.debug(method + "GET request while Artifact profile not enabled");
               }

               return false;
            }
         }

         return true;
      }
   }

   public void doACSGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      String method = "SAMLSingleSignOnService.doACSGet: ";
      boolean debug = this.log.isDebugEnabled();
      if (this.checkACSAuthentication(req)) {
         if (this.checkRedirectTargetOnly(req)) {
            if (debug) {
               this.log.debug(method + "ACS already authenticated, redirecting to target only");
            }

            this.dsHelper.doTargetRedirect(req, resp);
         } else {
            if (debug) {
               this.log.debug(method + " Unexpected GET for ACS service, returning NOT_FOUND");
            }

            resp.sendError(404);
         }
      } else if (this.ssoServiceConfig.isACSRequiresSSL() && !req.isSecure()) {
         if (debug) {
            this.log.debug(method + "ACS requires SSL, but the request is not secure");
         }

         resp.sendError(403);
      } else {
         String apid = req.getParameter("APID");
         if (apid != null) {
            apid = apid.trim();
            if (apid.length() == 0) {
               if (debug) {
                  this.log.debug(method + "Invalid (empty) APID parameter, returning BAD_REQUEST");
               }

               resp.sendError(400);
               return;
            }
         }

         if (apid == null && this.ssoServiceConfig.isV2Config()) {
            if (debug) {
               this.log.debug(method + "No APID parameter, returning BAD_REQUEST");
            }

            resp.sendError(400);
         } else {
            SAMLAssertingPartyConfig partner = null;
            if (apid != null) {
               partner = this.dsHelper.lookupPartner(apid);
            }

            String assertion = this.dsHelper.getAssertion(partner, req, resp);
            if (assertion == null) {
               if (debug) {
                  this.log.debug(method + "Failed to get SAML credentials -- returning");
               }

            } else if (!this.dsHelper.doLogin(partner, assertion, req, resp)) {
               if (debug) {
                  this.log.debug(method + "Login failed, returning");
               }

            } else {
               if (debug) {
                  this.log.debug(method + "Login succeeded, redirecting to target");
               }

               this.dsHelper.doTargetRedirect(req, resp);
            }
         }
      }
   }

   public void doACSPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      this.doACSGet(req, resp);
   }

   public void doARSPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      String method = "SAMLSingleSignOnService.doARSPost: ";
      boolean debug = this.log.isDebugEnabled();
      if (!this.ssoServiceConfig.isSourceSiteEnabled()) {
         if (debug) {
            this.log.debug(method + "request while Source Site not enabled, returning NOT_FOUND");
         }

         resp.sendError(404);
      } else {
         boolean isSecure = false;
         X509Certificate clientCert = null;
         if (req.isSecure()) {
            isSecure = true;
            if (debug) {
               this.log.debug(method + "request is on a secure channel");
            }

            X509Certificate[] certs = (X509Certificate[])((X509Certificate[])req.getAttribute("javax.servlet.request.X509Certificate"));
            if (certs != null && certs.length > 0) {
               if (debug) {
                  this.log.debug(method + "request: client cert chain found");

                  for(int i = 0; i < certs.length && certs[i] != null; ++i) {
                     this.log.debug(method + "cert[" + i + "] subject DN: " + certs[i].getSubjectDN().getName());
                     this.log.debug(method + "cert[" + i + "] issuer DN: " + certs[i].getIssuerDN().getName());
                  }
               }

               clientCert = certs[0];
            } else if (debug) {
               this.log.debug(method + "request: client cert chain not found");
            }
         } else if (debug) {
            this.log.debug(method + "request is not on a secure channel");
         }

         String basicAuth = req.getHeader("Authorization");
         if (basicAuth != null) {
            String[] tokens = basicAuth.split("\\s");
            if (tokens.length != 2 || !tokens[0].equals("Basic")) {
               if (debug) {
                  this.log.debug(method + "Invalid " + "Authorization" + " header '" + basicAuth + "' found in ARS request, returning BAD_REQUEST");
               }

               resp.sendError(400);
               return;
            }

            basicAuth = tokens[1];
         }

         if (this.ssoServiceConfig.isARSRequiresSSL() && !isSecure) {
            if (debug) {
               this.log.debug(method + "Non-secure ARS request but SSL required, returning NOT_FOUND");
            }

            resp.sendError(404);
         } else if (this.ssoServiceConfig.isARSRequiresTwoWaySSL() && clientCert == null) {
            if (debug) {
               this.log.debug(method + "No client certificate for ARS request but two-way SSL required, returning FORBIDDEN");
            }

            resp.sendError(403);
         } else {
            if (debug) {
               this.log.debug(method + "Dispatching assertion request");
            }

            this.ssHelper.dispatchAssertionRequest(req, resp, clientCert, basicAuth);
         }
      }
   }

   public void doITSGet(HttpServletRequest req, HttpServletResponse resp, ServletContext context) throws ServletException, IOException {
      String method = "SAMLSingleSignOnService.doITSGet: ";
      boolean debug = this.log.isDebugEnabled();
      String requestURI = req.getRequestURI();
      if (debug) {
         this.log.debug(method + "Request URI is '" + requestURI + "'");
      }

      String contextURI = req.getContextPath();
      String servletURI = requestURI.substring(contextURI.length());
      if (debug) {
         this.log.debug(method + "Servlet URI is '" + servletURI + "'");
      }

      if (!this.ssoServiceConfig.isSourceSiteEnabled()) {
         if (debug) {
            this.log.debug(method + "request while Source Site not enabled, returning NOT_FOUND");
         }

         resp.sendError(404);
      } else if (this.ssoServiceConfig.isITSRequiresSSL() && !req.isSecure()) {
         if (debug) {
            this.log.debug(method + "Non-secure ITS request but SSL required, returning FORBIDDEN");
         }

         resp.sendError(403);
      } else {
         Identity currentUser = this.identityService.getCurrentIdentity();
         if (currentUser == null) {
            currentUser = this.sessionService.getIdentity(req.getSession());
         }

         if (currentUser != null && !currentUser.isAnonymous()) {
            String rpid = req.getParameter("RPID");
            if (rpid != null) {
               rpid = rpid.trim();
               if (rpid.length() == 0) {
                  if (debug) {
                     this.log.debug(method + "Invalid (empty) RPID parameter, returning BAD_REQUEST");
                  }

                  resp.sendError(400);
                  return;
               }
            }

            if (rpid == null && this.ssoServiceConfig.isV2Config()) {
               if (debug) {
                  this.log.debug(method + "No RPID parameter, returning BAD_REQUEST");
               }

               resp.sendError(400);
            } else {
               String targetParam = req.getParameter("TARGET");
               String targetURL = SAMLUtil.normalizeURL(targetParam);
               if (targetURL == null) {
                  if (debug) {
                     this.log.debug(method + (targetParam == null ? "No TARGET parameter" : "Invalid TARGET parameter '" + targetParam + "'"));
                  }

                  resp.sendError(400);
               } else {
                  SAMLRelyingPartyConfig partner = this.ssHelper.lookupPartner(rpid, targetURL, servletURI);
                  if (partner == null) {
                     if (debug) {
                        this.log.debug(method + "Partner not found, returning FORBIDDEN");
                     }

                     resp.sendError(403);
                  } else {
                     String badURI = this.ssHelper.validateRequestURI(partner, servletURI);
                     if (badURI != null) {
                        if (debug) {
                           this.log.debug(method + partner.getProfile() + " request received on " + badURI + " URI '" + servletURI + "', returning BAD_REQUEST");
                        }

                        resp.sendError(400);
                     } else {
                        if (partner.getProfileConfMethodName().equals("bearer")) {
                           if (debug) {
                              this.log.debug(method + "Dispatching POST request");
                           }

                           this.ssHelper.dispatchPOSTRequest(partner, currentUser, targetURL, req, resp, context);
                        } else if (partner.getProfileConfMethodName().equals("artifact")) {
                           if (debug) {
                              this.log.debug(method + "Dispatching Artifact request");
                           }

                           this.ssHelper.dispatchArtifactRequest(partner, currentUser, targetURL, req, resp);
                        } else {
                           if (debug) {
                              this.log.debug(method + "Unsupported confirmation method '" + partner.getProfileConfMethodName() + "' for relying party '" + partner.getPartnerId() + "', returning FORBIDDEN");
                           }

                           resp.sendError(403);
                        }

                     }
                  }
               }
            }
         } else {
            if (debug) {
               this.log.debug(method + "Attempt to access ITS by unauthenticated user");
            }

            resp.sendError(403);
         }
      }
   }

   public void doRedirectFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
      String method = "SAMLSingleSignOnService.doRedirectFilter: ";
      if (req instanceof HttpServletRequest && resp instanceof HttpServletResponse) {
         HttpServletRequest httpRequest = (HttpServletRequest)req;
         HttpServletResponse httpResponse = (HttpServletResponse)resp;
         if (!this.dsHelper.doSourceSiteRedirect(httpRequest, httpResponse)) {
            if (this.log.isDebugEnabled()) {
               this.log.debug(method + "Ignoring request not on consumer URL or redirect URL");
            }

            this.callChain(req, resp, chain);
         }
      } else {
         if (this.log.isDebugEnabled()) {
            this.log.debug(method + "Ignoring non-HTTP servlet request");
         }

         this.callChain(req, resp, chain);
      }
   }

   private void callChain(ServletRequest req, ServletResponse res, FilterChain ch) throws IOException, ServletException {
      if (ch != null) {
         this.log.debug("SAMLSingleSignOnService passing to next filter in the chain");
         ch.doFilter(req, res);
      } else {
         this.log.debug("SAMLSingleSignOnService no filter chain");
      }

   }

   private void dumpRequest(HttpServletRequest req) {
      if (this.log != null && this.log.isDebugEnabled()) {
         Map params = req.getParameterMap();
         if (params == null) {
            this.log.debug("Request parameter map is null");
            return;
         }

         Set entries = params.entrySet();
         Iterator it = entries.iterator();

         while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            this.log.debug("Outputting param: " + (String)entry.getKey() + "=" + (String)entry.getValue());
         }
      }

   }
}
