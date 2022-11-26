package org.apache.openjpa.meta;

import java.io.Serializable;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.enhance.DynamicPersistenceCapable;
import org.apache.openjpa.enhance.PCRegistry;
import org.apache.openjpa.enhance.PersistenceCapable;
import org.apache.openjpa.event.LifecycleEventManager;
import org.apache.openjpa.lib.conf.Configurable;
import org.apache.openjpa.lib.conf.Configuration;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Closeable;
import org.apache.openjpa.lib.util.J2DoPrivHelper;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.lib.util.StringDistance;
import org.apache.openjpa.util.ImplHelper;
import org.apache.openjpa.util.InternalException;
import org.apache.openjpa.util.MetaDataException;
import org.apache.openjpa.util.OpenJPAId;
import serp.util.Strings;

public class MetaDataRepository implements PCRegistry.RegisterClassListener, Configurable, Closeable, MetaDataModes, Serializable {
   public static final int VALIDATE_NONE = 0;
   public static final int VALIDATE_META = 1;
   public static final int VALIDATE_MAPPING = 2;
   public static final int VALIDATE_UNENHANCED = 4;
   public static final int VALIDATE_RUNTIME = 8;
   protected static final Class[] EMPTY_CLASSES = new Class[0];
   protected static final NonPersistentMetaData[] EMPTY_NON_PERSISTENT = new NonPersistentMetaData[0];
   protected final ClassMetaData[] EMPTY_METAS = this.newClassMetaDataArray(0);
   protected final FieldMetaData[] EMPTY_FIELDS = this.newFieldMetaDataArray(0);
   protected final Order[] EMPTY_ORDERS = this.newOrderArray(0);
   private static final Localizer _loc = Localizer.forPackage(MetaDataRepository.class);
   private SequenceMetaData _sysSeq = null;
   private final Map _metas = new HashMap();
   private final Map _oids = Collections.synchronizedMap(new HashMap());
   private final Map _impls = Collections.synchronizedMap(new HashMap());
   private final Map _ifaces = Collections.synchronizedMap(new HashMap());
   private final Map _queries = new HashMap();
   private final Map _seqs = new HashMap();
   private final Map _aliases = Collections.synchronizedMap(new HashMap());
   private final Map _pawares = Collections.synchronizedMap(new HashMap());
   private final Map _nonMapped = Collections.synchronizedMap(new HashMap());
   private final Map _subs = Collections.synchronizedMap(new HashMap());
   protected final XMLMetaData[] EMPTY_XMLMETAS = this.newXMLClassMetaDataArray(0);
   private final Map _xmlmetas = new HashMap();
   private transient OpenJPAConfiguration _conf = null;
   private transient Log _log = null;
   private transient InterfaceImplGenerator _implGen = null;
   private transient MetaDataFactory _factory = null;
   private int _resMode = 3;
   private int _sourceMode = 7;
   private int _validate = 5;
   private final Collection _registered = new HashSet();
   private final InheritanceOrderedMetaDataList _resolving = new InheritanceOrderedMetaDataList();
   private final InheritanceOrderedMetaDataList _mapping = new InheritanceOrderedMetaDataList();
   private final List _errs = new LinkedList();
   private LifecycleEventManager.ListenerList _listeners = new LifecycleEventManager.ListenerList(3);

   public OpenJPAConfiguration getConfiguration() {
      return this._conf;
   }

   public Log getLog() {
      return this._log;
   }

   public MetaDataFactory getMetaDataFactory() {
      return this._factory;
   }

   public void setMetaDataFactory(MetaDataFactory factory) {
      factory.setRepository(this);
      this._factory = factory;
   }

   public int getValidate() {
      return this._validate;
   }

   public void setValidate(int validate) {
      this._validate = validate;
   }

   public void setValidate(int validate, boolean on) {
      if (validate == 0) {
         this._validate = validate;
      } else if (on) {
         this._validate |= validate;
      } else {
         this._validate &= ~validate;
      }

   }

   public int getResolve() {
      return this._resMode;
   }

   public void setResolve(int mode) {
      this._resMode = mode;
   }

   public void setResolve(int mode, boolean on) {
      if (mode == 0) {
         this._resMode = mode;
      } else if (on) {
         this._resMode |= mode;
      } else {
         this._resMode &= ~mode;
      }

   }

   public int getSourceMode() {
      return this._sourceMode;
   }

   public void setSourceMode(int mode) {
      this._sourceMode = mode;
   }

   public void setSourceMode(int mode, boolean on) {
      if (mode == 0) {
         this._sourceMode = mode;
      } else if (on) {
         this._sourceMode |= mode;
      } else {
         this._sourceMode &= ~mode;
      }

   }

   public synchronized ClassMetaData getMetaData(Class cls, ClassLoader envLoader, boolean mustExist) {
      if (cls != null && DynamicPersistenceCapable.class.isAssignableFrom(cls)) {
         cls = cls.getSuperclass();
      }

      if (cls != null && this._implGen.isImplType(cls)) {
         cls = this._implGen.toManagedInterface(cls);
      }

      ClassMetaData meta = this.getMetaDataInternal(cls, envLoader);
      if (meta == null && mustExist) {
         if (cls != null && !ImplHelper.isManagedType(this._conf, cls)) {
            throw (new MetaDataException(_loc.get("no-meta-notpc", (Object)cls))).setFatal(false);
         } else {
            Set pcNames = this.getPersistentTypeNames(false, envLoader);
            if (pcNames != null && pcNames.size() > 0) {
               throw new MetaDataException(_loc.get("no-meta-types", cls, pcNames));
            } else {
               throw new MetaDataException(_loc.get("no-meta", (Object)cls));
            }
         }
      } else {
         this.resolve(meta);
         return meta;
      }
   }

   public ClassMetaData getMetaData(String alias, ClassLoader envLoader, boolean mustExist) {
      if (alias == null && mustExist) {
         throw new MetaDataException(_loc.get("no-alias-meta", alias, this._aliases));
      } else if (alias == null) {
         return null;
      } else {
         this.processRegisteredClasses(envLoader);
         List classList = (List)this._aliases.get(alias);
         Set pcNames = this.getPersistentTypeNames(false, envLoader);
         Class cls = null;

         for(int i = 0; classList != null && i < classList.size(); ++i) {
            Class c = (Class)classList.get(i);

            try {
               Class nc = Class.forName(c.getName(), false, envLoader);
               if (pcNames == null || pcNames.size() == 0 || pcNames.contains(nc.getName())) {
                  cls = nc;
                  if (!classList.contains(nc)) {
                     classList.add(nc);
                  }
                  break;
               }
            } catch (Throwable var10) {
            }
         }

         if (cls != null) {
            return this.getMetaData(cls, envLoader, mustExist);
         } else if (this._aliases.containsKey(alias)) {
            if (mustExist) {
               this.throwNoRegisteredAlias(alias);
            }

            return null;
         } else {
            this._aliases.put(alias, (Object)null);
            return !mustExist ? null : this.throwNoRegisteredAlias(alias);
         }
      }
   }

   private ClassMetaData throwNoRegisteredAlias(String alias) {
      String close = this.getClosestAliasName(alias);
      if (close != null) {
         throw new MetaDataException(_loc.get("no-alias-meta-hint", alias, this._aliases, close));
      } else {
         throw new MetaDataException(_loc.get("no-alias-meta", alias, this._aliases));
      }
   }

   public String getClosestAliasName(String alias) {
      Collection aliases = this.getAliasNames();
      return StringDistance.getClosestLevenshteinDistance(alias, aliases);
   }

   public Collection getAliasNames() {
      Collection aliases = new HashSet();
      synchronized(this._aliases) {
         Iterator iter = this._aliases.entrySet().iterator();

         while(iter.hasNext()) {
            Map.Entry e = (Map.Entry)iter.next();
            if (e.getValue() != null) {
               aliases.add(e.getKey());
            }
         }

         return aliases;
      }
   }

   private ClassMetaData getMetaDataInternal(Class cls, ClassLoader envLoader) {
      if (cls == null) {
         return null;
      } else {
         ClassMetaData meta = (ClassMetaData)this._metas.get(cls);
         if (meta != null && ((meta.getSourceMode() & 1) != 0 || (this._sourceMode & 1) == 0)) {
            return meta;
         } else {
            if ((this._validate & 8) != 0) {
               Set pcNames = this.getPersistentTypeNames(false, envLoader);
               if (pcNames != null && !pcNames.contains(cls.getName())) {
                  return meta;
               }
            }

            if (meta == null) {
               if (this._metas.containsKey(cls)) {
                  return null;
               }

               if (cls.isPrimitive() || cls.getName().startsWith("java.") || cls == PersistenceCapable.class) {
                  return null;
               }

               if ((this._validate & 8) != 0) {
                  try {
                     Class.forName(cls.getName(), true, (ClassLoader)AccessController.doPrivileged(J2DoPrivHelper.getClassLoaderAction(cls)));
                  } catch (Throwable var5) {
                  }
               }
            }

            int mode = 0;
            if ((this._sourceMode & 1) != 0) {
               mode = this._sourceMode & -3;
            } else if ((this._sourceMode & 2) == 0) {
               mode = this._sourceMode;
            }

            if (mode != 0) {
               if (this._log.isTraceEnabled()) {
                  this._log.trace(_loc.get("load-cls", cls, toModeString(mode)));
               }

               this._factory.load(cls, mode, envLoader);
            }

            if (meta == null) {
               meta = (ClassMetaData)this._metas.get(cls);
            }

            if (meta == null || (meta.getSourceMode() & 1) == 0 && (this._sourceMode & 1) != 0) {
               if (meta != null) {
                  this.removeMetaData(meta);
               }

               this._metas.put(cls, (Object)null);
               return null;
            } else {
               return meta;
            }
         }
      }
   }

   private static String toModeString(int mode) {
      StringBuffer buf = new StringBuffer(31);
      if ((mode & 1) != 0) {
         buf.append("[META]");
      }

      if ((mode & 4) != 0) {
         buf.append("[QUERY]");
      }

      if ((mode & 2) != 0) {
         buf.append("[MAPPING]");
      }

      if ((mode & 8) != 0) {
         buf.append("[MAPPING_INIT]");
      }

      return buf.toString();
   }

   protected void prepareMapping(ClassMetaData meta) {
      meta.defineSuperclassFields(false);
   }

   private void resolve(ClassMetaData meta) {
      if (meta != null && this._resMode != 0 && (meta.getResolve() & 1) == 0) {
         List resolved = this.resolveMeta(meta);
         if (resolved != null) {
            int i;
            for(i = 0; i < resolved.size(); ++i) {
               this.loadMapping((ClassMetaData)resolved.get(i));
            }

            for(i = 0; i < resolved.size(); ++i) {
               this.preMapping((ClassMetaData)resolved.get(i));
            }

            boolean err = true;
            if ((this._resMode & 2) != 0) {
               for(int i = 0; i < resolved.size(); ++i) {
                  err &= this.resolveMapping((ClassMetaData)resolved.get(i));
               }
            }

            if (err && !this._errs.isEmpty()) {
               Object re;
               if (this._errs.size() == 1 && this._errs.get(0) instanceof MetaDataException) {
                  re = (RuntimeException)this._errs.get(0);
               } else {
                  re = (new MetaDataException(_loc.get("resolve-errs"))).setNestedThrowables((Throwable[])((Throwable[])this._errs.toArray(new Exception[this._errs.size()])));
               }

               this._errs.clear();
               throw re;
            }
         }
      }
   }

   private List resolveMeta(ClassMetaData meta) {
      if (meta.getPCSuperclass() == null) {
         Class sup;
         ClassMetaData supMeta;
         for(sup = meta.getDescribedType().getSuperclass(); sup != null && sup != Object.class; sup = sup.getSuperclass()) {
            supMeta = this.getMetaData(sup, meta.getEnvClassLoader(), false);
            if (supMeta != null) {
               meta.setPCSuperclass(sup);
               meta.setPCSuperclassMetaData(supMeta);
               break;
            }
         }

         if (meta.getDescribedType().isInterface()) {
            Class[] sups = meta.getDescribedType().getInterfaces();

            for(int i = 0; i < sups.length; ++i) {
               supMeta = this.getMetaData(sups[i], meta.getEnvClassLoader(), false);
               if (supMeta != null) {
                  meta.setPCSuperclass(sup);
                  meta.setPCSuperclassMetaData(supMeta);
                  break;
               }
            }
         }

         if (this._log.isTraceEnabled()) {
            this._log.trace(_loc.get("assigned-sup", meta, meta.getPCSuperclass()));
         }
      }

      FieldMetaData[] fmds = meta.getDeclaredFields();

      for(int i = 0; i < fmds.length; ++i) {
         if (fmds[i].isPrimaryKey()) {
            this.getMetaData(fmds[i].getDeclaredType(), meta.getEnvClassLoader(), false);
         }
      }

      return this.processBuffer(meta, this._resolving, 1);
   }

   private void loadMapping(ClassMetaData meta) {
      if ((meta.getResolve() & 2) == 0) {
         if ((meta.getSourceMode() & 2) == 0 && (this._sourceMode & 2) != 0) {
            if (meta.isEmbeddedOnly()) {
               meta.setSourceMode(2, true);
            } else {
               int mode = this._sourceMode & -2;
               if (this._log.isTraceEnabled()) {
                  this._log.trace(_loc.get("load-mapping", meta, toModeString(mode)));
               }

               try {
                  this._factory.load(meta.getDescribedType(), mode, meta.getEnvClassLoader());
               } catch (RuntimeException var4) {
                  this.removeMetaData(meta);
                  this._errs.add(var4);
               }
            }
         }

      }
   }

   private void preMapping(ClassMetaData meta) {
      if ((meta.getResolve() & 2) == 0) {
         try {
            if ((this._resMode & 2) != 0) {
               if (this._log.isTraceEnabled()) {
                  this._log.trace(_loc.get("prep-mapping", (Object)meta));
               }

               this.prepareMapping(meta);
            } else {
               meta.defineSuperclassFields(false);
            }
         } catch (RuntimeException var3) {
            this.removeMetaData(meta);
            this._errs.add(var3);
         }

      }
   }

   private boolean resolveMapping(ClassMetaData meta) {
      List mapped = this.processBuffer(meta, this._mapping, 2);
      if (mapped == null) {
         return false;
      } else {
         if ((this._resMode & 8) != 0) {
            for(int i = 0; i < mapped.size(); ++i) {
               meta = (ClassMetaData)mapped.get(i);

               try {
                  meta.resolve(8);
               } catch (RuntimeException var5) {
                  this.removeMetaData(meta);
                  this._errs.add(var5);
               }
            }
         }

         return true;
      }
   }

   private List processBuffer(ClassMetaData meta, InheritanceOrderedMetaDataList buffer, int mode) {
      if (buffer.add(meta) && buffer.size() == 1) {
         List processed = new ArrayList(5);

         while(!buffer.isEmpty()) {
            ClassMetaData buffered = buffer.peek();

            try {
               buffered.resolve(mode);
               processed.add(buffered);
               buffer.remove(buffered);
            } catch (RuntimeException var8) {
               this._errs.add(var8);
               Iterator itr = buffer.iterator();

               while(itr.hasNext()) {
                  meta = (ClassMetaData)itr.next();
                  this.removeMetaData(meta);
                  if (meta != buffered) {
                     this._errs.add(new MetaDataException(_loc.get("prev-errs", meta, buffered)));
                  }
               }

               buffer.clear();
            }
         }

         return processed;
      } else {
         return null;
      }
   }

   public synchronized ClassMetaData[] getMetaDatas() {
      ClassMetaData[] metas = (ClassMetaData[])((ClassMetaData[])this._metas.values().toArray(new ClassMetaData[this._metas.size()]));

      for(int i = 0; i < metas.length; ++i) {
         if (metas[i] != null) {
            this.getMetaData(metas[i].getDescribedType(), metas[i].getEnvClassLoader(), true);
         }
      }

      List resolved = new ArrayList(this._metas.size());
      Iterator itr = this._metas.values().iterator();

      while(itr.hasNext()) {
         ClassMetaData meta = (ClassMetaData)itr.next();
         if (meta != null) {
            resolved.add(meta);
         }
      }

      metas = (ClassMetaData[])((ClassMetaData[])resolved.toArray(this.newClassMetaDataArray(resolved.size())));
      Arrays.sort(metas);
      return metas;
   }

   public ClassMetaData getCachedMetaData(Class cls) {
      return (ClassMetaData)this._metas.get(cls);
   }

   public ClassMetaData addMetaData(Class cls) {
      return this.addMetaData(cls, 0);
   }

   public ClassMetaData addMetaData(Class cls, int access) {
      if (cls != null && !cls.isPrimitive()) {
         ClassMetaData meta = this.newClassMetaData(cls);
         this._factory.getDefaults().populate(meta, access);
         synchronized(this) {
            if (this._pawares.containsKey(cls)) {
               throw new MetaDataException(_loc.get("pc-and-aware", (Object)cls));
            } else {
               this._metas.put(cls, meta);
               return meta;
            }
         }
      } else {
         return null;
      }
   }

   protected ClassMetaData newClassMetaData(Class type) {
      return new ClassMetaData(type, this);
   }

   protected ClassMetaData[] newClassMetaDataArray(int length) {
      return new ClassMetaData[length];
   }

   protected FieldMetaData newFieldMetaData(String name, Class type, ClassMetaData owner) {
      return new FieldMetaData(name, type, owner);
   }

   protected FieldMetaData[] newFieldMetaDataArray(int length) {
      return new FieldMetaData[length];
   }

   protected XMLMetaData[] newXMLClassMetaDataArray(int length) {
      return new XMLClassMetaData[length];
   }

   protected ClassMetaData newEmbeddedClassMetaData(ValueMetaData owner) {
      return new ClassMetaData(owner);
   }

   protected ValueMetaData newValueMetaData(FieldMetaData owner) {
      return new ValueMetaDataImpl(owner);
   }

   protected Order newOrder(FieldMetaData owner, String name, boolean asc) {
      if (name.startsWith("#element")) {
         name = name.substring("#element".length());
      }

      if (name.length() == 0) {
         return this.newValueOrder(owner, asc);
      } else {
         if (name.charAt(0) == '.') {
            name = name.substring(1);
         }

         ClassMetaData meta = owner.getElement().getTypeMetaData();
         if (meta == null) {
            throw new MetaDataException(_loc.get("nonpc-field-orderable", owner, name));
         } else {
            FieldMetaData rel = meta.getField(name);
            if (rel == null) {
               throw new MetaDataException(_loc.get("bad-field-orderable", owner, name));
            } else {
               return this.newRelatedFieldOrder(owner, rel, asc);
            }
         }
      }
   }

   protected Order newValueOrder(FieldMetaData owner, boolean asc) {
      return new InMemoryValueOrder(asc, this.getConfiguration());
   }

   protected Order newRelatedFieldOrder(FieldMetaData owner, FieldMetaData rel, boolean asc) {
      return new InMemoryRelatedFieldOrder(rel, asc, this.getConfiguration());
   }

   protected Order[] newOrderArray(int size) {
      return new Order[size];
   }

   public boolean removeMetaData(ClassMetaData meta) {
      return meta == null ? false : this.removeMetaData(meta.getDescribedType());
   }

   public synchronized boolean removeMetaData(Class cls) {
      if (cls == null) {
         return false;
      } else if (this._metas.remove(cls) != null) {
         Class impl = (Class)this._ifaces.remove(cls);
         if (impl != null) {
            this._metas.remove(impl);
         }

         return true;
      } else {
         return false;
      }
   }

   void addDeclaredInterfaceImpl(ClassMetaData meta, Class iface) {
      synchronized(this._impls) {
         Collection vals = (Collection)this._impls.get(iface);
         if (vals != null) {
            for(ClassMetaData sup = meta.getPCSuperclassMetaData(); sup != null; sup = sup.getPCSuperclassMetaData()) {
               if (vals.contains(sup.getDescribedType())) {
                  return;
               }
            }
         }

         this.addToCollection(this._impls, iface, meta.getDescribedType(), false);
      }
   }

   synchronized void setInterfaceImpl(ClassMetaData meta, Class impl) {
      if (!meta.isManagedInterface()) {
         throw new MetaDataException(_loc.get("not-managed-interface", meta, impl));
      } else {
         this._ifaces.put(meta.getDescribedType(), impl);
         this.addDeclaredInterfaceImpl(meta, meta.getDescribedType());

         for(ClassMetaData sup = meta.getPCSuperclassMetaData(); sup != null; sup = sup.getPCSuperclassMetaData()) {
            sup.clearSubclassCache();
            this.addToCollection(this._subs, sup.getDescribedType(), impl, true);
         }

      }
   }

   InterfaceImplGenerator getImplGenerator() {
      return this._implGen;
   }

   public ClassMetaData getMetaData(Object oid, ClassLoader envLoader, boolean mustExist) {
      if (oid == null && mustExist) {
         throw new MetaDataException(_loc.get("no-oid-meta", oid, "?", this._oids.toString()));
      } else if (oid == null) {
         return null;
      } else {
         Class cls;
         if (oid instanceof OpenJPAId) {
            cls = ((OpenJPAId)oid).getType();
            return this.getMetaData(cls, envLoader, mustExist);
         } else {
            this.processRegisteredClasses(envLoader);
            cls = (Class)this._oids.get(oid.getClass());
            if (cls != null) {
               return this.getMetaData(cls, envLoader, mustExist);
            } else if (this._oids.containsKey(oid.getClass())) {
               if (mustExist) {
                  throw new MetaDataException(_loc.get("no-oid-meta", oid, oid.getClass(), this._oids));
               } else {
                  return null;
               }
            } else {
               this.resolveIdentityClass(oid);
               if (this.processRegisteredClasses(envLoader).length > 0) {
                  cls = (Class)this._oids.get(oid.getClass());
                  if (cls != null) {
                     return this.getMetaData(cls, envLoader, mustExist);
                  }
               }

               this._oids.put(oid.getClass(), (Object)null);
               if (!mustExist) {
                  return null;
               } else {
                  throw (new MetaDataException(_loc.get("no-oid-meta", oid, oid.getClass(), this._oids))).setFailedObject(oid);
               }
            }
         }
      }
   }

   private void resolveIdentityClass(Object oid) {
      if (oid != null) {
         Class oidClass = oid.getClass();
         if (this._log.isTraceEnabled()) {
            this._log.trace(_loc.get("resolve-identity", (Object)oidClass));
         }

         for(ClassLoader cl = (ClassLoader)AccessController.doPrivileged(J2DoPrivHelper.getClassLoaderAction(oidClass)); oidClass != null && oidClass != Object.class; oidClass = oidClass.getSuperclass()) {
            String className = oidClass.getName();

            for(int i = className.length(); i > 1 && className.charAt(i - 1) != '.'; --i) {
               try {
                  Class.forName(className.substring(0, i), true, cl);
               } catch (Exception var7) {
               }
            }
         }

      }
   }

   public ClassMetaData[] getImplementorMetaDatas(Class cls, ClassLoader envLoader, boolean mustExist) {
      if (cls == null && mustExist) {
         throw new MetaDataException(_loc.get("no-meta", (Object)cls));
      } else if (cls == null) {
         return this.EMPTY_METAS;
      } else {
         this.loadRegisteredClassMetaData(envLoader);
         Collection vals = (Collection)this._impls.get(cls);
         Collection mapped = null;
         if (vals != null) {
            synchronized(vals) {
               Iterator itr = vals.iterator();

               label48:
               while(true) {
                  ClassMetaData meta;
                  do {
                     if (!itr.hasNext()) {
                        break label48;
                     }

                     meta = this.getMetaData((Class)itr.next(), envLoader, true);
                  } while(!meta.isMapped() && meta.getMappedPCSubclassMetaDatas().length <= 0);

                  if (mapped == null) {
                     mapped = new ArrayList(vals.size());
                  }

                  mapped.add(meta);
               }
            }
         }

         if (mapped == null && mustExist) {
            throw new MetaDataException(_loc.get("no-meta", (Object)cls));
         } else {
            return mapped == null ? this.EMPTY_METAS : (ClassMetaData[])((ClassMetaData[])mapped.toArray(this.newClassMetaDataArray(mapped.size())));
         }
      }
   }

   public NonPersistentMetaData getPersistenceAware(Class cls) {
      return (NonPersistentMetaData)this._pawares.get(cls);
   }

   public NonPersistentMetaData[] getPersistenceAwares() {
      synchronized(this._pawares) {
         return this._pawares.isEmpty() ? EMPTY_NON_PERSISTENT : (NonPersistentMetaData[])((NonPersistentMetaData[])this._pawares.values().toArray(new NonPersistentMetaData[this._pawares.size()]));
      }
   }

   public NonPersistentMetaData addPersistenceAware(Class cls) {
      if (cls == null) {
         return null;
      } else {
         synchronized(this) {
            if (this._pawares.containsKey(cls)) {
               return (NonPersistentMetaData)this._pawares.get(cls);
            } else if (this.getCachedMetaData(cls) != null) {
               throw new MetaDataException(_loc.get("pc-and-aware", (Object)cls));
            } else {
               NonPersistentMetaData meta = new NonPersistentMetaData(cls, this, 1);
               this._pawares.put(cls, meta);
               return meta;
            }
         }
      }
   }

   public boolean removePersistenceAware(Class cls) {
      return this._pawares.remove(cls) != null;
   }

   public NonPersistentMetaData getNonMappedInterface(Class iface) {
      return (NonPersistentMetaData)this._nonMapped.get(iface);
   }

   public NonPersistentMetaData[] getNonMappedInterfaces() {
      synchronized(this._nonMapped) {
         return this._nonMapped.isEmpty() ? EMPTY_NON_PERSISTENT : (NonPersistentMetaData[])((NonPersistentMetaData[])this._nonMapped.values().toArray(new NonPersistentMetaData[this._nonMapped.size()]));
      }
   }

   public NonPersistentMetaData addNonMappedInterface(Class iface) {
      if (iface == null) {
         return null;
      } else if (!iface.isInterface()) {
         throw new MetaDataException(_loc.get("not-non-mapped", (Object)iface));
      } else {
         synchronized(this) {
            if (this._nonMapped.containsKey(iface)) {
               return (NonPersistentMetaData)this._nonMapped.get(iface);
            } else if (this.getCachedMetaData(iface) != null) {
               throw new MetaDataException(_loc.get("non-mapped-pc", (Object)iface));
            } else {
               NonPersistentMetaData meta = new NonPersistentMetaData(iface, this, 2);
               this._nonMapped.put(iface, meta);
               return meta;
            }
         }
      }
   }

   public boolean removeNonMappedInterface(Class iface) {
      return this._nonMapped.remove(iface) != null;
   }

   public synchronized void clear() {
      if (this._log.isTraceEnabled()) {
         this._log.trace(_loc.get("clear-repos", (Object)this));
      }

      this._metas.clear();
      this._oids.clear();
      this._subs.clear();
      this._impls.clear();
      this._queries.clear();
      this._seqs.clear();
      this._registered.clear();
      this._factory.clear();
      this._aliases.clear();
      this._pawares.clear();
      this._nonMapped.clear();
   }

   public synchronized Set getPersistentTypeNames(boolean devpath, ClassLoader envLoader) {
      return this._factory.getPersistentTypeNames(devpath, envLoader);
   }

   public synchronized Collection loadPersistentTypes(boolean devpath, ClassLoader envLoader) {
      Set names = this.getPersistentTypeNames(devpath, envLoader);
      if (names != null && !names.isEmpty()) {
         ClassLoader clsLoader = this._conf.getClassResolverInstance().getClassLoader(this.getClass(), envLoader);
         List classes = new ArrayList(names.size());
         Iterator itr = names.iterator();

         while(itr.hasNext()) {
            Class cls = this.classForName((String)itr.next(), clsLoader);
            if (cls != null) {
               classes.add(cls);
               if (cls.isInterface()) {
                  this.getMetaData(cls, clsLoader, false);
               }
            }
         }

         return classes;
      } else {
         return Collections.EMPTY_LIST;
      }
   }

   private Class classForName(String name, ClassLoader loader) {
      try {
         return Class.forName(name, true, loader);
      } catch (Exception var4) {
         if ((this._validate & 8) != 0) {
            if (this._log.isWarnEnabled()) {
               this._log.warn(_loc.get("bad-discover-class", (Object)name));
            }
         } else if (this._log.isInfoEnabled()) {
            this._log.info(_loc.get("bad-discover-class", (Object)name));
         }

         if (this._log.isTraceEnabled()) {
            this._log.trace(var4);
         }
      } catch (NoSuchMethodError var5) {
         if (var5.getMessage().indexOf(".pc") == -1) {
            throw var5;
         }

         if ((this._validate & 8) != 0) {
            if (this._log.isWarnEnabled()) {
               this._log.warn(_loc.get("bad-discover-class", (Object)name));
            }
         } else if (this._log.isInfoEnabled()) {
            this._log.info(_loc.get("bad-discover-class", (Object)name));
         }

         if (this._log.isTraceEnabled()) {
            this._log.trace(var5);
         }
      }

      return null;
   }

   Collection getPCSubclasses(Class cls) {
      Collection subs = (Collection)this._subs.get(cls);
      return (Collection)(subs == null ? Collections.EMPTY_LIST : subs);
   }

   public void register(Class cls) {
      synchronized(this._registered) {
         this._registered.add(cls);
      }
   }

   private void loadRegisteredClassMetaData(ClassLoader envLoader) {
      Class[] reg = this.processRegisteredClasses(envLoader);

      for(int i = 0; i < reg.length; ++i) {
         try {
            this.getMetaData(reg[i], envLoader, false);
         } catch (MetaDataException var5) {
            if (this._log.isWarnEnabled()) {
               this._log.warn(var5);
            }
         }
      }

   }

   Class[] processRegisteredClasses(ClassLoader envLoader) {
      if (this._registered.isEmpty()) {
         return EMPTY_CLASSES;
      } else {
         Class[] reg;
         synchronized(this._registered) {
            reg = (Class[])((Class[])this._registered.toArray(new Class[this._registered.size()]));
            this._registered.clear();
         }

         Collection pcNames = this.getPersistentTypeNames(false, envLoader);
         Collection failed = null;

         for(int i = 0; i < reg.length; ++i) {
            if (pcNames == null || pcNames.isEmpty() || pcNames.contains(reg[i].getName())) {
               try {
                  this.processRegisteredClass(reg[i]);
               } catch (Throwable var10) {
                  if (!this._conf.getRetryClassRegistration()) {
                     throw new MetaDataException(_loc.get("error-registered", (Object)reg[i]), var10);
                  }

                  if (this._log.isWarnEnabled()) {
                     this._log.warn(_loc.get("failed-registered", (Object)reg[i]), var10);
                  }

                  if (failed == null) {
                     failed = new ArrayList();
                  }

                  failed.add(reg[i]);
               }
            }
         }

         if (failed != null) {
            synchronized(this._registered) {
               this._registered.addAll(failed);
            }
         }

         return reg;
      }
   }

   private void processRegisteredClass(Class cls) {
      if (this._log.isTraceEnabled()) {
         this._log.trace(_loc.get("process-registered", (Object)cls));
      }

      Class leastDerived = cls;
      Class sup;
      synchronized(this) {
         sup = cls;

         while(true) {
            if ((sup = PCRegistry.getPersistentSuperclass(sup)) == null) {
               break;
            }

            this.addToCollection(this._subs, sup, cls, true);
            ClassMetaData meta = (ClassMetaData)this._metas.get(sup);
            if (meta != null) {
               meta.clearSubclassCache();
            }

            leastDerived = sup;
         }
      }

      Object oid = null;

      try {
         oid = PCRegistry.newObjectId(cls);
      } catch (InternalException var11) {
      }

      if (oid != null) {
         Class existing = (Class)this._oids.get(oid.getClass());
         if (existing != null) {
            for(sup = cls; PCRegistry.getPersistentSuperclass(sup) != null; sup = PCRegistry.getPersistentSuperclass(sup)) {
            }

            this._oids.put(oid.getClass(), sup);
         } else if (existing == null || cls.isAssignableFrom(existing)) {
            this._oids.put(oid.getClass(), cls);
         }
      }

      synchronized(this._impls) {
         this.updateImpls(cls, leastDerived, cls);
      }

      String alias = PCRegistry.getTypeAlias(cls);
      if (alias != null) {
         synchronized(this._aliases) {
            List classList = (List)this._aliases.get(alias);
            if (classList == null) {
               classList = new ArrayList(3);
               this._aliases.put(alias, classList);
            }

            if (!((List)classList).contains(cls)) {
               ((List)classList).add(cls);
            }
         }
      }

   }

   private void updateImpls(Class cls, Class leastDerived, Class check) {
      Class sup = check.getSuperclass();
      if (leastDerived == cls && sup != null && sup != Object.class) {
         this.addToCollection(this._impls, sup, cls, false);
         this.updateImpls(cls, leastDerived, sup);
      }

      if (this._factory.getDefaults().isDeclaredInterfacePersistent()) {
         Class[] ints = check.getInterfaces();

         for(int i = 0; i < ints.length; ++i) {
            if (!ints[i].getName().startsWith("java.") && (leastDerived == cls || this.isLeastDerivedImpl(ints[i], cls))) {
               this.addToCollection(this._impls, ints[i], cls, false);
               this.updateImpls(cls, leastDerived, ints[i]);
            }
         }

      }
   }

   private boolean isLeastDerivedImpl(Class inter, Class cls) {
      for(Class parent = PCRegistry.getPersistentSuperclass(cls); parent != null; parent = PCRegistry.getPersistentSuperclass(parent)) {
         if (Arrays.asList(parent.getInterfaces()).contains(inter)) {
            return false;
         }
      }

      return true;
   }

   private void addToCollection(Map map, Class key, Class value, boolean inheritance) {
      synchronized(map) {
         Collection coll = (Collection)map.get(key);
         if (coll == null) {
            if (inheritance) {
               InheritanceComparator comp = new InheritanceComparator();
               comp.setBase(key);
               coll = new TreeSet(comp);
            } else {
               coll = new LinkedList();
            }

            map.put(key, coll);
         }

         ((Collection)coll).add(value);
      }
   }

   public void setConfiguration(Configuration conf) {
      this._conf = (OpenJPAConfiguration)conf;
      this._log = this._conf.getLog("openjpa.MetaData");
   }

   public void startConfiguration() {
   }

   public void endConfiguration() {
      this.initializeMetaDataFactory();
      if (this._implGen == null) {
         this._implGen = new InterfaceImplGenerator(this);
      }

   }

   private void initializeMetaDataFactory() {
      if (this._factory == null) {
         MetaDataFactory mdf = this._conf.newMetaDataFactoryInstance();
         if (mdf == null) {
            throw new MetaDataException(_loc.get("no-metadatafactory"));
         }

         this.setMetaDataFactory(mdf);
      }

   }

   public synchronized QueryMetaData getQueryMetaData(Class cls, String name, ClassLoader envLoader, boolean mustExist) {
      QueryMetaData meta = this.getQueryMetaDataInternal(cls, name, envLoader);
      if (meta == null) {
         this.resolveAll(envLoader);
         meta = this.getQueryMetaDataInternal(cls, name, envLoader);
      }

      if (meta == null && mustExist) {
         if (cls == null) {
            throw new MetaDataException(_loc.get("no-named-query-null-class", this.getPersistentTypeNames(false, envLoader), name));
         } else {
            throw new MetaDataException(_loc.get("no-named-query", cls, name));
         }
      } else {
         return meta;
      }
   }

   private void resolveAll(ClassLoader envLoader) {
      Collection types = this.loadPersistentTypes(false, envLoader);
      Iterator i = types.iterator();

      while(i.hasNext()) {
         Class c = (Class)i.next();
         this.getMetaData(c, envLoader, false);
      }

   }

   private QueryMetaData getQueryMetaDataInternal(Class cls, String name, ClassLoader envLoader) {
      if (name == null) {
         return null;
      } else {
         Object key = getQueryKey(cls, name);
         QueryMetaData qm = (QueryMetaData)this._queries.get(key);
         if (qm != null) {
            return qm;
         } else {
            if (cls != null && this.getMetaData(cls, envLoader, false) != null) {
               qm = (QueryMetaData)this._queries.get(key);
               if (qm != null) {
                  return qm;
               }
            }

            if ((this._sourceMode & 4) == 0) {
               return null;
            } else {
               if (cls == null) {
                  cls = this._factory.getQueryScope(name, envLoader);
               }

               this._factory.load(cls, 4, envLoader);
               return (QueryMetaData)this._queries.get(key);
            }
         }
      }
   }

   public synchronized QueryMetaData[] getQueryMetaDatas() {
      return (QueryMetaData[])((QueryMetaData[])this._queries.values().toArray(new QueryMetaData[this._queries.size()]));
   }

   public synchronized QueryMetaData getCachedQueryMetaData(Class cls, String name) {
      return (QueryMetaData)this._queries.get(getQueryKey(cls, name));
   }

   public synchronized QueryMetaData addQueryMetaData(Class cls, String name) {
      QueryMetaData meta = this.newQueryMetaData(cls, name);
      this._queries.put(getQueryKey(meta), meta);
      return meta;
   }

   protected QueryMetaData newQueryMetaData(Class cls, String name) {
      QueryMetaData meta = new QueryMetaData(name);
      meta.setDefiningType(cls);
      return meta;
   }

   public synchronized boolean removeQueryMetaData(QueryMetaData meta) {
      if (meta == null) {
         return false;
      } else {
         return this._queries.remove(getQueryKey(meta)) != null;
      }
   }

   public synchronized boolean removeQueryMetaData(Class cls, String name) {
      if (name == null) {
         return false;
      } else {
         return this._queries.remove(getQueryKey(cls, name)) != null;
      }
   }

   private static Object getQueryKey(QueryMetaData meta) {
      return meta == null ? null : getQueryKey(meta.getDefiningType(), meta.getName());
   }

   protected static Object getQueryKey(Class cls, String name) {
      if (cls == null) {
         return name;
      } else {
         QueryKey key = new QueryKey();
         key.clsName = cls.getName();
         key.name = name;
         return key;
      }
   }

   public synchronized SequenceMetaData getSequenceMetaData(String name, ClassLoader envLoader, boolean mustExist) {
      SequenceMetaData meta = this.getSequenceMetaDataInternal(name, envLoader);
      if (meta == null && "system".equals(name)) {
         if (this._sysSeq == null) {
            this._sysSeq = this.newSequenceMetaData(name);
         }

         return this._sysSeq;
      } else if (meta == null && mustExist) {
         throw new MetaDataException(_loc.get("no-named-sequence", (Object)name));
      } else {
         return meta;
      }
   }

   SequenceMetaData getSequenceMetaData(ClassMetaData context, String name, boolean mustExist) {
      MetaDataException e = null;

      try {
         SequenceMetaData seq = this.getSequenceMetaData(name, context.getEnvClassLoader(), mustExist);
         if (seq != null) {
            return seq;
         }
      } catch (MetaDataException var6) {
         e = var6;
      }

      if (name.indexOf(46) != -1) {
         if (e != null) {
            throw e;
         } else {
            return null;
         }
      } else {
         name = Strings.getPackageName(context.getDescribedType()) + "." + name;

         try {
            return this.getSequenceMetaData(name, context.getEnvClassLoader(), mustExist);
         } catch (MetaDataException var7) {
            if (e != null) {
               throw e;
            } else {
               throw var7;
            }
         }
      }
   }

   private SequenceMetaData getSequenceMetaDataInternal(String name, ClassLoader envLoader) {
      if (name == null) {
         return null;
      } else {
         SequenceMetaData meta = (SequenceMetaData)this._seqs.get(name);
         if (meta == null) {
            this.loadRegisteredClassMetaData(envLoader);
            meta = (SequenceMetaData)this._seqs.get(name);
         }

         return meta;
      }
   }

   public synchronized SequenceMetaData[] getSequenceMetaDatas() {
      return (SequenceMetaData[])((SequenceMetaData[])this._seqs.values().toArray(new SequenceMetaData[this._seqs.size()]));
   }

   public synchronized SequenceMetaData getCachedSequenceMetaData(String name) {
      return (SequenceMetaData)this._seqs.get(name);
   }

   public synchronized SequenceMetaData addSequenceMetaData(String name) {
      SequenceMetaData meta = this.newSequenceMetaData(name);
      this._seqs.put(name, meta);
      return meta;
   }

   protected SequenceMetaData newSequenceMetaData(String name) {
      return new SequenceMetaData(name, this);
   }

   public synchronized boolean removeSequenceMetaData(SequenceMetaData meta) {
      if (meta == null) {
         return false;
      } else {
         return this._seqs.remove(meta.getName()) != null;
      }
   }

   public synchronized boolean removeSequenceMetaData(String name) {
      if (name == null) {
         return false;
      } else {
         return this._seqs.remove(name) != null;
      }
   }

   public synchronized void addSystemListener(Object listener) {
      LifecycleEventManager.ListenerList listeners = new LifecycleEventManager.ListenerList(this._listeners);
      listeners.add(listener);
      this._listeners = listeners;
   }

   public synchronized boolean removeSystemListener(Object listener) {
      if (!this._listeners.contains(listener)) {
         return false;
      } else {
         LifecycleEventManager.ListenerList listeners = new LifecycleEventManager.ListenerList(this._listeners);
         listeners.remove(listener);
         this._listeners = listeners;
         return true;
      }
   }

   public LifecycleEventManager.ListenerList getSystemListeners() {
      return this._listeners;
   }

   public synchronized void close() {
      SequenceMetaData[] smds = this.getSequenceMetaDatas();

      for(int i = 0; i < smds.length; ++i) {
         smds[i].close();
      }

      this.clear();
   }

   public synchronized XMLMetaData getXMLMetaData(FieldMetaData fmd) {
      Class cls = fmd.getDeclaredType();
      XMLMetaData xmlmeta = (XMLClassMetaData)this._xmlmetas.get(cls);
      if (xmlmeta != null) {
         return xmlmeta;
      } else {
         this._factory.loadXMLMetaData(fmd);
         xmlmeta = (XMLClassMetaData)this._xmlmetas.get(cls);
         return xmlmeta;
      }
   }

   public XMLClassMetaData addXMLMetaData(Class type, String name) {
      XMLClassMetaData meta = this.newXMLClassMetaData(type, name);
      synchronized(this) {
         this._xmlmetas.put(type, meta);
         return meta;
      }
   }

   public XMLMetaData getCachedXMLMetaData(Class cls) {
      return (XMLMetaData)this._xmlmetas.get(cls);
   }

   protected XMLClassMetaData newXMLClassMetaData(Class type, String name) {
      return new XMLClassMetaData(type, name);
   }

   public XMLFieldMetaData newXMLFieldMetaData(Class type, String name) {
      return new XMLFieldMetaData(type, name);
   }

   private static class QueryKey implements Serializable {
      public String clsName;
      public String name;

      private QueryKey() {
      }

      public int hashCode() {
         int clsHash = this.clsName == null ? 0 : this.clsName.hashCode();
         int nameHash = this.name == null ? 0 : this.name.hashCode();
         return clsHash + nameHash;
      }

      public boolean equals(Object obj) {
         if (obj == this) {
            return true;
         } else if (!(obj instanceof QueryKey)) {
            return false;
         } else {
            QueryKey qk = (QueryKey)obj;
            return StringUtils.equals(this.clsName, qk.clsName) && StringUtils.equals(this.name, qk.name);
         }
      }

      // $FF: synthetic method
      QueryKey(Object x0) {
         this();
      }
   }
}
