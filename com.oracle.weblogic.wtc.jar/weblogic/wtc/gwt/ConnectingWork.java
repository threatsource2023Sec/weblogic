package weblogic.wtc.gwt;

import com.bea.core.jatmi.intf.TCTask;

class ConnectingWork implements TCTask {
   private TDMRemoteTDomain rdom;
   private ScheduledReconnect myControl;
   private String myName;

   ConnectingWork(ScheduledReconnect ctrl, TDMRemoteTDomain rd) {
      this.rdom = rd;
      this.myControl = ctrl;
   }

   public int execute() {
      if (this.rdom.getTsession(true) != null) {
         this.myControl.connectingSuccess();
      } else {
         this.myControl.connectingFailure();
      }

      return 0;
   }

   public void setTaskName(String tname) {
      this.myName = new String("ConnectingWork$" + tname);
   }

   public String getTaskName() {
      return this.myName == null ? "ConnectingWork$unknown" : this.myName;
   }
}
