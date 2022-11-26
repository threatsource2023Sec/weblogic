package kodo.jdo.jdbc;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.meta.ClassMappingInfo;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.QueryMetaData;
import org.apache.openjpa.meta.SequenceMetaData;
import org.apache.openjpa.util.GeneralException;
import serp.util.Strings;

public class MappingFileDeprecatedJDOMappingFactory extends AbstractDeprecatedJDOMappingFactory {
   private AbstractDeprecatedJDOMappingFactory.MappingAttrsParser _parser = null;
   private boolean _singleFile = false;
   private boolean _parsed = false;
   private String _fileName = "package.mapping";

   public boolean isSingleFile() {
      return this._singleFile;
   }

   public void setSingleFile(boolean singleFile) {
      this._singleFile = singleFile;
   }

   public String getFileName() {
      return this._fileName;
   }

   public void setFileName(String fileName) {
      this._fileName = fileName;
   }

   public void clear() {
      super.clear();
      if (this._parser != null) {
         this._parser.clear();
      }

      this._parsed = false;
   }

   public void load(Class cls, int mode, ClassLoader envLoader) {
      if (mode != 2) {
         super.load(cls, mode & -3, envLoader);
      }

      if (cls != null && (mode & 2) != 0) {
         if (this._parser == null) {
            this._parser = this.newMappingParser();
         }

         AbstractDeprecatedJDOMappingFactory.MappingAttrsRepository arepos = this._parser.getRepository();
         ClassLoader loader = this.repos.getConfiguration().getClassResolverInstance().getClassLoader(cls, envLoader);
         AbstractDeprecatedJDOMappingFactory.ClassMappingAttrs cattrs = arepos.getMapping(cls.getName(), false);
         if (cattrs == null) {
            this._parsed = this.parse(cls, this._parser, loader, this._parsed);
            cattrs = arepos.getMapping(cls.getName(), false);
            if (cattrs == null) {
               return;
            }
         }

         ClassMetaData meta = this.repos.getCachedMetaData(cls);
         if (meta == null) {
            if ((mode & 1) != 0) {
               return;
            }

            meta = this.repos.addMetaData(cls);
            meta.setEnvClassLoader(envLoader);
         }

         this.fromMappingAttrs((ClassMapping)meta, cattrs, loader);
      }
   }

   private boolean parse(Class cls, AbstractDeprecatedJDOMappingFactory.MappingAttrsParser parser, ClassLoader loader, boolean parsed) {
      if (this._singleFile && parsed) {
         return true;
      } else {
         parser.setClassLoader(loader);
         if (this._singleFile) {
            try {
               parser.parse(this._fileName);
               return true;
            } catch (IOException var6) {
               throw new GeneralException(var6);
            }
         } else {
            this.parse(parser, new Class[]{cls});
            return false;
         }
      }
   }

   public boolean store(ClassMetaData[] metas, QueryMetaData[] queries, SequenceMetaData[] seqs, int mode, Map output) {
      if (mode != 2 && !super.store(metas, queries, seqs, mode & -3, output)) {
         return false;
      } else if ((mode & 2) == 0) {
         return true;
      } else {
         Class cls = metas.length == 0 ? null : metas[0].getDescribedType();
         ClassLoader loader = this.repos.getConfiguration().getClassResolverInstance().getClassLoader(cls, (ClassLoader)null);
         Set mapFiles = this.assignDefaultMappingFiles(metas);
         AbstractDeprecatedJDOMappingFactory.MappingAttrsSerializer ser = this.newMappingSerializer();
         if (mapFiles != null) {
            AbstractDeprecatedJDOMappingFactory.MappingAttrsParser parser = this.newMappingParser();
            parser.setClassLoader(loader);
            this.parse(parser, mapFiles);
            ser.setRepository(parser.getRepository());
         }

         for(int i = 0; i < metas.length; ++i) {
            ser.getRepository().addMapping(this.toMappingAttrs((ClassMapping)metas[i]));
         }

         this.serialize(ser, output, 1);
         return true;
      }
   }

   private Set assignDefaultMappingFiles(ClassMetaData[] metas) {
      Set files = null;

      for(int i = 0; i < metas.length; ++i) {
         ClassMappingInfo info = ((ClassMapping)metas[i]).getMappingInfo();
         if (info.getSourceFile() == null) {
            info.setSource(this.defaultMappingFile(metas[i]), info.getSourceType());
         }

         if (info.getSourceFile().exists()) {
            if (files == null) {
               files = new HashSet();
            }

            files.add(info.getSourceFile());
         }
      }

      return files;
   }

   private File defaultMappingFile(ClassMetaData meta) {
      File file = meta.getSourceFile();
      if (file == null) {
         file = super.defaultSourceFile(meta);
      }

      String name = Strings.getPackageName(file.getName());
      return new File(file.getParentFile(), name + ".mapping");
   }

   public boolean drop(Class[] cls, int mode, ClassLoader envLoader) {
      boolean drop = true;
      if (mode != 2) {
         drop = super.drop(cls, mode & -3, envLoader);
      }

      if ((mode & 2) == 0) {
         return drop;
      } else {
         AbstractDeprecatedJDOMappingFactory.MappingAttrsParser parser = this.newMappingParser();
         AbstractDeprecatedJDOMappingFactory.MappingAttrsRepository arepos = parser.getRepository();
         boolean parsed = false;

         for(int i = 0; i < cls.length; ++i) {
            if (cls[i] != null) {
               ClassLoader loader = this.repos.getConfiguration().getClassResolverInstance().getClassLoader(cls[i], envLoader);
               parsed = this.parse(cls[i], parser, loader, parsed);
            }
         }

         Set files = new HashSet();

         for(int i = 0; i < cls.length; ++i) {
            if (cls[i] != null) {
               AbstractDeprecatedJDOMappingFactory.ClassMappingAttrs attrs = arepos.getMapping(cls[i].getName(), false);
               if (attrs != null && attrs.getSourceFile() != null) {
                  files.add(attrs.getSourceFile());
               }

               arepos.removeMapping(cls[i].getName());
            }
         }

         this.backupAndDelete(files);
         AbstractDeprecatedJDOMappingFactory.MappingAttrsSerializer ser = this.newMappingSerializer();
         ser.setRepository(arepos);
         this.serialize(ser, (Map)null, 1);
         return drop;
      }
   }

   protected String getMetaDataSuffix() {
      return ".mapping";
   }
}
