package weblogic.wtc.gwt;

import java.io.Serializable;

public final class DServiceInfo implements Serializable {
   private String lap;
   private String svcname;
   private int type;
   private int ss;
   private static final long serialVersionUID = 6192948866405001848L;

   public DServiceInfo(String svc, String ap, int stype, int status) {
      this.svcname = svc;
      this.lap = ap;
      this.type = stype;
      this.ss = status;
   }

   public DServiceInfo() {
      this.svcname = null;
      this.lap = null;
      this.type = 0;
      this.ss = 0;
   }

   public String getLocalAccessPoint() {
      return this.lap;
   }

   public String getServiceName() {
      return this.svcname;
   }

   public int getStatus() {
      return this.ss;
   }

   public int getServiceType() {
      return this.type;
   }
}
