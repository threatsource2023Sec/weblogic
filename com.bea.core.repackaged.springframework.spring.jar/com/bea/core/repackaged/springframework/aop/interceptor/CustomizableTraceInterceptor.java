package com.bea.core.repackaged.springframework.aop.interceptor;

import com.bea.core.repackaged.aopalliance.intercept.MethodInvocation;
import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.springframework.core.Constants;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.StopWatch;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomizableTraceInterceptor extends AbstractTraceInterceptor {
   public static final String PLACEHOLDER_METHOD_NAME = "$[methodName]";
   public static final String PLACEHOLDER_TARGET_CLASS_NAME = "$[targetClassName]";
   public static final String PLACEHOLDER_TARGET_CLASS_SHORT_NAME = "$[targetClassShortName]";
   public static final String PLACEHOLDER_RETURN_VALUE = "$[returnValue]";
   public static final String PLACEHOLDER_ARGUMENT_TYPES = "$[argumentTypes]";
   public static final String PLACEHOLDER_ARGUMENTS = "$[arguments]";
   public static final String PLACEHOLDER_EXCEPTION = "$[exception]";
   public static final String PLACEHOLDER_INVOCATION_TIME = "$[invocationTime]";
   private static final String DEFAULT_ENTER_MESSAGE = "Entering method '$[methodName]' of class [$[targetClassName]]";
   private static final String DEFAULT_EXIT_MESSAGE = "Exiting method '$[methodName]' of class [$[targetClassName]]";
   private static final String DEFAULT_EXCEPTION_MESSAGE = "Exception thrown in method '$[methodName]' of class [$[targetClassName]]";
   private static final Pattern PATTERN = Pattern.compile("\\$\\[\\p{Alpha}+\\]");
   private static final Set ALLOWED_PLACEHOLDERS = (new Constants(CustomizableTraceInterceptor.class)).getValues("PLACEHOLDER_");
   private String enterMessage = "Entering method '$[methodName]' of class [$[targetClassName]]";
   private String exitMessage = "Exiting method '$[methodName]' of class [$[targetClassName]]";
   private String exceptionMessage = "Exception thrown in method '$[methodName]' of class [$[targetClassName]]";

   public void setEnterMessage(String enterMessage) throws IllegalArgumentException {
      Assert.hasText(enterMessage, "enterMessage must not be empty");
      this.checkForInvalidPlaceholders(enterMessage);
      Assert.doesNotContain(enterMessage, "$[returnValue]", "enterMessage cannot contain placeholder $[returnValue]");
      Assert.doesNotContain(enterMessage, "$[exception]", "enterMessage cannot contain placeholder $[exception]");
      Assert.doesNotContain(enterMessage, "$[invocationTime]", "enterMessage cannot contain placeholder $[invocationTime]");
      this.enterMessage = enterMessage;
   }

   public void setExitMessage(String exitMessage) {
      Assert.hasText(exitMessage, "exitMessage must not be empty");
      this.checkForInvalidPlaceholders(exitMessage);
      Assert.doesNotContain(exitMessage, "$[exception]", "exitMessage cannot contain placeholder$[exception]");
      this.exitMessage = exitMessage;
   }

   public void setExceptionMessage(String exceptionMessage) {
      Assert.hasText(exceptionMessage, "exceptionMessage must not be empty");
      this.checkForInvalidPlaceholders(exceptionMessage);
      Assert.doesNotContain(exceptionMessage, "$[returnValue]", "exceptionMessage cannot contain placeholder $[returnValue]");
      this.exceptionMessage = exceptionMessage;
   }

   protected Object invokeUnderTrace(MethodInvocation invocation, Log logger) throws Throwable {
      String name = ClassUtils.getQualifiedMethodName(invocation.getMethod());
      StopWatch stopWatch = new StopWatch(name);
      Object returnValue = null;
      boolean exitThroughException = false;

      Object var7;
      try {
         stopWatch.start(name);
         this.writeToLog(logger, this.replacePlaceholders(this.enterMessage, invocation, (Object)null, (Throwable)null, -1L));
         returnValue = invocation.proceed();
         var7 = returnValue;
      } catch (Throwable var11) {
         if (stopWatch.isRunning()) {
            stopWatch.stop();
         }

         exitThroughException = true;
         this.writeToLog(logger, this.replacePlaceholders(this.exceptionMessage, invocation, (Object)null, var11, stopWatch.getTotalTimeMillis()), var11);
         throw var11;
      } finally {
         if (!exitThroughException) {
            if (stopWatch.isRunning()) {
               stopWatch.stop();
            }

            this.writeToLog(logger, this.replacePlaceholders(this.exitMessage, invocation, returnValue, (Throwable)null, stopWatch.getTotalTimeMillis()));
         }

      }

      return var7;
   }

   protected String replacePlaceholders(String message, MethodInvocation methodInvocation, @Nullable Object returnValue, @Nullable Throwable throwable, long invocationTime) {
      Matcher matcher = PATTERN.matcher(message);
      StringBuffer output = new StringBuffer();

      while(true) {
         while(matcher.find()) {
            String match = matcher.group();
            if ("$[methodName]".equals(match)) {
               matcher.appendReplacement(output, Matcher.quoteReplacement(methodInvocation.getMethod().getName()));
            } else {
               String shortName;
               if ("$[targetClassName]".equals(match)) {
                  shortName = this.getClassForLogging(methodInvocation.getThis()).getName();
                  matcher.appendReplacement(output, Matcher.quoteReplacement(shortName));
               } else if ("$[targetClassShortName]".equals(match)) {
                  shortName = ClassUtils.getShortName(this.getClassForLogging(methodInvocation.getThis()));
                  matcher.appendReplacement(output, Matcher.quoteReplacement(shortName));
               } else if ("$[arguments]".equals(match)) {
                  matcher.appendReplacement(output, Matcher.quoteReplacement(StringUtils.arrayToCommaDelimitedString(methodInvocation.getArguments())));
               } else if ("$[argumentTypes]".equals(match)) {
                  this.appendArgumentTypes(methodInvocation, matcher, output);
               } else if ("$[returnValue]".equals(match)) {
                  this.appendReturnValue(methodInvocation, matcher, output, returnValue);
               } else if (throwable != null && "$[exception]".equals(match)) {
                  matcher.appendReplacement(output, Matcher.quoteReplacement(throwable.toString()));
               } else {
                  if (!"$[invocationTime]".equals(match)) {
                     throw new IllegalArgumentException("Unknown placeholder [" + match + "]");
                  }

                  matcher.appendReplacement(output, Long.toString(invocationTime));
               }
            }
         }

         matcher.appendTail(output);
         return output.toString();
      }
   }

   private void appendReturnValue(MethodInvocation methodInvocation, Matcher matcher, StringBuffer output, @Nullable Object returnValue) {
      if (methodInvocation.getMethod().getReturnType() == Void.TYPE) {
         matcher.appendReplacement(output, "void");
      } else if (returnValue == null) {
         matcher.appendReplacement(output, "null");
      } else {
         matcher.appendReplacement(output, Matcher.quoteReplacement(returnValue.toString()));
      }

   }

   private void appendArgumentTypes(MethodInvocation methodInvocation, Matcher matcher, StringBuffer output) {
      Class[] argumentTypes = methodInvocation.getMethod().getParameterTypes();
      String[] argumentTypeShortNames = new String[argumentTypes.length];

      for(int i = 0; i < argumentTypeShortNames.length; ++i) {
         argumentTypeShortNames[i] = ClassUtils.getShortName(argumentTypes[i]);
      }

      matcher.appendReplacement(output, Matcher.quoteReplacement(StringUtils.arrayToCommaDelimitedString(argumentTypeShortNames)));
   }

   private void checkForInvalidPlaceholders(String message) throws IllegalArgumentException {
      Matcher matcher = PATTERN.matcher(message);

      String match;
      do {
         if (!matcher.find()) {
            return;
         }

         match = matcher.group();
      } while(ALLOWED_PLACEHOLDERS.contains(match));

      throw new IllegalArgumentException("Placeholder [" + match + "] is not valid");
   }
}
