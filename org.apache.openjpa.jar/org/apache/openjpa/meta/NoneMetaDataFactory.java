package org.apache.openjpa.meta;

import java.io.File;
import java.lang.reflect.Member;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import org.apache.openjpa.lib.meta.ClassArgParser;

public class NoneMetaDataFactory implements MetaDataFactory, MetaDataDefaults {
   private static final NoneMetaDataFactory _instance = new NoneMetaDataFactory();

   public static NoneMetaDataFactory getInstance() {
      return _instance;
   }

   public void setRepository(MetaDataRepository repos) {
   }

   public void setStoreDirectory(File dir) {
   }

   public void setStoreMode(int store) {
   }

   public void setStrict(boolean strict) {
   }

   public void load(Class cls, int mode, ClassLoader envLoader) {
   }

   public boolean store(ClassMetaData[] metas, QueryMetaData[] queries, SequenceMetaData[] seqs, int mode, Map output) {
      return false;
   }

   public boolean drop(Class[] cls, int mode, ClassLoader envLoader) {
      return false;
   }

   public MetaDataDefaults getDefaults() {
      return this;
   }

   public Set getPersistentTypeNames(boolean classpath, ClassLoader envLoader) {
      return null;
   }

   public Class getQueryScope(String queryName, ClassLoader loader) {
      return null;
   }

   public Class getResultSetMappingScope(String resultSetMappingName, ClassLoader loader) {
      return null;
   }

   public ClassArgParser newClassArgParser() {
      return new ClassArgParser();
   }

   public void clear() {
   }

   public void addClassExtensionKeys(Collection exts) {
   }

   public void addFieldExtensionKeys(Collection exts) {
   }

   public int getDefaultAccessType() {
      return 0;
   }

   public int getDefaultIdentityType() {
      return 0;
   }

   public int getCallbackMode() {
      return 4;
   }

   public boolean getCallbacksBeforeListeners(int type) {
      return false;
   }

   public void setIgnoreNonPersistent(boolean ignore) {
   }

   public boolean isDeclaredInterfacePersistent() {
      return false;
   }

   public boolean isDataStoreObjectIdFieldUnwrapped() {
      return false;
   }

   public void populate(ClassMetaData meta, int access) {
   }

   public Member getBackingMember(FieldMetaData fmd) {
      return null;
   }

   public Class getUnimplementedExceptionType() {
      return null;
   }

   public void loadXMLMetaData(FieldMetaData fmd) {
   }
}
