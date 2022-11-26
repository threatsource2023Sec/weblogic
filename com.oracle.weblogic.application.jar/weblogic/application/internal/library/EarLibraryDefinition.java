package weblogic.application.internal.library;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.xml.stream.XMLStreamException;
import weblogic.application.ApplicationDescriptor;
import weblogic.application.Type;
import weblogic.application.internal.Controls;
import weblogic.application.internal.classloading.ShareabilityException;
import weblogic.application.internal.classloading.ShareabilityManager;
import weblogic.application.io.DescriptorFinder;
import weblogic.application.io.Ear;
import weblogic.application.io.ManifestFinder;
import weblogic.application.library.ApplicationLibrary;
import weblogic.application.library.J2EELibraryReference;
import weblogic.application.library.Library;
import weblogic.application.library.LibraryContext;
import weblogic.application.library.LibraryData;
import weblogic.application.library.LibraryDefinition;
import weblogic.application.library.LibraryProcessingException;
import weblogic.application.library.LibraryReference;
import weblogic.application.metadatacache.Cache;
import weblogic.application.metadatacache.ClassInfoFinderMetadataEntry;
import weblogic.application.metadatacache.MetadataCacheException;
import weblogic.application.metadatacache.MetadataEntry;
import weblogic.application.metadatacache.MetadataType;
import weblogic.application.utils.EarUtils;
import weblogic.application.utils.IOUtils;
import weblogic.application.utils.LibraryLoggingUtils;
import weblogic.application.utils.LibraryUtils;
import weblogic.application.utils.annotation.ClassInfoFinder;
import weblogic.j2ee.descriptor.ApplicationBean;
import weblogic.j2ee.descriptor.ModuleBean;
import weblogic.j2ee.descriptor.wl.CustomModuleBean;
import weblogic.j2ee.descriptor.wl.PreferApplicationPackagesBean;
import weblogic.j2ee.descriptor.wl.ShareableBean;
import weblogic.j2ee.descriptor.wl.WeblogicApplicationBean;
import weblogic.j2ee.descriptor.wl.WeblogicExtensionBean;
import weblogic.j2ee.descriptor.wl.WeblogicModuleBean;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.ClasspathClassFinder2;
import weblogic.utils.classloaders.MultiClassFinder;
import weblogic.utils.jars.VirtualJarFactory;
import weblogic.utils.jars.VirtualJarFile;

public class EarLibraryDefinition extends LibraryDefinition implements Library, ApplicationLibrary {
   private final File extractDir;
   private Ear ear = null;
   private VirtualJarFile vjf = null;
   private ApplicationDescriptor parser = null;
   private J2EELibraryReference[] libraryRefs = null;
   private boolean inited = false;
   private String earClassFinderClassPath = null;
   private ShareabilityManager shareabilityManager = null;
   private String sharedAppFindersClassPath = null;
   private String instanceAppFindersClassPath = null;
   private Map cacheEntries;
   private ClassInfoFinderMetadataEntry classInfoFinderEntry;
   private List filters;

   public EarLibraryDefinition(LibraryData d, File extractDir) {
      super(d, Type.EAR);
      this.filters = Collections.EMPTY_LIST;
      this.extractDir = extractDir;
   }

   public void init() throws LibraryProcessingException {
      if (!this.inited) {
         if (this.getAutoRef().length > 0) {
            throw new LibraryProcessingException("ear libraries may not be auto-ref");
         } else {
            try {
               if (this.isArchive(this.getLocation())) {
                  this.ear = new Ear(this.getName(), this.extractDir, this.getLocation());
                  this.setLocation(this.extractDir);
                  if (LibraryUtils.isDebugOn()) {
                     LibraryUtils.debug("Extracted ear into:" + this.extractDir.getAbsolutePath());
                  }
               } else {
                  this.ear = new Ear(this.getName(), this.extractDir, new File[]{this.getLocation()});
               }

               this.earClassFinderClassPath = this.ear.getClassFinder().getClassPath();
               WeblogicApplicationBean wlAppBean = this.initDescriptors();
               ShareableBean[] sBeans = null;
               if (wlAppBean != null) {
                  sBeans = wlAppBean.getClassLoading().getShareables();
               }

               this.shareabilityManager = new ShareabilityManager(sBeans);
               MultiClassFinder sharedAppFinders = new MultiClassFinder();
               MultiClassFinder instanceAppFinders = new ClasspathClassFinder2(this.earClassFinderClassPath);
               this.shareabilityManager.extractShareableFinders(instanceAppFinders, sharedAppFinders);
               if (sharedAppFinders.size() > 0) {
                  this.sharedAppFindersClassPath = sharedAppFinders.getClassPath();
                  this.instanceAppFindersClassPath = instanceAppFinders.getClassPath();
               } else {
                  this.instanceAppFindersClassPath = this.earClassFinderClassPath;
               }
            } catch (IOException var6) {
               throw new LibraryProcessingException(var6);
            } catch (ShareabilityException var7) {
               throw new LibraryProcessingException(var7);
            }

            if (Controls.annoscancache.enabled) {
               this.cacheEntries = new HashMap();
               this.classInfoFinderEntry = new ClassInfoFinderMetadataEntry(this.getLibData().getLocation(), this.extractDir, this.earClassFinderClassPath);
               this.cacheEntries.put(MetadataType.CLASSLEVEL_INFOS, this.classInfoFinderEntry);

               try {
                  Cache.LibMetadataCache.initCache(this);
               } catch (MetadataCacheException var5) {
                  throw new LibraryProcessingException(var5);
               }
            }

            this.inited = true;
         }
      }
   }

   private WeblogicApplicationBean initDescriptors() throws IOException, LibraryProcessingException {
      WeblogicApplicationBean dd = null;

      try {
         this.vjf = VirtualJarFactory.createVirtualJar(this.getLocation());
         this.parser = new ApplicationDescriptor(this.vjf);
         dd = this.parser.getWeblogicApplicationDescriptor();
         if (dd != null) {
            this.libraryRefs = LibraryLoggingUtils.initLibRefs(dd.getLibraryRefs());
            PreferApplicationPackagesBean bean = dd.getPreferApplicationPackages();
            if (bean != null) {
               String[] packagesList = bean.getPackageNames();
               this.filters = validatePackages(packagesList);
            }
         }

         return dd;
      } catch (XMLStreamException var4) {
         throw new LibraryProcessingException(var4);
      }
   }

   private boolean isArchive(File f) {
      return f.isFile();
   }

   public void cleanup() {
      IOUtils.forceClose(this.vjf);
      if (this.ear != null) {
         this.ear.getClassFinder().close();
      }

   }

   public void remove() {
      if (this.ear != null) {
         if (LibraryUtils.isDebugOn()) {
            LibraryUtils.debug("Calling remove on ear library");
         }

         this.ear.remove();
      }

   }

   public void importLibrary(J2EELibraryReference libRef, LibraryContext ctx, MultiClassFinder libraryClassFinder, MultiClassFinder instanceAppLibClassFinder, MultiClassFinder shaerdAppLibClassFinder) throws LibraryProcessingException {
      LibraryLoggingUtils.checkNoContextRootSet(libRef, Type.EAR);
      ModuleBean[] appModules = null;
      WeblogicModuleBean[] wlModules = null;
      CustomModuleBean[] customModules = null;

      try {
         ApplicationBean libAppDD = this.parser.getApplicationDescriptor();
         if (libAppDD != null) {
            appModules = libAppDD.getModules();
         }

         WeblogicApplicationBean libWLAppDD = this.parser.getWeblogicApplicationDescriptor();
         if (libWLAppDD != null) {
            wlModules = libWLAppDD.getModules();
         }

         WeblogicExtensionBean libExtDD = this.parser.getWeblogicExtensionDescriptor();
         if (libExtDD != null) {
            customModules = libExtDD.getCustomModules();
         }

         ApplicationDescriptor appDescriptor = ctx.getApplicationDescriptor();
         LibraryLoggingUtils.mergeDescriptors(appDescriptor, this.vjf);
         ctx.notifyDescriptorUpdate();
      } catch (IOException var17) {
         LibraryLoggingUtils.errorMerging(var17);
      } catch (XMLStreamException var18) {
         LibraryLoggingUtils.errorMerging(var18);
      }

      int i;
      String path;
      if (appModules != null) {
         for(i = 0; i < appModules.length; ++i) {
            try {
               path = EarUtils.reallyGetModuleURI(appModules[i]);
               ctx.registerLink(path, new File(this.getLocation(), path));
            } catch (IOException var16) {
               throw new LibraryProcessingException(var16);
            }
         }
      }

      if (wlModules != null) {
         for(i = 0; i < wlModules.length; ++i) {
            try {
               path = wlModules[i].getPath();
               File f = new File(this.getLocation(), path);
               if (f.isDirectory()) {
                  ctx.registerLink(f.getName(), f);
               } else {
                  ctx.registerLink(path, f);
               }
            } catch (IOException var15) {
               throw new LibraryProcessingException(var15);
            }
         }
      }

      if (customModules != null) {
         for(i = 0; i < customModules.length; ++i) {
            try {
               File f = new File(this.getLocation(), customModules[i].getUri());
               if (f.exists()) {
                  ctx.registerLink(f);
               } else {
                  ctx.registerLink(customModules[i].getUri(), this.getLocation());
               }
            } catch (IOException var14) {
               throw new LibraryProcessingException(var14);
            }
         }
      }

      libraryClassFinder.addFinderFirst(new DescriptorFinder(ctx.getRefappUri(), new ClasspathClassFinder2(this.getLocation().getAbsolutePath())));
      instanceAppLibClassFinder.addFinderFirst(new DescriptorFinder(ctx.getRefappUri(), new ClasspathClassFinder2(this.getLocation().getAbsolutePath())));
      libraryClassFinder.addFinderFirst(this.getClassFinder(this.earClassFinderClassPath));
      instanceAppLibClassFinder.addFinderFirst(this.getClassFinder(this.instanceAppFindersClassPath));
      if (this.sharedAppFindersClassPath != null) {
         shaerdAppLibClassFinder.addFinderFirst(new ClasspathClassFinder2(this.sharedAppFindersClassPath));
      }

      if (Controls.annoscancache.enabled) {
         try {
            ctx.addLibraryClassInfoFinderFirst((ClassInfoFinder)Cache.LibMetadataCache.lookupCachedObject(this.classInfoFinderEntry));
         } catch (MetadataCacheException var13) {
            throw new LibraryProcessingException(var13);
         }
      }

   }

   public LibraryReference[] getLibraryReferences() {
      return this.libraryRefs;
   }

   private ClassFinder getClassFinder(String earClassPath) {
      MultiClassFinder mcf = new MultiClassFinder();
      mcf.addFinder(new ManifestFinder.ExtensionListFinder(this.getLocation()));
      mcf.addFinder(new ClasspathClassFinder2(earClassPath));
      return mcf;
   }

   public List getFilters() {
      return this.filters;
   }

   private static List validatePackages(String[] packagesList) {
      if (packagesList != null && packagesList.length != 0) {
         List pkgs = Arrays.asList(packagesList);
         List ret = new ArrayList(pkgs.size());

         String pkg;
         for(Iterator i = pkgs.iterator(); i.hasNext(); ret.add(pkg)) {
            pkg = (String)i.next();
            if (pkg.endsWith("*")) {
               pkg = pkg.substring(0, pkg.length() - 1);
            }

            if (!pkg.endsWith(".")) {
               pkg = pkg + ".";
            }
         }

         return ret;
      } else {
         return Collections.EMPTY_LIST;
      }
   }

   public MetadataEntry[] findAllCachableEntry() {
      return (MetadataEntry[])this.cacheEntries.values().toArray(new MetadataEntry[this.cacheEntries.size()]);
   }

   public MetadataEntry getCachableEntry(MetadataType type) {
      return (MetadataEntry)this.cacheEntries.get(type);
   }
}
