package weblogic.wtc.jatmi;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import weblogic.wtc.WTCLogger;

public final class rsession implements Runnable {
   private DataInputStream istream;
   private Map map;
   private boolean is_term;
   private int[] cd_array_flags;
   private WsKey[] cd_array;
   private unsolthr unsol_hndlr;
   private Thread unsolicited;
   private int srv_protocol;
   private static final int WSCUSEDBIT = 1;
   private static final int WSCARRBIT = 2;
   private static final int WSCCOREBIT = 4;
   private static final int WSCTHANDLE = 8;
   static final int TM_MAXHANDLES = 50;

   public rsession(DataInputStream wsh_istream, DataOutputStream wsh_ostream, unsol hndlr, int cltid, int wscpid, int wsh_protocol) {
      this(wsh_istream, wsh_ostream, hndlr, cltid, wscpid);
      this.srv_protocol = wsh_protocol;
   }

   public rsession(DataInputStream wsh_istream, DataOutputStream wsh_ostream, unsol hndlr, int cltid, int wscpid) {
      this.is_term = false;
      this.srv_protocol = 49;
      this.istream = wsh_istream;
      this.map = Collections.synchronizedMap(new HashMap());
      this.cd_array = new WsKey[50];
      this.cd_array_flags = new int[50];

      for(int lcv = 0; lcv < 50; ++lcv) {
         this.cd_array[lcv] = null;
         this.cd_array_flags[lcv] = 0;
      }

      this.unsol_hndlr = new unsolthr(hndlr, wsh_ostream, cltid, wscpid);
      this.unsolicited = new Thread(this.unsol_hndlr);
      this.unsolicited.start();
   }

   public void set_unsol_hndlr(unsol hdlr) {
      this.unsol_hndlr.set_unsol_hdlr(hdlr);
   }

   public Object get_cd_lock() {
      return this.cd_array;
   }

   public int reserve_cd(boolean intransaction) {
      int lcv;
      synchronized(this.cd_array) {
         lcv = 0;

         while(true) {
            if (lcv >= 50 || this.cd_array[lcv] == null) {
               if (lcv < 50) {
                  if (intransaction) {
                     this.cd_array_flags[lcv] = 8;
                  } else {
                     this.cd_array_flags[lcv] = 0;
                  }
               }
               break;
            }

            ++lcv;
         }
      }

      return lcv >= 50 ? -1 : lcv;
   }

   public int make_cd(int cd, WsKey key) {
      if (cd >= 0 && cd < 50 && key != null && key.get_cd() == cd) {
         synchronized(this.cd_array) {
            if (this.cd_array[cd] != null) {
               return -1;
            } else {
               this.cd_array[cd] = key;
               return 0;
            }
         }
      } else {
         return -1;
      }
   }

   public void clear_wskey(int cd) {
      if (cd >= 0 && cd < 50) {
         synchronized(this.cd_array) {
            this.cd_array[cd] = null;
            this.cd_array_flags[cd] = 0;
         }
      }
   }

   public WsKey get_wskey(int cd) {
      if (cd >= 0 && cd < 50) {
         synchronized(this.cd_array) {
            WsKey rval = this.cd_array[cd];
            return rval;
         }
      } else {
         return null;
      }
   }

   public int mkstale() {
      int rc = 0;
      WsKey[] toretrieve = new WsKey[50];
      int nr = 0;
      int lcv;
      synchronized(this.cd_array) {
         for(lcv = 0; lcv < 50; ++lcv) {
            if (this.cd_array[lcv] != null) {
               if ((this.cd_array_flags[lcv] & 2) != 0) {
                  toretrieve[nr++] = this.cd_array[lcv];
               }

               this.cd_array[lcv] = null;
               ++rc;
            }
         }
      }

      for(lcv = 0; lcv < nr; ++lcv) {
         this.getlist(toretrieve[lcv], true);
      }

      return rc;
   }

   public boolean get_is_term() {
      return this.is_term;
   }

   private synchronized void addlist(tfmh tmmsg) {
      WsTcb ws = (WsTcb)tmmsg.ws.body;
      WsKey wskey = ws.get_key();
      if (ws.get_opcode() == 3) {
         int ws_handle = ws.get_HANDLE();
         wskey.set_thandle(ws_handle);
         int lcv;
         synchronized(this.cd_array) {
            lcv = 0;

            while(true) {
               if (lcv >= 50 || this.cd_array[lcv] != null && ws_handle == this.cd_array[lcv].get_thandle()) {
                  if (lcv < 50) {
                     int[] var10000 = this.cd_array_flags;
                     var10000[lcv] |= 2;
                  }
                  break;
               }

               ++lcv;
            }
         }

         if (lcv >= 50) {
            WTCLogger.logWarnOWSAREPLY();
         } else {
            wskey.set_cd(lcv);
         }
      }

      this.map.put(wskey, tmmsg);
      this.notifyAll();
   }

   public synchronized tfmh getlist(WsKey wskey, boolean block) {
      tfmh tmmsg;
      if ((tmmsg = (tfmh)this.map.remove(wskey)) != null) {
         return tmmsg;
      } else if (!block) {
         return null;
      } else {
         int cd = wskey.get_cd();

         while((tmmsg = (tfmh)this.map.remove(wskey)) == null) {
            try {
               this.wait();
            } catch (InterruptedException var8) {
               return null;
            }

            if (cd != -1) {
               synchronized(this.cd_array) {
                  if (this.cd_array[cd] == null) {
                     return null;
                  }
               }
            }
         }

         return tmmsg;
      }
   }

   public synchronized int getany(boolean block) {
      while(true) {
         synchronized(this.cd_array) {
            int lcv;
            for(lcv = 0; lcv < 50 && (this.cd_array[lcv] == null || (this.cd_array_flags[lcv] & 2) == 0); ++lcv) {
            }

            if (lcv < 50) {
               return lcv;
            }
         }

         if (!block) {
            return -1;
         }

         try {
            this.wait();
         } catch (InterruptedException var5) {
            return -1;
         }
      }
   }

   public void run() {
      while(true) {
         try {
            tfmh tmmsg = new tfmh();
            int ret;
            if (this.srv_protocol <= 46) {
               ret = tmmsg.read_ws_65_tfmh(this.istream, false);
            } else {
               ret = tmmsg.read_tfmh(this.istream);
            }

            if (ret == -1) {
               WTCLogger.logErrorReadTfmh();
               this.is_term = true;
               this.unsol_hndlr.stop_unsol_thr();
               this.unsol_hndlr = null;
               return;
            }

            if (tmmsg.ws == null) {
               WTCLogger.logErrorNullTmmsgWs();
               this.is_term = true;
               this.unsol_hndlr.stop_unsol_thr();
               this.unsol_hndlr = null;
               return;
            }

            WsTcb ws = (WsTcb)tmmsg.ws.body;
            int opcode = ws.get_opcode();
            switch (opcode) {
               case 14:
                  this.unsol_hndlr.add_unsol_msg(tmmsg);
               case 23:
                  break;
               default:
                  this.addlist(tmmsg);
            }

            if (opcode == 12) {
               this.is_term = true;
               this.unsol_hndlr.stop_unsol_thr();
               this.unsol_hndlr = null;
               return;
            }
         } catch (IOException var6) {
            WTCLogger.logIOEbadRsessionClose(var6.getMessage());
            this.is_term = true;
            this.unsol_hndlr.stop_unsol_thr();
            this.unsol_hndlr = null;
            return;
         }
      }
   }
}
