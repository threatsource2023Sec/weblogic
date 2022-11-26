package com.bea.core.repackaged.jdt.internal.compiler.batch;

import com.bea.core.repackaged.jdt.internal.compiler.classfmt.ClassFileReader;
import com.bea.core.repackaged.jdt.internal.compiler.classfmt.ClassFormatException;
import com.bea.core.repackaged.jdt.internal.compiler.env.AccessRuleSet;
import com.bea.core.repackaged.jdt.internal.compiler.env.IModule;
import com.bea.core.repackaged.jdt.internal.compiler.env.NameEnvironmentAnswer;
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
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class ClasspathJep247Jdk12 extends ClasspathJep247 {
   Map modules;

   public ClasspathJep247Jdk12(File jdkHome, String release, AccessRuleSet accessRuleSet) {
      super(jdkHome, release, accessRuleSet);
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

               label266:
               for(int var9 = 0; var9 < var10; ++var9) {
                  String rel = var11[var9];
                  Path p;
                  if (moduleName == null) {
                     p = this.fs.getPath(rel);
                     Throwable var13 = null;
                     Object var14 = null;

                     try {
                        DirectoryStream stream = Files.newDirectoryStream(p);

                        try {
                           Iterator var17 = stream.iterator();

                           while(var17.hasNext()) {
                              Path subdir = (Path)var17.next();
                              Path f = this.fs.getPath(rel, JRTUtil.sanitizedFileName(subdir), qualifiedBinaryFileName);
                              if (Files.exists(f, new LinkOption[0])) {
                                 content = JRTUtil.safeReadBytes(f);
                                 if (content != null) {
                                    break label266;
                                 }
                              }
                           }
                        } finally {
                           if (stream != null) {
                              stream.close();
                           }

                        }
                     } catch (Throwable var28) {
                        if (var13 == null) {
                           var13 = var28;
                        } else if (var13 != var28) {
                           var13.addSuppressed(var28);
                        }

                        throw var13;
                     }
                  } else {
                     p = this.fs.getPath(rel, moduleName, qualifiedBinaryFileName);
                     if (Files.exists(p, new LinkOption[0])) {
                        content = JRTUtil.safeReadBytes(p);
                        if (content != null) {
                           break;
                        }
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
         } catch (ClassFormatException var29) {
         } catch (IOException var30) {
         }

         return null;
      }
   }

   public void initialize() throws IOException {
      if (this.compliance != null) {
         if (this.fs != null) {
            super.initialize();
         } else {
            this.releaseInHex = Integer.toHexString(Integer.parseInt(this.compliance)).toUpperCase();
            Path filePath = this.jdkHome.toPath().resolve("lib").resolve("ct.sym");
            URI t = filePath.toUri();
            if (Files.exists(filePath, new LinkOption[0])) {
               URI uri = URI.create("jar:file:" + t.getRawPath());

               try {
                  this.fs = FileSystems.getFileSystem(uri);
               } catch (FileSystemNotFoundException var19) {
               }

               if (this.fs == null) {
                  HashMap env = new HashMap();
                  this.fs = FileSystems.newFileSystem(uri, env);
               }

               this.releasePath = this.fs.getPath("/");
               if (!Files.exists(this.fs.getPath(this.releaseInHex), new LinkOption[0])) {
                  throw new IllegalArgumentException("release " + this.compliance + " is not found in the system");
               } else {
                  List sub = new ArrayList();

                  try {
                     Throwable var5 = null;
                     Object var6 = null;

                     try {
                        DirectoryStream stream = Files.newDirectoryStream(this.releasePath);

                        try {
                           Iterator var9 = stream.iterator();

                           while(var9.hasNext()) {
                              Path subdir = (Path)var9.next();
                              String rel = JRTUtil.sanitizedFileName(subdir);
                              if (rel.contains(this.releaseInHex)) {
                                 sub.add(rel);
                              }
                           }

                           this.subReleases = (String[])sub.toArray(new String[sub.size()]);
                        } finally {
                           if (stream != null) {
                              stream.close();
                           }

                        }
                     } catch (Throwable var21) {
                        if (var5 == null) {
                           var5 = var21;
                        } else if (var5 != var21) {
                           var5.addSuppressed(var21);
                        }

                        throw var5;
                     }
                  } catch (IOException var22) {
                  }

                  super.initialize();
               }
            }
         }
      }
   }

   public void loadModules() {
      if (this.jdklevel <= 3407872L) {
         super.loadModules();
      } else {
         Path modPath = this.fs.getPath(this.releaseInHex);
         this.modulePath = this.file.getPath() + "|" + modPath.toString();
         this.modules = (Map)ModulesCache.get(this.modulePath);
         if (this.modules == null) {
            try {
               Throwable var2 = null;
               Object var3 = null;

               try {
                  DirectoryStream stream = Files.newDirectoryStream(this.releasePath);

                  try {
                     final HashMap newCache = new HashMap();
                     Iterator var7 = stream.iterator();

                     while(var7.hasNext()) {
                        Path subdir = (Path)var7.next();
                        String rel = JRTUtil.sanitizedFileName(subdir);
                        if (rel.contains(this.releaseInHex)) {
                           Files.walkFileTree(subdir, Collections.EMPTY_SET, 2, new FileVisitor() {
                              public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                                 return FileVisitResult.CONTINUE;
                              }

                              public FileVisitResult visitFile(Path f, BasicFileAttributes attrs) throws IOException {
                                 if (!attrs.isDirectory() && f.getNameCount() >= 3) {
                                    byte[] contentx = null;
                                    if (Files.exists(f, new LinkOption[0])) {
                                       byte[] content = JRTUtil.safeReadBytes(f);
                                       if (content == null) {
                                          return FileVisitResult.CONTINUE;
                                       }

                                       Path m = f.subpath(1, f.getNameCount() - 1);
                                       String name = JRTUtil.sanitizedFileName(m);
                                       ClasspathJep247Jdk12.this.acceptModule(name, content, newCache);
                                       ClasspathJep247Jdk12.this.moduleNamesCache.add(name);
                                    }

                                    return FileVisitResult.SKIP_SIBLINGS;
                                 } else {
                                    return FileVisitResult.CONTINUE;
                                 }
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

                     synchronized(ModulesCache) {
                        if (ModulesCache.get(this.modulePath) == null) {
                           this.modules = Collections.unmodifiableMap(newCache);
                           ModulesCache.put(this.modulePath, this.modules);
                        }
                     }
                  } finally {
                     if (stream != null) {
                        stream.close();
                     }

                  }
               } catch (Throwable var19) {
                  if (var2 == null) {
                     var2 = var19;
                  } else if (var2 != var19) {
                     var2.addSuppressed(var19);
                  }

                  throw var2;
               }
            } catch (IOException var20) {
               var20.printStackTrace();
            }
         } else {
            this.moduleNamesCache.addAll(this.modules.keySet());
         }

      }
   }

   public Collection getModuleNames(Collection limitModule, Function getModule) {
      return this.selectModules(this.moduleNamesCache, limitModule, getModule);
   }

   public IModule getModule(char[] moduleName) {
      return this.modules != null ? (IModule)this.modules.get(String.valueOf(moduleName)) : null;
   }

   void acceptModule(String name, byte[] content, Map cache) {
      if (content != null) {
         if (!cache.containsKey(name)) {
            ClassFileReader reader = null;

            try {
               reader = new ClassFileReader(content, "module-info.class".toCharArray());
            } catch (ClassFormatException var6) {
               var6.printStackTrace();
            }

            if (reader != null) {
               this.acceptModule(reader, cache);
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

   public synchronized char[][] getModulesDeclaringPackage(String qualifiedPackageName, String moduleName) {
      if (this.packageCache != null) {
         return this.singletonModuleNameIf(this.packageCache.contains(qualifiedPackageName));
      } else {
         this.packageCache = new HashSet(41);
         this.packageCache.add(Util.EMPTY_STRING);

         try {
            Throwable var3 = null;
            Object var4 = null;

            try {
               DirectoryStream stream = Files.newDirectoryStream(this.releasePath);

               try {
                  Iterator var7 = stream.iterator();

                  while(true) {
                     Path subdir;
                     String rel;
                     do {
                        if (!var7.hasNext()) {
                           return this.singletonModuleNameIf(this.packageCache.contains(qualifiedPackageName));
                        }

                        subdir = (Path)var7.next();
                        rel = JRTUtil.sanitizedFileName(subdir);
                     } while(!rel.contains(this.releaseInHex));

                     Throwable var9 = null;
                     Object var10 = null;

                     try {
                        DirectoryStream stream2 = Files.newDirectoryStream(subdir);

                        try {
                           Iterator var13 = stream2.iterator();

                           while(var13.hasNext()) {
                              Path subdir2 = (Path)var13.next();
                              Files.walkFileTree(subdir2, new FileVisitor() {
                                 public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                                    if (dir.getNameCount() <= 2) {
                                       return FileVisitResult.CONTINUE;
                                    } else {
                                       Path relative = dir.subpath(2, dir.getNameCount());
                                       ClasspathJep247Jdk12.this.addToPackageCache(relative.toString(), false);
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
                        } finally {
                           if (stream2 != null) {
                              stream2.close();
                           }

                        }
                     } catch (Throwable var35) {
                        if (var9 == null) {
                           var9 = var35;
                        } else if (var9 != var35) {
                           var9.addSuppressed(var35);
                        }

                        throw var9;
                     }
                  }
               } finally {
                  if (stream != null) {
                     stream.close();
                  }

               }
            } catch (Throwable var37) {
               if (var3 == null) {
                  var3 = var37;
               } else if (var3 != var37) {
                  var3.addSuppressed(var37);
               }

               throw var3;
            }
         } catch (IOException var38) {
            var38.printStackTrace();
            return this.singletonModuleNameIf(this.packageCache.contains(qualifiedPackageName));
         }
      }
   }
}
