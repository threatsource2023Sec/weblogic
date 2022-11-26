package com.bea.security.providers.xacml.entitlement.function;

import com.bea.common.security.xacml.URISyntaxException;
import com.bea.security.xacml.function.SimpleFunctionFactoryContainer;

public class CustomFunctions extends SimpleFunctionFactoryContainer {
   public CustomFunctions(boolean inProductionMode, boolean inSecureMode) throws URISyntaxException {
      this.register(new StringFunctionLibrary());
      this.register(new MathFunctionLibrary());
      this.register(new ObjectFunctionLibrary());
      this.register(new EntitlementFunctionLibrary(inProductionMode, inSecureMode));
   }
}
