package weblogic.descriptor;

public interface SettableBean {
   boolean isSet(String var1) throws IllegalArgumentException;

   void unSet(String var1) throws IllegalArgumentException;
}
