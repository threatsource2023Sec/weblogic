package org.apache.taglibs.standard.lang.jstl;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;

public class Coercions {
   public static Object coerce(Object pValue, Class pClass, Logger pLogger) throws ELException {
      if (pClass == String.class) {
         return coerceToString(pValue, pLogger);
      } else if (isPrimitiveNumberClass(pClass)) {
         return coerceToPrimitiveNumber(pValue, pClass, pLogger);
      } else if (pClass != Character.class && pClass != Character.TYPE) {
         return pClass != Boolean.class && pClass != Boolean.TYPE ? coerceToObject(pValue, pClass, pLogger) : coerceToBoolean(pValue, pLogger);
      } else {
         return coerceToCharacter(pValue, pLogger);
      }
   }

   static boolean isPrimitiveNumberClass(Class pClass) {
      return pClass == Byte.class || pClass == Byte.TYPE || pClass == Short.class || pClass == Short.TYPE || pClass == Integer.class || pClass == Integer.TYPE || pClass == Long.class || pClass == Long.TYPE || pClass == Float.class || pClass == Float.TYPE || pClass == Double.class || pClass == Double.TYPE;
   }

   public static String coerceToString(Object pValue, Logger pLogger) throws ELException {
      if (pValue == null) {
         return "";
      } else if (pValue instanceof String) {
         return (String)pValue;
      } else {
         try {
            return pValue.toString();
         } catch (Exception var3) {
            if (pLogger.isLoggingError()) {
               pLogger.logError(Constants.TOSTRING_EXCEPTION, (Throwable)var3, pValue.getClass().getName());
            }

            return "";
         }
      }
   }

   public static Number coerceToPrimitiveNumber(Object pValue, Class pClass, Logger pLogger) throws ELException {
      if (pValue != null && !"".equals(pValue)) {
         if (pValue instanceof Character) {
            char val = (Character)pValue;
            return coerceToPrimitiveNumber((long)((short)val), pClass);
         } else if (pValue instanceof Boolean) {
            if (pLogger.isLoggingError()) {
               pLogger.logError(Constants.BOOLEAN_TO_NUMBER, (Object)pValue, pClass.getName());
            }

            return coerceToPrimitiveNumber(0L, pClass);
         } else if (pValue.getClass() == pClass) {
            return (Number)pValue;
         } else if (pValue instanceof Number) {
            return coerceToPrimitiveNumber((Number)pValue, pClass);
         } else if (pValue instanceof String) {
            try {
               return coerceToPrimitiveNumber((String)pValue, pClass);
            } catch (Exception var4) {
               if (pLogger.isLoggingError()) {
                  pLogger.logError(Constants.STRING_TO_NUMBER_EXCEPTION, (Object)((String)pValue), pClass.getName());
               }

               return coerceToPrimitiveNumber(0L, pClass);
            }
         } else {
            if (pLogger.isLoggingError()) {
               pLogger.logError(Constants.COERCE_TO_NUMBER, (Object)pValue.getClass().getName(), pClass.getName());
            }

            return coerceToPrimitiveNumber(0L, pClass);
         }
      } else {
         return coerceToPrimitiveNumber(0L, pClass);
      }
   }

   public static Integer coerceToInteger(Object pValue, Logger pLogger) throws ELException {
      if (pValue == null) {
         return null;
      } else if (pValue instanceof Character) {
         return PrimitiveObjects.getInteger((Character)pValue);
      } else if (pValue instanceof Boolean) {
         if (pLogger.isLoggingWarning()) {
            pLogger.logWarning(Constants.BOOLEAN_TO_NUMBER, (Object)pValue, Integer.class.getName());
         }

         return PrimitiveObjects.getInteger((Boolean)pValue ? 1 : 0);
      } else if (pValue instanceof Integer) {
         return (Integer)pValue;
      } else if (pValue instanceof Number) {
         return PrimitiveObjects.getInteger(((Number)pValue).intValue());
      } else if (pValue instanceof String) {
         try {
            return Integer.valueOf((String)pValue);
         } catch (Exception var3) {
            if (pLogger.isLoggingWarning()) {
               pLogger.logWarning(Constants.STRING_TO_NUMBER_EXCEPTION, (Object)((String)pValue), Integer.class.getName());
            }

            return null;
         }
      } else {
         if (pLogger.isLoggingWarning()) {
            pLogger.logWarning(Constants.COERCE_TO_NUMBER, (Object)pValue.getClass().getName(), Integer.class.getName());
         }

         return null;
      }
   }

   static Number coerceToPrimitiveNumber(long pValue, Class pClass) throws ELException {
      if (pClass != Byte.class && pClass != Byte.TYPE) {
         if (pClass != Short.class && pClass != Short.TYPE) {
            if (pClass != Integer.class && pClass != Integer.TYPE) {
               if (pClass != Long.class && pClass != Long.TYPE) {
                  if (pClass != Float.class && pClass != Float.TYPE) {
                     return (Number)(pClass != Double.class && pClass != Double.TYPE ? PrimitiveObjects.getInteger(0) : PrimitiveObjects.getDouble((double)pValue));
                  } else {
                     return PrimitiveObjects.getFloat((float)pValue);
                  }
               } else {
                  return PrimitiveObjects.getLong(pValue);
               }
            } else {
               return PrimitiveObjects.getInteger((int)pValue);
            }
         } else {
            return PrimitiveObjects.getShort((short)((int)pValue));
         }
      } else {
         return PrimitiveObjects.getByte((byte)((int)pValue));
      }
   }

   static Number coerceToPrimitiveNumber(double pValue, Class pClass) throws ELException {
      if (pClass != Byte.class && pClass != Byte.TYPE) {
         if (pClass != Short.class && pClass != Short.TYPE) {
            if (pClass != Integer.class && pClass != Integer.TYPE) {
               if (pClass != Long.class && pClass != Long.TYPE) {
                  if (pClass != Float.class && pClass != Float.TYPE) {
                     return (Number)(pClass != Double.class && pClass != Double.TYPE ? PrimitiveObjects.getInteger(0) : PrimitiveObjects.getDouble(pValue));
                  } else {
                     return PrimitiveObjects.getFloat((float)pValue);
                  }
               } else {
                  return PrimitiveObjects.getLong((long)pValue);
               }
            } else {
               return PrimitiveObjects.getInteger((int)pValue);
            }
         } else {
            return PrimitiveObjects.getShort((short)((int)pValue));
         }
      } else {
         return PrimitiveObjects.getByte((byte)((int)pValue));
      }
   }

   static Number coerceToPrimitiveNumber(Number pValue, Class pClass) throws ELException {
      if (pClass != Byte.class && pClass != Byte.TYPE) {
         if (pClass != Short.class && pClass != Short.TYPE) {
            if (pClass != Integer.class && pClass != Integer.TYPE) {
               if (pClass != Long.class && pClass != Long.TYPE) {
                  if (pClass != Float.class && pClass != Float.TYPE) {
                     return (Number)(pClass != Double.class && pClass != Double.TYPE ? PrimitiveObjects.getInteger(0) : PrimitiveObjects.getDouble(pValue.doubleValue()));
                  } else {
                     return PrimitiveObjects.getFloat(pValue.floatValue());
                  }
               } else {
                  return PrimitiveObjects.getLong(pValue.longValue());
               }
            } else {
               return PrimitiveObjects.getInteger(pValue.intValue());
            }
         } else {
            return PrimitiveObjects.getShort(pValue.shortValue());
         }
      } else {
         return PrimitiveObjects.getByte(pValue.byteValue());
      }
   }

   static Number coerceToPrimitiveNumber(String pValue, Class pClass) throws ELException {
      if (pClass != Byte.class && pClass != Byte.TYPE) {
         if (pClass != Short.class && pClass != Short.TYPE) {
            if (pClass != Integer.class && pClass != Integer.TYPE) {
               if (pClass != Long.class && pClass != Long.TYPE) {
                  if (pClass != Float.class && pClass != Float.TYPE) {
                     return (Number)(pClass != Double.class && pClass != Double.TYPE ? PrimitiveObjects.getInteger(0) : Double.valueOf(pValue));
                  } else {
                     return Float.valueOf(pValue);
                  }
               } else {
                  return Long.valueOf(pValue);
               }
            } else {
               return Integer.valueOf(pValue);
            }
         } else {
            return Short.valueOf(pValue);
         }
      } else {
         return Byte.valueOf(pValue);
      }
   }

   public static Character coerceToCharacter(Object pValue, Logger pLogger) throws ELException {
      if (pValue != null && !"".equals(pValue)) {
         if (pValue instanceof Character) {
            return (Character)pValue;
         } else if (pValue instanceof Boolean) {
            if (pLogger.isLoggingError()) {
               pLogger.logError(Constants.BOOLEAN_TO_CHARACTER, pValue);
            }

            return PrimitiveObjects.getCharacter('\u0000');
         } else if (pValue instanceof Number) {
            return PrimitiveObjects.getCharacter((char)((Number)pValue).shortValue());
         } else if (pValue instanceof String) {
            String str = (String)pValue;
            return PrimitiveObjects.getCharacter(str.charAt(0));
         } else {
            if (pLogger.isLoggingError()) {
               pLogger.logError(Constants.COERCE_TO_CHARACTER, (Object)pValue.getClass().getName());
            }

            return PrimitiveObjects.getCharacter('\u0000');
         }
      } else {
         return PrimitiveObjects.getCharacter('\u0000');
      }
   }

   public static Boolean coerceToBoolean(Object pValue, Logger pLogger) throws ELException {
      if (pValue != null && !"".equals(pValue)) {
         if (pValue instanceof Boolean) {
            return (Boolean)pValue;
         } else if (pValue instanceof String) {
            String str = (String)pValue;

            try {
               return Boolean.valueOf(str);
            } catch (Exception var4) {
               if (pLogger.isLoggingError()) {
                  pLogger.logError(Constants.STRING_TO_BOOLEAN, (Throwable)var4, (String)pValue);
               }

               return Boolean.FALSE;
            }
         } else {
            if (pLogger.isLoggingError()) {
               pLogger.logError(Constants.COERCE_TO_BOOLEAN, (Object)pValue.getClass().getName());
            }

            return Boolean.TRUE;
         }
      } else {
         return Boolean.FALSE;
      }
   }

   public static Object coerceToObject(Object pValue, Class pClass, Logger pLogger) throws ELException {
      if (pValue == null) {
         return null;
      } else if (pClass.isAssignableFrom(pValue.getClass())) {
         return pValue;
      } else if (!(pValue instanceof String)) {
         if (pLogger.isLoggingError()) {
            pLogger.logError(Constants.COERCE_TO_OBJECT, (Object)pValue.getClass().getName(), pClass.getName());
         }

         return null;
      } else {
         String str = (String)pValue;
         PropertyEditor pe = PropertyEditorManager.findEditor(pClass);
         if (pe == null) {
            if ("".equals(str)) {
               return null;
            } else {
               if (pLogger.isLoggingError()) {
                  pLogger.logError(Constants.NO_PROPERTY_EDITOR, (Object)str, pClass.getName());
               }

               return null;
            }
         } else {
            try {
               pe.setAsText(str);
               return pe.getValue();
            } catch (IllegalArgumentException var6) {
               if ("".equals(str)) {
                  return null;
               } else {
                  if (pLogger.isLoggingError()) {
                     pLogger.logError(Constants.PROPERTY_EDITOR_ERROR, (Throwable)var6, pValue, pClass.getName());
                  }

                  return null;
               }
            }
         }
      }
   }

   public static Object applyArithmeticOperator(Object pLeft, Object pRight, ArithmeticOperator pOperator, Logger pLogger) throws ELException {
      if (pLeft == null && pRight == null) {
         if (pLogger.isLoggingWarning()) {
            pLogger.logWarning(Constants.ARITH_OP_NULL, (Object)pOperator.getOperatorSymbol());
         }

         return PrimitiveObjects.getInteger(0);
      } else if (!isFloatingPointType(pLeft) && !isFloatingPointType(pRight) && !isFloatingPointString(pLeft) && !isFloatingPointString(pRight)) {
         long left = coerceToPrimitiveNumber(pLeft, Long.class, pLogger).longValue();
         long right = coerceToPrimitiveNumber(pRight, Long.class, pLogger).longValue();
         return PrimitiveObjects.getLong(pOperator.apply(left, right, pLogger));
      } else {
         double left = coerceToPrimitiveNumber(pLeft, Double.class, pLogger).doubleValue();
         double right = coerceToPrimitiveNumber(pRight, Double.class, pLogger).doubleValue();
         return PrimitiveObjects.getDouble(pOperator.apply(left, right, pLogger));
      }
   }

   public static Object applyRelationalOperator(Object pLeft, Object pRight, RelationalOperator pOperator, Logger pLogger) throws ELException {
      if (!isFloatingPointType(pLeft) && !isFloatingPointType(pRight)) {
         if (!isIntegerType(pLeft) && !isIntegerType(pRight)) {
            if (!(pLeft instanceof String) && !(pRight instanceof String)) {
               int result;
               if (pLeft instanceof Comparable) {
                  try {
                     result = ((Comparable)pLeft).compareTo(pRight);
                     return PrimitiveObjects.getBoolean(pOperator.apply((long)result, (long)(-result), pLogger));
                  } catch (Exception var11) {
                     if (pLogger.isLoggingError()) {
                        pLogger.logError(Constants.COMPARABLE_ERROR, (Throwable)var11, pLeft.getClass().getName(), pRight == null ? "null" : pRight.getClass().getName(), pOperator.getOperatorSymbol());
                     }

                     return Boolean.FALSE;
                  }
               } else if (pRight instanceof Comparable) {
                  try {
                     result = ((Comparable)pRight).compareTo(pLeft);
                     return PrimitiveObjects.getBoolean(pOperator.apply((long)(-result), (long)result, pLogger));
                  } catch (Exception var10) {
                     if (pLogger.isLoggingError()) {
                        pLogger.logError(Constants.COMPARABLE_ERROR, (Throwable)var10, pRight.getClass().getName(), pLeft == null ? "null" : pLeft.getClass().getName(), pOperator.getOperatorSymbol());
                     }

                     return Boolean.FALSE;
                  }
               } else {
                  if (pLogger.isLoggingError()) {
                     pLogger.logError(Constants.ARITH_OP_BAD_TYPE, (Object)pOperator.getOperatorSymbol(), pLeft.getClass().getName(), pRight.getClass().getName());
                  }

                  return Boolean.FALSE;
               }
            } else {
               String left = coerceToString(pLeft, pLogger);
               String right = coerceToString(pRight, pLogger);
               return PrimitiveObjects.getBoolean(pOperator.apply(left, right, pLogger));
            }
         } else {
            long left = coerceToPrimitiveNumber(pLeft, Long.class, pLogger).longValue();
            long right = coerceToPrimitiveNumber(pRight, Long.class, pLogger).longValue();
            return PrimitiveObjects.getBoolean(pOperator.apply(left, right, pLogger));
         }
      } else {
         double left = coerceToPrimitiveNumber(pLeft, Double.class, pLogger).doubleValue();
         double right = coerceToPrimitiveNumber(pRight, Double.class, pLogger).doubleValue();
         return PrimitiveObjects.getBoolean(pOperator.apply(left, right, pLogger));
      }
   }

   public static Object applyEqualityOperator(Object pLeft, Object pRight, EqualityOperator pOperator, Logger pLogger) throws ELException {
      if (pLeft == pRight) {
         return PrimitiveObjects.getBoolean(pOperator.apply(true, pLogger));
      } else if (pLeft != null && pRight != null) {
         if (!isFloatingPointType(pLeft) && !isFloatingPointType(pRight)) {
            if (!isIntegerType(pLeft) && !isIntegerType(pRight)) {
               if (!(pLeft instanceof Boolean) && !(pRight instanceof Boolean)) {
                  if (!(pLeft instanceof String) && !(pRight instanceof String)) {
                     try {
                        return PrimitiveObjects.getBoolean(pOperator.apply(pLeft.equals(pRight), pLogger));
                     } catch (Exception var10) {
                        if (pLogger.isLoggingError()) {
                           pLogger.logError(Constants.ERROR_IN_EQUALS, (Throwable)var10, pLeft.getClass().getName(), pRight.getClass().getName(), pOperator.getOperatorSymbol());
                        }

                        return Boolean.FALSE;
                     }
                  } else {
                     String left = coerceToString(pLeft, pLogger);
                     String right = coerceToString(pRight, pLogger);
                     return PrimitiveObjects.getBoolean(pOperator.apply(left.equals(right), pLogger));
                  }
               } else {
                  boolean left = coerceToBoolean(pLeft, pLogger);
                  boolean right = coerceToBoolean(pRight, pLogger);
                  return PrimitiveObjects.getBoolean(pOperator.apply(left == right, pLogger));
               }
            } else {
               long left = coerceToPrimitiveNumber(pLeft, Long.class, pLogger).longValue();
               long right = coerceToPrimitiveNumber(pRight, Long.class, pLogger).longValue();
               return PrimitiveObjects.getBoolean(pOperator.apply(left == right, pLogger));
            }
         } else {
            double left = coerceToPrimitiveNumber(pLeft, Double.class, pLogger).doubleValue();
            double right = coerceToPrimitiveNumber(pRight, Double.class, pLogger).doubleValue();
            return PrimitiveObjects.getBoolean(pOperator.apply(left == right, pLogger));
         }
      } else {
         return PrimitiveObjects.getBoolean(pOperator.apply(false, pLogger));
      }
   }

   public static boolean isFloatingPointType(Object pObject) {
      return pObject != null && isFloatingPointType(pObject.getClass());
   }

   public static boolean isFloatingPointType(Class pClass) {
      return pClass == Float.class || pClass == Float.TYPE || pClass == Double.class || pClass == Double.TYPE;
   }

   public static boolean isFloatingPointString(Object pObject) {
      if (!(pObject instanceof String)) {
         return false;
      } else {
         String str = (String)pObject;
         int len = str.length();

         for(int i = 0; i < len; ++i) {
            char ch = str.charAt(i);
            if (ch == '.' || ch == 'e' || ch == 'E') {
               return true;
            }
         }

         return false;
      }
   }

   public static boolean isIntegerType(Object pObject) {
      return pObject != null && isIntegerType(pObject.getClass());
   }

   public static boolean isIntegerType(Class pClass) {
      return pClass == Byte.class || pClass == Byte.TYPE || pClass == Short.class || pClass == Short.TYPE || pClass == Character.class || pClass == Character.TYPE || pClass == Integer.class || pClass == Integer.TYPE || pClass == Long.class || pClass == Long.TYPE;
   }
}
