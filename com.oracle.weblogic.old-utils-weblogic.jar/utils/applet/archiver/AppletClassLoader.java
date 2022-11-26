package utils.applet.archiver;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class AppletClassLoader extends ClassLoader {
   URL codebase;
   PrintStream ps;
   ZipOutputStream zos;
   File cabDir;
   boolean isCab;
   String fileName;
   String dumpOnClass;
   Hashtable images = new Hashtable();
   Hashtable audio = new Hashtable();
   String[] remList;
   boolean compressJar;
   static boolean gotMiscClasses = false;
   static JarFile weblogicJar = null;
   public static final String CLASSPATH_PROP = "java.class.path";

   public AppletClassLoader(URL cb, PrintStream p, String fname, File cabDir, String[] remList, String dumpClass, boolean compressJar) throws IOException {
      this.codebase = cb;
      this.compressJar = compressJar;
      this.dumpOnClass = dumpClass;
      this.remList = remList;
      if (this.dumpOnClass == null) {
         this.dumpOnClass = "";
      }

      this.ps = p;
      this.fileName = fname;
      if ((this.cabDir = cabDir) != null) {
         System.out.println("cabDir=" + cabDir.getAbsolutePath());
         this.isCab = true;
      } else {
         this.zos = new ZipOutputStream(new FileOutputStream(fname));
         if (!compressJar) {
            ZipOutputStream var10001 = this.zos;
            this.zos.setMethod(0);
         }

         this.getMiscClasses();
         gotMiscClasses = true;
      }

   }

   private void deltree(File dir) {
      String[] l = dir.list();

      for(int i = 0; l != null && i < l.length; ++i) {
         File f = new File(dir, l[i]);
         if (f.isDirectory()) {
            this.deltree(f);
         } else {
            f.delete();
         }
      }

      dir.delete();
   }

   void p(String s) {
      this.ps.println(s);
      System.out.println(s);
   }

   protected synchronized Class loadClass(String name, boolean resolve) throws ClassNotFoundException {
      Class cl = this.findLoadedClass(name);
      if (cl != null) {
         return cl;
      } else {
         SecurityManager security = System.getSecurityManager();
         int i;
         if (security != null) {
            i = name.lastIndexOf(46);
            if (i >= 0) {
               security.checkPackageAccess(name.substring(0, i));
            }
         }

         boolean tryLocal = true;

         for(i = 0; this.remList != null && i < this.remList.length; ++i) {
            if (name.startsWith(this.remList[i])) {
               tryLocal = false;
               break;
            }
         }

         if (tryLocal) {
            try {
               cl = this.findSystemClass(name);
            } catch (ClassNotFoundException var6) {
            }
         }

         if (cl != null) {
            return cl;
         } else {
            cl = this.findURLClass(name);
            if (cl == null) {
               throw new ClassNotFoundException(name);
            } else {
               if (resolve) {
                  this.resolveClass(cl);
               }

               return cl;
            }
         }
      }
   }

   byte[] loadBytes(URL u, String name) throws IOException {
      InputStream i = null;
      String auth = AppletArchiver.getInstance().getAuthenticationInfo(false);
      HttpURLConnection conn = null;

      while(i == null) {
         conn = (HttpURLConnection)u.openConnection();
         conn.setRequestProperty("User-Agent", "Mozilla/404");
         if (auth != null) {
            conn.setRequestProperty("Authorization", "Basic " + auth);
         }

         try {
            i = conn.getInputStream();
         } catch (IOException var11) {
            if (conn == null || conn.getResponseCode() != 401) {
               throw var11;
            }

            AppletArchiver.getInstance().setAuthenticationInfo((String)null);
            auth = AppletArchiver.getInstance().getAuthenticationInfo(true);
            if (auth == null) {
               throw new ProtocolException("HTTP Authorization failed");
            }
         }
      }

      int cl = conn.getContentLength();
      byte[] dat = new byte[cl == -1 ? 4096 : cl];
      int nread = 0;

      int r;
      byte[] newdat;
      do {
         r = i.read(dat, nread, dat.length - nread);
         if (r != -1) {
            nread += r;
            if (nread == dat.length) {
               if (nread == cl) {
                  break;
               }

               newdat = new byte[nread << 1];
               System.arraycopy(dat, 0, newdat, 0, nread);
               dat = newdat;
            }
         }
      } while(r != -1);

      this.ps.println(nread + " bytes, " + name);
      if (nread != cl) {
         newdat = new byte[nread];
         System.arraycopy(dat, 0, newdat, 0, nread);
         dat = newdat;
      }

      this.saveBytes(dat, name, dat.length);
      return dat;
   }

   Image loadImage(URL u) throws IOException {
      String f = u.getFile();
      int rind = f.lastIndexOf(47);
      if (rind > 0) {
         f = f.substring(rind + 1);
      }

      synchronized(this.images) {
         Image img = (Image)this.images.get(f);
         if (img != null) {
            return img;
         } else {
            byte[] dat = this.loadBytes(u, f);
            img = Toolkit.getDefaultToolkit().createImage(dat);
            this.images.put(f, img);
            return img;
         }
      }
   }

   synchronized Class findURLClass(String name) throws ClassNotFoundException {
      if (this.dumpOnClass.length() > 1 && name.indexOf(this.dumpOnClass) >= 0) {
         System.err.println("loading class: " + name + ", matches " + this.dumpOnClass + ":");
         Thread.dumpStack();
         System.err.println();
      }

      URL u = null;

      try {
         String cname = null;
         if (gotMiscClasses || name.lastIndexOf("MANIFEST.MF") == -1 && name.indexOf("i18n") == -1) {
            cname = name.replace('.', '/') + ".class";
         } else {
            int lastDot = false;
            int lastDot;
            if ((lastDot = name.lastIndexOf(".")) != -1) {
               StringBuffer sb = new StringBuffer(name.replace('.', '/'));
               sb.setCharAt(lastDot, '.');
               cname = sb.toString();
            }
         }

         u = new URL(this.codebase, cname);
         byte[] dat = this.loadBytes(u, cname);
         return this.defineClass(name, dat, 0, dat.length);
      } catch (FileNotFoundException var7) {
         if (name.startsWith("javax")) {
            try {
               return this.findSystemClass(name);
            } catch (ClassNotFoundException var6) {
               var6.printStackTrace();
               throw new ClassNotFoundException(u == null ? name : u.toString());
            }
         } else {
            var7.printStackTrace();
            throw new ClassNotFoundException(u == null ? name : u.toString());
         }
      } catch (IOException var8) {
         this.ps.println("ERROR: " + var8.toString());
         var8.printStackTrace();
         throw new ClassNotFoundException(u == null ? name : u.toString());
      }
   }

   void saveBytes(byte[] buf, String cname, int len) throws IOException {
      if (!gotMiscClasses || cname.indexOf("i18n") == -1) {
         if (!this.isCab) {
            ZipEntry ze = new ZipEntry(cname);
            ze.setSize((long)len);
            if (!this.compressJar) {
               CRC32OutputStream o = new CRC32OutputStream();
               o.write(buf, 0, len);
               ze.setCrc(o.crc.getValue());
            }

            this.zos.putNextEntry(ze);
            this.zos.write(buf, 0, len);
         } else {
            cname = cname.replace('/', File.separatorChar);
            File cfile = new File(this.cabDir, cname);
            File tmp = new File(cfile.getParent());
            tmp.mkdirs();
            OutputStream o = new FileOutputStream(cfile);
            o.write(buf, 0, len);
            o.close();
         }

      }
   }

   public void close() {
      try {
         if (!this.isCab) {
            this.zos.close();
            File targ = new File(this.fileName);
            this.p("jar file saved: " + targ.getAbsolutePath() + ", size=" + targ.length());
            this.zos = null;
            return;
         }
      } catch (IOException var15) {
         var15.printStackTrace();
         return;
      }

      Process p = null;

      try {
         StringBuffer cmd = new StringBuffer();
         cmd.append("cabarc -r -p -P ");
         cmd.append(this.cabDir.getName());
         cmd.append(File.separatorChar);
         cmd.append(" N ");
         cmd.append(this.fileName);
         cmd.append(' ');
         cmd.append(this.cabDir.getName());
         cmd.append(File.separatorChar);
         cmd.append('*');
         this.p("launching cabarc tool: " + cmd.toString());
         p = Runtime.getRuntime().exec(cmd.toString());
         Thread t1 = new Thread(new ProcRunner(p.getInputStream(), this.ps), "stdout");
         t1.start();
         Thread t2 = new Thread(new ProcRunner(p.getErrorStream(), this.ps), "stderr");
         t2.start();
         int val = p.waitFor();
         t1.join();
         t2.join();
         this.p("cabarc exit value: " + val);
         if (val == 0) {
            File targ = new File(this.fileName);
            this.p("cab file saved: " + targ.getAbsolutePath() + ", size=" + targ.length());
         }
      } catch (InterruptedException var12) {
         var12.printStackTrace();
      } catch (IOException var13) {
         this.p("CABARC ERROR: do you have cabarc.exe in you path?");
         var13.printStackTrace();
      } finally {
         this.ps.print("deleting temporary cab directory...");
         this.deltree(this.cabDir);
         this.ps.println("done");
      }

   }

   void getMiscClasses() {
      String classpath = System.getProperty("java.class.path");
      String aClassPath = null;
      String result = null;
      StringTokenizer classPaths = new StringTokenizer(classpath, File.pathSeparator);

      while(classPaths.hasMoreTokens()) {
         aClassPath = classPaths.nextToken();
         if (aClassPath.endsWith("weblogic.jar")) {
            try {
               weblogicJar = new JarFile(aClassPath);
               break;
            } catch (Exception var12) {
               System.out.println("No weblogic.jar in classpath. Archive will be incomplete.");
               return;
            }
         }
      }

      if (weblogicJar != null) {
         Enumeration enum_ = weblogicJar.entries();

         while(true) {
            ZipEntry e;
            do {
               do {
                  if (!enum_.hasMoreElements()) {
                     return;
                  }

                  e = (ZipEntry)enum_.nextElement();
                  result = e.getName();
               } while(e.isDirectory());
            } while(result.indexOf("i18n") == -1 && result.indexOf("MANIFEST.MF") == -1);

            try {
               this.findURLClass(result);
            } catch (ClassNotFoundException var8) {
            } catch (NoClassDefFoundError var9) {
            } catch (ClassFormatError var10) {
            } catch (Exception var11) {
            }
         }
      }
   }

   public URL getResource(String name) {
      try {
         URL ret = getSystemResource(name);
         if (ret != null) {
            return ret;
         } else {
            ret = new URL(this.codebase, name);
            ret.openStream().close();
            return ret;
         }
      } catch (Exception var3) {
         return null;
      }
   }

   public InputStream getResourceAsStream(String name) {
      InputStream ret = getSystemResourceAsStream(name);
      if (ret != null) {
         return ret;
      } else {
         try {
            ret = (new URL(this.codebase, name)).openStream();
            return ret;
         } catch (IOException var4) {
            return null;
         }
      }
   }
}
