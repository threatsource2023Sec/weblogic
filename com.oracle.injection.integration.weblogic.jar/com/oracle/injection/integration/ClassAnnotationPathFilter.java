package com.oracle.injection.integration;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Logger;
import weblogic.application.utils.annotation.ClassInfoFinder;
import weblogic.application.utils.annotation.ClassInfoFinder.Target;

public class ClassAnnotationPathFilter implements ClassInfoFinder.Filter {
   private static Logger logger = Logger.getLogger(ClassAnnotationPathFilter.class.getName());
   private ArrayList archivePaths = new ArrayList();

   public ClassAnnotationPathFilter(Collection archivePaths) {
      if (archivePaths != null) {
         Iterator var2 = archivePaths.iterator();

         while(var2.hasNext()) {
            URI path = (URI)var2.next();
            this.archivePaths.add(path.toASCIIString());
         }
      }

   }

   public boolean accept(ClassInfoFinder.Target target) {
      return target.equals(Target.CLASS);
   }

   public boolean accept(ClassInfoFinder.Target target, URL codeSourceURL, CharSequence annotationName, CharSequence className) {
      boolean result = false;
      if (!CDIUtils.cdiEnablingAnnotations.contains(className.toString())) {
         try {
            String codeSourceURI = CDIUtils.encodeURL(codeSourceURL).toURI().toASCIIString();
            Iterator var7 = this.archivePaths.iterator();

            while(var7.hasNext()) {
               String path = (String)var7.next();
               if (codeSourceURI.equals(path)) {
                  result = true;
                  break;
               }
            }
         } catch (URISyntaxException var9) {
            logger.warning("Failed to convert code source URL: " + codeSourceURL + " to a URI.  Exception: " + var9.getMessage());
         }
      }

      return result;
   }
}
