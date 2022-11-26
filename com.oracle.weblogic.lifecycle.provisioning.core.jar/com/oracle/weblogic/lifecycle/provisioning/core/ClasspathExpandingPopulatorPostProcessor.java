package com.oracle.weblogic.lifecycle.provisioning.core;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.FileSystem;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.hk2.api.HK2Loader;
import org.glassfish.hk2.api.PopulatorPostProcessor;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.DescriptorImpl;
import org.glassfish.hk2.utilities.HK2LoaderImpl;

public class ClasspathExpandingPopulatorPostProcessor implements PopulatorPostProcessor {
   private final URI base = this.getBase();

   public DescriptorImpl process(ServiceLocator serviceLocator, DescriptorImpl descriptor) {
      String className = this.getClass().getName();
      String methodName = "process";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "process", new Object[]{serviceLocator, descriptor});
      }

      if (descriptor != null && this.base != null && descriptor.getLoader() == null) {
         Map metadata = descriptor.getMetadata();
         if (metadata != null && !metadata.isEmpty()) {
            Collection urlStrings = (Collection)metadata.get("hk2LoaderUrls");
            if (urlStrings != null && !urlStrings.isEmpty()) {
               int size = urlStrings.size();

               assert size > 0;

               Collection classpathExtensionUrls = null;

               try {
                  classpathExtensionUrls = this.toUrls(this.base, urlStrings);
               } catch (IOException | UnsupportedOperationException | IllegalArgumentException var12) {
                  classpathExtensionUrls = null;
                  if (logger != null && logger.isLoggable(Level.WARNING)) {
                     logger.logp(Level.WARNING, className, "process", var12.getMessage(), var12);
                  }
               }

               if (classpathExtensionUrls != null && !classpathExtensionUrls.isEmpty()) {
                  HK2Loader newLoader = new HK2LoaderImpl(new URLClassLoader((URL[])classpathExtensionUrls.toArray(new URL[classpathExtensionUrls.size()])));
                  descriptor.setLoader(newLoader);

                  assert newLoader == descriptor.getLoader();

                  if (logger != null && logger.isLoggable(Level.FINE)) {
                     logger.logp(Level.FINE, className, "process", "Installed new HK2LoaderImpl in descriptor with URLs: {0}\nDescriptor: {1}", new Object[]{classpathExtensionUrls, descriptor});
                  }
               }
            } else if (logger != null && logger.isLoggable(Level.FINER)) {
               logger.logp(Level.FINER, className, "process", "The following descriptor had an hk2LoaderUrls metadata key, but no actual URLs: {0}", descriptor);
            }
         }
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(className, "process", descriptor);
      }

      return descriptor;
   }

   protected Collection toUrls(URI baseUri, Collection pathMatcherPatterns) throws IOException {
      String className = this.getClass().getName();
      String methodName = "toUrls";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "toUrls", new Object[]{baseUri, pathMatcherPatterns});
      }

      Collection returnValue = null;
      if (baseUri != null && pathMatcherPatterns != null && !pathMatcherPatterns.isEmpty()) {
         FileSystem fileSystem = null;
         Exception exception = null;

         try {
            fileSystem = getFileSystem(baseUri);

            assert fileSystem != null;

            Path path = Paths.get(baseUri);

            assert path != null;

            assert path.getFileSystem() == fileSystem;

            int size = pathMatcherPatterns.size();
            ArrayList pathMatchers = new ArrayList(size);
            pathMatchers.ensureCapacity(size);
            Iterator var12 = pathMatcherPatterns.iterator();

            while(var12.hasNext()) {
               String pathMatcherPattern = (String)var12.next();
               if (pathMatcherPattern != null) {
                  if (!pathMatcherPattern.startsWith("glob:") && !pathMatcherPattern.startsWith("regex:")) {
                     pathMatcherPattern = "glob:" + pathMatcherPattern;
                  }

                  PathMatcher pathMatcher = fileSystem.getPathMatcher(pathMatcherPattern);

                  assert pathMatcher != null;

                  pathMatchers.add(pathMatcher);
               }
            }

            if (!pathMatchers.isEmpty()) {
               Collection includedPaths = (new PathTreeMatcher(path, pathMatchers)).getIncludedPaths();
               if (includedPaths != null && !includedPaths.isEmpty()) {
                  returnValue = new ArrayList(includedPaths.size());
                  Iterator var30 = includedPaths.iterator();

                  while(var30.hasNext()) {
                     Path includedPath = (Path)var30.next();
                     if (includedPath != null) {
                        URI pathUri = includedPath.toUri();
                        if (pathUri != null) {
                           URL pathUrl = pathUri.toURL();
                           if (pathUrl != null) {
                              returnValue.add(pathUrl);
                           }
                        }
                     }
                  }
               }
            }
         } catch (IOException | RuntimeException var27) {
            exception = var27;
            throw var27;
         } finally {
            if (fileSystem != null && fileSystem != FileSystems.getDefault()) {
               try {
                  fileSystem.close();
               } catch (UnsupportedOperationException var25) {
                  if (logger.isLoggable(Level.WARNING)) {
                     logger.logp(Level.WARNING, className, "toUrls", var25.getMessage(), var25);
                  }
               } catch (IOException var26) {
                  if (exception == null) {
                     throw var26;
                  }

                  exception.addSuppressed(var26);
               }
            }

         }
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(className, "toUrls", returnValue);
      }

      return returnValue;
   }

   protected boolean resourceExists(URL url) {
      String className = this.getClass().getName();
      String methodName = "resourceExists";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "resourceExists", url);
      }

      boolean returnValue = false;
      if (url != null) {
         try {
            InputStream inputStream = url.openStream();
            Throwable var7 = null;

            try {
               returnValue = inputStream != null;
            } catch (Throwable var17) {
               var7 = var17;
               throw var17;
            } finally {
               if (inputStream != null) {
                  if (var7 != null) {
                     try {
                        inputStream.close();
                     } catch (Throwable var16) {
                        var7.addSuppressed(var16);
                     }
                  } else {
                     inputStream.close();
                  }
               }

            }
         } catch (IOException var19) {
            returnValue = false;
         }
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(className, "resourceExists", returnValue);
      }

      return returnValue;
   }

   protected URI getBase() {
      String className = this.getClass().getName();
      String methodName = "getBase";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "getBase");
      }

      String mwHome = null;

      try {
         mwHome = System.getProperty("BEA_HOME");
      } catch (SecurityException var10) {
         mwHome = null;
      }

      if (mwHome == null) {
         try {
            mwHome = System.getProperty("bea.home");
         } catch (SecurityException var9) {
            mwHome = null;
         }
      }

      if (mwHome == null) {
         try {
            mwHome = System.getenv("MW_HOME");
         } catch (SecurityException var8) {
            mwHome = null;
         }
      }

      if (mwHome == null) {
         try {
            mwHome = System.getenv("WL_HOME");
         } catch (SecurityException var7) {
            mwHome = null;
         }

         if (mwHome != null) {
            mwHome = (new File(mwHome)).getParent();
         }
      }

      URI returnValue;
      if (mwHome == null) {
         returnValue = null;
      } else {
         returnValue = (new File(mwHome)).toURI();
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(className, "getBase", returnValue);
      }

      return returnValue;
   }

   public static final FileSystem getFileSystem(URI uri) throws IOException {
      FileSystem returnValue = null;
      if (uri != null) {
         try {
            try {
               returnValue = FileSystems.getFileSystem(uri);
            } catch (IllegalArgumentException var3) {
               if (uri.isOpaque()) {
                  throw var3;
               }

               returnValue = FileSystems.getFileSystem(uri.resolve("/"));
            }
         } catch (FileSystemNotFoundException var4) {
            returnValue = FileSystems.newFileSystem(uri, Collections.emptyMap());
         }
      }

      return returnValue;
   }
}
