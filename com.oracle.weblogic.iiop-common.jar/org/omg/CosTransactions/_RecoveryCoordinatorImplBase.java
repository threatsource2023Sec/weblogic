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

public abstract class _RecoveryCoordinatorImplBase extends ObjectImpl implements RecoveryCoordinator, InvokeHandler {
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
               try {
                  Resource r = ResourceHelper.read(in);
                  Status $result = null;
                  $result = this.replay_completion(r);
                  out = $rh.createReply();
                  StatusHelper.write(out, $result);
               } catch (NotPrepared var8) {
                  out = $rh.createExceptionReply();
                  NotPreparedHelper.write(out, var8);
               }

               return out;
            default:
               throw new BAD_OPERATION(0, CompletionStatus.COMPLETED_MAYBE);
         }
      }
   }

   public String[] _ids() {
      return (String[])((String[])__ids.clone());
   }

   static {
      _methods.put("replay_completion", 0);
      __ids = new String[]{"IDL:omg.org/CosTransactions/RecoveryCoordinator:1.0"};
   }
}
