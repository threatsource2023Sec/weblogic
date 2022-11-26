package org.apache.xmlbeans.impl.schema;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;
import org.apache.xmlbeans.ResourceLoader;
import org.apache.xmlbeans.SchemaAttributeGroup;
import org.apache.xmlbeans.SchemaGlobalAttribute;
import org.apache.xmlbeans.SchemaGlobalElement;
import org.apache.xmlbeans.SchemaIdentityConstraint;
import org.apache.xmlbeans.SchemaModelGroup;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.SchemaTypeLoader;
import org.apache.xmlbeans.SchemaTypeSystem;
import org.apache.xmlbeans.impl.common.QNameHelper;
import org.apache.xmlbeans.impl.common.SystemCache;
import org.apache.xmlbeans.impl.common.XBeanDebug;
import org.apache.xmlbeans.impl.common.utils.collections.ConcurrentWeakHashMap;

public class SchemaTypeLoaderImpl extends SchemaTypeLoaderBase {
   private ResourceLoader _resourceLoader;
   private ClassLoader _classLoader;
   private SchemaTypeLoader[] _searchPath;
   private ConcurrentWeakHashMapCache _classpathTypeSystems;
   private ConcurrentWeakHashMapCache _classLoaderTypeSystems;
   private ConcurrentWeakHashMapCache _elementCache;
   private ConcurrentWeakHashMapCache _attributeCache;
   private ConcurrentWeakHashMapCache _modelGroupCache;
   private ConcurrentWeakHashMapCache _attributeGroupCache;
   private ConcurrentWeakHashMapCache _idConstraintCache;
   private ConcurrentWeakHashMapCache _typeCache;
   private ConcurrentWeakHashMapCache _documentCache;
   private ConcurrentWeakHashMapCache _attributeTypeCache;
   private ConcurrentWeakHashMapCache _classnameCache;
   public static String METADATA_PACKAGE_LOAD;
   private static final SchemaTypeLoader[] EMPTY_SCHEMATYPELOADER_ARRAY;

   public static SchemaTypeLoaderImpl getContextTypeLoader() {
      ClassLoader cl = Thread.currentThread().getContextClassLoader();
      if (cl == null) {
         throw new IllegalStateException("The calling thread's context ClassLoader is null");
      } else {
         SchemaTypeLoaderImpl result = (SchemaTypeLoaderImpl)SystemCache.get().getFromTypeLoaderCache(cl);
         if (result == null) {
            result = new SchemaTypeLoaderImpl(new SchemaTypeLoader[]{BuiltinSchemaTypeSystem.get()}, (ResourceLoader)null, cl);
            SystemCache.get().addToTypeLoaderCache(result, cl);
         }

         return result;
      }
   }

   public static SchemaTypeLoader build(SchemaTypeLoader[] searchPath, ResourceLoader resourceLoader, ClassLoader classLoader) {
      if (searchPath == null) {
         searchPath = EMPTY_SCHEMATYPELOADER_ARRAY;
      } else {
         SubLoaderList list = new SubLoaderList();

         for(int i = 0; i < searchPath.length; ++i) {
            if (searchPath[i] == null) {
               throw new IllegalArgumentException("searchPath[" + i + "] is null");
            }

            if (!(searchPath[i] instanceof SchemaTypeLoaderImpl)) {
               list.add(searchPath[i]);
            } else {
               SchemaTypeLoaderImpl sub = (SchemaTypeLoaderImpl)searchPath[i];
               if (sub._classLoader == null && sub._resourceLoader == null) {
                  for(int j = 0; j < sub._searchPath.length; ++j) {
                     list.add(sub._searchPath[j]);
                  }
               } else {
                  list.add(sub);
               }
            }
         }

         searchPath = list.toArray();
      }

      return (SchemaTypeLoader)(searchPath.length == 1 && resourceLoader == null && classLoader == null ? searchPath[0] : new SchemaTypeLoaderImpl(searchPath, resourceLoader, classLoader));
   }

   private SchemaTypeLoaderImpl(SchemaTypeLoader[] searchPath, ResourceLoader resourceLoader, ClassLoader classLoader) {
      if (searchPath == null) {
         this._searchPath = EMPTY_SCHEMATYPELOADER_ARRAY;
      } else {
         this._searchPath = searchPath;
      }

      this._resourceLoader = resourceLoader;
      this._classLoader = classLoader;
      this.initCaches();
   }

   private final void initCaches() {
      this._classpathTypeSystems = new ConcurrentWeakHashMapCache(SchemaTypeSystemImpl.class);
      this._classLoaderTypeSystems = new ConcurrentWeakHashMapCache(SchemaTypeSystemImpl.class);
      this._elementCache = new ConcurrentWeakHashMapCache(SchemaGlobalElement.Ref.class);
      this._attributeCache = new ConcurrentWeakHashMapCache(SchemaGlobalAttribute.Ref.class);
      this._modelGroupCache = new ConcurrentWeakHashMapCache(SchemaModelGroup.Ref.class);
      this._attributeGroupCache = new ConcurrentWeakHashMapCache(SchemaAttributeGroup.Ref.class);
      this._idConstraintCache = new ConcurrentWeakHashMapCache(SchemaIdentityConstraint.Ref.class);
      this._typeCache = new ConcurrentWeakHashMapCache(SchemaType.Ref.class);
      this._documentCache = new ConcurrentWeakHashMapCache(SchemaType.Ref.class);
      this._attributeTypeCache = new ConcurrentWeakHashMapCache(SchemaType.Ref.class);
      this._classnameCache = new ConcurrentWeakHashMapCache(Object.class);
   }

   SchemaTypeSystemImpl typeSystemForComponent(String searchdir, QName name) {
      String searchfor = searchdir + QNameHelper.hexsafedir(name) + ".xsb";
      String tsname = null;
      if (this._resourceLoader != null) {
         tsname = crackEntry(this._resourceLoader, searchfor);
      }

      if (this._classLoader != null) {
         tsname = crackEntry(this._classLoader, searchfor);
      }

      return tsname != null ? (SchemaTypeSystemImpl)this.typeSystemForName(tsname) : null;
   }

   public SchemaTypeSystem typeSystemForName(String name) {
      SchemaTypeSystemImpl result;
      if (this._resourceLoader != null) {
         result = this.getTypeSystemOnClasspath(name);
         if (result != null) {
            return result;
         }
      }

      if (this._classLoader != null) {
         result = this.getTypeSystemOnClassloader(name);
         if (result != null) {
            return result;
         }
      }

      return null;
   }

   SchemaTypeSystemImpl typeSystemForClassname(String searchdir, String name) {
      String searchfor = searchdir + name.replace('.', '/') + ".xsb";
      String tsname;
      if (this._resourceLoader != null) {
         tsname = crackEntry(this._resourceLoader, searchfor);
         if (tsname != null) {
            return this.getTypeSystemOnClasspath(tsname);
         }
      }

      if (this._classLoader != null) {
         tsname = crackEntry(this._classLoader, searchfor);
         if (tsname != null) {
            return this.getTypeSystemOnClassloader(tsname);
         }
      }

      return null;
   }

   SchemaTypeSystemImpl getTypeSystemOnClasspath(String name) {
      SchemaTypeSystemImpl result = (SchemaTypeSystemImpl)this._classpathTypeSystems.get(name);
      if (result == null) {
         result = new SchemaTypeSystemImpl(this._resourceLoader, name, this);
         this._classpathTypeSystems.put(name, result);
      }

      return result;
   }

   SchemaTypeSystemImpl getTypeSystemOnClassloader(String name) {
      XBeanDebug.trace(1, "Finding type system " + name + " on classloader", 0);
      SchemaTypeSystemImpl result = (SchemaTypeSystemImpl)this._classLoaderTypeSystems.get(name);
      if (result == null) {
         XBeanDebug.trace(1, "Type system " + name + " not cached - consulting field", 0);
         result = SchemaTypeSystemImpl.forName(name, this._classLoader);
         this._classLoaderTypeSystems.put(name, result);
      }

      return result;
   }

   static String crackEntry(ResourceLoader loader, String searchfor) {
      InputStream is = loader.getResourceAsStream(searchfor);
      return is == null ? null : crackPointer(is);
   }

   static String crackEntry(ClassLoader loader, String searchfor) {
      InputStream stream = loader.getResourceAsStream(searchfor);
      return stream == null ? null : crackPointer(stream);
   }

   static String crackPointer(InputStream stream) {
      return SchemaTypeSystemImpl.crackPointer(stream);
   }

   public boolean isNamespaceDefined(String namespace) {
      for(int i = 0; i < this._searchPath.length; ++i) {
         if (this._searchPath[i].isNamespaceDefined(namespace)) {
            return true;
         }
      }

      SchemaTypeSystem sts = this.typeSystemForComponent("schema" + METADATA_PACKAGE_LOAD + "/namespace/", new QName(namespace, "xmlns"));
      return sts != null;
   }

   public SchemaType.Ref findTypeRef(QName name) {
      SchemaType.Ref cached = (SchemaType.Ref)this._typeCache.get(name);
      if (this._typeCache.isCachedNotFound(cached)) {
         return null;
      } else {
         SchemaType.Ref result = cached;
         if (cached == null) {
            for(int i = 0; i < this._searchPath.length && null == (result = this._searchPath[i].findTypeRef(name)); ++i) {
            }

            if (result == null) {
               SchemaTypeSystem ts = this.typeSystemForComponent("schema" + METADATA_PACKAGE_LOAD + "/type/", name);
               if (ts != null) {
                  result = ts.findTypeRef(name);

                  assert result != null : "Type system registered type " + QNameHelper.pretty(name) + " but does not return it";
               }
            }

            this._typeCache.put(name, result);
         }

         return result;
      }
   }

   public SchemaType typeForClassname(String classname) {
      classname = classname.replace('$', '.');
      Object cached = this._classnameCache.get(classname);
      if (this._classnameCache.isCachedNotFound(cached)) {
         return null;
      } else {
         SchemaType result = (SchemaType)cached;
         if (result == null) {
            for(int i = 0; i < this._searchPath.length && null == (result = this._searchPath[i].typeForClassname(classname)); ++i) {
            }

            if (result == null) {
               SchemaTypeSystem ts = this.typeSystemForClassname("schema" + METADATA_PACKAGE_LOAD + "/javaname/", classname);
               if (ts != null) {
                  result = ts.typeForClassname(classname);

                  assert result != null : "Type system registered type " + classname + " but does not return it";
               }
            }

            this._classnameCache.put(classname, result);
         }

         return result;
      }
   }

   public SchemaType.Ref findDocumentTypeRef(QName name) {
      SchemaType.Ref cached = (SchemaType.Ref)this._documentCache.get(name);
      if (this._documentCache.isCachedNotFound(cached)) {
         return null;
      } else {
         SchemaType.Ref result = cached;
         if (cached == null) {
            for(int i = 0; i < this._searchPath.length && null == (result = this._searchPath[i].findDocumentTypeRef(name)); ++i) {
            }

            if (result == null) {
               SchemaTypeSystem ts = this.typeSystemForComponent("schema" + METADATA_PACKAGE_LOAD + "/element/", name);
               if (ts != null) {
                  result = ts.findDocumentTypeRef(name);

                  assert result != null : "Type system registered element " + QNameHelper.pretty(name) + " but does not contain document type";
               }
            }

            this._documentCache.put(name, result);
         }

         return result;
      }
   }

   public SchemaType.Ref findAttributeTypeRef(QName name) {
      SchemaType.Ref cached = (SchemaType.Ref)this._attributeTypeCache.get(name);
      if (this._attributeTypeCache.isCachedNotFound(cached)) {
         return null;
      } else {
         SchemaType.Ref result = cached;
         if (cached == null) {
            for(int i = 0; i < this._searchPath.length && null == (result = this._searchPath[i].findAttributeTypeRef(name)); ++i) {
            }

            if (result == null) {
               SchemaTypeSystem ts = this.typeSystemForComponent("schema" + METADATA_PACKAGE_LOAD + "/attribute/", name);
               if (ts != null) {
                  result = ts.findAttributeTypeRef(name);

                  assert result != null : "Type system registered attribute " + QNameHelper.pretty(name) + " but does not contain attribute type";
               }
            }

            this._attributeTypeCache.put(name, result);
         }

         return result;
      }
   }

   public SchemaGlobalElement.Ref findElementRef(QName name) {
      SchemaGlobalElement.Ref cached = (SchemaGlobalElement.Ref)this._elementCache.get(name);
      if (this._elementCache.isCachedNotFound(cached)) {
         return null;
      } else {
         SchemaGlobalElement.Ref result = cached;
         if (cached == null) {
            for(int i = 0; i < this._searchPath.length && null == (result = this._searchPath[i].findElementRef(name)); ++i) {
            }

            if (result == null) {
               SchemaTypeSystem ts = this.typeSystemForComponent("schema" + METADATA_PACKAGE_LOAD + "/element/", name);
               if (ts != null) {
                  result = ts.findElementRef(name);

                  assert result != null : "Type system registered element " + QNameHelper.pretty(name) + " but does not return it";
               }
            }

            this._elementCache.put(name, result);
         }

         return result;
      }
   }

   public SchemaGlobalAttribute.Ref findAttributeRef(QName name) {
      SchemaGlobalAttribute.Ref cached = (SchemaGlobalAttribute.Ref)this._attributeCache.get(name);
      if (this._attributeCache.isCachedNotFound(cached)) {
         return null;
      } else {
         SchemaGlobalAttribute.Ref result = cached;
         if (cached == null) {
            for(int i = 0; i < this._searchPath.length && null == (result = this._searchPath[i].findAttributeRef(name)); ++i) {
            }

            if (result == null) {
               SchemaTypeSystem ts = this.typeSystemForComponent("schema" + METADATA_PACKAGE_LOAD + "/attribute/", name);
               if (ts != null) {
                  result = ts.findAttributeRef(name);

                  assert result != null : "Type system registered attribute " + QNameHelper.pretty(name) + " but does not return it";
               }
            }

            this._attributeCache.put(name, result);
         }

         return result;
      }
   }

   public SchemaModelGroup.Ref findModelGroupRef(QName name) {
      SchemaModelGroup.Ref cached = (SchemaModelGroup.Ref)this._modelGroupCache.get(name);
      if (this._modelGroupCache.isCachedNotFound(cached)) {
         return null;
      } else {
         SchemaModelGroup.Ref result = cached;
         if (cached == null) {
            for(int i = 0; i < this._searchPath.length && null == (result = this._searchPath[i].findModelGroupRef(name)); ++i) {
            }

            if (result == null) {
               SchemaTypeSystem ts = this.typeSystemForComponent("schema" + METADATA_PACKAGE_LOAD + "/modelgroup/", name);
               if (ts != null) {
                  result = ts.findModelGroupRef(name);

                  assert result != null : "Type system registered model group " + QNameHelper.pretty(name) + " but does not return it";
               }
            }

            this._modelGroupCache.put(name, result);
         }

         return result;
      }
   }

   public SchemaAttributeGroup.Ref findAttributeGroupRef(QName name) {
      SchemaAttributeGroup.Ref cached = (SchemaAttributeGroup.Ref)this._attributeGroupCache.get(name);
      if (this._attributeGroupCache.isCachedNotFound(cached)) {
         return null;
      } else {
         SchemaAttributeGroup.Ref result = cached;
         if (cached == null) {
            for(int i = 0; i < this._searchPath.length && null == (result = this._searchPath[i].findAttributeGroupRef(name)); ++i) {
            }

            if (result == null) {
               SchemaTypeSystem ts = this.typeSystemForComponent("schema" + METADATA_PACKAGE_LOAD + "/attributegroup/", name);
               if (ts != null) {
                  result = ts.findAttributeGroupRef(name);

                  assert result != null : "Type system registered attribute group " + QNameHelper.pretty(name) + " but does not return it";
               }
            }

            this._attributeGroupCache.put(name, result);
         }

         return result;
      }
   }

   public SchemaIdentityConstraint.Ref findIdentityConstraintRef(QName name) {
      SchemaIdentityConstraint.Ref cached = (SchemaIdentityConstraint.Ref)this._idConstraintCache.get(name);
      if (this._idConstraintCache.isCachedNotFound(cached)) {
         return null;
      } else {
         SchemaIdentityConstraint.Ref result = cached;
         if (cached == null) {
            for(int i = 0; i < this._searchPath.length && null == (result = this._searchPath[i].findIdentityConstraintRef(name)); ++i) {
            }

            if (result == null) {
               SchemaTypeSystem ts = this.typeSystemForComponent("schema" + METADATA_PACKAGE_LOAD + "/identityconstraint/", name);
               if (ts != null) {
                  result = ts.findIdentityConstraintRef(name);

                  assert result != null : "Type system registered identity constraint " + QNameHelper.pretty(name) + " but does not return it";
               }
            }

            this._idConstraintCache.put(name, result);
         }

         return result;
      }
   }

   public InputStream getSourceAsStream(String sourceName) {
      InputStream result = null;
      if (!sourceName.startsWith("/")) {
         sourceName = "/" + sourceName;
      }

      if (this._resourceLoader != null) {
         result = this._resourceLoader.getResourceAsStream("schema" + METADATA_PACKAGE_LOAD + "/src" + sourceName);
      }

      return result == null && this._classLoader != null ? this._classLoader.getResourceAsStream("schema" + METADATA_PACKAGE_LOAD + "/src" + sourceName) : result;
   }

   static {
      METADATA_PACKAGE_LOAD = SchemaTypeSystemImpl.METADATA_PACKAGE_GEN;
      EMPTY_SCHEMATYPELOADER_ARRAY = new SchemaTypeLoader[0];
      if (SystemCache.get() instanceof SystemCache) {
         SystemCache.set(new SchemaTypeLoaderCache());
      }

   }

   private static class SubLoaderList {
      private List theList;
      private Map seen;

      private SubLoaderList() {
         this.theList = new ArrayList();
         this.seen = new IdentityHashMap();
      }

      private boolean add(SchemaTypeLoader loader) {
         if (this.seen.containsKey(loader)) {
            return false;
         } else {
            this.theList.add(loader);
            this.seen.put(loader, (Object)null);
            return true;
         }
      }

      private SchemaTypeLoader[] toArray() {
         return (SchemaTypeLoader[])((SchemaTypeLoader[])this.theList.toArray(SchemaTypeLoaderImpl.EMPTY_SCHEMATYPELOADER_ARRAY));
      }

      // $FF: synthetic method
      SubLoaderList(Object x0) {
         this();
      }
   }

   private static class SchemaTypeLoaderCache extends SystemCache {
      private ConcurrentWeakHashMap _cachedTypeSystems;

      private SchemaTypeLoaderCache() {
         this._cachedTypeSystems = new ConcurrentWeakHashMap();
      }

      public SchemaTypeLoader getFromTypeLoaderCache(ClassLoader cl) {
         WeakReference ref = (WeakReference)this._cachedTypeSystems.get(cl);
         return ref != null ? (SchemaTypeLoader)ref.get() : null;
      }

      public void addToTypeLoaderCache(SchemaTypeLoader stl, ClassLoader cl) {
         assert stl instanceof SchemaTypeLoaderImpl && ((SchemaTypeLoaderImpl)stl)._classLoader == cl;

         this._cachedTypeSystems.put(cl, new WeakReference(stl));
      }

      // $FF: synthetic method
      SchemaTypeLoaderCache(Object x0) {
         this();
      }
   }

   private static class ConcurrentWeakHashMapCache {
      private ConcurrentWeakHashMap theCache = new ConcurrentWeakHashMap();
      private Object CACHED_NOT_FOUND;

      public ConcurrentWeakHashMapCache(Class valueType) {
         try {
            this.CACHED_NOT_FOUND = valueType.newInstance();
         } catch (Exception var3) {
            throw new RuntimeException(var3);
         }
      }

      public void put(Object name, Object val) {
         this.theCache.put(name, new WeakReference(val == null ? this.CACHED_NOT_FOUND : val));
      }

      public Object get(Object name) {
         WeakReference ref = (WeakReference)this.theCache.get(name);
         return ref != null ? ref.get() : null;
      }

      public boolean isCachedNotFound(Object value) {
         return value == this.CACHED_NOT_FOUND;
      }
   }
}
