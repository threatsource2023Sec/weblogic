package weblogic.apache.org.apache.velocity.runtime;

import java.io.Reader;
import java.util.Properties;
import org.apache.commons.collections.ExtendedProperties;
import weblogic.apache.org.apache.velocity.Template;
import weblogic.apache.org.apache.velocity.exception.ParseErrorException;
import weblogic.apache.org.apache.velocity.exception.ResourceNotFoundException;
import weblogic.apache.org.apache.velocity.runtime.directive.Directive;
import weblogic.apache.org.apache.velocity.runtime.parser.ParseException;
import weblogic.apache.org.apache.velocity.runtime.parser.Parser;
import weblogic.apache.org.apache.velocity.runtime.parser.node.SimpleNode;
import weblogic.apache.org.apache.velocity.runtime.resource.ContentResource;
import weblogic.apache.org.apache.velocity.util.introspection.Introspector;
import weblogic.apache.org.apache.velocity.util.introspection.Uberspect;

public class RuntimeSingleton implements RuntimeConstants {
   private static RuntimeInstance ri = new RuntimeInstance();

   public static synchronized void init() throws Exception {
      ri.init();
   }

   public static RuntimeServices getRuntimeServices() {
      return ri;
   }

   public static void setProperty(String key, Object value) {
      ri.setProperty(key, value);
   }

   public static void setConfiguration(ExtendedProperties configuration) {
      ri.setConfiguration(configuration);
   }

   public static void addProperty(String key, Object value) {
      ri.addProperty(key, value);
   }

   public static void clearProperty(String key) {
      ri.clearProperty(key);
   }

   public static Object getProperty(String key) {
      return ri.getProperty(key);
   }

   public static void init(Properties p) throws Exception {
      ri.init(p);
   }

   public static void init(String configurationFile) throws Exception {
      ri.init(configurationFile);
   }

   private static Parser createNewParser() {
      return ri.createNewParser();
   }

   public static SimpleNode parse(Reader reader, String templateName) throws ParseException {
      return ri.parse(reader, templateName);
   }

   public static SimpleNode parse(Reader reader, String templateName, boolean dumpNamespace) throws ParseException {
      return ri.parse(reader, templateName, dumpNamespace);
   }

   public static Template getTemplate(String name) throws ResourceNotFoundException, ParseErrorException, Exception {
      return ri.getTemplate(name);
   }

   public static Template getTemplate(String name, String encoding) throws ResourceNotFoundException, ParseErrorException, Exception {
      return ri.getTemplate(name, encoding);
   }

   public static ContentResource getContent(String name) throws ResourceNotFoundException, ParseErrorException, Exception {
      return ri.getContent(name);
   }

   public static ContentResource getContent(String name, String encoding) throws ResourceNotFoundException, ParseErrorException, Exception {
      return ri.getContent(name, encoding);
   }

   public static String getLoaderNameForResource(String resourceName) {
      return ri.getLoaderNameForResource(resourceName);
   }

   public static void warn(Object message) {
      ri.warn(message);
   }

   public static void info(Object message) {
      ri.info(message);
   }

   public static void error(Object message) {
      ri.error(message);
   }

   public static void debug(Object message) {
      ri.debug(message);
   }

   public static String getString(String key, String defaultValue) {
      return ri.getString(key, defaultValue);
   }

   public static Directive getVelocimacro(String vmName, String templateName) {
      return ri.getVelocimacro(vmName, templateName);
   }

   public static boolean addVelocimacro(String name, String macro, String[] argArray, String sourceTemplate) {
      return ri.addVelocimacro(name, macro, argArray, sourceTemplate);
   }

   public static boolean isVelocimacro(String vmName, String templateName) {
      return ri.isVelocimacro(vmName, templateName);
   }

   public static boolean dumpVMNamespace(String namespace) {
      return ri.dumpVMNamespace(namespace);
   }

   public static String getString(String key) {
      return ri.getString(key);
   }

   public static int getInt(String key) {
      return ri.getInt(key);
   }

   public static int getInt(String key, int defaultValue) {
      return ri.getInt(key, defaultValue);
   }

   public static boolean getBoolean(String key, boolean def) {
      return ri.getBoolean(key, def);
   }

   public static ExtendedProperties getConfiguration() {
      return ri.getConfiguration();
   }

   public static Introspector getIntrospector() {
      return ri.getIntrospector();
   }

   public static Object getApplicationAttribute(Object key) {
      return ri.getApplicationAttribute(key);
   }

   public static Uberspect getUberspect() {
      return ri.getUberspect();
   }

   /** @deprecated */
   public static RuntimeInstance getRuntimeInstance() {
      return ri;
   }
}
