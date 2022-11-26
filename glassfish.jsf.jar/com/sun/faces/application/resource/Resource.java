package com.sun.faces.application.resource;

import com.sun.faces.util.FacesLogger;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

public final class Resource {
   protected static final Logger LOGGER;

   static URL getResourceUrl(FacesContext ctx, String path) throws MalformedURLException {
      ExternalContext externalContext = ctx.getExternalContext();
      URL url = externalContext.getResource(path);
      if (LOGGER.isLoggable(Level.FINE)) {
         LOGGER.fine("Resource-Url from external context: " + url);
      }

      if (url == null && resourceExist(externalContext, path)) {
         url = getUrlForResourceAsStream(externalContext, path);
      }

      return url;
   }

   static Set getViewResourcePaths(FacesContext ctx, String path) {
      return ctx.getExternalContext().getResourcePaths(path);
   }

   private static boolean resourceExist(ExternalContext externalContext, String path) {
      if ("/".equals(path)) {
         return true;
      } else {
         Object ctx = externalContext.getContext();
         if (ctx instanceof ServletContext) {
            ServletContext servletContext = (ServletContext)ctx;
            InputStream stream = servletContext.getResourceAsStream(path);
            if (stream != null) {
               try {
                  stream.close();
               } catch (IOException var6) {
                  if (LOGGER.isLoggable(Level.FINEST)) {
                     LOGGER.log(Level.FINEST, "Closing stream", var6);
                  }
               }

               return true;
            }
         }

         return false;
      }
   }

   private static URL getUrlForResourceAsStream(final ExternalContext externalContext, String path) throws MalformedURLException {
      URLStreamHandler handler = new URLStreamHandler() {
         protected URLConnection openConnection(URL u) throws IOException {
            final String file = u.getFile();
            return new URLConnection(u) {
               public void connect() throws IOException {
               }

               public InputStream getInputStream() throws IOException {
                  if (Resource.LOGGER.isLoggable(Level.FINE)) {
                     Resource.LOGGER.fine("Opening internal url to " + file);
                  }

                  Object ctx = externalContext.getContext();
                  if (ctx instanceof ServletContext) {
                     ServletContext servletContext = (ServletContext)ctx;
                     InputStream stream = servletContext.getResourceAsStream(file);
                     if (stream == null) {
                        throw new FileNotFoundException("Cannot open resource " + file);
                     } else {
                        return stream;
                     }
                  } else {
                     throw new IOException("Cannot open resource for an context of " + (ctx != null ? ctx.getClass() : null));
                  }
               }
            };
         }
      };
      return new URL("internal", (String)null, 0, path, handler);
   }

   static {
      LOGGER = FacesLogger.FACELETS_FACTORY.getLogger();
   }
}
