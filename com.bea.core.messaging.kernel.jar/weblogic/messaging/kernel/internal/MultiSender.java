package weblogic.messaging.kernel.internal;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import weblogic.messaging.Message;
import weblogic.messaging.kernel.Listener;
import weblogic.messaging.kernel.MessageElement;
import weblogic.messaging.kernel.MultiListener;

final class MultiSender {
   private List firstList;
   private MultiListener firstListener;
   private IdentityHashMap listenerMap;
   private Message message;

   synchronized void add(MultiListener multiListener, MessageElement ref, Listener listener) {
      Object pushList;
      if (this.firstList == null && this.listenerMap == null) {
         this.firstList = new ArrayList();
         this.firstListener = multiListener;
         pushList = this.firstList;
         this.message = ref.getMessage();
      } else if (multiListener == this.firstListener) {
         pushList = this.firstList;
      } else {
         if (this.listenerMap == null) {
            this.listenerMap = new IdentityHashMap();
            this.listenerMap.put(this.firstListener, this.firstList);
         }

         pushList = (List)this.listenerMap.get(multiListener);
         if (pushList == null) {
            pushList = new ArrayList();
            this.listenerMap.put(multiListener, pushList);
         }
      }

      DeliveryInfoImpl info = new DeliveryInfoImpl(ref, listener);
      ((List)pushList).add(info);
   }

   synchronized void push() {
      if (this.listenerMap != null) {
         Iterator listeners = this.listenerMap.entrySet().iterator();

         while(listeners.hasNext()) {
            Map.Entry entry = (Map.Entry)listeners.next();
            MultiListener listener = (MultiListener)entry.getKey();
            listener.multiDeliver(this.message, (List)entry.getValue());
         }
      } else if (this.firstListener != null) {
         this.firstListener.multiDeliver(this.message, this.firstList);
      }

   }

   private static final class DeliveryInfoImpl implements MultiListener.DeliveryInfo {
      private MessageElement ref;
      private Listener listener;

      DeliveryInfoImpl(MessageElement ref, Listener listener) {
         this.ref = ref;
         this.listener = listener;
      }

      public MessageElement getMessageElement() {
         return this.ref;
      }

      public Listener getListener() {
         return this.listener;
      }
   }
}
