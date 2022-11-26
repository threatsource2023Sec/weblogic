package com.bea.httppubsub;

import java.io.IOException;
import java.util.List;

public interface Transport {
   void send(List var1) throws IOException;

   boolean isValid();

   void setCommentFiltered(boolean var1);

   String getBrowserId();

   void setNormalPolling(boolean var1);

   boolean isNormalPolling();
}
