package weblogic.security.service.internal;

import weblogic.security.acl.internal.AuthenticatedSubject;

interface NamedSQLConnectionLookupServiceConfig {
   AuthenticatedSubject getKernelId();
}
