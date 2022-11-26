package kodo.jdo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Properties;
import javax.jdo.PersistenceManagerFactory;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.conf.OpenJPAConfigurationImpl;
import org.apache.openjpa.conf.OpenJPAProductDerivation;
import org.apache.openjpa.lib.conf.AbstractProductDerivation;
import org.apache.openjpa.lib.conf.Configuration;
import org.apache.openjpa.lib.conf.ConfigurationProvider;
import org.apache.openjpa.lib.conf.MapConfigurationProvider;
import org.apache.openjpa.lib.conf.ProductDerivations;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.FormatPreservingProperties;
import org.apache.openjpa.lib.util.Localizer;

public class JDOProductDerivation extends AbstractProductDerivation implements OpenJPAProductDerivation {
   public static final String SPEC_JDO = "jdo";
   public static final String ALIAS_KODO3 = "kodo3";
   private static final List DEFAULT_ANCHORS = Collections.singletonList("default");
   private static final Localizer _loc = Localizer.forPackage(JDOProductDerivation.class);

   public void putBrokerFactoryAliases(Map m) {
   }

   public int getType() {
      return 0;
   }

   public void validate() throws Exception {
      PersistenceManagerFactory.class.getClassLoader();
   }

   public boolean beforeConfigurationLoad(Configuration c) {
      if (!(c instanceof OpenJPAConfigurationImpl)) {
         return false;
      } else {
         OpenJPAConfigurationImpl conf = (OpenJPAConfigurationImpl)c;
         conf.metaFactoryPlugin.setAlias("jdo", JDOMetaDataFactory.class.getName());
         conf.metaFactoryPlugin.setAlias("kodo3", DeprecatedJDOMetaDataFactory.class.getName());
         return true;
      }
   }

   public boolean afterSpecificationSet(Configuration c) {
      if (!(c instanceof OpenJPAConfigurationImpl)) {
         return false;
      } else {
         OpenJPAConfigurationImpl conf = (OpenJPAConfigurationImpl)c;
         if (!"jdo".equals(conf.getSpecification())) {
            return false;
         } else {
            conf.metaFactoryPlugin.setDefault("jdo");
            conf.metaFactoryPlugin.setString("jdo");
            return true;
         }
      }
   }

   public ConfigurationProvider loadGlobals(ClassLoader loader) throws IOException {
      return this.load((String)null, (String)null, loader);
   }

   public ConfigurationProvider loadDefaults(ClassLoader loader) {
      return null;
   }

   public ConfigurationProvider load(String rsrc, String anchor, ClassLoader loader) throws IOException {
      if (rsrc == null) {
         rsrc = this.getSpecifiedResourceLocation();
      }

      boolean explicit = false;
      if (rsrc != null) {
         if (!isPropertiesName(rsrc)) {
            return null;
         }

         explicit = true;
      } else {
         rsrc = "kodo.properties";
      }

      URL url = this.findResource(rsrc, loader);
      if (url == null && !rsrc.startsWith("META-INF")) {
         url = this.findResource("META-INF/" + rsrc, loader);
      }

      if (url == null) {
         if (!explicit) {
            return null;
         } else {
            throw new MissingResourceException(_loc.get("missing-property-file", rsrc).getMessage(), this.getClass().getName(), rsrc);
         }
      } else {
         InputStream in = url.openStream();
         if (in == null) {
            return null;
         } else {
            Properties props = new FormatPreservingProperties();

            ConfigurationProviderImpl var8;
            try {
               props.load(in);
               var8 = new ConfigurationProviderImpl(JDOProperties.toKodoProperties(props), url.toString());
            } finally {
               try {
                  in.close();
               } catch (Exception var15) {
               }

            }

            return var8;
         }
      }
   }

   private URL findResource(String rsrc, ClassLoader loader) throws IOException {
      if (loader != null) {
         return loader.getResource(rsrc);
      } else {
         URL url = null;
         loader = this.getClass().getClassLoader();
         if (loader != null) {
            url = loader.getResource(rsrc);
         }

         if (url == null) {
            loader = Thread.currentThread().getContextClassLoader();
            if (loader != null) {
               url = loader.getResource(rsrc);
            }
         }

         if (url == null) {
            loader = ClassLoader.getSystemClassLoader();
            if (loader != null) {
               url = loader.getResource(rsrc);
            }
         }

         return url;
      }
   }

   public ConfigurationProvider load(File file, String anchor) throws IOException {
      if (!isPropertiesName(file.getName())) {
         return null;
      } else {
         InputStream in = new FileInputStream(file);
         Properties props = new FormatPreservingProperties();

         ConfigurationProviderImpl var5;
         try {
            props.load(in);
            var5 = new ConfigurationProviderImpl(JDOProperties.toKodoProperties(props), file.toString());
         } finally {
            try {
               in.close();
            } catch (IOException var12) {
            }

         }

         return var5;
      }
   }

   private String getSpecifiedResourceLocation() {
      String[] prefixes = ProductDerivations.getConfigurationPrefixes();
      String rsrc = null;

      for(int i = 0; i < prefixes.length && rsrc == null; ++i) {
         rsrc = System.getProperty(prefixes[i] + ".properties");
      }

      return rsrc;
   }

   private static boolean isPropertiesFile(InputStream in) {
      if (in == null) {
         return false;
      } else {
         try {
            boolean var2;
            try {
               Properties props = new Properties();
               props.load(in);
               var2 = props.size() > 0;
            } finally {
               in.close();
            }

            return var2;
         } catch (IOException var7) {
            return false;
         }
      }
   }

   private static boolean isPropertiesName(String name) {
      return name != null && name.endsWith(".properties");
   }

   public List getAnchorsInFile(File file) {
      try {
         return isPropertiesName(file.getName()) && isPropertiesFile(new FileInputStream(file)) ? DEFAULT_ANCHORS : null;
      } catch (Exception var3) {
         return null;
      }
   }

   public List getAnchorsInResource(String res) {
      try {
         return isPropertiesName(res) && isPropertiesFile(this.getClass().getClassLoader().getResourceAsStream(res)) ? DEFAULT_ANCHORS : null;
      } catch (Exception var3) {
         return null;
      }
   }

   public String getDefaultResourceLocation() {
      String rsrc = this.getSpecifiedResourceLocation();
      if (rsrc == null) {
         rsrc = "kodo.properties";
      }

      return rsrc;
   }

   public static class ConfigurationProviderImpl extends MapConfigurationProvider {
      private final String _source;

      public ConfigurationProviderImpl(Map props) {
         super(props);
         this._source = "?";
      }

      public ConfigurationProviderImpl(Map props, String source) {
         super(props);
         this._source = source;
      }

      public String getSource() {
         return this._source;
      }

      public void setInto(Configuration conf) {
         if (conf instanceof OpenJPAConfiguration) {
            ((OpenJPAConfiguration)conf).setSpecification("jdo");
         }

         super.setInto(conf, (Log)null);
         Log log = conf.getConfigurationLog();
         if (log.isTraceEnabled()) {
            log.trace(JDOProductDerivation._loc.get("conf-load", this._source, this.getProperties()));
         }

      }
   }
}
