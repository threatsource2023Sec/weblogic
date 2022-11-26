package com.sun.faces.config;

import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.Util;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;

public class WebConfiguration {
   private static final Logger LOGGER;
   private static final Pattern ALLOWABLE_BOOLEANS;
   private static final String WEB_CONFIG_KEY = "com.sun.faces.config.WebConfiguration";
   private Level loggingLevel;
   private Map booleanContextParameters;
   private Map contextParameters;
   private Map envEntries;
   private List setParams;
   private ServletContext servletContext;

   private WebConfiguration(ServletContext servletContext) {
      this.loggingLevel = Level.FINE;
      this.booleanContextParameters = new EnumMap(BooleanWebContextInitParameter.class);
      this.contextParameters = new EnumMap(WebContextInitParameter.class);
      this.envEntries = new EnumMap(WebEnvironmentEntry.class);
      this.setParams = new ArrayList();
      this.servletContext = servletContext;
      String contextName = this.getServletContextName();
      this.initSetList(servletContext);
      this.processBooleanParameters(servletContext, contextName);
      this.processInitParameters(servletContext, contextName);
      if (this.canProcessJndiEntries()) {
         this.processJndiEntries(contextName);
      }

   }

   public static WebConfiguration getInstance() {
      FacesContext facesContext = FacesContext.getCurrentInstance();
      return getInstance(facesContext.getExternalContext());
   }

   public static WebConfiguration getInstance(ExternalContext extContext) {
      WebConfiguration config = (WebConfiguration)extContext.getApplicationMap().get("com.sun.faces.config.WebConfiguration");
      return config == null ? getInstance((ServletContext)extContext.getContext()) : config;
   }

   public static WebConfiguration getInstance(ServletContext servletContext) {
      WebConfiguration webConfig = (WebConfiguration)servletContext.getAttribute("com.sun.faces.config.WebConfiguration");
      if (webConfig == null) {
         webConfig = new WebConfiguration(servletContext);
         servletContext.setAttribute("com.sun.faces.config.WebConfiguration", webConfig);
      }

      return webConfig;
   }

   public ServletContext getServletContext() {
      return this.servletContext;
   }

   public boolean isOptionEnabled(BooleanWebContextInitParameter param) {
      return this.booleanContextParameters.get(param) != null ? (Boolean)this.booleanContextParameters.get(param) : param.getDefaultValue();
   }

   public String getOptionValue(WebContextInitParameter param) {
      return (String)this.contextParameters.get(param);
   }

   public String getEnvironmentEntry(WebEnvironmentEntry entry) {
      return (String)this.envEntries.get(entry);
   }

   public boolean isSet(WebContextInitParameter param) {
      return this.isSet(param.getQualifiedName());
   }

   public boolean isSet(BooleanWebContextInitParameter param) {
      return this.isSet(param.getQualifiedName());
   }

   public String getServletContextName() {
      return this.servletContext.getMajorVersion() == 2 && this.servletContext.getMinorVersion() <= 4 ? this.servletContext.getServletContextName() : this.servletContext.getContextPath();
   }

   public void overrideContextInitParameter(BooleanWebContextInitParameter param, boolean value) {
      this.booleanContextParameters.put(param, value);
   }

   public void overrideContextInitParameter(WebContextInitParameter param, String value) {
      this.contextParameters.put(param, value);
   }

   public void overrideEnvEntry(WebEnvironmentEntry entry) {
   }

   static void clear(ServletContext servletContext) {
      servletContext.removeAttribute("com.sun.faces.config.WebConfiguration");
   }

   private boolean isValueValid(BooleanWebContextInitParameter param, String value) {
      if (!ALLOWABLE_BOOLEANS.matcher(value).matches()) {
         if (LOGGER.isLoggable(Level.WARNING)) {
            LOGGER.log(Level.WARNING, "jsf.config.webconfig.boolconfig.invalidvalue", new Object[]{value, param.getQualifiedName(), "true|false"});
         }

         return false;
      } else {
         return true;
      }
   }

   private void processBooleanParameters(ServletContext servletContext, String contextName) {
      BooleanWebContextInitParameter[] arr$ = WebConfiguration.BooleanWebContextInitParameter.values();
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         BooleanWebContextInitParameter param = arr$[i$];
         String strValue = servletContext.getInitParameter(param.getQualifiedName());
         boolean value;
         if (strValue != null && strValue.length() > 0 && param.isDeprecated()) {
            BooleanWebContextInitParameter alternate = param.getAlternate();
            if (LOGGER.isLoggable(Level.WARNING)) {
               if (alternate != null) {
                  LOGGER.log(Level.WARNING, "jsf.config.webconfig.param.deprecated", new Object[]{contextName, param.getQualifiedName(), alternate.getQualifiedName()});
               } else {
                  LOGGER.log(Level.WARNING, "jsf.config.webconfig.param.deprecated.no_replacement", new Object[]{contextName, param.getQualifiedName()});
               }
            }

            if (alternate != null) {
               if (this.isValueValid(param, strValue)) {
                  value = Boolean.valueOf(strValue);
               } else {
                  value = param.getDefaultValue();
               }

               if (LOGGER.isLoggable(Level.INFO) && alternate != null) {
                  LOGGER.log(Level.INFO, value ? "jsf.config.webconfig.configinfo.reset.enabled" : "jsf.config.webconfig.configinfo.reset.disabled", new Object[]{contextName, alternate.getQualifiedName()});
               }

               this.booleanContextParameters.put(alternate, value);
            }
         } else if (!param.isDeprecated()) {
            if (strValue == null) {
               value = param.getDefaultValue();
            } else if (this.isValueValid(param, strValue)) {
               value = Boolean.valueOf(strValue);
            } else {
               value = param.getDefaultValue();
            }

            if (WebConfiguration.BooleanWebContextInitParameter.DisplayConfiguration.equals(param) && value) {
               this.loggingLevel = Level.INFO;
            }

            if (LOGGER.isLoggable(this.loggingLevel)) {
               LOGGER.log(this.loggingLevel, value ? "jsf.config.webconfig.boolconfiginfo.enabled" : "jsf.config.webconfig.boolconfiginfo.disabled", new Object[]{contextName, param.getQualifiedName()});
            }

            this.booleanContextParameters.put(param, value);
         }
      }

   }

   private void initSetList(ServletContext servletContext) {
      Enumeration e = servletContext.getInitParameterNames();

      while(e.hasMoreElements()) {
         String name = e.nextElement().toString();
         if (name.startsWith("com.sun.faces")) {
            this.setParams.add(name);
         }
      }

   }

   private boolean isSet(String name) {
      return this.setParams.contains(name);
   }

   private void processInitParameters(ServletContext servletContext, String contextName) {
      WebContextInitParameter[] arr$ = WebConfiguration.WebContextInitParameter.values();
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         WebContextInitParameter param = arr$[i$];
         String value = servletContext.getInitParameter(param.getQualifiedName());
         if (value != null && value.length() > 0 && param.isDeprecated()) {
            WebContextInitParameter alternate = param.getAlternate();
            if (LOGGER.isLoggable(Level.WARNING)) {
               if (alternate != null) {
                  LOGGER.log(Level.WARNING, "jsf.config.webconfig.param.deprecated", new Object[]{contextName, param.getQualifiedName(), alternate.getQualifiedName()});
               } else {
                  LOGGER.log(Level.WARNING, "jsf.config.webconfig.param.deprecated.no_replacement", new Object[]{contextName, param.getQualifiedName()});
               }
            }

            if (alternate != null) {
               if (LOGGER.isLoggable(Level.INFO)) {
                  LOGGER.log(Level.INFO, "jsf.config.webconfig.configinfo.reset", new Object[]{contextName, alternate.getQualifiedName(), value});
               }

               this.contextParameters.put(alternate, value);
            }
         } else {
            if ((value == null || value.length() == 0) && !param.isDeprecated()) {
               value = param.getDefaultValue();
            }

            if (value != null && value.length() != 0) {
               if (value.length() > 0) {
                  if (LOGGER.isLoggable(this.loggingLevel)) {
                     LOGGER.log(this.loggingLevel, "jsf.config.webconfig.configinfo", new Object[]{contextName, param.getQualifiedName(), value});
                  }

                  this.contextParameters.put(param, value);
               } else if (LOGGER.isLoggable(this.loggingLevel)) {
                  LOGGER.log(this.loggingLevel, "jsf.config.webconfig.option.notconfigured", new Object[]{contextName, param.getQualifiedName()});
               }
            }
         }
      }

   }

   private boolean canProcessJndiEntries() {
      try {
         Util.getCurrentLoader(this).loadClass("javax.naming.InitialContext");
         return true;
      } catch (Exception var2) {
         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.fine("javax.naming is unavailable.  JNDI entries related to Mojarra configuration will not be processed.");
         }

         return false;
      }
   }

   private void processJndiEntries(String contextName) {
      InitialContext initialContext = null;

      try {
         initialContext = new InitialContext();
      } catch (NamingException var10) {
      }

      if (initialContext != null) {
         WebEnvironmentEntry[] arr$ = WebConfiguration.WebEnvironmentEntry.values();
         int len$ = arr$.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            WebEnvironmentEntry entry = arr$[i$];
            String entryName = entry.getQualifiedName();

            try {
               String value = (String)initialContext.lookup(entryName);
               if (value != null) {
                  if (LOGGER.isLoggable(Level.INFO)) {
                     if (!entry.equals(WebConfiguration.WebEnvironmentEntry.ClientStateSavingPassword)) {
                        if (LOGGER.isLoggable(this.loggingLevel)) {
                           LOGGER.log(this.loggingLevel, "jsf.config.webconfig.enventryinfo", new Object[]{contextName, entryName, value});
                        }
                     } else if (LOGGER.isLoggable(this.loggingLevel)) {
                        LOGGER.log(this.loggingLevel, "jsf.config.webconfig.enventry.clientencrypt", contextName);
                     }
                  }

                  this.envEntries.put(entry, value);
               }
            } catch (NamingException var9) {
            }
         }
      }

   }

   static {
      LOGGER = FacesLogger.CONFIG.getLogger();
      ALLOWABLE_BOOLEANS = Pattern.compile("true|false");
   }

   public static enum DisableUnicodeEscaping {
      True("true"),
      False("false"),
      Auto("auto");

      private final String value;

      private DisableUnicodeEscaping(String value) {
         this.value = value;
      }

      public static DisableUnicodeEscaping getByValue(String value) {
         DisableUnicodeEscaping[] arr$ = values();
         int len$ = arr$.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            DisableUnicodeEscaping disableUnicodeEscaping = arr$[i$];
            if (disableUnicodeEscaping.value.equals(value)) {
               return disableUnicodeEscaping;
            }
         }

         return null;
      }
   }

   public static enum WebEnvironmentEntry {
      ClientStateSavingPassword("ClientStateSavingPassword");

      private static final String JNDI_PREFIX = "java:comp/env/";
      private String qualifiedName;

      public String getQualifiedName() {
         return this.qualifiedName;
      }

      private WebEnvironmentEntry(String qualifiedName) {
         this.qualifiedName = "java:comp/env/com.sun.faces." + qualifiedName;
      }
   }

   public static enum BooleanWebContextInitParameter {
      DisplayConfiguration("com.sun.faces.displayConfiguration", false),
      ValidateFacesConfigFiles("com.sun.faces.validateXml", false),
      VerifyFacesConfigObjects("com.sun.faces.verifyObjects", false),
      ForceLoadFacesConfigFiles("com.sun.faces.forceLoadConfiguration", false),
      DisableArtifactVersioning("com.sun.faces.disableVersionTracking", false, true, (BooleanWebContextInitParameter)null),
      EnableHtmlTagLibraryValidator("com.sun.faces.enableHtmlTagLibValidator", false),
      PreferXHTMLContentType("com.sun.faces.preferXHTML", false),
      PreferXHTMLContextTypeDeprecated("com.sun.faces.PreferXHTML", false, true, PreferXHTMLContentType),
      CompressViewState("com.sun.faces.compressViewState", true),
      CompressViewStateDeprecated("com.sun.faces.COMPRESS_STATE", true, true, CompressViewState),
      CompressJavaScript("com.sun.faces.compressJavaScript", true),
      ExternalizeJavaScript("com.sun.faces.externalizeJavaScript", false),
      SendPoweredByHeader("com.sun.faces.sendPoweredByHeader", true),
      EnableJSStyleHiding("com.sun.faces.enableJSStyleHiding", false),
      EnableScriptInAttributeValue("com.sun.faces.enableScriptsInAttributeValues", true),
      WriteStateAtFormEnd("com.sun.faces.writeStateAtFormEnd", true),
      EnableLazyBeanValidation("com.sun.faces.enableLazyBeanValidation", true),
      EnableLoadBundle11Compatibility("com.sun.faces.enabledLoadBundle11Compatibility", false),
      EnableRestoreView11Compatibility("com.sun.faces.enableRestoreView11Compatibility", false),
      SerializeServerState("com.sun.faces.serializeServerState", false),
      EnableViewStateIdRendering("com.sun.faces.enableViewStateIdRendering", true),
      RegisterConverterPropertyEditors("com.sun.faces.registerConverterPropertyEditors", false),
      DevelopmentMode("com.sun.faces.developmentMode", false),
      EnableThreading("com.sun.faces.enableThreading", true),
      EnableMultiThreadedStartup("com.sun.faces.enableMultiThreadedStartup", true, true, EnableThreading),
      AutoCompleteOffOnViewState("com.sun.faces.autoCompleteOffOnViewState", true),
      GenerateUniqueServerStateIds("com.sun.faces.generateUniqueServerStateIds", true);

      private BooleanWebContextInitParameter alternate;
      private String qualifiedName;
      private boolean defaultValue;
      private boolean deprecated;

      public boolean getDefaultValue() {
         return this.defaultValue;
      }

      public String getQualifiedName() {
         return this.qualifiedName;
      }

      private BooleanWebContextInitParameter(String qualifiedName, boolean defaultValue) {
         this(qualifiedName, defaultValue, false, (BooleanWebContextInitParameter)null);
      }

      private BooleanWebContextInitParameter(String qualifiedName, boolean defaultValue, boolean deprecated, BooleanWebContextInitParameter alternate) {
         this.qualifiedName = qualifiedName;
         this.defaultValue = defaultValue;
         this.deprecated = deprecated;
         this.alternate = alternate;
      }

      private BooleanWebContextInitParameter getAlternate() {
         return this.alternate;
      }

      private boolean isDeprecated() {
         return this.deprecated;
      }
   }

   public static enum WebContextInitParameter {
      ManagedBeanFactoryDecorator("com.sun.faces.managedBeanFactoryDecoratorClass", ""),
      StateSavingMethod("javax.faces.STATE_SAVING_METHOD", "server"),
      JspDefaultSuffix("javax.faces.DEFAULT_SUFFIX", ".jsp"),
      JavaxFacesConfigFiles("javax.faces.CONFIG_FILES", ""),
      AlternateLifecycleId("javax.faces.LIFECYCLE_ID", ""),
      NumberOfViews("com.sun.faces.numberOfViewsInSession", "15"),
      NumberOfViewsDeprecated("com.sun.faces.NUMBER_OF_VIEWS_IN_SESSION", "15", true, NumberOfViews),
      NumberOfLogicalViews("com.sun.faces.numberOfLogicalViews", "15"),
      NumberOfLogicalViewsDeprecated("com.sun.faces.NUMBER_OF_VIEWS_IN_LOGICAL_VIEW_IN_SESSION", "15", true, NumberOfLogicalViews),
      InjectionProviderClass("com.sun.faces.injectionProvider", ""),
      SerializationProviderClass("com.sun.faces.serializationProvider", ""),
      ResponseBufferSize("com.sun.faces.responseBufferSize", "1024"),
      ClientStateWriteBufferSize("com.sun.faces.clientStateWriteBufferSize", "8192"),
      ExpressionFactory("com.sun.faces.expressionFactory", "com.sun.el.ExpressionFactoryImpl"),
      ClientStateTimeout("com.sun.faces.clientStateTimeout", ""),
      DisableUnicodeEscaping("com.sun.faces.disableUnicodeEscaping", "false"),
      DuplicateJARPattern("com.sun.faces.duplicateJARPattern", "");

      private String defaultValue;
      private String qualifiedName;
      private WebContextInitParameter alternate;
      private boolean deprecated;

      public String getDefaultValue() {
         return this.defaultValue;
      }

      public String getQualifiedName() {
         return this.qualifiedName;
      }

      private WebContextInitParameter(String qualifiedName, String defaultValue) {
         this(qualifiedName, defaultValue, false, (WebContextInitParameter)null);
      }

      private WebContextInitParameter(String qualifiedName, String defaultValue, boolean deprecated, WebContextInitParameter alternate) {
         this.qualifiedName = qualifiedName;
         this.defaultValue = defaultValue;
         this.deprecated = deprecated;
         this.alternate = alternate;
      }

      private WebContextInitParameter getAlternate() {
         return this.alternate;
      }

      private boolean isDeprecated() {
         return this.deprecated;
      }
   }
}
