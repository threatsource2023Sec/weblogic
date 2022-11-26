package weblogic.security.spi;

public interface PolicyConsumer {
   PolicyCollectionHandler getPolicyCollectionHandler(PolicyCollectionInfo var1) throws ConsumptionException;
}
