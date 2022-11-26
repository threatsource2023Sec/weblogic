package org.apache.openjpa.enhance;

import java.lang.instrument.Instrumentation;
import java.security.AccessController;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.conf.OpenJPAConfigurationImpl;
import org.apache.openjpa.lib.conf.Configurations;
import org.apache.openjpa.lib.util.J2DoPrivHelper;
import org.apache.openjpa.lib.util.Options;
import org.apache.openjpa.util.ClassResolver;

public class PCEnhancerAgent {
   public static void premain(String args, Instrumentation inst) {
      Options opts = Configurations.parseProperties(args);
      if (!opts.containsKey("ClassLoadEnhancement") && !opts.containsKey("classLoadEnhancement")) {
         if (!opts.containsKey("RuntimeEnhancement") && !opts.containsKey("runtimeEnhancement")) {
            registerClassLoadEnhancer(inst, opts);
         } else if (opts.getBooleanProperty("RuntimeEnhancement", "runtimeEnhancement", true)) {
            registerClassLoadEnhancer(inst, opts);
         }
      } else if (opts.getBooleanProperty("ClassLoadEnhancement", "classLoadEnhancement", true)) {
         registerClassLoadEnhancer(inst, opts);
      }

      if (opts.getBooleanProperty("RuntimeRedefinition", "runtimeRedefinition", true)) {
         InstrumentationFactory.setInstrumentation(inst);
      } else {
         InstrumentationFactory.setDynamicallyInstallAgent(false);
      }

   }

   private static void registerClassLoadEnhancer(Instrumentation inst, Options opts) {
      OpenJPAConfiguration conf = new OpenJPAConfigurationImpl();
      Configurations.populateConfiguration(conf, opts);
      conf.setConnectionUserName((String)null);
      conf.setConnectionPassword((String)null);
      conf.setConnectionURL((String)null);
      conf.setConnectionDriverName((String)null);
      conf.setConnectionFactoryName((String)null);
      final ClassLoader tmpLoader = (ClassLoader)AccessController.doPrivileged(J2DoPrivHelper.newTemporaryClassLoaderAction((ClassLoader)AccessController.doPrivileged(J2DoPrivHelper.getContextClassLoaderAction())));
      conf.setClassResolver(new ClassResolver() {
         public ClassLoader getClassLoader(Class context, ClassLoader env) {
            return tmpLoader;
         }
      });
      conf.setReadOnly(1);
      conf.instantiateAll();
      PCClassFileTransformer transformer = new PCClassFileTransformer(conf.newMetaDataRepositoryInstance(), opts, tmpLoader);
      inst.addTransformer(transformer);
   }
}
