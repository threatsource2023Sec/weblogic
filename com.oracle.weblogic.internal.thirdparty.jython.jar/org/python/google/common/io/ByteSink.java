package org.python.google.common.io;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.base.Preconditions;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@GwtIncompatible
public abstract class ByteSink {
   protected ByteSink() {
   }

   public CharSink asCharSink(Charset charset) {
      return new AsCharSink(charset);
   }

   public abstract OutputStream openStream() throws IOException;

   public OutputStream openBufferedStream() throws IOException {
      OutputStream out = this.openStream();
      return out instanceof BufferedOutputStream ? (BufferedOutputStream)out : new BufferedOutputStream(out);
   }

   public void write(byte[] bytes) throws IOException {
      Preconditions.checkNotNull(bytes);
      Closer closer = Closer.create();

      try {
         OutputStream out = (OutputStream)closer.register(this.openStream());
         out.write(bytes);
         out.flush();
      } catch (Throwable var7) {
         throw closer.rethrow(var7);
      } finally {
         closer.close();
      }

   }

   @CanIgnoreReturnValue
   public long writeFrom(InputStream input) throws IOException {
      Preconditions.checkNotNull(input);
      Closer closer = Closer.create();

      long var6;
      try {
         OutputStream out = (OutputStream)closer.register(this.openStream());
         long written = ByteStreams.copy(input, out);
         out.flush();
         var6 = written;
      } catch (Throwable var11) {
         throw closer.rethrow(var11);
      } finally {
         closer.close();
      }

      return var6;
   }

   private final class AsCharSink extends CharSink {
      private final Charset charset;

      private AsCharSink(Charset charset) {
         this.charset = (Charset)Preconditions.checkNotNull(charset);
      }

      public Writer openStream() throws IOException {
         return new OutputStreamWriter(ByteSink.this.openStream(), this.charset);
      }

      public String toString() {
         return ByteSink.this.toString() + ".asCharSink(" + this.charset + ")";
      }

      // $FF: synthetic method
      AsCharSink(Charset x1, Object x2) {
         this(x1);
      }
   }
}
