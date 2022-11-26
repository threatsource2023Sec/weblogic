package weblogic.transaction;

public interface InterposedTransactionManagerXAResource extends javax.transaction.xa.XAResource {
   int XA_WORK = 1;
   int OUTSIDE_WORK = 2;
   int OUTSIDE_RESERVED = 4;

   int begin(int var1);

   void finish(int var1);

   void close();
}
