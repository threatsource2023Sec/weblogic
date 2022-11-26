package com.bea.core.repackaged.jdt.internal.compiler.apt.util;

import com.bea.core.repackaged.jdt.internal.compiler.DefaultErrorHandlingPolicies;
import com.bea.core.repackaged.jdt.internal.compiler.batch.FileSystem;
import com.bea.core.repackaged.jdt.internal.compiler.batch.Main;
import com.bea.core.repackaged.jdt.internal.compiler.batch.ModuleFinder;
import com.bea.core.repackaged.jdt.internal.compiler.env.AccessRule;
import com.bea.core.repackaged.jdt.internal.compiler.env.AccessRuleSet;
import com.bea.core.repackaged.jdt.internal.compiler.impl.CompilerOptions;
import com.bea.core.repackaged.jdt.internal.compiler.parser.Parser;
import com.bea.core.repackaged.jdt.internal.compiler.problem.DefaultProblemFactory;
import com.bea.core.repackaged.jdt.internal.compiler.problem.ProblemReporter;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.zip.ZipException;
import javax.lang.model.SourceVersion;
import javax.tools.FileObject;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.JavaFileObject.Kind;

public class EclipseFileManager implements StandardJavaFileManager {
   private static final String NO_EXTENSION = "";
   static final int HAS_EXT_DIRS = 1;
   static final int HAS_BOOTCLASSPATH = 2;
   static final int HAS_ENDORSED_DIRS = 4;
   static final int HAS_PROCESSORPATH = 8;
   static final int HAS_PROC_MODULEPATH = 16;
   Map archivesCache;
   Charset charset;
   Locale locale;
   ModuleLocationHandler locationHandler;
   final Map classloaders;
   int flags;
   boolean isOnJvm9;
   File jrtHome;
   JrtFileSystem jrtSystem;
   public ResourceBundle bundle;
   String releaseVersion;

   public EclipseFileManager(Locale locale, Charset charset) {
      this.locale = locale == null ? Locale.getDefault() : locale;
      this.charset = charset == null ? Charset.defaultCharset() : charset;
      this.locationHandler = new ModuleLocationHandler();
      this.classloaders = new HashMap();
      this.archivesCache = new HashMap();
      this.isOnJvm9 = this.isRunningJvm9();

      try {
         this.initialize(com.bea.core.repackaged.jdt.internal.compiler.util.Util.getJavaHome());
      } catch (IOException var5) {
         var5.printStackTrace();
      }

      try {
         this.bundle = Main.ResourceBundleFactory.getBundle(this.locale);
      } catch (MissingResourceException var4) {
         System.out.println("Missing resource : " + "com.bea.core.repackaged.jdt.internal.compiler.batch.messages".replace('.', '/') + ".properties for locale " + locale);
      }

   }

   protected void initialize(File javahome) throws IOException {
      if (this.isOnJvm9) {
         this.jrtSystem = new JrtFileSystem(javahome);
         this.archivesCache.put(javahome, this.jrtSystem);
         this.jrtHome = javahome;
         this.locationHandler.newSystemLocation(StandardLocation.SYSTEM_MODULES, (JrtFileSystem)this.jrtSystem);
      } else {
         this.setLocation(StandardLocation.PLATFORM_CLASS_PATH, this.getDefaultBootclasspath());
      }

      Iterable defaultClasspath = this.getDefaultClasspath();
      this.setLocation(StandardLocation.CLASS_PATH, defaultClasspath);
      this.setLocation(StandardLocation.ANNOTATION_PROCESSOR_PATH, defaultClasspath);
   }

   public void close() throws IOException {
      this.locationHandler.close();
      Iterator var2 = this.archivesCache.values().iterator();

      while(var2.hasNext()) {
         Archive archive = (Archive)var2.next();
         archive.close();
      }

      this.archivesCache.clear();
      var2 = this.classloaders.values().iterator();

      while(var2.hasNext()) {
         URLClassLoader cl = (URLClassLoader)var2.next();
         cl.close();
      }

      this.classloaders.clear();
   }

   private void collectAllMatchingFiles(JavaFileManager.Location location, File file, String normalizedPackageName, Set kinds, boolean recurse, ArrayList collector) {
      if (file.equals(this.jrtHome)) {
         if (location instanceof ModuleLocationHandler.ModuleLocationWrapper) {
            List list = this.jrtSystem.list((ModuleLocationHandler.ModuleLocationWrapper)location, normalizedPackageName, kinds, recurse, this.charset);
            Iterator var9 = list.iterator();

            while(var9.hasNext()) {
               JrtFileSystem.JrtFileObject fo = (JrtFileSystem.JrtFileObject)var9.next();
               JavaFileObject.Kind kind = this.getKind(this.getExtension(fo.entryName));
               if (kinds.contains(kind)) {
                  collector.add(fo);
               }
            }
         }
      } else {
         JavaFileObject.Kind kind;
         String key;
         if (this.isArchive(file)) {
            Archive archive = this.getArchive(file);
            if (archive == Archive.UNKNOWN_ARCHIVE) {
               return;
            }

            key = normalizedPackageName;
            if (!normalizedPackageName.endsWith("/")) {
               key = normalizedPackageName + '/';
            }

            if (recurse) {
               Iterator var22 = archive.allPackages().iterator();

               while(true) {
                  List types;
                  String packageName;
                  do {
                     do {
                        if (!var22.hasNext()) {
                           return;
                        }

                        packageName = (String)var22.next();
                     } while(!packageName.startsWith(key));

                     types = archive.getTypes(packageName);
                  } while(types == null);

                  Iterator var13 = types.iterator();

                  while(var13.hasNext()) {
                     String[] entry = (String[])var13.next();
                     kind = this.getKind(this.getExtension(entry[0]));
                     if (kinds.contains(kind)) {
                        collector.add(archive.getArchiveFileObject(packageName + entry[0], entry[1], this.charset));
                     }
                  }
               }
            } else {
               List types = archive.getTypes(key);
               if (types != null) {
                  Iterator var25 = types.iterator();

                  while(var25.hasNext()) {
                     String[] entry = (String[])var25.next();
                     JavaFileObject.Kind kind = this.getKind(this.getExtension(entry[0]));
                     if (kinds.contains(kind)) {
                        collector.add(archive.getArchiveFileObject(key + entry[0], entry[1], this.charset));
                     }
                  }
               }
            }
         } else {
            File currentFile = new File(file, normalizedPackageName);
            if (!currentFile.exists()) {
               return;
            }

            try {
               key = currentFile.getCanonicalPath();
            } catch (IOException var15) {
               return;
            }

            if (File.separatorChar == '/') {
               if (!key.endsWith(normalizedPackageName)) {
                  return;
               }
            } else if (!key.endsWith(normalizedPackageName.replace('/', File.separatorChar))) {
               return;
            }

            File[] files = currentFile.listFiles();
            if (files != null) {
               File[] var29 = files;
               int var28 = files.length;

               for(int var26 = 0; var26 < var28; ++var26) {
                  File f = var29[var26];
                  if (f.isDirectory() && recurse) {
                     this.collectAllMatchingFiles(location, file, normalizedPackageName + '/' + f.getName(), kinds, recurse, collector);
                  } else {
                     kind = this.getKind(f);
                     if (kinds.contains(kind)) {
                        collector.add(new EclipseFileObject(normalizedPackageName + f.getName(), f.toURI(), kind, this.charset));
                     }
                  }
               }
            }
         }
      }

   }

   private Iterable concatFiles(Iterable iterable, Iterable iterable2) {
      ArrayList list = new ArrayList();
      if (iterable2 == null) {
         return iterable;
      } else {
         Iterator iterator = iterable.iterator();

         while(iterator.hasNext()) {
            list.add((File)iterator.next());
         }

         iterator = iterable2.iterator();

         while(iterator.hasNext()) {
            list.add((File)iterator.next());
         }

         return list;
      }
   }

   public void flush() throws IOException {
      Iterator var2 = this.archivesCache.values().iterator();

      while(var2.hasNext()) {
         Archive archive = (Archive)var2.next();
         archive.flush();
      }

   }

   private Archive getArchive(File f) {
      Archive archive = (Archive)this.archivesCache.get(f);
      if (archive == null) {
         archive = Archive.UNKNOWN_ARCHIVE;
         if (f.exists()) {
            try {
               archive = new Archive(f);
            } catch (ZipException var3) {
            } catch (IOException var4) {
            }

            if (archive != null) {
               this.archivesCache.put(f, archive);
            }
         }

         this.archivesCache.put(f, archive);
      }

      return archive;
   }

   public ClassLoader getClassLoader(JavaFileManager.Location location) {
      this.validateNonModuleLocation(location);
      Iterable files = this.getLocation(location);
      if (files == null) {
         return null;
      } else {
         URLClassLoader cl = (URLClassLoader)this.classloaders.get(location);
         if (cl == null) {
            ArrayList allURLs = new ArrayList();
            Iterator var6 = files.iterator();

            while(var6.hasNext()) {
               File f = (File)var6.next();

               try {
                  allURLs.add(f.toURI().toURL());
               } catch (MalformedURLException var8) {
                  throw new RuntimeException(var8);
               }
            }

            URL[] result = new URL[allURLs.size()];
            cl = new URLClassLoader((URL[])allURLs.toArray(result), this.getClass().getClassLoader());
            this.classloaders.put(location, cl);
         }

         return cl;
      }
   }

   private Iterable getPathsFrom(String path) {
      ArrayList paths = new ArrayList();
      ArrayList files = new ArrayList();

      try {
         this.processPathEntries(4, paths, path, this.charset.name(), false, false);
      } catch (IllegalArgumentException var6) {
         return null;
      }

      Iterator var5 = paths.iterator();

      while(var5.hasNext()) {
         FileSystem.Classpath classpath = (FileSystem.Classpath)var5.next();
         files.add(new File(classpath.getPath()));
      }

      return files;
   }

   Iterable getDefaultBootclasspath() {
      List files = new ArrayList();
      String javaversion = System.getProperty("java.version");
      if (javaversion.length() > 3) {
         javaversion = javaversion.substring(0, 3);
      }

      long jdkLevel = CompilerOptions.versionToJdkLevel(javaversion);
      if (jdkLevel < 3276800L) {
         return null;
      } else {
         Iterator var6 = com.bea.core.repackaged.jdt.internal.compiler.util.Util.collectFilesNames().iterator();

         while(var6.hasNext()) {
            FileSystem.Classpath classpath = (FileSystem.Classpath)var6.next();
            files.add(new File(classpath.getPath()));
         }

         return files;
      }
   }

   Iterable getDefaultClasspath() {
      ArrayList files = new ArrayList();
      String classProp = System.getProperty("java.class.path");
      if (classProp != null && classProp.length() != 0) {
         StringTokenizer tokenizer = new StringTokenizer(classProp, File.pathSeparator);

         while(tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            File file = new File(token);
            if (file.exists()) {
               files.add(file);
            }
         }

         return files;
      } else {
         return null;
      }
   }

   private Iterable getEndorsedDirsFrom(String path) {
      ArrayList paths = new ArrayList();
      ArrayList files = new ArrayList();

      try {
         this.processPathEntries(4, paths, path, this.charset.name(), false, false);
      } catch (IllegalArgumentException var6) {
         return null;
      }

      Iterator var5 = paths.iterator();

      while(var5.hasNext()) {
         FileSystem.Classpath classpath = (FileSystem.Classpath)var5.next();
         files.add(new File(classpath.getPath()));
      }

      return files;
   }

   private Iterable getExtdirsFrom(String path) {
      ArrayList paths = new ArrayList();
      ArrayList files = new ArrayList();

      try {
         this.processPathEntries(4, paths, path, this.charset.name(), false, false);
      } catch (IllegalArgumentException var6) {
         return null;
      }

      Iterator var5 = paths.iterator();

      while(var5.hasNext()) {
         FileSystem.Classpath classpath = (FileSystem.Classpath)var5.next();
         files.add(new File(classpath.getPath()));
      }

      return files;
   }

   private String getExtension(File file) {
      String name = file.getName();
      return this.getExtension(name);
   }

   private String getExtension(String name) {
      int index = name.lastIndexOf(46);
      return index == -1 ? "" : name.substring(index);
   }

   public FileObject getFileForInput(JavaFileManager.Location location, String packageName, String relativeName) throws IOException {
      this.validateNonModuleLocation(location);
      Iterable files = this.getLocation(location);
      if (files == null) {
         throw new IllegalArgumentException("Unknown location : " + location);
      } else {
         String normalizedFileName = this.normalizedFileName(packageName, relativeName);
         Iterator var7 = files.iterator();

         while(var7.hasNext()) {
            File file = (File)var7.next();
            if (file.isDirectory()) {
               File f = new File(file, normalizedFileName);
               if (f.exists()) {
                  return new EclipseFileObject(packageName + File.separator + relativeName, f.toURI(), this.getKind(f), this.charset);
               }
            } else if (this.isArchive(file)) {
               Archive archive = this.getArchive(file);
               if (archive != Archive.UNKNOWN_ARCHIVE && archive.contains(normalizedFileName)) {
                  return archive.getArchiveFileObject(normalizedFileName, (String)null, this.charset);
               }
            }
         }

         return null;
      }
   }

   private String normalizedFileName(String packageName, String relativeName) {
      StringBuilder sb = new StringBuilder();
      sb.append(this.normalized(packageName));
      if (sb.length() > 0) {
         sb.append('/');
      }

      sb.append(relativeName.replace('\\', '/'));
      return sb.toString();
   }

   public FileObject getFileForOutput(JavaFileManager.Location location, String packageName, String relativeName, FileObject sibling) throws IOException {
      this.validateOutputLocation(location);
      Iterable files = this.getLocation(location);
      if (files == null) {
         throw new IllegalArgumentException("Unknown location : " + location);
      } else {
         Iterator iterator = files.iterator();
         if (iterator.hasNext()) {
            File file = (File)iterator.next();
            String normalizedFileName = this.normalized(packageName) + '/' + relativeName.replace('\\', '/');
            File f = new File(file, normalizedFileName);
            return new EclipseFileObject(packageName + File.separator + relativeName, f.toURI(), this.getKind(f), this.charset);
         } else {
            throw new IllegalArgumentException("location is empty : " + location);
         }
      }
   }

   public JavaFileObject getJavaFileForInput(JavaFileManager.Location location, String className, JavaFileObject.Kind kind) throws IOException {
      this.validateNonModuleLocation(location);
      if (kind != Kind.CLASS && kind != Kind.SOURCE) {
         throw new IllegalArgumentException("Invalid kind : " + kind);
      } else {
         Iterable files = this.getLocation(location);
         if (files == null) {
            throw new IllegalArgumentException("Unknown location : " + location);
         } else {
            String normalizedFileName = this.normalized(className);
            normalizedFileName = normalizedFileName + kind.extension;
            Iterator var7 = files.iterator();

            while(var7.hasNext()) {
               File file = (File)var7.next();
               if (file.isDirectory()) {
                  File f = new File(file, normalizedFileName);
                  if (f.exists()) {
                     return new EclipseFileObject(className, f.toURI(), kind, this.charset);
                  }
               } else if (this.isArchive(file)) {
                  Archive archive = this.getArchive(file);
                  if (archive != Archive.UNKNOWN_ARCHIVE && archive.contains(normalizedFileName)) {
                     return archive.getArchiveFileObject(normalizedFileName, (String)null, this.charset);
                  }
               }
            }

            return null;
         }
      }
   }

   public JavaFileObject getJavaFileForOutput(JavaFileManager.Location location, String className, JavaFileObject.Kind kind, FileObject sibling) throws IOException {
      this.validateOutputLocation(location);
      if (kind != Kind.CLASS && kind != Kind.SOURCE) {
         throw new IllegalArgumentException("Invalid kind : " + kind);
      } else {
         Iterable files = this.getLocation(location);
         File f;
         if (files != null) {
            Iterator iterator = files.iterator();
            if (iterator.hasNext()) {
               f = (File)iterator.next();
               String normalizedFileName = this.normalized(className);
               normalizedFileName = normalizedFileName + kind.extension;
               File f = new File(f, normalizedFileName);
               return new EclipseFileObject(className, f.toURI(), kind, this.charset);
            } else {
               throw new IllegalArgumentException("location is empty : " + location);
            }
         } else if (!location.equals(StandardLocation.CLASS_OUTPUT) && !location.equals(StandardLocation.SOURCE_OUTPUT)) {
            throw new IllegalArgumentException("Unknown location : " + location);
         } else {
            String normalizedFileName;
            if (sibling == null) {
               normalizedFileName = this.normalized(className);
               normalizedFileName = normalizedFileName + kind.extension;
               f = new File(System.getProperty("user.dir"), normalizedFileName);
               return new EclipseFileObject(className, f.toURI(), kind, this.charset);
            } else {
               normalizedFileName = this.normalized(className);
               int index = normalizedFileName.lastIndexOf(47);
               if (index != -1) {
                  normalizedFileName = normalizedFileName.substring(index + 1);
               }

               normalizedFileName = normalizedFileName + kind.extension;
               URI uri = sibling.toUri();
               URI uri2 = null;

               try {
                  String path = uri.getPath();
                  index = path.lastIndexOf(47);
                  if (index != -1) {
                     path = path.substring(0, index + 1);
                     path = path + normalizedFileName;
                  }

                  uri2 = new URI(uri.getScheme(), uri.getHost(), path, uri.getFragment());
               } catch (URISyntaxException var11) {
                  throw new IllegalArgumentException("invalid sibling", var11);
               }

               return new EclipseFileObject(className, uri2, kind, this.charset);
            }
         }
      }
   }

   public Iterable getJavaFileObjects(File... files) {
      return this.getJavaFileObjectsFromFiles(Arrays.asList(files));
   }

   public Iterable getJavaFileObjects(String... names) {
      return this.getJavaFileObjectsFromStrings(Arrays.asList(names));
   }

   public Iterable getJavaFileObjectsFromFiles(Iterable files) {
      ArrayList javaFileArrayList = new ArrayList();
      Iterator var4 = files.iterator();

      while(var4.hasNext()) {
         File f = (File)var4.next();
         if (f.isDirectory()) {
            throw new IllegalArgumentException("file : " + f.getAbsolutePath() + " is a directory");
         }

         javaFileArrayList.add(new EclipseFileObject(f.getAbsolutePath(), f.toURI(), this.getKind(f), this.charset));
      }

      return javaFileArrayList;
   }

   public Iterable getJavaFileObjectsFromStrings(Iterable names) {
      ArrayList files = new ArrayList();
      Iterator var4 = names.iterator();

      while(var4.hasNext()) {
         String name = (String)var4.next();
         files.add(new File(name));
      }

      return this.getJavaFileObjectsFromFiles(files);
   }

   public JavaFileObject.Kind getKind(File f) {
      return this.getKind(this.getExtension(f));
   }

   private JavaFileObject.Kind getKind(String extension) {
      if (Kind.CLASS.extension.equals(extension)) {
         return Kind.CLASS;
      } else if (Kind.SOURCE.extension.equals(extension)) {
         return Kind.SOURCE;
      } else {
         return Kind.HTML.extension.equals(extension) ? Kind.HTML : Kind.OTHER;
      }
   }

   public Iterable getLocation(JavaFileManager.Location location) {
      if (location instanceof ModuleLocationHandler.LocationWrapper) {
         return this.getFiles(((ModuleLocationHandler.LocationWrapper)location).paths);
      } else {
         ModuleLocationHandler.LocationWrapper loc = this.locationHandler.getLocation(location, "");
         return loc == null ? null : this.getFiles(loc.getPaths());
      }
   }

   private Iterable getOutputDir(String string) {
      if ("none".equals(string)) {
         return null;
      } else {
         File file = new File(string);
         if (file.exists() && !file.isDirectory()) {
            throw new IllegalArgumentException("file : " + file.getAbsolutePath() + " is not a directory");
         } else {
            ArrayList list = new ArrayList(1);
            list.add(file);
            return list;
         }
      }
   }

   public boolean handleOption(String current, Iterator remaining) {
      try {
         Iterable classpaths;
         Iterable iterable;
         label256: {
            switch (current.hashCode()) {
               case -1818390937:
                  if (current.equals("--release")) {
                     if (remaining.hasNext()) {
                        this.releaseVersion = (String)remaining.next();
                        return true;
                     }

                     throw new IllegalArgumentException();
                  }

                  return false;
               case -1743456290:
                  if (current.equals("-bootclasspath")) {
                     if (!remaining.hasNext()) {
                        throw new IllegalArgumentException();
                     }

                     classpaths = this.getPathsFrom((String)remaining.next());
                     if (classpaths != null) {
                        iterable = this.getLocation(StandardLocation.PLATFORM_CLASS_PATH);
                        if ((this.flags & 4) == 0 && (this.flags & 1) == 0) {
                           this.setLocation(StandardLocation.PLATFORM_CLASS_PATH, classpaths);
                        } else if ((this.flags & 4) != 0) {
                           this.setLocation(StandardLocation.PLATFORM_CLASS_PATH, this.concatFiles(iterable, classpaths));
                        } else {
                           this.setLocation(StandardLocation.PLATFORM_CLASS_PATH, this.prependFiles(iterable, classpaths));
                        }
                     }

                     this.flags |= 2;
                     return true;
                  }

                  return false;
               case -369053011:
                  if (current.equals("-sourcepath")) {
                     if (remaining.hasNext()) {
                        iterable = this.getPathsFrom((String)remaining.next());
                        if (iterable != null) {
                           this.setLocation(StandardLocation.SOURCE_PATH, iterable);
                        }

                        return true;
                     }

                     throw new IllegalArgumentException();
                  }

                  return false;
               case -262048752:
                  if (!current.equals("-classpath")) {
                     return false;
                  }
                  break label256;
               case -188644774:
                  if (current.equals("-extdirs")) {
                     if (this.isOnJvm9) {
                        throw new IllegalArgumentException();
                     }

                     if (remaining.hasNext()) {
                        iterable = this.getLocation(StandardLocation.PLATFORM_CLASS_PATH);
                        this.setLocation(StandardLocation.PLATFORM_CLASS_PATH, this.concatFiles(iterable, this.getExtdirsFrom((String)remaining.next())));
                        this.flags |= 1;
                        return true;
                     }

                     throw new IllegalArgumentException();
                  }

                  return false;
               case -45598394:
                  if (!current.equals("--module-path")) {
                     return false;
                  }
                  break;
               case 1495:
                  if (current.equals("-d")) {
                     if (remaining.hasNext()) {
                        iterable = this.getOutputDir((String)remaining.next());
                        if (iterable != null) {
                           this.setLocation(StandardLocation.CLASS_OUTPUT, iterable);
                        }

                        return true;
                     }

                     throw new IllegalArgumentException();
                  }

                  return false;
               case 1507:
                  if (!current.equals("-p")) {
                     return false;
                  }
                  break;
               case 1510:
                  if (current.equals("-s")) {
                     if (remaining.hasNext()) {
                        iterable = this.getOutputDir((String)remaining.next());
                        if (iterable != null) {
                           this.setLocation(StandardLocation.SOURCE_OUTPUT, iterable);
                        }

                        return true;
                     }

                     throw new IllegalArgumentException();
                  }

                  return false;
               case 46426:
                  if (!current.equals("-cp")) {
                     return false;
                  }
                  break label256;
               case 846432426:
                  if (current.equals("-processorpath")) {
                     if (remaining.hasNext()) {
                        iterable = this.getPathsFrom((String)remaining.next());
                        if (iterable != null) {
                           this.setLocation(StandardLocation.ANNOTATION_PROCESSOR_PATH, iterable);
                        }

                        this.flags |= 8;
                        return true;
                     }

                     throw new IllegalArgumentException();
                  }

                  return false;
               case 1158429461:
                  if (current.equals("--upgrade-module-path")) {
                     if (remaining.hasNext()) {
                        classpaths = this.getPathsFrom((String)remaining.next());
                        if (classpaths != null) {
                           iterable = this.getLocation(StandardLocation.UPGRADE_MODULE_PATH);
                           if (iterable != null) {
                              this.setLocation(StandardLocation.UPGRADE_MODULE_PATH, this.concatFiles(iterable, classpaths));
                           } else {
                              this.setLocation(StandardLocation.UPGRADE_MODULE_PATH, classpaths);
                           }
                        }

                        return true;
                     }

                     throw new IllegalArgumentException();
                  }

                  return false;
               case 1163973771:
                  if (current.equals("--processor-module-path")) {
                     if (remaining.hasNext()) {
                        iterable = this.getPathsFrom((String)remaining.next());
                        if (iterable != null && this.isOnJvm9) {
                           this.setLocation(StandardLocation.ANNOTATION_PROCESSOR_MODULE_PATH, iterable);
                           this.flags |= 16;
                        }

                        return true;
                     }

                     throw new IllegalArgumentException();
                  }

                  return false;
               case 1512685519:
                  if (current.equals("--system")) {
                     if (remaining.hasNext()) {
                        classpaths = this.getPathsFrom((String)remaining.next());
                        if (classpaths != null) {
                           iterable = this.getLocation(StandardLocation.SYSTEM_MODULES);
                           if (iterable != null) {
                              this.setLocation(StandardLocation.SYSTEM_MODULES, this.concatFiles(iterable, classpaths));
                           } else {
                              this.setLocation(StandardLocation.SYSTEM_MODULES, classpaths);
                           }
                        }

                        return true;
                     }

                     throw new IllegalArgumentException();
                  }

                  return false;
               case 1585172423:
                  if (current.equals("-endorseddirs")) {
                     if (remaining.hasNext()) {
                        iterable = this.getLocation(StandardLocation.PLATFORM_CLASS_PATH);
                        this.setLocation(StandardLocation.PLATFORM_CLASS_PATH, this.prependFiles(iterable, this.getEndorsedDirsFrom((String)remaining.next())));
                        this.flags |= 4;
                        return true;
                     }

                     throw new IllegalArgumentException();
                  }

                  return false;
               case 1847282262:
                  if (current.equals("--module-source-path")) {
                     if (remaining.hasNext()) {
                        iterable = this.getPathsFrom((String)remaining.next());
                        if (iterable != null && this.isOnJvm9) {
                           this.setLocation(StandardLocation.MODULE_SOURCE_PATH, iterable);
                        }

                        return true;
                     }

                     throw new IllegalArgumentException();
                  }

                  return false;
               case 1980149888:
                  if (current.equals("-encoding")) {
                     if (remaining.hasNext()) {
                        this.charset = Charset.forName((String)remaining.next());
                        return true;
                     }

                     throw new IllegalArgumentException();
                  }

                  return false;
               default:
                  return false;
            }

            classpaths = this.getPathsFrom((String)remaining.next());
            if (classpaths != null) {
               iterable = this.getLocation(StandardLocation.MODULE_PATH);
               if (iterable != null) {
                  this.setLocation(StandardLocation.MODULE_PATH, this.concatFiles(iterable, classpaths));
               } else {
                  this.setLocation(StandardLocation.MODULE_PATH, classpaths);
               }

               if ((this.flags & 8) == 0) {
                  this.setLocation(StandardLocation.ANNOTATION_PROCESSOR_PATH, classpaths);
               } else if ((this.flags & 16) == 0 && this.isOnJvm9) {
                  this.setLocation(StandardLocation.ANNOTATION_PROCESSOR_MODULE_PATH, classpaths);
               }
            }

            return true;
         }

         if (remaining.hasNext()) {
            classpaths = this.getPathsFrom((String)remaining.next());
            if (classpaths != null) {
               iterable = this.getLocation(StandardLocation.CLASS_PATH);
               if (iterable != null) {
                  this.setLocation(StandardLocation.CLASS_PATH, this.concatFiles(iterable, classpaths));
               } else {
                  this.setLocation(StandardLocation.CLASS_PATH, classpaths);
               }

               if ((this.flags & 8) == 0) {
                  this.setLocation(StandardLocation.ANNOTATION_PROCESSOR_PATH, classpaths);
               } else if ((this.flags & 16) == 0 && this.isOnJvm9) {
                  this.setLocation(StandardLocation.ANNOTATION_PROCESSOR_MODULE_PATH, classpaths);
               }
            }

            return true;
         } else {
            throw new IllegalArgumentException();
         }
      } catch (IOException var6) {
         return false;
      }
   }

   public boolean hasLocation(JavaFileManager.Location location) {
      try {
         return this.getLocationForModule(location, "") != null;
      } catch (IOException var2) {
         return false;
      }
   }

   public String inferBinaryName(JavaFileManager.Location location, JavaFileObject file) {
      this.validateNonModuleLocation(location);
      String name = file.getName();
      JavaFileObject javaFileObject = null;
      int index = name.lastIndexOf(46);
      if (index != -1) {
         name = name.substring(0, index);
      }

      try {
         javaFileObject = this.getJavaFileForInput(location, name, file.getKind());
      } catch (IOException var6) {
      } catch (IllegalArgumentException var7) {
         return null;
      }

      return javaFileObject == null ? null : name.replace('/', '.');
   }

   private boolean isArchive(File f) {
      String extension = this.getExtension(f);
      return extension.equalsIgnoreCase(".jar") || extension.equalsIgnoreCase(".zip");
   }

   public boolean isSameFile(FileObject fileObject1, FileObject fileObject2) {
      if (!(fileObject1 instanceof EclipseFileObject)) {
         throw new IllegalArgumentException("Unsupported file object class : " + fileObject1.getClass());
      } else if (!(fileObject2 instanceof EclipseFileObject)) {
         throw new IllegalArgumentException("Unsupported file object class : " + fileObject2.getClass());
      } else {
         return fileObject1.equals(fileObject2);
      }
   }

   public int isSupportedOption(String option) {
      return Options.processOptionsFileManager(option);
   }

   public Iterable list(JavaFileManager.Location location, String packageName, Set kinds, boolean recurse) throws IOException {
      this.validateNonModuleLocation(location);
      Iterable allFilesInLocations = this.getLocation(location);
      if (allFilesInLocations == null) {
         throw new IllegalArgumentException("Unknown location : " + location);
      } else {
         ArrayList collector = new ArrayList();
         String normalizedPackageName = this.normalized(packageName);
         Iterator var9 = allFilesInLocations.iterator();

         while(var9.hasNext()) {
            File file = (File)var9.next();
            this.collectAllMatchingFiles(location, file, normalizedPackageName, kinds, recurse, collector);
         }

         return collector;
      }
   }

   private String normalized(String className) {
      char[] classNameChars = className.toCharArray();
      int i = 0;

      for(int max = classNameChars.length; i < max; ++i) {
         switch (classNameChars[i]) {
            case '.':
               classNameChars[i] = '/';
               break;
            case '\\':
               classNameChars[i] = '/';
         }
      }

      return new String(classNameChars);
   }

   private Iterable prependFiles(Iterable iterable, Iterable iterable2) {
      if (iterable2 == null) {
         return iterable;
      } else {
         ArrayList list = new ArrayList();
         Iterator iterator = iterable2.iterator();

         while(iterator.hasNext()) {
            list.add((File)iterator.next());
         }

         if (iterable != null) {
            iterator = iterable.iterator();

            while(iterator.hasNext()) {
               list.add((File)iterator.next());
            }
         }

         return list;
      }
   }

   private boolean isRunningJvm9() {
      return SourceVersion.latest().compareTo(SourceVersion.RELEASE_8) > 0;
   }

   public void setLocation(JavaFileManager.Location location, Iterable files) throws IOException {
      if (location.isOutputLocation()) {
         int count = 0;

         for(Iterator iterator = files.iterator(); iterator.hasNext(); ++count) {
            iterator.next();
         }

         if (count != 1) {
            throw new IllegalArgumentException("output location can only have one path");
         }
      }

      this.locationHandler.setLocation(location, "", this.getPaths(files));
   }

   public void setLocale(Locale locale) {
      this.locale = locale == null ? Locale.getDefault() : locale;

      try {
         this.bundle = Main.ResourceBundleFactory.getBundle(this.locale);
      } catch (MissingResourceException var3) {
         System.out.println("Missing resource : " + "com.bea.core.repackaged.jdt.internal.compiler.batch.messages".replace('.', '/') + ".properties for locale " + locale);
         throw var3;
      }
   }

   public void processPathEntries(int defaultSize, ArrayList paths, String currentPath, String customEncoding, boolean isSourceOnly, boolean rejectDestinationPathOnJars) {
      String currentClasspathName = null;
      String currentDestinationPath = null;
      ArrayList currentRuleSpecs = new ArrayList(defaultSize);
      StringTokenizer tokenizer = new StringTokenizer(currentPath, File.pathSeparator + "[]", true);
      ArrayList tokens = new ArrayList();

      while(tokenizer.hasMoreTokens()) {
         tokens.add(tokenizer.nextToken());
      }

      int state = 0;
      String token = null;
      int cursor = 0;
      int tokensNb = tokens.size();
      int bracket = -1;

      while(cursor < tokensNb && state != 99) {
         token = (String)tokens.get(cursor++);
         if (token.equals(File.pathSeparator)) {
            switch (state) {
               case 0:
               case 3:
               case 10:
                  break;
               case 1:
               case 2:
               case 8:
                  state = 3;
                  this.addNewEntry(paths, currentClasspathName, currentRuleSpecs, customEncoding, currentDestinationPath, isSourceOnly, rejectDestinationPathOnJars);
                  currentRuleSpecs.clear();
                  break;
               case 4:
               case 5:
               case 9:
               default:
                  state = 99;
                  break;
               case 6:
                  state = 4;
                  break;
               case 7:
                  throw new IllegalArgumentException(this.bind("configure.incorrectDestinationPathEntry", currentPath));
               case 11:
                  cursor = bracket + 1;
                  state = 5;
            }
         } else if (token.equals("[")) {
            switch (state) {
               case 0:
                  currentClasspathName = "";
               case 1:
                  bracket = cursor - 1;
               case 11:
                  state = 10;
                  break;
               case 2:
                  state = 9;
                  break;
               case 3:
               case 4:
               case 5:
               case 6:
               case 7:
               case 9:
               case 10:
               default:
                  state = 99;
                  break;
               case 8:
                  state = 5;
            }
         } else if (token.equals("]")) {
            switch (state) {
               case 6:
                  state = 2;
                  break;
               case 7:
                  state = 8;
                  break;
               case 8:
               case 9:
               case 11:
               default:
                  state = 99;
                  break;
               case 10:
                  state = 11;
            }
         } else {
            switch (state) {
               case 0:
               case 3:
                  state = 1;
                  currentClasspathName = token;
                  break;
               case 1:
               case 2:
               case 6:
               case 7:
               case 8:
               default:
                  state = 99;
                  break;
               case 5:
                  if (token.startsWith("-d ")) {
                     if (currentDestinationPath != null) {
                        throw new IllegalArgumentException(this.bind("configure.duplicateDestinationPathEntry", currentPath));
                     }

                     currentDestinationPath = token.substring(3).trim();
                     state = 7;
                     break;
                  }
               case 4:
                  if (currentDestinationPath != null) {
                     throw new IllegalArgumentException(this.bind("configure.accessRuleAfterDestinationPath", currentPath));
                  }

                  state = 6;
                  currentRuleSpecs.add(token);
                  break;
               case 9:
                  if (!token.startsWith("-d ")) {
                     state = 99;
                  } else {
                     currentDestinationPath = token.substring(3).trim();
                     state = 7;
                  }
               case 10:
                  break;
               case 11:
                  for(int i = bracket; i < cursor; ++i) {
                     currentClasspathName = currentClasspathName + (String)tokens.get(i);
                  }

                  state = 1;
            }
         }

         if (state == 11 && cursor == tokensNb) {
            cursor = bracket + 1;
            state = 5;
         }
      }

      switch (state) {
         case 1:
         case 2:
         case 8:
            this.addNewEntry(paths, currentClasspathName, currentRuleSpecs, customEncoding, currentDestinationPath, isSourceOnly, rejectDestinationPathOnJars);
         case 3:
         case 4:
         case 5:
         case 6:
         case 7:
         case 9:
         case 10:
         case 11:
         default:
      }
   }

   protected void addNewEntry(ArrayList paths, String currentClasspathName, ArrayList currentRuleSpecs, String customEncoding, String destPath, boolean isSourceOnly, boolean rejectDestinationPathOnJars) {
      int rulesSpecsSize = currentRuleSpecs.size();
      AccessRuleSet accessRuleSet = null;
      if (rulesSpecsSize != 0) {
         AccessRule[] accessRules = new AccessRule[currentRuleSpecs.size()];
         boolean rulesOK = true;
         Iterator i = currentRuleSpecs.iterator();
         int j = 0;

         while(i.hasNext()) {
            String ruleSpec = (String)i.next();
            char key = ruleSpec.charAt(0);
            String pattern = ruleSpec.substring(1);
            if (pattern.length() > 0) {
               switch (key) {
                  case '+':
                     accessRules[j++] = new AccessRule(pattern.toCharArray(), 0);
                     break;
                  case '-':
                     accessRules[j++] = new AccessRule(pattern.toCharArray(), 16777523);
                     break;
                  case '?':
                     accessRules[j++] = new AccessRule(pattern.toCharArray(), 16777523, true);
                     break;
                  case '~':
                     accessRules[j++] = new AccessRule(pattern.toCharArray(), 16777496);
                     break;
                  default:
                     rulesOK = false;
               }
            } else {
               rulesOK = false;
            }
         }

         if (!rulesOK) {
            return;
         }

         accessRuleSet = new AccessRuleSet(accessRules, (byte)0, currentClasspathName);
      }

      if ("none".equals(destPath)) {
         destPath = "none";
      }

      if (!rejectDestinationPathOnJars || destPath == null || !currentClasspathName.endsWith(".jar") && !currentClasspathName.endsWith(".zip")) {
         FileSystem.Classpath currentClasspath = FileSystem.getClasspath(currentClasspathName, customEncoding, isSourceOnly, accessRuleSet, destPath, (Map)null, this.releaseVersion);
         if (currentClasspath != null) {
            paths.add(currentClasspath);
         }

      } else {
         throw new IllegalArgumentException(this.bind("configure.unexpectedDestinationPathEntryFile", currentClasspathName));
      }
   }

   private String bind(String id, String binding) {
      return this.bind(id, new String[]{binding});
   }

   private String bind(String id, String[] arguments) {
      if (id == null) {
         return "No message available";
      } else {
         String message = null;

         try {
            message = this.bundle.getString(id);
         } catch (MissingResourceException var4) {
            return "Missing message: " + id + " in: " + "com.bea.core.repackaged.jdt.internal.compiler.batch.messages";
         }

         return MessageFormat.format(message, arguments);
      }
   }

   private Iterable getFiles(Iterable paths) {
      return paths == null ? null : () -> {
         return new Iterator(paths) {
            Iterator original;

            {
               this.original = var2.iterator();
            }

            public boolean hasNext() {
               return this.original.hasNext();
            }

            public File next() {
               return ((Path)this.original.next()).toFile();
            }
         };
      };
   }

   private Iterable getPaths(Iterable files) {
      return files == null ? null : () -> {
         return new Iterator(files) {
            Iterator original;

            {
               this.original = var2.iterator();
            }

            public boolean hasNext() {
               return this.original.hasNext();
            }

            public Path next() {
               return ((File)this.original.next()).toPath();
            }
         };
      };
   }

   private void validateFileObject(FileObject file) {
   }

   private void validateModuleLocation(JavaFileManager.Location location, String modName) {
      Objects.requireNonNull(location);
      if (modName == null) {
         throw new IllegalArgumentException("module must not be null");
      } else if (this.isOnJvm9 && !location.isModuleOrientedLocation() && !location.isOutputLocation()) {
         throw new IllegalArgumentException("location is module related :" + location.getName());
      }
   }

   private void validateNonModuleLocation(JavaFileManager.Location location) {
      Objects.requireNonNull(location);
      if (this.isOnJvm9 && location.isModuleOrientedLocation() && location.isOutputLocation()) {
         throw new IllegalArgumentException("location is module related :" + location.getName());
      }
   }

   private void validateOutputLocation(JavaFileManager.Location location) {
      Objects.requireNonNull(location);
      if (!location.isOutputLocation()) {
         throw new IllegalArgumentException("location is not output location :" + location.getName());
      }
   }

   public Iterable getJavaFileObjects(Path... paths) {
      return this.getJavaFileObjectsFromPaths(Arrays.asList(paths));
   }

   public Iterable getJavaFileObjectsFromPaths(Iterable paths) {
      return this.getJavaFileObjectsFromFiles(this.getFiles(paths));
   }

   public Iterable getLocationAsPaths(JavaFileManager.Location location) {
      if (location instanceof ModuleLocationHandler.LocationWrapper) {
         return ((ModuleLocationHandler.LocationWrapper)location).paths;
      } else {
         ModuleLocationHandler.LocationWrapper loc = this.locationHandler.getLocation(location);
         return loc == null ? null : loc.getPaths();
      }
   }

   public void setLocationFromPaths(JavaFileManager.Location location, Collection paths) throws IOException {
      this.setLocation(location, this.getFiles(paths));
      if (location == StandardLocation.MODULE_PATH) {
         Map options = new HashMap();
         options.put("com.bea.core.repackaged.jdt.core.compiler.compliance", "9");
         options.put("com.bea.core.repackaged.jdt.core.compiler.source", "9");
         options.put("com.bea.core.repackaged.jdt.core.compiler.codegen.targetPlatform", "9");
         CompilerOptions compilerOptions = new CompilerOptions(options);
         ProblemReporter problemReporter = new ProblemReporter(DefaultErrorHandlingPolicies.proceedWithAllProblems(), compilerOptions, new DefaultProblemFactory());
         Iterator var7 = paths.iterator();

         while(var7.hasNext()) {
            Path path = (Path)var7.next();
            List mp = ModuleFinder.findModules(path.toFile(), (String)null, new Parser(problemReporter, true), (Map)null, true, this.releaseVersion);
            Iterator var10 = mp.iterator();

            while(var10.hasNext()) {
               FileSystem.Classpath cp = (FileSystem.Classpath)var10.next();
               Collection moduleNames = cp.getModuleNames((Collection)null);
               Iterator var13 = moduleNames.iterator();

               while(var13.hasNext()) {
                  String string = (String)var13.next();
                  Path p = Paths.get(cp.getPath());
                  this.setLocationForModule(StandardLocation.MODULE_PATH, string, Collections.singletonList(p));
               }
            }
         }
      }

   }

   public boolean contains(JavaFileManager.Location location, FileObject fo) throws IOException {
      this.validateFileObject(fo);
      Iterable files = this.getLocation(location);
      if (files == null) {
         throw new IllegalArgumentException("Unknown location : " + location);
      } else {
         Iterator var5 = files.iterator();

         while(var5.hasNext()) {
            File file = (File)var5.next();
            if (file.isDirectory()) {
               if (fo instanceof EclipseFileObject) {
                  Path filepath = ((EclipseFileObject)fo).f.toPath();
                  if (filepath.startsWith(Paths.get(file.toURI()).toAbsolutePath())) {
                     return true;
                  }
               }
            } else if (this.isArchive(file) && fo instanceof ArchiveFileObject) {
               Archive archive = this.getArchive(file);
               if (archive != Archive.UNKNOWN_ARCHIVE && archive.contains(((ArchiveFileObject)fo).entryName)) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   public JavaFileManager.Location getLocationForModule(JavaFileManager.Location location, String moduleName) throws IOException {
      this.validateModuleLocation(location, moduleName);
      JavaFileManager.Location result = this.locationHandler.getLocation(location, moduleName);
      ModuleLocationHandler.LocationWrapper wrapper;
      if (result == null && location == StandardLocation.CLASS_OUTPUT) {
         wrapper = this.locationHandler.getLocation(StandardLocation.MODULE_SOURCE_PATH, (String)moduleName);
         this.deriveOutputLocationForModules(moduleName, wrapper.paths);
         result = this.getLocationForModule(location, moduleName);
      } else if (result == null && location == StandardLocation.SOURCE_OUTPUT) {
         wrapper = this.locationHandler.getLocation(StandardLocation.MODULE_SOURCE_PATH, (String)moduleName);
         this.deriveSourceOutputLocationForModules(moduleName, wrapper.paths);
         result = this.getLocationForModule(location, moduleName);
      }

      return (JavaFileManager.Location)result;
   }

   public JavaFileManager.Location getLocationForModule(JavaFileManager.Location location, JavaFileObject fo) {
      this.validateModuleLocation(location, "");
      Path path = null;
      if (fo instanceof ArchiveFileObject) {
         path = ((ArchiveFileObject)fo).file.toPath();
         return this.locationHandler.getLocation(location, path);
      } else {
         if (fo instanceof EclipseFileObject) {
            path = ((EclipseFileObject)fo).f.toPath();

            try {
               path = path.toRealPath();
            } catch (IOException var6) {
               var6.printStackTrace();
            }

            for(ModuleLocationHandler.LocationContainer container = this.locationHandler.getLocation(location); path != null; path = path.getParent()) {
               JavaFileManager.Location loc = container.get(path);
               if (loc != null) {
                  return loc;
               }
            }
         }

         return null;
      }
   }

   public ServiceLoader getServiceLoader(JavaFileManager.Location location, Class service) throws IOException {
      return ServiceLoader.load(service, this.getClassLoader(location));
   }

   public String inferModuleName(JavaFileManager.Location location) throws IOException {
      if (location instanceof ModuleLocationHandler.ModuleLocationWrapper) {
         ModuleLocationHandler.ModuleLocationWrapper wrapper = (ModuleLocationHandler.ModuleLocationWrapper)location;
         return wrapper.modName;
      } else {
         return null;
      }
   }

   public Iterable listLocationsForModules(JavaFileManager.Location location) {
      this.validateModuleLocation(location, "");
      return this.locationHandler.listLocationsForModules(location);
   }

   public Path asPath(FileObject file) {
      this.validateFileObject(file);
      EclipseFileObject eclFile = (EclipseFileObject)file;
      return eclFile.f != null ? eclFile.f.toPath() : null;
   }

   private void deriveOutputLocationForModules(String moduleName, Collection paths) {
      ModuleLocationHandler.LocationWrapper wrapper = this.locationHandler.getLocation(StandardLocation.CLASS_OUTPUT, (String)moduleName);
      if (wrapper == null) {
         ModuleLocationHandler.LocationWrapper wrapper = this.locationHandler.getLocation(StandardLocation.CLASS_OUTPUT, (String)"");
         if (wrapper == null) {
            wrapper = this.locationHandler.getLocation(StandardLocation.CLASS_OUTPUT);
         }

         if (wrapper != null) {
            Iterator iterator = ((ModuleLocationHandler.LocationWrapper)wrapper).paths.iterator();
            if (iterator.hasNext()) {
               try {
                  Path path = ((Path)iterator.next()).resolve(moduleName);
                  this.locationHandler.setLocation(StandardLocation.CLASS_OUTPUT, moduleName, Collections.singletonList(path));
               } catch (Exception var6) {
                  var6.printStackTrace();
               }
            }
         }
      }

   }

   private void deriveSourceOutputLocationForModules(String moduleName, Collection paths) {
      ModuleLocationHandler.LocationWrapper wrapper = this.locationHandler.getLocation(StandardLocation.SOURCE_OUTPUT, (String)moduleName);
      if (wrapper == null) {
         ModuleLocationHandler.LocationWrapper wrapper = this.locationHandler.getLocation(StandardLocation.SOURCE_OUTPUT, (String)"");
         if (wrapper == null) {
            wrapper = this.locationHandler.getLocation(StandardLocation.SOURCE_OUTPUT);
         }

         if (wrapper != null) {
            Iterator iterator = ((ModuleLocationHandler.LocationWrapper)wrapper).paths.iterator();
            if (iterator.hasNext()) {
               try {
                  Path path = ((Path)iterator.next()).resolve(moduleName);
                  this.locationHandler.setLocation(StandardLocation.SOURCE_OUTPUT, moduleName, Collections.singletonList(path));
               } catch (Exception var6) {
                  var6.printStackTrace();
               }
            }
         }
      }

   }

   public void setLocationForModule(JavaFileManager.Location location, String moduleName, Collection paths) throws IOException {
      this.validateModuleLocation(location, moduleName);
      this.locationHandler.setLocation(location, moduleName, paths);
      if (location == StandardLocation.MODULE_SOURCE_PATH) {
         ModuleLocationHandler.LocationWrapper wrapper = this.locationHandler.getLocation(StandardLocation.CLASS_OUTPUT, (String)moduleName);
         if (wrapper == null) {
            wrapper = this.locationHandler.getLocation(StandardLocation.CLASS_OUTPUT, (String)"");
            if (wrapper != null) {
               Iterator iterator = wrapper.paths.iterator();
               if (iterator.hasNext()) {
                  Path path = ((Path)iterator.next()).resolve(moduleName);
                  this.locationHandler.setLocation(StandardLocation.CLASS_OUTPUT, moduleName, Collections.singletonList(path));
               }
            }
         }
      }

   }
}
