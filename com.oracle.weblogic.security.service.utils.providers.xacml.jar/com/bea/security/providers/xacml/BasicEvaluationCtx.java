package com.bea.security.providers.xacml;

import com.bea.security.xacml.EvaluationCtx;
import weblogic.security.spi.Resource;

public interface BasicEvaluationCtx extends EvaluationCtx {
   Resource getResource();
}
