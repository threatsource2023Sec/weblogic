package weblogic.diagnostics.instrumentation;

import weblogic.diagnostics.instrumentation.engine.MonitorSpecification;

public class CustomMonitorControl extends DelegatingMonitorControl implements CustomMonitor {
   static final long serialVersionUID = -851465013676026651L;
   private String pointcutExpr;
   private MonitorSpecification monitorSpecification;

   public CustomMonitorControl(CustomMonitorControl mon) {
      super((DelegatingMonitorControl)mon);
      this.pointcutExpr = mon.pointcutExpr;
      this.monitorSpecification = mon.monitorSpecification;
   }

   public CustomMonitorControl(String type) {
      this("", type);
   }

   public CustomMonitorControl(String name, String type) {
      super(name, type);
   }

   public String[] getCompatibleActionTypes() {
      String[] retVal = new String[0];
      InstrumentationLibrary library = InstrumentationLibrary.getInstrumentationLibrary();
      switch (this.getLocationType()) {
         case 1:
         case 2:
            return library.getStatelessDiagnosticActionTypes();
         case 3:
            return library.getAroundDiagnosticActionTypes();
         default:
            return retVal;
      }
   }

   public String getPointcut() {
      return this.pointcutExpr;
   }

   public void setPointcut(String pointcutExpression) throws InvalidPointcutException {
      this.pointcutExpr = pointcutExpression;
   }

   void setMonitorSpecification(MonitorSpecification monitorSpecification) {
      this.monitorSpecification = monitorSpecification;
   }

   MonitorSpecification getMonitorSpecification() {
      return this.monitorSpecification;
   }

   public synchronized boolean merge(DiagnosticMonitorControl other) {
      boolean retVal = false;
      if (super.merge(other) && other instanceof CustomMonitorControl) {
         CustomMonitorControl oM = (CustomMonitorControl)other;
         this.pointcutExpr = oM.pointcutExpr;
         this.monitorSpecification = oM.monitorSpecification;
         retVal = true;
      }

      return retVal;
   }

   boolean isStructurallyDifferent(CustomMonitorControl other) {
      if (this.pointcutExpr != null && !this.pointcutExpr.equals(other.pointcutExpr)) {
         return true;
      } else if (other.pointcutExpr != null && !other.pointcutExpr.equals(this.pointcutExpr)) {
         return true;
      } else {
         int thisLocation = this.monitorSpecification != null ? this.monitorSpecification.getLocation() : -1;
         int otherLocation = other.monitorSpecification != null ? other.monitorSpecification.getLocation() : -1;
         return thisLocation != otherLocation;
      }
   }
}
