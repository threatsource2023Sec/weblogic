package org.apache.openjpa.persistence;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.persistence.spi.PersistenceUnitTransactionType;
import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.conf.OpenJPAConfigurationImpl;
import org.apache.openjpa.conf.OpenJPAProductDerivation;
import org.apache.openjpa.lib.conf.AbstractProductDerivation;
import org.apache.openjpa.lib.conf.Configuration;
import org.apache.openjpa.lib.conf.ConfigurationProvider;
import org.apache.openjpa.lib.conf.Configurations;
import org.apache.openjpa.lib.conf.MapConfigurationProvider;
import org.apache.openjpa.lib.conf.ProductDerivations;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.meta.XMLMetaDataParser;
import org.apache.openjpa.lib.meta.XMLVersionParser;
import org.apache.openjpa.lib.util.J2DoPrivHelper;
import org.apache.openjpa.lib.util.Localizer;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class PersistenceProductDerivation extends AbstractProductDerivation implements OpenJPAProductDerivation {
   public static final String SPEC_JPA = "jpa";
   public static final String ALIAS_EJB = "ejb";
   public static final String RSRC_GLOBAL = "META-INF/openjpa.xml";
   public static final String RSRC_DEFAULT = "META-INF/persistence.xml";
   private static final Localizer _loc = Localizer.forPackage(PersistenceProductDerivation.class);

   public void putBrokerFactoryAliases(Map m) {
   }

   public int getType() {
      return 0;
   }

   public void validate() throws Exception {
      AccessController.doPrivileged(J2DoPrivHelper.getClassLoaderAction(EntityManagerFactory.class));
   }

   public boolean beforeConfigurationLoad(Configuration c) {
      if (!(c instanceof OpenJPAConfigurationImpl)) {
         return false;
      } else {
         OpenJPAConfigurationImpl conf = (OpenJPAConfigurationImpl)c;
         conf.metaFactoryPlugin.setAlias("ejb", PersistenceMetaDataFactory.class.getName());
         conf.metaFactoryPlugin.setAlias("jpa", PersistenceMetaDataFactory.class.getName());
         conf.addValue(new EntityManagerFactoryValue());
         return true;
      }
   }

   public boolean afterSpecificationSet(Configuration c) {
      if (c instanceof OpenJPAConfigurationImpl && "jpa".equals(((OpenJPAConfiguration)c).getSpecification())) {
         OpenJPAConfigurationImpl conf = (OpenJPAConfigurationImpl)c;
         conf.metaFactoryPlugin.setDefault("jpa");
         conf.metaFactoryPlugin.setString("jpa");
         conf.lockManagerPlugin.setDefault("version");
         conf.lockManagerPlugin.setString("version");
         conf.nontransactionalWrite.setDefault("true");
         conf.nontransactionalWrite.set(true);
         return true;
      } else {
         return false;
      }
   }

   public ConfigurationProvider load(PersistenceUnitInfo pinfo, Map m) throws IOException {
      if (pinfo == null) {
         return null;
      } else if (!isOpenJPAPersistenceProvider(pinfo, (ClassLoader)null)) {
         warnUnknownProvider(pinfo);
         return null;
      } else {
         ConfigurationProviderImpl cp = new ConfigurationProviderImpl();
         cp.addProperties(PersistenceUnitInfoImpl.toOpenJPAProperties(pinfo));
         cp.addProperties(m);
         if (pinfo instanceof PersistenceUnitInfoImpl) {
            PersistenceUnitInfoImpl impl = (PersistenceUnitInfoImpl)pinfo;
            if (impl.getPersistenceXmlFileUrl() != null) {
               cp.setSource(impl.getPersistenceXmlFileUrl().toString());
            }
         }

         return cp;
      }
   }

   public ConfigurationProvider load(String rsrc, String name, Map m) throws IOException {
      boolean explicit = !StringUtils.isEmpty(rsrc);
      if (!explicit) {
         rsrc = "META-INF/persistence.xml";
      }

      ConfigurationProviderImpl cp = new ConfigurationProviderImpl();
      Boolean ret = this.load(cp, rsrc, name, m, (ClassLoader)null, explicit);
      if (ret != null) {
         return ret ? cp : null;
      } else if (explicit) {
         return null;
      } else {
         PersistenceUnitInfoImpl pinfo = new PersistenceUnitInfoImpl();
         pinfo.fromUserProperties(m);
         if (!isOpenJPAPersistenceProvider(pinfo, (ClassLoader)null)) {
            warnUnknownProvider(pinfo);
            return null;
         } else {
            cp.addProperties(pinfo.toOpenJPAProperties());
            return cp;
         }
      }
   }

   public ConfigurationProvider load(String rsrc, String anchor, ClassLoader loader) throws IOException {
      if (rsrc != null && !rsrc.endsWith(".xml")) {
         return null;
      } else {
         ConfigurationProviderImpl cp = new ConfigurationProviderImpl();
         return this.load(cp, rsrc, anchor, (Map)null, loader, true) == Boolean.TRUE ? cp : null;
      }
   }

   public ConfigurationProvider load(File file, String anchor) throws IOException {
      if (!file.getName().endsWith(".xml")) {
         return null;
      } else if (!this.isVersionOnePersistenceDoc(file)) {
         return null;
      } else {
         ConfigurationParser parser = new ConfigurationParser((Map)null);
         parser.parse(file);
         return this.load((PersistenceUnitInfo)this.findUnit(parser.getResults(), anchor, (ClassLoader)null), (Map)null);
      }
   }

   public String getDefaultResourceLocation() {
      return "META-INF/persistence.xml";
   }

   public List getAnchorsInFile(File file) throws IOException {
      if (!this.isVersionOnePersistenceDoc(file)) {
         return null;
      } else {
         ConfigurationParser parser = new ConfigurationParser((Map)null);

         try {
            parser.parse(file);
            return this.getUnitNames(parser);
         } catch (IOException var4) {
            return null;
         }
      }
   }

   private List getUnitNames(ConfigurationParser parser) {
      List units = parser.getResults();
      List names = new ArrayList();
      Iterator i$ = units.iterator();

      while(i$.hasNext()) {
         PersistenceUnitInfoImpl unit = (PersistenceUnitInfoImpl)i$.next();
         names.add(unit.getPersistenceUnitName());
      }

      return names;
   }

   public List getAnchorsInResource(String resource) throws Exception {
      ConfigurationParser parser = new ConfigurationParser((Map)null);

      try {
         ClassLoader loader = (ClassLoader)AccessController.doPrivileged(J2DoPrivHelper.getContextClassLoaderAction());
         List urls = getResourceURLs(resource, loader);
         if (urls != null) {
            Iterator i$ = urls.iterator();

            while(i$.hasNext()) {
               URL url = (URL)i$.next();
               if (this.isVersionOnePersistenceDoc(url)) {
                  parser.parse(url);
               }
            }
         }

         return this.getUnitNames(parser);
      } catch (IOException var7) {
         return null;
      }
   }

   public ConfigurationProvider loadGlobals(ClassLoader loader) throws IOException {
      String[] prefixes = ProductDerivations.getConfigurationPrefixes();
      String rsrc = null;

      for(int i = 0; i < prefixes.length && StringUtils.isEmpty(rsrc); ++i) {
         rsrc = (String)AccessController.doPrivileged(J2DoPrivHelper.getPropertyAction(prefixes[i] + ".properties"));
      }

      boolean explicit = !StringUtils.isEmpty(rsrc);
      String anchor = null;
      int idx = !explicit ? -1 : rsrc.lastIndexOf(35);
      if (idx != -1) {
         if (idx < rsrc.length() - 1) {
            anchor = rsrc.substring(idx + 1);
         }

         rsrc = rsrc.substring(0, idx);
      }

      if (StringUtils.isEmpty(rsrc)) {
         rsrc = "META-INF/openjpa.xml";
      } else if (!rsrc.endsWith(".xml")) {
         return null;
      }

      ConfigurationProviderImpl cp = new ConfigurationProviderImpl();
      return this.load(cp, rsrc, anchor, (Map)null, loader, explicit) == Boolean.TRUE ? cp : null;
   }

   public ConfigurationProvider loadDefaults(ClassLoader loader) throws IOException {
      ConfigurationProviderImpl cp = new ConfigurationProviderImpl();
      return this.load(cp, "META-INF/persistence.xml", (String)null, (Map)null, loader, false) == Boolean.TRUE ? cp : null;
   }

   private static List getResourceURLs(String rsrc, ClassLoader loader) throws IOException {
      Enumeration urls = null;

      try {
         urls = (Enumeration)AccessController.doPrivileged(J2DoPrivHelper.getResourcesAction(loader, rsrc));
         if (!urls.hasMoreElements()) {
            if (!rsrc.startsWith("META-INF")) {
               urls = (Enumeration)AccessController.doPrivileged(J2DoPrivHelper.getResourcesAction(loader, "META-INF/" + rsrc));
            }

            if (!urls.hasMoreElements()) {
               return null;
            }
         }
      } catch (PrivilegedActionException var4) {
         throw (IOException)var4.getException();
      }

      return Collections.list(urls);
   }

   private Boolean load(ConfigurationProviderImpl cp, String rsrc, String name, Map m, ClassLoader loader, boolean explicit) throws IOException {
      if (loader == null) {
         loader = (ClassLoader)AccessController.doPrivileged(J2DoPrivHelper.getContextClassLoaderAction());
      }

      List urls = getResourceURLs(rsrc, loader);
      if (urls != null && urls.size() != 0) {
         ConfigurationParser parser = new ConfigurationParser(m);
         PersistenceUnitInfoImpl pinfo = this.parseResources(parser, urls, name, loader);
         if (pinfo == null) {
            if (!explicit) {
               return Boolean.FALSE;
            } else {
               throw new MissingResourceException(_loc.get("missing-xml-config", rsrc, String.valueOf(name)).getMessage(), this.getClass().getName(), rsrc);
            }
         } else if (!isOpenJPAPersistenceProvider(pinfo, loader)) {
            if (!explicit) {
               warnUnknownProvider(pinfo);
               return Boolean.FALSE;
            } else {
               throw new MissingResourceException(_loc.get("unknown-provider", rsrc, name, pinfo.getPersistenceProviderClassName()).getMessage(), this.getClass().getName(), rsrc);
            }
         } else {
            cp.addProperties(pinfo.toOpenJPAProperties());
            cp.setSource(pinfo.getPersistenceXmlFileUrl().toString());
            return Boolean.TRUE;
         }
      } else {
         return null;
      }
   }

   private PersistenceUnitInfoImpl parseResources(ConfigurationParser parser, List urls, String name, ClassLoader loader) throws IOException {
      List pinfos = new ArrayList();
      Iterator i$ = urls.iterator();

      while(i$.hasNext()) {
         URL url = (URL)i$.next();
         if (this.isVersionOnePersistenceDoc(url)) {
            parser.parse(url);
            pinfos.addAll(parser.getResults());
         }
      }

      return this.findUnit(pinfos, name, loader);
   }

   private PersistenceUnitInfoImpl findUnit(List pinfos, String name, ClassLoader loader) {
      PersistenceUnitInfoImpl ojpa = null;
      Iterator i$ = pinfos.iterator();

      while(i$.hasNext()) {
         PersistenceUnitInfoImpl pinfo = (PersistenceUnitInfoImpl)i$.next();
         if (name != null) {
            if (name.equals(pinfo.getPersistenceUnitName())) {
               return pinfo;
            }
         } else if (isOpenJPAPersistenceProvider(pinfo, loader)) {
            if (StringUtils.isEmpty(pinfo.getPersistenceUnitName())) {
               return pinfo;
            }

            if (ojpa == null) {
               ojpa = pinfo;
            }
         }
      }

      return ojpa;
   }

   private static boolean isOpenJPAPersistenceProvider(PersistenceUnitInfo pinfo, ClassLoader loader) {
      String provider = pinfo.getPersistenceProviderClassName();
      if (!StringUtils.isEmpty(provider) && !PersistenceProviderImpl.class.getName().equals(provider)) {
         if (loader == null) {
            loader = (ClassLoader)AccessController.doPrivileged(J2DoPrivHelper.getContextClassLoaderAction());
         }

         try {
            return PersistenceProviderImpl.class.isAssignableFrom(Class.forName(provider, false, loader));
         } catch (Throwable var4) {
            log(_loc.get("unloadable-provider", provider, var4).getMessage());
            return false;
         }
      } else {
         return true;
      }
   }

   private static void warnUnknownProvider(PersistenceUnitInfo pinfo) {
      log(_loc.get("unrecognized-provider", (Object)pinfo.getPersistenceProviderClassName()).getMessage());
   }

   private static void log(String msg) {
      System.err.println(msg);
   }

   public boolean isVersionOnePersistenceDoc(URL url) {
      boolean retv = true;
      XMLVersionParser vp = new XMLVersionParser("persistence");

      try {
         vp.parse(url);
         String versionStr = vp.getVersion();
         if (!"1.0".equals(versionStr)) {
            retv = false;
            log(_loc.get("version-limitation", versionStr == null ? "" : versionStr, url.toString()).getMessage());
         }
      } catch (Exception var7) {
         String msg = "Exception: " + var7.getClass().getName() + ": ";
         String m = var7.getLocalizedMessage();
         msg = msg + (StringUtils.isEmpty(m) ? "" : ": " + m);
         log(_loc.get("version-check-error", msg, url.toString()).getMessage());
      }

      return retv;
   }

   public boolean isVersionOnePersistenceDoc(File file) {
      boolean retv = true;
      XMLVersionParser vp = new XMLVersionParser("persistence");

      try {
         vp.parse(file);
         String versionStr = vp.getVersion();
         if (!"1.0".equals(versionStr)) {
            retv = false;
            log(_loc.get("version-limitation", versionStr == null ? "" : versionStr, file.toString()).getMessage());
         }
      } catch (Exception var7) {
         String msg = "Exception: " + var7.getClass().getName() + ": ";
         String m = var7.getLocalizedMessage();
         msg = msg + (StringUtils.isEmpty(m) ? "" : ": " + m);
         log(_loc.get("version-check-error", msg, file.toString()).getMessage());
      }

      return retv;
   }

   public static class ConfigurationParser extends XMLMetaDataParser {
      private final Map _map;
      private PersistenceUnitInfoImpl _info = null;
      private URL _source = null;

      public ConfigurationParser(Map map) {
         this._map = map;
         this.setCaching(false);
         this.setValidating(true);
         this.setParseText(true);
      }

      public void parse(URL url) throws IOException {
         this._source = url;
         super.parse(url);
      }

      public void parse(File file) throws IOException {
         try {
            this._source = (URL)AccessController.doPrivileged(J2DoPrivHelper.toURLAction(file));
         } catch (PrivilegedActionException var3) {
            throw (MalformedURLException)var3.getException();
         }

         super.parse(file);
      }

      protected Object getSchemaSource() {
         return this.getClass().getResourceAsStream("persistence-xsd.rsrc");
      }

      protected void reset() {
         super.reset();
         this._info = null;
         this._source = null;
      }

      protected boolean startElement(String name, Attributes attrs) throws SAXException {
         if (this.currentDepth() == 1) {
            this.startPersistenceUnit(attrs);
         } else if (this.currentDepth() == 3 && "property".equals(name)) {
            this._info.setProperty(attrs.getValue("name"), attrs.getValue("value"));
         }

         return true;
      }

      protected void endElement(String name) throws SAXException {
         if (this.currentDepth() == 1) {
            this._info.fromUserProperties(this._map);
            this.addResult(this._info);
         }

         if (this.currentDepth() == 2) {
            switch (name.charAt(0)) {
               case 'c':
                  this._info.addManagedClassName(this.currentText());
               case 'e':
                  this._info.setExcludeUnlistedClasses("true".equalsIgnoreCase(this.currentText()));
               case 'd':
               case 'f':
               case 'g':
               case 'h':
               case 'i':
               case 'k':
               case 'l':
               case 'o':
               default:
                  break;
               case 'j':
                  if ("jta-data-source".equals(name)) {
                     this._info.setJtaDataSourceName(this.currentText());
                  } else {
                     try {
                        this._info.addJarFileName(this.currentText());
                     } catch (IllegalArgumentException var3) {
                        throw this.getException(var3.getMessage());
                     }
                  }
                  break;
               case 'm':
                  this._info.addMappingFileName(this.currentText());
                  break;
               case 'n':
                  this._info.setNonJtaDataSourceName(this.currentText());
                  break;
               case 'p':
                  if ("provider".equals(name)) {
                     this._info.setPersistenceProviderClassName(this.currentText());
                  }
            }

         }
      }

      private void startPersistenceUnit(Attributes attrs) throws SAXException {
         this._info = new PersistenceUnitInfoImpl();
         this._info.setPersistenceUnitName(attrs.getValue("name"));
         String val = attrs.getValue("transaction-type");
         if (val == null) {
            this._info.setTransactionType(PersistenceUnitTransactionType.RESOURCE_LOCAL);
         } else {
            this._info.setTransactionType((PersistenceUnitTransactionType)Enum.valueOf(PersistenceUnitTransactionType.class, val));
         }

         if (this._source != null) {
            this._info.setPersistenceXmlFileUrl(this._source);
         }

      }
   }

   public static class ConfigurationProviderImpl extends MapConfigurationProvider {
      private String _source;

      public ConfigurationProviderImpl() {
      }

      public ConfigurationProviderImpl(Map props) {
         super(props);
      }

      public void setSource(String source) {
         this._source = source;
      }

      public void setInto(Configuration conf) {
         String orig;
         if (conf instanceof OpenJPAConfiguration) {
            OpenJPAConfiguration oconf = (OpenJPAConfiguration)conf;
            oconf.setSpecification("jpa");
            orig = oconf.getMetaDataFactory();
            if (!StringUtils.isEmpty(orig)) {
               String key = ProductDerivations.getConfigurationKey("MetaDataFactory", this.getProperties());
               Object override = this.getProperties().get(key);
               if (override instanceof String) {
                  this.addProperty(key, Configurations.combinePlugins(orig, (String)override));
               }
            }
         }

         super.setInto(conf, (Log)null);
         Log log = conf.getConfigurationLog();
         if (log.isTraceEnabled()) {
            orig = this._source == null ? "?" : this._source;
            log.trace(PersistenceProductDerivation._loc.get("conf-load", orig, this.getProperties()));
         }

      }
   }
}
