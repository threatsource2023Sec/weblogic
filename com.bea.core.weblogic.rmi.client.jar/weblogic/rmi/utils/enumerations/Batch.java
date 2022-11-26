package weblogic.rmi.utils.enumerations;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Enumeration;
import java.util.NoSuchElementException;
import weblogic.common.WLObjectInput;
import weblogic.common.WLObjectOutput;
import weblogic.utils.AssertionError;

public final class Batch implements Enumeration, Externalizable {
   private static final long serialVersionUID = 8157687339265696593L;
   private Object[] data;
   private int size;
   private int index;

   public Batch(Object[] data, int size) {
      if (size > data.length) {
         throw new AssertionError("Batch constructed with size > data.length");
      } else {
         this.data = data;
         this.size = size;
         this.index = 0;
      }
   }

   public boolean hasMoreElements() {
      return this.index < this.size;
   }

   public Object nextElement() {
      if (this.index < this.size) {
         return this.data[this.index++];
      } else {
         throw new NoSuchElementException();
      }
   }

   public Batch() {
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      if (in instanceof WLObjectInput) {
         WLObjectInput wloi = (WLObjectInput)in;
         this.data = wloi.readArrayOfObjects();
         this.size = wloi.readInt();
         this.index = wloi.readInt();
      } else {
         this.data = (Object[])((Object[])in.readObject());
         this.size = in.readInt();
         this.index = in.readInt();
      }

   }

   public void writeExternal(ObjectOutput out) throws IOException {
      if (out instanceof WLObjectOutput) {
         WLObjectOutput wloo = (WLObjectOutput)out;
         wloo.writeArrayOfObjects(this.data);
         wloo.writeInt(this.size);
         wloo.writeInt(this.index);
      } else {
         out.writeObject(this.data);
         out.writeInt(this.size);
         out.writeInt(this.index);
      }

   }
}
