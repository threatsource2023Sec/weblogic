package kodo.conf.descriptor;

public interface JMXBean {
   NoneJMXBean getNoneJMX();

   NoneJMXBean createNoneJMX();

   void destroyNoneJMX();

   LocalJMXBean getLocalJMX();

   LocalJMXBean createLocalJMX();

   void destroyLocalJMX();

   GUIJMXBean getGUIJMX();

   GUIJMXBean createGUIJMX();

   void destroyGUIJMX();

   JMX2JMXBean getJMX2JMX();

   JMX2JMXBean createJMX2JMX();

   void destroyJMX2JMX();

   MX4J1JMXBean getMX4J1JMX();

   MX4J1JMXBean createMX4J1JMX();

   void destroyMX4J1JMX();

   WLS81JMXBean getWLS81JMX();

   WLS81JMXBean createWLS81JMX();

   void destroyWLS81JMX();
}
