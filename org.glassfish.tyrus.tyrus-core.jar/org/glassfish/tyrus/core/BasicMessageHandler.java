package org.glassfish.tyrus.core;

import javax.websocket.MessageHandler;

interface BasicMessageHandler extends MessageHandler.Whole {
   Class getType();

   long getMaxMessageSize();
}
