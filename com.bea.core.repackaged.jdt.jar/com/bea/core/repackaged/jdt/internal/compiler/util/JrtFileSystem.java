package com.bea.core.repackaged.jdt.internal.compiler.util;

import com.bea.core.repackaged.jdt.internal.compiler.classfmt.ClassFileReader;
import com.bea.core.repackaged.jdt.internal.compiler.classfmt.ClassFormatException;
import com.bea.core.repackaged.jdt.internal.compiler.env.IModule;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

class JrtFileSystem {
   private final Map packageToModule = new HashMap();
   private final Map packageToModules = new HashMap();
   FileSystem fs = null;
   Path modRoot = null;
   String jdkHome = null;

   public static JrtFileSystem getNewJrtFileSystem(File jrt, String release) throws IOException {
      return (JrtFileSystem)(release == null ? new JrtFileSystem(jrt) : new JrtFileSystemWithOlderRelease(jrt, release));
   }

   JrtFileSystem(File jrt) throws IOException {
      this.initialize(jrt);
   }

   void initialize(File jrt) throws IOException {
      URL jrtPath = null;
      this.jdkHome = null;
      if (jrt.toString().endsWith("jrt-fs.jar")) {
         jrtPath = jrt.toPath().toUri().toURL();
         this.jdkHome = jrt.getParentFile().getParent();
      } else {
         this.jdkHome = jrt.toPath().toString();
         jrtPath = Paths.get(this.jdkHome, "lib", "jrt-fs.jar").toUri().toURL();
      }

      JRTUtil.MODULE_TO_LOAD = System.getProperty("modules.to.load");
      String javaVersion = System.getProperty("java.version");
      if (javaVersion != null && javaVersion.startsWith("1.8")) {
         URLClassLoader loader = new URLClassLoader(new URL[]{jrtPath});
         HashMap env = new HashMap();
         this.fs = FileSystems.newFileSystem(JRTUtil.JRT_URI, env, loader);
      } else {
         HashMap env = new HashMap();
         env.put("java.home", this.jdkHome);
         this.fs = FileSystems.newFileSystem(JRTUtil.JRT_URI, env);
      }

      this.modRoot = this.fs.getPath("/modules");
      this.walkJrtForModules();
   }

   public List getModulesDeclaringPackage(String qualifiedPackageName, String moduleName) {
      qualifiedPackageName = qualifiedPackageName.replace('.', '/');
      String module = (String)this.packageToModule.get(qualifiedPackageName);
      if (moduleName == null) {
         if (module == null) {
            return null;
         } else {
            return module == "MU" ? (List)this.packageToModules.get(qualifiedPackageName) : Collections.singletonList(module);
         }
      } else {
         if (module != null) {
            if (module == "MU") {
               List list = (List)this.packageToModules.get(qualifiedPackageName);
               if (list.contains(moduleName)) {
                  return Collections.singletonList(moduleName);
               }
            } else if (module.equals(moduleName)) {
               return Collections.singletonList(moduleName);
            }
         }

         return null;
      }
   }

   public String[] getModules(String fileName) {
      int idx = fileName.lastIndexOf(47);
      String pack = null;
      if (idx != -1) {
         pack = fileName.substring(0, idx);
      } else {
         pack = "";
      }

      String module = (String)this.packageToModule.get(pack);
      if (module != null) {
         if (module == "MU") {
            List list = (List)this.packageToModules.get(pack);
            return (String[])list.toArray(new String[list.size()]);
         } else {
            return new String[]{module};
         }
      } else {
         return JRTUtil.DEFAULT_MODULE;
      }
   }

   public boolean hasClassFile(String qualifiedPackageName, String module) {
      if (module == null) {
         return false;
      } else {
         String knownModule = (String)this.packageToModule.get(qualifiedPackageName);
         if (knownModule == null || knownModule != "MU" && !knownModule.equals(module)) {
            return false;
         } else {
            Path packagePath = this.fs.getPath("/modules", module, qualifiedPackageName);
            if (!Files.exists(packagePath, new LinkOption[0])) {
               return false;
            } else {
               try {
                  return Files.list(packagePath).anyMatch((filePath) -> {
                     return filePath.toString().endsWith(".class") || filePath.toString().endsWith(".CLASS");
                  });
               } catch (IOException var5) {
                  return false;
               }
            }
         }
      }
   }

   public InputStream getContentFromJrt(String fileName, String module) throws IOException {
      if (module != null) {
         return Files.newInputStream(this.fs.getPath("/modules", module, fileName));
      } else {
         String[] modules = this.getModules(fileName);
         if (modules.length != 0) {
            String mod = modules[0];
            return Files.newInputStream(this.fs.getPath("/modules", mod, fileName));
         } else {
            return null;
         }
      }
   }

   private ClassFileReader getClassfile(String fileName, Predicate moduleNameFilter) throws IOException, ClassFormatException {
      String[] modules = this.getModules(fileName);
      byte[] content = null;
      String module = null;
      String[] var9 = modules;
      int var8 = modules.length;

      for(int var7 = 0; var7 < var8; ++var7) {
         String mod = var9[var7];
         if (moduleNameFilter == null || moduleNameFilter.test(mod)) {
            content = JRTUtil.safeReadBytes(this.fs.getPath("/modules", mod, fileName));
            if (content != null) {
               module = mod;
               break;
            }
         }
      }

      if (content != null) {
         ClassFileReader reader = new ClassFileReader(content, fileName.toCharArray());
         reader.moduleName = module.toCharArray();
         return reader;
      } else {
         return null;
      }
   }

   byte[] getClassfileContent(String fileName, String module) throws IOException, ClassFormatException {
      byte[] content = null;
      if (module != null) {
         content = this.getClassfileBytes(fileName, module);
      } else {
         String[] modules = this.getModules(fileName);
         String[] var8 = modules;
         int var7 = modules.length;

         for(int var6 = 0; var6 < var7; ++var6) {
            String mod = var8[var6];
            content = JRTUtil.safeReadBytes(this.fs.getPath("/modules", mod, fileName));
            if (content != null) {
               break;
            }
         }
      }

      return content;
   }

   private byte[] getClassfileBytes(String fileName, String module) throws IOException, ClassFormatException {
      return JRTUtil.safeReadBytes(this.fs.getPath("/modules", module, fileName));
   }

   public ClassFileReader getClassfile(String fileName, String module, Predicate moduleNameFilter) throws IOException, ClassFormatException {
      ClassFileReader reader = null;
      if (module == null) {
         reader = this.getClassfile(fileName, moduleNameFilter);
      } else {
         byte[] content = this.getClassfileBytes(fileName, module);
         if (content != null) {
            reader = new ClassFileReader(content, fileName.toCharArray());
            reader.moduleName = module.toCharArray();
         }
      }

      return reader;
   }

   public ClassFileReader getClassfile(String fileName, IModule module) throws IOException, ClassFormatException {
      ClassFileReader reader = null;
      if (module == null) {
         reader = this.getClassfile(fileName, (Predicate)null);
      } else {
         byte[] content = this.getClassfileBytes(fileName, new String(module.name()));
         if (content != null) {
            reader = new ClassFileReader(content, fileName.toCharArray());
         }
      }

      return reader;
   }

   void walkJrtForModules() throws IOException {
      Iterable roots = this.fs.getRootDirectories();
      Iterator var3 = roots.iterator();

      while(var3.hasNext()) {
         Path path = (Path)var3.next();

         try {
            Throwable var4 = null;
            Object var5 = null;

            try {
               DirectoryStream stream = Files.newDirectoryStream(path);

               try {
                  Iterator var8 = stream.iterator();

                  while(var8.hasNext()) {
                     final Path subdir = (Path)var8.next();
                     if (!subdir.toString().equals("/modules")) {
                        Files.walkFileTree(subdir, new JRTUtil.AbstractFileVisitor() {
                           public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                              Path relative = subdir.relativize(file);
                              JrtFileSystem.this.cachePackage(relative.getParent().toString(), relative.getFileName().toString());
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
            } catch (Throwable var16) {
               if (var4 == null) {
                  var4 = var16;
               } else if (var4 != var16) {
                  var4.addSuppressed(var16);
               }

               throw var4;
            }
         } catch (Exception var17) {
            throw new IOException(var17.getMessage());
         }
      }

   }

   void walkModuleImage(final JRTUtil.JrtFileVisitor visitor, final int notify) throws IOException {
      Files.walkFileTree(this.modRoot, new JRTUtil.AbstractFileVisitor() {
         public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            int count = dir.getNameCount();
            if (count == 1) {
               return FileVisitResult.CONTINUE;
            } else if (count == 2) {
               Path mod = dir.getName(1);
               if (JRTUtil.MODULE_TO_LOAD != null && JRTUtil.MODULE_TO_LOAD.length() > 0 && JRTUtil.MODULE_TO_LOAD.indexOf(mod.toString()) == -1) {
                  return FileVisitResult.SKIP_SUBTREE;
               } else {
                  return (notify & JRTUtil.NOTIFY_MODULES) == 0 ? FileVisitResult.CONTINUE : visitor.visitModule(dir, JRTUtil.sanitizedFileName(mod));
               }
            } else {
               return (notify & JRTUtil.NOTIFY_PACKAGES) == 0 ? FileVisitResult.CONTINUE : visitor.visitPackage(dir.subpath(2, count), dir.getName(1), attrs);
            }
         }

         public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            if ((notify & JRTUtil.NOTIFY_FILES) == 0) {
               return FileVisitResult.CONTINUE;
            } else {
               int count = file.getNameCount();
               if (count == 3) {
                  JrtFileSystem.this.cachePackage("", file.getName(1).toString());
               }

               return visitor.visitFile(file.subpath(2, file.getNameCount()), file.getName(1), attrs);
            }
         }
      });
   }

   void cachePackage(String packageName, String module) {
      packageName = packageName.intern();
      module = module.intern();
      packageName = packageName.replace('.', '/');
      Object current = this.packageToModule.get(packageName);
      if (current == null) {
         this.packageToModule.put(packageName, module);
      } else {
         if (current == module || current.equals(module)) {
            return;
         }

         if (current == "MU") {
            List list = (List)this.packageToModules.get(packageName);
            if (!list.contains(module)) {
               if ("java.base" != module && !"java.base".equals(module)) {
                  list.add(module);
               } else {
                  list.add(0, "java.base");
               }
            }
         } else {
            String first = (String)current;
            this.packageToModule.put(packageName, "MU");
            List list = new ArrayList();
            if ("java.base" != current && !"java.base".equals(current)) {
               list.add(module);
               list.add(first);
            } else {
               list.add(first);
               list.add(module);
            }

            this.packageToModules.put(packageName, list);
         }
      }

   }
}
