package weblogic.servlet.internal;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.AccessController;
import java.util.HashMap;
import javax.security.auth.login.LoginException;
import javax.servlet.ServletInputStream;
import weblogic.management.provider.ManagementService;
import weblogic.protocol.ServerChannelManager;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.servlet.provider.WlsSecurityProvider;
import weblogic.servlet.security.ServletAuthentication;
import weblogic.servlet.spi.SubjectHandle;
import weblogic.servlet.spi.WebServerRegistry;
import weblogic.utils.http.HttpRequestParseException;
import weblogic.utils.http.HttpRequestParser;
import weblogic.utils.io.NullInputStream;
import weblogic.utils.io.UnsyncByteArrayOutputStream;

public class InternalRequestDispatcher {
   private static final boolean DEBUG = false;
   private static final String CONTENT_TYPE = "Content-Type";
   private final String host;
   private final String uri;
   private final HashMap headers;
   private String content;
   private String charset;
   private int status;
   private String statusString;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private String runAs;

   public InternalRequestDispatcher(String uri) throws IOException {
      this(uri, (String)null);
   }

   public InternalRequestDispatcher(String uri, String host) throws IOException {
      this(uri, host, (String)null);
   }

   public InternalRequestDispatcher(String uri, String host, String username) throws IOException {
      this.headers = new HashMap();
      this.status = 0;
      this.statusString = null;
      this.runAs = null;
      this.host = host;
      this.uri = uri;
      this.runAs = username;
      this.dispatch();
   }

   public int getStatus() {
      return this.status;
   }

   public String getStatusString() {
      return this.statusString;
   }

   public String getContent() {
      return this.content;
   }

   public String getHeader(String name) {
      return (String)this.headers.get(name);
   }

   public String getContentType() {
      return this.getHeader("Content-Type");
   }

   public String getCharset() {
      return this.charset;
   }

   protected void dispatch() throws IOException {
      if (this.uri != null && this.uri.startsWith("/")) {
         UnsyncByteArrayOutputStream os = new UnsyncByteArrayOutputStream();
         ServletRequestImpl req = new ServletRequestImpl((AbstractHttpConnectionHandler)null);
         byte[] reqBytes = (new String("GET " + this.uri + " " + "HTTP/1.0" + "\r\n\r\n")).getBytes();

         HttpRequestParser parser;
         IOException httpServer;
         try {
            parser = new HttpRequestParser(reqBytes, reqBytes.length);
         } catch (HttpRequestParseException var20) {
            httpServer = new IOException("Couldn't parse request");
            httpServer.initCause(var20);
            throw httpServer;
         }

         req.initFromRequestParser(parser);
         ServletResponseImpl res = new ServletResponseImpl(req, os);
         res.getServletOutputStream().setWriteEnabled(!"HEAD".equals(parser.getMethod()));
         httpServer = null;
         HttpServer httpServer;
         if (this.host != null) {
            httpServer = WebServerRegistry.getInstance().getHttpServerManager().getVirtualHost(this.host, req.getInputHelper().getNormalizedURI());
         } else {
            httpServer = WebServerRegistry.getInstance().getHttpServerManager().defaultHttpServer();
         }

         if (httpServer == null) {
            throw new IOException("Virtual host not found: " + this.host);
         } else {
            ServletContextManager scm = httpServer.getServletContextManager();
            ContextVersionManager ctxManager = scm.resolveVersionManagerForURI(this.uri);
            WebAppServletContext ctx;
            if (ctxManager.isVersioned()) {
               req.initContextManager(ctxManager);
               ctx = req.getContext();
            } else {
               ctx = ctxManager.getContext();
               req.initContext(ctx);
            }

            ServletInputStreamImpl sis = new ServletInputStreamImpl(NullInputStream.getInstance());
            req.setInputStream((ServletInputStream)sis);
            String frHost = httpServer.getFrontendHost();
            if (frHost != null) {
               req.setServerName(frHost);
            } else {
               req.setServerName(httpServer.getListenAddress());
            }

            int frPort = httpServer.getFrontendHTTPPort();
            if (frPort != 0) {
               req.setServerPort(frPort);
            } else {
               req.setServerPort(ServerChannelManager.findLocalServerChannel(ProtocolHandlerHTTP.PROTOCOL_HTTP).getPublicPort());
            }

            res.initContext(ctx);
            res.getServletOutputStream().setHttpServer(httpServer);
            res.setHeaderInternal("Server", HttpServer.SERVER_INFO);
            if (!ManagementService.getRuntimeAccess(kernelId).getServer().isHttpdEnabled()) {
               throw new IOException("HTTP is not enabled on this server!");
            } else if (ctx == null) {
               res.sendError(404);
               this.status = 404;
               this.statusString = "Not Found";
            } else {
               ServletStubImpl servletStub = ctx.resolveDirectRequest(req);
               req.setServletStub(servletStub);
               if (this.runAs != null) {
                  SubjectHandle subject = null;

                  try {
                     subject = ctx.getSecurityManager().getAppSecurityProvider().impersonate(this.runAs, ctx.getSecurityRealmName(), req, res);
                  } catch (LoginException var19) {
                     ctx.log((String)"Failed to get runAs subject for the InternalRequestDispatcher", (Throwable)var19);
                     throw new IllegalArgumentException(var19.getMessage());
                  }

                  if (subject != null) {
                     ServletAuthentication.runAs((AuthenticatedSubject)WlsSecurityProvider.toAuthSubject(subject), req);
                  }
               }

               req.getHttpAccountingInfo().setInvokeTime(System.currentTimeMillis());
               ctx.execute(req, res);

               try {
                  res.getServletOutputStream().flush();
               } catch (RuntimeException var18) {
               }

               byte[] resultBytes = os.toByteArray();
               os.close();
               res.getServletOutputStream().close();
               int headerLen = this.parseHttpHeaders(resultBytes);
               this.parseCharset();
               if (headerLen != -1) {
                  if (this.getCharset() != null) {
                     try {
                        this.content = new String(resultBytes, headerLen, resultBytes.length - headerLen, this.getCharset());
                        return;
                     } catch (UnsupportedEncodingException var21) {
                     }
                  }

                  try {
                     this.content = new String(resultBytes, headerLen, resultBytes.length - headerLen, "ISO-8859-1");
                  } catch (UnsupportedEncodingException var17) {
                     this.content = new String(resultBytes, headerLen, resultBytes.length - headerLen);
                  }
               }
            }
         }
      } else {
         throw new IOException("URI must be absolute with no protocol, etc: " + this.uri);
      }
   }

   protected int parseHttpHeaders(byte[] buf) throws IOException {
      int headerLen = -1;
      String ret = this.getFirstLine(buf);
      int pos = false;
      int localCount = buf.length;
      byte[] localBuf = buf;
      if (ret == null) {
         return headerLen;
      } else {
         int pos;
         for(pos = ret.length(); localBuf[pos] == 10 || localBuf[pos] == 13; ++pos) {
         }

         this.parseResponseStatus(ret);

         while(true) {
            String hdr = null;

            int start;
            for(start = pos; pos < localCount && localBuf[pos] != 10; ++pos) {
               if (localBuf[pos] == 13) {
                  ++pos;
                  if (pos < localCount && localBuf[pos] == 10) {
                     break;
                  }
               }

               if (localBuf[pos] == 58) {
                  ++pos;
                  if (pos >= localCount) {
                     throw new IllegalArgumentException("malformed HTTP headers");
                  }

                  hdr = new String(localBuf, start, pos - start - 1);
                  if (localBuf[pos] != 32) {
                     --pos;
                  }
                  break;
               }
            }

            ++pos;
            if (hdr == null) {
               if (pos <= localCount) {
                  headerLen = pos;
               } else {
                  headerLen = -1;
               }

               return headerLen;
            }

            start = pos;

            String val;
            for(val = null; pos < localCount; ++pos) {
               if (localBuf[pos] == 10) {
                  val = new String(localBuf, start, pos - start);
                  break;
               }

               if (localBuf[pos] == 13) {
                  ++pos;
                  if (pos < localCount && localBuf[pos] == 10) {
                     val = new String(localBuf, start, pos - start - 1);
                     break;
                  }
               }
            }

            ++pos;
            if (val == null) {
               throw new IllegalArgumentException("malformed HTTP headers");
            }

            this.headers.put(hdr, val);
         }
      }
   }

   protected String getFirstLine(byte[] buf) {
      String ret = null;
      int pos = false;
      int localCount = buf.length;
      byte[] localBuf = buf;
      int start = 0;

      for(int pos = start; pos < localCount; ++pos) {
         if (localBuf[pos] == 10) {
            ret = new String(localBuf, start, pos);
            break;
         }

         if (localBuf[pos] == 13) {
            ++pos;
            if (localBuf[pos] == 10 && pos > 2) {
               ret = new String(localBuf, start, pos - 1);
            }
            break;
         }
      }

      return ret;
   }

   protected void parseResponseStatus(String line) {
      line = line.trim();
      int ix1 = line.indexOf(32);
      if (ix1 != -1) {
         int ix2 = line.indexOf(32, ix1 + 1);
         if (ix2 != -1) {
            this.statusString = line.substring(ix2 + 1);
         } else {
            ix2 = line.length();
         }

         try {
            this.status = Integer.parseInt(line.substring(ix1 + 1, ix2));
         } catch (NumberFormatException var5) {
         }
      }

   }

   private void parseCharset() {
      String type = this.getContentType();
      if (type != null) {
         int charSetIdx = type.indexOf("charset=");
         if (charSetIdx != -1) {
            int charSetEnd = type.indexOf(";", charSetIdx);
            charSetIdx += 8;
            if (charSetEnd == -1) {
               this.charset = type.substring(charSetIdx);
            } else {
               this.charset = type.substring(charSetIdx, charSetEnd);
            }
         }

      }
   }
}
