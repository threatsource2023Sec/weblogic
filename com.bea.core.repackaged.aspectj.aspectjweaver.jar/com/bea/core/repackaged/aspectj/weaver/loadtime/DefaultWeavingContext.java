package com.bea.core.repackaged.aspectj.weaver.loadtime;

import com.bea.core.repackaged.aspectj.weaver.bcel.BcelWeakClassLoaderReference;
import com.bea.core.repackaged.aspectj.weaver.tools.Trace;
import com.bea.core.repackaged.aspectj.weaver.tools.TraceFactory;
import com.bea.core.repackaged.aspectj.weaver.tools.WeavingAdaptor;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;

public class DefaultWeavingContext implements IWeavingContext {
   protected BcelWeakClassLoaderReference loaderRef;
   private String shortName;
   private static Trace trace = TraceFactory.getTraceFactory().getTrace(DefaultWeavingContext.class);

   public DefaultWeavingContext(ClassLoader loader) {
      this.loaderRef = new BcelWeakClassLoaderReference(loader);
   }

   public Enumeration getResources(String name) throws IOException {
      return this.getClassLoader().getResources(name);
   }

   public String getBundleIdFromURL(URL url) {
      return "";
   }

   public String getClassLoaderName() {
      ClassLoader loader = this.getClassLoader();
      return loader != null ? loader.getClass().getName() + "@" + Integer.toHexString(System.identityHashCode(loader)) : "null";
   }

   public ClassLoader getClassLoader() {
      return this.loaderRef.getClassLoader();
   }

   public String getFile(URL url) {
      return url.getFile();
   }

   public String getId() {
      if (this.shortName == null) {
         this.shortName = this.getClassLoaderName().replace('$', '.');
         int index = this.shortName.lastIndexOf(".");
         if (index != -1) {
            this.shortName = this.shortName.substring(index + 1);
         }
      }

      return this.shortName;
   }

   public String getSuffix() {
      return this.getClassLoaderName();
   }

   public boolean isLocallyDefined(String classname) {
      String asResource = classname.replace('.', '/').concat(".class");
      ClassLoader loader = this.getClassLoader();
      URL localURL = loader.getResource(asResource);
      if (localURL == null) {
         return false;
      } else {
         boolean isLocallyDefined = true;
         ClassLoader parent = loader.getParent();
         if (parent != null) {
            URL parentURL = parent.getResource(asResource);
            if (localURL.equals(parentURL)) {
               isLocallyDefined = false;
            }
         }

         return isLocallyDefined;
      }
   }

   public List getDefinitions(ClassLoader loader, WeavingAdaptor adaptor) {
      if (trace.isTraceEnabled()) {
         trace.enter("getDefinitions", this, (Object[])(new Object[]{"goo", adaptor}));
      }

      List definitions = ((ClassLoaderWeavingAdaptor)adaptor).parseDefinitions(loader);
      if (trace.isTraceEnabled()) {
         trace.exit("getDefinitions", (Object)definitions);
      }

      return definitions;
   }
}
