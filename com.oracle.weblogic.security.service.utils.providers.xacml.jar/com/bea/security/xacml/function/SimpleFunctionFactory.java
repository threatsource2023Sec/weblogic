package com.bea.security.xacml.function;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.security.xacml.AttributeEvaluator;
import com.bea.security.xacml.InvalidArgumentsException;
import java.util.List;

public interface SimpleFunctionFactory {
   AttributeEvaluator createFunction(List var1, LoggerSpi var2) throws InvalidArgumentsException;
}
