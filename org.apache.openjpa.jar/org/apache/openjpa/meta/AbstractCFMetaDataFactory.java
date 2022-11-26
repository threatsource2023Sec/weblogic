package org.apache.openjpa.meta;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.lib.meta.ClassArgParser;
import org.apache.openjpa.lib.meta.ClasspathMetaDataIterator;
import org.apache.openjpa.lib.meta.FileMetaDataIterator;
import org.apache.openjpa.lib.meta.MetaDataFilter;
import org.apache.openjpa.lib.meta.MetaDataIterator;
import org.apache.openjpa.lib.meta.MetaDataParser;
import org.apache.openjpa.lib.meta.MetaDataSerializer;
import org.apache.openjpa.lib.meta.ResourceMetaDataIterator;
import org.apache.openjpa.lib.meta.URLMetaDataIterator;
import org.apache.openjpa.lib.meta.ZipFileMetaDataIterator;
import org.apache.openjpa.lib.meta.ZipStreamMetaDataIterator;
import org.apache.openjpa.lib.util.Files;
import org.apache.openjpa.lib.util.J2DoPrivHelper;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.GeneralException;
import org.apache.openjpa.util.UserException;
import serp.util.Strings;

public abstract class AbstractCFMetaDataFactory extends AbstractMetaDataFactory {
   private static final Localizer _loc = Localizer.forPackage(AbstractMetaDataFactory.class);
   protected Collection files = null;
   protected Collection urls = null;
   protected Collection rsrcs = null;
   protected Collection cpath = null;
   private Set _typeNames = null;

   public void setFiles(Collection files) {
      this.files = files;
   }

   public void setFiles(String files) {
      if (StringUtils.isEmpty(files)) {
         this.files = null;
      } else {
         String[] strs = Strings.split(files, ";", 0);
         this.files = new HashSet((int)((double)strs.length * 1.33 + 1.0));

         for(int i = 0; i < strs.length; ++i) {
            File file = new File(strs[i]);
            if ((Boolean)AccessController.doPrivileged(J2DoPrivHelper.existsAction(file))) {
               this.files.add(file);
            }
         }
      }

   }

   public void setURLs(Collection urls) {
      this.urls = urls;
   }

   public void setURLs(String urls) {
      if (StringUtils.isEmpty(urls)) {
         this.urls = null;
      } else {
         String[] strs = Strings.split(urls, ";", 0);
         this.urls = new HashSet((int)((double)strs.length * 1.33 + 1.0));

         try {
            for(int i = 0; i < strs.length; ++i) {
               this.urls.add(new URL(strs[i]));
            }
         } catch (MalformedURLException var4) {
            throw new UserException(var4);
         }
      }

   }

   public void setResources(Collection rsrcs) {
      this.rsrcs = rsrcs;
   }

   public void setResources(String rsrcs) {
      this.rsrcs = StringUtils.isEmpty(rsrcs) ? null : new ArrayList(Arrays.asList(Strings.split(rsrcs, ";", 0)));
   }

   public void setClasspathScan(Collection cpath) {
      this.cpath = cpath;
   }

   public void setClasspathScan(String cpath) {
      this.cpath = StringUtils.isEmpty(cpath) ? null : new ArrayList(Arrays.asList(Strings.split(cpath, ";", 0)));
   }

   public boolean store(ClassMetaData[] metas, QueryMetaData[] queries, SequenceMetaData[] seqs, int mode, Map output) {
      if (mode == 0) {
         return true;
      } else if (this.isMappingOnlyFactory() && (mode & 2) == 0) {
         return true;
      } else {
         if (!this.strict && (mode & 1) != 0) {
            mode |= 2;
         }

         Class cls = metas.length == 0 ? null : metas[0].getDescribedType();
         ClassLoader loader = this.repos.getConfiguration().getClassResolverInstance().getClassLoader(cls, (ClassLoader)null);
         Map clsNames = new HashMap((int)((double)metas.length * 1.33 + 1.0));

         for(int i = 0; i < metas.length; ++i) {
            clsNames.put(metas[i].getDescribedType().getName(), metas[i]);
         }

         Set metaFiles = null;
         Set queryFiles = null;
         if (this.isMappingOnlyFactory() || (mode & 1) != 0) {
            metaFiles = this.assignDefaultMetaDataFiles(metas, queries, seqs, mode, clsNames);
         }

         if (!this.isMappingOnlyFactory() && (mode & 4) != 0) {
            queryFiles = this.assignDefaultQueryFiles(queries, clsNames);
         }

         Serializer ser;
         Parser parser;
         int i;
         if (mode != 4) {
            int sermode = this.isMappingOnlyFactory() ? mode : mode | 1;
            if ((mode & 16) != 0) {
               ser = this.newAnnotationSerializer();
            } else {
               ser = this.newSerializer();
            }

            ser.setMode(sermode);
            if (metaFiles != null) {
               parser = this.newParser(false);
               parser.setMode(sermode);
               parser.setClassLoader(loader);
               this.parse(parser, (Collection)metaFiles);
               MetaDataRepository pr = parser.getRepository();
               pr.setSourceMode(mode);
               if (this.isMappingOnlyFactory()) {
                  pr.setResolve(0);
               } else {
                  pr.setResolve(2, false);
               }

               ser.addAll(pr);
            }

            for(i = 0; i < metas.length; ++i) {
               ser.addMetaData(metas[i]);
            }

            if ((mode & 2) != 0) {
               for(i = 0; i < seqs.length; ++i) {
                  ser.addSequenceMetaData(seqs[i]);
               }
            }

            for(i = 0; i < queries.length; ++i) {
               if (queries[i].getSourceMode() != 4 && (queries[i].getSourceMode() & mode) != 0) {
                  ser.addQueryMetaData(queries[i]);
               }
            }

            i = 1;
            if ((this.store & 2) != 0) {
               i |= 4;
            }

            this.serialize(ser, output, i);
         }

         if (!this.isMappingOnlyFactory()) {
            boolean qFiles = queryFiles != null;

            for(i = 0; !qFiles && i < queries.length; ++i) {
               qFiles = queries[i].getSourceMode() == 4;
            }

            if (qFiles) {
               if ((mode & 16) != 0) {
                  ser = this.newAnnotationSerializer();
               } else {
                  ser = this.newSerializer();
               }

               ser.setMode(4);
               if (queryFiles != null) {
                  parser = this.newParser(false);
                  parser.setMode(4);
                  parser.setClassLoader(loader);
                  this.parse(parser, (Collection)queryFiles);
                  ser.addAll(parser.getRepository());
               }

               for(i = 0; i < queries.length; ++i) {
                  if (queries[i].getSourceMode() == 4) {
                     ser.addQueryMetaData(queries[i]);
                  }
               }

               this.serialize(ser, output, 1);
            }
         }

         return true;
      }
   }

   public boolean drop(Class[] cls, int mode, ClassLoader envLoader) {
      if (mode == 0) {
         return true;
      } else if (this.isMappingOnlyFactory() && (mode & 2) == 0) {
         return true;
      } else {
         Parser parser = this.newParser(false);
         MetaDataRepository pr = parser.getRepository();
         pr.setSourceMode(2, false);
         pr.setResolve(2, false);
         if ((mode & 3) != 0) {
            parser.setMode(this.isMappingOnlyFactory() ? mode : 7);
            this.parse(parser, (Class[])cls);
         }

         if (!this.isMappingOnlyFactory() && (mode & 4) != 0) {
            parser.setMode(4);
            this.parse(parser, (Class[])cls);
         }

         Set files = new HashSet();
         Set clsNames = null;
         if ((mode & 3) != 0) {
            clsNames = new HashSet((int)((double)cls.length * 1.33 + 1.0));

            for(int i = 0; i < cls.length; ++i) {
               if (cls[i] == null) {
                  clsNames.add((Object)null);
               } else {
                  clsNames.add(cls[i].getName());
               }

               ClassMetaData meta = pr.getMetaData(cls[i], envLoader, false);
               if (meta != null) {
                  if (this.getSourceFile(meta) != null) {
                     files.add(this.getSourceFile(meta));
                  }

                  if ((mode & 1) != 0) {
                     pr.removeMetaData(meta);
                  } else if (!this.isMappingOnlyFactory()) {
                     this.clearMapping(meta);
                  }
               }
            }
         }

         QueryMetaData[] queries = pr.getQueryMetaDatas();
         List qqs = !this.isMappingOnlyFactory() && (mode & 4) == 0 ? null : new ArrayList();

         for(int i = 0; i < queries.length; ++i) {
            if (!this.isMappingOnlyFactory() && queries[i].getSourceFile() != null) {
               files.add(queries[i].getSourceFile());
            }

            Class def = queries[i].getDefiningType();
            boolean rem = (queries[i].getSourceMode() & mode) != 0 && clsNames.contains(def == null ? null : def.getName());
            if (rem || !this.isMappingOnlyFactory() && queries[i].getSourceMode() == 4) {
               pr.removeQueryMetaData(queries[i]);
            }

            if (qqs != null && queries[i].getSourceMode() == 4 && !rem) {
               qqs.add(queries[i]);
            }
         }

         this.backupAndDelete(files);
         int i;
         Serializer ser;
         if ((mode & 3) != 0) {
            ser = this.newSerializer();
            ser.setMode(this.isMappingOnlyFactory() ? mode : mode | 1);
            ser.addAll(pr);
            if (this.isMappingOnlyFactory()) {
               for(i = 0; i < cls.length; ++i) {
                  ser.removeMetaData(pr.getMetaData(cls[i], envLoader, false));
               }
            }

            this.serialize(ser, (Map)null, 1);
         }

         if (qqs != null && !qqs.isEmpty()) {
            ser = this.newSerializer();
            ser.setMode(4);

            for(i = 0; i < qqs.size(); ++i) {
               ser.addQueryMetaData((QueryMetaData)qqs.get(i));
            }

            this.serialize(ser, (Map)null, 1);
         }

         return true;
      }
   }

   private Set assignDefaultMetaDataFiles(ClassMetaData[] metas, QueryMetaData[] queries, SequenceMetaData[] seqs, int mode, Map clsNames) {
      Set files = null;

      int i;
      for(i = 0; i < metas.length; ++i) {
         if (this.getSourceFile(metas[i]) == null) {
            this.setSourceFile(metas[i], this.defaultSourceFile(metas[i]));
         }

         if ((Boolean)AccessController.doPrivileged(J2DoPrivHelper.existsAction(this.getSourceFile(metas[i])))) {
            if (files == null) {
               files = new HashSet();
            }

            files.add(this.getSourceFile(metas[i]));
         }
      }

      for(i = 0; i < queries.length; ++i) {
         if (queries[i].getSourceMode() != 4 && (mode & queries[i].getSourceMode()) != 0) {
            if (queries[i].getSourceFile() == null) {
               queries[i].setSource(this.defaultSourceFile(queries[i], clsNames), queries[i].getSourceScope(), queries[i].getSourceType());
            }

            if ((Boolean)AccessController.doPrivileged(J2DoPrivHelper.existsAction(queries[i].getSourceFile()))) {
               if (files == null) {
                  files = new HashSet();
               }

               files.add(queries[i].getSourceFile());
            }
         }
      }

      if ((mode & 2) != 0) {
         for(i = 0; i < seqs.length; ++i) {
            if (this.getSourceFile(seqs[i]) == null) {
               this.setSourceFile(seqs[i], this.defaultSourceFile(seqs[i], clsNames));
            }

            if ((Boolean)AccessController.doPrivileged(J2DoPrivHelper.existsAction(this.getSourceFile(seqs[i])))) {
               if (files == null) {
                  files = new HashSet();
               }

               files.add(this.getSourceFile(seqs[i]));
            }
         }
      }

      return files;
   }

   private Set assignDefaultQueryFiles(QueryMetaData[] queries, Map clsNames) {
      Set files = null;

      for(int i = 0; i < queries.length; ++i) {
         if (queries[i].getSourceMode() == 4) {
            if (queries[i].getSourceFile() == null) {
               queries[i].setSource(this.defaultSourceFile(queries[i], clsNames), queries[i].getSourceScope(), queries[i].getSourceType());
            }

            if ((Boolean)AccessController.doPrivileged(J2DoPrivHelper.existsAction(queries[i].getSourceFile()))) {
               if (files == null) {
                  files = new HashSet();
               }

               files.add(queries[i].getSourceFile());
            }
         }
      }

      return files;
   }

   protected boolean isMappingOnlyFactory() {
      return false;
   }

   protected void parse(MetaDataParser parser, Collection files) {
      try {
         Iterator itr = files.iterator();

         while(itr.hasNext()) {
            parser.parse((File)itr.next());
         }

      } catch (IOException var4) {
         throw new GeneralException(var4);
      }
   }

   protected void parse(MetaDataParser parser, Class[] cls) {
      try {
         for(int i = 0; i < cls.length; ++i) {
            parser.parse(cls[i], this.isParseTopDown());
         }

      } catch (IOException var4) {
         throw new GeneralException(var4);
      }
   }

   protected boolean isParseTopDown() {
      return false;
   }

   protected void serialize(MetaDataSerializer ser, Map output, int flags) {
      try {
         if (output == null) {
            ser.serialize(flags);
         } else {
            ser.serialize(output, flags);
         }

      } catch (IOException var5) {
         throw new GeneralException(var5);
      }
   }

   protected void backupAndDelete(Collection files) {
      Iterator itr = files.iterator();

      while(itr.hasNext()) {
         File file = (File)itr.next();
         if (Files.backup(file, false) != null) {
            AccessController.doPrivileged(J2DoPrivHelper.deleteAction(file));
         }
      }

   }

   protected void clearMapping(ClassMetaData meta) {
      meta.setSourceMode(2, false);
   }

   protected File getSourceFile(ClassMetaData meta) {
      return meta.getSourceFile();
   }

   protected void setSourceFile(ClassMetaData meta, File sourceFile) {
      meta.setSource(sourceFile, meta.getSourceType());
   }

   protected File getSourceFile(SequenceMetaData meta) {
      return meta.getSourceFile();
   }

   protected void setSourceFile(SequenceMetaData meta, File sourceFile) {
      meta.setSource(sourceFile, meta.getSourceScope(), meta.getSourceType());
   }

   protected abstract File defaultSourceFile(ClassMetaData var1);

   protected abstract File defaultSourceFile(QueryMetaData var1, Map var2);

   protected abstract File defaultSourceFile(SequenceMetaData var1, Map var2);

   protected abstract Parser newParser(boolean var1);

   protected abstract Serializer newSerializer();

   protected abstract Serializer newAnnotationSerializer();

   protected ClassMetaData getDefiningMetaData(QueryMetaData query, Map clsNames) {
      Class def = query.getDefiningType();
      if (def != null) {
         return (ClassMetaData)clsNames.get(def.getName());
      } else {
         Iterator itr = clsNames.entrySet().iterator();

         Map.Entry entry;
         String pkg;
         do {
            if (!itr.hasNext()) {
               return null;
            }

            entry = (Map.Entry)itr.next();
            pkg = Strings.getPackageName((String)entry.getKey());
         } while(pkg.length() != 0);

         return (ClassMetaData)entry.getValue();
      }
   }

   public Set getPersistentTypeNames(boolean devpath, ClassLoader envLoader) {
      if (this._typeNames != null) {
         return this._typeNames.isEmpty() ? null : this._typeNames;
      } else {
         try {
            ClassLoader loader = this.repos.getConfiguration().getClassResolverInstance().getClassLoader(this.getClass(), envLoader);
            long start = System.currentTimeMillis();
            Set names = this.parsePersistentTypeNames(loader);
            if (names.isEmpty() && devpath) {
               this.scan(new ClasspathMetaDataIterator((String[])null, this.newMetaDataFilter()), this.newClassArgParser(), names, false, (Object)null);
            } else {
               this._typeNames = names;
            }

            if (this.log.isTraceEnabled()) {
               this.log.trace(_loc.get("found-pcs", String.valueOf(names.size()), String.valueOf(System.currentTimeMillis() - start)));
            }

            return names.isEmpty() ? null : names;
         } catch (IOException var7) {
            throw new GeneralException(var7);
         }
      }
   }

   private Set parsePersistentTypeNames(ClassLoader loader) throws IOException {
      ClassArgParser cparser = this.newClassArgParser();
      Set names = new HashSet();
      String[] clss;
      Iterator itr;
      File file;
      if (this.files != null) {
         itr = this.files.iterator();

         while(itr.hasNext()) {
            File file = (File)itr.next();
            if ((Boolean)AccessController.doPrivileged(J2DoPrivHelper.isDirectoryAction(file))) {
               if (this.log.isTraceEnabled()) {
                  this.log.trace(_loc.get("scanning-directory", (Object)file));
               }

               this.scan(new FileMetaDataIterator(file, this.newMetaDataFilter()), cparser, names, true, file);
            } else if (file.getName().endsWith(".jar")) {
               if (this.log.isTraceEnabled()) {
                  this.log.trace(_loc.get("scanning-jar", (Object)file));
               }

               try {
                  ZipFile zFile = (ZipFile)AccessController.doPrivileged(J2DoPrivHelper.newZipFileAction(file));
                  this.scan(new ZipFileMetaDataIterator(zFile, this.newMetaDataFilter()), cparser, names, true, file);
               } catch (PrivilegedActionException var13) {
                  throw (IOException)var13.getException();
               }
            } else {
               if (this.log.isTraceEnabled()) {
                  this.log.trace(_loc.get("scanning-file", (Object)file));
               }

               clss = cparser.parseTypeNames((MetaDataIterator)(new FileMetaDataIterator(file)));
               if (this.log.isTraceEnabled()) {
                  this.log.trace(_loc.get("scan-found-names", clss, file));
               }

               names.addAll(Arrays.asList(clss));
               file = (File)AccessController.doPrivileged(J2DoPrivHelper.getAbsoluteFileAction(file));

               try {
                  this.mapPersistentTypeNames(AccessController.doPrivileged(J2DoPrivHelper.toURLAction(file)), clss);
               } catch (PrivilegedActionException var12) {
                  throw (FileNotFoundException)var12.getException();
               }
            }
         }
      }

      URL url;
      if (this.urls != null) {
         itr = this.urls.iterator();

         label155:
         while(true) {
            while(true) {
               if (!itr.hasNext()) {
                  break label155;
               }

               url = (URL)itr.next();
               if ("file".equals(url.getProtocol())) {
                  file = (File)AccessController.doPrivileged(J2DoPrivHelper.getAbsoluteFileAction(new File(url.getFile())));
                  if (this.files != null && this.files.contains(file)) {
                     continue;
                  }

                  if ((Boolean)AccessController.doPrivileged(J2DoPrivHelper.isDirectoryAction(file))) {
                     if (this.log.isTraceEnabled()) {
                        this.log.trace(_loc.get("scanning-directory", (Object)file));
                     }

                     this.scan(new FileMetaDataIterator(file, this.newMetaDataFilter()), cparser, names, true, file);
                     continue;
                  }
               }

               if ("jar".equals(url.getProtocol()) && url.getPath().endsWith("!/")) {
                  if (this.log.isTraceEnabled()) {
                     this.log.trace(_loc.get("scanning-jar-url", (Object)url));
                  }

                  this.scan(new ZipFileMetaDataIterator(url, this.newMetaDataFilter()), cparser, names, true, url);
               } else if (url.getPath().endsWith(".jar")) {
                  if (this.log.isTraceEnabled()) {
                     this.log.trace(_loc.get("scanning-jar-at-url", (Object)url));
                  }

                  try {
                     InputStream is = (InputStream)AccessController.doPrivileged(J2DoPrivHelper.openStreamAction(url));
                     this.scan(new ZipStreamMetaDataIterator(new ZipInputStream(is), this.newMetaDataFilter()), cparser, names, true, url);
                  } catch (PrivilegedActionException var11) {
                     throw (IOException)var11.getException();
                  }
               } else {
                  if (this.log.isTraceEnabled()) {
                     this.log.trace(_loc.get("scanning-url", (Object)url));
                  }

                  clss = cparser.parseTypeNames((MetaDataIterator)(new URLMetaDataIterator(url)));
                  if (this.log.isTraceEnabled()) {
                     this.log.trace(_loc.get("scan-found-names", clss, url));
                  }

                  names.addAll(Arrays.asList(clss));
                  this.mapPersistentTypeNames(url, clss);
               }
            }
         }
      }

      if (this.rsrcs != null) {
         Iterator itr = this.rsrcs.iterator();

         label136:
         while(true) {
            while(true) {
               if (!itr.hasNext()) {
                  break label136;
               }

               String rsrc = (String)itr.next();
               if (rsrc.endsWith(".jar")) {
                  url = (URL)AccessController.doPrivileged(J2DoPrivHelper.getResourceAction(loader, rsrc));
                  if (url != null) {
                     if (this.log.isTraceEnabled()) {
                        this.log.trace(_loc.get("scanning-jar-stream-url", (Object)url));
                     }

                     try {
                        InputStream is = (InputStream)AccessController.doPrivileged(J2DoPrivHelper.openStreamAction(url));
                        this.scan(new ZipStreamMetaDataIterator(new ZipInputStream(is), this.newMetaDataFilter()), cparser, names, true, url);
                     } catch (PrivilegedActionException var10) {
                        throw (IOException)var10.getException();
                     }
                  }
               } else {
                  if (this.log.isTraceEnabled()) {
                     this.log.trace(_loc.get("scanning-resource", (Object)rsrc));
                  }

                  MetaDataIterator mitr = new ResourceMetaDataIterator(rsrc, loader);

                  while(mitr.hasNext()) {
                     url = (URL)mitr.next();
                     clss = cparser.parseTypeNames((MetaDataIterator)(new URLMetaDataIterator(url)));
                     if (this.log.isTraceEnabled()) {
                        this.log.trace(_loc.get("scan-found-names", clss, rsrc));
                     }

                     names.addAll(Arrays.asList(clss));
                     this.mapPersistentTypeNames(url, clss);
                  }

                  mitr.close();
               }
            }
         }
      }

      if (this.cpath != null) {
         String[] dirs = (String[])((String[])this.cpath.toArray(new String[this.cpath.size()]));
         this.scan(new ClasspathMetaDataIterator(dirs, this.newMetaDataFilter()), cparser, names, true, dirs);
      }

      if (this.types != null) {
         names.addAll(this.types);
      }

      if (this.log.isTraceEnabled()) {
         this.log.trace(_loc.get("parse-found-names", (Object)names));
      }

      return names;
   }

   private void scan(MetaDataIterator mitr, ClassArgParser cparser, Set names, boolean mapNames, Object debugContext) throws IOException {
      Map map;
      try {
         map = cparser.mapTypeNames(mitr);
      } finally {
         mitr.close();
      }

      List newNames;
      for(Iterator itr = map.entrySet().iterator(); itr.hasNext(); names.addAll(newNames)) {
         Map.Entry entry = (Map.Entry)itr.next();
         if (mapNames) {
            this.mapPersistentTypeNames(entry.getKey(), (String[])((String[])entry.getValue()));
         }

         newNames = Arrays.asList((String[])((String[])entry.getValue()));
         if (this.log.isTraceEnabled()) {
            this.log.trace(_loc.get("scan-found-names", newNames, debugContext));
         }
      }

   }

   protected void mapPersistentTypeNames(Object rsrc, String[] names) {
   }

   protected abstract MetaDataFilter newMetaDataFilter();

   public void clear() {
      super.clear();
      this._typeNames = null;
   }

   public interface Serializer extends MetaDataSerializer {
      void setMode(int var1);

      void addMetaData(ClassMetaData var1);

      boolean removeMetaData(ClassMetaData var1);

      void addSequenceMetaData(SequenceMetaData var1);

      void addQueryMetaData(QueryMetaData var1);

      void addAll(MetaDataRepository var1);
   }

   public interface Parser extends MetaDataParser {
      MetaDataRepository getRepository();

      void setMode(int var1);
   }
}
