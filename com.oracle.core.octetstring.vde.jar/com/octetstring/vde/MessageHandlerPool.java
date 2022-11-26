package com.octetstring.vde;

import com.octetstring.vde.util.ObjectPool;

class MessageHandlerPool extends ObjectPool {
   private static MessageHandlerPool mhp = null;

   private MessageHandlerPool() {
   }

   public static MessageHandlerPool getInstance() {
      if (mhp == null) {
         mhp = new MessageHandlerPool();
      }

      return mhp;
   }

   public Object create() throws Exception {
      return new MessageHandler();
   }

   public void expire(Object o) {
   }

   public boolean validate(Object o) {
      return false;
   }
}
