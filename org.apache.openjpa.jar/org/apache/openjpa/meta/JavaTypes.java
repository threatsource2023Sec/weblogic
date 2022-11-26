package org.apache.openjpa.meta;

import java.io.InputStream;
import java.io.Reader;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import org.apache.openjpa.enhance.PersistenceCapable;
import org.apache.openjpa.lib.meta.CFMetaDataParser;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.MetaDataException;
import serp.util.Numbers;
import serp.util.Strings;

public class JavaTypes {
   public static final int BOOLEAN = 0;
   public static final int BYTE = 1;
   public static final int CHAR = 2;
   public static final int DOUBLE = 3;
   public static final int FLOAT = 4;
   public static final int INT = 5;
   public static final int LONG = 6;
   public static final int SHORT = 7;
   public static final int OBJECT = 8;
   public static final int STRING = 9;
   public static final int NUMBER = 10;
   public static final int ARRAY = 11;
   public static final int COLLECTION = 12;
   public static final int MAP = 13;
   public static final int DATE = 14;
   public static final int PC = 15;
   public static final int BOOLEAN_OBJ = 16;
   public static final int BYTE_OBJ = 17;
   public static final int CHAR_OBJ = 18;
   public static final int DOUBLE_OBJ = 19;
   public static final int FLOAT_OBJ = 20;
   public static final int INT_OBJ = 21;
   public static final int LONG_OBJ = 22;
   public static final int SHORT_OBJ = 23;
   public static final int BIGDECIMAL = 24;
   public static final int BIGINTEGER = 25;
   public static final int LOCALE = 26;
   public static final int PC_UNTYPED = 27;
   public static final int CALENDAR = 28;
   public static final int OID = 29;
   public static final int INPUT_STREAM = 30;
   public static final int INPUT_READER = 31;
   private static final Localizer _loc = Localizer.forPackage(JavaTypes.class);
   private static final Map _typeCodes = new HashMap();

   public static int getTypeCode(Class type) {
      if (type == null) {
         return 8;
      } else {
         if (type.isPrimitive()) {
            switch (type.getName().charAt(0)) {
               case 'b':
                  return type == Boolean.TYPE ? 0 : 1;
               case 'c':
                  return 2;
               case 'd':
                  return 3;
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
               default:
                  break;
               case 'f':
                  return 4;
               case 'i':
                  return 5;
               case 'l':
                  return 6;
               case 's':
                  return 7;
            }
         }

         Integer code = (Integer)_typeCodes.get(type);
         if (code != null) {
            return code;
         } else if (Collection.class.isAssignableFrom(type)) {
            return 12;
         } else if (Map.class.isAssignableFrom(type)) {
            return 13;
         } else if (type.isArray()) {
            return 11;
         } else if (Calendar.class.isAssignableFrom(type)) {
            return 28;
         } else if (type.isInterface()) {
            return type == Serializable.class ? 8 : 27;
         } else if (type.isAssignableFrom(Reader.class)) {
            return 31;
         } else {
            return type.isAssignableFrom(InputStream.class) ? 30 : 8;
         }
      }
   }

   public static Class classForName(String name, ClassMetaData context) {
      return classForName(name, (ClassMetaData)context, (ClassLoader)null);
   }

   public static Class classForName(String name, ClassMetaData context, ClassLoader loader) {
      return classForName(name, context, context.getDescribedType(), (ValueMetaData)null, loader);
   }

   public static Class classForName(String name, ValueMetaData context) {
      return classForName(name, (ValueMetaData)context, (ClassLoader)null);
   }

   public static Class classForName(String name, ValueMetaData context, ClassLoader loader) {
      return classForName(name, context.getFieldMetaData().getDefiningMetaData(), context.getFieldMetaData().getDeclaringType(), context, loader);
   }

   private static Class classForName(String name, ClassMetaData meta, Class dec, ValueMetaData vmd, ClassLoader loader) {
      if (!"PersistenceCapable".equals(name) && !"javax.jdo.PersistenceCapable".equals(name)) {
         if ("Object".equals(name)) {
            return Object.class;
         } else {
            MetaDataRepository rep = meta.getRepository();
            boolean runtime = (rep.getValidate() & 8) != 0;
            if (loader == null) {
               loader = rep.getConfiguration().getClassResolverInstance().getClassLoader(dec, meta.getEnvClassLoader());
            }

            String pkg = Strings.getPackageName(dec);
            Class cls = CFMetaDataParser.classForName(name, pkg, runtime, loader);
            if (cls == null && vmd != null) {
               pkg = Strings.getPackageName(vmd.getDeclaredType());
               cls = CFMetaDataParser.classForName(name, pkg, runtime, loader);
            }

            if (cls == null) {
               throw new MetaDataException(_loc.get("bad-class", name, vmd == null ? meta : vmd));
            } else {
               return cls;
            }
         }
      } else {
         return PersistenceCapable.class;
      }
   }

   public static Object convert(Object val, int typeCode) {
      if (val == null) {
         return null;
      } else {
         switch (typeCode) {
            case 0:
            case 16:
               if (val instanceof String) {
                  return Boolean.valueOf(val.toString());
               }

               return val;
            case 2:
            case 18:
               if (val instanceof Character) {
                  return val;
               } else if (val instanceof String) {
                  return new Character(val.toString().charAt(0));
               } else {
                  if (val instanceof Number) {
                     return new Character((char)((Number)val).intValue());
                  }

                  return val;
               }
            case 8:
            case 11:
            case 12:
            case 13:
            case 15:
            default:
               return val;
            case 9:
               return val.toString();
            case 10:
               if (val instanceof Number) {
                  return val;
               } else {
                  if (val instanceof String) {
                     return new BigDecimal(val.toString());
                  }

                  return val;
               }
            case 14:
               if (val instanceof String) {
                  return new Date(val.toString());
               }

               return val;
            case 17:
               if (val instanceof Byte) {
                  return val;
               } else if (val instanceof Number) {
                  return new Byte(((Number)val).byteValue());
               }
            case 1:
               if (val instanceof String) {
                  return new Byte(val.toString());
               }

               return val;
            case 19:
               if (val instanceof Double) {
                  return val;
               } else if (val instanceof Number) {
                  return new Double(((Number)val).doubleValue());
               }
            case 3:
               if (val instanceof String) {
                  return new Double(val.toString());
               }

               return val;
            case 20:
               if (val instanceof Float) {
                  return val;
               } else if (val instanceof Number) {
                  return new Float(((Number)val).floatValue());
               }
            case 4:
               if (val instanceof String) {
                  return new Float(val.toString());
               }

               return val;
            case 21:
               if (val instanceof Integer) {
                  return val;
               } else if (val instanceof Number) {
                  return Numbers.valueOf(((Number)val).intValue());
               }
            case 5:
               if (val instanceof String) {
                  return new Integer(val.toString());
               }

               return val;
            case 22:
               if (val instanceof Long) {
                  return val;
               } else if (val instanceof Number) {
                  return Numbers.valueOf(((Number)val).longValue());
               }
            case 6:
               if (val instanceof String) {
                  return new Long(val.toString());
               }

               return val;
            case 23:
               if (val instanceof Short) {
                  return val;
               } else if (val instanceof Number) {
                  return new Short(((Number)val).shortValue());
               }
            case 7:
               if (val instanceof String) {
                  return new Short(val.toString());
               }

               return val;
            case 24:
               if (val instanceof BigDecimal) {
                  return val;
               } else if (val instanceof Number) {
                  return new BigDecimal(((Number)val).doubleValue());
               } else {
                  if (val instanceof String) {
                     return new BigDecimal(val.toString());
                  }

                  return val;
               }
            case 25:
               if (val instanceof BigInteger) {
                  return val;
               } else {
                  return !(val instanceof Number) && !(val instanceof String) ? val : new BigInteger(val.toString());
               }
         }
      }
   }

   public static boolean maybePC(FieldMetaData field) {
      switch (field.getDeclaredTypeCode()) {
         case 11:
         case 12:
            return maybePC(field.getElement());
         case 13:
            return maybePC(field.getKey()) || maybePC(field.getElement());
         default:
            return maybePC((ValueMetaData)field);
      }
   }

   public static boolean maybePC(ValueMetaData val) {
      return maybePC(val.getDeclaredTypeCode(), val.getDeclaredType());
   }

   static boolean maybePC(int typeCode, Class type) {
      if (type == null) {
         return false;
      } else {
         switch (typeCode) {
            case 8:
            case 15:
            case 27:
               return true;
            case 12:
            case 13:
               return !type.getName().startsWith("java.util.");
            default:
               return false;
         }
      }
   }

   public static List toList(Object val, Class elem, boolean mutable) {
      if (val == null) {
         return null;
      } else {
         Object l;
         if (!elem.isPrimitive()) {
            l = Arrays.asList((Object[])((Object[])val));
            if (mutable) {
               l = new ArrayList((Collection)l);
            }
         } else {
            int length = Array.getLength(val);
            l = new ArrayList(length);

            for(int i = 0; i < length; ++i) {
               ((List)l).add(Array.get(val, i));
            }
         }

         return (List)l;
      }
   }

   public static Object toArray(Collection coll, Class elem) {
      if (coll == null) {
         return null;
      } else {
         Object array = Array.newInstance(elem, coll.size());
         int idx = 0;

         for(Iterator itr = coll.iterator(); itr.hasNext(); ++idx) {
            Array.set(array, idx, itr.next());
         }

         return array;
      }
   }

   static {
      _typeCodes.put(String.class, Numbers.valueOf(9));
      _typeCodes.put(Boolean.class, Numbers.valueOf(16));
      _typeCodes.put(Byte.class, Numbers.valueOf(17));
      _typeCodes.put(Character.class, Numbers.valueOf(18));
      _typeCodes.put(Double.class, Numbers.valueOf(19));
      _typeCodes.put(Float.class, Numbers.valueOf(20));
      _typeCodes.put(Integer.class, Numbers.valueOf(21));
      _typeCodes.put(Long.class, Numbers.valueOf(22));
      _typeCodes.put(Short.class, Numbers.valueOf(23));
      _typeCodes.put(Date.class, Numbers.valueOf(14));
      _typeCodes.put(java.sql.Date.class, Numbers.valueOf(14));
      _typeCodes.put(Timestamp.class, Numbers.valueOf(14));
      _typeCodes.put(Time.class, Numbers.valueOf(14));
      _typeCodes.put(BigInteger.class, Numbers.valueOf(25));
      _typeCodes.put(BigDecimal.class, Numbers.valueOf(24));
      _typeCodes.put(Number.class, Numbers.valueOf(10));
      _typeCodes.put(Locale.class, Numbers.valueOf(26));
      _typeCodes.put(Object.class, Numbers.valueOf(8));
      _typeCodes.put(PersistenceCapable.class, Numbers.valueOf(27));
      _typeCodes.put(Properties.class, Numbers.valueOf(13));
      _typeCodes.put(Calendar.class, Numbers.valueOf(28));
   }
}
