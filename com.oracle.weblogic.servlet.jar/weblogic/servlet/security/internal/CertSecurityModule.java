package weblogic.servlet.security.internal;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import javax.security.auth.login.LoginException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.servlet.HTTPLogger;
import weblogic.servlet.internal.ServletRequestImpl;
import weblogic.servlet.internal.VirtualConnection;
import weblogic.servlet.spi.ApplicationSecurity;
import weblogic.servlet.spi.SubjectHandle;
import weblogic.utils.StringUtils;
import weblogic.utils.encoders.BASE64Decoder;

public final class CertSecurityModule extends SecurityModule {
   private static final String X509_TYPE = "X.509";
   private static final String CERT_RESERVED_IP = "IP";
   private static final String CERT_RESERVED_KEYSIZE = "Keysize";
   private static final String CERT_RESERVED_SECRETKEYSIZE = "SecretKeysize";
   private static final boolean protectResourceIfUnspecifiedConstraint = Boolean.getBoolean("weblogic.http.security.cert.protectResourceIfUnspecifiedConstraint");
   protected static final DebugLogger DEBUG_IA = DebugLogger.getDebugLogger("DebugWebAppIdentityAssertion");
   private final boolean alwaysAssert;

   public CertSecurityModule(ServletSecurityContext ctx, WebAppSecurity was, boolean controller, boolean isIA) {
      super(ctx, was, controller);
      this.alwaysAssert = isIA;
   }

   protected boolean checkUserPerm(HttpServletRequest req, HttpServletResponse rsp, SessionSecurityData session, ResourceConstraint cons, SubjectHandle subject, boolean applyAuthFilters) throws IOException, ServletException {
      ServletRequestImpl reqi = ServletRequestImpl.getOriginalRequest(req);
      boolean isAsserted = false;
      boolean isReAuthenticateRequired = isReAuthenticateRequired(this.getSecurityContext(), session);
      if (this.alwaysAssert || subject == null || subject.isAnonymous() || isReAuthenticateRequired) {
         SubjectHandle newSubject = this.assertIdentity(req, rsp, reqi.getConnection(), isReAuthenticateRequired);
         if (isReAuthenticateRequired && newSubject == null && subject != null) {
            this.sendForbiddenResponse(req, rsp);
            return false;
         }

         if (newSubject != null && subject != newSubject) {
            subject = newSubject;
            isAsserted = true;
         }
      }

      boolean accessAllowed = false;
      if (protectResourceIfUnspecifiedConstraint) {
         accessAllowed = cons == null && !this.webAppSecurity.isFullSecurityDelegationRequired() || subject != null && this.webAppSecurity.hasPermission(req, rsp, subject, cons);
      } else {
         accessAllowed = this.webAppSecurity.hasPermission(req, rsp, subject, cons);
      }

      if (!accessAllowed) {
         if (DEBUG_IA.isDebugEnabled()) {
            DEBUG_IA.debug("Permission check failed for " + req.toString());
         }

         if (this.isForbidden(cons) || subject != null && !this.isReloginEnabled()) {
            this.sendForbiddenResponse(req, rsp);
         } else {
            if (applyAuthFilters && this.webAppSecurity.hasAuthFilters()) {
               this.webAppSecurity.invokeAuthFilterChain(req, rsp);
               return false;
            }

            this.sendUnauthorizedResponse(req, rsp);
         }

         return false;
      } else {
         if (DEBUG_SEC.isDebugEnabled()) {
            DEBUG_SEC.debug(cons == null ? "No security constraints declared!" : "security constraints =" + cons);
         }

         if (cons != null && this.wlsAuthCookieMissing(req, session)) {
            if (DEBUG_SEC.isDebugEnabled()) {
               DEBUG_SEC.debug("AuthCookie not found - permission denied for " + req);
            }

            this.sendUnauthorizedResponse(req, rsp);
            this.setAuthCookieForReAuth(this.getSecurityContext(), session, this);
            return false;
         } else if (!isAsserted) {
            return true;
         } else {
            if (session != null) {
               synchronized(session) {
                  if (getCurrentUser(this.getSecurityContext(), req, session) == null) {
                     this.login(req, subject, session);
                  }
               }
            } else {
               this.login(req, subject, session);
            }

            if (DEBUG_SEC.isDebugEnabled()) {
               DEBUG_SEC.debug(this.getSecurityContext().getLogContext() + ": user: " + getUsername(subject) + " has permissions to access " + req);
            }

            return true;
         }
      }
   }

   protected SubjectHandle assertIdentity(HttpServletRequest req, HttpServletResponse rsp, VirtualConnection conn, boolean isReAuthenticateRequired) {
      if (isReAuthenticateRequired && this.webAppSecurity.isNotLastChainedSecurityModule(this)) {
         return null;
      } else {
         try {
            if (DEBUG_IA.isDebugEnabled()) {
               DEBUG_IA.debug("Trying to find identity assertion tokens for " + req);
            }

            Token token = findToken(req, conn, this.getSecurityContext());
            if (token == null) {
               if (DEBUG_IA.isDebugEnabled()) {
                  DEBUG_IA.debug("Didn't find any token!");
               }

               return null;
            }

            if (DEBUG_IA.isDebugEnabled()) {
               DEBUG_IA.debug("assertIdentity with tokem.type: " + token.type + " token.value: " + token.value);
            }

            return this.webAppSecurity.getAppSecurityProvider().assertIdentity(token.type, token.value, req, rsp);
         } catch (LoginException var6) {
            if (DEBUG_SEC.isDebugEnabled()) {
               DEBUG_SEC.debug("Login failed for request: " + req.toString(), var6);
            }
         } catch (SecurityException var7) {
            if (DEBUG_IA.isDebugEnabled()) {
               DEBUG_IA.debug("Indentity assertion failed", var7);
            }

            HTTPLogger.logCertAuthenticationError(req.getRequestURI(), var7);
         }

         return null;
      }
   }

   public static Token findToken(HttpServletRequest req, VirtualConnection conn, final ServletSecurityContext context) {
      ApplicationSecurity wap = (ApplicationSecurity)AccessController.doPrivileged(new PrivilegedAction() {
         public ApplicationSecurity run() {
            return context.getAppSecurityProvider();
         }
      });
      Map assertionsMap = wap.getAssertionsEncodingMap();
      if (assertionsMap != null && !assertionsMap.isEmpty()) {
         Map[] assertionsPrecedence = wap.getAssertionsEncodingPrecedence();
         if (DEBUG_IA.isDebugEnabled()) {
            DEBUG_IA.debug("AssertionsEncodingMap size: " + assertionsMap.size());
            DEBUG_IA.debug("AssertionsEncodingPrecedence size: " + (assertionsPrecedence != null ? assertionsPrecedence.length : "None"));
         }

         X509Certificate[] certs = (X509Certificate[])((X509Certificate[])req.getAttribute("javax.servlet.request.X509Certificate"));
         if (certs != null && certs.length > 0 && assertionsMap.containsKey("X.509")) {
            return new Token("X.509", certs);
         } else {
            List tokenTypeList = conn.getPerimeterAuthClientCertType();
            int size = tokenTypeList.size();
            byte[] certBytes;
            if (size > 0) {
               List tokenValueList = conn.getPerimeterAuthClientCert();

               for(int index = size - 1; index >= 0; --index) {
                  String tokenType = (String)tokenTypeList.get(index);
                  if (assertionsMap.containsKey(tokenType) && !isForbiddenTokenType(tokenType)) {
                     certBytes = decodeCert(tokenType, (byte[])((byte[])tokenValueList.get(index)));
                     if (certBytes != null) {
                        return new Token(tokenType, certBytes);
                     }
                  }
               }
            }

            String cookieName;
            String[] splitValue;
            String headerName;
            if (assertionsPrecedence != null && assertionsPrecedence.length > 0) {
               if (DEBUG_IA.isDebugEnabled()) {
                  DEBUG_IA.debug("Trying to find identity assertion tokens based on precedence ordering");
               }

               int i = 0;

               while(true) {
                  if (i >= assertionsPrecedence.length) {
                     if (DEBUG_IA.isDebugEnabled()) {
                        DEBUG_IA.debug("Didn't find identity assertion tokens based on precedence ordering!");
                     }
                     break;
                  }

                  headerName = (String)assertionsPrecedence[i].get("header");
                  if (!"Cookie".equalsIgnoreCase(headerName)) {
                     Object mapMatch = assertionsMap.get(headerName);
                     if (mapMatch != null) {
                        certBytes = null;
                        String value = req.getHeader(headerName);
                        if (value != null) {
                           label214: {
                              cookieName = (String)assertionsPrecedence[i].get("scheme");
                              if (cookieName != null) {
                                 splitValue = StringUtils.split(value, ' ');
                                 if (!splitValue[0].equalsIgnoreCase(cookieName)) {
                                    break label214;
                                 }
                              }

                              try {
                                 certBytes = value.getBytes(getInputEncoding(req, context));
                              } catch (UnsupportedEncodingException var20) {
                              }

                              if (certBytes != null && certBytes.length >= 1) {
                                 if (!wap.doesTokenRequireBase64Decoding(mapMatch)) {
                                    return new Token(headerName, certBytes);
                                 }

                                 certBytes = decodeCert(headerName, certBytes);
                                 if (certBytes != null) {
                                    return new Token(headerName, certBytes);
                                 }
                              }
                           }
                        }
                     }
                  }

                  ++i;
               }
            }

            Enumeration e = req.getHeaderNames();
            headerName = null;
            ServletRequestImpl reqi = null;
            boolean wrapped = true;
            if (req instanceof ServletRequestImpl) {
               wrapped = false;
               reqi = (ServletRequestImpl)req;
            }

            byte[] certBytes;
            do {
               Object mapMatch;
               do {
                  do {
                     String cookieValue;
                     do {
                        do {
                           if (!e.hasMoreElements()) {
                              Cookie[] cookies = req.getCookies();
                              if (cookies == null) {
                                 return null;
                              }

                              cookieName = null;
                              splitValue = null;

                              for(int i = 0; i < cookies.length; ++i) {
                                 cookieName = cookies[i].getName();
                                 cookieValue = cookies[i].getValue();
                                 if (cookieValue != null && cookieValue.length() >= 1) {
                                    byte[] tokenValue;
                                    if (cookieName.length() > 16 && "WL-Proxy-Client-".regionMatches(true, 0, cookies[i].getName(), 0, 16)) {
                                       String tokenType = cookieName.substring(16);
                                       if (assertionsMap.containsKey(tokenType)) {
                                          tokenValue = decodeCert(tokenType, cookieValue.getBytes());
                                          if (tokenValue != null) {
                                             return new Token(tokenType, tokenValue);
                                          }
                                       }
                                    } else {
                                       Object mapMatch = assertionsMap.get(cookieName);
                                       if (mapMatch != null) {
                                          if (!wap.doesTokenRequireBase64Decoding(mapMatch)) {
                                             return new Token(cookieName, cookieValue.getBytes());
                                          }

                                          tokenValue = decodeCert(cookieName, cookieValue.getBytes());
                                          if (tokenValue != null) {
                                             return new Token(cookieName, tokenValue);
                                          }
                                       }
                                    }
                                 }
                              }

                              return null;
                           }

                           headerName = (String)e.nextElement();
                        } while("Cookie".equalsIgnoreCase(headerName));

                        mapMatch = assertionsMap.get(headerName);
                     } while(mapMatch == null);

                     certBytes = null;
                     if (!wrapped) {
                        certBytes = reqi.getRequestHeaders().getHeaderAsBytes(headerName);
                     } else {
                        cookieValue = req.getHeader(headerName);
                        if (cookieValue != null) {
                           try {
                              certBytes = cookieValue.getBytes(getInputEncoding(req, context));
                           } catch (UnsupportedEncodingException var19) {
                           }
                        }
                     }
                  } while(certBytes == null);
               } while(certBytes.length < 1);

               if (!wap.doesTokenRequireBase64Decoding(mapMatch)) {
                  break;
               }

               certBytes = decodeCert(headerName, certBytes);
            } while(certBytes == null);

            return new Token(headerName, certBytes);
         }
      } else {
         if (DEBUG_IA.isDebugEnabled()) {
            DEBUG_IA.debug("AssertionsEncodingMap for active token types was null!!");
         }

         return null;
      }
   }

   private static String getInputEncoding(HttpServletRequest req, ServletSecurityContext securityContext) {
      String inputEncoding = req.getCharacterEncoding();
      return inputEncoding != null ? inputEncoding : securityContext.getDefaultEncoding();
   }

   private static byte[] decodeCert(String tokenType, byte[] cert) {
      try {
         ByteArrayInputStream bais = new ByteArrayInputStream(cert);
         byte[] decodedBytes = (new BASE64Decoder()).decodeBuffer(bais);
         return decodedBytes != null && decodedBytes.length >= 1 ? decodedBytes : null;
      } catch (Exception var4) {
         HTTPLogger.logIgnoringClientCert(tokenType, var4);
         return null;
      }
   }

   private static boolean isForbiddenTokenType(String tokenType) {
      return tokenType.equalsIgnoreCase("IP") || tokenType.equalsIgnoreCase("Keysize") || tokenType.equalsIgnoreCase("SecretKeysize");
   }

   public static class Token {
      public final String type;
      public final Object value;

      Token(String t, Object v) {
         this.type = t;
         this.value = v;
      }
   }
}
