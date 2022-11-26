package weblogic.application.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import weblogic.application.archive.ApplicationArchive;
import weblogic.application.archive.ApplicationArchiveEntry;
import weblogic.application.archive.utils.FileFilters;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.utils.jars.VirtualJarFile;

public class AnnotationScanner {
   private final MetaDataFilter filter;
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugAppContainer");
   private final Class[] annotations;

   public AnnotationScanner(Class[] annotations) {
      this.annotations = annotations;
      this.filter = new ClassAnnotationMetaDataFilter(annotations, true);
   }

   public Map scan(ZipFile file) throws IOException {
      Map annotatedMap = null;
      Enumeration _entries = file == null ? null : file.entries();
      if (_entries == null) {
         return annotatedMap;
      } else {
         AnnotationDetector.Resource resource = new AnnotationDetector.Resource(file);

         while(_entries.hasMoreElements()) {
            ZipEntry _entry = (ZipEntry)_entries.nextElement();
            if (!_entry.isDirectory()) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Scanning annotations in archive " + file.getName() + " entry " + _entry.getName());
               }

               resource.setEntry(_entry);
               List match = this.filter.matches(resource);
               if (match != null) {
                  if (annotatedMap == null) {
                     annotatedMap = new HashMap();
                  }

                  this.processAnnotationList(annotatedMap, resource.getName(), match);
               }
            }
         }

         return annotatedMap;
      }
   }

   private void processAnnotationList(Map master, String entryName, List listToBeProcessed) {
      Object annotatedEntries;
      for(Iterator var4 = listToBeProcessed.iterator(); var4.hasNext(); ((List)annotatedEntries).add(entryName)) {
         Class annotationFound = (Class)var4.next();
         if (!master.containsKey(annotationFound)) {
            annotatedEntries = new ArrayList();
            master.put(annotationFound, annotatedEntries);
         } else {
            annotatedEntries = (List)master.get(annotationFound);
         }
      }

   }

   public Map scan(VirtualJarFile vjf) throws IOException {
      Iterator _entries = vjf == null ? null : vjf.entries();
      return _entries == null ? null : this.scan(vjf, _entries);
   }

   private Map scan(VirtualJarFile vjf, Iterator _entries) throws IOException {
      Map annotatedMap = null;
      AnnotationDetector.Resource resource = new AnnotationDetector.Resource(vjf);

      while(_entries.hasNext()) {
         ZipEntry _entry = (ZipEntry)_entries.next();
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Scanning annotations in archive " + vjf.getName() + " entry " + _entry.getName());
         }

         resource.setEntry(_entry);
         List match = this.filter.matches(resource);
         if (match != null) {
            if (annotatedMap == null) {
               annotatedMap = new HashMap();
            }

            this.processAnnotationList(annotatedMap, resource.getName(), match);
         }
      }

      return annotatedMap;
   }

   public Map scan(File file) throws IOException {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Recursively scanning annotations in directory " + file.getName());
      }

      Map annotatedMap = new HashMap();
      this.scan(annotatedMap, file, "", this.filter, new AnnotationDetector.FileResource());
      return !annotatedMap.isEmpty() ? annotatedMap : null;
   }

   private void scan(Map annotatedMap, File file, String relativePath, MetaDataFilter filter, AnnotationDetector.FileResource rsrc) throws IOException {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Scanning annotations in file " + file.getName());
      }

      rsrc.setFile(file, relativePath);
      if (!file.isDirectory()) {
         List match = filter.matches(rsrc);
         if (match != null) {
            this.processAnnotationList(annotatedMap, rsrc.getName(), match);
         }
      } else {
         File[] files = file.listFiles();
         if (files != null) {
            for(int i = 0; i < files.length; ++i) {
               String relativePathOfListing = relativePath.length() > 0 ? relativePath + File.separator + files[i].getName() : files[i].getName();
               this.scan(annotatedMap, files[i], relativePathOfListing, filter, rsrc);
            }
         }
      }

   }

   public Map scan(ZipInputStream zis) throws IOException {
      AnnotationDetector.Resource resource = new AnnotationDetector.Resource(zis);
      ZipEntry entry = null;
      Map annotatedMap = null;

      while((entry = zis.getNextEntry()) != null) {
         try {
            if (!entry.isDirectory()) {
               resource.setEntry(entry);
               List match = this.filter.matches(resource);
               if (match != null) {
                  if (annotatedMap == null) {
                     annotatedMap = new HashMap();
                  }

                  this.processAnnotationList(annotatedMap, resource.getName(), match);
               }
            }
         } finally {
            zis.closeEntry();
         }
      }

      return annotatedMap;
   }

   public Map scan(ApplicationArchive archive) throws IOException {
      Map annotatedMap = null;
      Iterator var3 = archive.find("/", FileFilters.ACCEPT_ALL).iterator();

      while(var3.hasNext()) {
         ApplicationArchiveEntry entry = (ApplicationArchiveEntry)var3.next();
         if (!entry.isDirectory()) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Scanning annotations in archive " + archive.getName() + " entry " + entry.getName());
            }

            List match = this.filter.matches(new AnnotationDetector.ApplicationArchiveEntryResource(entry));
            if (match != null) {
               if (annotatedMap == null) {
                  annotatedMap = new HashMap();
               }

               this.processAnnotationList(annotatedMap, entry.getName(), match);
            }
         }
      }

      return annotatedMap;
   }
}
