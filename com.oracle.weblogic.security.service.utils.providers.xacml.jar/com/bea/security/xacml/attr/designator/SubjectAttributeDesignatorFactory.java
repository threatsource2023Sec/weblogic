package com.bea.security.xacml.attr.designator;

import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.policy.SubjectAttributeDesignator;
import com.bea.security.xacml.AttributeEvaluator;
import com.bea.security.xacml.Configuration;
import java.util.Iterator;

public interface SubjectAttributeDesignatorFactory {
   AttributeEvaluator createDesignator(SubjectAttributeDesignator var1, Configuration var2, Iterator var3) throws URISyntaxException;
}
