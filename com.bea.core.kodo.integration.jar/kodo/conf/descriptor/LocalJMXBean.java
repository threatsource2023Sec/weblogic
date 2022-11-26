package kodo.conf.descriptor;

public interface LocalJMXBean extends JMXBean {
   String getMBeanServerStrategy();

   void setMBeanServerStrategy(String var1);

   boolean getEnableLogMBean();

   void setEnableLogMBean(boolean var1);

   boolean getEnableRuntimeMBean();

   void setEnableRuntimeMBean(boolean var1);
}
