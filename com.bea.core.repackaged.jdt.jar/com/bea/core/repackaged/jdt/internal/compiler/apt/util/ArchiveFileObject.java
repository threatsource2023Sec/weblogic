package com.bea.core.repackaged.jdt.internal.compiler.apt.util;

import com.bea.core.repackaged.jdt.internal.compiler.classfmt.ClassFileReader;
import com.bea.core.repackaged.jdt.internal.compiler.classfmt.ClassFormatException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.NestingKind;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;

public class ArchiveFileObject implements JavaFileObject {
   protected String entryName;
   protected File file;
   protected ZipFile zipFile;
   protected Charset charset;
   // $FF: synthetic field
   private static int[] $SWITCH_TABLE$javax$tools$JavaFileObject$Kind;

   public ArchiveFileObject(File file, String entryName, Charset charset) {
      this.entryName = entryName;
      this.file = file;
      this.charset = charset;
   }

   protected void finalize() throws Throwable {
      if (this.zipFile != null) {
         try {
            this.zipFile.close();
         } catch (IOException var1) {
         }
      }

      super.finalize();
   }

   public Modifier getAccessLevel() {
      if (this.getKind() != Kind.CLASS) {
         return null;
      } else {
         ClassFileReader reader = this.getClassReader();
         if (reader == null) {
            return null;
         } else {
            int accessFlags = reader.accessFlags();
            if ((accessFlags & 1) != 0) {
               return Modifier.PUBLIC;
            } else if ((accessFlags & 1024) != 0) {
               return Modifier.ABSTRACT;
            } else {
               return (accessFlags & 16) != 0 ? Modifier.FINAL : null;
            }
         }
      }
   }

   protected ClassFileReader getClassReader() {
      ClassFileReader reader = null;

      try {
         Throwable var2 = null;
         Object var3 = null;

         try {
            ZipFile zip = new ZipFile(this.file);

            try {
               reader = ClassFileReader.read(zip, this.entryName);
            } finally {
               if (zip != null) {
                  zip.close();
               }

            }
         } catch (Throwable var14) {
            if (var2 == null) {
               var2 = var14;
            } else if (var2 != var14) {
               var2.addSuppressed(var14);
            }

            throw var2;
         }
      } catch (ClassFormatException var15) {
      } catch (IOException var16) {
      }

      return reader;
   }

   public JavaFileObject.Kind getKind() {
      String name = this.entryName.toLowerCase();
      if (name.endsWith(Kind.CLASS.extension)) {
         return Kind.CLASS;
      } else if (name.endsWith(Kind.SOURCE.extension)) {
         return Kind.SOURCE;
      } else {
         return name.endsWith(Kind.HTML.extension) ? Kind.HTML : Kind.OTHER;
      }
   }

   public NestingKind getNestingKind() {
      switch (this.getKind()) {
         case SOURCE:
            return NestingKind.TOP_LEVEL;
         case CLASS:
            ClassFileReader reader = this.getClassReader();
            if (reader == null) {
               return null;
            } else if (reader.isAnonymous()) {
               return NestingKind.ANONYMOUS;
            } else if (reader.isLocal()) {
               return NestingKind.LOCAL;
            } else {
               if (reader.isMember()) {
                  return NestingKind.MEMBER;
               }

               return NestingKind.TOP_LEVEL;
            }
         default:
            return null;
      }
   }

   public boolean isNameCompatible(String simpleName, JavaFileObject.Kind kind) {
      return this.entryName.endsWith(simpleName + kind.extension);
   }

   public boolean delete() {
      throw new UnsupportedOperationException();
   }

   public boolean equals(Object o) {
      if (!(o instanceof ArchiveFileObject)) {
         return false;
      } else {
         ArchiveFileObject archiveFileObject = (ArchiveFileObject)o;
         return archiveFileObject.toUri().equals(this.toUri());
      }
   }

   public int hashCode() {
      return this.toUri().hashCode();
   }

   public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
      if (this.getKind() == Kind.SOURCE) {
         Throwable var2 = null;
         Object var3 = null;

         try {
            ZipFile zipFile2 = new ZipFile(this.file);

            CharSequence var10000;
            try {
               ZipEntry zipEntry = zipFile2.getEntry(this.entryName);
               var10000 = Util.getCharContents(this, ignoreEncodingErrors, com.bea.core.repackaged.jdt.internal.compiler.util.Util.getZipEntryByteContent(zipEntry, zipFile2), this.charset.name());
            } finally {
               if (zipFile2 != null) {
                  zipFile2.close();
               }

            }

            return var10000;
         } catch (Throwable var11) {
            if (var2 == null) {
               var2 = var11;
            } else if (var2 != var11) {
               var2.addSuppressed(var11);
            }

            throw var2;
         }
      } else {
         return null;
      }
   }

   public long getLastModified() {
      try {
         Throwable var1 = null;
         Object var2 = null;

         try {
            ZipFile zip = new ZipFile(this.file);

            long var10000;
            try {
               ZipEntry zipEntry = zip.getEntry(this.entryName);
               var10000 = zipEntry.getTime();
            } finally {
               if (zip != null) {
                  zip.close();
               }

            }

            return var10000;
         } catch (Throwable var12) {
            if (var1 == null) {
               var1 = var12;
            } else if (var1 != var12) {
               var1.addSuppressed(var12);
            }

            throw var1;
         }
      } catch (IOException var13) {
         return 0L;
      }
   }

   public String getName() {
      return this.entryName;
   }

   public InputStream openInputStream() throws IOException {
      if (this.zipFile == null) {
         this.zipFile = new ZipFile(this.file);
      }

      ZipEntry zipEntry = this.zipFile.getEntry(this.entryName);
      return this.zipFile.getInputStream(zipEntry);
   }

   public OutputStream openOutputStream() throws IOException {
      throw new UnsupportedOperationException();
   }

   public Reader openReader(boolean ignoreEncodingErrors) throws IOException {
      throw new UnsupportedOperationException();
   }

   public Writer openWriter() throws IOException {
      throw new UnsupportedOperationException();
   }

   public URI toUri() {
      try {
         return new URI("jar:" + this.file.toURI().getPath() + "!" + this.entryName);
      } catch (URISyntaxException var1) {
         return null;
      }
   }

   public String toString() {
      return this.file.getAbsolutePath() + "[" + this.entryName + "]";
   }

   // $FF: synthetic method
   static int[] $SWITCH_TABLE$javax$tools$JavaFileObject$Kind() {
      int[] var10000 = $SWITCH_TABLE$javax$tools$JavaFileObject$Kind;
      if (var10000 != null) {
         return var10000;
      } else {
         int[] var0 = new int[Kind.values().length];

         try {
            var0[Kind.CLASS.ordinal()] = 2;
         } catch (NoSuchFieldError var4) {
         }

         try {
            var0[Kind.HTML.ordinal()] = 3;
         } catch (NoSuchFieldError var3) {
         }

         try {
            var0[Kind.OTHER.ordinal()] = 4;
         } catch (NoSuchFieldError var2) {
         }

         try {
            var0[Kind.SOURCE.ordinal()] = 1;
         } catch (NoSuchFieldError var1) {
         }

         $SWITCH_TABLE$javax$tools$JavaFileObject$Kind = var0;
         return var0;
      }
   }
}
