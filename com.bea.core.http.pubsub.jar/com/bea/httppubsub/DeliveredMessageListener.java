package com.bea.httppubsub;

import java.util.EventListener;

public interface DeliveredMessageListener extends EventListener {
   void onPublish(DeliveredMessageEvent var1);
}
