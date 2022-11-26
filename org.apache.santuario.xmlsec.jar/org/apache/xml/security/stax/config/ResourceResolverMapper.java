package org.apache.xml.security.stax.config;

import java.util.ArrayList;
import java.util.List;
import org.apache.xml.security.configuration.ResolverType;
import org.apache.xml.security.configuration.ResourceResolversType;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.ResourceResolver;
import org.apache.xml.security.stax.ext.ResourceResolverLookup;
import org.apache.xml.security.utils.ClassLoaderUtils;

public class ResourceResolverMapper {
   private static List resourceResolvers;

   private ResourceResolverMapper() {
   }

   protected static synchronized void init(ResourceResolversType resourceResolversType, Class callingClass) throws Exception {
      List handlerList = resourceResolversType.getResolver();
      resourceResolvers = new ArrayList(handlerList.size() + 1);

      for(int i = 0; i < handlerList.size(); ++i) {
         ResolverType uriResolverType = (ResolverType)handlerList.get(i);
         resourceResolvers.add((ResourceResolverLookup)ClassLoaderUtils.loadClass(uriResolverType.getJAVACLASS(), callingClass).newInstance());
      }

   }

   public static ResourceResolver getResourceResolver(String uri, String baseURI) throws XMLSecurityException {
      for(int i = 0; i < resourceResolvers.size(); ++i) {
         ResourceResolverLookup resourceResolver = (ResourceResolverLookup)resourceResolvers.get(i);
         ResourceResolverLookup rr = resourceResolver.canResolve(uri, baseURI);
         if (rr != null) {
            return rr.newInstance(uri, baseURI);
         }
      }

      throw new XMLSecurityException("utils.resolver.noClass", new Object[]{uri, baseURI});
   }
}
