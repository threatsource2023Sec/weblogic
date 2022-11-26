package com.bea.core.jatmi.internal;

import weblogic.wtc.jatmi.ApplicationToMonitorInterface;

public class TCRouteEntry {
   private ApplicationToMonitorInterface mySG;
   private String rname;
   private String impSvc_lap;
   private String impSvc_raplist;

   public TCRouteEntry(ApplicationToMonitorInterface sg, String name) {
      this.mySG = sg;
      this.rname = name;
   }

   public ApplicationToMonitorInterface getSessionGroup() {
      return this.mySG;
   }

   public String getRemoteName() {
      return this.rname;
   }

   public String[] getImpSvc() {
      String[] ret = new String[]{this.impSvc_lap, this.impSvc_raplist};
      return ret;
   }

   public void setTDMImport(String lap, String raplist) {
      this.impSvc_lap = lap;
      this.impSvc_raplist = raplist;
   }
}
