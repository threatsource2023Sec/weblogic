package com.oracle.weblogic.lifecycle.core;

public class EditSessionThreadLocal {
   public static final ThreadLocal threadLocal = new ThreadLocal();

   public static void setEditSession(String editSessionName) {
      threadLocal.set(editSessionName);
   }

   public static void unset() {
      threadLocal.remove();
   }

   public static String getEditSession() {
      return (String)threadLocal.get();
   }
}
