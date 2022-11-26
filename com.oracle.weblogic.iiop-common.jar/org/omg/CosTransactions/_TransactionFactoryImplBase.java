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

public abstract class _TransactionFactoryImplBase extends ObjectImpl implements TransactionFactory, InvokeHandler {
   private static Map _methods = new HashMap();
   private static String[] __ids;

   public OutputStream _invoke(String $method, InputStream in, ResponseHandler $rh) {
      OutputStream out = null;
      Integer __method = (Integer)_methods.get($method);
      if (__method == null) {
         throw new BAD_OPERATION(0, CompletionStatus.COMPLETED_MAYBE);
      } else {
         Control $result;
         switch (__method) {
            case 0:
               int time_out = in.read_ulong();
               $result = null;
               $result = this.create(time_out);
               out = $rh.createReply();
               ControlHelper.write(out, $result);
               break;
            case 1:
               PropagationContext ctx = PropagationContextHelper.read(in);
               $result = null;
               $result = this.recreate(ctx);
               out = $rh.createReply();
               ControlHelper.write(out, $result);
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
      _methods.put("create", 0);
      _methods.put("recreate", 1);
      __ids = new String[]{"IDL:omg.org/CosTransactions/TransactionFactory:1.0"};
   }
}
