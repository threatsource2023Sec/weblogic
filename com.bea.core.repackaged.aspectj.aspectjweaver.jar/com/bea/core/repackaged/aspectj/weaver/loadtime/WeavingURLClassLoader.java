package com.bea.core.repackaged.aspectj.weaver.loadtime;

import com.bea.core.repackaged.aspectj.bridge.AbortException;
import com.bea.core.repackaged.aspectj.weaver.bcel.ExtensibleURLClassLoader;
import com.bea.core.repackaged.aspectj.weaver.tools.Trace;
import com.bea.core.repackaged.aspectj.weaver.tools.TraceFactory;
import com.bea.core.repackaged.aspectj.weaver.tools.WeavingAdaptor;
import com.bea.core.repackaged.aspectj.weaver.tools.WeavingClassLoader;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class WeavingURLClassLoader extends ExtensibleURLClassLoader implements WeavingClassLoader {
   public static final String WEAVING_CLASS_PATH = "aj.class.path";
   public static final String WEAVING_ASPECT_PATH = "aj.aspect.path";
   private URL[] aspectURLs;
   private WeavingAdaptor adaptor;
   private boolean initializingAdaptor;
   private Map generatedClasses;
   private static Trace trace = TraceFactory.getTraceFactory().getTrace(WeavingURLClassLoader.class);

   public WeavingURLClassLoader(ClassLoader parent) {
      this(getURLs(getClassPath()), getURLs(getAspectPath()), parent);
   }

   public WeavingURLClassLoader(URL[] urls, ClassLoader parent) {
      super(urls, parent);
      this.generatedClasses = new HashMap();
      if (trace.isTraceEnabled()) {
         trace.enter("<init>", this, (Object[])(new Object[]{urls, parent}));
      }

      if (trace.isTraceEnabled()) {
         trace.exit("<init>");
      }

   }

   public WeavingURLClassLoader(URL[] classURLs, URL[] aspectURLs, ClassLoader parent) {
      super(classURLs, parent);
      this.generatedClasses = new HashMap();
      this.aspectURLs = aspectURLs;
      if (this.aspectURLs.length > 0 || this.getParent() instanceof WeavingClassLoader) {
         try {
            this.adaptor = new WeavingAdaptor(this);
         } catch (ExceptionInInitializerError var5) {
            var5.printStackTrace(System.out);
            throw var5;
         }
      }

   }

   private static String getAspectPath() {
      return System.getProperty("aj.aspect.path", "");
   }

   private static String getClassPath() {
      return System.getProperty("aj.class.path", "");
   }

   private static URL[] getURLs(String path) {
      List urlList = new ArrayList();
      StringTokenizer t = new StringTokenizer(path, File.pathSeparator);

      while(t.hasMoreTokens()) {
         File f = new File(t.nextToken().trim());

         try {
            if (f.exists()) {
               URL url = f.toURL();
               if (url != null) {
                  urlList.add(url);
               }
            }
         } catch (MalformedURLException var5) {
         }
      }

      URL[] urls = new URL[urlList.size()];
      urlList.toArray(urls);
      return urls;
   }

   protected void addURL(URL url) {
      if (this.adaptor == null) {
         this.createAdaptor();
      }

      this.adaptor.addURL(url);
      super.addURL(url);
   }

   protected Class defineClass(String name, byte[] b, CodeSource cs) throws IOException {
      if (trace.isTraceEnabled()) {
         trace.enter("defineClass", this, (Object[])(new Object[]{name, b, cs}));
      }

      if (!this.initializingAdaptor) {
         if (this.adaptor == null && !this.initializingAdaptor) {
            this.createAdaptor();
         }

         try {
            b = this.adaptor.weaveClass(name, b, false);
         } catch (AbortException var8) {
            trace.error("defineClass", var8);
            throw var8;
         } catch (Throwable var9) {
            trace.error("defineClass", var9);
         }
      }

      Class clazz;
      try {
         clazz = super.defineClass(name, b, cs);
      } catch (Throwable var7) {
         trace.error("Weaving class problem. Original class has been returned. The error was caused because of: " + var7, var7);
         clazz = super.defineClass(name, b, cs);
      }

      if (trace.isTraceEnabled()) {
         trace.exit("defineClass", (Object)clazz);
      }

      return clazz;
   }

   private void createAdaptor() {
      DefaultWeavingContext weavingContext = new DefaultWeavingContext(this) {
         public String getClassLoaderName() {
            ClassLoader loader = this.getClassLoader();
            return loader.getClass().getName();
         }
      };
      ClassLoaderWeavingAdaptor clwAdaptor = new ClassLoaderWeavingAdaptor();
      this.initializingAdaptor = true;
      clwAdaptor.initialize(this, weavingContext);
      this.initializingAdaptor = false;
      this.adaptor = clwAdaptor;
   }

   protected byte[] getBytes(String name) throws IOException {
      byte[] bytes = super.getBytes(name);
      return bytes == null ? (byte[])((byte[])this.generatedClasses.remove(name)) : bytes;
   }

   public URL[] getAspectURLs() {
      return this.aspectURLs;
   }

   public void acceptClass(String name, byte[] classBytes, byte[] weavedBytes) {
      this.generatedClasses.put(name, weavedBytes);
   }
}
