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

public abstract class _ResourceImplBase extends ObjectImpl implements Resource, InvokeHandler {
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
                  Vote $result = null;
                  $result = this.prepare();
                  out = $rh.createReply();
                  VoteHelper.write(out, $result);
               } catch (HeuristicMixed var15) {
                  out = $rh.createExceptionReply();
                  HeuristicMixedHelper.write(out, var15);
               } catch (HeuristicHazard var16) {
                  out = $rh.createExceptionReply();
                  HeuristicHazardHelper.write(out, var16);
               }
               break;
            case 1:
               try {
                  this.rollback();
                  out = $rh.createReply();
               } catch (HeuristicCommit var12) {
                  out = $rh.createExceptionReply();
                  HeuristicCommitHelper.write(out, var12);
               } catch (HeuristicMixed var13) {
                  out = $rh.createExceptionReply();
                  HeuristicMixedHelper.write(out, var13);
               } catch (HeuristicHazard var14) {
                  out = $rh.createExceptionReply();
                  HeuristicHazardHelper.write(out, var14);
               }
               break;
            case 2:
               try {
                  this.commit();
                  out = $rh.createReply();
               } catch (NotPrepared var8) {
                  out = $rh.createExceptionReply();
                  NotPreparedHelper.write(out, var8);
               } catch (HeuristicRollback var9) {
                  out = $rh.createExceptionReply();
                  HeuristicRollbackHelper.write(out, var9);
               } catch (HeuristicMixed var10) {
                  out = $rh.createExceptionReply();
                  HeuristicMixedHelper.write(out, var10);
               } catch (HeuristicHazard var11) {
                  out = $rh.createExceptionReply();
                  HeuristicHazardHelper.write(out, var11);
               }
               break;
            case 3:
               try {
                  this.commit_one_phase();
                  out = $rh.createReply();
               } catch (HeuristicHazard var7) {
                  out = $rh.createExceptionReply();
                  HeuristicHazardHelper.write(out, var7);
               }
               break;
            case 4:
               this.forget();
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
      _methods.put("prepare", 0);
      _methods.put("rollback", 1);
      _methods.put("commit", 2);
      _methods.put("commit_one_phase", 3);
      _methods.put("forget", 4);
      __ids = new String[]{"IDL:omg.org/CosTransactions/Resource:1.0"};
   }
}
