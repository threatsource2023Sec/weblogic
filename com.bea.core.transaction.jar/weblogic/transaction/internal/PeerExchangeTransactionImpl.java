package weblogic.transaction.internal;

import java.io.Serializable;
import weblogic.transaction.PeerExchangeTransaction;

public class PeerExchangeTransactionImpl implements PeerExchangeTransaction {
   public boolean isSSLEnabled() {
      return false;
   }

   public int getTimeToLiveSeconds() {
      return 30;
   }

   public Serializable getProperty(String key) {
      return null;
   }

   public Object getLocalProperty(String key) {
      return null;
   }

   public String toString() {
      StringBuffer sb = new StringBuffer(100);
      sb.append("Name=this is for peerExchange only,TimeTpLiveSecods=").append(this.getTimeToLiveSeconds());
      sb.append(",useSecure").append(this.isSSLEnabled());
      return sb.toString();
   }
}
