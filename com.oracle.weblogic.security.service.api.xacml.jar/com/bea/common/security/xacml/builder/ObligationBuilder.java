package com.bea.common.security.xacml.builder;

import com.bea.common.security.xacml.InvalidParameterException;
import com.bea.common.security.xacml.InvalidXACMLPolicyException;
import com.bea.common.security.xacml.policy.Obligation;
import java.util.List;

public interface ObligationBuilder {
   ObligationBuilder setFulfillOnPermit(boolean var1);

   ObligationBuilder setObligationId(String var1) throws InvalidParameterException;

   ObligationBuilder addAttributeAssignment(String var1, String var2, String var3) throws InvalidParameterException;

   List removeAllAttributeAssignments();

   Obligation getResult() throws InvalidXACMLPolicyException;
}
