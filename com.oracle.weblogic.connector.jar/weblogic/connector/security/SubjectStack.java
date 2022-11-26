package weblogic.connector.security;

import weblogic.security.acl.internal.AuthenticatedSubject;

public interface SubjectStack {
   void pushSubject(AuthenticatedSubject var1);

   void pushWorkSubject(AuthenticatedSubject var1, AuthenticatedSubject var2);

   void pushGivenSubject(AuthenticatedSubject var1, AuthenticatedSubject var2);

   void popSubject(AuthenticatedSubject var1);
}
