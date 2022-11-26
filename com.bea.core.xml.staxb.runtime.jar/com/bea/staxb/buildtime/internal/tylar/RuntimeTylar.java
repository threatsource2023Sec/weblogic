package com.bea.staxb.buildtime.internal.tylar;

import com.bea.staxb.buildtime.internal.bts.BindingFile;
import com.bea.staxb.buildtime.internal.bts.BindingLoader;
import com.bea.staxb.buildtime.internal.bts.BindingMappingFile;
import com.bea.staxb.buildtime.internal.bts.BuiltinBindingLoader;
import com.bea.staxb.buildtime.internal.bts.CompositeBindingLoader;
import com.bea.util.jam.JamClassLoader;
import com.bea.util.jam.JamServiceFactory;
import com.bea.xbean.schema.BuiltinSchemaTypeSystem;
import com.bea.xbean.schema.SchemaTypeLoaderImpl;
import com.bea.xbean.xb.xsdschema.SchemaDocument;
import com.bea.xml.ResourceLoader;
import com.bea.xml.SchemaTypeLoader;
import com.bea.xml.XmlRuntimeException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import weblogic.kernel.KernelStatus;
import weblogic.utils.cmm.MemoryPressureListener;
import weblogic.utils.cmm.MemoryPressureService;
import weblogic.utils.cmm.Scrubber;

public class RuntimeTylar implements Tylar, TylarConstants, Scrubber {
   private ClassLoader mClassLoader = null;
   private String mDescription = null;
   private URL[] mLocations = null;
   private URL[] mExcludeLocations = null;
   private BindingFile[] mBindingFiles = null;
   private SchemaTypeLoader mSchemaTypeLoader = null;
   private BindingLoader mBindingLoader = null;
   private JamClassLoader mJamClassLoader = null;
   private static final boolean VERBOSE = false;
   private static long FiveMinutes = 300000L;
   private static ArrayList cmmInstances = new ArrayList();

   public RuntimeTylar(ClassLoader loader) throws IOException {
      if (loader == null) {
         throw new IllegalArgumentException("null loader");
      } else {
         sanityCheck(loader);
         this.mClassLoader = loader;
         this.mLocations = new URL[0];
         this.mDescription = "loaded from " + loader.toString();
         if (KernelStatus.isServer()) {
            synchronized(cmmInstances) {
               cmmInstances.add(this);
            }
         }

      }
   }

   public static void setupCMMListeners(ServiceLocator locator) {
      synchronized(cmmInstances) {
         Iterator var2 = cmmInstances.iterator();

         while(var2.hasNext()) {
            RuntimeTylar instance = (RuntimeTylar)var2.next();
            instance.setupMemoryListeners(locator);
            ServiceLocatorUtilities.addOneConstant(locator, instance, (String)null, new Type[]{Scrubber.class});
         }

         cmmInstances.clear();
      }
   }

   private void setupMemoryListeners(ServiceLocator locator) {
      MemoryPressureListener ml = new MemoryPressureListener() {
         public void handleCMMLevel(int newLevel) {
            if (newLevel > 2) {
               RuntimeTylar.this.releaseBindingDelegates();
            }

         }
      };

      try {
         MemoryPressureService memoryPressureService = (MemoryPressureService)locator.getService(MemoryPressureService.class, new Annotation[0]);
         memoryPressureService.registerListener(this.getClass().getName(), ml);
      } catch (RuntimeException var4) {
         throw var4;
      } catch (Exception var5) {
         throw new RuntimeException(var5);
      }
   }

   public String getDescription() {
      return this.mDescription;
   }

   public URL[] getLocations() {
      return this.mLocations;
   }

   public URL getLocation() {
      return null;
   }

   public BindingLoader getBindingLoader() {
      try {
         if (this.mBindingLoader == null) {
            BindingFile[] bfs = this.getBindingFiles();
            BindingLoader[] loaders = new BindingLoader[bfs.length + 1];
            System.arraycopy(bfs, 0, loaders, 1, bfs.length);
            loaders[0] = BuiltinBindingLoader.getBuiltinBindingLoader(false);
            this.mBindingLoader = CompositeBindingLoader.forPath(loaders);
         }

         return this.mBindingLoader;
      } catch (Exception var3) {
         throw new RuntimeException(var3);
      }
   }

   public BindingFile[] getBindingFiles() {
      try {
         long total = 0L;
         if (this.mBindingFiles == null) {
            List list = new ArrayList();
            HashMap mappings = new HashMap();
            Enumeration urls = this.mClassLoader.getResources("META-INF/binding-mapping-file.ser");

            while(true) {
               URL next;
               String loc;
               boolean skipLocation;
               do {
                  do {
                     if (!urls.hasMoreElements()) {
                        label322:
                        for(int i = 0; i < 5; ++i) {
                           loc = "META-INF/binding-file.ser";
                           if (i > 0) {
                              loc = loc + i;
                           }

                           urls = this.mClassLoader.getResources(loc);
                           if (!urls.hasMoreElements() && i > 0) {
                              break;
                           }

                           while(true) {
                              while(true) {
                                 if (!urls.hasMoreElements()) {
                                    continue label322;
                                 }

                                 URL next = (URL)urls.nextElement();
                                 String loc = next.toExternalForm();
                                 BindingMappingFile bmf = (BindingMappingFile)mappings.get(loc.substring(0, loc.lastIndexOf(loc)));
                                 if (bmf != null && i == 0) {
                                    bmf.setDelegateBindingLoaderURL(next);
                                 } else {
                                    InputStream in = null;

                                    try {
                                       in = next.openStream();
                                       long start = System.currentTimeMillis();
                                       list.add(BindingFile.forSer(in));
                                    } catch (IOException var25) {
                                       throw var25;
                                    } finally {
                                       if (in != null) {
                                          in.close();
                                       }

                                    }
                                 }
                              }
                           }
                        }

                        this.mBindingFiles = new BindingFile[list.size()];
                        list.toArray(this.mBindingFiles);
                        return this.mBindingFiles;
                     }

                     next = (URL)urls.nextElement();
                     loc = next.toExternalForm();
                     loc = loc.substring(0, loc.lastIndexOf("META-INF/binding-mapping-file.ser"));
                  } while(mappings.containsKey(loc));

                  if (this.mExcludeLocations == null) {
                     break;
                  }

                  skipLocation = false;
                  URL[] var9 = this.mExcludeLocations;
                  int var10 = var9.length;

                  for(int var11 = 0; var11 < var10; ++var11) {
                     URL exclude = var9[var11];
                     if (loc.contains(exclude.toExternalForm())) {
                        skipLocation = true;
                     }
                  }
               } while(skipLocation);

               InputStream in = null;

               try {
                  URLConnection urlConn = next.openConnection();
                  urlConn.setUseCaches(false);
                  in = urlConn.getInputStream();
                  long start = System.currentTimeMillis();
                  BindingFile bf = BindingMappingFile.forSer(in);
                  list.add(bf);
                  mappings.put(loc, bf);
               } catch (IOException var26) {
                  throw var26;
               } finally {
                  if (in != null) {
                     in.close();
                  }

               }
            }
         } else {
            return this.mBindingFiles;
         }
      } catch (Exception var29) {
         throw new XmlRuntimeException(var29);
      }
   }

   private synchronized void releaseBindingDelegates() {
      if (this.mBindingFiles != null) {
         BindingFile[] var1 = this.mBindingFiles;
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            BindingFile d = var1[var3];
            if (d instanceof BindingMappingFile) {
               ((BindingMappingFile)d).releaseDelegate();
            }
         }
      }

   }

   private synchronized void releaseInactiveBindingDelegates() {
      if (this.mBindingFiles != null) {
         long fiveMinAgo = System.currentTimeMillis() - FiveMinutes;
         BindingFile[] var3 = this.mBindingFiles;
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            BindingFile bf = var3[var5];
            if (bf instanceof BindingMappingFile) {
               BindingMappingFile bmf = (BindingMappingFile)bf;
               if (bmf.getLastAccess() <= fiveMinAgo) {
                  bmf.releaseDelegate();
               }
            }
         }

      }
   }

   public SchemaDocument[] getSchemas() {
      throw new UnsupportedOperationException("SchemaDocuments cannot be retrieved at runtime.");
   }

   public SchemaTypeLoader getSchemaTypeLoader() {
      if (this.mSchemaTypeLoader == null) {
         try {
            Class.forName("com.bea.xml.XmlBeans");
         } catch (ClassNotFoundException var2) {
         }

         this.mSchemaTypeLoader = SchemaTypeLoaderImpl.build(new SchemaTypeLoader[]{BuiltinSchemaTypeSystem.get()}, (ResourceLoader)null, this.mClassLoader);
      }

      return this.mSchemaTypeLoader;
   }

   public JamClassLoader getJamClassLoader() {
      if (this.mJamClassLoader == null) {
         this.mJamClassLoader = JamServiceFactory.getInstance().createJamClassLoader(this.mClassLoader);
      }

      return this.mJamClassLoader;
   }

   public static boolean hasTylar(ClassLoader cl) {
      try {
         Enumeration urls = cl.getResources("META-INF/binding-file.ser");
         return urls.hasMoreElements();
      } catch (IOException var2) {
         return false;
      }
   }

   public void setExcludeLocations(URL[] excludes) {
      this.mExcludeLocations = excludes;
   }

   private static void sanityCheck(ClassLoader cl) throws IOException {
      if (!hasTylar(cl)) {
         StringWriter msg = new StringWriter();
         msg.write("The given classloader does not contain any xbean binding\nfiles (META-INF/binding-file.ser\n");
         if (cl instanceof URLClassLoader) {
            msg.write("URLClassLoader path:");
            URL[] cl_urls = ((URLClassLoader)cl).getURLs();

            for(int i = 0; i < cl_urls.length; ++i) {
               msg.write(cl_urls[i].toString());
               msg.write("\n");
            }
         } else {
            msg.write(cl.getClass().toString());
         }

         throw new IOException(msg.toString());
      }
   }

   public void scrubADubDub() {
      this.releaseInactiveBindingDelegates();
   }
}
