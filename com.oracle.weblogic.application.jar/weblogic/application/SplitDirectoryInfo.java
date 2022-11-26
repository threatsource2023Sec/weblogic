package weblogic.application;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public final class SplitDirectoryInfo {
   private static final String[] ZERO_STRINGS = new String[0];
   private final SplitDirParser parser;

   public SplitDirectoryInfo(File destDir, File propsFile) throws IOException {
      this.parser = new SplitDirParser(destDir, propsFile);
      this.parser.load();
   }

   public File[] getRootDirectories() {
      return this.getSrcDir() != null && this.getSrcDir().equals(this.getDestDir()) ? new File[]{this.getSrcDir()} : new File[]{this.getSrcDir(), this.getDestDir()};
   }

   public File getSrcDir() {
      return this.parser.srcDir;
   }

   public File getDestDir() {
      return this.parser.destDir;
   }

   public Map getUriLinks() {
      return this.parser.uriLinks;
   }

   public Iterator getAppClasses() {
      return this.parser.appClasses.iterator();
   }

   public String[] getWebAppClasses(String uri) {
      return this.toArray((Collection)this.parser.webClasses.get(uri));
   }

   public String[] getWebInfClasses(String uri) {
      return this.toArray((Collection)this.parser.webInfClasses.get(uri));
   }

   public String getExtraClasspath() {
      StringBuffer cp = new StringBuffer();
      String sep = "";
      Iterator it = this.parser.appClasses.iterator();

      while(it.hasNext()) {
         String path = (String)it.next();
         cp.append(sep);
         sep = File.pathSeparator;
         cp.append(path);
      }

      return cp.toString();
   }

   private String[] toArray(Collection values) {
      return values != null ? (String[])values.toArray(new String[values.size()]) : ZERO_STRINGS;
   }

   private static class SplitDirParser extends Properties {
      private static final long serialVersionUID = -3523623857088132660L;
      private final File destDir;
      private final File propsFile;
      private File srcDir;
      private String srcPath;
      private final Map uriLinks = new HashMap();
      private final List appClasses = new ArrayList();
      private final Map webClasses = new HashMap();
      private Map webInfClasses = new HashMap();

      SplitDirParser(File destDir, File propsFile) {
         this.destDir = destDir;
         this.propsFile = propsFile;
      }

      private File resolveSrcDir() throws IOException {
         if (this.srcPath == null) {
            return this.destDir;
         } else {
            File srcDir = new File(this.srcPath);
            if (!srcDir.isAbsolute()) {
               srcDir = new File(this.destDir, srcDir.getPath());
            }

            if (!srcDir.exists()) {
               throw new IOException("Unable to find the source directory: " + srcDir.getAbsolutePath() + ".  This directory is referenced in the file: " + this.propsFile.getAbsolutePath() + ".  A possible solution is to rebuild your application.");
            } else if (!srcDir.isDirectory()) {
               throw new IOException("The source directory: " + srcDir.getAbsolutePath() + " exists, but it is not a directory.  This directory is referenced in the file: " + this.propsFile.getAbsolutePath());
            } else {
               return srcDir;
            }
         }
      }

      public Object put(Object n, Object v) {
         String name = (String)n;
         String value = (String)v;
         if ("bea.srcdir".equals(n)) {
            this.srcPath = value;
         } else if (!value.startsWith("APP-INF/lib/") && !"APP-INF/classes".equals(value)) {
            if (value.indexOf("WEB-INF/classes") <= -1 && value.indexOf("WEB-INF/lib/") <= -1) {
               List fileList = (List)this.uriLinks.get(v);
               if (fileList == null) {
                  fileList = new ArrayList();
               }

               ((List)fileList).add(new File(name));
               this.uriLinks.put(v, fileList);
            } else {
               int sl = value.replace(File.separatorChar, '/').indexOf(47);
               String uri = sl == -1 ? value : value.substring(0, sl);
               this.putNameToMap(this.webClasses, uri, name);
               if (value.indexOf("WEB-INF/classes") > -1) {
                  this.putNameToMap(this.webInfClasses, uri, name);
               }
            }
         } else {
            this.appClasses.add(n);
         }

         return super.put(n, v);
      }

      public void load() throws IOException {
         InputStream is = null;

         try {
            is = new FileInputStream(this.propsFile);
            this.load(is);
         } finally {
            if (is != null) {
               is.close();
            }

         }

         this.srcDir = this.resolveSrcDir();
      }

      private void putNameToMap(Map map, String uri, String name) {
         Object names;
         if (map.containsKey(uri)) {
            names = (Collection)map.get(uri);
         } else {
            map.put(uri, names = new ArrayList());
         }

         ((Collection)names).add(name);
      }
   }
}
