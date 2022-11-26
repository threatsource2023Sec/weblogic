package com.bea.core.security;

import weblogic.security.subject.SubjectManager;

public abstract class Environment {
   private static Environment singleton;

   public static Environment getEnvironment() {
      if (singleton == null) {
         try {
            singleton = (Environment)Class.forName("com.bea.core.security.WLSEnvironmentImpl").newInstance();
         } catch (Exception var5) {
            try {
               singleton = (Environment)Class.forName("com.bea.core.security.CEEnvironmentImpl").newInstance();
            } catch (Exception var4) {
               try {
                  singleton = (Environment)Class.forName("com.bea.core.security.WLSClientEnvironmentImpl").newInstance();
               } catch (Exception var3) {
                  throw new IllegalArgumentException("No known environment for com.bea.core.security");
               }
            }
         }
      }

      return singleton;
   }

   static void setEnvironment(Environment helper) {
      singleton = helper;
   }

   public abstract SubjectManager getSubjectManager();
}
