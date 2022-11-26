package org.jboss.weld.serialization;

import java.io.ObjectStreamException;

public abstract class AbstractSerializableHolder implements SerializableHolder {
   private static final long serialVersionUID = -5217996922004189423L;
   private transient Object value;

   public AbstractSerializableHolder() {
      this.value = this.initialize();
   }

   public AbstractSerializableHolder(Object value) {
      this.value = value;
   }

   protected abstract Object initialize();

   protected Object readResolve() throws ObjectStreamException {
      this.value = this.initialize();
      return this;
   }

   public Object get() {
      return this.value;
   }
}
