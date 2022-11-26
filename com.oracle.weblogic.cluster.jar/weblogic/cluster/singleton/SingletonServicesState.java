package weblogic.cluster.singleton;

import java.io.Serializable;

public class SingletonServicesState implements Serializable {
   private int state;
   private Serializable data;

   public SingletonServicesState(int state) {
      this.state = state;
   }

   public void setStateData(Serializable data) {
      this.data = data;
   }

   public Serializable getStateData() {
      return this.data;
   }

   public int getState() {
      return this.state;
   }

   public String toString() {
      String s = SingletonServicesStateManager.STRINGIFIED_STATE[this.state];
      if (this.data != null) {
         s = s + " Data:" + this.data;
      }

      return s;
   }

   public int hashCode() {
      return this.toString().hashCode();
   }

   public boolean equals(Object obj) {
      boolean result = false;
      if (obj instanceof SingletonServicesState) {
         SingletonServicesState other = (SingletonServicesState)obj;
         if (other.toString().equals(this.toString())) {
            result = true;
         }
      }

      return result;
   }
}
