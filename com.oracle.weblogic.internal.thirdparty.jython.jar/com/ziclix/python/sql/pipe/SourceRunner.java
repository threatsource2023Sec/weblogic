package com.ziclix.python.sql.pipe;

import com.ziclix.python.sql.util.Queue;
import org.python.core.Py;
import org.python.core.PyObject;

class SourceRunner extends PipeRunner {
   protected Source source;

   public SourceRunner(Queue queue, Source source) {
      super(queue);
      this.source = source;
   }

   protected void pipe() throws InterruptedException {
      PyObject row = Py.None;
      this.source.start();

      try {
         while((row = this.source.next()) != Py.None) {
            this.queue.enqueue(row);
            ++this.counter;
         }
      } finally {
         try {
            this.queue.enqueue(Py.None);
         } finally {
            this.source.end();
         }
      }

   }
}
