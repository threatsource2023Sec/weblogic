package weblogic.security.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.configuration.SecurityConfigurationMBean;
import weblogic.security.HMAC;
import weblogic.security.SecurityLogger;
import weblogic.security.SecurityRuntimeAccess;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.utils.LocatorUtilities;
import weblogic.utils.encoders.BASE64Decoder;
import weblogic.utils.encoders.BASE64Encoder;
import weblogic.utils.encoders.CharacterDecoder;
import weblogic.utils.encoders.CharacterEncoder;

public class RequestSigner {
   private static RequestSigner theSigner = new RequestSigner();
   private static final String CACHE_KEY_DELIM = ":";
   private byte[] domainWideSecret = null;
   private volatile boolean gotSecret = false;
   private Map serverNonceCaches = Collections.synchronizedMap(new HashMap());
   private static boolean disableNonceCache = Boolean.getBoolean("weblogic.security.disableNonceCache");
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugSecurityRealm");
   private static AuthenticatedSubject KERNEL_ID;

   private RequestSigner() {
      KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
      this.addBeanUpdateListener();
   }

   public static RequestSigner getInstance() {
      return theSigner;
   }

   public SignedRequestInfo signRequest(AuthenticatedSubject requester, String targetServerName) {
      SecurityServiceManager.checkKernelIdentity(requester);
      if (targetServerName != null && !targetServerName.isEmpty()) {
         byte[] nonce = SecurityServiceManager.getFastRandomBytes(16);
         String clientServerName = RequestSigner.SecurityRuntimeAccessService.runtimeAccess.getServerName();
         String ts = Long.toString(System.currentTimeMillis());
         CharacterEncoder ENCODER = new BASE64Encoder();
         String saltString = ENCODER.encodeBuffer(nonce);
         byte[] data = this.createDataBuffer(clientServerName, targetServerName, ts);
         String signatureString = ENCODER.encodeBuffer(HMAC.digest(data, this.getSecret(), nonce));
         SignedRequestInfo info = new SignedRequestInfo(signatureString, ts, clientServerName, targetServerName, saltString);
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Request signer signRequest - " + info);
         }

         return info;
      } else {
         throw new IllegalArgumentException("targetServerName must not be null or empty");
      }
   }

   public boolean verify(SignedRequestInfo info, boolean isRepeated) {
      if (info != null && info.getNonce() != null && info.getClientServerName() != null && info.getTimeStamp() != null && info.getSignature() != null && info.getTargetServerName() != null) {
         String clientServerName = info.getClientServerName();

         byte[] nonceBytes;
         byte[] signatureBytes;
         byte[] data;
         try {
            CharacterDecoder decoder = new BASE64Decoder();
            nonceBytes = decoder.decodeBuffer(info.getNonce());
            signatureBytes = decoder.decodeBuffer(info.getSignature());
            data = this.createDataBuffer(info.getClientServerName(), info.getTargetServerName(), info.getTimeStamp());
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Request signer verify - " + info);
            }
         } catch (IOException var18) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Request signer verify - exception creating data buffer: ", var18);
            }

            return false;
         }

         boolean valid = HMAC.verify(signatureBytes, data, this.getSecret(), nonceBytes);
         if (!valid) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Request signer verify - HMAC did not verify");
            }

            return false;
         } else {
            int timeoutSeconds = RequestSigner.SecurityRuntimeAccessService.runtimeAccess.getDomain().getSecurityConfiguration().getNonceTimeoutSeconds();
            long currentTime = System.currentTimeMillis();

            try {
               long noncetime = Long.parseLong(info.getTimeStamp());
               if (noncetime + (long)(timeoutSeconds * 1000) < currentTime) {
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug("Request signer verify - expired request. noncetime: " + noncetime + " currenttime: " + currentTime);
                  }

                  SecurityLogger.logConnectionNonceExpired();
                  return false;
               }
            } catch (NumberFormatException var19) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Request signer verify - number format exception: ", var19);
               }

               return false;
            }

            if (disableNonceCache) {
               return true;
            } else {
               TTLLRUCache serverNonces = this.getServerNonceCache(info.getClientServerName());
               String cacheKey = info.getNonce() + ":" + info.getTimeStamp();
               synchronized(serverNonces) {
                  boolean inCache = serverNonces.containsKey(cacheKey);
                  boolean isRepeatedNonce = false;
                  if (inCache) {
                     isRepeatedNonce = (Boolean)serverNonces.get(cacheKey);
                  }

                  serverNonces.removeExpiredEntries();
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug("Request signer size after remove: " + serverNonces.size());
                  }

                  if (inCache) {
                     if (debugLogger.isDebugEnabled()) {
                        debugLogger.debug("Request signer verify - nonce in cache");
                     }

                     if (isRepeatedNonce) {
                        if (debugLogger.isDebugEnabled()) {
                           debugLogger.debug("Request signer verify - nonce is repeated once");
                        }

                        serverNonces.put(cacheKey, new Boolean(false));
                        return true;
                     } else {
                        return false;
                     }
                  } else {
                     serverNonces.put(cacheKey, new Boolean(isRepeated));
                     return true;
                  }
               }
            }
         }
      } else {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Request signer verify - invalid info");
         }

         return false;
      }
   }

   private byte[] getSecret() {
      if (!this.gotSecret) {
         Class var1 = RequestSigner.class;
         synchronized(RequestSigner.class) {
            if (!this.gotSecret) {
               PrivilegedAction theAction = new PrivilegedAction() {
                  public Object run() {
                     SecurityConfigurationMBean securityConfigMbean = RequestSigner.SecurityRuntimeAccessService.runtimeAccess.getDomain().getSecurityConfiguration();
                     return securityConfigMbean.getCredential();
                  }
               };
               String credential = (String)SecurityServiceManager.runAs(KERNEL_ID, KERNEL_ID, theAction);
               this.domainWideSecret = credential.getBytes();
               this.gotSecret = true;
            }
         }
      }

      return this.domainWideSecret;
   }

   private synchronized TTLLRUCache getServerNonceCache(String serverName) {
      TTLLRUCache serverNonceCache = (TTLLRUCache)this.serverNonceCaches.get(serverName);
      if (serverNonceCache == null) {
         int timeoutSeconds = RequestSigner.SecurityRuntimeAccessService.runtimeAccess.getDomain().getSecurityConfiguration().getNonceTimeoutSeconds();
         int cacheSize = timeoutSeconds > 60 ? timeoutSeconds / 60 * 1024 : 1024;
         serverNonceCache = new TTLLRUCache(cacheSize, timeoutSeconds);
         this.serverNonceCaches.put(serverName, serverNonceCache);
      }

      return serverNonceCache;
   }

   private byte[] createDataBuffer(String clientServerName, String targetServerName, String ts) {
      byte[] tsBytes = null;
      byte[] clientServerNameBytes = null;
      byte[] targetServerNameBytes = null;

      byte[] tsBytes;
      byte[] clientServerNameBytes;
      byte[] targetServerNameBytes;
      try {
         tsBytes = ts.getBytes("UTF-8");
         clientServerNameBytes = clientServerName.getBytes("UTF-8");
         targetServerNameBytes = targetServerName.getBytes("UTF-8");
      } catch (UnsupportedEncodingException var8) {
         tsBytes = ts.getBytes();
         clientServerNameBytes = clientServerName.getBytes();
         targetServerNameBytes = targetServerName.getBytes();
      }

      byte[] data = new byte[tsBytes.length + clientServerNameBytes.length + targetServerNameBytes.length];
      System.arraycopy(tsBytes, 0, data, 0, tsBytes.length);
      System.arraycopy(clientServerNameBytes, 0, data, tsBytes.length, clientServerNameBytes.length);
      System.arraycopy(targetServerNameBytes, 0, data, tsBytes.length + clientServerNameBytes.length, targetServerNameBytes.length);
      return data;
   }

   private void addBeanUpdateListener() {
      SecurityConfigurationMBean secConfig = RequestSigner.SecurityRuntimeAccessService.runtimeAccess.getDomain().getSecurityConfiguration();
      BeanUpdateListener listener = new BeanUpdateListener() {
         public void prepareUpdate(BeanUpdateEvent event) throws BeanUpdateRejectedException {
         }

         public void activateUpdate(BeanUpdateEvent event) {
            BeanUpdateEvent.PropertyUpdate[] updates = event.getUpdateList();
            if (event.getProposedBean() instanceof SecurityConfigurationMBean) {
               SecurityConfigurationMBean mBean = (SecurityConfigurationMBean)event.getProposedBean();

               for(int i = 0; i < updates.length; ++i) {
                  BeanUpdateEvent.PropertyUpdate update = updates[i];
                  String propertyName = update.getPropertyName();
                  if (propertyName.equals("NonceTimeoutSeconds")) {
                     Iterator it = RequestSigner.this.serverNonceCaches.values().iterator();

                     while(it.hasNext()) {
                        TTLLRUCache cache = (TTLLRUCache)it.next();
                        synchronized(cache) {
                           if (RequestSigner.debugLogger.isDebugEnabled()) {
                              RequestSigner.debugLogger.debug("Request signer activate update - update time to live:  " + mBean.getNonceTimeoutSeconds());
                           }

                           cache.setTimeToLive(mBean.getNonceTimeoutSeconds());
                        }
                     }
                  }
               }

            }
         }

         public void rollbackUpdate(BeanUpdateEvent event) {
         }
      };
      secConfig.addBeanUpdateListener(listener);
   }

   private static final class SecurityRuntimeAccessService {
      private static final SecurityRuntimeAccess runtimeAccess = (SecurityRuntimeAccess)AccessController.doPrivileged(new PrivilegedAction() {
         public SecurityRuntimeAccess run() {
            return (SecurityRuntimeAccess)LocatorUtilities.getService(SecurityRuntimeAccess.class);
         }
      });
   }
}
