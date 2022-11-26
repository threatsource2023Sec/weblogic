package com.bea.core.repackaged.jdt.internal.compiler.batch;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.classfmt.ClassFileReader;
import com.bea.core.repackaged.jdt.internal.compiler.classfmt.ClassFormatException;
import com.bea.core.repackaged.jdt.internal.compiler.classfmt.ExternalAnnotationDecorator;
import com.bea.core.repackaged.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;
import com.bea.core.repackaged.jdt.internal.compiler.env.AccessRuleSet;
import com.bea.core.repackaged.jdt.internal.compiler.env.IBinaryType;
import com.bea.core.repackaged.jdt.internal.compiler.env.IModule;
import com.bea.core.repackaged.jdt.internal.compiler.env.IMultiModuleEntry;
import com.bea.core.repackaged.jdt.internal.compiler.env.NameEnvironmentAnswer;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BinaryTypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.util.JRTUtil;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
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
import java.util.Set;
import java.util.function.Function;
import java.util.zip.ZipFile;

public class ClasspathJrt extends ClasspathLocation implements IMultiModuleEntry {
   public File file;
   protected ZipFile annotationZipFile;
   protected boolean closeZipFileAtEnd;
   protected static HashMap ModulesCache = new HashMap();
   public final Set moduleNamesCache;
   protected List annotationPaths;

   public ClasspathJrt(File file, boolean closeZipFileAtEnd, AccessRuleSet accessRuleSet, String destinationPath) {
      super(accessRuleSet, destinationPath);
      this.file = file;
      this.closeZipFileAtEnd = closeZipFileAtEnd;
      this.moduleNamesCache = new HashSet();
   }

   public List fetchLinkedJars(FileSystem.ClasspathSectionProblemReporter problemReporter) {
      return null;
   }

   public char[][] getModulesDeclaringPackage(String qualifiedPackageName, String moduleName) {
      List modules = JRTUtil.getModulesDeclaringPackage(this.file, qualifiedPackageName, moduleName);
      return CharOperation.toCharArrays(modules);
   }

   public boolean hasCompilationUnit(String qualifiedPackageName, String moduleName) {
      return JRTUtil.hasCompilationUnit(this.file, qualifiedPackageName, moduleName);
   }

   public NameEnvironmentAnswer findClass(char[] typeName, String qualifiedPackageName, String moduleName, String qualifiedBinaryFileName) {
      return this.findClass(typeName, qualifiedPackageName, moduleName, qualifiedBinaryFileName, false);
   }

   public NameEnvironmentAnswer findClass(char[] typeName, String qualifiedPackageName, String moduleName, String qualifiedBinaryFileName, boolean asBinaryOnly) {
      if (!this.isPackage(qualifiedPackageName, moduleName)) {
         return null;
      } else {
         try {
            File var10000 = this.file;
            Set var10003 = this.moduleNamesCache;
            var10003.getClass();
            IBinaryType reader = ClassFileReader.readFromModule(var10000, moduleName, qualifiedBinaryFileName, var10003::contains);
            if (reader != null) {
               if (this.annotationPaths != null) {
                  label57: {
                     String qualifiedClassName = qualifiedBinaryFileName.substring(0, qualifiedBinaryFileName.length() - "CLASS".length() - 1);
                     Iterator var10 = this.annotationPaths.iterator();

                     while(var10.hasNext()) {
                        String annotationPath = (String)var10.next();

                        try {
                           if (this.annotationZipFile == null) {
                              this.annotationZipFile = ExternalAnnotationDecorator.getAnnotationZipFile(annotationPath, (ExternalAnnotationDecorator.ZipFileProducer)null);
                           }

                           reader = ExternalAnnotationDecorator.create((IBinaryType)reader, annotationPath, qualifiedClassName, this.annotationZipFile);
                           if (((IBinaryType)reader).getExternalAnnotationStatus() == BinaryTypeBinding.ExternalAnnotationStatus.TYPE_IS_ANNOTATED) {
                              break label57;
                           }
                        } catch (IOException var11) {
                        }
                     }

                     reader = new ExternalAnnotationDecorator((IBinaryType)reader, (ExternalAnnotationProvider)null);
                  }
               }

               char[] answerModuleName = ((IBinaryType)reader).getModule();
               if (answerModuleName == null && moduleName != null) {
                  answerModuleName = moduleName.toCharArray();
               }

               return new NameEnvironmentAnswer((IBinaryType)reader, this.fetchAccessRestriction(qualifiedBinaryFileName), answerModuleName);
            }
         } catch (ClassFormatException var12) {
         } catch (IOException var13) {
         }

         return null;
      }
   }

   public boolean hasAnnotationFileFor(String qualifiedTypeName) {
      return false;
   }

   public char[][][] findTypeNames(final String qualifiedPackageName, final String moduleName) {
      if (!this.isPackage(qualifiedPackageName, moduleName)) {
         return null;
      } else {
         final char[] packageArray = qualifiedPackageName.toCharArray();
         final ArrayList answers = new ArrayList();

         try {
            JRTUtil.walkModuleImage(this.file, new JRTUtil.JrtFileVisitor() {
               public FileVisitResult visitPackage(Path dir, Path modPath, BasicFileAttributes attrs) throws IOException {
                  return qualifiedPackageName.startsWith(dir.toString()) ? FileVisitResult.CONTINUE : FileVisitResult.SKIP_SUBTREE;
               }

               public FileVisitResult visitFile(Path dir, Path modPath, BasicFileAttributes attrs) throws IOException {
                  Path parent = dir.getParent();
                  if (parent == null) {
                     return FileVisitResult.CONTINUE;
                  } else if (!parent.toString().equals(qualifiedPackageName)) {
                     return FileVisitResult.CONTINUE;
                  } else {
                     String fileName = dir.getName(dir.getNameCount() - 1).toString();
                     ClasspathJrt.this.addTypeName(answers, fileName, -1, packageArray);
                     return FileVisitResult.CONTINUE;
                  }
               }

               public FileVisitResult visitModule(Path p, String name) throws IOException {
                  if (moduleName == null) {
                     return FileVisitResult.CONTINUE;
                  } else {
                     return !moduleName.equals(name) ? FileVisitResult.SKIP_SUBTREE : FileVisitResult.CONTINUE;
                  }
               }
            }, JRTUtil.NOTIFY_ALL);
         } catch (IOException var7) {
         }

         int size = answers.size();
         if (size != 0) {
            char[][][] result = new char[size][][];
            answers.toArray(result);
            return result;
         } else {
            return null;
         }
      }
   }

   protected void addTypeName(ArrayList answers, String fileName, int last, char[] packageName) {
      int indexOfDot = fileName.lastIndexOf(46);
      if (indexOfDot != -1) {
         String typeName = fileName.substring(last + 1, indexOfDot);
         answers.add(CharOperation.arrayConcat(CharOperation.splitOn('/', packageName), typeName.toCharArray()));
      }

   }

   public void initialize() throws IOException {
      this.loadModules();
   }

   public void loadModules() {
      Map cache = (Map)ModulesCache.get(this.file.getPath());
      if (cache == null) {
         try {
            final HashMap newCache = new HashMap();
            JRTUtil.walkModuleImage(this.file, new JRTUtil.JrtFileVisitor() {
               public FileVisitResult visitPackage(Path dir, Path mod, BasicFileAttributes attrs) throws IOException {
                  return FileVisitResult.CONTINUE;
               }

               public FileVisitResult visitFile(Path f, Path mod, BasicFileAttributes attrs) throws IOException {
                  return FileVisitResult.CONTINUE;
               }

               public FileVisitResult visitModule(Path p, String name) throws IOException {
                  try {
                     ClasspathJrt.this.acceptModule((byte[])JRTUtil.getClassfileContent(ClasspathJrt.this.file, "module-info.class", name), newCache);
                     ClasspathJrt.this.moduleNamesCache.add(name);
                  } catch (ClassFormatException var4) {
                     var4.printStackTrace();
                  }

                  return FileVisitResult.SKIP_SUBTREE;
               }
            }, JRTUtil.NOTIFY_MODULES);
            synchronized(ModulesCache) {
               if (ModulesCache.get(this.file.getPath()) == null) {
                  ModulesCache.put(this.file.getPath(), Collections.unmodifiableMap(newCache));
               }
            }
         } catch (IOException var5) {
         }
      } else {
         this.moduleNamesCache.addAll(cache.keySet());
      }

   }

   void acceptModule(ClassFileReader reader, Map cache) {
      if (reader != null) {
         IModule moduleDecl = reader.getModuleDeclaration();
         if (moduleDecl != null) {
            cache.put(String.valueOf(moduleDecl.name()), moduleDecl);
         }
      }

   }

   void acceptModule(byte[] content, Map cache) {
      if (content != null) {
         ClassFileReader reader = null;

         try {
            reader = new ClassFileReader(content, "module-info.class".toCharArray());
         } catch (ClassFormatException var5) {
            var5.printStackTrace();
         }

         if (reader != null) {
            this.acceptModule(reader, cache);
         }

      }
   }

   public Collection getModuleNames(Collection limitModule, Function getModule) {
      Map cache = (Map)ModulesCache.get(this.file.getPath());
      return this.selectModules(cache.keySet(), limitModule, getModule);
   }

   protected List allModules(Iterable allSystemModules, Function getModuleName, Function getModule) {
      List result = new ArrayList();
      boolean hasJavaDotSE = false;
      Iterator var7 = allSystemModules.iterator();

      Object mod;
      String moduleName;
      while(var7.hasNext()) {
         mod = (Object)var7.next();
         moduleName = (String)getModuleName.apply(mod);
         if ("java.se".equals(moduleName)) {
            result.add(moduleName);
            hasJavaDotSE = true;
            break;
         }
      }

      var7 = allSystemModules.iterator();

      while(true) {
         while(true) {
            IModule m;
            do {
               boolean isPotentialRoot;
               do {
                  if (!var7.hasNext()) {
                     return result;
                  }

                  mod = (Object)var7.next();
                  moduleName = (String)getModuleName.apply(mod);
                  boolean isJavaDotStart = moduleName.startsWith("java.");
                  isPotentialRoot = !isJavaDotStart;
                  if (!hasJavaDotSE) {
                     isPotentialRoot |= isJavaDotStart;
                  }
               } while(!isPotentialRoot);

               m = (IModule)getModule.apply(mod);
            } while(m == null);

            IModule.IPackageExport[] var15;
            int var14 = (var15 = m.exports()).length;

            for(int var13 = 0; var13 < var14; ++var13) {
               IModule.IPackageExport packageExport = var15[var13];
               if (!packageExport.isQualified()) {
                  result.add(moduleName);
                  break;
               }
            }
         }
      }
   }

   public void reset() {
      if (this.closeZipFileAtEnd && this.annotationZipFile != null) {
         try {
            this.annotationZipFile.close();
         } catch (IOException var1) {
         }

         this.annotationZipFile = null;
      }

      if (this.annotationPaths != null) {
         this.annotationPaths = null;
      }

   }

   public String toString() {
      return "Classpath for JRT System " + this.file.getPath();
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

   public boolean hasModule() {
      return true;
   }

   public IModule getModule(char[] moduleName) {
      Map modules = (Map)ModulesCache.get(this.file.getPath());
      return modules != null ? (IModule)modules.get(String.valueOf(moduleName)) : null;
   }

   public boolean servesModule(char[] moduleName) {
      return this.getModule(moduleName) != null;
   }
}
