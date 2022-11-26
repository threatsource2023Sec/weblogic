package com.ziclix.python.sql.pipe;

import com.ziclix.python.sql.util.Queue;
import org.python.core.Py;
import org.python.core.PyObject;

class SinkRunner extends PipeRunner {
   protected Sink sink;

   public SinkRunner(Queue queue, Sink sink) {
      super(queue);
      this.sink = sink;
   }

   protected void pipe() throws InterruptedException {
      PyObject row = Py.None;
      this.sink.start();

      try {
         while((row = (PyObject)this.queue.dequeue()) != Py.None) {
            this.sink.row(row);
            ++this.counter;
         }
      } finally {
         this.sink.end();
      }

   }
}
