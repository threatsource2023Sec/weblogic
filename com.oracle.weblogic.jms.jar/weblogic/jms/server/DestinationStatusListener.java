package weblogic.jms.server;

public interface DestinationStatusListener {
   void onProductionPauseChange(DestinationStatus var1);

   void onConsumptionPauseChange(DestinationStatus var1);

   void onInsertionPauseChange(DestinationStatus var1);

   void onHasConsumersStatusChange(DestinationStatus var1);

   void onUpStatusChange(DestinationStatus var1);
}
