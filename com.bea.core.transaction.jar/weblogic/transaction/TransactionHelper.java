package weblogic.transaction;

import java.util.EmptyStackException;
import java.util.Stack;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.transaction.SystemException;
import weblogic.kernel.KernelStatus;

public abstract class TransactionHelper {
   private static TransactionHelper singleton;
   private static Stack txStack = new Stack();

   public static TransactionHelper getTransactionHelper() {
      if (singleton == null) {
         try {
            singleton = (TransactionHelper)Class.forName("weblogic.transaction.internal.CETransactionHelperImpl").newInstance();
         } catch (Exception var3) {
            try {
               singleton = (TransactionHelper)Class.forName("weblogic.transaction.internal.TransactionHelperImpl").newInstance();
            } catch (Exception var2) {
               throw new IllegalArgumentException(var2.toString());
            }
         }
      }

      return singleton;
   }

   public static void setTransactionHelper(TransactionHelper helper) {
      singleton = helper;
   }

   public static void pushTransactionHelper(TransactionHelper helper) {
      if (!KernelStatus.isServer()) {
         txStack.push(helper);
         singleton = helper;
      }

   }

   public static TransactionHelper popTransactionHelper() throws EmptyStackException {
      if (!KernelStatus.isServer()) {
         try {
            singleton = (TransactionHelper)txStack.pop();
         } catch (EmptyStackException var1) {
         }
      }

      return singleton;
   }

   public abstract javax.transaction.UserTransaction getUserTransaction();

   public abstract ClientTransactionManager getTransactionManager();

   public javax.transaction.Transaction getTransaction() {
      try {
         javax.transaction.TransactionManager tm = this.getTransactionManager();
         if (tm != null) {
            return tm.getTransaction();
         }
      } catch (SystemException var2) {
         var2.printStackTrace();
      }

      return null;
   }

   public static InterposedTransactionManager getClientInterposedTransactionManager(Context initialContext, String serverName) {
      try {
         return doGetClientInterposedTransactionManager(initialContext, serverName);
      } catch (Exception var3) {
         return null;
      }
   }

   public static InterposedTransactionManager getClientInterposedTransactionManagerThrowsOnException(Context initialContext, String serverName) throws Exception {
      return doGetClientInterposedTransactionManager(initialContext, serverName);
   }

   private static InterposedTransactionManager doGetClientInterposedTransactionManager(Context initialContext, String serverName) throws NamingException {
      InterposedTransactionManager itm = (InterposedTransactionManager)initialContext.lookup("weblogic.transaction.coordinators." + serverName);
      itm.setSSLURLFromClientInfo(itm, initialContext);
      return itm;
   }
}
