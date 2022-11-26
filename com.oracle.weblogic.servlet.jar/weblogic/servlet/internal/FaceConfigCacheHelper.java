package weblogic.servlet.internal;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import weblogic.descriptor.DescriptorCache;
import weblogic.servlet.HTTPLogger;
import weblogic.servlet.utils.ResourceLocation;

public class FaceConfigCacheHelper {
   private static final String FACES_CACHE_DIR = ".faces_cache";

   public static Set parseFacesConfigs(Collection facesConfigs, String tempDir, String logCtx) {
      return parseFacesConfigs(facesConfigs, new File(tempDir), logCtx);
   }

   public static Set parseFacesConfigs(Collection facesConfigs, File tempDir, String logCtx) {
      if (facesConfigs != null && facesConfigs.size() != 0) {
         Set managedBeanClasses = new HashSet();
         long t = System.currentTimeMillis();
         File rootTempDir = new File(tempDir, ".faces_cache");
         rootTempDir.mkdirs();
         Iterator it = facesConfigs.iterator();

         while(it.hasNext()) {
            ResourceLocation facesConfig = (ResourceLocation)it.next();
            FacesConfigsIOHelper helper = new FacesConfigsIOHelper(facesConfig);

            try {
               File cacheDir = new File(rootTempDir, facesConfig.getURI().replace('\\', '/'));
               managedBeanClasses.addAll((Set)DescriptorCache.getInstance().parseXML(cacheDir, helper));
               if (HTTPDebugLogger.isEnabled()) {
                  Iterator j = managedBeanClasses.iterator();

                  while(j.hasNext()) {
                     HTTPDebugLogger.debug("[FaceConfigCacheHelper] Found managed bean class '" + j.next() + "' in faces config at " + facesConfig.getLocation());
                  }
               }
            } catch (XMLStreamException var12) {
               if (logCtx != null) {
                  HTTPLogger.logFacesConfigParseException(logCtx, facesConfig.getLocation(), var12);
               }
            } catch (IOException var13) {
               if (logCtx != null) {
                  HTTPLogger.logFacesConfigParseException(logCtx, facesConfig.getLocation(), var13);
               }
            }
         }

         if (HTTPDebugLogger.isEnabled()) {
            HTTPDebugLogger.debug("[FaceConfigCacheHelper] parseFacesConfigs() took : " + (System.currentTimeMillis() - t));
         }

         return managedBeanClasses;
      } else {
         return Collections.EMPTY_SET;
      }
   }

   private static class FacesConfigsIOHelper extends WebAppIOHelper {
      FacesConfigsIOHelper(ResourceLocation config) {
         super(config);
      }

      protected Object parseXMLInternal(XMLStreamReader parser) throws IOException, XMLStreamException {
         Set managedBeans = new HashSet();
         boolean inManagedBeanClass = false;
         boolean inManagedBeanScope = false;
         String managedBeanClass = null;
         String managedBeanScope = null;

         for(int event = parser.next(); event != 8; event = parser.next()) {
            switch (event) {
               case 1:
                  if ("managed-bean".equals(parser.getLocalName())) {
                     managedBeanClass = null;
                     managedBeanScope = null;
                  } else if ("managed-bean-class".equals(parser.getLocalName())) {
                     inManagedBeanClass = true;
                  } else if ("managed-bean-scope".equals(parser.getLocalName())) {
                     inManagedBeanScope = true;
                  }
                  break;
               case 2:
                  if ("managed-bean".equals(parser.getLocalName())) {
                     if (managedBeanClass != null && managedBeanScope != null && !"none".equals(managedBeanScope)) {
                        managedBeans.add(managedBeanClass);
                     }
                  } else if ("managed-bean-class".equals(parser.getLocalName())) {
                     inManagedBeanClass = false;
                  } else if ("managed-bean-scope".equals(parser.getLocalName())) {
                     inManagedBeanScope = false;
                  }
                  break;
               case 4:
               case 12:
                  String text;
                  if (inManagedBeanScope) {
                     text = parser.getText().trim();
                     if (text.length() != 0) {
                        managedBeanScope = text;
                     }
                  }

                  if (inManagedBeanClass) {
                     text = parser.getText().trim();
                     if (text.length() != 0) {
                        managedBeanClass = text;
                     }
                  }
            }
         }

         return managedBeans;
      }
   }
}
