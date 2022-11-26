package weblogic.servlet.internal;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.jar.Attributes.Name;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
import weblogic.application.io.ClasspathInfo;
import weblogic.application.io.DescriptorFinder;
import weblogic.application.io.ExplodedJar;
import weblogic.application.io.JarCopyFilter;
import weblogic.utils.FileUtils;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.ClasspathClassFinder2;
import weblogic.utils.classloaders.MultiClassFinder;
import weblogic.utils.classloaders.Source;
import weblogic.utils.classloaders.ZipClassFinder;
import weblogic.utils.classloaders.ZipSource;
import weblogic.utils.collections.FilteringIterator;
import weblogic.utils.enumerations.EmptyEnumerator;
import weblogic.utils.io.StreamUtils;
import weblogic.utils.jars.JarFileUtils;
import weblogic.utils.jars.VirtualJarFactory;
import weblogic.utils.jars.VirtualJarFile;

public class ArchivedWar extends ExplodedJar {
   public static final String TMP_CLASSES_JAR = "_wl_cls_gen.jar";
   private static final String WEB_INF_LIB;
   private static final String MANIFEST_MF = "META-INF/MANIFEST.MF";
   private final boolean foundClasses;
   private final File classesJar;
   private final FileFilter jarFileFilter;

   public ArchivedWar(String uri, File extractDir, File jarFile, ClasspathInfo info) throws IOException {
      super(uri, extractWarFile(extractDir, jarFile), new File[0], info, JarCopyFilter.NOCOPY_FILTER);
      this.classesJar = new File(extractDir, WEB_INF_LIB + File.separator + "_wl_cls_gen.jar");
      this.foundClasses = this.classesJar.exists();
      this.jarFileFilter = new ExplodedJar.JarFileFilter(this.classesJar);
   }

   public ClassFinder getClassFinder() throws IOException {
      ClassFinder cf = super.getClassFinder();
      if (this.foundClasses) {
         MultiClassFinder finder = new MultiClassFinder(new ClasspathClassFinder2(this.classesJar.getAbsolutePath()));
         finder.addFinder(cf);
         finder.addFinder(new DescriptorFinder(this.getURI(), new WebInfClassesFinder(this.classesJar)));
         return finder;
      } else {
         return cf;
      }
   }

   protected ClassFinder buildDescriptorFinder() {
      StringBuffer cp = new StringBuffer();

      for(int i = 0; i < this.dirs.length; ++i) {
         this.addClasspath(cp, this.dirs[i]);
      }

      return new DescriptorFinder(this.uri, new CaseAwareClasspathClassFinder(cp.toString()));
   }

   protected FileFilter getJarFileFilter() {
      return this.jarFileFilter;
   }

   private static ExplodedJar.ExtractDirectory extractWarFile(File dir, File jarFile) throws IOException {
      boolean isReExtract = false;
      if (dir.exists()) {
         if (extractionUpToDate(dir, jarFile)) {
            return new ExplodedJar.ExtractDirectory(dir, isReExtract);
         }

         FileUtils.remove(dir);
         isReExtract = true;
      }

      dir.mkdirs();
      JarFile jf = null;

      try {
         jf = new JarFile(jarFile);
         expandWarFileIntoDirectory(jf, dir);
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
      return new ExplodedJar.ExtractDirectory(dir, isReExtract);
   }

   static void expandWarFileIntoDirectory(JarFile jf, File dir) throws IOException {
      VirtualJarFile vjf = null;

      try {
         vjf = VirtualJarFactory.createVirtualJar(jf);
         boolean foundClasses = extractClasses((VirtualJarFile)vjf, dir);
         if (foundClasses) {
            vjf = new NoClassVirtualJarFile((VirtualJarFile)vjf);
         }

         JarFileUtils.extract((VirtualJarFile)vjf, dir);
      } finally {
         if (vjf != null) {
            try {
               ((VirtualJarFile)vjf).close();
            } catch (IOException var9) {
            }
         }

      }

   }

   private static boolean extractClasses(VirtualJarFile vjf, File extractDir) throws IOException {
      boolean foundClasses = false;
      Iterator i = vjf.getEntries("WEB-INF/classes/");
      int len = "WEB-INF/classes/".length();
      ZipOutputStream zos = null;
      Set entries = new HashSet();

      try {
         while(i.hasNext()) {
            ZipEntry entry = (ZipEntry)i.next();
            String entryName = entry.getName();
            String name = entryName.substring(len);
            if (name != null && !name.trim().equals("")) {
               if (zos == null) {
                  zos = initZipOutputStream(extractDir);
               }

               InputStream is = vjf.getInputStream(entry);
               foundClasses = true;
               writeZipEntry(name, is, zos, entries);
            }
         }

         if (zos != null && !entries.contains("META-INF/MANIFEST.MF")) {
            writeZipEntry("META-INF/MANIFEST.MF", createManifestStream(), zos, entries);
         }
      } finally {
         if (zos != null) {
            try {
               zos.close();
            } catch (IOException var16) {
            }
         }

      }

      return foundClasses;
   }

   private static ZipOutputStream initZipOutputStream(File extractDir) throws IOException {
      File dir = new File(extractDir, "WEB-INF" + File.separator + "lib");
      if (!dir.exists()) {
         dir.mkdirs();
      }

      File jar = new File(dir, "_wl_cls_gen.jar");
      FileOutputStream fos = new FileOutputStream(jar);
      return new ZipOutputStream(new BufferedOutputStream(fos));
   }

   private static void writeZipEntry(String name, InputStream is, ZipOutputStream zos, Set entries) throws IOException {
      if (name.startsWith("/")) {
         name = name.substring(1);
      }

      if (!entries.contains(name) && !name.toLowerCase().endsWith(".jsp") && !name.toLowerCase().endsWith(".jspx")) {
         zos.putNextEntry(new ZipEntry(name));
         entries.add(name);
         StreamUtils.writeTo(is, zos);
         zos.closeEntry();
      }

      is.close();
      createSubEntries(name, zos, entries);
   }

   private static void createSubEntries(String name, ZipOutputStream zos, Set entries) throws IOException {
      if (name.endsWith("/")) {
         name = name.substring(0, name.length() - 2);
      }

      while(true) {
         int slash = name.lastIndexOf("/");
         if (slash == -1) {
            return;
         }

         name = name.substring(0, slash + 1);
         if (!entries.contains(name)) {
            zos.putNextEntry(new ZipEntry(name));
            zos.closeEntry();
            entries.add(name);
         }

         name = name.substring(0, name.length() - 2);
      }
   }

   private static InputStream createManifestStream() throws IOException {
      Manifest mf = new Manifest();
      Attributes attrs = mf.getMainAttributes();
      attrs.put(Name.MANIFEST_VERSION, "1.0");
      ByteArrayOutputStream bout = new ByteArrayOutputStream();
      mf.write(bout);
      bout.close();
      return new ByteArrayInputStream(bout.toByteArray());
   }

   static {
      WEB_INF_LIB = "WEB-INF" + File.separator + "lib";
   }

   private static class WebInfClassesFinder extends ZipClassFinder {
      private static final String PREFIX = "WEB-INF/classes";

      private WebInfClassesFinder(File f) throws IOException {
         super((ZipFile)(f.getName().endsWith(".jar") ? new JarFile(f) : new ZipFile(f)));
      }

      public Source getSource(String name) {
         if (name == null) {
            return null;
         } else {
            if (name.startsWith("/")) {
               name = name.substring(1);
            }

            if (!name.startsWith("WEB-INF/classes")) {
               return null;
            } else {
               name = name.substring("WEB-INF/classes".length());
               if (name.startsWith("/")) {
                  name = name.substring(1);
               }

               return (Source)(name.equals("") ? new ZipSource(this.getZipFile(), new ZipEntry("")) : super.getSource(name));
            }
         }
      }

      public Enumeration getSources(String name) {
         return (Enumeration)(name == null ? new EmptyEnumerator() : super.getSources(name));
      }

      public Source getClassSource(String name) {
         if (name == null) {
            return null;
         } else {
            if (name.startsWith("/")) {
               name = name.substring(1);
            }

            if (!name.startsWith("WEB-INF/classes")) {
               return null;
            } else {
               name = name.substring("WEB-INF/classes".length());
               if (name.startsWith("/")) {
                  name = name.substring(1);
               }

               return super.getClassSource(name);
            }
         }
      }

      // $FF: synthetic method
      WebInfClassesFinder(File x0, Object x1) throws IOException {
         this(x0);
      }
   }

   private static class NoClassVirtualJarFile implements VirtualJarFile {
      private static final String PREFIX = "WEB-INF/classes";
      private static final String JSP_SURFIX = ".jsp";
      private static final String JSPX_SURFIX = ".jspx";
      private VirtualJarFile vjf;

      NoClassVirtualJarFile(VirtualJarFile jf) {
         this.vjf = jf;
      }

      public String getName() {
         return this.vjf.getName();
      }

      public void close() throws IOException {
         this.vjf.close();
      }

      public Iterator entries() {
         return new FilteringIterator(this.vjf.entries()) {
            public boolean accept(Object o) {
               ZipEntry ze = (ZipEntry)o;
               String name = ze.getName();
               if (name.endsWith("WEB-INF/classes/") && ze.isDirectory()) {
                  return true;
               } else {
                  return !name.startsWith("WEB-INF/classes") || name.toLowerCase().endsWith(".jsp") || name.toLowerCase().endsWith(".jspx");
               }
            }
         };
      }

      public URL getResource(String name) {
         return this.vjf.getResource(name);
      }

      public ZipEntry getEntry(String name) {
         return this.vjf.getEntry(name);
      }

      public Iterator getEntries(String uri) throws IOException {
         return this.vjf.getEntries(uri);
      }

      public InputStream getInputStream(ZipEntry ze) throws IOException {
         return this.vjf.getInputStream(ze);
      }

      public Manifest getManifest() throws IOException {
         return this.vjf.getManifest();
      }

      public File[] getRootFiles() {
         return this.vjf.getRootFiles();
      }

      public boolean isDirectory() {
         return this.vjf.isDirectory();
      }

      public JarFile getJarFile() {
         return this.vjf.getJarFile();
      }

      public File getDirectory() {
         return this.vjf.getDirectory();
      }
   }
}
