package com.bea.core.security.managers;

import com.bea.core.security.managers.internal.SubjectManagerFactoryImpl;
import weblogic.security.subject.SubjectManager;

public abstract class SubjectManagerFactory {
   private static final SubjectManagerFactory INSTANCE = new SubjectManagerFactoryImpl();

   public static SubjectManagerFactory getInstance() {
      return INSTANCE;
   }

   public abstract SubjectManager getSubjectManager();
}
