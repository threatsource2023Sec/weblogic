package com.bea.common.security.saml.utils;

import com.bea.common.logger.spi.LoggerSpi;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.security.Principal;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import javax.security.auth.Subject;
import org.opensaml.SAMLAssertion;
import org.opensaml.SAMLAttributeStatement;
import org.opensaml.SAMLAuthenticationStatement;
import org.opensaml.SAMLException;
import org.opensaml.SAMLNameIdentifier;
import org.opensaml.SAMLSignedObject;
import org.opensaml.SAMLSubject;
import org.w3c.dom.Element;
import weblogic.security.principal.IdentityDomainPrincipal;
import weblogic.security.service.ContextHandler;
import weblogic.utils.encoders.BASE64Decoder;
import weblogic.utils.encoders.BASE64Encoder;

public class SAMLUtil {
   public static final String RESPONSE_PARAMETER_NAME = "SAMLResponse";
   public static final String ARTIFACT_PARAMETER_NAME = "SAMLart";
   public static final String TARGET_PARAMETER_NAME = "TARGET";
   public static final String APID_PARAMETER_NAME = "APID";
   public static final String RPID_PARAMETER_NAME = "RPID";
   public static final String ACS_URL_ATTR_NAME_V1 = "consumerURL";
   public static final String ITS_TARGET_ATTR_NAME_V1 = "targetURL";
   public static final String ACS_URL_ATTR_NAME = "SAML_AssertionConsumerURL";
   public static final String ACS_PARAMS_ATTR_NAME = "SAML_AssertionConsumerParams";
   public static final String ITS_REQUEST_PARAMS_ATTR_NAME = "SAML_ITSRequestParams";
   public static boolean ENABLE_URL_REWRITING = Boolean.getBoolean("weblogic.security.saml.enableURLRewriting");
   public static final String HTTP_SCHEME = "http://";
   public static final String HTTPS_SCHEME = "https://";

   public static final String calculateSourceIdHex(String sourceSiteURL) {
      try {
         SAMLSourceId srcId = new SAMLSourceId(1, sourceSiteURL);
         return srcId.getSourceIdHex();
      } catch (IllegalArgumentException var2) {
         return new String("Invalid Source Site URL");
      }
   }

   public static final String calculateSourceIdBase64(String sourceSiteURL) {
      try {
         SAMLSourceId srcId = new SAMLSourceId(1, sourceSiteURL);
         return srcId.getSourceIdBase64();
      } catch (IllegalArgumentException var2) {
         return new String("Invalid Source Site URL");
      }
   }

   public static final String normalizeURL(String url) {
      url = trimString(url);
      if (url == null) {
         return null;
      } else {
         URL newURL = null;

         try {
            String query = "";
            int queryIndex = url.indexOf(63);
            if (queryIndex != -1) {
               query = url.substring(queryIndex);
               url = url.substring(0, queryIndex);
            }

            URI newURI = new URI(url);
            newURI.normalize();
            newURL = new URL(newURI.toASCIIString() + query);
         } catch (Exception var5) {
            return null;
         }

         return newURL.toString();
      }
   }

   public static final String normalizeURI(String uri) {
      uri = trimString(uri);
      if (uri == null) {
         return null;
      } else {
         URI newURI = null;

         try {
            newURI = new URI(uri);
         } catch (Exception var3) {
            return null;
         }

         newURI.normalize();
         return newURI.toString();
      }
   }

   public static final String normalizeContextPath(String path) {
      String ctxPath = normalizeURI(path);
      return ctxPath != null && ctxPath.startsWith("/") ? ctxPath : null;
   }

   public static final String[] cleanupStringArray(String[] attr) {
      if (attr != null && attr.length != 0) {
         LinkedList list = new LinkedList();

         for(int i = 0; i < attr.length; ++i) {
            String[] tmp = attr[i].split("\\s");

            for(int j = 0; j < tmp.length; ++j) {
               if (tmp[j] != null && tmp[j].length() > 0) {
                  list.add(tmp[j]);
               }
            }
         }

         if (list.isEmpty()) {
            return null;
         } else {
            String[] values = new String[list.size()];
            return (String[])((String[])list.toArray(values));
         }
      } else {
         return null;
      }
   }

   public static final String base64Encode(byte[] bytes) {
      BASE64Encoder encoder = new BASE64Encoder();
      return encoder.encodeBuffer(bytes);
   }

   public static final byte[] base64Decode(String b64) throws IOException {
      BASE64Decoder decoder = new BASE64Decoder();
      return decoder.decodeBuffer(b64);
   }

   public static final String queryStringStripParam(String queryString, String paramName) {
      StringBuffer buf = new StringBuffer(queryString);
      int paramStart = buf.indexOf(paramName + "=");
      if (paramStart > 0) {
         paramStart = buf.indexOf("&" + paramName + "=");
      }

      if (paramStart >= 0) {
         int paramEnd = buf.indexOf("&", paramStart + 1);
         if (paramEnd < 0) {
            paramEnd = buf.length();
         }

         buf.delete(paramStart, paramEnd);
         if (buf.length() > 0 && buf.charAt(0) == '&') {
            return buf.substring(1);
         }
      }

      return buf.toString();
   }

   public static final String buildURLWithParams(String url, String[] params) {
      if (url != null && url.length() > 0) {
         String paramString = "";

         for(int i = 0; params != null && i < params.length; ++i) {
            if (params[i] != null && params[i].length() > 0) {
               paramString = paramString + params[i] + "&";
            }
         }

         return url + "?" + paramString;
      } else {
         return null;
      }
   }

   public static final Map paramStringToMap(String paramString) {
      return (Map)(paramString == null ? new HashMap() : paramArrayToMap(paramString.split("&")));
   }

   public static final Map paramArrayToMap(String[] paramArray) {
      HashMap map = new HashMap();

      for(int i = 0; paramArray != null && i < paramArray.length; ++i) {
         if (paramArray[i] != null && paramArray[i].length() > 0) {
            int index = paramArray[i].indexOf(61);
            if (index != -1 && index != 0) {
               String name = paramArray[i].substring(0, index).trim();
               String value = paramArray[i].substring(index + 1).trim();
               if (name.length() > 0) {
                  map.put(name, value);
               }
            }
         }
      }

      return map;
   }

   public static final String trimString(String s) {
      if (s != null) {
         s = s.trim();
         if (s.length() > 0) {
            return s;
         }
      }

      return null;
   }

   public static final String[] mergeArrays(String[] array1, String[] array2) {
      ArrayList merged = new ArrayList();

      int i;
      for(i = 0; array1 != null && i < array1.length; ++i) {
         if (array1[i] != null && !merged.contains(array1[i])) {
            merged.add(array1[i]);
         }
      }

      for(i = 0; array2 != null && i < array2.length; ++i) {
         if (array2[i] != null && !merged.contains(array2[i])) {
            merged.add(array2[i]);
         }
      }

      return (String[])((String[])merged.toArray(new String[0]));
   }

   public static final String getStringContextElement(String name, ContextHandler handler) {
      if (name != null && handler != null) {
         Object obj = handler.getValue(name);
         if (obj != null && obj instanceof String) {
            return (String)obj;
         }
      }

      return null;
   }

   public static final Element getKeyInfoContextElement(ContextHandler handler) {
      if (handler != null) {
         Object obj = handler.getValue("com.bea.contextelement.saml.subject.dom.KeyInfo");
         if (obj != null && obj instanceof Element) {
            return (Element)obj;
         }
      }

      return null;
   }

   public static final boolean getBooleanContextElement(String name, ContextHandler handler) {
      if (name != null && handler != null) {
         Object obj = handler.getValue(name);
         if (obj != null && obj instanceof Boolean) {
            return (Boolean)obj;
         }
      }

      return false;
   }

   public static final String displaySubject(Subject subject) {
      if (subject == null) {
         return "Subject == null";
      } else {
         StringBuffer buf = new StringBuffer("Subject: ");
         Set principals = subject.getPrincipals();
         buf.append(principals.size());
         buf.append("\n");
         Object[] pArray = principals.toArray();

         for(int i = 0; i < pArray.length; ++i) {
            Principal p = (Principal)pArray[i];
            buf.append("\tPrincipal = ");
            buf.append(p.getClass());
            buf.append("(\"");
            String userName = null;
            if (p instanceof IdentityDomainPrincipal) {
               userName = p.toString();
            } else {
               userName = p.getName();
            }

            if (userName != null) {
               buf.append(userName);
            }

            buf.append("\")\n");
         }

         return buf.toString();
      }
   }

   public static X509Certificate getEndCertFromSignedObject(LoggerSpi logger, SAMLSignedObject signedObject) {
      boolean loggerEnabled = false;
      if (logger != null && logger.isDebugEnabled()) {
         loggerEnabled = true;
      }

      Iterator i = null;

      try {
         i = signedObject.getX509Certificates();
      } catch (SAMLException var7) {
         if (loggerEnabled) {
            logger.debug("Exception while calling SignedObject.getX509Certificates(): " + var7.toString());
         }

         return null;
      }

      if (i != null && i.hasNext()) {
         Object cert = i.next();
         if (!(cert instanceof X509Certificate)) {
            return null;
         } else {
            X509Certificate x509cert = (X509Certificate)cert;
            if (x509cert != null) {
               try {
                  x509cert.checkValidity();
               } catch (CertificateExpiredException var8) {
                  if (loggerEnabled) {
                     logger.debug("Certificate has expired: " + var8.toString());
                  }

                  return null;
               } catch (CertificateNotYetValidException var9) {
                  if (loggerEnabled) {
                     logger.debug("Certificate is not yet valid: " + var9.toString());
                  }

                  return null;
               }
            }

            if (loggerEnabled) {
               logger.debug("Got signing certificate for signed object: " + x509cert.getSubjectDN().getName());
            }

            return x509cert;
         }
      } else {
         return null;
      }
   }

   public static String getConfirmationMethod(LoggerSpi logger, SAMLAssertion assertion) {
      SAMLSubject sub = getSubject(assertion);
      return getConfirmationMethod(logger, sub);
   }

   private static boolean isSupportedConfirmationMethod(String method) {
      return method.equals("urn:oasis:names:tc:SAML:1.0:cm:artifact") || method.equals("urn:oasis:names:tc:SAML:1.0:cm:bearer") || method.equals("urn:oasis:names:tc:SAML:1.0:cm:holder-of-key") || method.equals("urn:oasis:names:tc:SAML:1.0:cm:sender-vouches");
   }

   public static Object instantiatePlugin(String className, String interfaceName) throws Exception {
      Class clz = Class.forName(className);
      Object obj = clz.newInstance();
      Class cls = Class.forName(interfaceName);
      if (!cls.isInstance(obj)) {
         throw new Exception("Class '" + className + "' is not an instance of '" + interfaceName + "'");
      } else {
         return obj;
      }
   }

   public static SAMLSubject getSubject(SAMLAssertion assertion) {
      boolean foundSubInAuthStmt = false;
      SAMLSubject subInAttrStmt = null;
      SAMLSubject subject = null;
      Iterator stmtItr = assertion.getStatements();

      while(stmtItr.hasNext()) {
         Object stmt = stmtItr.next();
         if (stmt instanceof SAMLAuthenticationStatement) {
            subject = ((SAMLAuthenticationStatement)stmt).getSubject();
            foundSubInAuthStmt = true;
            break;
         }

         if (stmt instanceof SAMLAttributeStatement) {
            SAMLSubject sub = ((SAMLAttributeStatement)stmt).getSubject();
            if (subInAttrStmt == null) {
               SAMLNameIdentifier nameId = sub == null ? null : sub.getName();
               if (nameId != null && nameId.getName() != null) {
                  subInAttrStmt = sub;
               }
            }
         }
      }

      if (!foundSubInAuthStmt) {
         subject = subInAttrStmt;
      }

      return subject;
   }

   public static String getConfirmationMethod(LoggerSpi logger, SAMLSubject sub) {
      if (sub == null) {
         if (logger != null && logger.isDebugEnabled()) {
            logger.debug("SAML subject is null.");
         }

         return null;
      } else {
         String method = null;
         Iterator methods = sub.getConfirmationMethods();
         if (methods.hasNext()) {
            method = (String)methods.next();
            if (method != null) {
               method = method.trim();
            }
         }

         if ((method == null || !isSupportedConfirmationMethod(method)) && logger != null && logger.isDebugEnabled()) {
            logger.debug("Invalid subject confirmation method '" + (method == null ? "null" : method) + "'");
         }

         return method;
      }
   }

   public static boolean isTargetRedirectHostAllowed(String targetURL, String[] allowedHosts) throws MalformedURLException {
      boolean allowed = false;
      String targetHost = (new URL(targetURL)).getHost();
      String[] var4 = allowedHosts;
      int var5 = allowedHosts.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         String s = var4[var6];
         if (s.startsWith("*.")) {
            if (targetHost.toLowerCase().endsWith(s.substring(1).toLowerCase())) {
               allowed = true;
               break;
            }
         } else if (targetHost.startsWith("[") && targetHost.endsWith("]") && targetHost.length() > 2) {
            if (s.equalsIgnoreCase(targetHost.substring(1, targetHost.length() - 1))) {
               allowed = true;
               break;
            }
         } else if (targetHost.equalsIgnoreCase(s)) {
            allowed = true;
            break;
         }
      }

      return allowed;
   }
}
