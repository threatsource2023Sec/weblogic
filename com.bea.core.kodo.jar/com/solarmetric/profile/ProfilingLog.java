package com.solarmetric.profile;

import org.apache.openjpa.lib.conf.Configuration;
import org.apache.openjpa.lib.log.Log;

public class ProfilingLog {
   public static Log get(Configuration conf) {
      return conf.getLog(conf.getProductName() + ".Profile");
   }
}
