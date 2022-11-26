package org.apache.velocity.app;

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
import org.apache.velocity.Template;
import org.apache.velocity.context.Context;
import org.apache.velocity.context.InternalContextAdapterImpl;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.RuntimeInstance;
import org.apache.velocity.runtime.configuration.Configuration;
import org.apache.velocity.runtime.parser.ParseException;
import org.apache.velocity.runtime.parser.node.SimpleNode;

public class VelocityEngine implements RuntimeConstants {
   private RuntimeInstance ri = new RuntimeInstance();

   public void init() throws Exception {
      this.ri.init();
   }

   public void init(String propsFilename) throws Exception {
      this.ri.init(propsFilename);
   }

   public void init(Properties p) throws Exception {
      this.ri.init(p);
   }

   public void setProperty(String key, Object value) {
      this.ri.setProperty(key, value);
   }

   public void addProperty(String key, Object value) {
      this.ri.addProperty(key, value);
   }

   public void clearProperty(String key) {
      this.ri.clearProperty(key);
   }

   /** @deprecated */
   public void setConfiguration(Configuration configuration) {
      ExtendedProperties ep = configuration.getExtendedProperties();
      this.ri.setConfiguration(ep);
   }

   public void setExtendedProperties(ExtendedProperties configuration) {
      this.ri.setConfiguration(configuration);
   }

   public Object getProperty(String key) {
      return this.ri.getProperty(key);
   }

   public boolean evaluate(Context context, Writer out, String logTag, String instring) throws ParseErrorException, MethodInvocationException, ResourceNotFoundException, IOException {
      return this.evaluate(context, out, logTag, (Reader)(new BufferedReader(new StringReader(instring))));
   }

   /** @deprecated */
   public boolean evaluate(Context context, Writer writer, String logTag, InputStream instream) throws ParseErrorException, MethodInvocationException, ResourceNotFoundException, IOException {
      BufferedReader br = null;
      String encoding = null;

      try {
         encoding = this.ri.getString("input.encoding", "ISO-8859-1");
         br = new BufferedReader(new InputStreamReader(instream, encoding));
      } catch (UnsupportedEncodingException var9) {
         String msg = "Unsupported input encoding : " + encoding + " for template " + logTag;
         throw new ParseErrorException(msg);
      }

      return this.evaluate(context, writer, logTag, (Reader)br);
   }

   public boolean evaluate(Context context, Writer writer, String logTag, Reader reader) throws ParseErrorException, MethodInvocationException, ResourceNotFoundException, IOException {
      SimpleNode nodeTree = null;

      try {
         nodeTree = this.ri.parse(reader, logTag);
      } catch (ParseException var15) {
         throw new ParseErrorException(var15.getMessage());
      }

      if (nodeTree != null) {
         InternalContextAdapterImpl ica = new InternalContextAdapterImpl(context);
         ica.pushCurrentTemplateName(logTag);

         try {
            try {
               nodeTree.init(ica, this.ri);
            } catch (Exception var13) {
               this.ri.error("Velocity.evaluate() : init exception for tag = " + logTag + " : " + var13);
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

   public boolean invokeVelocimacro(String vmName, String logTag, String[] params, Context context, Writer writer) throws Exception {
      if (vmName != null && params != null && context != null && writer != null && logTag != null) {
         if (!this.ri.isVelocimacro(vmName, logTag)) {
            this.ri.error("VelocityEngine.invokeVelocimacro() : VM '" + vmName + "' not registered.");
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
               boolean retval = this.evaluate(context, writer, logTag, construct.toString());
               return retval;
            } catch (Exception var9) {
               this.ri.error("VelocityEngine.invokeVelocimacro() : error " + var9);
               throw var9;
            }
         }
      } else {
         this.ri.error("VelocityEngine.invokeVelocimacro() : invalid parameter");
         return false;
      }
   }

   public boolean mergeTemplate(String templateName, Context context, Writer writer) throws ResourceNotFoundException, ParseErrorException, MethodInvocationException, Exception {
      return this.mergeTemplate(templateName, this.ri.getString("input.encoding", "ISO-8859-1"), context, writer);
   }

   public boolean mergeTemplate(String templateName, String encoding, Context context, Writer writer) throws ResourceNotFoundException, ParseErrorException, MethodInvocationException, Exception {
      Template template = this.ri.getTemplate(templateName, encoding);
      if (template == null) {
         this.ri.error("Velocity.parseTemplate() failed loading template '" + templateName + "'");
         return false;
      } else {
         template.merge(context, writer);
         return true;
      }
   }

   public Template getTemplate(String name) throws ResourceNotFoundException, ParseErrorException, Exception {
      return this.ri.getTemplate(name);
   }

   public Template getTemplate(String name, String encoding) throws ResourceNotFoundException, ParseErrorException, Exception {
      return this.ri.getTemplate(name, encoding);
   }

   public boolean templateExists(String templateName) {
      return this.ri.getLoaderNameForResource(templateName) != null;
   }

   public void warn(Object message) {
      this.ri.warn(message);
   }

   public void info(Object message) {
      this.ri.info(message);
   }

   public void error(Object message) {
      this.ri.error(message);
   }

   public void debug(Object message) {
      this.ri.debug(message);
   }

   public void setApplicationAttribute(Object key, Object value) {
      this.ri.setApplicationAttribute(key, value);
   }
}
