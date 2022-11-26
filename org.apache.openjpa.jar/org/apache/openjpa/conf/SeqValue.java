package org.apache.openjpa.conf;

import org.apache.openjpa.kernel.TimeSeededSeq;
import org.apache.openjpa.lib.conf.PluginValue;

public class SeqValue extends PluginValue {
   private static final String[] ALIASES = new String[]{"time", TimeSeededSeq.class.getName(), "native", TimeSeededSeq.class.getName(), "sjvm", TimeSeededSeq.class.getName()};

   public SeqValue(String prop) {
      super(prop, true);
      this.setAliases(ALIASES);
      this.setDefault(ALIASES[0]);
      this.setClassName(ALIASES[1]);
   }
}
