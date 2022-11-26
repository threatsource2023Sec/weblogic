package weblogic.apache.org.apache.velocity.runtime;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.collections.ExtendedProperties;
import weblogic.apache.org.apache.velocity.Template;
import weblogic.apache.org.apache.velocity.exception.ParseErrorException;
import weblogic.apache.org.apache.velocity.exception.ResourceNotFoundException;
import weblogic.apache.org.apache.velocity.runtime.directive.Directive;
import weblogic.apache.org.apache.velocity.runtime.log.LogManager;
import weblogic.apache.org.apache.velocity.runtime.log.LogSystem;
import weblogic.apache.org.apache.velocity.runtime.log.NullLogSystem;
import weblogic.apache.org.apache.velocity.runtime.log.PrimordialLogSystem;
import weblogic.apache.org.apache.velocity.runtime.parser.ParseException;
import weblogic.apache.org.apache.velocity.runtime.parser.Parser;
import weblogic.apache.org.apache.velocity.runtime.parser.node.SimpleNode;
import weblogic.apache.org.apache.velocity.runtime.resource.ContentResource;
import weblogic.apache.org.apache.velocity.runtime.resource.ResourceManager;
import weblogic.apache.org.apache.velocity.util.SimplePool;
import weblogic.apache.org.apache.velocity.util.StringUtils;
import weblogic.apache.org.apache.velocity.util.introspection.Introspector;
import weblogic.apache.org.apache.velocity.util.introspection.Uberspect;
import weblogic.apache.org.apache.velocity.util.introspection.UberspectLoggable;

public class RuntimeInstance implements RuntimeConstants, RuntimeServices {
   private VelocimacroFactory vmFactory = null;
   private LogSystem logSystem = new PrimordialLogSystem();
   private SimplePool parserPool;
   private boolean initialized;
   private ExtendedProperties overridingProperties = null;
   private Hashtable runtimeDirectives;
   private ExtendedProperties configuration = new ExtendedProperties();
   private ResourceManager resourceManager = null;
   private Introspector introspector = null;
   private Map applicationAttributes = null;
   private Uberspect uberSpect;

   public RuntimeInstance() {
      this.vmFactory = new VelocimacroFactory(this);
      this.introspector = new Introspector(this);
      this.applicationAttributes = new HashMap();
   }

   public synchronized void init() throws Exception {
      if (!this.initialized) {
         this.info("************************************************************** ");
         this.info("Starting Jakarta Velocity v1.4");
         this.info("RuntimeInstance initializing.");
         this.initializeProperties();
         this.initializeLogger();
         this.initializeResourceManager();
         this.initializeDirectives();
         this.initializeParserPool();
         this.initializeIntrospection();
         this.vmFactory.initVelocimacro();
         this.info("Velocity successfully started.");
         this.initialized = true;
      }

   }

   private void initializeIntrospection() throws Exception {
      String rm = this.getString("runtime.introspector.uberspect");
      String err;
      if (rm != null && rm.length() > 0) {
         err = null;

         Object o;
         try {
            o = Class.forName(rm).newInstance();
         } catch (ClassNotFoundException var5) {
            String err = "The specified class for Uberspect (" + rm + ") does not exist (or is not accessible to the current classlaoder.";
            this.error(err);
            throw new Exception(err);
         }

         if (!(o instanceof Uberspect)) {
            String err = "The specified class for Uberspect (" + rm + ") does not implement org.apache.velocity.util.introspector.Uberspect." + " Velocity not initialized correctly.";
            this.error(err);
            throw new Exception(err);
         } else {
            this.uberSpect = (Uberspect)o;
            if (this.uberSpect instanceof UberspectLoggable) {
               ((UberspectLoggable)this.uberSpect).setRuntimeLogger(this);
            }

            this.uberSpect.init();
         }
      } else {
         err = "It appears that no class was specified as the Uberspect.  Please ensure that all configuration information is correct.";
         this.error(err);
         throw new Exception(err);
      }
   }

   private void setDefaultProperties() {
      try {
         InputStream inputStream = this.getClass().getResourceAsStream("/weblogic/apache/org/apache/velocity/runtime/defaults/velocity.properties");
         this.configuration.load(inputStream);
         this.info("Default Properties File: " + (new File("weblogic/apache/org/apache/velocity/runtime/defaults/velocity.properties")).getPath());
      } catch (IOException var2) {
         System.err.println("Cannot get Velocity Runtime default properties!");
      }

   }

   public void setProperty(String key, Object value) {
      if (this.overridingProperties == null) {
         this.overridingProperties = new ExtendedProperties();
      }

      this.overridingProperties.setProperty(key, value);
   }

   public void setConfiguration(ExtendedProperties configuration) {
      if (this.overridingProperties == null) {
         this.overridingProperties = configuration;
      } else if (this.overridingProperties != configuration) {
         this.overridingProperties.combine(configuration);
      }

   }

   public void addProperty(String key, Object value) {
      if (this.overridingProperties == null) {
         this.overridingProperties = new ExtendedProperties();
      }

      this.overridingProperties.addProperty(key, value);
   }

   public void clearProperty(String key) {
      if (this.overridingProperties != null) {
         this.overridingProperties.clearProperty(key);
      }

   }

   public Object getProperty(String key) {
      return this.configuration.getProperty(key);
   }

   private void initializeProperties() {
      if (!this.configuration.isInitialized()) {
         this.setDefaultProperties();
      }

      if (this.overridingProperties != null) {
         this.configuration.combine(this.overridingProperties);
      }

   }

   public void init(Properties p) throws Exception {
      this.overridingProperties = ExtendedProperties.convertProperties(p);
      this.init();
   }

   public void init(String configurationFile) throws Exception {
      this.overridingProperties = new ExtendedProperties(configurationFile);
      this.init();
   }

   private void initializeResourceManager() throws Exception {
      String rm = this.getString("resource.manager.class");
      String err;
      if (rm != null && rm.length() > 0) {
         err = null;

         Object o;
         try {
            o = Class.forName(rm).newInstance();
         } catch (ClassNotFoundException var5) {
            String err = "The specified class for Resourcemanager (" + rm + ") does not exist (or is not accessible to the current classlaoder.";
            this.error(err);
            throw new Exception(err);
         }

         if (!(o instanceof ResourceManager)) {
            String err = "The specified class for ResourceManager (" + rm + ") does not implement org.apache.runtime.resource.ResourceManager." + " Velocity not initialized correctly.";
            this.error(err);
            throw new Exception(err);
         } else {
            this.resourceManager = (ResourceManager)o;
            this.resourceManager.initialize(this);
         }
      } else {
         err = "It appears that no class was specified as the ResourceManager.  Please ensure that all configuration information is correct.";
         this.error(err);
         throw new Exception(err);
      }
   }

   private void initializeLogger() throws Exception {
      if (this.logSystem instanceof PrimordialLogSystem) {
         PrimordialLogSystem pls = (PrimordialLogSystem)this.logSystem;
         this.logSystem = LogManager.createLogSystem(this);
         if (this.logSystem == null) {
            this.logSystem = new NullLogSystem();
         } else {
            pls.dumpLogMessages(this.logSystem);
         }
      }

   }

   private void initializeDirectives() throws Exception {
      this.runtimeDirectives = new Hashtable();
      Properties directiveProperties = new Properties();
      InputStream inputStream = this.getClass().getResourceAsStream("/weblogic/apache/org/apache/velocity/runtime/defaults/directive.properties");
      if (inputStream == null) {
         throw new Exception("Error loading directive.properties! Something is very wrong if these properties aren't being located. Either your Velocity distribution is incomplete or your Velocity jar file is corrupted!");
      } else {
         directiveProperties.load(inputStream);
         Enumeration directiveClasses = directiveProperties.elements();

         while(directiveClasses.hasMoreElements()) {
            String directiveClass = (String)directiveClasses.nextElement();
            this.loadDirective(directiveClass, "System");
         }

         String[] userdirective = this.configuration.getStringArray("userdirective");

         for(int i = 0; i < userdirective.length; ++i) {
            this.loadDirective(userdirective[i], "User");
         }

      }
   }

   private void loadDirective(String directiveClass, String caption) {
      try {
         Object o = Class.forName(directiveClass).newInstance();
         if (o instanceof Directive) {
            Directive directive = (Directive)o;
            this.runtimeDirectives.put(directive.getName(), directive);
            this.info("Loaded " + caption + " Directive: " + directiveClass);
         } else {
            this.error(caption + " Directive " + directiveClass + " is not org.apache.velocity.runtime.directive.Directive." + " Ignoring. ");
         }
      } catch (Exception var5) {
         this.error("Exception Loading " + caption + " Directive: " + directiveClass + " : " + var5);
      }

   }

   private void initializeParserPool() {
      int numParsers = this.getInt("parser.pool.size", 20);
      this.parserPool = new SimplePool(numParsers);

      for(int i = 0; i < numParsers; ++i) {
         this.parserPool.put(this.createNewParser());
      }

      this.info("Created: " + numParsers + " parsers.");
   }

   public Parser createNewParser() {
      Parser parser = new Parser(this);
      parser.setDirectives(this.runtimeDirectives);
      return parser;
   }

   public SimpleNode parse(Reader reader, String templateName) throws ParseException {
      return this.parse(reader, templateName, true);
   }

   public SimpleNode parse(Reader reader, String templateName, boolean dumpNamespace) throws ParseException {
      SimpleNode ast = null;
      Parser parser = (Parser)this.parserPool.get();
      boolean madeNew = false;
      if (parser == null) {
         this.error("Runtime : ran out of parsers. Creating new.   Please increment the parser.pool.size property. The current value is too small.");
         parser = this.createNewParser();
         if (parser != null) {
            madeNew = true;
         }
      }

      if (parser != null) {
         try {
            if (dumpNamespace) {
               this.dumpVMNamespace(templateName);
            }

            ast = parser.parse(reader, templateName);
         } finally {
            if (!madeNew) {
               this.parserPool.put(parser);
            }

         }
      } else {
         this.error("Runtime : ran out of parsers and unable to create more.");
      }

      return ast;
   }

   public Template getTemplate(String name) throws ResourceNotFoundException, ParseErrorException, Exception {
      return this.getTemplate(name, this.getString("input.encoding", "ISO-8859-1"));
   }

   public Template getTemplate(String name, String encoding) throws ResourceNotFoundException, ParseErrorException, Exception {
      return (Template)this.resourceManager.getResource(name, 1, encoding);
   }

   public ContentResource getContent(String name) throws ResourceNotFoundException, ParseErrorException, Exception {
      return this.getContent(name, this.getString("input.encoding", "ISO-8859-1"));
   }

   public ContentResource getContent(String name, String encoding) throws ResourceNotFoundException, ParseErrorException, Exception {
      return (ContentResource)this.resourceManager.getResource(name, 2, encoding);
   }

   public String getLoaderNameForResource(String resourceName) {
      return this.resourceManager.getLoaderNameForResource(resourceName);
   }

   private boolean showStackTrace() {
      return this.configuration.isInitialized() ? this.getBoolean("runtime.log.warn.stacktrace", false) : false;
   }

   private void log(int level, Object message) {
      String out;
      if (!this.showStackTrace() || !(message instanceof Throwable) && !(message instanceof Exception)) {
         out = message.toString();
      } else {
         out = StringUtils.stackTrace((Throwable)message);
      }

      this.logSystem.logVelocityMessage(level, out);
   }

   public void warn(Object message) {
      this.log(2, message);
   }

   public void info(Object message) {
      this.log(1, message);
   }

   public void error(Object message) {
      this.log(3, message);
   }

   public void debug(Object message) {
      this.log(0, message);
   }

   public String getString(String key, String defaultValue) {
      return this.configuration.getString(key, defaultValue);
   }

   public Directive getVelocimacro(String vmName, String templateName) {
      return this.vmFactory.getVelocimacro(vmName, templateName);
   }

   public boolean addVelocimacro(String name, String macro, String[] argArray, String sourceTemplate) {
      return this.vmFactory.addVelocimacro(name, macro, argArray, sourceTemplate);
   }

   public boolean isVelocimacro(String vmName, String templateName) {
      return this.vmFactory.isVelocimacro(vmName, templateName);
   }

   public boolean dumpVMNamespace(String namespace) {
      return this.vmFactory.dumpVMNamespace(namespace);
   }

   public String getString(String key) {
      return this.configuration.getString(key);
   }

   public int getInt(String key) {
      return this.configuration.getInt(key);
   }

   public int getInt(String key, int defaultValue) {
      return this.configuration.getInt(key, defaultValue);
   }

   public boolean getBoolean(String key, boolean def) {
      return this.configuration.getBoolean(key, def);
   }

   public ExtendedProperties getConfiguration() {
      return this.configuration;
   }

   public Introspector getIntrospector() {
      return this.introspector;
   }

   public Object getApplicationAttribute(Object key) {
      return this.applicationAttributes.get(key);
   }

   public Object setApplicationAttribute(Object key, Object o) {
      return this.applicationAttributes.put(key, o);
   }

   public Uberspect getUberspect() {
      return this.uberSpect;
   }
}
