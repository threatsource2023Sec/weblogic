package com.bea.core.repackaged.jdt.internal.compiler.batch;

import com.bea.core.repackaged.jdt.internal.compiler.CompilationResult;
import com.bea.core.repackaged.jdt.internal.compiler.ast.CompilationUnitDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.classfmt.ClassFileReader;
import com.bea.core.repackaged.jdt.internal.compiler.classfmt.ClassFormatException;
import com.bea.core.repackaged.jdt.internal.compiler.env.AccessRuleSet;
import com.bea.core.repackaged.jdt.internal.compiler.env.IBinaryModule;
import com.bea.core.repackaged.jdt.internal.compiler.env.ICompilationUnit;
import com.bea.core.repackaged.jdt.internal.compiler.env.IModule;
import com.bea.core.repackaged.jdt.internal.compiler.env.PackageExportImpl;
import com.bea.core.repackaged.jdt.internal.compiler.parser.Parser;
import com.bea.core.repackaged.jdt.internal.compiler.util.Util;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ModuleFinder {
   public static List findModules(File f, String destinationPath, Parser parser, Map options, boolean isModulepath, String release) {
      List collector = new ArrayList();
      scanForModules(destinationPath, parser, options, isModulepath, false, collector, f, release);
      return collector;
   }

   protected static FileSystem.Classpath findModule(File file, String destinationPath, Parser parser, Map options, boolean isModulepath, String release) {
      FileSystem.Classpath modulePath = FileSystem.getClasspath(file.getAbsolutePath(), (String)null, !isModulepath, (AccessRuleSet)null, destinationPath == null ? null : destinationPath + File.separator + file.getName(), options, release);
      if (modulePath != null) {
         scanForModule(modulePath, file, parser, isModulepath, release);
      }

      return modulePath;
   }

   protected static void scanForModules(String destinationPath, Parser parser, Map options, boolean isModulepath, boolean thisAnAutomodule, List collector, File file, String release) {
      FileSystem.Classpath entry = FileSystem.getClasspath(file.getAbsolutePath(), (String)null, !isModulepath, (AccessRuleSet)null, destinationPath == null ? null : destinationPath + File.separator + file.getName(), options, release);
      if (entry != null) {
         IModule module = scanForModule(entry, file, parser, thisAnAutomodule, release);
         if (module != null) {
            collector.add(entry);
         } else if (file.isDirectory()) {
            File[] files = file.listFiles();
            File[] var14 = files;
            int var13 = files.length;

            for(int var12 = 0; var12 < var13; ++var12) {
               File f = var14[var12];
               scanForModules(destinationPath, parser, options, isModulepath, isModulepath, collector, f, release);
            }
         }
      }

   }

   protected static IModule scanForModule(FileSystem.Classpath modulePath, final File file, Parser parser, boolean considerAutoModules, String release) {
      IModule module = null;
      if (file.isDirectory()) {
         String[] list = file.list(new FilenameFilter() {
            public boolean accept(File dir, String name) {
               return dir == file && (name.equalsIgnoreCase("module-info.class") || name.equalsIgnoreCase("module-info.java"));
            }
         });
         if (list.length > 0) {
            switch (fileName) {
               case "module-info.class":
                  module = extractModuleFromClass(new File(file, fileName), modulePath);
                  break;
               case "module-info.java":
                  module = extractModuleFromSource(new File(file, fileName), parser, modulePath);
                  if (module == null) {
                     return null;
                  }

                  String modName = new String(module.name());
                  if (!modName.equals(file.getName())) {
                     throw new IllegalArgumentException("module name " + modName + " does not match expected name " + file.getName());
                  }
            }
         }
      } else {
         String moduleDescPath = getModulePathForArchive(file);
         if (moduleDescPath != null) {
            module = extractModuleFromArchive(file, modulePath, moduleDescPath, release);
         }
      }

      if (considerAutoModules && module == null && !(modulePath instanceof ClasspathJrt)) {
         module = IModule.createAutomatic(getFileName(file), file.isFile(), getManifest(file));
      }

      if (module != null) {
         modulePath.acceptModule(module);
      }

      return module;
   }

   private static Manifest getManifest(File file) {
      if (getModulePathForArchive(file) == null) {
         return null;
      } else {
         try {
            Throwable var1 = null;
            Object var2 = null;

            try {
               JarFile jar = new JarFile(file);

               Manifest var10000;
               try {
                  var10000 = jar.getManifest();
               } finally {
                  if (jar != null) {
                     jar.close();
                  }

               }

               return var10000;
            } catch (Throwable var11) {
               if (var1 == null) {
                  var1 = var11;
               } else if (var1 != var11) {
                  var1.addSuppressed(var11);
               }

               throw var1;
            }
         } catch (IOException var12) {
            return null;
         }
      }
   }

   private static String getFileName(File file) {
      String name = file.getName();
      int index = name.lastIndexOf(46);
      return index == -1 ? name : name.substring(0, index);
   }

   protected static String[] extractAddonRead(String option) {
      StringTokenizer tokenizer = new StringTokenizer(option, "=");
      String source = null;
      String target = null;
      if (tokenizer.hasMoreTokens()) {
         source = tokenizer.nextToken();
         if (tokenizer.hasMoreTokens()) {
            target = tokenizer.nextToken();
            return new String[]{source, target};
         } else {
            return null;
         }
      } else {
         return null;
      }
   }

   protected static AddExport extractAddonExport(String option) {
      StringTokenizer tokenizer = new StringTokenizer(option, "/");
      String source = null;
      String pack = null;
      List targets = new ArrayList();
      if (!tokenizer.hasMoreTokens()) {
         return null;
      } else {
         source = tokenizer.nextToken("/");
         if (!tokenizer.hasMoreTokens()) {
            return null;
         } else {
            pack = tokenizer.nextToken("/=");

            while(tokenizer.hasMoreTokens()) {
               targets.add(tokenizer.nextToken("=,"));
            }

            PackageExportImpl export = new PackageExportImpl();
            export.pack = pack.toCharArray();
            export.exportedTo = new char[targets.size()][];

            for(int i = 0; i < export.exportedTo.length; ++i) {
               export.exportedTo[i] = ((String)targets.get(i)).toCharArray();
            }

            return new AddExport(source, export);
         }
      }
   }

   private static String getModulePathForArchive(File file) {
      int format = Util.archiveFormat(file.getAbsolutePath());
      if (format == 0) {
         return "module-info.class";
      } else {
         return format == 1 ? "classes/module-info.class" : null;
      }
   }

   private static IModule extractModuleFromArchive(File file, FileSystem.Classpath pathEntry, String path, String release) {
      ZipFile zipFile = null;

      IBinaryModule var8;
      try {
         zipFile = new ZipFile(file);
         if (release != null) {
            String releasePath = "META-INF/versions/" + release + "/" + path;
            ZipEntry entry = zipFile.getEntry(releasePath);
            if (entry != null) {
               path = releasePath;
            }
         }

         ClassFileReader reader = ClassFileReader.read(zipFile, path);
         IModule module = getModule(reader);
         if (module == null) {
            return null;
         }

         var8 = reader.getModuleDeclaration();
      } catch (IOException | ClassFormatException var16) {
         return null;
      } finally {
         if (zipFile != null) {
            try {
               zipFile.close();
            } catch (IOException var15) {
            }
         }

      }

      return var8;
   }

   private static IModule extractModuleFromClass(File classfilePath, FileSystem.Classpath pathEntry) {
      try {
         ClassFileReader reader = ClassFileReader.read(classfilePath);
         IModule module = getModule(reader);
         return module != null ? reader.getModuleDeclaration() : null;
      } catch (IOException | ClassFormatException var4) {
         var4.printStackTrace();
         return null;
      }
   }

   private static IModule getModule(ClassFileReader classfile) {
      return classfile != null ? classfile.getModuleDeclaration() : null;
   }

   private static IModule extractModuleFromSource(File file, Parser parser, FileSystem.Classpath pathEntry) {
      ICompilationUnit cu = new CompilationUnit((char[])null, file.getAbsolutePath(), (String)null, pathEntry.getDestinationPath());
      CompilationResult compilationResult = new CompilationResult(cu, 0, 1, 10);
      CompilationUnitDeclaration unit = parser.parse((ICompilationUnit)cu, (CompilationResult)compilationResult);
      return unit.isModuleInfo() && unit.moduleDeclaration != null ? new BasicModule(unit.moduleDeclaration, pathEntry) : null;
   }

   static class AddExport {
      public final String sourceModuleName;
      public final IModule.IPackageExport export;

      public AddExport(String moduleName, IModule.IPackageExport export) {
         this.sourceModuleName = moduleName;
         this.export = export;
      }
   }
}
