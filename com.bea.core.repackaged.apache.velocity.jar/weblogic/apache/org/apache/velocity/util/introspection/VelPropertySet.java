package weblogic.apache.org.apache.velocity.util.introspection;

public interface VelPropertySet {
   Object invoke(Object var1, Object var2) throws Exception;

   boolean isCacheable();

   String getMethodName();
}
