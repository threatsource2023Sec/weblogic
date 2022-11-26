package org.apache.velocity.runtime;

import java.io.Reader;
import java.util.Properties;
import org.apache.commons.collections.ExtendedProperties;
import org.apache.velocity.Template;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.ParseException;
import org.apache.velocity.runtime.parser.node.SimpleNode;
import org.apache.velocity.runtime.resource.ContentResource;

/** @deprecated */
public class Runtime implements RuntimeConstants {
   public static synchronized void init() throws Exception {
      RuntimeSingleton.init();
   }

   public static void setProperty(String key, Object value) {
      RuntimeSingleton.setProperty(key, value);
   }

   public static void setConfiguration(ExtendedProperties configuration) {
      RuntimeSingleton.setConfiguration(configuration);
   }

   public static void addProperty(String key, Object value) {
      RuntimeSingleton.addProperty(key, value);
   }

   public static void clearProperty(String key) {
      RuntimeSingleton.clearProperty(key);
   }

   public static Object getProperty(String key) {
      return RuntimeSingleton.getProperty(key);
   }

   public static void init(Properties p) throws Exception {
      RuntimeSingleton.init(p);
   }

   public static void init(String configurationFile) throws Exception {
      RuntimeSingleton.init(configurationFile);
   }

   public static SimpleNode parse(Reader reader, String templateName) throws ParseException {
      return RuntimeSingleton.parse(reader, templateName);
   }

   public static SimpleNode parse(Reader reader, String templateName, boolean dumpNamespace) throws ParseException {
      return RuntimeSingleton.parse(reader, templateName, dumpNamespace);
   }

   public static Template getTemplate(String name) throws ResourceNotFoundException, ParseErrorException, Exception {
      return RuntimeSingleton.getTemplate(name);
   }

   public static Template getTemplate(String name, String encoding) throws ResourceNotFoundException, ParseErrorException, Exception {
      return RuntimeSingleton.getTemplate(name, encoding);
   }

   public static ContentResource getContent(String name) throws ResourceNotFoundException, ParseErrorException, Exception {
      return RuntimeSingleton.getContent(name);
   }

   public static ContentResource getContent(String name, String encoding) throws ResourceNotFoundException, ParseErrorException, Exception {
      return RuntimeSingleton.getContent(name, encoding);
   }

   public static String getLoaderNameForResource(String resourceName) {
      return RuntimeSingleton.getLoaderNameForResource(resourceName);
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

   public static String getString(String key, String defaultValue) {
      return RuntimeSingleton.getString(key, defaultValue);
   }

   public static Directive getVelocimacro(String vmName, String templateName) {
      return RuntimeSingleton.getVelocimacro(vmName, templateName);
   }

   public static boolean addVelocimacro(String name, String macro, String[] argArray, String sourceTemplate) {
      return RuntimeSingleton.addVelocimacro(name, macro, argArray, sourceTemplate);
   }

   public static boolean isVelocimacro(String vmName, String templateName) {
      return RuntimeSingleton.isVelocimacro(vmName, templateName);
   }

   public static boolean dumpVMNamespace(String namespace) {
      return RuntimeSingleton.dumpVMNamespace(namespace);
   }

   public static String getString(String key) {
      return RuntimeSingleton.getString(key);
   }

   public static int getInt(String key) {
      return RuntimeSingleton.getInt(key);
   }

   public static int getInt(String key, int defaultValue) {
      return RuntimeSingleton.getInt(key, defaultValue);
   }

   public static boolean getBoolean(String key, boolean def) {
      return RuntimeSingleton.getBoolean(key, def);
   }

   public static ExtendedProperties getConfiguration() {
      return RuntimeSingleton.getConfiguration();
   }
}
