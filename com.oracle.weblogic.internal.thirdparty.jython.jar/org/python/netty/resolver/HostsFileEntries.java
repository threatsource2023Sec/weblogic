package org.python.netty.resolver;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class HostsFileEntries {
   static final HostsFileEntries EMPTY = new HostsFileEntries(Collections.emptyMap(), Collections.emptyMap());
   private final Map inet4Entries;
   private final Map inet6Entries;

   public HostsFileEntries(Map inet4Entries, Map inet6Entries) {
      this.inet4Entries = Collections.unmodifiableMap(new HashMap(inet4Entries));
      this.inet6Entries = Collections.unmodifiableMap(new HashMap(inet6Entries));
   }

   public Map inet4Entries() {
      return this.inet4Entries;
   }

   public Map inet6Entries() {
      return this.inet6Entries;
   }
}
