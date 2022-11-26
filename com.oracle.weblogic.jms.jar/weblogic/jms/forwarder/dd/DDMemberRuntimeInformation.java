package weblogic.jms.forwarder.dd;

public interface DDMemberRuntimeInformation {
   boolean isUp();

   boolean hasConsumers();

   boolean isProductionPaused();

   boolean isInsertionPaused();

   boolean isConsumptionPaused();
}
