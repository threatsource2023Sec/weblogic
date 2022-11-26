package com.bea.core.repackaged.jdt.internal.compiler.batch;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.classfmt.ClassFileReader;
import com.bea.core.repackaged.jdt.internal.compiler.classfmt.ClassFormatException;
import com.bea.core.repackaged.jdt.internal.compiler.env.AccessRuleSet;
import com.bea.core.repackaged.jdt.internal.compiler.env.IModule;
import com.bea.core.repackaged.jdt.internal.compiler.env.NameEnvironmentAnswer;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;

public class ClasspathJsr199 extends ClasspathLocation {
   private static final Set fileTypes = new HashSet();
   private JavaFileManager fileManager;
   private JavaFileManager.Location location;
   private FileSystem.Classpath jrt;

   static {
      fileTypes.add(Kind.CLASS);
   }

   public ClasspathJsr199(JavaFileManager file, JavaFileManager.Location location) {
      super((AccessRuleSet)null, (String)null);
      this.fileManager = file;
      this.location = location;
   }

   public ClasspathJsr199(FileSystem.Classpath jrt, JavaFileManager file, JavaFileManager.Location location) {
      super((AccessRuleSet)null, (String)null);
      this.fileManager = file;
      this.jrt = jrt;
      this.location = location;
   }

   public ClasspathJsr199(ClasspathJep247 older, JavaFileManager file, JavaFileManager.Location location) {
      super((AccessRuleSet)null, (String)null);
      this.fileManager = file;
      this.jrt = older;
      this.location = location;
   }

   public List fetchLinkedJars(FileSystem.ClasspathSectionProblemReporter problemReporter) {
      return null;
   }

   public NameEnvironmentAnswer findClass(char[] typeName, String qualifiedPackageName, String moduleName, String aQualifiedBinaryFileName, boolean asBinaryOnly) {
      if (this.jrt != null) {
         return this.jrt.findClass(typeName, qualifiedPackageName, moduleName, aQualifiedBinaryFileName, asBinaryOnly);
      } else {
         String qualifiedBinaryFileName = File.separatorChar == '/' ? aQualifiedBinaryFileName : aQualifiedBinaryFileName.replace(File.separatorChar, '/');

         try {
            int lastDot = qualifiedBinaryFileName.lastIndexOf(46);
            String className = lastDot < 0 ? qualifiedBinaryFileName : qualifiedBinaryFileName.substring(0, lastDot);
            JavaFileObject jfo = null;

            try {
               jfo = this.fileManager.getJavaFileForInput(this.location, className, Kind.CLASS);
            } catch (IOException var24) {
            }

            if (jfo == null) {
               return null;
            }

            Throwable var10 = null;
            Object var11 = null;

            try {
               InputStream inputStream = jfo.openInputStream();

               NameEnvironmentAnswer var10000;
               try {
                  ClassFileReader reader = ClassFileReader.read(inputStream, qualifiedBinaryFileName);
                  if (reader == null) {
                     return null;
                  }

                  var10000 = new NameEnvironmentAnswer(reader, this.fetchAccessRestriction(qualifiedBinaryFileName));
               } finally {
                  if (inputStream != null) {
                     inputStream.close();
                  }

               }

               return var10000;
            } catch (Throwable var26) {
               if (var10 == null) {
                  var10 = var26;
               } else if (var10 != var26) {
                  var10.addSuppressed(var26);
               }

               throw var10;
            }
         } catch (ClassFormatException var27) {
         } catch (IOException var28) {
         }

         return null;
      }
   }

   public char[][][] findTypeNames(String aQualifiedPackageName, String moduleName) {
      if (this.jrt != null) {
         return this.jrt.findTypeNames(aQualifiedPackageName, moduleName);
      } else {
         String qualifiedPackageName = File.separatorChar == '/' ? aQualifiedPackageName : aQualifiedPackageName.replace(File.separatorChar, '/');
         Iterable files = null;

         try {
            files = this.fileManager.list(this.location, qualifiedPackageName, fileTypes, false);
         } catch (IOException var13) {
         }

         if (files == null) {
            return null;
         } else {
            ArrayList answers = new ArrayList();
            char[][] packageName = CharOperation.splitOn(File.separatorChar, qualifiedPackageName.toCharArray());
            Iterator var8 = files.iterator();

            while(var8.hasNext()) {
               JavaFileObject file = (JavaFileObject)var8.next();
               String fileName = file.toUri().getPath();
               int last = fileName.lastIndexOf(47);
               if (last > 0) {
                  int indexOfDot = fileName.lastIndexOf(46);
                  if (indexOfDot != -1) {
                     String typeName = fileName.substring(last + 1, indexOfDot);
                     answers.add(CharOperation.arrayConcat(packageName, typeName.toCharArray()));
                  }
               }
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
   }

   public void initialize() throws IOException {
      if (this.jrt != null) {
         this.jrt.initialize();
      }

   }

   public void acceptModule(IModule mod) {
   }

   public char[][] getModulesDeclaringPackage(String aQualifiedPackageName, String moduleName) {
      if (this.jrt != null) {
         return this.jrt.getModulesDeclaringPackage(aQualifiedPackageName, moduleName);
      } else {
         String qualifiedPackageName = File.separatorChar == '/' ? aQualifiedPackageName : aQualifiedPackageName.replace(File.separatorChar, '/');
         boolean result = false;

         try {
            Iterable files = this.fileManager.list(this.location, qualifiedPackageName, fileTypes, false);
            Iterator f = files.iterator();
            if (f.hasNext()) {
               result = true;
            } else {
               files = this.fileManager.list(this.location, qualifiedPackageName, fileTypes, true);
               f = files.iterator();
               if (f.hasNext()) {
                  result = true;
               }
            }
         } catch (IOException var7) {
         }

         return this.singletonModuleNameIf(result);
      }
   }

   public boolean hasCompilationUnit(String qualifiedPackageName, String moduleName) {
      return this.jrt != null ? this.jrt.hasCompilationUnit(qualifiedPackageName, moduleName) : false;
   }

   public void reset() {
      try {
         super.reset();
         this.fileManager.flush();
      } catch (IOException var1) {
      }

      if (this.jrt != null) {
         this.jrt.reset();
      }

   }

   public String toString() {
      return "Classpath for Jsr 199 JavaFileManager: " + this.location;
   }

   public char[] normalizedPath() {
      if (this.normalizedPath == null) {
         this.normalizedPath = this.path.toCharArray();
      }

      return this.normalizedPath;
   }

   public String getPath() {
      if (this.path == null) {
         this.path = this.location.getName();
      }

      return this.path;
   }

   public int getMode() {
      return 2;
   }

   public boolean hasAnnotationFileFor(String qualifiedTypeName) {
      return false;
   }

   public Collection getModuleNames(Collection limitModules) {
      return (Collection)(this.jrt != null ? this.jrt.getModuleNames(limitModules) : Collections.emptyList());
   }

   public boolean hasModule() {
      return this.jrt != null ? this.jrt.hasModule() : super.hasModule();
   }

   public IModule getModule(char[] name) {
      return this.jrt != null ? this.jrt.getModule(name) : super.getModule(name);
   }

   public NameEnvironmentAnswer findClass(char[] typeName, String qualifiedPackageName, String moduleName, String qualifiedBinaryFileName) {
      return this.findClass(typeName, qualifiedPackageName, moduleName, qualifiedBinaryFileName, false);
   }
}
