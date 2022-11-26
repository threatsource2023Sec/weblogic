package weblogic.apache.org.apache.velocity.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Properties;
import org.apache.commons.collections.ExtendedProperties;
import weblogic.apache.org.apache.velocity.Template;
import weblogic.apache.org.apache.velocity.context.Context;
import weblogic.apache.org.apache.velocity.context.InternalContextAdapterImpl;
import weblogic.apache.org.apache.velocity.exception.MethodInvocationException;
import weblogic.apache.org.apache.velocity.exception.ParseErrorException;
import weblogic.apache.org.apache.velocity.exception.ResourceNotFoundException;
import weblogic.apache.org.apache.velocity.runtime.RuntimeConstants;
import weblogic.apache.org.apache.velocity.runtime.RuntimeSingleton;
import weblogic.apache.org.apache.velocity.runtime.configuration.Configuration;
import weblogic.apache.org.apache.velocity.runtime.parser.ParseException;
import weblogic.apache.org.apache.velocity.runtime.parser.node.SimpleNode;

public class Velocity implements RuntimeConstants {
   public static void init() throws Exception {
      RuntimeSingleton.init();
   }

   public static void init(String propsFilename) throws Exception {
      RuntimeSingleton.init(propsFilename);
   }

   public static void init(Properties p) throws Exception {
      RuntimeSingleton.init(p);
   }

   public static void setProperty(String key, Object value) {
      RuntimeSingleton.setProperty(key, value);
   }

   public static void addProperty(String key, Object value) {
      RuntimeSingleton.addProperty(key, value);
   }

   public static void clearProperty(String key) {
      RuntimeSingleton.clearProperty(key);
   }

   /** @deprecated */
   public static void setConfiguration(Configuration configuration) {
      ExtendedProperties ep = configuration.getExtendedProperties();
      RuntimeSingleton.setConfiguration(ep);
   }

   public static void setExtendedProperties(ExtendedProperties configuration) {
      RuntimeSingleton.setConfiguration(configuration);
   }

   public static Object getProperty(String key) {
      return RuntimeSingleton.getProperty(key);
   }

   public static boolean evaluate(Context context, Writer out, String logTag, String instring) throws ParseErrorException, MethodInvocationException, ResourceNotFoundException, IOException {
      return evaluate(context, out, logTag, (Reader)(new BufferedReader(new StringReader(instring))));
   }

   /** @deprecated */
   public static boolean evaluate(Context context, Writer writer, String logTag, InputStream instream) throws ParseErrorException, MethodInvocationException, ResourceNotFoundException, IOException {
      BufferedReader br = null;
      String encoding = null;

      try {
         encoding = RuntimeSingleton.getString("input.encoding", "ISO-8859-1");
         br = new BufferedReader(new InputStreamReader(instream, encoding));
      } catch (UnsupportedEncodingException var8) {
         String msg = "Unsupported input encoding : " + encoding + " for template " + logTag;
         throw new ParseErrorException(msg);
      }

      return evaluate(context, writer, logTag, (Reader)br);
   }

   public static boolean evaluate(Context context, Writer writer, String logTag, Reader reader) throws ParseErrorException, MethodInvocationException, ResourceNotFoundException, IOException {
      SimpleNode nodeTree = null;

      try {
         nodeTree = RuntimeSingleton.parse(reader, logTag);
      } catch (ParseException var14) {
         throw new ParseErrorException(var14.getMessage());
      }

      if (nodeTree != null) {
         InternalContextAdapterImpl ica = new InternalContextAdapterImpl(context);
         ica.pushCurrentTemplateName(logTag);

         try {
            try {
               nodeTree.init(ica, RuntimeSingleton.getRuntimeServices());
            } catch (Exception var12) {
               RuntimeSingleton.error("Velocity.evaluate() : init exception for tag = " + logTag + " : " + var12);
            }

            nodeTree.render(ica, writer);
         } finally {
            ica.popCurrentTemplateName();
         }

         return true;
      } else {
         return false;
      }
   }

   public static boolean invokeVelocimacro(String vmName, String logTag, String[] params, Context context, Writer writer) {
      if (vmName != null && params != null && context != null && writer != null && logTag != null) {
         if (!RuntimeSingleton.isVelocimacro(vmName, logTag)) {
            RuntimeSingleton.error("Velocity.invokeVelocimacro() : VM '" + vmName + "' not registered.");
            return false;
         } else {
            StringBuffer construct = new StringBuffer("#");
            construct.append(vmName);
            construct.append("(");

            for(int i = 0; i < params.length; ++i) {
               construct.append(" $");
               construct.append(params[i]);
            }

            construct.append(" )");

            try {
               boolean retval = evaluate(context, writer, logTag, construct.toString());
               return retval;
            } catch (Exception var8) {
               RuntimeSingleton.error("Velocity.invokeVelocimacro() : error " + var8);
               return false;
            }
         }
      } else {
         RuntimeSingleton.error("Velocity.invokeVelocimacro() : invalid parameter");
         return false;
      }
   }

   /** @deprecated */
   public static boolean mergeTemplate(String templateName, Context context, Writer writer) throws ResourceNotFoundException, ParseErrorException, MethodInvocationException, Exception {
      return mergeTemplate(templateName, RuntimeSingleton.getString("input.encoding", "ISO-8859-1"), context, writer);
   }

   public static boolean mergeTemplate(String templateName, String encoding, Context context, Writer writer) throws ResourceNotFoundException, ParseErrorException, MethodInvocationException, Exception {
      Template template = RuntimeSingleton.getTemplate(templateName, encoding);
      if (template == null) {
         RuntimeSingleton.error("Velocity.parseTemplate() failed loading template '" + templateName + "'");
         return false;
      } else {
         template.merge(context, writer);
         return true;
      }
   }

   public static Template getTemplate(String name) throws ResourceNotFoundException, ParseErrorException, Exception {
      return RuntimeSingleton.getTemplate(name);
   }

   public static Template getTemplate(String name, String encoding) throws ResourceNotFoundException, ParseErrorException, Exception {
      return RuntimeSingleton.getTemplate(name, encoding);
   }

   public static boolean resourceExists(String resourceName) {
      return RuntimeSingleton.getLoaderNameForResource(resourceName) != null;
   }

   public static void warn(Object message) {
      RuntimeSingleton.warn(message);
   }

   public static void info(Object message) {
      RuntimeSingleton.info(message);
   }

   public static void error(Object message) {
      RuntimeSingleton.error(message);
   }

   public static void debug(Object message) {
      RuntimeSingleton.debug(message);
   }

   public static void setApplicationAttribute(Object key, Object value) {
      RuntimeSingleton.getRuntimeInstance().setApplicationAttribute(key, value);
   }

   /** @deprecated */
   public static boolean templateExists(String resourceName) {
      return resourceExists(resourceName);
   }
}
