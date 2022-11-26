package serp.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.LinkedList;

public class Strings {
   private static final Object[][] _codes;

   public static String replace(String str, String from, String to) {
      String[] split = split(str, from, Integer.MAX_VALUE);
      return join(split, to);
   }

   public static String[] split(String str, String token, int max) {
      if (str != null && str.length() != 0) {
         if (token != null && token.length() != 0) {
            LinkedList ret = new LinkedList();
            int start = 0;
            int split = 0;

            while(true) {
               while(split != -1) {
                  split = str.indexOf(token, start);
                  if (split == -1 && start >= str.length()) {
                     ret.add("");
                  } else if (split == -1) {
                     ret.add(str.substring(start));
                  } else {
                     ret.add(str.substring(start, split));
                     start = split + token.length();
                  }
               }

               if (max == 0) {
                  while(ret.getLast().equals("")) {
                     ret.removeLast();
                  }
               } else if (max > 0 && ret.size() > max) {
                  StringBuilder buf = new StringBuilder(ret.removeLast().toString());

                  while(ret.size() >= max) {
                     buf.insert(0, token);
                     buf.insert(0, ret.removeLast());
                  }

                  ret.add(buf.toString());
               }

               return (String[])((String[])ret.toArray(new String[ret.size()]));
            }
         } else {
            throw new IllegalArgumentException("token: [" + token + "]");
         }
      } else {
         return new String[0];
      }
   }

   public static String join(Object[] strings, String token) {
      if (strings == null) {
         return null;
      } else {
         StringBuilder buf = new StringBuilder(20 * strings.length);

         for(int i = 0; i < strings.length; ++i) {
            if (i > 0) {
               buf.append(token);
            }

            if (strings[i] != null) {
               buf.append(strings[i]);
            }
         }

         return buf.toString();
      }
   }

   public static Class toClass(String str, ClassLoader loader) {
      return toClass(str, false, loader);
   }

   public static Class toClass(String str, boolean resolve, ClassLoader loader) {
      if (str == null) {
         throw new NullPointerException("str == null");
      } else {
         int dims;
         for(dims = 0; str.endsWith("[]"); str = str.substring(0, str.length() - 2)) {
            ++dims;
         }

         boolean primitive = false;
         int i;
         if (str.indexOf(46) == -1) {
            for(i = 0; !primitive && i < _codes.length; ++i) {
               if (_codes[i][1].equals(str)) {
                  if (dims == 0) {
                     return (Class)_codes[i][0];
                  }

                  str = (String)_codes[i][2];
                  primitive = true;
               }
            }
         }

         if (dims > 0) {
            i = str.length() + dims;
            if (!primitive) {
               i += 2;
            }

            StringBuilder buf = new StringBuilder(i);

            for(int i = 0; i < dims; ++i) {
               buf.append('[');
            }

            if (!primitive) {
               buf.append('L');
            }

            buf.append(str);
            if (!primitive) {
               buf.append(';');
            }

            str = buf.toString();
         }

         if (loader == null) {
            loader = Thread.currentThread().getContextClassLoader();
         }

         try {
            return Class.forName(str, resolve, loader);
         } catch (Throwable var8) {
            throw new IllegalArgumentException(var8.toString());
         }
      }
   }

   public static String getClassName(Class cls) {
      return cls == null ? null : getClassName(cls.getName());
   }

   public static String getClassName(String fullName) {
      if (fullName == null) {
         return null;
      } else {
         int dims = 0;

         int i;
         for(i = 0; i < fullName.length(); ++i) {
            if (fullName.charAt(i) != '[') {
               dims = i;
               break;
            }
         }

         if (dims > 0) {
            fullName = fullName.substring(dims);
         }

         for(i = 0; i < _codes.length; ++i) {
            if (_codes[i][2].equals(fullName)) {
               fullName = (String)_codes[i][1];
               break;
            }
         }

         fullName = fullName.substring(fullName.lastIndexOf(46) + 1);

         for(i = 0; i < dims; ++i) {
            fullName = fullName + "[]";
         }

         return fullName;
      }
   }

   public static String getPackageName(Class cls) {
      return cls == null ? null : getPackageName(cls.getName());
   }

   public static String getPackageName(String fullName) {
      if (fullName == null) {
         return null;
      } else {
         int dotIdx = fullName.lastIndexOf(46);
         return dotIdx == -1 ? "" : fullName.substring(0, dotIdx);
      }
   }

   public static Object parse(String val, Class type) {
      if (!canParse(type)) {
         throw new IllegalArgumentException("invalid type: " + type.getName());
      } else if (val == null) {
         if (!type.isPrimitive()) {
            return null;
         } else if (type == Boolean.TYPE) {
            return Boolean.FALSE;
         } else if (type == Byte.TYPE) {
            return new Byte((byte)0);
         } else if (type == Character.TYPE) {
            return new Character('\u0000');
         } else if (type == Double.TYPE) {
            return new Double(0.0);
         } else if (type == Float.TYPE) {
            return new Float(0.0F);
         } else if (type == Integer.TYPE) {
            return Numbers.valueOf(0);
         } else if (type == Long.TYPE) {
            return Numbers.valueOf(0L);
         } else if (type == Short.TYPE) {
            return new Short((short)0);
         } else {
            throw new IllegalStateException("invalid type: " + type);
         }
      } else if (type != Boolean.TYPE && type != Boolean.class) {
         if (type != Byte.TYPE && type != Byte.class) {
            if (type != Character.TYPE && type != Character.class) {
               if (type != Double.TYPE && type != Double.class) {
                  if (type != Float.TYPE && type != Float.class) {
                     if (type != Integer.TYPE && type != Integer.class) {
                        if (type != Long.TYPE && type != Long.class) {
                           if (type != Short.TYPE && type != Short.class) {
                              if (type == String.class) {
                                 return val;
                              } else if (type == Date.class) {
                                 return new Date(val);
                              } else if (type == BigInteger.class) {
                                 return new BigInteger(val);
                              } else if (type == BigDecimal.class) {
                                 return new BigDecimal(val);
                              } else {
                                 throw new IllegalArgumentException("Invalid type: " + type);
                              }
                           } else {
                              return Short.valueOf(val);
                           }
                        } else {
                           return Long.valueOf(val);
                        }
                     } else {
                        return Integer.valueOf(val);
                     }
                  } else {
                     return Float.valueOf(val);
                  }
               } else {
                  return Double.valueOf(val);
               }
            } else if (val.length() == 0) {
               return new Character('\u0000');
            } else if (val.length() == 1) {
               return new Character(val.charAt(0));
            } else {
               throw new IllegalArgumentException("'" + val + "' is longer than " + "one character.");
            }
         } else {
            return Byte.valueOf(val);
         }
      } else {
         return Boolean.valueOf(val);
      }
   }

   public static boolean canParse(Class type) {
      return type.isPrimitive() || type == Boolean.class || type == Byte.class || type == Character.class || type == Short.class || type == Integer.class || type == Long.class || type == Float.class || type == Double.class || type == String.class || type == Date.class || type == BigInteger.class || type == BigDecimal.class;
   }

   static {
      _codes = new Object[][]{{Byte.TYPE, "byte", "B"}, {Character.TYPE, "char", "C"}, {Double.TYPE, "double", "D"}, {Float.TYPE, "float", "F"}, {Integer.TYPE, "int", "I"}, {Long.TYPE, "long", "J"}, {Short.TYPE, "short", "S"}, {Boolean.TYPE, "boolean", "Z"}, {Void.TYPE, "void", "V"}};
   }
}
