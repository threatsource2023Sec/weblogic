package com.bea.core.repackaged.jdt.internal.compiler.util;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

class JrtFileSystemWithOlderRelease extends JrtFileSystem {
   final String release;
   String releaseInHex = null;
   private String[] subReleases = null;
   protected Path modulePath = null;

   JrtFileSystemWithOlderRelease(File jrt, String release) throws IOException {
      super(jrt);
      this.release = release;
      this.initialize(jrt, release);
   }

   void initialize(File jdk) throws IOException {
   }

   void initialize(File jdk, String rel) throws IOException {
      super.initialize(jdk);
      this.fs = null;
      this.releaseInHex = Integer.toHexString(Integer.parseInt(this.release)).toUpperCase();
      Path ct = Paths.get(this.jdkHome, "lib", "ct.sym");
      if (Files.exists(ct, new LinkOption[0])) {
         URI uri = URI.create("jar:file:" + ct.toUri().getRawPath());

         try {
            this.fs = FileSystems.getFileSystem(uri);
         } catch (FileSystemNotFoundException var24) {
         }

         if (this.fs == null) {
            HashMap env = new HashMap();

            try {
               this.fs = FileSystems.newFileSystem(uri, env);
            } catch (IOException var23) {
               return;
            }
         }

         Path releasePath = this.fs.getPath("/");
         if (!Files.exists(this.fs.getPath(this.releaseInHex), new LinkOption[0]) || Files.exists(this.fs.getPath(this.releaseInHex, "system-modules"), new LinkOption[0])) {
            this.fs = null;
         }

         if (this.release != null) {
            List sub = new ArrayList();

            try {
               Throwable var7 = null;
               Object var8 = null;

               try {
                  DirectoryStream stream = Files.newDirectoryStream(releasePath);

                  try {
                     Iterator var11 = stream.iterator();

                     while(var11.hasNext()) {
                        Path subdir = (Path)var11.next();
                        String r = JRTUtil.sanitizedFileName(subdir);
                        if (r.contains(this.releaseInHex)) {
                           sub.add(r);
                        }
                     }
                  } finally {
                     if (stream != null) {
                        stream.close();
                     }

                  }
               } catch (Throwable var26) {
                  if (var7 == null) {
                     var7 = var26;
                  } else if (var7 != var26) {
                     var7.addSuppressed(var26);
                  }

                  throw var7;
               }
            } catch (IOException var27) {
               var27.printStackTrace();
            }

            this.subReleases = (String[])sub.toArray(new String[sub.size()]);
         }

      }
   }

   void walkModuleImage(final JRTUtil.JrtFileVisitor visitor, final int notify) throws IOException {
      if (this.subReleases != null && this.subReleases.length > 0) {
         String[] var6;
         int var5 = (var6 = this.subReleases).length;

         for(int var4 = 0; var4 < var5; ++var4) {
            String rel = var6[var4];
            Path p = this.fs.getPath(rel);
            Files.walkFileTree(p, new JRTUtil.AbstractFileVisitor() {
               public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                  int count = dir.getNameCount();
                  if (count == 1) {
                     return FileVisitResult.CONTINUE;
                  } else if (count == 2) {
                     Path mod = dir.getName(1);
                     if (JRTUtil.MODULE_TO_LOAD != null && JRTUtil.MODULE_TO_LOAD.length() > 0 && JRTUtil.MODULE_TO_LOAD.indexOf(mod.toString()) == -1) {
                        return FileVisitResult.SKIP_SUBTREE;
                     } else {
                        return (notify & JRTUtil.NOTIFY_MODULES) == 0 ? FileVisitResult.CONTINUE : visitor.visitModule(dir, JRTUtil.sanitizedFileName(mod));
                     }
                  } else {
                     return (notify & JRTUtil.NOTIFY_PACKAGES) == 0 ? FileVisitResult.CONTINUE : visitor.visitPackage(dir.subpath(2, count), dir.getName(1), attrs);
                  }
               }

               public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                  if ((notify & JRTUtil.NOTIFY_FILES) == 0) {
                     return FileVisitResult.CONTINUE;
                  } else {
                     if (file.getNameCount() == 3) {
                        JrtFileSystemWithOlderRelease.this.cachePackage("", file.getName(1).toString());
                     }

                     return visitor.visitFile(file.subpath(2, file.getNameCount()), file.getName(1), attrs);
                  }
               }
            });
         }
      }

   }
}
