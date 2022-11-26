package weblogic.j2ee.descriptor;

public interface ConcurrentMethodBean {
   String getConcurrentLockType();

   void setConcurrentLockType(String var1);

   NamedMethodBean getMethod();

   NamedMethodBean createMethod();

   void destroyMethod(NamedMethodBean var1);

   AccessTimeoutBean getAccessTimeout();

   AccessTimeoutBean createAccessTimeout();

   void destroyAccessTimeout(AccessTimeoutBean var1);

   String getId();

   void setId(String var1);
}
