package weblogic.application.io;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.jar.JarFile;
import weblogic.utils.FileUtils;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.ClasspathClassFinder2;
import weblogic.utils.classloaders.DelegateFinder;
import weblogic.utils.classloaders.MultiClassFinder;
import weblogic.utils.classloaders.Source;
import weblogic.utils.enumerations.EmptyEnumerator;
import weblogic.utils.jars.JarFileUtils;

public class ExplodedJar extends Archive {
   private static final FileFilter JAR_FILTER = new JarFileFilter((File)null);
   private final File extractDir;
   private final ClasspathInfo info;
   private final JarCopyFilter jarCopyFilter;
   protected final File[] dirs;
   protected final String uri;
   private static final String META_INF_RESOURCES_PREFIX = "/META-INF/resources";
   private boolean isReExtract;
   protected static final String MARKER_FILE = ".beamarker.dat";

   public ExplodedJar(String uri, File extractDir, File jarFile, ClasspathInfo info) throws IOException {
      this(uri, extractJarFile(extractDir, jarFile), new File[0], info, JarCopyFilter.NOCOPY_FILTER);
   }

   public ExplodedJar(String uri, File extractDir, File[] userDirs, ClasspathInfo info, JarCopyFilter jarCopyFilter) {
      this.isReExtract = false;
      this.extractDir = extractDir;
      this.info = info;
      this.uri = uri;
      this.jarCopyFilter = jarCopyFilter;
      this.dirs = new File[userDirs.length + 1];
      this.dirs[0] = extractDir;
      System.arraycopy(userDirs, 0, this.dirs, 1, userDirs.length);
   }

   public ExplodedJar(String uri, ExtractDirectory extractDir, File[] userDirs, ClasspathInfo info, JarCopyFilter jarCopyFilter) {
      this(uri, extractDir.getExtractDir(), userDirs, info, jarCopyFilter);
      this.isReExtract = extractDir.isReExtract();
   }

   protected String getURI() {
      return this.uri;
   }

   public File[] getDirs() {
      return this.dirs;
   }

   public ClassFinder getClassFinder() throws IOException {
      MultiClassFinder mcf = new MultiClassFinder();
      ClassFinder classpathFinder = this.buildClasspathFinder();
      mcf.addFinder(classpathFinder);
      mcf.addFinder(this.buildDescriptorFinder());
      if (this.info.getArchiveType() == ClasspathInfo.ArchiveType.WAR && classpathFinder.getSource("/META-INF/resources") != null) {
         mcf.addFinder(this.buildMetaInfResourcesFinder(classpathFinder));
      }

      this.addManifestFinders(mcf);
      return mcf;
   }

   private ClassFinder buildMetaInfResourcesFinder(ClassFinder libsFinder) {
      ClassFinder delegateFinder = new DelegateFinder(libsFinder) {
         public Source getSource(String name) {
            return name == null ? null : super.getSource("/META-INF/resources" + name);
         }

         public Enumeration getSources(String name) {
            return (Enumeration)(name == null ? new EmptyEnumerator() : super.getSources("/META-INF/resources" + name));
         }
      };
      return new DescriptorFinder(this.uri, delegateFinder);
   }

   protected ClassFinder buildDescriptorFinder() {
      StringBuffer cp = new StringBuffer();

      for(int i = 0; i < this.dirs.length; ++i) {
         this.addClasspath(cp, this.dirs[i]);
      }

      return new DescriptorFinder(this.uri, new ClasspathClassFinder2(cp.toString()));
   }

   protected FileFilter getJarFileFilter() {
      return JAR_FILTER;
   }

   private void addManifestFinders(MultiClassFinder mcf) throws IOException {
      for(int i = 0; i < this.dirs.length; ++i) {
         mcf.addFinder(new ManifestFinder.ExtensionListFinder(this.dirs[i]));
      }

   }

   private ClassFinder buildClasspathFinder() throws IOException {
      StringBuffer cp = new StringBuffer();
      String[] cpURIs = this.info.getClasspathURIs();

      for(int i = 0; i < cpURIs.length; ++i) {
         this.addClasspathURI(cp, cpURIs[i]);
      }

      String[] jarURIs = this.info.getJarURIs();

      for(int i = 0; i < jarURIs.length; ++i) {
         this.addJarURI(cp, jarURIs[i]);
      }

      return new ClasspathClassFinder2(cp.toString());
   }

   private void addClasspathURI(StringBuffer cp, String uri) {
      for(int i = 0; i < this.dirs.length; ++i) {
         this.addClasspath(cp, new File(this.dirs[i], uri));
      }

   }

   private void addJarURI(StringBuffer cp, String jarUri) throws IOException {
      File jarDest = new File(this.extractDir, jarUri);
      StringBuffer tmpCp = new StringBuffer();
      Set emptySet = Collections.emptySet();
      Set cachedJars = emptySet;
      if (this.dirs.length > 1 && jarDest.exists() && jarDest.isDirectory()) {
         cachedJars = new HashSet(Arrays.asList(jarDest.listFiles(this.getJarFileFilter())));
      }

      for(int i = 1; i < this.dirs.length; ++i) {
         this.addJars(tmpCp, this.dirs[i], jarUri, jarDest, (Set)cachedJars);
      }

      Iterator it = ((Set)cachedJars).iterator();

      while(it.hasNext()) {
         FileUtils.remove((File)it.next());
      }

      this.addJars(cp, this.dirs[0], jarUri, jarDest, emptySet);
      cp.append(File.pathSeparator).append(tmpCp);
   }

   private void addJars(StringBuffer cp, File root, String jarUri, File jarDest, Set cachedJars) throws IOException {
      File jarDir = new File(root, jarUri);
      if (jarDir.exists() && jarDir.isDirectory()) {
         File[] jf = jarDir.listFiles(this.getJarFileFilter());
         if (jf != null && jf.length != 0) {
            for(int i = 0; i < jf.length; ++i) {
               this.addAndMaybeCopy(cp, jf[i], root, jarDest, cachedJars);
            }

         }
      }
   }

   private void addAndMaybeCopy(StringBuffer cp, File jar, File root, File dest, Set cachedJars) throws IOException {
      if (!this.jarCopyFilter.copyJars()) {
         this.addClasspath(cp, jar);
      } else {
         this.handleManifestReferences(cp, jar, root);
         File toFile = new File(dest, jar.getName());
         if (cachedJars.contains(toFile)) {
            cachedJars.remove(toFile);
         }

         this.copyFile(jar, toFile);
         this.addClasspath(cp, toFile);
      }
   }

   private void copyFile(File f, File toFile) throws IOException {
      if (!toFile.exists() || f.lastModified() > toFile.lastModified()) {
         FileUtils.copyPreservePermissions(f, toFile);
         toFile.setLastModified(f.lastModified());
      }

   }

   private void handleManifestReferences(StringBuffer cp, File jar, File root) throws IOException {
      File[] pathElements = ManifestHelper.getExistingMFClassPathElements(jar);
      URI rootURI = root.toURI();

      for(int i = 0; i < pathElements.length; ++i) {
         URI refJarURI = pathElements[i].toURI();
         URI relativeURI = rootURI.relativize(refJarURI);
         if (relativeURI != refJarURI) {
            if (pathElements[i].isFile()) {
               if (!pathElements[i].getParentFile().equals(jar.getParentFile())) {
                  File toFile = new File(this.extractDir, relativeURI.toString());
                  this.copyFile(pathElements[i], toFile);
               }
            } else if (pathElements[i].isDirectory()) {
               this.addClasspath(cp, pathElements[i]);
            }
         }
      }

   }

   protected void addClasspath(StringBuffer cp, File f) {
      this.addClasspath(cp, f.getAbsolutePath());
   }

   private void addClasspath(StringBuffer cp, String path) {
      if (cp.length() > 0) {
         cp.append(File.pathSeparator);
      }

      cp.append(path);
   }

   public void remove() {
      if (this.extractDir.exists()) {
         FileUtils.remove(this.extractDir);
      }

   }

   public void removeTopLevelTempDir() {
      File parent = this.extractDir.getParentFile();
      if (parent.exists() && this.isEmptyDir(parent)) {
         FileUtils.remove(parent);
      }

   }

   private boolean isEmptyDir(File dir) {
      String[] files = dir.list();
      return files == null || files.length <= 0;
   }

   protected static boolean extractionUpToDate(File dir, File jf) {
      File marker = new File(dir, ".beamarker.dat");
      return marker.exists() && marker.lastModified() == jf.lastModified() ? isExtractedFileSizeSame(jf, marker) : false;
   }

   private static boolean isExtractedFileSizeSame(File jf, File marker) {
      try {
         DataInputStream fis = new DataInputStream(new FileInputStream(marker));
         Throwable var3 = null;

         boolean var4;
         try {
            var4 = fis.readLong() == jf.length();
         } catch (Throwable var14) {
            var3 = var14;
            throw var14;
         } finally {
            if (fis != null) {
               if (var3 != null) {
                  try {
                     fis.close();
                  } catch (Throwable var13) {
                     var3.addSuppressed(var13);
                  }
               } else {
                  fis.close();
               }
            }

         }

         return var4;
      } catch (IOException var16) {
         return true;
      }
   }

   private static File extractJarFile(File dir, File jarFile) throws IOException {
      if (dir.exists()) {
         if (extractionUpToDate(dir, jarFile)) {
            return dir;
         }

         FileUtils.remove(dir);
      }

      dir.mkdirs();
      JarFile jf = null;

      try {
         jf = new JarFile(jarFile);
         JarFileUtils.extract(jf, dir);
      } finally {
         if (jf != null) {
            jf.close();
         }

      }

      File marker = new File(dir, ".beamarker.dat");
      DataOutputStream fos = new DataOutputStream(new FileOutputStream(marker));

      try {
         fos.writeLong(jarFile.length());
      } finally {
         fos.close();
      }

      marker.setLastModified(jarFile.lastModified());
      return dir;
   }

   public boolean isReExtract() {
      return this.isReExtract;
   }

   public static class ExtractDirectory {
      private final File extractDir;
      private final boolean isReExtract;

      public ExtractDirectory(File extractDir, boolean isReExtract) {
         this.extractDir = extractDir;
         this.isReExtract = isReExtract;
      }

      public File getExtractDir() {
         return this.extractDir;
      }

      public boolean isReExtract() {
         return this.isReExtract;
      }
   }

   protected static class JarFileFilter implements FileFilter {
      private File excludeFile;

      public JarFileFilter(File exclude) {
         this.excludeFile = exclude;
      }

      public boolean accept(File f) {
         if ((f.isFile() || f.isDirectory()) && f.getName().endsWith(".jar")) {
            return this.excludeFile == null || !this.excludeFile.equals(f);
         } else {
            return false;
         }
      }
   }
}
