package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;
import com.oracle.jrockit.jfr.UseConstantPool;
import com.oracle.jrockit.jfr.ValueDefinition;
import java.util.ArrayList;
import weblogic.diagnostics.instrumentation.DynamicJoinPoint;

@EventDefinition(
   name = "Webservices JAXRPC Base Timed",
   description = "Webservices JAX-RPC information with timing",
   path = "wls/Webservices/JAXRPC/Webservices_JAXRPC_Base_Timed",
   thread = true
)
public class WebservicesJAXRPCBaseTimedEvent extends BaseTimedEvent implements WebservicesJAXRPCEventInfo {
   @UseConstantPool(
      name = "GlobalPool"
   )
   @ValueDefinition(
      name = "Subsystem",
      description = "The subsystem ID",
      relationKey = "http://www.oracle.com/wls/Webservices"
   )
   String subsystem = "Webservices";
   @ValueDefinition(
      name = "Action",
      description = "The action for JAX-RPC"
   )
   String action = null;
   @ValueDefinition(
      name = "Current Party",
      description = "The current party for JAX-RPC"
   )
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
}
