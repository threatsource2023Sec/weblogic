package org.glassfish.grizzly.http.server;

import java.io.File;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.http.Method;
import org.glassfish.grizzly.http.util.Header;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.grizzly.utils.ArraySet;

public class StaticHttpHandler extends StaticHttpHandlerBase {
   private static final Logger LOGGER = Grizzly.logger(StaticHttpHandler.class);
   protected final ArraySet docRoots = new ArraySet(File.class);
   private boolean directorySlashOff;

   public StaticHttpHandler() {
      this.addDocRoot(".");
   }

   public StaticHttpHandler(String... docRoots) {
      if (docRoots != null) {
         String[] var2 = docRoots;
         int var3 = docRoots.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String docRoot = var2[var4];
            this.addDocRoot(docRoot);
         }
      }

   }

   public StaticHttpHandler(Set docRoots) {
      if (docRoots != null) {
         Iterator var2 = docRoots.iterator();

         while(var2.hasNext()) {
            String docRoot = (String)var2.next();
            this.addDocRoot(docRoot);
         }
      }

   }

   public File getDefaultDocRoot() {
      File[] array = (File[])this.docRoots.getArray();
      return array != null && array.length > 0 ? array[0] : null;
   }

   public ArraySet getDocRoots() {
      return this.docRoots;
   }

   public final File addDocRoot(String docRoot) {
      if (docRoot == null) {
         throw new NullPointerException("docRoot can't be null");
      } else {
         File file = new File(docRoot);
         this.addDocRoot(file);
         return file;
      }
   }

   public final void addDocRoot(File docRoot) {
      this.docRoots.add(docRoot);
   }

   public void removeDocRoot(File docRoot) {
      this.docRoots.remove(docRoot);
   }

   public boolean isDirectorySlashOff() {
      return this.directorySlashOff;
   }

   public void setDirectorySlashOff(boolean directorySlashOff) {
      this.directorySlashOff = directorySlashOff;
   }

   protected boolean handle(String uri, Request request, Response response) throws Exception {
      boolean found = false;
      File[] fileFolders = (File[])this.docRoots.getArray();
      if (fileFolders == null) {
         return false;
      } else {
         File resource = null;

         for(int i = 0; i < fileFolders.length; ++i) {
            File webDir = fileFolders[i];
            resource = new File(webDir, uri);
            boolean exists = resource.exists();
            boolean isDirectory = resource.isDirectory();
            if (exists && isDirectory) {
               if (!this.directorySlashOff && !uri.endsWith("/")) {
                  response.setStatus(HttpStatus.MOVED_PERMANENTLY_301);
                  response.setHeader(Header.Location, response.encodeRedirectURL(uri + "/"));
                  return true;
               }

               File f = new File(resource, "/index.html");
               if (f.exists()) {
                  resource = f;
                  found = true;
                  break;
               }
            }

            if (!isDirectory && exists) {
               found = true;
               break;
            }

            found = false;
         }

         if (!found) {
            if (LOGGER.isLoggable(Level.FINE)) {
               LOGGER.log(Level.FINE, "File not found {0}", resource);
            }

            return false;
         } else {
            assert resource != null;

            if (!Method.GET.equals(request.getMethod())) {
               if (LOGGER.isLoggable(Level.FINE)) {
                  LOGGER.log(Level.FINE, "File found {0}, but HTTP method {1} is not allowed", new Object[]{resource, request.getMethod()});
               }

               response.setStatus(HttpStatus.METHOD_NOT_ALLOWED_405);
               response.setHeader(Header.Allow, "GET");
               return true;
            } else {
               pickupContentType(response, resource.getPath());
               this.addToFileCache(request, response, resource);
               sendFile(response, resource);
               return true;
            }
         }
      }
   }
}
