package weblogic.application.utils;

import com.bea.util.jam.JClass;
import com.bea.util.jam.JamService;
import com.bea.util.jam.JamServiceFactory;
import com.bea.util.jam.JamServiceParams;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import org.apache.tools.ant.taskdefs.Javac;
import weblogic.application.archive.ApplicationArchive;
import weblogic.application.archive.ApplicationArchiveEntry;
import weblogic.application.archive.utils.FileFilters;
import weblogic.application.utils.annotation.AnnotationDetectorAdapter;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.utils.io.DataIO;
import weblogic.utils.jars.VirtualJarFile;

public class AnnotationDetector {
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugAppContainer");
   private final Class[] annotations;
   private final String[] annotationNames;

   public AnnotationDetector(Class... annotations) {
      this.annotations = annotations;
      this.annotationNames = this.computeAnnotationNames();
   }

   public boolean isAnnotated(ZipFile file) throws IOException {
      Enumeration _entries = file == null ? null : file.entries();
      if (_entries == null) {
         return false;
      } else {
         Resource resource = new Resource(file);

         while(_entries.hasMoreElements()) {
            ZipEntry _entry = (ZipEntry)_entries.nextElement();
            if (!_entry.isDirectory()) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Scanning annotations in archive " + file.getName() + " entry " + _entry.getName());
               }

               resource.setEntry(_entry);
               if (_entry.getName().endsWith(".class") && this.isAnnotated(resource.getName(), resource.getContent())) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   private String[] computeAnnotationNames() {
      String[] annotationNames = new String[this.annotations.length];

      for(int i = 0; i < this.annotations.length; ++i) {
         annotationNames[i] = this.annotations[i].getName().replaceAll("\\.", "/");
      }

      return annotationNames;
   }

   public boolean isAnnotated(ApplicationArchive application) throws IOException {
      Iterator var2 = application.find("/", FileFilters.ACCEPT_ALL).iterator();

      while(var2.hasNext()) {
         ApplicationArchiveEntry entry = (ApplicationArchiveEntry)var2.next();
         if (!entry.isDirectory()) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Scanning annotations in archive " + application.getName() + " entry " + entry.getName());
            }

            if (entry.getName().endsWith(".class") && this.isAnnotated(entry.getName(), (new ApplicationArchiveEntryResource(entry)).getContent())) {
               return true;
            }
         }
      }

      return false;
   }

   public boolean isAnnotated(VirtualJarFile vjf) throws IOException {
      Iterator _entries = vjf == null ? null : vjf.entries();
      return _entries == null ? false : this.isAnnotated(vjf, _entries);
   }

   public boolean isAnnotated(VirtualJarFile vjf, Iterator _entries) throws IOException {
      Resource resource = new Resource(vjf);

      ZipEntry _entry;
      do {
         if (!_entries.hasNext()) {
            return false;
         }

         _entry = (ZipEntry)_entries.next();
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Scanning annotations in archive " + vjf.getName() + " entry " + _entry.getName());
         }

         resource.setEntry(_entry);
      } while(!_entry.getName().endsWith(".class") || !this.isAnnotated(resource.getName(), resource.getContent()));

      return true;
   }

   public boolean isAnnotated(File file) throws IOException {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Recursively scanning annotations in directory " + file.getName());
      }

      return this.scan(file, "", new FileResource());
   }

   private boolean scan(File file, String relativePath, FileResource rsrc) throws IOException {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Scanning annotations in file " + file.getName());
      }

      rsrc.setFile(file, relativePath);
      if (file.getName().endsWith(".class") && this.isAnnotated(rsrc.getName(), rsrc.getContent())) {
         return true;
      } else {
         File[] files = file.listFiles();
         if (files != null) {
            for(int i = 0; i < files.length; ++i) {
               String relativePathOfListing = relativePath.length() > 0 ? relativePath + File.separator + files[i].getName() : files[i].getName();
               if (this.scan(files[i], relativePathOfListing, rsrc)) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   private boolean isAnnotated(String resourceName, byte[] bytes) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("sought-after annotations: " + Arrays.toString(this.annotationNames));
      }

      return AnnotationDetectorAdapter.INSTANCE.isAnnotated(resourceName, bytes, this.annotationNames);
   }

   public boolean hasAnnotatedSources(Javac javacTask, File root, String encoding) throws IOException {
      JamServiceFactory factory = JamServiceFactory.getInstance();
      JamServiceParams params = factory.createServiceParams();
      if (encoding != null) {
         params.setCharacterEncoding(encoding);
      }

      this.includeSources(root, params);
      JamService service = factory.createService(params);
      JClass[] sourceObjects = service.getAllClasses(javacTask);
      if (sourceObjects == null) {
         return false;
      } else {
         for(int i = 0; i < sourceObjects.length; ++i) {
            if (this.hasAnnotation(sourceObjects[i])) {
               return true;
            }
         }

         return false;
      }
   }

   private boolean hasAnnotation(JClass jClass) throws IOException {
      for(int i = 0; i < this.annotations.length; ++i) {
         if (jClass.getAnnotation(this.annotations[i]) != null) {
            return true;
         }
      }

      return false;
   }

   private void includeSources(File root, JamServiceParams params) {
      File[] curList = root.listFiles();
      if (curList != null) {
         for(int i = 0; i < curList.length; ++i) {
            if (this.isJavaSource(curList[i])) {
               params.includeSourceFile(curList[i]);
            } else {
               this.includeSources(curList[i], params);
            }
         }

      }
   }

   private boolean isJavaSource(File file) {
      return file.getName().endsWith(".java");
   }

   public boolean isAnnotated(ZipInputStream zis) throws IOException {
      Resource resource = new Resource(zis);
      ZipEntry entry = null;

      while((entry = zis.getNextEntry()) != null) {
         try {
            if (!entry.isDirectory()) {
               resource.setEntry(entry);
               if (entry.getName().endsWith(".class") && this.isAnnotated(resource.getName(), resource.getContent())) {
                  boolean var4 = true;
                  return var4;
               }
            }
         } finally {
            zis.closeEntry();
         }
      }

      return false;
   }

   public static class ApplicationArchiveEntryResource implements MetaDataFilter.Resource {
      private final ApplicationArchiveEntry entry;

      public ApplicationArchiveEntryResource(ApplicationArchiveEntry entry) {
         this.entry = entry;
      }

      public String getName() {
         return this.entry.getName();
      }

      public byte[] getContent() throws IOException {
         byte[] buf = new byte[(int)this.entry.getSize()];
         InputStream in = this.entry.getInputStream();
         DataIO.readFully(in, buf);
         in.close();
         return buf;
      }
   }

   public static class FileResource implements MetaDataFilter.Resource {
      private File _file = null;
      private String _relativePath = null;

      public void setFile(File file, String relativePath) {
         this._file = file;
         this._relativePath = relativePath;
      }

      public String getName() {
         return this._relativePath;
      }

      public byte[] getContent() throws IOException {
         long len = this._file.length();
         FileInputStream fin = new FileInputStream(this._file);

         byte[] var16;
         try {
            byte[] content;
            int size;
            if (len > 0L) {
               content = new byte[(int)len];
               int count = 0;
               size = (int)len;

               int r;
               while((r = fin.read(content, count, size - count)) > 0) {
                  count += r;
                  if (count == size) {
                     break;
                  }
               }
            } else {
               ByteArrayOutputStream bout = new ByteArrayOutputStream();
               byte[] buf = new byte[1024];

               while((size = fin.read(buf)) != -1) {
                  bout.write(buf, 0, size);
               }

               content = bout.toByteArray();
            }

            var16 = content;
         } finally {
            try {
               fin.close();
            } catch (IOException var13) {
            }

         }

         return var16;
      }
   }

   public static class Resource implements MetaDataFilter.Resource {
      private final ZipFile _file;
      private final VirtualJarFile _vjf;
      private final ZipInputStream zip;
      private ZipEntry _entry = null;

      public Resource(ZipFile file) {
         this._file = file;
         this._vjf = null;
         this.zip = null;
      }

      public Resource(VirtualJarFile vjf) {
         this._file = null;
         this._vjf = vjf;
         this.zip = null;
      }

      public Resource(ZipInputStream zip) {
         this.zip = zip;
         this._file = null;
         this._vjf = null;
      }

      public void setEntry(ZipEntry entry) {
         this._entry = entry;
      }

      public String getName() {
         return this._entry.getName();
      }

      public byte[] getContent() throws IOException {
         long size = this._entry.getSize();
         if (size == 0L) {
            return new byte[0];
         } else {
            byte[] content = null;
            InputStream in = null;

            byte[] content;
            try {
               if (this._file != null) {
                  in = this._file.getInputStream(this._entry);
               } else if (this._vjf != null) {
                  in = this._vjf.getInputStream(this._entry);
               } else if (this.zip != null) {
                  in = this.zip;
               }

               int len;
               if (size < 0L) {
                  ByteArrayOutputStream bout = new ByteArrayOutputStream();
                  byte[] buf = new byte[1024];
                  if (in != null) {
                     while((len = ((InputStream)in).read(buf)) != -1) {
                        bout.write(buf, 0, len);
                     }
                  }

                  content = bout.toByteArray();
               } else {
                  content = new byte[(int)size];
                  int count = 0;
                  len = (int)size;
                  int r;
                  if (in != null) {
                     while((r = ((InputStream)in).read(content, count, len - count)) > 0) {
                        count += r;
                        if (count == len) {
                           break;
                        }
                     }
                  }
               }
            } finally {
               if (this.zip == null && in != null) {
                  try {
                     ((InputStream)in).close();
                  } catch (IOException var13) {
                  }
               }

            }

            return content;
         }
      }
   }
}
