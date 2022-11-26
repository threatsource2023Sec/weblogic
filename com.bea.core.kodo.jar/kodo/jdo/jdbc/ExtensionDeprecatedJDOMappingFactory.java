package kodo.jdo.jdbc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.Extensions;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.meta.QueryMetaData;
import org.apache.openjpa.meta.SequenceMetaData;
import serp.util.Strings;

public class ExtensionDeprecatedJDOMappingFactory extends AbstractDeprecatedJDOMappingFactory {
   public void load(Class cls, int mode, ClassLoader envLoader) {
      if ((mode & 2) != 0) {
         mode |= 1;
      }

      super.load(cls, mode & -3, envLoader);
      if (cls != null && (mode & 2) != 0) {
         ClassMetaData meta = this.repos.getCachedMetaData(cls);
         if (meta != null) {
            String type = meta.getStringExtension("kodo", "jdbc-class-map");
            if (type != null) {
               AbstractDeprecatedJDOMappingFactory.ClassMappingAttrs cattrs = new AbstractDeprecatedJDOMappingFactory.ClassMappingAttrs(meta.getDescribedType().getName());
               cattrs.type = type;
               Extensions exts = meta.getEmbeddedExtensions("kodo", "jdbc-class-map", false);
               this.fromExtensions(cattrs, exts);
               type = meta.getStringExtension("kodo", "jdbc-version-ind");
               if (type != null) {
                  cattrs.version.type = type;
                  exts = meta.getEmbeddedExtensions("kodo", "jdbc-version-ind", false);
                  this.fromExtensions(cattrs.version, exts);
               }

               type = meta.getStringExtension("kodo", "jdbc-class-ind");
               if (type != null) {
                  cattrs.discriminator.type = type;
                  exts = meta.getEmbeddedExtensions("kodo", "jdbc-class-ind", false);
                  this.fromExtensions(cattrs.discriminator, exts);
               }

               FieldMetaData[] fmds = meta.getDeclaredFields();

               for(int i = 0; i < fmds.length; ++i) {
                  this.fieldFromExtensions(cattrs, fmds[i].getName(), fmds[i]);
               }

               Extensions defFieldExts = meta.getEmbeddedExtensions("kodo", "jdbc-field-mappings", false);
               if (defFieldExts != null) {
                  String[] fieldNames = defFieldExts.getExtensionKeys("kodo");

                  for(int i = 0; fieldNames != null && i < fieldNames.length; ++i) {
                     exts = defFieldExts.getEmbeddedExtensions("kodo", fieldNames[i], false);
                     this.fieldFromExtensions(cattrs, fieldNames[i], exts);
                  }
               }

               ClassLoader loader = this.repos.getConfiguration().getClassResolverInstance().getClassLoader(cls, envLoader);
               this.fromMappingAttrs((ClassMapping)meta, cattrs, loader);
            }
         }
      }
   }

   private void fieldFromExtensions(AbstractDeprecatedJDOMappingFactory.MappingAttrs mattrs, String name, Extensions exts) {
      String type = exts.getStringExtension("kodo", "jdbc-field-map");
      if (type != null) {
         AbstractDeprecatedJDOMappingFactory.FieldMappingAttrs field = new AbstractDeprecatedJDOMappingFactory.FieldMappingAttrs(name);
         field.type = type;
         mattrs.fields.put(name, field);
         exts = exts.getEmbeddedExtensions("kodo", "jdbc-field-map", false);
         this.fromExtensions(field, exts);
      }
   }

   private void fromExtensions(AbstractDeprecatedJDOMappingFactory.MappingAttrs mattrs, Extensions ext) {
      if (ext != null) {
         String[] keys = ext.getExtensionKeys("kodo");

         for(int i = 0; i < keys.length; ++i) {
            String val = ext.getStringExtension("kodo", keys[i]);
            if (val != null && val.length() != 0) {
               mattrs.attrs.put(keys[i], ext.getStringExtension("kodo", keys[i]));
            } else {
               this.fieldFromExtensions(mattrs, keys[i], ext.getEmbeddedExtensions("kodo", keys[i], false));
            }
         }

      }
   }

   public boolean store(ClassMetaData[] metas, QueryMetaData[] queries, SequenceMetaData[] seqs, int mode, Map output) {
      if ((mode & 2) != 0) {
         mode |= 1;

         for(int i = 0; i < metas.length; ++i) {
            this.addExtensions(metas[i]);
         }
      }

      return super.store(metas, queries, seqs, mode & -3, output);
   }

   private void addExtensions(ClassMetaData meta) {
      AbstractDeprecatedJDOMappingFactory.ClassMappingAttrs cattrs = this.toMappingAttrs((ClassMapping)meta);
      this.addExtensions(cattrs, "jdbc-class-map", meta);
      this.addExtensions(cattrs.version, "jdbc-version-ind", meta);
      this.addExtensions(cattrs.discriminator, "jdbc-class-ind", meta);
      FieldMetaData[] fmds = meta.getDefinedFields();
      List sups = null;

      AbstractDeprecatedJDOMappingFactory.FieldMappingAttrs fattrs;
      Extensions exts;
      for(int i = 0; i < fmds.length; ++i) {
         if (fmds[i].getDeclaringType() != meta.getDescribedType()) {
            if (sups == null) {
               sups = new ArrayList();
            }

            sups.add(fmds[i]);
         } else {
            fattrs = (AbstractDeprecatedJDOMappingFactory.FieldMappingAttrs)cattrs.fields.get(fmds[i].getName());
            if (fattrs != null) {
               exts = this.addExtensions(fattrs, "jdbc-field-map", fmds[i]);
               this.addEmbeddedExtensions(fattrs, exts);
            }
         }
      }

      if (sups != null) {
         Extensions defFieldExts = this.addExtension(meta, "jdbc-field-mappings");

         for(int i = 0; i < sups.size(); ++i) {
            FieldMetaData fmd = (FieldMetaData)sups.get(i);
            String name = fmd.getFullName(false);
            fattrs = (AbstractDeprecatedJDOMappingFactory.FieldMappingAttrs)cattrs.fields.get(name);
            if (fattrs == null) {
               name = Strings.getClassName(fmds[i].getDeclaringType()) + "." + fmds[i].getName();
               fattrs = (AbstractDeprecatedJDOMappingFactory.FieldMappingAttrs)cattrs.fields.get(name);
            }

            if (fattrs != null) {
               exts = this.addExtension(defFieldExts, fattrs.name);
               exts = this.addExtensions(fattrs, "jdbc-field-map", exts);
               this.addEmbeddedExtensions(fattrs, exts);
            }
         }
      }

   }

   private Extensions addExtension(Extensions ext, String name) {
      ext.addExtension("kodo", name, (Object)null);
      return ext.getEmbeddedExtensions("kodo", name, true);
   }

   private void addEmbeddedExtensions(AbstractDeprecatedJDOMappingFactory.MappingAttrs mattrs, Extensions exts) {
      if (exts != null) {
         Iterator itr = mattrs.fields.values().iterator();

         while(itr.hasNext()) {
            AbstractDeprecatedJDOMappingFactory.FieldMappingAttrs fattrs = (AbstractDeprecatedJDOMappingFactory.FieldMappingAttrs)itr.next();
            Extensions fexts = this.addExtension(exts, fattrs.name);
            fexts = this.addExtensions(fattrs, "jdbc-field-map", fexts);
            this.addEmbeddedExtensions(fattrs, fexts);
         }

      }
   }

   private Extensions addExtensions(AbstractDeprecatedJDOMappingFactory.MappingAttrs mattrs, String key, Extensions exts) {
      if (mattrs.type == null) {
         return null;
      } else {
         exts.removeExtension("kodo", key);
         exts.addExtension("kodo", key, mattrs.type);
         exts = exts.getEmbeddedExtensions("kodo", key, true);
         Iterator itr = mattrs.attrs.entrySet().iterator();

         while(itr.hasNext()) {
            Map.Entry entry = (Map.Entry)itr.next();
            exts.addExtension("kodo", (String)entry.getKey(), entry.getValue());
         }

         return exts;
      }
   }

   public void addClassExtensionKeys(Collection exts) {
      super.addClassExtensionKeys(exts);
      exts.add("jdbc-class-map");
      exts.add("jdbc-version-ind");
      exts.add("jdbc-class-ind");
      exts.add("jdbc-field-mappings");
   }

   public void addFieldExtensionKeys(Collection exts) {
      super.addFieldExtensionKeys(exts);
      exts.add("jdbc-field-map");
   }

   protected void clearMapping(ClassMetaData meta) {
      this.stripMappingExtensions(meta);
   }
}
