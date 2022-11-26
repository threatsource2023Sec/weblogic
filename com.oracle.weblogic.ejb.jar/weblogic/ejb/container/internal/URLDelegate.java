package weblogic.ejb.container.internal;

import java.io.ObjectOutput;
import weblogic.protocol.ChannelHelperBase;

/** @deprecated */
@Deprecated
public interface URLDelegate {
   /** @deprecated */
   @Deprecated
   URLDelegate CHANNEL_URL_DELEGATE = new URLDelegate() {
      public String getURL(ObjectOutput oo) {
         return ChannelHelperBase.getChannelURL(oo);
      }
   };

   String getURL(ObjectOutput var1);
}
