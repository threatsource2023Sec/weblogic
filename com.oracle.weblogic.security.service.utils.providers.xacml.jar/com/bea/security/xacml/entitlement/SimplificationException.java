package com.bea.security.xacml.entitlement;

import com.bea.common.security.xacml.XACMLException;
import java.util.List;

public class SimplificationException extends XACMLException {
   static final long serialVersionUID = 3834591006598705721L;
   private List rules;

   public SimplificationException(List rules) {
      this.rules = rules;
   }

   public List getRules() {
      return this.rules;
   }
}
