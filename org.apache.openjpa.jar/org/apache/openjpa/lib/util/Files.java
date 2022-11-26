package org.apache.openjpa.lib.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.URL;
import java.net.URLDecoder;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.NestableRuntimeException;
import serp.util.Strings;

public class Files {
   public static File backup(File file, boolean copy) {
      if (file != null && (Boolean)AccessController.doPrivileged(J2DoPrivHelper.existsAction(file))) {
         String aPath = (String)AccessController.doPrivileged(J2DoPrivHelper.getAbsolutePathAction(file));
         File clone = new File(aPath);
         File bk = new File(aPath + "~");
         if (!(Boolean)AccessController.doPrivileged(J2DoPrivHelper.renameToAction(clone, bk))) {
            return null;
         } else {
            if (copy) {
               try {
                  copy(bk, file);
               } catch (IOException var6) {
                  throw new NestableRuntimeException(var6);
               }
            }

            return bk;
         }
      } else {
         return null;
      }
   }

   public static File revert(File backup, boolean copy) {
      if (backup == null) {
         return null;
      } else {
         if (!backup.getName().endsWith("~")) {
            backup = new File(backup.getPath() + "~");
         }

         if (!(Boolean)AccessController.doPrivileged(J2DoPrivHelper.existsAction(backup))) {
            return null;
         } else {
            String path = (String)AccessController.doPrivileged(J2DoPrivHelper.getAbsolutePathAction(backup));
            File clone = new File(path);
            File orig = new File(path.substring(0, path.length() - 1));
            if (!(Boolean)AccessController.doPrivileged(J2DoPrivHelper.renameToAction(clone, orig))) {
               return null;
            } else {
               if (copy) {
                  try {
                     copy(orig, backup);
                  } catch (IOException var6) {
                     throw new NestableRuntimeException(var6);
                  }
               }

               return orig;
            }
         }
      }
   }

   public static File getSourceFile(Class cls) {
      return getClassFile(cls, ".java");
   }

   public static File getClassFile(Class cls) {
      return getClassFile(cls, ".class");
   }

   private static File getClassFile(Class cls, String ext) {
      String name = Strings.getClassName(cls);
      int innerIdx = name.indexOf(36);
      if (innerIdx != -1) {
         name = name.substring(0, innerIdx);
      }

      URL rsrc = (URL)AccessController.doPrivileged(J2DoPrivHelper.getResourceAction(cls, name + ext));
      return rsrc != null && rsrc.getProtocol().equals("file") ? new File(URLDecoder.decode(rsrc.getFile())) : null;
   }

   public static File getPackageFile(File base, String pkg, boolean mkdirs) {
      if (base == null) {
         base = new File((String)AccessController.doPrivileged(J2DoPrivHelper.getPropertyAction("user.dir")));
      }

      if (StringUtils.isEmpty(pkg)) {
         if (mkdirs && !(Boolean)AccessController.doPrivileged(J2DoPrivHelper.existsAction(base))) {
            AccessController.doPrivileged(J2DoPrivHelper.mkdirsAction(base));
         }

         return base;
      } else {
         pkg = pkg.replace('.', File.separatorChar);
         File file = null;

         try {
            if (((String)AccessController.doPrivileged(J2DoPrivHelper.getCanonicalPathAction(base))).endsWith(pkg)) {
               file = base;
            } else {
               file = new File(base, pkg);
            }
         } catch (PrivilegedActionException var5) {
            throw new NestableRuntimeException((IOException)var5.getException());
         } catch (IOException var6) {
            throw new NestableRuntimeException(var6);
         }

         if (mkdirs && !(Boolean)AccessController.doPrivileged(J2DoPrivHelper.existsAction(file))) {
            AccessController.doPrivileged(J2DoPrivHelper.mkdirsAction(file));
         }

         return file;
      }
   }

   public static File getFile(String name, ClassLoader loader) {
      if (name == null) {
         return null;
      } else {
         File file = new File(name);
         if ((Boolean)AccessController.doPrivileged(J2DoPrivHelper.existsAction(file))) {
            return file;
         } else {
            if (loader == null) {
               loader = (ClassLoader)AccessController.doPrivileged(J2DoPrivHelper.getContextClassLoaderAction());
            }

            URL url = (URL)AccessController.doPrivileged(J2DoPrivHelper.getResourceAction(loader, name));
            if (url != null) {
               String urlFile = url.getFile();
               if (urlFile != null) {
                  File rsrc = new File(URLDecoder.decode(urlFile));
                  if ((Boolean)AccessController.doPrivileged(J2DoPrivHelper.existsAction(rsrc))) {
                     return rsrc;
                  }
               }
            }

            return file;
         }
      }
   }

   public static Writer getWriter(String file, ClassLoader loader) throws IOException {
      if (file == null) {
         return null;
      } else if ("stdout".equals(file)) {
         return new PrintWriter(System.out);
      } else if ("stderr".equals(file)) {
         return new PrintWriter(System.err);
      } else {
         try {
            return new FileWriter(getFile(file, loader));
         } catch (IOException var3) {
            throw new NestableRuntimeException(var3);
         }
      }
   }

   public static OutputStream getOutputStream(String file, ClassLoader loader) {
      if (file == null) {
         return null;
      } else if ("stdout".equals(file)) {
         return System.out;
      } else if ("stderr".equals(file)) {
         return System.err;
      } else {
         try {
            return (FileOutputStream)AccessController.doPrivileged(J2DoPrivHelper.newFileOutputStreamAction(getFile(file, loader)));
         } catch (PrivilegedActionException var3) {
            throw new NestableRuntimeException(var3.getException());
         } catch (IOException var4) {
            throw new NestableRuntimeException(var4);
         }
      }
   }

   public static boolean copy(File from, File to) throws IOException {
      if (from != null && to != null && (Boolean)AccessController.doPrivileged(J2DoPrivHelper.existsAction(from))) {
         FileInputStream in = null;
         FileOutputStream out = null;

         try {
            in = (FileInputStream)AccessController.doPrivileged(J2DoPrivHelper.newFileInputStreamAction(from));
            BufferedInputStream inbuf = new BufferedInputStream(in);
            out = (FileOutputStream)AccessController.doPrivileged(J2DoPrivHelper.newFileOutputStreamAction(to));
            BufferedOutputStream outbuf = new BufferedOutputStream(out);

            int b;
            while((b = inbuf.read()) != -1) {
               outbuf.write(b);
            }

            outbuf.flush();
            boolean var20 = true;
            return var20;
         } catch (PrivilegedActionException var18) {
            throw (FileNotFoundException)var18.getException();
         } finally {
            if (in != null) {
               try {
                  in.close();
               } catch (Exception var17) {
               }
            }

            if (out != null) {
               try {
                  out.close();
               } catch (Exception var16) {
               }
            }

         }
      } else {
         return false;
      }
   }
}
