package com.bea.xbean.tool;

import com.bea.xbean.common.IOUtil;
import com.bea.xml.XmlBeans;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CommandLine {
   private Map _options;
   private String[] _badopts;
   private String[] _args;
   private List _files;
   private List _urls;
   private File _baseDir;
   private static final File[] EMPTY_FILEARRAY = new File[0];
   private static final URL[] EMPTY_URLARRAY = new URL[0];

   public CommandLine(String[] args, Collection flags, Collection scheme) {
      if (flags != null && scheme != null) {
         this._options = new LinkedHashMap();
         ArrayList badopts = new ArrayList();
         ArrayList endargs = new ArrayList();

         for(int i = 0; i < args.length; ++i) {
            if (args[i].indexOf(45) == 0) {
               String opt = args[i].substring(1);
               String val = null;
               if (flags.contains(opt)) {
                  val = "";
               } else if (scheme.contains(opt)) {
                  if (i + 1 < args.length) {
                     ++i;
                     val = args[i];
                  } else {
                     val = "";
                  }
               } else {
                  badopts.add(args[i]);
               }

               this._options.put(opt, val);
            } else {
               endargs.add(args[i]);
            }
         }

         this._badopts = (String[])((String[])badopts.toArray(new String[badopts.size()]));
         this._args = (String[])((String[])endargs.toArray(new String[endargs.size()]));
      } else {
         throw new IllegalArgumentException("collection required (use Collections.EMPTY_SET if no options)");
      }
   }

   public static void printLicense() {
      try {
         IOUtil.copyCompletely((InputStream)CommandLine.class.getClassLoader().getResourceAsStream("LICENSE.txt"), (OutputStream)System.out);
      } catch (Exception var1) {
         System.out.println("License available in this JAR in LICENSE.txt");
      }

   }

   public static void printVersion() {
      System.out.println(XmlBeans.getVendor() + ", " + XmlBeans.getTitle() + ".XmlBeans version " + XmlBeans.getVersion());
   }

   public String[] args() {
      String[] result = new String[this._args.length];
      System.arraycopy(this._args, 0, result, 0, this._args.length);
      return result;
   }

   public String[] getBadOpts() {
      return this._badopts;
   }

   public String getOpt(String opt) {
      return (String)this._options.get(opt);
   }

   private static List collectFiles(File[] dirs) {
      List files = new ArrayList();

      for(int i = 0; i < dirs.length; ++i) {
         File f = dirs[i];
         if (!f.isDirectory()) {
            files.add(f);
         } else {
            files.addAll(collectFiles(f.listFiles()));
         }
      }

      return files;
   }

   private List getFileList() {
      if (this._files == null) {
         String[] args = this.args();
         File[] files = new File[args.length];
         boolean noBaseDir = false;

         for(int i = 0; i < args.length; ++i) {
            files[i] = new File(args[i]);
            if (!noBaseDir && this._baseDir == null) {
               if (files[i].isDirectory()) {
                  this._baseDir = files[i];
               } else {
                  this._baseDir = files[i].getParentFile();
               }
            } else {
               URI currUri = files[i].toURI();
               if (this._baseDir != null && this._baseDir.toURI().relativize(currUri).equals(currUri)) {
                  this._baseDir = null;
                  noBaseDir = true;
               }
            }
         }

         this._files = Collections.unmodifiableList(collectFiles(files));
      }

      return this._files;
   }

   private List getUrlList() {
      if (this._urls == null) {
         String[] args = this.args();
         List urls = new ArrayList();

         for(int i = 0; i < args.length; ++i) {
            if (looksLikeURL(args[i])) {
               try {
                  urls.add(new URL(args[i]));
               } catch (MalformedURLException var5) {
                  System.err.println("ignoring invalid url: " + args[i] + ": " + var5.getMessage());
               }
            }
         }

         this._urls = Collections.unmodifiableList(urls);
      }

      return this._urls;
   }

   private static boolean looksLikeURL(String str) {
      return str.startsWith("http:") || str.startsWith("https:") || str.startsWith("ftp:") || str.startsWith("file:");
   }

   public URL[] getURLs() {
      return (URL[])((URL[])this.getUrlList().toArray(EMPTY_URLARRAY));
   }

   public File[] getFiles() {
      return (File[])((File[])this.getFileList().toArray(EMPTY_FILEARRAY));
   }

   public File getBaseDir() {
      return this._baseDir;
   }

   public File[] filesEndingWith(String ext) {
      List result = new ArrayList();
      Iterator i = this.getFileList().iterator();

      while(i.hasNext()) {
         File f = (File)i.next();
         if (f.getName().endsWith(ext) && !looksLikeURL(f.getPath())) {
            result.add(f);
         }
      }

      return (File[])((File[])result.toArray(EMPTY_FILEARRAY));
   }
}
