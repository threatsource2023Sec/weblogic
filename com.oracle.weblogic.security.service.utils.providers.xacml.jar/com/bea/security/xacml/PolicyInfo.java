package com.bea.security.xacml;

import com.bea.common.security.xacml.policy.Policy;

public interface PolicyInfo {
   Policy getPolicy();

   PolicyMetaData getMetaDataEntry();
}
