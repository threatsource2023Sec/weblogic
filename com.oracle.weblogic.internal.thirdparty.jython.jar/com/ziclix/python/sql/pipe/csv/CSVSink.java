package com.ziclix.python.sql.pipe.csv;

import com.ziclix.python.sql.pipe.Sink;
import java.io.PrintWriter;
import org.python.core.Py;
import org.python.core.PyObject;

public class CSVSink implements Sink {
   protected boolean header;
   protected String delimiter;
   protected PrintWriter writer;
   protected PyObject converters;

   public CSVSink(PrintWriter writer) {
      this(writer, Py.None);
   }

   public CSVSink(PrintWriter writer, PyObject converters) {
      this.header = false;
      this.writer = writer;
      this.converters = converters;
      this.delimiter = ",";
   }

   public void row(PyObject row) {
      String[] values = new String[row.__len__()];
      int i;
      if (this.header) {
         for(i = 0; i < row.__len__(); ++i) {
            values[i] = this.convert(Py.newInteger(i), row.__getitem__(i));
         }
      } else {
         for(i = 0; i < row.__len__(); ++i) {
            values[i] = row.__getitem__(i).__getitem__(0).toString();
         }

         this.header = true;
      }

      this.println(values);
   }

   protected String convert(PyObject index, PyObject object) {
      if (this.converters != Py.None) {
         PyObject converter = this.converters.__finditem__(index);
         if (converter != Py.None) {
            object = converter.__call__(object);
         }
      }

      return object != Py.None && object != null ? CSVString.toCSV(object.toString()) : "";
   }

   protected void println(String[] row) {
      for(int i = 0; i < row.length - 1; ++i) {
         this.writer.print(row[i]);
         this.writer.print(this.delimiter);
      }

      this.writer.println(row[row.length - 1]);
   }

   public void start() {
   }

   public void end() {
   }
}
