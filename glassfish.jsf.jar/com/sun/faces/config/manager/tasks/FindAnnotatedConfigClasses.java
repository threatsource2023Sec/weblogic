package com.sun.faces.config.manager.tasks;

import com.sun.faces.config.InitFacesContext;
import com.sun.faces.config.manager.spi.FilterClassesFromFacesInitializerAnnotationProvider;
import com.sun.faces.spi.AnnotationProvider;
import com.sun.faces.spi.AnnotationProviderFactory;
import com.sun.faces.spi.AnnotationScanner;
import com.sun.faces.util.Timer;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import javax.servlet.ServletContext;

public class FindAnnotatedConfigClasses implements Callable {
   private InitFacesContext facesContext;
   private AnnotationProvider provider;
   private ProvideMetadataToAnnotationScanTask metadataGetter;
   private Set annotatedSet;

   public FindAnnotatedConfigClasses(ServletContext servletContext, InitFacesContext facesContext, ProvideMetadataToAnnotationScanTask metadataGetter) {
      this.facesContext = facesContext;
      this.provider = AnnotationProviderFactory.createAnnotationProvider(servletContext);
      this.metadataGetter = metadataGetter;
      this.annotatedSet = (Set)servletContext.getAttribute("com.sun.faces.AnnotatedClasses");
   }

   public Map call() throws Exception {
      Timer t = Timer.getInstance();
      if (t != null) {
         t.startTiming();
      }

      this.facesContext.addInitContextEntryForCurrentThread();
      Set scanUris = null;
      AnnotationScanner annotationScanner = this.metadataGetter.getAnnotationScanner();
      if (this.provider instanceof FilterClassesFromFacesInitializerAnnotationProvider && annotationScanner != null) {
         ((FilterClassesFromFacesInitializerAnnotationProvider)this.provider).setAnnotationScanner(annotationScanner, this.metadataGetter.getJarNames(this.annotatedSet));
         scanUris = Collections.emptySet();
      } else {
         scanUris = this.metadataGetter.getAnnotationScanURIs(this.annotatedSet);
      }

      Map annotatedClasses = this.provider.getAnnotatedClasses(scanUris);
      if (t != null) {
         t.stopTiming();
         t.logResult("Configuration annotation scan complete.");
      }

      return annotatedClasses;
   }
}
