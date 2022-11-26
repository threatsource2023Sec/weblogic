package com.octetstring.vde;

import com.octetstring.ldapv3.LDAPMessage;

public class WorkQueueItem {
   private Connection con = null;
   private LDAPMessage msg = null;

   public WorkQueueItem(Connection con, LDAPMessage msg) {
      this.con = con;
      this.msg = msg;
   }

   public Connection getConnection() {
      return this.con;
   }

   public LDAPMessage getMessage() {
      return this.msg;
   }
}
