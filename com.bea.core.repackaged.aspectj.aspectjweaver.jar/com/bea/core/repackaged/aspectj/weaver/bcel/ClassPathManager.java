package com.bea.core.repackaged.aspectj.weaver.bcel;

import com.bea.core.repackaged.aspectj.bridge.IMessageHandler;
import com.bea.core.repackaged.aspectj.bridge.MessageUtil;
import com.bea.core.repackaged.aspectj.weaver.BCException;
import com.bea.core.repackaged.aspectj.weaver.UnresolvedType;
import com.bea.core.repackaged.aspectj.weaver.WeaverMessages;
import com.bea.core.repackaged.aspectj.weaver.tools.Trace;
import com.bea.core.repackaged.aspectj.weaver.tools.TraceFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ClassPathManager {
   private static Trace trace = TraceFactory.getTraceFactory().getTrace(ClassPathManager.class);
   private List entries;
   private List openArchives = new ArrayList();
   private static int maxOpenArchives = -1;
   private static final int MAXOPEN_DEFAULT = 1000;

   public ClassPathManager(List classpath, IMessageHandler handler) {
      if (trace.isTraceEnabled()) {
         trace.enter("<init>", this, (Object[])(new Object[]{classpath == null ? "null" : classpath.toString(), handler}));
      }

      this.entries = new ArrayList();
      Iterator i$ = classpath.iterator();

      while(i$.hasNext()) {
         String classpathEntry = (String)i$.next();
         this.addPath(classpathEntry, handler);
      }

      if (trace.isTraceEnabled()) {
         trace.exit("<init>");
      }

   }

   protected ClassPathManager() {
   }

   public void addPath(String name, IMessageHandler handler) {
      File f = new File(name);
      String lc = name.toLowerCase();
      if (!f.isDirectory()) {
         if (!f.isFile()) {
            if (lc.endsWith(".jar") && !lc.endsWith(".zip")) {
               MessageUtil.info(handler, WeaverMessages.format("directoryEntryMissing", name));
            } else {
               MessageUtil.info(handler, WeaverMessages.format("zipfileEntryMissing", name));
            }

            return;
         }

         try {
            this.entries.add(new ZipFileEntry(f));
         } catch (IOException var6) {
            MessageUtil.warn(handler, WeaverMessages.format("zipfileEntryInvalid", name, var6.getMessage()));
            return;
         }
      } else {
         this.entries.add(new DirEntry(f));
      }

   }

   public ClassFile find(UnresolvedType type) {
      if (trace.isTraceEnabled()) {
         trace.enter("find", this, (Object)type);
      }

      String name = type.getName();
      Iterator i = this.entries.iterator();

      while(i.hasNext()) {
         Entry entry = (Entry)i.next();

         try {
            ClassFile ret = entry.find(name);
            if (trace.isTraceEnabled()) {
               trace.event("searching for " + type + " in " + entry.toString());
            }

            if (ret != null) {
               if (trace.isTraceEnabled()) {
                  trace.exit("find", (Object)ret);
               }

               return ret;
            }
         } catch (IOException var6) {
            if (trace.isTraceEnabled()) {
               trace.error("Removing classpath entry for " + entry, var6);
            }

            i.remove();
         }
      }

      if (trace.isTraceEnabled()) {
         trace.exit("find", (Throwable)null);
      }

      return null;
   }

   public String toString() {
      StringBuffer buf = new StringBuffer();
      boolean start = true;

      for(Iterator i = this.entries.iterator(); i.hasNext(); buf.append(i.next())) {
         if (start) {
            start = false;
         } else {
            buf.append(File.pathSeparator);
         }
      }

      return buf.toString();
   }

   static boolean hasClassExtension(String name) {
      return name.toLowerCase().endsWith(".class");
   }

   public void closeArchives() {
      for(Iterator i$ = this.entries.iterator(); i$.hasNext(); this.openArchives.clear()) {
         Entry entry = (Entry)i$.next();
         if (entry instanceof ZipFileEntry) {
            ((ZipFileEntry)entry).close();
         }
      }

   }

   private static String getSystemPropertyWithoutSecurityException(String aPropertyName, String aDefaultValue) {
      try {
         return System.getProperty(aPropertyName, aDefaultValue);
      } catch (SecurityException var3) {
         return aDefaultValue;
      }
   }

   static {
      String openzipsString = getSystemPropertyWithoutSecurityException("com.bea.core.repackaged.aspectj.weaver.openarchives", Integer.toString(1000));
      maxOpenArchives = Integer.parseInt(openzipsString);
      if (maxOpenArchives < 20) {
         maxOpenArchives = 1000;
      }

   }

   public class ZipFileEntry extends Entry {
      private File file;
      private ZipFile zipFile;

      public ZipFileEntry(File file) throws IOException {
         this.file = file;
      }

      public ZipFileEntry(ZipFile zipFile) {
         this.zipFile = zipFile;
      }

      public ZipFile getZipFile() {
         return this.zipFile;
      }

      public ClassFile find(String name) throws IOException {
         this.ensureOpen();
         String key = name.replace('.', '/') + ".class";
         ZipEntry entry = this.zipFile.getEntry(key);
         return entry != null ? new ZipEntryClassFile(this, entry) : null;
      }

      public List getAllClassFiles() throws IOException {
         this.ensureOpen();
         List ret = new ArrayList();
         Enumeration e = this.zipFile.entries();

         while(e.hasMoreElements()) {
            ZipEntry entry = (ZipEntry)e.nextElement();
            String name = entry.getName();
            if (ClassPathManager.hasClassExtension(name)) {
               ret.add(new ZipEntryClassFile(this, entry));
            }
         }

         return ret;
      }

      private void ensureOpen() throws IOException {
         if (this.zipFile == null || !ClassPathManager.this.openArchives.contains(this.zipFile) || !this.isReallyOpen()) {
            if (ClassPathManager.this.openArchives.size() >= ClassPathManager.maxOpenArchives) {
               this.closeSomeArchives(ClassPathManager.this.openArchives.size() / 10);
            }

            this.zipFile = new ZipFile(this.file);
            if (!this.isReallyOpen()) {
               throw new FileNotFoundException("Can't open archive: " + this.file.getName() + " (size() check failed)");
            } else {
               ClassPathManager.this.openArchives.add(this.zipFile);
            }
         }
      }

      private boolean isReallyOpen() {
         try {
            this.zipFile.size();
            return true;
         } catch (IllegalStateException var2) {
            return false;
         }
      }

      public void closeSomeArchives(int n) {
         for(int i = n - 1; i >= 0; --i) {
            ZipFile zf = (ZipFile)ClassPathManager.this.openArchives.get(i);

            try {
               zf.close();
            } catch (IOException var5) {
               var5.printStackTrace();
            }

            ClassPathManager.this.openArchives.remove(i);
         }

      }

      public void close() {
         if (this.zipFile != null) {
            try {
               ClassPathManager.this.openArchives.remove(this.zipFile);
               this.zipFile.close();
            } catch (IOException var5) {
               throw new BCException("Can't close archive: " + this.file.getName(), var5);
            } finally {
               this.zipFile = null;
            }

         }
      }

      public String toString() {
         return this.file.getName();
      }
   }

   private static class ZipEntryClassFile extends ClassFile {
      private ZipEntry entry;
      private ZipFileEntry zipFile;
      private InputStream is;

      public ZipEntryClassFile(ZipFileEntry zipFile, ZipEntry entry) {
         this.zipFile = zipFile;
         this.entry = entry;
      }

      public InputStream getInputStream() throws IOException {
         this.is = this.zipFile.getZipFile().getInputStream(this.entry);
         return this.is;
      }

      public void close() {
         try {
            if (this.is != null) {
               this.is.close();
            }
         } catch (IOException var5) {
            var5.printStackTrace();
         } finally {
            this.is = null;
         }

      }

      public String getPath() {
         return this.entry.getName();
      }
   }

   public class DirEntry extends Entry {
      private String dirPath;

      public DirEntry(File dir) {
         this.dirPath = dir.getPath();
      }

      public DirEntry(String dirPath) {
         this.dirPath = dirPath;
      }

      public ClassFile find(String name) {
         File f = new File(this.dirPath + File.separator + name.replace('.', File.separatorChar) + ".class");
         return f.isFile() ? new FileClassFile(f) : null;
      }

      public String toString() {
         return this.dirPath;
      }
   }

   private static class FileClassFile extends ClassFile {
      private File file;
      private FileInputStream fis;

      public FileClassFile(File file) {
         this.file = file;
      }

      public InputStream getInputStream() throws IOException {
         this.fis = new FileInputStream(this.file);
         return this.fis;
      }

      public void close() {
         try {
            if (this.fis != null) {
               this.fis.close();
            }
         } catch (IOException var5) {
            throw new BCException("Can't close class file : " + this.file.getName(), var5);
         } finally {
            this.fis = null;
         }

      }

      public String getPath() {
         return this.file.getPath();
      }
   }

   public abstract static class Entry {
      public abstract ClassFile find(String var1) throws IOException;
   }

   public abstract static class ClassFile {
      public abstract InputStream getInputStream() throws IOException;

      public abstract String getPath();

      public abstract void close();
   }
}
