package com.bea.core.repackaged.jdt.internal.compiler.apt.util;

import com.bea.core.repackaged.jdt.internal.compiler.classfmt.ClassFileReader;
import com.bea.core.repackaged.jdt.internal.compiler.classfmt.ClassFormatException;
import com.bea.core.repackaged.jdt.internal.compiler.util.JRTUtil;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.Charset;
import java.nio.file.FileSystem;
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
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipException;

public class JrtFileSystem extends Archive {
   private static URI JRT_URI = URI.create("jrt:/");
   static final String BOOT_MODULE = "jrt-fs.jar";
   public HashMap modulePathMap;
   Path modules;
   private FileSystem jrtfs;

   public JrtFileSystem(File file) throws ZipException, IOException {
      this.file = file;
      this.initialize();
   }

   public void initialize() throws IOException {
      this.modulePathMap = new HashMap();
      URL jrtPath = null;
      if (this.file.exists()) {
         jrtPath = Paths.get(this.file.toPath().toString(), "lib", "jrt-fs.jar").toUri().toURL();
         Throwable var2 = null;
         Object var3 = null;

         try {
            URLClassLoader loader = new URLClassLoader(new URL[]{jrtPath});

            try {
               HashMap env = new HashMap();
               this.jrtfs = FileSystems.newFileSystem(JRT_URI, env, loader);
               this.modules = this.jrtfs.getPath("/modules");
            } finally {
               if (loader != null) {
                  loader.close();
               }

            }
         } catch (Throwable var11) {
            if (var2 == null) {
               var2 = var11;
            } else if (var2 != var11) {
               var2.addSuppressed(var11);
            }

            throw var2;
         }

         JRTUtil.walkModuleImage(this.file, new JRTUtil.JrtFileVisitor() {
            public FileVisitResult visitPackage(Path dir, Path mod, BasicFileAttributes attrs) throws IOException {
               return FileVisitResult.CONTINUE;
            }

            public FileVisitResult visitFile(Path f, Path mod, BasicFileAttributes attrs) throws IOException {
               return FileVisitResult.CONTINUE;
            }

            public FileVisitResult visitModule(Path path, String name) throws IOException {
               JrtFileSystem.this.modulePathMap.put(name, path);
               return FileVisitResult.CONTINUE;
            }
         }, JRTUtil.NOTIFY_MODULES);
      }
   }

   public List list(ModuleLocationHandler.ModuleLocationWrapper location, String packageName, Set kinds, boolean recurse, Charset charset) {
      String module = location.modName;
      Path mPath = this.modules.resolve(module);
      Path resolve = mPath.resolve(packageName);
      List files = null;

      Path p;
      try {
         Throwable var10 = null;
         p = null;

         try {
            Stream p = Files.list(resolve);

            try {
               files = (List)p.filter((path) -> {
                  return !Files.isDirectory(path, new LinkOption[0]);
               }).collect(Collectors.toList());
            } finally {
               if (p != null) {
                  p.close();
               }

            }
         } catch (Throwable var20) {
            if (var10 == null) {
               var10 = var20;
            } else if (var10 != var20) {
               var10.addSuppressed(var20);
            }

            throw var10;
         }
      } catch (IOException var21) {
      }

      List result = new ArrayList();
      Iterator var23 = files.iterator();

      while(var23.hasNext()) {
         p = (Path)var23.next();
         result.add(new JrtFileObject(this.file, p, module, charset, (JrtFileObject)null));
      }

      return result;
   }

   public ArchiveFileObject getArchiveFileObject(String fileName, String module, Charset charset) {
      return new JrtFileObject(this.file, this.modules.resolve(module).resolve(fileName), module, charset, (JrtFileObject)null);
   }

   public boolean contains(String entryName) {
      return false;
   }

   public String toString() {
      return "JRT: " + (this.file == null ? "UNKNOWN_ARCHIVE" : this.file.getAbsolutePath());
   }

   class JrtFileObject extends ArchiveFileObject {
      String module;
      Path path;

      private JrtFileObject(File file, Path path, String module, Charset charset) {
         super(file, path.toString(), charset);
         this.path = path;
      }

      protected void finalize() throws Throwable {
      }

      protected ClassFileReader getClassReader() {
         ClassFileReader reader = null;

         try {
            byte[] content = JRTUtil.getClassfileContent(this.file, this.entryName, this.module);
            if (content == null) {
               return null;
            }

            return new ClassFileReader(content, this.entryName.toCharArray());
         } catch (ClassFormatException var3) {
            var3.printStackTrace();
         } catch (IOException var4) {
            var4.printStackTrace();
         }

         return (ClassFileReader)reader;
      }

      public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
         try {
            return Util.getCharContents(this, ignoreEncodingErrors, JRTUtil.getClassfileContent(this.file, this.entryName, this.module), this.charset.name());
         } catch (ClassFormatException var3) {
            var3.printStackTrace();
            return null;
         }
      }

      public long getLastModified() {
         return 0L;
      }

      public String getName() {
         return this.path.toString();
      }

      public InputStream openInputStream() throws IOException {
         return Files.newInputStream(this.path);
      }

      public OutputStream openOutputStream() throws IOException {
         throw new UnsupportedOperationException();
      }

      public Reader openReader(boolean ignoreEncodingErrors) throws IOException {
         throw new UnsupportedOperationException();
      }

      public Writer openWriter() throws IOException {
         throw new UnsupportedOperationException();
      }

      public URI toUri() {
         try {
            return new URI("JRT:" + this.file.toURI().getPath() + "!" + this.entryName);
         } catch (URISyntaxException var1) {
            return null;
         }
      }

      public String toString() {
         return this.file.getAbsolutePath() + "[" + this.entryName + "]";
      }

      // $FF: synthetic method
      JrtFileObject(File var2, Path var3, String var4, Charset var5, JrtFileObject var6) {
         this(var2, var3, var4, var5);
      }
   }
}
