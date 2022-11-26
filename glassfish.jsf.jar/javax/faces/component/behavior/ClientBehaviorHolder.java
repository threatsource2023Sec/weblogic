package javax.faces.component.behavior;

import java.util.Collection;
import java.util.Map;

public interface ClientBehaviorHolder {
   void addClientBehavior(String var1, ClientBehavior var2);

   Collection getEventNames();

   Map getClientBehaviors();

   String getDefaultEventName();
}
