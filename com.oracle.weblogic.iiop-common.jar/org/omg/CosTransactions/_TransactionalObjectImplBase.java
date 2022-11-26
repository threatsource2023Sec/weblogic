package org.omg.CosTransactions;

import java.util.HashMap;
import java.util.Map;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.InvokeHandler;
import org.omg.CORBA.portable.ObjectImpl;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.ResponseHandler;

public abstract class _TransactionalObjectImplBase extends ObjectImpl implements TransactionalObject, InvokeHandler {
   private static Map _methods = new HashMap();
   private static String[] __ids = new String[]{"IDL:omg.org/CosTransactions/TransactionalObject:1.0"};

   public OutputStream _invoke(String $method, InputStream in, ResponseHandler $rh) {
      OutputStream out = null;
      Integer __method = (Integer)_methods.get($method);
      if (__method == null) {
         throw new BAD_OPERATION(0, CompletionStatus.COMPLETED_MAYBE);
      } else {
         return (OutputStream)out;
      }
   }

   public String[] _ids() {
      return (String[])((String[])__ids.clone());
   }
}
