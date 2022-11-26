package com.bea.security.xacml;

import com.bea.common.security.xacml.XACMLException;
import java.util.List;

public class IndeterminateEvaluationException extends XACMLException {
   private static final long serialVersionUID = 3760850051601217844L;

   public IndeterminateEvaluationException() {
   }

   public IndeterminateEvaluationException(String msg) {
      super(msg);
   }

   public IndeterminateEvaluationException(Throwable cause) {
      super(cause);
   }

   public IndeterminateEvaluationException(List indeterminates) {
   }
}
