package com.bea.core.repackaged.springframework.core;

import com.bea.core.repackaged.springframework.util.Assert;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class ExceptionDepthComparator implements Comparator {
   private final Class targetException;

   public ExceptionDepthComparator(Throwable exception) {
      Assert.notNull(exception, (String)"Target exception must not be null");
      this.targetException = exception.getClass();
   }

   public ExceptionDepthComparator(Class exceptionType) {
      Assert.notNull(exceptionType, (String)"Target exception type must not be null");
      this.targetException = exceptionType;
   }

   public int compare(Class o1, Class o2) {
      int depth1 = this.getDepth(o1, this.targetException, 0);
      int depth2 = this.getDepth(o2, this.targetException, 0);
      return depth1 - depth2;
   }

   private int getDepth(Class declaredException, Class exceptionToMatch, int depth) {
      if (exceptionToMatch.equals(declaredException)) {
         return depth;
      } else {
         return exceptionToMatch == Throwable.class ? Integer.MAX_VALUE : this.getDepth(declaredException, exceptionToMatch.getSuperclass(), depth + 1);
      }
   }

   public static Class findClosestMatch(Collection exceptionTypes, Throwable targetException) {
      Assert.notEmpty(exceptionTypes, "Exception types must not be empty");
      if (exceptionTypes.size() == 1) {
         return (Class)exceptionTypes.iterator().next();
      } else {
         List handledExceptions = new ArrayList(exceptionTypes);
         handledExceptions.sort(new ExceptionDepthComparator(targetException));
         return (Class)handledExceptions.get(0);
      }
   }
}
