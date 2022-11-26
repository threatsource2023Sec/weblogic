package com.bea.security.xacml;

import com.bea.common.security.xacml.policy.PolicySet;

public interface PolicySetInfo {
   PolicySet getPolicySet();

   PolicyMetaData getMetaDataEntry();
}
