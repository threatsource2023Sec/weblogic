package weblogic.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.text.CollationKey;
import java.text.Collator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import javax.servlet.DispatcherType;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.servlet.internal.ByteRangeHandler;
import weblogic.servlet.internal.ServletOutputStreamImpl;
import weblogic.servlet.internal.ServletRequestImpl;
import weblogic.servlet.internal.ServletResponseImpl;
import weblogic.servlet.internal.WarSource;
import weblogic.servlet.internal.WebAppServletContext;
import weblogic.servlet.spi.WebServerRegistry;
import weblogic.utils.CharsetMap;
import weblogic.utils.classloaders.Source;
import weblogic.utils.http.HttpParsing;
import weblogic.utils.io.StreamUtils;

public class FileServlet extends HttpServlet {
   private static final String REQUEST_URI_INCLUDE = "javax.servlet.include.request_uri";
   private static final String SERVLET_PATH_INCLUDE = "javax.servlet.include.servlet_path";
   private static final String PATH_INFO_INCLUDE = "javax.servlet.include.path_info";
   private static final String METHOD_GET = "GET";
   private static final String METHOD_HEAD = "HEAD";
   private static final String METHOD_DELETE = "DELETE";
   private static final String METHOD_OPTIONS = "OPTIONS";
   private static final String METHOD_POST = "POST";
   private static final String METHOD_PUT = "PUT";
   private static final String METHOD_TRACE = "TRACE";
   private static final int FORMAT_SPACE = 5;
   private static final String HIDE_CONTEXT_ROOT_DIR = System.getProperty("weblogic.servlet.hideContextRootDir");
   private static final boolean ENABLE_CONTENT_LENGTH = Boolean.getBoolean("weblogic.fileServlet.enableContentLength");
   public static final String SORTBY_NAME = "NAME";
   public static final String SORTBY_LAST_MODIFIED = "LAST_MODIFIED";
   public static final String SORTBY_SIZE = "SIZE";
   public static final DebugLogger DEBUG_URL_RES = DebugLogger.getDebugLogger("DebugURLResolution");
   public static final long DEFAULT_MIN_NATIVE = 4096L;
   private long minNative = 4096L;
   private boolean nativeOK = false;
   protected WebAppServletContext context;
   protected String defaultFilename;

   public void init(ServletConfig config) throws ServletException {
      super.init(config);
      this.context = (WebAppServletContext)config.getServletContext();
      this.defaultFilename = this.context.getInitParameter("defaultFilename");
      if (this.defaultFilename == null) {
         this.defaultFilename = config.getInitParameter("defaultFilename");
      }

      if ("".equals(this.defaultFilename)) {
         this.defaultFilename = null;
      }

      this.nativeOK = this.initNative(config);
   }

   private synchronized boolean initNative(ServletConfig config) {
      String minNativeFileSize;
      if (!this.context.getConfigManager().isNativeIOEnabled()) {
         minNativeFileSize = this.context.getInitParameter("weblogic.http.nativeIOEnabled");
         if (minNativeFileSize == null) {
            minNativeFileSize = config.getInitParameter("nativeIOEnabled");
         }

         if (!"true".equalsIgnoreCase(minNativeFileSize)) {
            if (DEBUG_URL_RES.isDebugEnabled()) {
               DEBUG_URL_RES.debug(this.context.getLogContext() + ": using standard I/O");
            }

            return false;
         }
      }

      minNativeFileSize = this.context.getInitParameter("weblogic.http.minimumNativeFileSize");
      if (minNativeFileSize == null) {
         minNativeFileSize = config.getInitParameter("minimumNativeFileSize");
      }

      try {
         if (minNativeFileSize != null && !"".equals(minNativeFileSize)) {
            this.minNative = Long.parseLong(minNativeFileSize);
         } else {
            this.minNative = this.context.getConfigManager().getMinimumNativeFileSize();
         }
      } catch (NumberFormatException var5) {
         if (DEBUG_URL_RES.isDebugEnabled()) {
            DEBUG_URL_RES.debug(this.context.getLogContext() + ": Invalid setting for minimumNativeFileSize: " + minNativeFileSize);
         }

         this.minNative = this.context.getConfigManager().getMinimumNativeFileSize();
      }

      try {
         System.loadLibrary("fastfile");
      } catch (UnsatisfiedLinkError var4) {
         if (DEBUG_URL_RES.isDebugEnabled()) {
            DEBUG_URL_RES.debug(this.context.getLogContext() + ": Could not load library for native I/O", var4);
         }

         HTTPLogger.logFailedToLoadNativeIOLibrary(this.context.getLogContext(), var4);
         return false;
      }

      if (DEBUG_URL_RES.isDebugEnabled()) {
         DEBUG_URL_RES.debug(this.context.getLogContext() + ": Native I/O library loaded");
      }

      return true;
   }

   public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
      String method = req.getMethod();
      boolean isErrorDispatch = req.getDispatcherType() == DispatcherType.ERROR;
      if (!method.equals("GET") && !method.equals("HEAD") && !method.equals("POST") && !isErrorDispatch) {
         if (method.equals("DELETE")) {
            this.doDelete(req, res);
         } else if (method.equals("OPTIONS")) {
            res.setHeader("Allow", "GET, HEAD, OPTIONS, POST");
         } else if (method.equals("PUT")) {
            this.doPut(req, res);
         } else if (method.equals("TRACE")) {
            this.doTrace(req, res);
         } else {
            res.sendError(501);
         }
      } else {
         this.doGetHeadPost(req, res);
      }

   }

   private void doGetHeadPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
      WarSource src = this.findSource(req, res);
      if (src != null) {
         if (src.length() == 0L) {
            res.setContentLength(0);
         } else if (req.getAttribute("javax.servlet.error.request_uri") != null || this.isModified(req, res, src)) {
            if (isRangeRequest(req)) {
               this.sendRangeData(src, req, res);
            } else {
               String contentType = src.getContentType(this.context);
               if (contentType != null) {
                  res.setContentType(contentType);
               }

               long contentLength = src.length();
               if (contentLength > 2147483647L) {
                  contentLength = -1L;
               }

               if (contentLength != -1L && (!this.context.getFilterManager().hasFilters() || ENABLE_CONTENT_LENGTH)) {
                  res.setContentLengthLong(contentLength + ServletResponseImpl.getOriginalResponse(res).getBufferCount());
               }

               if (!req.getMethod().equals("HEAD")) {
                  if (this.nativeOK && this.isNativeOK(src, req, res)) {
                     if (DEBUG_URL_RES.isDebugEnabled()) {
                        DEBUG_URL_RES.debug(this.context.getLogContext() + ": Sending file using native I/O");
                     }

                     WebServerRegistry.getInstance().getContainerSupportProvider().sendFileNative(src.getFileName(), ServletRequestImpl.getOriginalRequest(req), contentLength);
                  } else {
                     if (DEBUG_URL_RES.isDebugEnabled()) {
                        DEBUG_URL_RES.debug(this.context.getLogContext() + ": Sending file using standard I/O");
                     }

                     this.sendFile(src, res, "HTTP/1.1".equals(req.getProtocol()));
                  }

               }
            }
         }
      }
   }

   private static boolean isRangeRequest(HttpServletRequest req) {
      if (!"HTTP/1.1".equals(req.getProtocol())) {
         return false;
      } else if (!req.getMethod().equals("GET")) {
         return false;
      } else {
         return req.getHeader("Range") != null;
      }
   }

   private boolean isNativeOK(WarSource src, HttpServletRequest req, HttpServletResponse res) {
      return !req.isSecure() && src.length() > this.minNative && src.isFile() && req.getAttribute("javax.servlet.include.request_uri") == null && res instanceof ServletResponseImpl && !((ServletResponseImpl)res).isInternalDispatch();
   }

   protected WarSource findSource(HttpServletRequest req, HttpServletResponse res) throws IOException {
      String URI = getURI(req);
      if (DEBUG_URL_RES.isDebugEnabled()) {
         DEBUG_URL_RES.debug(this.context.getLogContext() + ": looking for file: '" + URI + "'");
      }

      WarSource src = this.getSource(URI);
      if (src == null) {
         res.sendError(404);
         return null;
      } else {
         char lastURIChar = URI.length() == 0 ? 0 : URI.charAt(URI.length() - 1);
         if (!src.isDirectory()) {
            if (lastURIChar != '\\' && lastURIChar != '/') {
               return src;
            } else {
               res.sendError(404);
               return null;
            }
         } else {
            if (lastURIChar != '/') {
               res.sendRedirect(getRedirectURL(req).toString());
            } else {
               if (this.defaultFilename != null) {
                  WarSource defaultSource = this.getSource(URI + this.defaultFilename);
                  if (defaultSource != null) {
                     return defaultSource;
                  }
               }

               if (this.context.getConfigManager().isIndexDirectoryEnabled()) {
                  Enumeration srcs = this.context.getResourceFinder(URI).getSources(URI);
                  this.produceDirectoryListing(srcs, req, res);
               } else if ("false".equalsIgnoreCase(HIDE_CONTEXT_ROOT_DIR) && "/".equals(URI)) {
                  res.sendError(403);
               } else {
                  res.sendError(404);
               }
            }

            return null;
         }
      }
   }

   private static StringBuffer getRedirectURL(HttpServletRequest req) {
      String reqUri = req.getRequestURI();
      int paramsIndex = reqUri.indexOf(59);
      StringBuffer redirectURL = new StringBuffer();
      if (paramsIndex != -1) {
         redirectURL.append(HttpParsing.ensureEndingSlash(reqUri.substring(0, paramsIndex)));
         redirectURL.append(reqUri.substring(paramsIndex));
      } else {
         redirectURL.append(HttpParsing.ensureEndingSlash(reqUri));
      }

      String queryString = req.getQueryString();
      if (queryString != null) {
         redirectURL.append('?').append(queryString);
      }

      return redirectURL;
   }

   private static String getURI(HttpServletRequest req) {
      String sPath = (String)req.getAttribute("javax.servlet.include.servlet_path");
      String pInfo = (String)req.getAttribute("javax.servlet.include.path_info");
      if (sPath == null && pInfo == null) {
         sPath = req.getServletPath();
         pInfo = req.getPathInfo();
      }

      String URI = null;
      if (sPath != null) {
         URI = sPath;
         if (pInfo != null) {
            URI = sPath + pInfo;
         }
      } else if (pInfo != null) {
         URI = pInfo;
      }

      if (URI == null) {
         URI = "";
      }

      return URI;
   }

   private WarSource getSource(String URI) {
      return this.context.getResourceAsSourceWithMDS(URI);
   }

   private boolean isModified(HttpServletRequest req, HttpServletResponse res, WarSource src) throws IOException {
      if (req.getAttribute("javax.servlet.include.request_uri") != null) {
         return true;
      } else {
         long lastModified = src.lastModified();
         lastModified -= lastModified % 1000L;
         if (lastModified == 0L) {
            if (DEBUG_URL_RES.isDebugEnabled()) {
               DEBUG_URL_RES.debug(this.context.getLogContext() + ": Couldn't find resource for URI: " + req.getRequestURI() + " - sending 404");
            }

            res.sendError(404);
            return false;
         } else {
            long ifModifiedSince = -1L;

            try {
               ifModifiedSince = req.getDateHeader("If-Modified-Since");
            } catch (IllegalArgumentException var9) {
            }

            boolean stale = false;
            if (this.context.getContextManager().isVersioned()) {
               stale = ifModifiedSince != lastModified;
            } else {
               stale = ifModifiedSince < lastModified;
            }

            if (!stale) {
               if (DEBUG_URL_RES.isDebugEnabled()) {
                  DEBUG_URL_RES.debug(this.context.getLogContext() + ": Source: " + src + " Last-Modified: " + lastModified + " If-Modified-Since : " + ifModifiedSince + " - sending 302");
               }

               res.setStatus(304);
               return false;
            } else {
               res.setHeader("Last-Modified", src.getLastModifiedAsString());
               return true;
            }
         }
      }
   }

   private void sendRangeData(WarSource source, HttpServletRequest req, HttpServletResponse res) throws IOException {
      String contentType = source.getContentType(this.context);
      res.addHeader("Accept-Ranges", "bytes");
      ByteRangeHandler handler = ByteRangeHandler.makeInstance(source, req, contentType);
      if (handler != null) {
         handler.sendRangeData(res);
      }

   }

   private void sendFile(Source src, HttpServletResponse res, boolean isHttp11) throws IOException {
      ServletOutputStream outputStream = this.getOutputStreamIfAvailable(res);
      if (outputStream != null) {
         this.sendFileUsingOutputStream(outputStream, src, res, isHttp11);
      } else {
         this.sendFileUsingWriter(src, res);
      }

   }

   private ServletOutputStream getOutputStreamIfAvailable(HttpServletResponse res) throws IOException {
      try {
         return res.getOutputStream();
      } catch (IllegalStateException var3) {
         return null;
      }
   }

   private void sendFileUsingOutputStream(ServletOutputStream out, Source src, HttpServletResponse res, boolean isHttp11) throws IOException {
      InputStream in = null;

      try {
         in = src.getInputStream();
         if (in == null) {
            res.sendError(404);
            return;
         }

         if (isHttp11) {
            res.addHeader("Accept-Ranges", "bytes");
         }

         try {
            ((ServletOutputStreamImpl)out).writeStream(in);
         } catch (ClassCastException var10) {
            StreamUtils.writeTo(in, out);
         }
      } finally {
         if (in != null) {
            in.close();
         }

      }

   }

   private void sendFileUsingWriter(Source src, HttpServletResponse res) throws IOException {
      InputStream in = null;
      Reader reader = null;
      int buflen = res.getBufferSize();

      try {
         PrintWriter pw = res.getWriter();
         in = src.getInputStream();
         if (in == null) {
            res.sendError(404);
            return;
         }

         String enc = res.getCharacterEncoding();
         if (enc == null) {
            enc = ((WebAppServletContext)this.getServletContext()).getConfigManager().getDefaultEncoding();
         }

         reader = new InputStreamReader(in, enc);
         if (buflen == -1) {
            buflen = 4096;
         }

         char[] data = new char[buflen];
         int read = false;

         int read;
         while((read = reader.read(data)) > 0) {
            pw.write(data, 0, read);
         }
      } finally {
         if (reader != null) {
            reader.close();
         }

         if (in != null) {
            in.close();
         }

      }

   }

   private void produceDirectoryListing(Enumeration srcs, HttpServletRequest rq, HttpServletResponse rp) throws IOException {
      String ianaCharset = CharsetMap.getIANAFromJava(System.getProperty("file.encoding"));
      String contentType = "text/html";
      if (ianaCharset != null) {
         contentType = contentType + "; charset=" + ianaCharset;
      }

      rp.setContentType(contentType);
      ServletOutputStream sos = rp.getOutputStream();
      String uri = ServletRequestImpl.getResolvedURI(rq);
      List sources = null;

      while(true) {
         while(srcs.hasMoreElements()) {
            WarSource src = (WarSource)srcs.nextElement();
            List sourceList = Arrays.asList(src.listSources());
            if (!srcs.hasMoreElements() && sources == null) {
               sources = sourceList;
            } else {
               if (sources == null) {
                  sources = new ArrayList();
               }

               ((List)sources).addAll(sourceList);
            }
         }

         int maxFileNameLength = 24;
         if (((List)sources).size() != 0) {
            Collections.sort((List)sources, new LengthComparator());
            WarSource fileSource = (WarSource)((List)sources).get(0);
            int currentMaxFileNameLength = fileSource.getName().length();
            maxFileNameLength = currentMaxFileNameLength > maxFileNameLength ? currentMaxFileNameLength : maxFileNameLength;
         }

         sos.println("<HTML>\n<HEAD>\n<TITLE>Index of ");
         sos.println(uri);
         sos.println("</TITLE>\n</HEAD>\n<BODY>");
         sos.println("<H1>Index of ");
         sos.println(uri);
         sos.println("</H1><BR>");
         sos.println("<PRE>");
         sos.print("<A HREF=\"?sortby=NAME\">");
         outStr("Name", sos, maxFileNameLength + 5, true);
         sos.print("<A HREF=\"?sortby=LAST_MODIFIED\">");
         outStr("Last Modified", sos, 24, true);
         sos.print("<A HREF=\"?sortby=SIZE\">");
         outStr("Size", sos, 10, true);
         sos.println("<HR>");
         if (!"/".equals(uri) && uri.length() > 2) {
            int lastInd = uri.lastIndexOf(47, uri.length() - 2);
            if (lastInd >= 0) {
               String parent = uri.substring(0, lastInd + 1);
               sos.println("<A HREF=\"" + parent + "\">Parent Directory</A>");
            }
         }

         if (((List)sources).size() == 0) {
            sos.println("</PRE></BODY></HTML>");
            return;
         }

         Comparator comparator = new TypeComparator(this.getComparator(rq));
         Collections.sort((List)sources, comparator);
         Iterator i = ((List)sources).iterator();

         while(true) {
            while(true) {
               if (!i.hasNext()) {
                  sos.println("</PRE></BODY></HTML>");
                  return;
               }

               WarSource s = (WarSource)i.next();
               String name = s.getName();
               Date lastMod = new Date(s.lastModified());
               SimpleDateFormat DIRECTORY_DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy kk:mm");
               if (s.isDirectory()) {
                  if ("WEB-INF".equals(name) || "META-INF".equals(name)) {
                     continue;
                  }

                  sos.print("<A HREF=\"" + name + "/\">");
                  outStr(name, sos, maxFileNameLength + 5, true);
                  outStr(DIRECTORY_DATE_FORMAT.format(lastMod), sos, 24, false);
                  outStr("&lt;DIR&gt;", sos, 10, false);
                  break;
               }

               sos.print("<A HREF=\"" + name + "\">");
               outStr(name, sos, maxFileNameLength + 5, true);
               outStr(DIRECTORY_DATE_FORMAT.format(lastMod), sos, 24, false);
               long len = s.length();
               String length;
               if (len >= 1024L) {
                  length = String.valueOf(len / 1024L) + 'k';
               } else {
                  length = String.valueOf(len);
               }

               outStr(length, sos, 10, false);
               break;
            }

            sos.println();
         }
      }
   }

   private Comparator getComparator(HttpServletRequest rq) {
      String sortby = rq.getParameter("sortby");
      if ("LAST_MODIFIED".equals(sortby)) {
         return new LastModifiedComparator();
      } else if ("SIZE".equals(sortby)) {
         return new SizeComparator();
      } else if ("NAME".equals(sortby)) {
         return new NameComparator();
      } else {
         sortby = this.context.getConfigManager().getIndexDirectorySortBy();
         if ("LAST_MODIFIED".equals(sortby)) {
            return new LastModifiedComparator();
         } else {
            return (Comparator)("SIZE".equals(sortby) ? new SizeComparator() : new NameComparator());
         }
      }
   }

   private static void outStr(String s, ServletOutputStream sos, int maxLen, boolean closeLink) throws IOException {
      int i;
      for(i = 0; i < s.length() && i < maxLen; ++i) {
         sos.print(s.charAt(i));
      }

      if (closeLink) {
         sos.print("</A>");
      }

      while(i < maxLen) {
         sos.write(32);
         ++i;
      }

   }

   public static native void transmitFile(String var0, long var1, int var3) throws IOException;

   private static final class LengthComparator extends NameComparator {
      private LengthComparator() {
         super(null);
      }

      public int compare(Object element1, Object element2) {
         WarSource s1 = (WarSource)element1;
         WarSource s2 = (WarSource)element2;
         long diff = (long)(s1.getName().length() - s2.getName().length());
         return diff <= 0L ? 1 : -1;
      }

      // $FF: synthetic method
      LengthComparator(Object x0) {
         this();
      }
   }

   private static final class TypeComparator implements Comparator {
      private final Comparator tieBreaker;

      TypeComparator(Comparator delegate) {
         this.tieBreaker = delegate;
      }

      public int compare(Object element1, Object element2) {
         WarSource s1 = (WarSource)element1;
         WarSource s2 = (WarSource)element2;
         boolean b1 = s1.isDirectory();
         boolean b2 = s2.isDirectory();
         if (b1 == b2) {
            return this.tieBreaker.compare(s1, s2);
         } else {
            return b1 ? -1 : 1;
         }
      }
   }

   private static final class SizeComparator extends NameComparator {
      private SizeComparator() {
         super(null);
      }

      public int compare(Object element1, Object element2) {
         WarSource s1 = (WarSource)element1;
         WarSource s2 = (WarSource)element2;
         if (s1.isDirectory()) {
            return super.compare(s1, s2);
         } else {
            long diff = s1.length() - s2.length();
            if (diff < 0L) {
               return -1;
            } else {
               return diff > 0L ? 1 : super.compare(s1, s2);
            }
         }
      }

      // $FF: synthetic method
      SizeComparator(Object x0) {
         this();
      }
   }

   private static final class LastModifiedComparator extends NameComparator {
      private LastModifiedComparator() {
         super(null);
      }

      public int compare(Object element1, Object element2) {
         Source s1 = (Source)element1;
         Source s2 = (Source)element2;
         long diff = s1.lastModified() - s2.lastModified();
         if (diff < 0L) {
            return -1;
         } else {
            return diff > 0L ? 1 : super.compare(s1, s2);
         }
      }

      // $FF: synthetic method
      LastModifiedComparator(Object x0) {
         this();
      }
   }

   private static class NameComparator implements Comparator {
      private final Collator collator;

      private NameComparator() {
         this.collator = Collator.getInstance();
      }

      public int compare(Object element1, Object element2) {
         String s1 = ((WarSource)element1).getName();
         String s2 = ((WarSource)element2).getName();
         CollationKey key1 = this.collator.getCollationKey(s1);
         CollationKey key2 = this.collator.getCollationKey(s2);
         return key1.compareTo(key2);
      }

      // $FF: synthetic method
      NameComparator(Object x0) {
         this();
      }
   }
}
