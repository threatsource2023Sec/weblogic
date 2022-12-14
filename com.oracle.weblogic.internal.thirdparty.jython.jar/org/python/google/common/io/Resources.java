package org.python.google.common.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.base.MoreObjects;
import org.python.google.common.base.Preconditions;
import org.python.google.common.collect.Lists;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@Beta
@GwtIncompatible
public final class Resources {
   private Resources() {
   }

   public static ByteSource asByteSource(URL url) {
      return new UrlByteSource(url);
   }

   public static CharSource asCharSource(URL url, Charset charset) {
      return asByteSource(url).asCharSource(charset);
   }

   public static byte[] toByteArray(URL url) throws IOException {
      return asByteSource(url).read();
   }

   public static String toString(URL url, Charset charset) throws IOException {
      return asCharSource(url, charset).read();
   }

   @CanIgnoreReturnValue
   public static Object readLines(URL url, Charset charset, LineProcessor callback) throws IOException {
      return asCharSource(url, charset).readLines(callback);
   }

   public static List readLines(URL url, Charset charset) throws IOException {
      return (List)readLines(url, charset, new LineProcessor() {
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

   public static void copy(URL from, OutputStream to) throws IOException {
      asByteSource(from).copyTo(to);
   }

   @CanIgnoreReturnValue
   public static URL getResource(String resourceName) {
      ClassLoader loader = (ClassLoader)MoreObjects.firstNonNull(Thread.currentThread().getContextClassLoader(), Resources.class.getClassLoader());
      URL url = loader.getResource(resourceName);
      Preconditions.checkArgument(url != null, "resource %s not found.", (Object)resourceName);
      return url;
   }

   public static URL getResource(Class contextClass, String resourceName) {
      URL url = contextClass.getResource(resourceName);
      Preconditions.checkArgument(url != null, "resource %s relative to %s not found.", resourceName, contextClass.getName());
      return url;
   }

   private static final class UrlByteSource extends ByteSource {
      private final URL url;

      private UrlByteSource(URL url) {
         this.url = (URL)Preconditions.checkNotNull(url);
      }

      public InputStream openStream() throws IOException {
         return this.url.openStream();
      }

      public String toString() {
         return "Resources.asByteSource(" + this.url + ")";
      }

      // $FF: synthetic method
      UrlByteSource(URL x0, Object x1) {
         this(x0);
      }
   }
}
