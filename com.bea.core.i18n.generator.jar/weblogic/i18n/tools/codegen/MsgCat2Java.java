package weblogic.i18n.tools.codegen;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import weblogic.i18n.logging.Severities;
import weblogic.i18n.tools.Config;
import weblogic.i18n.tools.LogMessage;
import weblogic.i18n.tools.Message;
import weblogic.i18n.tools.MessageCatalog;
import weblogic.i18n.tools.MessageCatalogParser;
import weblogic.utils.Getopt2;
import weblogic.utils.compiler.BadOutputException;
import weblogic.utils.compiler.CodeGenerationException;
import weblogic.utils.compiler.CodeGenerator;

public final class MsgCat2Java extends CodeGenerator {
   private static final String WLS_TEXT_FORMATTER_TEMPLATE = "TextFormatter.j";
   private static final String GLASSFISH_TEXT_FORMATTER_TEMPLATE = "GlassfishTextFormatter.j";
   private static final String WLS_LOGGER_TEMPLATE = "Logger.j";
   private static final String GLASSFISH_LOGGER_TEMPLATE = "GlassfishLogger.j";
   private static final String CSS_LOGGER_TEMPLATE = "CSSLogger.j";
   public static final String VERBOSE = "verbose";
   public static final String DEBUG = "debug";
   public static final String IGNORE = "ignore";
   public static final String SERVER = "server";
   public static final String KEEP = "keepgenerated";
   public static final String GENERATED = "generated";
   public static final String NOBUILD = "nobuild";
   public static final String BUILD = "build";
   public static final String NOWRITE = "nowrite";
   public static final String CLASSPATH = "classpath";
   public static final String COMPILE = "compile";
   public static final String SOURCE = "source";
   public static final String TARGET = "target";
   public static final String I18N = "i18n";
   public static final String L10N = "l10n";
   public static final String DEPRECATION = "deprecation";
   public static final String NOWARN = "nowarn";
   public static final String GENERATEANTDEPENDENCY = "generateantdependency";
   public static final String CHECKDEPENDS = "checkdepends";
   public static final String DATES = "dates";
   public static final String METHODPROPS = "methodprops";
   public static final String COMMENTARY = "commentary";
   public static final String STRICT_PACKAGE_CHECKING = "strict-package-checking";
   public static final String RANGE = "range";
   public static final String GLASSFISH = "glassfish";
   public static final String CSS = "css";
   public static final String RANGE_SERVER = "server";
   public static final String RANGE_CLIENT = "client";
   private Output currentOutput;
   private LogMessage currentLogMessage;
   private Message currentMessage;
   public Set outputs = new HashSet();
   private HashSet catalogs = new HashSet();
   private Output o;
   private Output o2;
   private boolean verbose = false;
   private boolean commentary = false;
   private boolean debug = false;
   private boolean server = false;
   private boolean ignore = false;
   private boolean nobuild = false;
   private boolean build = false;
   private boolean nowrite = false;
   private boolean compile = false;
   private boolean i18n = false;
   private boolean l10n = false;
   private boolean nowarn = false;
   private boolean dates = false;
   private boolean methodProps = false;
   private String generated = null;
   private boolean checkDepends = false;
   private String source = "1.4";
   private String target = "1.4";
   private boolean strictPackageCheckingEnabled = false;
   private boolean useGlassfishLoggerTemplate = false;
   private boolean useCssLoggerTemplate = false;
   private static final Map GF_SEV_LEVEL_MAP = new HashMap() {
      {
         this.put("Emergency".toLowerCase(), "LogLevel.EMERGENCY");
         this.put("Alert".toLowerCase(), "LogLevel.ALERT");
         this.put("Critical".toLowerCase(), "Level.SEVERE");
         this.put("Error".toLowerCase(), "Level.SEVERE");
         this.put("Warning".toLowerCase(), "Level.WARNING");
         this.put("Notice".toLowerCase(), "Level.INFO");
         this.put("Info".toLowerCase(), "Level.INFO");
         this.put("Debug".toLowerCase(), "Level.FINE");
         this.put("Trace".toLowerCase(), "Level.FINER");
      }
   };
   private static final Map GF_SEV_LEVEL_NAME_MAP = new HashMap() {
      {
         this.put("Emergency".toLowerCase(), "EMERGENCY");
         this.put("Alert".toLowerCase(), "ALERT");
         this.put("Critical".toLowerCase(), "SEVERE");
         this.put("Error".toLowerCase(), "SEVERE");
         this.put("Warning".toLowerCase(), "WARNING");
         this.put("Notice".toLowerCase(), "INFO");
         this.put("Info".toLowerCase(), "INFO");
         this.put("Debug".toLowerCase(), "FINE");
         this.put("Trace".toLowerCase(), "FINER");
      }
   };

   public MsgCat2Java(Getopt2 opts) {
      super(opts);
      opts.addFlag("compile", "compile generated source.");
      opts.addOption("source", "source", "The JRE version to which generated sources must comply(defaults to  " + this.source + ")");
      opts.addOption("target", "target", "The JRE version for which the compiled classes are targeted(defaults to  " + this.target + ")");
      opts.addOption("classpath", "path", "Classpath to use.");
      opts.addAlias("cp", "classpath");
      opts.addFlag("l10n", "generate localizers.");
      opts.addFlag("i18n", "generate internationalizers.");
      opts.addFlag("build", "Generate and compile catalogs.");
      opts.addAlias("b", "build");
      opts.addFlag("nobuild", "parse and validate only.");
      opts.addAlias("n", "nobuild");
      opts.addFlag("keepgenerated", "keep generated java source.");
      opts.addOption("generated", "generated", "Target directory for the intermediate sources generated.");
      opts.addAlias("k", "keepgenerated");
      opts.addFlag("deprecation", "show deprecated uses.");
      opts.addFlag("ignore", "ignore errors.");
      opts.addFlag("verbose", "Verbose output.");
      opts.addFlag("commentary", "Verbose compiler output.");
      opts.addFlag("server", "Server mode.");
      opts.addAlias("s", "server");
      opts.markPrivate("server");
      opts.addFlag("range", "client or server.");
      opts.markPrivate("range");
      opts.addFlag("debug", "Debug output.");
      opts.markPrivate("debug");
      opts.addFlag("nowarn", "do not display warning messages.");
      opts.markPrivate("nowarn");
      opts.addOption("generateantdependency", "build-xml-filename", "Target build xml filename (e.g. build-msgcat-deplist.xml) ");
      opts.addFlag("dates", "Update timestamps.");
      opts.addFlag("methodprops", "Generate id/log method properties file");
      opts.markPrivate("methodprops");
      opts.markPrivate("generateantdependency");
      opts.setUsageArgs("[filelist]");
      opts.addOption("compiler", "compiler", "compiler to be used");
      opts.addFlag("checkdepends", "Test if the target files are more up to date than the catalog.");
      opts.addFlag("strict-package-checking", "Check for default i18n and l10n packages");
      opts.markPrivate("strict-package-checking");
      opts.addFlag("glassfish", "Generate Logger that directly uses java.util.logging directly");
      opts.markPrivate("glassfish");
      opts.addFlag("css", "Generate Logger that implements CSS-specific Logger methods");
      opts.markPrivate("css");
   }

   protected void extractOptionValues(Getopt2 opts) {
      if (opts.hasOption("build")) {
         try {
            opts.setFlag("i18n", true);
            opts.setFlag("l10n", true);
            opts.setFlag("compile", true);
            opts.setFlag("keepgenerated", true);
         } catch (Exception var3) {
            System.err.println("Unable to apply build option");
            this.debug = true;
         }
      }

      if (opts.hasOption("server")) {
         this.server = true;
      } else if (opts.hasOption("range")) {
         this.server = opts.getOption("range").equals("server");
      }

      this.ignore = opts.hasOption("ignore");
      this.verbose = opts.hasOption("verbose");
      this.commentary = opts.hasOption("commentary");
      this.debug = opts.hasOption("debug");
      this.nobuild = opts.hasOption("nobuild");
      this.nowrite = opts.hasOption("nowrite");
      this.i18n = opts.hasOption("i18n");
      this.l10n = opts.hasOption("l10n");
      this.compile = opts.hasOption("compile");
      this.nowarn = opts.hasOption("nowarn");
      this.dates = opts.hasOption("dates");
      this.methodProps = opts.hasOption("methodprops");
      this.generated = opts.getOption("generated");
      this.checkDepends = opts.hasOption("checkdepends");
      this.source = opts.getOption("source");
      this.target = opts.getOption("target");
      this.strictPackageCheckingEnabled = opts.hasOption("strict-package-checking");
      this.useGlassfishLoggerTemplate = opts.hasOption("glassfish");
      this.useCssLoggerTemplate = opts.hasOption("css");
      MsgCat2Java.Output.generated = this.generated;
      if (this.debug) {
         System.err.println("Options:");
         System.err.println("        server=" + this.server);
         System.err.println("        ignore=" + this.ignore);
         System.err.println("        verbose=" + this.verbose);
         System.err.println("        commentary=" + this.commentary);
         System.err.println("        debug=" + this.debug);
         System.err.println("        nobuild=" + this.nobuild);
         System.err.println("        build=" + this.build);
         System.err.println("        nowrite=" + this.nowrite);
         System.err.println("        i18n=" + this.i18n);
         System.err.println("        l10n=" + this.l10n);
         System.err.println("        compile=" + this.compile);
         System.err.println("        nowarn=" + this.nowarn);
         System.err.println("        dates=" + this.dates);
         System.err.println("        methodprops=" + this.methodProps);
         System.err.println("        generated=" + this.generated);
         System.err.println("        source=" + this.source);
         System.err.println("        target=" + this.target);
      }

   }

   public HashSet getCatalogs() {
      return this.catalogs;
   }

   public boolean testUpToDate(Output output) {
      File sourceFile = new File(output.getMessageCatalog().getFullName());
      File targetFile = new File(output.getSrcName());
      if (!targetFile.isAbsolute()) {
         File destinationDir = new File(this.getRootDirectoryName(), output.getPackage().replace('.', File.separatorChar));
         targetFile = new File(destinationDir, output.getSrcName());
      }

      if (targetFile.exists() && targetFile.lastModified() > sourceFile.lastModified()) {
         System.out.println(targetFile.getName() + " is up to date");
         return true;
      } else {
         return false;
      }
   }

   private void addOutput(Output output) {
      if (!this.checkDepends || !this.testUpToDate(output)) {
         this.outputs.add(output);
      }
   }

   private void buildOutputs(String name) throws SAXException, ParserConfigurationException, IOException {
      boolean builtFormatter = false;
      File f = new File(name);
      if (f.canRead() && name.endsWith(".xml")) {
         Config cfg = new Config();
         cfg.setVerbose(this.verbose);
         cfg.setDebug(this.debug);
         cfg.setServer(this.server);
         cfg.setStrictPackageCheckingEnabled(this.strictPackageCheckingEnabled);
         MessageCatalogParser parser = new MessageCatalogParser(cfg, this.verbose);
         parser.setNoWarn(this.nowarn);

         try {
            MessageCatalog msgcat = parser.parse(name);
            if (msgcat != null && !this.nobuild) {
               this.catalogs.add(msgcat);
               if (this.l10n) {
                  if (msgcat.getCatType() == 2) {
                     if (this.debug) {
                        System.err.println("Generating output: " + name + " for LogLocalizer");
                     }

                     this.o = new Output(name, "LogLocalizer.p", msgcat);
                     this.addOutput(this.o);
                     if (this.debug) {
                        System.err.println("Generating output: " + name + " for LogLocalizerDetail");
                     }

                     this.o = new Output(name, "LogLocalizerDetail.p", msgcat);
                     this.addOutput(this.o);
                  } else if (msgcat.getCatType() == 1) {
                     if (this.debug) {
                        System.err.println("Generating output: " + name + " for TextLocalizer");
                     }

                     this.o = new Output(name, "TextLocalizer.p", msgcat);
                     this.addOutput(this.o);
                  }
               }

               if (this.i18n) {
                  if (msgcat.getCatType() == 2) {
                     if (this.debug) {
                        System.err.println("Generating output: " + name + " for Logger");
                     }

                     this.o2 = new Output(name, this.useCssLoggerTemplate ? "CSSLogger.j" : (this.useGlassfishLoggerTemplate ? "GlassfishLogger.j" : "Logger.j"), msgcat);
                     this.addOutput(this.o2);
                  } else if (msgcat.getCatType() == 1 && !builtFormatter) {
                     if (this.debug) {
                        System.err.println("num methods: " + msgcat.getNumMethods());
                     }

                     if (msgcat.getNumMethods() > 0) {
                        if (this.debug) {
                           System.err.println("Generating output: " + name + " for TextFormatter");
                        }

                        this.o = new Output(name, this.useGlassfishLoggerTemplate ? "GlassfishTextFormatter.j" : "TextFormatter.j", msgcat);
                        this.addOutput(this.o);
                     }
                  }
               }
            } else {
               System.err.println("Unable to parse " + name);
            }
         } catch (SAXException var7) {
            System.err.print(name + ": " + var7.getMessage());
            if (!this.ignore) {
               throw var7;
            }
         } catch (ParserConfigurationException var8) {
            System.err.print(name + ": " + var8.getMessage());
            if (!this.ignore) {
               throw var8;
            }
         } catch (IOException var9) {
            System.err.print(name + ": " + var9.getMessage());
            if (!this.ignore) {
               throw var9;
            }
         }
      } else {
         System.err.println("ignoring " + name);
         if (!name.endsWith(".xml")) {
            System.err.println("filename does not end in .xml");
         } else if (!f.exists()) {
            System.err.println("file does not exist.");
         } else if (!f.canRead()) {
            System.err.println("file cannot be read");
         }
      }

   }

   public Enumeration outputs(Object[] inputs) throws Exception {
      XMLFilter filter = new XMLFilter();

      try {
         for(int i = 0; i < inputs.length; ++i) {
            File f = new File((String)inputs[i]);
            String fName = f.getAbsolutePath();
            if (this.debug) {
               System.err.println("Processing input: " + fName);
            }

            if (f.isDirectory()) {
               File[] fArray = f.listFiles(filter);

               for(int j = 0; j < fArray.length; ++j) {
                  String currName = fArray[j].getAbsolutePath();
                  this.buildOutputs(currName);
               }
            } else {
               this.buildOutputs(fName);
            }
         }

         return Collections.enumeration(this.outputs);
      } catch (Throwable var9) {
         return null;
      }
   }

   public String[] getOutputFileNames() {
      String targetDir = this.getRootDirectoryName();
      String[] filenames = new String[this.outputs.size()];
      Iterator iter = this.outputs.iterator();

      for(int i = 0; i < filenames.length && iter.hasNext(); ++i) {
         Output o = (Output)iter.next();
         String filename = targetDir + File.separatorChar + o.getPackage().replace('.', File.separatorChar) + File.separatorChar + o.getOutputFile();
         filenames[i] = filename;
      }

      return filenames;
   }

   protected void prepare(CodeGenerator.Output output) throws BadOutputException {
      this.currentOutput = (Output)output;
      if (this.currentOutput.getMessageCatalog() == null) {
         throw new BadOutputException("XML errors: " + this.currentOutput.getSrcName() + "not being generated");
      }
   }

   public String className() {
      return this.currentOutput.getClassName();
   }

   public String textMessageDescription() {
      return this.currentMessage.getMessageBody().getCdata();
   }

   public String textPackageDeclaration() {
      if (this.currentOutput.getPackage() == null) {
         return "";
      } else {
         StringBuilder result = new StringBuilder("package ");
         result.append(this.currentOutput.getMessageCatalog().getL10nPackage());
         result.append(";");
         return result.toString();
      }
   }

   public String formatterClass() {
      StringBuilder result = new StringBuilder("TextLocalizer");
      String cName = this.currentOutput.getClassName();
      int i = cName.lastIndexOf("TextFormatter");
      result.insert(0, cName.substring(0, i));
      result.insert(0, this.currentOutput.getMessageCatalog().getL10nPackage() + ".");
      if (this.debug) {
         System.err.println(result.toString());
      }

      return result.toString();
   }

   public String textMessages() throws CodeGenerationException {
      StringBuilder result = new StringBuilder();
      Iterator it = this.currentOutput.getMessageCatalog().getMessages().iterator();

      while(it.hasNext()) {
         this.currentMessage = (Message)it.next();
         if (!this.currentMessage.isRetired() && this.currentMessage.getMethodName() != null && this.currentMessage.getMethodName().length() > 0) {
            result.append(this.parse(this.getProductionRule("textMessage")));
            result.append("\n");
         }
      }

      return result.toString();
   }

   public String textMethodName() {
      return this.currentMessage.getMethodName();
   }

   public String textArguments() {
      StringBuilder result = new StringBuilder();
      String[] args = this.currentMessage.getArguments();
      if (args != null) {
         for(int i = 0; i < args.length; ++i) {
            result.append(args[i]);
            result.append(" arg");
            result.append(i);
            if (i != args.length - 1) {
               result.append(", ");
            }
         }
      }

      return result.toString();
   }

   public String textExceptions() {
      return "";
   }

   public String textArgumentClasses() {
      StringBuilder result = new StringBuilder();
      String[] args = this.currentMessage.getArguments();
      if (args != null) {
         for(int i = 0; i < args.length; ++i) {
            String currArg = args[i];
            if (currArg.equals("int")) {
               result.append("Integer.valueOf(arg");
               result.append(i);
               result.append(")");
            } else if (currArg.equals("double")) {
               result.append("Double.valueOf(arg");
               result.append(i);
               result.append(")");
            } else if (currArg.equals("float")) {
               result.append("Float.valueOf(arg");
               result.append(i);
               result.append(")");
            } else if (currArg.equals("long")) {
               result.append("Long.valueOf(arg");
               result.append(i);
               result.append(")");
            } else if (currArg.equals("short")) {
               result.append("Short.valueOf(arg");
               result.append(i);
               result.append(")");
            } else if (currArg.equals("byte")) {
               result.append("Byte.valueOf(arg");
               result.append(i);
               result.append(")");
            } else if (currArg.equals("char")) {
               result.append("Character.valueOf(arg");
               result.append(i);
               result.append(")");
            } else if (currArg.equals("boolean")) {
               result.append("Boolean.valueOf(arg");
               result.append(i);
               result.append(")");
            } else {
               result.append("arg");
               result.append(i);
            }

            if (i != args.length - 1) {
               result.append(", ");
            }
         }
      }

      return result.toString();
   }

   public String textMessageId() {
      return this.currentMessage.getMessageId();
   }

   public String isExtendedFormatEnabled() {
      return "" + !this.server;
   }

   public String localizerClass() {
      StringBuilder result = new StringBuilder("LogLocalizer");
      String cName = this.currentOutput.getClassName();
      int i = cName.lastIndexOf("Logger");
      int gfIndex = cName.lastIndexOf("GlassfishLogger");
      if (gfIndex > 0) {
         i = gfIndex;
      }

      result.insert(0, cName.substring(0, i));
      result.insert(0, this.currentOutput.getMessageCatalog().getL10nPackage() + ".");
      if (this.debug) {
         System.err.println(result.toString());
      }

      return result.toString();
   }

   public String packageDeclaration() {
      if (this.currentOutput.getPackage() == null) {
         return "";
      } else {
         StringBuilder result = new StringBuilder("package ");
         result.append(this.currentOutput.getPackage());
         result.append(";");
         return result.toString();
      }
   }

   public String messageCatalogSubsystem() {
      return this.currentOutput.getMessageCatalog().getSubsystem();
   }

   public String logMessages() throws CodeGenerationException {
      StringBuilder result = new StringBuilder();
      Iterator it = this.currentOutput.getMessageCatalog().getLogMessages().iterator();

      while(true) {
         do {
            if (!it.hasNext()) {
               return result.toString();
            }

            this.currentLogMessage = (LogMessage)it.next();
         } while(this.currentLogMessage.isRetired());

         if (this.currentLogMessage.getMethodType().equals("logger")) {
            result.append(this.parse(this.getProductionRule("logMessage")));
         } else {
            result.append(this.parse(this.getProductionRule("getMessage")));
         }

         if (this.currentLogMessage.getMessageResetPeriod() > 0L) {
            result.append(this.parse(this.getProductionRule("resetLogMessage")));
         }

         if (this.currentOutput.getMessageCatalog().getLoggables() || this.currentLogMessage.getLoggable()) {
            result.append(this.parse(this.getProductionRule("logMessageLoggable")));
         }

         result.append("\n");
      }
   }

   public String logMessageInfo() throws CodeGenerationException {
      return this.parse(this.getProductionRule("logMessageInfo"));
   }

   public String logMessageDescription() {
      return this.currentLogMessage.getMessageBody().getCdata();
   }

   public String exclude() {
      return "@exclude";
   }

   public String logMethodName() {
      return this.currentLogMessage.getMethodName();
   }

   public String loggableMethodName() {
      return this.currentLogMessage.getMethodName() + "Loggable";
   }

   public String logLoggerSpi() {
      StringBuilder result = new StringBuilder();
      String[] args = this.currentLogMessage.getArguments();
      if (args != null && args.length > 0) {
         result.append("LoggerSpi logger,");
      } else {
         result.append("LoggerSpi logger");
      }

      return result.toString();
   }

   public String logArguments() {
      StringBuilder result = new StringBuilder();
      String[] args = this.currentLogMessage.getArguments();
      if (args != null) {
         for(int i = 0; i < args.length; ++i) {
            result.append(args[i]);
            result.append(" arg");
            result.append(i);
            if (i != args.length - 1) {
               result.append(", ");
            }
         }
      }

      return result.toString();
   }

   public String logThrowableArguments() {
      StringBuilder result = new StringBuilder();
      String[] args = this.currentLogMessage.getArguments();
      if (args != null) {
         result.append(this.logArguments());
         result.append(",");
      }

      result.append("Throwable th");
      return result.toString();
   }

   public String logExceptions() {
      return "";
   }

   public String logMessageId() {
      return this.currentLogMessage.getMessageId();
   }

   public String logMessageSeverity() {
      return this.currentLogMessage.getSeverity();
   }

   public int logMessageSeverityValue() {
      String sevText = this.currentLogMessage.getSeverity();
      return Severities.severityStringToNum(sevText);
   }

   public String logMessageLevelName() {
      String sevText = this.currentLogMessage.getSeverity();
      String levelName = (String)GF_SEV_LEVEL_NAME_MAP.get(sevText);

      assert levelName != null;

      return levelName;
   }

   public String logMessageLevel() {
      String sevText = this.currentLogMessage.getSeverity();
      String level = (String)GF_SEV_LEVEL_MAP.get(sevText);

      assert level != null;

      return level;
   }

   public String logMessageResetPeriod() {
      return String.valueOf(this.currentLogMessage.getMessageResetPeriod());
   }

   public String logMessageBody() {
      return this.currentLogMessage.getMessageBody().getCdata();
   }

   public String logMessageBodyString() {
      return this.removeSpecialChars(this.logMessageBody());
   }

   public String logMessageDetail() {
      return this.currentLogMessage.getMessageDetail().getCdata();
   }

   public String logMessageDetailString() {
      return this.removeSpecialChars(this.logMessageDetail());
   }

   public String logMessageCause() {
      return this.currentLogMessage.getCause().getCdata();
   }

   public String logMessageCauseString() {
      return this.removeSpecialChars(this.logMessageCause());
   }

   public String logMessageAction() {
      return this.currentLogMessage.getAction().getCdata();
   }

   public String logMessageActionString() {
      return this.removeSpecialChars(this.logMessageAction());
   }

   private String removeSpecialChars(String input) {
      String temp = input.replaceAll("\\\\\"", "");
      return temp.replaceAll("\"", "");
   }

   public String logArgumentClasses() {
      StringBuilder result = new StringBuilder();
      String[] args = this.currentLogMessage.getArguments();
      if (args != null) {
         for(int i = 0; i < args.length; ++i) {
            String currArg = args[i];
            if (currArg.equals("int")) {
               result.append("Integer.valueOf(arg");
               result.append(i);
               result.append(")");
            } else if (currArg.equals("double")) {
               result.append("Double.valueOf(arg");
               result.append(i);
               result.append(")");
            } else if (currArg.equals("float")) {
               result.append("Float.valueOf(arg");
               result.append(i);
               result.append(")");
            } else if (currArg.equals("long")) {
               result.append("Long.valueOf(arg");
               result.append(i);
               result.append(")");
            } else if (currArg.equals("short")) {
               result.append("Short.valueOf(arg");
               result.append(i);
               result.append(")");
            } else if (currArg.equals("byte")) {
               result.append("Byte.valueOf(arg");
               result.append(i);
               result.append(")");
            } else if (currArg.equals("char")) {
               result.append("Character.valueOf(arg");
               result.append(i);
               result.append(")");
            } else if (currArg.equals("boolean")) {
               result.append("Boolean.valueOf(arg");
               result.append(i);
               result.append(")");
            } else {
               result.append("arg");
               result.append(i);
            }

            if (i != args.length - 1) {
               result.append(", ");
            }
         }
      }

      return result.toString();
   }

   public String logSetLogRecordThrown() {
      String[] args = this.currentLogMessage.getArguments();
      if (args != null && args.length > 0) {
         String paramClass = args[args.length - 1];
         int index = paramClass.indexOf(46);
         if (index < 0) {
            paramClass = "java.lang." + paramClass;
         }

         try {
            if (Throwable.class.isAssignableFrom(Class.forName(paramClass))) {
               StringBuilder buf = new StringBuilder();
               buf.append("rec.setThrown(arg");
               buf.append(args.length - 1);
               buf.append(");");
               String snippet = buf.toString();
               return snippet;
            }
         } catch (ClassNotFoundException var6) {
            return "";
         }
      }

      return "";
   }

   public String catalogProperties() {
      StringBuilder result = new StringBuilder();
      MessageCatalog currCatalog = this.currentOutput.getMessageCatalog();
      result.append("version=" + currCatalog.getVersion() + "\n");
      result.append("L10nPackage=" + currCatalog.getL10nPackage() + "\n");
      result.append("I18nPackage=" + currCatalog.getI18nPackage() + "\n");
      result.append("subsystem=" + currCatalog.getSubsystem() + "\n");
      if (currCatalog.getPrefix() != null) {
         result.append("prefix=" + currCatalog.getPrefix() + "\n");
      }

      return result.toString();
   }

   public String logMessageProperties() {
      StringBuilder result = new StringBuilder();
      Iterator it = this.currentOutput.getMessageCatalog().getLogMessages().iterator();

      while(it.hasNext()) {
         this.currentLogMessage = (LogMessage)it.next();
         if (!this.currentLogMessage.isRetired()) {
            String id = this.currentLogMessage.getMessageId();
            result.append(id + "=" + this.currentLogMessage.getMessageBody().getCdata() + "\n");
         }
      }

      return result.toString();
   }

   public boolean logStackTrace() {
      return this.currentLogMessage.getStackTrace();
   }

   public String logDiagnosticVolume() {
      return this.currentLogMessage.getDiagnosticVolume();
   }

   public boolean logExcludePartition() {
      return true;
   }

   public String logMessageDetailProperties() {
      StringBuilder result = new StringBuilder();
      result.append("prefix=" + this.currentOutput.getMessageCatalog().getPrefix() + "\n");
      Iterator it = this.currentOutput.getMessageCatalog().getLogMessages().iterator();

      while(it.hasNext()) {
         this.currentLogMessage = (LogMessage)it.next();
         if (!this.currentLogMessage.isRetired()) {
            String id = this.currentLogMessage.getMessageId();
            String sevText = this.currentLogMessage.getSeverity();
            int sev = Severities.severityStringToNum(sevText);
            if (sev == 0) {
               sev = 8;
            }

            if (sev != 8) {
               result.append("severity" + id + "=" + Integer.toString(sev) + "\n");
            }

            if (!this.currentLogMessage.getStackTrace()) {
               result.append("stack" + id + "=" + this.currentLogMessage.getStackTrace() + "\n");
            }

            if (this.currentLogMessage.getDiagnosticVolume() != null && !this.currentLogMessage.getDiagnosticVolume().equalsIgnoreCase("Off")) {
               result.append("diagnosticvolume" + id + "=" + this.currentLogMessage.getDiagnosticVolume() + "\n");
            }

            result.append("messagedetail" + id + "=" + this.currentLogMessage.getMessageDetail().getCdata() + "\n");
            result.append("cause" + id + "=" + this.currentLogMessage.getCause().getCdata() + "\n");
            result.append("action" + id + "=" + this.currentLogMessage.getAction().getCdata() + "\n");
         }
      }

      return result.toString();
   }

   public String textMessageProperties() {
      StringBuilder result = new StringBuilder();
      Iterator it = this.currentOutput.getMessageCatalog().getMessages().iterator();

      while(it.hasNext()) {
         this.currentMessage = (Message)it.next();
         if (!this.currentMessage.isRetired()) {
            String id = this.currentMessage.getMessageId();
            result.append(id + "=" + this.currentMessage.getMessageBody().getCdata() + "\n");
         }
      }

      return result.toString();
   }

   static class XMLFilter implements FileFilter {
      public boolean accept(File name) {
         return name.getName().endsWith(".xml");
      }
   }

   public static class Output extends CodeGenerator.Output {
      private static final String JAVA_FILE_EXTN = ".java";
      private static final String XML_FILE_EXTN = ".xml";
      private static final String GLASSFISH = "Glassfish";
      private static final String CSS = "CSS";
      static String generated = null;
      static MessageCatalog currCatalog = null;
      static String currClassName = null;
      static String currSrcName = null;
      MessageCatalog catalog = null;
      String className = null;
      String srcName = null;
      boolean localizer = false;
      public String xmlSrcFile;

      public Output(String inputFile, String outName, MessageCatalog msgcat) {
         super(makeOutputFile(inputFile, outName, msgcat), outName, findPackage(msgcat));
         this.xmlSrcFile = inputFile;
         this.setCatalog();
         this.setClassName();
         this.setSrcName();
         this.localizer = this.className.endsWith("Localizer");
      }

      public MessageCatalog getMessageCatalog() {
         return this.catalog;
      }

      public void setCatalog() {
         this.catalog = currCatalog;
      }

      public void setClassName() {
         this.className = currClassName;
      }

      public void setSrcName() {
         this.srcName = currSrcName;
      }

      public String getClassName() {
         return this.className;
      }

      public String getSrcName() {
         return this.srcName;
      }

      public boolean isLocalizer() {
         return this.localizer;
      }

      private static final String findPackage(MessageCatalog msgcat) {
         currCatalog = msgcat;
         return currClassName.endsWith("Logger") ? currCatalog.getPackage() : currCatalog.getL10nPackage();
      }

      private static final String makeOutputFile(String inputFile, String outName, MessageCatalog msgcat) {
         File f = new File(inputFile);
         String inFilename = f.getName();
         inputFile = inFilename.substring(0, inFilename.length() - ".xml".length());
         String toutName = outName.substring(0, outName.length() - ".j".length());
         if (toutName.startsWith("Glassfish")) {
            toutName = toutName.substring("Glassfish".length());
         } else if (toutName.startsWith("CSS")) {
            toutName = toutName.substring("CSS".length());
         }

         currClassName = inputFile + toutName;
         if (outName.endsWith(".j")) {
            currSrcName = currClassName + ".java";
            if (generated != null) {
               String pkg = findPackage(msgcat);
               File pkgdir = new File(generated, pkg.replace('.', File.separatorChar));
               File srcFile = new File(pkgdir, currSrcName);
               currSrcName = srcFile.getAbsolutePath();
            }
         } else {
            currSrcName = currClassName + ".properties";
         }

         return currSrcName;
      }
   }
}
