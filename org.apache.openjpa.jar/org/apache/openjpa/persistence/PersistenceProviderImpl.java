package org.apache.openjpa.persistence;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.Map;
import javax.persistence.spi.ClassTransformer;
import javax.persistence.spi.PersistenceProvider;
import javax.persistence.spi.PersistenceUnitInfo;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.conf.OpenJPAConfigurationImpl;
import org.apache.openjpa.enhance.PCClassFileTransformer;
import org.apache.openjpa.kernel.Bootstrap;
import org.apache.openjpa.kernel.BrokerFactory;
import org.apache.openjpa.lib.conf.ConfigurationProvider;
import org.apache.openjpa.lib.conf.Configurations;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.MetaDataRepository;
import org.apache.openjpa.util.ClassResolver;

public class PersistenceProviderImpl implements PersistenceProvider {
   static final String CLASS_TRANSFORMER_OPTIONS = "ClassTransformerOptions";
   private static final String EMF_POOL = "EntityManagerFactoryPool";
   private static final Localizer _loc = Localizer.forPackage(PersistenceProviderImpl.class);

   public OpenJPAEntityManagerFactory createEntityManagerFactory(String name, String resource, Map m) {
      PersistenceProductDerivation pd = new PersistenceProductDerivation();

      try {
         Object poolValue = Configurations.removeProperty("EntityManagerFactoryPool", m);
         ConfigurationProvider cp = pd.load(resource, name, m);
         if (cp == null) {
            return null;
         } else {
            BrokerFactory factory = this.getBrokerFactory(cp, poolValue, (ClassLoader)null);
            return JPAFacadeHelper.toEntityManagerFactory(factory);
         }
      } catch (Exception var8) {
         throw PersistenceExceptions.toPersistenceException(var8);
      }
   }

   private BrokerFactory getBrokerFactory(ConfigurationProvider cp, Object poolValue, ClassLoader loader) {
      if (poolValue instanceof String && ("true".equalsIgnoreCase((String)poolValue) || "false".equalsIgnoreCase((String)poolValue))) {
         poolValue = Boolean.valueOf((String)poolValue);
      }

      if (poolValue != null && !(poolValue instanceof Boolean)) {
         throw new IllegalArgumentException(poolValue.toString());
      } else {
         return poolValue != null && (Boolean)poolValue ? Bootstrap.getBrokerFactory(cp, loader) : Bootstrap.newBrokerFactory(cp, loader);
      }
   }

   public OpenJPAEntityManagerFactory createEntityManagerFactory(String name, Map m) {
      return this.createEntityManagerFactory(name, (String)null, m);
   }

   public OpenJPAEntityManagerFactory createContainerEntityManagerFactory(PersistenceUnitInfo pui, Map m) {
      PersistenceProductDerivation pd = new PersistenceProductDerivation();

      try {
         Object poolValue = Configurations.removeProperty("EntityManagerFactoryPool", m);
         ConfigurationProvider cp = pd.load(pui, m);
         if (cp == null) {
            return null;
         } else {
            Exception transformerException = null;
            String ctOpts = (String)Configurations.getProperty("ClassTransformerOptions", pui.getProperties());

            try {
               pui.addTransformer(new ClassTransformerImpl(cp, ctOpts, pui.getNewTempClassLoader(), this.newConfigurationImpl()));
            } catch (Exception var10) {
               transformerException = var10;
            }

            if (!Configurations.containsProperty("BrokerImpl", cp.getProperties())) {
               cp.addProperty("openjpa.BrokerImpl", this.getDefaultBrokerAlias());
            }

            BrokerFactory factory = this.getBrokerFactory(cp, poolValue, pui.getClassLoader());
            if (transformerException != null) {
               Log log = factory.getConfiguration().getLog("openjpa.Runtime");
               if (log.isTraceEnabled()) {
                  log.warn(_loc.get("transformer-registration-error-ex", (Object)pui), transformerException);
               } else {
                  log.warn(_loc.get("transformer-registration-error", (Object)pui));
               }
            }

            return JPAFacadeHelper.toEntityManagerFactory(factory);
         }
      } catch (Exception var11) {
         throw PersistenceExceptions.toPersistenceException(var11);
      }
   }

   protected String getDefaultBrokerAlias() {
      return "non-finalizing";
   }

   protected OpenJPAConfiguration newConfigurationImpl() {
      return new OpenJPAConfigurationImpl();
   }

   private static class ClassTransformerImpl implements ClassTransformer {
      private final ClassFileTransformer _trans;

      private ClassTransformerImpl(ConfigurationProvider cp, String props, final ClassLoader tmpLoader, OpenJPAConfiguration conf) {
         cp.setInto(conf);
         conf.setClassResolver(new ClassResolver() {
            public ClassLoader getClassLoader(Class context, ClassLoader env) {
               return tmpLoader;
            }
         });
         conf.setReadOnly(1);
         MetaDataRepository repos = conf.getMetaDataRepositoryInstance();
         repos.setResolve(2, false);
         this._trans = new PCClassFileTransformer(repos, Configurations.parseProperties(props), tmpLoader);
      }

      public byte[] transform(ClassLoader cl, String name, Class previousVersion, ProtectionDomain pd, byte[] bytes) throws IllegalClassFormatException {
         return this._trans.transform(cl, name, previousVersion, pd, bytes);
      }

      // $FF: synthetic method
      ClassTransformerImpl(ConfigurationProvider x0, String x1, ClassLoader x2, OpenJPAConfiguration x3, Object x4) {
         this(x0, x1, x2, x3);
      }
   }
}
