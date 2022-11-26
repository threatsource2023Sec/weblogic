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

public abstract class _TerminatorImplBase extends ObjectImpl implements Terminator, InvokeHandler {
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
                  boolean report_heuristics = in.read_boolean();
                  this.commit(report_heuristics);
                  out = $rh.createReply();
               } catch (HeuristicMixed var7) {
                  out = $rh.createExceptionReply();
                  HeuristicMixedHelper.write(out, var7);
               } catch (HeuristicHazard var8) {
                  out = $rh.createExceptionReply();
                  HeuristicHazardHelper.write(out, var8);
               }
               break;
            case 1:
               this.rollback();
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
      _methods.put("commit", 0);
      _methods.put("rollback", 1);
      __ids = new String[]{"IDL:omg.org/CosTransactions/Terminator:1.0"};
   }
}
