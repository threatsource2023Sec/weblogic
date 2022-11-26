package org.python.netty.channel.group;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import org.python.netty.channel.ChannelException;

public class ChannelGroupException extends ChannelException implements Iterable {
   private static final long serialVersionUID = -4093064295562629453L;
   private final Collection failed;

   public ChannelGroupException(Collection causes) {
      if (causes == null) {
         throw new NullPointerException("causes");
      } else if (causes.isEmpty()) {
         throw new IllegalArgumentException("causes must be non empty");
      } else {
         this.failed = Collections.unmodifiableCollection(causes);
      }
   }

   public Iterator iterator() {
      return this.failed.iterator();
   }
}
