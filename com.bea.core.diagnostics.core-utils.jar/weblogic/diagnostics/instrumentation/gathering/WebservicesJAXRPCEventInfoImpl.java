package weblogic.diagnostics.instrumentation.gathering;

import java.util.ArrayList;
import weblogic.diagnostics.flightrecorder.event.WebservicesJAXRPCEventInfo;

public class WebservicesJAXRPCEventInfoImpl implements WebservicesJAXRPCEventInfo {
   private Object smc = null;
   private boolean sending = false;
   private String action = null;
   private String currentParty = null;
   private WSSoapMessageContextBaseRenderer renderer = null;
   private ArrayList deferredArguments = null;

   public WebservicesJAXRPCEventInfoImpl(WSSoapMessageContextBaseRenderer renderer, Object smc, boolean sending) {
      this.renderer = renderer;
      this.smc = smc;
      this.sending = sending;
   }

   public boolean isSending() {
      return this.sending;
   }

   public String getAction() {
      if (this.action != null) {
         return this.action;
      } else {
         if (this.smc != null) {
            this.action = this.renderer.getAction(this.smc);
         }

         return this.action;
      }
   }

   public void setAction(String action) {
      this.action = action;
   }

   public String getCurrentParty() {
      if (this.currentParty != null) {
         return this.currentParty;
      } else {
         if (this.smc != null) {
            this.currentParty = this.renderer.getCurrentParty(this.smc, this.sending);
         }

         return this.currentParty;
      }
   }

   public void setCurrentParty(String currentParty) {
      this.currentParty = currentParty;
   }

   public ArrayList getDeferredArguments() {
      return this.deferredArguments;
   }

   public void setDeferredArguments(ArrayList deferredArguments) {
      this.deferredArguments = deferredArguments;
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("action=");
      sb.append(this.getAction());
      sb.append(",");
      sb.append("currentParty=");
      sb.append(this.getCurrentParty());
      return sb.toString();
   }
}
