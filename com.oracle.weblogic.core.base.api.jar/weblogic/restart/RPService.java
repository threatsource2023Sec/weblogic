package weblogic.restart;

public interface RPService {
   int DEFAULT_ORDER = 100;

   String getName();

   int getType();

   void rpDeactivate(String var1) throws RPException;

   void rpActivate(String var1) throws RPException;

   int getOrder() throws RPException;
}
