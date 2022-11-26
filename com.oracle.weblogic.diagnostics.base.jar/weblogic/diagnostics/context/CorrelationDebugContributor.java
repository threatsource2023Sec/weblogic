package weblogic.diagnostics.context;

import weblogic.diagnostics.flightrecorder.FlightRecorderDebugEvent;
import weblogic.diagnostics.flightrecorder.event.DebugEventContributor;

public final class CorrelationDebugContributor implements DebugEventContributor {
   private String ECID = null;
   private String RID = null;
   private boolean findCorrelation = false;

   static Object getInstance() {
      return new CorrelationDebugContributor();
   }

   static Object getInstance(String ECID, String RID) {
      return new CorrelationDebugContributor(ECID, RID);
   }

   public static Object getInstance(Correlation correlation) {
      return correlation == null ? new CorrelationDebugContributor((String)null, (String)null) : new CorrelationDebugContributor(correlation.getECID(), correlation.getRID());
   }

   static Object getInstance(DiagnosticContext dc) {
      return dc == null ? new CorrelationDebugContributor((String)null, (String)null) : new CorrelationDebugContributor(dc.getContextId(), dc.getRID());
   }

   private CorrelationDebugContributor() {
      this.findCorrelation = true;
   }

   private CorrelationDebugContributor(String ECID, String RID) {
      this.ECID = ECID;
      this.RID = RID;
   }

   public void contribute(FlightRecorderDebugEvent event) {
      if (event != null) {
         if (this.findCorrelation) {
            Correlation ctx = CorrelationFactory.findCorrelation();
            if (ctx != null) {
               event.setECID(ctx.getECID());
               event.setRID(ctx.getRID());
            }
         } else {
            if (this.ECID != null) {
               event.setECID(this.ECID);
            }

            if (this.RID != null) {
               event.setRID(this.RID);
            }

         }
      }
   }

   public void contributeBefore(FlightRecorderDebugEvent event) {
   }

   public void contributeAfter(FlightRecorderDebugEvent event) {
      if (event != null) {
         if (this.findCorrelation) {
            Correlation ctx = CorrelationFactory.findCorrelation();
            if (ctx != null) {
               event.setECID(ctx.getECID());
               event.setRID(ctx.getRID());
            }
         } else {
            if (this.ECID != null) {
               event.setECID(this.ECID);
            }

            if (this.RID != null) {
               event.setRID(this.RID);
            }

         }
      }
   }
}
