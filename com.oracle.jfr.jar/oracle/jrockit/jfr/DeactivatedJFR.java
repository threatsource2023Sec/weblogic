package oracle.jrockit.jfr;

import com.oracle.jrockit.jfr.NoSuchEventException;
import com.oracle.jrockit.jfr.Producer;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import javax.management.MBeanServer;
import oracle.jrockit.jfr.events.DisabledEventHandler;
import oracle.jrockit.jfr.events.EventDescriptor;
import oracle.jrockit.jfr.events.EventHandler;
import oracle.jrockit.jfr.events.JavaEventDescriptor;

final class DeactivatedJFR extends JFR {
   private void shouldNotReach() {
      throw new InternalError();
   }

   public boolean active() {
      return false;
   }

   public void bind(MBeanServer server) {
      throw new UnsupportedOperationException();
   }

   public void unbind(MBeanServer server) {
      throw new UnsupportedOperationException();
   }

   public EventDescriptor getEvent(int eventID) throws NoSuchEventException {
      this.shouldNotReach();
      return null;
   }

   public Collection getEvents() {
      this.shouldNotReach();
      return null;
   }

   public ProducerDescriptor getProducer(int producerID) throws NoSuchProducerException {
      this.shouldNotReach();
      return null;
   }

   public Collection getProducers() {
      this.shouldNotReach();
      return null;
   }

   public Timer getTimer() {
      this.shouldNotReach();
      return null;
   }

   public int getpid() {
      this.shouldNotReach();
      return 0;
   }

   public int nextID() {
      return 0;
   }

   public void addProducer(Producer p, int id, List events, Map pool) {
   }

   public void removeProducer(int id) {
   }

   public void addEventsToRegisteredProducer(Producer p, int id, List events, Map pools) {
   }

   public FlightRecorder getMBean() {
      this.shouldNotReach();
      return null;
   }

   public EventHandler createHandler(JavaEventDescriptor d, Class receiverType, Map pools) {
      return new DisabledEventHandler(d);
   }

   protected void addConstpool(StringConstantPool pool) {
      this.shouldNotReach();
   }

   protected void removeConstpool(StringConstantPool pool) {
      this.shouldNotReach();
   }

   protected void storeConstpool(StringConstantPool pool) {
      this.shouldNotReach();
   }
}
