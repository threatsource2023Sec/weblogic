package weblogic.utils.classfile;

import java.util.ArrayList;
import java.util.List;
import weblogic.utils.Debug;

public final class Descriptor {
   private static final boolean debug = true;
   private static final boolean verbose = true;
   private String text;
   private boolean isMethodDesriptor;

   public Descriptor(String t) {
      this.text = t;
   }

   public boolean isFieldDesriptor() {
      return !this.isMethodDesriptor();
   }

   public boolean isMethodDesriptor() {
      return this.text.indexOf(40) != -1;
   }

   public String getFieldType() {
      if (!this.isFieldDesriptor()) {
         throw new WrongDescriptorTypeException("Not a field descriptor.");
      } else {
         return (String)parse(this.text).get(0);
      }
   }

   public String getReturnType() {
      if (!this.isMethodDesriptor()) {
         throw new WrongDescriptorTypeException("Not a method descriptor.");
      } else {
         return (String)parse(this.text.substring(this.text.indexOf(41) + 1)).get(0);
      }
   }

   public List getArgumentTypes() {
      if (!this.isMethodDesriptor()) {
         throw new WrongDescriptorTypeException("Not a method descriptor.");
      } else {
         int openP = this.text.indexOf(40);
         int closeP = this.text.indexOf(41);
         Debug.assertion(openP != -1 && closeP != -1);
         return parse(this.text.substring(openP + 1, closeP));
      }
   }

   public static String getBaseType(String text) {
      int bracket = text.lastIndexOf(91);
      return bracket == -1 ? text : text.substring(0, bracket);
   }

   public static String getClassType(String text) {
      return text.startsWith("[") ? (String)parse(text).get(0) : text.replace('/', '.');
   }

   static List parse(String desc) {
      List types = new ArrayList();
      int arrayDim = 0;
      int idx = 0;

      for(int len = desc.length(); idx < len; ++idx) {
         switch (desc.charAt(idx)) {
            case 'B':
               types.add(typeWithArray("byte", arrayDim));
               break;
            case 'C':
               types.add(typeWithArray("char", arrayDim));
               break;
            case 'D':
               types.add(typeWithArray("double", arrayDim));
               break;
            case 'E':
            case 'G':
            case 'H':
            case 'K':
            case 'M':
            case 'N':
            case 'O':
            case 'P':
            case 'Q':
            case 'R':
            case 'T':
            case 'U':
            case 'W':
            case 'X':
            case 'Y':
            default:
               throw new Error("Bad descriptor: <" + desc + ">");
            case 'F':
               types.add(typeWithArray("float", arrayDim));
               break;
            case 'I':
               types.add(typeWithArray("int", arrayDim));
               break;
            case 'J':
               types.add(typeWithArray("long", arrayDim));
               break;
            case 'L':
               int semi = desc.indexOf(59, idx);
               String type = desc.substring(idx + 1, semi);
               type = type.replace('/', '.');
               types.add(typeWithArray(type, arrayDim));
               idx = semi;
               break;
            case 'S':
               types.add(typeWithArray("short", arrayDim));
               break;
            case 'V':
               types.add(typeWithArray("void", arrayDim));
               break;
            case 'Z':
               types.add(typeWithArray("boolean", arrayDim));
               break;
            case '[':
               ++arrayDim;
               continue;
         }

         arrayDim = 0;
      }

      return types;
   }

   private static String typeWithArray(String type, int arrayDim) {
      StringBuffer sb = new StringBuffer(type);

      for(int i = 0; i < arrayDim; ++i) {
         sb.append("[]");
      }

      return sb.toString();
   }

   public static boolean isPrimitive(String type) {
      String base = getBaseType(type);
      switch (base.charAt(0)) {
         case 'b':
            if (!base.equals("boolean") && !base.equals("byte")) {
               break;
            }

            return true;
         case 'c':
            if (base.equals("char")) {
               return true;
            }
            break;
         case 'd':
            if (base.equals("double")) {
               return true;
            }
         case 'e':
         case 'g':
         case 'h':
         case 'j':
         case 'k':
         case 'm':
         case 'n':
         case 'o':
         case 'p':
         case 'q':
         case 'r':
         case 't':
         case 'u':
         default:
            break;
         case 'f':
            if (base.equals("float")) {
               return true;
            }
            break;
         case 'i':
            if (base.equals("int")) {
               return true;
            }
            break;
         case 'l':
            if (base.equals("long")) {
               return true;
            }
            break;
         case 's':
            if (base.equals("short")) {
               return true;
            }
            break;
         case 'v':
            if (base.equals("void")) {
               return true;
            }
      }

      return false;
   }

   public static int getArrayDimension(String type) {
      int dim = 0;
      int i = 0;

      for(int len = type.length(); i < len && type.charAt(i) == '['; ++i) {
         ++dim;
      }

      return dim;
   }
}
