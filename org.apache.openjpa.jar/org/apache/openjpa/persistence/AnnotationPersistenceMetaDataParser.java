package org.apache.openjpa.persistence;

import java.io.File;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ExcludeDefaultListeners;
import javax.persistence.ExcludeSuperclassListeners;
import javax.persistence.FetchType;
import javax.persistence.FlushModeType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.MappedSuperclass;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.QueryHint;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;
import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.enhance.PersistenceCapable;
import org.apache.openjpa.event.BeanLifecycleCallbacks;
import org.apache.openjpa.event.LifecycleCallbacks;
import org.apache.openjpa.event.LifecycleEvent;
import org.apache.openjpa.event.MethodLifecycleCallbacks;
import org.apache.openjpa.lib.conf.Configurations;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.J2DoPriv5Helper;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.DelegatingMetaDataFactory;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.meta.JavaTypes;
import org.apache.openjpa.meta.LifecycleMetaData;
import org.apache.openjpa.meta.MetaDataDefaults;
import org.apache.openjpa.meta.MetaDataFactory;
import org.apache.openjpa.meta.MetaDataModes;
import org.apache.openjpa.meta.MetaDataRepository;
import org.apache.openjpa.meta.QueryMetaData;
import org.apache.openjpa.meta.SequenceMetaData;
import org.apache.openjpa.meta.ValueMetaData;
import org.apache.openjpa.util.ImplHelper;
import org.apache.openjpa.util.InternalException;
import org.apache.openjpa.util.MetaDataException;
import org.apache.openjpa.util.UnsupportedException;
import org.apache.openjpa.util.UserException;
import serp.util.Numbers;
import serp.util.Strings;

public class AnnotationPersistenceMetaDataParser implements MetaDataModes {
   private static final Localizer _loc = Localizer.forPackage(AnnotationPersistenceMetaDataParser.class);
   private static final Map _tags = new HashMap();
   private final OpenJPAConfiguration _conf;
   private final Log _log;
   private MetaDataRepository _repos = null;
   private ClassLoader _envLoader = null;
   private boolean _override = false;
   private int _mode = 0;
   private final Map _pkgs = new HashMap();
   private Class _cls = null;
   private File _file = null;

   public AnnotationPersistenceMetaDataParser(OpenJPAConfiguration conf) {
      this._conf = conf;
      this._log = conf.getLog("openjpa.MetaData");
   }

   public OpenJPAConfiguration getConfiguration() {
      return this._conf;
   }

   public Log getLog() {
      return this._log;
   }

   public MetaDataRepository getRepository() {
      if (this._repos == null) {
         MetaDataRepository repos = this._conf.newMetaDataRepositoryInstance();
         MetaDataFactory mdf = repos.getMetaDataFactory();
         if (mdf instanceof DelegatingMetaDataFactory) {
            mdf = ((DelegatingMetaDataFactory)mdf).getInnermostDelegate();
         }

         if (mdf instanceof PersistenceMetaDataFactory) {
            ((PersistenceMetaDataFactory)mdf).setAnnotationParser(this);
         }

         this._repos = repos;
      }

      return this._repos;
   }

   public void setRepository(MetaDataRepository repos) {
      this._repos = repos;
   }

   public ClassLoader getEnvClassLoader() {
      return this._envLoader;
   }

   public void setEnvClassLoader(ClassLoader loader) {
      this._envLoader = loader;
   }

   public boolean getMappingOverride() {
      return this._override;
   }

   public void setMappingOverride(boolean override) {
      this._override = override;
   }

   public int getMode() {
      return this._mode;
   }

   public void setMode(int mode, boolean on) {
      if (mode == 0) {
         this._mode = 0;
      } else if (on) {
         this._mode |= mode;
      } else {
         this._mode &= ~mode;
      }

   }

   public void setMode(int mode) {
      this._mode = mode;
   }

   protected boolean isMetaDataMode() {
      return (this._mode & 1) != 0;
   }

   protected boolean isQueryMode() {
      return (this._mode & 4) != 0;
   }

   protected boolean isMappingMode() {
      return (this._mode & 2) != 0;
   }

   protected boolean isMappingOverrideMode() {
      return this.isMappingMode() || this._override && this.isMetaDataMode();
   }

   public void clear() {
      this._cls = null;
      this._file = null;
      this._pkgs.clear();
   }

   public void parse(Class cls) {
      if (this._log.isTraceEnabled()) {
         this._log.trace(_loc.get("parse-class", (Object)cls.getName()));
      }

      this._cls = cls;

      try {
         this.parsePackageAnnotations();
         ClassMetaData meta = this.parseClassAnnotations();
         this.updateSourceMode(meta);
      } finally {
         this._cls = null;
         this._file = null;
      }

   }

   private void updateSourceMode(ClassMetaData meta) {
      if (this._cls.getPackage() != null) {
         this.addSourceMode(this._cls.getPackage(), this._mode);
      }

      if (meta != null) {
         meta.setSourceMode(this._mode, true);
      }

   }

   private void parsePackageAnnotations() {
      Package pkg = this._cls.getPackage();
      if (pkg != null) {
         int pkgMode = this.getSourceMode(pkg);
         if (pkgMode == 0 && this._log.isTraceEnabled()) {
            this._log.trace(_loc.get("parse-package", (Object)this._cls.getName()));
         }

         if ((pkgMode & this._mode) != this._mode) {
            Annotation[] arr$ = pkg.getDeclaredAnnotations();
            int len$ = arr$.length;

            for(int i$ = 0; i$ < len$; ++i$) {
               Annotation anno = arr$[i$];
               MetaDataTag tag = (MetaDataTag)_tags.get(anno.annotationType());
               if (tag == null) {
                  this.handleUnknownPackageAnnotation(pkg, anno);
               } else {
                  switch (tag) {
                     case NATIVE_QUERIES:
                        if (this.isQueryMode() && (pkgMode & 4) == 0) {
                           this.parseNamedNativeQueries(pkg, ((NamedNativeQueries)anno).value());
                        }
                        break;
                     case NATIVE_QUERY:
                        if (this.isQueryMode() && (pkgMode & 4) == 0) {
                           this.parseNamedNativeQueries(pkg, (NamedNativeQuery)anno);
                        }
                        break;
                     case QUERIES:
                        if (this.isQueryMode() && (pkgMode & 4) == 0) {
                           this.parseNamedQueries(pkg, ((NamedQueries)anno).value());
                        }
                        break;
                     case QUERY:
                        if (this.isQueryMode() && (pkgMode & 4) == 0) {
                           this.parseNamedQueries(pkg, (NamedQuery)anno);
                        }
                        break;
                     case SEQ_GENERATOR:
                        if (this.isMappingOverrideMode() && (pkgMode & 2) == 0) {
                           this.parseSequenceGenerator(pkg, (SequenceGenerator)anno);
                        }
                        break;
                     default:
                        throw new UnsupportedException(_loc.get("unsupported", pkg, anno.toString()));
                  }
               }
            }

            if (this.isMappingOverrideMode() && (pkgMode & 2) == 0) {
               this.parsePackageMappingAnnotations(pkg);
            }

         }
      }
   }

   protected void parsePackageMappingAnnotations(Package pkg) {
   }

   protected boolean handleUnknownPackageAnnotation(Package pkg, Annotation anno) {
      return false;
   }

   private int getSourceMode(Package pkg) {
      Number num = (Number)this._pkgs.get(pkg);
      return num == null ? 0 : num.intValue();
   }

   private void addSourceMode(Package pkg, int mode) {
      Integer num = (Integer)this._pkgs.get(pkg);
      if (num == null) {
         num = Numbers.valueOf(mode);
      } else {
         num = Numbers.valueOf(num | mode);
      }

      this._pkgs.put(pkg, num);
   }

   private ClassMetaData parseClassAnnotations() {
      if (!(Boolean)AccessController.doPrivileged(J2DoPriv5Helper.isAnnotationPresentAction(this._cls, Entity.class)) && !(Boolean)AccessController.doPrivileged(J2DoPriv5Helper.isAnnotationPresentAction(this._cls, Embeddable.class)) && !(Boolean)AccessController.doPrivileged(J2DoPriv5Helper.isAnnotationPresentAction(this._cls, MappedSuperclass.class))) {
         return null;
      } else {
         ClassMetaData meta = this.getMetaData();
         if (meta == null) {
            return null;
         } else {
            Entity entity = (Entity)this._cls.getAnnotation(Entity.class);
            MappedSuperclass mapped = (MappedSuperclass)this._cls.getAnnotation(MappedSuperclass.class);
            if (this.isMetaDataMode()) {
               meta.setAbstract(mapped != null);
               if (entity == null) {
                  meta.setEmbeddedOnly(true);
               } else {
                  meta.setEmbeddedOnly(false);
                  if (!StringUtils.isEmpty(entity.name())) {
                     meta.setTypeAlias(entity.name());
                  }
               }
            }

            FetchGroup[] fgs = null;
            DetachedState detached = null;
            Collection[] listeners = null;
            Annotation[] arr$ = this._cls.getDeclaredAnnotations();
            int len$ = arr$.length;

            int i$;
            for(i$ = 0; i$ < len$; ++i$) {
               Annotation anno = arr$[i$];
               MetaDataTag tag = (MetaDataTag)_tags.get(anno.annotationType());
               if (tag == null) {
                  this.handleUnknownClassAnnotation(meta, anno);
               } else {
                  switch (tag) {
                     case NATIVE_QUERIES:
                        if (this.isQueryMode() && (meta.getSourceMode() & 4) == 0) {
                           this.parseNamedNativeQueries(this._cls, ((NamedNativeQueries)anno).value());
                        }
                        break;
                     case NATIVE_QUERY:
                        if (this.isQueryMode() && (meta.getSourceMode() & 4) == 0) {
                           this.parseNamedNativeQueries(this._cls, (NamedNativeQuery)anno);
                        }
                        break;
                     case QUERIES:
                        if (this.isQueryMode() && (meta.getSourceMode() & 4) == 0) {
                           this.parseNamedQueries(this._cls, ((NamedQueries)anno).value());
                        }
                        break;
                     case QUERY:
                        if (this.isQueryMode() && (meta.getSourceMode() & 4) == 0) {
                           this.parseNamedQueries(this._cls, (NamedQuery)anno);
                        }
                        break;
                     case SEQ_GENERATOR:
                        if (this.isMappingOverrideMode()) {
                           this.parseSequenceGenerator(this._cls, (SequenceGenerator)anno);
                        }
                        break;
                     case ENTITY_LISTENERS:
                        if (this.isMetaDataMode()) {
                           listeners = this.parseEntityListeners(meta, (EntityListeners)anno);
                        }
                        break;
                     case EXCLUDE_DEFAULT_LISTENERS:
                        if (this.isMetaDataMode()) {
                           meta.getLifecycleMetaData().setIgnoreSystemListeners(true);
                        }
                        break;
                     case EXCLUDE_SUPERCLASS_LISTENERS:
                        if (this.isMetaDataMode()) {
                           meta.getLifecycleMetaData().setIgnoreSuperclassCallbacks(2);
                        }
                        break;
                     case FLUSH_MODE:
                        if (this.isMetaDataMode()) {
                           this.warnFlushMode(meta);
                        }
                        break;
                     case ID_CLASS:
                        if (this.isMetaDataMode()) {
                           meta.setObjectIdType(((IdClass)anno).value(), true);
                        }
                        break;
                     case DATA_CACHE:
                        if (this.isMetaDataMode()) {
                           this.parseDataCache(meta, (DataCache)anno);
                        }
                        break;
                     case DATASTORE_ID:
                        if (this.isMetaDataMode()) {
                           this.parseDataStoreId(meta, (DataStoreId)anno);
                        }
                        break;
                     case DETACHED_STATE:
                        detached = (DetachedState)anno;
                        break;
                     case FETCH_GROUP:
                        if (this.isMetaDataMode()) {
                           fgs = new FetchGroup[]{(FetchGroup)anno};
                        }
                        break;
                     case FETCH_GROUPS:
                        if (this.isMetaDataMode()) {
                           fgs = ((FetchGroups)anno).value();
                        }
                        break;
                     case MANAGED_INTERFACE:
                        if (this.isMetaDataMode()) {
                           this.parseManagedInterface(meta, (ManagedInterface)anno);
                        }
                        break;
                     default:
                        throw new UnsupportedException(_loc.get("unsupported", this._cls, anno.toString()));
                  }
               }
            }

            if (this.isMetaDataMode()) {
               this.parseDetachedState(meta, detached);
               int[] highs = null;
               if (listeners != null) {
                  highs = new int[listeners.length];

                  for(len$ = 0; len$ < listeners.length; ++len$) {
                     if (listeners[len$] != null) {
                        highs[len$] = listeners[len$].size();
                     }
                  }
               }

               this.recordCallbacks(meta, parseCallbackMethods(this._cls, listeners, false, false, this.getRepository()), highs, false);
               if (this._cls.getSuperclass() != null && !Object.class.equals(this._cls.getSuperclass())) {
                  this.recordCallbacks(meta, parseCallbackMethods(this._cls.getSuperclass(), (Collection[])null, true, false, this.getRepository()), (int[])null, true);
               }
            }

            FieldMetaData[] arr$ = meta.getDeclaredFields();
            len$ = arr$.length;

            FieldMetaData fmd;
            for(i$ = 0; i$ < len$; ++i$) {
               fmd = arr$[i$];
               if (fmd.getManagement() == 3) {
                  this.parseMemberAnnotations(fmd);
               }
            }

            if (fgs != null) {
               this.parseFetchGroups(meta, fgs);
            }

            if (this.isMappingOverrideMode()) {
               this.parseClassMappingAnnotations(meta);
               arr$ = meta.getDeclaredFields();
               len$ = arr$.length;

               for(i$ = 0; i$ < len$; ++i$) {
                  fmd = arr$[i$];
                  if (fmd.getManagement() == 3) {
                     this.parseMemberMappingAnnotations(fmd);
                  }
               }
            }

            return meta;
         }
      }
   }

   protected void parseClassMappingAnnotations(ClassMetaData meta) {
   }

   protected boolean handleUnknownClassAnnotation(ClassMetaData meta, Annotation anno) {
      return false;
   }

   private ClassMetaData getMetaData() {
      ClassMetaData meta = this.getRepository().getCachedMetaData(this._cls);
      if (meta == null || (!this.isMetaDataMode() || (meta.getSourceMode() & 1) == 0) && (!this.isMappingMode() || (meta.getSourceMode() & 2) == 0)) {
         if (meta == null) {
            meta = this.getRepository().addMetaData(this._cls);
            meta.setEnvClassLoader(this._envLoader);
            meta.setSourceMode(0);
            meta.setSource(this.getSourceFile(), 1);
         }

         return meta;
      } else {
         if (this._log.isWarnEnabled()) {
            this._log.warn(_loc.get("dup-metadata", (Object)this._cls.getName()));
         }

         return null;
      }
   }

   protected File getSourceFile() {
      if (this._file != null) {
         return this._file;
      } else {
         Class cls;
         for(cls = this._cls; cls.getEnclosingClass() != null; cls = cls.getEnclosingClass()) {
         }

         String rsrc = StringUtils.replace(cls.getName(), ".", "/");
         ClassLoader loader = (ClassLoader)AccessController.doPrivileged(J2DoPriv5Helper.getClassLoaderAction(cls));
         if (loader == null) {
            loader = (ClassLoader)AccessController.doPrivileged(J2DoPriv5Helper.getSystemClassLoaderAction());
         }

         if (loader == null) {
            return null;
         } else {
            URL url = (URL)AccessController.doPrivileged(J2DoPriv5Helper.getResourceAction(loader, rsrc + ".java"));
            if (url == null) {
               url = (URL)AccessController.doPrivileged(J2DoPriv5Helper.getResourceAction(loader, rsrc + ".class"));
               if (url == null) {
                  return null;
               }
            }

            try {
               this._file = new File(url.toURI());
            } catch (URISyntaxException var6) {
            } catch (IllegalArgumentException var7) {
            }

            return this._file;
         }
      }
   }

   private void parseDataStoreId(ClassMetaData meta, DataStoreId id) {
      meta.setIdentityType(1);
      int strat = getGeneratedValueStrategy(meta, id.strategy(), id.generator());
      if (strat != -1) {
         meta.setIdentityStrategy(strat);
      } else {
         switch (id.strategy()) {
            case TABLE:
            case SEQUENCE:
               if (StringUtils.isEmpty(id.generator())) {
                  meta.setIdentitySequenceName("system");
               } else {
                  meta.setIdentitySequenceName(id.generator());
               }
               break;
            case AUTO:
               meta.setIdentityStrategy(1);
               break;
            case IDENTITY:
               meta.setIdentityStrategy(3);
               break;
            default:
               throw new UnsupportedException(id.strategy().toString());
         }
      }

   }

   private void warnFlushMode(Object context) {
      if (this._log.isWarnEnabled()) {
         this._log.warn(_loc.get("unsupported", "FlushMode", context));
      }

   }

   private void parseDataCache(ClassMetaData meta, DataCache cache) {
      if (cache.timeout() != Integer.MIN_VALUE) {
         meta.setDataCacheTimeout(cache.timeout());
      }

      if (!StringUtils.isEmpty(cache.name())) {
         meta.setDataCacheName(cache.name());
      } else if (cache.enabled()) {
         meta.setDataCacheName("default");
      } else {
         meta.setDataCacheName((String)null);
      }

   }

   private void parseManagedInterface(ClassMetaData meta, ManagedInterface iface) {
      meta.setManagedInterface(true);
   }

   private void parseDetachedState(ClassMetaData meta, DetachedState detached) {
      if (detached != null) {
         if (!detached.enabled()) {
            meta.setDetachedState((String)null);
         } else if (StringUtils.isEmpty(detached.fieldName())) {
            meta.setDetachedState("`syn");
         } else {
            meta.setDetachedState(detached.fieldName());
         }
      } else {
         Field[] fields = (Field[])((Field[])AccessController.doPrivileged(J2DoPriv5Helper.getDeclaredFieldsAction(meta.getDescribedType())));

         for(int i = 0; i < fields.length; ++i) {
            if ((Boolean)AccessController.doPrivileged(J2DoPriv5Helper.isAnnotationPresentAction(fields[i], DetachedState.class))) {
               meta.setDetachedState(fields[i].getName());
            }
         }
      }

   }

   private Collection[] parseEntityListeners(ClassMetaData meta, EntityListeners listeners) {
      Class[] classes = listeners.value();
      Collection[] parsed = null;
      Class[] arr$ = classes;
      int len$ = classes.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         Class cls = arr$[i$];
         parsed = parseCallbackMethods(cls, parsed, true, true, this.getRepository());
      }

      return parsed;
   }

   public static Collection[] parseCallbackMethods(Class cls, Collection[] callbacks, boolean sups, boolean listener, MetaDataRepository repos) {
      if (cls == null) {
         throw new IllegalArgumentException("cls cannot be null");
      } else {
         Set methods = new TreeSet(AnnotationPersistenceMetaDataParser.MethodComparator.getInstance());
         Class sup = cls;
         Set seen = new HashSet();

         do {
            Method[] arr$ = (Method[])((Method[])AccessController.doPrivileged(J2DoPriv5Helper.getDeclaredMethodsAction(sup)));
            int len$ = arr$.length;

            for(int i$ = 0; i$ < len$; ++i$) {
               Method m = arr$[i$];
               int mods = m.getModifiers();
               if (!Modifier.isStatic(mods) && !Modifier.isFinal(mods) && !Object.class.equals(m.getDeclaringClass())) {
                  MethodKey key = new MethodKey(m);
                  if (!seen.contains(key)) {
                     methods.add(m);
                     seen.add(key);
                  }
               }
            }

            sup = sup.getSuperclass();
         } while(sups && !Object.class.equals(sup));

         MetaDataDefaults def = repos.getMetaDataFactory().getDefaults();
         Iterator i$ = methods.iterator();

         while(i$.hasNext()) {
            Method m = (Method)i$.next();
            Annotation[] arr$ = (Annotation[])((Annotation[])AccessController.doPrivileged(J2DoPriv5Helper.getDeclaredAnnotationsAction(m)));
            int len$ = arr$.length;

            for(int i$ = 0; i$ < len$; ++i$) {
               Annotation anno = arr$[i$];
               MetaDataTag tag = (MetaDataTag)_tags.get(anno.annotationType());
               if (tag != null) {
                  int[] events = MetaDataParsers.getEventTypes(tag);
                  if (events != null) {
                     if (callbacks == null) {
                        callbacks = (Collection[])(new Collection[LifecycleEvent.ALL_EVENTS.length]);
                     }

                     for(int i = 0; i < events.length; ++i) {
                        int e = events[i];
                        if (callbacks[e] == null) {
                           callbacks[e] = new ArrayList(3);
                        }

                        MetaDataParsers.validateMethodsForSameCallback(cls, callbacks[e], m, tag, def, repos.getLog());
                        if (listener) {
                           callbacks[e].add(new BeanLifecycleCallbacks(cls, m, false));
                        } else {
                           callbacks[e].add(new MethodLifecycleCallbacks(m, false));
                        }
                     }
                  }
               }
            }
         }

         return callbacks;
      }
   }

   private void recordCallbacks(ClassMetaData cls, Collection[] callbacks, int[] highs, boolean superClass) {
      if (callbacks != null) {
         LifecycleMetaData meta = cls.getLifecycleMetaData();
         int[] arr$ = LifecycleEvent.ALL_EVENTS;
         int len$ = arr$.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            int event = arr$[i$];
            if (callbacks[event] != null) {
               LifecycleCallbacks[] array = (LifecycleCallbacks[])callbacks[event].toArray(new LifecycleCallbacks[callbacks[event].size()]);
               if (superClass) {
                  meta.setNonPCSuperclassCallbacks(event, array, highs == null ? 0 : highs[event]);
               } else {
                  meta.setDeclaredCallbacks(event, array, highs == null ? 0 : highs[event]);
               }
            }
         }

      }
   }

   private void parseFetchGroups(ClassMetaData meta, FetchGroup... groups) {
      FetchGroup[] arr$ = groups;
      int len$ = groups.length;

      org.apache.openjpa.meta.FetchGroup fg;
      int i$;
      FetchGroup group;
      String[] arr$;
      int len$;
      int i$;
      for(i$ = 0; i$ < len$; ++i$) {
         group = arr$[i$];
         if (StringUtils.isEmpty(group.name())) {
            throw new MetaDataException(_loc.get("unnamed-fg", (Object)meta));
         }

         fg = meta.addDeclaredFetchGroup(group.name());
         if (group.postLoad()) {
            fg.setPostLoad(true);
         }

         arr$ = group.fetchGroups();
         len$ = arr$.length;

         for(i$ = 0; i$ < len$; ++i$) {
            String s = arr$[i$];
            fg.addDeclaredInclude(s);
         }
      }

      arr$ = groups;
      len$ = groups.length;

      for(i$ = 0; i$ < len$; ++i$) {
         group = arr$[i$];
         fg = meta.getFetchGroup(group.name());
         arr$ = fg.getDeclaredIncludes();
         String[] arr$ = arr$;
         i$ = arr$.length;

         for(int i$ = 0; i$ < i$; ++i$) {
            String includedFectchGroupName = arr$[i$];
            org.apache.openjpa.meta.FetchGroup child = meta.getFetchGroup(includedFectchGroupName);
            if (child == null) {
               throw new UserException(_loc.get("missing-included-fg", meta.getDescribedType().getName(), fg.getName(), includedFectchGroupName));
            }

            child.addContainedBy(fg);
         }
      }

      arr$ = groups;
      len$ = groups.length;

      for(i$ = 0; i$ < len$; ++i$) {
         group = arr$[i$];
         fg = meta.getFetchGroup(group.name());
         FetchAttribute[] arr$ = group.attributes();
         len$ = arr$.length;

         for(i$ = 0; i$ < len$; ++i$) {
            FetchAttribute attr = arr$[i$];
            this.parseFetchAttribute(meta, fg, attr);
         }
      }

   }

   private void parseFetchAttribute(ClassMetaData meta, org.apache.openjpa.meta.FetchGroup fg, FetchAttribute attr) {
      FieldMetaData field = meta.getDeclaredField(attr.name());
      if (field != null && field.getManagement() == 3) {
         field.setInFetchGroup(fg.getName(), true);
         Set parentFetchGroups = fg.getContainedBy();
         Iterator i$ = parentFetchGroups.iterator();

         while(i$.hasNext()) {
            Object parentFetchGroup = i$.next();
            field.setInFetchGroup(parentFetchGroup.toString(), true);
         }

         if (attr.recursionDepth() != Integer.MIN_VALUE) {
            fg.setRecursionDepth(field, attr.recursionDepth());
         }

      } else {
         throw new MetaDataException(_loc.get("bad-fg-field", fg.getName(), meta, attr.name()));
      }
   }

   private void parseMemberAnnotations(FieldMetaData fmd) {
      Member member = this.getRepository().getMetaDataFactory().getDefaults().getBackingMember(fmd);
      PersistenceStrategy pstrat = PersistenceMetaDataDefaults.getPersistenceStrategy(fmd, member);
      if (pstrat != null) {
         fmd.setExplicit(true);
         AnnotatedElement el = (AnnotatedElement)member;
         boolean lob = (Boolean)AccessController.doPrivileged(J2DoPriv5Helper.isAnnotationPresentAction(el, Lob.class));
         if (this.isMetaDataMode()) {
            switch (pstrat) {
               case BASIC:
                  this.parseBasic(fmd, (Basic)el.getAnnotation(Basic.class), lob);
                  break;
               case MANY_ONE:
                  this.parseManyToOne(fmd, (ManyToOne)el.getAnnotation(ManyToOne.class));
                  break;
               case ONE_ONE:
                  this.parseOneToOne(fmd, (OneToOne)el.getAnnotation(OneToOne.class));
                  break;
               case EMBEDDED:
                  this.parseEmbedded(fmd, (Embedded)el.getAnnotation(Embedded.class));
                  break;
               case ONE_MANY:
                  this.parseOneToMany(fmd, (OneToMany)el.getAnnotation(OneToMany.class));
                  break;
               case MANY_MANY:
                  this.parseManyToMany(fmd, (ManyToMany)el.getAnnotation(ManyToMany.class));
                  break;
               case PERS:
                  this.parsePersistent(fmd, (Persistent)el.getAnnotation(Persistent.class));
                  break;
               case PERS_COLL:
                  this.parsePersistentCollection(fmd, (PersistentCollection)el.getAnnotation(PersistentCollection.class));
                  break;
               case PERS_MAP:
                  this.parsePersistentMap(fmd, (PersistentMap)el.getAnnotation(PersistentMap.class));
               case TRANSIENT:
                  break;
               default:
                  throw new InternalException();
            }
         }

         if (this.isMappingOverrideMode() && lob) {
            this.parseLobMapping(fmd);
         }

         Annotation[] arr$ = el.getDeclaredAnnotations();
         int len$ = arr$.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            Annotation anno = arr$[i$];
            MetaDataTag tag = (MetaDataTag)_tags.get(anno.annotationType());
            if (tag == null) {
               this.handleUnknownMemberAnnotation(fmd, anno);
            } else {
               switch (tag) {
                  case SEQ_GENERATOR:
                     if (this.isMappingOverrideMode()) {
                        this.parseSequenceGenerator(el, (SequenceGenerator)anno);
                     }
                     break;
                  case ENTITY_LISTENERS:
                  case EXCLUDE_DEFAULT_LISTENERS:
                  case EXCLUDE_SUPERCLASS_LISTENERS:
                  case ID_CLASS:
                  case DATA_CACHE:
                  case DATASTORE_ID:
                  case DETACHED_STATE:
                  case FETCH_GROUP:
                  case FETCH_GROUPS:
                  case MANAGED_INTERFACE:
                  default:
                     throw new UnsupportedException(_loc.get("unsupported", fmd, anno.toString()));
                  case FLUSH_MODE:
                     if (this.isMetaDataMode()) {
                        this.warnFlushMode(fmd);
                     }
                     break;
                  case GENERATED_VALUE:
                     if (this.isMappingOverrideMode()) {
                        this.parseGeneratedValue(fmd, (GeneratedValue)anno);
                     }
                     break;
                  case ID:
                  case EMBEDDED_ID:
                     fmd.setPrimaryKey(true);
                     break;
                  case MAP_KEY:
                     if (this.isMappingOverrideMode()) {
                        this.parseMapKey(fmd, (MapKey)anno);
                     }
                     break;
                  case ORDER_BY:
                     this.parseOrderBy(fmd, (OrderBy)el.getAnnotation(OrderBy.class));
                     break;
                  case VERSION:
                     fmd.setVersion(true);
                     break;
                  case DEPENDENT:
                     if (this.isMetaDataMode() && ((Dependent)anno).value()) {
                        fmd.setCascadeDelete(2);
                     }
                     break;
                  case ELEM_DEPENDENT:
                     if (this.isMetaDataMode() && ((ElementDependent)anno).value()) {
                        fmd.getElement().setCascadeDelete(2);
                     }
                     break;
                  case ELEM_TYPE:
                     if (this.isMetaDataMode()) {
                        fmd.getElement().setTypeOverride(toOverrideType(((ElementType)anno).value()));
                     }
                     break;
                  case EXTERNAL_VALS:
                     if (this.isMetaDataMode()) {
                        fmd.setExternalValues(Strings.join(((ExternalValues)anno).value(), ","));
                     }
                     break;
                  case EXTERNALIZER:
                     if (this.isMetaDataMode()) {
                        fmd.setExternalizer(((Externalizer)anno).value());
                     }
                     break;
                  case FACTORY:
                     if (this.isMetaDataMode()) {
                        fmd.setFactory(((Factory)anno).value());
                     }
                     break;
                  case INVERSE_LOGICAL:
                     if (this.isMetaDataMode()) {
                        fmd.setInverse(((InverseLogical)anno).value());
                     }
                     break;
                  case KEY_DEPENDENT:
                     if (this.isMetaDataMode() && ((KeyDependent)anno).value()) {
                        fmd.getKey().setCascadeDelete(2);
                     }
                     break;
                  case KEY_TYPE:
                     if (this.isMetaDataMode()) {
                        fmd.getKey().setTypeOverride(toOverrideType(((KeyType)anno).value()));
                     }
                     break;
                  case LOAD_FETCH_GROUP:
                     if (this.isMetaDataMode()) {
                        fmd.setLoadFetchGroup(((LoadFetchGroup)anno).value());
                     }
                     break;
                  case LRS:
                     if (this.isMetaDataMode()) {
                        fmd.setLRS(((LRS)anno).value());
                     }
                     break;
                  case READ_ONLY:
                     if (this.isMetaDataMode()) {
                        this.parseReadOnly(fmd, (ReadOnly)anno);
                     }
                     break;
                  case TYPE:
                     if (this.isMetaDataMode()) {
                        fmd.setTypeOverride(toOverrideType(((Type)anno).value()));
                     }
               }
            }
         }

      }
   }

   protected void parseMemberMappingAnnotations(FieldMetaData fmd) {
   }

   protected boolean handleUnknownMemberAnnotation(FieldMetaData fmd, Annotation anno) {
      return false;
   }

   private static Class toOverrideType(Class cls) {
      return cls == Entity.class ? PersistenceCapable.class : cls;
   }

   private void parseReadOnly(FieldMetaData fmd, ReadOnly ro) {
      if (ro.value() == UpdateAction.RESTRICT) {
         fmd.setUpdateStrategy(2);
      } else {
         if (ro.value() != UpdateAction.IGNORE) {
            throw new InternalException();
         }

         fmd.setUpdateStrategy(1);
      }

   }

   private void parseGeneratedValue(FieldMetaData fmd, GeneratedValue gen) {
      GenerationType strategy = gen.strategy();
      String generator = gen.generator();
      parseGeneratedValue(fmd, strategy, generator);
   }

   static void parseGeneratedValue(FieldMetaData fmd, GenerationType strategy, String generator) {
      int strat = getGeneratedValueStrategy(fmd, strategy, generator);
      if (strat != -1) {
         fmd.setValueStrategy(strat);
      } else {
         switch (strategy) {
            case TABLE:
            case SEQUENCE:
               if (StringUtils.isEmpty(generator)) {
                  fmd.setValueSequenceName("system");
               } else {
                  fmd.setValueSequenceName(generator);
               }
               break;
            case AUTO:
               fmd.setValueSequenceName("system");
               break;
            case IDENTITY:
               fmd.setValueStrategy(3);
               break;
            default:
               throw new UnsupportedException(strategy.toString());
         }
      }

   }

   private static int getGeneratedValueStrategy(Object context, GenerationType strategy, String generator) {
      if (strategy == GenerationType.AUTO && !StringUtils.isEmpty(generator)) {
         if ("uuid-hex".equals(generator)) {
            return 6;
         } else if ("uuid-string".equals(generator)) {
            return 5;
         } else {
            throw new MetaDataException(_loc.get("generator-bad-strategy", context, generator));
         }
      } else {
         return -1;
      }
   }

   private void parseBasic(FieldMetaData fmd, Basic anno, boolean lob) {
      Class type = fmd.getDeclaredType();
      if (lob && type != String.class && type != char[].class && type != Character[].class && type != byte[].class && type != Byte[].class) {
         fmd.setSerialized(true);
      } else if (!lob) {
         switch (fmd.getDeclaredTypeCode()) {
            case 8:
               if (Enum.class.isAssignableFrom(type)) {
                  break;
               }
            case 12:
            case 13:
            case 15:
            case 27:
               if (!Serializable.class.isAssignableFrom(type)) {
                  throw new MetaDataException(_loc.get("bad-meta-anno", fmd, "Basic"));
               }

               fmd.setSerialized(true);
            case 9:
            case 10:
            case 14:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            default:
               break;
            case 11:
               if (type != char[].class && type != Character[].class && type != byte[].class && type != Byte[].class) {
                  if (!Serializable.class.isAssignableFrom(type.getComponentType())) {
                     throw new MetaDataException(_loc.get("bad-meta-anno", fmd, "Basic"));
                  }

                  fmd.setSerialized(true);
               }
         }
      }

      if (anno != null) {
         fmd.setInDefaultFetchGroup(anno.fetch() == FetchType.EAGER);
         if (!anno.optional()) {
            fmd.setNullValue(2);
         }

      }
   }

   private void parseManyToOne(FieldMetaData fmd, ManyToOne anno) {
      if (!JavaTypes.maybePC(fmd.getValue())) {
         throw new MetaDataException(_loc.get("bad-meta-anno", fmd, "ManyToOne"));
      } else {
         if (anno.fetch() == FetchType.EAGER) {
            fmd.setInDefaultFetchGroup(true);
         }

         if (!anno.optional()) {
            fmd.setNullValue(2);
         }

         if (anno.targetEntity() != Void.TYPE) {
            fmd.setTypeOverride(anno.targetEntity());
         }

         this.setCascades(fmd, anno.cascade());
      }
   }

   private void parseOneToOne(FieldMetaData fmd, OneToOne anno) {
      if (!JavaTypes.maybePC(fmd.getValue())) {
         throw new MetaDataException(_loc.get("bad-meta-anno", fmd, "OneToOne"));
      } else {
         if (anno.fetch() == FetchType.EAGER) {
            fmd.setInDefaultFetchGroup(true);
         }

         if (!anno.optional()) {
            fmd.setNullValue(2);
         }

         if (this.isMappingOverrideMode() && !StringUtils.isEmpty(anno.mappedBy())) {
            fmd.setMappedBy(anno.mappedBy());
         }

         if (anno.targetEntity() != Void.TYPE) {
            fmd.setTypeOverride(anno.targetEntity());
         }

         this.setCascades(fmd, anno.cascade());
      }
   }

   private void parseEmbedded(FieldMetaData fmd, Embedded anno) {
      if (!JavaTypes.maybePC(fmd.getValue())) {
         throw new MetaDataException(_loc.get("bad-meta-anno", fmd, "Embedded"));
      } else {
         fmd.setInDefaultFetchGroup(true);
         fmd.setEmbedded(true);
         if (fmd.getEmbeddedMetaData() == null) {
            fmd.addEmbeddedMetaData();
         }

      }
   }

   private void parseOneToMany(FieldMetaData fmd, OneToMany anno) {
      switch (fmd.getDeclaredTypeCode()) {
         case 11:
         case 12:
         case 13:
            if (JavaTypes.maybePC(fmd.getElement())) {
               fmd.setInDefaultFetchGroup(anno.fetch() == FetchType.EAGER);
               if (this.isMappingOverrideMode() && !StringUtils.isEmpty(anno.mappedBy())) {
                  fmd.setMappedBy(anno.mappedBy());
               }

               if (anno.targetEntity() != Void.TYPE) {
                  fmd.getElement().setDeclaredType(anno.targetEntity());
               }

               this.setCascades(fmd.getElement(), anno.cascade());
               return;
            }
         default:
            throw new MetaDataException(_loc.get("bad-meta-anno", fmd, "OneToMany"));
      }
   }

   private void parseManyToMany(FieldMetaData fmd, ManyToMany anno) {
      switch (fmd.getDeclaredTypeCode()) {
         case 11:
         case 12:
         case 13:
            if (JavaTypes.maybePC(fmd.getElement())) {
               fmd.setInDefaultFetchGroup(anno.fetch() == FetchType.EAGER);
               if (this.isMappingOverrideMode() && !StringUtils.isEmpty(anno.mappedBy())) {
                  fmd.setMappedBy(anno.mappedBy());
               }

               if (anno.targetEntity() != Void.TYPE) {
                  fmd.getElement().setDeclaredType(anno.targetEntity());
               }

               this.setCascades(fmd.getElement(), anno.cascade());
               return;
            }
         default:
            throw new MetaDataException(_loc.get("bad-meta-anno", fmd, "OneToMany"));
      }
   }

   private void parseMapKey(FieldMetaData fmd, MapKey anno) {
      String name = anno.name();
      if (StringUtils.isEmpty(name)) {
         fmd.getKey().setValueMappedBy("`pk`");
      } else {
         fmd.getKey().setValueMappedBy(name);
      }

   }

   protected void parseLobMapping(FieldMetaData fmd) {
   }

   private void parseOrderBy(FieldMetaData fmd, OrderBy anno) {
      String dec = anno.value();
      if (dec.length() == 0) {
         dec = "#element asc";
      }

      fmd.setOrderDeclaration(dec);
   }

   private void parsePersistent(FieldMetaData fmd, Persistent anno) {
      switch (fmd.getDeclaredTypeCode()) {
         case 11:
            if (fmd.getDeclaredType() == byte[].class || fmd.getDeclaredType() == Byte[].class || fmd.getDeclaredType() == char[].class || fmd.getDeclaredType() == Character[].class) {
               break;
            }
         case 12:
         case 13:
            throw new MetaDataException(_loc.get("bad-meta-anno", fmd, "Persistent"));
      }

      if (!StringUtils.isEmpty(anno.mappedBy())) {
         fmd.setMappedBy(anno.mappedBy());
      }

      fmd.setInDefaultFetchGroup(anno.fetch() == FetchType.EAGER);
      if (!anno.optional()) {
         fmd.setNullValue(2);
      }

      this.setCascades(fmd, anno.cascade());
      if (anno.embedded()) {
         if (!JavaTypes.maybePC(fmd.getValue())) {
            throw new MetaDataException(_loc.get("bad-meta-anno", fmd, "Persistent(embedded=true)"));
         }

         fmd.setEmbedded(true);
         if (fmd.getEmbeddedMetaData() == null) {
            fmd.addEmbeddedMetaData();
         }
      }

   }

   private void parsePersistentCollection(FieldMetaData fmd, PersistentCollection anno) {
      if (fmd.getDeclaredTypeCode() != 11 && fmd.getDeclaredTypeCode() != 12) {
         throw new MetaDataException(_loc.get("bad-meta-anno", fmd, "PersistentCollection"));
      } else {
         if (!StringUtils.isEmpty(anno.mappedBy())) {
            fmd.setMappedBy(anno.mappedBy());
         }

         fmd.setInDefaultFetchGroup(anno.fetch() == FetchType.EAGER);
         if (anno.elementType() != Void.TYPE) {
            fmd.getElement().setDeclaredType(anno.elementType());
         }

         this.setCascades(fmd.getElement(), anno.elementCascade());
         if (anno.elementEmbedded()) {
            if (!JavaTypes.maybePC(fmd.getElement())) {
               throw new MetaDataException(_loc.get("bad-meta-anno", fmd, "PersistentCollection(embeddedElement=true)"));
            }

            fmd.getElement().setEmbedded(true);
            if (fmd.getElement().getEmbeddedMetaData() == null) {
               fmd.getElement().addEmbeddedMetaData();
            }
         }

      }
   }

   private void parsePersistentMap(FieldMetaData fmd, PersistentMap anno) {
      if (fmd.getDeclaredTypeCode() != 13) {
         throw new MetaDataException(_loc.get("bad-meta-anno", fmd, "PersistentMap"));
      } else {
         fmd.setInDefaultFetchGroup(anno.fetch() == FetchType.EAGER);
         if (anno.keyType() != Void.TYPE) {
            fmd.getKey().setDeclaredType(anno.keyType());
         }

         if (anno.elementType() != Void.TYPE) {
            fmd.getElement().setDeclaredType(anno.elementType());
         }

         this.setCascades(fmd.getKey(), anno.keyCascade());
         this.setCascades(fmd.getElement(), anno.elementCascade());
         if (anno.keyEmbedded()) {
            if (!JavaTypes.maybePC(fmd.getKey())) {
               throw new MetaDataException(_loc.get("bad-meta-anno", fmd, "PersistentMap(embeddedKey=true)"));
            }

            fmd.getKey().setEmbedded(true);
            if (fmd.getKey().getEmbeddedMetaData() == null) {
               fmd.getKey().addEmbeddedMetaData();
            }
         }

         if (anno.elementEmbedded()) {
            if (!JavaTypes.maybePC(fmd.getElement())) {
               throw new MetaDataException(_loc.get("bad-meta-anno", fmd, "PersistentMap(embeddedValue=true)"));
            }

            fmd.getElement().setEmbedded(true);
            if (fmd.getElement().getEmbeddedMetaData() == null) {
               fmd.getElement().addEmbeddedMetaData();
            }
         }

      }
   }

   private void setCascades(ValueMetaData vmd, CascadeType[] cascades) {
      CascadeType[] arr$ = cascades;
      int len$ = cascades.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         CascadeType cascade = arr$[i$];
         if (cascade == CascadeType.ALL || cascade == CascadeType.REMOVE) {
            vmd.setCascadeDelete(1);
         }

         if (cascade == CascadeType.ALL || cascade == CascadeType.PERSIST) {
            vmd.setCascadePersist(1);
         }

         if (cascade == CascadeType.ALL || cascade == CascadeType.MERGE) {
            vmd.setCascadeAttach(1);
         }

         if (cascade == CascadeType.ALL || cascade == CascadeType.REFRESH) {
            vmd.setCascadeRefresh(1);
         }
      }

   }

   private void parseSequenceGenerator(AnnotatedElement el, SequenceGenerator gen) {
      String name = gen.name();
      if (StringUtils.isEmpty(name)) {
         throw new MetaDataException(_loc.get("no-seq-name", (Object)el));
      } else {
         if (this._log.isTraceEnabled()) {
            this._log.trace(_loc.get("parse-sequence", (Object)name));
         }

         SequenceMetaData meta = this.getRepository().getCachedSequenceMetaData(name);
         if (meta != null) {
            if (this._log.isWarnEnabled()) {
               this._log.warn(_loc.get("dup-sequence", name, el));
            }

         } else {
            meta = this.getRepository().addSequenceMetaData(name);
            String seq = gen.sequenceName();
            int initial = gen.initialValue();
            int allocate = gen.allocationSize();
            if (initial == 0) {
               initial = 1;
            }

            String clsName;
            String props;
            if (StringUtils.isEmpty(seq)) {
               clsName = "native";
               props = null;
            } else if (seq.indexOf(40) != -1) {
               clsName = Configurations.getClassName(seq);
               props = Configurations.getProperties(seq);
               seq = null;
            } else {
               clsName = "native";
               props = null;
            }

            meta.setSequencePlugin(Configurations.getPlugin(clsName, props));
            meta.setSequence(seq);
            meta.setInitialValue(initial);
            meta.setAllocate(allocate);
            meta.setSource(this.getSourceFile(), el instanceof Class ? el : null, 1);
         }
      }
   }

   private void parseNamedQueries(AnnotatedElement el, NamedQuery... queries) {
      NamedQuery[] arr$ = queries;
      int len$ = queries.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         NamedQuery query = arr$[i$];
         if (StringUtils.isEmpty(query.name())) {
            throw new MetaDataException(_loc.get("no-query-name", (Object)el));
         }

         if (StringUtils.isEmpty(query.query())) {
            throw new MetaDataException(_loc.get("no-query-string", query.name(), el));
         }

         if (this._log.isTraceEnabled()) {
            this._log.trace(_loc.get("parse-query", (Object)query.name()));
         }

         QueryMetaData meta = this.getRepository().getCachedQueryMetaData((Class)null, query.name());
         if (meta != null) {
            if (this._log.isWarnEnabled()) {
               this._log.warn(_loc.get("dup-query", query.name(), el));
            }
         } else {
            meta = this.getRepository().addQueryMetaData((Class)null, query.name());
            meta.setQueryString(query.query());
            meta.setLanguage("javax.persistence.JPQL");
            QueryHint[] arr$ = query.hints();
            int len$ = arr$.length;

            for(int i$ = 0; i$ < len$; ++i$) {
               QueryHint hint = arr$[i$];
               meta.addHint(hint.name(), hint.value());
            }

            meta.setSource(this.getSourceFile(), el instanceof Class ? el : null, 1);
            if (this.isMetaDataMode()) {
               meta.setSourceMode(1);
            } else if (this.isMappingMode()) {
               meta.setSourceMode(2);
            } else {
               meta.setSourceMode(4);
            }
         }
      }

   }

   private void parseNamedNativeQueries(AnnotatedElement el, NamedNativeQuery... queries) {
      NamedNativeQuery[] arr$ = queries;
      int len$ = queries.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         NamedNativeQuery query = arr$[i$];
         if (StringUtils.isEmpty(query.name())) {
            throw new MetaDataException(_loc.get("no-native-query-name", (Object)el));
         }

         if (StringUtils.isEmpty(query.query())) {
            throw new MetaDataException(_loc.get("no-native-query-string", query.name(), el));
         }

         if (this._log.isTraceEnabled()) {
            this._log.trace(_loc.get("parse-native-query", (Object)query.name()));
         }

         QueryMetaData meta = this.getRepository().getCachedQueryMetaData((Class)null, query.name());
         if (meta != null) {
            if (this._log.isWarnEnabled()) {
               this._log.warn(_loc.get("dup-query", query.name(), el));
            }
         } else {
            meta = this.getRepository().addQueryMetaData((Class)null, query.name());
            meta.setQueryString(query.query());
            meta.setLanguage("openjpa.SQL");
            Class res = query.resultClass();
            if (ImplHelper.isManagedType(this.getConfiguration(), res)) {
               meta.setCandidateType(res);
            } else if (!Void.TYPE.equals(res)) {
               meta.setResultType(res);
            }

            if (!StringUtils.isEmpty(query.resultSetMapping())) {
               meta.setResultSetMappingName(query.resultSetMapping());
            }

            meta.setSource(this.getSourceFile(), el instanceof Class ? el : null, 1);
            if (this.isMetaDataMode()) {
               meta.setSourceMode(1);
            } else if (this.isMappingMode()) {
               meta.setSourceMode(2);
            } else {
               meta.setSourceMode(4);
            }
         }
      }

   }

   static {
      _tags.put(EmbeddedId.class, MetaDataTag.EMBEDDED_ID);
      _tags.put(EntityListeners.class, MetaDataTag.ENTITY_LISTENERS);
      _tags.put(ExcludeDefaultListeners.class, MetaDataTag.EXCLUDE_DEFAULT_LISTENERS);
      _tags.put(ExcludeSuperclassListeners.class, MetaDataTag.EXCLUDE_SUPERCLASS_LISTENERS);
      _tags.put(FlushModeType.class, MetaDataTag.FLUSH_MODE);
      _tags.put(GeneratedValue.class, MetaDataTag.GENERATED_VALUE);
      _tags.put(Id.class, MetaDataTag.ID);
      _tags.put(IdClass.class, MetaDataTag.ID_CLASS);
      _tags.put(MapKey.class, MetaDataTag.MAP_KEY);
      _tags.put(NamedNativeQueries.class, MetaDataTag.NATIVE_QUERIES);
      _tags.put(NamedNativeQuery.class, MetaDataTag.NATIVE_QUERY);
      _tags.put(NamedQueries.class, MetaDataTag.QUERIES);
      _tags.put(NamedQuery.class, MetaDataTag.QUERY);
      _tags.put(OrderBy.class, MetaDataTag.ORDER_BY);
      _tags.put(PostLoad.class, MetaDataTag.POST_LOAD);
      _tags.put(PostPersist.class, MetaDataTag.POST_PERSIST);
      _tags.put(PostRemove.class, MetaDataTag.POST_REMOVE);
      _tags.put(PostUpdate.class, MetaDataTag.POST_UPDATE);
      _tags.put(PrePersist.class, MetaDataTag.PRE_PERSIST);
      _tags.put(PreRemove.class, MetaDataTag.PRE_REMOVE);
      _tags.put(PreUpdate.class, MetaDataTag.PRE_UPDATE);
      _tags.put(SequenceGenerator.class, MetaDataTag.SEQ_GENERATOR);
      _tags.put(Version.class, MetaDataTag.VERSION);
      _tags.put(DataCache.class, MetaDataTag.DATA_CACHE);
      _tags.put(DataStoreId.class, MetaDataTag.DATASTORE_ID);
      _tags.put(Dependent.class, MetaDataTag.DEPENDENT);
      _tags.put(DetachedState.class, MetaDataTag.DETACHED_STATE);
      _tags.put(ElementDependent.class, MetaDataTag.ELEM_DEPENDENT);
      _tags.put(ElementType.class, MetaDataTag.ELEM_TYPE);
      _tags.put(ExternalValues.class, MetaDataTag.EXTERNAL_VALS);
      _tags.put(Externalizer.class, MetaDataTag.EXTERNALIZER);
      _tags.put(Factory.class, MetaDataTag.FACTORY);
      _tags.put(FetchGroup.class, MetaDataTag.FETCH_GROUP);
      _tags.put(FetchGroups.class, MetaDataTag.FETCH_GROUPS);
      _tags.put(InverseLogical.class, MetaDataTag.INVERSE_LOGICAL);
      _tags.put(KeyDependent.class, MetaDataTag.KEY_DEPENDENT);
      _tags.put(KeyType.class, MetaDataTag.KEY_TYPE);
      _tags.put(LoadFetchGroup.class, MetaDataTag.LOAD_FETCH_GROUP);
      _tags.put(LRS.class, MetaDataTag.LRS);
      _tags.put(ManagedInterface.class, MetaDataTag.MANAGED_INTERFACE);
      _tags.put(ReadOnly.class, MetaDataTag.READ_ONLY);
      _tags.put(Type.class, MetaDataTag.TYPE);
   }

   private static class MethodComparator implements Comparator {
      private static MethodComparator INSTANCE = null;

      public static MethodComparator getInstance() {
         if (INSTANCE == null) {
            INSTANCE = new MethodComparator();
         }

         return INSTANCE;
      }

      public int compare(Object o1, Object o2) {
         Method m1 = (Method)o1;
         Method m2 = (Method)o2;
         Class c1 = m1.getDeclaringClass();
         Class c2 = m2.getDeclaringClass();
         if (!c1.equals(c2)) {
            return c1.isAssignableFrom(c2) ? -1 : 1;
         } else {
            int compare = m1.getName().compareTo(m2.getName());
            return compare == 0 ? m1.hashCode() - m2.hashCode() : compare;
         }
      }
   }

   private static class MethodKey {
      private final Method _method;

      public MethodKey(Method m) {
         this._method = m;
      }

      public int hashCode() {
         int code = 552 + this._method.getName().hashCode();
         Class[] arr$ = this._method.getParameterTypes();
         int len$ = arr$.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            Class param = arr$[i$];
            code = 46 * code + param.hashCode();
         }

         return code;
      }

      public boolean equals(Object o) {
         if (!(o instanceof MethodKey)) {
            return false;
         } else {
            Method other = ((MethodKey)o)._method;
            return !this._method.getName().equals(other.getName()) ? false : Arrays.equals(this._method.getParameterTypes(), other.getParameterTypes());
         }
      }
   }
}
