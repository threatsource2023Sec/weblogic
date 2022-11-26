package org.apache.openjpa.kernel;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.apache.openjpa.enhance.Reflection;
import org.apache.openjpa.kernel.exps.AggregateListener;
import org.apache.openjpa.kernel.exps.FilterListener;
import org.apache.openjpa.lib.util.J2DoPrivHelper;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.util.InternalException;
import org.apache.openjpa.util.UserException;
import serp.util.Numbers;
import serp.util.Strings;

public class Filters {
   private static final BigDecimal ZERO_BIGDECIMAL = new BigDecimal(0.0);
   private static final BigInteger ZERO_BIGINTEGER = new BigInteger("0");
   private static final int OP_ADD = 0;
   private static final int OP_SUBTRACT = 1;
   private static final int OP_MULTIPLY = 2;
   private static final int OP_DIVIDE = 3;
   private static final int OP_MOD = 4;
   private static final Localizer _loc = Localizer.forPackage(Filters.class);

   public static Class wrap(Class c) {
      if (!c.isPrimitive()) {
         return c;
      } else if (c == Integer.TYPE) {
         return Integer.class;
      } else if (c == Float.TYPE) {
         return Float.class;
      } else if (c == Double.TYPE) {
         return Double.class;
      } else if (c == Long.TYPE) {
         return Long.class;
      } else if (c == Boolean.TYPE) {
         return Boolean.class;
      } else if (c == Short.TYPE) {
         return Short.class;
      } else if (c == Byte.TYPE) {
         return Byte.class;
      } else {
         return c == Character.TYPE ? Character.class : c;
      }
   }

   public static Class unwrap(Class c) {
      if (!c.isPrimitive() && c != String.class) {
         if (c == Integer.class) {
            return Integer.TYPE;
         } else if (c == Float.class) {
            return Float.TYPE;
         } else if (c == Double.class) {
            return Double.TYPE;
         } else if (c == Long.class) {
            return Long.TYPE;
         } else if (c == Boolean.class) {
            return Boolean.TYPE;
         } else if (c == Short.class) {
            return Short.TYPE;
         } else if (c == Byte.class) {
            return Byte.TYPE;
         } else {
            return c == Character.class ? Character.TYPE : c;
         }
      } else {
         return c;
      }
   }

   public static Class promote(Class c1, Class c2) {
      if (c1 == c2) {
         return unwrap(c1);
      } else {
         Class w1 = wrap(c1);
         Class w2 = wrap(c2);
         if (w1 == w2) {
            return unwrap(c1);
         } else {
            boolean w1Number = Number.class.isAssignableFrom(w1);
            boolean w2Number = Number.class.isAssignableFrom(w2);
            if (w1Number && w2Number) {
               if (w1 != BigDecimal.class && w2 != BigDecimal.class) {
                  if (w1 == BigInteger.class) {
                     return w2 != Float.class && w2 != Double.class ? BigInteger.class : BigDecimal.class;
                  } else if (w2 == BigInteger.class) {
                     return w1 != Float.class && w1 != Double.class ? BigInteger.class : BigDecimal.class;
                  } else if (w1 != Double.class && w2 != Double.class) {
                     if (w1 != Float.class && w2 != Float.class) {
                        return w1 != Long.class && w2 != Long.class ? Integer.TYPE : Long.TYPE;
                     } else {
                        return Float.TYPE;
                     }
                  } else {
                     return Double.TYPE;
                  }
               } else {
                  return BigDecimal.class;
               }
            } else {
               if (!w1Number) {
                  if (w2Number && (w1 == Character.class || w1 == String.class)) {
                     return w2 != Byte.class && w2 != Short.class ? unwrap(c2) : Integer.class;
                  }

                  if (!w2Number && w1 == Character.class && w2 == String.class) {
                     return String.class;
                  }

                  if (w2Number) {
                     return unwrap(c2);
                  }
               }

               if (!w2Number) {
                  if (w1Number && (w2 == Character.class || w2 == String.class)) {
                     return w1 != Byte.class && w1 != Short.class ? unwrap(c1) : Integer.class;
                  }

                  if (!w1Number && w2 == Character.class && w1 == String.class) {
                     return String.class;
                  }

                  if (w1Number) {
                     return unwrap(c1);
                  }
               }

               if (!w1Number && !w2Number) {
                  if (w1 == Object.class) {
                     return unwrap(c2);
                  }

                  if (w2 == Object.class) {
                     return unwrap(c1);
                  }

                  if (w1.isAssignableFrom(w2)) {
                     return unwrap(c1);
                  }

                  if (w2.isAssignableFrom(w1)) {
                     return unwrap(c2);
                  }

                  if (isNonstandardType(w1)) {
                     return isNonstandardType(w2) ? Object.class : unwrap(c2);
                  }

                  if (isNonstandardType(w2)) {
                     return isNonstandardType(w1) ? Object.class : unwrap(c1);
                  }
               }

               return Object.class;
            }
         }
      }
   }

   private static boolean isNonstandardType(Class var0) {
      // $FF: Couldn't be decompiled
   }

   public static boolean canConvert(Class c1, Class c2, boolean strict) {
      c1 = wrap(c1);
      c2 = wrap(c2);
      if (c2.isAssignableFrom(c1)) {
         return true;
      } else {
         boolean c1Number = Number.class.isAssignableFrom(c1);
         boolean c2Number = Number.class.isAssignableFrom(c2);
         if (c1Number && c2Number) {
            return true;
         } else if (c1Number && (c2 == Character.class || !strict && c2 == String.class) || c2Number && (c1 == Character.class || !strict && c1 == String.class)) {
            return true;
         } else if (c1 == String.class && c2 == Character.class) {
            return true;
         } else if (c2 == String.class) {
            return !strict;
         } else {
            return false;
         }
      }
   }

   public static Object convert(Object o, Class type) {
      if (o == null) {
         return null;
      } else if (o.getClass() == type) {
         return o;
      } else {
         type = wrap(type);
         if (type.isAssignableFrom(o.getClass())) {
            return o;
         } else {
            boolean num = o instanceof Number;
            if (!num) {
               if (type == String.class) {
                  return o.toString();
               }

               if (type == Character.class) {
                  String str = o.toString();
                  if (str != null && str.length() == 1) {
                     return new Character(str.charAt(0));
                  }
               } else {
                  if (Calendar.class.isAssignableFrom(type) && o instanceof Date) {
                     Calendar cal = Calendar.getInstance();
                     cal.setTime((Date)o);
                     return cal;
                  }

                  if (Date.class.isAssignableFrom(type) && o instanceof Calendar) {
                     return ((Calendar)o).getTime();
                  }

                  if (Number.class.isAssignableFrom(type)) {
                     Integer i = null;
                     if (o instanceof Character) {
                        i = Numbers.valueOf((Character)o);
                     } else if (o instanceof String && ((String)o).length() == 1) {
                        i = Numbers.valueOf(((String)o).charAt(0));
                     }

                     if (i != null) {
                        if (type == Integer.class) {
                           return i;
                        }

                        num = true;
                     }
                  }
               }
            }

            if (!num) {
               throw new ClassCastException(_loc.get("cant-convert", o, o.getClass(), type).getMessage());
            } else if (type == Integer.class) {
               return Numbers.valueOf(((Number)o).intValue());
            } else if (type == Float.class) {
               return new Float(((Number)o).floatValue());
            } else if (type == Double.class) {
               return new Double(((Number)o).doubleValue());
            } else if (type == Long.class) {
               return Numbers.valueOf(((Number)o).longValue());
            } else if (type == BigDecimal.class) {
               double dval = ((Number)o).doubleValue();
               if (!Double.isNaN(dval) && !Double.isInfinite(dval)) {
                  float fval = ((Number)o).floatValue();
                  return !Float.isNaN(fval) && !Float.isInfinite(fval) ? new BigDecimal(o.toString()) : new Float(fval);
               } else {
                  return new Double(dval);
               }
            } else if (type == BigInteger.class) {
               return new BigInteger(o.toString());
            } else if (type == Short.class) {
               return new Short(((Number)o).shortValue());
            } else {
               return type == Byte.class ? new Byte(((Number)o).byteValue()) : Numbers.valueOf(((Number)o).intValue());
            }
         }
      }
   }

   public static Object add(Object o1, Class c1, Object o2, Class c2) {
      return op(o1, c1, o2, c2, 0);
   }

   public static Object subtract(Object o1, Class c1, Object o2, Class c2) {
      return op(o1, c1, o2, c2, 1);
   }

   public static Object multiply(Object o1, Class c1, Object o2, Class c2) {
      return op(o1, c1, o2, c2, 2);
   }

   public static Object divide(Object o1, Class c1, Object o2, Class c2) {
      return op(o1, c1, o2, c2, 3);
   }

   public static Object mod(Object o1, Class c1, Object o2, Class c2) {
      return op(o1, c1, o2, c2, 4);
   }

   private static Object op(Object o1, Class c1, Object o2, Class c2, int op) {
      Class promote = promote(c1, c2);
      int n1;
      int n2;
      if (promote == Integer.TYPE) {
         n1 = o1 == null ? 0 : ((Number)o1).intValue();
         n2 = o2 == null ? 0 : ((Number)o2).intValue();
         return op(n1, n2, op);
      } else if (promote == Float.TYPE) {
         float n1 = o1 == null ? 0.0F : ((Number)o1).floatValue();
         float n2 = o2 == null ? 0.0F : ((Number)o2).floatValue();
         return op(n1, n2, op);
      } else if (promote == Double.TYPE) {
         double n1 = o1 == null ? 0.0 : ((Number)o1).doubleValue();
         double n2 = o2 == null ? 0.0 : ((Number)o2).doubleValue();
         return op(n1, n2, op);
      } else if (promote == Long.TYPE) {
         long n1 = o1 == null ? 0L : ((Number)o1).longValue();
         long n2 = o2 == null ? 0L : ((Number)o2).longValue();
         return op(n1, n2, op);
      } else if (promote == BigDecimal.class) {
         BigDecimal n1 = o1 == null ? ZERO_BIGDECIMAL : (BigDecimal)convert(o1, promote);
         BigDecimal n2 = o2 == null ? ZERO_BIGDECIMAL : (BigDecimal)convert(o2, promote);
         return op(n1, n2, op);
      } else if (promote == BigInteger.class) {
         BigInteger n1 = o1 == null ? ZERO_BIGINTEGER : (BigInteger)convert(o1, promote);
         BigInteger n2 = o2 == null ? ZERO_BIGINTEGER : (BigInteger)convert(o2, promote);
         return op(n1, n2, op);
      } else {
         n1 = o1 == null ? 0 : ((Number)o1).intValue();
         n2 = o2 == null ? 0 : ((Number)o2).intValue();
         return op(n1, n2, op);
      }
   }

   private static Object op(int n1, int n2, int op) {
      int tot;
      switch (op) {
         case 0:
            tot = n1 + n2;
            break;
         case 1:
            tot = n1 - n2;
            break;
         case 2:
            tot = n1 * n2;
            break;
         case 3:
            tot = n1 / n2;
            break;
         case 4:
            tot = n1 % n2;
            break;
         default:
            throw new InternalException();
      }

      return Numbers.valueOf(tot);
   }

   private static Object op(float n1, float n2, int op) {
      float tot;
      switch (op) {
         case 0:
            tot = n1 + n2;
            break;
         case 1:
            tot = n1 - n2;
            break;
         case 2:
            tot = n1 * n2;
            break;
         case 3:
            tot = n1 / n2;
            break;
         case 4:
            tot = n1 % n2;
            break;
         default:
            throw new InternalException();
      }

      return new Float(tot);
   }

   private static Object op(double n1, double n2, int op) {
      double tot;
      switch (op) {
         case 0:
            tot = n1 + n2;
            break;
         case 1:
            tot = n1 - n2;
            break;
         case 2:
            tot = n1 * n2;
            break;
         case 3:
            tot = n1 / n2;
            break;
         case 4:
            tot = n1 % n2;
            break;
         default:
            throw new InternalException();
      }

      return new Double(tot);
   }

   private static Object op(long n1, long n2, int op) {
      long tot;
      switch (op) {
         case 0:
            tot = n1 + n2;
            break;
         case 1:
            tot = n1 - n2;
            break;
         case 2:
            tot = n1 * n2;
            break;
         case 3:
            tot = n1 / n2;
            break;
         case 4:
            tot = n1 % n2;
            break;
         default:
            throw new InternalException();
      }

      return Numbers.valueOf(tot);
   }

   private static Object op(BigDecimal n1, BigDecimal n2, int op) {
      switch (op) {
         case 0:
            return n1.add(n2);
         case 1:
            return n1.subtract(n2);
         case 2:
            return n1.multiply(n2);
         case 3:
            int scale = Math.max(n1.scale(), n2.scale());
            return n1.divide(n2, scale, 4);
         case 4:
            throw new UserException(_loc.get("mod-bigdecimal"));
         default:
            throw new InternalException();
      }
   }

   private static Object op(BigInteger n1, BigInteger n2, int op) {
      switch (op) {
         case 0:
            return n1.add(n2);
         case 1:
            return n1.subtract(n2);
         case 2:
            return n1.multiply(n2);
         case 3:
            return n1.divide(n2);
         default:
            throw new InternalException();
      }
   }

   public static List parseDeclaration(String dec, char split, String decType) {
      if (dec == null) {
         return null;
      } else {
         char bad = 0;
         if (split == ',') {
            bad = 59;
         } else if (split == ';') {
            bad = 44;
         }

         char sentinal = ' ';
         int start = 0;
         boolean skipSpace = false;
         List results = new ArrayList(6);

         for(int i = 0; i < dec.length(); ++i) {
            char cur = dec.charAt(i);
            if (cur == bad) {
               throw new UserException(_loc.get("bad-dec", dec, decType));
            }

            if (cur == ' ' && skipSpace) {
               ++start;
            } else {
               skipSpace = false;
               if (cur == sentinal) {
                  sentinal = sentinal == ' ' ? split : 32;
                  results.add(dec.substring(start, i).trim());
                  start = i + 1;
                  skipSpace = true;
               }
            }
         }

         if (start < dec.length()) {
            results.add(dec.substring(start));
         }

         if (!results.isEmpty() && results.size() % 2 == 0) {
            return results;
         } else {
            throw new UserException(_loc.get("bad-dec", dec, decType));
         }
      }
   }

   public static List splitExpressions(String str, char split, int expected) {
      if (str == null) {
         return null;
      } else {
         List exps = null;
         int parenDepth = 0;
         int begin = 0;
         int pos = 0;
         boolean escape = false;
         boolean string = false;
         boolean nonspace = false;

         for(char quote = 0; pos < str.length(); ++pos) {
            char c = str.charAt(pos);
            if (c == '\\') {
               escape = !escape;
            } else if (escape) {
               escape = false;
            } else {
               switch (c) {
                  case '\t':
                  case '\n':
                  case '\r':
                  case ' ':
                     if (c == split && !string && parenDepth == 0 && nonspace) {
                        if (exps == null) {
                           exps = new ArrayList(expected);
                        }

                        exps.add(str.substring(begin, pos).trim());
                        begin = pos + 1;
                        nonspace = false;
                     }
                     break;
                  case '"':
                  case '\'':
                     if (string && quote == c) {
                        string = false;
                     } else if (!string) {
                        quote = c;
                        string = true;
                     }

                     nonspace = true;
                     break;
                  case '(':
                     if (!string) {
                        ++parenDepth;
                     }

                     nonspace = true;
                     break;
                  case ')':
                     if (!string) {
                        --parenDepth;
                     }

                     nonspace = true;
                     break;
                  default:
                     if (c == split && !string && parenDepth == 0) {
                        if (exps == null) {
                           exps = new ArrayList(expected);
                        }

                        exps.add(str.substring(begin, pos).trim());
                        begin = pos + 1;
                     }

                     nonspace = true;
               }

               escape = false;
            }
         }

         if (exps == null) {
            exps = new ArrayList(1);
            exps.add(str);
            return exps;
         } else {
            String last = str.substring(begin).trim();
            if (last.length() > 0) {
               exps.add(last);
            }

            return exps;
         }
      }
   }

   public static List addAccessPathMetaDatas(List metas, ClassMetaData[] path) {
      if (path != null && path.length != 0) {
         if (metas == null) {
            metas = new ArrayList();
         }

         int last = ((List)metas).size();

         for(int i = 0; i < path.length; ++i) {
            boolean add = true;

            for(int j = 0; add && j < last; ++j) {
               ClassMetaData meta = (ClassMetaData)((List)metas).get(j);
               if (meta.getDescribedType().isAssignableFrom(path[i].getDescribedType())) {
                  add = false;
               } else if (path[i].getDescribedType().isAssignableFrom(meta.getDescribedType())) {
                  add = false;
                  ((List)metas).set(j, path[i]);
               }
            }

            if (add) {
               ((List)metas).add(path[i]);
            }
         }

         return (List)metas;
      } else {
         return (List)metas;
      }
   }

   public static AggregateListener hintToAggregateListener(Object hint, ClassLoader loader) {
      if (hint == null) {
         return null;
      } else if (hint instanceof AggregateListener) {
         return (AggregateListener)hint;
      } else {
         Exception cause = null;
         if (hint instanceof String) {
            try {
               return (AggregateListener)AccessController.doPrivileged(J2DoPrivHelper.newInstanceAction(Class.forName((String)hint, true, loader)));
            } catch (Exception var4) {
               Exception e = var4;
               if (var4 instanceof PrivilegedActionException) {
                  e = ((PrivilegedActionException)var4).getException();
               }

               cause = e;
            }
         }

         throw (new UserException(_loc.get("bad-agg-listener-hint", hint, hint.getClass()))).setCause(cause);
      }
   }

   public static AggregateListener[] hintToAggregateListeners(Object hint, ClassLoader loader) {
      if (hint == null) {
         return null;
      } else if (hint instanceof AggregateListener[]) {
         return (AggregateListener[])((AggregateListener[])hint);
      } else if (hint instanceof AggregateListener) {
         return new AggregateListener[]{(AggregateListener)hint};
      } else if (hint instanceof Collection) {
         Collection c = (Collection)hint;
         return (AggregateListener[])((AggregateListener[])c.toArray(new AggregateListener[c.size()]));
      } else {
         Exception cause = null;
         if (hint instanceof String) {
            String[] clss = Strings.split((String)hint, ",", 0);
            AggregateListener[] aggs = new AggregateListener[clss.length];

            try {
               for(int i = 0; i < clss.length; ++i) {
                  aggs[i] = (AggregateListener)AccessController.doPrivileged(J2DoPrivHelper.newInstanceAction(Class.forName(clss[i], true, loader)));
               }

               return aggs;
            } catch (Exception var6) {
               Exception e = var6;
               if (var6 instanceof PrivilegedActionException) {
                  e = ((PrivilegedActionException)var6).getException();
               }

               cause = e;
            }
         }

         throw (new UserException(_loc.get("bad-agg-listener-hint", hint, hint.getClass()))).setCause(cause);
      }
   }

   public static FilterListener hintToFilterListener(Object hint, ClassLoader loader) {
      if (hint == null) {
         return null;
      } else if (hint instanceof FilterListener) {
         return (FilterListener)hint;
      } else {
         Exception cause = null;
         if (hint instanceof String) {
            try {
               return (FilterListener)AccessController.doPrivileged(J2DoPrivHelper.newInstanceAction(Class.forName((String)hint, true, loader)));
            } catch (Exception var4) {
               Exception e = var4;
               if (var4 instanceof PrivilegedActionException) {
                  e = ((PrivilegedActionException)var4).getException();
               }

               cause = e;
            }
         }

         throw (new UserException(_loc.get("bad-filter-listener-hint", hint, hint.getClass()))).setCause(cause);
      }
   }

   public static FilterListener[] hintToFilterListeners(Object hint, ClassLoader loader) {
      if (hint == null) {
         return null;
      } else if (hint instanceof FilterListener[]) {
         return (FilterListener[])((FilterListener[])hint);
      } else if (hint instanceof FilterListener) {
         return new FilterListener[]{(FilterListener)hint};
      } else if (hint instanceof Collection) {
         Collection c = (Collection)hint;
         return (FilterListener[])((FilterListener[])c.toArray(new FilterListener[c.size()]));
      } else {
         Exception cause = null;
         if (hint instanceof String) {
            String[] clss = Strings.split((String)hint, ",", 0);
            FilterListener[] filts = new FilterListener[clss.length];

            try {
               for(int i = 0; i < clss.length; ++i) {
                  filts[i] = (FilterListener)AccessController.doPrivileged(J2DoPrivHelper.newInstanceAction(Class.forName(clss[i], true, loader)));
               }

               return filts;
            } catch (Exception var6) {
               Exception e = var6;
               if (var6 instanceof PrivilegedActionException) {
                  e = ((PrivilegedActionException)var6).getException();
               }

               cause = e;
            }
         }

         throw (new UserException(_loc.get("bad-filter-listener-hint", hint, hint.getClass()))).setCause(cause);
      }
   }

   public static Object hintToGetter(Object target, String hintKey) {
      if (target != null && hintKey != null) {
         Method getter = Reflection.findGetter(target.getClass(), hintKey, true);
         return Reflection.get(target, getter);
      } else {
         return null;
      }
   }

   public static void hintToSetter(Object target, String hintKey, Object value) {
      if (target != null && hintKey != null) {
         Method setter = Reflection.findSetter(target.getClass(), hintKey, true);
         if (value instanceof String) {
            if ("null".equals(value)) {
               value = null;
            } else {
               try {
                  value = Strings.parse((String)value, setter.getParameterTypes()[0]);
               } catch (Exception var5) {
                  throw (new UserException(_loc.get("bad-setter-hint-arg", hintKey, value, setter.getParameterTypes()[0]))).setCause(var5);
               }
            }
         }

         Reflection.set(target, setter, value);
      }
   }
}
