package com.bea.security.xacml;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.xacml.attr.Bag;
import com.bea.security.xacml.attr.AttributeEvaluatableFactory;
import com.bea.security.xacml.attr.SubjectAttributeEvaluatableFactory;
import com.bea.security.xacml.policy.VariableContext;
import java.util.Map;

public interface EvaluationCtx extends LoggerSpi {
   SubjectAttributeEvaluatableFactory getSubjectAttributes();

   AttributeEvaluatableFactory getResourceAttributes();

   AttributeEvaluatableFactory getActionAttributes();

   AttributeEvaluatableFactory getEnvironmentAttributes();

   Bag getCurrentTime() throws IndeterminateEvaluationException;

   Bag getCurrentDate() throws IndeterminateEvaluationException;

   Bag getCurrentDateTime() throws IndeterminateEvaluationException;

   VariableContext getVariableContext();

   void setVariableContext(VariableContext var1);

   Map getEvaluationData(Object var1);
}
