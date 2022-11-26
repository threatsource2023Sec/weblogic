package javax.faces.component;

public interface TransientStateHelper extends TransientStateHolder {
   Object getTransient(Object var1);

   Object getTransient(Object var1, Object var2);

   Object putTransient(Object var1, Object var2);
}
