package com.bea.core.security.managers.internal;

import com.bea.core.security.Environment;
import com.bea.core.security.managers.SubjectManagerFactory;
import weblogic.security.subject.SubjectManager;

public class SubjectManagerFactoryImpl extends SubjectManagerFactory {
   private final SubjectManager subjectManager = Environment.getEnvironment().getSubjectManager();

   public SubjectManager getSubjectManager() {
      return this.subjectManager;
   }
}
