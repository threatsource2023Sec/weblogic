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

public abstract class _CurrentImplBase extends ObjectImpl implements Current, InvokeHandler {
   private static Map _methods = new HashMap();
   private static String[] __ids;

   public OutputStream _invoke(String $method, InputStream in, ResponseHandler $rh) {
      OutputStream out = null;
      Integer __method = (Integer)_methods.get($method);
      if (__method == null) {
         throw new BAD_OPERATION(0, CompletionStatus.COMPLETED_MAYBE);
      } else {
         Control which;
         boolean report_heuristics;
         int $result;
         switch (__method) {
            case 0:
               try {
                  this.begin();
                  out = $rh.createReply();
               } catch (SubtransactionsUnavailable var13) {
                  out = $rh.createExceptionReply();
                  SubtransactionsUnavailableHelper.write(out, var13);
               }
               break;
            case 1:
               try {
                  report_heuristics = in.read_boolean();
                  this.commit(report_heuristics);
                  out = $rh.createReply();
               } catch (NoTransaction var10) {
                  out = $rh.createExceptionReply();
                  NoTransactionHelper.write(out, var10);
               } catch (HeuristicMixed var11) {
                  out = $rh.createExceptionReply();
                  HeuristicMixedHelper.write(out, var11);
               } catch (HeuristicHazard var12) {
                  out = $rh.createExceptionReply();
                  HeuristicHazardHelper.write(out, var12);
               }
               break;
            case 2:
               try {
                  this.rollback();
                  out = $rh.createReply();
               } catch (NoTransaction var9) {
                  out = $rh.createExceptionReply();
                  NoTransactionHelper.write(out, var9);
               }
               break;
            case 3:
               try {
                  this.rollback_only();
                  out = $rh.createReply();
               } catch (NoTransaction var8) {
                  out = $rh.createExceptionReply();
                  NoTransactionHelper.write(out, var8);
               }
               break;
            case 4:
               which = null;
               Status $result = this.get_status();
               out = $rh.createReply();
               StatusHelper.write(out, $result);
               break;
            case 5:
               which = null;
               String $result = this.get_transaction_name();
               out = $rh.createReply();
               out.write_string($result);
               break;
            case 6:
               $result = in.read_ulong();
               this.set_timeout($result);
               out = $rh.createReply();
               break;
            case 7:
               report_heuristics = false;
               $result = this.get_timeout();
               out = $rh.createReply();
               out.write_ulong($result);
               break;
            case 8:
               which = null;
               which = this.get_control();
               out = $rh.createReply();
               ControlHelper.write(out, which);
               break;
            case 9:
               which = null;
               which = this.suspend();
               out = $rh.createReply();
               ControlHelper.write(out, which);
               break;
            case 10:
               try {
                  which = ControlHelper.read(in);
                  this.resume(which);
                  out = $rh.createReply();
               } catch (InvalidControl var7) {
                  out = $rh.createExceptionReply();
                  InvalidControlHelper.write(out, var7);
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
      _methods.put("begin", 0);
      _methods.put("commit", 1);
      _methods.put("rollback", 2);
      _methods.put("rollback_only", 3);
      _methods.put("get_status", 4);
      _methods.put("get_transaction_name", 5);
      _methods.put("set_timeout", 6);
      _methods.put("get_timeout", 7);
      _methods.put("get_control", 8);
      _methods.put("suspend", 9);
      _methods.put("resume", 10);
      __ids = new String[]{"IDL:omg.org/CosTransactions/Current:1.0"};
   }
}
