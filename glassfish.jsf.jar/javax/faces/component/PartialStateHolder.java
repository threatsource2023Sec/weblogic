package javax.faces.component;

public interface PartialStateHolder extends StateHolder {
   void markInitialState();

   boolean initialStateMarked();

   void clearInitialState();
}
