package com.bea.core.repackaged.jdt.internal.compiler.batch;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.CompilationResult;
import com.bea.core.repackaged.jdt.internal.compiler.DefaultErrorHandlingPolicies;
import com.bea.core.repackaged.jdt.internal.compiler.ast.CompilationUnitDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.ast.TypeDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.classfmt.ClassFileReader;
import com.bea.core.repackaged.jdt.internal.compiler.classfmt.ClassFormatException;
import com.bea.core.repackaged.jdt.internal.compiler.env.AccessRuleSet;
import com.bea.core.repackaged.jdt.internal.compiler.env.ICompilationUnit;
import com.bea.core.repackaged.jdt.internal.compiler.env.IModule;
import com.bea.core.repackaged.jdt.internal.compiler.env.NameEnvironmentAnswer;
import com.bea.core.repackaged.jdt.internal.compiler.impl.CompilerOptions;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeConstants;
import com.bea.core.repackaged.jdt.internal.compiler.parser.Parser;
import com.bea.core.repackaged.jdt.internal.compiler.parser.ScannerHelper;
import com.bea.core.repackaged.jdt.internal.compiler.problem.DefaultProblemFactory;
import com.bea.core.repackaged.jdt.internal.compiler.problem.ProblemReporter;
import com.bea.core.repackaged.jdt.internal.compiler.util.Util;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.jar.Manifest;
import java.util.stream.Stream;

public class ClasspathDirectory extends ClasspathLocation {
   private Hashtable directoryCache;
   private String[] missingPackageHolder = new String[1];
   private int mode;
   private String encoding;
   private Hashtable packageSecondaryTypes = null;
   Map options;

   ClasspathDirectory(File directory, String encoding, int mode, AccessRuleSet accessRuleSet, String destinationPath, Map options) {
      super(accessRuleSet, destinationPath);
      this.mode = mode;
      this.options = options;

      try {
         this.path = directory.getCanonicalPath();
      } catch (IOException var7) {
         this.path = directory.getAbsolutePath();
      }

      if (!this.path.endsWith(File.separator)) {
         this.path = this.path + File.separator;
      }

      this.directoryCache = new Hashtable(11);
      this.encoding = encoding;
   }

   String[] directoryList(String qualifiedPackageName) {
      String[] dirList = (String[])this.directoryCache.get(qualifiedPackageName);
      if (dirList == this.missingPackageHolder) {
         return null;
      } else if (dirList != null) {
         return dirList;
      } else {
         File dir = new File(this.path + qualifiedPackageName);
         if (dir.isDirectory()) {
            label49: {
               int index = qualifiedPackageName.length();
               int last = qualifiedPackageName.lastIndexOf(File.separatorChar);

               do {
                  --index;
               } while(index > last && !ScannerHelper.isUpperCase(qualifiedPackageName.charAt(index)));

               if (index > last) {
                  if (last == -1) {
                     if (!this.doesFileExist(qualifiedPackageName, Util.EMPTY_STRING)) {
                        break label49;
                     }
                  } else {
                     String packageName = qualifiedPackageName.substring(last + 1);
                     String parentPackage = qualifiedPackageName.substring(0, last);
                     if (!this.doesFileExist(packageName, parentPackage)) {
                        break label49;
                     }
                  }
               }

               if ((dirList = dir.list()) == null) {
                  dirList = CharOperation.NO_STRINGS;
               }

               this.directoryCache.put(qualifiedPackageName, dirList);
               return dirList;
            }
         }

         this.directoryCache.put(qualifiedPackageName, this.missingPackageHolder);
         return null;
      }
   }

   boolean doesFileExist(String fileName, String qualifiedPackageName) {
      String[] dirList = this.directoryList(qualifiedPackageName);
      if (dirList == null) {
         return false;
      } else {
         int i = dirList.length;

         do {
            --i;
            if (i < 0) {
               return false;
            }
         } while(!fileName.equals(dirList[i]));

         return true;
      }
   }

   public List fetchLinkedJars(FileSystem.ClasspathSectionProblemReporter problemReporter) {
      return null;
   }

   private NameEnvironmentAnswer findClassInternal(char[] typeName, String qualifiedPackageName, String qualifiedBinaryFileName, boolean asBinaryOnly) {
      if (!this.isPackage(qualifiedPackageName, (String)null)) {
         return null;
      } else {
         String fileName = new String(typeName);
         boolean binaryExists = (this.mode & 2) != 0 && this.doesFileExist(fileName + ".class", qualifiedPackageName);
         boolean sourceExists = (this.mode & 1) != 0 && this.doesFileExist(fileName + ".java", qualifiedPackageName);
         if (sourceExists && !asBinaryOnly) {
            String fullSourcePath = this.path + qualifiedBinaryFileName.substring(0, qualifiedBinaryFileName.length() - 6) + ".java";
            CompilationUnit unit = new CompilationUnit((char[])null, fullSourcePath, this.encoding, this.destinationPath);
            unit.module = this.module == null ? null : this.module.name();
            if (!binaryExists) {
               return new NameEnvironmentAnswer(unit, this.fetchAccessRestriction(qualifiedBinaryFileName));
            }

            String fullBinaryPath = this.path + qualifiedBinaryFileName;
            long binaryModified = (new File(fullBinaryPath)).lastModified();
            long sourceModified = (new File(fullSourcePath)).lastModified();
            if (sourceModified > binaryModified) {
               return new NameEnvironmentAnswer(unit, this.fetchAccessRestriction(qualifiedBinaryFileName));
            }
         }

         if (binaryExists) {
            try {
               ClassFileReader reader = ClassFileReader.read(this.path + qualifiedBinaryFileName);
               String typeSearched = qualifiedPackageName.length() > 0 ? qualifiedPackageName.replace(File.separatorChar, '/') + "/" + fileName : fileName;
               if (!CharOperation.equals(reader.getName(), typeSearched.toCharArray())) {
                  reader = null;
               }

               if (reader != null) {
                  char[] modName = reader.moduleName != null ? reader.moduleName : (this.module != null ? this.module.name() : null);
                  return new NameEnvironmentAnswer(reader, this.fetchAccessRestriction(qualifiedBinaryFileName), modName);
               }
            } catch (IOException var15) {
            } catch (ClassFormatException var16) {
            }
         }

         return null;
      }
   }

   public NameEnvironmentAnswer findSecondaryInClass(char[] typeName, String qualifiedPackageName, String qualifiedBinaryFileName) {
      if (CharOperation.equals(TypeConstants.PACKAGE_INFO_NAME, typeName)) {
         return null;
      } else {
         String typeNameString = new String(typeName);
         String moduleName = this.module != null ? String.valueOf(this.module.name()) : null;
         boolean prereqs = this.options != null && this.isPackage(qualifiedPackageName, moduleName) && (this.mode & 1) != 0 && this.doesFileExist(typeNameString + ".java", qualifiedPackageName);
         return prereqs ? null : this.findSourceSecondaryType(typeNameString, qualifiedPackageName, qualifiedBinaryFileName);
      }
   }

   public boolean hasAnnotationFileFor(String qualifiedTypeName) {
      int pos = qualifiedTypeName.lastIndexOf(47);
      if (pos != -1 && pos + 1 < qualifiedTypeName.length()) {
         String fileName = qualifiedTypeName.substring(pos + 1) + ".eea";
         return this.doesFileExist(fileName, qualifiedTypeName.substring(0, pos));
      } else {
         return false;
      }
   }

   public NameEnvironmentAnswer findClass(char[] typeName, String qualifiedPackageName, String moduleName, String qualifiedBinaryFileName) {
      return this.findClass(typeName, qualifiedPackageName, moduleName, qualifiedBinaryFileName, false);
   }

   public NameEnvironmentAnswer findClass(char[] typeName, String qualifiedPackageName, String moduleName, String qualifiedBinaryFileName, boolean asBinaryOnly) {
      return File.separatorChar == '/' ? this.findClassInternal(typeName, qualifiedPackageName, qualifiedBinaryFileName, asBinaryOnly) : this.findClassInternal(typeName, qualifiedPackageName.replace('/', File.separatorChar), qualifiedBinaryFileName.replace('/', File.separatorChar), asBinaryOnly);
   }

   private Hashtable getSecondaryTypes(String qualifiedPackageName) {
      Hashtable packageEntry = new Hashtable();
      String[] dirList = (String[])this.directoryCache.get(qualifiedPackageName);
      if (dirList != this.missingPackageHolder && dirList != null) {
         File dir = new File(this.path + qualifiedPackageName);
         File[] listFiles = dir.isDirectory() ? dir.listFiles() : null;
         if (listFiles == null) {
            return packageEntry;
         } else {
            int i = 0;

            for(int l = listFiles.length; i < l; ++i) {
               File f = listFiles[i];
               if (!f.isDirectory()) {
                  String s = f.getAbsolutePath();
                  if (s != null && (s.endsWith(".java") || s.endsWith(".JAVA"))) {
                     CompilationUnit cu = new CompilationUnit((char[])null, s, this.encoding, this.destinationPath);
                     CompilationResult compilationResult = new CompilationResult(s.toCharArray(), 1, 1, 10);
                     ProblemReporter problemReporter = new ProblemReporter(DefaultErrorHandlingPolicies.proceedWithAllProblems(), new CompilerOptions(this.options), new DefaultProblemFactory());
                     Parser parser = new Parser(problemReporter, false);
                     parser.reportSyntaxErrorIsRequired = false;
                     CompilationUnitDeclaration unit = parser.parse((ICompilationUnit)cu, (CompilationResult)compilationResult);
                     TypeDeclaration[] types = unit != null ? unit.types : null;
                     if (types != null) {
                        int j = 0;

                        for(int k = types.length; j < k; ++j) {
                           TypeDeclaration type = types[j];
                           char[] name = type.isSecondary() ? type.name : null;
                           if (name != null) {
                              packageEntry.put(new String(name), s);
                           }
                        }
                     }
                  }
               }
            }

            return packageEntry;
         }
      } else {
         return packageEntry;
      }
   }

   private NameEnvironmentAnswer findSourceSecondaryType(String typeName, String qualifiedPackageName, String qualifiedBinaryFileName) {
      if (this.packageSecondaryTypes == null) {
         this.packageSecondaryTypes = new Hashtable();
      }

      Hashtable packageEntry = (Hashtable)this.packageSecondaryTypes.get(qualifiedPackageName);
      if (packageEntry == null) {
         packageEntry = this.getSecondaryTypes(qualifiedPackageName);
         this.packageSecondaryTypes.put(qualifiedPackageName, packageEntry);
      }

      String fileName = (String)packageEntry.get(typeName);
      return fileName != null ? new NameEnvironmentAnswer(new CompilationUnit((char[])null, fileName, this.encoding, this.destinationPath), this.fetchAccessRestriction(qualifiedBinaryFileName)) : null;
   }

   public char[][][] findTypeNames(String qualifiedPackageName, String moduleName) {
      if (!this.isPackage(qualifiedPackageName, moduleName)) {
         return null;
      } else {
         File dir = new File(this.path + qualifiedPackageName);
         if (dir.exists() && dir.isDirectory()) {
            String[] listFiles = dir.list(new FilenameFilter() {
               public boolean accept(File directory1, String name) {
                  String fileName = name.toLowerCase();
                  return fileName.endsWith(".class") || fileName.endsWith(".java");
               }
            });
            int length;
            if (listFiles != null && (length = listFiles.length) != 0) {
               Set secondary = this.getSecondaryTypes(qualifiedPackageName).keySet();
               char[][][] result = new char[length + secondary.size()][][];
               char[][] packageName = CharOperation.splitOn(File.separatorChar, qualifiedPackageName.toCharArray());

               int idx;
               String type;
               for(idx = 0; idx < length; ++idx) {
                  type = listFiles[idx];
                  int indexOfLastDot = type.indexOf(46);
                  String typeName = indexOfLastDot > 0 ? type.substring(0, indexOfLastDot) : type;
                  result[idx] = CharOperation.arrayConcat(packageName, typeName.toCharArray());
               }

               if (secondary.size() > 0) {
                  idx = length;

                  for(Iterator var13 = secondary.iterator(); var13.hasNext(); result[idx++] = CharOperation.arrayConcat(packageName, type.toCharArray())) {
                     type = (String)var13.next();
                  }
               }

               return result;
            } else {
               return null;
            }
         } else {
            return null;
         }
      }
   }

   public void initialize() throws IOException {
   }

   public char[][] getModulesDeclaringPackage(String qualifiedPackageName, String moduleName) {
      String qp2 = File.separatorChar == '/' ? qualifiedPackageName : qualifiedPackageName.replace('/', File.separatorChar);
      return this.singletonModuleNameIf(this.directoryList(qp2) != null);
   }

   public boolean hasCompilationUnit(String qualifiedPackageName, String moduleName) {
      String qp2 = File.separatorChar == '/' ? qualifiedPackageName : qualifiedPackageName.replace('/', File.separatorChar);
      String[] dirList = this.directoryList(qp2);
      if (dirList != null) {
         String[] var8 = dirList;
         int var7 = dirList.length;

         for(int var6 = 0; var6 < var7; ++var6) {
            String entry = var8[var6];
            String entryLC = entry.toLowerCase();
            if (entryLC.endsWith(".java") || entryLC.endsWith(".class")) {
               return true;
            }
         }
      }

      return false;
   }

   public boolean hasCUDeclaringPackage(String qualifiedPackageName, Function pkgNameExtractor) {
      String qp2 = File.separatorChar == '/' ? qualifiedPackageName : qualifiedPackageName.replace('/', File.separatorChar);
      return Stream.of(this.directoryList(qp2)).anyMatch((entry) -> {
         String entryLC = entry.toLowerCase();
         boolean hasDeclaration = false;
         String fullPath = this.path + qp2 + "/" + entry;
         String pkgName = null;
         if (entryLC.endsWith(".class")) {
            return true;
         } else {
            if (entryLC.endsWith(".java")) {
               CompilationUnit cu = new CompilationUnit((char[])null, fullPath, this.encoding);
               pkgName = (String)pkgNameExtractor.apply(cu);
            }

            if (pkgName != null && pkgName.equals(qp2.replace(File.separatorChar, '.'))) {
               hasDeclaration = true;
            }

            return hasDeclaration;
         }
      });
   }

   public void reset() {
      super.reset();
      this.directoryCache = new Hashtable(11);
   }

   public String toString() {
      return "ClasspathDirectory " + this.path;
   }

   public char[] normalizedPath() {
      if (this.normalizedPath == null) {
         this.normalizedPath = this.path.toCharArray();
         if (File.separatorChar == '\\') {
            CharOperation.replace(this.normalizedPath, '\\', '/');
         }
      }

      return this.normalizedPath;
   }

   public String getPath() {
      return this.path;
   }

   public int getMode() {
      return this.mode;
   }

   public IModule getModule() {
      return this.isAutoModule && this.module == null ? (this.module = IModule.createAutomatic(this.path, false, (Manifest)null)) : this.module;
   }
}
