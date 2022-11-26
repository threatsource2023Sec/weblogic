package com.bea.core.security;

import com.bea.core.security.managers.internal.WLSClientSubjectManagerImpl;
import weblogic.security.subject.SubjectManager;

public class WLSClientEnvironmentImpl extends Environment {
   private SubjectManager subjectManager = new WLSClientSubjectManagerImpl();

   public SubjectManager getSubjectManager() {
      return this.subjectManager;
   }
}
