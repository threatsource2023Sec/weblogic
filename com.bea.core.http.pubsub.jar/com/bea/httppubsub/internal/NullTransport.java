package com.bea.httppubsub.internal;

import com.bea.httppubsub.Transport;
import java.io.IOException;
import java.util.List;

public class NullTransport implements Transport {
   public String getBrowserId() {
      return null;
   }

   public boolean isNormalPolling() {
      return false;
   }

   public boolean isValid() {
      return false;
   }

   public void send(List messages) throws IOException {
   }

   public void setCommentFiltered(boolean isCommentFiltered) {
   }

   public void setNormalPolling(boolean isNormalPolling) {
   }
}
