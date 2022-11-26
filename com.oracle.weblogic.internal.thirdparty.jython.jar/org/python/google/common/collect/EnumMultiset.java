package org.python.google.common.collect;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.base.Preconditions;

@GwtCompatible(
   emulated = true
)
public final class EnumMultiset extends AbstractMapBasedMultiset {
   private transient Class type;
   @GwtIncompatible
   private static final long serialVersionUID = 0L;

   public static EnumMultiset create(Class type) {
      return new EnumMultiset(type);
   }

   public static EnumMultiset create(Iterable elements) {
      Iterator iterator = elements.iterator();
      Preconditions.checkArgument(iterator.hasNext(), "EnumMultiset constructor passed empty Iterable");
      EnumMultiset multiset = new EnumMultiset(((Enum)iterator.next()).getDeclaringClass());
      Iterables.addAll(multiset, elements);
      return multiset;
   }

   public static EnumMultiset create(Iterable elements, Class type) {
      EnumMultiset result = create(type);
      Iterables.addAll(result, elements);
      return result;
   }

   private EnumMultiset(Class type) {
      super(new EnumCountHashMap(type));
      this.type = type;
   }

   @GwtIncompatible
   private void writeObject(ObjectOutputStream stream) throws IOException {
      stream.defaultWriteObject();
      stream.writeObject(this.type);
      Serialization.writeMultiset(this, stream);
   }

   @GwtIncompatible
   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
      stream.defaultReadObject();
      Class localType = (Class)stream.readObject();
      this.type = localType;
      this.setBackingMap(new EnumCountHashMap(this.type));
      Serialization.populateMultiset(this, stream);
   }
}
