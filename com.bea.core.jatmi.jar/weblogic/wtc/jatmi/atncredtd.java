package weblogic.wtc.jatmi;

import com.bea.core.jatmi.common.Utilities;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import weblogic.wtc.WTCLogger;

public final class atncredtd implements atncred {
   public String cred_usrname;
   public String cred_cltname;
   public String cred_passwd;
   public String cred_grpname;
   public int cred_flags;
   public int cred_usage;
   public byte[] cred_proof;
   public int cred_timestamp;

   public atncredtd(TPINIT tpinfo, int timestamp) {
      this.cred_timestamp = timestamp;
      if (tpinfo != null) {
         this.cred_usrname = new String(tpinfo.usrname);
         this.cred_cltname = new String(tpinfo.cltname);
         this.cred_passwd = new String(tpinfo.passwd);
         this.cred_grpname = new String(tpinfo.grpname);
         this.cred_flags = tpinfo.flags;
         if (!tpinfo.no_usrpasswd) {
            if (tpinfo.use_string_usrpasswd) {
               try {
                  ByteArrayOutputStream bstream = new ByteArrayOutputStream();
                  DataOutputStream encoder = new DataOutputStream(bstream);
                  byte[] usrpasswdBytes = Utilities.getEncBytes(tpinfo.usrpasswd);
                  encoder.write(usrpasswdBytes);
                  this.cred_proof = bstream.toByteArray();
               } catch (IOException var6) {
                  WTCLogger.logIOEbadUsrPasswd(var6.getMessage());
               }
            } else {
               this.cred_proof = new byte[tpinfo.data.length];

               for(int lcv = 0; lcv < tpinfo.data.length; ++lcv) {
                  this.cred_proof[lcv] = tpinfo.data[lcv];
               }
            }
         }

      }
   }
}
