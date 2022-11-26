package weblogic.servlet;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import weblogic.utils.classloaders.Source;
import weblogic.utils.http.QueryParams;

public final class ServerSideIncludeServlet extends FileServlet {
   private static final boolean DEBUG = false;
   private static final int DEFAULT_READ_BUFFER_SIZE = 8192;
   private int readBufferSizeInt = 8192;

   public void init(ServletConfig config) throws ServletException {
      super.init(config);
      String readBufferSize = config.getInitParameter("readBufferSize");
      if (readBufferSize != null && !"".equals(readBufferSize.trim())) {
         try {
            this.readBufferSizeInt = Integer.parseInt(readBufferSize.trim());
         } catch (NumberFormatException var4) {
            if (DEBUG_URL_RES.isDebugEnabled()) {
               DEBUG_URL_RES.debug("Invalid int value for the readBufferSize servlet param. Using default value of 8192 for the readBufferSize servlet param.", var4);
            }

            this.readBufferSizeInt = 8192;
         }

         if (this.readBufferSizeInt <= 0) {
            if (DEBUG_URL_RES.isDebugEnabled()) {
               DEBUG_URL_RES.debug("Negative values or 0 are not allowed. Using default value of 8192 for the readBufferSize param.");
            }

            this.readBufferSizeInt = 8192;
         }

      } else {
         if (DEBUG_URL_RES.isDebugEnabled()) {
            DEBUG_URL_RES.debug("Using default value of 8192 for the readBufferSize param.");
         }

         this.readBufferSizeInt = 8192;
      }
   }

   public void service(HttpServletRequest req, HttpServletResponse res) throws IOException {
      try {
         Source src = this.findSource(req, res);
         if (src != null) {
            if (src.length() == 0L) {
               res.setContentLength(0);
            } else {
               InputStream is = src.getInputStream();

               try {
                  ServletOutputStream out = res.getOutputStream();
                  PushbackInputStream in = new PushbackInputStream(new BufferedInputStream(is, this.readBufferSizeInt));

                  while(true) {
                     while(true) {
                        while(true) {
                           int c = in.read();
                           if (c == 60) {
                              c = in.read();
                              String s;
                              if (c == 115 || c == 83) {
                                 in.unread(c);
                                 s = this.parseTagName(in);
                                 if (s.equalsIgnoreCase("servlet")) {
                                    this.handleServletTag(req, res, in, out);
                                 } else {
                                    out.print("<");
                                    out.print(s);
                                 }
                                 continue;
                              }

                              if (c == 33) {
                                 c = in.read();
                                 if (c == 45) {
                                    c = in.read();
                                    if (c == 45) {
                                       c = in.read();
                                       if (c == 35) {
                                          s = this.parseTagName(in);
                                          if (s.equalsIgnoreCase("include")) {
                                             try {
                                                this.handleIncludeTag(in, req, res);
                                             } catch (Exception var14) {
                                                this.log("ServerSideIncludeServlet failed to handle include tag", var14);
                                                res.sendError(404);
                                             }
                                          } else {
                                             out.print("<!--#");
                                             out.print(s);
                                          }
                                          continue;
                                       }

                                       out.print("<!--");
                                    } else {
                                       out.print("<!-");
                                    }
                                 } else {
                                    out.print("<!");
                                 }
                              } else {
                                 if (c != -1) {
                                    in.unread(c);
                                 }

                                 c = 60;
                              }
                           }

                           if (c == -1) {
                              return;
                           }

                           out.write(c);
                        }
                     }
                  }
               } finally {
                  is.close();
               }
            }
         }
      } catch (FileNotFoundException var16) {
         res.sendError(404);
      }
   }

   protected void handleServletTag(HttpServletRequest req, HttpServletResponse res, PushbackInputStream in, ServletOutputStream out) throws IOException {
      Map initParams = this.parseTagParameters(in);
      Map queryParams = new QueryParams(this.context.getServer().getMaxRequestParameterCount());
      in.read();

      while(true) {
         int c = in.read();
         if (c == 60) {
            c = in.read();
            String tag;
            if (c == 47) {
               tag = this.parseTagName(in);
               if ("servlet".equalsIgnoreCase(tag)) {
                  in.read();
                  break;
               }

               out.print("</");
               out.print(tag);
            } else {
               if (c != -1) {
                  in.unread(c);
               }

               tag = this.parseTagName(in);
               if (tag.equalsIgnoreCase("param")) {
                  this.parseParamTag(in, queryParams);
               } else {
                  out.print("<");
                  out.print(tag);
               }
            }
         } else {
            if (c == -1) {
               break;
            }

            out.write(c);
         }
      }

      String name = (String)initParams.get("name");
      if (name != null && !name.equals("")) {
         try {
            RequestDispatcher rd = this.getRD(name, req);
            if (rd == null) {
               if (DEBUG_URL_RES.isDebugEnabled()) {
                  DEBUG_URL_RES.debug(this.context.getLogContext() + ": Couldn't find servlet for name: " + name);
               }

            } else {
               rd.include(req, res);
            }
         } catch (Exception var9) {
            if (DEBUG_URL_RES.isDebugEnabled()) {
               DEBUG_URL_RES.debug(this.context.getLogContext() + ": Error initializing servlet", var9);
            }

         }
      } else {
         if (DEBUG_URL_RES.isDebugEnabled()) {
            DEBUG_URL_RES.debug(this.context.getLogContext() + ": Couldn't find servlet parameter: name");
         }

      }
   }

   private void consumeToTagEnd(InputStream i) throws IOException {
      int r;
      do {
         r = i.read();
      } while(r != 62 && r != -1);

   }

   private String readRValue(PushbackInputStream in) throws IOException {
      char delim = 0;
      StringBuilder sb = new StringBuilder();
      this.skipWhiteSpace(in);
      int c = in.read();
      if (c != 39 && c != 34) {
         if (c == -1) {
            return "";
         }

         sb.append((char)c);
      } else {
         delim = (char)c;
      }

      while(true) {
         c = in.read();
         if (c == -1 || c == delim && c != 0) {
            break;
         }

         if (c == 32) {
            if (delim == 0) {
               in.unread(c);
               break;
            }

            sb.append((char)c);
         } else {
            if (c == 62) {
               in.unread(c);
               break;
            }

            if (c <= 32 || c >= 127) {
               break;
            }

            sb.append((char)c);
         }
      }

      return sb.toString();
   }

   private boolean parseParamTag(PushbackInputStream in, Map params) throws IOException {
      boolean retOldStyle = false;

      while(true) {
         this.skipWhiteSpace(in);
         boolean oldStyle = false;
         String name = this.parseTagName(in);
         if ("".equals(name)) {
            break;
         }

         int r;
         if ("name".equalsIgnoreCase(name)) {
            oldStyle = true;
            retOldStyle = true;
            this.skipWhiteSpace(in);
            r = in.read();
            if (r == -1 || r == 62) {
               break;
            }

            if (r != 61) {
               this.consumeToTagEnd(in);
               break;
            }

            name = this.readRValue(in);
            if ("".equalsIgnoreCase(name)) {
               break;
            }
         } else {
            this.skipWhiteSpace(in);
            r = in.read();
            if (r != 61) {
               this.consumeToTagEnd(in);
               break;
            }
         }

         this.skipWhiteSpace(in);
         String value = this.parseTagName(in);
         if (oldStyle) {
            this.skipWhiteSpace(in);
            int r = in.read();
            if (r == -1 || r == 62) {
               break;
            }

            if (r != 61) {
               this.consumeToTagEnd(in);
               break;
            }

            value = this.readRValue(in);
         }

         this.consumeToTagEnd(in);
         params.put(name, value);
      }

      return retOldStyle;
   }

   protected void handleIncludeTag(PushbackInputStream in, HttpServletRequest req, HttpServletResponse res) throws Exception {
      RequestDispatcher rd = null;
      Map params = this.parseTagParameters(in);
      String filename;
      if ((filename = (String)params.get("virtual")) != null) {
         if (filename.charAt(0) != '/') {
            filename = "/" + filename;
         }

         if ((rd = this.getRD(filename, req)) == null) {
            throw new FileNotFoundException("Failed find SSI included file: " + filename);
         } else {
            try {
               rd.include(req, res);
            } catch (Exception var9) {
            }

         }
      } else if ((filename = (String)params.get("file")) != null) {
         rd = this.getRD(filename, req);

         try {
            rd.include(req, res);
         } catch (Exception var10) {
         }

      } else {
         Iterator enum_ = params.keySet().iterator();
         String key = (String)params.get(enum_.next());
         if (DEBUG_URL_RES.isDebugEnabled()) {
            if (key != null) {
               DEBUG_URL_RES.debug(this.context.getLogContext() + ": Syntax of include tag containing " + key + " is incorrect.  Tag format is: <!--#include virtual=\"somefile.txt\">");
            } else {
               DEBUG_URL_RES.debug(this.context.getLogContext() + ": Syntax of include tag incorrect.  Tag format is: <!--#include virtual=\"somefile.txt\">");
            }
         }

      }
   }

   private String parseTagName(PushbackInputStream in) throws IOException {
      StringBuilder buf = new StringBuilder();

      int c;
      for(c = in.read(); c != -1 && (Character.isLetterOrDigit((char)c) || (char)c == '_'); c = in.read()) {
         buf.append((char)c);
      }

      if (c != -1) {
         in.unread(c);
      }

      return buf.toString();
   }

   private Map parseTagParameters(PushbackInputStream in) throws IOException {
      Map params = new QueryParams(this.context.getServer().getMaxRequestParameterCount());
      this.skipWhiteSpace(in);

      String name;
      while((name = this.parseTagName(in)) != null && !"".equals(name)) {
         this.skipWhiteSpace(in);
         String value = "";
         if (in.read() == 61) {
            this.skipWhiteSpace(in);
            int c = in.read();
            boolean withinQuotes;
            int quoteChar;
            if (c != 34 && c != 39) {
               withinQuotes = false;
               quoteChar = -1;
            } else {
               withinQuotes = true;
               quoteChar = c;
               c = in.read();
            }

            StringBuilder buf;
            for(buf = new StringBuilder(); c > 0; c = in.read()) {
               if (withinQuotes) {
                  if (c == 10 || c == 13) {
                     throw new IOException("End of line reached within quoted value");
                  }

                  if (c == quoteChar) {
                     break;
                  }
               } else if (c == 32 || c == 9 || c == 10 || c == 13 || c == 62) {
                  in.unread(c);
                  break;
               }

               buf.append((char)c);
            }

            if (c == 0) {
               throw new IOException("End of file reached within SSI tag");
            }

            this.skipWhiteSpace(in);
            value = buf.toString();
         }

         name = name.toLowerCase(Locale.ENGLISH);
         params.put(name, value);
         this.skipWhiteSpace(in);
      }

      this.consumeToTagEnd(in);
      return params;
   }

   private void skipWhiteSpace(PushbackInputStream in) throws IOException {
      int c;
      do {
         c = in.read();
      } while(c != -1 && Character.isWhitespace((char)c));

      if (c != -1) {
         in.unread(c);
      }

   }

   private RequestDispatcher getRD(String url, HttpServletRequest request) {
      if (url.charAt(0) != '/') {
         String orig = request.getRequestURI();
         int ind = orig.lastIndexOf(47);
         if (ind == -1) {
            url = '/' + url;
         } else if (ind == orig.length() - 1) {
            url = orig + url;
         } else {
            String ctx = request.getContextPath();
            if (orig.startsWith(ctx)) {
               url = orig.substring(ctx.length(), ind + 1) + url;
            } else {
               url = orig.substring(0, ind + 1) + url;
            }
         }
      }

      RequestDispatcher rd = this.context.getRequestDispatcher(url);
      return rd;
   }
}
