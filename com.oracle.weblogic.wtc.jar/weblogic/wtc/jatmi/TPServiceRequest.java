package weblogic.wtc.jatmi;

import com.bea.core.jatmi.common.ntrace;
import com.bea.core.jatmi.intf.TCAuthenticatedUser;
import com.bea.core.jatmi.intf.TCTask;
import java.io.DataOutputStream;
import java.io.IOException;

public class TPServiceRequest implements TCTask {
   private InvokeSvc how_to_do_them;
   private gwatmi mysession;
   private int protocol;
   private DataOutputStream output_stream;
   private tfmh myServiceRequest;
   String myLocalDomain;
   private BetaFeatures beta;
   TCAuthenticatedUser mySubject;
   private String myName;

   public TPServiceRequest(gwatmi ss, int theProtocol, String localDomain, DataOutputStream os, InvokeSvc htd, tfmh serviceRequest, BetaFeatures betaFeatures) throws TPException {
      if (ss != null && os != null && htd != null && serviceRequest != null && localDomain != null) {
         this.mysession = ss;
         this.protocol = theProtocol;
         this.myLocalDomain = localDomain;
         this.output_stream = os;
         this.how_to_do_them = htd;
         this.myServiceRequest = serviceRequest;
         this.beta = betaFeatures;
      } else {
         throw new TPException(4, "Invalid null parameter/" + ss + "/" + os + "/" + htd + "/" + serviceRequest + "/" + localDomain);
      }
   }

   public int execute() {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/TPServiceRequest/execute/");
      }

      int myTPException = 0;
      int mytperrordetail = 0;
      int mytpurcode = 0;
      dreqid myReqid = null;
      int service_flags = 0;
      tfmh tmmsg = this.myServiceRequest;
      if (tmmsg.tdom == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/TPServiceRequest/10/Unknown message format");
         }

         return 0;
      } else {
         TdomTcb tmmsg_tdom = (TdomTcb)tmmsg.tdom.body;
         myReqid = new dreqid(tmmsg_tdom.get_reqid());
         int myConvid = tmmsg_tdom.get_convid();
         int flag = tmmsg_tdom.get_flag();
         if ((flag & 4) != 0) {
            service_flags |= 4;
         }

         if (myConvid != -1) {
            service_flags |= 1024;
            if ((flag & 2048) != 0) {
               service_flags |= 4096;
            } else {
               service_flags |= 2048;
            }
         }

         TypedBuffer tb;
         if (tmmsg.user == null) {
            tb = null;
         } else {
            UserTcb utcb = (UserTcb)tmmsg.user.body;
            tb = utcb.user_data;
         }

         if (myTPException == 0) {
            if (this.how_to_do_them != null) {
               InvokeInfo si = new InvokeInfo(tmmsg_tdom.get_service(), tb, service_flags, tmmsg, myReqid, this.mysession.getUid(), myConvid);
               if (this.mySubject != null) {
                  si.setTargetSubject(this.mySubject);
               }

               try {
                  if (traceEnabled) {
                     ntrace.doTrace("/servicethr/calling invoker/" + si);
                  }

                  this.how_to_do_them.invoke(si, this.mysession);
                  if (traceEnabled) {
                     ntrace.doTrace("/servicethr/invoker success/" + si);
                  }

                  myTPException = 0;
               } catch (TPException var21) {
                  if (traceEnabled) {
                     ntrace.doTrace("/servicethr/invoker threw TPException " + var21 + "/");
                  }

                  myTPException = var21.gettperrno();
                  mytpurcode = var21.gettpurcode();
                  mytperrordetail = var21.gettperrordetail();
               }
            } else {
               if (traceEnabled) {
                  ntrace.doTrace("/servicethr/no invoker/");
               }

               myTPException = 6;
            }
         }

         if (myTPException != 0) {
            if (traceEnabled) {
               ntrace.doTrace("/servicethr/sending failure/");
            }

            tfmh fail_tmmsg = new tfmh(1);
            TdomTcb fail_tmmsg_tdom = new TdomTcb(3, myReqid.reqid, 0, (String)null);
            fail_tmmsg_tdom.set_errdetail(mytperrordetail);
            fail_tmmsg_tdom.set_tpurcode(mytpurcode);
            fail_tmmsg_tdom.set_diagnostic(myTPException);
            fail_tmmsg.tdom = new tcm((short)7, fail_tmmsg_tdom);
            synchronized(this.output_stream) {
               try {
                  if (this.protocol >= 15) {
                     fail_tmmsg.write_tfmh(this.output_stream, this.mysession.getCompressionThreshold());
                  } else {
                     fail_tmmsg.write_dom_65_tfmh(this.output_stream, this.myLocalDomain, this.protocol, this.mysession.getCompressionThreshold());
                  }

                  if (traceEnabled) {
                     ntrace.doTrace("/servicethr/failure sent/");
                  }
               } catch (IOException var19) {
                  if (traceEnabled) {
                     ntrace.doTrace("/servicethr/Could not send failure/" + var19);
                  }
               }
            }
         }

         if (traceEnabled) {
            ntrace.doTrace("]/TPServiceRequest/20/Success");
         }

         return 0;
      }
   }

   public void setTargetSubject(TCAuthenticatedUser subj) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/TPServiceRequest/setTargetSubject/");
      }

      this.mySubject = subj;
      if (traceEnabled) {
         ntrace.doTrace("]/TPServiceRequest/setTargetSubject/10");
      }

   }

   public void setTaskName(String tname) {
      this.myName = new String("TPServiceRequest$" + tname);
   }

   public String getTaskName() {
      return this.myName == null ? "TPServiceRequest$unknown" : this.myName;
   }
}
