package weblogic.servlet.security.internal;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import weblogic.security.auth.callback.IdentityDomainNamesEncoder;
import weblogic.servlet.HTTPLogger;
import weblogic.servlet.internal.ForwardAction;
import weblogic.servlet.internal.MaxPostSizeExceededException;
import weblogic.servlet.internal.RedirectStatus;
import weblogic.servlet.internal.ServletRequestImpl;
import weblogic.servlet.internal.ServletResponseImpl;
import weblogic.servlet.internal.WebAppServletContext;
import weblogic.servlet.spi.SubjectHandle;

final class FormSecurityModule extends SecurityModule {
   private SubjectHandle currentUser = null;
   private int maxSavePostSize = 0;
   private RefererValidationType refererValidationType;

   public FormSecurityModule(ServletSecurityContext ctx, WebAppSecurity was) {
      super(was, ctx);
      WebAppServletContext webAppServletContext = (WebAppServletContext)ctx.getServletContext();
      this.maxSavePostSize = webAppServletContext.getSessionConfigManager().getMaxSavePostSize();
      this.maxSavePostSize = this.maxSavePostSize < 0 ? 0 : this.maxSavePostSize;
      this.refererValidationType = webAppServletContext.getConfigManager().getRefererValidationType();
   }

   protected boolean checkAccess(HttpServletRequest req, HttpServletResponse rsp, SessionSecurityData session, ResourceConstraint cons, boolean applyAuthFilters) throws IOException, ServletException {
      String requestURI = req.getRequestURI();
      boolean isJSecurityCheck = this.isJSecurityCheck(req);
      if (session != null) {
         this.currentUser = getCurrentUser(this.getSecurityContext(), req, session);
         if (isJSecurityCheck) {
            SecurityModule.logout(this.getSecurityContext(), session);
         }
      }

      this.recoverSavedPostData(req, session);
      String relUri = WebAppSecurity.getRelativeURI(req);
      if (cons == null && !this.webAppSecurity.isFullSecurityDelegationRequired() && !isJSecurityCheck && !this.getSecurityContext().isAdminMode() && !this.needToCheckAuthCookie(req, this.getSecurityContext(), session)) {
         if (DEBUG_SEC.isDebugEnabled()) {
            DEBUG_SEC.debug(this.getSecurityContext().getLogContext() + ": user: " + req.getUserPrincipal() + " has permissions to access " + req);
         }

         return true;
      } else if (!this.webAppSecurity.checkTransport(cons, req, rsp)) {
         return false;
      } else if (!relUri.equals(this.webAppSecurity.getLoginPage()) && !relUri.equals(this.webAppSecurity.getErrorPage())) {
         if (!this.webAppSecurity.isFullSecurityDelegationRequired() && cons != null && cons.isUnrestricted() && !isJSecurityCheck && !this.getSecurityContext().isAdminMode() && !this.needToCheckAuthCookie(req, this.getSecurityContext(), session)) {
            if (DEBUG_SEC.isDebugEnabled()) {
               DEBUG_SEC.debug(this.getSecurityContext().getLogContext() + ": " + req.getUserPrincipal() + " has permissions to access " + req);
            }

            return true;
         } else {
            SubjectHandle subject = getCurrentUser(this.getSecurityContext(), req, session);
            if (!this.checkUserPerm(req, rsp, session, cons, subject, applyAuthFilters)) {
               return false;
            } else {
               if (subject != null && session != null) {
                  session = getUserSession(req, false);
                  session.removeInternalAttribute("weblogic.formauth.immediate");
               }

               return true;
            }
         }
      } else {
         if (DEBUG_SEC.isDebugEnabled()) {
            DEBUG_SEC.debug(this.getSecurityContext().getLogContext() + ": user: " + req.getUserPrincipal() + " has permissions to access " + req);
         }

         return true;
      }
   }

   private void recoverSavedPostData(HttpServletRequest req, SessionSecurityData session) {
      if (session != null) {
         Object postCookie;
         try {
            postCookie = session.getInternalAttribute("weblogic.formauth.postcookie");
         } catch (IllegalStateException var11) {
            postCookie = null;
         }

         if (postCookie == null) {
            String meth = (String)session.getInternalAttribute("weblogic.formauth.method");
            if (meth != null && meth.equals("GET")) {
               session.removeInternalAttribute("weblogic.formauth.method");
            }

         } else {
            session.removeInternalAttribute("weblogic.formauth.method");
            session.removeInternalAttribute("weblogic.formauth.postcookie");
            byte[] inBytes = (byte[])((byte[])session.getInternalAttribute("weblogic.formauth.bytearray"));
            session.removeInternalAttribute("weblogic.formauth.bytearray");
            this.getRequestFacade().setRequestData(req, inBytes);
            ArrayList headerNames = (ArrayList)session.getInternalAttribute("weblogic.formauth.reqheadernames");
            if (headerNames != null) {
               ArrayList headerValues = (ArrayList)session.getInternalAttribute("weblogic.formauth.reqheadervalues");
               byte[] cookieHeader = this.getRequestFacade().getCookieHeader(req);
               int size;
               if (cookieHeader != null) {
                  size = headerNames.size();
                  boolean replaced = false;

                  for(int i = 0; i < size; ++i) {
                     if ("Cookie".startsWith((String)headerNames.get(i))) {
                        headerValues.set(i, cookieHeader);
                        replaced = true;
                        break;
                     }
                  }

                  if (!replaced) {
                     headerNames.add("Cookie");
                     headerValues.add(cookieHeader);
                  }
               }

               session.removeInternalAttribute("weblogic.formauth.reqheadernames");
               session.removeInternalAttribute("weblogic.formauth.reqheadervalues");
               if (inBytes == null || inBytes.length == 0) {
                  size = headerNames.size();

                  for(int i = 0; i < size; ++i) {
                     String headerName = (String)headerNames.get(i);
                     if ("Content-Length".equalsIgnoreCase(headerName)) {
                        headerValues.set(i, String.valueOf(0).getBytes());
                        break;
                     }
                  }
               }

               this.getRequestFacade().replaceRequestHeaders(req, headerNames, headerValues);
            }

         }
      }
   }

   protected boolean checkUserPerm(HttpServletRequest req, HttpServletResponse rsp, SessionSecurityData session, ResourceConstraint cons, SubjectHandle waFormAuthUser, boolean applyAuthFilters) throws IOException, ServletException {
      if (this.isJSecurityCheck(req)) {
         return this.processJSecurityCheck(req, rsp, session);
      } else if (waFormAuthUser != null) {
         return this.processLoggedInUser(req, rsp, waFormAuthUser);
      } else if (this.webAppSecurity.isFullSecurityDelegationRequired() && this.webAppSecurity.hasPermission(req, rsp, (SubjectHandle)null, cons)) {
         return true;
      } else if (applyAuthFilters && this.webAppSecurity.hasAuthFilters()) {
         this.webAppSecurity.invokeAuthFilterChain(req, rsp);
         return false;
      } else {
         if (this.isForbidden(cons)) {
            this.sendForbiddenResponse(req, rsp);
         } else {
            this.sendLoginPage(req, rsp);
         }

         return false;
      }
   }

   private boolean isJSecurityCheck(HttpServletRequest req) {
      String uri = req.getRequestURI();
      int pos = uri.indexOf(59);
      if (pos > 0) {
         uri = uri.substring(0, pos);
      }

      return uri.endsWith("j_security_check");
   }

   private boolean processJSecurityCheck(HttpServletRequest req, HttpServletResponse rsp, SessionSecurityData session) throws IOException {
      if (!"POST".equalsIgnoreCase(req.getMethod())) {
         rsp.setStatus(405);
         rsp.setHeader("Allow", "POST");
         this.sendErrorPage(req, rsp);
         return false;
      } else if (!this.checkRefererHeader(req, rsp)) {
         this.sendError(req, rsp);
         return false;
      } else {
         String waFormCharacterEncoding = req.getParameter("j_character_encoding");
         if (waFormCharacterEncoding != null) {
            try {
               if (Charset.isSupported(waFormCharacterEncoding)) {
                  req.setCharacterEncoding(waFormCharacterEncoding);
               }
            } catch (IllegalCharsetNameException var13) {
            }
         }

         String waFormUserName = req.getParameter("j_username");
         String waFormUserPswd = req.getParameter("j_password");
         if (waFormUserName != null && waFormUserPswd != null) {
            String waFormUserIDD = req.getParameter("wls_identity_domain");
            if (waFormUserIDD != null && !waFormUserIDD.isEmpty()) {
               waFormUserName = IdentityDomainNamesEncoder.encodeNames(waFormUserName, waFormUserIDD);
            }

            SubjectHandle waFormAuthUser = checkAuthenticate(this.getSecurityContext(), req, rsp, waFormUserName, waFormUserPswd, true);
            if (waFormAuthUser == null) {
               this.sendError(req, rsp);
               return false;
            } else {
               req.setAttribute("weblogic.auth.result", 0);
               String storedUrl = null;
               if (session != null) {
                  if (this.getSecurityContext().isRetainOriginalURL()) {
                     storedUrl = (String)session.getInternalAttribute("weblogic.formauth.targeturl");
                  } else {
                     storedUrl = (String)session.getInternalAttribute("weblogic.formauth.targeturi");
                  }
               }

               if (this.currentUser != null && !this.currentUser.isAnonymous() && !this.currentUser.getUsername().equals(waFormUserName) && this.getSecurityContext().isInvalidateOnRelogin()) {
                  if (session != null && session.isValid()) {
                     session.invalidate();
                  }

                  session = null;
               }

               this.login(req, waFormAuthUser, session);
               String origMethod;
               if (storedUrl == null) {
                  origMethod = req.getRequestURI();
                  int contextPathLen = req.getContextPath().length();
                  String welcomeFile;
                  if (contextPathLen > 0) {
                     welcomeFile = origMethod.substring(0, contextPathLen);
                  } else {
                     welcomeFile = "/";
                  }

                  ((ServletResponseImpl)rsp).sendRedirect(rsp.encodeRedirectURL(welcomeFile), "HTTP/1.1".equals(req.getProtocol()) ? RedirectStatus.SC_SEE_OTHER : RedirectStatus.SC_MOVED_TEMPORARILY);
                  return false;
               } else {
                  if (this.getSecurityContext().isInvalidateOnRelogin()) {
                     session = getUserSession(req, false);
                  }

                  origMethod = (String)session.getInternalAttribute("weblogic.formauth.method");
                  if (origMethod != null && "POST".equals(origMethod)) {
                     session.setInternalAttribute("weblogic.formauth.postcookie", "true");
                  }

                  session.setInternalAttribute("weblogic.formauth.immediate", "true");
                  ((ServletResponseImpl)rsp).sendRedirect(rsp.encodeRedirectURL(storedUrl), "HTTP/1.1".equals(req.getProtocol()) ? RedirectStatus.SC_SEE_OTHER : RedirectStatus.SC_MOVED_TEMPORARILY);
                  return false;
               }
            }
         } else {
            this.sendError(req, rsp);
            return false;
         }
      }
   }

   private boolean checkRefererHeader(HttpServletRequest req, HttpServletResponse rsp) {
      if (this.refererValidationType == RefererValidationType.NONE) {
         return true;
      } else {
         String referer = req.getHeader("Referer");
         if (referer == null && this.refererValidationType == RefererValidationType.LENIENT) {
            return true;
         } else if (referer == null) {
            if (DEBUG_SEC.isDebugEnabled()) {
               DEBUG_SEC.debug("RefererHeader verification failed, because Referer Header is null.");
            }

            return false;
         } else {
            referer = referer.trim();
            if (RefererHeaderUtil.isRelativeURI(referer)) {
               return true;
            } else {
               RefererHeaderUtil.RefererInfo refererInfo = RefererHeaderUtil.getRefererInfo(referer);
               if (refererInfo == null) {
                  if (DEBUG_SEC.isDebugEnabled()) {
                     DEBUG_SEC.debug("RefererHeader verification failed, Refer Header is : " + referer + ", Format is invalid.");
                  }

                  return false;
               } else {
                  String serverHost = req.getServerName();
                  if (req.getServerPort() != refererInfo.port) {
                     if (DEBUG_SEC.isDebugEnabled()) {
                        DEBUG_SEC.debug("RefererHeader verification failed, because Port(" + req.getServerPort() + ") in Request is not equal to Port(" + refererInfo.port + ") in Referer Header.");
                     }

                     return false;
                  } else if (serverHost.equals(refererInfo.host)) {
                     return true;
                  } else {
                     try {
                        if (InetAddress.getByName(serverHost).getHostAddress().equals(refererInfo.host)) {
                           return true;
                        }

                        if (DEBUG_SEC.isDebugEnabled()) {
                           DEBUG_SEC.debug("RefererHeader verification failed, because Host(" + serverHost + ") in Request is not equal to Host(" + refererInfo.host + ") in Referer Header.");
                        }
                     } catch (UnknownHostException var7) {
                        if (DEBUG_SEC.isDebugEnabled()) {
                           DEBUG_SEC.debug("RefererHeader verification failed, " + var7.getMessage());
                        }
                     }

                     return false;
                  }
               }
            }
         }
      }
   }

   private boolean processLoggedInUser(HttpServletRequest req, HttpServletResponse rsp, SubjectHandle waFormAuthUser) throws IOException {
      SessionSecurityData session = getUserSession(req, false);
      ResourceConstraint cons = this.webAppSecurity.getConstraint(req);
      if (!this.webAppSecurity.hasPermission(req, rsp, waFormAuthUser, cons)) {
         if (session != null && session.getInternalAttribute("weblogic.formauth.immediate") != null) {
            session.removeInternalAttribute("weblogic.formauth.immediate");
            this.sendForbiddenResponse(req, rsp);
            return false;
         } else {
            if (this.isReloginEnabled() && !this.isForbidden(cons)) {
               this.sendLoginPage(req, rsp);
            } else {
               this.sendForbiddenResponse(req, rsp);
            }

            return false;
         }
      } else {
         if (session != null) {
            session.removeInternalAttribute("weblogic.formauth.targeturi");
            session.removeInternalAttribute("weblogic.formauth.targeturl");
         }

         if (this.wlsAuthCookieMissing(req, session)) {
            if (DEBUG_SEC.isDebugEnabled()) {
               DEBUG_SEC.debug("AuthCookie not found - permission denied for " + req);
            }

            this.sendLoginPage(req, rsp);
            return false;
         } else {
            return true;
         }
      }
   }

   public void sendError(HttpServletRequest req, HttpServletResponse res) throws IOException {
      res.setStatus(403);
      this.sendErrorPage(req, res);
   }

   private void sendErrorPage(HttpServletRequest req, HttpServletResponse res) throws IOException {
      if (res.isCommitted()) {
         res.sendRedirect(res.encodeURL(getContextURL(req) + this.webAppSecurity.getErrorPage()));
      } else {
         RequestDispatcher rd = req.getRequestDispatcher(this.webAppSecurity.getErrorPage());
         SubjectHandle subject = SecurityModule.getCurrentUser(this.getSecurityContext(), req);
         if (subject == null) {
            subject = WebAppSecurity.getProvider().getAnonymousSubject();
         }

         PrivilegedAction forwardAction = new ForwardAction(rd, req, res);
         Throwable e = (Throwable)subject.run((PrivilegedAction)forwardAction);
         if (e != null) {
            if (e instanceof IOException) {
               throw (IOException)e;
            }

            HTTPLogger.logSendError(this.getSecurityContext().getLogContext(), e);
         }
      }

   }

   private static final String getContextURL(HttpServletRequest req) {
      int port = req.getServerPort();
      StringBuffer buf = new StringBuffer(128);
      if (port != 80 && port != 443) {
         buf.append(req.getScheme()).append("://");
         buf.append(req.getServerName()).append(':');
         buf.append(req.getServerPort());
         buf.append(ServletRequestImpl.getResolvedContextPath(req));
      } else {
         buf.append(req.getScheme()).append("://");
         buf.append(req.getServerName());
         buf.append(ServletRequestImpl.getResolvedContextPath(req));
      }

      return buf.toString();
   }

   private final void sendLoginPage(HttpServletRequest req, HttpServletResponse res) throws IOException {
      this.stuffSession(req);
      res.sendRedirect(res.encodeURL(ServletRequestImpl.getResolvedContextPath(req) + this.webAppSecurity.getLoginPage()));
   }

   private void stuffSession(HttpServletRequest req) throws IOException {
      SessionSecurityData session = getUserSession(req, true);
      String storedUri = req.getRequestURI();
      String storedUrl = this.getRequestFacade().getURLForRedirect(req);
      String queryString = req.getQueryString();
      if (queryString != null) {
         storedUrl = storedUrl + "?" + queryString;
         storedUri = storedUri + "?" + queryString;
      }

      session.setInternalAttribute("weblogic.formauth.targeturi", storedUri);
      session.setInternalAttribute("weblogic.formauth.targeturl", storedUrl);
      session.setInternalAttribute("weblogic.formauth.method", req.getMethod());
      if ("POST".equals(req.getMethod())) {
         if (this.maxSavePostSize == 0) {
            this.raiseException(session);
         }

         ServletInputStream inStream = req.getInputStream();
         byte[] buffer = new byte[4096];
         int read = false;
         int count = 0;
         ByteArrayOutputStream outStream = new ByteArrayOutputStream();

         int read;
         while((read = inStream.read(buffer, 0, buffer.length)) != -1) {
            count += read;
            if (count > this.maxSavePostSize) {
               break;
            }

            outStream.write(buffer, 0, read);
         }

         if (count > this.maxSavePostSize) {
            this.raiseException(session);
         }

         byte[] dump = outStream.toByteArray();
         if (dump.length > 0) {
            session.setInternalAttribute("weblogic.formauth.bytearray", dump);
         } else {
            session.removeInternalAttribute("weblogic.formauth.bytearray");
         }

         Object[] pair = this.getRequestFacade().getHeadersAsLists(req);
         session.setInternalAttribute("weblogic.formauth.reqheadernames", pair[0]);
         session.setInternalAttribute("weblogic.formauth.reqheadervalues", pair[1]);
      }
   }

   private void raiseException(SessionSecurityData session) throws IOException {
      session.removeInternalAttribute("weblogic.formauth.targeturi");
      session.removeInternalAttribute("weblogic.formauth.targeturl");
      session.removeInternalAttribute("weblogic.formauth.method");
      throw new MaxPostSizeExceededException("MaxSavePostSize [" + this.maxSavePostSize + "] exceeded !");
   }
}
