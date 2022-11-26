package com.bea.core.repackaged.jdt.internal.compiler.batch;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.CompilationResult;
import com.bea.core.repackaged.jdt.internal.compiler.DefaultErrorHandlingPolicies;
import com.bea.core.repackaged.jdt.internal.compiler.classfmt.ClassFileReader;
import com.bea.core.repackaged.jdt.internal.compiler.classfmt.ExternalAnnotationDecorator;
import com.bea.core.repackaged.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;
import com.bea.core.repackaged.jdt.internal.compiler.env.AccessRuleSet;
import com.bea.core.repackaged.jdt.internal.compiler.env.IModule;
import com.bea.core.repackaged.jdt.internal.compiler.env.IModuleAwareNameEnvironment;
import com.bea.core.repackaged.jdt.internal.compiler.env.IModulePathEntry;
import com.bea.core.repackaged.jdt.internal.compiler.env.IUpdatableModule;
import com.bea.core.repackaged.jdt.internal.compiler.env.NameEnvironmentAnswer;
import com.bea.core.repackaged.jdt.internal.compiler.impl.CompilerOptions;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ModuleBinding;
import com.bea.core.repackaged.jdt.internal.compiler.parser.Parser;
import com.bea.core.repackaged.jdt.internal.compiler.problem.DefaultProblemFactory;
import com.bea.core.repackaged.jdt.internal.compiler.problem.ProblemReporter;
import com.bea.core.repackaged.jdt.internal.compiler.util.SuffixConstants;
import com.bea.core.repackaged.jdt.internal.compiler.util.Util;
import java.io.File;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.zip.ZipFile;

public class FileSystem implements IModuleAwareNameEnvironment, SuffixConstants {
   public static ArrayList EMPTY_CLASSPATH = new ArrayList();
   protected Classpath[] classpaths;
   protected IModule module;
   Set knownFileNames;
   protected boolean annotationsFromClasspath;
   private static HashMap JRT_CLASSPATH_CACHE = null;
   protected Map moduleLocations;
   Map moduleUpdates;
   static final boolean isJRE12Plus = "12".equals(System.getProperty("java.specification.version"));
   // $FF: synthetic field
   private static int[] $SWITCH_TABLE$org$eclipse$jdt$internal$compiler$env$IModuleAwareNameEnvironment$LookupStrategy;

   public FileSystem(String[] classpathNames, String[] initialFileNames, String encoding) {
      this(classpathNames, initialFileNames, encoding, (Collection)null);
   }

   protected FileSystem(String[] classpathNames, String[] initialFileNames, String encoding, Collection limitModules) {
      this.moduleLocations = new HashMap();
      this.moduleUpdates = new HashMap();
      int classpathSize = classpathNames.length;
      this.classpaths = new Classpath[classpathSize];
      int counter = 0;

      for(int i = 0; i < classpathSize; ++i) {
         Classpath classpath = getClasspath(classpathNames[i], encoding, (AccessRuleSet)null, (Map)null, (String)null);

         try {
            classpath.initialize();
            Iterator var10 = classpath.getModuleNames(limitModules).iterator();

            while(var10.hasNext()) {
               String moduleName = (String)var10.next();
               this.moduleLocations.put(moduleName, classpath);
            }

            this.classpaths[counter++] = classpath;
         } catch (IOException var11) {
         }
      }

      if (counter != classpathSize) {
         System.arraycopy(this.classpaths, 0, this.classpaths = new Classpath[counter], 0, counter);
      }

      this.initializeKnownFileNames(initialFileNames);
   }

   protected FileSystem(Classpath[] paths, String[] initialFileNames, boolean annotationsFromClasspath, Set limitedModules) {
      this.moduleLocations = new HashMap();
      this.moduleUpdates = new HashMap();
      int length = paths.length;
      int counter = 0;
      this.classpaths = new Classpath[length];

      for(int i = 0; i < length; ++i) {
         Classpath classpath = paths[i];

         try {
            classpath.initialize();
            Iterator var10 = classpath.getModuleNames(limitedModules).iterator();

            while(var10.hasNext()) {
               String moduleName = (String)var10.next();
               this.moduleLocations.put(moduleName, classpath);
            }

            this.classpaths[counter++] = classpath;
         } catch (InvalidPathException | IOException var11) {
         }
      }

      if (counter != length) {
         System.arraycopy(this.classpaths, 0, this.classpaths = new Classpath[counter], 0, counter);
      }

      this.initializeModuleLocations(limitedModules);
      this.initializeKnownFileNames(initialFileNames);
      this.annotationsFromClasspath = annotationsFromClasspath;
   }

   private void initializeModuleLocations(Set limitedModules) {
      int var4;
      if (limitedModules == null) {
         Classpath[] var5;
         var4 = (var5 = this.classpaths).length;

         for(int var3 = 0; var3 < var4; ++var3) {
            Classpath c = var5[var3];
            Iterator var7 = c.getModuleNames((Collection)null).iterator();

            while(var7.hasNext()) {
               String moduleName = (String)var7.next();
               this.moduleLocations.put(moduleName, c);
            }
         }
      } else {
         Map moduleMap = new HashMap();
         Classpath[] var13;
         int var12 = (var13 = this.classpaths).length;

         Iterator var8;
         Classpath c;
         String moduleName;
         for(var4 = 0; var4 < var12; ++var4) {
            c = var13[var4];
            var8 = c.getModuleNames((Collection)null).iterator();

            while(var8.hasNext()) {
               moduleName = (String)var8.next();
               moduleMap.put(moduleName, c);
            }
         }

         var12 = (var13 = this.classpaths).length;

         for(var4 = 0; var4 < var12; ++var4) {
            c = var13[var4];
            var8 = c.getModuleNames(limitedModules, (m) -> {
               return this.getModuleFromEnvironment(m.toCharArray());
            }).iterator();

            while(var8.hasNext()) {
               moduleName = (String)var8.next();
               Classpath classpath = (Classpath)moduleMap.get(moduleName);
               this.moduleLocations.put(moduleName, classpath);
            }
         }
      }

   }

   protected FileSystem(Classpath[] paths, String[] initialFileNames, boolean annotationsFromClasspath) {
      this(paths, initialFileNames, annotationsFromClasspath, (Set)null);
   }

   public static Classpath getClasspath(String classpathName, String encoding, AccessRuleSet accessRuleSet) {
      return getClasspath(classpathName, encoding, false, accessRuleSet, (String)null, (Map)null, (String)null);
   }

   public static Classpath getClasspath(String classpathName, String encoding, AccessRuleSet accessRuleSet, Map options, String release) {
      return getClasspath(classpathName, encoding, false, accessRuleSet, (String)null, options, release);
   }

   public static Classpath getJrtClasspath(String jdkHome, String encoding, AccessRuleSet accessRuleSet, Map options) {
      return new ClasspathJrt(new File(convertPathSeparators(jdkHome)), true, accessRuleSet, (String)null);
   }

   public static Classpath getOlderSystemRelease(String jdkHome, String release, AccessRuleSet accessRuleSet) {
      return (Classpath)(isJRE12Plus ? new ClasspathJep247Jdk12(new File(convertPathSeparators(jdkHome)), release, accessRuleSet) : new ClasspathJep247(new File(convertPathSeparators(jdkHome)), release, accessRuleSet));
   }

   public static Classpath getClasspath(String classpathName, String encoding, boolean isSourceOnly, AccessRuleSet accessRuleSet, String destinationPath, Map options, String release) {
      Classpath result = null;
      File file = new File(convertPathSeparators(classpathName));
      if (file.isDirectory()) {
         if (file.exists()) {
            result = new ClasspathDirectory(file, encoding, isSourceOnly ? 1 : 3, accessRuleSet, destinationPath != null && destinationPath != "none" ? convertPathSeparators(destinationPath) : destinationPath, options);
         }
      } else {
         int format = Util.archiveFormat(classpathName);
         if (format == 0) {
            if (isSourceOnly) {
               result = new ClasspathSourceJar(file, true, accessRuleSet, encoding, destinationPath != null && destinationPath != "none" ? convertPathSeparators(destinationPath) : destinationPath);
            } else if (destinationPath == null) {
               if (classpathName.endsWith("jrt-fs.jar")) {
                  if (JRT_CLASSPATH_CACHE == null) {
                     JRT_CLASSPATH_CACHE = new HashMap();
                  } else {
                     result = (Classpath)JRT_CLASSPATH_CACHE.get(file);
                  }

                  if (result == null) {
                     result = new ClasspathJrt(file, true, accessRuleSet, (String)null);

                     try {
                        ((Classpath)result).initialize();
                     } catch (IOException var10) {
                     }

                     JRT_CLASSPATH_CACHE.put(file, result);
                  }
               } else {
                  result = release == null ? new ClasspathJar(file, true, accessRuleSet, (String)null) : new ClasspathMultiReleaseJar(file, true, accessRuleSet, destinationPath, release);
               }
            }
         } else if (format == 1) {
            return new ClasspathJmod(file, true, accessRuleSet, (String)null);
         }
      }

      return (Classpath)result;
   }

   private void initializeKnownFileNames(String[] initialFileNames) {
      if (initialFileNames == null) {
         this.knownFileNames = new HashSet(0);
      } else {
         this.knownFileNames = new HashSet(initialFileNames.length * 2);
         int i = initialFileNames.length;

         while(true) {
            --i;
            if (i < 0) {
               return;
            }

            File compilationUnitFile = new File(initialFileNames[i]);
            char[] fileName = null;

            char[] fileName;
            try {
               fileName = compilationUnitFile.getCanonicalPath().toCharArray();
            } catch (IOException var12) {
               continue;
            }

            char[] matchingPathName = null;
            int lastIndexOf = CharOperation.lastIndexOf('.', fileName);
            if (lastIndexOf != -1) {
               fileName = CharOperation.subarray((char[])fileName, 0, lastIndexOf);
            }

            CharOperation.replace(fileName, '\\', '/');
            boolean globalPathMatches = false;
            int j = 0;

            for(int max = this.classpaths.length; j < max; ++j) {
               char[] matchCandidate = this.classpaths[j].normalizedPath();
               boolean currentPathMatch = false;
               if (this.classpaths[j] instanceof ClasspathDirectory && CharOperation.prefixEquals(matchCandidate, fileName)) {
                  currentPathMatch = true;
                  if (matchingPathName == null) {
                     matchingPathName = matchCandidate;
                  } else if (currentPathMatch) {
                     if (matchCandidate.length > matchingPathName.length) {
                        matchingPathName = matchCandidate;
                     }
                  } else if (!globalPathMatches && matchCandidate.length < matchingPathName.length) {
                     matchingPathName = matchCandidate;
                  }

                  if (currentPathMatch) {
                     globalPathMatches = true;
                  }
               }
            }

            if (matchingPathName == null) {
               this.knownFileNames.add(new String(fileName));
            } else {
               this.knownFileNames.add(new String(CharOperation.subarray(fileName, matchingPathName.length, fileName.length)));
            }

            Object var14 = null;
         }
      }
   }

   public void scanForModules(Parser parser) {
      int i = 0;

      for(int max = this.classpaths.length; i < max; ++i) {
         File file = new File(this.classpaths[i].getPath());
         IModule iModule = ModuleFinder.scanForModule(this.classpaths[i], file, parser, false, (String)null);
         if (iModule != null) {
            this.moduleLocations.put(String.valueOf(iModule.name()), this.classpaths[i]);
         }
      }

   }

   public void cleanup() {
      int i = 0;

      for(int max = this.classpaths.length; i < max; ++i) {
         this.classpaths[i].reset();
      }

   }

   private static String convertPathSeparators(String path) {
      return File.separatorChar == '/' ? path.replace('\\', '/') : path.replace('/', '\\');
   }

   private NameEnvironmentAnswer findClass(String qualifiedTypeName, char[] typeName, boolean asBinaryOnly, char[] moduleName) {
      NameEnvironmentAnswer answer = this.internalFindClass(qualifiedTypeName, typeName, asBinaryOnly, moduleName);
      if (this.annotationsFromClasspath && answer != null && answer.getBinaryType() instanceof ClassFileReader) {
         int i = 0;

         for(int length = this.classpaths.length; i < length; ++i) {
            Classpath classpathEntry = this.classpaths[i];
            if (classpathEntry.hasAnnotationFileFor(qualifiedTypeName)) {
               ZipFile zip = classpathEntry instanceof ClasspathJar ? ((ClasspathJar)classpathEntry).zipFile : null;
               boolean shouldClose = false;

               try {
                  if (zip == null) {
                     zip = ExternalAnnotationDecorator.getAnnotationZipFile(classpathEntry.getPath(), (ExternalAnnotationDecorator.ZipFileProducer)null);
                     shouldClose = true;
                  }

                  answer.setBinaryType(ExternalAnnotationDecorator.create(answer.getBinaryType(), classpathEntry.getPath(), qualifiedTypeName, zip));
                  NameEnvironmentAnswer var12 = answer;
                  return var12;
               } catch (IOException var19) {
               } finally {
                  if (shouldClose && zip != null) {
                     try {
                        zip.close();
                     } catch (IOException var18) {
                     }
                  }

               }
            }
         }

         answer.setBinaryType(new ExternalAnnotationDecorator(answer.getBinaryType(), (ExternalAnnotationProvider)null));
         return answer;
      } else {
         return answer;
      }
   }

   private NameEnvironmentAnswer internalFindClass(String qualifiedTypeName, char[] typeName, boolean asBinaryOnly, char[] moduleName) {
      if (this.knownFileNames.contains(qualifiedTypeName)) {
         return null;
      } else {
         String qualifiedBinaryFileName = qualifiedTypeName + ".class";
         String qualifiedPackageName = qualifiedTypeName.length() == typeName.length ? Util.EMPTY_STRING : qualifiedBinaryFileName.substring(0, qualifiedTypeName.length() - typeName.length - 1);
         IModuleAwareNameEnvironment.LookupStrategy strategy = IModuleAwareNameEnvironment.LookupStrategy.get(moduleName);
         String qp2;
         if (strategy == IModuleAwareNameEnvironment.LookupStrategy.Named) {
            if (this.moduleLocations != null) {
               qp2 = String.valueOf(moduleName);
               Classpath classpath = (Classpath)this.moduleLocations.get(qp2);
               if (classpath != null) {
                  return classpath.findClass(typeName, qualifiedPackageName, qp2, qualifiedBinaryFileName);
               }
            }

            return null;
         } else {
            qp2 = File.separatorChar == '/' ? qualifiedPackageName : qualifiedPackageName.replace('/', File.separatorChar);
            NameEnvironmentAnswer suggestedAnswer = null;
            int i;
            if (qualifiedPackageName == qp2) {
               int i = 0;

               for(i = this.classpaths.length; i < i; ++i) {
                  if (strategy.matches(this.classpaths[i], Classpath::hasModule)) {
                     NameEnvironmentAnswer answer = this.classpaths[i].findClass(typeName, qualifiedPackageName, (String)null, qualifiedBinaryFileName, asBinaryOnly);
                     if (answer != null && (answer.moduleName() == null || this.moduleLocations.containsKey(String.valueOf(answer.moduleName())))) {
                        if (!answer.ignoreIfBetter()) {
                           if (answer.isBetter(suggestedAnswer)) {
                              return answer;
                           }
                        } else if (answer.isBetter(suggestedAnswer)) {
                           suggestedAnswer = answer;
                        }
                     }
                  }
               }
            } else {
               String qb2 = qualifiedBinaryFileName.replace('/', File.separatorChar);
               i = 0;

               for(int length = this.classpaths.length; i < length; ++i) {
                  Classpath p = this.classpaths[i];
                  if (strategy.matches(p, Classpath::hasModule)) {
                     NameEnvironmentAnswer answer = !(p instanceof ClasspathDirectory) ? p.findClass(typeName, qualifiedPackageName, (String)null, qualifiedBinaryFileName, asBinaryOnly) : p.findClass(typeName, qp2, (String)null, qb2, asBinaryOnly);
                     if (answer != null && (answer.moduleName() == null || this.moduleLocations.containsKey(String.valueOf(answer.moduleName())))) {
                        if (!answer.ignoreIfBetter()) {
                           if (answer.isBetter(suggestedAnswer)) {
                              return answer;
                           }
                        } else if (answer.isBetter(suggestedAnswer)) {
                           suggestedAnswer = answer;
                        }
                     }
                  }
               }
            }

            return suggestedAnswer;
         }
      }
   }

   public NameEnvironmentAnswer findType(char[][] compoundName, char[] moduleName) {
      return compoundName != null ? this.findClass(new String(CharOperation.concatWith(compoundName, '/')), compoundName[compoundName.length - 1], false, moduleName) : null;
   }

   public char[][][] findTypeNames(char[][] packageName) {
      char[][][] result = null;
      if (packageName != null) {
         String qualifiedPackageName = new String(CharOperation.concatWith(packageName, '/'));
         String qualifiedPackageName2 = File.separatorChar == '/' ? qualifiedPackageName : qualifiedPackageName.replace('/', File.separatorChar);
         int i;
         int length;
         int answersLength;
         if (qualifiedPackageName == qualifiedPackageName2) {
            i = 0;

            for(length = this.classpaths.length; i < length; ++i) {
               char[][][] answers = this.classpaths[i].findTypeNames(qualifiedPackageName, (String)null);
               if (answers != null) {
                  if (result == null) {
                     result = answers;
                  } else {
                     int resultLength = result.length;
                     answersLength = answers.length;
                     System.arraycopy(result, 0, result = new char[answersLength + resultLength][][], 0, resultLength);
                     System.arraycopy(answers, 0, result, resultLength, answersLength);
                  }
               }
            }
         } else {
            i = 0;

            for(length = this.classpaths.length; i < length; ++i) {
               Classpath p = this.classpaths[i];
               char[][][] answers = !(p instanceof ClasspathDirectory) ? p.findTypeNames(qualifiedPackageName, (String)null) : p.findTypeNames(qualifiedPackageName2, (String)null);
               if (answers != null) {
                  if (result == null) {
                     result = answers;
                  } else {
                     answersLength = result.length;
                     int answersLength = answers.length;
                     System.arraycopy(result, 0, result = new char[answersLength + answersLength][][], 0, answersLength);
                     System.arraycopy(answers, 0, result, answersLength, answersLength);
                  }
               }
            }
         }
      }

      return result;
   }

   public NameEnvironmentAnswer findType(char[] typeName, char[][] packageName, char[] moduleName) {
      return typeName != null ? this.findClass(new String(CharOperation.concatWith(packageName, typeName, '/')), typeName, false, moduleName) : null;
   }

   public char[][] getModulesDeclaringPackage(char[][] parentPackageName, char[] packageName, char[] moduleName) {
      String qualifiedPackageName = new String(CharOperation.concatWith(parentPackageName, packageName, '/'));
      String moduleNameString = String.valueOf(moduleName);
      IModuleAwareNameEnvironment.LookupStrategy strategy = IModuleAwareNameEnvironment.LookupStrategy.get(moduleName);
      if (strategy == IModuleAwareNameEnvironment.LookupStrategy.Named) {
         if (this.moduleLocations != null) {
            Classpath classpath = (Classpath)this.moduleLocations.get(moduleNameString);
            if (classpath != null && classpath.isPackage(qualifiedPackageName, moduleNameString)) {
               return new char[][]{moduleName};
            }
         }

         return null;
      } else {
         char[][] allNames = null;
         Classpath[] var11;
         int var10 = (var11 = this.classpaths).length;

         for(int var9 = 0; var9 < var10; ++var9) {
            Classpath cp = var11[var9];
            if (strategy.matches(cp, Classpath::hasModule)) {
               if (strategy == IModuleAwareNameEnvironment.LookupStrategy.Unnamed) {
                  if (cp.isPackage(qualifiedPackageName, moduleNameString)) {
                     return new char[][]{ModuleBinding.UNNAMED};
                  }
               } else {
                  char[][] declaringModules = cp.getModulesDeclaringPackage(qualifiedPackageName, (String)null);
                  if (declaringModules != null) {
                     if (allNames == null) {
                        allNames = declaringModules;
                     } else {
                        allNames = CharOperation.arrayConcat(allNames, declaringModules);
                     }
                  }
               }
            }
         }

         return allNames;
      }
   }

   private Parser getParser() {
      Map opts = new HashMap();
      opts.put("com.bea.core.repackaged.jdt.core.compiler.source", "9");
      return new Parser(new ProblemReporter(DefaultErrorHandlingPolicies.exitOnFirstError(), new CompilerOptions(opts), new DefaultProblemFactory(Locale.getDefault())), false);
   }

   public boolean hasCompilationUnit(char[][] qualifiedPackageName, char[] moduleName, boolean checkCUs) {
      String qPackageName = String.valueOf(CharOperation.concatWith(qualifiedPackageName, '/'));
      String moduleNameString = String.valueOf(moduleName);
      IModuleAwareNameEnvironment.LookupStrategy strategy = IModuleAwareNameEnvironment.LookupStrategy.get(moduleName);
      Parser parser = checkCUs ? this.getParser() : null;
      Function pkgNameExtractor = (sourceUnit) -> {
         String pkgName = null;
         CompilationResult compilationResult = new CompilationResult(sourceUnit, 0, 0, 1);
         char[][] name = parser.parsePackageDeclaration(sourceUnit.getContents(), compilationResult);
         if (name != null) {
            pkgName = CharOperation.toString(name);
         }

         return pkgName;
      };
      switch (strategy) {
         case Named:
            if (this.moduleLocations != null) {
               Classpath location = (Classpath)this.moduleLocations.get(moduleNameString);
               if (location != null) {
                  return checkCUs ? location.hasCUDeclaringPackage(qPackageName, pkgNameExtractor) : location.hasCompilationUnit(qPackageName, moduleNameString);
               }
            }

            return false;
         default:
            for(int i = 0; i < this.classpaths.length; ++i) {
               Classpath location = this.classpaths[i];
               if (strategy.matches(location, Classpath::hasModule) && location.hasCompilationUnit(qPackageName, moduleNameString)) {
                  return true;
               }
            }

            return false;
      }
   }

   public IModule getModule(char[] name) {
      if (this.module != null && CharOperation.equals(name, this.module.name())) {
         return this.module;
      } else {
         if (this.moduleLocations.containsKey(new String(name))) {
            Classpath[] var5;
            int var4 = (var5 = this.classpaths).length;

            for(int var3 = 0; var3 < var4; ++var3) {
               Classpath classpath = var5[var3];
               IModule mod = classpath.getModule(name);
               if (mod != null) {
                  return mod;
               }
            }
         }

         return null;
      }
   }

   public IModule getModuleFromEnvironment(char[] name) {
      if (this.module != null && CharOperation.equals(name, this.module.name())) {
         return this.module;
      } else {
         Classpath[] var5;
         int var4 = (var5 = this.classpaths).length;

         for(int var3 = 0; var3 < var4; ++var3) {
            Classpath classpath = var5[var3];
            IModule mod = classpath.getModule(name);
            if (mod != null) {
               return mod;
            }
         }

         return null;
      }
   }

   public char[][] getAllAutomaticModules() {
      Set set = new HashSet();
      int i = 0;

      for(int l = this.classpaths.length; i < l; ++i) {
         if (this.classpaths[i].isAutomaticModule()) {
            set.add(this.classpaths[i].getModule().name());
         }
      }

      return (char[][])set.toArray(new char[set.size()][]);
   }

   void addModuleUpdate(String moduleName, Consumer update, IUpdatableModule.UpdateKind kind) {
      IUpdatableModule.UpdatesByKind updates = (IUpdatableModule.UpdatesByKind)this.moduleUpdates.get(moduleName);
      if (updates == null) {
         this.moduleUpdates.put(moduleName, updates = new IUpdatableModule.UpdatesByKind());
      }

      updates.getList(kind, true).add(update);
   }

   public void applyModuleUpdates(IUpdatableModule compilerModule, IUpdatableModule.UpdateKind kind) {
      char[] name = compilerModule.name();
      if (name != ModuleBinding.UNNAMED) {
         IUpdatableModule.UpdatesByKind updates = (IUpdatableModule.UpdatesByKind)this.moduleUpdates.get(String.valueOf(name));
         if (updates != null) {
            Iterator var6 = updates.getList(kind, false).iterator();

            while(var6.hasNext()) {
               Consumer update = (Consumer)var6.next();
               update.accept(compilerModule);
            }
         }
      }

   }

   // $FF: synthetic method
   static int[] $SWITCH_TABLE$org$eclipse$jdt$internal$compiler$env$IModuleAwareNameEnvironment$LookupStrategy() {
      int[] var10000 = $SWITCH_TABLE$org$eclipse$jdt$internal$compiler$env$IModuleAwareNameEnvironment$LookupStrategy;
      if (var10000 != null) {
         return var10000;
      } else {
         int[] var0 = new int[IModuleAwareNameEnvironment.LookupStrategy.values().length];

         try {
            var0[IModuleAwareNameEnvironment.LookupStrategy.Any.ordinal()] = 3;
         } catch (NoSuchFieldError var4) {
         }

         try {
            var0[IModuleAwareNameEnvironment.LookupStrategy.AnyNamed.ordinal()] = 2;
         } catch (NoSuchFieldError var3) {
         }

         try {
            var0[IModuleAwareNameEnvironment.LookupStrategy.Named.ordinal()] = 1;
         } catch (NoSuchFieldError var2) {
         }

         try {
            var0[IModuleAwareNameEnvironment.LookupStrategy.Unnamed.ordinal()] = 4;
         } catch (NoSuchFieldError var1) {
         }

         $SWITCH_TABLE$org$eclipse$jdt$internal$compiler$env$IModuleAwareNameEnvironment$LookupStrategy = var0;
         return var0;
      }
   }

   public interface Classpath extends IModulePathEntry {
      char[][][] findTypeNames(String var1, String var2);

      NameEnvironmentAnswer findClass(char[] var1, String var2, String var3, String var4);

      NameEnvironmentAnswer findClass(char[] var1, String var2, String var3, String var4, boolean var5);

      boolean isPackage(String var1, String var2);

      default boolean hasModule() {
         return this.getModule() != null;
      }

      default boolean hasCUDeclaringPackage(String qualifiedPackageName, Function pkgNameExtractor) {
         return this.hasCompilationUnit(qualifiedPackageName, (String)null);
      }

      List fetchLinkedJars(ClasspathSectionProblemReporter var1);

      void reset();

      char[] normalizedPath();

      String getPath();

      void initialize() throws IOException;

      boolean hasAnnotationFileFor(String var1);

      void acceptModule(IModule var1);

      String getDestinationPath();

      Collection getModuleNames(Collection var1);

      Collection getModuleNames(Collection var1, Function var2);
   }

   public static class ClasspathNormalizer {
      public static ArrayList normalize(ArrayList classpaths) {
         ArrayList normalizedClasspath = new ArrayList();
         HashSet cache = new HashSet();
         Iterator iterator = classpaths.iterator();

         while(iterator.hasNext()) {
            Classpath classpath = (Classpath)iterator.next();
            if (!cache.contains(classpath)) {
               normalizedClasspath.add(classpath);
               cache.add(classpath);
            }
         }

         return normalizedClasspath;
      }
   }

   public interface ClasspathSectionProblemReporter {
      void invalidClasspathSection(String var1);

      void multipleClasspathSections(String var1);
   }
}
