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
import com.bea.core.repackaged.jdt.internal.compiler.util.ManifestAnalyzer;
import com.bea.core.repackaged.jdt.internal.compiler.util.Util;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ClasspathJar extends ClasspathLocation {
   protected File file;
   protected ZipFile zipFile;
   protected ZipFile annotationZipFile;
   protected boolean closeZipFileAtEnd;
   protected Set packageCache;
   protected List annotationPaths;

   public ClasspathJar(File file, boolean closeZipFileAtEnd, AccessRuleSet accessRuleSet, String destinationPath) {
      super(accessRuleSet, destinationPath);
      this.file = file;
      this.closeZipFileAtEnd = closeZipFileAtEnd;
   }

   public List fetchLinkedJars(FileSystem.ClasspathSectionProblemReporter problemReporter) {
      InputStream inputStream = null;

      try {
         this.initialize();
         ArrayList result = new ArrayList();
         ZipEntry manifest = this.zipFile.getEntry("META-INF/MANIFEST.MF");
         if (manifest != null) {
            inputStream = this.zipFile.getInputStream(manifest);
            ManifestAnalyzer analyzer = new ManifestAnalyzer();
            boolean success = analyzer.analyzeManifestContents(inputStream);
            List calledFileNames = analyzer.getCalledFileNames();
            if (problemReporter != null) {
               if (success && (analyzer.getClasspathSectionsCount() != 1 || calledFileNames != null)) {
                  if (analyzer.getClasspathSectionsCount() > 1) {
                     problemReporter.multipleClasspathSections(this.getPath());
                  }
               } else {
                  problemReporter.invalidClasspathSection(this.getPath());
               }
            }

            if (calledFileNames != null) {
               Iterator calledFilesIterator = calledFileNames.iterator();
               String directoryPath = this.getPath();
               int lastSeparator = directoryPath.lastIndexOf(File.separatorChar);
               directoryPath = directoryPath.substring(0, lastSeparator + 1);

               while(calledFilesIterator.hasNext()) {
                  result.add(new ClasspathJar(new File(directoryPath + (String)calledFilesIterator.next()), this.closeZipFileAtEnd, this.accessRuleSet, this.destinationPath));
               }
            }
         }

         ArrayList var12 = result;
         return var12;
      } catch (IllegalArgumentException | IOException var19) {
      } finally {
         if (inputStream != null) {
            try {
               inputStream.close();
            } catch (IOException var18) {
            }
         }

      }

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
            int last = fileName.lastIndexOf(47);
            if (last > 0) {
               String packageName = fileName.substring(0, last);
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

   public void initialize() throws IOException {
      if (this.zipFile == null) {
         this.zipFile = new ZipFile(this.file);
      }

   }

   void acceptModule(ClassFileReader reader) {
      if (reader != null) {
         this.acceptModule((IModule)reader.getModuleDeclaration());
      }

   }

   void acceptModule(byte[] content) {
      if (content != null) {
         ClassFileReader reader = null;

         try {
            reader = new ClassFileReader(content, "module-info.class".toCharArray());
         } catch (ClassFormatException var4) {
            var4.printStackTrace();
         }

         if (reader != null && reader.getModuleDeclaration() != null) {
            this.acceptModule(reader);
         }

      }
   }

   protected void addToPackageCache(String fileName, boolean endsWithSep) {
      String packageName;
      for(int last = endsWithSep ? fileName.length() : fileName.lastIndexOf(47); last > 0; last = packageName.lastIndexOf(47)) {
         packageName = fileName.substring(0, last);
         if (this.packageCache.contains(packageName)) {
            return;
         }

         this.packageCache.add(packageName);
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
            String fileName = ((ZipEntry)e.nextElement()).getName();
            this.addToPackageCache(fileName, false);
         }

         return this.singletonModuleNameIf(this.packageCache.contains(qualifiedPackageName));
      }
   }

   public boolean hasCompilationUnit(String qualifiedPackageName, String moduleName) {
      qualifiedPackageName = qualifiedPackageName + '/';
      Enumeration e = this.zipFile.entries();

      while(e.hasMoreElements()) {
         String fileName = ((ZipEntry)e.nextElement()).getName();
         if (fileName.startsWith(qualifiedPackageName) && fileName.length() > qualifiedPackageName.length()) {
            String tail = fileName.substring(qualifiedPackageName.length());
            if (tail.indexOf(47) == -1 && tail.toLowerCase().endsWith(".class")) {
               return true;
            }
         }
      }

      return false;
   }

   public void reset() {
      super.reset();
      if (this.closeZipFileAtEnd) {
         if (this.zipFile != null) {
            try {
               this.zipFile.close();
            } catch (IOException var2) {
            }

            this.zipFile = null;
         }

         if (this.annotationZipFile != null) {
            try {
               this.annotationZipFile.close();
            } catch (IOException var1) {
            }

            this.annotationZipFile = null;
         }
      }

      this.packageCache = null;
      this.annotationPaths = null;
   }

   public String toString() {
      return "Classpath for jar file " + this.file.getPath();
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

   public IModule getModule() {
      if (this.isAutoModule && this.module == null) {
         Manifest manifest = null;

         try {
            this.initialize();
            ZipEntry entry = this.zipFile.getEntry("META-INF/MANIFEST.MF");
            if (entry != null) {
               manifest = new Manifest(this.zipFile.getInputStream(entry));
            }
         } catch (IOException var3) {
         }

         return this.module = IModule.createAutomatic(this.file.getName(), true, manifest);
      } else {
         return this.module;
      }
   }
}
