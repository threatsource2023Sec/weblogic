package com.bea.common.security.xacml.builder;

import com.bea.common.security.xacml.InvalidParameterException;

public interface Parameter {
   com.bea.common.security.xacml.policy.Expression toXACML() throws InvalidParameterException;
}
