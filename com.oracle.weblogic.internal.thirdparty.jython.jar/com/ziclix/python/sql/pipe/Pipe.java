package com.ziclix.python.sql.pipe;

import com.ziclix.python.sql.zxJDBC;
import com.ziclix.python.sql.util.Queue;
import org.python.core.Py;
import org.python.core.PyObject;

public class Pipe {
   public PyObject pipe(Source source, Sink sink) {
      Queue queue = new Queue();
      SourceRunner sourceRunner = new SourceRunner(queue, source);
      SinkRunner sinkRunner = new SinkRunner(queue, sink);
      sourceRunner.start();
      sinkRunner.start();

      try {
         sourceRunner.join();
      } catch (InterruptedException var9) {
         queue.close();
         throw zxJDBC.makeException((Throwable)var9);
      }

      try {
         sinkRunner.join();
      } catch (InterruptedException var8) {
         queue.close();
         throw zxJDBC.makeException((Throwable)var8);
      }

      if (sourceRunner.threwException()) {
         throw zxJDBC.makeException(sourceRunner.getException().toString());
      } else if (sinkRunner.threwException()) {
         throw zxJDBC.makeException(sinkRunner.getException().toString());
      } else if (sinkRunner.getCount() == 0) {
         return Py.newInteger(0);
      } else {
         if (sourceRunner.getCount() - sinkRunner.getCount() != 0) {
            Integer[] counts = new Integer[]{new Integer(sourceRunner.getCount()), new Integer(sinkRunner.getCount())};
            String msg = zxJDBC.getString("inconsistentRowCount", counts);
            Py.assert_(Py.Zero, Py.newString(msg));
         }

         return Py.newInteger(sinkRunner.getCount());
      }
   }
}
