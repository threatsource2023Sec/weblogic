package com.bea.core.repackaged.aspectj.bridge;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public interface IMessage {
   IMessage[] RA_IMessage = new IMessage[0];
   Kind WEAVEINFO = new Kind("weaveinfo", 5);
   Kind INFO = new Kind("info", 10);
   Kind DEBUG = new Kind("debug", 20);
   Kind TASKTAG = new Kind("task", 25);
   Kind WARNING = new Kind("warning", 30);
   Kind ERROR = new Kind("error", 40);
   Kind FAIL = new Kind("fail", 50);
   Kind ABORT = new Kind("abort", 60);
   List KINDS = Collections.unmodifiableList(Arrays.asList(WEAVEINFO, INFO, DEBUG, TASKTAG, WARNING, ERROR, FAIL, ABORT));

   String getMessage();

   Kind getKind();

   boolean isError();

   boolean isWarning();

   boolean isDebug();

   boolean isInfo();

   boolean isAbort();

   boolean isTaskTag();

   boolean isFailed();

   boolean getDeclared();

   int getID();

   int getSourceStart();

   int getSourceEnd();

   Throwable getThrown();

   ISourceLocation getSourceLocation();

   String getDetails();

   List getExtraSourceLocations();

   public static final class Kind implements Comparable {
      public static final Comparator COMPARATOR = new Comparator() {
         public int compare(Kind one, Kind two) {
            if (null == one) {
               return null == two ? 0 : -1;
            } else if (null == two) {
               return 1;
            } else {
               return one == two ? 0 : one.precedence - two.precedence;
            }
         }
      };
      private final int precedence;
      private final String name;

      public boolean isSameOrLessThan(Kind kind) {
         return 0 >= COMPARATOR.compare(this, kind);
      }

      public int compareTo(Kind other) {
         return COMPARATOR.compare(this, other);
      }

      private Kind(String name, int precedence) {
         this.name = name;
         this.precedence = precedence;
      }

      public String toString() {
         return this.name;
      }

      // $FF: synthetic method
      Kind(String x0, int x1, Object x2) {
         this(x0, x1);
      }
   }
}
