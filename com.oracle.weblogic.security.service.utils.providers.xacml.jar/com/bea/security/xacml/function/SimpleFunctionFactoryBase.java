package com.bea.security.xacml.function;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.xacml.Type;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.security.xacml.AttributeEvaluator;
import com.bea.security.xacml.InvalidArgumentsException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public abstract class SimpleFunctionFactoryBase implements SimpleFunctionFactory {
   private List argumentTypes;
   private Type returnType;

   public SimpleFunctionFactoryBase(Type returnType, Type[] argumentTypes) {
      this(returnType, Arrays.asList(argumentTypes));
   }

   public SimpleFunctionFactoryBase(Type returnType, List argumentTypes) {
      this.returnType = returnType;
      this.argumentTypes = argumentTypes;
   }

   public List getExpectedArguments() {
      return this.argumentTypes;
   }

   public AttributeEvaluator createFunction(List arguments, LoggerSpi log) throws InvalidArgumentsException {
      List expectedArgs = this.getExpectedArguments();
      if (expectedArgs != null && !expectedArgs.isEmpty()) {
         Iterator expectedIt = expectedArgs.iterator();
         Iterator argsIt = arguments.iterator();

         try {
            label57:
            while(expectedIt.hasNext()) {
               Type nextType = (Type)expectedIt.next();
               if (!(nextType instanceof MultipleArgumentType)) {
                  if (argsIt.hasNext() && nextType.equals(((AttributeEvaluator)argsIt.next()).getType())) {
                     continue;
                  }

                  throw new InvalidArgumentsException(this.returnType, expectedArgs);
               }

               int count = ((MultipleArgumentType)nextType).getMinCount();

               do {
                  if (count-- <= 0) {
                     do {
                        if (!argsIt.hasNext()) {
                           break label57;
                        }
                     } while(nextType.equals(((AttributeEvaluator)argsIt.next()).getType()));

                     throw new InvalidArgumentsException(this.returnType, expectedArgs);
                  }
               } while(argsIt.hasNext() && nextType.equals(((AttributeEvaluator)argsIt.next()).getType()));

               throw new InvalidArgumentsException(this.returnType, expectedArgs);
            }
         } catch (URISyntaxException var8) {
            throw new IllegalArgumentException(var8);
         }

         if (argsIt.hasNext()) {
            throw new InvalidArgumentsException(this.returnType, expectedArgs);
         }
      } else if (arguments != null && !arguments.isEmpty()) {
         throw new InvalidArgumentsException(this.returnType, expectedArgs);
      }

      return this.getFunctionImplementation(arguments, log);
   }

   protected AttributeEvaluator getFunctionImplementation(List arguments, LoggerSpi log) {
      return this.getFunctionImplementation(arguments);
   }

   protected AttributeEvaluator getFunctionImplementation(List arguments) {
      return null;
   }
}
