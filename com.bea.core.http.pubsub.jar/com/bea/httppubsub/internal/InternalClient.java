package com.bea.httppubsub.internal;

import com.bea.httppubsub.AuthenticatedUser;
import com.bea.httppubsub.Client;
import com.bea.httppubsub.Transport;
import com.bea.httppubsub.bayeux.messages.DeliverEventMessage;
import java.io.IOException;
import java.util.List;

public interface InternalClient extends Client {
   void setConnected(boolean var1);

   boolean isConnected();

   void addSubscribedChannel(String var1);

   void removeSubscribedChannel(String var1);

   void addSubscribedMessage(DeliverEventMessage var1);

   void addPublishedMessagesCount();

   boolean hasTransportPending();

   boolean send(Transport var1, List var2) throws IOException;

   void setCommentFilterRequired(boolean var1);

   void setAuthenticatedUser(AuthenticatedUser var1);

   void setBrowserId(String var1);

   void setOverloadError(String var1);
}
