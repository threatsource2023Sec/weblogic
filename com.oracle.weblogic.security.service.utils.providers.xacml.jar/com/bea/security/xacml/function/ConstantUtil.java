package com.bea.security.xacml.function;

import com.bea.common.security.xacml.attr.AttributeValue;
import com.bea.common.security.xacml.attr.Bag;
import com.bea.security.xacml.AttributeEvaluator;
import com.bea.security.xacml.attr.evaluator.Constant;
import com.bea.security.xacml.attr.evaluator.ConstantBag;

public class ConstantUtil {
   private ConstantUtil() {
   }

   public static AttributeValue getConstantValue(AttributeEvaluator ae) {
      if (ae instanceof Constant) {
         AttributeValue av = ((Constant)ae).getValue();
         if (av != null) {
            return av;
         }
      } else if (ae instanceof ConstantBag) {
         Bag b = ((ConstantBag)ae).getValue();
         if (b != null && b.size() == 1) {
            AttributeValue av = (AttributeValue)b.iterator().next();
            if (av != null) {
               return av;
            }
         }
      }

      return null;
   }

   public static Bag getConstantBagValue(AttributeEvaluator ae) {
      if (ae instanceof ConstantBag) {
         Bag b = ((ConstantBag)ae).getValue();
         if (b != null) {
            return b;
         }
      } else if (ae instanceof Constant) {
         AttributeValue av = ((Constant)ae).getValue();
         if (av != null) {
            return av;
         }
      }

      return null;
   }
}
