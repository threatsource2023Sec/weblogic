package com.bea.common.security.xacml.attr;

import com.bea.common.security.xacml.InvalidAttributeException;
import com.bea.common.security.xacml.URISyntaxException;

public interface SimpleAttributeFactory {
   AttributeValue createAttribute(String var1) throws InvalidAttributeException, URISyntaxException;
}
