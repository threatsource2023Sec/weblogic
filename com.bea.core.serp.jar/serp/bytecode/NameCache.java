package serp.bytecode;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class NameCache {
   static final Object[][] _codes;
   private final Map _internal = new HashMap();
   private final Map _internalDescriptor = new HashMap();
   private final Map _external = new HashMap();
   private final Map _externalHuman = new HashMap();

   public String getInternalForm(String className, boolean descriptor) {
      if (className != null && className.length() != 0) {
         Map cache = descriptor ? this._internalDescriptor : this._internal;
         String cached = (String)cache.get(className);
         if (cached != null) {
            return cached;
         } else {
            String ret = this.getInternalFormInternal(className, descriptor);
            cache.put(className, ret);
            return ret;
         }
      } else {
         return className;
      }
   }

   private String getInternalFormInternal(String cls, boolean descriptor) {
      StringBuilder prefix = new StringBuilder();

      while(true) {
         while(!cls.endsWith("[]")) {
            if (!cls.startsWith("[")) {
               for(int i = 0; i < _codes.length; ++i) {
                  if (cls.equals(_codes[i][1].toString()) || cls.equals(_codes[i][0].toString())) {
                     return prefix.append(_codes[i][1]).toString();
                  }
               }

               if (cls.startsWith("L") && cls.endsWith(";")) {
                  cls = cls.substring(1, cls.length() - 1);
               }

               cls = cls.replace('.', '/');
               if ((descriptor || prefix.length() > 0) && cls.charAt(0) != '(') {
                  return prefix.append("L").append(cls).append(";").toString();
               }

               return prefix.append(cls).toString();
            }

            prefix.append("[");
            cls = cls.substring(1);
         }

         prefix.append("[");
         cls = cls.substring(0, cls.length() - 2);
      }
   }

   public String getExternalForm(String internalName, boolean humanReadable) {
      if (internalName != null && internalName.length() != 0) {
         Map cache = humanReadable ? this._externalHuman : this._external;
         String cached = (String)cache.get(internalName);
         if (cached != null) {
            return cached;
         } else {
            String ret = this.getExternalFormInternal(internalName, humanReadable);
            cache.put(internalName, ret);
            return ret;
         }
      } else {
         return internalName;
      }
   }

   private String getExternalFormInternal(String intern, boolean humanReadable) {
      if (!humanReadable) {
         for(int i = 0; i < _codes.length; ++i) {
            if (intern.equals(_codes[i][1].toString())) {
               return _codes[i][0].toString();
            }

            if (intern.equals(_codes[i][0].toString())) {
               return intern;
            }
         }

         intern = this.getInternalForm(intern, false);
         return intern.replace('/', '.');
      } else {
         StringBuilder postfix = new StringBuilder(2);

         while(intern.startsWith("[")) {
            intern = intern.substring(1);
            postfix.append("[]");
         }

         if (intern.endsWith(";")) {
            intern = intern.substring(1, intern.length() - 1);
         }

         for(int i = 0; i < _codes.length; ++i) {
            if (intern.equals(_codes[i][1].toString())) {
               return _codes[i][0].toString() + postfix;
            }
         }

         return intern.replace('/', '.') + postfix;
      }
   }

   public String getDescriptor(String returnType, String[] paramTypes) {
      StringBuilder buf = new StringBuilder();
      buf.append("(");
      if (paramTypes != null) {
         for(int i = 0; i < paramTypes.length; ++i) {
            if (paramTypes[i] == null) {
               throw new NullPointerException("paramTypes[" + i + "] = null");
            }

            buf.append(this.getInternalForm(paramTypes[i], true));
         }
      }

      buf.append(")");
      if (returnType == null) {
         throw new NullPointerException("returnType = null");
      } else {
         buf.append(this.getInternalForm(returnType, true));
         return buf.toString();
      }
   }

   public String getDescriptorReturnName(String descriptor) {
      int index = descriptor.indexOf(41);
      return index == -1 ? "" : descriptor.substring(descriptor.indexOf(41) + 1);
   }

   public String[] getDescriptorParamNames(String descriptor) {
      if (descriptor != null && descriptor.length() != 0) {
         int index = descriptor.indexOf(41);
         if (index == -1) {
            return new String[0];
         } else {
            descriptor = descriptor.substring(1, index);

            LinkedList tokens;
            for(tokens = new LinkedList(); descriptor.length() > 0; descriptor = descriptor.substring(index + 1)) {
               for(index = 0; !Character.isLetter(descriptor.charAt(index)); ++index) {
               }

               if (descriptor.charAt(index) == 'L') {
                  index = descriptor.indexOf(59);
               }

               tokens.add(descriptor.substring(0, index + 1));
            }

            return (String[])((String[])tokens.toArray(new String[tokens.size()]));
         }
      } else {
         return new String[0];
      }
   }

   public String getComponentName(String name) {
      if (name != null && name.startsWith("[")) {
         name = name.substring(1);
         if (!name.startsWith("[") && name.endsWith(";")) {
            name = name.substring(1, name.length() - 1);
         }

         return this.getExternalForm(name, false);
      } else {
         return null;
      }
   }

   public void clear() {
      this._internal.clear();
      this._internalDescriptor.clear();
      this._external.clear();
      this._externalHuman.clear();
   }

   static {
      _codes = new Object[][]{{Byte.TYPE, "B"}, {Character.TYPE, "C"}, {Double.TYPE, "D"}, {Float.TYPE, "F"}, {Integer.TYPE, "I"}, {Long.TYPE, "J"}, {Short.TYPE, "S"}, {Boolean.TYPE, "Z"}, {Void.TYPE, "V"}};
   }
}
