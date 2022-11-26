package weblogic.jms.server;

public interface DestinationStatus {
   String getName();

   boolean isLocal();

   boolean isInsertionPaused();

   boolean isConsumptionPaused();

   boolean isProductionPaused();

   boolean hasConsumers();

   boolean isUp();

   boolean isPersistent();

   String getPathServiceJndiName();
}
