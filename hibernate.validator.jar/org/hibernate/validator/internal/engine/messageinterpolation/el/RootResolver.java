package org.hibernate.validator.internal.engine.messageinterpolation.el;

import java.util.IllegalFormatException;
import java.util.Iterator;
import javax.el.ELContext;
import javax.el.ELException;
import javax.el.ELResolver;
import javax.el.PropertyNotWritableException;
import org.hibernate.validator.internal.engine.messageinterpolation.FormatterWrapper;

public class RootResolver extends ELResolver {
   public static final String FORMATTER = "formatter";
   private static final String FORMAT = "format";

   public Class getCommonPropertyType(ELContext context, Object base) {
      return null;
   }

   public Iterator getFeatureDescriptors(ELContext context, Object base) {
      return null;
   }

   public Class getType(ELContext context, Object base, Object property) {
      return null;
   }

   public Object getValue(ELContext context, Object base, Object property) {
      return null;
   }

   public boolean isReadOnly(ELContext context, Object base, Object property) {
      return true;
   }

   public void setValue(ELContext context, Object base, Object property, Object value) {
      throw new PropertyNotWritableException();
   }

   public Object invoke(ELContext context, Object base, Object method, Class[] paramTypes, Object[] params) {
      return !(base instanceof FormatterWrapper) ? null : this.evaluateFormatExpression(context, method, params);
   }

   private Object evaluateFormatExpression(ELContext context, Object method, Object[] params) {
      if (!"format".equals(method)) {
         throw new ELException("Wrong method name 'formatter#" + method + "' does not exist. Only formatter#format is supported.");
      } else if (params.length == 0) {
         throw new ELException("Invalid number of arguments to Formatter#format");
      } else if (!(params[0] instanceof String)) {
         throw new ELException("The first argument to Formatter#format must be String");
      } else {
         FormatterWrapper formatterWrapper = (FormatterWrapper)context.getVariableMapper().resolveVariable("formatter").getValue(context);
         Object[] formattingParameters = new Object[params.length - 1];
         System.arraycopy(params, 1, formattingParameters, 0, params.length - 1);

         try {
            Object returnValue = formatterWrapper.format((String)params[0], formattingParameters);
            context.setPropertyResolved(true);
            return returnValue;
         } catch (IllegalFormatException var8) {
            throw new ELException("Error in Formatter#format call", var8);
         }
      }
   }
}
