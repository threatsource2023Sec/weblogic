package org.mozilla.javascript;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Enumeration;
import java.util.Hashtable;

class JavaMembers {
   static Hashtable classTable = new Hashtable();
   private Class cl;
   private Hashtable members = new Hashtable(23);
   private Hashtable fieldAndMethods;
   private Hashtable staticMembers = new Hashtable(7);
   private Hashtable staticFieldAndMethods;
   private Constructor[] ctors;

   JavaMembers(Scriptable var1, Class var2) {
      this.cl = var2;
      this.reflect(var1, var2);
   }

   Member findExplicitFunction(String var1, boolean var2) {
      Hashtable var3 = var2 ? this.staticMembers : this.members;
      int var4 = var1.indexOf(40);
      Object var5 = null;
      NativeJavaMethod var6 = null;
      boolean var7 = var2 && var4 == 0;
      if (var7) {
         var5 = this.ctors;
      } else if (var4 > 0) {
         String var8 = var1.substring(0, var4);
         Object var9 = var3.get(var8);
         if (!var2 && var9 == null) {
            var9 = this.staticMembers.get(var8);
         }

         if (var9 != null && var9 instanceof NativeJavaMethod) {
            var6 = (NativeJavaMethod)var9;
            var5 = var6.getMethods();
         }
      }

      if (var5 != null) {
         for(int var10 = 0; var10 < ((Object[])var5).length; ++var10) {
            String var11 = NativeJavaMethod.signature((Member)((Object[])var5)[var10]);
            if (var1.equals(var11)) {
               return (Member)((Object[])var5)[var10];
            }
         }
      }

      return null;
   }

   Object get(Scriptable var1, String var2, Object var3, boolean var4) {
      Hashtable var5 = var4 ? this.staticMembers : this.members;
      Object var6 = var5.get(var2);
      if (!var4 && var6 == null) {
         var6 = this.staticMembers.get(var2);
      }

      if (var6 == null) {
         var6 = this.getExplicitFunction(var1, var2, var3, var4);
         if (var6 == null) {
            return Scriptable.NOT_FOUND;
         }
      }

      if (var6 instanceof Scriptable) {
         return var6;
      } else {
         Object var7;
         Class var8;
         try {
            if (var6 instanceof BeanProperty) {
               BeanProperty var9 = (BeanProperty)var6;
               var7 = var9.getter.invoke(var3, ScriptRuntime.emptyArgs);
               var8 = var9.getter.getReturnType();
            } else {
               Field var12 = (Field)var6;
               var7 = var12.get(var4 ? null : var3);
               var8 = var12.getType();
            }
         } catch (IllegalAccessException var10) {
            throw new RuntimeException("unexpected IllegalAccessException accessing Java field");
         } catch (InvocationTargetException var11) {
            throw WrappedException.wrapException(JavaScriptException.wrapException(var1, var11));
         }

         var1 = ScriptableObject.getTopLevelScope(var1);
         return NativeJavaObject.wrap(var1, var7, var8);
      }
   }

   Constructor[] getConstructors() {
      return this.ctors;
   }

   Object getExplicitFunction(Scriptable var1, String var2, Object var3, boolean var4) {
      Hashtable var5 = var4 ? this.staticMembers : this.members;
      Object var6 = null;
      Member var7 = this.findExplicitFunction(var2, var4);
      if (var7 != null) {
         Scriptable var8 = ScriptableObject.getFunctionPrototype(var1);
         if (var7 instanceof Constructor) {
            NativeJavaConstructor var9 = new NativeJavaConstructor((Constructor)var7);
            var9.setPrototype(var8);
            var6 = var9;
            var5.put(var2, var9);
         } else {
            String var11 = var7.getName();
            var6 = var5.get(var11);
            if (var6 instanceof NativeJavaMethod && ((NativeJavaMethod)var6).getMethods().length > 1) {
               NativeJavaMethod var10 = new NativeJavaMethod((Method)var7, var2);
               var10.setPrototype(var8);
               var5.put(var2, var10);
               var6 = var10;
            }
         }
      }

      return var6;
   }

   Hashtable getFieldAndMethodsObjects(Scriptable var1, Object var2, boolean var3) {
      Hashtable var4 = var3 ? this.staticFieldAndMethods : this.fieldAndMethods;
      if (var4 == null) {
         return null;
      } else {
         int var5 = var4.size();
         Hashtable var6 = new Hashtable(Math.max(var5, 1));
         Enumeration var7 = var4.elements();

         while(var5-- > 0) {
            FieldAndMethods var8 = (FieldAndMethods)var7.nextElement();
            var8 = (FieldAndMethods)var8.clone();
            var8.setJavaObject(var2);
            var6.put(var8.getName(), var8);
         }

         return var6;
      }
   }

   Hashtable getFieldAndMethodsTable(boolean var1) {
      Hashtable var2 = var1 ? this.staticFieldAndMethods : this.fieldAndMethods;
      if (var2 == null) {
         var2 = new Hashtable(11);
         if (var1) {
            this.staticFieldAndMethods = var2;
         } else {
            this.fieldAndMethods = var2;
         }
      }

      return var2;
   }

   Object[] getIds(boolean var1) {
      Hashtable var2 = var1 ? this.staticMembers : this.members;
      int var3 = var2.size();
      Object[] var4 = new Object[var3];
      Enumeration var5 = var2.keys();

      for(int var6 = 0; var6 < var3; ++var6) {
         var4[var6] = var5.nextElement();
      }

      return var4;
   }

   Class getReflectedClass() {
      return this.cl;
   }

   boolean has(String var1, boolean var2) {
      Hashtable var3 = var2 ? this.staticMembers : this.members;
      Object var4 = var3.get(var1);
      if (var4 != null) {
         return true;
      } else {
         Member var5 = this.findExplicitFunction(var1, var2);
         return var5 != null;
      }
   }

   static JavaMembers lookupClass(Scriptable var0, Class var1, Class var2) {
      Class var3 = var1;
      Hashtable var4 = classTable;
      JavaMembers var5 = (JavaMembers)var4.get(var1);
      if (var5 != null) {
         return var5;
      } else {
         if (var2 != null && var2 != var1 && !Modifier.isPublic(var1.getModifiers()) && Modifier.isPublic(var2.getModifiers())) {
            var3 = var2;

            for(Class var6 = var1; var6 != null && var6 != ScriptRuntime.ObjectClass; var6 = var6.getSuperclass()) {
               if (Modifier.isPublic(var6.getModifiers())) {
                  var3 = var6;
                  break;
               }
            }
         }

         try {
            var5 = new JavaMembers(var0, var3);
         } catch (SecurityException var7) {
            if (var3 == var2) {
               throw var7;
            }

            var5 = new JavaMembers(var0, var2);
         }

         if (Context.isCachingEnabled) {
            var4.put(var3, var5);
         }

         return var5;
      }
   }

   void makeBeanProperties(Scriptable var1, boolean var2) {
      Hashtable var3 = var2 ? this.staticMembers : this.members;
      Hashtable var4 = new Hashtable();
      Enumeration var5 = var3.keys();

      while(true) {
         String var9;
         String var10;
         Object var11;
         Class[] var13;
         Method[] var14;
         Class var15;
         do {
            do {
               do {
                  do {
                     do {
                        do {
                           do {
                              String var6;
                              do {
                                 do {
                                    boolean var7;
                                    boolean var8;
                                    do {
                                       if (!var5.hasMoreElements()) {
                                          Enumeration var22 = var4.keys();

                                          while(var22.hasMoreElements()) {
                                             String var23 = (String)var22.nextElement();
                                             Object var24 = var4.get(var23);
                                             var3.put(var23, var24);
                                          }

                                          return;
                                       }

                                       var6 = (String)var5.nextElement();
                                       var7 = var6.startsWith("get");
                                       var8 = var6.startsWith("is");
                                    } while(!var7 && !var8);

                                    var9 = var6.substring(var7 ? 3 : 2);
                                 } while(var9.length() == 0);

                                 var10 = var9;
                                 if (Character.isUpperCase(var9.charAt(0))) {
                                    if (var9.length() == 1) {
                                       var10 = var9.substring(0, 1).toLowerCase();
                                    } else if (!Character.isUpperCase(var9.charAt(1))) {
                                       var10 = Character.toLowerCase(var9.charAt(0)) + var9.substring(1);
                                    }
                                 }
                              } while(var3.containsKey(var10));

                              var11 = var3.get(var6);
                           } while(!(var11 instanceof NativeJavaMethod));

                           NativeJavaMethod var12 = (NativeJavaMethod)var11;
                           var14 = var12.getMethods();
                        } while(var14 == null);
                     } while(var14.length != 1);
                  } while((var15 = var14[0].getReturnType()) == null);
               } while((var13 = var14[0].getParameterTypes()) == null);
            } while(var13.length != 0);
         } while(var2 && !Modifier.isStatic(var14[0].getModifiers()));

         Method var16 = null;
         String var17 = "set" + var9;
         if (var3.containsKey(var17)) {
            var11 = var3.get(var17);
            if (var11 instanceof NativeJavaMethod) {
               NativeJavaMethod var18 = (NativeJavaMethod)var11;
               Method[] var19 = var18.getMethods();

               for(int var20 = 1; var20 <= 2 && var16 == null; ++var20) {
                  for(int var21 = 0; var21 < var19.length; ++var21) {
                     if (var19[var21].getReturnType() == Void.TYPE && (!var2 || Modifier.isStatic(var19[var21].getModifiers())) && (var13 = var19[var21].getParameterTypes()) != null && var13.length == 1 && (var20 == 1 && var13[0] == var15 || var20 == 2 && var13[0].isAssignableFrom(var15))) {
                        var16 = var19[var21];
                        break;
                     }
                  }
               }
            }
         }

         BeanProperty var25 = new BeanProperty(var14[0], var16);
         var4.put(var10, var25);
      }
   }

   public void put(Scriptable var1, String var2, Object var3, Object var4, boolean var5) {
      Hashtable var6 = var5 ? this.staticMembers : this.members;
      Object var7 = var6.get(var2);
      if (!var5 && var7 == null) {
         var7 = this.staticMembers.get(var2);
      }

      if (var7 == null) {
         throw this.reportMemberNotFound(var2);
      } else {
         FieldAndMethods var8;
         if (var7 instanceof FieldAndMethods) {
            var8 = (FieldAndMethods)var6.get(var2);
            var7 = var8.getField();
         }

         if (var7 instanceof BeanProperty) {
            try {
               Method var16 = ((BeanProperty)var7).setter;
               if (var16 == null) {
                  throw this.reportMemberNotFound(var2);
               }

               Class[] var9 = var16.getParameterTypes();
               Object[] var10 = new Object[]{NativeJavaObject.coerceType(var9[0], var4)};
               var16.invoke(var3, var10);
            } catch (IllegalAccessException var14) {
               throw new RuntimeException("unexpected IllegalAccessException accessing Java field");
            } catch (InvocationTargetException var15) {
               throw WrappedException.wrapException(JavaScriptException.wrapException(var1, var15));
            }
         } else {
            var8 = null;

            try {
               Field var17 = (Field)var7;
               if (var17 == null) {
                  throw Context.reportRuntimeError1("msg.java.internal.private", var2);
               }

               var17.set(var3, NativeJavaObject.coerceType(var17.getType(), var4));
            } catch (ClassCastException var11) {
               throw Context.reportRuntimeError1("msg.java.method.assign", var2);
            } catch (IllegalAccessException var12) {
               throw new RuntimeException("unexpected IllegalAccessException accessing Java field");
            } catch (IllegalArgumentException var13) {
               throw Context.reportRuntimeError3("msg.java.internal.field.type", var4.getClass().getName(), var8, var3.getClass().getName());
            }
         }

      }
   }

   void reflect(Scriptable var1, Class var2) {
      Method[] var3 = var2.getMethods();

      for(int var4 = 0; var4 < var3.length; ++var4) {
         this.reflectMethod(var1, var3[var4]);
      }

      Field[] var5 = var2.getFields();

      for(int var6 = 0; var6 < var5.length; ++var6) {
         this.reflectField(var1, var5[var6]);
      }

      this.makeBeanProperties(var1, false);
      this.makeBeanProperties(var1, true);
      this.ctors = var2.getConstructors();
   }

   void reflectField(Scriptable var1, Field var2) {
      int var3 = var2.getModifiers();
      if (Modifier.isPublic(var3)) {
         boolean var4 = Modifier.isStatic(var3);
         Hashtable var5 = var4 ? this.staticMembers : this.members;
         String var6 = var2.getName();
         Object var7 = var5.get(var6);
         if (var7 != null) {
            if (var7 instanceof NativeJavaMethod) {
               NativeJavaMethod var10 = (NativeJavaMethod)var7;
               FieldAndMethods var9 = new FieldAndMethods(var10.getMethods(), var2, (String)null);
               var9.setPrototype(ScriptableObject.getFunctionPrototype(var1));
               this.getFieldAndMethodsTable(var4).put(var6, var9);
               var5.put(var6, var9);
            } else if (var7 instanceof Field) {
               Field var8 = (Field)var7;
               if (var8.getDeclaringClass().isAssignableFrom(var2.getDeclaringClass())) {
                  var5.put(var6, var2);
               }

            } else {
               throw new RuntimeException("unknown member type");
            }
         } else {
            var5.put(var6, var2);
         }
      }
   }

   void reflectMethod(Scriptable var1, Method var2) {
      int var3 = var2.getModifiers();
      if (Modifier.isPublic(var3)) {
         boolean var4 = Modifier.isStatic(var3);
         Hashtable var5 = var4 ? this.staticMembers : this.members;
         String var6 = var2.getName();
         NativeJavaMethod var7 = (NativeJavaMethod)var5.get(var6);
         if (var7 == null) {
            var7 = new NativeJavaMethod();
            if (var1 != null) {
               var7.setPrototype(ScriptableObject.getFunctionPrototype(var1));
            }

            var5.put(var6, var7);
            var7.add(var2);
         } else {
            var7.add(var2);
         }

      }
   }

   RuntimeException reportMemberNotFound(String var1) {
      return Context.reportRuntimeError2("msg.java.member.not.found", this.cl.getName(), var1);
   }
}
