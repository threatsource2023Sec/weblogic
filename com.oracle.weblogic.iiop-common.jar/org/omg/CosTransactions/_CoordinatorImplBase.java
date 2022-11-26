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

public abstract class _CoordinatorImplBase extends ObjectImpl implements Coordinator, InvokeHandler {
   private static Map _methods = new HashMap();
   private static String[] __ids;

   public OutputStream _invoke(String $method, InputStream in, ResponseHandler $rh) {
      OutputStream out = null;
      Integer __method = (Integer)_methods.get($method);
      if (__method == null) {
         throw new BAD_OPERATION(0, CompletionStatus.COMPLETED_MAYBE);
      } else {
         PropagationContext $result;
         boolean $result;
         boolean $result;
         int $result;
         Coordinator tc;
         Status $result;
         switch (__method) {
            case 0:
               $result = null;
               $result = this.get_status();
               out = $rh.createReply();
               StatusHelper.write(out, $result);
               break;
            case 1:
               $result = null;
               $result = this.get_parent_status();
               out = $rh.createReply();
               StatusHelper.write(out, $result);
               break;
            case 2:
               $result = null;
               $result = this.get_top_level_status();
               out = $rh.createReply();
               StatusHelper.write(out, $result);
               break;
            case 3:
               tc = CoordinatorHelper.read(in);
               $result = false;
               $result = this.is_same_transaction(tc);
               out = $rh.createReply();
               out.write_boolean($result);
               break;
            case 4:
               tc = CoordinatorHelper.read(in);
               $result = false;
               $result = this.is_related_transaction(tc);
               out = $rh.createReply();
               out.write_boolean($result);
               break;
            case 5:
               tc = CoordinatorHelper.read(in);
               $result = false;
               $result = this.is_ancestor_transaction(tc);
               out = $rh.createReply();
               out.write_boolean($result);
               break;
            case 6:
               tc = CoordinatorHelper.read(in);
               $result = false;
               $result = this.is_descendant_transaction(tc);
               out = $rh.createReply();
               out.write_boolean($result);
               break;
            case 7:
               $result = false;
               $result = this.is_top_level_transaction();
               out = $rh.createReply();
               out.write_boolean($result);
               break;
            case 8:
               $result = false;
               $result = this.hash_transaction();
               out = $rh.createReply();
               out.write_ulong($result);
               break;
            case 9:
               $result = false;
               $result = this.hash_top_level_tran();
               out = $rh.createReply();
               out.write_ulong($result);
               break;
            case 10:
               try {
                  Resource r = ResourceHelper.read(in);
                  RecoveryCoordinator $result = null;
                  $result = this.register_resource(r);
                  out = $rh.createReply();
                  RecoveryCoordinatorHelper.write(out, $result);
               } catch (Inactive var16) {
                  out = $rh.createExceptionReply();
                  InactiveHelper.write(out, var16);
               }
               break;
            case 11:
               try {
                  Synchronization sync = SynchronizationHelper.read(in);
                  this.register_synchronization(sync);
                  out = $rh.createReply();
               } catch (Inactive var14) {
                  out = $rh.createExceptionReply();
                  InactiveHelper.write(out, var14);
               } catch (SynchronizationUnavailable var15) {
                  out = $rh.createExceptionReply();
                  SynchronizationUnavailableHelper.write(out, var15);
               }
               break;
            case 12:
               try {
                  SubtransactionAwareResource r = SubtransactionAwareResourceHelper.read(in);
                  this.register_subtran_aware(r);
                  out = $rh.createReply();
               } catch (Inactive var12) {
                  out = $rh.createExceptionReply();
                  InactiveHelper.write(out, var12);
               } catch (NotSubtransaction var13) {
                  out = $rh.createExceptionReply();
                  NotSubtransactionHelper.write(out, var13);
               }
               break;
            case 13:
               try {
                  this.rollback_only();
                  out = $rh.createReply();
               } catch (Inactive var11) {
                  out = $rh.createExceptionReply();
                  InactiveHelper.write(out, var11);
               }
               break;
            case 14:
               $result = null;
               String $result = this.get_transaction_name();
               out = $rh.createReply();
               out.write_string($result);
               break;
            case 15:
               try {
                  $result = null;
                  Control $result = this.create_subtransaction();
                  out = $rh.createReply();
                  ControlHelper.write(out, $result);
               } catch (SubtransactionsUnavailable var9) {
                  out = $rh.createExceptionReply();
                  SubtransactionsUnavailableHelper.write(out, var9);
               } catch (Inactive var10) {
                  out = $rh.createExceptionReply();
                  InactiveHelper.write(out, var10);
               }
               break;
            case 16:
               try {
                  $result = null;
                  $result = this.get_txcontext();
                  out = $rh.createReply();
                  PropagationContextHelper.write(out, $result);
               } catch (Unavailable var8) {
                  out = $rh.createExceptionReply();
                  UnavailableHelper.write(out, var8);
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
      _methods.put("get_status", 0);
      _methods.put("get_parent_status", 1);
      _methods.put("get_top_level_status", 2);
      _methods.put("is_same_transaction", 3);
      _methods.put("is_related_transaction", 4);
      _methods.put("is_ancestor_transaction", 5);
      _methods.put("is_descendant_transaction", 6);
      _methods.put("is_top_level_transaction", 7);
      _methods.put("hash_transaction", 8);
      _methods.put("hash_top_level_tran", 9);
      _methods.put("register_resource", 10);
      _methods.put("register_synchronization", 11);
      _methods.put("register_subtran_aware", 12);
      _methods.put("rollback_only", 13);
      _methods.put("get_transaction_name", 14);
      _methods.put("create_subtransaction", 15);
      _methods.put("get_txcontext", 16);
      __ids = new String[]{"IDL:omg.org/CosTransactions/Coordinator:1.0"};
   }
}
