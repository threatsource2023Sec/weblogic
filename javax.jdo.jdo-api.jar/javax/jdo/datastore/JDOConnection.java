package javax.jdo.datastore;

public interface JDOConnection {
   Object getNativeConnection();

   void close();
}
