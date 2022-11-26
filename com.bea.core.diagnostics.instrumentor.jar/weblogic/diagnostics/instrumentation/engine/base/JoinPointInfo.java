package weblogic.diagnostics.instrumentation.engine.base;

import org.objectweb.asm.Label;
import weblogic.diagnostics.instrumentation.JoinPoint;

class JoinPointInfo {
   private JoinPoint joinPoint;
   private Label label;
   private String name;
   private int index = -1;
   private MonitorSpecificationBase[] monitors;

   JoinPointInfo(JoinPoint joinPoint, Label label) {
      this.joinPoint = joinPoint;
      this.label = label;
   }

   JoinPoint getJoinPoint() {
      return this.joinPoint;
   }

   void setLabel(Label label) {
      this.label = label;
   }

   Label getLabel() {
      return this.label;
   }

   void setName(String name) {
      this.name = name;
   }

   String getName() {
      return this.name;
   }

   void setIndex(int index) {
      this.index = index;
   }

   int getIndex() {
      return this.index;
   }

   void setMonitors(MonitorSpecificationBase[] monitors) {
      this.monitors = monitors;
   }

   MonitorSpecificationBase[] getMonitors() {
      return this.monitors;
   }
}
