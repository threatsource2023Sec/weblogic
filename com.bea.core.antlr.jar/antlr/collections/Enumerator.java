package antlr.collections;

public interface Enumerator {
   Object cursor();

   Object next();

   boolean valid();
}
