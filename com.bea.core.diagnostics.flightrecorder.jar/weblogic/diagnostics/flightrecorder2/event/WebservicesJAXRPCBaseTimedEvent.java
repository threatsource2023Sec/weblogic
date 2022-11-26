package weblogic.diagnostics.flightrecorder2.event;

import java.util.ArrayList;
import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;
import weblogic.diagnostics.flightrecorder.event.WebservicesJAXRPCEventInfo;
import weblogic.diagnostics.flightrecorder.event.WebservicesJAXRPCEventInfoHelper;
import weblogic.diagnostics.flightrecorder2.impl.RelationKey;
import weblogic.diagnostics.instrumentation.DynamicJoinPoint;

@Label("Webservices JAXRPC Base Timed")
@Name("com.oracle.weblogic.webservices.WebservicesJAXRPCBaseTimedEvent")
@Description("Webservices JAX-RPC information with timing")
@Category({"WebLogic Server", "Webservices", "JAXRPC"})
public abstract class WebservicesJAXRPCBaseTimedEvent extends BaseEvent implements WebservicesJAXRPCEventInfo {
   @Label("Subsystem")
   @Description("The subsystem ID")
   @RelationKey("http://www.oracle.com/wls/Webservices")
   String subsystem = "Webservices";
   @Label("Action")
   @Description("The action for JAX-RPC")
   String action = null;
   @Label("Current Party")
   @Description("The current party for JAX-RPC")
   String currentParty = null;
   public ArrayList deferredArguments = null;

   public String getSubsystem() {
      return this.subsystem;
   }

   public void setSubsystem(String subsystem) {
      this.subsystem = subsystem;
   }

   public void populateExtensions(Object retVal, Object[] args, DynamicJoinPoint djp, boolean isAfter) {
      WebservicesJAXRPCEventInfoHelper.populateExtensions(retVal, args, this, isAfter);
   }

   public String getAction() {
      return this.action;
   }

   public void setAction(String action) {
      this.action = action;
   }

   public String getCurrentParty() {
      return this.currentParty;
   }

   public void setCurrentParty(String currentParty) {
      this.currentParty = currentParty;
   }

   public boolean isSending() {
      return false;
   }

   public boolean requiresProcessingArgsAfter() {
      return this.deferredArguments != null;
   }

   public ArrayList getDeferredArguments() {
      return this.deferredArguments;
   }

   public void setDeferredArguments(ArrayList deferredArguments) {
      this.deferredArguments = deferredArguments;
   }

   public boolean isEventTimed() {
      return true;
   }
}
