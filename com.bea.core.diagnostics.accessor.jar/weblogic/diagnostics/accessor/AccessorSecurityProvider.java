package weblogic.diagnostics.accessor;

public interface AccessorSecurityProvider {
   int CREATE_ACTION = 1;
   int READ_ACTION = 2;
   int WRITE_ACTION = 3;
   int DELETE_ACTION = 4;

   void ensureUserAuthorized(int var1) throws AccessorException;
}
