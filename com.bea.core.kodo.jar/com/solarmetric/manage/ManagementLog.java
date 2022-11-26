package com.solarmetric.manage;

import org.apache.openjpa.lib.conf.Configuration;
import org.apache.openjpa.lib.log.Log;

public class ManagementLog {
   public static Log get(Configuration conf) {
      return conf.getLog(conf.getProductName() + ".Manage");
   }
}
