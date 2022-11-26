package net.shibboleth.utilities.java.support.resolver;

import com.google.common.base.Predicate;
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
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CriterionPredicateRegistry {
   private Logger log = LoggerFactory.getLogger(CriterionPredicateRegistry.class);
   private Map registry = new HashMap();

   @Nullable
   public Predicate getPredicate(@Nonnull Criterion criterion) throws ResolverException {
      Constraint.isNotNull(criterion, "Criterion to map cannot be null");
      Class predicateClass = this.lookup(criterion.getClass());
      if (predicateClass != null) {
         this.log.debug("Registry located Predicate class {} for Criterion class {}", predicateClass.getName(), criterion.getClass().getName());

         try {
            Constructor constructor = predicateClass.getConstructor(criterion.getClass());
            return (Predicate)constructor.newInstance(criterion);
         } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException var4) {
            this.log.error("Error instantiating new Predicate instance", var4);
            throw new ResolverException("Could not create new Predicate instance", var4);
         }
      } else {
         this.log.debug("Registry did not locate Predicate implementation registered for Criterion class {}", criterion.getClass().getName());
         return null;
      }
   }

   @Nullable
   protected Class lookup(@Nonnull Class clazz) {
      Constraint.isNotNull(clazz, "Criterion class to lookup cannot be null");
      return (Class)this.registry.get(clazz);
   }

   public void register(@Nonnull Class criterionClass, @Nonnull Class predicateClass) {
      Constraint.isNotNull(criterionClass, "Criterion class to register cannot be null");
      Constraint.isNotNull(predicateClass, "Predicate class to register cannot be null");
      this.log.debug("Registering class {} as Predicate for Criterion class {}", predicateClass.getName(), criterionClass.getName());
      this.registry.put(criterionClass, predicateClass);
   }

   public void deregister(@Nonnull Class criterionClass) {
      Constraint.isNotNull(criterionClass, "Criterion class to unregister cannot be null");
      this.log.debug("Deregistering Predicate for Criterion class {}", criterionClass.getName());
      this.registry.remove(criterionClass);
   }

   public void clearRegistry() {
      this.log.debug("Clearing Criterion Predicate registry");
      this.registry.clear();
   }

   public void loadMappings(@Nonnull String classpathResource) {
      String resource = (String)Constraint.isNotNull(StringSupport.trimOrNull(classpathResource), "Classpath resource was null or empty");

      try {
         InputStream inStream = this.getClass().getResourceAsStream(resource);
         Throwable var4 = null;

         try {
            if (inStream == null) {
               this.log.error("Could not open resource stream from resource '{}'", resource);
               return;
            }

            Properties mappings = new Properties();
            mappings.load(inStream);
            this.loadMappings(mappings);
         } catch (Throwable var15) {
            var4 = var15;
            throw var15;
         } finally {
            if (inStream != null) {
               if (var4 != null) {
                  try {
                     inStream.close();
                  } catch (Throwable var14) {
                     var4.addSuppressed(var14);
                  }
               } else {
                  inStream.close();
               }
            }

         }

      } catch (IOException var17) {
         this.log.error("Error load mappings from resource '{}'", resource, var17);
      }
   }

   public void loadMappings(@Nonnull Properties mappings) {
      Constraint.isNotNull(mappings, "Mappings to load cannot be null");
      Iterator i$ = mappings.keySet().iterator();

      while(true) {
         while(i$.hasNext()) {
            Object key = i$.next();
            if (!(key instanceof String)) {
               this.log.error("Properties key was not an instance of String, was '{}', skipping...", key.getClass().getName());
            } else {
               String criterionName = (String)key;
               String predicateName = mappings.getProperty(criterionName);
               ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
               Class criterionClass = null;

               try {
                  criterionClass = classLoader.loadClass(criterionName).asSubclass(Criterion.class);
               } catch (ClassNotFoundException var11) {
                  this.log.error("Could not find Criterion class '{}', skipping registration", criterionName);
                  continue;
               }

               Class predicateClass = null;

               try {
                  predicateClass = classLoader.loadClass(predicateName).asSubclass(Predicate.class);
               } catch (ClassNotFoundException var10) {
                  this.log.error("Could not find Predicate class '{}', skipping registration", criterionName);
                  continue;
               }

               this.register(criterionClass, predicateClass);
            }
         }

         return;
      }
   }
}
