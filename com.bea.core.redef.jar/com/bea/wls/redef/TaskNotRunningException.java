package com.bea.wls.redef;

import java.lang.instrument.IllegalClassFormatException;

public class TaskNotRunningException extends IllegalClassFormatException {
   public TaskNotRunningException(String s) {
      super(s);
   }
}
