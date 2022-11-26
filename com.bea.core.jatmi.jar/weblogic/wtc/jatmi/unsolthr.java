package weblogic.wtc.jatmi;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import weblogic.wtc.WTCLogger;

public class unsolthr implements Runnable {
   private Object unsol_lock = new Object();
   private unsol how_to_do_them;
   private LinkedList things_to_do;
   private DataOutputStream output_stream;
   private boolean quitnow = false;
   private int cltid;
   private int wscpid;

   public unsolthr(unsol hndlr, DataOutputStream output_stream, int cltid, int wscpid) {
      this.how_to_do_them = hndlr;
      this.things_to_do = new LinkedList();
      this.output_stream = output_stream;
      this.cltid = cltid;
      this.wscpid = wscpid;
   }

   public void stop_unsol_thr() {
      this.quitnow = true;
      synchronized(this.things_to_do) {
         this.things_to_do.notifyAll();
      }
   }

   public void set_unsol_hdlr(unsol hndlr) {
      synchronized(this.unsol_lock) {
         this.how_to_do_them = hndlr;
      }
   }

   public void add_unsol_msg(tfmh tmmsg) {
      synchronized(this.things_to_do) {
         this.things_to_do.addLast(tmmsg);
         this.things_to_do.notifyAll();
      }
   }

   public void run() {
      while(!this.quitnow) {
         tfmh tmmsg;
         synchronized(this.things_to_do) {
            while(this.things_to_do.size() == 0) {
               try {
                  this.things_to_do.wait();
               } catch (InterruptedException var20) {
                  return;
               }

               if (this.quitnow) {
                  return;
               }
            }

            tmmsg = (tfmh)this.things_to_do.removeFirst();
         }

         TypedBuffer tb;
         if (tmmsg.user == null) {
            tb = null;
         } else {
            UserTcb utcb = (UserTcb)tmmsg.user.body;
            tb = utcb.user_data;
         }

         int myTPException;
         int myUunixerr;
         int mytpurcode;
         int mytperrordetail;
         synchronized(this.unsol_lock) {
            if (this.how_to_do_them != null) {
               try {
                  myTPException = 0;
                  myUunixerr = 0;
                  mytpurcode = this.how_to_do_them.disp(tb, 0);
                  mytperrordetail = 0;
               } catch (TPException var18) {
                  myTPException = var18.gettperrno();
                  myUunixerr = var18.getUunixerr();
                  mytpurcode = 0;
                  mytperrordetail = var18.gettperrordetail();
               }
            } else {
               myTPException = 6;
               myUunixerr = 0;
               mytpurcode = 0;
               mytperrordetail = 3;
            }
         }

         WsTcb ws = (WsTcb)tmmsg.ws.body;
         if (ws.wsrplywanted()) {
            WsTcb tmmsg_ws_out = new WsTcb(23);
            tmmsg_ws_out.set_HANDLE(this.wscpid);
            tmmsg_ws_out.set_CLTID(this.cltid);
            tmmsg_ws_out.set_ws_TPException(myTPException);
            tmmsg_ws_out.set_ws_Uunixerr(myUunixerr);
            tmmsg_ws_out.set_ws_tpurcode(mytpurcode);
            tmmsg_ws_out.set_ws_tperrordetail(mytperrordetail);
            tfmh tmmsg_out = new tfmh();
            tmmsg_out.ws = new tcm((short)3, tmmsg_ws_out);
            synchronized(this.output_stream) {
               try {
                  tmmsg_out.write_tfmh(this.output_stream, Integer.MAX_VALUE);
               } catch (IOException var16) {
                  WTCLogger.logIOEbadUnsolAck(var16.getMessage());
               }
            }
         }
      }

   }
}
