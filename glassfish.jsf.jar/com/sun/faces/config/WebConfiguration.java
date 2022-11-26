package com.sun.faces.config;

import com.sun.faces.application.ApplicationAssociate;
import com.sun.faces.application.view.FaceletViewHandlingStrategy;
import com.sun.faces.facelets.util.Classpath;
import com.sun.faces.lifecycle.HttpMethodRestrictionsPhaseListener;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.Util;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.faces.FactoryFinder;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseListener;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;

public class WebConfiguration {
   private static final Logger LOGGER;
   private static final Pattern ALLOWABLE_BOOLEANS;
   private static final String WEB_CONFIG_KEY = "com.sun.faces.config.WebConfiguration";
   public static final String META_INF_CONTRACTS_DIR;
   private static final int META_INF_CONTRACTS_DIR_LEN;
   private static final String RESOURCE_CONTRACT_SUFFIX = "/javax.faces.contract.xml";
   private Level loggingLevel;
   private Map booleanContextParameters;
   private Map contextParameters;
   private Map facesConfigParameters;
   private Map envEntries;
   private Map cachedListParams;
   private Set setParams;
   private ServletContext servletContext;
   private ArrayList deferredLoggingActions;
   private FaceletsConfiguration faceletsConfig;
   private boolean hasFlows;

   private WebConfiguration(ServletContext servletContext) {
      this.loggingLevel = Level.FINE;
      this.booleanContextParameters = new EnumMap(BooleanWebContextInitParameter.class);
      this.contextParameters = new EnumMap(WebContextInitParameter.class);
      this.facesConfigParameters = new EnumMap(WebContextInitParameter.class);
      this.envEntries = new EnumMap(WebEnvironmentEntry.class);
      this.setParams = new HashSet();
      this.servletContext = servletContext;
      String contextName = this.getServletContextName();
      this.initSetList(servletContext);
      this.processBooleanParameters(servletContext, contextName);
      this.processInitParameters(servletContext, contextName);
      if (this.canProcessJndiEntries()) {
         this.processJndiEntries(contextName);
      }

      this.cachedListParams = new HashMap(3);
      this.getOptionValue(WebConfiguration.WebContextInitParameter.ResourceExcludes, " ");
      this.getOptionValue(WebConfiguration.WebContextInitParameter.DefaultSuffix, " ");
      this.getOptionValue(WebConfiguration.WebContextInitParameter.FaceletsViewMappings, ";");
      this.getOptionValue(WebConfiguration.WebContextInitParameter.FaceletsSuffix, " ");
   }

   public static WebConfiguration getInstance() {
      return getInstance(FacesContext.getCurrentInstance().getExternalContext());
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

   public static WebConfiguration getInstanceWithoutCreating(ServletContext servletContext) {
      return (WebConfiguration)servletContext.getAttribute("com.sun.faces.config.WebConfiguration");
   }

   public ServletContext getServletContext() {
      return this.servletContext;
   }

   public boolean isHasFlows() {
      return this.hasFlows;
   }

   public void setHasFlows(boolean hasFlows) {
      this.hasFlows = hasFlows;
   }

   public boolean isOptionEnabled(BooleanWebContextInitParameter param) {
      return this.booleanContextParameters.get(param) != null ? (Boolean)this.booleanContextParameters.get(param) : param.getDefaultValue();
   }

   public String getOptionValue(WebContextInitParameter param) {
      String result = (String)this.contextParameters.get(param);
      if (result == null) {
         WebContextInitParameter alternate = param.getAlternate();
         if (alternate != null) {
            result = (String)this.contextParameters.get(alternate);
         }
      }

      return result;
   }

   public void setOptionValue(WebContextInitParameter param, String value) {
      this.contextParameters.put(param, value);
   }

   public void setOptionEnabled(BooleanWebContextInitParameter param, boolean value) {
      this.booleanContextParameters.put(param, value);
   }

   public FaceletsConfiguration getFaceletsConfiguration() {
      if (this.faceletsConfig == null) {
         this.faceletsConfig = new FaceletsConfiguration(this);
      }

      return this.faceletsConfig;
   }

   public Map getFacesConfigOptionValue(WebContextInitParameter param, boolean create) {
      assert this.facesConfigParameters != null;

      Map result = (Map)this.facesConfigParameters.get(param);
      if (result == null) {
         if (create) {
            result = new ConcurrentHashMap(3);
            this.facesConfigParameters.put(param, result);
         } else {
            result = Collections.emptyMap();
         }
      }

      return (Map)result;
   }

   public Map getFacesConfigOptionValue(WebContextInitParameter param) {
      return this.getFacesConfigOptionValue(param, false);
   }

   public String[] getOptionValue(WebContextInitParameter param, String sep) {
      assert this.cachedListParams != null;

      String[] result;
      if ((result = (String[])this.cachedListParams.get(param)) == null) {
         String value = this.getOptionValue(param);
         if (value == null) {
            result = new String[0];
         } else {
            Map appMap = FacesContext.getCurrentInstance().getExternalContext().getApplicationMap();
            result = Util.split(appMap, value, sep);
         }

         this.cachedListParams.put(param, result);
      }

      return result;
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
      if (param != null) {
         boolean oldVal = (Boolean)this.booleanContextParameters.put(param, value);
         if (LOGGER.isLoggable(Level.FINE) && oldVal != value) {
            LOGGER.log(Level.FINE, "Overriding init parameter {0}.  Changing from {1} to {2}.", new Object[]{param.getQualifiedName(), oldVal, value});
         }

      }
   }

   public String[] getConfiguredExtensions() {
      String[] defaultSuffix = this.getOptionValue(WebConfiguration.WebContextInitParameter.DefaultSuffix, " ");
      String[] faceletsSuffix = this.getOptionValue(WebConfiguration.WebContextInitParameter.FaceletsSuffix, " ");
      List mergedList = new ArrayList(Arrays.asList(defaultSuffix));
      mergedList.addAll(Arrays.asList(faceletsSuffix));
      return (String[])mergedList.toArray(new String[0]);
   }

   public void overrideContextInitParameter(WebContextInitParameter param, String value) {
      if (param != null && value != null && value.length() != 0) {
         value = value.trim();
         String oldVal = (String)this.contextParameters.put(param, value);
         this.cachedListParams.remove(param);
         if (oldVal != null && LOGGER.isLoggable(Level.FINE) && !oldVal.equals(value)) {
            LOGGER.log(Level.FINE, "Overriding init parameter {0}.  Changing from {1} to {2}.", new Object[]{param.getQualifiedName(), oldVal, value});
         }

      }
   }

   public void doPostBringupActions() {
      if (this.deferredLoggingActions != null) {
         Iterator var1 = this.deferredLoggingActions.iterator();

         while(var1.hasNext()) {
            DeferredLoggingAction loggingAction = (DeferredLoggingAction)var1.next();
            loggingAction.log();
         }
      }

      boolean enabled = this.isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.EnableHttpMethodRestrictionPhaseListener);
      if (enabled) {
         LifecycleFactory factory = (LifecycleFactory)FactoryFinder.getFactory("javax.faces.lifecycle.LifecycleFactory");
         Iterator ids = factory.getLifecycleIds();
         PhaseListener listener = null;

         while(ids.hasNext()) {
            Lifecycle cur = factory.getLifecycle((String)ids.next());
            boolean foundExistingListenerInstance = false;
            PhaseListener[] var7 = cur.getPhaseListeners();
            int var8 = var7.length;

            for(int var9 = 0; var9 < var8; ++var9) {
               PhaseListener curListener = var7[var9];
               if (curListener instanceof HttpMethodRestrictionsPhaseListener) {
                  foundExistingListenerInstance = true;
                  break;
               }
            }

            if (!foundExistingListenerInstance) {
               if (null == listener) {
                  listener = new HttpMethodRestrictionsPhaseListener();
               }

               cur.addPhaseListener(listener);
            }
         }
      }

      this.discoverResourceLibraryContracts();
   }

   private void discoverResourceLibraryContracts() {
      FacesContext context = FacesContext.getCurrentInstance();
      ExternalContext extContex = context.getExternalContext();
      Set foundContracts = new HashSet();
      String contractsDirName = this.getOptionValue(WebConfiguration.WebContextInitParameter.WebAppContractsDirectory);

      assert null != contractsDirName;

      Set candidates = extContex.getResourcePaths(contractsDirName);
      if (null != candidates) {
         int contractsDirNameLen = contractsDirName.length();

         int end;
         String cur;
         for(Iterator var8 = candidates.iterator(); var8.hasNext(); foundContracts.add(cur.substring(contractsDirNameLen + 1, end))) {
            cur = (String)var8.next();
            end = cur.length();
            if (cur.endsWith("/")) {
               --end;
            }
         }
      }

      try {
         URL[] candidateURLs = Classpath.search(Util.getCurrentLoader(this), META_INF_CONTRACTS_DIR, "/javax.faces.contract.xml", Classpath.SearchAdvice.AllMatches);
         URL[] var18 = candidateURLs;
         int var19 = candidateURLs.length;

         for(int var22 = 0; var22 < var19; ++var22) {
            URL curURL = var18[var22];
            String cur = curURL.toExternalForm();
            int i = cur.indexOf(META_INF_CONTRACTS_DIR) + META_INF_CONTRACTS_DIR_LEN + 1;
            int j = cur.indexOf("/javax.faces.contract.xml");
            if (i < j) {
               foundContracts.add(cur.substring(i, j));
            }
         }
      } catch (IOException var15) {
         if (LOGGER.isLoggable(Level.FINEST)) {
            LOGGER.log(Level.FINEST, "Unable to scan " + META_INF_CONTRACTS_DIR, var15);
         }
      }

      if (!foundContracts.isEmpty()) {
         Map contractMappings = new HashMap();
         ApplicationAssociate associate = ApplicationAssociate.getCurrentInstance();
         Map contractsFromConfig = associate.getResourceLibraryContracts();
         ArrayList contractsToExpose;
         if (null != contractsFromConfig && !contractsFromConfig.isEmpty()) {
            Iterator var25 = contractsFromConfig.entrySet().iterator();

            label72:
            while(true) {
               while(true) {
                  if (!var25.hasNext()) {
                     break label72;
                  }

                  Map.Entry cur = (Map.Entry)var25.next();
                  List contractsFromMapping = (List)cur.getValue();
                  if (null != contractsFromMapping && !contractsFromMapping.isEmpty()) {
                     contractsToExpose = new ArrayList();
                     Iterator var27 = contractsFromMapping.iterator();

                     while(var27.hasNext()) {
                        String curContractFromMapping = (String)var27.next();
                        if (foundContracts.contains(curContractFromMapping)) {
                           contractsToExpose.add(curContractFromMapping);
                        } else if (LOGGER.isLoggable(Level.CONFIG)) {
                           LOGGER.log(Level.CONFIG, "resource library contract mapping for pattern {0} exposes contract {1}, but that contract is not available to the application.", new String[]{(String)cur.getKey(), curContractFromMapping});
                        }
                     }

                     if (!contractsToExpose.isEmpty()) {
                        contractMappings.put(cur.getKey(), contractsToExpose);
                     }
                  } else if (LOGGER.isLoggable(Level.CONFIG)) {
                     LOGGER.log(Level.CONFIG, "resource library contract mapping for pattern {0} has no contracts.", cur.getKey());
                  }
               }
            }
         } else {
            contractsToExpose = new ArrayList();
            contractsToExpose.addAll(foundContracts);
            contractMappings.put("*", contractsToExpose);
         }

         extContex.getApplicationMap().put(FaceletViewHandlingStrategy.RESOURCE_LIBRARY_CONTRACT_DATA_STRUCTURE_KEY, contractMappings);
      }
   }

   static void clear(ServletContext servletContext) {
      servletContext.removeAttribute("com.sun.faces.config.WebConfiguration");
   }

   private boolean isValueValid(BooleanWebContextInitParameter param, String value) {
      if (!ALLOWABLE_BOOLEANS.matcher(value).matches()) {
         if (LOGGER.isLoggable(Level.WARNING)) {
            LOGGER.log(Level.WARNING, "jsf.config.webconfig.boolconfig.invalidvalue", new Object[]{value, param.getQualifiedName(), "true|false", "true|false", param.getDefaultValue()});
         }

         return false;
      } else {
         return true;
      }
   }

   private void processBooleanParameters(ServletContext servletContext, String contextName) {
      BooleanWebContextInitParameter[] var3 = WebConfiguration.BooleanWebContextInitParameter.values();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         BooleanWebContextInitParameter param = var3[var5];
         String strValue = servletContext.getInitParameter(param.getQualifiedName());
         boolean value;
         if (strValue != null && strValue.length() > 0 && param.isDeprecated()) {
            BooleanWebContextInitParameter alternate = param.getAlternate();
            if (LOGGER.isLoggable(Level.WARNING)) {
               if (alternate != null) {
                  this.queueLoggingAction(new DeferredBooleanParameterLoggingAction(param, Level.WARNING, "jsf.config.webconfig.param.deprecated", new Object[]{contextName, param.getQualifiedName(), alternate.getQualifiedName()}));
               } else {
                  this.queueLoggingAction(new DeferredBooleanParameterLoggingAction(param, Level.WARNING, "jsf.config.webconfig.param.deprecated.no_replacement", new Object[]{contextName, param.getQualifiedName()}));
               }
            }

            if (alternate != null) {
               if (this.isValueValid(param, strValue)) {
                  value = Boolean.valueOf(strValue);
               } else {
                  value = param.getDefaultValue();
               }

               if (LOGGER.isLoggable(Level.INFO) && alternate != null) {
                  this.queueLoggingAction(new DeferredBooleanParameterLoggingAction(param, Level.INFO, value ? "jsf.config.webconfig.configinfo.reset.enabled" : "jsf.config.webconfig.configinfo.reset.disabled", new Object[]{contextName, alternate.getQualifiedName()}));
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

      while(true) {
         String name;
         do {
            if (!e.hasMoreElements()) {
               return;
            }

            name = e.nextElement().toString();
         } while(!name.startsWith("com.sun.faces") && !name.startsWith("javax.faces"));

         this.setParams.add(name);
      }
   }

   private boolean isSet(String name) {
      return this.setParams.contains(name);
   }

   private void processInitParameters(ServletContext servletContext, String contextName) {
      WebContextInitParameter[] var3 = WebConfiguration.WebContextInitParameter.values();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         WebContextInitParameter param = var3[var5];
         String value = servletContext.getInitParameter(param.getQualifiedName());
         if (value != null && value.length() > 0 && param.isDeprecated()) {
            WebContextInitParameter alternate = param.getAlternate();
            DeprecationLoggingStrategy strategy = param.getDeprecationLoggingStrategy();
            if ((strategy == null || strategy.shouldBeLogged(this)) && LOGGER.isLoggable(Level.WARNING)) {
               if (alternate != null) {
                  this.queueLoggingAction(new DeferredParameterLoggingAction(param, Level.WARNING, "jsf.config.webconfig.param.deprecated", new Object[]{contextName, param.getQualifiedName(), alternate.getQualifiedName()}));
               } else {
                  this.queueLoggingAction(new DeferredParameterLoggingAction(param, Level.WARNING, "jsf.config.webconfig.param.deprecated.no_replacement", new Object[]{contextName, param.getQualifiedName()}));
               }
            }

            if (alternate != null) {
               this.queueLoggingAction(new DeferredParameterLoggingAction(param, Level.INFO, "jsf.config.webconfig.configinfo.reset", new Object[]{contextName, alternate.getQualifiedName(), value}));
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

   private void processJndiEntries(String contextName) {
      Context initialContext = null;

      try {
         initialContext = new InitialContext();
      } catch (NoClassDefFoundError var11) {
         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, var11.toString(), var11);
         }
      } catch (NamingException var12) {
         if (LOGGER.isLoggable(Level.WARNING)) {
            LOGGER.log(Level.WARNING, var12.toString(), var12);
         }
      }

      if (initialContext != null) {
         WebEnvironmentEntry[] var3 = WebConfiguration.WebEnvironmentEntry.values();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            WebEnvironmentEntry entry = var3[var5];
            String entryName = entry.getQualifiedName();
            String value = null;

            try {
               value = (String)initialContext.lookup(entryName);
            } catch (NamingException var10) {
               if (LOGGER.isLoggable(Level.FINE)) {
                  LOGGER.fine(var10.toString());
               }
            }

            if (value != null) {
               if (LOGGER.isLoggable(Level.INFO) && LOGGER.isLoggable(this.loggingLevel)) {
                  LOGGER.log(this.loggingLevel, "jsf.config.webconfig.enventryinfo", new Object[]{contextName, entryName, value});
               }

               this.envEntries.put(entry, value);
            }
         }
      }

   }

   public boolean canProcessJndiEntries() {
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

   private void queueLoggingAction(DeferredLoggingAction loggingAction) {
      if (this.deferredLoggingActions == null) {
         this.deferredLoggingActions = new ArrayList();
      }

      this.deferredLoggingActions.add(loggingAction);
   }

   static {
      LOGGER = FacesLogger.CONFIG.getLogger();
      ALLOWABLE_BOOLEANS = Pattern.compile("true|false", 2);
      META_INF_CONTRACTS_DIR = "META-INF" + WebConfiguration.WebContextInitParameter.WebAppContractsDirectory.getDefaultValue();
      META_INF_CONTRACTS_DIR_LEN = META_INF_CONTRACTS_DIR.length();
   }

   private class DeferredBooleanParameterLoggingAction implements DeferredLoggingAction {
      private BooleanWebContextInitParameter parameter;
      private Level loggingLevel;
      private String logKey;
      private Object[] params;

      DeferredBooleanParameterLoggingAction(BooleanWebContextInitParameter parameter, Level loggingLevel, String logKey, Object[] params) {
         this.parameter = parameter;
         this.loggingLevel = loggingLevel;
         this.logKey = logKey;
         this.params = params;
      }

      public void log() {
         if (WebConfiguration.LOGGER.isLoggable(this.loggingLevel)) {
            DeprecationLoggingStrategy strategy = this.parameter.getDeprecationLoggingStrategy();
            if (strategy != null && strategy.shouldBeLogged(WebConfiguration.this)) {
               WebConfiguration.LOGGER.log(this.loggingLevel, this.logKey, this.params);
            }
         }

      }
   }

   private class DeferredParameterLoggingAction implements DeferredLoggingAction {
      private WebContextInitParameter parameter;
      private Level loggingLevel;
      private String logKey;
      private Object[] params;

      DeferredParameterLoggingAction(WebContextInitParameter parameter, Level loggingLevel, String logKey, Object[] params) {
         this.parameter = parameter;
         this.loggingLevel = loggingLevel;
         this.logKey = logKey;
         this.params = params;
      }

      public void log() {
         if (WebConfiguration.LOGGER.isLoggable(this.loggingLevel)) {
            DeprecationLoggingStrategy strategy = this.parameter.getDeprecationLoggingStrategy();
            if (strategy != null && strategy.shouldBeLogged(WebConfiguration.this)) {
               WebConfiguration.LOGGER.log(this.loggingLevel, this.logKey, this.params);
            }
         }

      }
   }

   private interface DeferredLoggingAction {
      void log();
   }

   private static class FaceletsConfigParamLoggingStrategy implements DeprecationLoggingStrategy {
      private FaceletsConfigParamLoggingStrategy() {
      }

      public boolean shouldBeLogged(WebConfiguration configuration) {
         return !configuration.isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.DisableFaceletJSFViewHandler) && !configuration.isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.DisableFaceletJSFViewHandlerDeprecated);
      }

      // $FF: synthetic method
      FaceletsConfigParamLoggingStrategy(Object x0) {
         this();
      }
   }

   private interface DeprecationLoggingStrategy {
      boolean shouldBeLogged(WebConfiguration var1);
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
         DisableUnicodeEscaping[] var1 = values();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            DisableUnicodeEscaping disableUnicodeEscaping = var1[var3];
            if (disableUnicodeEscaping.value.equals(value)) {
               return disableUnicodeEscaping;
            }
         }

         return null;
      }
   }

   public static enum WebEnvironmentEntry {
      ProjectStage("java:comp/env/jsf/ProjectStage");

      private static final String JNDI_PREFIX = "java:comp/env/";
      private String qualifiedName;

      public String getQualifiedName() {
         return this.qualifiedName;
      }

      private WebEnvironmentEntry(String qualifiedName) {
         if (qualifiedName.startsWith("java:comp/env/")) {
            this.qualifiedName = qualifiedName;
         } else {
            this.qualifiedName = "java:comp/env/" + qualifiedName;
         }

      }
   }

   public static enum BooleanWebContextInitParameter {
      AlwaysPerformValidationWhenRequiredTrue("javax.faces.ALWAYS_PERFORM_VALIDATION_WHEN_REQUIRED_IS_TRUE", false),
      DisplayConfiguration("com.sun.faces.displayConfiguration", false),
      ValidateFacesConfigFiles("com.sun.faces.validateXml", false),
      VerifyFacesConfigObjects("com.sun.faces.verifyObjects", false),
      ForceLoadFacesConfigFiles("com.sun.faces.forceLoadConfiguration", false),
      DisableArtifactVersioning("com.sun.faces.disableVersionTracking", false, true, (BooleanWebContextInitParameter)null),
      DisableClientStateEncryption("com.sun.faces.disableClientStateEncryption", false),
      DisableFacesServletAutomaticMapping("javax.faces.DISABLE_FACESSERVLET_TO_XHTML", false),
      EnableClientStateDebugging("com.sun.faces.enableClientStateDebugging", false),
      EnableHtmlTagLibraryValidator("com.sun.faces.enableHtmlTagLibValidator", false),
      EnableCoreTagLibraryValidator("com.sun.faces.enableCoreTagLibValidator", false),
      PreferXHTMLContentType("com.sun.faces.preferXHTML", false),
      PreferXHTMLContextTypeDeprecated("com.sun.faces.PreferXHTML", false, true, PreferXHTMLContentType),
      CompressViewState("com.sun.faces.compressViewState", true),
      CompressViewStateDeprecated("com.sun.faces.COMPRESS_STATE", true, true, CompressViewState),
      CompressJavaScript("com.sun.faces.compressJavaScript", true),
      ExternalizeJavaScriptDeprecated("com.sun.faces.externalizeJavaScript", true, true, (BooleanWebContextInitParameter)null),
      EnableJSStyleHiding("com.sun.faces.enableJSStyleHiding", false),
      EnableScriptInAttributeValue("com.sun.faces.enableScriptsInAttributeValues", true),
      WriteStateAtFormEnd("com.sun.faces.writeStateAtFormEnd", true),
      EnableLazyBeanValidation("com.sun.faces.enableLazyBeanValidation", true),
      EnableLoadBundle11Compatibility("com.sun.faces.enabledLoadBundle11Compatibility", false),
      EnableRestoreView11Compatibility("com.sun.faces.enableRestoreView11Compatibility", false),
      SerializeServerState("javax.faces.SERIALIZE_SERVER_STATE", false),
      SerializeServerStateDeprecated("com.sun.faces.serializeServerState", false, true, SerializeServerState),
      EnableViewStateIdRendering("com.sun.faces.enableViewStateIdRendering", true),
      RegisterConverterPropertyEditors("com.sun.faces.registerConverterPropertyEditors", false),
      DisableFaceletJSFViewHandler("javax.faces.DISABLE_FACELET_JSF_VIEWHANDLER", false),
      DisableFaceletJSFViewHandlerDeprecated("DISABLE_FACELET_JSF_VIEWHANDLER", false, true, DisableFaceletJSFViewHandler),
      DisableDefaultBeanValidator("javax.faces.validator.DISABLE_DEFAULT_BEAN_VALIDATOR", false),
      DateTimeConverterUsesSystemTimezone("javax.faces.DATETIMECONVERTER_DEFAULT_TIMEZONE_IS_SYSTEM_TIMEZONE", false),
      EnableHttpMethodRestrictionPhaseListener("com.sun.faces.ENABLE_HTTP_METHOD_RESTRICTION_PHASE_LISTENER", false),
      FaceletsSkipComments("javax.faces.FACELETS_SKIP_COMMENTS", false),
      FaceletsSkipCommentsDeprecated("facelets.SKIP_COMMENTS", false, true, FaceletsSkipComments, new FaceletsConfigParamLoggingStrategy()),
      PartialStateSaving("javax.faces.PARTIAL_STATE_SAVING", true),
      GenerateUniqueServerStateIds("com.sun.faces.generateUniqueServerStateIds", true),
      InterpretEmptyStringSubmittedValuesAsNull("javax.faces.INTERPRET_EMPTY_STRING_SUBMITTED_VALUES_AS_NULL", false),
      AutoCompleteOffOnViewState("com.sun.faces.autoCompleteOffOnViewState", true),
      EnableThreading("com.sun.faces.enableThreading", false),
      AllowTextChildren("com.sun.faces.allowTextChildren", false),
      CacheResourceModificationTimestamp("com.sun.faces.cacheResourceModificationTimestamp", false),
      EnableAgressiveSessionDirtying("com.sun.faces.enableAgressiveSessionDirtying", false),
      EnableDistributable("com.sun.faces.enableDistributable", false),
      EnableFaceletsResourceResolverResolveCompositeComponents("com.sun.faces.enableFaceletsResourceResolverCompositeComponents", false),
      EnableMissingResourceLibraryDetection("com.sun.faces.enableMissingResourceLibraryDetection", false),
      DisableIdUniquenessCheck("com.sun.faces.disableIdUniquenessCheck", false),
      EnableTransitionTimeNoOpFlash("com.sun.faces.enableTransitionTimeNoOpFlash", false),
      ForceAlwaysWriteFlashCookie("com.sun.faces.forceAlwaysWriteFlashCookie", false),
      ViewRootPhaseListenerQueuesException("javax.faces.VIEWROOT_PHASE_LISTENER_QUEUES_EXCEPTIONS", false),
      EnableValidateWholeBean("javax.faces.validator.ENABLE_VALIDATE_WHOLE_BEAN", false),
      EnableWebsocketEndpoint("javax.faces.ENABLE_WEBSOCKET_ENDPOINT", false),
      DisallowDoctypeDecl("com.sun.faces.disallowDoctypeDecl", false);

      private BooleanWebContextInitParameter alternate;
      private String qualifiedName;
      private boolean defaultValue;
      private boolean deprecated;
      private DeprecationLoggingStrategy loggingStrategy;

      public boolean getDefaultValue() {
         return this.defaultValue;
      }

      public String getQualifiedName() {
         return this.qualifiedName;
      }

      DeprecationLoggingStrategy getDeprecationLoggingStrategy() {
         return this.loggingStrategy;
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

      private BooleanWebContextInitParameter(String qualifiedName, boolean defaultValue, boolean deprecated, BooleanWebContextInitParameter alternate, DeprecationLoggingStrategy loggingStrategy) {
         this(qualifiedName, defaultValue, deprecated, alternate);
         this.loggingStrategy = loggingStrategy;
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
      FaceletsSuffix("javax.faces.FACELETS_SUFFIX", ".xhtml"),
      DefaultSuffix("javax.faces.DEFAULT_SUFFIX", ".xhtml .view.xml .jsp"),
      JavaxFacesConfigFiles("javax.faces.CONFIG_FILES", ""),
      JavaxFacesProjectStage("javax.faces.PROJECT_STAGE", "Production"),
      AlternateLifecycleId("javax.faces.LIFECYCLE_ID", ""),
      ResourceExcludes("javax.faces.RESOURCE_EXCLUDES", ".class .jsp .jspx .properties .xhtml .groovy"),
      NumberOfViews("com.sun.faces.numberOfViewsInSession", "15"),
      NumberOfViewsDeprecated("com.sun.faces.NUMBER_OF_VIEWS_IN_SESSION", "15", true, NumberOfViews),
      NumberOfLogicalViews("com.sun.faces.numberOfLogicalViews", "15"),
      NumberOfLogicalViewsDeprecated("com.sun.faces.NUMBER_OF_VIEWS_IN_LOGICAL_VIEW_IN_SESSION", "15", true, NumberOfLogicalViews),
      NumberOfConcurrentFlashUsers("com.sun.faces.numberOfConcerrentFlashUsers", "5000"),
      NumberOfFlashesBetweenFlashReapings("com.sun.faces.numberOfFlashesBetweenFlashReapings", "5000"),
      InjectionProviderClass("com.sun.faces.injectionProvider", ""),
      SerializationProviderClass("com.sun.faces.serializationProvider", ""),
      ResponseBufferSize("com.sun.faces.responseBufferSize", "1024"),
      FaceletsBufferSize("javax.faces.FACELETS_BUFFER_SIZE", "1024"),
      FaceletsBufferSizeDeprecated("facelets.BUFFER_SIZE", "1024", true, FaceletsBufferSize, new FaceletsConfigParamLoggingStrategy()),
      ClientStateWriteBufferSize("com.sun.faces.clientStateWriteBufferSize", "8192"),
      ResourceBufferSize("com.sun.faces.resourceBufferSize", "2048"),
      ExpressionFactory("com.sun.faces.expressionFactory", "com.sun.el.ExpressionFactoryImpl"),
      ClientStateTimeout("com.sun.faces.clientStateTimeout", ""),
      DefaultResourceMaxAge("com.sun.faces.defaultResourceMaxAge", "604800000"),
      ResourceUpdateCheckPeriod("com.sun.faces.resourceUpdateCheckPeriod", "5"),
      CompressableMimeTypes("com.sun.faces.compressableMimeTypes", ""),
      DisableUnicodeEscaping("com.sun.faces.disableUnicodeEscaping", "auto"),
      FaceletsDefaultRefreshPeriod("javax.faces.FACELETS_REFRESH_PERIOD", "2"),
      FaceletsDefaultRefreshPeriodDeprecated("facelets.REFRESH_PERIOD", "2", true, FaceletsDefaultRefreshPeriod, new FaceletsConfigParamLoggingStrategy()),
      FaceletsResourceResolver("javax.faces.FACELETS_RESOURCE_RESOLVER", ""),
      FaceletsResourceResolverDeprecated("facelets.RESOURCE_RESOLVER", "", true, FaceletsResourceResolver, new FaceletsConfigParamLoggingStrategy()),
      FaceletsViewMappings("javax.faces.FACELETS_VIEW_MAPPINGS", ""),
      FaceletsViewMappingsDeprecated("facelets.VIEW_MAPPINGS", "", true, FaceletsViewMappings, new FaceletsConfigParamLoggingStrategy()),
      FaceletsLibraries("javax.faces.FACELETS_LIBRARIES", ""),
      FaceletsLibrariesDeprecated("facelets.LIBRARIES", "", true, FaceletsLibraries, new FaceletsConfigParamLoggingStrategy()),
      FaceletsDecorators("javax.faces.FACELETS_DECORATORS", ""),
      FaceletsDecoratorsDeprecated("facelets.DECORATORS", "", true, FaceletsDecorators, new FaceletsConfigParamLoggingStrategy()),
      DuplicateJARPattern("com.sun.faces.duplicateJARPattern", ""),
      ValidateEmptyFields("javax.faces.VALIDATE_EMPTY_FIELDS", "auto"),
      FullStateSavingViewIds("javax.faces.FULL_STATE_SAVING_VIEW_IDS", ""),
      AnnotationScanPackages("com.sun.faces.annotationScanPackages", ""),
      FaceletCache("com.sun.faces.faceletCache", ""),
      FaceletsProcessingFileExtensionProcessAs("", ""),
      ClientWindowMode("javax.faces.CLIENT_WINDOW_MODE", "none"),
      WebAppResourcesDirectory("javax.faces.WEBAPP_RESOURCES_DIRECTORY", "/resources"),
      WebAppContractsDirectory("javax.faces.WEBAPP_CONTRACTS_DIRECTORY", "/contracts");

      private String defaultValue;
      private String qualifiedName;
      private WebContextInitParameter alternate;
      private boolean deprecated;
      private DeprecationLoggingStrategy loggingStrategy;

      public String getDefaultValue() {
         return this.defaultValue;
      }

      public String getQualifiedName() {
         return this.qualifiedName;
      }

      DeprecationLoggingStrategy getDeprecationLoggingStrategy() {
         return this.loggingStrategy;
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

      private WebContextInitParameter(String qualifiedName, String defaultValue, boolean deprecated, WebContextInitParameter alternate, DeprecationLoggingStrategy loggingStrategy) {
         this(qualifiedName, defaultValue, deprecated, alternate);
         this.loggingStrategy = loggingStrategy;
      }

      private WebContextInitParameter getAlternate() {
         return this.alternate;
      }

      private boolean isDeprecated() {
         return this.deprecated;
      }
   }
}
