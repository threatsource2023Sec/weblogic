package org.python.core;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.TimeUnit;
import org.python.core.util.StringUtil;
import org.python.util.Generic;

public class PyJavaType extends PyType {
   private static final Class[] OO = new Class[]{PyObject.class, PyObject.class};
   private static final Set BAD_AWT_METHODS = Generic.set("layout", "insets", "size", "minimumSize", "preferredSize", "maximumSize", "bounds", "enable");
   private static final Set immutableClasses = Generic.set(Boolean.class, Byte.class, Character.class, Class.class, Double.class, Float.class, Integer.class, Long.class, Short.class, String.class, InetAddress.class, Inet4Address.class, Inet6Address.class, InetSocketAddress.class, Proxy.class, URI.class, TimeUnit.class);
   private Set conflicted;
   private Set modified;

   public static PyObject wrapJavaObject(Object o) {
      PyObject obj = new PyObjectDerived(PyType.fromClass(o.getClass(), false));
      JyAttribute.setAttr(obj, (byte)-128, o);
      return obj;
   }

   public PyJavaType() {
      super(TYPE == null ? fromClass(PyType.class) : TYPE);
   }

   protected boolean useMetatypeFirst(PyObject attr) {
      return !(attr instanceof PyReflectedField) && !(attr instanceof PyReflectedFunction) && !(attr instanceof PyBeanEventProperty);
   }

   void type___setattr__(String name, PyObject value) {
      PyObject field = this.lookup(name);
      if (field == null || !field._doset((PyObject)null, value)) {
         if (this.modified == null) {
            this.modified = Generic.set();
         }

         if (this.modified.add(name) && this.conflicted != null) {
            Iterator var4 = this.conflicted.iterator();

            while(var4.hasNext()) {
               PyJavaType conflict = (PyJavaType)var4.next();
               if (conflict.modified != null && conflict.modified.contains(name)) {
                  throw Py.TypeError(this.getName() + " does not have a consistent method resolution order with " + conflict.getName() + ", and it already has " + name + " added for Python");
               }
            }
         }

         this.object___setattr__(name, value);
         this.postSetattr(name);
      }
   }

   void type___delattr__(String name) {
      PyObject field = this.lookup(name);
      if (field == null) {
         throw Py.NameError("attribute not found: " + name);
      } else {
         if (!field.jdontdel()) {
            this.object___delattr__(name);
         }

         if (this.modified != null) {
            this.modified.remove(name);
         }

         this.postDelattr(name);
      }
   }

   void handleMroError(PyType.MROMergeState[] toMerge, List mro) {
      if (this.underlying_class != null) {
         super.handleMroError(toMerge, mro);
      }

      Set inConflict = Generic.set();
      PyJavaType winner = null;
      PyType.MROMergeState[] var5 = toMerge;
      int var6 = toMerge.length;

      int i;
      for(int var7 = 0; var7 < var6; ++var7) {
         PyType.MROMergeState mergee = var5[var7];

         for(i = mergee.next; i < mergee.mro.length; ++i) {
            if (mergee.mro[i] != PyObject.TYPE && mergee.mro[i] != PyType.fromClass(Object.class)) {
               if (winner == null) {
                  winner = (PyJavaType)mergee.mro[i];
               }

               inConflict.add((PyJavaType)mergee.mro[i]);
            }
         }
      }

      Set allModified = Generic.set();
      PyJavaType[] conflictedAttributes = (PyJavaType[])inConflict.toArray(new PyJavaType[inConflict.size()]);
      PyJavaType[] var21 = conflictedAttributes;
      int var22 = conflictedAttributes.length;

      PyJavaType type;
      Iterator var11;
      label103:
      for(i = 0; i < var22; ++i) {
         type = var21[i];
         if (type.modified != null) {
            var11 = type.modified.iterator();

            String method;
            PyList types;
            Set proxySet;
            do {
               do {
                  if (!var11.hasNext()) {
                     continue label103;
                  }

                  method = (String)var11.next();
               } while(allModified.add(method));

               types = new PyList();
               proxySet = Generic.set();
               PyJavaType[] var15 = conflictedAttributes;
               int var16 = conflictedAttributes.length;

               for(int var17 = 0; var17 < var16; ++var17) {
                  PyJavaType othertype = var15[var17];
                  if (othertype.modified != null && othertype.modified.contains(method)) {
                     types.add(othertype);
                     proxySet.add(othertype.getProxyType());
                  }
               }
            } while(method.equals("__iter__") && proxySet.equals(Generic.set(Iterable.class, Map.class)));

            throw Py.TypeError(String.format("Supertypes that share a modified attribute have an MRO conflict[attribute=%s, supertypes=%s, type=%s]", method, types, this.getName()));
         }
      }

      var21 = conflictedAttributes;
      var22 = conflictedAttributes.length;

      for(i = 0; i < var22; ++i) {
         type = var21[i];
         var11 = inConflict.iterator();

         while(var11.hasNext()) {
            PyJavaType otherType = (PyJavaType)var11.next();
            if (otherType != type) {
               if (type.conflicted == null) {
                  type.conflicted = Generic.set();
               }

               type.conflicted.add(otherType);
            }
         }
      }

      mro.add(winner);
      PyType.MROMergeState[] var23 = toMerge;
      var22 = toMerge.length;

      for(i = 0; i < var22; ++i) {
         PyType.MROMergeState mergee = var23[i];
         mergee.removeFromUnmerged(winner);
      }

      this.computeMro(toMerge, mro);
   }

   protected void init(Class forClass, Set needsInners) {
      this.name = forClass.getName();
      if (this.name.startsWith("org.python.core.Py")) {
         this.name = this.name.substring("org.python.core.Py".length()).toLowerCase();
      }

      this.dict = new PyStringMap();
      Class baseClass = forClass.getSuperclass();
      Class c;
      if (PyObject.class.isAssignableFrom(forClass)) {
         this.underlying_class = forClass;
         this.computeLinearMro(baseClass);
      } else {
         needsInners.add(this);
         JyAttribute.setAttr(this, (byte)-128, forClass);
         this.objtype = PyType.fromClassSkippingInners(Class.class, needsInners);
         List visibleBases = Generic.list();
         Class[] var5 = forClass.getInterfaces();
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            c = var5[var7];
            if (c != PyProxy.class && c != ClassDictInit.class && (baseClass == null || !c.isAssignableFrom(baseClass))) {
               visibleBases.add(PyType.fromClassSkippingInners(c, needsInners));
            }
         }

         Object javaProxy = JyAttribute.getAttr(this, (byte)-128);
         if (javaProxy == Object.class) {
            this.base = PyType.fromClassSkippingInners(PyObject.class, needsInners);
         } else if (baseClass == null) {
            this.base = PyType.fromClassSkippingInners(Object.class, needsInners);
         } else if (javaProxy == Class.class) {
            this.base = PyType.fromClassSkippingInners(PyType.class, needsInners);
         } else {
            this.base = PyType.fromClassSkippingInners(baseClass, needsInners);
         }

         visibleBases.add(this.base);
         this.bases = (PyObject[])visibleBases.toArray(new PyObject[visibleBases.size()]);
         this.mro = this.computeMro();
      }

      if (!Modifier.isPublic(forClass.getModifiers()) && !this.name.startsWith("org.python.core") && Options.respectJavaAccessibility) {
         this.handleSuperMethodArgCollisions(forClass);
      } else {
         Map props = Generic.map();
         Map events = Generic.map();
         int var11;
         Method[] methods;
         if (Options.respectJavaAccessibility) {
            methods = forClass.getMethods();
         } else {
            List allMethods = Generic.list();

            for(c = forClass; c != null; c = c.getSuperclass()) {
               Method[] var9 = c.getDeclaredMethods();
               int var10 = var9.length;

               for(var11 = 0; var11 < var10; ++var11) {
                  Method meth = var9[var11];
                  allMethods.add(meth);
                  meth.setAccessible(true);
               }
            }

            methods = (Method[])allMethods.toArray(new Method[allMethods.size()]);
         }

         Arrays.sort(methods, new MethodComparator(new ClassComparator()));
         boolean isInAwt = this.name.startsWith("java.awt.") && this.name.indexOf(46, 9) == -1;
         ArrayList reflectedFuncs = new ArrayList(methods.length);
         Method[] var31 = methods;
         var11 = methods.length;

         Method meth;
         String methname;
         String fldname;
         int n;
         String methodName;
         PyReflectedFunction reflfunc;
         int var33;
         String doc;
         for(var33 = 0; var33 < var11; ++var33) {
            meth = var31[var33];
            if (declaredOnMember(baseClass, meth) && !ignore(meth)) {
               methname = meth.getName();
               if (!isInAwt || !BAD_AWT_METHODS.contains(methname)) {
                  fldname = normalize(methname);
                  reflfunc = (PyReflectedFunction)this.dict.__finditem__(fldname);
                  if (reflfunc == null) {
                     reflfunc = new PyReflectedFunction(new Method[]{meth});
                     reflectedFuncs.add(reflfunc);
                     this.dict.__setitem__((String)fldname, reflfunc);
                  } else {
                     reflfunc.addMethod(meth);
                  }

                  if (!Modifier.isStatic(meth.getModifiers())) {
                     n = meth.getParameterTypes().length;
                     if ((methname.startsWith("add") || methname.startsWith("set")) && methname.endsWith("Listener") && n == 1 && meth.getReturnType() == Void.TYPE && EventListener.class.isAssignableFrom(meth.getParameterTypes()[0])) {
                        Class eventClass = meth.getParameterTypes()[0];
                        doc = eventClass.getName();
                        int idot = doc.lastIndexOf(46);
                        if (idot != -1) {
                           doc = doc.substring(idot + 1);
                        }

                        doc = normalize(StringUtil.decapitalize(doc));
                        events.put(doc, new PyBeanEvent(doc, eventClass, meth));
                     } else {
                        methodName = null;
                        boolean get = true;
                        if (methname.startsWith("get") && methname.length() > 3 && n == 0) {
                           methodName = methname.substring(3);
                        } else if (methname.startsWith("is") && methname.length() > 2 && n == 0 && meth.getReturnType() == Boolean.TYPE) {
                           methodName = methname.substring(2);
                        } else if (methname.startsWith("set") && methname.length() > 3 && n == 1) {
                           methodName = methname.substring(3);
                           get = false;
                        }

                        if (methodName != null) {
                           methodName = normalize(StringUtil.decapitalize(methodName));
                           PyBeanProperty prop = (PyBeanProperty)props.get(methodName);
                           if (prop == null) {
                              prop = new PyBeanProperty(methodName, (Class)null, (Method)null, (Method)null);
                              props.put(methodName, prop);
                           }

                           if (get) {
                              prop.getMethod = meth;
                              prop.myType = meth.getReturnType();
                           } else {
                              prop.setMethod = meth;
                              if (prop.myType == null) {
                                 Class[] params = meth.getParameterTypes();
                                 if (params.length == 1) {
                                    prop.myType = params[0];
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }

         var31 = methods;
         var11 = methods.length;

         for(var33 = 0; var33 < var11; ++var33) {
            meth = var31[var33];
            methname = normalize(meth.getName());
            reflfunc = (PyReflectedFunction)this.dict.__finditem__(methname);
            if (reflfunc != null) {
               reflfunc.addMethod(meth);
            } else if (PyReflectedFunction.isPackagedProtected(meth.getDeclaringClass()) && this.lookup(methname) == null) {
               reflfunc = new PyReflectedFunction(new Method[]{meth});
               reflectedFuncs.add(reflfunc);
               this.dict.__setitem__((String)methname, reflfunc);
            }
         }

         Field[] fields;
         Field[] var34;
         int var36;
         Field field;
         if (Options.respectJavaAccessibility) {
            fields = forClass.getFields();
         } else {
            fields = forClass.getDeclaredFields();
            var34 = fields;
            var33 = fields.length;

            for(var36 = 0; var36 < var33; ++var36) {
               field = var34[var36];
               field.setAccessible(true);
            }
         }

         var34 = fields;
         var33 = fields.length;

         for(var36 = 0; var36 < var33; ++var36) {
            field = var34[var36];
            if (declaredOnMember(baseClass, field)) {
               fldname = field.getName();
               if (Modifier.isStatic(field.getModifiers()) && fldname.startsWith("__doc__") && fldname.length() > 7 && CharSequence.class.isAssignableFrom(field.getType())) {
                  String fname = fldname.substring(7).intern();
                  PyObject memb = this.dict.__finditem__(fname);
                  if (memb != null && memb instanceof PyReflectedFunction) {
                     doc = null;

                     CharSequence doc;
                     try {
                        doc = (CharSequence)field.get((Object)null);
                     } catch (IllegalAccessException var22) {
                        throw Py.JavaError(var22);
                     }

                     ((PyReflectedFunction)memb).__doc__ = doc instanceof PyString ? (PyString)doc : new PyString(doc.toString());
                  }
               }

               if (this.dict.__finditem__(normalize(fldname)) == null) {
                  this.dict.__setitem__((String)normalize(fldname), new PyReflectedField(field));
               }
            }
         }

         Iterator var35 = events.values().iterator();

         int var43;
         int var45;
         while(var35.hasNext()) {
            PyBeanEvent ev = (PyBeanEvent)var35.next();
            if (this.dict.__finditem__(ev.__name__) == null) {
               this.dict.__setitem__((String)ev.__name__, ev);
            }

            Method[] var41 = ev.eventClass.getMethods();
            var43 = var41.length;

            for(var45 = 0; var45 < var43; ++var45) {
               Method meth = var41[var45];
               methodName = meth.getName().intern();
               if (this.dict.__finditem__(methodName) == null) {
                  this.dict.__setitem__((String)methodName, new PyBeanEventProperty(methodName, ev.eventClass, ev.addMethod, meth));
               }
            }
         }

         var35 = props.values().iterator();

         while(true) {
            PyBeanProperty prop;
            PyObject[] fromType;
            PyObject superForName;
            do {
               while(true) {
                  if (!var35.hasNext()) {
                     final PyReflectedConstructor reflctr = new PyReflectedConstructor(this.name);
                     Constructor[] constructors;
                     Constructor[] var46;
                     Constructor ctr;
                     if (!Options.respectJavaAccessibility && Class.class != forClass) {
                        constructors = forClass.getDeclaredConstructors();
                        var46 = constructors;
                        var43 = constructors.length;

                        for(var45 = 0; var45 < var43; ++var45) {
                           ctr = var46[var45];
                           ctr.setAccessible(true);
                        }
                     } else {
                        constructors = forClass.getConstructors();
                     }

                     var46 = constructors;
                     var43 = constructors.length;

                     for(var45 = 0; var45 < var43; ++var45) {
                        ctr = var46[var45];
                        reflctr.addConstructor(ctr);
                     }

                     if (PyObject.class.isAssignableFrom(forClass)) {
                        PyObject new_ = new PyNewWrapper(forClass, "__new__", -1, -1) {
                           public PyObject new_impl(boolean init, PyType subtype, PyObject[] args, String[] keywords) {
                              return reflctr.make(args, keywords);
                           }
                        };
                        this.dict.__setitem__((String)"__new__", new_);
                     } else {
                        this.dict.__setitem__((String)"__init__", reflctr);
                     }

                     PyBuiltinMethod[] collectionProxyMethods = (PyBuiltinMethod[])getCollectionProxies().get(forClass);
                     if (collectionProxyMethods != null) {
                        PyBuiltinMethod[] var53 = collectionProxyMethods;
                        var45 = collectionProxyMethods.length;

                        for(n = 0; n < var45; ++n) {
                           PyBuiltinMethod meth = var53[n];
                           this.addMethod(meth);
                        }
                     }

                     Iterator var55 = getPostCollectionProxies().keySet().iterator();

                     while(true) {
                        Class type;
                        do {
                           if (!var55.hasNext()) {
                              PyObject nameSpecified = null;
                              if (ClassDictInit.class.isAssignableFrom(forClass) && forClass != ClassDictInit.class) {
                                 try {
                                    Method m = forClass.getMethod("classDictInit", PyObject.class);
                                    m.invoke((Object)null, this.dict);
                                    nameSpecified = this.dict.__finditem__("__name__");
                                    if (nameSpecified != null) {
                                       this.name = ((PyObject)nameSpecified).toString();
                                    }
                                 } catch (Exception var21) {
                                    throw Py.JavaError(var21);
                                 }
                              }

                              if (reflectedFuncs.size() > 0) {
                                 if (nameSpecified == null) {
                                    nameSpecified = Py.newString(this.name);
                                 }

                                 PyReflectedFunction func;
                                 for(Iterator var63 = reflectedFuncs.iterator(); var63.hasNext(); func.__module__ = (PyObject)nameSpecified) {
                                    func = (PyReflectedFunction)var63.next();
                                 }
                              }

                              if (baseClass != Object.class) {
                                 this.hasGet = getDescrMethod(forClass, "__get__", OO) != null || getDescrMethod(forClass, "_doget", PyObject.class) != null || getDescrMethod(forClass, "_doget", OO) != null;
                                 this.hasSet = getDescrMethod(forClass, "__set__", OO) != null || getDescrMethod(forClass, "_doset", OO) != null;
                                 this.hasDelete = getDescrMethod(forClass, "__delete__", PyObject.class) != null || getDescrMethod(forClass, "_dodel", PyObject.class) != null;
                              }

                              if (forClass == Object.class) {
                                 this.addMethod(new PyBuiltinMethodNarrow("__copy__") {
                                    public PyObject __call__() {
                                       throw Py.TypeError("Could not copy Java object because it is not Cloneable or known to be immutable. Consider monkeypatching __copy__ for " + this.self.getType().fastGetName());
                                    }
                                 });
                                 this.addMethod(new PyBuiltinMethodNarrow("__deepcopy__") {
                                    public PyObject __call__(PyObject memo) {
                                       throw Py.TypeError("Could not deepcopy Java object because it is not Serializable. Consider monkeypatching __deepcopy__ for " + this.self.getType().fastGetName());
                                    }
                                 });
                                 this.addMethod(new PyBuiltinMethodNarrow("__eq__", 1) {
                                    public PyObject __call__(PyObject o) {
                                       Object proxy = this.self.getJavaProxy();
                                       Object oProxy = o.getJavaProxy();
                                       return proxy.equals(oProxy) ? Py.True : Py.False;
                                    }
                                 });
                                 this.addMethod(new PyBuiltinMethodNarrow("__ne__", 1) {
                                    public PyObject __call__(PyObject o) {
                                       Object proxy = this.self.getJavaProxy();
                                       Object oProxy = o.getJavaProxy();
                                       return !proxy.equals(oProxy) ? Py.True : Py.False;
                                    }
                                 });
                                 this.addMethod(new PyBuiltinMethodNarrow("__hash__") {
                                    public PyObject __call__() {
                                       return Py.newInteger(this.self.getJavaProxy().hashCode());
                                    }
                                 });
                                 this.addMethod(new PyBuiltinMethodNarrow("__repr__") {
                                    public PyObject __call__() {
                                       String toString = this.self.getJavaProxy().toString();
                                       return toString == null ? Py.EmptyUnicode : Py.newUnicode(toString);
                                    }
                                 });
                                 this.addMethod(new PyBuiltinMethodNarrow("__unicode__") {
                                    public PyObject __call__() {
                                       return new PyUnicode(this.self.toString());
                                    }
                                 });
                              }

                              if (forClass == Comparable.class) {
                                 this.addMethod(new ComparableMethod("__lt__", 1) {
                                    protected boolean getResult(int comparison) {
                                       return comparison < 0;
                                    }
                                 });
                                 this.addMethod(new ComparableMethod("__le__", 1) {
                                    protected boolean getResult(int comparison) {
                                       return comparison <= 0;
                                    }
                                 });
                                 this.addMethod(new ComparableMethod("__gt__", 1) {
                                    protected boolean getResult(int comparison) {
                                       return comparison > 0;
                                    }
                                 });
                                 this.addMethod(new ComparableMethod("__ge__", 1) {
                                    protected boolean getResult(int comparison) {
                                       return comparison >= 0;
                                    }
                                 });
                              }

                              if (immutableClasses.contains(forClass)) {
                                 this.addMethod(new PyBuiltinMethodNarrow("__copy__") {
                                    public PyObject __call__() {
                                       return this.self;
                                    }
                                 });
                              }

                              if (forClass == Cloneable.class) {
                                 this.addMethod(new PyBuiltinMethodNarrow("__copy__") {
                                    public PyObject __call__() {
                                       Object obj = this.self.getJavaProxy();

                                       try {
                                          Method clone = obj.getClass().getMethod("clone");
                                          Object copy = clone.invoke(obj);
                                          return Py.java2py(copy);
                                       } catch (Exception var4) {
                                          throw Py.TypeError("Could not copy Java object");
                                       }
                                    }
                                 });
                              }

                              if (forClass == Serializable.class) {
                                 this.addMethod(new PyBuiltinMethodNarrow("__deepcopy__") {
                                    public PyObject __call__(PyObject memo) {
                                       Object obj = this.self.getJavaProxy();

                                       try {
                                          Object copy = PyJavaType.cloneX(obj);
                                          return Py.java2py(copy);
                                       } catch (Exception var4) {
                                          throw Py.TypeError("Could not copy Java object");
                                       }
                                    }
                                 });
                              }

                              return;
                           }

                           type = (Class)var55.next();
                        } while(!type.isAssignableFrom(forClass));

                        PyBuiltinMethod[] var59 = (PyBuiltinMethod[])getPostCollectionProxies().get(type);
                        int var68 = var59.length;

                        for(int var71 = 0; var71 < var68; ++var71) {
                           PyBuiltinMethod meth = var59[var71];
                           this.addMethod(meth);
                        }
                     }
                  }

                  prop = (PyBeanProperty)var35.next();
                  PyObject prev = this.dict.__finditem__(prop.__name__);
                  if (prev == null) {
                     break;
                  }

                  if (prev instanceof PyReflectedField && Modifier.isStatic(((PyReflectedField)prev).field.getModifiers())) {
                     prop.field = ((PyReflectedField)prev).field;
                     break;
                  }
               }

               fromType = new PyObject[]{null};
               superForName = this.lookup_where_mro(prop.__name__, fromType);
               if (superForName instanceof PyBeanProperty) {
                  PyBeanProperty superProp = (PyBeanProperty)superForName;
                  if (prop.setMethod == null) {
                     prop.setMethod = superProp.setMethod;
                  } else if (prop.getMethod == null && superProp.myType == prop.setMethod.getParameterTypes()[0]) {
                     prop.getMethod = superProp.getMethod;
                     prop.myType = superProp.myType;
                  }

                  if (prop.field == null) {
                     prop.field = superProp.field;
                  }
                  break;
               }
            } while(superForName != null && fromType[0] != this && !(superForName instanceof PyBeanEvent));

            if (prop.getMethod != null && prop.setMethod != null && prop.myType != prop.setMethod.getParameterTypes()[0]) {
               prop.setMethod = null;
            }

            this.dict.__setitem__((String)prop.__name__, prop);
         }
      }
   }

   private static Object cloneX(Object x) throws IOException, ClassNotFoundException {
      ByteArrayOutputStream bout = new ByteArrayOutputStream();
      CloneOutput cout = new CloneOutput(bout);
      cout.writeObject(x);
      byte[] bytes = bout.toByteArray();
      ByteArrayInputStream bin = new ByteArrayInputStream(bytes);
      CloneInput cin = new CloneInput(bin, cout);
      Object clone = cin.readObject();
      cin.close();
      return clone;
   }

   private void handleSuperMethodArgCollisions(Class forClass) {
      Class[] var2 = forClass.getInterfaces();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Class iface = var2[var4];
         this.mergeMethods(iface);
      }

      if (forClass.getSuperclass() != null) {
         this.mergeMethods(forClass.getSuperclass());
         if (!Modifier.isPublic(forClass.getSuperclass().getModifiers())) {
            this.handleSuperMethodArgCollisions(forClass.getSuperclass());
         }
      }

   }

   private void mergeMethods(Class parent) {
      Method[] var2 = parent.getMethods();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Method meth = var2[var4];
         if (Modifier.isPublic(meth.getDeclaringClass().getModifiers())) {
            String nmethname = normalize(meth.getName());
            PyObject[] where = new PyObject[1];
            PyObject obj = this.lookup_where_mro(nmethname, where);
            if (obj != null) {
               if (where[0] == this) {
                  if (!((PyReflectedFunction)obj).handles(meth)) {
                     ((PyReflectedFunction)obj).addMethod(meth);
                  }
               } else {
                  this.dict.__setitem__((String)nmethname, new PyReflectedFunction(new Method[]{meth}));
               }
            }
         }
      }

   }

   private static boolean declaredOnMember(Class base, Member declaring) {
      return base == null || declaring.getDeclaringClass() != base && base.isAssignableFrom(declaring.getDeclaringClass());
   }

   private static String normalize(String name) {
      if (name.endsWith("$")) {
         name = name.substring(0, name.length() - 1);
      }

      return name.intern();
   }

   private static Method getDescrMethod(Class c, String name, Class... parmtypes) {
      Method meth;
      try {
         meth = c.getMethod(name, parmtypes);
      } catch (NoSuchMethodException var5) {
         return null;
      }

      return !Modifier.isStatic(meth.getModifiers()) && meth.getDeclaringClass() != PyObject.class ? meth : null;
   }

   private static boolean ignore(Method meth) {
      Class[] exceptions = meth.getExceptionTypes();
      Class[] var2 = exceptions;
      int var3 = exceptions.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Class exception = var2[var4];
         if (exception == PyIgnoreMethodTag.class) {
            return true;
         }
      }

      return false;
   }

   private static Map getCollectionProxies() {
      return PyJavaType.CollectionsProxiesHolder.proxies.proxies;
   }

   private static Map getPostCollectionProxies() {
      return PyJavaType.CollectionsProxiesHolder.proxies.postProxies;
   }

   private static Map buildCollectionProxies() {
      Map proxies = new HashMap();
      PyBuiltinMethodNarrow iterableProxy = new PyBuiltinMethodNarrow("__iter__") {
         public PyObject __call__() {
            return new JavaIterator((Iterable)this.self.getJavaProxy());
         }
      };
      proxies.put(Iterable.class, new PyBuiltinMethod[]{iterableProxy});
      PyBuiltinMethodNarrow lenProxy = new PyBuiltinMethodNarrow("__len__") {
         public PyObject __call__() {
            return Py.newInteger(((Collection)this.self.getJavaProxy()).size());
         }
      };
      PyBuiltinMethodNarrow containsProxy = new PyBuiltinMethodNarrow("__contains__", 1) {
         public PyObject __call__(PyObject obj) {
            boolean contained = false;
            Object proxy = obj.getJavaProxy();
            if (proxy == null) {
               Iterator var4 = ((Collection)this.self.getJavaProxy()).iterator();

               while(var4.hasNext()) {
                  Object item = var4.next();
                  if (Py.java2py(item)._eq(obj).__nonzero__()) {
                     contained = true;
                     break;
                  }
               }
            } else {
               Object other = obj.__tojava__(Object.class);
               contained = ((Collection)this.self.getJavaProxy()).contains(other);
            }

            return contained ? Py.True : Py.False;
         }
      };
      proxies.put(Collection.class, new PyBuiltinMethod[]{lenProxy, containsProxy});
      PyBuiltinMethodNarrow iteratorProxy = new PyBuiltinMethodNarrow("__iter__") {
         public PyObject __call__() {
            return new JavaIterator((Iterator)this.self.getJavaProxy());
         }
      };
      proxies.put(Iterator.class, new PyBuiltinMethod[]{iteratorProxy});
      PyBuiltinMethodNarrow enumerationProxy = new PyBuiltinMethodNarrow("__iter__") {
         public PyObject __call__() {
            return new EnumerationIter((Enumeration)this.self.getJavaProxy());
         }
      };
      proxies.put(Enumeration.class, new PyBuiltinMethod[]{enumerationProxy});
      proxies.put(List.class, JavaProxyList.getProxyMethods());
      proxies.put(Map.class, JavaProxyMap.getProxyMethods());
      proxies.put(Set.class, JavaProxySet.getProxyMethods());
      return Collections.unmodifiableMap(proxies);
   }

   private static Map buildPostCollectionProxies() {
      Map postProxies = new HashMap();
      postProxies.put(List.class, JavaProxyList.getPostProxyMethods());
      postProxies.put(Map.class, JavaProxyMap.getPostProxyMethods());
      postProxies.put(Set.class, JavaProxySet.getPostProxyMethods());
      return Collections.unmodifiableMap(postProxies);
   }

   public int traverse(Visitproc visit, Object arg) {
      int retVal = super.traverse(visit, arg);
      if (retVal != 0) {
         return retVal;
      } else {
         if (this.conflicted != null) {
            Iterator var4 = this.conflicted.iterator();

            while(var4.hasNext()) {
               PyObject ob = (PyObject)var4.next();
               if (ob != null) {
                  retVal = visit.visit(ob, arg);
                  if (retVal != 0) {
                     return retVal;
                  }
               }
            }
         }

         return 0;
      }
   }

   public boolean refersDirectlyTo(PyObject ob) throws UnsupportedOperationException {
      if (ob == null) {
         return false;
      } else {
         if (this.conflicted != null) {
            Iterator var2 = this.conflicted.iterator();

            while(var2.hasNext()) {
               PyObject obj = (PyObject)var2.next();
               if (obj == ob) {
                  return true;
               }
            }
         }

         return super.refersDirectlyTo(ob);
      }
   }

   private class MethodComparator implements Comparator {
      private ClassComparator classComparator;

      public MethodComparator(ClassComparator classComparator) {
         this.classComparator = classComparator;
      }

      public int compare(Method m1, Method m2) {
         int result = m1.getName().compareTo(m2.getName());
         if (result != 0) {
            return result;
         } else {
            Class[] p1 = m1.getParameterTypes();
            Class[] p2 = m2.getParameterTypes();
            int n1 = p1.length;
            int n2 = p2.length;
            result = n1 - n2;
            if (result != 0) {
               return result;
            } else {
               result = this.classComparator.compare(m1.getDeclaringClass(), m2.getDeclaringClass());
               if (result != 0) {
                  return result;
               } else if (n1 == 0) {
                  return this.classComparator.compare(m1.getReturnType(), m2.getReturnType());
               } else {
                  return n1 == 1 ? this.classComparator.compare(p1[0], p2[0]) : result;
               }
            }
         }
      }
   }

   private class ClassComparator implements Comparator {
      private ClassComparator() {
      }

      public int compare(Class c1, Class c2) {
         if (c1.equals(c2)) {
            return 0;
         } else if (c1.isAssignableFrom(c2)) {
            return -1;
         } else if (c2.isAssignableFrom(c1)) {
            return 1;
         } else {
            String s1 = this.hierarchyName(c1);
            String s2 = this.hierarchyName(c2);
            return s1.compareTo(s2);
         }
      }

      private String hierarchyName(Class c) {
         Stack nameStack = new Stack();
         StringBuilder namesBuilder = new StringBuilder();

         do {
            nameStack.push(c.getSimpleName());
            c = c.getSuperclass();
         } while(c != null);

         Iterator var4 = nameStack.iterator();

         while(var4.hasNext()) {
            String name = (String)var4.next();
            namesBuilder.append(name);
         }

         return namesBuilder.toString();
      }

      // $FF: synthetic method
      ClassComparator(Object x1) {
         this();
      }
   }

   private static class CollectionsProxiesHolder {
      static final CollectionProxies proxies = new CollectionProxies();
   }

   private static class CollectionProxies {
      final Map proxies = PyJavaType.buildCollectionProxies();
      final Map postProxies = PyJavaType.buildPostCollectionProxies();

      CollectionProxies() {
      }
   }

   private abstract static class ComparableMethod extends PyBuiltinMethodNarrow {
      protected ComparableMethod(String name, int numArgs) {
         super(name, numArgs);
      }

      public PyObject __call__(PyObject arg) {
         Object asjava = arg.__tojava__(Object.class);

         int compare;
         try {
            compare = ((Comparable)this.self.getJavaProxy()).compareTo(asjava);
         } catch (ClassCastException var5) {
            return Py.NotImplemented;
         }

         return this.getResult(compare) ? Py.True : Py.False;
      }

      protected abstract boolean getResult(int var1);
   }

   private static class EnumerationIter extends PyIterator {
      private Enumeration proxy;

      public EnumerationIter(Enumeration proxy) {
         this.proxy = proxy;
      }

      public PyObject __iternext__() {
         return this.proxy.hasMoreElements() ? Py.java2py(this.proxy.nextElement()) : null;
      }
   }

   private static class CloneInput extends ObjectInputStream {
      private final CloneOutput output;

      CloneInput(InputStream in, CloneOutput output) throws IOException {
         super(in);
         this.output = output;
      }

      protected Class resolveClass(ObjectStreamClass osc) throws IOException, ClassNotFoundException {
         Class c = (Class)this.output.classQueue.poll();
         String expected = osc.getName();
         String found = c == null ? null : c.getName();
         if (!expected.equals(found)) {
            throw new InvalidClassException("Classes desynchronized: found " + found + " when expecting " + expected);
         } else {
            return c;
         }
      }

      protected Class resolveProxyClass(String[] interfaceNames) throws IOException, ClassNotFoundException {
         return (Class)this.output.classQueue.poll();
      }
   }

   private static class CloneOutput extends ObjectOutputStream {
      Queue classQueue = new LinkedList();

      CloneOutput(OutputStream out) throws IOException {
         super(out);
      }

      protected void annotateClass(Class c) {
         this.classQueue.add(c);
      }

      protected void annotateProxyClass(Class c) {
         this.classQueue.add(c);
      }
   }
}
