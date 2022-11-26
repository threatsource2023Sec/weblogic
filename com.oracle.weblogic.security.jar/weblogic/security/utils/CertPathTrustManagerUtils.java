package weblogic.security.utils;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.cert.CertPath;
import java.security.cert.CertPathValidator;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import javax.net.ssl.SSLSocket;
import weblogic.management.configuration.SSLMBean;
import weblogic.management.configuration.TLSMBean;
import weblogic.protocol.ServerChannel;
import weblogic.security.SecurityLogger;
import weblogic.security.SecurityRuntimeAccess;
import weblogic.security.pk.CertPathValidatorParameters;
import weblogic.security.service.ContextElement;
import weblogic.security.service.ContextHandler;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.shared.LoggerWrapper;
import weblogic.utils.LocatorUtilities;

public final class CertPathTrustManagerUtils {
   public static final int CERT_PATH_VAL_IF_CONFIGURED = 0;
   public static final int CERT_PATH_VAL_ALWAYS = 1;
   public static final int CERT_PATH_VAL_NEVER = 2;
   private static boolean running = false;
   private static LoggerWrapper log = LoggerWrapper.getInstance("SecurityCertPath");
   private static String CLIENT_CERTS_ENFORCED = "weblogic.security.ClientCertificatesEnforced";

   public static synchronized void start() {
      running = true;
   }

   public static synchronized void stop() {
      running = false;
   }

   public static synchronized void halt() {
      running = false;
   }

   private static boolean isDebug() {
      return log.isDebugEnabled();
   }

   private static void debug(String method, String info) {
      String msg = "CertPathTrustManagerUtils." + method + ": " + info;
      if (log.isDebugEnabled()) {
         log.debug(msg);
      }

      System.out.println(msg);
   }

   public static boolean certificateCallback(int certPathValStyle, X509Certificate[] chain, int validateErr) {
      return certificateCallback(certPathValStyle, chain, validateErr, (ServerChannel)null);
   }

   public static boolean certificateCallback(int certPathValStyle, X509Certificate[] chain, int validateErr, ServerChannel serverChannel) {
      String outboundCertificateValidation = serverChannel != null ? serverChannel.getOutboundCertificateValidation() : null;
      String inboundCertificateValidation = serverChannel != null ? serverChannel.getInboundCertificateValidation() : null;
      return certificateCallback(certPathValStyle, chain, validateErr, outboundCertificateValidation, inboundCertificateValidation);
   }

   public static boolean certificateCallback(int certPathValStyle, X509Certificate[] chain, int validateErr, TLSMBean tlsmBean) {
      String outboundCertificateValidation = tlsmBean != null ? tlsmBean.getOutboundCertificateValidation() : null;
      String inboundCertificateValidation = tlsmBean != null ? tlsmBean.getInboundCertificateValidation() : null;
      return certificateCallback(certPathValStyle, chain, validateErr, outboundCertificateValidation, inboundCertificateValidation);
   }

   public static boolean certificateCallback(int certPathValStyle, X509Certificate[] chain, int validateErr, String outboundCertificateValidation, String inboundCertificateValidation) {
      String method = "certificateCallback";
      if (isDebug()) {
         debug(method, "certPathValStype = " + certPathValStyle);
         debug(method, "validateErr = " + validateErr);

         for(int i = 0; chain != null && i < chain.length; ++i) {
            debug(method, "chain[" + i + "] = " + chain[i]);
         }
      }

      boolean dontValidateIfSSLErrors = false;
      dontValidateIfSSLErrors = Boolean.getBoolean("weblogic.security.dontValidateIfSSLErrors");
      if (dontValidateIfSSLErrors && validateErr != 0) {
         if (isDebug()) {
            debug(method, "returning false because of built-in SSL validation errors");
         }

         return false;
      } else if (0 != validateErr || chain != null && chain.length >= 1) {
         boolean clientCertsRequired;
         if (!doCertPathValidation(certPathValStyle, outboundCertificateValidation, inboundCertificateValidation)) {
            clientCertsRequired = validateErr == 0;
            if (isDebug()) {
               debug(method, "returning " + clientCertsRequired + " because the CertPathValidators should not be called");
            }

            return clientCertsRequired;
         } else {
            clientCertsRequired = false;
            if (!dontValidateIfSSLErrors) {
               SSLMBean sslMBean = CertPathTrustManagerUtils.SecurityRuntimeAccessService.runtimeAccess.getServer().getSSL();
               if (sslMBean.isClientCertificateEnforced()) {
                  clientCertsRequired = true;
               }
            }

            boolean rtn = performCertPathValidation(chain, validateErr, clientCertsRequired);
            if (isDebug()) {
               debug(method, "returning results of CertPathValidators = " + rtn);
            }

            return rtn;
         }
      } else {
         if (isDebug()) {
            debug(method, "returning true because there is no chain and the chain is not required");
         }

         return true;
      }
   }

   private static boolean doCertPathValidation(int certPathValStyle, String outboundCertificateValidation, String inboundCertificateValidation) {
      String method = "doCertPathValidation";
      if (isDebug()) {
         debug(method, "");
      }

      if (!running) {
         if (isDebug()) {
            debug(method, "returning false because cert path validation is not yet available in this server");
         }

         return false;
      } else if (certPathValStyle == 1) {
         if (isDebug()) {
            debug(method, "returning true because configured to always call the cert path validators");
         }

         return true;
      } else if (certPathValStyle == 2) {
         if (isDebug()) {
            debug(method, "returning false because configured to never call the cert path validators");
         }

         return false;
      } else {
         if (isDebug()) {
            debug(method, "configured to defer to the admin");
         }

         boolean outbound = TrustManagerEnvironment.getSSLSocket().getUseClientMode();
         if (isDebug()) {
            debug(method, "outbound = " + outbound);
         }

         String style;
         if (null != outboundCertificateValidation && null != inboundCertificateValidation) {
            style = outbound ? outboundCertificateValidation : inboundCertificateValidation;
         } else {
            SSLMBean sslMBean = CertPathTrustManagerUtils.SecurityRuntimeAccessService.runtimeAccess.getServer().getSSL();
            style = outbound ? sslMBean.getOutboundCertificateValidation() : sslMBean.getInboundCertificateValidation();
         }

         if (isDebug()) {
            debug(method, "style = " + style);
         }

         boolean rtn = "BuiltinSSLValidationAndCertPathValidators".equals(style);
         if (isDebug()) {
            debug(method, "returning " + rtn);
         }

         return rtn;
      }
   }

   private static boolean performCertPathValidation(X509Certificate[] chain, int validateErrs, boolean clientCertsRequired) {
      String method = "performCertPathValidation";
      if (isDebug()) {
         debug(method, "");
      }

      try {
         int chainLength = null == chain ? 0 : chain.length;
         ArrayList list = new ArrayList(chainLength);

         for(int i = 0; i < chainLength; ++i) {
            list.add(chain[i]);
         }

         CertPath certPath = CertificateFactory.getInstance("X509").generateCertPath(list);
         SSLSocket sslSocket = TrustManagerEnvironment.getSSLSocket();
         CertPathValidatorParameters params = new CertPathValidatorParameters(SecurityServiceManager.getContextSensitiveRealmName(), TrustManagerEnvironment.getTrustedCAs(), new SSLPrevalidationContextParams(sslSocket.getPort(), sslSocket.getInetAddress().toString(), validateErrs, clientCertsRequired));
         CertPathValidator validator = CertPathValidator.getInstance("WLSCertPathValidator");

         try {
            validator.validate(certPath, params);
            if (isDebug()) {
               debug(method, "the chain was validated by the cert path validators");
            }

            return true;
         } catch (CertPathValidatorException var11) {
            SecurityLogger.logSSLCertPathNotValidated(certPath.toString(), var11);
            if (isDebug()) {
               debug(method, "the chain was not validated by the cert path validators:" + var11);
            }

            return false;
         } catch (IllegalArgumentException var12) {
            if (isDebug()) {
               debug(method, "the chain was not validated by the cert path validators:" + var12);
            }

            return false;
         }
      } catch (Exception var13) {
         if (isDebug()) {
            debug(method, "unexpected exception: " + var13);
         }

         throw new CertPathTrustManagerRuntimeException(var13);
      }
   }

   private static class SSLPrevalidationContextParams implements ContextHandler {
      ContextElement[] ctxElements = new ContextElement[4];

      public SSLPrevalidationContextParams(int peerPort, String peerAddress, int validateErrs, boolean clientCertsRequired) {
         this.ctxElements[0] = new ContextElement("com.bea.contextelement.security.ChainPrevailidatedBySSL", new Boolean(validateErrs == 0));
         this.ctxElements[1] = new ContextElement("com.bea.contextelement.channel.RemotePort", new Integer(peerPort));
         this.ctxElements[2] = new ContextElement("com.bea.contextelement.channel.RemoteAddress", peerAddress);
         this.ctxElements[3] = new ContextElement(CertPathTrustManagerUtils.CLIENT_CERTS_ENFORCED, new Boolean(clientCertsRequired));
      }

      public int size() {
         return this.ctxElements.length;
      }

      public String[] getNames() {
         String[] names = new String[this.ctxElements.length];

         for(int i = 0; i < this.ctxElements.length; ++i) {
            names[i] = this.ctxElements[i].getName();
         }

         return names;
      }

      public Object getValue(String name) {
         for(int i = 0; i < this.ctxElements.length; ++i) {
            if (this.ctxElements[i].getName().equals(name)) {
               return this.ctxElements[i].getValue();
            }
         }

         return null;
      }

      public ContextElement[] getValues(String[] names) {
         ArrayList valuesList = new ArrayList(this.ctxElements.length);

         for(int i = 0; names != null && i < names.length; ++i) {
            for(int j = 0; j < this.ctxElements.length; ++j) {
               if (this.ctxElements[j].getName().equals(names[i])) {
                  valuesList.add(this.ctxElements[j]);
                  break;
               }
            }
         }

         return (ContextElement[])((ContextElement[])valuesList.toArray(new ContextElement[valuesList.size()]));
      }
   }

   private static final class CertPathTrustManagerRuntimeException extends RuntimeException {
      public CertPathTrustManagerRuntimeException(Throwable nested) {
         super(nested);
      }
   }

   private static final class SecurityRuntimeAccessService {
      private static final SecurityRuntimeAccess runtimeAccess = (SecurityRuntimeAccess)AccessController.doPrivileged(new PrivilegedAction() {
         public SecurityRuntimeAccess run() {
            return (SecurityRuntimeAccess)LocatorUtilities.getService(SecurityRuntimeAccess.class);
         }
      });
   }
}
