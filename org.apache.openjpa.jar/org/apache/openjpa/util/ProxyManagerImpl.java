package org.apache.openjpa.util;

import java.io.File;
import java.io.IOException;
import java.io.ObjectStreamException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;
import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.lib.util.Files;
import org.apache.openjpa.lib.util.J2DoPrivHelper;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.lib.util.Options;
import org.apache.openjpa.lib.util.concurrent.NullSafeConcurrentHashMap;
import serp.bytecode.BCClass;
import serp.bytecode.BCField;
import serp.bytecode.BCMethod;
import serp.bytecode.Code;
import serp.bytecode.JumpInstruction;
import serp.bytecode.Project;
import serp.util.Strings;

public class ProxyManagerImpl implements ProxyManager {
   private static final String PROXY_SUFFIX = "$proxy";
   private static final Localizer _loc = Localizer.forPackage(ProxyManagerImpl.class);
   private static long _proxyId = 0L;
   private static final Map _stdCollections = new HashMap();
   private static final Map _stdMaps = new HashMap();
   private final Set _unproxyable = new HashSet();
   private final Map _proxies = new NullSafeConcurrentHashMap();
   private boolean _trackChanges = true;
   private boolean _assertType = false;

   public ProxyManagerImpl() {
      this._unproxyable.add(TimeZone.class.getName());
   }

   public boolean getTrackChanges() {
      return this._trackChanges;
   }

   public void setTrackChanges(boolean track) {
      this._trackChanges = track;
   }

   public boolean getAssertAllowedType() {
      return this._assertType;
   }

   public void setAssertAllowedType(boolean assertType) {
      this._assertType = assertType;
   }

   public Collection getUnproxyable() {
      return this._unproxyable;
   }

   public void setUnproxyable(String clsNames) {
      if (clsNames != null) {
         this._unproxyable.addAll(Arrays.asList(Strings.split(clsNames, ";", 0)));
      }

   }

   public Object copyArray(Object orig) {
      if (orig == null) {
         return null;
      } else {
         try {
            int length = Array.getLength(orig);
            Object array = Array.newInstance(orig.getClass().getComponentType(), length);
            System.arraycopy(orig, 0, array, 0, length);
            return array;
         } catch (Exception var4) {
            throw new UnsupportedException(_loc.get("bad-array", (Object)var4.getMessage()), var4);
         }
      }
   }

   public Collection copyCollection(Collection orig) {
      if (orig == null) {
         return null;
      } else if (orig instanceof Proxy) {
         return (Collection)((Proxy)orig).copy(orig);
      } else {
         ProxyCollection proxy = this.getFactoryProxyCollection(orig.getClass());
         return (Collection)proxy.copy(orig);
      }
   }

   public Proxy newCollectionProxy(Class type, Class elementType, Comparator compare, boolean autoOff) {
      type = this.toProxyableCollectionType(type);
      ProxyCollection proxy = this.getFactoryProxyCollection(type);
      return proxy.newInstance(this._assertType ? elementType : null, compare, this._trackChanges, autoOff);
   }

   public Map copyMap(Map orig) {
      if (orig == null) {
         return null;
      } else if (orig instanceof Proxy) {
         return (Map)((Proxy)orig).copy(orig);
      } else {
         ProxyMap proxy = this.getFactoryProxyMap(orig.getClass());
         return (Map)proxy.copy(orig);
      }
   }

   public Proxy newMapProxy(Class type, Class keyType, Class elementType, Comparator compare, boolean autoOff) {
      type = this.toProxyableMapType(type);
      ProxyMap proxy = this.getFactoryProxyMap(type);
      return proxy.newInstance(this._assertType ? keyType : null, this._assertType ? elementType : null, compare, this._trackChanges, autoOff);
   }

   public Date copyDate(Date orig) {
      if (orig == null) {
         return null;
      } else if (orig instanceof Proxy) {
         return (Date)((Proxy)orig).copy(orig);
      } else {
         ProxyDate proxy = this.getFactoryProxyDate(orig.getClass());
         return (Date)proxy.copy(orig);
      }
   }

   public Proxy newDateProxy(Class type) {
      ProxyDate proxy = this.getFactoryProxyDate(type);
      return proxy.newInstance();
   }

   public Calendar copyCalendar(Calendar orig) {
      if (orig == null) {
         return null;
      } else if (orig instanceof Proxy) {
         return (Calendar)((Proxy)orig).copy(orig);
      } else {
         ProxyCalendar proxy = this.getFactoryProxyCalendar(orig.getClass());
         return (Calendar)proxy.copy(orig);
      }
   }

   public Proxy newCalendarProxy(Class type, TimeZone zone) {
      if (type == Calendar.class) {
         type = GregorianCalendar.class;
      }

      ProxyCalendar proxy = this.getFactoryProxyCalendar(type);
      ProxyCalendar cal = proxy.newInstance();
      if (zone != null) {
         ((Calendar)cal).setTimeZone(zone);
      }

      return cal;
   }

   public Object copyCustom(Object orig) {
      if (orig == null) {
         return null;
      } else if (orig instanceof Proxy) {
         return ((Proxy)orig).copy(orig);
      } else if (ImplHelper.isManageable(orig)) {
         return null;
      } else if (orig instanceof Collection) {
         return this.copyCollection((Collection)orig);
      } else if (orig instanceof Map) {
         return this.copyMap((Map)orig);
      } else if (orig instanceof Date) {
         return this.copyDate((Date)orig);
      } else if (orig instanceof Calendar) {
         return this.copyCalendar((Calendar)orig);
      } else {
         ProxyBean proxy = this.getFactoryProxyBean(orig);
         return proxy == null ? null : proxy.copy(orig);
      }
   }

   public Proxy newCustomProxy(Object orig, boolean autoOff) {
      if (orig == null) {
         return null;
      } else if (orig instanceof Proxy) {
         return (Proxy)orig;
      } else if (ImplHelper.isManageable(orig)) {
         return null;
      } else {
         Comparator comp;
         if (orig instanceof Collection) {
            comp = orig instanceof SortedSet ? ((SortedSet)orig).comparator() : null;
            Collection c = (Collection)this.newCollectionProxy(orig.getClass(), (Class)null, comp, autoOff);
            c.addAll((Collection)orig);
            return (Proxy)c;
         } else if (orig instanceof Map) {
            comp = orig instanceof SortedMap ? ((SortedMap)orig).comparator() : null;
            Map m = (Map)this.newMapProxy(orig.getClass(), (Class)null, (Class)null, comp, autoOff);
            m.putAll((Map)orig);
            return (Proxy)m;
         } else if (orig instanceof Date) {
            Date d = (Date)this.newDateProxy(orig.getClass());
            d.setTime(((Date)orig).getTime());
            if (orig instanceof Timestamp) {
               ((Timestamp)d).setNanos(((Timestamp)orig).getNanos());
            }

            return (Proxy)d;
         } else if (orig instanceof Calendar) {
            Calendar c = (Calendar)this.newCalendarProxy(orig.getClass(), ((Calendar)orig).getTimeZone());
            c.setTimeInMillis(((Calendar)orig).getTimeInMillis());
            return (Proxy)c;
         } else {
            ProxyBean proxy = this.getFactoryProxyBean(orig);
            return proxy == null ? null : proxy.newInstance(orig);
         }
      }
   }

   protected Class toProxyableCollectionType(Class type) {
      if (type.getName().endsWith("$proxy")) {
         type = type.getSuperclass();
      } else if (type.isInterface()) {
         type = toConcreteType(type, _stdCollections);
         if (type == null) {
            throw new UnsupportedException(_loc.get("no-proxy-intf", (Object)type));
         }
      } else if (Modifier.isAbstract(type.getModifiers())) {
         throw new UnsupportedException(_loc.get("no-proxy-abstract", (Object)type));
      }

      return type;
   }

   protected Class toProxyableMapType(Class type) {
      if (type.getName().endsWith("$proxy")) {
         type = type.getSuperclass();
      } else if (type.isInterface()) {
         type = toConcreteType(type, _stdMaps);
         if (type == null) {
            throw new UnsupportedException(_loc.get("no-proxy-intf", (Object)type));
         }
      } else if (Modifier.isAbstract(type.getModifiers())) {
         throw new UnsupportedException(_loc.get("no-proxy-abstract", (Object)type));
      }

      return type;
   }

   private static Class toConcreteType(Class intf, Map concretes) {
      Class concrete = (Class)concretes.get(intf);
      if (concrete != null) {
         return concrete;
      } else {
         Class[] intfs = intf.getInterfaces();

         for(int i = 0; i < intfs.length; ++i) {
            concrete = toConcreteType(intfs[i], concretes);
            if (concrete != null) {
               return concrete;
            }
         }

         return null;
      }
   }

   private ProxyCollection getFactoryProxyCollection(Class type) {
      ProxyCollection proxy = (ProxyCollection)this._proxies.get(type);
      if (proxy == null) {
         ClassLoader l = GeneratedClasses.getMostDerivedLoader(type, ProxyCollection.class);
         Class pcls = this.loadBuildTimeProxy(type, l);
         if (pcls == null) {
            pcls = GeneratedClasses.loadBCClass(this.generateProxyCollectionBytecode(type, true), l);
         }

         proxy = (ProxyCollection)this.instantiateProxy(pcls, (Constructor)null, (Object[])null);
         this._proxies.put(type, proxy);
      }

      return proxy;
   }

   private ProxyMap getFactoryProxyMap(Class type) {
      ProxyMap proxy = (ProxyMap)this._proxies.get(type);
      if (proxy == null) {
         ClassLoader l = GeneratedClasses.getMostDerivedLoader(type, ProxyMap.class);
         Class pcls = this.loadBuildTimeProxy(type, l);
         if (pcls == null) {
            pcls = GeneratedClasses.loadBCClass(this.generateProxyMapBytecode(type, true), l);
         }

         proxy = (ProxyMap)this.instantiateProxy(pcls, (Constructor)null, (Object[])null);
         this._proxies.put(type, proxy);
      }

      return proxy;
   }

   private ProxyDate getFactoryProxyDate(Class type) {
      ProxyDate proxy = (ProxyDate)this._proxies.get(type);
      if (proxy == null) {
         ClassLoader l = GeneratedClasses.getMostDerivedLoader(type, ProxyDate.class);
         Class pcls = this.loadBuildTimeProxy(type, l);
         if (pcls == null) {
            pcls = GeneratedClasses.loadBCClass(this.generateProxyDateBytecode(type, true), l);
         }

         proxy = (ProxyDate)this.instantiateProxy(pcls, (Constructor)null, (Object[])null);
         this._proxies.put(type, proxy);
      }

      return proxy;
   }

   private ProxyCalendar getFactoryProxyCalendar(Class type) {
      ProxyCalendar proxy = (ProxyCalendar)this._proxies.get(type);
      if (proxy == null) {
         ClassLoader l = GeneratedClasses.getMostDerivedLoader(type, ProxyCalendar.class);
         Class pcls = this.loadBuildTimeProxy(type, l);
         if (pcls == null) {
            pcls = GeneratedClasses.loadBCClass(this.generateProxyCalendarBytecode(type, true), l);
         }

         proxy = (ProxyCalendar)this.instantiateProxy(pcls, (Constructor)null, (Object[])null);
         this._proxies.put(type, proxy);
      }

      return proxy;
   }

   private ProxyBean getFactoryProxyBean(Object orig) {
      final Class type = orig.getClass();
      if (this.isUnproxyable(type)) {
         return null;
      } else {
         ProxyBean proxy = (ProxyBean)this._proxies.get(type);
         if (proxy == null && !this._proxies.containsKey(type)) {
            ClassLoader l = GeneratedClasses.getMostDerivedLoader(type, ProxyBean.class);
            Class pcls = this.loadBuildTimeProxy(type, l);
            if (pcls == null) {
               BCClass bc = (BCClass)AccessController.doPrivileged(new PrivilegedAction() {
                  public Object run() {
                     return ProxyManagerImpl.this.generateProxyBeanBytecode(type, true);
                  }
               });
               if (bc != null) {
                  pcls = GeneratedClasses.loadBCClass(bc, l);
               }
            }

            if (pcls != null) {
               proxy = (ProxyBean)this.instantiateProxy(pcls, this.findCopyConstructor(type), new Object[]{orig});
            }

            this._proxies.put(type, proxy);
         }

         return proxy;
      }
   }

   protected boolean isUnproxyable(Class type) {
      while(type != null && type != Object.class) {
         if (this._unproxyable.contains(type.getName())) {
            return true;
         }

         type = type.getSuperclass();
      }

      return false;
   }

   protected Class loadBuildTimeProxy(Class type, ClassLoader loader) {
      try {
         return Class.forName(getProxyClassName(type, false), true, loader);
      } catch (Throwable var4) {
         return null;
      }
   }

   private Proxy instantiateProxy(Class cls, Constructor cons, Object[] args) {
      try {
         return cons != null ? (Proxy)cls.getConstructor(cons.getParameterTypes()).newInstance(args) : (Proxy)AccessController.doPrivileged(J2DoPrivHelper.newInstanceAction(cls));
      } catch (InstantiationException var6) {
         throw new UnsupportedException(_loc.get("cant-newinstance", (Object)cls.getSuperclass().getName()));
      } catch (PrivilegedActionException var7) {
         Exception e = var7.getException();
         if (e instanceof InstantiationException) {
            throw new UnsupportedException(_loc.get("cant-newinstance", (Object)cls.getSuperclass().getName()));
         } else {
            throw (new GeneralException(cls.getName())).setCause(e);
         }
      } catch (Throwable var8) {
         throw (new GeneralException(cls.getName())).setCause(var8);
      }
   }

   protected BCClass generateProxyCollectionBytecode(Class type, boolean runtime) {
      assertNotFinal(type);
      Project project = new Project();
      BCClass bc = (BCClass)AccessController.doPrivileged(J2DoPrivHelper.loadProjectClassAction(project, getProxyClassName(type, runtime)));
      bc.setSuperclass(type);
      bc.declareInterface(ProxyCollection.class);
      this.delegateConstructors(bc, type);
      this.addProxyMethods(bc, false);
      this.addProxyCollectionMethods(bc, type);
      this.proxyRecognizedMethods(bc, type, ProxyCollections.class, ProxyCollection.class);
      this.proxySetters(bc, type);
      this.addWriteReplaceMethod(bc, runtime);
      return bc;
   }

   private static String getProxyClassName(Class type, boolean runtime) {
      String id = runtime ? "$" + nextProxyId() : "";
      return Strings.getPackageName(ProxyManagerImpl.class) + "." + type.getName().replace('.', '$') + id + "$proxy";
   }

   private static void assertNotFinal(Class type) {
      if (Modifier.isFinal(type.getModifiers())) {
         throw new UnsupportedException(_loc.get("no-proxy-final", (Object)type));
      }
   }

   protected BCClass generateProxyMapBytecode(Class type, boolean runtime) {
      assertNotFinal(type);
      Project project = new Project();
      BCClass bc = (BCClass)AccessController.doPrivileged(J2DoPrivHelper.loadProjectClassAction(project, getProxyClassName(type, runtime)));
      bc.setSuperclass(type);
      bc.declareInterface(ProxyMap.class);
      this.delegateConstructors(bc, type);
      this.addProxyMethods(bc, false);
      this.addProxyMapMethods(bc, type);
      this.proxyRecognizedMethods(bc, type, ProxyMaps.class, ProxyMap.class);
      this.proxySetters(bc, type);
      this.addWriteReplaceMethod(bc, runtime);
      return bc;
   }

   protected BCClass generateProxyDateBytecode(Class type, boolean runtime) {
      assertNotFinal(type);
      Project project = new Project();
      BCClass bc = (BCClass)AccessController.doPrivileged(J2DoPrivHelper.loadProjectClassAction(project, getProxyClassName(type, runtime)));
      bc.setSuperclass(type);
      bc.declareInterface(ProxyDate.class);
      this.delegateConstructors(bc, type);
      this.addProxyMethods(bc, true);
      this.addProxyDateMethods(bc, type);
      this.proxySetters(bc, type);
      this.addWriteReplaceMethod(bc, runtime);
      return bc;
   }

   protected BCClass generateProxyCalendarBytecode(Class type, boolean runtime) {
      assertNotFinal(type);
      Project project = new Project();
      BCClass bc = (BCClass)AccessController.doPrivileged(J2DoPrivHelper.loadProjectClassAction(project, getProxyClassName(type, runtime)));
      bc.setSuperclass(type);
      bc.declareInterface(ProxyCalendar.class);
      this.delegateConstructors(bc, type);
      this.addProxyMethods(bc, true);
      this.addProxyCalendarMethods(bc, type);
      this.proxySetters(bc, type);
      this.addWriteReplaceMethod(bc, runtime);
      return bc;
   }

   protected BCClass generateProxyBeanBytecode(Class type, boolean runtime) {
      if (Modifier.isFinal(type.getModifiers())) {
         return null;
      } else if (ImplHelper.isManagedType((OpenJPAConfiguration)null, type)) {
         return null;
      } else {
         Constructor cons = this.findCopyConstructor(type);
         if (cons == null) {
            Constructor[] cs = type.getConstructors();

            for(int i = 0; cons == null && i < cs.length; ++i) {
               if (cs[i].getParameterTypes().length == 0) {
                  cons = cs[i];
               }
            }

            if (cons == null) {
               return null;
            }
         }

         Project project = new Project();
         BCClass bc = (BCClass)AccessController.doPrivileged(J2DoPrivHelper.loadProjectClassAction(project, getProxyClassName(type, runtime)));
         bc.setSuperclass(type);
         bc.declareInterface(ProxyBean.class);
         this.delegateConstructors(bc, type);
         this.addProxyMethods(bc, true);
         this.addProxyBeanMethods(bc, type, cons);
         if (!this.proxySetters(bc, type)) {
            return null;
         } else {
            this.addWriteReplaceMethod(bc, runtime);
            return bc;
         }
      }
   }

   private void delegateConstructors(BCClass bc, Class type) {
      Constructor[] cons = type.getConstructors();

      for(int i = 0; i < cons.length; ++i) {
         Class[] params = cons[i].getParameterTypes();
         BCMethod m = bc.declareMethod("<init>", Void.TYPE, params);
         m.makePublic();
         Code code = m.getCode(true);
         code.aload().setThis();

         for(int j = 0; j < params.length; ++j) {
            code.xload().setParam(j).setType(params[j]);
         }

         code.invokespecial().setMethod(cons[i]);
         code.vreturn();
         code.calculateMaxStack();
         code.calculateMaxLocals();
      }

   }

   private void addProxyMethods(BCClass bc, boolean changeTracker) {
      BCField sm = bc.declareField("sm", OpenJPAStateManager.class);
      sm.setTransient(true);
      BCField field = bc.declareField("field", Integer.TYPE);
      field.setTransient(true);
      BCMethod m = bc.declareMethod("setOwner", Void.TYPE, new Class[]{OpenJPAStateManager.class, Integer.TYPE});
      m.makePublic();
      Code code = m.getCode(true);
      code.aload().setThis();
      code.aload().setParam(0);
      code.putfield().setField(sm);
      code.aload().setThis();
      code.iload().setParam(1);
      code.putfield().setField(field);
      code.vreturn();
      code.calculateMaxStack();
      code.calculateMaxLocals();
      m = bc.declareMethod("getOwner", OpenJPAStateManager.class, (Class[])null);
      m.makePublic();
      code = m.getCode(true);
      code.aload().setThis();
      code.getfield().setField(sm);
      code.areturn();
      code.calculateMaxStack();
      code.calculateMaxLocals();
      m = bc.declareMethod("getOwnerField", Integer.TYPE, (Class[])null);
      m.makePublic();
      code = m.getCode(true);
      code.aload().setThis();
      code.getfield().setField(field);
      code.ireturn();
      code.calculateMaxStack();
      code.calculateMaxLocals();
      m = bc.declareMethod("clone", Object.class, (Class[])null);
      m.makePublic();
      code = m.getCode(true);
      code.aload().setThis();
      code.invokespecial().setMethod(bc.getSuperclassType(), "clone", Object.class, (Class[])null);
      code.checkcast().setType(Proxy.class);
      int other = code.getNextLocalsIndex();
      code.astore().setLocal(other);
      code.aload().setLocal(other);
      code.constant().setNull();
      code.constant().setValue(0);
      code.invokeinterface().setMethod(Proxy.class, "setOwner", Void.TYPE, new Class[]{OpenJPAStateManager.class, Integer.TYPE});
      code.aload().setLocal(other);
      code.areturn();
      code.calculateMaxStack();
      code.calculateMaxLocals();
      if (changeTracker) {
         m = bc.declareMethod("getChangeTracker", ChangeTracker.class, (Class[])null);
         m.makePublic();
         code = m.getCode(true);
         code.constant().setNull();
         code.areturn();
         code.calculateMaxStack();
         code.calculateMaxLocals();
      }

   }

   private void addProxyCollectionMethods(BCClass bc, Class type) {
      BCField changeTracker = bc.declareField("changeTracker", CollectionChangeTracker.class);
      changeTracker.setTransient(true);
      BCMethod m = bc.declareMethod("getChangeTracker", ChangeTracker.class, (Class[])null);
      m.makePublic();
      Code code = m.getCode(true);
      code.aload().setThis();
      code.getfield().setField(changeTracker);
      code.areturn();
      code.calculateMaxStack();
      code.calculateMaxLocals();
      Constructor cons = this.findCopyConstructor(type);
      if (cons == null && SortedSet.class.isAssignableFrom(type)) {
         cons = findComparatorConstructor(type);
      }

      Class[] params = cons == null ? new Class[0] : cons.getParameterTypes();
      m = bc.declareMethod("copy", Object.class, new Class[]{Object.class});
      m.makePublic();
      code = m.getCode(true);
      code.anew().setType(type);
      code.dup();
      if (params.length == 1) {
         code.aload().setParam(0);
         if (params[0] == Comparator.class) {
            code.checkcast().setType(SortedSet.class);
            code.invokeinterface().setMethod(SortedSet.class, "comparator", Comparator.class, (Class[])null);
         } else {
            code.checkcast().setType(params[0]);
         }
      }

      code.invokespecial().setMethod(type, "<init>", Void.TYPE, params);
      if (params.length == 0 || params[0] == Comparator.class) {
         code.dup();
         code.aload().setParam(0);
         code.checkcast().setType(Collection.class);
         code.invokevirtual().setMethod(type, "addAll", Boolean.TYPE, new Class[]{Collection.class});
         code.pop();
      }

      code.areturn();
      code.calculateMaxStack();
      code.calculateMaxLocals();
      BCField elementType = bc.declareField("elementType", Class.class);
      elementType.setTransient(true);
      m = bc.declareMethod("getElementType", Class.class, (Class[])null);
      m.makePublic();
      code = m.getCode(true);
      code.aload().setThis();
      code.getfield().setField(elementType);
      code.areturn();
      code.calculateMaxStack();
      code.calculateMaxLocals();
      m = bc.declareMethod("newInstance", ProxyCollection.class, new Class[]{Class.class, Comparator.class, Boolean.TYPE, Boolean.TYPE});
      m.makePublic();
      code = m.getCode(true);
      code.anew().setType(bc);
      code.dup();
      cons = findComparatorConstructor(type);
      params = cons == null ? new Class[0] : cons.getParameterTypes();
      if (params.length == 1) {
         code.aload().setParam(1);
      }

      code.invokespecial().setMethod("<init>", Void.TYPE, params);
      int ret = code.getNextLocalsIndex();
      code.astore().setLocal(ret);
      code.aload().setLocal(ret);
      code.aload().setParam(0);
      code.putfield().setField(elementType);
      code.iload().setParam(2);
      JumpInstruction ifins = code.ifeq();
      code.aload().setLocal(ret);
      code.anew().setType(CollectionChangeTrackerImpl.class);
      code.dup();
      code.aload().setLocal(ret);
      code.constant().setValue(this.allowsDuplicates(type));
      code.constant().setValue(this.isOrdered(type));
      code.aload().setParam(3);
      code.invokespecial().setMethod(CollectionChangeTrackerImpl.class, "<init>", Void.TYPE, new Class[]{Collection.class, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE});
      code.putfield().setField(changeTracker);
      ifins.setTarget(code.aload().setLocal(ret));
      code.areturn();
      code.calculateMaxStack();
      code.calculateMaxLocals();
   }

   protected boolean allowsDuplicates(Class type) {
      return !Set.class.isAssignableFrom(type);
   }

   protected boolean isOrdered(Class type) {
      return List.class.isAssignableFrom(type) || "java.util.LinkedHashSet".equals(type.getName());
   }

   private void addProxyMapMethods(BCClass bc, Class type) {
      BCField changeTracker = bc.declareField("changeTracker", MapChangeTracker.class);
      changeTracker.setTransient(true);
      BCMethod m = bc.declareMethod("getChangeTracker", ChangeTracker.class, (Class[])null);
      m.makePublic();
      Code code = m.getCode(true);
      code.aload().setThis();
      code.getfield().setField(changeTracker);
      code.areturn();
      code.calculateMaxStack();
      code.calculateMaxLocals();
      Constructor cons = this.findCopyConstructor(type);
      if (cons == null && SortedMap.class.isAssignableFrom(type)) {
         cons = findComparatorConstructor(type);
      }

      Class[] params = cons == null ? new Class[0] : cons.getParameterTypes();
      m = bc.declareMethod("copy", Object.class, new Class[]{Object.class});
      m.makePublic();
      code = m.getCode(true);
      code.anew().setType(type);
      code.dup();
      if (params.length == 1) {
         code.aload().setParam(0);
         if (params[0] == Comparator.class) {
            code.checkcast().setType(SortedMap.class);
            code.invokeinterface().setMethod(SortedMap.class, "comparator", Comparator.class, (Class[])null);
         } else {
            code.checkcast().setType(params[0]);
         }
      }

      code.invokespecial().setMethod(type, "<init>", Void.TYPE, params);
      if (params.length == 0 || params[0] == Comparator.class) {
         code.dup();
         code.aload().setParam(0);
         code.checkcast().setType(Map.class);
         code.invokevirtual().setMethod(type, "putAll", Void.TYPE, new Class[]{Map.class});
      }

      code.areturn();
      code.calculateMaxStack();
      code.calculateMaxLocals();
      BCField keyType = bc.declareField("keyType", Class.class);
      keyType.setTransient(true);
      m = bc.declareMethod("getKeyType", Class.class, (Class[])null);
      m.makePublic();
      code = m.getCode(true);
      code.aload().setThis();
      code.getfield().setField(keyType);
      code.areturn();
      code.calculateMaxStack();
      code.calculateMaxLocals();
      BCField valueType = bc.declareField("valueType", Class.class);
      valueType.setTransient(true);
      m = bc.declareMethod("getValueType", Class.class, (Class[])null);
      m.makePublic();
      code = m.getCode(true);
      code.aload().setThis();
      code.getfield().setField(valueType);
      code.areturn();
      code.calculateMaxStack();
      code.calculateMaxLocals();
      m = bc.declareMethod("newInstance", ProxyMap.class, new Class[]{Class.class, Class.class, Comparator.class, Boolean.TYPE, Boolean.TYPE});
      m.makePublic();
      code = m.getCode(true);
      code.anew().setType(bc);
      code.dup();
      cons = findComparatorConstructor(type);
      params = cons == null ? new Class[0] : cons.getParameterTypes();
      if (params.length == 1) {
         code.aload().setParam(2);
      }

      code.invokespecial().setMethod("<init>", Void.TYPE, params);
      int ret = code.getNextLocalsIndex();
      code.astore().setLocal(ret);
      code.aload().setLocal(ret);
      code.aload().setParam(0);
      code.putfield().setField(keyType);
      code.aload().setLocal(ret);
      code.aload().setParam(1);
      code.putfield().setField(valueType);
      code.iload().setParam(3);
      JumpInstruction ifins = code.ifeq();
      code.aload().setLocal(ret);
      code.anew().setType(MapChangeTrackerImpl.class);
      code.dup();
      code.aload().setLocal(ret);
      code.aload().setParam(4);
      code.invokespecial().setMethod(MapChangeTrackerImpl.class, "<init>", Void.TYPE, new Class[]{Map.class, Boolean.TYPE});
      code.putfield().setField(changeTracker);
      ifins.setTarget(code.aload().setLocal(ret));
      code.areturn();
      code.calculateMaxStack();
      code.calculateMaxLocals();
   }

   private void addProxyDateMethods(BCClass bc, Class type) {
      boolean hasDefaultCons = bc.getDeclaredMethod("<init>", (Class[])null) != null;
      boolean hasMillisCons = bc.getDeclaredMethod("<init>", new Class[]{Long.TYPE}) != null;
      if (!hasDefaultCons && !hasMillisCons) {
         throw new UnsupportedException(_loc.get("no-date-cons", (Object)type));
      } else {
         BCMethod m;
         Code code;
         if (!hasDefaultCons) {
            m = bc.declareMethod("<init>", Void.TYPE, (Class[])null);
            m.makePublic();
            code = m.getCode(true);
            code.aload().setThis();
            code.invokestatic().setMethod(System.class, "currentTimeMillis", Long.TYPE, (Class[])null);
            code.invokespecial().setMethod(type, "<init>", Void.TYPE, new Class[]{Long.TYPE});
            code.vreturn();
            code.calculateMaxStack();
            code.calculateMaxLocals();
         }

         Constructor cons = this.findCopyConstructor(type);
         Class[] params;
         if (cons != null) {
            params = cons.getParameterTypes();
         } else if (hasMillisCons) {
            params = new Class[]{Long.TYPE};
         } else {
            params = new Class[0];
         }

         m = bc.declareMethod("copy", Object.class, new Class[]{Object.class});
         m.makePublic();
         code = m.getCode(true);
         code.anew().setType(type);
         code.dup();
         if (params.length == 1) {
            if (params[0] == Long.TYPE) {
               code.aload().setParam(0);
               code.checkcast().setType(Date.class);
               code.invokevirtual().setMethod(Date.class, "getTime", Long.TYPE, (Class[])null);
            } else {
               code.aload().setParam(0);
               code.checkcast().setType(params[0]);
            }
         }

         code.invokespecial().setMethod(type, "<init>", Void.TYPE, params);
         if (params.length == 0) {
            code.dup();
            code.aload().setParam(0);
            code.checkcast().setType(Date.class);
            code.invokevirtual().setMethod(Date.class, "getTime", Long.TYPE, (Class[])null);
            code.invokevirtual().setMethod(type, "setTime", Void.TYPE, new Class[]{Long.TYPE});
         }

         if ((params.length == 0 || params[0] == Long.TYPE) && Timestamp.class.isAssignableFrom(type)) {
            code.dup();
            code.aload().setParam(0);
            code.checkcast().setType(Timestamp.class);
            code.invokevirtual().setMethod(Timestamp.class, "getNanos", Integer.TYPE, (Class[])null);
            code.invokevirtual().setMethod(type, "setNanos", Void.TYPE, new Class[]{Integer.TYPE});
         }

         code.areturn();
         code.calculateMaxStack();
         code.calculateMaxLocals();
         m = bc.declareMethod("newInstance", ProxyDate.class, (Class[])null);
         m.makePublic();
         code = m.getCode(true);
         code.anew().setType(bc);
         code.dup();
         code.invokespecial().setMethod("<init>", Void.TYPE, (Class[])null);
         code.areturn();
         code.calculateMaxStack();
         code.calculateMaxLocals();
      }
   }

   private void addProxyCalendarMethods(BCClass bc, Class type) {
      Constructor cons = this.findCopyConstructor(type);
      Class[] params = cons == null ? new Class[0] : cons.getParameterTypes();
      BCMethod m = bc.declareMethod("copy", Object.class, new Class[]{Object.class});
      m.makePublic();
      Code code = m.getCode(true);
      code.anew().setType(type);
      code.dup();
      if (params.length == 1) {
         code.aload().setParam(0);
         code.checkcast().setType(params[0]);
      }

      code.invokespecial().setMethod(type, "<init>", Void.TYPE, params);
      if (params.length == 0) {
         code.dup();
         code.aload().setParam(0);
         code.checkcast().setType(Calendar.class);
         code.invokevirtual().setMethod(Calendar.class, "getTimeInMillis", Long.TYPE, (Class[])null);
         code.invokevirtual().setMethod(type, "setTimeInMillis", Void.TYPE, new Class[]{Long.TYPE});
         code.dup();
         code.aload().setParam(0);
         code.checkcast().setType(Calendar.class);
         code.invokevirtual().setMethod(Calendar.class, "isLenient", Boolean.TYPE, (Class[])null);
         code.invokevirtual().setMethod(type, "setLenient", Void.TYPE, new Class[]{Boolean.TYPE});
         code.dup();
         code.aload().setParam(0);
         code.checkcast().setType(Calendar.class);
         code.invokevirtual().setMethod(Calendar.class, "getFirstDayOfWeek", Integer.TYPE, (Class[])null);
         code.invokevirtual().setMethod(type, "setFirstDayOfWeek", Void.TYPE, new Class[]{Integer.TYPE});
         code.dup();
         code.aload().setParam(0);
         code.checkcast().setType(Calendar.class);
         code.invokevirtual().setMethod(Calendar.class, "getMinimalDaysInFirstWeek", Integer.TYPE, (Class[])null);
         code.invokevirtual().setMethod(type, "setMinimalDaysInFirstWeek", Void.TYPE, new Class[]{Integer.TYPE});
         code.dup();
         code.aload().setParam(0);
         code.checkcast().setType(Calendar.class);
         code.invokevirtual().setMethod(Calendar.class, "getTimeZone", TimeZone.class, (Class[])null);
         code.invokevirtual().setMethod(type, "setTimeZone", Void.TYPE, new Class[]{TimeZone.class});
      }

      code.areturn();
      code.calculateMaxStack();
      code.calculateMaxLocals();
      m = bc.declareMethod("newInstance", ProxyCalendar.class, (Class[])null);
      m.makePublic();
      code = m.getCode(true);
      code.anew().setType(bc);
      code.dup();
      code.invokespecial().setMethod("<init>", Void.TYPE, (Class[])null);
      code.areturn();
      code.calculateMaxStack();
      code.calculateMaxLocals();
      m = bc.declareMethod("computeFields", Void.TYPE, (Class[])null);
      m.makeProtected();
      code = m.getCode(true);
      code.aload().setThis();
      code.constant().setValue(true);
      code.invokestatic().setMethod(Proxies.class, "dirty", Void.TYPE, new Class[]{Proxy.class, Boolean.TYPE});
      code.aload().setThis();
      code.invokespecial().setMethod(type, "computeFields", Void.TYPE, (Class[])null);
      code.vreturn();
      code.calculateMaxStack();
      code.calculateMaxLocals();
   }

   private void addProxyBeanMethods(BCClass bc, Class type, Constructor cons) {
      BCMethod m = bc.declareMethod("copy", Object.class, new Class[]{Object.class});
      m.makePublic();
      Code code = m.getCode(true);
      code.anew().setType(type);
      code.dup();
      Class[] params = cons.getParameterTypes();
      if (params.length == 1) {
         code.aload().setParam(0);
         code.checkcast().setType(params[0]);
      }

      code.invokespecial().setMethod(cons);
      if (params.length == 0) {
         this.copyProperties(type, code);
      }

      code.areturn();
      code.calculateMaxStack();
      code.calculateMaxLocals();
      m = bc.declareMethod("newInstance", ProxyBean.class, new Class[]{Object.class});
      m.makePublic();
      code = m.getCode(true);
      code.anew().setType(bc);
      code.dup();
      if (params.length == 1) {
         code.aload().setParam(0);
         code.checkcast().setType(params[0]);
      }

      code.invokespecial().setMethod("<init>", Void.TYPE, params);
      if (params.length == 0) {
         this.copyProperties(type, code);
      }

      code.areturn();
      code.calculateMaxStack();
      code.calculateMaxLocals();
   }

   private void copyProperties(Class type, Code code) {
      int copy = code.getNextLocalsIndex();
      code.astore().setLocal(copy);
      Method[] meths = type.getMethods();

      for(int i = 0; i < meths.length; ++i) {
         int mods = meths[i].getModifiers();
         if (Modifier.isPublic(mods) && !Modifier.isStatic(mods) && startsWith(meths[i].getName(), "set") && meths[i].getParameterTypes().length == 1) {
            Method getter = this.findGetter(type, meths[i]);
            if (getter != null) {
               code.aload().setLocal(copy);
               code.aload().setParam(0);
               code.checkcast().setType(type);
               code.invokevirtual().setMethod(getter);
               code.invokevirtual().setMethod(meths[i]);
            }
         }
      }

      code.aload().setLocal(copy);
   }

   private void proxyRecognizedMethods(BCClass bc, Class type, Class helper, Class proxyType) {
      Method[] meths = type.getMethods();

      for(int i = 0; i < meths.length; ++i) {
         Class[] params = toHelperParameters(meths[i].getParameterTypes(), proxyType);

         Method match;
         try {
            match = helper.getMethod(meths[i].getName(), params);
            this.proxyOverrideMethod(bc, meths[i], match, params);
         } catch (NoSuchMethodException var16) {
            match = null;

            try {
               match = helper.getMethod("before" + StringUtils.capitalize(meths[i].getName()), params);
            } catch (NoSuchMethodException var14) {
            } catch (Exception var15) {
               throw new GeneralException(var15);
            }

            Method after = null;
            Class[] afterParams = null;

            try {
               afterParams = toHelperAfterParameters(params, meths[i].getReturnType(), match == null ? Void.TYPE : match.getReturnType());
               after = helper.getMethod("after" + StringUtils.capitalize(meths[i].getName()), afterParams);
            } catch (NoSuchMethodException var12) {
            } catch (Exception var13) {
               throw new GeneralException(var13);
            }

            if (match != null || after != null) {
               this.proxyBeforeAfterMethod(bc, type, meths[i], match, params, after, afterParams);
            }
         } catch (Exception var17) {
            throw new GeneralException(var17);
         }
      }

   }

   private static Class[] toHelperParameters(Class[] cls, Class helper) {
      Class[] params = new Class[cls.length + 1];
      params[0] = helper;
      System.arraycopy(cls, 0, params, 1, cls.length);
      return params;
   }

   private static Class[] toHelperAfterParameters(Class[] cls, Class ret, Class beforeRet) {
      if (ret == Void.TYPE && beforeRet == Void.TYPE) {
         return cls;
      } else {
         int len = cls.length;
         if (ret != Void.TYPE) {
            ++len;
         }

         if (beforeRet != Void.TYPE) {
            ++len;
         }

         Class[] params = new Class[len];
         System.arraycopy(cls, 0, params, 0, cls.length);
         int pos = cls.length;
         if (ret != Void.TYPE) {
            params[pos++] = ret;
         }

         if (beforeRet != Void.TYPE) {
            params[pos++] = beforeRet;
         }

         return params;
      }
   }

   private boolean proxySetters(BCClass bc, Class type) {
      Method[] meths = type.getMethods();
      int setters = 0;

      for(int i = 0; i < meths.length; ++i) {
         if (this.isSetter(meths[i]) && !Modifier.isFinal(meths[i].getModifiers()) && bc.getDeclaredMethod(meths[i].getName(), meths[i].getParameterTypes()) == null) {
            ++setters;
            this.proxySetter(bc, type, meths[i]);
         }
      }

      return setters > 0;
   }

   private void proxyOverrideMethod(BCClass bc, Method meth, Method helper, Class[] params) {
      BCMethod m = bc.declareMethod(meth.getName(), meth.getReturnType(), meth.getParameterTypes());
      m.makePublic();
      Code code = m.getCode(true);
      code.aload().setThis();

      for(int i = 1; i < params.length; ++i) {
         code.xload().setParam(i - 1).setType(params[i]);
      }

      code.invokestatic().setMethod(helper);
      code.xreturn().setType(meth.getReturnType());
      code.calculateMaxStack();
      code.calculateMaxLocals();
   }

   private void proxyBeforeAfterMethod(BCClass bc, Class type, Method meth, Method before, Class[] params, Method after, Class[] afterParams) {
      BCMethod m = bc.declareMethod(meth.getName(), meth.getReturnType(), meth.getParameterTypes());
      m.makePublic();
      Code code = m.getCode(true);
      int beforeRet = -1;
      int ret;
      if (before != null) {
         code.aload().setThis();

         for(ret = 1; ret < params.length; ++ret) {
            code.xload().setParam(ret - 1).setType(params[ret]);
         }

         code.invokestatic().setMethod(before);
         if (after != null && before.getReturnType() != Void.TYPE) {
            beforeRet = code.getNextLocalsIndex();
            code.xstore().setLocal(beforeRet).setType(before.getReturnType());
         }
      }

      code.aload().setThis();

      for(ret = 1; ret < params.length; ++ret) {
         code.xload().setParam(ret - 1).setType(params[ret]);
      }

      code.invokespecial().setMethod(type, meth.getName(), meth.getReturnType(), meth.getParameterTypes());
      if (after != null) {
         ret = -1;
         if (meth.getReturnType() != Void.TYPE) {
            ret = code.getNextLocalsIndex();
            code.xstore().setLocal(ret).setType(meth.getReturnType());
         }

         code.aload().setThis();

         for(int i = 1; i < params.length; ++i) {
            code.xload().setParam(i - 1).setType(params[i]);
         }

         if (ret != -1) {
            code.xload().setLocal(ret).setType(meth.getReturnType());
         }

         if (beforeRet != -1) {
            code.xload().setLocal(beforeRet).setType(before.getReturnType());
         }

         code.invokestatic().setMethod(after);
      }

      code.xreturn().setType(meth.getReturnType());
      code.calculateMaxStack();
      code.calculateMaxLocals();
   }

   protected boolean isSetter(Method meth) {
      return startsWith(meth.getName(), "set") || startsWith(meth.getName(), "add") || startsWith(meth.getName(), "remove") || startsWith(meth.getName(), "insert") || startsWith(meth.getName(), "clear") || startsWith(meth.getName(), "roll");
   }

   protected Method findGetter(Class type, Method setter) {
      String name = setter.getName().substring(3);
      Class param = setter.getParameterTypes()[0];

      Method getter;
      try {
         getter = type.getMethod("get" + name, (Class[])null);
         if (getter.getReturnType().isAssignableFrom(param) || param.isAssignableFrom(getter.getReturnType())) {
            return getter;
         }
      } catch (NoSuchMethodException var9) {
      } catch (Exception var10) {
         throw new GeneralException(var10);
      }

      if (param == Boolean.TYPE || param == Boolean.class) {
         try {
            getter = type.getMethod("is" + name, (Class[])null);
            if (getter.getReturnType().isAssignableFrom(param) || param.isAssignableFrom(getter.getReturnType())) {
               return getter;
            }
         } catch (NoSuchMethodException var7) {
         } catch (Exception var8) {
            throw new GeneralException(var8);
         }
      }

      return null;
   }

   private static boolean startsWith(String str, String token) {
      return str.startsWith(token) && (str.length() == token.length() || Character.isUpperCase(str.charAt(token.length())));
   }

   private void proxySetter(BCClass bc, Class type, Method meth) {
      Class[] params = meth.getParameterTypes();
      Class ret = meth.getReturnType();
      BCMethod m = bc.declareMethod(meth.getName(), ret, params);
      m.makePublic();
      Code code = m.getCode(true);
      code.aload().setThis();
      code.constant().setValue(true);
      code.invokestatic().setMethod(Proxies.class, "dirty", Void.TYPE, new Class[]{Proxy.class, Boolean.TYPE});
      code.aload().setThis();

      for(int i = 0; i < params.length; ++i) {
         code.xload().setParam(i).setType(params[i]);
      }

      code.invokespecial().setMethod(type, meth.getName(), ret, params);
      code.xreturn().setType(ret);
      code.calculateMaxStack();
      code.calculateMaxLocals();
   }

   private void addWriteReplaceMethod(BCClass bc, boolean runtime) {
      BCMethod m = bc.declareMethod("writeReplace", Object.class, (Class[])null);
      m.makeProtected();
      m.getExceptions(true).addException(ObjectStreamException.class);
      Code code = m.getCode(true);
      code.aload().setThis();
      code.constant().setValue(!runtime);
      code.invokestatic().setMethod(Proxies.class, "writeReplace", Object.class, new Class[]{Proxy.class, Boolean.TYPE});
      code.areturn();
      code.calculateMaxLocals();
      code.calculateMaxStack();
   }

   private static synchronized long nextProxyId() {
      return (long)(_proxyId++);
   }

   protected Constructor findCopyConstructor(Class cls) {
      Constructor[] cons = cls.getConstructors();
      Constructor match = null;
      Class matchParam = null;

      for(int i = 0; i < cons.length; ++i) {
         Class[] params = cons[i].getParameterTypes();
         if (params.length == 1) {
            if (params[0] == cls) {
               return cons[i];
            }

            if (params[0].isAssignableFrom(cls) && (matchParam == null || matchParam.isAssignableFrom(params[0]))) {
               match = cons[i];
               matchParam = params[0];
            }
         }
      }

      return match;
   }

   private static Constructor findComparatorConstructor(Class cls) {
      try {
         return cls.getConstructor(Comparator.class);
      } catch (NoSuchMethodException var2) {
         return null;
      } catch (Exception var3) {
         throw new GeneralException(var3);
      }
   }

   public static void main(String[] args) throws ClassNotFoundException, IOException {
      File dir = Files.getClassFile(ProxyManagerImpl.class);
      dir = dir == null ? new File((String)AccessController.doPrivileged(J2DoPrivHelper.getPropertyAction("user.dir"))) : dir.getParentFile();
      Options opts = new Options();
      args = opts.setFromCmdLine(args);
      List types = new ArrayList();
      types.addAll(Arrays.asList(args));
      int utils = opts.removeIntProperty("utils", "u", 0);
      if (utils >= 4) {
         types.addAll(Arrays.asList(java.sql.Date.class.getName(), Time.class.getName(), Timestamp.class.getName(), ArrayList.class.getName(), Date.class.getName(), GregorianCalendar.class.getName(), HashMap.class.getName(), HashSet.class.getName(), Hashtable.class.getName(), LinkedList.class.getName(), Properties.class.getName(), TreeMap.class.getName(), TreeSet.class.getName(), Vector.class.getName()));
      }

      if (utils >= 5) {
         types.addAll(Arrays.asList("java.util.EnumMap", "java.util.IdentityHashMap", "java.util.LinkedHashMap", "java.util.LinkedHashSet", "java.util.PriorityQueue"));
      }

      final ProxyManagerImpl mgr = new ProxyManagerImpl();

      for(int i = 0; i < types.size(); ++i) {
         final Class cls = Class.forName((String)types.get(i));

         try {
            if (Class.forName(getProxyClassName(cls, false), true, GeneratedClasses.getMostDerivedLoader(cls, Proxy.class)) != null) {
               continue;
            }
         } catch (Throwable var10) {
         }

         BCClass bc;
         if (Collection.class.isAssignableFrom(cls)) {
            bc = mgr.generateProxyCollectionBytecode(cls, false);
         } else if (Map.class.isAssignableFrom(cls)) {
            bc = mgr.generateProxyMapBytecode(cls, false);
         } else if (Date.class.isAssignableFrom(cls)) {
            bc = mgr.generateProxyDateBytecode(cls, false);
         } else if (Calendar.class.isAssignableFrom(cls)) {
            bc = mgr.generateProxyCalendarBytecode(cls, false);
         } else {
            bc = (BCClass)AccessController.doPrivileged(new PrivilegedAction() {
               public Object run() {
                  return mgr.generateProxyBeanBytecode(cls, false);
               }
            });
         }

         System.out.println(bc.getName());
         bc.write(new File(dir, bc.getClassName() + ".class"));
      }

   }

   static {
      _stdCollections.put(Collection.class, ArrayList.class);
      _stdCollections.put(Set.class, HashSet.class);
      _stdCollections.put(SortedSet.class, TreeSet.class);
      _stdCollections.put(List.class, ArrayList.class);
      _stdCollections.put(Queue.class, LinkedList.class);
      _stdMaps.put(Map.class, HashMap.class);
      _stdMaps.put(SortedMap.class, TreeMap.class);
   }
}
