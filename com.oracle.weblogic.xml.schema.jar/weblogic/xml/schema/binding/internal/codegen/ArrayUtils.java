package weblogic.xml.schema.binding.internal.codegen;

import weblogic.utils.Debug;
import weblogic.xml.schema.binding.internal.NameUtil;

public final class ArrayUtils {
   private static final boolean ASSERT = true;

   static String getArrayClassName(String base, int rank) {
      String array_str;
      if (rank < 1) {
         array_str = "rank must be greater than zero: " + rank;
         throw new IllegalArgumentException(array_str);
      } else {
         StringBuffer array_str;
         int extra;
         if (base.startsWith("[")) {
            array_str = new StringBuffer();
            extra = getArrayComponentName(array_str, base);
            rank += extra;
            base = array_str.toString();
         }

         array_str = null;
         if (Integer.TYPE.getName().equals(base)) {
            array_str = int[].class.getName();
         } else if (Short.TYPE.getName().equals(base)) {
            array_str = short[].class.getName();
         } else if (Long.TYPE.getName().equals(base)) {
            array_str = long[].class.getName();
         } else if (Float.TYPE.getName().equals(base)) {
            array_str = float[].class.getName();
         } else if (Double.TYPE.getName().equals(base)) {
            array_str = double[].class.getName();
         } else if (Byte.TYPE.getName().equals(base)) {
            array_str = byte[].class.getName();
         } else if (Boolean.TYPE.getName().equals(base)) {
            array_str = boolean[].class.getName();
         } else if (Character.TYPE.getName().equals(base)) {
            array_str = char[].class.getName();
         } else {
            StringBuffer buf = new StringBuffer();
            buf.append('[');
            buf.append('L');
            buf.append(base);
            buf.append(';');
            array_str = buf.toString();
         }

         if (rank > 1) {
            extra = rank - 1;
            StringBuffer ex = new StringBuffer(array_str.length() + extra);

            for(int i = 0; i < extra; ++i) {
               ex.append('[');
            }

            ex.append(array_str);
            array_str = ex.toString();
         }

         return array_str;
      }
   }

   public static String getArrayDeclString(String raw_class) {
      StringBuffer comp = new StringBuffer(raw_class.length());
      int dims = getArrayComponentName(comp, raw_class);

      for(int i = 0; i < dims; ++i) {
         comp.append("[]");
      }

      return comp.toString();
   }

   public static int getArrayComponentNameFromDecl(StringBuffer compname, String aname) {
      compname.setLength(0);
      int first_bracket = aname.indexOf(91);
      if (first_bracket <= 0) {
         compname.append(aname);
         return 0;
      } else {
         String base = aname.substring(0, first_bracket).trim();
         compname.append(base);
         int rank = 0;

         for(int idx = aname.indexOf(93); idx >= 0; idx = aname.indexOf(93, idx + 1)) {
            ++rank;
         }

         if (compname.length() == 0) {
            throw new AssertionError("aname=" + aname);
         } else if (rank == 0) {
            throw new AssertionError("rank=" + rank);
         } else {
            return rank;
         }
      }
   }

   static int getArrayComponentName(StringBuffer compname, String aname) {
      compname.setLength(0);
      int dims = 0;
      if (aname.startsWith("[")) {
         int first_bracket = aname.indexOf(91);
         int last_bracket = aname.lastIndexOf(91);
         int semi = aname.indexOf(59, last_bracket);
         if (semi == -1) {
            char array_type = aname.charAt(1 + last_bracket);
            String at = "[" + array_type;
            String array_str;
            if (int[].class.getName().equals(at)) {
               array_str = int[].class.getComponentType().getName();
            } else if (short[].class.getName().equals(at)) {
               array_str = short[].class.getComponentType().getName();
            } else if (long[].class.getName().equals(at)) {
               array_str = long[].class.getComponentType().getName();
            } else if (float[].class.getName().equals(at)) {
               array_str = float[].class.getComponentType().getName();
            } else if (double[].class.getName().equals(at)) {
               array_str = double[].class.getComponentType().getName();
            } else if (byte[].class.getName().equals(at)) {
               array_str = byte[].class.getComponentType().getName();
            } else if (boolean[].class.getName().equals(at)) {
               array_str = boolean[].class.getComponentType().getName();
            } else {
               if (!char[].class.getName().equals(at)) {
                  throw new AssertionError("unknown array type for " + aname);
               }

               array_str = char[].class.getComponentType().getName();
            }

            compname.append(array_str);
         } else {
            compname.append(aname.substring(last_bracket + 2, semi));
         }

         dims = 1 + last_bracket - first_bracket;
      } else {
         compname.append(aname);
      }

      if (compname.length() == 0) {
         throw new AssertionError("aname=" + aname);
      } else {
         return dims;
      }
   }

   public static Object toObject(byte[] b) {
      Byte[] obj = new Byte[b.length];

      for(int i = 0; i < b.length; ++i) {
         obj[i] = new Byte(b[i]);
      }

      return obj;
   }

   public static void main(String[] args) throws Exception {
      Class c = Class.forName("[F");
      Debug.say("guessed float class=" + c);
      Debug.say("float class name=" + Float.TYPE.getName());
      int[][][] i3 = new int[1][1][1];
      String[][][] s3 = new String[1][1][1];
      String s3_n = s3.getClass().getName();
      String i3_n = i3.getClass().getName();
      int[] i1 = new int[1];
      String[] s1 = new String[1];
      String s1_n = s1.getClass().getName();
      String i1_n = i1.getClass().getName();
      Debug.say("s3_n pkg = " + s3_n.getClass().getPackage().getName());
      Debug.say("s3_n my pkg = " + NameUtil.getPackageNameFromClass(s3_n));
      Debug.say("i3_n pkg = " + i3_n.getClass().getPackage().getName());
      Debug.say("i3_n my pkg = " + NameUtil.getPackageNameFromClass(i3_n));
      Debug.say("i1_n pkg = " + i1_n.getClass().getPackage().getName());
      Debug.say("i1_n my pkg = " + NameUtil.getPackageNameFromClass(i1_n));
      Debug.assertion(s3_n.equals(getArrayClassName("java.lang.String", 3)));
      Debug.assertion(i3_n.equals(getArrayClassName("int", 3)));
   }
}
