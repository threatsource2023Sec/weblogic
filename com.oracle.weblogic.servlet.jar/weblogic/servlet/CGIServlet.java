package weblogic.servlet;

import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import weblogic.version;
import weblogic.servlet.internal.ServletOutputStreamImpl;
import weblogic.servlet.internal.WebAppServletContext;
import weblogic.servlet.security.Utils;
import weblogic.utils.Executable;
import weblogic.utils.StringUtils;
import weblogic.utils.http.HttpParsing;
import weblogic.utils.io.Chunk;

public final class CGIServlet extends HttpServlet {
   private String[] cgiDir = null;
   private String allCgiDir = null;
   private ArrayList stat_env = null;
   private Hashtable extensionMap = null;
   private boolean debug = false;

   public void init(ServletConfig config) throws ServletException {
      super.init(config);
      String param = this.getInitParameter("debug");
      if (param != null) {
         this.debug = param.equals("true");
      }

      this.setCgiDir(((WebAppServletContext)this.getServletContext()).getDocroot(), this.getInitParameter("cgiDir"));
      if (this.debug) {
         this.log("CGI Initialized [root : " + this.allCgiDir + "] [debug : " + this.debug + "]");
      }

      Enumeration initParams = this.getInitParameterNames();
      this.extensionMap = new Hashtable();

      while(initParams.hasMoreElements()) {
         String k = (String)initParams.nextElement();
         String v = this.getInitParameter(k);
         if (k.startsWith("*.")) {
            this.extensionMap.put(k, v);
         }
      }

      Env env = Env.getenv();
      this.stat_env = env.getWholeEnv();
      this.stat_env.add("SERVER_SOFTWARE=WebLogic/" + version.getReleaseBuildVersion());
      this.stat_env.add("GATEWAY_INTERFACE=CGI/1.1");
   }

   private void setCgiDir(String root, String dirset) {
      if (dirset == null) {
         dirset = File.separator + "cgi-bin";
      }

      if (File.separatorChar == '\\') {
         dirset = dirset.replace('/', File.separatorChar);
      } else {
         dirset = dirset.replace('\\', File.separatorChar);
      }

      File rootFile = new File(root);
      if (!rootFile.isDirectory() && root.toUpperCase().endsWith(".WAR")) {
         String tmpDir = root.substring(0, root.lastIndexOf(File.separatorChar)) + File.separatorChar + ((WebAppServletContext)this.getServletContext()).getTempPath();
         if (File.separatorChar == '\\') {
            tmpDir = tmpDir.replace('/', File.separatorChar);
         } else {
            tmpDir = tmpDir.replace('\\', File.separatorChar);
         }

         if (!this.extractScripts(root, tmpDir, dirset)) {
            this.log("Could not extract scripts from '" + root + "'");
         }

         root = tmpDir;
      }

      String[] dir = StringUtils.splitCompletely(dirset, ";");
      int n = dir.length;
      this.cgiDir = new String[n];
      this.allCgiDir = "";

      for(int i = 0; i < n; ++i) {
         if (dir[i].endsWith("/") || dir[i].endsWith("\\")) {
            dir[i] = dir[i].substring(0, dir[i].length() - 1);
         }

         File d = new File(dir[i]);
         String path;
         if (!d.isAbsolute()) {
            if (dir[i].charAt(0) == File.separatorChar) {
               path = root + dir[i];
            } else {
               path = root + File.separator + dir[i];
            }
         } else {
            path = dir[i];
         }

         d = new File(path);
         if (!d.exists()) {
            this.log("CGI directory: " + path + " doesn't exist.");
            this.cgiDir[i] = null;
         } else if (!d.isDirectory()) {
            this.log("CGI directory: " + path + " isn't a directory.");
            this.cgiDir[i] = null;
         } else {
            try {
               this.cgiDir[i] = d.getCanonicalPath();
               this.allCgiDir = this.allCgiDir + this.cgiDir[i] + File.pathSeparator;
            } catch (IOException var10) {
               this.cgiDir[i] = null;
               this.log("CGI directory: " + path + " doesn't resolve to a canonical name.");
            }
         }
      }

   }

   public String getCgiDir() {
      return this.allCgiDir;
   }

   public void service(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
      ArrayList cmd = new ArrayList();
      ArrayList rqst_env = new ArrayList();
      String scriptName = null;
      String path_info = (String)req.getAttribute("javax.servlet.include.path_info");
      String servlet_path = (String)req.getAttribute("javax.servlet.include.servlet_path");
      if (path_info == null) {
         path_info = Utils.encodeXSS(req.getPathInfo());
      }

      if (servlet_path == null) {
         servlet_path = req.getServletPath();
      }

      String complete_path = (servlet_path.length() <= 1 ? "" : servlet_path) + (path_info == null ? "" : path_info);
      if (complete_path != null && complete_path.length() != 0) {
         if (!complete_path.startsWith("/")) {
            complete_path = "/" + complete_path;
         }

         int extIdx = complete_path.indexOf(46);
         if (extIdx != -1) {
            int preIdx = complete_path.lastIndexOf("/", extIdx);
            int postIdx = complete_path.indexOf("/", extIdx);
            if (postIdx != -1) {
               scriptName = complete_path.substring(preIdx + 1, postIdx);
            } else {
               scriptName = complete_path.substring(preIdx + 1);
            }
         } else if (path_info == null) {
            try {
               scriptName = complete_path.substring(1, complete_path.indexOf(47, 1));
            } catch (StringIndexOutOfBoundsException var41) {
               scriptName = complete_path.substring(1, complete_path.length());
            }
         } else {
            try {
               scriptName = path_info.substring(1, path_info.indexOf(47, 1));
            } catch (StringIndexOutOfBoundsException var40) {
               scriptName = path_info.substring(1, path_info.length());
            }
         }

         String cgi_path_info = complete_path.substring(complete_path.indexOf(scriptName) + scriptName.length()).replace('/', File.separatorChar);
         String docroot = ((WebAppServletContext)this.getServletContext()).getDocroot();
         File rootFile = new File(docroot);
         if (!rootFile.isDirectory() && docroot.toUpperCase().endsWith(".WAR")) {
            docroot = docroot.substring(0, docroot.length() - 4);
         }

         String path_trans = docroot + cgi_path_info;
         String script = scriptName;
         if (scriptName.length() == 0) {
            res.sendError(404);
         } else {
            if (this.debug) {
               this.log("script name=" + scriptName);
            }

            OutputStream outStrm = new CGIServletOutputStream(res);
            CharArrayWriter errWriter = new CharArrayWriter();
            String query = Utils.encodeXSS(req.getQueryString());
            if (query == null) {
               query = "";
            }

            String cont_type = req.getContentType();
            int cont_lnth = req.getContentLength();
            rqst_env.add("DOCUMENT_ROOT=" + docroot);
            rqst_env.add("SERVER_NAME=" + req.getServerName());
            rqst_env.add("SERVER_PROTOCOL=" + req.getProtocol());
            rqst_env.add("SERVER_PORT=" + req.getServerPort());
            rqst_env.add("REQUEST_METHOD=" + req.getMethod());
            rqst_env.add("SCRIPT_NAME=" + HttpParsing.unescape(req.getServletPath() + "/" + scriptName));
            rqst_env.add("QUERY_STRING=" + query);
            rqst_env.add("REMOTE_HOST=" + Utils.encodeXSS(req.getRemoteHost()));
            rqst_env.add("REMOTE_ADDR=" + Utils.encodeXSS(req.getRemoteAddr()));
            rqst_env.add("REQUEST_URI=" + Utils.encodeXSS(req.getRequestURI()) + (req.getQueryString() == null ? "" : "?" + query));
            if (cgi_path_info.length() != 0) {
               rqst_env.add("PATH_INFO=" + HttpParsing.unescape(cgi_path_info).replace(File.separatorChar, '/'));
               rqst_env.add("PATH_TRANSLATED=" + HttpParsing.unescape(path_trans));
            }

            Enumeration rqst_enum = req.getHeaderNames();

            while(rqst_enum.hasMoreElements()) {
               String hdr = Utils.encodeXSS((String)rqst_enum.nextElement());
               if (!hdr.toUpperCase(Locale.ENGLISH).startsWith("AUTHORIZATION")) {
                  rqst_env.add("HTTP_" + hdr.toUpperCase().replace('-', '_') + "=" + Utils.encodeXSS(req.getHeader(hdr)));
               }
            }

            rqst_env.add("AUTH_TYPE=" + req.getAuthType());
            rqst_env.add("REMOTE_USER=" + Utils.encodeXSS(req.getRemoteUser()));
            rqst_env.add("REMOTE_IDENT=");
            rqst_env.add("HTTP_COOKIE=" + Utils.encodeXSS(req.getHeader("Cookie")));
            rqst_env.add("SERVER_URL=" + Utils.encodeXSS(req.getScheme()) + "://" + Utils.encodeXSS(req.getHeader("HOST")) + Utils.encodeXSS(req.getContextPath()));
            if (cont_type != null) {
               rqst_env.add("CONTENT_TYPE=" + Utils.encodeXSS(cont_type));
            }

            if (cont_lnth > -1) {
               rqst_env.add("CONTENT_LENGTH=" + Integer.toString(cont_lnth));
            }

            int dot = scriptName.lastIndexOf(46);
            String absScriptName;
            if (dot != -1) {
               absScriptName = scriptName.substring(dot, scriptName.length());
               String prependExe = (String)this.extensionMap.get("*" + absScriptName);
               if (prependExe != null) {
                  cmd.add(prependExe);
               }
            }

            absScriptName = null;
            File workingDir = null;

            int k;
            for(k = 0; k < this.cgiDir.length; ++k) {
               absScriptName = this.cgiDir[k] + File.separator + script;
               File absFile = new File(absScriptName);
               if (absFile.exists()) {
                  workingDir = new File(this.cgiDir[k]);
                  break;
               }
            }

            if (k == this.cgiDir.length) {
               this.log("Could not find script '" + script + "' in '" + this.allCgiDir + "'");
               if (this.debug && query.startsWith("__cgiinfo")) {
                  this.cgiInfo(cmd, rqst_env, res);
               } else {
                  res.sendError(404);
               }
            } else {
               rqst_env.add("SCRIPT_FILENAME=" + absScriptName);
               cmd.add(absScriptName);
               if (this.debug) {
                  this.log("Script found " + absScriptName);
               }

               if (query.indexOf("=") < 0) {
                  StringTokenizer query_tok = new StringTokenizer(query, "+");

                  while(query_tok.hasMoreTokens()) {
                     cmd.add(query_tok.nextToken());
                  }
               }

               if (this.debug && query.startsWith("__cgiinfo")) {
                  this.cgiInfo(cmd, rqst_env, res);
               } else {
                  if (this.debug) {
                     this.log("Exec script with args :" + cmd);
                  }

                  Executable executable = new Executable();
                  executable.setStdout(outStrm);
                  executable.setErrwriter(errWriter);
                  executable.setPath("");
                  if ("POST".equals(req.getMethod()) || "PUT".equals(req.getMethod())) {
                     executable.setStdin(req.getInputStream());
                  }

                  int stat_env_sz = this.stat_env.size();
                  int rqst_env_sz = rqst_env.size();
                  int cmd_sz = cmd.size();
                  String[] common_env = new String[stat_env_sz + rqst_env_sz];
                  k = 0;

                  int i;
                  for(i = 0; i < stat_env_sz; ++i) {
                     common_env[k++] = (String)this.stat_env.get(i);
                  }

                  for(i = 0; i < rqst_env_sz; ++i) {
                     common_env[k++] = (String)rqst_env.get(i);
                  }

                  String[] args = new String[cmd_sz];

                  for(int i = 0; i < cmd_sz; ++i) {
                     args[i] = (String)cmd.get(i);
                  }

                  try {
                     if (!executable.exec(args, common_env, workingDir)) {
                        if (this.debug) {
                           try {
                              this.log("Failed to exec CGI script. Return value : " + executable.getExitValue());
                           } catch (Throwable var39) {
                              this.log("Failed to exec CGI script. Process ended abrubtly. No return value available.");
                           }
                        } else {
                           this.log("Failed to exec CGI script:" + script);
                        }

                        if (!res.isCommitted()) {
                           res.sendError(500);
                        }
                     }
                  } catch (Exception var42) {
                     String errors = errWriter != null ? errWriter.toString() : "";
                     errors = "CGI script failed: " + errors;
                     this.getServletContext().log(errors, var42);
                     throw new ServletException(errors);
                  } finally {
                     outStrm.close();
                  }

               }
            }
         }
      } else {
         this.log("Cannot resolve cgi script. Cannot proceed further.");
         res.sendError(500);
      }
   }

   private void cgiInfo(ArrayList cmd, ArrayList rqst_env, HttpServletResponse res) throws IOException {
      res.setContentType("text/html");
      res.setHeader("Pragma", "no-cache");
      String val = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Draft//EN\">\n<HTML>\n<HEAD>\n<META NAME=\"GENERATOR\" CONTENT=\"WebLogic Server\">\n</HEAD>\n<BODY>\n<TABLE><TR><TD>Command = ";

      try {
         int i;
         for(i = 0; i < cmd.size(); ++i) {
            val = val + (String)cmd.get(i) + " ";
         }

         val = val + "</TD></TR>\n";

         for(i = 0; i < rqst_env.size(); ++i) {
            val = val + "<TR><TD>" + (String)rqst_env.get(i) + "</TD></TR>\n";
         }

         for(i = 0; i < this.stat_env.size(); ++i) {
            val = val + "<TR><TD>" + (String)this.stat_env.get(i) + "</TD></TR>\n";
         }
      } catch (Exception var6) {
         val = val + "<TR><TD>got exception " + var6 + "</TD></TR>\n";
      }

      val = val + "</TABLE>\n\n</BODY>\n</HTML>\n";
      res.getOutputStream().write(val.getBytes());
   }

   private boolean extractScripts(String warFile, String tmpDir, String cgidir) {
      byte[] buf = new byte[4096];
      String[] dir = StringUtils.splitCompletely(cgidir, ";");

      try {
         File tdir;
         for(int i = 0; i < dir.length; ++i) {
            tdir = new File(dir[i]);
            if (tdir.isAbsolute()) {
               dir[i] = null;
            } else if (!dir[i].startsWith("/") && !dir[i].startsWith("\\")) {
               dir[i] = dir[i].replace('\\', '/');
            } else {
               dir[i] = dir[i].substring(1).replace('\\', '/');
            }
         }

         ZipFile zf = new ZipFile(warFile);
         tdir = new File(tmpDir);
         if (!tdir.exists() && !tdir.mkdirs()) {
            this.log("Cannot make temp directory '" + tmpDir + "' to extract scripts");
         }

         Enumeration enum_ = zf.entries();

         while(true) {
            ZipEntry e;
            String name;
            do {
               do {
                  if (!enum_.hasMoreElements()) {
                     return true;
                  }

                  e = (ZipEntry)enum_.nextElement();
                  name = e.getName();
                  cgidir = cgidir.replace('\\', '/');
               } while(name.endsWith("/"));
            } while(!this.dirExistsInPath(name, dir));

            File f = new File(tdir, name.replace('/', File.separatorChar));
            (new File(f.getParent())).mkdirs();
            InputStream is = zf.getInputStream(e);
            OutputStream os = new FileOutputStream(f);

            int n;
            while((n = is.read(buf)) > 0) {
               os.write(buf, 0, n);
            }

            is.close();
            os.close();
         }
      } catch (Exception var15) {
         this.log("Failure extracting CGI scripts from WAR file " + tmpDir, var15);
         return false;
      }
   }

   private boolean dirExistsInPath(String file, String[] dir) {
      if (dir == null) {
         return false;
      } else {
         for(int i = 0; i < dir.length; ++i) {
            if (dir[i] != null && file.startsWith(dir[i])) {
               return true;
            }
         }

         return false;
      }
   }

   class CGIServletOutputStream extends OutputStream {
      private ServletOutputStream sos = null;
      private HttpServletResponse res;
      private StringBuffer buf;
      private boolean isBody;
      private boolean nphScript = false;
      String locHeader = null;
      private Chunk chunk = Chunk.getChunk();
      private ByteBuffer byteBuffer;

      CGIServletOutputStream(HttpServletResponse res) {
         this.byteBuffer = ByteBuffer.wrap(this.chunk.buf);
         this.res = res;
         this.buf = new StringBuffer();
         this.isBody = false;
         this.nphScript = false;
         this.locHeader = null;
         this.byteBuffer.clear();
      }

      private OutputStream getOutputStream() throws IOException {
         if (this.sos == null) {
            this.sos = (ServletOutputStreamImpl)this.res.getOutputStream();
         }

         return this.sos;
      }

      public void close() throws IOException {
         Chunk.releaseChunk(this.chunk);
      }

      public void flush() throws IOException {
         if (this.byteBuffer.position() > 0) {
            this.getOutputStream().write(this.byteBuffer.array(), 0, this.byteBuffer.position());
         }

         this.byteBuffer.clear();
      }

      public void write(int b) {
         if (this.isBody) {
            if (!this.byteBuffer.hasRemaining()) {
               try {
                  this.flush();
               } catch (IOException var4) {
                  CGIServlet.this.log("CGIServlet failed to write body for the response", var4);
                  return;
               }
            }

            this.byteBuffer.put((byte)b);
         } else {
            if (b == 13) {
               return;
            }

            if (b != 10) {
               this.buf.append((char)b);
            } else if (this.buf.length() == 0) {
               this.isBody = true;

               try {
                  if (this.locHeader != null && !this.nphScript) {
                     this.res.sendRedirect(this.locHeader);
                  }
               } catch (IOException var3) {
                  CGIServlet.this.log("CGIServlet failed to redirect the request. locHeader=" + this.locHeader, var3);
                  return;
               }
            } else {
               this.processHeader();
            }
         }

      }

      private void processHeader() {
         String header = this.buf.toString();
         this.buf.setLength(0);
         String[] splits;
         if (header.startsWith("HTTP/")) {
            this.res.reset();
            this.nphScript = true;
            splits = StringUtils.splitCompletely(header, " ");
            if (splits.length < 2 || splits[1] == null) {
               return;
            }

            try {
               this.res.setStatus(Integer.parseInt(splits[1]));
            } catch (NumberFormatException var4) {
               CGIServlet.this.log("CGIServlet failed to set StatusHeader", var4);
               return;
            }
         } else {
            splits = StringUtils.split(header, ':');
            if (splits.length >= 2) {
               this.setHeaderElement(splits);
            }
         }

      }

      private void setHeaderElement(String[] splits) {
         splits[0] = splits[0].trim();
         splits[1] = splits[1].trim();
         if ("Content-type".equalsIgnoreCase(splits[0])) {
            this.res.setContentType(splits[1]);
         } else if ("Content-length".equalsIgnoreCase(splits[0])) {
            try {
               this.res.setContentLength(Integer.parseInt(splits[1]));
            } catch (NumberFormatException var5) {
               CGIServlet.this.log("CGIServlet failed to set ContentLength", var5);
            }
         } else if ("Location".equalsIgnoreCase(splits[0])) {
            this.locHeader = new String(splits[1]);
            this.res.setHeader("Location", this.locHeader);
         } else if ("Status".equalsIgnoreCase(splits[0])) {
            String[] status = StringUtils.splitCompletely(splits[1], " ");

            try {
               this.res.setStatus(Integer.parseInt(status[0]));
            } catch (NumberFormatException var4) {
               CGIServlet.this.log("CGIServlet failed to set StatusHeader", var4);
            }
         } else if ("Set-Cookie".equalsIgnoreCase(splits[0])) {
            this.res.addHeader(splits[0], splits[1]);
         } else {
            this.res.setHeader(splits[0], splits[1]);
         }

      }
   }
}
