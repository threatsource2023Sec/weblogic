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

public abstract class _ControlImplBase extends ObjectImpl implements Control, InvokeHandler {
   private static Map _methods = new HashMap();
   private static String[] __ids;

   public OutputStream _invoke(String $method, InputStream in, ResponseHandler $rh) {
      OutputStream out = null;
      Integer __method = (Integer)_methods.get($method);
      if (__method == null) {
         throw new BAD_OPERATION(0, CompletionStatus.COMPLETED_MAYBE);
      } else {
         Coordinator $result;
         switch (__method) {
            case 0:
               try {
                  $result = null;
                  Terminator $result = this.get_terminator();
                  out = $rh.createReply();
                  TerminatorHelper.write(out, $result);
               } catch (Unavailable var8) {
                  out = $rh.createExceptionReply();
                  UnavailableHelper.write(out, var8);
               }
               break;
            case 1:
               try {
                  $result = null;
                  $result = this.get_coordinator();
                  out = $rh.createReply();
                  CoordinatorHelper.write(out, $result);
               } catch (Unavailable var7) {
                  out = $rh.createExceptionReply();
                  UnavailableHelper.write(out, var7);
               }
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
      _methods.put("get_terminator", 0);
      _methods.put("get_coordinator", 1);
      __ids = new String[]{"IDL:omg.org/CosTransactions/Control:1.0"};
   }
}
