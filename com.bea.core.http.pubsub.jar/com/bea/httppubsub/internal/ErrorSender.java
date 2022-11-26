package com.bea.httppubsub.internal;

import com.bea.httppubsub.Transport;
import com.bea.httppubsub.bayeux.messages.AbstractBayeuxMessage;
import java.io.IOException;
import java.util.List;

public interface ErrorSender {
   void send(Transport var1, AbstractBayeuxMessage var2, int var3) throws IOException;

   void send(Transport var1, AbstractBayeuxMessage var2, int var3, String var4) throws IOException;

   void send(Transport var1, List var2, int var3) throws IOException;
}
