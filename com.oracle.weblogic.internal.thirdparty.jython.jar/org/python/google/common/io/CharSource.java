package org.python.google.common.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.base.Ascii;
import org.python.google.common.base.Optional;
import org.python.google.common.base.Preconditions;
import org.python.google.common.base.Splitter;
import org.python.google.common.collect.AbstractIterator;
import org.python.google.common.collect.ImmutableList;
import org.python.google.common.collect.Lists;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@GwtIncompatible
public abstract class CharSource {
   protected CharSource() {
   }

   @Beta
   public ByteSource asByteSource(Charset charset) {
      return new AsByteSource(charset);
   }

   public abstract Reader openStream() throws IOException;

   public BufferedReader openBufferedStream() throws IOException {
      Reader reader = this.openStream();
      return reader instanceof BufferedReader ? (BufferedReader)reader : new BufferedReader(reader);
   }

   @Beta
   public Optional lengthIfKnown() {
      return Optional.absent();
   }

   @Beta
   public long length() throws IOException {
      Optional lengthIfKnown = this.lengthIfKnown();
      if (lengthIfKnown.isPresent()) {
         return (Long)lengthIfKnown.get();
      } else {
         Closer closer = Closer.create();

         long var4;
         try {
            Reader reader = (Reader)closer.register(this.openStream());
            var4 = this.countBySkipping(reader);
         } catch (Throwable var9) {
            throw closer.rethrow(var9);
         } finally {
            closer.close();
         }

         return var4;
      }
   }

   private long countBySkipping(Reader reader) throws IOException {
      long count;
      long read;
      for(count = 0L; (read = reader.skip(Long.MAX_VALUE)) != 0L; count += read) {
      }

      return count;
   }

   @CanIgnoreReturnValue
   public long copyTo(Appendable appendable) throws IOException {
      Preconditions.checkNotNull(appendable);
      Closer closer = Closer.create();

      long var4;
      try {
         Reader reader = (Reader)closer.register(this.openStream());
         var4 = CharStreams.copy(reader, appendable);
      } catch (Throwable var9) {
         throw closer.rethrow(var9);
      } finally {
         closer.close();
      }

      return var4;
   }

   @CanIgnoreReturnValue
   public long copyTo(CharSink sink) throws IOException {
      Preconditions.checkNotNull(sink);
      Closer closer = Closer.create();

      long var5;
      try {
         Reader reader = (Reader)closer.register(this.openStream());
         Writer writer = (Writer)closer.register(sink.openStream());
         var5 = CharStreams.copy(reader, writer);
      } catch (Throwable var10) {
         throw closer.rethrow(var10);
      } finally {
         closer.close();
      }

      return var5;
   }

   public String read() throws IOException {
      Closer closer = Closer.create();

      String var3;
      try {
         Reader reader = (Reader)closer.register(this.openStream());
         var3 = CharStreams.toString(reader);
      } catch (Throwable var7) {
         throw closer.rethrow(var7);
      } finally {
         closer.close();
      }

      return var3;
   }

   @Nullable
   public String readFirstLine() throws IOException {
      Closer closer = Closer.create();

      String var3;
      try {
         BufferedReader reader = (BufferedReader)closer.register(this.openBufferedStream());
         var3 = reader.readLine();
      } catch (Throwable var7) {
         throw closer.rethrow(var7);
      } finally {
         closer.close();
      }

      return var3;
   }

   public ImmutableList readLines() throws IOException {
      Closer closer = Closer.create();

      try {
         BufferedReader reader = (BufferedReader)closer.register(this.openBufferedStream());
         List result = Lists.newArrayList();

         String line;
         while((line = reader.readLine()) != null) {
            result.add(line);
         }

         ImmutableList var5 = ImmutableList.copyOf((Collection)result);
         return var5;
      } catch (Throwable var9) {
         throw closer.rethrow(var9);
      } finally {
         closer.close();
      }
   }

   @Beta
   @CanIgnoreReturnValue
   public Object readLines(LineProcessor processor) throws IOException {
      Preconditions.checkNotNull(processor);
      Closer closer = Closer.create();

      Object var4;
      try {
         Reader reader = (Reader)closer.register(this.openStream());
         var4 = CharStreams.readLines(reader, processor);
      } catch (Throwable var8) {
         throw closer.rethrow(var8);
      } finally {
         closer.close();
      }

      return var4;
   }

   public boolean isEmpty() throws IOException {
      Optional lengthIfKnown = this.lengthIfKnown();
      if (lengthIfKnown.isPresent() && (Long)lengthIfKnown.get() == 0L) {
         return true;
      } else {
         Closer closer = Closer.create();

         boolean var4;
         try {
            Reader reader = (Reader)closer.register(this.openStream());
            var4 = reader.read() == -1;
         } catch (Throwable var8) {
            throw closer.rethrow(var8);
         } finally {
            closer.close();
         }

         return var4;
      }
   }

   public static CharSource concat(Iterable sources) {
      return new ConcatenatedCharSource(sources);
   }

   public static CharSource concat(Iterator sources) {
      return concat((Iterable)ImmutableList.copyOf(sources));
   }

   public static CharSource concat(CharSource... sources) {
      return concat((Iterable)ImmutableList.copyOf((Object[])sources));
   }

   public static CharSource wrap(CharSequence charSequence) {
      return new CharSequenceCharSource(charSequence);
   }

   public static CharSource empty() {
      return CharSource.EmptyCharSource.INSTANCE;
   }

   private static final class ConcatenatedCharSource extends CharSource {
      private final Iterable sources;

      ConcatenatedCharSource(Iterable sources) {
         this.sources = (Iterable)Preconditions.checkNotNull(sources);
      }

      public Reader openStream() throws IOException {
         return new MultiReader(this.sources.iterator());
      }

      public boolean isEmpty() throws IOException {
         Iterator var1 = this.sources.iterator();

         CharSource source;
         do {
            if (!var1.hasNext()) {
               return true;
            }

            source = (CharSource)var1.next();
         } while(source.isEmpty());

         return false;
      }

      public Optional lengthIfKnown() {
         long result = 0L;

         Optional lengthIfKnown;
         for(Iterator var3 = this.sources.iterator(); var3.hasNext(); result += (Long)lengthIfKnown.get()) {
            CharSource source = (CharSource)var3.next();
            lengthIfKnown = source.lengthIfKnown();
            if (!lengthIfKnown.isPresent()) {
               return Optional.absent();
            }
         }

         return Optional.of(result);
      }

      public long length() throws IOException {
         long result = 0L;

         CharSource source;
         for(Iterator var3 = this.sources.iterator(); var3.hasNext(); result += source.length()) {
            source = (CharSource)var3.next();
         }

         return result;
      }

      public String toString() {
         return "CharSource.concat(" + this.sources + ")";
      }
   }

   private static final class EmptyCharSource extends CharSequenceCharSource {
      private static final EmptyCharSource INSTANCE = new EmptyCharSource();

      private EmptyCharSource() {
         super("");
      }

      public String toString() {
         return "CharSource.empty()";
      }
   }

   private static class CharSequenceCharSource extends CharSource {
      private static final Splitter LINE_SPLITTER = Splitter.onPattern("\r\n|\n|\r");
      private final CharSequence seq;

      protected CharSequenceCharSource(CharSequence seq) {
         this.seq = (CharSequence)Preconditions.checkNotNull(seq);
      }

      public Reader openStream() {
         return new CharSequenceReader(this.seq);
      }

      public String read() {
         return this.seq.toString();
      }

      public boolean isEmpty() {
         return this.seq.length() == 0;
      }

      public long length() {
         return (long)this.seq.length();
      }

      public Optional lengthIfKnown() {
         return Optional.of((long)this.seq.length());
      }

      private Iterator linesIterator() {
         return new AbstractIterator() {
            Iterator lines;

            {
               this.lines = CharSource.CharSequenceCharSource.LINE_SPLITTER.split(CharSequenceCharSource.this.seq).iterator();
            }

            protected String computeNext() {
               if (this.lines.hasNext()) {
                  String next = (String)this.lines.next();
                  if (this.lines.hasNext() || !next.isEmpty()) {
                     return next;
                  }
               }

               return (String)this.endOfData();
            }
         };
      }

      public String readFirstLine() {
         Iterator lines = this.linesIterator();
         return lines.hasNext() ? (String)lines.next() : null;
      }

      public ImmutableList readLines() {
         return ImmutableList.copyOf(this.linesIterator());
      }

      public Object readLines(LineProcessor processor) throws IOException {
         Iterator lines = this.linesIterator();

         while(lines.hasNext() && processor.processLine((String)lines.next())) {
         }

         return processor.getResult();
      }

      public String toString() {
         return "CharSource.wrap(" + Ascii.truncate(this.seq, 30, "...") + ")";
      }
   }

   private final class AsByteSource extends ByteSource {
      final Charset charset;

      AsByteSource(Charset charset) {
         this.charset = (Charset)Preconditions.checkNotNull(charset);
      }

      public CharSource asCharSource(Charset charset) {
         return charset.equals(this.charset) ? CharSource.this : super.asCharSource(charset);
      }

      public InputStream openStream() throws IOException {
         return new ReaderInputStream(CharSource.this.openStream(), this.charset, 8192);
      }

      public String toString() {
         return CharSource.this.toString() + ".asByteSource(" + this.charset + ")";
      }
   }
}
