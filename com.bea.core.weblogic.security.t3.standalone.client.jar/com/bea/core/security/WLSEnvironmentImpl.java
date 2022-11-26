package com.bea.core.security;

import com.bea.core.security.managers.internal.WLSSubjectManagerImpl;
import weblogic.security.subject.SubjectManager;

public class WLSEnvironmentImpl extends Environment {
   private SubjectManager subjectManager = new WLSSubjectManagerImpl();

   public SubjectManager getSubjectManager() {
      return this.subjectManager;
   }
}
