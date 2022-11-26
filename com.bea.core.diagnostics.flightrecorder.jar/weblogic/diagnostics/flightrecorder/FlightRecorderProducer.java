package weblogic.diagnostics.flightrecorder;

import com.oracle.jrockit.jfr.InvalidEventDefinitionException;
import com.oracle.jrockit.jfr.InvalidValueException;
import com.oracle.jrockit.jfr.Producer;
import java.net.URISyntaxException;

public class FlightRecorderProducer {
   private static FlightRecorderManager flightRecorderMgr = FlightRecorderManager.Factory.getInstance();
   private Producer jfr1Producer = null;

   public FlightRecorderProducer(String a, String b, String c) throws URISyntaxException {
      if (!flightRecorderMgr.isJFR2() && flightRecorderMgr.isRecordingPossible()) {
         this.jfr1Producer = new Producer(a, b, c);
      }

   }

   public String getName() {
      return this.jfr1Producer == null ? "NoProducer" : this.jfr1Producer.getName();
   }

   public Object addEvent(Class clazz) throws InvalidEventDefinitionException, InvalidValueException {
      return this.jfr1Producer == null ? null : this.jfr1Producer.addEvent(clazz);
   }

   public void register() {
      if (this.jfr1Producer != null) {
         this.jfr1Producer.register();
      }

   }

   public void enable() {
      if (this.jfr1Producer != null) {
         this.jfr1Producer.enable();
      }

   }

   public void disable() {
      if (this.jfr1Producer != null) {
         this.jfr1Producer.disable();
      }

   }

   public void createConstantPool(Class clazz, String poolName, int a, int b, boolean c) {
      if (this.jfr1Producer != null) {
         this.jfr1Producer.createConstantPool(clazz, poolName, a, b, c);
      }

   }

   public Producer getInternalDelegate() {
      return this.jfr1Producer;
   }
}
