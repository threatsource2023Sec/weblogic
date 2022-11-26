package weblogic.rmi.internal;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.rmi.Remote;
import java.util.Arrays;
import weblogic.common.WLObjectInput;
import weblogic.common.WLObjectOutput;

public final class RemoteType implements Serializable {
   private static final long serialVersionUID = 3291626140575618082L;
   private transient Class[] interfaces;
   private final String[] interfaceNames;

   RemoteType(Class[] interfaces) {
      this.interfaces = interfaces;
      String[] temp = new String[interfaces.length];

      for(int i = 0; i < temp.length; ++i) {
         temp[i] = interfaces[i].getName();
      }

      this.interfaceNames = temp;
   }

   public boolean isInstance(Remote object) {
      for(int i = 0; i < this.interfaces.length; ++i) {
         if (!this.interfaces[i].isInstance(object)) {
            return false;
         }
      }

      return true;
   }

   public boolean isAssignableFrom(RemoteType otherType) {
      if (otherType.interfaceNames.length != this.interfaceNames.length) {
         return false;
      } else {
         for(int i = 0; i < this.interfaceNames.length; ++i) {
            if (!this.interfaceNames[i].equals(otherType.interfaceNames[i])) {
               return false;
            }
         }

         return true;
      }
   }

   public String[] getInterfaces() {
      return this.interfaceNames;
   }

   public int hashCode() {
      return Arrays.hashCode(this.interfaceNames);
   }

   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (o instanceof RemoteType) {
         RemoteType other = (RemoteType)o;
         return this.isAssignableFrom(other);
      } else {
         return false;
      }
   }

   public String toString() {
      StringBuffer s = new StringBuffer();
      s.append("[");

      for(int i = 0; i < this.interfaceNames.length; ++i) {
         if (i > 0) {
            s.append("+");
         }

         s.append(this.interfaceNames[i]);
      }

      s.append("]");
      return s.toString();
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      if (out instanceof WLObjectOutput) {
         WLObjectOutput wlout = (WLObjectOutput)out;
         wlout.writeInt(this.interfaceNames.length);

         for(int i = 0; i < this.interfaceNames.length; ++i) {
            wlout.writeString(this.interfaceNames[i]);
         }
      } else {
         out.writeInt(this.interfaceNames.length);

         for(int i = 0; i < this.interfaceNames.length; ++i) {
            out.writeObject(this.interfaceNames[i]);
         }
      }

   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int i;
      if (in instanceof WLObjectInput) {
         WLObjectInput wlin = (WLObjectInput)in;
         i = wlin.readInt();

         for(int i = 0; i < i; ++i) {
            this.interfaceNames[i] = wlin.readString();
         }
      } else {
         int length = in.readInt();

         for(i = 0; i < length; ++i) {
            this.interfaceNames[i] = (String)in.readObject();
         }
      }

   }
}
