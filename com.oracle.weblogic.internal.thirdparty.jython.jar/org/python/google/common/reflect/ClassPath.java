package org.python.google.common.reflect;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.jar.Attributes.Name;
import java.util.logging.Logger;
import javax.annotation.Nullable;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.VisibleForTesting;
import org.python.google.common.base.CharMatcher;
import org.python.google.common.base.Preconditions;
import org.python.google.common.base.Predicate;
import org.python.google.common.base.Splitter;
import org.python.google.common.collect.FluentIterable;
import org.python.google.common.collect.ImmutableMap;
import org.python.google.common.collect.ImmutableSet;
import org.python.google.common.collect.Maps;
import org.python.google.common.collect.MultimapBuilder;
import org.python.google.common.collect.SetMultimap;
import org.python.google.common.collect.Sets;
import org.python.google.common.collect.UnmodifiableIterator;
import org.python.google.common.io.ByteSource;
import org.python.google.common.io.CharSource;
import org.python.google.common.io.Resources;

@Beta
public final class ClassPath {
   private static final Logger logger = Logger.getLogger(ClassPath.class.getName());
   private static final Predicate IS_TOP_LEVEL = new Predicate() {
      public boolean apply(ClassInfo info) {
         return info.className.indexOf(36) == -1;
      }
   };
   private static final Splitter CLASS_PATH_ATTRIBUTE_SEPARATOR = Splitter.on(" ").omitEmptyStrings();
   private static final String CLASS_FILE_NAME_EXTENSION = ".class";
   private final ImmutableSet resources;

   private ClassPath(ImmutableSet resources) {
      this.resources = resources;
   }

   public static ClassPath from(ClassLoader classloader) throws IOException {
      DefaultScanner scanner = new DefaultScanner();
      scanner.scan(classloader);
      return new ClassPath(scanner.getResources());
   }

   public ImmutableSet getResources() {
      return this.resources;
   }

   public ImmutableSet getAllClasses() {
      return FluentIterable.from((Iterable)this.resources).filter(ClassInfo.class).toSet();
   }

   public ImmutableSet getTopLevelClasses() {
      return FluentIterable.from((Iterable)this.resources).filter(ClassInfo.class).filter(IS_TOP_LEVEL).toSet();
   }

   public ImmutableSet getTopLevelClasses(String packageName) {
      Preconditions.checkNotNull(packageName);
      ImmutableSet.Builder builder = ImmutableSet.builder();
      UnmodifiableIterator var3 = this.getTopLevelClasses().iterator();

      while(var3.hasNext()) {
         ClassInfo classInfo = (ClassInfo)var3.next();
         if (classInfo.getPackageName().equals(packageName)) {
            builder.add((Object)classInfo);
         }
      }

      return builder.build();
   }

   public ImmutableSet getTopLevelClassesRecursive(String packageName) {
      Preconditions.checkNotNull(packageName);
      String packagePrefix = packageName + '.';
      ImmutableSet.Builder builder = ImmutableSet.builder();
      UnmodifiableIterator var4 = this.getTopLevelClasses().iterator();

      while(var4.hasNext()) {
         ClassInfo classInfo = (ClassInfo)var4.next();
         if (classInfo.getName().startsWith(packagePrefix)) {
            builder.add((Object)classInfo);
         }
      }

      return builder.build();
   }

   @VisibleForTesting
   static String getClassName(String filename) {
      int classNameEnd = filename.length() - ".class".length();
      return filename.substring(0, classNameEnd).replace('/', '.');
   }

   @VisibleForTesting
   static File toFile(URL url) {
      Preconditions.checkArgument(url.getProtocol().equals("file"));

      try {
         return new File(url.toURI());
      } catch (URISyntaxException var2) {
         return new File(url.getPath());
      }
   }

   @VisibleForTesting
   static final class DefaultScanner extends Scanner {
      private final SetMultimap resources = MultimapBuilder.hashKeys().linkedHashSetValues().build();

      ImmutableSet getResources() {
         ImmutableSet.Builder builder = ImmutableSet.builder();
         Iterator var2 = this.resources.entries().iterator();

         while(var2.hasNext()) {
            Map.Entry entry = (Map.Entry)var2.next();
            builder.add((Object)ClassPath.ResourceInfo.of((String)entry.getValue(), (ClassLoader)entry.getKey()));
         }

         return builder.build();
      }

      protected void scanJarFile(ClassLoader classloader, JarFile file) {
         Enumeration entries = file.entries();

         while(entries.hasMoreElements()) {
            JarEntry entry = (JarEntry)entries.nextElement();
            if (!entry.isDirectory() && !entry.getName().equals("META-INF/MANIFEST.MF")) {
               this.resources.get(classloader).add(entry.getName());
            }
         }

      }

      protected void scanDirectory(ClassLoader classloader, File directory) throws IOException {
         Set currentPath = new HashSet();
         currentPath.add(directory.getCanonicalFile());
         this.scanDirectory(directory, classloader, "", currentPath);
      }

      private void scanDirectory(File directory, ClassLoader classloader, String packagePrefix, Set currentPath) throws IOException {
         File[] files = directory.listFiles();
         if (files == null) {
            ClassPath.logger.warning("Cannot read directory " + directory);
         } else {
            File[] var6 = files;
            int var7 = files.length;

            for(int var8 = 0; var8 < var7; ++var8) {
               File f = var6[var8];
               String name = f.getName();
               if (f.isDirectory()) {
                  File deref = f.getCanonicalFile();
                  if (currentPath.add(deref)) {
                     this.scanDirectory(deref, classloader, packagePrefix + name + "/", currentPath);
                     currentPath.remove(deref);
                  }
               } else {
                  String resourceName = packagePrefix + name;
                  if (!resourceName.equals("META-INF/MANIFEST.MF")) {
                     this.resources.get(classloader).add(resourceName);
                  }
               }
            }

         }
      }
   }

   abstract static class Scanner {
      private final Set scannedUris = Sets.newHashSet();

      public final void scan(ClassLoader classloader) throws IOException {
         UnmodifiableIterator var2 = getClassPathEntries(classloader).entrySet().iterator();

         while(var2.hasNext()) {
            Map.Entry entry = (Map.Entry)var2.next();
            this.scan((File)entry.getKey(), (ClassLoader)entry.getValue());
         }

      }

      protected abstract void scanDirectory(ClassLoader var1, File var2) throws IOException;

      protected abstract void scanJarFile(ClassLoader var1, JarFile var2) throws IOException;

      @VisibleForTesting
      final void scan(File file, ClassLoader classloader) throws IOException {
         if (this.scannedUris.add(file.getCanonicalFile())) {
            this.scanFrom(file, classloader);
         }

      }

      private void scanFrom(File file, ClassLoader classloader) throws IOException {
         try {
            if (!file.exists()) {
               return;
            }
         } catch (SecurityException var4) {
            ClassPath.logger.warning("Cannot access " + file + ": " + var4);
            return;
         }

         if (file.isDirectory()) {
            this.scanDirectory(classloader, file);
         } else {
            this.scanJar(file, classloader);
         }

      }

      private void scanJar(File file, ClassLoader classloader) throws IOException {
         JarFile jarFile;
         try {
            jarFile = new JarFile(file);
         } catch (IOException var13) {
            return;
         }

         try {
            UnmodifiableIterator var4 = getClassPathFromManifest(file, jarFile.getManifest()).iterator();

            while(var4.hasNext()) {
               File path = (File)var4.next();
               this.scan(path, classloader);
            }

            this.scanJarFile(classloader, jarFile);
         } finally {
            try {
               jarFile.close();
            } catch (IOException var12) {
            }

         }
      }

      @VisibleForTesting
      static ImmutableSet getClassPathFromManifest(File jarFile, @Nullable Manifest manifest) {
         if (manifest == null) {
            return ImmutableSet.of();
         } else {
            ImmutableSet.Builder builder = ImmutableSet.builder();
            String classpathAttribute = manifest.getMainAttributes().getValue(Name.CLASS_PATH.toString());
            if (classpathAttribute != null) {
               Iterator var4 = ClassPath.CLASS_PATH_ATTRIBUTE_SEPARATOR.split(classpathAttribute).iterator();

               while(var4.hasNext()) {
                  String path = (String)var4.next();

                  URL url;
                  try {
                     url = getClassPathEntry(jarFile, path);
                  } catch (MalformedURLException var8) {
                     ClassPath.logger.warning("Invalid Class-Path entry: " + path);
                     continue;
                  }

                  if (url.getProtocol().equals("file")) {
                     builder.add((Object)ClassPath.toFile(url));
                  }
               }
            }

            return builder.build();
         }
      }

      @VisibleForTesting
      static ImmutableMap getClassPathEntries(ClassLoader classloader) {
         LinkedHashMap entries = Maps.newLinkedHashMap();
         ClassLoader parent = classloader.getParent();
         if (parent != null) {
            entries.putAll(getClassPathEntries(parent));
         }

         if (classloader instanceof URLClassLoader) {
            URLClassLoader urlClassLoader = (URLClassLoader)classloader;
            URL[] var4 = urlClassLoader.getURLs();
            int var5 = var4.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               URL entry = var4[var6];
               if (entry.getProtocol().equals("file")) {
                  File file = ClassPath.toFile(entry);
                  if (!entries.containsKey(file)) {
                     entries.put(file, classloader);
                  }
               }
            }
         }

         return ImmutableMap.copyOf((Map)entries);
      }

      @VisibleForTesting
      static URL getClassPathEntry(File jarFile, String path) throws MalformedURLException {
         return new URL(jarFile.toURI().toURL(), path);
      }
   }

   @Beta
   public static final class ClassInfo extends ResourceInfo {
      private final String className;

      ClassInfo(String resourceName, ClassLoader loader) {
         super(resourceName, loader);
         this.className = ClassPath.getClassName(resourceName);
      }

      public String getPackageName() {
         return Reflection.getPackageName(this.className);
      }

      public String getSimpleName() {
         int lastDollarSign = this.className.lastIndexOf(36);
         String packageName;
         if (lastDollarSign != -1) {
            packageName = this.className.substring(lastDollarSign + 1);
            return CharMatcher.digit().trimLeadingFrom(packageName);
         } else {
            packageName = this.getPackageName();
            return packageName.isEmpty() ? this.className : this.className.substring(packageName.length() + 1);
         }
      }

      public String getName() {
         return this.className;
      }

      public Class load() {
         try {
            return this.loader.loadClass(this.className);
         } catch (ClassNotFoundException var2) {
            throw new IllegalStateException(var2);
         }
      }

      public String toString() {
         return this.className;
      }
   }

   @Beta
   public static class ResourceInfo {
      private final String resourceName;
      final ClassLoader loader;

      static ResourceInfo of(String resourceName, ClassLoader loader) {
         return (ResourceInfo)(resourceName.endsWith(".class") ? new ClassInfo(resourceName, loader) : new ResourceInfo(resourceName, loader));
      }

      ResourceInfo(String resourceName, ClassLoader loader) {
         this.resourceName = (String)Preconditions.checkNotNull(resourceName);
         this.loader = (ClassLoader)Preconditions.checkNotNull(loader);
      }

      public final URL url() {
         URL url = this.loader.getResource(this.resourceName);
         if (url == null) {
            throw new NoSuchElementException(this.resourceName);
         } else {
            return url;
         }
      }

      public final ByteSource asByteSource() {
         return Resources.asByteSource(this.url());
      }

      public final CharSource asCharSource(Charset charset) {
         return Resources.asCharSource(this.url(), charset);
      }

      public final String getResourceName() {
         return this.resourceName;
      }

      public int hashCode() {
         return this.resourceName.hashCode();
      }

      public boolean equals(Object obj) {
         if (!(obj instanceof ResourceInfo)) {
            return false;
         } else {
            ResourceInfo that = (ResourceInfo)obj;
            return this.resourceName.equals(that.resourceName) && this.loader == that.loader;
         }
      }

      public String toString() {
         return this.resourceName;
      }
   }
}
