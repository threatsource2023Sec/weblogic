package kodo.conf.descriptor;

public interface InMemorySavepointManagerBean extends SavepointManagerBean {
   boolean getPreFlush();

   void setPreFlush(boolean var1);
}
