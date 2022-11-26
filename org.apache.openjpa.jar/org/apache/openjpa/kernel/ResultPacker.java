package org.apache.openjpa.kernel;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.lib.util.J2DoPrivHelper;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.OpenJPAException;
import org.apache.openjpa.util.UserException;

public class ResultPacker {
   private static final Localizer _loc = Localizer.forPackage(ResultPacker.class);
   private static final Set _stdTypes = new HashSet();
   private final Class _resultClass;
   private final String[] _aliases;
   private final Member[] _sets;
   private final Method _put;
   private final Constructor _constructor;

   public ResultPacker(Class candidate, String alias, Class resultClass) {
      this(candidate, (Class[])null, new String[]{alias}, resultClass);
   }

   public ResultPacker(Class[] types, String[] aliases, Class resultClass) {
      this((Class)null, types, aliases, resultClass);
   }

   private ResultPacker(Class candidate, Class[] types, String[] aliases, Class resultClass) {
      this._aliases = aliases;
      if (resultClass.isPrimitive()) {
         this.assertConvertable(candidate, types, resultClass);
         this._resultClass = Filters.wrap(resultClass);
         this._sets = null;
         this._put = null;
         this._constructor = null;
      } else if (!_stdTypes.contains(this._resultClass = resultClass)) {
         Constructor cons = null;
         if (types != null && types.length > 0) {
            try {
               cons = this._resultClass.getConstructor(types);
            } catch (NoSuchMethodException var10) {
            }
         }

         this._constructor = cons;
         if (cons == null) {
            Method[] methods = this._resultClass.getMethods();
            Field[] fields = this._resultClass.getFields();
            this._put = findPut(methods);
            this._sets = new Member[aliases.length];

            for(int i = 0; i < this._sets.length; ++i) {
               Class type = types == null ? candidate : types[i];
               this._sets[i] = findSet(aliases[i], type, fields, methods);
               if (this._sets[i] == null && this._put == null) {
                  throw new UserException(_loc.get("cant-set", resultClass, aliases[i], types == null ? null : Arrays.asList(types)));
               }
            }
         } else {
            this._sets = null;
            this._put = null;
         }
      } else {
         if (resultClass != Map.class && resultClass != HashMap.class && resultClass != Object[].class) {
            this.assertConvertable(candidate, types, resultClass);
         }

         this._sets = null;
         this._put = null;
         this._constructor = null;
      }

   }

   private void assertConvertable(Class candidate, Class[] types, Class resultClass) {
      Class c = types == null ? candidate : types[0];
      if (types != null && types.length != 1 || c != null && c != Object.class && !Filters.canConvert(c, resultClass, true)) {
         throw new UserException(_loc.get("cant-convert-result", c, resultClass));
      }
   }

   public Object pack(Object result) {
      if (this._resultClass == Object.class) {
         return result;
      } else if (this._resultClass == Object[].class) {
         return new Object[]{result};
      } else if (this._resultClass != HashMap.class && this._resultClass != Map.class) {
         return this._constructor == null && this._sets == null ? Filters.convert(result, this._resultClass) : this.packUserType(new Object[]{result});
      } else {
         HashMap map = new HashMap(1, 1.0F);
         map.put(this._aliases[0], result);
         return map;
      }
   }

   public Object pack(Object[] result) {
      if (result != null && result.length != 0) {
         if (this._resultClass == Object[].class) {
            if (result.length > this._aliases.length) {
               Object[] trim = new Object[this._aliases.length];
               System.arraycopy(result, 0, trim, 0, trim.length);
               return trim;
            } else {
               return result;
            }
         } else if (this._resultClass == Object.class) {
            return result[0];
         } else if (this._resultClass != HashMap.class && this._resultClass != Map.class) {
            return this._sets == null && this._constructor == null ? Filters.convert(result[0], this._resultClass) : this.packUserType(result);
         } else {
            Map map = new HashMap(result.length);

            for(int i = 0; i < this._aliases.length; ++i) {
               map.put(this._aliases[i], result[i]);
            }

            return map;
         }
      } else {
         return null;
      }
   }

   private Object packUserType(Object[] result) {
      try {
         if (this._constructor != null) {
            return this._constructor.newInstance(result);
         } else {
            Object user = AccessController.doPrivileged(J2DoPrivHelper.newInstanceAction(this._resultClass));

            for(int i = 0; i < this._aliases.length; ++i) {
               if (this._sets[i] instanceof Method) {
                  Method meth = (Method)this._sets[i];
                  meth.invoke(user, Filters.convert(result[i], meth.getParameterTypes()[0]));
               } else if (this._sets[i] instanceof Field) {
                  Field field = (Field)this._sets[i];
                  field.set(user, Filters.convert(result[i], field.getType()));
               } else if (this._put != null) {
                  this._put.invoke(user, this._aliases[i], result[i]);
               }
            }

            return user;
         }
      } catch (OpenJPAException var5) {
         throw var5;
      } catch (PrivilegedActionException var6) {
         throw new UserException(_loc.get("pack-instantiation-err", (Object)this._resultClass), var6.getException());
      } catch (InstantiationException var7) {
         throw new UserException(_loc.get("pack-instantiation-err", (Object)this._resultClass), var7);
      } catch (Exception var8) {
         throw new UserException(_loc.get("pack-err", (Object)this._resultClass), var8);
      }
   }

   private static Member findSet(String alias, Class type, Field[] fields, Method[] methods) {
      if (StringUtils.isEmpty(alias)) {
         return null;
      } else {
         if (type == Object.class) {
            type = null;
         }

         Field field = null;

         for(int i = 0; i < fields.length; ++i) {
            if (fields[i].getName().equals(alias)) {
               if (type != null && !Filters.canConvert(type, fields[i].getType(), true)) {
                  break;
               }

               return fields[i];
            }

            if (field == null && fields[i].getName().equalsIgnoreCase(alias) && (type == null || Filters.canConvert(type, fields[i].getType(), true))) {
               field = fields[i];
            }
         }

         String setName = "set" + StringUtils.capitalize(alias);
         Method method = null;
         boolean eqName = false;

         for(int i = 0; i < methods.length; ++i) {
            if (methods[i].getName().equalsIgnoreCase(setName)) {
               Class[] params = methods[i].getParameterTypes();
               if (params.length == 1) {
                  if (type != null && params[0] == Object.class) {
                     if (methods[i].getName().equals(setName)) {
                        eqName = true;
                        method = methods[i];
                     } else if (method == null) {
                        method = methods[i];
                     }
                  } else if (type == null || Filters.canConvert(type, params[0], true)) {
                     if (methods[i].getName().equals(setName)) {
                        return methods[i];
                     }

                     if (method == null || !eqName) {
                        method = methods[i];
                     }
                  }
               }
            }
         }

         if (!eqName && field != null) {
            return field;
         } else {
            return method;
         }
      }
   }

   private static Method findPut(Method[] methods) {
      for(int i = 0; i < methods.length; ++i) {
         if (methods[i].getName().equals("put")) {
            Class[] params = methods[i].getParameterTypes();
            if (params.length == 2 && params[0] == Object.class && params[1] == Object.class) {
               return methods[i];
            }
         }
      }

      return null;
   }

   static {
      _stdTypes.add(Object[].class);
      _stdTypes.add(Object.class);
      _stdTypes.add(Map.class);
      _stdTypes.add(HashMap.class);
      _stdTypes.add(Character.class);
      _stdTypes.add(Boolean.class);
      _stdTypes.add(Byte.class);
      _stdTypes.add(Short.class);
      _stdTypes.add(Integer.class);
      _stdTypes.add(Long.class);
      _stdTypes.add(Float.class);
      _stdTypes.add(Double.class);
      _stdTypes.add(String.class);
      _stdTypes.add(BigInteger.class);
      _stdTypes.add(BigDecimal.class);
      _stdTypes.add(Date.class);
      _stdTypes.add(java.sql.Date.class);
      _stdTypes.add(Time.class);
      _stdTypes.add(Timestamp.class);
      _stdTypes.add(Calendar.class);
      _stdTypes.add(GregorianCalendar.class);
   }
}
