package org.python.google.common.io;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.base.Preconditions;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@GwtIncompatible
public abstract class CharSink {
   protected CharSink() {
   }

   public abstract Writer openStream() throws IOException;

   public Writer openBufferedStream() throws IOException {
      Writer writer = this.openStream();
      return writer instanceof BufferedWriter ? (BufferedWriter)writer : new BufferedWriter(writer);
   }

   public void write(CharSequence charSequence) throws IOException {
      Preconditions.checkNotNull(charSequence);
      Closer closer = Closer.create();

      try {
         Writer out = (Writer)closer.register(this.openStream());
         out.append(charSequence);
         out.flush();
      } catch (Throwable var7) {
         throw closer.rethrow(var7);
      } finally {
         closer.close();
      }

   }

   public void writeLines(Iterable lines) throws IOException {
      this.writeLines(lines, System.getProperty("line.separator"));
   }

   public void writeLines(Iterable lines, String lineSeparator) throws IOException {
      Preconditions.checkNotNull(lines);
      Preconditions.checkNotNull(lineSeparator);
      Closer closer = Closer.create();

      try {
         Writer out = (Writer)closer.register(this.openBufferedStream());
         Iterator var5 = lines.iterator();

         while(var5.hasNext()) {
            CharSequence line = (CharSequence)var5.next();
            out.append(line).append(lineSeparator);
         }

         out.flush();
      } catch (Throwable var10) {
         throw closer.rethrow(var10);
      } finally {
         closer.close();
      }
   }

   @CanIgnoreReturnValue
   public long writeFrom(Readable readable) throws IOException {
      Preconditions.checkNotNull(readable);
      Closer closer = Closer.create();

      long var6;
      try {
         Writer out = (Writer)closer.register(this.openStream());
         long written = CharStreams.copy(readable, out);
         out.flush();
         var6 = written;
      } catch (Throwable var11) {
         throw closer.rethrow(var11);
      } finally {
         closer.close();
      }

      return var6;
   }
}
