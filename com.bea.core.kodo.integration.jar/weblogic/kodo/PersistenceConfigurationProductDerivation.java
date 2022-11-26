package weblogic.kodo;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import kodo.jdbc.conf.descriptor.JDBCConfigurationBeanParser;
import kodo.jdbc.conf.descriptor.PersistenceConfigurationBean;
import kodo.jdbc.conf.descriptor.PersistenceUnitConfigurationBean;
import org.apache.openjpa.conf.OpenJPAConfigurationImpl;
import org.apache.openjpa.lib.conf.AbstractProductDerivation;
import org.apache.openjpa.lib.conf.BootstrapException;
import org.apache.openjpa.lib.conf.Configuration;
import org.apache.openjpa.lib.conf.ConfigurationProvider;
import org.apache.openjpa.lib.conf.Configurations;
import org.apache.openjpa.persistence.PersistenceProductDerivation;
import org.apache.openjpa.persistence.PersistenceUnitInfoImpl;
import org.apache.openjpa.util.UserException;
import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;

public class PersistenceConfigurationProductDerivation extends AbstractProductDerivation {
   private static final String PREFIX_KODO = "kodo.";
   private static final String PREFIX_OPENJPA = "openjpa.";

   public int getType() {
      return 1000;
   }

   public boolean beforeConfigurationConstruct(ConfigurationProvider conf) {
      boolean modified = false;
      ClassLoader loader = Thread.currentThread().getContextClassLoader();
      URL url = loader.getResource("META-INF/persistence-configuration.xml");
      if (url == null) {
         return false;
      } else {
         File configDir = null;
         DeploymentPlanBean plan = null;
         String moduleName = "";
         String qualifiedURI = null;

         try {
            qualifiedURI = url.toURI().toString();
         } catch (URISyntaxException var25) {
            throw new UserException(var25);
         }

         Map existing = conf.getProperties();
         boolean idDefined = Configurations.containsProperty("Id", existing);
         String punitName = idDefined ? Configurations.getProperty("Id", existing).toString() : null;
         PersistenceUnitInfoImpl pUnit = this.getPersistenceUnit(punitName);
         Properties pUnitProperties = pUnit == null ? new Properties() : pUnit.getProperties();
         if (pUnit != null && !pUnitProperties.isEmpty()) {
            throw (new BootstrapException(pUnitProperties.keySet() + " is set before sourcing " + qualifiedURI)).setFatal(true);
         } else {
            try {
               PersistenceConfigDescriptorLoader ploader = new PersistenceConfigDescriptorLoader(url, (File)configDir, (DeploymentPlanBean)plan, moduleName, qualifiedURI);
               JDBCConfigurationBeanParser parser = new JDBCConfigurationBeanParser();
               DescriptorBean bean = ploader.loadDescriptorBean();
               if (bean instanceof PersistenceConfigurationBean) {
                  PersistenceConfigurationBean pconfigBean = (PersistenceConfigurationBean)bean;
                  PersistenceUnitConfigurationBean[] pUnitBeans = pconfigBean.getPersistenceUnitConfigurations();

                  for(int i = 0; i < pUnitBeans.length; ++i) {
                     Properties pconfig = null;
                     if (this.matches(pUnitBeans[i].getName(), punitName)) {
                        pconfig = parser.load(pUnitBeans[i]);
                        pconfig.remove("kodo.Name");
                        Iterator keys = pconfig.keySet().iterator();

                        while(keys.hasNext()) {
                           String key = keys.next().toString();
                           String partialKey = key.substring("kodo.".length());
                           boolean existsKey = Configurations.containsProperty(partialKey, existing);
                           if (!existsKey) {
                              conf.addProperty(key, pconfig.get(key));
                              modified = true;
                           }
                        }
                     }
                  }
               }

               return modified;
            } catch (Exception var26) {
               throw new RuntimeException(var26);
            }
         }
      }
   }

   public boolean beforeConfigurationLoad(Configuration c) {
      if (!(c instanceof OpenJPAConfigurationImpl)) {
         return false;
      } else {
         OpenJPAConfigurationImpl conf = (OpenJPAConfigurationImpl)c;
         conf.managedRuntimePlugin.setDefault("org.apache.openjpa.ee.WLSManagedRuntime");
         conf.managedRuntimePlugin.setString("org.apache.openjpa.ee.WLSManagedRuntime");
         return true;
      }
   }

   boolean matches(String name, String anchor) {
      return this.isEmpty(name) && this.isEmpty(anchor) || !this.isEmpty(name) && name.equals(anchor);
   }

   boolean isEmpty(String s) {
      return s == null || s.trim().length() == 0;
   }

   public void validate() throws Exception {
      Class.forName("weblogic.descriptor.DescriptorBean");
   }

   PersistenceUnitInfoImpl getPersistenceUnit(String unitName) {
      try {
         Enumeration urls = this.getClass().getClassLoader().getResources("META-INF/persistence.xml");

         while(urls.hasMoreElements()) {
            PersistenceProductDerivation.ConfigurationParser parser = new PersistenceProductDerivation.ConfigurationParser(new HashMap());
            parser.parse((URL)urls.nextElement());
            List units = parser.getResults();
            Iterator var5 = units.iterator();

            while(var5.hasNext()) {
               PersistenceUnitInfoImpl unit = (PersistenceUnitInfoImpl)var5.next();
               if (this.matches(unitName, unit.getPersistenceUnitName())) {
                  return unit;
               }
            }
         }
      } catch (IOException var7) {
      }

      return null;
   }

   Map ignoreKnownOrUnrecognizedProperty(Map existing) {
      if (existing != null && !existing.isEmpty()) {
         Map copy = new HashMap(existing);
         Configurations.removeProperty("Id", copy);
         Configurations.removeProperty("BrokerFactory", copy);
         Iterator keys = copy.keySet().iterator();

         while(keys.hasNext()) {
            String key = (String)keys.next();
            if (!key.startsWith("kodo.") && !key.startsWith("openjpa.")) {
               copy.remove(key);
            }
         }

         return copy;
      } else {
         return existing;
      }
   }
}
