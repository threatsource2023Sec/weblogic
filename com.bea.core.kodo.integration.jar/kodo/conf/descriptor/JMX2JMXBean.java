package kodo.conf.descriptor;

public interface JMX2JMXBean extends LocalJMXBean {
   String getNamingImpl();

   void setNamingImpl(String var1);

   String getServiceURL();

   void setServiceURL(String var1);
}
