package weblogic.entitlement.data;

public interface EnResourceCursor extends EnCursor {
   EResource getCurrentResource();

   EResource next();
}
