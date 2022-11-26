package com.sun.faces.config.manager.tasks;

import com.sun.faces.config.manager.FacesConfigInfo;
import com.sun.faces.config.manager.documents.DocumentInfo;
import com.sun.faces.spi.AnnotationScanner;
import com.sun.faces.spi.InjectionProvider;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ProvideMetadataToAnnotationScanTask {
   private static final Pattern JAR_PATTERN = Pattern.compile("(.*/(\\S*\\.jar)).*(/faces-config.xml|/*.\\.faces-config.xml)");
   private final DocumentInfo[] documentInfos;
   private final InjectionProvider containerConnector;
   private Set uris;
   private Set jarNames;

   public ProvideMetadataToAnnotationScanTask(DocumentInfo[] documentInfos, InjectionProvider containerConnector) {
      this.documentInfos = documentInfos;
      this.containerConnector = containerConnector;
   }

   private void initializeIvars(Set annotatedSet) {
      if (this.uris == null && this.jarNames == null) {
         this.uris = new HashSet(this.documentInfos.length);
         this.jarNames = new HashSet(this.documentInfos.length);
         DocumentInfo[] var2 = this.documentInfos;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            DocumentInfo docInfo = var2[var4];
            URI sourceURI = docInfo.getSourceURI();
            Matcher jarMatcher = JAR_PATTERN.matcher(sourceURI == null ? "" : sourceURI.toString());
            if (jarMatcher.matches()) {
               String jarName = jarMatcher.group(2);
               if (!this.jarNames.contains(jarName)) {
                  FacesConfigInfo configInfo = new FacesConfigInfo(docInfo);
                  if (!configInfo.isMetadataComplete()) {
                     this.uris.add(sourceURI);
                     this.jarNames.add(jarName);
                  } else {
                     ArrayList toRemove = new ArrayList(1);
                     String sourceURIString = sourceURI.toString();
                     if (annotatedSet != null) {
                        Iterator var12 = annotatedSet.iterator();

                        while(var12.hasNext()) {
                           Class clazz = (Class)var12.next();
                           if (sourceURIString.contains(clazz.getProtectionDomain().getCodeSource().getLocation().toString())) {
                              toRemove.add(clazz);
                           }
                        }

                        annotatedSet.removeAll(toRemove);
                     }
                  }
               }
            }
         }

      }
   }

   public Set getAnnotationScanURIs(Set annotatedSet) {
      this.initializeIvars(annotatedSet);
      return this.uris;
   }

   public Set getJarNames(Set annotatedSet) {
      this.initializeIvars(annotatedSet);
      return this.jarNames;
   }

   public AnnotationScanner getAnnotationScanner() {
      return this.containerConnector instanceof AnnotationScanner ? (AnnotationScanner)this.containerConnector : null;
   }
}
