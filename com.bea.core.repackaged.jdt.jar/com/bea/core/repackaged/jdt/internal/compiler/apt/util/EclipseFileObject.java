package com.bea.core.repackaged.jdt.internal.compiler.apt.util;

import com.bea.core.repackaged.jdt.internal.compiler.classfmt.ClassFileReader;
import com.bea.core.repackaged.jdt.internal.compiler.classfmt.ClassFormatException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.URI;
import java.nio.charset.Charset;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.NestingKind;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.JavaFileObject.Kind;

public class EclipseFileObject extends SimpleJavaFileObject {
   File f;
   private Charset charset;
   private boolean parentsExist;
   // $FF: synthetic field
   private static int[] $SWITCH_TABLE$javax$tools$JavaFileObject$Kind;

   public EclipseFileObject(String className, URI uri, JavaFileObject.Kind kind, Charset charset) {
      super(uri, kind);
      this.f = new File(this.uri);
      this.charset = charset;
      this.parentsExist = false;
   }

   public Modifier getAccessLevel() {
      if (this.getKind() != Kind.CLASS) {
         return null;
      } else {
         ClassFileReader reader = null;

         try {
            reader = ClassFileReader.read(this.f);
         } catch (ClassFormatException var3) {
         } catch (IOException var4) {
         }

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

   public NestingKind getNestingKind() {
      switch (this.kind) {
         case SOURCE:
            return NestingKind.TOP_LEVEL;
         case CLASS:
            ClassFileReader reader = null;

            try {
               reader = ClassFileReader.read(this.f);
            } catch (ClassFormatException var2) {
            } catch (IOException var3) {
            }

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

   public boolean delete() {
      return this.f.delete();
   }

   public boolean equals(Object o) {
      if (!(o instanceof EclipseFileObject)) {
         return false;
      } else {
         EclipseFileObject eclipseFileObject = (EclipseFileObject)o;
         return eclipseFileObject.toUri().equals(this.uri);
      }
   }

   public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
      return Util.getCharContents(this, ignoreEncodingErrors, com.bea.core.repackaged.jdt.internal.compiler.util.Util.getFileByteContent(this.f), this.charset.name());
   }

   public long getLastModified() {
      return this.f.lastModified();
   }

   public String getName() {
      return this.f.getPath();
   }

   public int hashCode() {
      return this.f.hashCode();
   }

   public InputStream openInputStream() throws IOException {
      return new FileInputStream(this.f);
   }

   public OutputStream openOutputStream() throws IOException {
      this.ensureParentDirectoriesExist();
      return new FileOutputStream(this.f);
   }

   public Reader openReader(boolean ignoreEncodingErrors) throws IOException {
      return new FileReader(this.f);
   }

   public Writer openWriter() throws IOException {
      this.ensureParentDirectoriesExist();
      return new FileWriter(this.f);
   }

   public String toString() {
      return this.f.getAbsolutePath();
   }

   private void ensureParentDirectoriesExist() throws IOException {
      if (!this.parentsExist) {
         File parent = this.f.getParentFile();
         if (parent != null && !parent.exists() && !parent.mkdirs() && (!parent.exists() || !parent.isDirectory())) {
            throw new IOException("Unable to create parent directories for " + this.f);
         }

         this.parentsExist = true;
      }

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
