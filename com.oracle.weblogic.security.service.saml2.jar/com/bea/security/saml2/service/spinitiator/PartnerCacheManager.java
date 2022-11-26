package com.bea.security.saml2.service.spinitiator;

import com.bea.security.saml2.config.SAML2ConfigSpi;
import java.util.HashMap;

class PartnerCacheManager {
   private static HashMap caches = new HashMap();

   static synchronized PartnerCache getPartnerCache(SAML2ConfigSpi config) {
      String cssName = config.getDomainName() + config.getRealmName();
      if (caches.get(cssName) != null) {
         return (PartnerCache)caches.get(cssName);
      } else {
         PartnerCache cache = new PartnerCache(config);
         caches.put(cssName, cache);
         return cache;
      }
   }

   static synchronized void removePartnerCache(SAML2ConfigSpi config) {
      String cssName = config.getDomainName() + config.getRealmName();
      PartnerCache cache = (PartnerCache)caches.remove(cssName);
      if (cache != null) {
         cache.removePartnerChangeListener();
      }

   }
}
