package com.bea.common.security.internal.service;

import com.bea.common.engine.ServiceConfigurationException;
import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.engine.ServiceLifecycleSpi;
import com.bea.common.engine.Services;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.internal.utils.CallbackUtils;
import com.bea.common.security.internal.utils.Delegator;
import com.bea.common.security.internal.utils.collections.SecondChanceCacheMap;
import com.bea.common.security.service.Identity;
import com.bea.common.security.service.IdentityCacheService;
import com.bea.common.security.servicecfg.IdentityCacheServiceConfig;
import java.security.InvalidParameterException;
import java.util.Arrays;
import javax.security.auth.callback.CallbackHandler;
import weblogic.security.auth.callback.IdentityDomainNames;
import weblogic.security.service.ContextHandler;

public class IdentityCacheServiceImpl implements ServiceLifecycleSpi, IdentityCacheService {
   private static final String OPSS_KEY_ATTR_NAME = "oracle.security.opss.auth.userTenantName";
   private static final String DEFAULT_IDM = "_def_Idm";
   private String[] notCacheElementNames;
   private LoggerSpi logger;
   private IdentityCache theCache = null;

   public Object init(Object config, Services dependentServices) throws ServiceInitializationException {
      this.logger = ((LoggerService)dependentServices.getService(LoggerService.SERVICE_NAME)).getLogger("com.bea.common.security.service.IdentityCacheService");
      boolean debug = this.logger.isDebugEnabled();
      String method = this.getClass().getName() + ".init";
      if (debug) {
         this.logger.debug(method);
      }

      if (config != null && config instanceof IdentityCacheServiceConfig) {
         IdentityCacheServiceConfig myconfig = (IdentityCacheServiceConfig)config;
         if (myconfig.isIdentityCacheEnabled()) {
            if (myconfig.getMaxIdentitiesInCache() < 1) {
               throw new ServiceConfigurationException(ServiceLogger.getIdentityServiceMaxIdentitiesInCacheInvalid(myconfig.getMaxIdentitiesInCache()));
            }

            this.theCache = new IdentityCache(myconfig.getMaxIdentitiesInCache(), myconfig.getIdentityCacheTTL());
            this.notCacheElementNames = myconfig.getIdentityAssertionDoNotCacheContextElements();
            if (debug) {
               this.logger.debug(method + " IdentityCache enabled, max size = " + myconfig.getMaxIdentitiesInCache() + ", TTL = " + myconfig.getIdentityCacheTTL());
            }
         } else if (debug) {
            this.logger.debug(method + " IdentityCache is not enabled");
         }

         return Delegator.getProxy(IdentityCacheService.class, this);
      } else {
         throw new ServiceConfigurationException(ServiceLogger.getExpectedConfigurationNotSupplied(method, "IdentityCacheServiceConfig"));
      }
   }

   public void shutdown() {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".shutdown" : null;
      if (debug) {
         this.logger.debug(method);
      }

   }

   public Identity getCachedIdentity(String userName) {
      return this.getCachedIdentity(new IdentityDomainNames(userName, (String)null));
   }

   public Identity getCachedIdentity(IdentityDomainNames user) {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".getCachedIdentity" : null;
      if (debug) {
         this.logger.debug(method + "(" + user + ")");
      }

      if (this.theCache == null) {
         if (debug) {
            this.logger.debug(method + " noop, cache is not enabled");
         }

         return null;
      } else {
         Identity theIdentity = this.theCache.getCachedIdentity(user);
         if (debug) {
            this.logger.debug(method + "(" + user + ") returning " + theIdentity);
         }

         return theIdentity;
      }
   }

   public Identity getCachedIdentity(IdentityDomainNames user, ContextHandler contextHandler) {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".getCachedIdentity" : null;
      if (debug) {
         this.logger.debug(method + "(" + user + " , " + contextHandler + ")");
      }

      if (this.theCache == null) {
         if (debug) {
            this.logger.debug(method + " noop, cache is not enabled");
         }

         return null;
      } else if (this.checkNotCacheElements(contextHandler)) {
         if (debug) {
            this.logger.debug(method + " return null since the Identity cache ignores the context elements present in the ContextHandler");
         }

         return null;
      } else if (user == null) {
         return null;
      } else {
         return user.getIdentityDomain() == null ? this.getCachedIdentity(user.getName(), contextHandler) : this.getCachedIdentity(user);
      }
   }

   public Identity getCachedIdentity(CallbackHandler callbackHandler, ContextHandler contextHandler) {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".getCachedIdentity" : null;
      if (debug) {
         this.logger.debug(method + "(" + callbackHandler + " , " + contextHandler + ")");
      }

      if (this.theCache == null) {
         if (debug) {
            this.logger.debug(method + " noop, cache is not enabled");
         }

         return null;
      } else {
         IdentityDomainNames user = CallbackUtils.getUser(callbackHandler, this.logger);
         return user == null ? null : this.getCachedIdentity(user, contextHandler);
      }
   }

   public Identity getCachedIdentity(String userName, ContextHandler contextHandler) {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".getCachedIdentity" : null;
      if (this.checkNotCacheElements(contextHandler)) {
         if (debug) {
            this.logger.debug(method + " return null since the Identity cache ignores the context elements present in the ContextHandler");
         }

         return null;
      } else {
         IdentityDomainNames cacheKey = getCacheKey(userName, contextHandler);
         return this.getCachedIdentity(cacheKey);
      }
   }

   public void cacheIdentity(Identity identity) {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".cachedIdentity" : null;
      if (debug) {
         this.logger.debug(method + "(" + identity + ")");
      }

      if (this.theCache == null) {
         if (debug) {
            this.logger.debug(method + " noop, cache is not enabled");
         }

      } else {
         this.theCache.putCachedIdentity(identity.getUser(), identity);
      }
   }

   public void cacheIdentity(Identity identity, ContextHandler contextHandler) {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".cachedIdentity" : null;
      if (this.checkNotCacheElements(contextHandler)) {
         if (debug) {
            this.logger.debug(method + " noop, since the Identity cache ignores the context elements present in the ContextHandler");
         }

      } else {
         IdentityDomainNames cacheKey = identity.getUser();
         if (cacheKey.getIdentityDomain() == null) {
            cacheKey = getCacheKey(identity.getUsername(), contextHandler);
         }

         if (debug) {
            this.logger.debug(method + "(" + identity + "),cacheKey= " + cacheKey);
         }

         if (this.theCache == null) {
            if (debug) {
               this.logger.debug(method + " noop, cache is not enabled");
            }

         } else {
            this.theCache.putCachedIdentity(cacheKey, identity);
         }
      }
   }

   private static String getIdentityDomainName(ContextHandler contextHandler) {
      if (contextHandler != null && contextHandler.size() >= 1) {
         Object idmObj = contextHandler.getValue("oracle.security.opss.auth.userTenantName");
         if (idmObj == null) {
            return null;
         } else if (!(idmObj instanceof String)) {
            throw new InvalidParameterException("The identity-domain/tenant name should be string, but it is " + idmObj.getClass().getName());
         } else {
            String idmName = (String)idmObj;
            if (idmName != null && !idmName.isEmpty()) {
               return idmName;
            } else {
               throw new InvalidParameterException("The identity-domain/tenant name should not be empty string");
            }
         }
      } else {
         return null;
      }
   }

   private boolean checkNotCacheElements(ContextHandler contextHandler) {
      if (contextHandler != null && contextHandler.size() >= 1) {
         if (this.notCacheElementNames != null && this.notCacheElementNames.length != 0) {
            boolean exist = false;
            String[] eleNames = contextHandler.getNames();

            for(int i = 0; i < this.notCacheElementNames.length; ++i) {
               String ncName = this.notCacheElementNames[i];
               if (Arrays.asList(eleNames).contains(ncName)) {
                  exist = true;
                  break;
               }
            }

            return exist;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private static IdentityDomainNames getCacheKey(String userName, ContextHandler contextHandler) {
      String idmName = getIdentityDomainName(contextHandler);
      return new IdentityDomainNames(userName, idmName);
   }

   private static final class IdentityCache {
      private SecondChanceCacheMap cache;
      private long cacheEOL;
      private long cacheTTL;

      private IdentityCache(int maxIdentities, long ttl) {
         this.cache = null;
         this.cacheEOL = 0L;
         this.cacheTTL = 0L;
         this.cache = new SecondChanceCacheMap(maxIdentities);
         this.cacheTTL = ttl;
         if (this.cacheTTL != 0L) {
            this.cacheEOL = System.currentTimeMillis() + this.cacheTTL;
         }

      }

      private Identity getCachedIdentity(IdentityDomainNames iddNames) {
         if (this.cacheTTL < 0L) {
            return null;
         } else {
            this.checkTTL();
            return (Identity)this.cache.get(iddNames);
         }
      }

      private void putCachedIdentity(IdentityDomainNames iddNames, Identity identity) {
         if (this.cacheTTL >= 0L) {
            this.checkTTL();
            if (!this.cache.containsKey(iddNames)) {
               this.cache.put(iddNames, identity);
            }

         }
      }

      private void checkTTL() {
         if (this.cacheTTL != 0L) {
            long curTime = System.currentTimeMillis();
            if (curTime > this.cacheEOL) {
               synchronized(this.cache) {
                  if (curTime > this.cacheEOL) {
                     this.cacheEOL = curTime + this.cacheTTL;
                     this.cache.clear();
                  }
               }
            }

         }
      }

      // $FF: synthetic method
      IdentityCache(int x0, long x1, Object x2) {
         this(x0, x1);
      }
   }
}
