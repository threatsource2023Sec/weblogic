package weblogic.ant.taskdefs.antline;

import weblogic.utils.NestedException;

public class AntLineException extends NestedException {
   public AntLineException() {
   }

   public AntLineException(Throwable t) {
      super(t);
   }

   public AntLineException(String msg, Throwable t) {
      super(msg, t);
   }

   public AntLineException(String msg) {
      super(msg);
   }
}
