package com.bea.core.repackaged.springframework.beans.factory.xml;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.core.io.ClassPathResource;
import com.bea.core.repackaged.springframework.core.io.Resource;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

public class BeansDtdResolver implements EntityResolver {
   private static final String DTD_EXTENSION = ".dtd";
   private static final String DTD_NAME = "spring-beans";
   private static final Log logger = LogFactory.getLog(BeansDtdResolver.class);

   @Nullable
   public InputSource resolveEntity(@Nullable String publicId, @Nullable String systemId) throws IOException {
      if (logger.isTraceEnabled()) {
         logger.trace("Trying to resolve XML entity with public ID [" + publicId + "] and system ID [" + systemId + "]");
      }

      if (systemId != null && systemId.endsWith(".dtd")) {
         int lastPathSeparator = systemId.lastIndexOf(47);
         int dtdNameStart = systemId.indexOf("spring-beans", lastPathSeparator);
         if (dtdNameStart != -1) {
            String dtdFile = "spring-beans.dtd";
            if (logger.isTraceEnabled()) {
               logger.trace("Trying to locate [" + dtdFile + "] in Spring jar on classpath");
            }

            try {
               Resource resource = new ClassPathResource(dtdFile, this.getClass());
               InputSource source = new InputSource(resource.getInputStream());
               source.setPublicId(publicId);
               source.setSystemId(systemId);
               if (logger.isTraceEnabled()) {
                  logger.trace("Found beans DTD [" + systemId + "] in classpath: " + dtdFile);
               }

               return source;
            } catch (FileNotFoundException var8) {
               if (logger.isDebugEnabled()) {
                  logger.debug("Could not resolve beans DTD [" + systemId + "]: not found in classpath", var8);
               }
            }
         }
      }

      return null;
   }

   public String toString() {
      return "EntityResolver for spring-beans DTD";
   }
}
