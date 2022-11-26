package com.bea.core.repackaged.springframework.beans.factory.parsing;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.lang.Nullable;

public class FailFastProblemReporter implements ProblemReporter {
   private Log logger = LogFactory.getLog(this.getClass());

   public void setLogger(@Nullable Log logger) {
      this.logger = logger != null ? logger : LogFactory.getLog(this.getClass());
   }

   public void fatal(Problem problem) {
      throw new BeanDefinitionParsingException(problem);
   }

   public void error(Problem problem) {
      throw new BeanDefinitionParsingException(problem);
   }

   public void warning(Problem problem) {
      this.logger.warn(problem, problem.getRootCause());
   }
}
