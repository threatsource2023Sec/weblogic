package com.bea.core.repackaged.jdt.internal.compiler.apt.dispatch;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.batch.CompilationUnit;
import com.bea.core.repackaged.jdt.internal.compiler.classfmt.ClassFileReader;
import com.bea.core.repackaged.jdt.internal.compiler.classfmt.ClassFormatException;
import com.bea.core.repackaged.jdt.internal.compiler.env.IBinaryType;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BinaryTypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ReferenceBinding;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import javax.tools.ForwardingJavaFileObject;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;

public class HookedJavaFileObject extends ForwardingJavaFileObject {
   protected final BatchFilerImpl _filer;
   protected final String _fileName;
   private boolean _closed = false;
   private String _typeName;
   // $FF: synthetic field
   private static int[] $SWITCH_TABLE$javax$tools$JavaFileObject$Kind;

   public HookedJavaFileObject(JavaFileObject fileObject, String fileName, String typeName, BatchFilerImpl filer) {
      super(fileObject);
      this._filer = filer;
      this._fileName = fileName;
      this._typeName = typeName;
   }

   public OutputStream openOutputStream() throws IOException {
      return new ForwardingOutputStream(super.openOutputStream());
   }

   public Writer openWriter() throws IOException {
      return new ForwardingWriter(super.openWriter());
   }

   protected void closed() {
      if (!this._closed) {
         this._closed = true;
         switch (this.getKind()) {
            case SOURCE:
               CompilationUnit unit = new CompilationUnit((char[])null, this._fileName, (String)null);
               this._filer.addNewUnit(unit);
               break;
            case CLASS:
               IBinaryType binaryType = null;

               try {
                  binaryType = ClassFileReader.read(this._fileName);
               } catch (ClassFormatException var6) {
                  ReferenceBinding type = this._filer._env._compiler.lookupEnvironment.getType(CharOperation.splitOn('.', this._typeName.toCharArray()));
                  if (type != null) {
                     this._filer.addNewClassFile(type);
                  }
               } catch (IOException var7) {
               }

               if (binaryType != null) {
                  char[] name = binaryType.getName();
                  ReferenceBinding type = this._filer._env._compiler.lookupEnvironment.getType(CharOperation.splitOn('/', name));
                  if (type != null && type.isValidBinding()) {
                     if (type.isBinaryBinding()) {
                        this._filer.addNewClassFile(type);
                     } else {
                        BinaryTypeBinding binaryBinding = new BinaryTypeBinding(type.getPackage(), binaryType, this._filer._env._compiler.lookupEnvironment, true);
                        if (binaryBinding != null) {
                           this._filer.addNewClassFile(binaryBinding);
                        }
                     }
                  }
               }
            case HTML:
            case OTHER:
         }
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

   private class ForwardingOutputStream extends OutputStream {
      private final OutputStream _os;

      ForwardingOutputStream(OutputStream os) {
         this._os = os;
      }

      public void close() throws IOException {
         this._os.close();
         HookedJavaFileObject.this.closed();
      }

      public void flush() throws IOException {
         this._os.flush();
      }

      public void write(byte[] b, int off, int len) throws IOException {
         this._os.write(b, off, len);
      }

      public void write(byte[] b) throws IOException {
         this._os.write(b);
      }

      public void write(int b) throws IOException {
         this._os.write(b);
      }

      protected Object clone() throws CloneNotSupportedException {
         return HookedJavaFileObject.this.new ForwardingOutputStream(this._os);
      }

      public int hashCode() {
         return this._os.hashCode();
      }

      public boolean equals(Object obj) {
         if (this == obj) {
            return true;
         } else if (obj == null) {
            return false;
         } else if (this.getClass() != obj.getClass()) {
            return false;
         } else {
            ForwardingOutputStream other = (ForwardingOutputStream)obj;
            if (this._os == null) {
               if (other._os != null) {
                  return false;
               }
            } else if (!this._os.equals(other._os)) {
               return false;
            }

            return true;
         }
      }

      public String toString() {
         return "ForwardingOutputStream wrapping " + this._os.toString();
      }
   }

   private class ForwardingWriter extends Writer {
      private final Writer _w;

      ForwardingWriter(Writer w) {
         this._w = w;
      }

      public Writer append(char c) throws IOException {
         return this._w.append(c);
      }

      public Writer append(CharSequence csq, int start, int end) throws IOException {
         return this._w.append(csq, start, end);
      }

      public Writer append(CharSequence csq) throws IOException {
         return this._w.append(csq);
      }

      public void close() throws IOException {
         this._w.close();
         HookedJavaFileObject.this.closed();
      }

      public void flush() throws IOException {
         this._w.flush();
      }

      public void write(char[] cbuf) throws IOException {
         this._w.write(cbuf);
      }

      public void write(int c) throws IOException {
         this._w.write(c);
      }

      public void write(String str, int off, int len) throws IOException {
         this._w.write(str, off, len);
      }

      public void write(String str) throws IOException {
         this._w.write(str);
      }

      public void write(char[] cbuf, int off, int len) throws IOException {
         this._w.write(cbuf, off, len);
      }

      protected Object clone() throws CloneNotSupportedException {
         return HookedJavaFileObject.this.new ForwardingWriter(this._w);
      }

      public int hashCode() {
         return this._w.hashCode();
      }

      public boolean equals(Object obj) {
         if (this == obj) {
            return true;
         } else if (obj == null) {
            return false;
         } else if (this.getClass() != obj.getClass()) {
            return false;
         } else {
            ForwardingWriter other = (ForwardingWriter)obj;
            if (this._w == null) {
               if (other._w != null) {
                  return false;
               }
            } else if (!this._w.equals(other._w)) {
               return false;
            }

            return true;
         }
      }

      public String toString() {
         return "ForwardingWriter wrapping " + this._w.toString();
      }
   }
}
