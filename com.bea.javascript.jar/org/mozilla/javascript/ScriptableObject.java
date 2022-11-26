package org.mozilla.javascript;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Enumeration;
import java.util.Hashtable;

public abstract class ScriptableObject implements Scriptable {
   public static final int EMPTY = 0;
   public static final int READONLY = 1;
   public static final int DONTENUM = 2;
   public static final int PERMANENT = 4;
   protected Scriptable prototype;
   protected Scriptable parent;
   private static final Object HAS_STATIC_ACCESSORS;
   private static final Slot REMOVED;
   private static Hashtable exclusionList;
   private Slot[] slots;
   private int count;
   private Slot lastAccess;
   private static final Class ContextClass;
   // $FF: synthetic field
   static Class class$org$mozilla$javascript$Context;
   // $FF: synthetic field
   static Class class$org$mozilla$javascript$Scriptable;
   // $FF: synthetic field
   static Class class$org$mozilla$javascript$Function;
   // $FF: synthetic field
   static Class class$org$mozilla$javascript$FunctionObject;
   // $FF: synthetic field
   static Class class$org$mozilla$javascript$ScriptableObject;

   static {
      HAS_STATIC_ACCESSORS = Void.TYPE;
      REMOVED = new Slot();
      exclusionList = null;
      ContextClass = class$org$mozilla$javascript$Context != null ? class$org$mozilla$javascript$Context : (class$org$mozilla$javascript$Context = class$("org.mozilla.javascript.Context"));
   }

   public ScriptableObject() {
      this.lastAccess = REMOVED;
   }

   synchronized void addPropertyAttribute(int var1) {
      if (this.slots != null) {
         for(int var2 = 0; var2 < this.slots.length; ++var2) {
            Slot var3 = this.slots[var2];
            if (var3 != null && var3 != REMOVED && ((var3.flags & 2) == 0 || var1 != 1)) {
               var3.attributes = (short)(var3.attributes | var1);
            }
         }

      }
   }

   private synchronized Slot addSlot(String var1, int var2, boolean var3) {
      if (this.count == -1) {
         throw Context.reportRuntimeError0("msg.add.sealed");
      } else {
         int var4 = (var2 & Integer.MAX_VALUE) % this.slots.length;
         int var5 = var4;

         while(true) {
            Slot var6 = this.slots[var5];
            if (var6 != null && var6 != REMOVED) {
               if (var6.intKey != var2 || var6.stringKey != var1 && (var1 == null || !var1.equals(var6.stringKey))) {
                  ++var5;
                  if (var5 == this.slots.length) {
                     var5 = 0;
                  }

                  if (var5 == var4) {
                     throw new RuntimeException("Hashtable internal error");
                  }
                  continue;
               }

               return var6;
            }

            if (4 * (this.count + 1) > 3 * this.slots.length) {
               this.grow();
               return this.getSlotToSet(var1, var2, var3);
            }

            Object var7 = var3 ? new GetterSlot() : new Slot();
            ((Slot)var7).stringKey = var1;
            ((Slot)var7).intKey = var2;
            this.slots[var5] = (Slot)var7;
            ++this.count;
            return (Slot)var7;
         }
      }
   }

   public static Object callMethod(Scriptable var0, String var1, Object[] var2) throws JavaScriptException {
      Context var3 = Context.enter();

      Object var4;
      try {
         Object var7 = getProperty(var0, var1);
         if (var7 == Scriptable.NOT_FOUND) {
            var7 = Undefined.instance;
         }

         var4 = ScriptRuntime.call(var3, var7, var0, var2, getTopLevelScope(var0));
      } finally {
         Context.exit();
      }

      return var4;
   }

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   public static void defineClass(Scriptable var0, Class var1) throws IllegalAccessException, InstantiationException, InvocationTargetException, ClassDefinitionException, PropertyException {
      defineClass(var0, var1, false);
   }

   public static void defineClass(Scriptable var0, Class var1, boolean var2) throws IllegalAccessException, InstantiationException, InvocationTargetException, ClassDefinitionException, PropertyException {
      Method[] var3 = FunctionObject.getMethodList(var1);

      for(int var4 = 0; var4 < var3.length; ++var4) {
         Method var5 = var3[var4];
         if (var5.getName().equals("init")) {
            Class[] var6 = var5.getParameterTypes();
            Object[] var7;
            if (var6.length == 3 && var6[0] == ContextClass && var6[1] == ScriptRuntime.ScriptableClass && var6[2] == Boolean.TYPE && Modifier.isStatic(var5.getModifiers())) {
               var7 = new Object[]{Context.getContext(), var0, var2 ? Boolean.TRUE : Boolean.FALSE};
               var5.invoke((Object)null, var7);
               return;
            }

            if (var6.length == 1 && var6[0] == ScriptRuntime.ScriptableClass && Modifier.isStatic(var5.getModifiers())) {
               var7 = new Object[]{var0};
               var5.invoke((Object)null, var7);
               return;
            }
         }
      }

      Hashtable var34 = getExclusionList();
      Constructor[] var35 = var1.getConstructors();
      Constructor var36 = null;

      for(int var8 = 0; var8 < var35.length; ++var8) {
         if (var35[var8].getParameterTypes().length == 0) {
            var36 = var35[var8];
            break;
         }
      }

      if (var36 == null) {
         throw new ClassDefinitionException(Context.getMessage1("msg.zero.arg.ctor", var1.getName()));
      } else {
         Scriptable var9 = (Scriptable)var36.newInstance(ScriptRuntime.emptyArgs);
         var9.setPrototype(getObjectPrototype(var0));
         String var10 = var9.getClassName();
         String var11 = "js_";
         String var12 = "jsFunction_";
         String var13 = "jsStaticFunction_";
         String var14 = "jsProperty_";
         String var15 = "jsGet_";
         String var16 = "jsSet_";
         String var17 = "jsConstructor";
         boolean var18 = false;
         Method[] var19 = FunctionObject.findMethods(var1, "jsConstructor");
         Object var20 = null;
         if (var19 != null) {
            if (var19.length > 1) {
               throw new ClassDefinitionException(Context.getMessage2("msg.multiple.ctors", var19[0], var19[1]));
            }

            var20 = var19[0];
            var18 = true;
         }

         for(int var21 = 0; var21 < var3.length; ++var21) {
            String var22 = var3[var21].getName();
            String var23 = null;
            if (!var22.startsWith("js")) {
               var23 = null;
            } else if (var22.startsWith("js_")) {
               var23 = "js_";
            } else if (var22.startsWith("jsFunction_")) {
               var23 = "jsFunction_";
            } else if (var22.startsWith("jsStaticFunction_")) {
               var23 = "jsStaticFunction_";
            } else if (var22.startsWith("jsProperty_")) {
               var23 = "jsProperty_";
            } else if (var22.startsWith("jsGet_")) {
               var23 = "jsGet_";
            } else if (var22.startsWith("jsSet_")) {
               var23 = "jsSet_";
            }

            if (var23 != null) {
               var18 = true;
               var22 = var22.substring(var23.length());
            }

            if (var22.equals(var10)) {
               if (var20 != null) {
                  throw new ClassDefinitionException(Context.getMessage2("msg.multiple.ctors", var20, var3[var21]));
               }

               var20 = var3[var21];
            }
         }

         if (var20 == null) {
            if (var35.length == 1) {
               var20 = var35[0];
            } else if (var35.length == 2) {
               if (var35[0].getParameterTypes().length == 0) {
                  var20 = var35[1];
               } else if (var35[1].getParameterTypes().length == 0) {
                  var20 = var35[0];
               }
            }

            if (var20 == null) {
               throw new ClassDefinitionException(Context.getMessage1("msg.ctor.multiple.parms", var1.getName()));
            }
         }

         FunctionObject var37 = new FunctionObject(var10, (Member)var20, var0);
         if (var37.isVarArgsMethod()) {
            throw Context.reportRuntimeError1("msg.varargs.ctor", ((Member)var20).getName());
         } else {
            var37.addAsConstructor(var0, var9);
            if (!var18 && var34 == null) {
               var34 = getExclusionList();
            }

            Method var38 = null;

            for(int var24 = 0; var24 < var3.length; ++var24) {
               if (var18 || var3[var24].getDeclaringClass() == var1) {
                  String var25 = var3[var24].getName();
                  if (var25.equals("finishInit")) {
                     Class[] var26 = var3[var24].getParameterTypes();
                     if (var26.length == 3 && var26[0] == ScriptRuntime.ScriptableClass && var26[1] == (class$org$mozilla$javascript$FunctionObject != null ? class$org$mozilla$javascript$FunctionObject : (class$org$mozilla$javascript$FunctionObject = class$("org.mozilla.javascript.FunctionObject"))) && var26[2] == ScriptRuntime.ScriptableClass && Modifier.isStatic(var3[var24].getModifiers())) {
                        var38 = var3[var24];
                        continue;
                     }
                  }

                  if (var25.indexOf(36) == -1 && !var25.equals("jsConstructor")) {
                     String var39 = null;
                     if (var18) {
                        if (var25.startsWith("js_")) {
                           var39 = "js_";
                        } else if (var25.startsWith("jsFunction_")) {
                           var39 = "jsFunction_";
                        } else if (var25.startsWith("jsStaticFunction_")) {
                           var39 = "jsStaticFunction_";
                           if (!Modifier.isStatic(var3[var24].getModifiers())) {
                              throw new ClassDefinitionException("jsStaticFunction must be used with static method.");
                           }
                        } else if (var25.startsWith("jsProperty_")) {
                           var39 = "jsProperty_";
                        } else if (var25.startsWith("jsGet_")) {
                           var39 = "jsGet_";
                        } else {
                           if (!var25.startsWith("jsSet_")) {
                              continue;
                           }

                           var39 = "jsSet_";
                        }

                        var25 = var25.substring(var39.length());
                     } else if (var34.get(var25) != null) {
                        continue;
                     }

                     if (var3[var24] != var20 && (var39 == null || !var39.equals("jsSet_"))) {
                        int var43;
                        if (var39 != null && var39.equals("jsGet_")) {
                           if (!(var9 instanceof ScriptableObject)) {
                              throw PropertyException.withMessage2("msg.extend.scriptable", var9.getClass().toString(), var25);
                           }

                           Method[] var42 = FunctionObject.findMethods(var1, "jsSet_" + var25);
                           if (var42 != null && var42.length != 1) {
                              throw PropertyException.withMessage2("msg.no.overload", var25, var1.getName());
                           }

                           var43 = 6 | (var42 != null ? 0 : 1);
                           Method var44 = var42 == null ? null : var42[0];
                           ((ScriptableObject)var9).defineProperty(var25, (Object)null, var3[var24], var44, var43);
                        } else if ((var25.startsWith("get") || var25.startsWith("set")) && var25.length() > 3 && (!var18 || !var39.equals("jsFunction_") && !var39.equals("jsStaticFunction_"))) {
                           if (!(var9 instanceof ScriptableObject)) {
                              throw PropertyException.withMessage2("msg.extend.scriptable", var9.getClass().toString(), var25);
                           }

                           if (!var25.startsWith("set")) {
                              StringBuffer var41 = new StringBuffer();
                              var43 = var25.charAt(3);
                              var41.append(Character.toLowerCase((char)var43));
                              if (var25.length() > 4) {
                                 var41.append(var25.substring(4));
                              }

                              String var29 = var41.toString();
                              var41.setCharAt(0, (char)var43);
                              var41.insert(0, "set");
                              String var30 = var41.toString();
                              Method[] var31 = FunctionObject.findMethods(var1, var18 ? "js_" + var30 : var30);
                              if (var31 != null && var31.length != 1) {
                                 throw PropertyException.withMessage2("msg.no.overload", var25, var1.getName());
                              }

                              if (var31 == null && var18) {
                                 var31 = FunctionObject.findMethods(var1, "jsProperty_" + var30);
                              }

                              int var32 = 6 | (var31 != null ? 0 : 1);
                              Method var33 = var31 == null ? null : var31[0];
                              ((ScriptableObject)var9).defineProperty(var29, (Object)null, var3[var24], var33, var32);
                           }
                        } else {
                           FunctionObject var27 = new FunctionObject(var25, var3[var24], var9);
                           if (var27.isVarArgsConstructor()) {
                              throw Context.reportRuntimeError1("msg.varargs.fun", ((Member)var20).getName());
                           }

                           Object var28 = var39 == "jsStaticFunction_" ? var37 : var9;
                           defineProperty((Scriptable)var28, var25, var27, 2);
                           if (var2) {
                              var27.sealObject();
                              var27.addPropertyAttribute(1);
                           }
                        }
                     }
                  }
               }
            }

            if (var38 != null) {
               Object[] var40 = new Object[]{var0, var37, var9};
               var38.invoke((Object)null, var40);
            }

            if (var2) {
               var37.sealObject();
               var37.addPropertyAttribute(1);
               if (var9 instanceof ScriptableObject) {
                  ((ScriptableObject)var9).sealObject();
                  ((ScriptableObject)var9).addPropertyAttribute(1);
               }
            }

         }
      }
   }

   public void defineFunctionProperties(String[] var1, Class var2, int var3) throws PropertyException {
      for(int var4 = 0; var4 < var1.length; ++var4) {
         String var5 = var1[var4];
         Method[] var6 = FunctionObject.findMethods(var2, var5);
         if (var6 == null) {
            throw PropertyException.withMessage2("msg.method.not.found", var5, var2.getName());
         }

         if (var6.length > 1) {
            throw PropertyException.withMessage2("msg.no.overload", var5, var2.getName());
         }

         FunctionObject var7 = new FunctionObject(var5, var6[0], this);
         this.defineProperty(var5, (Object)var7, var3);
      }

   }

   public void defineProperty(String var1, Class var2, int var3) throws PropertyException {
      StringBuffer var4 = new StringBuffer(var1);
      var4.setCharAt(0, Character.toUpperCase(var1.charAt(0)));
      String var5 = var4.toString();
      Method[] var6 = FunctionObject.findMethods(var2, "get" + var5);
      Method[] var7 = FunctionObject.findMethods(var2, "set" + var5);
      if (var7 == null) {
         var3 |= 1;
      }

      if (var6.length == 1 && (var7 == null || var7.length == 1)) {
         this.defineProperty(var1, (Object)null, var6[0], var7 == null ? null : var7[0], var3);
      } else {
         throw PropertyException.withMessage2("msg.no.overload", var1, var2.getName());
      }
   }

   public void defineProperty(String var1, Object var2, int var3) {
      this.put(var1, this, var2);

      try {
         this.setAttributes(var1, this, var3);
      } catch (PropertyException var4) {
         throw new RuntimeException("Cannot create property");
      }
   }

   public void defineProperty(String var1, Object var2, Method var3, Method var4, int var5) throws PropertyException {
      int var6 = 1;
      if (var2 == null && Modifier.isStatic(var3.getModifiers())) {
         var2 = HAS_STATIC_ACCESSORS;
      }

      Class[] var7 = var3.getParameterTypes();
      if (var7.length != 0) {
         if (var7.length != 1 || var7[0] != (class$org$mozilla$javascript$ScriptableObject != null ? class$org$mozilla$javascript$ScriptableObject : (class$org$mozilla$javascript$ScriptableObject = class$("org.mozilla.javascript.ScriptableObject")))) {
            throw PropertyException.withMessage1("msg.bad.getter.parms", var3.toString());
         }
      } else if (var2 != null) {
         throw PropertyException.withMessage1("msg.obj.getter.parms", var3.toString());
      }

      if (var4 != null) {
         var6 |= 2;
         if (var2 == HAS_STATIC_ACCESSORS != Modifier.isStatic(var4.getModifiers())) {
            throw PropertyException.withMessage0("msg.getter.static");
         }

         var7 = var4.getParameterTypes();
         if (var7.length == 2) {
            if (var7[0] != (class$org$mozilla$javascript$ScriptableObject != null ? class$org$mozilla$javascript$ScriptableObject : (class$org$mozilla$javascript$ScriptableObject = class$("org.mozilla.javascript.ScriptableObject")))) {
               throw PropertyException.withMessage0("msg.setter2.parms");
            }

            if (var2 == null) {
               throw PropertyException.withMessage1("msg.setter1.parms", var4.toString());
            }
         } else {
            if (var7.length != 1) {
               throw PropertyException.withMessage0("msg.setter.parms");
            }

            if (var2 != null) {
               throw PropertyException.withMessage1("msg.setter2.expected", var4.toString());
            }
         }
      }

      GetterSlot var8 = (GetterSlot)this.getSlotToSet(var1, var1.hashCode(), true);
      var8.delegateTo = var2;
      var8.getter = var3;
      var8.setter = var4;
      var8.setterReturnsValue = var4 != null && var4.getReturnType() != Void.TYPE;
      var8.value = null;
      var8.attributes = (short)var5;
      var8.flags = (byte)var6;
   }

   public static void defineProperty(Scriptable var0, String var1, Object var2, int var3) {
      if (var0 instanceof ScriptableObject) {
         ScriptableObject var4 = (ScriptableObject)var0;
         var4.defineProperty(var1, var2, var3);
      } else {
         var0.put(var1, var0, var2);
      }

   }

   public void delete(int var1) {
      this.removeSlot((String)null, var1);
   }

   public void delete(String var1) {
      this.removeSlot(var1, var1.hashCode());
   }

   public static boolean deleteProperty(Scriptable var0, int var1) {
      Scriptable var2 = getBase(var0, var1);
      if (var2 == null) {
         return true;
      } else {
         var2.delete(var1);
         return var2.get(var1, var0) == Scriptable.NOT_FOUND;
      }
   }

   public static boolean deleteProperty(Scriptable var0, String var1) {
      Scriptable var2 = getBase(var0, var1);
      if (var2 == null) {
         return true;
      } else {
         var2.delete(var1);
         return var2.get(var1, var0) == Scriptable.NOT_FOUND;
      }
   }

   public Object get(int var1, Scriptable var2) {
      Slot var3 = this.getSlot((String)null, var1, false);
      return var3 == null ? Scriptable.NOT_FOUND : var3.value;
   }

   public Object get(String var1, Scriptable var2) {
      Slot var3 = this.lastAccess;
      if (var1 == var3.stringKey && var3.wasDeleted == 0) {
         return var3.value;
      } else {
         int var4 = var1.hashCode();
         var3 = this.getSlot(var1, var4, false);
         if (var3 == null) {
            return Scriptable.NOT_FOUND;
         } else if ((var3.flags & 1) != 0) {
            GetterSlot var5 = (GetterSlot)var3;

            try {
               if (var5.delegateTo != null) {
                  Object[] var9 = new Object[]{this};
                  return var5.getter.invoke(var5.delegateTo, var9);
               } else {
                  Class var6 = var5.getter.getDeclaringClass();

                  while(!var6.isInstance(var2)) {
                     var2 = ((Scriptable)var2).getPrototype();
                     if (var2 == null) {
                        var2 = this;
                        break;
                     }
                  }

                  return var5.getter.invoke(var2, ScriptRuntime.emptyArgs);
               }
            } catch (InvocationTargetException var7) {
               throw WrappedException.wrapException(var7);
            } catch (IllegalAccessException var8) {
               throw WrappedException.wrapException(var8);
            }
         } else {
            var3.stringKey = var1;
            this.lastAccess = var3;
            return var3.value;
         }
      }
   }

   public Object[] getAllIds() {
      return this.getIds(true);
   }

   public int getAttributes(int var1, Scriptable var2) throws PropertyException {
      Slot var3 = this.getSlot((String)null, var1, false);
      if (var3 == null) {
         throw PropertyException.withMessage0("msg.prop.not.found");
      } else {
         return var3.attributes;
      }
   }

   public int getAttributes(String var1, Scriptable var2) throws PropertyException {
      Slot var3 = this.getSlot(var1, var1.hashCode(), false);
      if (var3 == null) {
         throw PropertyException.withMessage0("msg.prop.not.found");
      } else {
         return var3.attributes;
      }
   }

   private static Scriptable getBase(Scriptable var0, int var1) {
      for(Scriptable var2 = var0; var2 != null; var2 = var2.getPrototype()) {
         if (var2.has(var1, var0)) {
            return var2;
         }
      }

      return null;
   }

   private static Scriptable getBase(Scriptable var0, String var1) {
      for(Scriptable var2 = var0; var2 != null; var2 = var2.getPrototype()) {
         if (var2.has(var1, var0)) {
            return var2;
         }
      }

      return null;
   }

   public abstract String getClassName();

   public static Scriptable getClassPrototype(Scriptable var0, String var1) {
      var0 = getTopLevelScope(var0);
      Object var2 = ScriptRuntime.getTopLevelProp(var0, var1);
      if (var2 != Scriptable.NOT_FOUND && var2 instanceof Scriptable) {
         Scriptable var3 = (Scriptable)var2;
         if (!var3.has("prototype", var3)) {
            return null;
         } else {
            Object var4 = var3.get("prototype", var3);
            return !(var4 instanceof Scriptable) ? null : (Scriptable)var4;
         }
      } else {
         return null;
      }
   }

   public Object getDefaultValue(Class var1) {
      Context var3 = null;

      try {
         for(int var4 = 0; var4 < 2; ++var4) {
            Object var2;
            Object var12;
            label147: {
               label132: {
                  boolean var10000;
                  if (var1 == ScriptRuntime.StringClass) {
                     if (var4 == 0) {
                        break label132;
                     }

                     var10000 = false;
                  } else {
                     if (var4 == 1) {
                        break label132;
                     }

                     var10000 = false;
                  }

                  if (!var10000) {
                     String var5;
                     if (var1 == null) {
                        var5 = "undefined";
                     } else if (var1 == ScriptRuntime.StringClass) {
                        var5 = "string";
                     } else if (var1 == ScriptRuntime.ScriptableClass) {
                        var5 = "object";
                     } else if (var1 == ScriptRuntime.FunctionClass) {
                        var5 = "function";
                     } else if (var1 != ScriptRuntime.BooleanClass && var1 != Boolean.TYPE) {
                        if (var1 != ScriptRuntime.NumberClass && var1 != ScriptRuntime.ByteClass && var1 != Byte.TYPE && var1 != ScriptRuntime.ShortClass && var1 != Short.TYPE && var1 != ScriptRuntime.IntegerClass && var1 != Integer.TYPE && var1 != ScriptRuntime.FloatClass && var1 != Float.TYPE && var1 != ScriptRuntime.DoubleClass && var1 != Double.TYPE) {
                           throw Context.reportRuntimeError1("msg.invalid.type", var1.toString());
                        }

                        var5 = "number";
                     } else {
                        var5 = "boolean";
                     }

                     Object var6 = getProperty(this, "valueOf");
                     if (!(var6 instanceof Function)) {
                        continue;
                     }

                     Function var7 = (Function)var6;
                     Object[] var8 = new Object[]{var5};
                     if (var3 == null) {
                        var3 = Context.getContext();
                     }

                     var2 = var7.call(var3, var7.getParentScope(), this, var8);
                     break label147;
                  }
               }

               var12 = getProperty(this, "toString");
               if (!(var12 instanceof Function)) {
                  continue;
               }

               Function var11 = (Function)var12;
               if (var3 == null) {
                  var3 = Context.getContext();
               }

               var2 = var11.call(var3, var11.getParentScope(), this, ScriptRuntime.emptyArgs);
            }

            if (var2 != null && (var2 == Undefined.instance || !(var2 instanceof Scriptable) || var1 == (class$org$mozilla$javascript$Scriptable != null ? class$org$mozilla$javascript$Scriptable : (class$org$mozilla$javascript$Scriptable = class$("org.mozilla.javascript.Scriptable"))) || var1 == (class$org$mozilla$javascript$Function != null ? class$org$mozilla$javascript$Function : (class$org$mozilla$javascript$Function = class$("org.mozilla.javascript.Function"))))) {
               return var2;
            }

            if (var2 instanceof NativeJavaObject) {
               var12 = ((Wrapper)var2).unwrap();
               if (var12 instanceof String) {
                  return var12;
               }
            }
         }
      } catch (JavaScriptException var9) {
      }

      String var10 = var1 == null ? "undefined" : var1.toString();
      throw NativeGlobal.typeError1("msg.default.value", var10, this);
   }

   private static Hashtable getExclusionList() {
      if (exclusionList != null) {
         return exclusionList;
      } else {
         Hashtable var0 = new Hashtable(17);
         Method[] var1 = ScriptRuntime.FunctionClass.getMethods();

         for(int var2 = 0; var2 < var1.length; ++var2) {
            var0.put(var1[var2].getName(), Boolean.TRUE);
         }

         exclusionList = var0;
         return var0;
      }
   }

   public static Scriptable getFunctionPrototype(Scriptable var0) {
      return getClassPrototype(var0, "Function");
   }

   public Object[] getIds() {
      return this.getIds(false);
   }

   Object[] getIds(boolean var1) {
      Slot[] var2 = this.slots;
      Object[] var3 = ScriptRuntime.emptyArgs;
      if (var2 == null) {
         return var3;
      } else {
         int var4 = 0;

         for(int var5 = 0; var5 < var2.length; ++var5) {
            Slot var6 = var2[var5];
            if (var6 != null && var6 != REMOVED && (var1 || (var6.attributes & 2) == 0)) {
               if (var4 == 0) {
                  var3 = new Object[var2.length - var5];
               }

               var3[var4++] = var6.stringKey != null ? var6.stringKey : new Integer(var6.intKey);
            }
         }

         if (var4 == var3.length) {
            return var3;
         } else {
            Object[] var7 = new Object[var4];
            System.arraycopy(var3, 0, var7, 0, var4);
            return var7;
         }
      }
   }

   public static Scriptable getObjectPrototype(Scriptable var0) {
      return getClassPrototype(var0, "Object");
   }

   public Scriptable getParentScope() {
      return this.parent;
   }

   public static Object getProperty(Scriptable var0, int var1) {
      Scriptable var2 = var0;

      Object var3;
      do {
         var3 = var0.get(var1, var2);
         if (var3 != Scriptable.NOT_FOUND) {
            break;
         }

         var0 = var0.getPrototype();
      } while(var0 != null);

      return var3;
   }

   public static Object getProperty(Scriptable var0, String var1) {
      Scriptable var2 = var0;

      Object var3;
      do {
         var3 = var0.get(var1, var2);
         if (var3 != Scriptable.NOT_FOUND) {
            break;
         }

         var0 = var0.getPrototype();
      } while(var0 != null);

      return var3;
   }

   public static Object[] getPropertyIds(Scriptable var0) {
      Hashtable var1;
      Object[] var2;
      for(var1 = new Hashtable(); var0 != null; var0 = var0.getPrototype()) {
         var2 = var0.getIds();

         for(int var3 = 0; var3 < var2.length; ++var3) {
            var1.put(var2[var3], var2[var3]);
         }
      }

      var2 = new Object[var1.size()];
      Enumeration var5 = var1.elements();

      for(int var4 = 0; var5.hasMoreElements(); var2[var4++] = var5.nextElement()) {
      }

      return var2;
   }

   public Scriptable getPrototype() {
      return this.prototype;
   }

   private Slot getSlot(String var1, int var2, boolean var3) {
      Slot[] var4 = this.slots;
      if (var4 == null) {
         return null;
      } else {
         int var5 = (var2 & Integer.MAX_VALUE) % var4.length;
         int var6 = var5;

         do {
            Slot var7 = var4[var6];
            if (var7 == null) {
               return null;
            }

            if (var7 != REMOVED && var7.intKey == var2 && (var7.stringKey == var1 || var1 != null && var1.equals(var7.stringKey))) {
               if (var3 && (var7.attributes & 4) == 0) {
                  var7.wasDeleted = 1;
                  var4[var6] = REMOVED;
                  --this.count;
                  if (var7 == this.lastAccess) {
                     this.lastAccess = REMOVED;
                  }
               }

               return var7;
            }

            ++var6;
            if (var6 == var4.length) {
               var6 = 0;
            }
         } while(var6 != var5);

         return null;
      }
   }

   private Slot getSlotToSet(String var1, int var2, boolean var3) {
      if (this.slots == null) {
         this.slots = new Slot[5];
      }

      int var4 = (var2 & Integer.MAX_VALUE) % this.slots.length;
      boolean var5 = false;
      int var6 = var4;

      do {
         Slot var7 = this.slots[var6];
         if (var7 == null) {
            return this.addSlot(var1, var2, var3);
         }

         if (var7 == REMOVED) {
            var5 = true;
         } else if (var7.intKey == var2 && (var7.stringKey == var1 || var1 != null && var1.equals(var7.stringKey))) {
            return var7;
         }

         ++var6;
         if (var6 == this.slots.length) {
            var6 = 0;
         }
      } while(var6 != var4);

      if (var5) {
         return this.addSlot(var1, var2, var3);
      } else {
         throw new RuntimeException("Hashtable internal error");
      }
   }

   public static Scriptable getTopLevelScope(Scriptable var0) {
      Scriptable var1 = var0;

      do {
         var0 = var1;
         var1 = var1.getParentScope();
      } while(var1 != null);

      return var0;
   }

   private synchronized void grow() {
      Slot[] var1 = new Slot[this.slots.length * 2 + 1];

      for(int var2 = this.slots.length - 1; var2 >= 0; --var2) {
         Slot var3 = this.slots[var2];
         if (var3 != null && var3 != REMOVED) {
            int var4 = (var3.intKey & Integer.MAX_VALUE) % var1.length;

            while(var1[var4] != null) {
               ++var4;
               if (var4 == var1.length) {
                  var4 = 0;
               }
            }

            var1[var4] = var3;
         }
      }

      this.slots = var1;
   }

   public boolean has(int var1, Scriptable var2) {
      return this.getSlot((String)null, var1, false) != null;
   }

   public boolean has(String var1, Scriptable var2) {
      return this.getSlot(var1, var1.hashCode(), false) != null;
   }

   public boolean hasInstance(Scriptable var1) {
      return ScriptRuntime.jsDelegatesTo(var1, this);
   }

   public static boolean hasProperty(Scriptable var0, int var1) {
      Scriptable var2 = var0;

      while(!var0.has(var1, var2)) {
         var0 = var0.getPrototype();
         if (var0 == null) {
            return false;
         }
      }

      return true;
   }

   public static boolean hasProperty(Scriptable var0, String var1) {
      Scriptable var2 = var0;

      while(!var0.has(var1, var2)) {
         var0 = var0.getPrototype();
         if (var0 == null) {
            return false;
         }
      }

      return true;
   }

   public boolean isSealed() {
      return this.count == -1;
   }

   public void put(int var1, Scriptable var2, Object var3) {
      Slot var4 = this.getSlot((String)null, var1, false);
      if (var4 == null) {
         if (var2 != this) {
            var2.put(var1, var2, var3);
            return;
         }

         var4 = this.getSlotToSet((String)null, var1, false);
      }

      if ((var4.attributes & 1) == 0) {
         if (this == var2) {
            var4.value = var3;
         } else {
            var2.put(var1, var2, var3);
         }

      }
   }

   public void put(String var1, Scriptable var2, Object var3) {
      int var4 = var1.hashCode();
      Slot var5 = this.getSlot(var1, var4, false);
      if (var5 == null) {
         if (var2 != this) {
            ((Scriptable)var2).put(var1, (Scriptable)var2, var3);
            return;
         }

         var5 = this.getSlotToSet(var1, var4, false);
      }

      if ((var5.attributes & 1) == 0) {
         if ((var5.flags & 2) != 0) {
            GetterSlot var6 = (GetterSlot)var5;

            try {
               Class[] var7 = var6.setter.getParameterTypes();
               Class var8 = var7[var7.length - 1];
               Object var9 = FunctionObject.convertArg((Scriptable)var2, var3, var8);
               Object[] var10;
               if (var6.delegateTo != null) {
                  var10 = new Object[]{this, var9};
                  Object var15 = var6.setter.invoke(var6.delegateTo, var10);
                  if (var6.setterReturnsValue) {
                     var5.value = var15;
                     if (!(var15 instanceof Method)) {
                        var5.flags = 0;
                     }
                  }

               } else {
                  var10 = new Object[]{var9};
                  Class var11 = var6.setter.getDeclaringClass();

                  while(!var11.isInstance(var2)) {
                     var2 = ((Scriptable)var2).getPrototype();
                     if (var2 == null) {
                        var2 = this;
                        break;
                     }
                  }

                  Object var12 = var6.setter.invoke(var2, var10);
                  if (var6.setterReturnsValue) {
                     var5.value = var12;
                     if (!(var12 instanceof Method)) {
                        var5.flags = 0;
                     }
                  }

               }
            } catch (InvocationTargetException var13) {
               throw WrappedException.wrapException(var13);
            } catch (IllegalAccessException var14) {
               throw WrappedException.wrapException(var14);
            }
         } else {
            if (this == var2) {
               var5.value = var3;
               var5.stringKey = var1;
               this.lastAccess = var5;
            } else {
               ((Scriptable)var2).put(var1, (Scriptable)var2, var3);
            }

         }
      }
   }

   public static void putProperty(Scriptable var0, int var1, Object var2) {
      Scriptable var3 = getBase(var0, var1);
      if (var3 == null) {
         var3 = var0;
      }

      var3.put(var1, var0, var2);
   }

   public static void putProperty(Scriptable var0, String var1, Object var2) {
      Scriptable var3 = getBase(var0, var1);
      if (var3 == null) {
         var3 = var0;
      }

      var3.put(var1, var0, var2);
   }

   private synchronized void removeSlot(String var1, int var2) {
      if (this.count == -1) {
         throw Context.reportRuntimeError0("msg.remove.sealed");
      } else {
         this.getSlot(var1, var2, true);
      }
   }

   public void sealObject() {
      this.count = -1;
   }

   public void setAttributes(int var1, Scriptable var2, int var3) throws PropertyException {
      Slot var4 = this.getSlot((String)null, var1, false);
      if (var4 == null) {
         throw PropertyException.withMessage0("msg.prop.not.found");
      } else {
         var4.attributes = (short)var3;
      }
   }

   public void setAttributes(String var1, Scriptable var2, int var3) throws PropertyException {
      boolean var4 = true;
      var3 &= 7;
      Slot var5 = this.getSlot(var1, var1.hashCode(), false);
      if (var5 == null) {
         throw PropertyException.withMessage0("msg.prop.not.found");
      } else {
         var5.attributes = (short)var3;
      }
   }

   public void setParentScope(Scriptable var1) {
      this.parent = var1;
   }

   public void setPrototype(Scriptable var1) {
      this.prototype = var1;
   }

   private static class Slot {
      static final int HAS_GETTER = 1;
      static final int HAS_SETTER = 2;
      int intKey;
      String stringKey;
      Object value;
      short attributes;
      byte flags;
      byte wasDeleted;

      Slot() {
      }
   }

   private static class GetterSlot extends Slot {
      Object delegateTo;
      Method getter;
      Method setter;
      boolean setterReturnsValue;

      GetterSlot() {
      }
   }
}
