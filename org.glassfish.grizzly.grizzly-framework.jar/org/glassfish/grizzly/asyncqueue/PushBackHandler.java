package org.glassfish.grizzly.asyncqueue;

import org.glassfish.grizzly.Connection;

/** @deprecated */
public interface PushBackHandler {
   void onAccept(Connection var1, WritableMessage var2);

   void onPushBack(Connection var1, WritableMessage var2, PushBackContext var3);
}
