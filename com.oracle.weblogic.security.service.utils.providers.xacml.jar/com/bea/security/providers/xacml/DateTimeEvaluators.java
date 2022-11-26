package com.bea.security.providers.xacml;

import com.bea.security.xacml.AttributeEvaluator;

public interface DateTimeEvaluators {
   AttributeEvaluator getDateEvaluator();

   AttributeEvaluator getDateTimeEvaluator();

   AttributeEvaluator getTimeEvaluator();
}
