package weblogic.security.service;

import weblogic.security.spi.AuditEvent;

public interface Auditor {
   void writeEvent(AuditEvent var1) throws NotYetInitializedException;
}
