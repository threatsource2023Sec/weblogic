package org.apache.taglibs.standard.lang.jstl;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

public class ArraySuffix extends ValueSuffix {
   static Object[] sNoArgs = new Object[0];
   Expression mIndex;

   public Expression getIndex() {
      return this.mIndex;
   }

   public void setIndex(Expression pIndex) {
      this.mIndex = pIndex;
   }

   public ArraySuffix(Expression pIndex) {
      this.mIndex = pIndex;
   }

   Object evaluateIndex(Object pContext, VariableResolver pResolver, Map functions, String defaultPrefix, Logger pLogger) throws ELException {
      return this.mIndex.evaluate(pContext, pResolver, functions, defaultPrefix, pLogger);
   }

   String getOperatorSymbol() {
      return "[]";
   }

   public String getExpressionString() {
      return "[" + this.mIndex.getExpressionString() + "]";
   }

   public Object evaluate(Object pValue, Object pContext, VariableResolver pResolver, Map functions, String defaultPrefix, Logger pLogger) throws ELException {
      if (pValue == null) {
         if (pLogger.isLoggingWarning()) {
            pLogger.logWarning(Constants.CANT_GET_INDEXED_VALUE_OF_NULL, (Object)this.getOperatorSymbol());
         }

         return null;
      } else {
         Object indexVal;
         if ((indexVal = this.evaluateIndex(pContext, pResolver, functions, defaultPrefix, pLogger)) == null) {
            if (pLogger.isLoggingWarning()) {
               pLogger.logWarning(Constants.CANT_GET_NULL_INDEX, (Object)this.getOperatorSymbol());
            }

            return null;
         } else if (pValue instanceof Map) {
            Map val = (Map)pValue;
            return val.get(indexVal);
         } else if (!(pValue instanceof List) && !pValue.getClass().isArray()) {
            String indexStr;
            if ((indexStr = Coercions.coerceToString(indexVal, pLogger)) == null) {
               return null;
            } else {
               BeanInfoProperty property;
               if ((property = BeanInfoManager.getBeanInfoProperty(pValue.getClass(), indexStr, pLogger)) != null && property.getReadMethod() != null) {
                  try {
                     return property.getReadMethod().invoke(pValue, sNoArgs);
                  } catch (InvocationTargetException var15) {
                     if (pLogger.isLoggingError()) {
                        pLogger.logError(Constants.ERROR_GETTING_PROPERTY, (Throwable)var15.getTargetException(), indexStr, pValue.getClass().getName());
                     }

                     return null;
                  } catch (Exception var16) {
                     if (pLogger.isLoggingError()) {
                        pLogger.logError(Constants.ERROR_GETTING_PROPERTY, (Throwable)var16, indexStr, pValue.getClass().getName());
                     }

                     return null;
                  }
               } else {
                  if (pLogger.isLoggingError()) {
                     pLogger.logError(Constants.CANT_FIND_INDEX, (Object)indexVal, pValue.getClass().getName(), this.getOperatorSymbol());
                  }

                  return null;
               }
            }
         } else {
            Integer indexObj = Coercions.coerceToInteger(indexVal, pLogger);
            if (indexObj == null) {
               if (pLogger.isLoggingError()) {
                  pLogger.logError(Constants.BAD_INDEX_VALUE, (Object)this.getOperatorSymbol(), indexVal.getClass().getName());
               }

               return null;
            } else if (pValue instanceof List) {
               try {
                  return ((List)pValue).get(indexObj);
               } catch (ArrayIndexOutOfBoundsException var17) {
                  if (pLogger.isLoggingWarning()) {
                     pLogger.logWarning(Constants.EXCEPTION_ACCESSING_LIST, (Throwable)var17, indexObj);
                  }

                  return null;
               } catch (IndexOutOfBoundsException var18) {
                  if (pLogger.isLoggingWarning()) {
                     pLogger.logWarning(Constants.EXCEPTION_ACCESSING_LIST, (Throwable)var18, indexObj);
                  }

                  return null;
               } catch (Exception var19) {
                  if (pLogger.isLoggingError()) {
                     pLogger.logError(Constants.EXCEPTION_ACCESSING_LIST, (Throwable)var19, indexObj);
                  }

                  return null;
               }
            } else {
               try {
                  return Array.get(pValue, indexObj);
               } catch (ArrayIndexOutOfBoundsException var12) {
                  if (pLogger.isLoggingWarning()) {
                     pLogger.logWarning(Constants.EXCEPTION_ACCESSING_ARRAY, (Throwable)var12, indexObj);
                  }

                  return null;
               } catch (IndexOutOfBoundsException var13) {
                  if (pLogger.isLoggingWarning()) {
                     pLogger.logWarning(Constants.EXCEPTION_ACCESSING_ARRAY, (Throwable)var13, indexObj);
                  }

                  return null;
               } catch (Exception var14) {
                  if (pLogger.isLoggingError()) {
                     pLogger.logError(Constants.EXCEPTION_ACCESSING_ARRAY, (Throwable)var14, indexObj);
                  }

                  return null;
               }
            }
         }
      }
   }
}
