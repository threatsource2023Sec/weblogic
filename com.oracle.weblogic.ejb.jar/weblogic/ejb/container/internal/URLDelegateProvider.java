package weblogic.ejb.container.internal;

import java.io.ObjectOutput;
import weblogic.protocol.configuration.ChannelHelper;

/** @deprecated */
@Deprecated
class URLDelegateProvider {
   private static final URLDelegate CLUSTERED_URL_DELEGATE = new URLDelegate() {
      public String getURL(ObjectOutput oo) {
         return ChannelHelper.getClusterURL(oo);
      }
   };

   static URLDelegate getURLDelegate(boolean isClustered) {
      return isClustered ? CLUSTERED_URL_DELEGATE : URLDelegate.CHANNEL_URL_DELEGATE;
   }
}
