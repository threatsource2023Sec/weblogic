package com.bea.core.repackaged.jdt.internal.compiler.batch;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.classfmt.ClassFileReader;
import com.bea.core.repackaged.jdt.internal.compiler.classfmt.ClassFormatException;
import com.bea.core.repackaged.jdt.internal.compiler.env.AccessRuleSet;
import com.bea.core.repackaged.jdt.internal.compiler.env.IModule;
import com.bea.core.repackaged.jdt.internal.compiler.env.NameEnvironmentAnswer;
import com.bea.core.repackaged.jdt.internal.compiler.impl.CompilerOptions;
import com.bea.core.repackaged.jdt.internal.compiler.util.JRTUtil;
import com.bea.core.repackaged.jdt.internal.compiler.util.Util;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ClasspathJep247 extends ClasspathJrt {
   protected java.nio.file.FileSystem fs = null;
   protected String compliance = null;
   protected long jdklevel;
   protected String releaseInHex = null;
   protected String[] subReleases = null;
   protected Path releasePath = null;
   protected Set packageCache;
   protected File jdkHome;
   protected String modulePath = null;

   public ClasspathJep247(File jdkHome, String release, AccessRuleSet accessRuleSet) {
      super(jdkHome, false, accessRuleSet, (String)null);
      this.compliance = release;
      this.jdklevel = CompilerOptions.releaseToJDKLevel(this.compliance);
      this.jdkHome = jdkHome;
      this.file = new File(new File(jdkHome, "lib"), "jrt-fs.jar");
   }

   public List fetchLinkedJars(FileSystem.ClasspathSectionProblemReporter problemReporter) {
      return null;
   }

   public NameEnvironmentAnswer findClass(char[] typeName, String qualifiedPackageName, String moduleName, String qualifiedBinaryFileName) {
      return this.findClass(typeName, qualifiedPackageName, moduleName, qualifiedBinaryFileName, false);
   }

   public NameEnvironmentAnswer findClass(char[] typeName, String qualifiedPackageName, String moduleName, String qualifiedBinaryFileName, boolean asBinaryOnly) {
      if (!this.isPackage(qualifiedPackageName, moduleName)) {
         return null;
      } else {
         try {
            ClassFileReader reader = null;
            byte[] content = null;
            qualifiedBinaryFileName = qualifiedBinaryFileName.replace(".class", ".sig");
            if (this.subReleases != null && this.subReleases.length > 0) {
               String[] var11;
               int var10 = (var11 = this.subReleases).length;

               for(int var9 = 0; var9 < var10; ++var9) {
                  String rel = var11[var9];
                  Path p = this.fs.getPath(rel, qualifiedBinaryFileName);
                  if (Files.exists(p, new LinkOption[0])) {
                     content = JRTUtil.safeReadBytes(p);
                     if (content != null) {
                        break;
                     }
                  }
               }
            } else {
               content = JRTUtil.safeReadBytes(this.fs.getPath(this.releaseInHex, qualifiedBinaryFileName));
            }

            if (content != null) {
               reader = new ClassFileReader(content, qualifiedBinaryFileName.toCharArray());
               char[] modName = moduleName != null ? moduleName.toCharArray() : null;
               return new NameEnvironmentAnswer(reader, this.fetchAccessRestriction(qualifiedBinaryFileName), modName);
            }
         } catch (ClassFormatException var13) {
         } catch (IOException var14) {
         }

         return null;
      }
   }

   public void initialize() throws IOException {
      if (this.compliance != null) {
         this.releaseInHex = Integer.toHexString(Integer.parseInt(this.compliance)).toUpperCase();
         Path filePath = this.jdkHome.toPath().resolve("lib").resolve("ct.sym");
         URI t = filePath.toUri();
         if (Files.exists(filePath, new LinkOption[0])) {
            URI uri = URI.create("jar:file:" + t.getRawPath());

            try {
               this.fs = FileSystems.getFileSystem(uri);
            } catch (FileSystemNotFoundException var5) {
            }

            if (this.fs == null) {
               HashMap env = new HashMap();
               this.fs = FileSystems.newFileSystem(uri, env);
            }

            this.releasePath = this.fs.getPath("/");
            if (!Files.exists(this.fs.getPath(this.releaseInHex), new LinkOption[0])) {
               throw new IllegalArgumentException("release " + this.compliance + " is not found in the system");
            } else {
               super.initialize();
            }
         }
      }
   }

   public void loadModules() {
      if (this.jdklevel <= 3407872L) {
         super.loadModules();
      } else {
         Path modPath = this.fs.getPath(this.releaseInHex + "-modules");
         if (!Files.exists(modPath, new LinkOption[0])) {
            throw new IllegalArgumentException("release " + this.compliance + " is not found in the system");
         } else {
            this.modulePath = this.file.getPath() + "|" + modPath.toString();
            Map cache = (Map)ModulesCache.get(this.modulePath);
            if (cache == null) {
               try {
                  Throwable var3 = null;
                  Object var4 = null;

                  try {
                     DirectoryStream stream = Files.newDirectoryStream(modPath);

                     try {
                        final HashMap newCache = new HashMap();
                        Iterator var8 = stream.iterator();

                        while(var8.hasNext()) {
                           Path subdir = (Path)var8.next();
                           Files.walkFileTree(subdir, new FileVisitor() {
                              public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                                 return FileVisitResult.CONTINUE;
                              }

                              public FileVisitResult visitFile(Path f, BasicFileAttributes attrs) throws IOException {
                                 byte[] content = null;
                                 if (Files.exists(f, new LinkOption[0])) {
                                    byte[] contentx = JRTUtil.safeReadBytes(f);
                                    if (contentx == null) {
                                       return FileVisitResult.CONTINUE;
                                    }

                                    ClasspathJep247.this.acceptModule(contentx, newCache);
                                    ClasspathJep247.this.moduleNamesCache.add(JRTUtil.sanitizedFileName(f));
                                 }

                                 return FileVisitResult.CONTINUE;
                              }

                              public FileVisitResult visitFileFailed(Path f, IOException exc) throws IOException {
                                 return FileVisitResult.CONTINUE;
                              }

                              public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                                 return FileVisitResult.CONTINUE;
                              }
                           });
                        }

                        synchronized(ModulesCache) {
                           if (ModulesCache.get(this.modulePath) == null) {
                              ModulesCache.put(this.modulePath, Collections.unmodifiableMap(newCache));
                           }
                        }
                     } finally {
                        if (stream != null) {
                           stream.close();
                        }

                     }
                  } catch (Throwable var19) {
                     if (var3 == null) {
                        var3 = var19;
                     } else if (var3 != var19) {
                        var3.addSuppressed(var19);
                     }

                     throw var3;
                  }
               } catch (IOException var20) {
                  var20.printStackTrace();
               }
            } else {
               this.moduleNamesCache.addAll(cache.keySet());
            }

         }
      }
   }

   void acceptModule(ClassFileReader reader, Map cache) {
      if (this.jdklevel <= 3407872L) {
         super.acceptModule(reader, cache);
      } else {
         if (reader != null) {
            IModule moduleDecl = reader.getModuleDeclaration();
            if (moduleDecl != null) {
               cache.put(String.valueOf(moduleDecl.name()), moduleDecl);
            }
         }

      }
   }

   protected void addToPackageCache(String packageName, boolean endsWithSep) {
      if (!this.packageCache.contains(packageName)) {
         this.packageCache.add(packageName);
      }
   }

   public synchronized char[][] getModulesDeclaringPackage(String qualifiedPackageName, String moduleName) {
      if (this.packageCache != null) {
         return this.singletonModuleNameIf(this.packageCache.contains(qualifiedPackageName));
      } else {
         this.packageCache = new HashSet(41);
         this.packageCache.add(Util.EMPTY_STRING);
         List sub = new ArrayList();

         try {
            Throwable var4 = null;
            Object var5 = null;

            try {
               DirectoryStream stream = Files.newDirectoryStream(this.releasePath);

               try {
                  Iterator var8 = stream.iterator();

                  while(var8.hasNext()) {
                     Path subdir = (Path)var8.next();
                     String rel = JRTUtil.sanitizedFileName(subdir);
                     if (rel.contains(this.releaseInHex)) {
                        sub.add(rel);
                        Files.walkFileTree(subdir, new FileVisitor() {
                           public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                              if (dir.getNameCount() <= 1) {
                                 return FileVisitResult.CONTINUE;
                              } else {
                                 Path relative = dir.subpath(1, dir.getNameCount());
                                 ClasspathJep247.this.addToPackageCache(relative.toString(), false);
                                 return FileVisitResult.CONTINUE;
                              }
                           }

                           public FileVisitResult visitFile(Path f, BasicFileAttributes attrs) throws IOException {
                              return FileVisitResult.CONTINUE;
                           }

                           public FileVisitResult visitFileFailed(Path f, IOException exc) throws IOException {
                              return FileVisitResult.CONTINUE;
                           }

                           public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                              return FileVisitResult.CONTINUE;
                           }
                        });
                     }
                  }
               } finally {
                  if (stream != null) {
                     stream.close();
                  }

               }
            } catch (Throwable var17) {
               if (var4 == null) {
                  var4 = var17;
               } else if (var4 != var17) {
                  var4.addSuppressed(var17);
               }

               throw var4;
            }
         } catch (IOException var18) {
            var18.printStackTrace();
         }

         this.subReleases = (String[])sub.toArray(new String[sub.size()]);
         return this.singletonModuleNameIf(this.packageCache.contains(qualifiedPackageName));
      }
   }

   public void reset() {
      try {
         super.reset();
         this.fs.close();
      } catch (IOException var1) {
      }

   }

   public String toString() {
      return "Classpath for JEP 247 for JDK " + this.file.getPath();
   }

   public char[] normalizedPath() {
      if (this.normalizedPath == null) {
         String path2 = this.getPath();
         char[] rawName = path2.toCharArray();
         if (File.separatorChar == '\\') {
            CharOperation.replace(rawName, '\\', '/');
         }

         this.normalizedPath = CharOperation.subarray((char[])rawName, 0, CharOperation.lastIndexOf('.', rawName));
      }

      return this.normalizedPath;
   }

   public String getPath() {
      if (this.path == null) {
         try {
            this.path = this.file.getCanonicalPath();
         } catch (IOException var1) {
            this.path = this.file.getAbsolutePath();
         }
      }

      return this.path;
   }

   public int getMode() {
      return 2;
   }
}
