package javax.jdo.datastore;

public interface Sequence {
   String getName();

   Object next();

   void allocate(int var1);

   Object current();

   long nextValue();

   long currentValue();
}
