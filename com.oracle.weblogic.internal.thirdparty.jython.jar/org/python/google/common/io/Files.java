package org.python.google.common.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.base.Joiner;
import org.python.google.common.base.Optional;
import org.python.google.common.base.Preconditions;
import org.python.google.common.base.Predicate;
import org.python.google.common.base.Splitter;
import org.python.google.common.collect.ImmutableSet;
import org.python.google.common.collect.Lists;
import org.python.google.common.collect.TreeTraverser;
import org.python.google.common.hash.HashCode;
import org.python.google.common.hash.HashFunction;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@Beta
@GwtIncompatible
public final class Files {
   private static final int TEMP_DIR_ATTEMPTS = 10000;
   private static final TreeTraverser FILE_TREE_TRAVERSER = new TreeTraverser() {
      public Iterable children(File file) {
         if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
               return Collections.unmodifiableList(Arrays.asList(files));
            }
         }

         return Collections.emptyList();
      }

      public String toString() {
         return "Files.fileTreeTraverser()";
      }
   };

   private Files() {
   }

   public static BufferedReader newReader(File file, Charset charset) throws FileNotFoundException {
      Preconditions.checkNotNull(file);
      Preconditions.checkNotNull(charset);
      return new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
   }

   public static BufferedWriter newWriter(File file, Charset charset) throws FileNotFoundException {
      Preconditions.checkNotNull(file);
      Preconditions.checkNotNull(charset);
      return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), charset));
   }

   public static ByteSource asByteSource(File file) {
      return new FileByteSource(file);
   }

   static byte[] readFile(InputStream in, long expectedSize) throws IOException {
      if (expectedSize > 2147483647L) {
         throw new OutOfMemoryError("file is too large to fit in a byte array: " + expectedSize + " bytes");
      } else {
         return expectedSize == 0L ? ByteStreams.toByteArray(in) : ByteStreams.toByteArray(in, (int)expectedSize);
      }
   }

   public static ByteSink asByteSink(File file, FileWriteMode... modes) {
      return new FileByteSink(file, modes);
   }

   public static CharSource asCharSource(File file, Charset charset) {
      return asByteSource(file).asCharSource(charset);
   }

   public static CharSink asCharSink(File file, Charset charset, FileWriteMode... modes) {
      return asByteSink(file, modes).asCharSink(charset);
   }

   public static byte[] toByteArray(File file) throws IOException {
      return asByteSource(file).read();
   }

   /** @deprecated */
   @Deprecated
   public static String toString(File file, Charset charset) throws IOException {
      return asCharSource(file, charset).read();
   }

   public static void write(byte[] from, File to) throws IOException {
      asByteSink(to).write(from);
   }

   public static void copy(File from, OutputStream to) throws IOException {
      asByteSource(from).copyTo(to);
   }

   public static void copy(File from, File to) throws IOException {
      Preconditions.checkArgument(!from.equals(to), "Source %s and destination %s must be different", from, to);
      asByteSource(from).copyTo(asByteSink(to));
   }

   /** @deprecated */
   @Deprecated
   public static void write(CharSequence from, File to, Charset charset) throws IOException {
      asCharSink(to, charset).write(from);
   }

   /** @deprecated */
   @Deprecated
   public static void append(CharSequence from, File to, Charset charset) throws IOException {
      asCharSink(to, charset, FileWriteMode.APPEND).write(from);
   }

   /** @deprecated */
   @Deprecated
   public static void copy(File from, Charset charset, Appendable to) throws IOException {
      asCharSource(from, charset).copyTo(to);
   }

   public static boolean equal(File file1, File file2) throws IOException {
      Preconditions.checkNotNull(file1);
      Preconditions.checkNotNull(file2);
      if (file1 != file2 && !file1.equals(file2)) {
         long len1 = file1.length();
         long len2 = file2.length();
         return len1 != 0L && len2 != 0L && len1 != len2 ? false : asByteSource(file1).contentEquals(asByteSource(file2));
      } else {
         return true;
      }
   }

   public static File createTempDir() {
      File baseDir = new File(System.getProperty("java.io.tmpdir"));
      String baseName = System.currentTimeMillis() + "-";

      for(int counter = 0; counter < 10000; ++counter) {
         File tempDir = new File(baseDir, baseName + counter);
         if (tempDir.mkdir()) {
            return tempDir;
         }
      }

      throw new IllegalStateException("Failed to create directory within 10000 attempts (tried " + baseName + "0 to " + baseName + 9999 + ')');
   }

   public static void touch(File file) throws IOException {
      Preconditions.checkNotNull(file);
      if (!file.createNewFile() && !file.setLastModified(System.currentTimeMillis())) {
         throw new IOException("Unable to update modification time of " + file);
      }
   }

   public static void createParentDirs(File file) throws IOException {
      Preconditions.checkNotNull(file);
      File parent = file.getCanonicalFile().getParentFile();
      if (parent != null) {
         parent.mkdirs();
         if (!parent.isDirectory()) {
            throw new IOException("Unable to create parent directories of " + file);
         }
      }
   }

   public static void move(File from, File to) throws IOException {
      Preconditions.checkNotNull(from);
      Preconditions.checkNotNull(to);
      Preconditions.checkArgument(!from.equals(to), "Source %s and destination %s must be different", from, to);
      if (!from.renameTo(to)) {
         copy(from, to);
         if (!from.delete()) {
            if (!to.delete()) {
               throw new IOException("Unable to delete " + to);
            }

            throw new IOException("Unable to delete " + from);
         }
      }

   }

   /** @deprecated */
   @Deprecated
   public static String readFirstLine(File file, Charset charset) throws IOException {
      return asCharSource(file, charset).readFirstLine();
   }

   public static List readLines(File file, Charset charset) throws IOException {
      return (List)asCharSource(file, charset).readLines(new LineProcessor() {
         final List result = Lists.newArrayList();

         public boolean processLine(String line) {
            this.result.add(line);
            return true;
         }

         public List getResult() {
            return this.result;
         }
      });
   }

   /** @deprecated */
   @Deprecated
   @CanIgnoreReturnValue
   public static Object readLines(File file, Charset charset, LineProcessor callback) throws IOException {
      return asCharSource(file, charset).readLines(callback);
   }

   /** @deprecated */
   @Deprecated
   @CanIgnoreReturnValue
   public static Object readBytes(File file, ByteProcessor processor) throws IOException {
      return asByteSource(file).read(processor);
   }

   /** @deprecated */
   @Deprecated
   public static HashCode hash(File file, HashFunction hashFunction) throws IOException {
      return asByteSource(file).hash(hashFunction);
   }

   public static MappedByteBuffer map(File file) throws IOException {
      Preconditions.checkNotNull(file);
      return map(file, MapMode.READ_ONLY);
   }

   public static MappedByteBuffer map(File file, FileChannel.MapMode mode) throws IOException {
      Preconditions.checkNotNull(file);
      Preconditions.checkNotNull(mode);
      if (!file.exists()) {
         throw new FileNotFoundException(file.toString());
      } else {
         return map(file, mode, file.length());
      }
   }

   public static MappedByteBuffer map(File file, FileChannel.MapMode mode, long size) throws FileNotFoundException, IOException {
      Preconditions.checkNotNull(file);
      Preconditions.checkNotNull(mode);
      Closer closer = Closer.create();

      MappedByteBuffer var6;
      try {
         RandomAccessFile raf = (RandomAccessFile)closer.register(new RandomAccessFile(file, mode == MapMode.READ_ONLY ? "r" : "rw"));
         var6 = map(raf, mode, size);
      } catch (Throwable var10) {
         throw closer.rethrow(var10);
      } finally {
         closer.close();
      }

      return var6;
   }

   private static MappedByteBuffer map(RandomAccessFile raf, FileChannel.MapMode mode, long size) throws IOException {
      Closer closer = Closer.create();

      MappedByteBuffer var6;
      try {
         FileChannel channel = (FileChannel)closer.register(raf.getChannel());
         var6 = channel.map(mode, 0L, size);
      } catch (Throwable var10) {
         throw closer.rethrow(var10);
      } finally {
         closer.close();
      }

      return var6;
   }

   public static String simplifyPath(String pathname) {
      Preconditions.checkNotNull(pathname);
      if (pathname.length() == 0) {
         return ".";
      } else {
         Iterable components = Splitter.on('/').omitEmptyStrings().split(pathname);
         List path = new ArrayList();
         Iterator var3 = components.iterator();

         while(true) {
            while(true) {
               while(true) {
                  String component;
                  do {
                     if (!var3.hasNext()) {
                        String result = Joiner.on('/').join((Iterable)path);
                        if (pathname.charAt(0) == '/') {
                           result = "/" + result;
                        }

                        while(result.startsWith("/../")) {
                           result = result.substring(3);
                        }

                        if (result.equals("/..")) {
                           result = "/";
                        } else if ("".equals(result)) {
                           result = ".";
                        }

                        return result;
                     }

                     component = (String)var3.next();
                  } while(component.equals("."));

                  if (component.equals("..")) {
                     if (path.size() > 0 && !((String)path.get(path.size() - 1)).equals("..")) {
                        path.remove(path.size() - 1);
                     } else {
                        path.add("..");
                     }
                  } else {
                     path.add(component);
                  }
               }
            }
         }
      }
   }

   public static String getFileExtension(String fullName) {
      Preconditions.checkNotNull(fullName);
      String fileName = (new File(fullName)).getName();
      int dotIndex = fileName.lastIndexOf(46);
      return dotIndex == -1 ? "" : fileName.substring(dotIndex + 1);
   }

   public static String getNameWithoutExtension(String file) {
      Preconditions.checkNotNull(file);
      String fileName = (new File(file)).getName();
      int dotIndex = fileName.lastIndexOf(46);
      return dotIndex == -1 ? fileName : fileName.substring(0, dotIndex);
   }

   public static TreeTraverser fileTreeTraverser() {
      return FILE_TREE_TRAVERSER;
   }

   public static Predicate isDirectory() {
      return Files.FilePredicate.IS_DIRECTORY;
   }

   public static Predicate isFile() {
      return Files.FilePredicate.IS_FILE;
   }

   private static enum FilePredicate implements Predicate {
      IS_DIRECTORY {
         public boolean apply(File file) {
            return file.isDirectory();
         }

         public String toString() {
            return "Files.isDirectory()";
         }
      },
      IS_FILE {
         public boolean apply(File file) {
            return file.isFile();
         }

         public String toString() {
            return "Files.isFile()";
         }
      };

      private FilePredicate() {
      }

      // $FF: synthetic method
      FilePredicate(Object x2) {
         this();
      }
   }

   private static final class FileByteSink extends ByteSink {
      private final File file;
      private final ImmutableSet modes;

      private FileByteSink(File file, FileWriteMode... modes) {
         this.file = (File)Preconditions.checkNotNull(file);
         this.modes = ImmutableSet.copyOf((Object[])modes);
      }

      public FileOutputStream openStream() throws IOException {
         return new FileOutputStream(this.file, this.modes.contains(FileWriteMode.APPEND));
      }

      public String toString() {
         return "Files.asByteSink(" + this.file + ", " + this.modes + ")";
      }

      // $FF: synthetic method
      FileByteSink(File x0, FileWriteMode[] x1, Object x2) {
         this(x0, x1);
      }
   }

   private static final class FileByteSource extends ByteSource {
      private final File file;

      private FileByteSource(File file) {
         this.file = (File)Preconditions.checkNotNull(file);
      }

      public FileInputStream openStream() throws IOException {
         return new FileInputStream(this.file);
      }

      public Optional sizeIfKnown() {
         return this.file.isFile() ? Optional.of(this.file.length()) : Optional.absent();
      }

      public long size() throws IOException {
         if (!this.file.isFile()) {
            throw new FileNotFoundException(this.file.toString());
         } else {
            return this.file.length();
         }
      }

      public byte[] read() throws IOException {
         Closer closer = Closer.create();

         byte[] var3;
         try {
            FileInputStream in = (FileInputStream)closer.register(this.openStream());
            var3 = Files.readFile(in, in.getChannel().size());
         } catch (Throwable var7) {
            throw closer.rethrow(var7);
         } finally {
            closer.close();
         }

         return var3;
      }

      public String toString() {
         return "Files.asByteSource(" + this.file + ")";
      }

      // $FF: synthetic method
      FileByteSource(File x0, Object x1) {
         this(x0);
      }
   }
}
