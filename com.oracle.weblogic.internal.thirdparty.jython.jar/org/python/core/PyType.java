package org.python.core;

import java.io.Serializable;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicReferenceArray;
import org.python.antlr.ast.cmpopType;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposeAsSuperclass;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;
import org.python.expose.TypeBuilder;
import org.python.google.common.collect.MapMaker;
import org.python.modules._weakref.WeakrefModule;
import org.python.util.Generic;

@ExposedType(
   name = "type",
   doc = "type(object) -> the object's type\ntype(name, bases, dict) -> a new type"
)
public class PyType extends PyObject implements Serializable, Traverseproc {
   public static final PyType TYPE;
   protected String name;
   protected PyType base;
   protected PyObject[] bases = new PyObject[0];
   protected PyObject dict;
   protected PyObject[] mro;
   private long tp_flags;
   protected Class underlying_class;
   protected boolean builtin;
   protected boolean instantiable = true;
   boolean hasGet;
   boolean hasSet;
   boolean hasDelete;
   private boolean isBaseType = true;
   protected boolean needs_userdict;
   protected boolean needs_weakref;
   protected boolean needs_finalizer;
   private volatile boolean usesObjectGetattribute;
   private volatile Object versionTag = new Object();
   private int numSlots;
   private int ownSlots = 0;
   private transient ReferenceQueue subclasses_refq = new ReferenceQueue();
   private Set subclasses = Generic.linkedHashSet();
   private static final MethodCache methodCache;
   // $FF: synthetic field
   static final boolean $assertionsDisabled;

   protected PyType(PyType subtype) {
      super(subtype);
   }

   private PyType() {
   }

   private PyType(boolean ignored) {
      super(ignored);
   }

   @ExposedNew
   static final PyObject type___new__(PyNewWrapper new_, boolean init, PyType subtype, PyObject[] args, String[] keywords) {
      if (args.length == 1 && keywords.length == 0) {
         PyObject obj = args[0];
         PyType objType = obj.getType();
         PyType psmType = fromClass(PyStringMap.class);
         return objType == psmType ? PyDictionary.TYPE : objType;
      } else if (args.length + keywords.length != 3) {
         throw Py.TypeError("type() takes 1 or 3 arguments");
      } else {
         ArgParser ap = new ArgParser("type()", args, keywords, "name", "bases", "dict");
         String name = ap.getString(0);
         PyTuple bases = (PyTuple)ap.getPyObjectByType(1, PyTuple.TYPE);
         PyObject dict = ap.getPyObject(2);
         if (!(dict instanceof AbstractDict)) {
            throw Py.TypeError("type(): argument 3 must be dict, not " + dict.getType());
         } else {
            return newType(new_, subtype, name, bases, dict);
         }
      }
   }

   final void type___init__(PyObject[] args, String[] kwds) {
      if (kwds.length > 0) {
         throw Py.TypeError("type.__init__() takes no keyword arguments");
      } else if (args.length != 1 && args.length != 3) {
         throw Py.TypeError("type.__init__() takes 1 or 3 arguments");
      } else {
         this.object___init__(Py.EmptyObjects, Py.NoKeywords);
      }
   }

   public static PyObject newType(PyNewWrapper new_, PyType metatype, String name, PyTuple bases, PyObject dict) {
      PyObject[] tmpBases = bases.getArray();
      PyType winner = findMostDerivedMetatype(tmpBases, metatype);
      if (winner != metatype) {
         PyObject winnerNew = winner.lookup("__new__");
         if (winnerNew != null && winnerNew != new_) {
            return invokeNew(winnerNew, winner, false, new PyObject[]{new PyString(name), bases, dict}, Py.NoKeywords);
         }

         metatype = winner;
      }

      if (metatype == fromClass(Class.class)) {
         metatype = TYPE;
      }

      Object type;
      if (new_.for_type == metatype) {
         type = new PyType();
      } else {
         type = new PyTypeDerived(metatype);
      }

      PyObject dict = ((AbstractDict)dict).copy();
      ((PyType)type).name = name;
      ((PyType)type).bases = tmpBases.length == 0 ? new PyObject[]{PyObject.TYPE} : tmpBases;
      ((PyType)type).dict = dict;
      ((PyType)type).tp_flags = 1536L;
      boolean defines_dict = dict.__finditem__("__dict__") != null;
      List interfaces = Generic.list();
      Class baseProxyClass = getJavaLayout(((PyType)type).bases, interfaces);
      ((PyType)type).setupProxy(baseProxyClass, interfaces);
      PyType base = ((PyType)type).base = best_base(((PyType)type).bases);
      if (!base.isBaseType) {
         throw Py.TypeError(String.format("type '%.100s' is not an acceptable base type", base.name));
      } else {
         ((PyType)type).createAllSlots(!base.needs_userdict && !defines_dict, !base.needs_weakref);
         ((PyType)type).ensureAttributes();
         ((PyType)type).invalidateMethodCache();
         PyObject[] var12 = ((PyType)type).bases;
         int var13 = var12.length;

         for(int var14 = 0; var14 < var13; ++var14) {
            PyObject cur = var12[var14];
            if (cur instanceof PyType) {
               ((PyType)cur).attachSubclass((PyType)type);
            }
         }

         return (PyObject)type;
      }
   }

   private static int findSlottedAncestors(PyType tp, List dest, Map slotsMap) {
      int baseEnd = false;
      if (tp.base != null && tp.base.numSlots > 0 && !slotsMap.containsKey(tp.base)) {
         findSlottedAncestors(tp.base, dest, slotsMap);
      }

      int baseEnd = dest.size();
      PyObject slots = tp.dict.__finditem__("__slots__");
      if (slots != null) {
         dest.add(tp);
         slotsMap.put(tp, slots);
      }

      if (tp.bases.length > 1) {
         PyObject[] var5 = tp.bases;
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            PyObject base = var5[var7];
            if (base != tp.base && base instanceof PyType && ((PyType)base).numSlots != 0 && !slotsMap.containsKey((PyType)base)) {
               findSlottedAncestors((PyType)base, dest, slotsMap);
            }
         }
      }

      return baseEnd;
   }

   private static void insertSlots(PyObject slots, Set dest) {
      if (slots instanceof PyString) {
         slots = new PyTuple(new PyObject[]{(PyObject)slots});
      }

      Iterator var2 = ((PyObject)slots).asIterable().iterator();

      while(var2.hasNext()) {
         PyObject slot = (PyObject)var2.next();
         String slotName = confirmIdentifier(slot);
         if (!slotName.equals("__dict__") && !slotName.equals("__weakref__")) {
            dest.add(slotName);
         }
      }

   }

   private void createAllSlots(boolean mayAddDict, boolean mayAddWeak) {
      List slottedAncestors = Generic.list(this.base.mro.length + (this.bases.length - 1) * 3 + 1);
      Map slotsMap = Generic.identityHashMap(slottedAncestors.size());
      int baseEnd = findSlottedAncestors(this, slottedAncestors, slotsMap);
      int slots_tmp = 0;

      PyType anc;
      for(Iterator var7 = slottedAncestors.iterator(); var7.hasNext(); slots_tmp += anc.numSlots) {
         anc = (PyType)var7.next();
      }

      Set allSlots = Generic.linkedHashSet(2 * slots_tmp);
      if (baseEnd > 0) {
         for(int i = 0; i < baseEnd; ++i) {
            insertSlots((PyObject)slotsMap.get(slottedAncestors.get(i)), allSlots);
         }
      }

      if (!$assertionsDisabled && allSlots.size() != this.base.numSlots) {
         throw new AssertionError();
      } else {
         boolean wantDict = false;
         boolean wantWeak = false;
         PyObject slots = this.dict.__finditem__("__slots__");
         this.ownSlots = 0;
         String slotName;
         if (slots == null) {
            wantDict = mayAddDict;
            wantWeak = mayAddWeak;
            slots_tmp = baseEnd;
         } else {
            if (slots instanceof PyString) {
               slots = new PyTuple(new PyObject[]{(PyObject)slots});
            }

            Iterator var11 = ((PyObject)slots).asIterable().iterator();

            while(true) {
               while(var11.hasNext()) {
                  PyObject slot = (PyObject)var11.next();
                  slotName = confirmIdentifier(slot);
                  if (slotName.equals("__dict__")) {
                     if (mayAddDict && !wantDict) {
                        wantDict = true;
                        continue;
                     }
                  } else if (slotName.equals("__weakref__") && (mayAddWeak && !wantWeak || this.base == PyObject.TYPE)) {
                     wantWeak = true;
                     continue;
                  }

                  if (allSlots.add(slotName)) {
                     ++this.ownSlots;
                  }
               }

               if (this.bases.length > 1 && (mayAddDict && !wantDict || mayAddWeak && !wantWeak)) {
                  PyObject[] var19 = this.bases;
                  int var21 = var19.length;

                  for(int var23 = 0; var23 < var21; ++var23) {
                     PyObject base = var19[var23];
                     if (base != this.base) {
                        if (base instanceof PyClass) {
                           if (mayAddDict && !wantDict) {
                              wantDict = true;
                           }

                           if (mayAddWeak && !wantWeak) {
                              wantWeak = true;
                           }
                           break;
                        }

                        PyType baseType = (PyType)base;
                        if (mayAddDict && !wantDict && baseType.needs_userdict) {
                           wantDict = true;
                        }

                        if (mayAddWeak && !wantWeak && baseType.needs_weakref) {
                           wantWeak = true;
                        }

                        if ((!mayAddDict || wantDict) && (!mayAddWeak || wantWeak)) {
                           break;
                        }
                     }
                  }
               }

               slots_tmp = baseEnd + 1;
               break;
            }
         }

         int slotPos;
         for(slotPos = slots_tmp; slotPos < slottedAncestors.size(); ++slotPos) {
            insertSlots((PyObject)slotsMap.get(slottedAncestors.get(slotPos)), allSlots);
         }

         this.numSlots = allSlots.size();
         slotPos = 0;

         Iterator slotIter;
         for(slotIter = allSlots.iterator(); slotPos < this.base.numSlots; ++slotPos) {
            slotIter.next();
         }

         while(slotIter.hasNext()) {
            slotName = (String)slotIter.next();
            slotName = mangleName(this.name, slotName);
            if (this.dict.__finditem__(slotName) == null) {
               this.dict.__setitem__((String)slotName, new PySlot(this, slotName, slotPos++));
            } else {
               --this.numSlots;
            }
         }

         if (!$assertionsDisabled && slotPos != this.numSlots) {
            throw new AssertionError();
         } else {
            if (wantDict) {
               this.createDictSlot();
            }

            if (wantWeak) {
               this.createWeakrefSlot();
            }

            this.needs_finalizer = this.needsFinalizer();
         }
      }
   }

   private void createDictSlot() {
      String doc = "dictionary for instance variables (if defined)";
      this.dict.__setitem__((String)"__dict__", new PyDataDescr(this, "__dict__", PyObject.class, doc) {
         public boolean implementsDescrGet() {
            return true;
         }

         public Object invokeGet(PyObject obj) {
            return obj.getDict();
         }

         public boolean implementsDescrSet() {
            return true;
         }

         public void invokeSet(PyObject obj, Object value) {
            obj.setDict((PyObject)value);
         }

         public boolean implementsDescrDelete() {
            return true;
         }

         public void invokeDelete(PyObject obj) {
            obj.delDict();
         }
      });
      this.needs_userdict = true;
   }

   private void createWeakrefSlot() {
      String doc = "list of weak references to the object (if defined)";
      this.dict.__setitem__((String)"__weakref__", new PyDataDescr(this, "__weakref__", PyObject.class, doc) {
         private static final String writeMsg = "attribute '%s' of '%s' objects is not writable";

         private void notWritable(PyObject obj) {
            throw Py.AttributeError(String.format("attribute '%s' of '%s' objects is not writable", "__weakref__", obj.getType().fastGetName()));
         }

         public boolean implementsDescrGet() {
            return true;
         }

         public Object invokeGet(PyObject obj) {
            PyList weakrefs = WeakrefModule.getweakrefs(obj);
            switch (weakrefs.size()) {
               case 0:
                  return Py.None;
               case 1:
                  return weakrefs.pyget(0);
               default:
                  return weakrefs;
            }
         }

         public boolean implementsDescrSet() {
            return true;
         }

         public void invokeSet(PyObject obj, Object value) {
            this.notWritable(obj);
         }

         public boolean implementsDescrDelete() {
            return true;
         }

         public void invokeDelete(PyObject obj) {
            this.notWritable(obj);
         }
      });
      this.needs_weakref = true;
   }

   private void ensureAttributes() {
      this.inheritSpecial();
      PyObject new_ = this.dict.__finditem__("__new__");
      if (new_ != null && new_ instanceof PyFunction) {
         this.dict.__setitem__((String)"__new__", new PyStaticMethod(new_));
      }

      ensureDoc(this.dict);
      ensureModule(this.dict);
      this.mro_internal();
      this.cacheDescrBinds();
   }

   private void inheritSpecial() {
      if (!this.needs_userdict && this.base.needs_userdict) {
         this.needs_userdict = true;
      }

      if (!this.needs_weakref && this.base.needs_weakref) {
         this.needs_weakref = true;
      }

   }

   public static void ensureDoc(PyObject dict) {
      if (dict.__finditem__("__doc__") == null) {
         dict.__setitem__("__doc__", Py.None);
      }

   }

   public static void ensureModule(PyObject dict) {
      if (dict.__finditem__("__module__") == null) {
         PyFrame frame = Py.getFrame();
         if (frame != null) {
            PyObject name = frame.f_globals.__finditem__("__name__");
            if (name != null) {
               dict.__setitem__("__module__", name);
            }

         }
      }
   }

   private static PyObject invokeNew(PyObject new_, PyType type, boolean init, PyObject[] args, String[] keywords) {
      PyObject obj;
      if (new_ instanceof PyNewWrapper) {
         obj = ((PyNewWrapper)new_).new_impl(init, type, args, keywords);
      } else {
         int n = args.length;
         PyObject[] typePrepended = new PyObject[n + 1];
         System.arraycopy(args, 0, typePrepended, 1, n);
         typePrepended[0] = type;
         obj = new_.__get__((PyObject)null, type).__call__(typePrepended, keywords);
      }

      return obj;
   }

   protected void init(Class forClass, Set needsInners) {
      this.underlying_class = forClass;
      if (this.underlying_class == PyObject.class) {
         this.mro = new PyType[]{this};
      } else {
         Class baseClass;
         if (!BootstrapTypesSingleton.getInstance().contains(this.underlying_class)) {
            baseClass = ((TypeBuilder)getClassToBuilder().get(this.underlying_class)).getBase();
         } else {
            baseClass = PyObject.class;
         }

         if (baseClass == Object.class) {
            baseClass = this.underlying_class.getSuperclass();
         }

         this.computeLinearMro(baseClass);
      }

      if (!BootstrapTypesSingleton.getInstance().contains(this.underlying_class)) {
         TypeBuilder builder = (TypeBuilder)getClassToBuilder().get(this.underlying_class);
         this.name = builder.getName();
         this.dict = builder.getDict(this);
         String doc = builder.getDoc();
         if (this.dict.__finditem__("__doc__") == null && forClass != PyBaseString.class && forClass != PyString.class) {
            Object docObj;
            if (doc != null) {
               docObj = new PyString(doc);
            } else {
               docObj = Py.None == null ? new PyString() : Py.None;
            }

            this.dict.__setitem__((String)"__doc__", (PyObject)docObj);
         }

         this.setIsBaseType(builder.getIsBaseType());
         this.needs_userdict = this.dict.__finditem__("__dict__") != null;
         this.instantiable = this.dict.__finditem__("__new__") != null;
         this.cacheDescrBinds();
      }
   }

   protected void computeLinearMro(Class baseClass) {
      this.base = fromClass(baseClass, false);
      this.mro = new PyType[this.base.mro.length + 1];
      System.arraycopy(this.base.mro, 0, this.mro, 1, this.base.mro.length);
      this.mro[0] = this;
      this.bases = new PyObject[]{this.base};
   }

   private void cacheDescrBinds() {
      this.hasGet = this.lookup_mro("__get__") != null;
      this.hasSet = this.lookup_mro("__set__") != null;
      this.hasDelete = this.lookup_mro("__delete__") != null;
   }

   public PyObject getStatic() {
      PyType cur;
      for(cur = this; cur.underlying_class == null; cur = cur.base) {
      }

      return cur;
   }

   public final boolean needsFinalizer() {
      if (this.needs_finalizer) {
         return true;
      } else {
         this.needs_finalizer = this.lookup_mro("__del__") != null;
         return this.needs_finalizer;
      }
   }

   public void compatibleForAssignment(PyType other, String attribute) {
      if (!this.getLayout().equals(other.getLayout()) || this.needs_userdict != other.needs_userdict || this.needs_finalizer != other.needs_finalizer) {
         throw Py.TypeError(String.format("%s assignment: '%s' object layout differs from '%s'", attribute, other.fastGetName(), this.fastGetName()));
      }
   }

   private PyType getLayout() {
      if (this.underlying_class != null) {
         return this;
      } else {
         return this.numSlots != this.base.numSlots ? this : this.base.getLayout();
      }
   }

   private static Class getJavaLayout(PyObject[] bases, List interfaces) {
      Class baseProxy = null;
      PyObject[] var3 = bases;
      int var4 = bases.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         PyObject base = var3[var5];
         if (base instanceof PyType) {
            Class proxy = ((PyType)base).getProxyType();
            if (proxy != null) {
               if (proxy.isInterface()) {
                  interfaces.add(proxy);
               } else {
                  if (baseProxy != null) {
                     String msg = "no multiple inheritance for Java classes: %s and %s";
                     throw Py.TypeError(String.format(msg, proxy.getName(), baseProxy.getName()));
                  }

                  baseProxy = proxy;
               }
            }
         }
      }

      return baseProxy;
   }

   private void setupProxy(Class baseProxyClass, List interfaces) {
      if (baseProxyClass != null || interfaces.size() != 0) {
         String proxyName = this.name;
         PyObject module = this.dict.__finditem__("__module__");
         if (module != null) {
            proxyName = module.toString() + "$" + proxyName;
         }

         Class proxyClass = MakeProxies.makeProxy(baseProxyClass, interfaces, this.name, proxyName, this.dict);
         JyAttribute.setAttr(this, (byte)-128, proxyClass);
         PyType proxyType = fromClass(proxyClass, false);
         List cleanedBases = Generic.list();
         boolean addedProxyType = false;
         PyObject[] var9 = this.bases;
         int var10 = var9.length;

         for(int var11 = 0; var11 < var10; ++var11) {
            PyObject base = var9[var11];
            if (!(base instanceof PyType)) {
               cleanedBases.add(base);
            } else {
               Class proxy = ((PyType)base).getProxyType();
               if (proxy == null) {
                  cleanedBases.add(base);
               } else if (!(base instanceof PyJavaType)) {
                  cleanedBases.add(base);
               } else if (!addedProxyType) {
                  cleanedBases.add(proxyType);
                  addedProxyType = true;
               }
            }
         }

         this.bases = (PyObject[])cleanedBases.toArray(new PyObject[cleanedBases.size()]);
      }
   }

   protected PyObject richCompare(PyObject other, cmpopType op) {
      if (!(other instanceof PyType) && other != this) {
         return null;
      } else if (this.__findattr__("__cmp__") == null && ((PyType)other).__findattr__("__cmp__") == null) {
         if (Options.py3k_warning && op != cmpopType.Eq && op != cmpopType.NotEq) {
            Py.warnPy3k("type inequality comparisons not supported in 3.x");
            return null;
         } else {
            int hash1 = this.object___hash__();
            int hash2 = other.object___hash__();
            switch (op) {
               case Lt:
                  return hash1 < hash2 ? Py.True : Py.False;
               case LtE:
                  return hash1 <= hash2 ? Py.True : Py.False;
               case Eq:
                  return hash1 == hash2 ? Py.True : Py.False;
               case NotEq:
                  return hash1 != hash2 ? Py.True : Py.False;
               case Gt:
                  return hash1 > hash2 ? Py.True : Py.False;
               case GtE:
                  return hash1 >= hash2 ? Py.True : Py.False;
               default:
                  return null;
            }
         }
      } else {
         return null;
      }
   }

   public PyObject type___eq__(PyObject other) {
      return this.richCompare(other, cmpopType.Eq);
   }

   public PyObject type___ne__(PyObject other) {
      return this.richCompare(other, cmpopType.NotEq);
   }

   public PyObject type___le__(PyObject other) {
      return this.richCompare(other, cmpopType.LtE);
   }

   public PyObject type___lt__(PyObject other) {
      return this.richCompare(other, cmpopType.Lt);
   }

   public PyObject type___ge__(PyObject other) {
      return this.richCompare(other, cmpopType.GtE);
   }

   public PyObject type___gt__(PyObject other) {
      return this.richCompare(other, cmpopType.Gt);
   }

   public PyObject getBase() {
      return (PyObject)(this.base == null ? Py.None : this.base);
   }

   public PyObject getBases() {
      return new PyTuple(this.bases);
   }

   public void delBases() {
      throw Py.TypeError("Can't delete __bases__ attribute");
   }

   public void setBases(PyObject newBasesTuple) {
      if (!(newBasesTuple instanceof PyTuple)) {
         throw Py.TypeError("bases must be a tuple");
      } else {
         PyObject[] newBases = ((PyTuple)newBasesTuple).getArray();
         if (newBases.length == 0) {
            throw Py.TypeError("can only assign non-empty tuple to __bases__, not " + newBasesTuple);
         } else {
            for(int i = 0; i < newBases.length; ++i) {
               if (!(newBases[i] instanceof PyType)) {
                  if (!(newBases[i] instanceof PyClass)) {
                     throw Py.TypeError(this.name + ".__bases__ must be a tuple of old- or new-style " + "classes, not " + newBases[i]);
                  }
               } else if (((PyType)newBases[i]).isSubType(this)) {
                  throw Py.TypeError("a __bases__ item causes an inheritance cycle");
               }
            }

            PyType newBase = best_base(newBases);
            this.base.compatibleForAssignment(newBase, "__bases__");
            PyObject[] savedBases = this.bases;
            PyType savedBase = this.base;
            PyObject[] savedMro = this.mro;
            List savedSubMros = Generic.list();

            try {
               this.bases = newBases;
               this.base = newBase;
               this.mro_internal();
               this.mro_subclasses(savedSubMros);
               PyObject[] var8 = savedBases;
               int var14 = savedBases.length;
               int var15 = 0;

               label61:
               while(true) {
                  PyObject newb;
                  if (var15 >= var14) {
                     var8 = newBases;
                     var14 = newBases.length;
                     var15 = 0;

                     while(true) {
                        if (var15 >= var14) {
                           break label61;
                        }

                        newb = var8[var15];
                        if (newb instanceof PyType) {
                           ((PyType)newb).attachSubclass(this);
                        }

                        ++var15;
                     }
                  }

                  newb = var8[var15];
                  if (newb instanceof PyType) {
                     ((PyType)newb).detachSubclass(this);
                  }

                  ++var15;
               }
            } catch (PyException var12) {
               PyType subtype;
               PyObject[] subtypeSavedMro;
               for(Iterator it = savedSubMros.iterator(); it.hasNext(); subtype.mro = subtypeSavedMro) {
                  subtype = (PyType)it.next();
                  subtypeSavedMro = (PyObject[])((PyObject[])it.next());
               }

               this.bases = savedBases;
               this.base = savedBase;
               this.mro = savedMro;
               throw var12;
            }

            this.postSetattr("__getattribute__");
         }
      }
   }

   private void setIsBaseType(boolean isBaseType) {
      this.isBaseType = isBaseType;
      this.tp_flags = isBaseType ? this.tp_flags | 1024L : this.tp_flags & -1025L;
   }

   boolean isAbstract() {
      return (this.tp_flags & 1048576L) != 0L;
   }

   private void mro_internal() {
      if (this.getType() == TYPE) {
         this.mro = this.computeMro();
      } else {
         PyObject mroDescr = this.getType().lookup("mro");
         if (mroDescr == null) {
            throw Py.AttributeError("mro");
         }

         PyObject[] result = Py.make_array(mroDescr.__get__((PyObject)null, this.getType()).__call__((PyObject)this));
         PyType solid = solid_base(this);
         PyObject[] var4 = result;
         int var5 = result.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            PyObject cls = var4[var6];
            if (!(cls instanceof PyClass)) {
               if (!(cls instanceof PyType)) {
                  throw Py.TypeError(String.format("mro() returned a non-class ('%.500s')", cls.getType().fastGetName()));
               }

               PyType t = (PyType)cls;
               if (!solid.isSubType(solid_base(t))) {
                  throw Py.TypeError(String.format("mro() returned base with unsuitable layout ('%.500s')", t.fastGetName()));
               }
            }
         }

         this.mro = result;
      }

   }

   private void mro_subclasses(List mroCollector) {
      Iterator var2 = this.subclasses.iterator();

      while(var2.hasNext()) {
         WeakReference ref = (WeakReference)var2.next();
         PyType subtype = (PyType)ref.get();
         if (subtype != null) {
            mroCollector.add(subtype);
            mroCollector.add(subtype.mro);
            subtype.mro_internal();
            subtype.mro_subclasses(mroCollector);
         }
      }

   }

   public PyObject instDict() {
      return this.needs_userdict ? new PyStringMap() : null;
   }

   private void cleanup_subclasses() {
      Reference ref;
      while((ref = this.subclasses_refq.poll()) != null) {
         this.subclasses.remove(ref);
      }

   }

   public PyTuple getMro() {
      return this.mro == null ? Py.EmptyTuple : new PyTuple(this.mro);
   }

   public PyLong getFlags() {
      return new PyLong(this.tp_flags);
   }

   public final synchronized PyObject type___subclasses__() {
      PyList result = new PyList();
      this.cleanup_subclasses();
      Iterator var2 = this.subclasses.iterator();

      while(var2.hasNext()) {
         WeakReference ref = (WeakReference)var2.next();
         PyType subtype = (PyType)ref.get();
         if (subtype != null) {
            result.append(subtype);
         }
      }

      return result;
   }

   public final synchronized boolean type___subclasscheck__(PyObject inst) {
      return Py.recursiveIsSubClass(inst, this);
   }

   public final synchronized boolean type___instancecheck__(PyObject inst) {
      return inst.getType() == this ? true : Py.recursiveIsInstance(inst, this);
   }

   public Class getProxyType() {
      return (Class)JyAttribute.getAttr(this, (byte)-128);
   }

   private synchronized void attachSubclass(PyType subtype) {
      this.cleanup_subclasses();
      this.subclasses.add(new WeakReference(subtype, this.subclasses_refq));
   }

   private synchronized void detachSubclass(PyType subtype) {
      this.cleanup_subclasses();
      Iterator var2 = this.subclasses.iterator();

      while(var2.hasNext()) {
         WeakReference ref = (WeakReference)var2.next();
         if (ref.get() == subtype) {
            this.subclasses.remove(ref);
            break;
         }
      }

   }

   private synchronized void traverse_hierarchy(boolean top, OnType behavior) {
      boolean stop = false;
      if (!top) {
         stop = behavior.onType(this);
      }

      if (!stop) {
         Iterator var4 = this.subclasses.iterator();

         while(var4.hasNext()) {
            WeakReference ref = (WeakReference)var4.next();
            PyType subtype = (PyType)ref.get();
            if (subtype != null) {
               subtype.traverse_hierarchy(false, behavior);
            }
         }

      }
   }

   private static void fill_classic_mro(List acc, PyClass classic_cl) {
      if (!acc.contains(classic_cl)) {
         acc.add(classic_cl);
      }

      PyObject[] bases = classic_cl.__bases__.getArray();
      PyObject[] var3 = bases;
      int var4 = bases.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         PyObject base = var3[var5];
         fill_classic_mro(acc, (PyClass)base);
      }

   }

   private static PyObject[] classic_mro(PyClass classic_cl) {
      List acc = Generic.list();
      fill_classic_mro(acc, classic_cl);
      return (PyObject[])acc.toArray(new PyObject[acc.size()]);
   }

   final PyList type_mro(PyObject o) {
      return o == null ? new PyList(this.computeMro()) : new PyList(((PyType)o).computeMro());
   }

   PyObject[] computeMro() {
      for(int i = 0; i < this.bases.length; ++i) {
         PyObject cur = this.bases[i];

         for(int j = i + 1; j < this.bases.length; ++j) {
            if (this.bases[j] == cur) {
               PyObject name = cur.__findattr__("__name__");
               throw Py.TypeError("duplicate base class " + (name == null ? "?" : name.toString()));
            }
         }
      }

      MROMergeState[] toMerge = new MROMergeState[this.bases.length + 1];

      for(int i = 0; i < this.bases.length; ++i) {
         toMerge[i] = new MROMergeState();
         if (this.bases[i] instanceof PyType) {
            toMerge[i].mro = ((PyType)this.bases[i]).mro;
         } else if (this.bases[i] instanceof PyClass) {
            toMerge[i].mro = classic_mro((PyClass)this.bases[i]);
         }
      }

      toMerge[this.bases.length] = new MROMergeState();
      toMerge[this.bases.length].mro = this.bases;
      List mro = Generic.list();
      mro.add(this);
      return this.computeMro(toMerge, mro);
   }

   PyObject[] computeMro(MROMergeState[] toMerge, List mro) {
      boolean addedProxy = false;
      PyType proxyAsType = !JyAttribute.hasAttr(this, (byte)-128) ? null : fromClass((Class)JyAttribute.getAttr(this, (byte)-128), false);

      label74:
      for(int i = 0; i < toMerge.length; ++i) {
         if (!toMerge[i].isMerged()) {
            PyObject candidate = toMerge[i].getCandidate();
            MROMergeState[] var7 = toMerge;
            int var8 = toMerge.length;

            int var9;
            MROMergeState element;
            for(var9 = 0; var9 < var8; ++var9) {
               element = var7[var9];
               if (element.pastnextContains(candidate)) {
                  continue label74;
               }
            }

            if (!addedProxy && !(this instanceof PyJavaType) && candidate instanceof PyJavaType && JyAttribute.hasAttr(candidate, (byte)-128) && PyProxy.class.isAssignableFrom((Class)JyAttribute.getAttr(candidate, (byte)-128)) && JyAttribute.getAttr(candidate, (byte)-128) != JyAttribute.getAttr(this, (byte)-128)) {
               mro.add(proxyAsType);
               addedProxy = true;
            }

            mro.add(candidate);
            addedProxy |= candidate == proxyAsType;
            var7 = toMerge;
            var8 = toMerge.length;

            for(var9 = 0; var9 < var8; ++var9) {
               element = var7[var9];
               element.noteMerged(candidate);
            }

            i = -1;
         }
      }

      MROMergeState[] var11 = toMerge;
      int var12 = toMerge.length;

      for(int var13 = 0; var13 < var12; ++var13) {
         MROMergeState mergee = var11[var13];
         if (!mergee.isMerged()) {
            this.handleMroError(toMerge, mro);
         }
      }

      return (PyObject[])mro.toArray(new PyObject[mro.size()]);
   }

   void handleMroError(MROMergeState[] toMerge, List mro) {
      StringBuilder msg = new StringBuilder("Cannot create a consistent method resolution\norder (MRO) for bases ");
      Set set = Generic.set();
      MROMergeState[] var5 = toMerge;
      int var6 = toMerge.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         MROMergeState mergee = var5[var7];
         if (!mergee.isMerged()) {
            set.add(mergee.mro[0]);
         }
      }

      boolean first = true;

      PyObject unmerged;
      PyObject name;
      for(Iterator var10 = set.iterator(); var10.hasNext(); msg.append(name == null ? "?" : name.toString() + new PyList(((PyType)unmerged).bases))) {
         unmerged = (PyObject)var10.next();
         name = unmerged.__findattr__("__name__");
         if (first) {
            first = false;
         } else {
            msg.append(", ");
         }
      }

      throw Py.TypeError(msg.toString());
   }

   private static PyType solid_base(PyType type) {
      do {
         if (isSolidBase(type)) {
            return type;
         }

         type = type.base;
      } while(type != null);

      return PyObject.TYPE;
   }

   private static boolean isSolidBase(PyType type) {
      return type.underlying_class != null || type.ownSlots != 0 && !type.needs_userdict;
   }

   private static PyType best_base(PyObject[] bases) {
      PyType winner = null;
      PyType candidate = null;
      PyType best = null;
      PyObject[] var4 = bases;
      int var5 = bases.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         PyObject base = var4[var6];
         if (!(base instanceof PyClass)) {
            if (!(base instanceof PyType)) {
               throw Py.TypeError("bases must be types");
            }

            candidate = solid_base((PyType)base);
            if (winner == null) {
               winner = candidate;
               best = (PyType)base;
            } else if (!winner.isSubType(candidate)) {
               if (!candidate.isSubType(winner)) {
                  throw Py.TypeError("multiple bases have instance lay-out conflict");
               }

               winner = candidate;
               best = (PyType)base;
            }
         }
      }

      if (best == null) {
         throw Py.TypeError("a new-style class can't have only classic bases");
      } else {
         return best;
      }
   }

   private static boolean isJavaRootClass(PyType type) {
      return type instanceof PyJavaType && type.fastGetName().equals("java.lang.Class");
   }

   private static PyType findMostDerivedMetatype(PyObject[] bases_list, PyType initialMetatype) {
      PyType winner = initialMetatype;
      if (isJavaRootClass(initialMetatype)) {
         winner = TYPE;
      }

      PyObject[] var3 = bases_list;
      int var4 = bases_list.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         PyObject base = var3[var5];
         if (!(base instanceof PyClass)) {
            PyType curtype = base.getType();
            if (isJavaRootClass(curtype)) {
               curtype = TYPE;
            }

            if (!winner.isSubType(curtype)) {
               if (!curtype.isSubType(winner)) {
                  throw Py.TypeError("metaclass conflict: the metaclass of a derived class must be a (non-strict) subclass of the metaclasses of all its bases");
               }

               winner = curtype;
            }
         }
      }

      return winner;
   }

   public boolean isSubType(PyType supertype) {
      if (this.mro != null) {
         PyObject[] var6 = this.mro;
         int var3 = var6.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            PyObject base = var6[var4];
            if (base == supertype) {
               return true;
            }
         }

         return false;
      } else {
         PyType type = this;

         while(type != supertype) {
            type = type.base;
            if (type == null) {
               return supertype == PyObject.TYPE;
            }
         }

         return true;
      }
   }

   public PyObject lookup(String name) {
      return this.lookup_where(name, (PyObject[])null);
   }

   protected PyObject lookup_mro(String name) {
      return this.lookup_where_mro(name, (PyObject[])null);
   }

   public PyObject lookup_where(String name, PyObject[] where) {
      if (methodCache == null) {
         Py.warning(Py.RuntimeWarning, "PyType: methodCache is null");
      }

      return methodCache.lookup_where(this, name, where);
   }

   protected PyObject lookup_where_mro(String name, PyObject[] where) {
      PyObject[] mro = this.mro;
      if (mro == null) {
         return null;
      } else {
         PyObject[] var4 = mro;
         int var5 = mro.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            PyObject t = var4[var6];
            PyObject dict = t.fastGetDict();
            if (dict != null) {
               PyObject obj = dict.__finditem__(name);
               if (obj != null) {
                  if (where != null) {
                     where[0] = t;
                  }

                  return obj;
               }
            }
         }

         return null;
      }
   }

   public PyObject super_lookup(PyType ref, String name) {
      PyObject[] mro = this.mro;
      if (mro == null) {
         return null;
      } else {
         int i;
         for(i = 0; i < mro.length && mro[i] != ref; ++i) {
         }

         ++i;

         for(; i < mro.length; ++i) {
            String lookupName;
            if (mro[i] instanceof PyJavaType) {
               if (name != "__init__" && !name.startsWith("super__")) {
                  lookupName = "super__" + name;
               } else {
                  lookupName = name;
               }
            } else {
               lookupName = name;
            }

            PyObject dict = mro[i].fastGetDict();
            if (dict != null) {
               PyObject obj = dict.__finditem__(lookupName);
               if (obj != null) {
                  return obj;
               }
            }
         }

         return null;
      }
   }

   public static void addBuilder(Class forClass, TypeBuilder builder) {
      getClassToBuilder().put(forClass, builder);
      if (getClassToType().containsKey(forClass)) {
         if (!BootstrapTypesSingleton.getInstance().remove(forClass)) {
            Py.writeWarning("init", "Bootstrapping class not in BootstrapTypesSingleton.getClassToType()[class=" + forClass + "]");
         }

         fromClass(builder.getTypeClass()).init(builder.getTypeClass(), (Set)null);
      }

   }

   private static PyType addFromClass(Class c, Set needsInners) {
      if (ExposeAsSuperclass.class.isAssignableFrom(c)) {
         PyType exposedAs = fromClass(c.getSuperclass(), false);
         PyType origExposedAs = (PyType)getClassToType().putIfAbsent(c, exposedAs);
         return exposedAs;
      } else {
         return createType(c, needsInners);
      }
   }

   static boolean hasBuilder(Class c) {
      return getClassToBuilder().containsKey(c);
   }

   private static TypeBuilder getBuilder(Class c) {
      if (!c.isPrimitive() && PyObject.class.isAssignableFrom(c)) {
         SecurityException exc = null;

         try {
            Class.forName(c.getName(), true, c.getClassLoader());
         } catch (ClassNotFoundException var3) {
            throw new RuntimeException("Got ClassNotFound calling Class.forName on an already  found class.", var3);
         } catch (ExceptionInInitializerError var4) {
            throw Py.JavaError(var4);
         } catch (SecurityException var5) {
            exc = var5;
         }

         TypeBuilder builder = (TypeBuilder)getClassToBuilder().get(c);
         if (builder == null && exc != null) {
            Py.writeComment("type", "Unable to initialize " + c.getName() + ", a PyObject subclass, due to a " + "security exception, and no type builder could be found for it. If it's an " + "exposed type, it may not work properly.  Security exception: " + exc.getMessage());
         }

         return builder;
      } else {
         return null;
      }
   }

   private static PyType createType(Class c, Set needsInners) {
      Object newtype;
      if (c == PyType.class) {
         newtype = new PyType(false);
      } else if (!BootstrapTypesSingleton.getInstance().contains(c) && getBuilder(c) == null) {
         newtype = new PyJavaType();
      } else {
         newtype = new PyType();
      }

      PyType type = (PyType)getClassToType().putIfAbsent(c, newtype);
      synchronized(c) {
         if (type != null) {
            return type;
         } else {
            ((PyType)newtype).builtin = true;
            ((PyType)newtype).init(c, needsInners);
            ((PyType)newtype).invalidateMethodCache();
            return (PyType)newtype;
         }
      }
   }

   public static PyType fromClass(Class c) {
      return fromClass(c, true);
   }

   public static PyType fromClass(Class c, boolean hardRef) {
      PyType type = (PyType)getClassToType().get(c);
      if (type != null) {
         synchronized(c) {
            return type;
         }
      } else {
         Set needsInners = Generic.set();
         PyType result = addFromClass(c, needsInners);
         Iterator var5 = needsInners.iterator();

         while(true) {
            PyJavaType javaType;
            Class forClass;
            do {
               if (!var5.hasNext()) {
                  if (hardRef && result != null) {
                     getExposedTypes().add(result);
                  }

                  return result;
               }

               javaType = (PyJavaType)var5.next();
               forClass = javaType.getProxyType();
            } while(forClass == null);

            Class[] var8 = forClass.getClasses();
            int var9 = var8.length;

            for(int var10 = 0; var10 < var9; ++var10) {
               Class inner = var8[var10];
               if (inner.getDeclaringClass() == forClass && javaType.dict.__finditem__(inner.getSimpleName()) == null) {
                  if (inner.getAnnotation(ExposedType.class) != null || ExposeAsSuperclass.class.isAssignableFrom(inner)) {
                     BootstrapTypesSingleton.getInstance().add(inner);
                  }

                  javaType.dict.__setitem__((String)inner.getSimpleName(), fromClass(inner, hardRef));
               }
            }
         }
      }
   }

   static PyType fromClassSkippingInners(Class c, Set needsInners) {
      PyType type = (PyType)getClassToType().get(c);
      return type != null ? type : addFromClass(c, needsInners);
   }

   final PyObject type___getattribute__(PyObject name) {
      String n = asName(name);
      PyObject ret = this.type___findattr_ex__(n);
      if (ret == null) {
         this.noAttributeError(n);
      }

      return ret;
   }

   public PyObject __findattr_ex__(String name) {
      return this.type___findattr_ex__(name);
   }

   final PyObject type___findattr_ex__(String name) {
      PyType metatype = this.getType();
      PyObject metaattr = metatype.lookup(name);
      boolean get = false;
      PyObject res;
      if (metaattr != null) {
         get = metaattr.implementsDescrGet();
         if (this.useMetatypeFirst(metaattr) && get && metaattr.isDataDescr()) {
            res = metaattr.__get__(this, metatype);
            if (res != null) {
               return res;
            }
         }
      }

      res = this.lookup(name);
      if (res != null) {
         PyObject res = res.__get__((PyObject)null, this);
         if (res != null) {
            return res;
         }
      }

      if (get) {
         return metaattr.__get__(this, metatype);
      } else {
         return metaattr != null ? metaattr : null;
      }
   }

   protected boolean useMetatypeFirst(PyObject attr) {
      return true;
   }

   final void type___setattr__(PyObject name, PyObject value) {
      this.type___setattr__(asName(name), value);
   }

   public void __setattr__(String name, PyObject value) {
      this.type___setattr__(name, value);
   }

   public void addMethod(PyBuiltinMethod meth) {
      PyMethodDescr pmd = meth.makeDescriptor(this);
      this.__setattr__(pmd.getName(), pmd);
   }

   public void removeMethod(PyBuiltinMethod meth) {
      this.__delattr__(meth.info.getName());
   }

   void type___setattr__(String name, PyObject value) {
      if (this.builtin) {
         throw Py.TypeError(String.format("can't set attributes of built-in/extension type '%s'", this.name));
      } else {
         super.__setattr__(name, value);
         this.postSetattr(name);
      }
   }

   void postSetattr(String name) {
      this.invalidateMethodCache();
      if (name == "__get__") {
         if (!this.hasGet && this.lookup("__get__") != null) {
            this.traverse_hierarchy(false, new OnType() {
               public boolean onType(PyType type) {
                  boolean old = type.hasGet;
                  type.hasGet = true;
                  return old;
               }
            });
         }
      } else if (name == "__set__") {
         if (!this.hasSet && this.lookup("__set__") != null) {
            this.traverse_hierarchy(false, new OnType() {
               public boolean onType(PyType type) {
                  boolean old = type.hasSet;
                  type.hasSet = true;
                  return old;
               }
            });
         }
      } else if (name == "__delete__") {
         if (!this.hasDelete && this.lookup("__delete__") != null) {
            this.traverse_hierarchy(false, new OnType() {
               public boolean onType(PyType type) {
                  boolean old = type.hasDelete;
                  type.hasDelete = true;
                  return old;
               }
            });
         }
      } else if (name == "__getattribute__") {
         this.traverse_hierarchy(false, new OnType() {
            public boolean onType(PyType type) {
               return type.usesObjectGetattribute = false;
            }
         });
      }

   }

   public void __delattr__(String name) {
      this.type___delattr__(name);
   }

   final void type___delattr__(PyObject name) {
      this.type___delattr__(asName(name));
   }

   protected void checkDelattr() {
   }

   void type___delattr__(String name) {
      if (this.builtin) {
         throw Py.TypeError(String.format("can't set attributes of built-in/extension type '%s'", this.name));
      } else {
         super.__delattr__(name);
         this.postDelattr(name);
      }
   }

   void postDelattr(String name) {
      this.invalidateMethodCache();
      if (name == "__get__") {
         if (this.hasGet && this.lookup("__get__") == null) {
            this.traverse_hierarchy(false, new OnType() {
               public boolean onType(PyType type) {
                  boolean absent = type.getDict().__finditem__("__get__") == null;
                  if (absent) {
                     type.hasGet = false;
                     return false;
                  } else {
                     return true;
                  }
               }
            });
         }
      } else if (name == "__set__") {
         if (this.hasSet && this.lookup("__set__") == null) {
            this.traverse_hierarchy(false, new OnType() {
               public boolean onType(PyType type) {
                  boolean absent = type.getDict().__finditem__("__set__") == null;
                  if (absent) {
                     type.hasSet = false;
                     return false;
                  } else {
                     return true;
                  }
               }
            });
         }
      } else if (name == "__delete__") {
         if (this.hasDelete && this.lookup("__delete__") == null) {
            this.traverse_hierarchy(false, new OnType() {
               public boolean onType(PyType type) {
                  boolean absent = type.getDict().__finditem__("__delete__") == null;
                  if (absent) {
                     type.hasDelete = false;
                     return false;
                  } else {
                     return true;
                  }
               }
            });
         }
      } else if (name == "__getattribute__") {
         this.traverse_hierarchy(false, new OnType() {
            public boolean onType(PyType type) {
               return type.usesObjectGetattribute = false;
            }
         });
      }

   }

   protected void invalidateMethodCache() {
      this.traverse_hierarchy(false, new OnType() {
         public boolean onType(PyType type) {
            type.versionTag = new Object();
            return false;
         }
      });
   }

   public PyObject __call__(PyObject[] args, String[] keywords) {
      return this.type___call__(args, keywords);
   }

   final PyObject type___call__(PyObject[] args, String[] keywords) {
      PyObject new_ = this.lookup("__new__");
      if (this.instantiable && new_ != null) {
         PyObject obj = invokeNew(new_, this, true, args, keywords);
         if ((this != TYPE || args.length != 1 || keywords.length != 0) && obj.getType().isSubType(this)) {
            obj.dispatch__init__(args, keywords);
            return obj;
         } else {
            return obj;
         }
      } else {
         throw Py.TypeError(String.format("cannot create '%.100s' instances", this.name));
      }
   }

   protected void __rawdir__(PyDictionary accum) {
      this.mergeClassDict(accum, this);
   }

   public String fastGetName() {
      return this.name;
   }

   public PyObject pyGetName() {
      return Py.newString(this.getName());
   }

   public String getName() {
      if (!this.builtin) {
         return this.name;
      } else {
         int lastDot = this.name.lastIndexOf(46);
         return lastDot != -1 ? this.name.substring(lastDot + 1) : this.name;
      }
   }

   public void pySetName(PyObject name) {
      if (!(name instanceof PyString)) {
         throw Py.TypeError(String.format("can only assign string to %s.__name__, not '%s'", this.name, name.getType().fastGetName()));
      } else {
         String nameStr = name.toString();
         if (nameStr.indexOf(0) > -1) {
            throw Py.ValueError("__name__ must not contain null bytes");
         } else {
            this.setName(nameStr);
            this.invalidateMethodCache();
         }
      }
   }

   public void setName(String name) {
      this.name = name;
   }

   public void pyDelName() {
      throw Py.TypeError(String.format("can't delete %s.__name__", this.name));
   }

   public PyObject fastGetDict() {
      return this.dict;
   }

   public PyObject getDict() {
      return new PyDictProxy(this.dict);
   }

   public void setDict(PyObject newDict) {
      throw Py.AttributeError(String.format("attribute '__dict__' of '%s' objects is not writable", this.getType().fastGetName()));
   }

   public void delDict() {
      this.setDict((PyObject)null);
   }

   public PyObject getDoc() {
      PyObject doc = this.dict.__finditem__("__doc__");
      return doc == null ? Py.None : doc.__get__((PyObject)null, this);
   }

   boolean getUsesObjectGetattribute() {
      return this.usesObjectGetattribute;
   }

   void setUsesObjectGetattribute(boolean usesObjectGetattribute) {
      this.usesObjectGetattribute = usesObjectGetattribute;
   }

   public Object __tojava__(Class c) {
      return this.underlying_class == null || c != Object.class && c != Class.class && c != Serializable.class ? super.__tojava__(c) : this.underlying_class;
   }

   public PyObject getModule() {
      if (!this.builtin) {
         return this.dict.__finditem__("__module__");
      } else {
         int lastDot = this.name.lastIndexOf(46);
         return lastDot != -1 ? Py.newString(this.name.substring(0, lastDot)) : Py.newString("__builtin__");
      }
   }

   public void delModule() {
      throw Py.TypeError(String.format("can't delete %s.__module__", this.name));
   }

   public PyObject getAbstractmethods() {
      PyObject result = this.dict.__finditem__("__abstractmethods__");
      if (result == null || result instanceof PyDataDescr) {
         this.noAttributeError("__abstractmethods__");
      }

      return result;
   }

   public void setAbstractmethods(PyObject value) {
      this.dict.__setitem__("__abstractmethods__", value);
      this.postSetattr("__abstractmethods__");
      this.tp_flags = value.__nonzero__() ? this.tp_flags | 1048576L : this.tp_flags & -1048577L;
   }

   public int getNumSlots() {
      return this.numSlots;
   }

   final String type_toString() {
      String kind;
      if (!this.builtin) {
         kind = "class";
      } else {
         kind = "type";
      }

      PyObject module = this.getModule();
      return module instanceof PyString && !module.toString().equals("__builtin__") ? String.format("<%s '%s.%s'>", kind, module.toString(), this.getName()) : String.format("<%s '%s'>", kind, this.getName());
   }

   public String toString() {
      return this.type_toString();
   }

   public void noAttributeError(String name) {
      throw Py.AttributeError(String.format("type object '%.50s' has no attribute '%.400s'", this.fastGetName(), name));
   }

   private static String confirmIdentifier(PyObject obj) {
      if (!(obj instanceof PyString)) {
         throw Py.TypeError(String.format("__slots__ items must be strings, not '%.200s'", obj.getType().fastGetName()));
      } else {
         String identifier;
         if (obj instanceof PyUnicode) {
            identifier = ((PyUnicode)obj).encode();
         } else {
            identifier = obj.toString();
         }

         String msg = "__slots__ must be identifiers";
         if (identifier.length() != 0 && (Character.isLetter(identifier.charAt(0)) || identifier.charAt(0) == '_')) {
            char[] var3 = identifier.toCharArray();
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               char c = var3[var5];
               if (!Character.isLetterOrDigit(c) && c != '_') {
                  throw Py.TypeError(msg);
               }
            }

            return identifier;
         } else {
            throw Py.TypeError(msg);
         }
      }
   }

   private static String mangleName(String classname, String methodname) {
      if (classname != null && methodname.startsWith("__") && !methodname.endsWith("__")) {
         int i;
         for(i = 0; classname.charAt(i) == '_'; ++i) {
         }

         return ("_" + classname.substring(i) + methodname).intern();
      } else {
         return methodname;
      }
   }

   protected Object writeReplace() {
      return new TypeResolver(this.underlying_class, this.getModule().toString(), this.getName());
   }

   private static ConcurrentMap getClassToType() {
      return PyType.LazyClassToTypeHolder.classToType;
   }

   private static ConcurrentMap getClassToBuilder() {
      return PyType.LazyClassToBuilderHolder.classToBuilder;
   }

   private static Set getExposedTypes() {
      return PyType.LazyExposedTypes.exposedTypes;
   }

   public int traverse(Visitproc visit, Object arg) {
      int retVal;
      if (this.base != null) {
         retVal = visit.visit(this.base, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      PyObject[] var4 = this.bases;
      int var5 = var4.length;

      int var6;
      PyObject ob;
      for(var6 = 0; var6 < var5; ++var6) {
         ob = var4[var6];
         if (ob != null) {
            retVal = visit.visit(ob, arg);
            if (retVal != 0) {
               return retVal;
            }
         }
      }

      if (this.dict != null) {
         retVal = visit.visit(this.dict, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      if (this.mro != null) {
         var4 = this.mro;
         var5 = var4.length;

         for(var6 = 0; var6 < var5; ++var6) {
            ob = var4[var6];
            retVal = visit.visit(ob, arg);
            if (retVal != 0) {
               return retVal;
            }
         }
      }

      return 0;
   }

   public boolean refersDirectlyTo(PyObject ob) throws UnsupportedOperationException {
      if (ob == null) {
         return false;
      } else {
         PyObject[] var2 = this.bases;
         int var3 = var2.length;

         int var4;
         PyObject obj;
         for(var4 = 0; var4 < var3; ++var4) {
            obj = var2[var4];
            if (obj == ob) {
               return true;
            }
         }

         if (this.mro != null) {
            var2 = this.mro;
            var3 = var2.length;

            for(var4 = 0; var4 < var3; ++var4) {
               obj = var2[var4];
               if (obj == ob) {
                  return true;
               }
            }
         }

         return ob == this.base || ob == this.dict;
      }
   }

   static {
      addBuilder(PyType.class, new PyExposer());
      $assertionsDisabled = !PyType.class.desiredAssertionStatus();
      TYPE = fromClass(PyType.class);
      methodCache = new MethodCache();
   }

   private static class LazyExposedTypes {
      private static Set exposedTypes = Generic.set();
   }

   private static class LazyClassToBuilderHolder {
      private static ConcurrentMap classToBuilder = Generic.concurrentMap();
   }

   private static class LazyClassToTypeHolder {
      private static ConcurrentMap classToType = (new MapMaker()).weakKeys().weakValues().makeMap();
   }

   static class MethodCache {
      private final AtomicReferenceArray table = new AtomicReferenceArray(4096);
      private static final int SIZE_EXP = 12;

      public MethodCache() {
         this.clear();
      }

      public void clear() {
         int length = this.table.length();

         for(int i = 0; i < length; ++i) {
            this.table.set(i, PyType.MethodCache.MethodCacheEntry.EMPTY);
         }

      }

      public PyObject lookup_where(PyType type, String name, PyObject[] where) {
         Object versionTag = type.versionTag;
         int index = indexFor(versionTag, name);
         MethodCacheEntry entry = (MethodCacheEntry)this.table.get(index);
         if (entry.isValid(versionTag, name)) {
            return entry.get(where);
         } else {
            if (where == null) {
               where = new PyObject[1];
            }

            PyObject value = type.lookup_where_mro(name, where);
            if (isCacheableName(name)) {
               this.table.compareAndSet(index, entry, new MethodCacheEntry(versionTag, name, where[0], value));
            }

            return value;
         }
      }

      private static int indexFor(Object version, String name) {
         return (version.hashCode() ^ name.hashCode()) & 4095;
      }

      private static boolean isCacheableName(String name) {
         return name.length() <= 100;
      }

      static class MethodCacheEntry extends WeakReference {
         private final Object version;
         private final String name;
         private final WeakReference where;
         static final MethodCacheEntry EMPTY = new MethodCacheEntry();

         private MethodCacheEntry() {
            this((Object)null, (String)null, (PyObject)null, (PyObject)null);
         }

         public MethodCacheEntry(Object version, String name, PyObject where, PyObject value) {
            super(value);
            this.version = version;
            this.name = name;
            this.where = new WeakReference(where);
         }

         public boolean isValid(Object version, String name) {
            return this.version == version && this.name == name;
         }

         public PyObject get(PyObject[] where) {
            if (where != null) {
               where[0] = (PyObject)this.where.get();
            }

            return (PyObject)this.get();
         }
      }
   }

   static class MROMergeState {
      public PyObject[] mro;
      public int next;

      public boolean isMerged() {
         return this.mro.length == this.next;
      }

      public PyObject getCandidate() {
         return this.mro[this.next];
      }

      public void noteMerged(PyObject candidate) {
         if (!this.isMerged() && this.getCandidate() == candidate) {
            ++this.next;
         }

      }

      public boolean pastnextContains(PyObject candidate) {
         for(int i = this.next + 1; i < this.mro.length; ++i) {
            if (this.mro[i] == candidate) {
               return true;
            }
         }

         return false;
      }

      public void removeFromUnmerged(PyJavaType winner) {
         if (!this.isMerged()) {
            List newMro = Generic.list();
            PyObject[] var3 = this.mro;
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               PyObject mroEntry = var3[var5];
               if (mroEntry != winner) {
                  newMro.add(mroEntry);
               }
            }

            this.mro = (PyObject[])newMro.toArray(new PyObject[newMro.size()]);
         }
      }
   }

   static class TypeResolver implements Serializable {
      private Class underlying_class;
      String module;
      private String name;

      TypeResolver(Class underlying_class, String module, String name) {
         if (underlying_class != null && !PyProxy.class.isAssignableFrom(underlying_class)) {
            this.underlying_class = underlying_class;
         }

         this.module = module;
         this.name = name;
      }

      private Object readResolve() {
         if (this.underlying_class != null) {
            return PyType.fromClass(this.underlying_class, false);
         } else {
            PyObject mod = imp.importName(this.module.intern(), false);
            PyObject pytyp = mod.__getattr__(this.name.intern());
            if (!(pytyp instanceof PyType)) {
               throw Py.TypeError(this.module + "." + this.name + " must be a type for deserialization");
            } else {
               return pytyp;
            }
         }
      }
   }

   private interface OnType {
      boolean onType(PyType var1);
   }

   private static class type___init___exposer extends PyBuiltinMethod {
      public type___init___exposer(String var1) {
         super(var1);
         super.doc = "x.__init__(...) initializes x; see help(type(x)) for signature";
      }

      public type___init___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__init__(...) initializes x; see help(type(x)) for signature";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new type___init___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         ((PyType)this.self).type___init__(var1, var2);
         return Py.None;
      }
   }

   private static class type___eq___exposer extends PyBuiltinMethodNarrow {
      public type___eq___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__eq__(y) <==> x==y";
      }

      public type___eq___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__eq__(y) <==> x==y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new type___eq___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyType)this.self).type___eq__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class type___ne___exposer extends PyBuiltinMethodNarrow {
      public type___ne___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__ne__(y) <==> x!=y";
      }

      public type___ne___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__ne__(y) <==> x!=y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new type___ne___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyType)this.self).type___ne__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class type___le___exposer extends PyBuiltinMethodNarrow {
      public type___le___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__le__(y) <==> x<=y";
      }

      public type___le___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__le__(y) <==> x<=y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new type___le___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyType)this.self).type___le__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class type___lt___exposer extends PyBuiltinMethodNarrow {
      public type___lt___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__lt__(y) <==> x<y";
      }

      public type___lt___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__lt__(y) <==> x<y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new type___lt___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyType)this.self).type___lt__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class type___ge___exposer extends PyBuiltinMethodNarrow {
      public type___ge___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__ge__(y) <==> x>=y";
      }

      public type___ge___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__ge__(y) <==> x>=y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new type___ge___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyType)this.self).type___ge__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class type___gt___exposer extends PyBuiltinMethodNarrow {
      public type___gt___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__gt__(y) <==> x>y";
      }

      public type___gt___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__gt__(y) <==> x>y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new type___gt___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyType)this.self).type___gt__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class type___subclasses___exposer extends PyBuiltinMethodNarrow {
      public type___subclasses___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "__subclasses__() -> list of immediate subclasses";
      }

      public type___subclasses___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "__subclasses__() -> list of immediate subclasses";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new type___subclasses___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyType)this.self).type___subclasses__();
      }
   }

   private static class type___subclasscheck___exposer extends PyBuiltinMethodNarrow {
      public type___subclasscheck___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "__subclasscheck__() -> bool\ncheck if a class is a subclass";
      }

      public type___subclasscheck___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "__subclasscheck__() -> bool\ncheck if a class is a subclass";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new type___subclasscheck___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return Py.newBoolean(((PyType)this.self).type___subclasscheck__(var1));
      }
   }

   private static class type___instancecheck___exposer extends PyBuiltinMethodNarrow {
      public type___instancecheck___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "__instancecheck__() -> bool\ncheck if an object is an instance";
      }

      public type___instancecheck___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "__instancecheck__() -> bool\ncheck if an object is an instance";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new type___instancecheck___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return Py.newBoolean(((PyType)this.self).type___instancecheck__(var1));
      }
   }

   private static class type_mro_exposer extends PyBuiltinMethodNarrow {
      public type_mro_exposer(String var1) {
         super(var1, 1, 2);
         super.doc = "mro() -> list\nreturn a type's method resolution order";
      }

      public type_mro_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "mro() -> list\nreturn a type's method resolution order";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new type_mro_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyType)this.self).type_mro(var1);
      }

      public PyObject __call__() {
         return ((PyType)this.self).type_mro((PyObject)null);
      }
   }

   private static class type___getattribute___exposer extends PyBuiltinMethodNarrow {
      public type___getattribute___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__getattribute__('name') <==> x.name";
      }

      public type___getattribute___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__getattribute__('name') <==> x.name";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new type___getattribute___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyType)this.self).type___getattribute__(var1);
      }
   }

   private static class type___setattr___exposer extends PyBuiltinMethodNarrow {
      public type___setattr___exposer(String var1) {
         super(var1, 3, 3);
         super.doc = "x.__setattr__('name', value) <==> x.name = value";
      }

      public type___setattr___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__setattr__('name', value) <==> x.name = value";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new type___setattr___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         ((PyType)this.self).type___setattr__(var1, var2);
         return Py.None;
      }
   }

   private static class type___delattr___exposer extends PyBuiltinMethodNarrow {
      public type___delattr___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__delattr__('name') <==> del x.name";
      }

      public type___delattr___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__delattr__('name') <==> del x.name";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new type___delattr___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         ((PyType)this.self).type___delattr__(var1);
         return Py.None;
      }
   }

   private static class type___call___exposer extends PyBuiltinMethod {
      public type___call___exposer(String var1) {
         super(var1);
         super.doc = "x.__call__(...) <==> x(...)";
      }

      public type___call___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__call__(...) <==> x(...)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new type___call___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         return ((PyType)this.self).type___call__(var1, var2);
      }
   }

   private static class type_toString_exposer extends PyBuiltinMethodNarrow {
      public type_toString_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__repr__() <==> repr(x)";
      }

      public type_toString_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__repr__() <==> repr(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new type_toString_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         String var10000 = ((PyType)this.self).type_toString();
         return (PyObject)(var10000 == null ? Py.None : Py.newString(var10000));
      }
   }

   private static class __abstractmethods___descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public __abstractmethods___descriptor() {
         super("__abstractmethods__", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyType)var1).getAbstractmethods();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((PyType)var1).setAbstractmethods((PyObject)var2);
      }

      public boolean implementsDescrSet() {
         return true;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class __bases___descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public __bases___descriptor() {
         super("__bases__", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyType)var1).getBases();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((PyType)var1).setBases((PyObject)var2);
      }

      public boolean implementsDescrSet() {
         return true;
      }

      public void invokeDelete(PyObject var1) {
         ((PyType)var1).delBases();
      }

      public boolean implementsDescrDelete() {
         return true;
      }
   }

   private static class __flags___descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public __flags___descriptor() {
         super("__flags__", PyLong.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyType)var1).getFlags();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public boolean implementsDescrSet() {
         return false;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class __name___descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public __name___descriptor() {
         super("__name__", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyType)var1).pyGetName();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((PyType)var1).pySetName((PyObject)var2);
      }

      public boolean implementsDescrSet() {
         return true;
      }

      public void invokeDelete(PyObject var1) {
         ((PyType)var1).pyDelName();
      }

      public boolean implementsDescrDelete() {
         return true;
      }
   }

   private static class __base___descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public __base___descriptor() {
         super("__base__", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyType)var1).getBase();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public boolean implementsDescrSet() {
         return false;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class __doc___descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public __doc___descriptor() {
         super("__doc__", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyType)var1).getDoc();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public boolean implementsDescrSet() {
         return false;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class __mro___descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public __mro___descriptor() {
         super("__mro__", PyTuple.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyType)var1).getMro();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public boolean implementsDescrSet() {
         return false;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class __dict___descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public __dict___descriptor() {
         super("__dict__", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyType)var1).getDict();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((PyType)var1).setDict((PyObject)var2);
      }

      public boolean implementsDescrSet() {
         return true;
      }

      public void invokeDelete(PyObject var1) {
         ((PyType)var1).delDict();
      }

      public boolean implementsDescrDelete() {
         return true;
      }
   }

   private static class __module___descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public __module___descriptor() {
         super("__module__", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyType)var1).getModule();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public boolean implementsDescrSet() {
         return false;
      }

      public void invokeDelete(PyObject var1) {
         ((PyType)var1).delModule();
      }

      public boolean implementsDescrDelete() {
         return true;
      }
   }

   private static class exposed___new__ extends PyNewWrapper {
      public exposed___new__() {
      }

      public PyObject new_impl(boolean var1, PyType var2, PyObject[] var3, String[] var4) {
         return PyType.type___new__(this, var1, var2, var3, var4);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new type___init___exposer("__init__"), new type___eq___exposer("__eq__"), new type___ne___exposer("__ne__"), new type___le___exposer("__le__"), new type___lt___exposer("__lt__"), new type___ge___exposer("__ge__"), new type___gt___exposer("__gt__"), new type___subclasses___exposer("__subclasses__"), new type___subclasscheck___exposer("__subclasscheck__"), new type___instancecheck___exposer("__instancecheck__"), new type_mro_exposer("mro"), new type___getattribute___exposer("__getattribute__"), new type___setattr___exposer("__setattr__"), new type___delattr___exposer("__delattr__"), new type___call___exposer("__call__"), new type_toString_exposer("__repr__")};
         PyDataDescr[] var2 = new PyDataDescr[]{new __abstractmethods___descriptor(), new __bases___descriptor(), new __flags___descriptor(), new __name___descriptor(), new __base___descriptor(), new __doc___descriptor(), new __mro___descriptor(), new __dict___descriptor(), new __module___descriptor()};
         super("type", PyType.class, Object.class, (boolean)1, "type(object) -> the object's type\ntype(name, bases, dict) -> a new type", var1, var2, new exposed___new__());
      }
   }
}
