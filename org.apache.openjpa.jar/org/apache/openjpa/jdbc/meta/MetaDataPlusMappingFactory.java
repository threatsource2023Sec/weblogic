package org.apache.openjpa.jdbc.meta;

import java.io.File;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.DelegatingMetaDataFactory;
import org.apache.openjpa.meta.MetaDataFactory;
import org.apache.openjpa.meta.MetaDataRepository;
import org.apache.openjpa.meta.QueryMetaData;
import org.apache.openjpa.meta.SequenceMetaData;

public class MetaDataPlusMappingFactory extends DelegatingMetaDataFactory {
   private final MetaDataFactory _map;

   public MetaDataPlusMappingFactory(MetaDataFactory meta, MetaDataFactory map) {
      super(meta);
      this._map = map;
      meta.setStrict(true);
      map.setStrict(true);
   }

   public MetaDataFactory getMappingDelegate() {
      return this._map;
   }

   public MetaDataFactory getInnermostMappingDelegate() {
      return this._map instanceof DelegatingMetaDataFactory ? ((DelegatingMetaDataFactory)this._map).getInnermostDelegate() : this._map;
   }

   public void setRepository(MetaDataRepository repos) {
      super.setRepository(repos);
      this._map.setRepository(repos);
   }

   public void setStoreDirectory(File dir) {
      super.setStoreDirectory(dir);
      this._map.setStoreDirectory(dir);
   }

   public void setStoreMode(int store) {
      super.setStoreMode(store);
      this._map.setStoreMode(store);
   }

   public void setStrict(boolean strict) {
   }

   public void load(Class cls, int mode, ClassLoader envLoader) {
      if ((mode & -3) != 0) {
         super.load(cls, mode & -3, envLoader);
      }

      if (cls != null && (mode & 2) != 0) {
         this._map.load(cls, mode & -2, envLoader);
      }

   }

   public boolean store(ClassMetaData[] metas, QueryMetaData[] queries, SequenceMetaData[] seqs, int mode, Map output) {
      boolean store = true;
      if ((mode & -3) != 0) {
         store &= super.store(metas, queries, seqs, mode & -3, output);
      }

      if ((mode & 2) != 0) {
         store &= this._map.store(metas, queries, seqs, mode & -2, output);
      }

      return store;
   }

   public boolean drop(Class[] cls, int mode, ClassLoader envLoader) {
      boolean drop = true;
      if ((mode & -3) != 0) {
         drop &= super.drop(cls, mode & -3, envLoader);
      }

      if ((mode & 2) != 0) {
         drop &= this._map.drop(cls, mode & -2, envLoader);
      }

      return drop;
   }

   public Set getPersistentTypeNames(boolean classpath, ClassLoader envLoader) {
      Set names = super.getPersistentTypeNames(classpath, envLoader);
      return names != null && !names.isEmpty() ? names : this._map.getPersistentTypeNames(classpath, envLoader);
   }

   public void clear() {
      super.clear();
      this._map.clear();
   }

   public void addClassExtensionKeys(Collection exts) {
      super.addClassExtensionKeys(exts);
      this._map.addClassExtensionKeys(exts);
   }

   public void addFieldExtensionKeys(Collection exts) {
      super.addFieldExtensionKeys(exts);
      this._map.addFieldExtensionKeys(exts);
   }
}
