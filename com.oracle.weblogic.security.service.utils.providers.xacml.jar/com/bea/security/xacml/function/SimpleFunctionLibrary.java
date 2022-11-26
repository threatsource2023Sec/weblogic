package com.bea.security.xacml.function;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.xacml.URI;
import com.bea.security.xacml.AttributeEvaluator;
import com.bea.security.xacml.InvalidArgumentsException;
import java.util.List;

public interface SimpleFunctionLibrary {
   AttributeEvaluator createFunction(URI var1, List var2, LoggerSpi var3) throws InvalidArgumentsException;
}
