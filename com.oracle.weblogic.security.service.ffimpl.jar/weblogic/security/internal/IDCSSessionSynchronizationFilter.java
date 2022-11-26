package weblogic.security.internal;

import java.io.IOException;
import java.security.Principal;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import weblogic.security.principal.IdentityDomainPrincipal;
import weblogic.security.providers.authentication.IDCSFilterAccess;
import weblogic.security.providers.authentication.IDCSFilterService;
import weblogic.security.shared.LoggerWrapper;
import weblogic.servlet.security.ServletAuthentication;

public class IDCSSessionSynchronizationFilter implements Filter {
   private String idcsFilterServiceKey = null;
   private static LoggerWrapper LOGGER = LoggerWrapper.getInstance("SecurityAtn");
   public static final String IDCSFILTER_SERVICE_KEY_PARAM = "FilterServiceKey";
   private static final String USER_KEY = "USER";
   private static final String TENANT_KEY = "TENANT";

   private boolean isDebugEnabled() {
      return LOGGER != null ? LOGGER.isDebugEnabled() : false;
   }

   public void init(FilterConfig filterConfig) throws ServletException {
      this.idcsFilterServiceKey = filterConfig.getInitParameter("FilterServiceKey");
      if (this.idcsFilterServiceKey == null) {
         throw new ServletException("No FilterServiceKey parameter specified");
      } else {
         IDCSFilterService idcsFilterService = IDCSFilterAccess.getInstance().getFilterService(this.idcsFilterServiceKey);
         if (idcsFilterService == null) {
            if (this.isDebugEnabled()) {
               LOGGER.debug("No IDCSIntegratorProvider configured in realm " + this.idcsFilterServiceKey);
            }

         }
      }
   }

   public void destroy() {
   }

   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
      IDCSFilterService idcsFilterService = IDCSFilterAccess.getInstance().getFilterService(this.idcsFilterServiceKey);
      if (idcsFilterService != null && idcsFilterService.isSyncFilterEnabled()) {
         HttpServletRequest hrequest = (HttpServletRequest)request;
         String currentSessionUser = null;
         String currentUserTenant = null;
         String remoteUser = null;
         String remoteTenant = null;
         Map remoteUserTenant = null;
         boolean isClientCertOnly = idcsFilterService.getSyncFilterClientCertOnly();
         boolean isClientCert = this.isClientCert(hrequest);
         if (isClientCertOnly && !isClientCert) {
            if (this.isDebugEnabled()) {
               LOGGER.debug("Ignoring synchronization check as request authentication type is not CLIENT CERT.");
            }

            chain.doFilter(request, response);
         } else {
            boolean matchcase = idcsFilterService.getSyncFilterMatchCase();
            boolean isAuthenticated = this.isAuthenticated(hrequest);
            if (!isAuthenticated) {
               if (this.isDebugEnabled()) {
                  LOGGER.debug("Ignoring synchronization check as request contains no authenticated Principal.");
               }

               chain.doFilter(request, response);
            } else {
               try {
                  remoteUserTenant = idcsFilterService.getRemoteUserTenant(hrequest);
                  if (remoteUserTenant != null) {
                     remoteUser = (String)remoteUserTenant.get("USER");
                     remoteTenant = (String)remoteUserTenant.get("TENANT");
                  }

                  if (this.isDebugEnabled()) {
                     LOGGER.debug("IDCS remote user in the request: " + remoteUser + " remote tenant: " + remoteTenant);
                  }
               } catch (Exception var17) {
                  if (this.isDebugEnabled()) {
                     LOGGER.debug("Exception retrieving remote user from the request header: " + var17.getMessage());
                  }
               }

               label127: {
                  if (null == remoteUser) {
                     if (isAuthenticated && isClientCert && idcsFilterService.isIDCSSession(hrequest)) {
                        if (this.isDebugEnabled()) {
                           LOGGER.debug("Authenticated and session available but remote user is null, invalidate session.");
                        }

                        this.invalidateSessionAndRedirect(hrequest, response);
                        return;
                     }
                  } else {
                     Principal principal = hrequest.getUserPrincipal();
                     currentSessionUser = principal.getName();
                     if (principal instanceof IdentityDomainPrincipal) {
                        currentUserTenant = ((IdentityDomainPrincipal)principal).getIdentityDomain();
                     }

                     if (this.isDebugEnabled()) {
                        LOGGER.debug("Synchronization check on request, current user: " + currentSessionUser + " current user tenant: " + currentUserTenant);
                     }

                     if (remoteTenant != null && currentUserTenant != null) {
                        boolean matchTenant = remoteTenant.equalsIgnoreCase(currentUserTenant);
                        if (!matchTenant) {
                           if (this.isDebugEnabled()) {
                              LOGGER.debug("remote user tenant " + remoteTenant + " does not match current user tenant " + currentUserTenant);
                           }

                           this.invalidateSessionAndRedirect(hrequest, response);
                           return;
                        }
                     } else if (remoteTenant != null || currentUserTenant != null) {
                        if (this.isDebugEnabled()) {
                           LOGGER.debug("remote user tenant " + remoteTenant + " does not match current user tenant " + currentUserTenant);
                        }

                        this.invalidateSessionAndRedirect(hrequest, response);
                        return;
                     }

                     if (remoteUser != null) {
                        if (matchcase) {
                           if (!remoteUser.equals(currentSessionUser)) {
                              break label127;
                           }
                        } else if (!remoteUser.equalsIgnoreCase(currentSessionUser)) {
                           break label127;
                        }
                     }
                  }

                  if (this.isDebugEnabled()) {
                     LOGGER.debug("Request has valid session, exiting IDCS filter");
                  }

                  chain.doFilter(request, response);
                  return;
               }

               if (this.isDebugEnabled()) {
                  LOGGER.debug("remote user " + remoteUser + " does not match current session user " + currentSessionUser);
               }

               this.invalidateSessionAndRedirect(hrequest, response);
            }
         }
      } else {
         if (this.isDebugEnabled()) {
            LOGGER.debug("Ignoring synchronization check since IDCS sync filter is not enabled. ");
         }

         chain.doFilter(request, response);
      }
   }

   private boolean isClientCert(HttpServletRequest request) {
      String authType = request.getAuthType();
      if (null == authType) {
         return false;
      } else {
         return authType.toUpperCase().contains("CLIENT_CERT") || authType.toUpperCase().contains("CLIENT-CERT");
      }
   }

   private boolean isAuthenticated(HttpServletRequest request) {
      return null != request.getUserPrincipal();
   }

   private void invalidateSessionAndRedirect(HttpServletRequest hrequest, ServletResponse response) throws IOException {
      String inSessionUser = null == hrequest.getUserPrincipal() ? null : hrequest.getUserPrincipal().getName();
      HttpSession session = hrequest.getSession(false);
      if (null != session) {
         if (this.isDebugEnabled()) {
            LOGGER.debug("Invalidating Session = " + session.getId());
         }
      } else {
         if (this.isDebugEnabled()) {
            LOGGER.debug("Unexpected request with JSESSIONID, but no session. Invalidating the JSESSIONID cookie");
         }

         try {
            ServletAuthentication.killCookie(hrequest);
         } catch (Exception var8) {
            if (this.isDebugEnabled()) {
               LOGGER.debug("Failed to invalidate JSESSIONID cookie " + var8.getMessage());
            }
         }
      }

      if (hrequest.isRequestedSessionIdValid()) {
         if (ServletAuthentication.invalidateAll(hrequest)) {
            if (this.isDebugEnabled()) {
               LOGGER.debug("Invalidated the Lingering Session for user " + inSessionUser);
            } else if (this.isDebugEnabled()) {
               LOGGER.debug("Invalidating session failed for user " + inSessionUser);
            }
         }
      } else if (this.isDebugEnabled()) {
         LOGGER.debug("Session already invalidated for user " + inSessionUser);
      }

      String query = hrequest.getQueryString();
      String redirectURL = hrequest.getRequestURL().toString();
      if (null != query) {
         redirectURL = redirectURL + "?" + query;
      }

      if (this.isDebugEnabled()) {
         LOGGER.debug("Request URI: " + hrequest.getRequestURI());
         LOGGER.debug("Request QueryString: " + hrequest.getQueryString());
         LOGGER.debug("Redirecting to: " + redirectURL);
      }

      HttpServletResponse hresponse = (HttpServletResponse)response;
      hresponse.sendRedirect(redirectURL);
   }
}
