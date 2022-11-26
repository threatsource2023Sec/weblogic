package weblogic.security.spi;

public interface PolicyCollectionHandler {
   void setPolicy(Resource var1, String[] var2) throws ConsumptionException;

   void setUncheckedPolicy(Resource var1) throws ConsumptionException;

   void done() throws ConsumptionException;
}
