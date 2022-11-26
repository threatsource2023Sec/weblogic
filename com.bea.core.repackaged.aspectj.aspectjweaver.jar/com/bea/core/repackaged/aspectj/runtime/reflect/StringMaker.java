package com.bea.core.repackaged.aspectj.runtime.reflect;

import java.lang.reflect.Modifier;

class StringMaker {
   boolean shortTypeNames = true;
   boolean includeArgs = true;
   boolean includeThrows = false;
   boolean includeModifiers = false;
   boolean shortPrimaryTypeNames = false;
   boolean includeJoinPointTypeName = true;
   boolean includeEnclosingPoint = true;
   boolean shortKindName = true;
   int cacheOffset;
   static StringMaker shortStringMaker = new StringMaker();
   static StringMaker middleStringMaker;
   static StringMaker longStringMaker;

   String makeKindName(String name) {
      int dash = name.lastIndexOf(45);
      return dash == -1 ? name : name.substring(dash + 1);
   }

   String makeModifiersString(int modifiers) {
      if (!this.includeModifiers) {
         return "";
      } else {
         String str = Modifier.toString(modifiers);
         return str.length() == 0 ? "" : str + " ";
      }
   }

   String stripPackageName(String name) {
      int dot = name.lastIndexOf(46);
      return dot == -1 ? name : name.substring(dot + 1);
   }

   String makeTypeName(Class type, String typeName, boolean shortName) {
      if (type == null) {
         return "ANONYMOUS";
      } else if (type.isArray()) {
         Class componentType = type.getComponentType();
         return this.makeTypeName(componentType, componentType.getName(), shortName) + "[]";
      } else {
         return shortName ? this.stripPackageName(typeName).replace('$', '.') : typeName.replace('$', '.');
      }
   }

   public String makeTypeName(Class type) {
      return this.makeTypeName(type, type.getName(), this.shortTypeNames);
   }

   public String makePrimaryTypeName(Class type, String typeName) {
      return this.makeTypeName(type, typeName, this.shortPrimaryTypeNames);
   }

   public void addTypeNames(StringBuffer buf, Class[] types) {
      for(int i = 0; i < types.length; ++i) {
         if (i > 0) {
            buf.append(", ");
         }

         buf.append(this.makeTypeName(types[i]));
      }

   }

   public void addSignature(StringBuffer buf, Class[] types) {
      if (types != null) {
         if (!this.includeArgs) {
            if (types.length == 0) {
               buf.append("()");
            } else {
               buf.append("(..)");
            }
         } else {
            buf.append("(");
            this.addTypeNames(buf, types);
            buf.append(")");
         }
      }
   }

   public void addThrows(StringBuffer buf, Class[] types) {
      if (this.includeThrows && types != null && types.length != 0) {
         buf.append(" throws ");
         this.addTypeNames(buf, types);
      }
   }

   static {
      shortStringMaker.shortTypeNames = true;
      shortStringMaker.includeArgs = false;
      shortStringMaker.includeThrows = false;
      shortStringMaker.includeModifiers = false;
      shortStringMaker.shortPrimaryTypeNames = true;
      shortStringMaker.includeJoinPointTypeName = false;
      shortStringMaker.includeEnclosingPoint = false;
      shortStringMaker.cacheOffset = 0;
      middleStringMaker = new StringMaker();
      middleStringMaker.shortTypeNames = true;
      middleStringMaker.includeArgs = true;
      middleStringMaker.includeThrows = false;
      middleStringMaker.includeModifiers = false;
      middleStringMaker.shortPrimaryTypeNames = false;
      shortStringMaker.cacheOffset = 1;
      longStringMaker = new StringMaker();
      longStringMaker.shortTypeNames = false;
      longStringMaker.includeArgs = true;
      longStringMaker.includeThrows = false;
      longStringMaker.includeModifiers = true;
      longStringMaker.shortPrimaryTypeNames = false;
      longStringMaker.shortKindName = false;
      longStringMaker.cacheOffset = 2;
   }
}
