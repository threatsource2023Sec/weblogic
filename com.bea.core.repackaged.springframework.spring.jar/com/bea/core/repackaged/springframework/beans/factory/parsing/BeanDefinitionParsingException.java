package com.bea.core.repackaged.springframework.beans.factory.parsing;

import com.bea.core.repackaged.springframework.beans.factory.BeanDefinitionStoreException;

public class BeanDefinitionParsingException extends BeanDefinitionStoreException {
   public BeanDefinitionParsingException(Problem problem) {
      super(problem.getResourceDescription(), problem.toString(), problem.getRootCause());
   }
}
