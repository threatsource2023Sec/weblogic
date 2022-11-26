package org.apache.openjpa.meta;

import java.io.File;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import org.apache.openjpa.lib.meta.ClassArgParser;

public class DelegatingMetaDataFactory implements MetaDataFactory {
   private final MetaDataFactory _delegate;

   public DelegatingMetaDataFactory(MetaDataFactory delegate) {
      this._delegate = delegate;
   }

   public MetaDataFactory getDelegate() {
      return this._delegate;
   }

   public MetaDataFactory getInnermostDelegate() {
      return this._delegate instanceof DelegatingMetaDataFactory ? ((DelegatingMetaDataFactory)this._delegate).getInnermostDelegate() : this._delegate;
   }

   public void setRepository(MetaDataRepository repos) {
      this._delegate.setRepository(repos);
   }

   public void setStoreDirectory(File dir) {
      this._delegate.setStoreDirectory(dir);
   }

   public void setStoreMode(int store) {
      this._delegate.setStoreMode(store);
   }

   public void setStrict(boolean strict) {
      this._delegate.setStrict(true);
   }

   public void load(Class cls, int mode, ClassLoader envLoader) {
      this._delegate.load(cls, mode, envLoader);
   }

   public boolean store(ClassMetaData[] metas, QueryMetaData[] queries, SequenceMetaData[] seqs, int mode, Map output) {
      return this._delegate.store(metas, queries, seqs, mode, output);
   }

   public boolean drop(Class[] cls, int mode, ClassLoader envLoader) {
      return this._delegate.drop(cls, mode, envLoader);
   }

   public MetaDataDefaults getDefaults() {
      return this._delegate.getDefaults();
   }

   public ClassArgParser newClassArgParser() {
      return this._delegate.newClassArgParser();
   }

   public Set getPersistentTypeNames(boolean classpath, ClassLoader envLoader) {
      return this._delegate.getPersistentTypeNames(classpath, envLoader);
   }

   public Class getQueryScope(String queryName, ClassLoader loader) {
      return this._delegate.getQueryScope(queryName, loader);
   }

   public Class getResultSetMappingScope(String resultSetMappingName, ClassLoader loader) {
      return this._delegate.getResultSetMappingScope(resultSetMappingName, loader);
   }

   public void clear() {
      this._delegate.clear();
   }

   public void addClassExtensionKeys(Collection exts) {
      this._delegate.addClassExtensionKeys(exts);
   }

   public void addFieldExtensionKeys(Collection exts) {
      this._delegate.addFieldExtensionKeys(exts);
   }

   public void loadXMLMetaData(FieldMetaData fmd) {
      this._delegate.loadXMLMetaData(fmd);
   }
}
