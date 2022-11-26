package javax.jdo.identity;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import javax.jdo.JDOFatalInternalException;
import javax.jdo.JDONullIdentityException;
import javax.jdo.spi.I18NHelper;

public abstract class SingleFieldIdentity implements Externalizable, Comparable {
   protected static I18NHelper msg = I18NHelper.getInstance("javax.jdo.Bundle");
   private transient Class targetClass;
   private String targetClassName;
   protected int hashCode;
   protected Object keyAsObject;

   protected SingleFieldIdentity(Class pcClass) {
      if (pcClass == null) {
         throw new NullPointerException();
      } else {
         this.targetClass = pcClass;
         this.targetClassName = pcClass.getName();
      }
   }

   public SingleFieldIdentity() {
   }

   protected void setKeyAsObject(Object key) {
      this.assertKeyNotNull(key);
      this.keyAsObject = key;
   }

   protected void assertKeyNotNull(Object key) {
      if (key == null) {
         throw new JDONullIdentityException(msg.msg("EXC_SingleFieldIdentityNullParameter"));
      }
   }

   public Class getTargetClass() {
      return this.targetClass;
   }

   public String getTargetClassName() {
      return this.targetClassName;
   }

   public synchronized Object getKeyAsObject() {
      if (this.keyAsObject == null) {
         this.keyAsObject = this.createKeyAsObject();
      }

      return this.keyAsObject;
   }

   protected Object createKeyAsObject() {
      throw new JDOFatalInternalException(msg.msg("EXC_CreateKeyAsObjectMustNotBeCalled"));
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj != null && this.getClass() == obj.getClass()) {
         SingleFieldIdentity other = (SingleFieldIdentity)obj;
         return this.targetClass != null && this.targetClass == other.targetClass ? true : this.targetClassName.equals(other.targetClassName);
      } else {
         return false;
      }
   }

   protected int hashClassName() {
      return this.targetClassName.hashCode();
   }

   public int hashCode() {
      return this.hashCode;
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeObject(this.targetClassName);
      out.writeInt(this.hashCode);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      this.targetClass = null;
      this.targetClassName = (String)in.readObject();
      this.hashCode = in.readInt();
   }

   protected int compare(SingleFieldIdentity o) {
      return this.targetClassName.compareTo(o.targetClassName);
   }
}
