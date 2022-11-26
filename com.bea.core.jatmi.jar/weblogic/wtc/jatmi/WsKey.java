package weblogic.wtc.jatmi;

public final class WsKey {
   private int opcode;
   private int reqgen;
   private int thandle = -1;
   private int cd = -1;
   private int opcode_flags;
   static final int WSCMAXSEQUENCE = 64000;
   static final int OWS_ABORT = 1;
   static final int OWS_ACALL = 2;
   static final int OWS_AREPLY = 3;
   static final int OWS_BROADCAST = 4;
   static final int OWS_CALL = 5;
   static final int OWS_CHALLENGE = 6;
   static final int OWS_COMMIT = 7;
   static final int OWS_ESTCON = 8;
   static final int OWS_GETRPLY = 9;
   static final int OWS_HSHUT = 10;
   static final int OWS_INIT6 = 11;
   static final int OWS_TERM = 12;
   static final int OWS_TICKET = 13;
   static final int OWS_UNSOL = 14;
   static final int OWS_CONNECT = 15;
   static final int OWS_DISCON = 16;
   static final int OWS_RECV = 17;
   static final int OWS_SEND = 18;
   static final int OWS_TXINFO = 19;
   static final int OWS_SUSPEND = 20;
   static final int OWS_RESUME = 21;
   static final int OWS_NOTIFY = 22;
   static final int OWS_UNSOL_A = 23;
   static final int OWS_LLE = 24;
   static final int OWS_ATN = 25;
   static final int OWS_INIT = 27;
   static final int OWS_BEGIN = Integer.MIN_VALUE;
   static final int OWS_REPLY = 1073741824;
   static final int OWS_MASK = -1073741824;

   public WsKey() {
      this.opcode = 3;
      this.reqgen = 0;
      this.opcode_flags = 0;
   }

   public WsKey(int opcode, int reqgen) {
      this.opcode = opcode & 1073741823;
      this.reqgen = reqgen;
      this.opcode_flags = opcode & -1073741824;
   }

   public int get_opcode() {
      return this.opcode;
   }

   public int get_reqgen() {
      return this.reqgen;
   }

   public int get_thandle() {
      return this.thandle;
   }

   public int get_cd() {
      return this.cd;
   }

   public void convert_to_AREPLY(int thandle, int cd) {
      this.opcode = 3;
      this.reqgen = 0;
      this.thandle = thandle;
      this.cd = cd;
   }

   public void set_thandle(int thandle) {
      this.thandle = thandle;
   }

   public void set_cd(int cd) {
      this.cd = cd;
   }

   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      } else {
         WsKey wskey = (WsKey)obj;
         int wskey_opcode = wskey.get_opcode();
         int wskey_reqgen = wskey.get_reqgen();
         int wskey_thandle = wskey.get_thandle();
         int wskey_cd = wskey.get_cd();
         if (wskey_reqgen == this.reqgen && wskey_opcode == this.opcode) {
            if (wskey_thandle != -1 && this.thandle != -1) {
               return wskey_thandle == this.thandle && wskey_cd == this.cd;
            } else {
               return true;
            }
         } else {
            return false;
         }
      }
   }

   public String toString() {
      return new String(" opcode=" + this.opcode + ";reqgen=" + this.reqgen + ";thandle=" + this.thandle + ";cd=" + this.cd + ";");
   }

   public int hashCode() {
      return this.reqgen;
   }
}
