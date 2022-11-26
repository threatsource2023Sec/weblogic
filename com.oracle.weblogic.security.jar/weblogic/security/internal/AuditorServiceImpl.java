package weblogic.security.internal;

import weblogic.security.service.Auditor;
import weblogic.security.spi.AuditEvent;
import weblogic.security.spi.AuditorService;

public class AuditorServiceImpl implements AuditorService {
   private Auditor auditor;

   public AuditorServiceImpl(Auditor auditor) {
      this.auditor = auditor;
   }

   public void providerAuditWriteEvent(AuditEvent event) {
      this.auditor.writeEvent(event);
   }
}
