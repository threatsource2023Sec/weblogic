package com.octetstring.vde.operation;

import com.octetstring.vde.Credentials;
import com.octetstring.vde.Entry;

public abstract class BasePlugin {
   private int type = 0;
   public static final int TYPE_PRESEARCH = 1;
   public static final int TYPE_POSTSEARCH = 2;

   public Entry postProcess(Credentials creds, Entry entry) {
      return entry;
   }

   public int getType() {
      return this.type;
   }

   public void setType(int type) {
      this.type = type;
   }
}
