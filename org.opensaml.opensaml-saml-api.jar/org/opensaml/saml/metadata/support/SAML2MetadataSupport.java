package org.opensaml.saml.metadata.support;

import java.util.Iterator;
import java.util.List;
import org.opensaml.saml.saml2.metadata.IndexedEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SAML2MetadataSupport {
   private SAML2MetadataSupport() {
   }

   public static IndexedEndpoint getDefaultIndexedEndpoint(List candidates) {
      Logger log = getLogger();
      log.debug("Selecting default IndexedEndpoint");
      if (candidates != null && !candidates.isEmpty()) {
         IndexedEndpoint firstNoDefault = null;
         Iterator var3 = candidates.iterator();

         while(var3.hasNext()) {
            IndexedEndpoint endpoint = (IndexedEndpoint)var3.next();
            if (endpoint.isDefault()) {
               log.debug("Selected IndexedEndpoint with explicit isDefault of true");
               return endpoint;
            }

            if (firstNoDefault == null && endpoint.isDefaultXSBoolean() == null) {
               firstNoDefault = endpoint;
            }
         }

         if (firstNoDefault != null) {
            log.debug("Selected first IndexedEndpoint with no explicit isDefault");
            return firstNoDefault;
         } else {
            log.debug("Selected first IndexedEndpoint with explicit isDefault of false");
            return (IndexedEndpoint)candidates.get(0);
         }
      } else {
         log.debug("IndexedEndpoint list was null or empty, returning null");
         return null;
      }
   }

   private static Logger getLogger() {
      return LoggerFactory.getLogger(SAML2MetadataSupport.class);
   }
}
