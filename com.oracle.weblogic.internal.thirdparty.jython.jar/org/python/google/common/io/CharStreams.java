package org.python.google.common.io;

import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.base.Preconditions;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@Beta
@GwtIncompatible
public final class CharStreams {
   static CharBuffer createBuffer() {
      return CharBuffer.allocate(2048);
   }

   private CharStreams() {
   }

   @CanIgnoreReturnValue
   public static long copy(Readable from, Appendable to) throws IOException {
      Preconditions.checkNotNull(from);
      Preconditions.checkNotNull(to);
      CharBuffer buf = createBuffer();
      long total = 0L;

      while(from.read(buf) != -1) {
         buf.flip();
         to.append(buf);
         total += (long)buf.remaining();
         buf.clear();
      }

      return total;
   }

   public static String toString(Readable r) throws IOException {
      return toStringBuilder(r).toString();
   }

   private static StringBuilder toStringBuilder(Readable r) throws IOException {
      StringBuilder sb = new StringBuilder();
      copy(r, sb);
      return sb;
   }

   public static List readLines(Readable r) throws IOException {
      List result = new ArrayList();
      LineReader lineReader = new LineReader(r);

      String line;
      while((line = lineReader.readLine()) != null) {
         result.add(line);
      }

      return result;
   }

   @CanIgnoreReturnValue
   public static Object readLines(Readable readable, LineProcessor processor) throws IOException {
      Preconditions.checkNotNull(readable);
      Preconditions.checkNotNull(processor);
      LineReader lineReader = new LineReader(readable);

      String line;
      while((line = lineReader.readLine()) != null && processor.processLine(line)) {
      }

      return processor.getResult();
   }

   @CanIgnoreReturnValue
   public static long exhaust(Readable readable) throws IOException {
      long total = 0L;
      CharBuffer buf = createBuffer();

      long read;
      while((read = (long)readable.read(buf)) != -1L) {
         total += read;
         buf.clear();
      }

      return total;
   }

   public static void skipFully(Reader reader, long n) throws IOException {
      Preconditions.checkNotNull(reader);

      while(n > 0L) {
         long amt = reader.skip(n);
         if (amt == 0L) {
            throw new EOFException();
         }

         n -= amt;
      }

   }

   public static Writer nullWriter() {
      return CharStreams.NullWriter.INSTANCE;
   }

   public static Writer asWriter(Appendable target) {
      return (Writer)(target instanceof Writer ? (Writer)target : new AppendableWriter(target));
   }

   private static final class NullWriter extends Writer {
      private static final NullWriter INSTANCE = new NullWriter();

      public void write(int c) {
      }

      public void write(char[] cbuf) {
         Preconditions.checkNotNull(cbuf);
      }

      public void write(char[] cbuf, int off, int len) {
         Preconditions.checkPositionIndexes(off, off + len, cbuf.length);
      }

      public void write(String str) {
         Preconditions.checkNotNull(str);
      }

      public void write(String str, int off, int len) {
         Preconditions.checkPositionIndexes(off, off + len, str.length());
      }

      public Writer append(CharSequence csq) {
         Preconditions.checkNotNull(csq);
         return this;
      }

      public Writer append(CharSequence csq, int start, int end) {
         Preconditions.checkPositionIndexes(start, end, csq.length());
         return this;
      }

      public Writer append(char c) {
         return this;
      }

      public void flush() {
      }

      public void close() {
      }

      public String toString() {
         return "CharStreams.nullWriter()";
      }
   }
}
