package org.python.google.common.hash;

import java.io.OutputStream;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Iterator;
import javax.annotation.Nullable;
import org.python.google.common.annotations.Beta;
import org.python.google.common.base.Preconditions;

@Beta
public final class Funnels {
   private Funnels() {
   }

   public static Funnel byteArrayFunnel() {
      return Funnels.ByteArrayFunnel.INSTANCE;
   }

   public static Funnel unencodedCharsFunnel() {
      return Funnels.UnencodedCharsFunnel.INSTANCE;
   }

   public static Funnel stringFunnel(Charset charset) {
      return new StringCharsetFunnel(charset);
   }

   public static Funnel integerFunnel() {
      return Funnels.IntegerFunnel.INSTANCE;
   }

   public static Funnel sequentialFunnel(Funnel elementFunnel) {
      return new SequentialFunnel(elementFunnel);
   }

   public static Funnel longFunnel() {
      return Funnels.LongFunnel.INSTANCE;
   }

   public static OutputStream asOutputStream(PrimitiveSink sink) {
      return new SinkAsStream(sink);
   }

   private static class SinkAsStream extends OutputStream {
      final PrimitiveSink sink;

      SinkAsStream(PrimitiveSink sink) {
         this.sink = (PrimitiveSink)Preconditions.checkNotNull(sink);
      }

      public void write(int b) {
         this.sink.putByte((byte)b);
      }

      public void write(byte[] bytes) {
         this.sink.putBytes(bytes);
      }

      public void write(byte[] bytes, int off, int len) {
         this.sink.putBytes(bytes, off, len);
      }

      public String toString() {
         return "Funnels.asOutputStream(" + this.sink + ")";
      }
   }

   private static enum LongFunnel implements Funnel {
      INSTANCE;

      public void funnel(Long from, PrimitiveSink into) {
         into.putLong(from);
      }

      public String toString() {
         return "Funnels.longFunnel()";
      }
   }

   private static class SequentialFunnel implements Funnel, Serializable {
      private final Funnel elementFunnel;

      SequentialFunnel(Funnel elementFunnel) {
         this.elementFunnel = (Funnel)Preconditions.checkNotNull(elementFunnel);
      }

      public void funnel(Iterable from, PrimitiveSink into) {
         Iterator var3 = from.iterator();

         while(var3.hasNext()) {
            Object e = var3.next();
            this.elementFunnel.funnel(e, into);
         }

      }

      public String toString() {
         return "Funnels.sequentialFunnel(" + this.elementFunnel + ")";
      }

      public boolean equals(@Nullable Object o) {
         if (o instanceof SequentialFunnel) {
            SequentialFunnel funnel = (SequentialFunnel)o;
            return this.elementFunnel.equals(funnel.elementFunnel);
         } else {
            return false;
         }
      }

      public int hashCode() {
         return SequentialFunnel.class.hashCode() ^ this.elementFunnel.hashCode();
      }
   }

   private static enum IntegerFunnel implements Funnel {
      INSTANCE;

      public void funnel(Integer from, PrimitiveSink into) {
         into.putInt(from);
      }

      public String toString() {
         return "Funnels.integerFunnel()";
      }
   }

   private static class StringCharsetFunnel implements Funnel, Serializable {
      private final Charset charset;

      StringCharsetFunnel(Charset charset) {
         this.charset = (Charset)Preconditions.checkNotNull(charset);
      }

      public void funnel(CharSequence from, PrimitiveSink into) {
         into.putString(from, this.charset);
      }

      public String toString() {
         return "Funnels.stringFunnel(" + this.charset.name() + ")";
      }

      public boolean equals(@Nullable Object o) {
         if (o instanceof StringCharsetFunnel) {
            StringCharsetFunnel funnel = (StringCharsetFunnel)o;
            return this.charset.equals(funnel.charset);
         } else {
            return false;
         }
      }

      public int hashCode() {
         return StringCharsetFunnel.class.hashCode() ^ this.charset.hashCode();
      }

      Object writeReplace() {
         return new SerializedForm(this.charset);
      }

      private static class SerializedForm implements Serializable {
         private final String charsetCanonicalName;
         private static final long serialVersionUID = 0L;

         SerializedForm(Charset charset) {
            this.charsetCanonicalName = charset.name();
         }

         private Object readResolve() {
            return Funnels.stringFunnel(Charset.forName(this.charsetCanonicalName));
         }
      }
   }

   private static enum UnencodedCharsFunnel implements Funnel {
      INSTANCE;

      public void funnel(CharSequence from, PrimitiveSink into) {
         into.putUnencodedChars(from);
      }

      public String toString() {
         return "Funnels.unencodedCharsFunnel()";
      }
   }

   private static enum ByteArrayFunnel implements Funnel {
      INSTANCE;

      public void funnel(byte[] from, PrimitiveSink into) {
         into.putBytes(from);
      }

      public String toString() {
         return "Funnels.byteArrayFunnel()";
      }
   }
}
