package weblogic.servlet.proxy;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Properties;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import weblogic.version;
import weblogic.security.SSL.SSLContext;
import weblogic.security.SSL.SSLSocketFactory;
import weblogic.security.internal.SerializedSystemIni;
import weblogic.security.internal.encryption.ClearOrEncryptedService;
import weblogic.security.internal.encryption.EncryptionService;
import weblogic.servlet.internal.ChunkInput;
import weblogic.servlet.internal.ServletRequestImpl;
import weblogic.servlet.internal.ServletResponseImpl;
import weblogic.servlet.internal.WebAppServletContext;
import weblogic.timers.NakedTimerListener;
import weblogic.timers.Timer;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;
import weblogic.utils.StringUtils;
import weblogic.utils.encoders.BASE64Encoder;
import weblogic.utils.io.Chunk;
import weblogic.work.WorkManagerFactory;

abstract class GenericProxyServlet extends HttpServlet {
   public static final String EOL = "\r\n";
   private static final String HEADER_SEPARATOR = ": ";
   private static final String HEADER_KEEP_ALIVE = "Connection: Keep-Alive\r\n";
   public static final String WEBLOGIC_BRIDGE_CONFIG_INFO = "__WebLogicBridgeConfig";
   private WebAppServletContext servletContext;
   public static final int MAX_POST_IN_MEMORY;
   protected String destHost;
   protected int destPort;
   protected String pathTrim;
   protected String pathPrepend;
   protected String defaultFileName;
   protected String trimExt;
   protected boolean verbose;
   protected boolean debugConfigInfo;
   protected boolean keepAliveEnabled;
   protected int keepAliveSecs;
   protected boolean isSecureProxy;
   protected String keyStore;
   protected String keyStoreType;
   protected String privateKeyAlias;
   protected String keyStorePasswordProperties;
   protected int socketTimeout;
   protected int maxPostSize = -1;
   protected boolean wlProxySSL;
   protected boolean wlProxySSLPassThrough = false;
   protected boolean wlProxyPassThrough = false;
   protected String cookieName;
   protected String wlCookieName;
   protected String logFileName;
   protected String httpVersion;
   protected boolean fileCaching;
   protected ProxyConnectionPool connPool;
   protected boolean inited = false;
   protected Object syncObj = new Object();
   private PrintStream out;
   private EncryptionService es = null;
   private ClearOrEncryptedService ces = null;
   private String encryptedKeyStorePasswd = null;
   private String encryptedPrivateKeyPasswd = null;

   public void init(ServletConfig sc) throws ServletException {
      if (!this.inited) {
         super.init(sc);
         this.servletContext = (WebAppServletContext)sc.getServletContext();
         this.destHost = this.getInitParameter("WebLogicHost");
         String param = this.getInitParameter("WebLogicPort");
         if (param != null) {
            this.destPort = Integer.parseInt(param);
         }

         this.pathTrim = this.getInitParameter("PathTrim");
         if (this.pathTrim == null) {
            this.pathTrim = this.getInitParameter("pathTrim");
         }

         if (this.pathTrim != null && this.pathTrim.charAt(0) != '/') {
            this.pathTrim = "/" + this.pathTrim;
         }

         this.defaultFileName = this.getInitParameter("DefaultFileName");
         if (this.defaultFileName != null && this.defaultFileName.charAt(0) != '/') {
            this.defaultFileName = "/" + this.defaultFileName;
         }

         this.pathPrepend = this.getInitParameter("PathPrepend");
         if (this.pathPrepend == null) {
            this.pathPrepend = this.getInitParameter("pathPrepend");
         }

         if (this.pathPrepend != null && this.pathPrepend.charAt(0) != '/') {
            this.pathPrepend = "/" + this.pathPrepend;
         }

         this.trimExt = this.getInitParameter("trimExt");
         if (this.trimExt == null) {
            this.trimExt = this.getInitParameter("TrimExt");
         }

         if (this.trimExt != null && this.trimExt.charAt(0) != '.') {
            this.trimExt = "." + this.trimExt;
         }

         param = this.getInitParameter("FileCaching");
         this.fileCaching = isTrue(param, true);
         param = this.getInitParameter("Debug");
         if (param == null) {
            param = this.getInitParameter("verbose");
         }

         this.verbose = isTrue(param, false);
         param = this.getInitParameter("DebugConfigInfo");
         this.debugConfigInfo = isTrue(param, false);
         param = this.getInitParameter("KeepAliveEnabled");
         this.keepAliveEnabled = isTrue(param, true);
         param = this.getInitParameter("KeepAliveSecs");
         if (param == null) {
            this.keepAliveSecs = 20;
         } else {
            this.keepAliveSecs = Integer.parseInt(param);
         }

         param = this.getInitParameter("WLIOTimeoutSecs");
         if (param == null) {
            this.socketTimeout = 300;
         } else {
            this.socketTimeout = Integer.parseInt(param);
         }

         if (param == null) {
            param = this.getInitParameter("HungServerRecoverSecs");
            if (param != null) {
               this.socketTimeout = Integer.parseInt(param);
            }
         }

         param = this.getInitParameter("SecureProxy");
         this.isSecureProxy = isTrue(param, false);
         if (this.isSecureProxy) {
            this.keyStore = this.getInitParameter("KeyStore");
            this.keyStoreType = this.getInitParameter("KeyStoreType");
            this.privateKeyAlias = this.getInitParameter("PrivateKeyAlias");
            this.keyStorePasswordProperties = this.getInitParameter("KeyStorePasswordProperties");
            if (this.keyStorePasswordProperties != null) {
               this.es = SerializedSystemIni.getExistingEncryptionService();
               this.ces = new ClearOrEncryptedService(this.es);
               InputStream is = this.servletContext.getResourceAsStream(this.keyStorePasswordProperties);

               try {
                  Properties p = new Properties();
                  p.load(is);
                  this.encryptedKeyStorePasswd = p.getProperty("KeyStorePassword");
                  this.encryptedPrivateKeyPasswd = p.getProperty("PrivateKeyPassword");
               } catch (IOException var6) {
                  throw new ServletException("Cannot load keyStorePasswordProperties");
               }
            }
         }

         param = this.getInitParameter("MaxPostSize");
         if (param != null) {
            this.maxPostSize = Integer.parseInt(param);
         }

         this.cookieName = this.getInitParameter("CookieName");
         if (this.cookieName == null) {
            this.cookieName = this.getInitParameter("cookieName");
         }

         this.wlCookieName = this.getInitParameter("WLCookieName");
         if (this.wlCookieName == null) {
            this.wlCookieName = this.cookieName;
         }

         if (this.wlCookieName == null) {
            this.wlCookieName = "JSESSIONID";
         }

         this.logFileName = this.getInitParameter("WLLogFile");
         File logFile;
         if (this.logFileName != null) {
            logFile = new File(this.logFileName);
         } else {
            String os_name = System.getProperty("os.name");
            if (os_name != null && os_name.startsWith("Windows")) {
               logFile = new File("c:/temp/wlproxy.log");
            } else {
               logFile = new File("/tmp/wlproxy.log");
            }

            logFile.getParentFile().mkdirs();
         }

         try {
            this.out = new PrintStream(new FileOutputStream(logFile, true));
         } catch (FileNotFoundException var5) {
            throw new ServletException("Cannot open file: " + logFile.getAbsolutePath(), var5);
         }

         param = this.getInitParameter("WLProxySSL");
         this.wlProxySSL = isTrue(param, false);
         param = this.getInitParameter("WLProxySSLPassThrough");
         this.wlProxySSLPassThrough = isTrue(param, false);
         param = this.getInitParameter("WLProxyPassThrough");
         this.wlProxyPassThrough = isTrue(param, false);
         this.initConnectionPool();
         if (this.cookieName != null) {
            this.trace("Warning: CookieName is deprecated and replaced by WLCookieName");
         }

         if (this.verbose) {
            this.trace("GenericProxyServelt: init()");
         }

         this.inited = true;
      }
   }

   public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String queryStr = request.getQueryString();
      if (queryStr != null && this.debugConfigInfo && queryStr.equals("__WebLogicBridgeConfig")) {
         this.printConfigInfo(response.getWriter());
      } else {
         int retries = 1;
         ProxyConnection con = null;
         PostDataCache postData = new PostDataCache();
         postData.readPostData(request);
         if (postData.error) {
            if (this.verbose) {
               this.trace("Failed to read the post data.");
            }

            postData.release();
            throw new IOException("Invalid Post");
         } else {
            try {
               while(true) {
                  CharArrayWriter out;
                  try {
                     if (retries != 0) {
                        con = this.connPool.getProxyConnection(this.destHost, this.destPort, this.isSecureProxy, this.socketTimeout);
                     } else {
                        con = this.connPool.getNewProxyConnection(this.destHost, this.destPort, this.isSecureProxy, this.socketTimeout);
                     }

                     this.sendRequest(request, con, postData);
                     this.sendResponse(request, response, con);
                     this.connPool.requeue(con);
                     break;
                  } catch (HalfOpenSocketRetryException var14) {
                     this.connPool.remove(con);
                     if (this.verbose) {
                        out = new CharArrayWriter();
                        var14.printStackTrace(new PrintWriter(out));
                        this.trace(out.toString());
                     }

                     if (retries-- <= 0) {
                        if (!response.isCommitted()) {
                           response.sendError(503, "Unable to connect to server");
                        }
                        break;
                     }

                     if (this.verbose) {
                        this.trace("Doing retry.");
                     }
                  } catch (weblogic.servlet.internal.WriteClientIOException var15) {
                     this.connPool.remove(con);
                     break;
                  } catch (IOException var16) {
                     if (con != null) {
                        this.connPool.remove(con);
                        if (!response.isCommitted()) {
                           response.sendError(500, "Internal Server Error");
                        }
                     } else {
                        response.sendError(503, "Unable to connect to server");
                     }

                     if (this.verbose) {
                        out = new CharArrayWriter();
                        var16.printStackTrace(new PrintWriter(out));
                        this.trace(out.toString());
                     }
                     break;
                  }
               }
            } finally {
               postData.release();
            }

         }
      }
   }

   public void destroy() {
      if (this.connPool != null) {
         synchronized(this.syncObj) {
            if (this.connPool != null) {
               this.connPool.destroy();
               this.connPool = null;
            }
         }
      }
   }

   protected static boolean isTrue(String s, boolean defaultValue) {
      if (s == null) {
         return defaultValue;
      } else {
         return s.equalsIgnoreCase("ON") || s.equalsIgnoreCase("true");
      }
   }

   protected void trace(String s) {
      StringBuilder sb = new StringBuilder(512);
      Date now = new Date(System.currentTimeMillis());
      sb.append("<");
      sb.append(now.toString());
      sb.append(">");
      sb.append(' ').append('<');
      sb.append(Thread.currentThread().getName());
      sb.append('>');
      sb.append(": ");
      sb.append(s);
      String outStr = sb.toString();
      this.out.println(outStr);
      this.out.flush();
   }

   protected void printConfigInfo(PrintWriter out) {
      out.write("<HTML><TITLE>WEBLOGIC PROXY DEBUG INFO</TITLE>");
      out.write("<FONT FACE=\"Tahoma\">");
      out.write("<BODY>Query String: __WebLogicBridgeConfig");
      out.write("<BR><BR><B>WebLogicHost:</B> <FONT COLOR=\"#0000ff\">" + this.destHost + "</FONT>");
      out.write("<BR><B>WebLogicPort:</B> <FONT COLOR=\"#0000ff\">" + this.destPort + "</FONT>");
      if (this.cookieName != null) {
         out.write("<BR><B>CookieName: </B><font color=#0000ff> deprecated</font>");
      }

      out.write("<BR><B>WLCookieName: </B>" + this.wlCookieName);
      out.write("<BR><B>Debug: </B>" + this.verbose);
      out.write("<BR><B>DebugConfigInfo: </B>" + this.debugConfigInfo);
      out.write("<BR><B>DefaultFileName: </B>" + this.defaultFileName);
      out.write("<BR><B>FileCaching: </B>" + this.fileCaching);
      out.write("<BR><B>WLIOTimeoutSecs: </B>" + this.socketTimeout);
      out.write("<BR><B>KeepAliveEnabled: </B>" + this.keepAliveEnabled);
      out.write("<BR><B>KeepAliveSecs: </B>" + this.keepAliveSecs);
      out.write("<BR><B>MaxPostSize: </B>" + this.maxPostSize);
      out.write("<BR><B>PathPrepend: </B>" + this.pathPrepend);
      out.write("<BR><B>PathTrim: </B>" + this.pathTrim);
      out.write("<BR><B>TrimExt: </B>" + this.trimExt);
      out.write("<BR><B>SecureProxy: </B>" + this.isSecureProxy);
      if (this.isSecureProxy) {
         out.write("<BR><B>KeyStore: </B>" + this.keyStore);
         out.write("<BR><B>KeyStoreType: </B>" + this.keyStoreType);
         out.write("<BR><B>PrivateKeyAlias: </B>" + this.privateKeyAlias);
         out.write("<BR><B>KeyStorePasswordProperties: </B>" + this.keyStorePasswordProperties);
      }

      out.write("<BR><B>WLLogFile: </B>" + this.logFileName);
      out.write("<BR><B>WLProxySSL: </B>" + this.wlProxySSL);
      out.write("<BR><B>WLProxySSLPassThrough: </B>" + this.wlProxySSLPassThrough);
      out.write("<BR><B>WLProxyPassThrough: </B>" + this.wlProxyPassThrough);
      out.write("<BR>_____________________________________________________");
      out.write("<BR><BR>Last Modified: " + version.getBuildVersion());
      out.write("</BODY></HTML>");
      out.close();
   }

   protected void initConnectionPool() {
      if (this.connPool == null) {
         synchronized(this.syncObj) {
            if (this.connPool == null) {
               this.connPool = new ProxyConnectionPool(50);
            }
         }
      }
   }

   public void sendRequest(HttpServletRequest request, ProxyConnection con) throws IOException {
      this.sendRequest(request, con, (PostDataCache)null);
   }

   private void sendRequest(HttpServletRequest request, ProxyConnection con, PostDataCache postData) throws IOException {
      String firstLine = this.resolveRequest(request);
      BufferedOutputStream out = new BufferedOutputStream(con.getSocket().getOutputStream());
      PrintStream headerOut = new PrintStream(out);
      InputStream pin = null;
      if (postData != null) {
         pin = postData.getInputStream();
      }

      Chunk c = null;

      try {
         headerOut.print(firstLine);
         this.sendRequestHeaders(request, headerOut, (Object)null, (Object)null);
         ServletRequestImpl requestImpl = ServletRequestImpl.getOriginalRequest(request);
         long len = requestImpl.getContentLengthLong();
         InputStream in = pin != null ? pin : request.getInputStream();
         if (len > 0L) {
            c = Chunk.getChunk();

            int lenRead;
            while((lenRead = ((InputStream)in).read(c.buf, 0, c.buf.length)) != -1) {
               try {
                  out.write(c.buf, 0, lenRead);
                  out.flush();
               } catch (SocketException var23) {
                  if (postData != null && postData.type != 0) {
                     throw new HalfOpenSocketRetryException(var23);
                  }
               } catch (IOException var24) {
                  throw var24;
               }

               len -= (long)lenRead;
               if (len < 1L) {
                  break;
               }
            }

            if (len != 0L) {
               throw new IOException("Failed to read " + requestImpl.getContentLengthLong() + " bytes from the inputStream");
            }
         } else if (len == -1L) {
            String chunkEnc = request.getHeader("Transfer-Encoding");
            if (chunkEnc != null && chunkEnc.equalsIgnoreCase("Chunked")) {
               throw new IOException("Can't process chunked request.");
            }
         }
      } finally {
         if (c != null) {
            Chunk.releaseChunk(c);
         }

         if (pin != null) {
            try {
               pin.close();
            } catch (IOException var22) {
            }
         }

      }

   }

   protected void sendRequestHeaders(HttpServletRequest request, PrintStream headerOut, Object o1, Object o2) {
      if (this.verbose) {
         this.trace("In-bound headers: ");
      }

      StringBuilder sb = new StringBuilder(256);
      Enumeration names = request.getHeaderNames();
      HashSet sentHeahers = new HashSet();

      while(true) {
         String name;
         do {
            do {
               if (!names.hasMoreElements()) {
                  headerOut.print("Connection: Keep-Alive\r\n");
                  this.addRequestHeaders(request, headerOut, o1, o2);
                  headerOut.print("\r\n");
                  headerOut.flush();
                  return;
               }

               name = (String)names.nextElement();
            } while(this.ignoreHeader(name));
         } while(sentHeahers.contains(name));

         sentHeahers.add(name);
         Enumeration values = request.getHeaders(name);

         while(values.hasMoreElements()) {
            sb.append(name).append(": ").append((String)values.nextElement());
            if (this.verbose) {
               this.trace(sb.toString());
            }

            sb.append("\r\n");
            headerOut.print(sb.toString());
            sb.delete(0, sb.length());
         }
      }
   }

   private boolean ignoreHeader(String name) {
      if ("Expect".equalsIgnoreCase(name)) {
         return true;
      } else if ("Transfer-Encoding".equalsIgnoreCase(name)) {
         return true;
      } else if ("Connection".equalsIgnoreCase(name)) {
         return true;
      } else if (("Proxy-Remote-User".equalsIgnoreCase(name) || "Proxy-Auth-Type".equalsIgnoreCase(name)) && !this.wlProxyPassThrough) {
         return true;
      } else {
         return "WL-Proxy-SSL".equalsIgnoreCase(name);
      }
   }

   protected void addRequestHeaders(HttpServletRequest request, PrintStream headerOut, Object o1, Object o2) {
      StringBuilder buf = new StringBuilder(21);
      buf.append("WL-Proxy-SSL").append(": ");
      if ("https".equalsIgnoreCase(request.getScheme())) {
         buf.append("true");
      } else if (this.wlProxySSLPassThrough) {
         String clientHeader = request.getHeader("WL-Proxy-SSL");
         if (clientHeader == null) {
            buf.append(this.wlProxySSL ? "true" : "false");
         } else {
            buf.append(isTrue(clientHeader, false) ? "true" : "false");
         }
      } else {
         buf.append(this.wlProxySSL ? "true" : "false");
      }

      buf.append("\r\n");
      headerOut.print(buf.toString());
      if (this.pathTrim != null) {
         headerOut.print("WL-PATH-TRIM: " + this.pathTrim);
         headerOut.print("\r\n");
      }

      if (this.pathPrepend != null) {
         headerOut.print("WL-PATH-PREPEND: " + this.pathPrepend);
         headerOut.print("\r\n");
      }

      headerOut.print("X-WebLogic-KeepAliveSecs: " + (this.keepAliveSecs + 10));
      headerOut.print("\r\n");
      X509Certificate[] certs = (X509Certificate[])((X509Certificate[])request.getAttribute("javax.servlet.request.X509Certificate"));
      if (certs != null) {
         headerOut.print("WL-Proxy-Client-Cert: ");
         ByteArrayOutputStream baos = new ByteArrayOutputStream();

         CharArrayWriter err;
         try {
            for(int i = 0; i < certs.length; ++i) {
               baos.write(certs[i].getEncoded());
            }

            byte[] tmp = baos.toByteArray();
            (new BASE64Encoder()).encodeBuffer(new ByteArrayInputStream(tmp), headerOut);
         } catch (CertificateEncodingException var10) {
            if (this.verbose) {
               err = new CharArrayWriter();
               var10.printStackTrace(new PrintWriter(err));
               this.trace("Error while dumping ssl certificate to ByteArrayOutputStream : " + err.toString());
            }
         } catch (IOException var11) {
            if (this.verbose) {
               err = new CharArrayWriter();
               var11.printStackTrace(new PrintWriter(err));
               this.trace("Error while encoding ssl certificate : " + err.toString());
            }
         }

         headerOut.print("\r\n");
      }

      headerOut.print("WL-Proxy-Client-IP: " + request.getRemoteAddr());
      headerOut.print("\r\n");
   }

   protected int readStatus(HttpServletRequest request, HttpServletResponse response, DataInputStream in) throws IOException {
      return this.readStatus(request, response, in, false);
   }

   protected int readStatus(HttpServletRequest request, HttpServletResponse response, DataInputStream in, boolean recycled) throws IOException {
      String statusLine = null;

      try {
         statusLine = in.readLine();
      } catch (SocketException var9) {
         if (recycled) {
            throw new HalfOpenSocketRetryException(var9);
         }
      } catch (IOException var10) {
         throw var10;
      }

      if (statusLine == null) {
         if (recycled) {
            throw new HalfOpenSocketRetryException("status line is null");
         } else {
            throw new IOException("status line is null");
         }
      } else {
         if (this.verbose) {
            this.trace(statusLine);
         }

         try {
            String[] resp = StringUtils.splitCompletely(statusLine, " ");
            int status = Integer.parseInt(resp[1]);
            response.setStatus(status);
            return status;
         } catch (IndexOutOfBoundsException var8) {
            throw new IOException("malformed status line");
         }
      }
   }

   public void sendResponse(HttpServletRequest request, HttpServletResponse response, ProxyConnection con) throws IOException {
      DataInputStream in = new DataInputStream(new BufferedInputStream(con.getSocket().getInputStream(), 100));
      int status = this.readStatus(request, response, in, con.canRecycle());
      int contentLength = this.sendResponseHeaders(response, in, con, (Object)null);
      if (status == 100) {
         status = this.readStatus(request, response, in);
         contentLength = this.sendResponseHeaders(response, in, con, (Object)null);
      }

      if (status != 204 && status != 304 && contentLength != 0 && !"HEAD".equalsIgnoreCase(request.getMethod())) {
         OutputStream os = response.getOutputStream();
         if (contentLength == -9999) {
            if (ChunkInput.readCTE(os, in, true) == -1) {
               con.setCanRecycle(false);
            }
         } else {
            this.readAndWriteResponseData(in, os, contentLength);
         }

      }
   }

   void readAndWriteResponseData(DataInputStream is, OutputStream os, int contentLength) throws IOException {
      boolean needToWrite = true;
      Chunk c = Chunk.getChunk();

      try {
         int bytesRead;
         if (contentLength > 0) {
            int bytesLeft = contentLength;

            while((bytesRead = is.read(c.buf, 0, Math.min(bytesLeft, c.buf.length))) != -1) {
               if (needToWrite) {
                  try {
                     os.write(c.buf, 0, bytesRead);
                  } catch (IOException var14) {
                     needToWrite = false;
                     throw new weblogic.servlet.internal.WriteClientIOException("Error in writing to client");
                  }
               }

               bytesLeft -= bytesRead;
               if (bytesLeft < 1) {
                  break;
               }
            }

            if (bytesLeft != 0) {
               throw new IOException("Failed to read " + contentLength + " bytes from the inputStream");
            }
         } else {
            while((bytesRead = is.read(c.buf)) != -1) {
               if (needToWrite) {
                  try {
                     os.write(c.buf, 0, bytesRead);
                     os.flush();
                  } catch (IOException var13) {
                     needToWrite = false;
                     throw new weblogic.servlet.internal.WriteClientIOException("Error in writing to client");
                  }
               }
            }
         }
      } finally {
         Chunk.releaseChunk(c);
      }

   }

   protected int sendResponseHeaders(HttpServletResponse response, DataInputStream in, ProxyConnection con, Object o) throws IOException {
      int length = -1;
      boolean isKeepAlive = false;
      if (this.verbose) {
         this.trace("Out-bound headers: ");
      }

      String header;
      while((header = in.readLine()) != null && header.length() > 0) {
         in.mark(1);
         int x = in.read();
         in.reset();
         if (x == 32 || x == 9) {
            do {
               do {
                  header = header + "\r\n" + in.readLine();
                  in.mark(1);
                  x = in.read();
                  in.reset();
               } while(x == 32);
            } while(x == 9);
         }

         String[] kv = StringUtils.split(header, ':');
         String name = kv[0].trim();
         String value = kv[1].trim();
         if (this.verbose) {
            this.trace(name + ": " + value);
         }

         if (name.equalsIgnoreCase("Set-Cookie")) {
            response.addHeader(name, value);
         } else if (name.equalsIgnoreCase("Transfer-Encoding") && value.equalsIgnoreCase("chunked")) {
            length = -9999;
         } else if (name.equalsIgnoreCase("Content-Length")) {
            if (length != -9999) {
               length = Integer.parseInt(value);
            }

            if (response instanceof ServletResponseImpl) {
               ((ServletResponseImpl)response).setHeaderInternal(name, value);
            } else {
               response.setHeader(name, value);
            }
         } else if (name.equalsIgnoreCase("connection")) {
            if (value.equalsIgnoreCase("close")) {
               con.setCanRecycle(false);
               ServletResponseImpl res = null;
               if (response instanceof ServletResponseImpl) {
                  res = (ServletResponseImpl)response;
               } else {
                  res = ServletResponseImpl.getOriginalResponse(response);
               }

               res.disableKeepAlive();
            } else {
               isKeepAlive = true;
               con.setCanRecycle(true);
            }
         } else {
            if (name.equalsIgnoreCase("X-WebLogic-KeepAliveSecs")) {
               try {
                  int val = Integer.parseInt(value);
                  if (this.keepAliveSecs > val - 10) {
                     this.keepAliveSecs = val - 10;
                  }
                  continue;
               } catch (Exception var13) {
               }
            }

            this.addResponseHeaders(response, name, value, o);
         }
      }

      return length;
   }

   protected void addResponseHeaders(HttpServletResponse response, String name, String value, Object o) {
      if (response instanceof ServletResponseImpl) {
         ((ServletResponseImpl)response).addHeaderInternal(name, value);
      } else {
         response.addHeader(name, value);
      }

   }

   protected String resolveRequest(HttpServletRequest request) {
      String uri = (String)request.getAttribute("javax.servlet.include.request_uri");
      if (uri == null) {
         uri = request.getRequestURI();
      }

      int index;
      int pathTrimSize;
      if (this.trimExt != null) {
         index = uri.indexOf(this.trimExt);
         if (index > -1) {
            pathTrimSize = uri.toLowerCase().indexOf(";" + this.wlCookieName.toLowerCase() + "=");
            if (pathTrimSize != -1) {
               uri = uri.substring(0, index) + uri.substring(pathTrimSize);
            } else {
               uri = uri.substring(0, index);
            }
         }
      }

      if (this.pathTrim != null) {
         index = uri.indexOf(this.pathTrim);
         if (index > -1) {
            pathTrimSize = this.pathTrim.length();
            uri = uri.substring(0, index) + uri.substring(index + pathTrimSize);
         }
      }

      if ((uri.length() == 0 || uri.equals("/")) && this.defaultFileName != null) {
         uri = uri + this.defaultFileName;
      }

      uri = this.pathPrepend == null ? uri : this.pathPrepend + uri;
      String query = request.getQueryString();
      ServletRequestImpl req = ServletRequestImpl.getOriginalRequest(request);
      String encodedSessionID = req.getSessionHelper().getEncodedSessionID();
      if (encodedSessionID != null) {
         uri = uri + ";" + this.wlCookieName + "=" + encodedSessionID;
      }

      if (query != null && !query.equals("")) {
         uri = uri + "?" + query;
      }

      StringBuilder sb = new StringBuilder(256);
      sb.append(request.getMethod());
      sb.append(" ");
      sb.append(uri);
      sb.append(" ");
      if (this.httpVersion != null) {
         sb.append("HTTP/");
         sb.append(this.httpVersion);
      } else {
         sb.append(request.getProtocol());
      }

      sb.append("\r\n");
      String firstLine = sb.toString();
      if (this.verbose) {
         this.trace("===New Request===" + firstLine);
      }

      return firstLine;
   }

   protected Chunk readPostDataToMemory(HttpServletRequest request, int len) throws IOException {
      Chunk postData = Chunk.getChunk();

      try {
         Chunk.chunk(postData, request.getInputStream(), len);
      } catch (IOException var5) {
         Chunk.releaseChunks(postData);
         throw var5;
      }

      if (this.verbose) {
         StringBuilder sb = new StringBuilder(100);
         sb.append("Declared content-length = ");
         sb.append(len);
         sb.append("; Actually read ");
         sb.append(Chunk.size(postData));
         this.trace(sb.toString());
      }

      if (len != Chunk.size(postData)) {
         Chunk.releaseChunks(postData);
         return null;
      } else {
         return postData;
      }
   }

   protected File readPostDataToFile(HttpServletRequest request, long len) throws IOException {
      File f = null;
      FileOutputStream fos = null;
      Chunk postData = Chunk.getChunk();

      try {
         f = File.createTempFile("proxy", (String)null, (File)null);
         ServletInputStream in;
         if (f == null) {
            in = null;
            return in;
         } else {
            fos = new FileOutputStream(f);

            int lenRead;
            for(in = request.getInputStream(); len > 0L; len -= (long)lenRead) {
               lenRead = in.read(postData.buf, 0, postData.buf.length);
               if (lenRead < 0) {
                  StringBuilder sb;
                  if (this.verbose) {
                     sb = new StringBuilder(100);
                     sb.append("Declared content-length = ");
                     sb.append(len);
                     sb.append("; Actually read ");
                     sb.append(lenRead);
                     this.trace(sb.toString());
                  }

                  try {
                     if (fos != null) {
                        fos.close();
                     }

                     if (f != null) {
                        f.delete();
                     }
                  } catch (Exception var16) {
                  }

                  sb = null;
                  return sb;
               }

               fos.write(postData.buf, 0, lenRead);
            }

            fos.flush();
            fos.close();
            return f;
         }
      } catch (IOException var17) {
         try {
            if (fos != null) {
               fos.close();
            }

            if (f != null) {
               f.delete();
            }
         } catch (Exception var15) {
         }

         throw var17;
      } finally {
         Chunk.releaseChunk(postData);
      }
   }

   static {
      MAX_POST_IN_MEMORY = Chunk.CHUNK_SIZE;
   }

   class PostDataCache {
      protected static final int NO_CACHE = 0;
      protected static final int MEMORY_CACHE = 1;
      protected static final int FILE_CACHE = 2;
      protected int type = 0;
      protected boolean error;
      private Chunk postData;
      private File postDataFile;
      private boolean alreadyRead;

      public void readPostData(HttpServletRequest request) {
         if (!this.alreadyRead) {
            long len = ServletRequestImpl.getOriginalRequest(request).getContentLengthLong();

            try {
               if (GenericProxyServlet.this.maxPostSize > 0 && len > (long)GenericProxyServlet.this.maxPostSize) {
                  if (GenericProxyServlet.this.verbose) {
                     GenericProxyServlet.this.trace("Content Length exceeded the MaxPostSize: " + GenericProxyServlet.this.maxPostSize);
                  }

                  this.error = true;
               } else if (len > 0L && len <= (long)GenericProxyServlet.MAX_POST_IN_MEMORY) {
                  this.type = 1;
                  this.postData = GenericProxyServlet.this.readPostDataToMemory(request, (int)len);
                  if (this.postData == null) {
                     this.error = true;
                  }
               } else if (len > (long)GenericProxyServlet.MAX_POST_IN_MEMORY && GenericProxyServlet.this.fileCaching) {
                  this.type = 2;
                  this.postDataFile = GenericProxyServlet.this.readPostDataToFile(request, len);
                  if (this.postDataFile == null) {
                     this.error = true;
                  }
               }
            } catch (IOException var9) {
               this.error = true;
               if (GenericProxyServlet.this.verbose) {
                  CharArrayWriter out = new CharArrayWriter();
                  var9.printStackTrace(new PrintWriter(out));
                  GenericProxyServlet.this.trace("Failed to read post data: " + out.toString());
               }
            } finally {
               this.alreadyRead = true;
            }
         }

      }

      public InputStream getInputStream() throws IOException {
         if (this.error) {
            return null;
         } else {
            InputStream in = null;
            switch (this.type) {
               case 0:
               default:
                  break;
               case 1:
                  in = new ByteArrayInputStream(this.postData.buf, 0, this.postData.end);
                  break;
               case 2:
                  in = new FileInputStream(this.postDataFile);
            }

            return (InputStream)in;
         }
      }

      public void release() {
         switch (this.type) {
            case 0:
            default:
               break;
            case 1:
               Chunk.releaseChunks(this.postData);
               break;
            case 2:
               if (this.postDataFile != null) {
                  if (GenericProxyServlet.this.verbose) {
                     GenericProxyServlet.this.trace("Remove temp file: " + this.postDataFile.getAbsolutePath());
                  }

                  this.postDataFile.delete();
               }
         }

      }
   }

   class ProxyConnectionPool implements NakedTimerListener {
      private final TimerManager timerManager;
      private Timer timer;
      private ArrayList pool;

      ProxyConnectionPool(int size) {
         this.timerManager = TimerManagerFactory.getTimerManagerFactory().getTimerManager("ProxyConnectionPool-ContextPath='" + GenericProxyServlet.this.servletContext.getContextPath() + (GenericProxyServlet.this.servletContext.getVersionId() != null ? "'-Version#" + GenericProxyServlet.this.servletContext.getVersionId() : ""), WorkManagerFactory.getInstance().getSystem());
         this.pool = new ArrayList(size);
         this.startTimer();
      }

      public void destroy() {
         if (GenericProxyServlet.this.verbose) {
            GenericProxyServlet.this.trace("Destroy the connection pool");
         }

         this.stopTimer();
         synchronized(this.pool) {
            int j = this.pool.size() - 1;

            while(true) {
               if (j < 0) {
                  break;
               }

               ProxyConnection con = (ProxyConnection)this.pool.get(j);
               con.close();
               this.pool.remove(j);
               --j;
            }
         }

         GenericProxyServlet.this.out.close();
      }

      public void remove(ProxyConnection con) {
         if (con != null) {
            con.close();
            if (GenericProxyServlet.this.keepAliveEnabled) {
               if (GenericProxyServlet.this.verbose) {
                  GenericProxyServlet.this.trace("Remove connection from pool: " + con);
               }

               synchronized(this.pool) {
                  this.pool.remove(con);
               }
            }

         }
      }

      public void requeue(ProxyConnection con) {
         if (!con.canRecycle()) {
            if (GenericProxyServlet.this.verbose) {
               GenericProxyServlet.this.trace("Close connection: " + con);
            }

            this.remove(con);
         } else {
            if (GenericProxyServlet.this.keepAliveEnabled) {
               synchronized(this.pool) {
                  if (GenericProxyServlet.this.verbose) {
                     GenericProxyServlet.this.trace("Requeue connection: " + con);
                  }

                  con.setLastUsed(System.currentTimeMillis());
                  this.pool.add(con);
               }
            } else {
               con.close();
            }

         }
      }

      ProxyConnection getNewProxyConnection(String host, int port, boolean isSecure, int timeout) throws IOException {
         ProxyConnection con = GenericProxyServlet.this.new ProxyConnection(host, port, isSecure, timeout);
         if (GenericProxyServlet.this.verbose) {
            GenericProxyServlet.this.trace("Create connection: " + con);
         }

         return con;
      }

      public ProxyConnection getProxyConnection(String host, int port, boolean isSecure, int timeout) throws IOException {
         if (GenericProxyServlet.this.keepAliveEnabled) {
            synchronized(this.pool) {
               long now = 0L;
               long interval = 0L;

               for(int i = 0; i < this.pool.size(); ++i) {
                  ProxyConnection con = (ProxyConnection)this.pool.get(i);
                  if (port == con.getPort() && host.equals(con.getHost())) {
                     this.pool.remove(i);
                     --i;
                     now = System.currentTimeMillis();
                     interval = now - con.getLastUsed();
                     if (interval <= (long)con.getKeepAliveMilliSecs()) {
                        if (GenericProxyServlet.this.verbose) {
                           GenericProxyServlet.this.trace("Returning recycled connection: " + con);
                        }

                        return con;
                     }

                     if (GenericProxyServlet.this.verbose) {
                        GenericProxyServlet.this.trace("Remove idle for '" + interval / 1000L + "' secs: " + con);
                     }

                     con.close();
                  }
               }
            }
         }

         return this.getNewProxyConnection(host, port, isSecure, timeout);
      }

      public ProxyConnection getProxyConnection(String host, int port, boolean isSecure) throws IOException {
         return this.getProxyConnection(host, port, isSecure, 300);
      }

      public void timerExpired(Timer timer) {
         if (GenericProxyServlet.this.keepAliveEnabled) {
            synchronized(this.pool) {
               long now = System.currentTimeMillis();

               for(int i = this.pool.size() - 1; i >= 0; --i) {
                  ProxyConnection con = (ProxyConnection)this.pool.get(i);
                  long lastUsed = con.getLastUsed();
                  long interval = now - lastUsed;
                  if (interval > (long)con.getKeepAliveMilliSecs()) {
                     if (GenericProxyServlet.this.verbose) {
                        GenericProxyServlet.this.trace("Trigger remove idle for '" + interval / 1000L + "' secs: " + con);
                     }

                     con.close();
                     this.pool.remove(i);
                  }
               }

            }
         }
      }

      private void stopTimer() {
         this.timer.cancel();
         this.timerManager.stop();
      }

      private void startTimer() {
         this.timer = this.timerManager.schedule(this, 0L, 10000L);
      }
   }

   class ProxyConnection {
      private Socket sock;
      private boolean isSecure;
      private int timeout;
      private String host;
      private int port;
      private boolean closed;
      private long lastUsed;
      private boolean canRecycle;
      private int keep_alive_millisecs;

      public ProxyConnection(String host, int port, boolean isSecure, int t) throws IOException {
         this.closed = true;
         this.lastUsed = 0L;
         this.canRecycle = true;
         this.host = host;
         this.port = port;
         this.isSecure = isSecure;
         this.timeout = t * 1000;
         this.setKeepAliveMilliSecs(GenericProxyServlet.this.keepAliveSecs * 1000);
         if (isSecure) {
            try {
               SSLContext context = SSLContext.getInstance("https");
               if (GenericProxyServlet.this.keyStore != null && GenericProxyServlet.this.privateKeyAlias != null && GenericProxyServlet.this.es != null) {
                  KeyStore ks = GenericProxyServlet.this.keyStoreType == null ? KeyStore.getInstance(KeyStore.getDefaultType()) : KeyStore.getInstance(GenericProxyServlet.this.keyStoreType);
                  String keyStorePassword = null;
                  if (GenericProxyServlet.this.encryptedKeyStorePasswd != null) {
                     keyStorePassword = GenericProxyServlet.this.ces.decrypt(GenericProxyServlet.this.encryptedKeyStorePasswd);
                  }

                  ks.load(GenericProxyServlet.this.servletContext.getResourceAsStream(GenericProxyServlet.this.keyStore), keyStorePassword != null ? keyStorePassword.toCharArray() : null);
                  String privateKeyPassword = null;
                  if (GenericProxyServlet.this.encryptedPrivateKeyPasswd != null) {
                     privateKeyPassword = GenericProxyServlet.this.ces.decrypt(GenericProxyServlet.this.encryptedPrivateKeyPasswd);
                  }

                  PrivateKey key = (PrivateKey)ks.getKey(GenericProxyServlet.this.privateKeyAlias, privateKeyPassword != null ? privateKeyPassword.toCharArray() : null);
                  Certificate[] certChain = ks.getCertificateChain(GenericProxyServlet.this.privateKeyAlias);
                  context.loadLocalIdentity(certChain, key);
               }

               SSLSocketFactory factory = context.getSocketFactory();
               this.sock = factory.createSocket(host, port);
            } catch (Exception var12) {
               throw (IOException)(new IOException(var12.getMessage())).initCause(var12);
            }
         } else {
            this.sock = new Socket(host, port);
         }

         this.sock.setSoTimeout(this.timeout);
         this.sock.setTcpNoDelay(true);
         this.closed = false;
      }

      public ProxyConnection(String host, int port, boolean isSecure) throws IOException {
         this(host, port, isSecure, 300);
      }

      public void close() {
         if (!this.closed) {
            try {
               this.sock.close();
               this.closed = true;
            } catch (IOException var2) {
            }

         }
      }

      public Socket getSocket() {
         return this.sock;
      }

      public int getTimeout() {
         return this.timeout;
      }

      public void setTimeout(int t) {
         this.timeout = t * 1000;

         try {
            this.sock.setSoTimeout(this.timeout);
         } catch (IOException var3) {
         }

      }

      public String getHost() {
         return this.host;
      }

      public int getPort() {
         return this.port;
      }

      public void setLastUsed(long l) {
         this.lastUsed = l;
      }

      public long getLastUsed() {
         return this.lastUsed;
      }

      public void setCanRecycle(boolean b) {
         this.canRecycle = b;
      }

      public boolean canRecycle() {
         return this.canRecycle;
      }

      public void setKeepAliveMilliSecs(int s) {
         this.keep_alive_millisecs = s;
      }

      public int getKeepAliveMilliSecs() {
         return this.keep_alive_millisecs;
      }

      public String toString() {
         StringBuilder sb = new StringBuilder(256);
         sb.append("ProxyConnection");
         sb.append("(isSecureProxy=");
         sb.append(this.isSecure);
         sb.append("):  ");
         sb.append(this.host);
         sb.append(":");
         sb.append(this.port);
         sb.append(", keep-alive='");
         sb.append(this.keep_alive_millisecs / 1000);
         sb.append("'secs");
         return sb.toString();
      }
   }
}
