package weblogic.common.internal;

import java.util.EmptyStackException;
import java.util.Stack;
import weblogic.utils.collections.ConcurrentHashMap;

public final class ObjectFactory {
   private static final boolean VERBOSE = false;
   private static final ConcurrentHashMap objBags = new ConcurrentHashMap();

   private static final Stack bag(String type) {
      Stack st = (Stack)objBags.get(type);
      if (st == null) {
         st = new Stack();
         objBags.put(type, st);
      }

      return st;
   }

   public static Manufacturable get(String type) {
      Stack st = bag(type);

      Manufacturable o;
      try {
         o = (Manufacturable)st.pop();
      } catch (EmptyStackException var10) {
         try {
            o = (Manufacturable)Class.forName(type).newInstance();
         } catch (ClassCastException var5) {
            throw new Error("Class " + type + " must be implement Manufacturable (or some subInterface). [" + var5 + "]");
         } catch (ClassNotFoundException var6) {
            throw new Error("Class " + type + " was not found. [" + var6 + "]");
         } catch (IllegalAccessException var7) {
            throw new Error("Class " + type + " must be declared \"public\" and have a \"public\" constructor. [" + var7 + "]");
         } catch (InstantiationException var8) {
            throw new Error("Class " + type + " could not be instantiated. Is it abstract? [" + var8 + "]");
         } catch (NoSuchMethodError var9) {
            throw new Error("Class " + type + " must have a default constructor. [" + var9 + "].");
         }
      }

      o.initialize();
      return o;
   }

   public static void put(Manufacturable o) {
      String type = o.getClass().getName();
      o.destroy();
      Stack st = bag(type);
      st.push(o);
   }
}
