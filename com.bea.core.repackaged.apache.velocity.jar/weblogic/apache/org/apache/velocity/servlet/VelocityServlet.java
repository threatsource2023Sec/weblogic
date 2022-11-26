package weblogic.apache.org.apache.velocity.servlet;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Properties;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import weblogic.apache.org.apache.velocity.Template;
import weblogic.apache.org.apache.velocity.VelocityContext;
import weblogic.apache.org.apache.velocity.app.Velocity;
import weblogic.apache.org.apache.velocity.context.Context;
import weblogic.apache.org.apache.velocity.exception.MethodInvocationException;
import weblogic.apache.org.apache.velocity.exception.ParseErrorException;
import weblogic.apache.org.apache.velocity.exception.ResourceNotFoundException;
import weblogic.apache.org.apache.velocity.io.VelocityWriter;
import weblogic.apache.org.apache.velocity.runtime.RuntimeSingleton;
import weblogic.apache.org.apache.velocity.util.SimplePool;

public abstract class VelocityServlet extends HttpServlet {
   public static final String REQUEST = "req";
   public static final String RESPONSE = "res";
   public static final String CONTENT_TYPE = "default.contentType";
   public static final String DEFAULT_CONTENT_TYPE = "text/html";
   public static final String DEFAULT_OUTPUT_ENCODING = "ISO-8859-1";
   private static String defaultContentType;
   protected static final String INIT_PROPS_KEY = "weblogic.apache.org.apache.velocity.properties";
   private static final String OLD_INIT_PROPS_KEY = "properties";
   private static SimplePool writerPool = new SimplePool(40);

   public void init(ServletConfig config) throws ServletException {
      super.init(config);
      this.initVelocity(config);
      defaultContentType = RuntimeSingleton.getString("default.contentType", "text/html");
   }

   protected void initVelocity(ServletConfig config) throws ServletException {
      try {
         Properties props = this.loadConfiguration(config);
         Velocity.init(props);
      } catch (Exception var3) {
         throw new ServletException("Error initializing Velocity: " + var3, var3);
      }
   }

   protected Properties loadConfiguration(ServletConfig config) throws IOException, FileNotFoundException {
      String propsFile = config.getInitParameter("weblogic.apache.org.apache.velocity.properties");
      if (propsFile == null || propsFile.length() == 0) {
         ServletContext sc = config.getServletContext();
         propsFile = config.getInitParameter("properties");
         if (propsFile != null && propsFile.length() != 0) {
            sc.log("Use of the properties initialization parameter 'properties' has been deprecated by 'org.apache.velocity.properties'");
         } else {
            propsFile = sc.getInitParameter("weblogic.apache.org.apache.velocity.properties");
            if (propsFile == null || propsFile.length() == 0) {
               propsFile = sc.getInitParameter("properties");
               if (propsFile != null && propsFile.length() > 0) {
                  sc.log("Use of the properties initialization parameter 'properties' has been deprecated by 'org.apache.velocity.properties'");
               }
            }
         }
      }

      Properties p = new Properties();
      if (propsFile != null) {
         String realPath = this.getServletContext().getRealPath(propsFile);
         if (realPath != null) {
            propsFile = realPath;
         }

         p.load(new FileInputStream(propsFile));
      }

      return p;
   }

   public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      this.doRequest(request, response);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      this.doRequest(request, response);
   }

   protected void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      Context context = null;

      try {
         context = this.createContext(request, response);
         this.setContentType(request, response);
         Template template = this.handleRequest(request, response, context);
         if (template == null) {
            return;
         }

         this.mergeTemplate(template, context, response);
      } catch (Exception var9) {
         this.error(request, response, var9);
      } finally {
         this.requestCleanup(request, response, context);
      }

   }

   protected void requestCleanup(HttpServletRequest request, HttpServletResponse response, Context context) {
   }

   protected void mergeTemplate(Template template, Context context, HttpServletResponse response) throws ResourceNotFoundException, ParseErrorException, MethodInvocationException, IOException, UnsupportedEncodingException, Exception {
      ServletOutputStream output = response.getOutputStream();
      VelocityWriter vw = null;
      String encoding = response.getCharacterEncoding();

      try {
         vw = (VelocityWriter)writerPool.get();
         if (vw == null) {
            vw = new VelocityWriter(new OutputStreamWriter(output, encoding), 4096, true);
         } else {
            vw.recycle(new OutputStreamWriter(output, encoding));
         }

         template.merge(context, vw);
      } finally {
         try {
            if (vw != null) {
               vw.flush();
               vw.recycle((Writer)null);
               writerPool.put(vw);
            }
         } catch (Exception var13) {
         }

      }

   }

   protected void setContentType(HttpServletRequest request, HttpServletResponse response) {
      String contentType = defaultContentType;
      int index = contentType.lastIndexOf(59) + 1;
      if (index <= 0 || index < contentType.length() && contentType.indexOf("charset", index) == -1) {
         String encoding = this.chooseCharacterEncoding(request);
         if (!"ISO-8859-1".equalsIgnoreCase(encoding)) {
            contentType = contentType + "; charset=" + encoding;
         }
      }

      response.setContentType(contentType);
   }

   protected String chooseCharacterEncoding(HttpServletRequest request) {
      return RuntimeSingleton.getString("output.encoding", "ISO-8859-1");
   }

   protected Context createContext(HttpServletRequest request, HttpServletResponse response) {
      VelocityContext context = new VelocityContext();
      context.put("req", request);
      context.put("res", response);
      return context;
   }

   public Template getTemplate(String name) throws ResourceNotFoundException, ParseErrorException, Exception {
      return RuntimeSingleton.getTemplate(name);
   }

   public Template getTemplate(String name, String encoding) throws ResourceNotFoundException, ParseErrorException, Exception {
      return RuntimeSingleton.getTemplate(name, encoding);
   }

   protected Template handleRequest(HttpServletRequest request, HttpServletResponse response, Context ctx) throws Exception {
      Template t = this.handleRequest(ctx);
      if (t == null) {
         throw new Exception("handleRequest(Context) returned null - no template selected!");
      } else {
         return t;
      }
   }

   /** @deprecated */
   protected Template handleRequest(Context ctx) throws Exception {
      throw new Exception("You must override VelocityServlet.handleRequest( Context)  or VelocityServlet.handleRequest( HttpServletRequest,  HttpServletResponse, Context)");
   }

   protected void error(HttpServletRequest request, HttpServletResponse response, Exception cause) throws ServletException, IOException {
      StringBuffer html = new StringBuffer();
      html.append("<html>");
      html.append("<title>Error</title>");
      html.append("<body bgcolor=\"#ffffff\">");
      html.append("<h2>VelocityServlet: Error processing the template</h2>");
      html.append("<pre>");
      String why = cause.getMessage();
      if (why != null && why.trim().length() > 0) {
         html.append(why);
         html.append("<br>");
      }

      StringWriter sw = new StringWriter();
      cause.printStackTrace(new PrintWriter(sw));
      html.append(sw.toString());
      html.append("</pre>");
      html.append("</body>");
      html.append("</html>");
      response.getOutputStream().print(html.toString());
   }
}
