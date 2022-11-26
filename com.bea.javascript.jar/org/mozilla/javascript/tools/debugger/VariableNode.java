package org.mozilla.javascript.tools.debugger;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Hashtable;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.NativeCall;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.Undefined;

class VariableNode {
   Scriptable scope;
   String name;
   int index;
   Object[] children;
   static final Object[] empty = new Object[0];
   static Scriptable[] builtin;

   public VariableNode(Scriptable var1, int var2) {
      this.scope = var1;
      this.name = null;
      this.index = var2;
   }

   public VariableNode(Scriptable var1, String var2) {
      this.scope = var1;
      this.name = var2;
   }

   protected Object[] getChildren() {
      if (this.children != null) {
         return this.children;
      } else {
         Context var1 = Context.enter();

         try {
            Object var5 = this.getObject();
            Object[] var2;
            if (var5 == null) {
               var2 = this.children = empty;
               return var2;
            }

            if (var5 == Scriptable.NOT_FOUND || var5 == Undefined.instance) {
               var2 = this.children = empty;
               return var2;
            }

            if (var5 instanceof Scriptable) {
               Scriptable var6 = (Scriptable)var5;
               Scriptable var7 = var6.getPrototype();
               Scriptable var8 = var6.getParentScope();
               if (var5 instanceof NativeCall) {
                  if (this.name != null && this.name.equals("this")) {
                     var8 = null;
                  } else if (!(var8 instanceof NativeCall)) {
                     var8 = null;
                  }
               }

               int var9;
               if (var7 != null) {
                  if (builtin == null) {
                     builtin = new Scriptable[6];
                     builtin[0] = ScriptableObject.getObjectPrototype(var6);
                     builtin[1] = ScriptableObject.getFunctionPrototype(var6);
                     builtin[2] = ScriptableObject.getClassPrototype(var6, "String");
                     builtin[3] = ScriptableObject.getClassPrototype(var6, "Boolean");
                     builtin[4] = ScriptableObject.getClassPrototype(var6, "Array");
                     builtin[5] = ScriptableObject.getClassPrototype(var6, "Number");
                  }

                  for(var9 = 0; var9 < builtin.length; ++var9) {
                     if (var7 == builtin[var9]) {
                        var7 = null;
                        break;
                     }
                  }
               }

               if (var6.has(0, var6)) {
                  var9 = 0;

                  try {
                     Scriptable var10 = var6;
                     Scriptable var11 = var6;
                     Object var12 = Undefined.instance;

                     do {
                        if (var11.has("length", var10)) {
                           var12 = var11.get("length", var10);
                           if (var12 != Scriptable.NOT_FOUND) {
                              break;
                           }
                        }

                        var11 = var11.getPrototype();
                     } while(var11 != null);

                     if (var12 instanceof Number) {
                        var9 = ((Number)var12).intValue();
                     }
                  } catch (Exception var19) {
                  }

                  if (var8 != null) {
                     ++var9;
                  }

                  if (var7 != null) {
                     ++var9;
                  }

                  this.children = new VariableNode[var9];
                  int var22 = 0;
                  int var23 = 0;
                  if (var7 != null) {
                     this.children[var22++] = new VariableNode(var6, "__proto__");
                     ++var23;
                  }

                  if (var8 != null) {
                     this.children[var22++] = new VariableNode(var6, "__parent__");
                     ++var23;
                  }

                  while(var22 < var9) {
                     this.children[var22] = new VariableNode(var6, var22 - var23);
                     ++var22;
                  }
               } else {
                  var9 = 0;
                  Hashtable var24 = new Hashtable();
                  Object[] var26 = var6.getIds();
                  if (var7 != null) {
                     var24.put("__proto__", "__proto__");
                  }

                  if (var8 != null) {
                     var24.put("__parent__", "__parent__");
                  }

                  if (var26.length > 0) {
                     for(int var25 = 0; var25 < var26.length; ++var25) {
                        var24.put(var26[var25], var26[var25]);
                     }
                  }

                  var26 = new Object[var24.size()];
                  Enumeration var27 = var24.keys();

                  for(int var13 = 0; var27.hasMoreElements(); var26[var13++] = var27.nextElement().toString()) {
                  }

                  if (var26 != null && var26.length > 0) {
                     Arrays.sort(var26, new Comparator() {
                        public int compare(Object var1, Object var2) {
                           return var1.toString().compareToIgnoreCase(var2.toString());
                        }
                     });
                     var9 = var26.length;
                  }

                  this.children = new VariableNode[var9];

                  for(int var14 = 0; var14 < var9; ++var14) {
                     Object var15 = var26[var14];
                     this.children[var14] = new VariableNode(var6, var15.toString());
                  }
               }
            }
         } catch (Exception var20) {
            var20.printStackTrace();
         } finally {
            Context.exit();
         }

         return this.children;
      }
   }

   public Object getObject() {
      try {
         if (this.scope == null) {
            return null;
         } else {
            Object var1;
            if (this.name != null) {
               if (this.name.equals("this")) {
                  return this.scope;
               } else {
                  if (this.name.equals("__proto__")) {
                     var1 = this.scope.getPrototype();
                  } else if (this.name.equals("__parent__")) {
                     var1 = this.scope.getParentScope();
                  } else {
                     var1 = this.scope.get(this.name, this.scope);
                  }

                  if (var1 == Scriptable.NOT_FOUND) {
                     var1 = Undefined.instance;
                  }

                  return var1;
               }
            } else {
               var1 = this.scope.get(this.index, this.scope);
               if (var1 == Scriptable.NOT_FOUND) {
                  var1 = Undefined.instance;
               }

               return var1;
            }
         }
      } catch (Exception var2) {
         return "undefined";
      }
   }

   public String toString() {
      return this.name != null ? this.name : "[" + this.index + "]";
   }
}
