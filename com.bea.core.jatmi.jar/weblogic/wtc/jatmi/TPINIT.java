package weblogic.wtc.jatmi;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import weblogic.wtc.WTCLogger;

public final class TPINIT extends StandardTypes implements TypedBuffer, Serializable {
   private static final long serialVersionUID = -5616463029754409007L;
   public String usrname;
   public String cltname;
   public String passwd;
   public String grpname;
   public int flags;
   public boolean no_usrpasswd;
   public byte[] data;
   public boolean use_string_usrpasswd;
   public String usrpasswd;

   public TPINIT() {
      super("TPINIT", 3);
      this.no_usrpasswd = true;
      this.use_string_usrpasswd = false;
   }

   public TPINIT(String usrname, String cltname, String passwd, String grpname, int flags) {
      super("TPINIT", 3);
      if (usrname != null) {
         this.usrname = new String(usrname);
      }

      if (cltname != null) {
         this.cltname = new String(cltname);
      }

      if (passwd != null) {
         this.passwd = new String(passwd);
      }

      if (grpname != null) {
         this.grpname = new String(grpname);
      }

      this.flags = flags;
      this.no_usrpasswd = true;
      this.use_string_usrpasswd = false;
   }

   public TPINIT(String usrname, String cltname, String passwd, String grpname, int flags, byte[] data) {
      super("TPINIT", 3);
      if (usrname != null) {
         this.usrname = new String(usrname);
      }

      if (cltname != null) {
         this.cltname = new String(cltname);
      }

      if (passwd != null) {
         this.passwd = new String(passwd);
      }

      if (grpname != null) {
         this.grpname = new String(grpname);
      }

      this.flags = flags;
      this.no_usrpasswd = false;
      this.use_string_usrpasswd = false;
      if (data != null) {
         this.data = new byte[data.length];

         for(int lcv = 0; lcv < data.length; ++lcv) {
            this.data[lcv] = data[lcv];
         }
      }

   }

   public TPINIT(String usrname, String cltname, String passwd, String grpname, int flags, String usrpasswd) {
      super("TPINIT", 3);
      if (usrname != null) {
         this.usrname = new String(usrname);
      }

      if (cltname != null) {
         this.cltname = new String(cltname);
      }

      if (passwd != null) {
         this.passwd = new String(passwd);
      }

      if (grpname != null) {
         this.grpname = new String(grpname);
      }

      this.flags = flags;
      this.no_usrpasswd = false;
      this.use_string_usrpasswd = true;
      if (usrpasswd != null) {
         this.usrpasswd = new String(usrpasswd);
      }

   }

   public void _tmpresend(DataOutputStream encoder) throws TPException, IOException {
      throw new TPException(9, "We never send the tpinit buffer!");
   }

   public void _tmpostrecv(DataInputStream decoder, int recv_size) throws TPException {
      WTCLogger.logErrorTpinitBuffer();
      throw new TPException(4, "Never postrecv the TPINIT buffer");
   }
}
