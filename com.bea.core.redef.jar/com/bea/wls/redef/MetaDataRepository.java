package com.bea.wls.redef;

import com.bea.wls.redef.filter.MetaDataFilter;
import com.bea.wls.redef.filter.MetaDataFilterFactory;
import com.bea.wls.redef.filter.MultiMetaDataFilterFactory;
import com.bea.wls.redef.filter.NullMetaDataFilterFactory;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import serp.bytecode.NameCache;
import serp.bytecode.lowlevel.ConstantPoolTable;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.NullClassFinder;
import weblogic.utils.classloaders.Source;
import weblogic.utils.classloaders.ZipSource;

public class MetaDataRepository {
   private static final int INDEX_SPAN = 1000;
   private static final boolean ENHANCE_JARS = Boolean.getBoolean("com.bea.wls.redef.enhancejars");
   private final Map _metas;
   private static final Map _methodIdxs = new HashMap();
   private final NameCache _names;
   private final RedefiningClassLoader _rcl;
   private int _baseIndex;
   private MetaDataFilterFactory _filterFactory;

   public MetaDataRepository() {
      this((RedefiningClassLoader)null);
   }

   public MetaDataRepository(RedefiningClassLoader rcl) {
      this._metas = new HashMap();
      this._names = new NameCache();
      this._filterFactory = NullMetaDataFilterFactory.NULL_FACTORY;
      this._rcl = rcl;
   }

   public void setMetaDataFilterFactory(MetaDataFilterFactory factory) {
      this._filterFactory = (MetaDataFilterFactory)(factory == null ? NullMetaDataFilterFactory.NULL_FACTORY : factory);
   }

   public void addMetaDataFilterFactory(MetaDataFilterFactory factory) {
      if (this._filterFactory instanceof MultiMetaDataFilterFactory) {
         ((MultiMetaDataFilterFactory)this._filterFactory).addFactory(factory);
      } else {
         MultiMetaDataFilterFactory tmp = new MultiMetaDataFilterFactory();
         if (this._filterFactory != null) {
            tmp.addFactory(this._filterFactory);
         }

         tmp.addFactory(factory);
         this._filterFactory = tmp;
      }

   }

   private MetaDataRepository getParentMetaDataRepository() {
      MetaDataRepository parentRepos = null;
      ClassLoader cl = this.getClassLoader();
      ClassLoader parentCL = cl != null ? cl.getParent() : null;
      if (parentCL instanceof RedefiningClassLoader) {
         parentRepos = ((RedefiningClassLoader)parentCL).getMetaDataRepository();
      }

      return parentRepos;
   }

   public synchronized ClassMetaData getMetaData(String clsName) {
      MetaDataRepository parentRepos = this.getParentMetaDataRepository();
      ClassMetaData meta = parentRepos != null ? parentRepos.getMetaData(clsName) : null;
      if (meta != null) {
         return meta;
      } else {
         meta = (ClassMetaData)this._metas.get(clsName);
         if (meta == null && !this._metas.containsKey(clsName)) {
            meta = this.loadMetaData(clsName);
            this._metas.put(clsName, meta);
         }

         if (meta != null) {
            meta.resolve(this._names, this.getClassLoader());
         }

         return meta;
      }
   }

   private ClassMetaData loadMetaData(String clsName) {
      ClassFinder finder = this._rcl == null ? NullClassFinder.NULL_FINDER : this._rcl.getClassFinder();
      Source source = finder.getClassSource(clsName);
      if (source != null && (ENHANCE_JARS || !(source instanceof ZipSource))) {
         if (this.isLoadableByNonRedfiningParent(clsName)) {
            return null;
         } else {
            try {
               ClassMetaData meta = this.newMetaData(clsName);
               byte[] bytes = source.getBytes();
               return !this.defineMetaData((ClassMetaData)meta, (Class)null, bytes) ? null : meta;
            } catch (IOException var6) {
               return null;
            }
         }
      } else {
         return null;
      }
   }

   private boolean isLoadableByNonRedfiningParent(String clsName) {
      ClassLoader cl;
      for(cl = this._rcl != null ? this._rcl.getParent() : null; cl != null && cl instanceof RedefiningClassLoader; cl = cl.getParent()) {
      }

      if (cl == null) {
         return false;
      } else {
         Class c = null;

         try {
            c = cl.loadClass(clsName);
         } catch (ClassNotFoundException var5) {
         }

         return c != null;
      }
   }

   private boolean defineMetaData(ClassMetaData meta, Class prev, byte[] bytes) {
      ConstantPoolTable table = new ConstantPoolTable(bytes);
      MetaDataFilter prevFilter = meta.getPreviousFilter();
      MetaDataFilter filter = this.getFilter(meta.getName(), prev, prevFilter, table, bytes);
      if (filter == null) {
         return false;
      } else {
         meta.define(table, this._names, filter);
         return true;
      }
   }

   private MetaDataFilter getFilter(String name, Class prev, MetaDataFilter prevFilter, ConstantPoolTable table, byte[] bytes) {
      MetaDataRepository parentRepos = this.getParentMetaDataRepository();
      MetaDataFilter filter;
      if (parentRepos != null) {
         filter = parentRepos.getFilter(name, prev, prevFilter, table, bytes);
         if (filter == null) {
            return null;
         }
      }

      filter = this._filterFactory.newFilter(name, prev, prevFilter, table, bytes);
      return filter;
   }

   public synchronized boolean defineMetaData(String clsName, Class prev, byte[] bytes) {
      ClassMetaData meta = (ClassMetaData)this._metas.get(clsName);
      boolean isnew = false;
      if (meta == null && !this._metas.containsKey(clsName)) {
         meta = this.newMetaData(clsName);
         isnew = true;
      } else {
         if (meta == null) {
            return false;
         }

         meta.resolve(this._names, this.getClassLoader());
      }

      if (this.defineMetaData(meta, prev, bytes) && isnew) {
         this._metas.put(clsName, meta);
      } else if (isnew) {
         this._metas.put(clsName, (Object)null);
         return false;
      }

      return true;
   }

   private ClassMetaData newMetaData(String clsName) {
      ClassMetaData meta = new ClassMetaData(this, clsName, this._baseIndex);
      this._baseIndex += 1000;
      return meta;
   }

   public synchronized ClassMetaData removeMetaData(String clsName) {
      return (ClassMetaData)this._metas.remove(clsName);
   }

   public synchronized void clear() {
      this._metas.clear();
      this._names.clear();
   }

   public ClassLoader getClassLoader() {
      return (ClassLoader)(this._rcl != null ? this._rcl : Thread.currentThread().getContextClassLoader());
   }

   int getMethodIndex(MethodMetaData method) {
      StringBuffer buf = new StringBuffer();
      buf.append(method.getName()).append('.').append(method.getDescriptor());
      if (method.isStatic()) {
         buf.append(".s");
      } else if (method.isPrivate()) {
         buf.append(".p");
      }

      String key = buf.toString();
      synchronized(_methodIdxs) {
         Integer idx = (Integer)_methodIdxs.get(key);
         if (idx == null) {
            idx = _methodIdxs.size();
            _methodIdxs.put(key, idx);
         }

         return idx;
      }
   }
}
