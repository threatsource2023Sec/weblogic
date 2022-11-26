package weblogic.rmi.cluster.ejb;

import java.io.Serializable;
import java.util.Objects;
import weblogic.rmi.cluster.ReplicaID;

public class ReplicaIDImpl implements ReplicaID, Serializable {
   private static final long serialVersionUID = 4679512365249903233L;
   private String type = "EJB";
   private Object id = null;

   public ReplicaIDImpl() {
   }

   public ReplicaIDImpl(Object id) {
      this.id = id;
   }

   public boolean equals(Object o) {
      return this == o | o instanceof ReplicaIDImpl && this.equals((ReplicaIDImpl)o);
   }

   private boolean equals(ReplicaIDImpl o) {
      return Objects.equals(this.type, o.type) && Objects.deepEquals(this.id, o.id);
   }

   public int hashCode() {
      int result = this.type.hashCode();
      if (this.id != null) {
         result = 31 * result + this.getValueHashCode();
      }

      return result;
   }

   private int getValueHashCode() {
      return this.id.getClass().isArray() ? this.id.getClass().getComponentType().hashCode() : this.id.hashCode();
   }

   public String getType() {
      return this.type;
   }

   public Object getID() {
      return this.id;
   }

   public String toString() {
      return "[" + this.type + ":" + this.id + "]";
   }
}
