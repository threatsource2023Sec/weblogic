package org.glassfish.tyrus.core;

import javax.websocket.MessageHandler;

interface AsyncMessageHandler extends MessageHandler.Partial {
   Class getType();

   long getMaxMessageSize();
}
