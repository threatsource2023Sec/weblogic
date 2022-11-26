package com.bea.core.repackaged.jdt.internal.compiler.batch;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.classfmt.ClassFileReader;
import com.bea.core.repackaged.jdt.internal.compiler.classfmt.ClassFormatException;
import com.bea.core.repackaged.jdt.internal.compiler.classfmt.ExternalAnnotationDecorator;
import com.bea.core.repackaged.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;
import com.bea.core.repackaged.jdt.internal.compiler.env.AccessRuleSet;
import com.bea.core.repackaged.jdt.internal.compiler.env.IBinaryType;
import com.bea.core.repackaged.jdt.internal.compiler.env.IModule;
import com.bea.core.repackaged.jdt.internal.compiler.env.NameEnvironmentAnswer;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BinaryTypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.util.Util;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;

public class ClasspathJmod extends ClasspathJar {
   public static char[] CLASSES = "classes".toCharArray();
   public static char[] CLASSES_FOLDER = "classes/".toCharArray();

   public ClasspathJmod(File file, boolean closeZipFileAtEnd, AccessRuleSet accessRuleSet, String destinationPath) {
      super(file, closeZipFileAtEnd, accessRuleSet, destinationPath);
   }

   public List fetchLinkedJars(FileSystem.ClasspathSectionProblemReporter problemReporter) {
      return null;
   }

   public NameEnvironmentAnswer findClass(char[] typeName, String qualifiedPackageName, String moduleName, String qualifiedBinaryFileName, boolean asBinaryOnly) {
      if (!this.isPackage(qualifiedPackageName, moduleName)) {
         return null;
      } else {
         try {
            qualifiedBinaryFileName = new String(CharOperation.append(CLASSES_FOLDER, qualifiedBinaryFileName.toCharArray()));
            IBinaryType reader = ClassFileReader.read(this.zipFile, qualifiedBinaryFileName);
            if (reader != null) {
               char[] modName = this.module == null ? null : this.module.name();
               if (reader instanceof ClassFileReader) {
                  ClassFileReader classReader = (ClassFileReader)reader;
                  if (classReader.moduleName == null) {
                     classReader.moduleName = modName;
                  } else {
                     modName = classReader.moduleName;
                  }
               }

               if (this.annotationPaths != null) {
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
                           return new NameEnvironmentAnswer((IBinaryType)reader, this.fetchAccessRestriction(qualifiedBinaryFileName), modName);
                        }
                     } catch (IOException var11) {
                     }
                  }

                  reader = new ExternalAnnotationDecorator((IBinaryType)reader, (ExternalAnnotationProvider)null);
               }

               return new NameEnvironmentAnswer((IBinaryType)reader, this.fetchAccessRestriction(qualifiedBinaryFileName), modName);
            }
         } catch (ClassFormatException var12) {
         } catch (IOException var13) {
         }

         return null;
      }
   }

   public boolean hasAnnotationFileFor(String qualifiedTypeName) {
      qualifiedTypeName = new String(CharOperation.append(CLASSES_FOLDER, qualifiedTypeName.toCharArray()));
      return this.zipFile.getEntry(qualifiedTypeName + ".eea") != null;
   }

   public char[][][] findTypeNames(String qualifiedPackageName, String moduleName) {
      if (!this.isPackage(qualifiedPackageName, moduleName)) {
         return null;
      } else {
         char[] packageArray = qualifiedPackageName.toCharArray();
         ArrayList answers = new ArrayList();
         Enumeration e = this.zipFile.entries();

         while(e.hasMoreElements()) {
            String fileName = ((ZipEntry)e.nextElement()).getName();
            int first = CharOperation.indexOf(CLASSES_FOLDER, fileName.toCharArray(), false);
            int last = fileName.lastIndexOf(47);
            if (last > 0) {
               String packageName = fileName.substring(first + 1, last);
               if (qualifiedPackageName.equals(packageName)) {
                  int indexOfDot = fileName.lastIndexOf(46);
                  if (indexOfDot != -1) {
                     String typeName = fileName.substring(last + 1, indexOfDot);
                     answers.add(CharOperation.arrayConcat(CharOperation.splitOn('/', packageArray), typeName.toCharArray()));
                  }
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

   public synchronized char[][] getModulesDeclaringPackage(String qualifiedPackageName, String moduleName) {
      if (this.packageCache != null) {
         return this.singletonModuleNameIf(this.packageCache.contains(qualifiedPackageName));
      } else {
         this.packageCache = new HashSet(41);
         this.packageCache.add(Util.EMPTY_STRING);
         Enumeration e = this.zipFile.entries();

         while(e.hasMoreElements()) {
            char[] entryName = ((ZipEntry)e.nextElement()).getName().toCharArray();
            int index = CharOperation.indexOf('/', entryName);
            if (index != -1) {
               char[] folder = CharOperation.subarray((char[])entryName, 0, index);
               if (CharOperation.equals(CLASSES, folder)) {
                  char[] fileName = CharOperation.subarray(entryName, index + 1, entryName.length);
                  this.addToPackageCache(new String(fileName), false);
               }
            }
         }

         return this.singletonModuleNameIf(this.packageCache.contains(qualifiedPackageName));
      }
   }

   public boolean hasCompilationUnit(String qualifiedPackageName, String moduleName) {
      qualifiedPackageName = qualifiedPackageName + '/';
      Enumeration e = this.zipFile.entries();

      while(e.hasMoreElements()) {
         char[] entryName = ((ZipEntry)e.nextElement()).getName().toCharArray();
         int index = CharOperation.indexOf('/', entryName);
         if (index != -1) {
            char[] folder = CharOperation.subarray((char[])entryName, 0, index);
            if (CharOperation.equals(CLASSES, folder)) {
               String fileName = new String(CharOperation.subarray(entryName, index + 1, entryName.length));
               if (fileName.startsWith(qualifiedPackageName) && fileName.length() > qualifiedPackageName.length()) {
                  String tail = fileName.substring(qualifiedPackageName.length());
                  if (tail.indexOf(47) == -1 && tail.toLowerCase().endsWith(".class")) {
                     return true;
                  }
               }
            }
         }
      }

      return false;
   }

   public String toString() {
      return "Classpath for JMod file " + this.file.getPath();
   }

   public IModule getModule() {
      return this.module;
   }
}
