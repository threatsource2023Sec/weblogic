package org.glassfish.grizzly.http.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.WriteHandler;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.http.Method;
import org.glassfish.grizzly.http.io.NIOOutputStream;
import org.glassfish.grizzly.http.server.filecache.FileCache;
import org.glassfish.grizzly.http.util.Header;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.grizzly.memory.BufferArray;
import org.glassfish.grizzly.memory.MemoryManager;
import org.glassfish.grizzly.utils.ArraySet;

public class CLStaticHttpHandler extends StaticHttpHandlerBase {
   private static final Logger LOGGER = Grizzly.logger(CLStaticHttpHandler.class);
   protected static final String CHECK_NON_SLASH_TERMINATED_FOLDERS_PROP = CLStaticHttpHandler.class.getName() + ".check-non-slash-terminated-folders";
   private static final boolean CHECK_NON_SLASH_TERMINATED_FOLDERS;
   private static final String SLASH_STR = "/";
   private static final String EMPTY_STR = "";
   private final ClassLoader classLoader;
   private final ArraySet docRoots = new ArraySet(String.class);

   public CLStaticHttpHandler(ClassLoader classLoader, String... docRoots) {
      if (classLoader == null) {
         throw new IllegalArgumentException("ClassLoader can not be null");
      } else {
         this.classLoader = classLoader;
         if (docRoots.length > 0) {
            String[] var3 = docRoots;
            int var4 = docRoots.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               String docRoot = var3[var5];
               if (!docRoot.endsWith("/")) {
                  throw new IllegalArgumentException("Doc root should end with slash ('/')");
               }
            }

            this.docRoots.addAll(docRoots);
         } else {
            this.docRoots.add("/");
         }

      }
   }

   public boolean addDocRoot(String docRoot) {
      if (!docRoot.endsWith("/")) {
         throw new IllegalArgumentException("Doc root should end with slash ('/')");
      } else {
         return this.docRoots.add(docRoot);
      }
   }

   public boolean removeDocRoot(String docRoot) {
      return this.docRoots.remove(docRoot);
   }

   public ClassLoader getClassLoader() {
      return this.classLoader;
   }

   protected boolean handle(String resourcePath, Request request, Response response) throws Exception {
      URLConnection urlConnection = null;
      InputStream urlInputStream = null;
      if (resourcePath.startsWith("/")) {
         resourcePath = resourcePath.substring(1);
      }

      boolean mayBeFolder = true;
      if (resourcePath.length() == 0 || resourcePath.endsWith("/")) {
         resourcePath = resourcePath + "index.html";
         mayBeFolder = false;
      }

      URL url = this.lookupResource(resourcePath);
      if (url == null && mayBeFolder && CHECK_NON_SLASH_TERMINATED_FOLDERS) {
         url = this.lookupResource(resourcePath + "/index.html");
         mayBeFolder = false;
      }

      File fileResource = null;
      String filePath = null;
      boolean found = false;
      File jarFile;
      if (url != null) {
         if ("file".equals(url.getProtocol())) {
            jarFile = new File(url.toURI());
            if (jarFile.exists()) {
               if (jarFile.isDirectory()) {
                  File welcomeFile = new File(jarFile, "/index.html");
                  if (welcomeFile.exists() && welcomeFile.isFile()) {
                     fileResource = welcomeFile;
                     filePath = welcomeFile.getPath();
                     found = true;
                  }
               } else {
                  fileResource = jarFile;
                  filePath = jarFile.getPath();
                  found = true;
               }
            }
         } else {
            urlConnection = url.openConnection();
            if ("jar".equals(url.getProtocol())) {
               JarURLConnection jarUrlConnection = (JarURLConnection)urlConnection;
               JarEntry jarEntry = jarUrlConnection.getJarEntry();
               JarFile jarFile = jarUrlConnection.getJarFile();
               InputStream is = null;
               if (jarEntry.isDirectory() || (is = jarFile.getInputStream(jarEntry)) == null) {
                  String welcomeResource = jarEntry.getName().endsWith("/") ? jarEntry.getName() + "index.html" : jarEntry.getName() + "/index.html";
                  jarEntry = jarFile.getJarEntry(welcomeResource);
                  if (jarEntry != null) {
                     is = jarFile.getInputStream(jarEntry);
                  }
               }

               if (is != null) {
                  urlInputStream = new JarURLInputStream(jarUrlConnection, jarFile, is);

                  assert jarEntry != null;

                  filePath = jarEntry.getName();
                  found = true;
               } else {
                  closeJarFileIfNeeded(jarUrlConnection, jarFile);
               }
            } else if ("bundle".equals(url.getProtocol())) {
               if (mayBeFolder && urlConnection.getContentLength() <= 0) {
                  URL welcomeUrl = this.classLoader.getResource(url.getPath() + "/index.html");
                  if (welcomeUrl != null) {
                     url = welcomeUrl;
                     urlConnection = welcomeUrl.openConnection();
                  }
               }

               found = true;
            } else {
               found = true;
            }
         }
      }

      if (!found) {
         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, "Resource not found {0}", resourcePath);
         }

         return false;
      } else {
         assert url != null;

         if (!Method.GET.equals(request.getMethod())) {
            if (LOGGER.isLoggable(Level.FINE)) {
               LOGGER.log(Level.FINE, "Resource found {0}, but HTTP method {1} is not allowed", new Object[]{resourcePath, request.getMethod()});
            }

            response.setStatus(HttpStatus.METHOD_NOT_ALLOWED_405);
            response.setHeader(Header.Allow, "GET");
            return true;
         } else {
            pickupContentType(response, filePath != null ? filePath : url.getPath());
            if (fileResource != null) {
               this.addToFileCache(request, response, fileResource);
               sendFile(response, fileResource);
            } else {
               assert urlConnection != null;

               if ("jar".equals(url.getProtocol())) {
                  jarFile = this.getJarFile((new URI(url.getPath())).getPath());
                  this.addTimeStampEntryToFileCache(request, response, jarFile);
               }

               sendResource(response, (InputStream)(urlInputStream != null ? urlInputStream : urlConnection.getInputStream()));
            }

            return true;
         }
      }
   }

   private URL lookupResource(String resourcePath) {
      String[] docRootsLocal = (String[])this.docRoots.getArray();
      if (docRootsLocal != null && docRootsLocal.length != 0) {
         String[] var3 = docRootsLocal;
         int var4 = docRootsLocal.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String docRoot = var3[var5];
            if ("/".equals(docRoot)) {
               docRoot = "";
            } else if (docRoot.startsWith("/")) {
               docRoot = docRoot.substring(1);
            }

            String fullPath = docRoot + resourcePath;
            URL url = this.classLoader.getResource(fullPath);
            if (url != null) {
               return url;
            }
         }

         return null;
      } else {
         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, "No doc roots registered -> resource {0} is not found ", resourcePath);
         }

         return null;
      }
   }

   private static void sendResource(Response response, InputStream input) throws IOException {
      response.setStatus(HttpStatus.OK_200);
      response.addDateHeader(Header.Date, System.currentTimeMillis());
      int chunkSize = true;
      response.suspend();
      NIOOutputStream outputStream = response.getNIOOutputStream();
      outputStream.notifyCanWrite(new NonBlockingDownloadHandler(response, outputStream, input, 8192));
   }

   private boolean addTimeStampEntryToFileCache(Request req, Response res, File archive) {
      if (this.isFileCacheEnabled()) {
         FilterChainContext fcContext = req.getContext();
         FileCacheFilter fileCacheFilter = this.lookupFileCache(fcContext);
         if (fileCacheFilter != null) {
            FileCache fileCache = fileCacheFilter.getFileCache();
            if (fileCache.isEnabled()) {
               if (res != null) {
                  addCachingHeaders(res, archive);
               }

               fileCache.add(req.getRequest(), archive.lastModified());
               return true;
            }
         }
      }

      return false;
   }

   private File getJarFile(String path) throws MalformedURLException, FileNotFoundException {
      int jarDelimIdx = path.indexOf("!/");
      if (jarDelimIdx == -1) {
         throw new MalformedURLException("The jar file delimeter were not found");
      } else {
         File file = new File(path.substring(0, jarDelimIdx));
         if (file.exists() && file.isFile()) {
            return file;
         } else {
            throw new FileNotFoundException("The jar file was not found");
         }
      }
   }

   private static void closeJarFileIfNeeded(JarURLConnection jarConnection, JarFile jarFile) throws IOException {
      if (!jarConnection.getUseCaches()) {
         jarFile.close();
      }

   }

   static {
      CHECK_NON_SLASH_TERMINATED_FOLDERS = System.getProperty(CHECK_NON_SLASH_TERMINATED_FOLDERS_PROP) == null || Boolean.getBoolean(CHECK_NON_SLASH_TERMINATED_FOLDERS_PROP);
   }

   static class JarURLInputStream extends FilterInputStream {
      private final JarURLConnection jarConnection;
      private final JarFile jarFile;

      JarURLInputStream(JarURLConnection jarConnection, JarFile jarFile, InputStream src) {
         super(src);
         this.jarConnection = jarConnection;
         this.jarFile = jarFile;
      }

      public void close() throws IOException {
         try {
            super.close();
         } finally {
            CLStaticHttpHandler.closeJarFileIfNeeded(this.jarConnection, this.jarFile);
         }

      }
   }

   private static class NonBlockingDownloadHandler implements WriteHandler {
      private final Response response;
      private final NIOOutputStream outputStream;
      private final InputStream inputStream;
      private final MemoryManager mm;
      private final int chunkSize;

      NonBlockingDownloadHandler(Response response, NIOOutputStream outputStream, InputStream inputStream, int chunkSize) {
         this.response = response;
         this.outputStream = outputStream;
         this.inputStream = inputStream;
         this.mm = response.getRequest().getContext().getMemoryManager();
         this.chunkSize = chunkSize;
      }

      public void onWritePossible() throws Exception {
         CLStaticHttpHandler.LOGGER.log(Level.FINE, "[onWritePossible]");
         boolean isWriteMore = this.sendChunk();
         if (isWriteMore) {
            this.outputStream.notifyCanWrite(this);
         }

      }

      public void onError(Throwable t) {
         CLStaticHttpHandler.LOGGER.log(Level.FINE, "[onError] ", t);
         this.response.setStatus(500, t.getMessage());
         this.complete(true);
      }

      private boolean sendChunk() throws IOException {
         Buffer buffer = null;
         if (!this.mm.willAllocateDirect(this.chunkSize)) {
            buffer = this.mm.allocate(this.chunkSize);
            int len;
            if (!buffer.isComposite()) {
               len = this.inputStream.read(buffer.array(), buffer.position() + buffer.arrayOffset(), this.chunkSize);
            } else {
               BufferArray bufferArray = buffer.toBufferArray();
               int size = bufferArray.size();
               Buffer[] buffers = (Buffer[])bufferArray.getArray();
               int lenCounter = 0;

               for(int i = 0; i < size; ++i) {
                  Buffer subBuffer = buffers[i];
                  int subBufferLen = subBuffer.remaining();
                  int justReadLen = this.inputStream.read(subBuffer.array(), subBuffer.position() + subBuffer.arrayOffset(), subBufferLen);
                  if (justReadLen > 0) {
                     lenCounter += justReadLen;
                  }

                  if (justReadLen < subBufferLen) {
                     break;
                  }
               }

               bufferArray.restore();
               bufferArray.recycle();
               len = lenCounter > 0 ? lenCounter : -1;
            }

            if (len > 0) {
               buffer.position(buffer.position() + len);
            } else {
               buffer.dispose();
               buffer = null;
            }
         } else {
            byte[] buf = new byte[this.chunkSize];
            int len = this.inputStream.read(buf);
            if (len > 0) {
               buffer = this.mm.allocate(len);
               buffer.put(buf);
            }
         }

         if (buffer == null) {
            this.complete(false);
            return false;
         } else {
            buffer.allowBufferDispose(true);
            buffer.trim();
            this.outputStream.write(buffer);
            return true;
         }
      }

      private void complete(boolean isError) {
         try {
            this.inputStream.close();
         } catch (IOException var4) {
            if (!isError) {
               this.response.setStatus(500, var4.getMessage());
            }
         }

         try {
            this.outputStream.close();
         } catch (IOException var3) {
            if (!isError) {
               this.response.setStatus(500, var3.getMessage());
            }
         }

         if (this.response.isSuspended()) {
            this.response.resume();
         } else {
            this.response.finish();
         }

      }
   }
}
