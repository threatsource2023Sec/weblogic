package org.python.netty.channel;

import java.io.Serializable;

public interface ChannelId extends Serializable, Comparable {
   String asShortText();

   String asLongText();
}
