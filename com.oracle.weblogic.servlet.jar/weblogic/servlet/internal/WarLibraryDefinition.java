package weblogic.servlet.internal;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.xml.stream.XMLStreamException;
import weblogic.application.Type;
import weblogic.application.io.ExplodedJar;
import weblogic.application.io.JarCopyFilter;
import weblogic.application.library.IllegalSpecVersionTypeException;
import weblogic.application.library.J2EELibraryReference;
import weblogic.application.library.LibraryConstants;
import weblogic.application.library.LibraryContext;
import weblogic.application.library.LibraryData;
import weblogic.application.library.LibraryProcessingException;
import weblogic.application.library.LibraryReference;
import weblogic.application.library.LibraryReferenceFactory;
import weblogic.application.library.LibraryConstants.AutoReferrer;
import weblogic.application.metadatacache.Cache;
import weblogic.application.metadatacache.MetadataCacheException;
import weblogic.application.utils.LibraryLoggingUtils;
import weblogic.j2ee.descriptor.ApplicationBean;
import weblogic.j2ee.descriptor.WebAppBean;
import weblogic.j2ee.descriptor.WebBean;
import weblogic.j2ee.descriptor.wl.WeblogicWebAppBean;
import weblogic.servlet.HTTPLogger;
import weblogic.servlet.utils.WarUtils;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.MultiClassFinder;
import weblogic.utils.jars.VirtualJarFactory;
import weblogic.utils.jars.VirtualJarFile;

public class WarLibraryDefinition extends SharedLibraryDefinition {
   private LibraryReference[] libraryRefs;
   private final String ctxRootFromLocation;
   private ExplodedJar jar;
   private String[] prefAppPackagesName;
   private String[] prefAppResourcesName;
   private String jsfConfigFiles;

   public WarLibraryDefinition(LibraryData d, File tmpDir) {
      this(d, tmpDir, Type.WAR);
   }

   public WarLibraryDefinition(LibraryData d, File tmpDir, Type type) {
      super(d, type, tmpDir);
      this.libraryRefs = null;
      this.prefAppPackagesName = null;
      this.prefAppResourcesName = null;
      this.jsfConfigFiles = null;
      this.ctxRootFromLocation = this.getLocation().getName();
   }

   protected List getTldLocations() throws MetadataCacheException {
      ClassFinder cf = null;
      ClassFinder rf = null;
      List tldUris = new ArrayList();

      try {
         cf = this.getClassFinder();
         rf = new War.ResourceFinder("wld#", cf);
         WarUtils.findTlds((ClassFinder)rf, (List)tldUris, (ClassFinder)cf);
      } catch (Exception var8) {
         throw new MetadataCacheException(var8);
      } finally {
         if (cf != null) {
            cf.close();
         }

         if (rf != null) {
            rf.close();
         }

      }

      return tldUris;
   }

   protected List getFacesConfigLocations() throws MetadataCacheException {
      ClassFinder cf = null;
      ClassFinder rf = null;

      List var3;
      try {
         cf = this.getClassFinder();
         rf = new War.ResourceFinder("wld#", cf);
         var3 = WarUtils.findFacesConfigs(this.jsfConfigFiles, rf, cf);
      } catch (Exception var7) {
         throw new MetadataCacheException(var7);
      } finally {
         if (cf != null) {
            cf.close();
         }

         if (rf != null) {
            rf.close();
         }

      }

      return var3;
   }

   protected ClassFinder getClassFinder() throws IOException {
      if (this.archived) {
         return this.jar.getClassFinder();
      } else {
         ExplodedJar libJar = new ExplodedJar("wld", this.extractDir, new File[]{this.getLocation()}, War.WAR_CLASSPATH_INFO, JarCopyFilter.NOCOPY_FILTER);
         return libJar.getClassFinder();
      }
   }

   ClassFinder getClassFinder(String uri) {
      File[] roots = this.archived ? new File[0] : new File[]{this.getLocation()};
      CaseAwareExplodedJar libJar = new CaseAwareExplodedJar(uri, this.extractDir, roots, War.WAR_CLASSPATH_INFO, JarCopyFilter.NOCOPY_FILTER);

      try {
         return libJar.getClassFinder();
      } catch (IOException var5) {
         return null;
      }
   }

   public void importLibrary(J2EELibraryReference libRef, LibraryContext ctx, MultiClassFinder libraryClassFinder, MultiClassFinder instanceAppLibClassFinder, MultiClassFinder shaerdAppLibClassFinder) throws LibraryProcessingException {
      String contextRoot = libRef.getContextRoot() == null ? this.ctxRootFromLocation : libRef.getContextRoot();
      this.addWebModule(ctx, this.getLocation().getName(), contextRoot);

      try {
         ctx.registerLink(this.getLocation());
      } catch (IOException var8) {
         throw new LibraryProcessingException(var8);
      }
   }

   public LibraryReference[] getLibraryReferences() {
      return this.libraryRefs;
   }

   public void remove() throws LibraryProcessingException {
      if (this.jar != null) {
         this.jar.remove();
         this.jar = null;
      }

      super.remove();
   }

   public Collection getWebFragments() {
      ClassFinder cf = null;
      ClassFinder rf = null;

      try {
         cf = this.getClassFinder();
         rf = new War.ResourceFinder("wld#", cf);
         Set var3 = WarUtils.getWebFragments("wld", cf, rf);
         return var3;
      } catch (IOException var7) {
      } finally {
         if (cf != null) {
            cf.close();
         }

         if (rf != null) {
            rf.close();
         }

      }

      return Collections.emptySet();
   }

   private void addWebModule(LibraryContext ctx, String uri, String contextRoot) throws LibraryProcessingException {
      ApplicationBean appDD = ctx.getApplicationDD();
      WebBean webBean = appDD.createModule().createWeb();
      webBean.setWebUri(uri);
      webBean.setContextRoot(contextRoot);
      LibraryLoggingUtils.updateDescriptor(ctx.getApplicationDescriptor(), appDD);
   }

   protected void initDescriptors() throws LibraryProcessingException {
      VirtualJarFile vjf = null;

      try {
         vjf = VirtualJarFactory.createVirtualJar(this.getLocation());
         WebAppParser parser = new WebAppDescriptor(vjf);
         WebAppBean webBean = parser.getWebAppBean();
         WeblogicWebAppBean wlWebBean = parser.getWeblogicWebAppBean();
         this.jsfConfigFiles = WarUtils.getFacesConfigFiles(webBean);
         this.isAnnotationEnabled = WarUtils.isAnnotationEnabled(webBean);
         if (wlWebBean != null && wlWebBean.getContainerDescriptors() != null && wlWebBean.getContainerDescriptors().length > 0) {
            if (wlWebBean.getContainerDescriptors()[0].getPreferApplicationPackages() != null) {
               this.prefAppPackagesName = wlWebBean.getContainerDescriptors()[0].getPreferApplicationPackages().getPackageNames();
            }

            if (wlWebBean.getContainerDescriptors()[0].getPreferApplicationResources() != null) {
               this.prefAppResourcesName = wlWebBean.getContainerDescriptors()[0].getPreferApplicationResources().getResourceNames();
            }
         }

         if (wlWebBean != null) {
            this.libraryRefs = LibraryReferenceFactory.getWebLibReference(wlWebBean.getLibraryRefs());
         }
      } catch (IOException var14) {
         throw new LibraryProcessingException(var14);
      } catch (XMLStreamException var15) {
         throw new LibraryProcessingException(var15);
      } catch (IllegalSpecVersionTypeException var16) {
         throw new LibraryProcessingException(HTTPLogger.logIllegalWebLibSpecVersionRefLoggable(this.getLocation().getName(), var16.getSpecVersion()).getMessage());
      } finally {
         if (vjf != null) {
            try {
               vjf.close();
            } catch (IOException var13) {
            }
         }

      }

   }

   public void init() throws LibraryProcessingException {
      LibraryConstants.AutoReferrer[] autoRefs = this.getAutoRef();
      if (autoRefs.length > 0) {
         LibraryConstants.AutoReferrer[] var2 = autoRefs;
         int var3 = autoRefs.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            LibraryConstants.AutoReferrer autoRef = var2[var4];
            if (autoRef != AutoReferrer.WebApp) {
               throw new LibraryProcessingException("Unsupported Auto-Ref value: " + autoRef);
            }
         }
      }

      if (this.jar == null && this.archived) {
         try {
            this.jar = new ExplodedJar("wld", this.extractDir, this.getLocation(), War.WAR_CLASSPATH_INFO);
            this.setLocation(this.extractDir);
         } catch (IOException var15) {
            throw new LibraryProcessingException(var15);
         } finally {
            if (this.jar != null) {
               try {
                  this.jar.getClassFinder().close();
               } catch (IOException var13) {
               }
            }

         }
      }

      this.initDescriptors();

      try {
         this.initializeCacheEntries();
         Cache.LibMetadataCache.initCache(this);
      } catch (MetadataCacheException var14) {
         throw new LibraryProcessingException(var14);
      }
   }

   public String[] getPrefAppPackagesName() {
      return this.prefAppPackagesName;
   }

   public String[] getPrefAppResourcesName() {
      return this.prefAppResourcesName;
   }

   static class Noop extends WarLibraryDefinition {
      Noop(LibraryData d, File tmpDir) {
         super(d, tmpDir);
      }

      public void importLibrary(J2EELibraryReference libRef, LibraryContext ctx, MultiClassFinder libraryClassFinder, MultiClassFinder instanceAppLibClassFinder, MultiClassFinder shaerdAppLibClassFinder) {
      }
   }
}
