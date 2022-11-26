package org.apache.openjpa.persistence;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import javax.persistence.spi.ClassTransformer;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.persistence.spi.PersistenceUnitTransactionType;
import javax.sql.DataSource;
import org.apache.openjpa.lib.conf.Configurations;
import org.apache.openjpa.lib.conf.ProductDerivations;
import org.apache.openjpa.lib.meta.SourceTracker;
import org.apache.openjpa.lib.util.J2DoPrivHelper;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.lib.util.MultiClassLoader;
import org.apache.openjpa.util.ClassResolver;

public class PersistenceUnitInfoImpl implements PersistenceUnitInfo, SourceTracker {
   public static final String KEY_PROVIDER = "javax.persistence.provider";
   private static final Localizer s_loc = Localizer.forPackage(PersistenceUnitInfoImpl.class);
   private String _name;
   private final Properties _props = new Properties();
   private PersistenceUnitTransactionType _transType;
   private String _providerClassName;
   private List _mappingFileNames;
   private List _entityClassNames;
   private List _jarFiles;
   private String _jtaDataSourceName;
   private DataSource _jtaDataSource;
   private String _nonJtaDataSourceName;
   private DataSource _nonJtaDataSource;
   private boolean _excludeUnlisted;
   private URL _persistenceXmlFile;
   private URL _root;

   public PersistenceUnitInfoImpl() {
      this._transType = PersistenceUnitTransactionType.RESOURCE_LOCAL;
   }

   public ClassLoader getClassLoader() {
      return null;
   }

   public ClassLoader getNewTempClassLoader() {
      return (ClassLoader)AccessController.doPrivileged(J2DoPrivHelper.newTemporaryClassLoaderAction((ClassLoader)AccessController.doPrivileged(J2DoPrivHelper.getContextClassLoaderAction())));
   }

   public String getPersistenceUnitName() {
      return this._name;
   }

   public void setPersistenceUnitName(String emName) {
      this._name = emName;
   }

   public String getPersistenceProviderClassName() {
      return this._providerClassName;
   }

   public void setPersistenceProviderClassName(String providerClassName) {
      this._providerClassName = providerClassName;
   }

   public PersistenceUnitTransactionType getTransactionType() {
      return this._transType;
   }

   public void setTransactionType(PersistenceUnitTransactionType transType) {
      this._transType = transType;
   }

   public String getJtaDataSourceName() {
      return this._jtaDataSourceName;
   }

   public void setJtaDataSourceName(String jta) {
      this._jtaDataSourceName = jta;
      if (jta != null) {
         this._jtaDataSource = null;
      }

   }

   public DataSource getJtaDataSource() {
      return this._jtaDataSource;
   }

   public void setJtaDataSource(DataSource ds) {
      this._jtaDataSource = ds;
      if (ds != null) {
         this._jtaDataSourceName = null;
      }

   }

   public String getNonJtaDataSourceName() {
      return this._nonJtaDataSourceName;
   }

   public void setNonJtaDataSourceName(String nonJta) {
      this._nonJtaDataSourceName = nonJta;
      if (nonJta != null) {
         this._nonJtaDataSource = null;
      }

   }

   public DataSource getNonJtaDataSource() {
      return this._nonJtaDataSource;
   }

   public void setNonJtaDataSource(DataSource ds) {
      this._nonJtaDataSource = ds;
      if (ds != null) {
         this._nonJtaDataSourceName = null;
      }

   }

   public URL getPersistenceUnitRootUrl() {
      return this._root;
   }

   public void setPersistenceUnitRootUrl(URL root) {
      this._root = root;
   }

   public boolean excludeUnlistedClasses() {
      return this._excludeUnlisted;
   }

   public void setExcludeUnlistedClasses(boolean excludeUnlisted) {
      this._excludeUnlisted = excludeUnlisted;
   }

   public List getMappingFileNames() {
      return this._mappingFileNames == null ? Collections.EMPTY_LIST : this._mappingFileNames;
   }

   public void addMappingFileName(String name) {
      if (this._mappingFileNames == null) {
         this._mappingFileNames = new ArrayList();
      }

      this._mappingFileNames.add(name);
   }

   public List getJarFileUrls() {
      return this._jarFiles == null ? Collections.EMPTY_LIST : this._jarFiles;
   }

   public void addJarFile(URL jar) {
      if (this._jarFiles == null) {
         this._jarFiles = new ArrayList();
      }

      this._jarFiles.add(jar);
   }

   public void addJarFileName(String name) {
      MultiClassLoader loader = (MultiClassLoader)AccessController.doPrivileged(J2DoPrivHelper.newMultiClassLoaderAction());
      loader.addClassLoader(this.getClass().getClassLoader());
      loader.addClassLoader(MultiClassLoader.THREAD_LOADER);
      URL url = (URL)AccessController.doPrivileged(J2DoPrivHelper.getResourceAction((ClassLoader)loader, name));
      if (url != null) {
         this.addJarFile(url);
      } else {
         String[] cp = ((String)AccessController.doPrivileged(J2DoPrivHelper.getPropertyAction("java.class.path"))).split(J2DoPrivHelper.getPathSeparator());

         for(int i = 0; i < cp.length; ++i) {
            if (cp[i].equals(name) || cp[i].endsWith(File.separatorChar + name)) {
               try {
                  this.addJarFile((URL)AccessController.doPrivileged(J2DoPrivHelper.toURLAction(new File(cp[i]))));
                  return;
               } catch (PrivilegedActionException var7) {
                  break;
               } catch (MalformedURLException var8) {
                  break;
               }
            }
         }

         throw new IllegalArgumentException(s_loc.get("bad-jar-name", (Object)name).getMessage());
      }
   }

   public List getManagedClassNames() {
      return this._entityClassNames == null ? Collections.EMPTY_LIST : this._entityClassNames;
   }

   public void addManagedClassName(String name) {
      if (this._entityClassNames == null) {
         this._entityClassNames = new ArrayList();
      }

      this._entityClassNames.add(name);
   }

   public Properties getProperties() {
      return this._props;
   }

   public void setProperty(String key, String value) {
      this._props.setProperty(key, value);
   }

   public void addTransformer(ClassTransformer transformer) {
      throw new UnsupportedOperationException();
   }

   public URL getPersistenceXmlFileUrl() {
      return this._persistenceXmlFile;
   }

   public void setPersistenceXmlFileUrl(URL url) {
      this._persistenceXmlFile = url;
   }

   public void fromUserProperties(Map map) {
      if (map != null) {
         Iterator i$ = map.entrySet().iterator();

         while(i$.hasNext()) {
            Object o = i$.next();
            Object key = ((Map.Entry)o).getKey();
            Object val = ((Map.Entry)o).getValue();
            if ("javax.persistence.provider".equals(key)) {
               this.setPersistenceProviderClassName((String)val);
            } else if ("javax.persistence.transactionType".equals(key)) {
               PersistenceUnitTransactionType ttype;
               if (val instanceof String) {
                  ttype = (PersistenceUnitTransactionType)Enum.valueOf(PersistenceUnitTransactionType.class, (String)val);
               } else {
                  ttype = (PersistenceUnitTransactionType)val;
               }

               this.setTransactionType(ttype);
            } else if ("javax.persistence.jtaDataSource".equals(key)) {
               if (val instanceof String) {
                  this.setJtaDataSourceName((String)val);
               } else {
                  this.setJtaDataSource((DataSource)val);
               }
            } else if ("javax.persistence.nonJtaDataSource".equals(key)) {
               if (val instanceof String) {
                  this.setNonJtaDataSourceName((String)val);
               } else {
                  this.setNonJtaDataSource((DataSource)val);
               }
            } else {
               this._props.put(key, val);
            }
         }

      }
   }

   public Map toOpenJPAProperties() {
      return toOpenJPAProperties(this);
   }

   public static Map toOpenJPAProperties(PersistenceUnitInfo info) {
      Map map = new HashMap();
      Set added = new HashSet();
      if (info.getTransactionType() == PersistenceUnitTransactionType.JTA) {
         put(map, added, "TransactionMode", "managed");
      }

      boolean hasJta = false;
      DataSource ds = info.getJtaDataSource();
      if (ds != null) {
         put(map, added, "ConnectionFactory", ds);
         put(map, added, "ConnectionFactoryMode", "managed");
         hasJta = true;
      } else if (info instanceof PersistenceUnitInfoImpl && ((PersistenceUnitInfoImpl)info).getJtaDataSourceName() != null) {
         put(map, added, "ConnectionFactoryName", ((PersistenceUnitInfoImpl)info).getJtaDataSourceName());
         put(map, added, "ConnectionFactoryMode", "managed");
         hasJta = true;
      }

      ds = info.getNonJtaDataSource();
      if (ds != null) {
         if (!hasJta) {
            put(map, added, "ConnectionFactory", ds);
         } else {
            put(map, added, "ConnectionFactory2", ds);
         }
      } else if (info instanceof PersistenceUnitInfoImpl && ((PersistenceUnitInfoImpl)info).getNonJtaDataSourceName() != null) {
         String nonJtaName = ((PersistenceUnitInfoImpl)info).getNonJtaDataSourceName();
         if (!hasJta) {
            put(map, added, "ConnectionFactoryName", nonJtaName);
         } else {
            put(map, added, "ConnectionFactory2Name", nonJtaName);
         }
      }

      if (info.getClassLoader() != null) {
         put(map, added, "ClassResolver", new ClassResolverImpl(info.getClassLoader()));
      }

      Properties props = info.getProperties();
      String key;
      if (props != null) {
         Iterator i$ = added.iterator();

         while(i$.hasNext()) {
            key = (String)i$.next();
            if (Configurations.containsProperty(key, props)) {
               Configurations.removeProperty(key, props);
            }
         }

         map.putAll(props);
         map.remove("ClassTransformerOptions");
      }

      if (!Configurations.containsProperty("Id", map)) {
         map.put("openjpa.Id", info.getPersistenceUnitName());
      }

      Properties metaFactoryProps = new Properties();
      Iterator i$;
      String rsrc;
      StringBuffer jars;
      if (info.getManagedClassNames() != null && !info.getManagedClassNames().isEmpty()) {
         jars = new StringBuffer();

         for(i$ = info.getManagedClassNames().iterator(); i$.hasNext(); jars.append(rsrc)) {
            rsrc = (String)i$.next();
            if (jars.length() > 0) {
               jars.append(';');
            }
         }

         metaFactoryProps.put("Types", jars.toString());
      }

      if (info.getJarFileUrls() != null && !info.getJarFileUrls().isEmpty() || !info.excludeUnlistedClasses() && info.getPersistenceUnitRootUrl() != null) {
         jars = new StringBuffer();
         String file = null;
         if (!info.excludeUnlistedClasses() && info.getPersistenceUnitRootUrl() != null) {
            URL url = info.getPersistenceUnitRootUrl();
            if ("file".equals(url.getProtocol())) {
               file = URLDecoder.decode(url.getPath());
            } else {
               jars.append(url);
            }
         }

         URL jar;
         for(Iterator i$ = info.getJarFileUrls().iterator(); i$.hasNext(); jars.append(jar)) {
            jar = (URL)i$.next();
            if (jars.length() > 0) {
               jars.append(';');
            }
         }

         if (file != null) {
            metaFactoryProps.put("Files", file);
         }

         if (jars.length() != 0) {
            metaFactoryProps.put("URLs", jars.toString());
         }
      }

      if (info.getMappingFileNames() != null && !info.getMappingFileNames().isEmpty()) {
         jars = new StringBuffer();

         for(i$ = info.getMappingFileNames().iterator(); i$.hasNext(); jars.append(rsrc)) {
            rsrc = (String)i$.next();
            if (jars.length() > 0) {
               jars.append(';');
            }
         }

         metaFactoryProps.put("Resources", jars.toString());
      }

      if (!metaFactoryProps.isEmpty()) {
         key = ProductDerivations.getConfigurationKey("MetaDataFactory", map);
         map.put(key, Configurations.combinePlugins((String)map.get(key), Configurations.serializeProperties(metaFactoryProps)));
      }

      if (info.getPersistenceProviderClassName() != null) {
         map.put("javax.persistence.provider", info.getPersistenceProviderClassName());
      }

      return map;
   }

   private static void put(Map map, Set added, String key, Object val) {
      map.put("openjpa." + key, val);
      added.add(key);
   }

   public File getSourceFile() {
      if (this._persistenceXmlFile == null) {
         return null;
      } else {
         try {
            return new File(this._persistenceXmlFile.toURI());
         } catch (URISyntaxException var2) {
            throw new IllegalStateException(var2);
         }
      }
   }

   public Object getSourceScope() {
      return null;
   }

   public int getSourceType() {
      return 2;
   }

   public String getResourceName() {
      return "PersistenceUnitInfo:" + this._name;
   }

   public static class ClassResolverImpl implements ClassResolver {
      private final ClassLoader _loader;

      public ClassResolverImpl(ClassLoader loader) {
         this._loader = loader;
      }

      public ClassLoader getClassLoader(Class ctx, ClassLoader env) {
         return this._loader;
      }
   }
}
