package org.apache.openjpa.lib.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeSet;
import org.apache.commons.lang.StringUtils;
import serp.util.Strings;

public class Options extends TypedProperties {
   public static Options EMPTY = new EmptyOptions();
   private static Object[][] _primWrappers;

   public Options() {
   }

   public Options(Properties defaults) {
      super(defaults);
   }

   public String[] setFromCmdLine(String[] args) {
      if (args != null && args.length != 0) {
         String key = null;
         String value = null;
         List remainder = new LinkedList();

         for(int i = 0; i < args.length + 1; ++i) {
            if (i != args.length && !args[i].startsWith("-")) {
               if (key != null) {
                  this.setProperty(key, trimQuote(args[i]));
                  key = null;
               } else {
                  remainder.add(args[i]);
               }
            } else {
               key = trimQuote(key);
               if (key != null) {
                  if (!StringUtils.isEmpty((String)value)) {
                     this.setProperty(key, trimQuote((String)value));
                  } else {
                     this.setProperty(key, "true");
                  }
               }

               if (i == args.length) {
                  break;
               }

               key = args[i].substring(1);
               value = null;
            }
         }

         return (String[])((String[])remainder.toArray(new String[remainder.size()]));
      } else {
         return args;
      }
   }

   public Options setInto(Object obj) {
      Map.Entry entry = null;
      if (this.defaults != null) {
         Iterator itr = this.defaults.entrySet().iterator();

         while(itr.hasNext()) {
            entry = (Map.Entry)itr.next();
            if (!this.containsKey(entry.getKey())) {
               this.setInto(obj, entry);
            }
         }
      }

      Options invalidEntries = null;
      Iterator itr = this.entrySet().iterator();

      while(itr.hasNext()) {
         Map.Entry e = (Map.Entry)itr.next();
         if (!this.setInto(obj, e)) {
            if (invalidEntries == null) {
               invalidEntries = new Options();
            }

            invalidEntries.put(e.getKey(), e.getValue());
         }
      }

      return invalidEntries == null ? EMPTY : invalidEntries;
   }

   private boolean setInto(Object obj, Map.Entry entry) {
      if (entry.getKey() == null) {
         return false;
      } else {
         try {
            Object[] match = new Object[]{obj, null};
            if (!matchOptionToMember(entry.getKey().toString(), match)) {
               return false;
            } else {
               Class[] type = getType(match[1]);
               Object[] values = new Object[type.length];
               String[] strValues;
               if (entry.getValue() == null) {
                  strValues = new String[1];
               } else if (values.length == 1) {
                  strValues = new String[]{entry.getValue().toString()};
               } else {
                  strValues = Strings.split(entry.getValue().toString(), ",", 0);
               }

               int i;
               for(i = 0; i < strValues.length; ++i) {
                  values[i] = this.stringToObject(strValues[i].trim(), type[i]);
               }

               for(i = strValues.length; i < values.length; ++i) {
                  values[i] = this.getDefaultValue(type[i]);
               }

               invoke(match[0], match[1], values);
               return true;
            }
         } catch (Throwable var8) {
            throw new ParseException(obj + "." + entry.getKey() + " = " + entry.getValue(), var8);
         }
      }
   }

   private static String trimQuote(String val) {
      return val != null && val.startsWith("'") && val.endsWith("'") ? val.substring(1, val.length() - 1) : val;
   }

   public static Collection findOptionsFor(Class type) {
      Collection names = new TreeSet();
      Method[] meths = type.getMethods();

      for(int i = 0; i < meths.length; ++i) {
         if (meths[i].getName().startsWith("set")) {
            Class[] params = meths[i].getParameterTypes();
            if (params.length != 0 && !params[0].isArray()) {
               names.add(StringUtils.capitalize(meths[i].getName().substring(3)));
            }
         }
      }

      Field[] fields = type.getFields();

      for(int i = 0; i < fields.length; ++i) {
         names.add(StringUtils.capitalize(fields[i].getName()));
      }

      return names;
   }

   private static boolean matchOptionToMember(String key, Object[] match) throws Exception {
      if (StringUtils.isEmpty(key)) {
         return false;
      } else {
         String[] find = Strings.split(key, ".", 2);
         String base = StringUtils.capitalize(find[0]);
         String set = "set" + base;
         String get = "get" + base;
         Class type = match[0].getClass();
         Method[] meths = type.getMethods();
         Method setMeth = null;
         Method getMeth = null;

         for(int i = 0; i < meths.length; ++i) {
            if (meths[i].getName().equals(set)) {
               Class[] params = meths[i].getParameterTypes();
               if (params.length != 0 && !params[0].isArray()) {
                  if (setMeth == null) {
                     setMeth = meths[i];
                  } else if (params.length < setMeth.getParameterTypes().length) {
                     setMeth = meths[i];
                  } else if (params.length == setMeth.getParameterTypes().length && params[0] == String.class) {
                     setMeth = meths[i];
                  }
               }
            } else if (meths[i].getName().equals(get)) {
               getMeth = meths[i];
            }
         }

         Member setter = setMeth;
         Member getter = getMeth;
         if (setMeth == null) {
            Field[] fields = type.getFields();
            String uncapBase = StringUtils.uncapitalize(find[0]);

            for(int i = 0; i < fields.length; ++i) {
               if (fields[i].getName().equals(base) || fields[i].getName().equals(uncapBase)) {
                  setter = fields[i];
                  getter = fields[i];
                  break;
               }
            }
         }

         if (setter == null && getter == null) {
            return false;
         } else if (find.length > 1) {
            Object inner = null;
            if (getter != null) {
               inner = invoke(match[0], getter, (Object[])null);
            }

            if (inner == null && setter != null) {
               Class innerType = getType(setter)[0];

               try {
                  inner = AccessController.doPrivileged(J2DoPrivHelper.newInstanceAction(innerType));
               } catch (PrivilegedActionException var16) {
                  throw var16.getException();
               }

               invoke(match[0], setter, new Object[]{inner});
            }

            match[0] = inner;
            return matchOptionToMember(find[1], match);
         } else {
            match[1] = setter;
            return match[1] != null;
         }
      }
   }

   private static Class[] getType(Object member) {
      return member instanceof Method ? ((Method)member).getParameterTypes() : new Class[]{((Field)member).getType()};
   }

   private static Object invoke(Object target, Object member, Object[] values) throws Exception {
      if (member instanceof Method) {
         return ((Method)member).invoke(target, values);
      } else if (values != null && values.length != 0) {
         ((Field)member).set(target, values[0]);
         return null;
      } else {
         return ((Field)member).get(target);
      }
   }

   private Object stringToObject(String str, Class type) throws Exception {
      if (str != null && type != String.class) {
         if (type == Class.class) {
            return Class.forName(str, false, this.getClass().getClassLoader());
         } else {
            if ((type.isPrimitive() || Number.class.isAssignableFrom(type)) && str.length() > 2 && str.endsWith(".0")) {
               str = str.substring(0, str.length() - 2);
            }

            if (type.isPrimitive()) {
               for(int i = 0; i < _primWrappers.length; ++i) {
                  if (type == _primWrappers[i][0]) {
                     return this.stringToObject(str, (Class)_primWrappers[i][1]);
                  }
               }
            }

            Exception err = null;

            try {
               Constructor cons = type.getConstructor(String.class);
               if (type == Boolean.class && "t".equalsIgnoreCase(str)) {
                  str = "true";
               }

               return cons.newInstance(str);
            } catch (Exception var8) {
               Class subType = null;

               try {
                  subType = Class.forName(str);
               } catch (Exception var7) {
                  throw var8;
               }

               if (!type.isAssignableFrom(subType)) {
                  throw var8;
               } else {
                  try {
                     return AccessController.doPrivileged(J2DoPrivHelper.newInstanceAction(subType));
                  } catch (PrivilegedActionException var6) {
                     throw var6.getException();
                  }
               }
            }
         }
      } else {
         return str;
      }
   }

   private Object getDefaultValue(Class type) {
      for(int i = 0; i < _primWrappers.length; ++i) {
         if (_primWrappers[i][0] == type) {
            return _primWrappers[i][2];
         }
      }

      return null;
   }

   public boolean getBooleanProperty(String key, String key2, boolean def) {
      String val = this.getProperty(key);
      if (val == null) {
         val = this.getProperty(key2);
      }

      if (val == null) {
         return def;
      } else {
         return "t".equalsIgnoreCase(val) || "true".equalsIgnoreCase(val);
      }
   }

   public float getFloatProperty(String key, String key2, float def) {
      String val = this.getProperty(key);
      if (val == null) {
         val = this.getProperty(key2);
      }

      return val == null ? def : Float.parseFloat(val);
   }

   public double getDoubleProperty(String key, String key2, double def) {
      String val = this.getProperty(key);
      if (val == null) {
         val = this.getProperty(key2);
      }

      return val == null ? def : Double.parseDouble(val);
   }

   public long getLongProperty(String key, String key2, long def) {
      String val = this.getProperty(key);
      if (val == null) {
         val = this.getProperty(key2);
      }

      return val == null ? def : Long.parseLong(val);
   }

   public int getIntProperty(String key, String key2, int def) {
      String val = this.getProperty(key);
      if (val == null) {
         val = this.getProperty(key2);
      }

      return val == null ? def : Integer.parseInt(val);
   }

   public String getProperty(String key, String key2, String def) {
      String val = this.getProperty(key);
      return val == null ? this.getProperty(key2, def) : val;
   }

   public boolean removeBooleanProperty(String key, String key2, boolean def) {
      String val = this.removeProperty(key);
      if (val == null) {
         val = this.removeProperty(key2);
      } else {
         this.removeProperty(key2);
      }

      if (val == null) {
         return def;
      } else {
         return "t".equalsIgnoreCase(val) || "true".equalsIgnoreCase(val);
      }
   }

   public float removeFloatProperty(String key, String key2, float def) {
      String val = this.removeProperty(key);
      if (val == null) {
         val = this.removeProperty(key2);
      } else {
         this.removeProperty(key2);
      }

      return val == null ? def : Float.parseFloat(val);
   }

   public double removeDoubleProperty(String key, String key2, double def) {
      String val = this.removeProperty(key);
      if (val == null) {
         val = this.removeProperty(key2);
      } else {
         this.removeProperty(key2);
      }

      return val == null ? def : Double.parseDouble(val);
   }

   public long removeLongProperty(String key, String key2, long def) {
      String val = this.removeProperty(key);
      if (val == null) {
         val = this.removeProperty(key2);
      } else {
         this.removeProperty(key2);
      }

      return val == null ? def : Long.parseLong(val);
   }

   public int removeIntProperty(String key, String key2, int def) {
      String val = this.removeProperty(key);
      if (val == null) {
         val = this.removeProperty(key2);
      } else {
         this.removeProperty(key2);
      }

      return val == null ? def : Integer.parseInt(val);
   }

   public String removeProperty(String key, String key2, String def) {
      String val = this.removeProperty(key);
      return val == null ? this.removeProperty(key2, def) : val;
   }

   static {
      _primWrappers = new Object[][]{{Boolean.TYPE, Boolean.class, Boolean.FALSE}, {Byte.TYPE, Byte.class, new Byte((byte)0)}, {Character.TYPE, Character.class, new Character('\u0000')}, {Double.TYPE, Double.class, new Double(0.0)}, {Float.TYPE, Float.class, new Float(0.0F)}, {Integer.TYPE, Integer.class, new Integer(0)}, {Long.TYPE, Long.class, new Long(0L)}, {Short.TYPE, Short.class, new Short((short)0)}};
   }

   private static class EmptyOptions extends Options {
      private EmptyOptions() {
      }

      public Object setProperty(String key, String value) {
         throw new UnsupportedOperationException();
      }

      public Object put(Object key, Object value) {
         throw new UnsupportedOperationException();
      }

      // $FF: synthetic method
      EmptyOptions(Object x0) {
         this();
      }
   }
}
