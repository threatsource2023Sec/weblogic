package weblogic.work.concurrent.utils;

public class DefaultConcurrentObjectInfo {
   private final String clazz;
   private final String name;
   private final String compJndiLeafName;
   private final String appJndiLeafName;

   public DefaultConcurrentObjectInfo(String clazz, String name, String compJndiLeafName, String appJndiLeafName) {
      this.clazz = clazz;
      this.name = name;
      this.compJndiLeafName = compJndiLeafName;
      this.appJndiLeafName = appJndiLeafName;
   }

   public String getClassName() {
      return this.clazz;
   }

   public String getName() {
      return this.name;
   }

   public String getCompJndiLeafName() {
      return this.compJndiLeafName;
   }

   public String getAppJndiLeafName() {
      return this.appJndiLeafName;
   }

   public String getCompJndi() {
      return "java:comp/" + this.compJndiLeafName;
   }
}
