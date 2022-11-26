package org.apache.taglibs.standard.lang.jstl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class FunctionInvocation extends Expression {
   private String functionName;
   private List argumentList;

   public String getFunctionName() {
      return this.functionName;
   }

   public void setFunctionName(String f) {
      this.functionName = f;
   }

   public List getArgumentList() {
      return this.argumentList;
   }

   public void setArgumentList(List l) {
      this.argumentList = l;
   }

   public FunctionInvocation(String functionName, List argumentList) {
      this.functionName = functionName;
      this.argumentList = argumentList;
   }

   public String getExpressionString() {
      StringBuffer b = new StringBuffer();
      b.append(this.functionName);
      b.append("(");
      Iterator i = this.argumentList.iterator();

      while(i.hasNext()) {
         b.append(((Expression)i.next()).getExpressionString());
         if (i.hasNext()) {
            b.append(", ");
         }
      }

      b.append(")");
      return b.toString();
   }

   public Object evaluate(Object pContext, VariableResolver pResolver, Map functions, String defaultPrefix, Logger pLogger) throws ELException {
      if (functions == null) {
         pLogger.logError(Constants.UNKNOWN_FUNCTION, (Object)this.functionName);
      }

      String functionName = this.functionName;
      if (functionName.indexOf(":") == -1) {
         if (defaultPrefix == null) {
            pLogger.logError(Constants.UNKNOWN_FUNCTION, (Object)functionName);
         }

         functionName = defaultPrefix + ":" + functionName;
      }

      Method target = (Method)functions.get(functionName);
      if (target == null) {
         pLogger.logError(Constants.UNKNOWN_FUNCTION, (Object)functionName);
      }

      Class[] params = target.getParameterTypes();
      if (params.length != this.argumentList.size()) {
         pLogger.logError(Constants.INAPPROPRIATE_FUNCTION_ARG_COUNT, (Object)params.length, this.argumentList.size());
      }

      Object[] arguments = new Object[this.argumentList.size()];

      for(int i = 0; i < params.length; ++i) {
         arguments[i] = ((Expression)this.argumentList.get(i)).evaluate(pContext, pResolver, functions, defaultPrefix, pLogger);
         arguments[i] = Coercions.coerce(arguments[i], params[i], pLogger);
      }

      try {
         return target.invoke((Object)null, arguments);
      } catch (InvocationTargetException var11) {
         pLogger.logError(Constants.FUNCTION_INVOCATION_ERROR, (Throwable)var11.getTargetException(), functionName);
         return null;
      } catch (Exception var12) {
         pLogger.logError(Constants.FUNCTION_INVOCATION_ERROR, (Throwable)var12, functionName);
         return null;
      }
   }
}
