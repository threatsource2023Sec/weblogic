package org.opensaml.security.credential.criteria.impl;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.resolver.Criterion;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.security.SecurityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class EvaluableCredentialCriteriaRegistry {
   public static final String DEFAULT_MAPPINGS_FILE = "/credential-criteria-registry.properties";
   private static Map registry;
   private static boolean initialized;

   private EvaluableCredentialCriteriaRegistry() {
   }

   @Nullable
   public static EvaluableCredentialCriterion getEvaluator(@Nonnull Criterion criteria) throws SecurityException {
      Constraint.isNotNull(criteria, "Criteria to map cannot be null");
      Logger log = getLogger();
      Class clazz = lookup(criteria.getClass());
      if (clazz != null) {
         log.debug("Registry located evaluable criteria class {} for criteria class {}", clazz.getName(), criteria.getClass().getName());

         try {
            Constructor constructor = clazz.getConstructor(criteria.getClass());
            return (EvaluableCredentialCriterion)constructor.newInstance(criteria);
         } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | java.lang.SecurityException var4) {
            log.error("Error instantiating new EvaluableCredentialCriterion instance", var4);
            throw new SecurityException("Could not create new EvaluableCredentialCriterion", var4);
         }
      } else {
         log.debug("Registry could not locate evaluable criteria for criteria class {}", criteria.getClass().getName());
         return null;
      }
   }

   @Nullable
   public static synchronized Class lookup(@Nonnull Class clazz) {
      Constraint.isNotNull(clazz, "Criterion class to lookup cannot be null");
      return (Class)registry.get(clazz);
   }

   public static synchronized void register(@Nonnull Class criteriaClass, @Nonnull Class evaluableClass) {
      Constraint.isNotNull(criteriaClass, "Criterion class to register cannot be null");
      Constraint.isNotNull(evaluableClass, "Evaluable class to register cannot be null");
      Logger log = getLogger();
      log.debug("Registering class {} as evaluator for class {}", evaluableClass.getName(), criteriaClass.getName());
      registry.put(criteriaClass, evaluableClass);
   }

   public static synchronized void deregister(@Nonnull Class criteriaClass) {
      Constraint.isNotNull(criteriaClass, "Criterion class to unregister cannot be null");
      Logger log = getLogger();
      log.debug("Deregistering evaluator for class {}", criteriaClass.getName());
      registry.remove(criteriaClass);
   }

   public static synchronized void clearRegistry() {
      Logger log = getLogger();
      log.debug("Clearing evaluable criteria registry");
      registry.clear();
   }

   public static synchronized boolean isInitialized() {
      return initialized;
   }

   public static synchronized void init() {
      if (!isInitialized()) {
         registry = new HashMap();
         loadDefaultMappings();
         initialized = true;
      }
   }

   public static synchronized void loadDefaultMappings() {
      Logger log = getLogger();
      log.debug("Loading default evaluable credential criteria mappings");
      InputStream inStream = EvaluableCredentialCriteriaRegistry.class.getResourceAsStream("/credential-criteria-registry.properties");
      if (inStream == null) {
         log.error("Could not open resource stream from default mappings file '{}'", "/credential-criteria-registry.properties");
      } else {
         Properties defaultMappings = new Properties();

         try {
            defaultMappings.load(inStream);
         } catch (IOException var4) {
            log.error("Error loading properties file from resource stream", var4);
            return;
         }

         loadMappings(defaultMappings);
      }
   }

   public static synchronized void loadMappings(@Nonnull Properties mappings) {
      Constraint.isNotNull(mappings, "Mappings to load cannot be null");
      Logger log = getLogger();
      Iterator var2 = mappings.keySet().iterator();

      while(true) {
         while(var2.hasNext()) {
            Object key = var2.next();
            if (!(key instanceof String)) {
               log.error("Properties key was not an instance of String, was '{}', skipping...", key.getClass().getName());
            } else {
               String criteriaName = (String)key;
               String evaluatorName = mappings.getProperty(criteriaName);
               ClassLoader classLoader = XMLObjectProviderRegistrySupport.class.getClassLoader();
               Class criteriaClass = null;

               try {
                  criteriaClass = classLoader.loadClass(criteriaName);
               } catch (ClassNotFoundException var11) {
                  log.error("Could not find criteria class '{}', skipping registration", criteriaName);
                  continue;
               }

               Class evaluableClass = null;

               try {
                  evaluableClass = classLoader.loadClass(evaluatorName);
               } catch (ClassNotFoundException var10) {
                  log.error("Could not find evaluator class '{}', skipping registration", criteriaName);
                  continue;
               }

               register(criteriaClass, evaluableClass);
            }
         }

         return;
      }
   }

   @Nonnull
   private static Logger getLogger() {
      return LoggerFactory.getLogger(EvaluableCredentialCriteriaRegistry.class);
   }

   static {
      init();
   }
}
