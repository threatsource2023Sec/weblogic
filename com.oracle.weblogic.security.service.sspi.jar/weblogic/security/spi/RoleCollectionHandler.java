package weblogic.security.spi;

public interface RoleCollectionHandler {
   void setRole(Resource var1, String var2, String[] var3) throws ConsumptionException;

   void done() throws ConsumptionException;
}
