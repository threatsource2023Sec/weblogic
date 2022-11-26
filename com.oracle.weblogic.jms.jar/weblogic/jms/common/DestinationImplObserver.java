package weblogic.jms.common;

public interface DestinationImplObserver {
   void newDestination(String var1, DestinationImpl var2);

   void removeDestination(String var1, DestinationImpl var2);
}
