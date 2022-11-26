package weblogic.utils.jars;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.jar.Attributes;
import java.util.jar.Attributes.Name;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import weblogic.utils.Debug;
import weblogic.utils.io.StreamUtils;

public final class JarFileObject {
   private static final boolean ASSERT = false;
   private static final boolean VERBOSE = false;
   private static final String MANIFEST_ENTRY = "META-INF/MANIFEST.MF";
   private Manifest manifest;
   private File location;

   public JarFileObject(File location, Manifest manifest) {
      this(location);
      this.setManifest(manifest);
   }

   public JarFileObject(File location) {
      this.setLocation(location);
   }

   public Manifest getManifest() {
      return this.manifest;
   }

   public void setManifest(Manifest manifest) {
      this.manifest = manifest;
   }

   public File getLocation() {
      return this.location;
   }

   private void setLocation(File location) {
      this.location = location;
   }

   public void extract(File root) throws IOException {
      this.extract(root, (String)null);
   }

   public void extract(File root, String filter) throws IOException {
      InputStream in = new BufferedInputStream(new FileInputStream(this.location));
      ZipInputStream zip = new ZipInputStream(in);
      String rootPath = root.getPath();

      ZipEntry entry;
      try {
         while((entry = zip.getNextEntry()) != null) {
            if (filter == null || entry.getName().startsWith(filter)) {
               String path = entry.getName().replace('/', File.separatorChar);
               path = rootPath + File.separator + path;
               File file = new File(path);
               String leaf = path.substring(path.lastIndexOf(File.separatorChar) + 1);
               String dir = path.substring(0, path.lastIndexOf(File.separatorChar));
               if (leaf != null && !leaf.equals("")) {
                  OutputStream out = null;
                  File parent = new File(dir);
                  if ((!parent.exists() || !parent.isDirectory()) && !parent.mkdirs()) {
                     throw new IOException("Couldn't make directory for " + dir);
                  }

                  try {
                     if (file.exists()) {
                        boolean deleted = file.delete();
                        if (!deleted) {
                           throw new IOException("Unable to overwrite file: " + file.getAbsolutePath());
                        }
                     }

                     out = new BufferedOutputStream(new FileOutputStream(file));
                     StreamUtils.writeTo(zip, out);
                  } finally {
                     zip.closeEntry();
                     if (out != null) {
                        out.close();
                     }

                  }
               } else if (dir != null && dir.length() > 0) {
                  try {
                     File subDir = new File(dir);
                     subDir.mkdirs();
                  } finally {
                     zip.closeEntry();
                  }
               }
            }
         }
      } finally {
         zip.close();
      }

   }

   public void save() throws IOException {
      if (this.location == null) {
         throw new NullPointerException("location not set");
      } else {
         OutputStream o = new BufferedOutputStream(new FileOutputStream(this.location));
         ZipOutputStream zip = null;

         try {
            zip = new ZipOutputStream(o);
            zip.setLevel(9);
            ZipEntry entry = new ZipEntry("META-INF/MANIFEST.MF");
            entry.setMethod(8);
            zip.putNextEntry(entry);
            this.manifest.stream((OutputStream)zip);
            zip.closeEntry();
            Iterator entries = this.manifest.getEntries().values().iterator();

            while(entries.hasNext()) {
               ManifestEntry manifestEntry = (ManifestEntry)entries.next();
               InputStream in = null;

               try {
                  File f;
                  if ((f = manifestEntry.getFile()) == null || !f.getName().equals("MANIFEST.MF")) {
                     in = new FileInputStream(f);
                     entry = new ZipEntry(manifestEntry.getName());
                     zip.putNextEntry(entry);
                     StreamUtils.writeTo(in, zip);
                     zip.closeEntry();
                  }
               } finally {
                  if (in != null) {
                     in.close();
                  }

               }
            }

            zip.flush();
         } finally {
            if (zip != null) {
               zip.close();
            }

         }

      }
   }

   private static void info(String info) {
      System.out.println("<JarTool> " + info);
   }

   private static void checkDirectory(File directory) throws IOException {
      if (!directory.exists()) {
         throw new IOException("Input Directory " + directory.getAbsolutePath() + " does not exist.");
      } else if (!directory.canRead()) {
         throw new IOException("Input Directory " + directory.getAbsolutePath() + " can not be read.");
      }
   }

   private static void addFilesToManifest(Manifest manifest, File root, File dir) {
      String[] fileNames = dir.list();
      String path = dir.getAbsolutePath();

      for(int i = 0; i < fileNames.length; ++i) {
         File f = new File(path + File.separator + fileNames[i]);
         if (f.isDirectory()) {
            addFilesToManifest(manifest, root, f);
         } else {
            ManifestEntry entry = new ManifestEntry(root, f);
            manifest.addEntry(entry);
         }
      }

   }

   public static JarFileObject makeJar(String jarPath, File directory) throws IOException {
      Debug.assertion(directory != null);
      checkDirectory(directory);
      File jarFile = new File(jarPath);
      Manifest mft = new Manifest();
      String mPath = directory + File.separator + "META-INF/MANIFEST.MF";
      File mFile = new File(mPath);
      if (mFile.exists()) {
         FileInputStream mFilein = new FileInputStream(mFile);
         java.util.jar.Manifest m = new java.util.jar.Manifest(mFilein);
         mFilein.close();
         Attributes mAttributes = m.getMainAttributes();
         if (mAttributes != null) {
            String mftCP = (String)mAttributes.get(Name.CLASS_PATH);
            if (mftCP != null) {
               mft.getHeaders().addHeader(Name.CLASS_PATH.toString(), mftCP);
            }
         }
      }

      addFilesToManifest(mft, directory, directory);
      return new JarFileObject(jarFile, mft);
   }
}
