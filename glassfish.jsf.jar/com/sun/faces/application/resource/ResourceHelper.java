package com.sun.faces.application.resource;

import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.MessageUtils;
import com.sun.faces.util.Util;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPOutputStream;
import javax.el.ELContext;
import javax.el.ELException;
import javax.el.ValueExpression;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

public abstract class ResourceHelper {
   private static final Logger LOGGER;
   private static final Pattern LIBRARY_VERSION_PATTERN;
   private static final Pattern RESOURCE_VERSION_PATTERN;
   private static final String COMPRESSED_CONTENT_FILENAME = "compressed-content";
   private static final String[] EL_CONTENT_TYPES;

   public abstract String getBaseResourcePath();

   public abstract String getBaseContractsPath();

   protected String getBasePath(String contract) {
      return contract == null ? this.getBaseResourcePath() : this.getBaseContractsPath() + '/' + contract;
   }

   public InputStream getInputStream(ResourceInfo toStream, FacesContext ctx) throws IOException {
      InputStream in = null;
      if (toStream instanceof ClientResourceInfo) {
         ClientResourceInfo resource = (ClientResourceInfo)toStream;
         in = this.getInputStreamFromClientInfo(resource, ctx);
         if (null == in) {
            ClientResourceInfo resourceWithoutLocalePrefix = new ClientResourceInfo(resource, false);
            in = this.getInputStreamFromClientInfo(resourceWithoutLocalePrefix, ctx);
            if (null != in) {
               resource.copy(resourceWithoutLocalePrefix);
            }
         }
      }

      return in;
   }

   private InputStream getInputStreamFromClientInfo(ClientResourceInfo resource, FacesContext ctx) throws IOException {
      InputStream in = null;
      if (resource.isCompressable() && this.clientAcceptsCompression(ctx)) {
         if (!resource.supportsEL()) {
            try {
               String path = resource.getCompressedPath();
               in = new BufferedInputStream(new FileInputStream(path + File.separatorChar + "compressed-content"));
            } catch (IOException var67) {
               if (LOGGER.isLoggable(Level.SEVERE)) {
                  LOGGER.log(Level.SEVERE, var67.getMessage(), var67);
               }

               in = null;
            }
         } else {
            byte[] buf = new byte[512];

            try {
               InputStream temp = new BufferedInputStream(new ELEvaluatingInputStream(ctx, resource, this.getNonCompressedInputStream(resource, ctx)));
               Throwable var6 = null;

               try {
                  ByteArrayOutputStream baos = new ByteArrayOutputStream(512);
                  Throwable var8 = null;

                  try {
                     OutputStream out = new GZIPOutputStream(baos);
                     Throwable var10 = null;

                     try {
                        for(int read = temp.read(buf); read != -1; read = temp.read(buf)) {
                           out.write(buf, 0, read);
                        }

                        in = new BufferedInputStream(new ByteArrayInputStream(baos.toByteArray()));
                     } catch (Throwable var60) {
                        var10 = var60;
                        throw var60;
                     } finally {
                        if (out != null) {
                           if (var10 != null) {
                              try {
                                 out.close();
                              } catch (Throwable var59) {
                                 var10.addSuppressed(var59);
                              }
                           } else {
                              out.close();
                           }
                        }

                     }
                  } catch (Throwable var62) {
                     var8 = var62;
                     throw var62;
                  } finally {
                     if (baos != null) {
                        if (var8 != null) {
                           try {
                              baos.close();
                           } catch (Throwable var58) {
                              var8.addSuppressed(var58);
                           }
                        } else {
                           baos.close();
                        }
                     }

                  }
               } catch (Throwable var64) {
                  var6 = var64;
                  throw var64;
               } finally {
                  if (temp != null) {
                     if (var6 != null) {
                        try {
                           temp.close();
                        } catch (Throwable var57) {
                           var6.addSuppressed(var57);
                        }
                     } else {
                        temp.close();
                     }
                  }

               }
            } catch (IOException var66) {
               if (LOGGER.isLoggable(Level.SEVERE)) {
                  LOGGER.log(Level.SEVERE, var66.getMessage(), var66);
               }
            }
         }
      }

      if (in == null) {
         if (resource.supportsEL()) {
            return new BufferedInputStream(new ELEvaluatingInputStream(ctx, resource, this.getNonCompressedInputStream(resource, ctx)));
         }

         in = this.getNonCompressedInputStream(resource, ctx);
      }

      return (InputStream)in;
   }

   public abstract URL getURL(ResourceInfo var1, FacesContext var2);

   public abstract LibraryInfo findLibrary(String var1, String var2, String var3, FacesContext var4);

   public abstract ResourceInfo findResource(LibraryInfo var1, String var2, String var3, boolean var4, FacesContext var5);

   public long getLastModified(ResourceInfo resource, FacesContext ctx) {
      URL url = this.getURL(resource, ctx);
      return url == null ? 0L : Util.getLastModified(url);
   }

   protected abstract InputStream getNonCompressedInputStream(ResourceInfo var1, FacesContext var2) throws IOException;

   protected VersionInfo getVersion(Collection resourcePaths, boolean isResource) {
      List versionedPaths = new ArrayList(resourcePaths.size());
      Iterator var4 = resourcePaths.iterator();

      while(var4.hasNext()) {
         String p = (String)var4.next();
         VersionInfo vp = this.getVersion(p, isResource);
         if (vp != null) {
            versionedPaths.add(vp);
         }
      }

      VersionInfo version = null;
      if (!versionedPaths.isEmpty()) {
         Collections.sort(versionedPaths);
         version = (VersionInfo)versionedPaths.get(versionedPaths.size() - 1);
      }

      return version;
   }

   protected boolean compressContent(ClientResourceInfo info) throws IOException {
      InputStream source = null;
      OutputStream dest = null;

      boolean var10;
      try {
         URL url = info.getHelper().getURL(info, FacesContext.getCurrentInstance());
         URLConnection conn = url.openConnection();
         conn.setUseCaches(false);
         conn.connect();
         source = conn.getInputStream();
         byte[] buf = new byte[512];
         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         dest = new GZIPOutputStream(baos);
         int totalRead = 0;

         for(int len = source.read(buf); len != -1; len = source.read(buf)) {
            ((OutputStream)dest).write(buf, 0, len);
            totalRead += len;
         }

         ((OutputStream)dest).flush();

         try {
            ((OutputStream)dest).close();
         } catch (IOException var24) {
            if (LOGGER.isLoggable(Level.FINEST)) {
               LOGGER.log(Level.FINEST, "Closing stream", var24);
            }
         }

         if (baos.size() >= totalRead) {
            boolean var27 = false;
            return var27;
         }

         String outputFile = info.getCompressedPath() + File.separatorChar + "compressed-content";
         dest = new FileOutputStream(outputFile);
         ((OutputStream)dest).write(baos.toByteArray());
         ((OutputStream)dest).flush();
         var10 = true;
      } finally {
         if (source != null) {
            try {
               source.close();
            } catch (IOException var23) {
               if (LOGGER.isLoggable(Level.FINEST)) {
                  LOGGER.log(Level.FINEST, "Closing stream", var23);
               }
            }
         }

         if (dest != null) {
            try {
               ((OutputStream)dest).close();
            } catch (IOException var22) {
               if (LOGGER.isLoggable(Level.FINEST)) {
                  LOGGER.log(Level.FINEST, "Closing stream", var22);
               }
            }
         }

      }

      return var10;
   }

   protected boolean clientAcceptsCompression(FacesContext ctx) {
      ExternalContext extCtx = ctx.getExternalContext();
      Object response = extCtx.getResponse();
      if (response instanceof HttpServletResponse) {
         String[] values = (String[])extCtx.getRequestHeaderValuesMap().get("accept-encoding");
         boolean gzipFound = false;
         String[] var6 = values;
         int var7 = values.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            String value = var6[var8];
            if (value.contains("gzip;q=0")) {
               return false;
            }

            if (value.contains("gzip")) {
               gzipFound = true;
               break;
            }

            if (value.contains("*") && !value.contains("*;q=0,") && !value.endsWith("*;q=0")) {
               gzipFound = true;
            }
         }

         if (gzipFound) {
            ((HttpServletResponse)response).setHeader("Content-Encoding", "gzip");
            return true;
         }
      }

      return false;
   }

   protected ClientResourceInfo handleCompression(ClientResourceInfo resource) {
      try {
         if (!resource.supportsEL() && !this.compressContent(resource)) {
            resource = this.rebuildAsNonCompressed(resource);
         }
      } catch (IOException var3) {
         if (LOGGER.isLoggable(Level.SEVERE)) {
            LOGGER.log(Level.SEVERE, var3.getMessage(), var3);
         }

         resource = this.rebuildAsNonCompressed(resource);
      }

      return resource;
   }

   protected boolean resourceSupportsEL(String resourceName, String libraryName, FacesContext ctx) {
      ExternalContext extContext = ctx.getExternalContext();
      String contentType = extContext.getMimeType(resourceName);
      boolean result = contentType != null && Arrays.binarySearch(EL_CONTENT_TYPES, contentType) >= 0 || null != resourceName && null != libraryName && "javax.faces".equals(libraryName) && "jsf.js".equals(resourceName);
      return result;
   }

   protected String trimLeadingSlash(String s) {
      return s.charAt(0) == '/' ? s.substring(1) : s;
   }

   private ClientResourceInfo rebuildAsNonCompressed(ClientResourceInfo resource) {
      LibraryInfo library = resource.getLibraryInfo();
      ClientResourceInfo ret;
      if (library != null) {
         ret = new ClientResourceInfo(resource.library, resource.contract, resource.name, resource.version, false, resource.supportsEL, resource.isDevStage, resource.cacheTimestamp);
      } else {
         ret = new ClientResourceInfo(resource.contract, resource.name, resource.version, resource.localePrefix, this, false, resource.supportsEL, resource.isDevStage, resource.cacheTimestamp);
      }

      return ret;
   }

   private VersionInfo getVersion(String pathElement, boolean isResource) {
      Map appMap = FacesContext.getCurrentInstance().getExternalContext().getApplicationMap();
      String[] pathElements = Util.split(appMap, pathElement, "/");
      String path = pathElements[pathElements.length - 1];
      String extension = null;
      if (isResource) {
         Matcher m = RESOURCE_VERSION_PATTERN.matcher(path);
         return m.matches() ? new VersionInfo(m.group(1), m.group(2)) : null;
      } else {
         return LIBRARY_VERSION_PATTERN.matcher(path).matches() ? new VersionInfo(path, (String)extension) : null;
      }
   }

   static {
      LOGGER = FacesLogger.RESOURCE.getLogger();
      LIBRARY_VERSION_PATTERN = Pattern.compile("^(\\d+)(_\\d+)+");
      RESOURCE_VERSION_PATTERN = Pattern.compile("^((?:\\d+)(?:_\\d+)+)[\\.]?(\\w+)?");
      EL_CONTENT_TYPES = new String[]{"text/css"};
      Arrays.sort(EL_CONTENT_TYPES);
   }

   private static final class ELEvaluatingInputStream extends InputStream {
      private List buf = new ArrayList(1024);
      private boolean failedExpressionTest = false;
      private boolean writingExpression = false;
      private InputStream inner;
      private ClientResourceInfo info;
      private FacesContext ctx;
      private boolean expressionEvaluated;
      private boolean endOfStreamReached;
      private int nextRead = -1;

      public ELEvaluatingInputStream(FacesContext ctx, ClientResourceInfo info, InputStream inner) {
         this.inner = inner;
         this.info = info;
         this.ctx = ctx;
      }

      public int read() throws IOException {
         if (null == this.inner) {
            return -1;
         } else {
            int i;
            if (this.failedExpressionTest) {
               i = this.nextRead;
               this.nextRead = -1;
               this.failedExpressionTest = false;
            } else if (this.writingExpression) {
               if (0 < this.buf.size()) {
                  i = (Integer)this.buf.remove(0);
               } else {
                  this.writingExpression = false;
                  i = this.inner.read();
               }
            } else {
               i = this.inner.read();
               char c = (char)i;
               if (c == '#') {
                  i = this.inner.read();
                  c = (char)i;
                  if (c == '{') {
                     this.readExpressionIntoBufferAndEvaluateIntoBuffer();
                     this.writingExpression = true;
                     i = this.read();
                  } else {
                     i = 35;
                     this.nextRead = c;
                     this.failedExpressionTest = true;
                  }
               }
            }

            if (i == -1) {
               this.endOfStreamReached = true;
            }

            return i;
         }
      }

      private void readExpressionIntoBufferAndEvaluateIntoBuffer() throws IOException {
         int i;
         char c;
         do {
            i = this.inner.read();
            c = (char)i;
            if (c == '}') {
               this.evaluateExpressionIntoBuffer();
            } else {
               this.buf.add(i);
            }
         } while(c != '}' && i != -1);

      }

      private void evaluateExpressionIntoBuffer() {
         char[] chars = new char[this.buf.size()];
         int i = 0;

         int colon;
         for(colon = this.buf.size(); i < colon; ++i) {
            chars[i] = (char)(Integer)this.buf.get(i);
         }

         String expressionBody = new String(chars);
         String message;
         if (-1 != (colon = expressionBody.indexOf(":"))) {
            if (!this.isPropertyValid(expressionBody)) {
               String message = MessageUtils.getExceptionMessageString("com.sun.faces.RESOURCE_INVALID_FORMAT_COLON_ERROR", expressionBody);
               throw new ELException(message);
            }

            Map appMap = FacesContext.getCurrentInstance().getExternalContext().getApplicationMap();
            String[] parts = Util.split(appMap, expressionBody, ":");
            if (null == parts[0] || null == parts[1]) {
               String message = MessageUtils.getExceptionMessageString("com.sun.faces.RESOURCE_INVALID_FORMAT_NO_LIBRARY_NAME_ERROR", expressionBody);
               throw new ELException(message);
            }

            try {
               int mark = parts[0].indexOf("[") + 2;
               char quoteMark = parts[0].charAt(mark - 1);
               parts[0] = parts[0].substring(mark, colon);
               if (parts[0].equals("this")) {
                  LibraryInfo libInfo = this.info.getLibraryInfo();
                  if (null != libInfo) {
                     parts[0] = libInfo.getName();
                  } else {
                     if (null == this.info.getContract()) {
                        throw new NullPointerException("Resource expression is not a library or resource library contract");
                     }

                     parts[0] = this.info.getContract();
                  }

                  mark = parts[1].indexOf("]") - 1;
                  parts[1] = parts[1].substring(0, mark);
                  expressionBody = "resource[" + quoteMark + parts[0] + ":" + parts[1] + quoteMark + "]";
               }
            } catch (Exception var10) {
               message = MessageUtils.getExceptionMessageString("com.sun.faces.RESOURCE_INVALID_FORMAT_ERROR", expressionBody);
               throw new ELException(message);
            }
         }

         ELContext elContext = this.ctx.getELContext();
         this.expressionEvaluated = true;
         ValueExpression ve = this.ctx.getApplication().getExpressionFactory().createValueExpression(elContext, "#{" + expressionBody + "}", String.class);
         Object value = ve.getValue(elContext);
         message = value != null ? value.toString() : "";
         this.buf.clear();
         int i = 0;

         for(int len = message.length(); i < len; ++i) {
            this.buf.add(Integer.valueOf(message.charAt(i)));
         }

      }

      public void close() throws IOException {
         if (this.endOfStreamReached && !this.expressionEvaluated) {
            this.info.disableEL();
         }

         this.inner.close();
         super.close();
      }

      private boolean isPropertyValid(String property) {
         int idx = property.indexOf(58);
         return property.indexOf(58, idx + 1) == -1;
      }
   }
}
