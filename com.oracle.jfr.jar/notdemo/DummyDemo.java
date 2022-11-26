package notdemo;

import com.oracle.jrockit.jfr.DynamicEventToken;
import com.oracle.jrockit.jfr.DynamicValue;
import com.oracle.jrockit.jfr.EventDefinition;
import com.oracle.jrockit.jfr.EventToken;
import com.oracle.jrockit.jfr.InstantEvent;
import com.oracle.jrockit.jfr.InvalidEventDefinitionException;
import com.oracle.jrockit.jfr.InvalidValueException;
import com.oracle.jrockit.jfr.NoSuchEventException;
import com.oracle.jrockit.jfr.Producer;
import com.oracle.jrockit.jfr.TimedEvent;
import com.oracle.jrockit.jfr.ValueDefinition;
import com.oracle.jrockit.jfr.client.FlightRecorderClient;
import com.oracle.jrockit.jfr.client.FlightRecordingClient;
import com.oracle.jrockit.jfr.management.NoSuchRecordingException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import javax.management.InstanceNotFoundException;
import javax.management.MalformedObjectNameException;
import javax.management.openmbean.OpenDataException;

public class DummyDemo {
   public static void main(String[] args) throws InvalidEventDefinitionException, MalformedObjectNameException, NullPointerException, OpenDataException, NoSuchEventException, InterruptedException, InvalidValueException, IOException, URISyntaxException, InstanceNotFoundException, NoSuchRecordingException {
      Producer p = new Producer("Esta loca", "Baba zuut", "http://www.kokos.com/");
      EventToken t = p.addEvent(MyEvent.class);
      DynamicEventToken db = p.createDynamicTimedEvent("Le pig", "Un grisé", (String)null, true, true, new DynamicValue("semper", "", "välling", String.class), new DynamicValue("bosse", "", "ringholm", Float.TYPE));
      p.register();
      FlightRecorderClient f = new FlightRecorderClient();
      FlightRecordingClient rr = f.createRecordingObject("Grisen");
      rr.setDestination("c:/temp/gris.flr");
      rr.setDuration(20000L);
      rr.setMaxSize(31457280L);
      rr.setEventEnabled(t.getId(), true);
      rr.setEventEnabled(db.getId(), true);
      rr.setThreshold(db.getId(), 100L);
      rr.start();
      TimedEvent te = db.newTimedEvent();
      int i = 0;

      while(true) {
         te.reset();
         te.begin();

         for(int j = 0; j < 10; ++j) {
            MyEvent e = new MyEvent(t);
            e.setOlga(12);
            e.setTuta("Kazaam");
            e.kossa = 234423324234L;
            e.klasso = String.class;
            e.commit();
         }

         te.end();
         if (te.shouldWrite()) {
            te.setValue("semper", "terrmooosss");
            te.setValue("bosse", i);
            te.commit();
         }

         if (i == 10) {
            Thread.sleep(1L);
         }

         if (i == 20) {
            rr.setStackTraceEnabled(t.getId(), true);
            rr.setStackTraceEnabled(db.getId(), true);
         }

         if (i > 2000 && !rr.isRunning() || rr.getDataSize() > 1048576L) {
            rr.stop();
            FileOutputStream fos = new FileOutputStream("c:/temp/tuta.flr");
            InputStream in = rr.openUncompressedStreamObject();
            byte[] bytes = new byte[4096];

            while(true) {
               int n = in.read(bytes);
               if (n == -1) {
                  fos.flush();
                  fos.close();
                  return;
               }

               fos.write(bytes, 0, n);
            }
         }

         ++i;
      }
   }

   @EventDefinition
   public static class MyEvent extends InstantEvent {
      @ValueDefinition
      private String tuta;
      @ValueDefinition
      private int olga;
      @ValueDefinition
      public long kossa;
      @ValueDefinition
      public Class klasso;

      public MyEvent() {
      }

      public MyEvent(EventToken eventToken) {
         super(eventToken);
      }

      public String getTuta() {
         return this.tuta;
      }

      public void setTuta(String tuta) {
         this.tuta = tuta;
      }

      public int getOlga() {
         return this.olga;
      }

      public void setOlga(int olga) {
         this.olga = olga;
      }
   }
}
