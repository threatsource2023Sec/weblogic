package com.bea.security.xacml.function;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.attr.Bag;
import com.bea.security.xacml.AttributeEvaluator;
import com.bea.security.xacml.EvaluationCtx;
import com.bea.security.xacml.IndeterminateEvaluationException;
import com.bea.security.xacml.InvalidArgumentsException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class SimpleFunctionLibraryBase implements SimpleFunctionLibrary {
   private Map registry = new HashMap();

   protected void register(URI identifier, SimpleFunctionFactory sff) {
      this.registry.put(identifier, sff);
   }

   public AttributeEvaluator createFunction(URI identifier, List arguments, LoggerSpi log) throws InvalidArgumentsException {
      SimpleFunctionFactory sff = (SimpleFunctionFactory)this.registry.get(identifier);
      return sff != null ? sff.createFunction(arguments, log) : null;
   }

   protected void debugEval(EvaluationCtx context, URI id, Bag result, Bag... params) {
      StringBuffer sb = new StringBuffer();
      sb.append("Evaluate ");
      sb.append(id);
      sb.append('(');
      if (params != null) {
         int l = params.length;

         for(int i = 0; i < l; ++i) {
            sb.append(params[i]);
            if (i + 1 < l) {
               sb.append(',');
            }
         }
      }

      sb.append(") -> ");
      sb.append(result);
      context.debug(sb.toString());
   }

   protected void debugEval(EvaluationCtx context, URI id, IndeterminateEvaluationException result, Bag... params) {
      StringBuffer sb = new StringBuffer();
      sb.append("Evaluate ");
      sb.append(id);
      sb.append('(');
      if (params != null) {
         int l = params.length;

         for(int i = 0; i < l; ++i) {
            sb.append(params[i]);
            if (i + 1 < l) {
               sb.append(',');
            }
         }
      }

      sb.append(") -> indeterminate: ");
      sb.append(result.getMessage());
      context.debug(sb.toString());
   }
}
