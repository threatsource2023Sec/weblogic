package com.oracle.weblogic.diagnostics.expressions;

import com.oracle.weblogic.diagnostics.l10n.DiagnosticsFrameworkTextTextFormatter;
import com.sun.el.stream.Stream;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.el.ELContext;
import javax.el.ELResolver;
import javax.el.LambdaExpression;
import weblogic.diagnostics.debug.DebugLogger;

public class DiagnosticsELResolver extends ELResolver {
   private static final DebugLogger logger = DebugLogger.getDebugLogger("DebugDiagnosticsELResolver");

   public Object getValue(ELContext context, Object base, Object property) {
      return null;
   }

   public Object invoke(ELContext context, Object base, Object method, Class[] paramTypes, Object[] params) {
      if (logger.isDebugEnabled()) {
         logger.debug(this.getClass().getSimpleName() + " [base: " + base + ", method: " + method + ", params: " + Arrays.toString(params) + ", paramTypes: " + Arrays.toString(paramTypes) + "]");
      }

      if (context == null) {
         throw new NullPointerException();
      } else if (method != null && method instanceof String) {
         String trimmedMethod = method.toString().trim();
         Iterable stream = null;
         switch (trimmedMethod) {
            case "percentMatch":
               stream = this.convertToIterable(base);
               if (stream != null) {
                  if (params.length == 1 && params[0] instanceof LambdaExpression) {
                     context.setPropertyResolved(true);
                     LambdaExpression lambda = (LambdaExpression)params[0];
                     lambda.setELContext(context);
                     return DiagnosticsFunctionProvider.percentMatch(stream, lambda);
                  }

                  throw new IllegalArgumentException(DiagnosticsFrameworkTextTextFormatter.getInstance().getIllegalPercentMatchArguments(Arrays.toString(params)));
               }
               break;
            case "tableAverages":
               stream = this.convertToIterable(base);
               if (stream != null) {
                  context.setPropertyResolved(true);
                  return DiagnosticsFunctionProvider.tableAverages(stream);
               }
               break;
            case "changes":
               stream = this.convertToIterable(base);
               if (stream != null) {
                  context.setPropertyResolved(true);
                  return DiagnosticsFunctionProvider.changes(stream);
               }
               break;
            case "tableChanges":
               stream = this.convertToIterable(base);
               if (stream != null) {
                  context.setPropertyResolved(true);
                  return DiagnosticsFunctionProvider.tableChanges(stream);
               }
         }

         return null;
      } else {
         return null;
      }
   }

   public Class getType(ELContext context, Object base, Object property) {
      switch (property.toString().trim()) {
         case "percentMatch":
         case "tableAverages":
         case "changes":
         case "tableChanges":
            if (base instanceof Iterable) {
               context.setPropertyResolved(true);
               return Iterable.class;
            }
         default:
            return null;
      }
   }

   public void setValue(ELContext context, Object base, Object property, Object value) {
   }

   public boolean isReadOnly(ELContext context, Object base, Object property) {
      return true;
   }

   public Iterator getFeatureDescriptors(ELContext context, Object base) {
      return null;
   }

   public Class getCommonPropertyType(ELContext context, Object base) {
      return null;
   }

   private Iterable convertToIterable(Object base) {
      Iterable stream = null;
      if (base instanceof Iterable) {
         stream = (Iterable)base;
      } else if (base.getClass().isArray()) {
         Object[] converted = null;
         if (base instanceof Object[]) {
            converted = (Object[])((Object[])base);
         } else {
            Object[] boxedArray = new Object[Array.getLength(base)];

            for(int index = 0; index < boxedArray.length; ++index) {
               boxedArray[index] = Array.get(base, index);
            }

            converted = boxedArray;
         }

         List list = Arrays.asList(converted);
         stream = list;
      } else if (base instanceof Stream) {
         stream = (List)((Stream)base).toList();
      }

      return (Iterable)stream;
   }
}
