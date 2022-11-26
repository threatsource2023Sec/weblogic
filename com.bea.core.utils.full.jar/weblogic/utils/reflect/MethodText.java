package weblogic.utils.reflect;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Hashtable;

public class MethodText {
   public static final int ARGNAMES = 1;
   public static final int ACCESS = 2;
   public static final int SCOPE = 4;
   public static final int COMPRESS_JAVA_LANG = 8;
   public static final int EXCEPTION = 16;
   public static final int SYNCHRONIZED = 32;
   public static final int RETURN_TYPE = 64;
   public static final int TEXT_ENCODE = 128;
   public static final int METHOD_NAME = 256;
   public static final int ARGTYPES = 512;
   public static final int WRAPPEDARGNAMES = 1024;
   public static final int COMPRESS_ARGS = 2048;
   public static final int ARGS = 513;
   public static final int DEFAULT = 859;
   private int options = 859;
   private Method method = null;
   static Hashtable typeToCode = new Hashtable();

   public MethodText() {
   }

   public MethodText(int options) {
      this.setOptions(options);
   }

   private boolean wantsArgumentNames() {
      return (this.options & 1) != 0;
   }

   private boolean wantsWrappedArgumentNames() {
      return (this.options & 1024) != 0;
   }

   public void setMethod(Method m) {
      this.method = m;
   }

   public void setOptions(int opts) {
      this.options = opts;
      if ((this.options & 128) != 0) {
         this.options = 904;
      }

   }

   public int getOptions() {
      return this.options;
   }

   private void check() {
      if (this.method == null) {
         throw new RuntimeException("MethodText.setMethod() has not been called");
      }
   }

   public String toString() {
      this.check();
      if ((this.options & 128) != 0) {
         return this.toTextString();
      } else {
         StringBuffer sb = new StringBuffer();
         this.addModifiers(sb);
         if ((this.options & 256) != 0) {
            sb.append(this.method.getName()).append("(");
         }

         if ((this.options & 1537) != 0) {
            this.addArguments(sb, true);
         }

         this.addThrowsClause(sb);
         return sb.toString();
      }
   }

   public String toTextString() {
      this.check();
      StringBuffer sb = new StringBuffer();
      if ((this.options & 256) != 0) {
         sb.append(this.method.getName()).append("_");
      }

      if ((this.options & 1537) != 0) {
         this.addArguments(sb, false);
      }

      this.trimTrailingUnderbar(sb);
      return sb.toString();
   }

   private void addArguments(StringBuffer sb, boolean addArgNames) {
      Class[] params = this.method.getParameterTypes();

      for(int j = 0; j < params.length; ++j) {
         if ((this.options & 512) != 0) {
            sb.append(this.getTypeName(params[j]));
         }

         if (addArgNames) {
            this.addArgName(sb, params, j);
         }
      }

   }

   private void addModifiers(StringBuffer sb) {
      int mod = this.method.getModifiers();
      if ((this.options & 2) != 0) {
         if (Modifier.isPublic(mod)) {
            sb.append("public ");
         }

         if (Modifier.isPrivate(mod)) {
            sb.append("private ");
         }

         if (Modifier.isProtected(mod)) {
            sb.append("protected ");
         }
      }

      if ((this.options & 4) != 0) {
         if (Modifier.isStatic(mod)) {
            sb.append("static ");
         }

         if (Modifier.isFinal(mod)) {
            sb.append("final ");
         }

         if (Modifier.isAbstract(mod)) {
            sb.append("abstract ");
         }
      }

      if ((this.options & 32) != 0 && Modifier.isSynchronized(mod)) {
         sb.append("synchronized");
      }

      if ((this.options & 64) != 0) {
         sb.append(this.getTypeName(this.method.getReturnType()) + " ");
      }

   }

   private void addArgName(StringBuffer sb, Class[] params, int j) {
      boolean wrapped = this.wantsWrappedArgumentNames();
      if (this.wantsArgumentNames() || wrapped) {
         if (wrapped) {
            String typeName = params[j].getName();
            wrapped &= !this.baseConversion(typeName).equals(typeName);
            if (wrapped) {
               sb.append("new " + this.baseConversion(typeName) + "(");
            }
         }

         sb.append(" arg" + j);
         if (wrapped) {
            sb.append(")");
         }
      }

      if (j < params.length - 1) {
         sb.append(",");
      }

   }

   private void addThrowsClause(StringBuffer sb) {
      if ((this.options & 256) != 0) {
         sb.append(")");
      }

      if ((this.options & 16) != 0) {
         String exstr = this.getExceptionString();
         if (exstr.length() > 0) {
            sb.append(" throws " + exstr);
         }
      }

   }

   private void trimTrailingUnderbar(StringBuffer sb) {
      int lastNonUnderscorePos;
      for(lastNonUnderscorePos = sb.length() - 1; sb.charAt(lastNonUnderscorePos) == '_'; --lastNonUnderscorePos) {
      }

      sb.setLength(lastNonUnderscorePos + 1);
   }

   private String baseConversion(String toconvert) {
      String converted = toconvert;
      if (toconvert.equals("boolean")) {
         converted = "Boolean";
      }

      if (toconvert.equals("int")) {
         converted = "Integer";
      }

      if (toconvert.equals("short")) {
         converted = "Short";
      }

      if (toconvert.equals("long")) {
         converted = "Long";
      }

      if (toconvert.equals("double")) {
         converted = "Double";
      }

      if (toconvert.equals("float")) {
         converted = "Float";
      }

      if (toconvert.equals("char")) {
         converted = "Character";
      }

      if (toconvert.equals("byte")) {
         converted = "Byte";
      }

      return converted;
   }

   public String getExceptionString() {
      this.check();

      try {
         StringBuffer sb = new StringBuffer();
         Class[] exceptions = this.method.getExceptionTypes();

         for(int i = 0; i < exceptions.length; ++i) {
            sb.append(this.compress(exceptions[i].getName()));
            if (i < exceptions.length - 1) {
               sb.append(",");
            }
         }

         return sb.toString();
      } catch (Exception var4) {
         return "";
      }
   }

   private String getTypeName(Class type) {
      boolean needsEncoding = (this.options & 128) != 0;
      if (type.isArray()) {
         try {
            Class cl = type;
            int dimensions = 0;

            String clName;
            for(clName = null; cl.isArray(); cl = cl.getComponentType()) {
               ++dimensions;
            }

            clName = this.compress(cl.getName());
            StringBuffer sb = new StringBuffer();
            int i;
            if (!needsEncoding) {
               sb.append(clName);

               for(i = 0; i < dimensions; ++i) {
                  sb.append("[]");
               }
            } else {
               for(i = 0; i < dimensions; ++i) {
                  sb.append("A");
               }

               sb.append(this.encode(clName));
            }

            return sb.toString();
         } catch (Throwable var8) {
         }
      }

      return this.encode(this.compress(type.getName()));
   }

   private String compress(String typeName) {
      if ((this.options & 2048) != 0) {
         int leafPos = typeName.lastIndexOf(46);
         if (leafPos != -1) {
            return typeName.substring(leafPos + 1);
         }
      } else if ((this.options & 8) != 0 && typeName.startsWith("java.lang.")) {
         String newTypeName = typeName.substring(10);
         if (newTypeName.indexOf(".") == -1) {
            return newTypeName;
         }
      }

      return adjustClassName(typeName);
   }

   private static String adjustClassName(String className) {
      return className != null && className.indexOf("$") > -1 ? className.replace('$', '.') : className;
   }

   private String encode(String typeName) {
      if ((this.options & 128) == 0) {
         return typeName;
      } else {
         String code;
         if ((this.options & 8) == 0 && typeName.startsWith("java.lang.")) {
            code = (String)typeToCode.get(typeName.substring(10));
         } else {
            code = (String)typeToCode.get(typeName);
         }

         if (code != null) {
            return code;
         } else {
            int i = typeName.indexOf(".");
            return i != -1 ? typeName.replace('.', '_') : typeName;
         }
      }
   }

   static {
      typeToCode.put("boolean", "b");
      typeToCode.put("byte", "y");
      typeToCode.put("char", "c");
      typeToCode.put("double", "d");
      typeToCode.put("float", "f");
      typeToCode.put("int", "i");
      typeToCode.put("long", "l");
      typeToCode.put("short", "h");
      typeToCode.put("Boolean", "B");
      typeToCode.put("Byte", "Y");
      typeToCode.put("Character", "C");
      typeToCode.put("Double", "D");
      typeToCode.put("Float", "F");
      typeToCode.put("Integer", "I");
      typeToCode.put("Long", "L");
      typeToCode.put("Short", "H");
      typeToCode.put("String", "S");
      typeToCode.put("Object", "O");
   }
}
