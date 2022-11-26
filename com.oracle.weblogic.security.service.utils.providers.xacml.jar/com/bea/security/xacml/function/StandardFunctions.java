package com.bea.security.xacml.function;

import com.bea.common.security.xacml.URISyntaxException;
import com.bea.security.xacml.function.standard.ArithmeticFunctionLibrary;
import com.bea.security.xacml.function.standard.BagFunctionLibrary;
import com.bea.security.xacml.function.standard.ComparisonFunctionLibrary;
import com.bea.security.xacml.function.standard.DateTimeArithmeticFunctionLibrary;
import com.bea.security.xacml.function.standard.EqualityFunctionLibrary;
import com.bea.security.xacml.function.standard.LogicalFunctionLibrary;
import com.bea.security.xacml.function.standard.NumericConversionFunctionLibrary;
import com.bea.security.xacml.function.standard.RegularExpressionFunctionLibrary;
import com.bea.security.xacml.function.standard.SetFunctionLibrary;
import com.bea.security.xacml.function.standard.SpecialMatchFunctionLibrary;
import com.bea.security.xacml.function.standard.StringConversionFunctionLibrary;
import com.bea.security.xacml.function.standard.StringFunctionLibrary;

public class StandardFunctions extends SimpleFunctionFactoryContainer {
   public StandardFunctions() throws URISyntaxException {
      this.register(new ArithmeticFunctionLibrary());
      this.register(new BagFunctionLibrary());
      this.register(new DateTimeArithmeticFunctionLibrary());
      this.register(new EqualityFunctionLibrary());
      this.register(new LogicalFunctionLibrary());
      this.register(new ComparisonFunctionLibrary());
      this.register(new NumericConversionFunctionLibrary());
      this.register(new SetFunctionLibrary());
      this.register(new StringConversionFunctionLibrary());
      this.register(new StringFunctionLibrary());
      this.register(new RegularExpressionFunctionLibrary());
      this.register(new SpecialMatchFunctionLibrary());
   }
}
