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

public abstract class _SynchronizationImplBase extends ObjectImpl implements Synchronization, InvokeHandler {
   private static Map _methods = new HashMap();
   private static String[] __ids;

   public OutputStream _invoke(String $method, InputStream in, ResponseHandler $rh) {
      OutputStream out = null;
      Integer __method = (Integer)_methods.get($method);
      if (__method == null) {
         throw new BAD_OPERATION(0, CompletionStatus.COMPLETED_MAYBE);
      } else {
         switch (__method) {
            case 0:
               this.before_completion();
               out = $rh.createReply();
               break;
            case 1:
               Status s = StatusHelper.read(in);
               this.after_completion(s);
               out = $rh.createReply();
               break;
            default:
               throw new BAD_OPERATION(0, CompletionStatus.COMPLETED_MAYBE);
         }

         return out;
      }
   }

   public String[] _ids() {
      return (String[])((String[])__ids.clone());
   }

   static {
      _methods.put("before_completion", 0);
      _methods.put("after_completion", 1);
      __ids = new String[]{"IDL:omg.org/CosTransactions/Synchronization:1.0", "IDL:omg.org/CosTransactions/TransactionalObject:1.0"};
   }
}
