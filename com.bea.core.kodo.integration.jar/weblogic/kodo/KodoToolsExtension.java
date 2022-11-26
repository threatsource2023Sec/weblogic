package weblogic.kodo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import javax.enterprise.deploy.shared.ModuleType;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.conf.OpenJPAConfigurationImpl;
import org.apache.openjpa.enhance.PCEnhancer;
import org.apache.openjpa.lib.conf.Configurations;
import org.apache.openjpa.lib.meta.XMLMetaDataParser;
import org.apache.openjpa.lib.util.Options;
import org.apache.openjpa.persistence.PersistenceUnitInfoImpl;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import serp.bytecode.Project;
import weblogic.application.compiler.AppcUtils;
import weblogic.application.compiler.ToolsContext;
import weblogic.application.compiler.ToolsExtension;
import weblogic.application.compiler.ToolsExtensionFactory;
import weblogic.application.compiler.ToolsModule;
import weblogic.application.utils.ClassAnnotationMetaDataFilter;
import weblogic.application.utils.MetaDataIterator;
import weblogic.application.utils.VirtualJarFileMetaDataIterator;
import weblogic.utils.FileUtils;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.classloaders.MultiClassFinder;
import weblogic.utils.classloaders.Source;
import weblogic.utils.compiler.ToolFailureException;
import weblogic.utils.jars.JarFileUtils;
import weblogic.utils.jars.VirtualJarFactory;
import weblogic.utils.jars.VirtualJarFile;

public class KodoToolsExtension implements ToolsExtension {
   private static final String JPA_CONFIGURATION_NAME = "META-INF/persistence.xml";
   private static final String OPJPA_PROVIDER = "org.apache.openjpa.persistence.PersistenceProviderImpl";
   private static final String KODO_PROVIDER = "kodo.persistence.PersistenceProviderImpl";
   private static final String WEBINF_CLASSES;
   private static final String WEBINF_LIB;
   private static final String APP_CLASSES;
   private static final String PERSISTENCE_XML = "<?xml version=\"1.0\"?> <persistence xmlns=\"http://java.sun.com/xml/ns/persistence\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://java.sun.com/xml/ns/persistence http://java.sun.com/xml/ns/persistence/persistence_1_0.xsd\" version=\"1.0\"> <persistence-unit name=\"default\"> </persistence-unit> </persistence> ";
   private final Class[] annotationClasses = new Class[]{Entity.class, Embeddable.class, MappedSuperclass.class};
   private Collection jarFiles = new HashSet();
   private ToolsContext ctx;
   private ToolsModule[] modules;
   private ClassLoader cl;
   private MultiClassFinder moduleFinder;
   private Collection finders;

   public void init(ToolsContext ctx, GenericClassLoader appClassLoader) throws ToolFailureException {
      this.ctx = ctx;
   }

   public void compile() throws ToolFailureException {
      File configFile = null;

      try {
         this.modules = this.ctx.getModules();
         new HashSet();
         Collection appLib = new HashSet();
         Collection warLib = new HashSet();
         URL url = null;
         this.cl = this.getModuleClassLoader();
         url = this.cl.getResource("META-INF/persistence.xml");
         if (url == null) {
            return;
         }

         configFile = this.createConfigFile();
         Enumeration puConfigs = this.moduleFinder.getSources("META-INF/persistence.xml");

         while(puConfigs.hasMoreElements()) {
            String source = puConfigs.nextElement().toString();
            if (source.endsWith(".jar")) {
               if (source.indexOf("WEB-INF") > -1) {
                  source = source.substring(source.lastIndexOf(File.separatorChar) + 1);
                  warLib.add(source);
               } else {
                  appLib.add(source);
               }
            }
         }

         Collection enhanceClasses = this.getEntityClasses(this.cl);
         if (enhanceClasses.size() != 0) {
            Iterator iter = enhanceClasses.iterator();

            while(iter.hasNext()) {
               String className = (String)iter.next();
               Iterator var9 = this.finders.iterator();

               while(var9.hasNext()) {
                  MultiClassFinder finder = (MultiClassFinder)var9.next();
                  Source classSource = finder.getClassSource(className);
                  if (classSource != null) {
                     String source = classSource.toString();
                     if (source.endsWith(".jar")) {
                        if (source.indexOf("WEB-INF") > -1) {
                           source = source.substring(source.lastIndexOf(File.separatorChar) + 1);
                           warLib.add(source);
                        } else {
                           appLib.add(source);
                        }
                     }
                  }
               }
            }

            for(int i = 0; i < this.modules.length; ++i) {
               if (this.modules[i].getModuleType() == ModuleType.EJB) {
                  this.processDir(this.ctx.getModuleContext(this.modules[i].getURI()).getOutputDir().getPath(), configFile, enhanceClasses, this.cl, false);
               }

               if (this.modules[i].getModuleType() == ModuleType.WAR) {
                  this.processDir(this.ctx.getModuleContext(this.modules[i].getURI()).getOutputDir() + WEBINF_CLASSES, configFile, enhanceClasses, this.cl, false);
                  Iterator lib = warLib.iterator();

                  while(lib.hasNext()) {
                     String libName = (String)lib.next();
                     File libFile = new File(this.ctx.getModuleContext(this.modules[i].getURI()).getOutputDir() + WEBINF_LIB + libName);
                     if (libFile.exists()) {
                        this.processJar(libFile, configFile, enhanceClasses, this.cl, false);
                     }
                  }
               }
            }

            if (this.ctx.getEar() != null) {
               this.processDir(this.ctx.getOutputDir() + APP_CLASSES, configFile, enhanceClasses, this.cl, false);
            }

            this.processJars(appLib, configFile, enhanceClasses, this.cl, false);
            return;
         }
      } catch (IOException var16) {
         throw new ToolFailureException(var16.getMessage(), var16);
      } finally {
         if (configFile != null && configFile.exists()) {
            FileUtils.remove(configFile);
         }

      }

   }

   public Map merge() throws ToolFailureException {
      return null;
   }

   public void cleanup() {
   }

   private File createConfigFile() throws IOException {
      File configFile = new File(System.getProperty("java.io.tmpdir"), "persistence.xml");
      FileWriter out = new FileWriter(configFile);
      out.write("<?xml version=\"1.0\"?> <persistence xmlns=\"http://java.sun.com/xml/ns/persistence\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://java.sun.com/xml/ns/persistence http://java.sun.com/xml/ns/persistence/persistence_1_0.xsd\" version=\"1.0\"> <persistence-unit name=\"default\"> </persistence-unit> </persistence> ");
      out.close();
      return configFile;
   }

   private void processJars(Collection jars, File configURL, Collection classes, ClassLoader classLoader, boolean needExclude) throws ToolFailureException {
      Iterator iter = jars.iterator();

      while(iter.hasNext()) {
         String jarPath = (String)iter.next();
         this.processJar(new File(jarPath), configURL, classes, classLoader, needExclude);
      }

   }

   private void processJar(File jar, File configURL, Collection classes, ClassLoader classLoader, boolean needExclude) throws ToolFailureException {
      String target = this.ctx.getTempDir().getPath() + File.separatorChar + "tempjar" + System.currentTimeMillis();

      try {
         JarFileUtils.extract(jar, new File(target));
         this.processDir(target, configURL, classes, classLoader, needExclude);
         JarFileUtils.createJarFileFromDirectory(jar, new File(target));
      } catch (IOException var11) {
         throw new ToolFailureException(var11.getMessage(), var11);
      } finally {
         FileUtils.remove(new File(target));
      }

   }

   private void processDir(String target, File configFile, Collection classes, ClassLoader classLoader, boolean needExclude) throws ToolFailureException {
      VirtualJarFile virtualFile = null;

      try {
         File targetPath = new File(target);
         if (targetPath.exists()) {
            virtualFile = VirtualJarFactory.createVirtualJar(targetPath);
            OpenJPAConfiguration conf = new OpenJPAConfigurationImpl();
            Options opts = new Options();
            opts.put("p", configFile.getCanonicalPath());
            Configurations.populateConfiguration(conf, opts);
            MetaDataIterator iter = new VirtualJarFileMetaDataIterator(virtualFile, new ClassAnnotationMetaDataFilter(this.annotationClasses));
            Project project = new Project();

            while(iter.hasNext()) {
               String path = iter.next().getName();
               String name = path.replace('/', '.');
               name = name.substring(0, name.length() - 6);
               if (classes.contains(name) || !needExclude) {
                  Class clazz = Class.forName(name, false, classLoader);
                  PCEnhancer enhancer = new PCEnhancer(conf, clazz);
                  enhancer.setDirectory(targetPath);
                  enhancer.run();
                  enhancer.record();
                  project.clear();
               }
            }

            return;
         }
      } catch (IOException var25) {
         throw new ToolFailureException(var25.getMessage(), var25);
      } catch (ClassNotFoundException var26) {
         throw new ToolFailureException(var26.getMessage(), var26);
      } finally {
         if (virtualFile != null) {
            try {
               virtualFile.close();
            } catch (IOException var24) {
            }
         }

      }

   }

   private Collection getEntityClasses(ClassLoader cl) throws ToolFailureException {
      Collection enhanceClasses = new HashSet();
      Thread thread = Thread.currentThread();
      ClassLoader origClassLoader = thread.getContextClassLoader();
      thread.setContextClassLoader(cl);

      try {
         Collection persistenceFiles = new HashSet(Collections.list(cl.getResources("META-INF/persistence.xml")));
         ConfigurationParser parser = new ConfigurationParser((Map)null);
         Iterator iter = persistenceFiles.iterator();

         label79:
         while(iter.hasNext()) {
            URL path = (URL)iter.next();
            parser.parse(path);
            Iterator iterator = parser.getResults().iterator();

            while(true) {
               PersistenceUnitInfoImpl pu;
               String persistenceProvider;
               do {
                  if (!iterator.hasNext()) {
                     parser.clear();
                     continue label79;
                  }

                  pu = (PersistenceUnitInfoImpl)iterator.next();
                  this.jarFiles.addAll(pu.getJarFileUrls());
                  persistenceProvider = pu.getPersistenceProviderClassName();
               } while(persistenceProvider != null && !persistenceProvider.equals("org.apache.openjpa.persistence.PersistenceProviderImpl") && !persistenceProvider.equals("kodo.persistence.PersistenceProviderImpl"));

               enhanceClasses.addAll(pu.getManagedClassNames());
            }
         }
      } catch (IOException var15) {
         throw new ToolFailureException(var15.getMessage(), var15);
      } finally {
         thread.setContextClassLoader(origClassLoader);
      }

      return enhanceClasses;
   }

   private ClassLoader getModuleClassLoader() {
      this.moduleFinder = new MultiClassFinder();
      MultiClassFinder warFinder = new MultiClassFinder();
      MultiClassFinder appFinder = new MultiClassFinder();
      MultiClassFinder shareLibFinder = new MultiClassFinder();
      this.finders = new ArrayList();
      if (this.ctx.getEar() != null) {
         this.moduleFinder.addFinder(this.ctx.getEar().getClassFinder());
         appFinder.addFinder(this.ctx.getEar().getClassFinder());
      }

      for(int i = 0; i < this.modules.length; ++i) {
         ClassFinder moduleClassFinder = this.ctx.getModuleContext(this.modules[i].getURI()).getClassFinder();
         if (moduleClassFinder != null) {
            this.moduleFinder.addFinder(moduleClassFinder);
         }

         if (this.modules[i].getModuleType() == ModuleType.WAR) {
            warFinder.addFinder(this.ctx.getModuleContext(this.modules[i].getURI()).getClassFinder());
         }
      }

      this.moduleFinder.addFinder(this.ctx.getAppClassLoader().getClassFinder());
      shareLibFinder.addFinder(this.ctx.getAppClassLoader().getClassFinder());
      this.finders.add(warFinder);
      this.finders.add(appFinder);
      this.finders.add(shareLibFinder);
      return AppcUtils.getClassLoaderForApplication(this.moduleFinder, this.ctx, this.ctx.getApplicationId());
   }

   static {
      WEBINF_CLASSES = File.separatorChar + "WEB-INF" + File.separatorChar + "classes";
      WEBINF_LIB = File.separatorChar + "WEB-INF" + File.separatorChar + "lib" + File.separatorChar;
      APP_CLASSES = File.separatorChar + "APP-INF" + File.separatorChar + "classes";
   }

   public static class KodoToolsExtensionFactory implements ToolsExtensionFactory {
      public ToolsExtension createExtension(ToolsContext ctx) {
         return new KodoToolsExtension();
      }
   }

   private class ConfigurationParser extends XMLMetaDataParser {
      private final Map _map;
      private PersistenceUnitInfoImpl _info;
      private URL _source;

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
         this._source = file.toURL();
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
                        this._info.addJarFileName(KodoToolsExtension.this.ctx.getRefappUri() + "#" + this.currentText());
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
         if (this._source != null) {
            this._info.setPersistenceXmlFileUrl(this._source);
         }

      }
   }
}
