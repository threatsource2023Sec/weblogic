package weblogic.diagnostics.accessor;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class DataRecord implements Externalizable {
   static final long serialVersionUID = -665469141712332428L;
   private Object[] data;
   private String rawData;

   public DataRecord() {
   }

   public DataRecord(Object[] data) {
      this.data = data;
   }

   public Object get(int index) {
      int size = this.data != null ? this.data.length : 0;
      return index >= 0 && index < size ? this.data[index] : null;
   }

   public Object[] getValues() {
      return this.data;
   }

   public void setValues(Object[] data) {
      this.data = data;
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      int size = this.data != null ? this.data.length : 0;
      out.writeInt(size);

      for(int i = 0; i < size; ++i) {
         out.writeObject(this.data[i]);
      }

   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int size = in.readInt();
      this.data = new Object[size];

      for(int i = 0; i < size; ++i) {
         this.data[i] = in.readObject();
      }

   }

   public String toString() {
      StringBuffer buf = new StringBuffer();
      int size = this.data != null ? this.data.length : 0;
      buf.append("DataRecord: {\n");

      for(int i = 0; i < size; ++i) {
         Object val = this.data[i];
         buf.append("  [");
         buf.append(i);
         buf.append("] ");
         buf.append(val != null ? val.toString() : "null");
         buf.append("\n");
      }

      buf.append("}\n");
      return buf.toString();
   }

   public String geRawData() {
      return this.rawData;
   }

   public void setRawData(String rawData) {
      this.rawData = rawData;
   }
}
