package weblogic.security.spi;

public interface RoleConsumer {
   RoleCollectionHandler getRoleCollectionHandler(RoleCollectionInfo var1) throws ConsumptionException;
}
