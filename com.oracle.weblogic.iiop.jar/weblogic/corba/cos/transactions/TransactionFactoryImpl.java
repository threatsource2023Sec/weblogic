package weblogic.corba.cos.transactions;

import java.rmi.server.ServerNotActiveException;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import org.omg.CORBA.BAD_INV_ORDER;
import org.omg.CORBA.INVALID_TRANSACTION;
import org.omg.CORBA.NO_IMPLEMENT;
import org.omg.CosTransactions.Control;
import org.omg.CosTransactions.PropagationContext;
import org.omg.CosTransactions.TransactionFactoryHelper;
import org.omg.CosTransactions._TransactionFactoryImplBase;
import weblogic.common.internal.PeerInfo;
import weblogic.iiop.Connection;
import weblogic.iiop.EndPoint;
import weblogic.iiop.ior.IOR;
import weblogic.iiop.server.ior.ServerIORFactory;
import weblogic.rmi.extensions.server.ServerHelper;
import weblogic.transaction.Transaction;
import weblogic.transaction.TransactionHelper;
import weblogic.transaction.TransactionManager;

public final class TransactionFactoryImpl extends _TransactionFactoryImplBase {
   private static TransactionFactoryImpl singleton;
   public static final String TYPE_ID = TransactionFactoryHelper.id();
   private static final IOR ior;

   public static TransactionFactoryImpl getTransactionFactory() {
      return singleton == null ? createSingleton() : singleton;
   }

   private static synchronized TransactionFactoryImpl createSingleton() {
      if (singleton == null) {
         singleton = new TransactionFactoryImpl();
      }

      return singleton;
   }

   private TransactionFactoryImpl() {
   }

   public IOR getIOR() {
      return ior;
   }

   public Control create(int timeout) {
      try {
         TransactionManager tm = (TransactionManager)TransactionHelper.getTransactionHelper().getTransactionManager();
         tm.begin("CORBA", timeout);
         Transaction tx = (Transaction)tm.getTransaction();
         tm.suspend();
         EndPoint ep = (EndPoint)ServerHelper.getClientEndPoint();
         Connection connection = ep.getConnection();
         if (OTSHelper.isDebugEnabled()) {
            p("creating UserTransaction on: " + connection);
         }

         if ((ep.getPeerInfo() == PeerInfo.FOREIGN || ep.getPeerInfo() == null) && connection.getTxContext() == null) {
            connection.setTxContext(tx);
            return new ControlImpl(tx.getXID(), connection);
         } else {
            return new ControlImpl(tx.getXID(), (Connection)null);
         }
      } catch (NotSupportedException var6) {
         throw new NO_IMPLEMENT(var6.getMessage());
      } catch (ServerNotActiveException var7) {
         throw new BAD_INV_ORDER(var7.getMessage());
      } catch (SystemException var8) {
         throw new INVALID_TRANSACTION(var8.getMessage());
      }
   }

   public Control recreate(PropagationContext pc) {
      throw new NO_IMPLEMENT();
   }

   protected static void p(String s) {
      System.err.println("<TransactionFactoryImpl> " + s);
   }

   static {
      ior = ServerIORFactory.createWellKnownIor(TYPE_ID, 17);
   }
}
