package weblogic.apache.org.apache.velocity.texen;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Properties;
import weblogic.apache.org.apache.velocity.Template;
import weblogic.apache.org.apache.velocity.VelocityContext;
import weblogic.apache.org.apache.velocity.app.VelocityEngine;
import weblogic.apache.org.apache.velocity.context.Context;

public class Generator {
   public static final String OUTPUT_PATH = "output.path";
   public static final String TEMPLATE_PATH = "template.path";
   private static final String DEFAULT_TEXEN_PROPERTIES = "weblogic/apache/org/apache/velocity/texen/defaults/texen.properties";
   private Properties props = new Properties();
   private Context controlContext;
   private Hashtable writers = new Hashtable();
   private static Generator instance = new Generator();
   protected String outputEncoding;
   protected String inputEncoding;
   protected VelocityEngine ve;
   // $FF: synthetic field
   static Class class$weblogic$apache$org$apache$velocity$app$VelocityEngine;

   private Generator() {
      this.setDefaultProps();
   }

   public static Generator getInstance() {
      return instance;
   }

   public void setVelocityEngine(VelocityEngine ve) {
      this.ve = ve;
   }

   public Generator(String propFile) {
      try {
         BufferedInputStream bi = null;

         try {
            bi = new BufferedInputStream(new FileInputStream(propFile));
            this.props.load(bi);
         } finally {
            if (bi != null) {
               bi.close();
            }

         }
      } catch (Exception var8) {
         this.setDefaultProps();
      }

   }

   public Generator(Properties props) {
      this.props = (Properties)props.clone();
   }

   protected void setDefaultProps() {
      ClassLoader classLoader = (class$weblogic$apache$org$apache$velocity$app$VelocityEngine == null ? (class$weblogic$apache$org$apache$velocity$app$VelocityEngine = class$("weblogic.apache.org.apache.velocity.app.VelocityEngine")) : class$weblogic$apache$org$apache$velocity$app$VelocityEngine).getClassLoader();

      try {
         InputStream inputStream = null;

         try {
            inputStream = classLoader.getResourceAsStream("weblogic/apache/org/apache/velocity/texen/defaults/texen.properties");
            this.props.load(inputStream);
         } finally {
            if (inputStream != null) {
               inputStream.close();
            }

         }
      } catch (Exception var8) {
         System.err.println("Cannot get default properties!");
      }

   }

   public void setTemplatePath(String templatePath) {
      this.props.put("template.path", templatePath);
   }

   public String getTemplatePath() {
      return this.props.getProperty("template.path");
   }

   public void setOutputPath(String outputPath) {
      this.props.put("output.path", outputPath);
   }

   public String getOutputPath() {
      return this.props.getProperty("output.path");
   }

   public void setOutputEncoding(String outputEncoding) {
      this.outputEncoding = outputEncoding;
   }

   public void setInputEncoding(String inputEncoding) {
      this.inputEncoding = inputEncoding;
   }

   public Writer getWriter(String path, String encoding) throws Exception {
      Object writer;
      if (encoding != null && encoding.length() != 0 && !encoding.equals("8859-1") && !encoding.equals("8859_1")) {
         writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), encoding));
      } else {
         writer = new FileWriter(path);
      }

      return (Writer)writer;
   }

   public Template getTemplate(String templateName, String encoding) throws Exception {
      Template template;
      if (encoding != null && encoding.length() != 0 && !encoding.equals("8859-1") && !encoding.equals("8859_1")) {
         template = this.ve.getTemplate(templateName, encoding);
      } else {
         template = this.ve.getTemplate(templateName);
      }

      return template;
   }

   public String parse(String inputTemplate, String outputFile) throws Exception {
      return this.parse(inputTemplate, outputFile, (String)null, (Object)null);
   }

   public String parse(String inputTemplate, String outputFile, String objectID, Object object) throws Exception {
      return this.parse(inputTemplate, (String)null, outputFile, (String)null, objectID, object);
   }

   public String parse(String inputTemplate, String intputEncoding, String outputFile, String outputEncoding, String objectID, Object object) throws Exception {
      if (objectID != null && object != null) {
         this.controlContext.put(objectID, object);
      }

      Template template = this.getTemplate(inputTemplate, this.inputEncoding != null ? this.inputEncoding : this.inputEncoding);
      StringWriter sw;
      if (outputFile != null && !outputFile.equals("")) {
         sw = null;
         Writer writer;
         if (this.writers.get(outputFile) == null) {
            writer = this.getWriter(this.getOutputPath() + File.separator + outputFile, outputEncoding != null ? outputEncoding : this.outputEncoding);
            this.writers.put(outputFile, writer);
         } else {
            writer = (Writer)this.writers.get(outputFile);
         }

         VelocityContext vc = new VelocityContext(this.controlContext);
         template.merge(vc, writer);
         return "";
      } else {
         sw = new StringWriter();
         template.merge(this.controlContext, sw);
         return sw.toString();
      }
   }

   public String parse(String controlTemplate, Context controlContext) throws Exception {
      this.controlContext = controlContext;
      this.fillContextDefaults(this.controlContext);
      this.fillContextProperties(this.controlContext);
      Template template = this.getTemplate(controlTemplate, this.inputEncoding);
      StringWriter sw = new StringWriter();
      template.merge(controlContext, sw);
      return sw.toString();
   }

   protected Context getContext(Hashtable objs) {
      this.fillContextHash(this.controlContext, objs);
      return this.controlContext;
   }

   protected void fillContextHash(Context context, Hashtable objs) {
      Enumeration enum = objs.keys();

      while(enum.hasMoreElements()) {
         String key = enum.nextElement().toString();
         context.put(key, objs.get(key));
      }

   }

   protected void fillContextDefaults(Context context) {
      context.put("generator", instance);
      context.put("outputDirectory", this.getOutputPath());
   }

   protected void fillContextProperties(Context context) {
      Enumeration enum = this.props.propertyNames();

      while(enum.hasMoreElements()) {
         String nm = (String)enum.nextElement();
         if (nm.startsWith("context.objects.")) {
            String contextObj = this.props.getProperty(nm);
            int colon = nm.lastIndexOf(46);
            String contextName = nm.substring(colon + 1);

            try {
               Class cls = Class.forName(contextObj);
               Object o = cls.newInstance();
               context.put(contextName, o);
            } catch (Exception var9) {
               var9.printStackTrace();
            }
         }
      }

   }

   public void shutdown() {
      Iterator iterator = this.writers.values().iterator();

      while(iterator.hasNext()) {
         Writer writer = (Writer)iterator.next();

         try {
            writer.flush();
            writer.close();
         } catch (Exception var4) {
         }
      }

      this.writers.clear();
   }

   // $FF: synthetic method
   static Class class$(String x0) {
      try {
         return Class.forName(x0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }
}
