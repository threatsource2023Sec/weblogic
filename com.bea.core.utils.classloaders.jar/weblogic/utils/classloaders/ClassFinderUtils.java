package weblogic.utils.classloaders;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import java.util.jar.Attributes.Name;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import weblogic.utils.OptionalPackageProvider;
import weblogic.utils.StringUtils;
import weblogic.utils.classloaders.debug.ClassLoaderDebugger;

public class ClassFinderUtils {
   private static final String MANIFEST_ENTRY = "META-INF/MANIFEST.MF";
   private static final String SHAREABLE_MANIFEST_ATTR_NAME = "WebLogic-Shareable";
   private static final String BEA_HOME_PLACEHOLDER = "@BEA_HOME@";

   private ClassFinderUtils() {
   }

   public static Manifest getManifest(File dir) throws IOException {
      File manFile = new File(dir, "META-INF/MANIFEST.MF");
      if (!manFile.exists()) {
         return null;
      } else {
         InputStream manFileStream = new FileInputStream(manFile);

         Manifest manifest;
         try {
            manifest = new Manifest(manFileStream);
         } finally {
            manFileStream.close();
         }

         return manifest;
      }
   }

   public static ClassFinder getManifestFinder(File dir, Set exclude) throws IOException {
      return getManifestFinder((File)dir, (Set)exclude, (Attr)null);
   }

   public static ClassFinder getManifestFinder(File dir, Set exclude, Attr attr) throws IOException {
      return getManifestFinder(getManifest(dir), dir.getPath(), exclude, attr);
   }

   public static ClassFinder getManifestFinder(Manifest manifest, String path, Set exclude) throws IOException {
      return getManifestFinder(manifest, path, exclude, (Attr)null);
   }

   public static ClassFinder getManifestFinder(Manifest manifest, String path, Set exclude, Attr attr) throws IOException {
      if (manifest != null) {
         String mftCP = getManifestClassPath(manifest, path, attr);
         if (mftCP != null) {
            exclude.add(path);
            return new ClasspathClassFinder2.ExistingScanningLimits(mftCP, exclude);
         }
      }

      return null;
   }

   public static Manifest getManifest(ZipFile zipFile) throws IOException {
      ZipEntry mftEntry = zipFile.getEntry("META-INF/MANIFEST.MF");
      Manifest mft = null;
      InputStream manFileStream = null;
      if (mftEntry != null) {
         try {
            manFileStream = zipFile.getInputStream(mftEntry);
            mft = new Manifest(manFileStream);
         } catch (IOException var6) {
            IOException newIOE = new IOException(var6.getMessage() + " while processing Manifest entry of Zip File: " + zipFile.getName());
            throw newIOE;
         } catch (RuntimeException var7) {
            RuntimeException newRE = new RuntimeException(var7.getMessage() + " while processing Manifest entry of Zip File: " + zipFile.getName(), var7.getCause());
            throw newRE;
         }
      }

      if (manFileStream != null) {
         manFileStream.close();
      }

      return mft;
   }

   public static ClassFinder getManifestFinder(ZipFile zipFile, Set exclude) throws IOException {
      return getManifestFinder((ZipFile)zipFile, (Set)exclude, (Attr)null);
   }

   public static ClassFinder getManifestFinder(ZipFile zipFile, Set exclude, Attr attr) throws IOException {
      return getManifestFinder(getManifest(zipFile), zipFile.getName(), exclude, attr);
   }

   private static String getManifestClassPath(Manifest mft, String fName, Attr attr) {
      if (mft != null) {
         Attributes mainAttributes = mft.getMainAttributes();
         if (mainAttributes != null) {
            StringBuffer ret = null;
            if (attr == null || attr == ClassFinderUtils.Attr.CLASS_PATH) {
               String cpAttr = (String)mainAttributes.get(Name.CLASS_PATH);
               if (cpAttr != null) {
                  ret = convertManifestClassPath(fName, cpAttr);
               }
            }

            if (attr == null || attr == ClassFinderUtils.Attr.EXTENSION_LIST) {
               StringBuffer optPackCP = getOptionalPackages(fName, mainAttributes);
               if (optPackCP != null) {
                  if (ret == null) {
                     ret = optPackCP;
                  } else {
                     ret.append(File.pathSeparatorChar).append(optPackCP);
                  }
               }
            }

            if (ret != null) {
               return ret.toString();
            }
         }
      }

      return null;
   }

   private static StringBuffer getOptionalPackages(String src, Attributes attrs) {
      OptionalPackageProvider provider = OptionalPackageProvider.get();
      if (provider == null) {
         return null;
      } else {
         File[] f = provider.getOptionalPackages(src, attrs);
         if (f == null) {
            return null;
         } else {
            StringBuffer ret = new StringBuffer();

            for(int i = 0; i < f.length; ++i) {
               ret.append(f[i].getAbsolutePath());
               if (i < f.length - 1) {
                  ret.append(File.pathSeparatorChar);
               }
            }

            return ret;
         }
      }
   }

   private static StringBuffer convertManifestClassPath(String fileName, String mftCP) {
      StringBuffer ret = new StringBuffer();
      int lastSep = fileName.lastIndexOf(File.separatorChar);
      String relPath = "";
      if (lastSep > -1) {
         relPath = fileName.substring(0, lastSep + 1);
      }

      String[] cp = StringUtils.splitCompletely(mftCP, " " + File.pathSeparatorChar);

      for(int i = 0; i < cp.length; ++i) {
         if (cp[i].startsWith("@BEA_HOME@")) {
            String home = BeaHomeHolder.getBeaHome();
            if (home == null) {
               throw new AssertionError("Can't get BEA home directory path");
            }

            ret.append(home);
            ret.append(cp[i].substring("@BEA_HOME@".length(), cp[i].length()).replace('/', File.separatorChar));
         } else {
            ret.append(relPath);
            cp[i] = cp[i].replace('/', File.separatorChar);
            ret.append(cp[i]);
         }

         if (i < cp.length - 1) {
            ret.append(File.pathSeparatorChar);
         }
      }

      return ret;
   }

   public static boolean isShareable(Manifest manifest) {
      if (manifest != null) {
         Attributes attrs = manifest.getMainAttributes();
         if (attrs != null) {
            return Boolean.valueOf(attrs.getValue("WebLogic-Shareable"));
         }
      }

      return false;
   }

   public static void checkArchive(String path, Exception ioe) {
      if (ClassLoaderDebugger.checkArchive()) {
         DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
         String lastModified = df.format(new Date((new File(path)).lastModified()));

         try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(path));
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            int len = false;
            byte[] buf = new byte[1024];

            int len;
            while((len = bis.read(buf, 0, 1024)) != -1) {
               md.update(buf, 0, len);
            }

            byte[] result = md.digest();
            StringBuilder sb = new StringBuilder();

            for(int i = 0; i < result.length; ++i) {
               sb.append(String.format("%02x", result[i]));
            }

            ClassLoadersLogger.foundCorruptedJarFile(path, sb.toString(), lastModified, ioe);
         } catch (NoSuchAlgorithmException var11) {
            ClassLoadersLogger.foundCorruptedJarFile(path, "<failed to calculate due to no SHA-256 component>", lastModified, ioe);
         } catch (IOException var12) {
            ClassLoadersLogger.foundCorruptedJarFile(path, "<failed to calculate due to IOException>" + var12.toString(), lastModified, ioe);
         }

      }
   }

   public static final class Attr {
      public static final Attr CLASS_PATH = new Attr();
      public static final Attr EXTENSION_LIST = new Attr();

      private Attr() {
      }
   }
}
