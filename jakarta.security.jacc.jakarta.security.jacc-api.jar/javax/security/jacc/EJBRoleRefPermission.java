package javax.security.jacc;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;
import java.security.Permission;

public final class EJBRoleRefPermission extends Permission {
   private static final long serialVersionUID = 1L;
   private static final ObjectStreamField[] serialPersistentFields = new ObjectStreamField[]{new ObjectStreamField("actions", String.class)};
   private final String actions;
   private transient int hashCodeValue;

   public EJBRoleRefPermission(String name, String actions) {
      super(name);
      this.actions = actions;
   }

   public boolean equals(Object other) {
      if (other != null && other instanceof EJBRoleRefPermission) {
         EJBRoleRefPermission that = (EJBRoleRefPermission)other;
         return !this.getName().equals(that.getName()) ? false : this.actions.equals(that.actions);
      } else {
         return false;
      }
   }

   public String getActions() {
      return this.actions;
   }

   public int hashCode() {
      if (this.hashCodeValue == 0) {
         String hashInput = this.getName() + " " + this.actions;
         this.hashCodeValue = hashInput.hashCode();
      }

      return this.hashCodeValue;
   }

   public boolean implies(Permission permission) {
      return this.equals(permission);
   }

   private void readObject(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
      inputStream.defaultReadObject();
   }

   private synchronized void writeObject(ObjectOutputStream outputStream) throws IOException {
      outputStream.defaultWriteObject();
   }
}
