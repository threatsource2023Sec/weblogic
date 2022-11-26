package weblogic.diagnostics.instrumentation.gathering;

import java.lang.reflect.Method;
import weblogic.diagnostics.instrumentation.ValueRenderer;

public class WSSoapMessageContextBaseRenderer implements ValueRenderer {
   private boolean enabled = false;
   private boolean sending = false;
   private static final String getActionName = "getAction";
   private static final String getCurrentPartyName = "getCurrentParty";
   private static final String wlSoapConnection = "weblogic.wsee.connection.soap.SoapConnection";
   private static final String wlSoapMessageContext = "weblogic.wsee.message.soap.SoapMessageContext";
   private Class wlSoapMessageContextClass;
   private Method getAction;
   private Method getCurrentParty;

   public WSSoapMessageContextBaseRenderer(boolean sending) {
      this.sending = sending;

      try {
         Class wlSoapConnectionClass = Class.forName("weblogic.wsee.connection.soap.SoapConnection");
         this.wlSoapMessageContextClass = Class.forName("weblogic.wsee.message.soap.SoapMessageContext");
         Class[] args = new Class[]{this.wlSoapMessageContextClass};
         this.getAction = wlSoapConnectionClass.getMethod("getAction", args);
         args = new Class[]{this.wlSoapMessageContextClass, Boolean.TYPE};
         this.getCurrentParty = wlSoapConnectionClass.getMethod("getCurrentParty", args);
         this.enabled = true;
      } catch (Exception var4) {
      }

   }

   public Object render(Object inputObject) {
      return this.enabled && inputObject != null && this.wlSoapMessageContextClass.isInstance(inputObject) ? new WebservicesJAXRPCEventInfoImpl(this, inputObject, this.sending) : null;
   }

   String getCurrentParty(Object smc, boolean sending) {
      try {
         Object[] args = new Object[]{smc, new Boolean(sending)};
         return (String)this.getCurrentParty.invoke((Object)null, args);
      } catch (Exception var4) {
         return null;
      }
   }

   String getAction(Object smc) {
      try {
         Object[] args = new Object[]{smc};
         return (String)this.getAction.invoke((Object)null, args);
      } catch (Exception var3) {
         return null;
      }
   }
}
