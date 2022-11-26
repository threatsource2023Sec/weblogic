package org.apache.xml.security.utils.resolver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.xml.security.signature.XMLSignatureInput;
import org.apache.xml.security.utils.ClassLoaderUtils;
import org.apache.xml.security.utils.JavaUtils;
import org.apache.xml.security.utils.resolver.implementations.ResolverDirectHTTP;
import org.apache.xml.security.utils.resolver.implementations.ResolverFragment;
import org.apache.xml.security.utils.resolver.implementations.ResolverLocalFilesystem;
import org.apache.xml.security.utils.resolver.implementations.ResolverXPointer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Attr;

public class ResourceResolver {
   private static final Logger LOG = LoggerFactory.getLogger(ResourceResolver.class);
   private static final List resolverList = new ArrayList();
   private final ResourceResolverSpi resolverSpi;

   public ResourceResolver(ResourceResolverSpi resourceResolver) {
      this.resolverSpi = resourceResolver;
   }

   public static final ResourceResolver getInstance(Attr uriAttr, String baseURI, boolean secureValidation) throws ResourceResolverException {
      ResourceResolverContext context = new ResourceResolverContext(uriAttr, baseURI, secureValidation);
      return internalGetInstance(context);
   }

   private static ResourceResolver internalGetInstance(ResourceResolverContext context) throws ResourceResolverException {
      synchronized(resolverList) {
         Iterator var2 = resolverList.iterator();

         while(var2.hasNext()) {
            ResourceResolver resolver = (ResourceResolver)var2.next();
            ResourceResolver resolverTmp = resolver;
            if (!resolver.resolverSpi.engineIsThreadSafe()) {
               try {
                  resolverTmp = new ResourceResolver((ResourceResolverSpi)resolver.resolverSpi.getClass().newInstance());
               } catch (InstantiationException var7) {
                  throw new ResourceResolverException(var7, context.uriToResolve, context.baseUri, "");
               } catch (IllegalAccessException var8) {
                  throw new ResourceResolverException(var8, context.uriToResolve, context.baseUri, "");
               }
            }

            LOG.debug("check resolvability by class {}", resolverTmp.getClass().getName());
            if (resolverTmp.canResolve(context)) {
               if (!context.secureValidation || !(resolverTmp.resolverSpi instanceof ResolverLocalFilesystem) && !(resolverTmp.resolverSpi instanceof ResolverDirectHTTP)) {
                  return resolverTmp;
               }

               Object[] exArgs = new Object[]{resolverTmp.resolverSpi.getClass().getName()};
               throw new ResourceResolverException("signature.Reference.ForbiddenResolver", exArgs, context.uriToResolve, context.baseUri);
            }
         }
      }

      Object[] exArgs = new Object[]{context.uriToResolve != null ? context.uriToResolve : "null", context.baseUri};
      throw new ResourceResolverException("utils.resolver.noClass", exArgs, context.uriToResolve, context.baseUri);
   }

   public static ResourceResolver getInstance(Attr uri, String baseURI, List individualResolvers) throws ResourceResolverException {
      return getInstance(uri, baseURI, individualResolvers, true);
   }

   public static ResourceResolver getInstance(Attr uri, String baseURI, List individualResolvers, boolean secureValidation) throws ResourceResolverException {
      LOG.debug("I was asked to create a ResourceResolver and got {}", individualResolvers == null ? 0 : individualResolvers.size());
      ResourceResolverContext context = new ResourceResolverContext(uri, baseURI, secureValidation);
      if (individualResolvers != null) {
         for(int i = 0; i < individualResolvers.size(); ++i) {
            ResourceResolver resolver = (ResourceResolver)individualResolvers.get(i);
            if (resolver != null) {
               String currentClass = resolver.resolverSpi.getClass().getName();
               LOG.debug("check resolvability by class {}", currentClass);
               if (resolver.canResolve(context)) {
                  return resolver;
               }
            }
         }
      }

      return internalGetInstance(context);
   }

   public static void register(String className) {
      JavaUtils.checkRegisterPermission();

      try {
         Class resourceResolverClass = ClassLoaderUtils.loadClass(className, ResourceResolver.class);
         register(resourceResolverClass, false);
      } catch (ClassNotFoundException var2) {
         LOG.warn("Error loading resolver " + className + " disabling it");
      }

   }

   public static void registerAtStart(String className) {
      JavaUtils.checkRegisterPermission();

      try {
         Class resourceResolverClass = ClassLoaderUtils.loadClass(className, ResourceResolver.class);
         register(resourceResolverClass, true);
      } catch (ClassNotFoundException var2) {
         LOG.warn("Error loading resolver " + className + " disabling it");
      }

   }

   public static void register(Class className, boolean start) {
      JavaUtils.checkRegisterPermission();

      try {
         ResourceResolverSpi resourceResolverSpi = (ResourceResolverSpi)className.newInstance();
         register(resourceResolverSpi, start);
      } catch (IllegalAccessException var3) {
         LOG.warn("Error loading resolver " + className + " disabling it");
      } catch (InstantiationException var4) {
         LOG.warn("Error loading resolver " + className + " disabling it");
      }

   }

   public static void register(ResourceResolverSpi resourceResolverSpi, boolean start) {
      JavaUtils.checkRegisterPermission();
      synchronized(resolverList) {
         if (start) {
            resolverList.add(0, new ResourceResolver(resourceResolverSpi));
         } else {
            resolverList.add(new ResourceResolver(resourceResolverSpi));
         }
      }

      LOG.debug("Registered resolver: {}", resourceResolverSpi.toString());
   }

   public static void registerDefaultResolvers() {
      synchronized(resolverList) {
         resolverList.add(new ResourceResolver(new ResolverFragment()));
         resolverList.add(new ResourceResolver(new ResolverLocalFilesystem()));
         resolverList.add(new ResourceResolver(new ResolverXPointer()));
         resolverList.add(new ResourceResolver(new ResolverDirectHTTP()));
      }
   }

   public XMLSignatureInput resolve(Attr uri, String baseURI, boolean secureValidation) throws ResourceResolverException {
      ResourceResolverContext context = new ResourceResolverContext(uri, baseURI, secureValidation);
      return this.resolverSpi.engineResolveURI(context);
   }

   public void setProperty(String key, String value) {
      this.resolverSpi.engineSetProperty(key, value);
   }

   public String getProperty(String key) {
      return this.resolverSpi.engineGetProperty(key);
   }

   public void addProperties(Map properties) {
      this.resolverSpi.engineAddProperies(properties);
   }

   public String[] getPropertyKeys() {
      return this.resolverSpi.engineGetPropertyKeys();
   }

   public boolean understandsProperty(String propertyToTest) {
      return this.resolverSpi.understandsProperty(propertyToTest);
   }

   private boolean canResolve(ResourceResolverContext context) {
      return this.resolverSpi.engineCanResolveURI(context);
   }
}
