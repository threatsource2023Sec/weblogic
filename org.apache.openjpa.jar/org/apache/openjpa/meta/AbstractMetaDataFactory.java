package org.apache.openjpa.meta;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.meta.ClassArgParser;
import serp.util.Strings;

public abstract class AbstractMetaDataFactory implements MetaDataFactory {
   protected MetaDataRepository repos = null;
   protected transient Log log = null;
   protected File dir = null;
   protected int store = 0;
   protected boolean strict = false;
   protected Set types = null;

   public void setTypes(Set types) {
      this.types = types;
   }

   public void setTypes(String types) {
      this.types = StringUtils.isEmpty(types) ? null : new HashSet(Arrays.asList(Strings.split(types, ";", 0)));
   }

   public void setRepository(MetaDataRepository repos) {
      this.repos = repos;
      if (repos != null) {
         this.log = repos.getConfiguration().getLog("openjpa.MetaData");
      }

   }

   public void setStoreDirectory(File dir) {
      this.dir = dir;
   }

   public void setStoreMode(int store) {
      this.store = store;
   }

   public void setStrict(boolean strict) {
      this.strict = strict;
   }

   public boolean store(ClassMetaData[] metas, QueryMetaData[] queries, SequenceMetaData[] seqs, int mode, Map output) {
      return false;
   }

   public boolean drop(Class[] cls, int mode, ClassLoader envLoader) {
      return false;
   }

   public Set getPersistentTypeNames(boolean devpath, ClassLoader envLoader) {
      return this.types;
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
}
