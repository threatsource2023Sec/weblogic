package weblogic.aspects;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.WeakHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipOutputStream;
import weblogic.utils.Getopt2;
import weblogic.utils.classloaders.ClassPreProcessor;
import weblogic.utils.classloaders.ClasspathClassLoader;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.classloaders.Source;
import weblogic.utils.jars.VirtualJarFactory;
import weblogic.utils.jars.VirtualJarFile;

public class AspectClassPreProcessor implements ClassPreProcessor {
   public static final Logger logger = Logger.getLogger("weblogic.aspects");
   private Map classLoaderMap = new WeakHashMap();
   private boolean creating = false;
   private static AspectBundle NULL_BUNDLE = new AspectBundle();

   public void initialize(Hashtable properties) {
   }

   public byte[] preProcess(String name, byte[] bytes) {
      ClassLoader ccl;
      for(ccl = Thread.currentThread().getContextClassLoader(); ccl != null && !(ccl instanceof GenericClassLoader); ccl = ccl.getParent()) {
      }

      if (ccl instanceof GenericClassLoader) {
         GenericClassLoader gcl = (GenericClassLoader)ccl;
         AspectBundle bundle = this.getAspectSystem(gcl);
         if (bundle != null && (bundle.aspectIncludePattern == null || bundle.aspectIncludePattern.matcher(name).find()) && (bundle.aspectExcludePattern == null || !bundle.aspectExcludePattern.matcher(name).find())) {
            try {
               byte[] result = bundle.aspectSystem.weaveClass(name, bytes);
               return result == null ? bytes : result;
            } catch (IOException var7) {
               logger.log(Level.WARNING, "Could not weave class " + name, var7);
            }
         }
      }

      return bytes;
   }

   private synchronized AspectBundle getAspectSystem(GenericClassLoader gcl) {
      if (this.creating) {
         return null;
      } else {
         AspectBundle bundle = (AspectBundle)this.classLoaderMap.get(gcl);
         if (bundle == NULL_BUNDLE) {
            return null;
         } else {
            if (bundle == null) {
               this.creating = true;

               try {
                  bundle = createAspectSystem(gcl);
                  if (bundle == null) {
                     this.classLoaderMap.put(gcl, NULL_BUNDLE);
                     Object var3 = null;
                     return (AspectBundle)var3;
                  }

                  this.classLoaderMap.put(gcl, bundle);
               } finally {
                  this.creating = false;
               }
            }

            return bundle;
         }
      }
   }

   private static AspectBundle createAspectSystem(GenericClassLoader gcl) {
      try {
         String aspectInclude = null;
         String aspectExclude = null;
         String aspectClassName = null;
         Properties aspectSystemProperties = new Properties();
         ClassLoader cl = gcl;
         InputStream is = getSourceAsStream("wlaspect.properties", gcl);

         while(is != null) {
            Properties p = new Properties();
            p.load(is);
            is.close();
            String systemName = (String)p.remove("aspect.system");
            if (systemName != null && (aspectClassName == null || systemName.equals(aspectClassName)) && !"false".equals(p.remove("aspect.enable"))) {
               aspectExclude = addToRegex((String)p.remove("aspect.exclude"), aspectExclude);
               aspectInclude = addToRegex((String)p.remove("aspect.include"), aspectInclude);
               aspectClassName = systemName;
               Iterator i = p.keySet().iterator();

               while(i.hasNext()) {
                  String key = (String)i.next();
                  if (!aspectSystemProperties.contains(key)) {
                     aspectSystemProperties.setProperty(key, p.getProperty(key));
                  }
               }
            }

            cl = ((ClassLoader)cl).getParent();
            if (cl instanceof GenericClassLoader) {
               is = getSourceAsStream("wlaspect.properties", (GenericClassLoader)cl);
            } else if (cl != null) {
               is = ((ClassLoader)cl).getResourceAsStream("wlaspect.properties");
            } else {
               is = null;
            }
         }

         if (aspectClassName == null) {
            logger.info("Could not find aspect.system property");
            return null;
         }

         AspectBundle bundle = new AspectBundle();
         if (aspectInclude != null) {
            bundle.aspectIncludePattern = Pattern.compile(aspectInclude);
         }

         if (aspectExclude != null) {
            bundle.aspectExcludePattern = Pattern.compile(aspectExclude);
         }

         try {
            bundle.aspectSystem = (AspectSystem)Class.forName(aspectClassName).newInstance();
            bundle.aspectSystem.init(gcl, aspectSystemProperties);
            gcl.addClassFinder(new AspectSystemClassFinder(bundle.aspectSystem));
            return bundle;
         } catch (InstantiationException var11) {
            logger.log(Level.WARNING, "Could not create AspectSystem: " + aspectClassName, var11);
         } catch (IllegalAccessException var12) {
            logger.log(Level.WARNING, "Could not access AspectSystem: " + aspectClassName, var12);
         } catch (ClassNotFoundException var13) {
            logger.log(Level.WARNING, "Could not find AspectSystem: " + aspectClassName, var13);
         }
      } catch (IOException var14) {
         logger.log(Level.WARNING, "Could not initialize aspect subsystem", var14);
      }

      return null;
   }

   private static String addToRegex(String term, String regex) {
      if (term == null) {
         return regex;
      } else {
         String[] list = term.split(",");

         for(int i = 0; i < list.length; ++i) {
            if (regex == null) {
               regex = list[i];
            } else {
               regex = regex + "|" + list[i];
            }
         }

         return regex;
      }
   }

   public static InputStream getSourceAsStream(String name, GenericClassLoader gcl) throws IOException {
      Source source = gcl.getClassFinder().getSource(name);
      if (source != null) {
         return source.getInputStream();
      } else {
         ClassLoader parent = gcl.getParent();
         if (parent instanceof GenericClassLoader) {
            GenericClassLoader gclParent = (GenericClassLoader)parent;
            return getSourceAsStream(name, gclParent);
         } else if (parent != null) {
            URL url = parent.getResource(name);
            return url == null ? null : url.openStream();
         } else {
            return null;
         }
      }
   }

   public static void main(String[] args) throws ZipException, IOException {
      Getopt2 go = new Getopt2();
      go.addOption("src", "jar or classes directory file", "This is the jar file of classes to process");
      go.addOption("outjar", "jar file", "This is the output of the jar needed to modify the src jar");
      go.addOption("aspects", "jar file", "These are the aspects you want to apply");
      go.grok(args);
      if (!go.hasOption("src") || !go.hasOption("outjar") || !go.hasOption("aspects")) {
         go.usageAndExit("Apply");
      }

      String srcjarFilename = go.getOption("src");
      String outjarFilename = go.getOption("outjar");
      String aspectsFilename = go.getOption("aspects");
      GenericClassLoader aspectLoader = new ClasspathClassLoader(aspectsFilename, new ClassLoader() {
         public Class findClass(String name) throws ClassNotFoundException {
            return super.findClass(name);
         }
      });
      AspectBundle bundle = createAspectSystem(aspectLoader);
      ZipOutputStream outFile = new ZipOutputStream(new FileOutputStream(outjarFilename));
      VirtualJarFile srcFile = VirtualJarFactory.createVirtualJar(new File(srcjarFilename));
      Iterator e = srcFile.entries();

      while(true) {
         ZipEntry entry;
         String filename;
         String name;
         do {
            do {
               do {
                  if (!e.hasNext()) {
                     Map sources = bundle.aspectSystem.getAllSources();
                     Iterator i = sources.keySet().iterator();

                     while(i.hasNext()) {
                        filename = (String)i.next();
                        byte[] bytes = (byte[])((byte[])sources.get(filename));
                        String filename = filename.replace('.', '/') + ".class";
                        ZipEntry newEntry = new ZipEntry(filename);
                        outFile.putNextEntry(newEntry);
                        outFile.write(bytes);
                        outFile.closeEntry();
                     }

                     outFile.finish();
                     outFile.close();
                     return;
                  }

                  entry = (ZipEntry)e.next();
                  filename = entry.getName();
               } while(!filename.endsWith(".class"));

               name = filename.substring(0, filename.length() - 6);
               name = name.replace('/', '.');
            } while(bundle.aspectIncludePattern != null && !bundle.aspectIncludePattern.matcher(name).find());
         } while(bundle.aspectExcludePattern != null && bundle.aspectExcludePattern.matcher(name).find());

         InputStream is = srcFile.getInputStream(entry);
         ByteArrayOutputStream baos = new ByteArrayOutputStream();

         int r;
         while((r = is.read()) != -1) {
            baos.write(r);
         }

         byte[] bytes = baos.toByteArray();
         byte[] result = bundle.aspectSystem.weaveClass(name, bytes);
         if (result != null && bytes != result) {
            ZipEntry newEntry = new ZipEntry(filename);
            outFile.putNextEntry(newEntry);
            outFile.write(result);
            outFile.closeEntry();
         }
      }
   }

   private static class AspectBundle {
      AspectSystem aspectSystem;
      Pattern aspectIncludePattern;
      Pattern aspectExcludePattern;

      private AspectBundle() {
      }

      // $FF: synthetic method
      AspectBundle(Object x0) {
         this();
      }
   }
}
