package weblogic.application.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import weblogic.application.ApplicationConstants;
import weblogic.application.SplitDirectoryInfo;
import weblogic.application.utils.IOUtils;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.ClasspathClassFinder2;
import weblogic.utils.classloaders.MultiClassFinder;
import weblogic.utils.collections.ConcurrentHashMap;
import weblogic.utils.jars.VirtualJarFactory;
import weblogic.utils.jars.VirtualJarFile;

public final class Ear {
   private final ExplodedJar archive;
   private final ClassFinder classfinder;
   private final Map uriLinks;
   private final Map virtualJars;
   private final String uri;
   private static final ClasspathInfo earInfo = new ClasspathInfo() {
      private final String[] APP_INF_CLASSES;
      private final String[] LIB_DIRS;

      {
         this.APP_INF_CLASSES = new String[]{ApplicationConstants.APP_INF_CLASSES};
         this.LIB_DIRS = new String[]{ApplicationConstants.APP_INF_LIB};
      }

      public String[] getClasspathURIs() {
         return this.APP_INF_CLASSES;
      }

      public String[] getJarURIs() {
         return this.LIB_DIRS;
      }

      public ClasspathInfo.ArchiveType getArchiveType() {
         return ClasspathInfo.ArchiveType.EAR;
      }
   };

   public Ear(String uri, File extractDir, File jarFile) throws IOException {
      this.uriLinks = new ConcurrentHashMap();
      this.virtualJars = new HashMap();
      this.archive = new ExplodedJar(uri, extractDir, jarFile, earInfo);
      this.classfinder = this.archive.getClassFinder();
      this.uri = uri;
   }

   public Ear(String uri, File extractDir, File[] dirs) throws IOException {
      this(uri, extractDir, dirs, JarCopyFilter.DEFAULT_FILTER);
   }

   public Ear(String uri, File extractDir, File[] dirs, JarCopyFilter copyFltr) throws IOException {
      this.uriLinks = new ConcurrentHashMap();
      this.virtualJars = new HashMap();
      this.archive = new ExplodedJar(uri, extractDir, dirs, earInfo, copyFltr);
      this.classfinder = this.archive.getClassFinder();
      this.uri = uri;
   }

   public Ear(String uri, File extractDir, SplitDirectoryInfo info) throws IOException {
      this(uri, extractDir, info, JarCopyFilter.DEFAULT_FILTER);
   }

   public Ear(String uri, File extractDir, SplitDirectoryInfo info, JarCopyFilter copyFilter) throws IOException {
      this(uri, extractDir, info.getRootDirectories(), copyFilter);
      this.uriLinks.putAll(info.getUriLinks());
      String extraClasspath = info.getExtraClasspath();
      if (!"".equals(extraClasspath)) {
         MultiClassFinder mcf = (MultiClassFinder)this.classfinder;
         ClassFinder extraFinder = new ClasspathClassFinder2(extraClasspath);
         mcf.addFinder(extraFinder);
         mcf.addFinder(new DescriptorFinder(uri, extraFinder));
      }

   }

   public String getURI() {
      return this.uri;
   }

   public File[] getModuleRoots(String uri) {
      Set fileList = new LinkedHashSet();
      File[] dirs = this.archive.getDirs();

      for(int i = 0; i < dirs.length; ++i) {
         File f = new File(dirs[i], uri);
         if (f.exists()) {
            fileList.add(f);
         }
      }

      List l = (List)this.uriLinks.get(uri);
      if (l != null) {
         fileList.addAll(l);
      }

      return (File[])((File[])fileList.toArray(new File[fileList.size()]));
   }

   public InputStream findScopedModuleResource(String parentUri, String uri, String path) throws IOException {
      VirtualJarFile vjf = null;
      synchronized(this.virtualJars) {
         if (this.virtualJars.containsKey(parentUri)) {
            vjf = (VirtualJarFile)this.virtualJars.get(parentUri);
         } else {
            vjf = VirtualJarFactory.createVirtualJar(this.getModuleRoots(parentUri));
            this.virtualJars.put(parentUri, vjf);
         }
      }

      if (vjf == null) {
         return null;
      } else {
         ZipEntry ze = vjf.getEntry(uri + "/" + path);
         return ze == null ? null : vjf.getInputStream(ze);
      }
   }

   public synchronized void registerLink(String uri, File f) {
      List l = (List)this.uriLinks.get(uri);
      if (l == null) {
         l = new ArrayList();
      }

      ((List)l).add(f);
      this.uriLinks.put(uri, l);
   }

   public ClassFinder getClassFinder() {
      return this.classfinder;
   }

   public void remove() {
      Iterator iter = this.virtualJars.values().iterator();

      while(iter.hasNext()) {
         IOUtils.forceClose((VirtualJarFile)iter.next());
      }

      this.classfinder.close();
      this.archive.remove();
      this.archive.removeTopLevelTempDir();
   }
}
