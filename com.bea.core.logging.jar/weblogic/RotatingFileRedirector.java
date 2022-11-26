package weblogic;

import com.bea.logging.LogFileConfigBean;
import com.bea.logging.RotatingFileOutputStream;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import weblogic.utils.Getopt2;
import weblogic.utils.PlatformConstants;

public class RotatingFileRedirector {
   private static final String HELP_OPTION = "help";
   private static final String VERBOSE_OPTION = "verbose";
   private static final String CONFIG_OPTION = "config";
   private static final String CONFIG_OVERRIDE_OPTION = "configOverride";
   private static final String ECHO_OPTION = "echo";
   private static final String DEBUG_OPTION = "debug";
   private static final String INPUT_OPTION = "input";
   private static final String TEST_OPTION = "test";
   private static final Logger LOGGER = Logger.getLogger(RotatingFileRedirector.class.getName());
   private static final String DEFAULT_OUT_FILE = "redirect.log";
   private static final int SUCCESS_CODE = 0;
   private static final int ERR_CODE = 1;

   public static void main(String[] args) {
      LOGGER.setLevel(Level.INFO);
      logMessage(Level.INFO, "Starting RotatingFileRedirector. Specify -help for help information.");
      Getopt2 options = new Getopt2();
      options.setUsageFooter("weblogic.RotatingFileRedirector is a stand alone utility for redirecting stdout and stderr streams to a rotating log file.");
      options.setFailOnUnrecognizedOpts(true);
      options.addFlag("help", "Prints help about supported options and flags");
      options.addFlag("verbose", "Prints additional output during execution");
      options.addFlag("echo", "Echoes input to stdout");
      options.markPrivate("echo");
      options.addFlag("debug", "Emit debug output");
      options.markPrivate("debug");
      options.addOption("input", "Input", "Input stream or file path, if not specified defaults to stdin");
      options.markPrivate("input");
      StringBuilder sb = new StringBuilder();
      sb.append("(Optional) Properties file which specifies the log rotation file parameters as key-value pairs. ").append("If not specified the rotation parameters are defaulted.");
      options.addOption("config", "Config properties file", sb.toString());
      options.addFlag("test", "used for internal testing, does not invoke System.exit()");
      options.markPrivate("test");
      options.addMultiOption("configOverride", "Command line override for config properties", "Override of a key-value config property pair.Multiple overrides can be specified, for example -configOverride rotationSize=5000 -configOverride rotatedFileCount=10");

      try {
         options.grok(args);
      } catch (IllegalArgumentException var6) {
         logThrowable("Invalid command line", var6);
         printUsageAndExit(options);
      }

      int code = false;

      int code;
      try {
         code = execute(options);
      } catch (Throwable var5) {
         logThrowable("Error:", var5);
         code = 1;
      }

      if (options.hasOption("test")) {
         if (code != 0) {
            throw new IllegalArgumentException("Test execution failed.");
         }
      } else {
         logMessage(Level.WARNING, "Exiting process with code " + code);
         System.exit(code);
      }
   }

   private static int execute(Getopt2 options) {
      if (options.containsOption("help")) {
         printUsageAndExit(options);
         return 0;
      } else {
         boolean verbose = options.containsOption("verbose");
         if (verbose) {
            LOGGER.setLevel(Level.FINE);
            Handler[] handlers = Logger.getLogger("").getHandlers();
            if (handlers != null) {
               Handler[] var3 = handlers;
               int var4 = handlers.length;

               for(int var5 = 0; var5 < var4; ++var5) {
                  Handler h = var3[var5];
                  h.setLevel(Level.FINE);
               }
            }
         }

         LogFileConfigBean logFileConfig = new LogFileConfigBean();
         String config = options.getOption("config");
         if (config != null && config.length() != 0) {
            Properties props = new Properties();

            try {
               FileInputStream fis = new FileInputStream(config);
               props.load(fis);
            } catch (IOException var28) {
               logThrowable("Error reading config file " + config, var28);
               return 1;
            }

            String[] configOverrides = options.getMultiOption("configOverride", new String[0]);
            if (configOverrides != null) {
               String[] var38 = configOverrides;
               int var7 = configOverrides.length;

               for(int var8 = 0; var8 < var7; ++var8) {
                  String override = var38[var8];
                  String[] tokens = override.split("=");
                  if (tokens.length == 2) {
                     props.put(tokens[0], tokens[1]);
                  } else {
                     logMessage(Level.WARNING, "Ignoring invalid config override " + override);
                  }
               }
            }

            try {
               BeanInfo beanInfo = Introspector.getBeanInfo(LogFileConfigBean.class);
               PropertyDescriptor[] propDescs = beanInfo.getPropertyDescriptors();
               validateConfigProperties(beanInfo, props);
               PropertyDescriptor[] var45 = propDescs;
               int var47 = propDescs.length;

               for(int var48 = 0; var48 < var47; ++var48) {
                  PropertyDescriptor pd = var45[var48];
                  String property = pd.getName();
                  String value = props.getProperty(property);
                  if (value != null) {
                     logMessage(Level.FINE, "Initializing property " + property + " of type " + pd.getPropertyType() + " with value=" + value);
                     Method setter = pd.getWriteMethod();

                     try {
                        setter.invoke(logFileConfig, convertValue(pd.getPropertyType(), value));
                     } catch (Exception var27) {
                        logThrowable("Error initializing property " + property + " with value=" + value, var27);
                     }
                  }
               }
            } catch (Exception var31) {
               logThrowable("Unexpected Error ", var31);
               return 1;
            }
         } else {
            logMessage(Level.INFO, "Config option not specified, using default configuration values.");
            logFileConfig.setBaseLogFileName("redirect.log");
         }

         if (options.hasOption("debug")) {
            dumpLogFileConfig(logFileConfig);
         }

         BufferedWriter writer = null;

         byte var41;
         try {
            String baseLogFile = logFileConfig.getBaseLogFileName();
            if (baseLogFile == null || baseLogFile.isEmpty()) {
               throw new IllegalArgumentException("Base log file name is not specified.");
            }

            RotatingFileOutputStream output = new RotatingFileOutputStream(logFileConfig);
            writer = new BufferedWriter(new OutputStreamWriter(output));
            logMessage(Level.INFO, "Redirecting input to rotating file " + logFileConfig.getBaseLogFileName());
            BufferedReader br;
            String input;
            if (options.hasOption("input")) {
               input = options.getOption("input");
               if (input == null || input.isEmpty()) {
                  throw new IllegalArgumentException("Input file name is not specified.");
               }

               br = new BufferedReader(new InputStreamReader(new FileInputStream(input)));
            } else {
               br = new BufferedReader(new InputStreamReader(System.in));
            }

            while((input = br.readLine()) != null) {
               if (options.hasOption("echo")) {
                  System.out.println(input);
               }

               writer.write(input);
               writer.write(PlatformConstants.EOL);
               writer.flush();
            }

            return 0;
         } catch (IOException var29) {
            logThrowable("Error writing to log.", var29);
            var41 = 1;
         } finally {
            if (writer != null) {
               try {
                  writer.flush();
                  writer.close();
               } catch (IOException var26) {
                  logThrowable("Error closing log.", var26);
               }
            }

         }

         return var41;
      }
   }

   private static void validateConfigProperties(BeanInfo beanInfo, Properties props) {
      PropertyDescriptor[] propDescs = beanInfo.getPropertyDescriptors();
      Set validProps = new HashSet();
      PropertyDescriptor[] var4 = propDescs;
      int var5 = propDescs.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         PropertyDescriptor pd = var4[var6];
         validProps.add(pd.getName());
      }

      if (props != null) {
         Iterator var8 = props.keySet().iterator();

         while(var8.hasNext()) {
            Object key = var8.next();
            if (!validProps.contains(key)) {
               logMessage(Level.WARNING, "Ignoring invalid config property " + key);
            }
         }
      }

   }

   private static void dumpLogFileConfig(LogFileConfigBean logFileConfig) {
      System.out.println("Dumping output file rotation config parameters.");

      try {
         BeanInfo beanInfo = Introspector.getBeanInfo(LogFileConfigBean.class);
         PropertyDescriptor[] propDescs = beanInfo.getPropertyDescriptors();
         PropertyDescriptor[] var3 = propDescs;
         int var4 = propDescs.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            PropertyDescriptor pd = var3[var5];
            String property = pd.getName();
            Method getter = pd.getReadMethod();

            try {
               Object value = getter.invoke(logFileConfig);
               System.out.println(property + "=" + value);
            } catch (Exception var10) {
               var10.printStackTrace();
            }
         }
      } catch (Exception var11) {
         var11.printStackTrace();
      }

   }

   private static Object convertValue(Class propertyType, String value) {
      if (propertyType.isPrimitive()) {
         if (propertyType == Boolean.TYPE) {
            return Boolean.parseBoolean(value);
         }

         if (propertyType == Integer.TYPE) {
            return Integer.parseInt(value);
         }

         if (propertyType == Long.TYPE) {
            return Long.parseLong(value);
         }
      }

      return value;
   }

   private static void printUsageAndExit(Getopt2 options) {
      options.usageAndExit(RotatingFileRedirector.class.getName());
   }

   private static void logMessage(Level level, String message) {
      LOGGER.log(level, message);
   }

   private static void logThrowable(String message, Throwable e) {
      LOGGER.log(Level.SEVERE, message, e);
   }
}
