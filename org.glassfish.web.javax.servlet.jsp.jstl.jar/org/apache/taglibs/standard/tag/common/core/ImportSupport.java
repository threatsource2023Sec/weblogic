package org.apache.taglibs.standard.tag.common.core;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;
import org.apache.taglibs.standard.resources.Resources;

public abstract class ImportSupport extends BodyTagSupport implements TryCatchFinally, ParamParent {
   public static final String VALID_SCHEME_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789+.-";
   public static final String DEFAULT_ENCODING = "ISO-8859-1";
   protected String url;
   protected String context;
   protected String charEncoding;
   private String var;
   private int scope;
   private String varReader;
   private Reader r;
   private boolean isAbsoluteUrl;
   private ParamSupport.ParamManager params;
   private String urlWithParams;

   public ImportSupport() {
      this.init();
   }

   private void init() {
      this.url = this.var = this.varReader = this.context = this.charEncoding = this.urlWithParams = null;
      this.params = null;
      this.scope = 1;
   }

   public int doStartTag() throws JspException {
      if (this.context == null || this.context.startsWith("/") && this.url.startsWith("/")) {
         this.urlWithParams = null;
         this.params = new ParamSupport.ParamManager();
         if (this.url != null && !this.url.equals("")) {
            this.isAbsoluteUrl = this.isAbsoluteUrl();

            try {
               if (this.varReader != null) {
                  this.r = this.acquireReader();
                  this.pageContext.setAttribute(this.varReader, this.r);
               }

               return 1;
            } catch (IOException var2) {
               throw new JspTagException(var2.toString(), var2);
            }
         } else {
            throw new NullAttributeException("import", "url");
         }
      } else {
         throw new JspTagException(Resources.getMessage("IMPORT_BAD_RELATIVE"));
      }
   }

   public int doEndTag() throws JspException {
      try {
         if (this.varReader == null) {
            if (this.var != null) {
               this.pageContext.setAttribute(this.var, this.acquireString(), this.scope);
            } else {
               this.pageContext.getOut().print(this.acquireString());
            }
         }

         return 6;
      } catch (IOException var2) {
         throw new JspTagException(var2.toString(), var2);
      }
   }

   public void doCatch(Throwable t) throws Throwable {
      throw t;
   }

   public void doFinally() {
      try {
         if (this.varReader != null) {
            if (this.r != null) {
               this.r.close();
            }

            this.pageContext.removeAttribute(this.varReader, 1);
         }
      } catch (IOException var2) {
      }

   }

   public void release() {
      this.init();
      super.release();
   }

   public void setVar(String var) {
      this.var = var;
   }

   public void setVarReader(String varReader) {
      this.varReader = varReader;
   }

   public void setScope(String scope) {
      this.scope = Util.getScope(scope);
   }

   public void addParameter(String name, String value) {
      this.params.addParameter(name, value);
   }

   private String acquireString() throws IOException, JspException {
      BufferedReader r;
      if (!this.isAbsoluteUrl) {
         if (this.pageContext.getRequest() instanceof HttpServletRequest && this.pageContext.getResponse() instanceof HttpServletResponse) {
            r = null;
            String targetUrl = this.targetUrl();
            ServletContext c;
            if (this.context != null) {
               c = this.pageContext.getServletContext().getContext(this.context);
            } else {
               c = this.pageContext.getServletContext();
               if (!targetUrl.startsWith("/")) {
                  String sp = ((HttpServletRequest)this.pageContext.getRequest()).getServletPath();
                  targetUrl = sp.substring(0, sp.lastIndexOf(47)) + '/' + targetUrl;
               }
            }

            if (c == null) {
               throw new JspTagException(Resources.getMessage("IMPORT_REL_WITHOUT_DISPATCHER", this.context, targetUrl));
            } else {
               RequestDispatcher rd = c.getRequestDispatcher(stripSession(targetUrl));
               if (rd == null) {
                  throw new JspTagException(stripSession(targetUrl));
               } else {
                  ImportResponseWrapper irw = new ImportResponseWrapper(this.pageContext);

                  try {
                     rd.include(this.pageContext.getRequest(), irw);
                  } catch (IOException var12) {
                     throw new JspException(var12);
                  } catch (RuntimeException var13) {
                     throw new JspException(var13);
                  } catch (ServletException var14) {
                     Throwable rc = var14.getRootCause();
                     if (rc == null) {
                        throw new JspException(var14);
                     }

                     throw new JspException(rc);
                  }

                  if (irw.getStatus() >= 200 && irw.getStatus() <= 299) {
                     return irw.getString();
                  } else {
                     throw new JspTagException(irw.getStatus() + " " + stripSession(targetUrl));
                  }
               }
            }
         } else {
            throw new JspTagException(Resources.getMessage("IMPORT_REL_WITHOUT_HTTP"));
         }
      } else {
         r = new BufferedReader(this.acquireReader());
         StringBuffer sb = new StringBuffer();

         try {
            int i;
            try {
               while((i = r.read()) != -1) {
                  sb.append((char)i);
               }
            } catch (IOException var15) {
               throw var15;
            }
         } finally {
            r.close();
         }

         return sb.toString();
      }
   }

   private Reader acquireReader() throws IOException, JspException {
      if (!this.isAbsoluteUrl) {
         return new StringReader(this.acquireString());
      } else {
         String target = this.targetUrl();

         try {
            URL u = new URL(target);
            URLConnection uc = u.openConnection();
            InputStream i = uc.getInputStream();
            Reader r = null;
            String charSet;
            if (this.charEncoding != null && !this.charEncoding.equals("")) {
               charSet = this.charEncoding;
            } else {
               String contentType = uc.getContentType();
               if (contentType != null) {
                  charSet = Util.getContentTypeAttribute(contentType, "charset");
                  if (charSet == null) {
                     charSet = "ISO-8859-1";
                  }
               } else {
                  charSet = "ISO-8859-1";
               }
            }

            try {
               r = new InputStreamReader(i, charSet);
            } catch (Exception var8) {
               r = new InputStreamReader(i, "ISO-8859-1");
            }

            if (uc instanceof HttpURLConnection) {
               int status = ((HttpURLConnection)uc).getResponseCode();
               if (status < 200 || status > 299) {
                  throw new JspTagException(status + " " + target);
               }
            }

            return r;
         } catch (IOException var9) {
            throw new JspException(Resources.getMessage("IMPORT_ABS_ERROR", target, var9), var9);
         } catch (RuntimeException var10) {
            throw new JspException(Resources.getMessage("IMPORT_ABS_ERROR", target, var10), var10);
         }
      }
   }

   private String targetUrl() {
      if (this.urlWithParams == null) {
         this.urlWithParams = this.params.aggregateParams(this.url);
      }

      return this.urlWithParams;
   }

   private boolean isAbsoluteUrl() throws JspTagException {
      return isAbsoluteUrl(this.url);
   }

   public static boolean isAbsoluteUrl(String url) {
      if (url == null) {
         return false;
      } else {
         int colonPos;
         if ((colonPos = url.indexOf(":")) == -1) {
            return false;
         } else {
            for(int i = 0; i < colonPos; ++i) {
               if ("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789+.-".indexOf(url.charAt(i)) == -1) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   public static String stripSession(String url) {
      StringBuffer u;
      int sessionStart;
      int sessionEnd;
      for(u = new StringBuffer(url); (sessionStart = u.toString().indexOf(";jsessionid=")) != -1; u.delete(sessionStart, sessionEnd)) {
         sessionEnd = u.toString().indexOf(";", sessionStart + 1);
         if (sessionEnd == -1) {
            sessionEnd = u.toString().indexOf("?", sessionStart + 1);
         }

         if (sessionEnd == -1) {
            sessionEnd = u.length();
         }
      }

      return u.toString();
   }

   private static class PrintWriterWrapper extends PrintWriter {
      private StringWriter out;
      private Writer parentWriter;

      public PrintWriterWrapper(StringWriter out, Writer parentWriter) {
         super(out);
         this.out = out;
         this.parentWriter = parentWriter;
      }

      public void flush() {
         try {
            this.parentWriter.write(this.out.toString());
            StringBuffer sb = this.out.getBuffer();
            sb.delete(0, sb.length());
         } catch (IOException var2) {
         }

      }
   }

   private class ImportResponseWrapper extends HttpServletResponseWrapper {
      private StringWriter sw = new StringWriter();
      private ByteArrayOutputStream bos = new ByteArrayOutputStream();
      private ServletOutputStream sos = new ServletOutputStream() {
         public void write(int b) throws IOException {
            ImportResponseWrapper.this.bos.write(b);
         }

         public void flush() throws IOException {
            ImportResponseWrapper.this.pageContext.getOut().write(ImportResponseWrapper.this.getString());
            ImportResponseWrapper.this.bos.reset();
         }
      };
      private boolean isWriterUsed;
      private boolean isStreamUsed;
      private int status = 200;
      private PageContext pageContext;

      public ImportResponseWrapper(PageContext pageContext) {
         super((HttpServletResponse)pageContext.getResponse());
         this.pageContext = pageContext;
      }

      public PrintWriter getWriter() throws IOException {
         if (this.isStreamUsed) {
            throw new IllegalStateException(Resources.getMessage("IMPORT_ILLEGAL_STREAM"));
         } else {
            this.isWriterUsed = true;
            return new PrintWriterWrapper(this.sw, this.pageContext.getOut());
         }
      }

      public ServletOutputStream getOutputStream() {
         if (this.isWriterUsed) {
            throw new IllegalStateException(Resources.getMessage("IMPORT_ILLEGAL_WRITER"));
         } else {
            this.isStreamUsed = true;
            return this.sos;
         }
      }

      public void setContentType(String x) {
      }

      public void setLocale(Locale x) {
      }

      public void setStatus(int status) {
         this.status = status;
      }

      public int getStatus() {
         return this.status;
      }

      public String getString() throws UnsupportedEncodingException {
         if (this.isWriterUsed) {
            return this.sw.toString();
         } else if (this.isStreamUsed) {
            return ImportSupport.this.charEncoding != null && !ImportSupport.this.charEncoding.equals("") ? this.bos.toString(ImportSupport.this.charEncoding) : this.bos.toString("ISO-8859-1");
         } else {
            return "";
         }
      }
   }
}
