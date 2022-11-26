package com.bea.core.repackaged.jdt.internal.compiler.batch;

import com.bea.core.repackaged.jdt.internal.compiler.classfmt.ClassFileReader;
import com.bea.core.repackaged.jdt.internal.compiler.classfmt.ClassFormatException;
import com.bea.core.repackaged.jdt.internal.compiler.classfmt.ExternalAnnotationDecorator;
import com.bea.core.repackaged.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;
import com.bea.core.repackaged.jdt.internal.compiler.env.AccessRestriction;
import com.bea.core.repackaged.jdt.internal.compiler.env.AccessRuleSet;
import com.bea.core.repackaged.jdt.internal.compiler.env.IBinaryType;
import com.bea.core.repackaged.jdt.internal.compiler.env.NameEnvironmentAnswer;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BinaryTypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.util.SuffixConstants;
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
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.zip.ZipEntry;

public class ClasspathMultiReleaseJar extends ClasspathJar {
   private java.nio.file.FileSystem fs = null;
   Path releasePath = null;
   String compliance = null;

   public ClasspathMultiReleaseJar(File file, boolean closeZipFileAtEnd, AccessRuleSet accessRuleSet, String destinationPath, String compliance) {
      super(file, closeZipFileAtEnd, accessRuleSet, destinationPath);
      this.compliance = compliance;
   }

   public void initialize() throws IOException {
      super.initialize();
      URI t = this.file.toURI();
      if (this.file.exists()) {
         URI uri = URI.create("jar:file:" + t.getRawPath());

         try {
            this.fs = FileSystems.getFileSystem(uri);
         } catch (FileSystemNotFoundException var5) {
         }

         if (this.fs == null) {
            HashMap env = new HashMap();

            try {
               this.fs = FileSystems.newFileSystem(uri, env);
            } catch (IOException var4) {
            }
         }

         this.releasePath = this.fs.getPath("/", "META-INF", "versions", this.compliance);
         if (!Files.exists(this.releasePath, new LinkOption[0])) {
            this.releasePath = null;
         }
      }

   }

   public synchronized char[][] getModulesDeclaringPackage(String qualifiedPackageName, String moduleName) {
      if (this.releasePath == null) {
         return super.getModulesDeclaringPackage(qualifiedPackageName, moduleName);
      } else if (this.packageCache != null) {
         return this.singletonModuleNameIf(this.packageCache.contains(qualifiedPackageName));
      } else {
         this.packageCache = new HashSet(41);
         this.packageCache.add(Util.EMPTY_STRING);
         Enumeration e = this.zipFile.entries();

         String fileName;
         while(e.hasMoreElements()) {
            fileName = ((ZipEntry)e.nextElement()).getName();
            this.addToPackageCache(fileName, false);
         }

         try {
            if (this.releasePath != null && Files.exists(this.releasePath, new LinkOption[0])) {
               Throwable var17 = null;
               fileName = null;

               try {
                  DirectoryStream stream = Files.newDirectoryStream(this.releasePath);

                  try {
                     Iterator var7 = stream.iterator();

                     while(var7.hasNext()) {
                        Path subdir = (Path)var7.next();
                        Files.walkFileTree(subdir, new FileVisitor() {
                           public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                              return FileVisitResult.CONTINUE;
                           }

                           public FileVisitResult visitFile(Path f, BasicFileAttributes attrs) throws IOException {
                              Path p = ClasspathMultiReleaseJar.this.releasePath.relativize(f);
                              ClasspathMultiReleaseJar.this.addToPackageCache(p.toString(), false);
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
                     if (stream != null) {
                        stream.close();
                     }

                  }
               } catch (Throwable var15) {
                  if (var17 == null) {
                     var17 = var15;
                  } else if (var17 != var15) {
                     var17.addSuppressed(var15);
                  }

                  throw var17;
               }
            }
         } catch (Exception var16) {
            var16.printStackTrace();
         }

         return this.singletonModuleNameIf(this.packageCache.contains(qualifiedPackageName));
      }
   }

   public NameEnvironmentAnswer findClass(char[] binaryFileName, String qualifiedPackageName, String moduleName, String qualifiedBinaryFileName, boolean asBinaryOnly) {
      if (!this.isPackage(qualifiedPackageName, moduleName)) {
         return null;
      } else {
         if (this.releasePath != null) {
            try {
               Path p = this.releasePath.resolve(qualifiedBinaryFileName);
               byte[] content = Files.readAllBytes(p);
               IBinaryType reader = null;
               if (content != null) {
                  reader = new ClassFileReader(content, qualifiedBinaryFileName.toCharArray());
               }

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

                  String fileNameWithoutExtension = qualifiedBinaryFileName.substring(0, qualifiedBinaryFileName.length() - SuffixConstants.SUFFIX_CLASS.length);
                  if (this.annotationPaths != null) {
                     label73: {
                        String qualifiedClassName = qualifiedBinaryFileName.substring(0, qualifiedBinaryFileName.length() - "CLASS".length() - 1);
                        Iterator var13 = this.annotationPaths.iterator();

                        while(var13.hasNext()) {
                           String annotationPath = (String)var13.next();

                           try {
                              if (this.annotationZipFile == null) {
                                 this.annotationZipFile = ExternalAnnotationDecorator.getAnnotationZipFile(annotationPath, (ExternalAnnotationDecorator.ZipFileProducer)null);
                              }

                              reader = ExternalAnnotationDecorator.create((IBinaryType)reader, annotationPath, qualifiedClassName, this.annotationZipFile);
                              if (((IBinaryType)reader).getExternalAnnotationStatus() == BinaryTypeBinding.ExternalAnnotationStatus.TYPE_IS_ANNOTATED) {
                                 break label73;
                              }
                           } catch (IOException var14) {
                           }
                        }

                        reader = new ExternalAnnotationDecorator((IBinaryType)reader, (ExternalAnnotationProvider)null);
                     }
                  }

                  if (this.accessRuleSet == null) {
                     return new NameEnvironmentAnswer((IBinaryType)reader, (AccessRestriction)null, modName);
                  }

                  return new NameEnvironmentAnswer((IBinaryType)reader, this.accessRuleSet.getViolatedRestriction(fileNameWithoutExtension.toCharArray()), modName);
               }
            } catch (ClassFormatException | IOException var15) {
            }
         }

         return super.findClass(binaryFileName, qualifiedPackageName, moduleName, qualifiedBinaryFileName, asBinaryOnly);
      }
   }
}
