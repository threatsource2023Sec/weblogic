package weblogic.rjvm;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamClass;
import java.io.Serializable;
import java.lang.ref.WeakReference;

final class ClassTableEntry implements Externalizable {
   private static final long serialVersionUID = 3409899474073745901L;
   private ObjectStreamClass descriptor;
   private WeakReference weakDescriptor;
   private WeakReference weakClass;
   private String annotation;
   private WeakReference clz;
   private WeakReference ccl;
   private int cachedHashCode;

   public ClassTableEntry() {
   }

   ClassTableEntry(ObjectStreamClass descriptor, String annotation) {
      this.descriptor = descriptor;
      this.annotation = annotation;
      this.calculateHashCode();
      if (annotation != null && !annotation.equals("")) {
         this.weakDescriptor = new WeakReference(descriptor);
         this.weakClass = new WeakReference(descriptor.forClass());
         this.descriptor = null;
      }

   }

   public ObjectStreamClass getDescriptor() {
      if (this.descriptor != null) {
         return this.descriptor;
      } else {
         if (this.weakDescriptor != null) {
            ObjectStreamClass osc = (ObjectStreamClass)this.weakDescriptor.get();
            if (osc != null) {
               return osc;
            }
         }

         if (this.weakClass != null) {
            Class c = (Class)this.weakClass.get();
            if (c != null) {
               ObjectStreamClass osc = ObjectStreamClass.lookup(c);
               if (osc != null) {
                  return osc;
               }
            }
         }

         throw new AssertionError("all descriptor information is removed. : " + this.toString());
      }
   }

   public void setClazz(Class clazz) {
      this.clz = new WeakReference(clazz);
   }

   public Class getClazz() {
      return this.clz != null ? (Class)this.clz.get() : null;
   }

   public void setClazzLoader(ClassLoader classLoader) {
      this.ccl = new WeakReference(classLoader);
   }

   public ClassLoader getClazzLoader() {
      return this.ccl != null ? (ClassLoader)this.ccl.get() : null;
   }

   public String getAnnotation() {
      return this.annotation;
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeObject(this.getDescriptor());
      out.writeUTF(this.annotation);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      this.descriptor = (ObjectStreamClass)in.readObject();
      this.annotation = in.readUTF();
      this.calculateHashCode();
   }

   public boolean equals(Object o) {
      if (o == null) {
         return false;
      } else if (this == o) {
         return true;
      } else {
         try {
            ClassTableEntry other = (ClassTableEntry)o;
            ObjectStreamClass thisDesc = this.getDescriptor();
            ObjectStreamClass otherDesc = other.getDescriptor();
            if (thisDesc != null && otherDesc != null) {
               return thisDesc.equals(otherDesc) && this.getAnnotation().equals(other.getAnnotation());
            } else {
               return false;
            }
         } catch (ClassCastException var5) {
            return false;
         }
      }
   }

   public int hashCode() {
      return this.cachedHashCode;
   }

   public String toString() {
      return "ClassTableEntry: [" + this.descriptor + ":" + (this.weakDescriptor != null ? (Serializable)this.weakDescriptor.get() : "*") + ":" + (this.weakClass != null ? (Serializable)this.weakClass.get() : "*") + "] @" + this.getAnnotation();
   }

   private void calculateHashCode() {
      this.cachedHashCode = this.getDescriptor().hashCode() ^ this.getAnnotation().hashCode();
   }
}
