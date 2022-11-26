package weblogic.security.service;

import weblogic.security.spi.Resource;
import weblogic.security.spi.SelfDescribingResourceV2;

public class TestResource {
   private static final String[] FIELD_TYPE_NAMES = new String[]{"N/A", "UNDEFINED", "NORMAL", "PATH", "LIST"};

   public static String getResourceHierarchy(Resource r) {
      StringBuffer buf = new StringBuffer();

      int max;
      for(max = 50; max-- > 0 && r != null; r = r.getParentResource()) {
         buf.append(r.toString());
         buf.append("\n");
      }

      if (max == 0) {
         System.err.println("!!!! Had to abort due to infinite loop !!!!");
         buf.append("!!!! Had to abort due to infinite loop !!!!\n");
      }

      return buf.toString();
   }

   public static String getFieldTypes(Resource r) {
      StringBuffer buf = new StringBuffer();
      String[] keys = r.getKeys();
      SelfDescribingResourceV2 r2 = null;
      if (r instanceof SelfDescribingResourceV2) {
         r2 = (SelfDescribingResourceV2)r;
      }

      for(int i = 0; i < keys.length; ++i) {
         buf.append("\t");
         buf.append(keys[i]);
         buf.append(" = ");
         int fieldType = -1;
         boolean transitive = false;
         if (r2 != null) {
            fieldType = r2.getFieldType(keys[i]);
            transitive = r2.isTransitiveField(keys[i]);
         }

         buf.append(FIELD_TYPE_NAMES[fieldType + 1]);
         if (transitive) {
            buf.append(" (transitive)");
         }

         buf.append("\n");
      }

      return buf.toString();
   }

   public static void main(String[] args) throws Exception {
      boolean correct = false;
      Resource r = new URLResource("myApp", "/MyWebApp", "/Foo/Bar/My.jsp", "GET", (String)null);
      System.out.println("URLResource:");
      System.out.println(getFieldTypes(r));
      System.out.println(getResourceHierarchy(r));
      r = new URLResource("myApp", "/MyWebApp", "/", "GET", (String)null);
      System.out.println("URLResource:");
      System.out.println(getResourceHierarchy(r));
      r = new URLResource("myApp", "/MyWebApp", "/Foo/Bar/My", "GET", (String)null);
      System.out.println("URLResource:");
      System.out.println(getResourceHierarchy(r));
      r = new URLResource("myApp", "/MyWebApp", "Foo/Bar/My", "GET", (String)null);
      System.out.println("URLResource:");
      System.out.println(getResourceHierarchy(r));
      r = new URLResource("myApp", "/MyWebApp", "*", "GET", (String)null);
      System.out.println("URLResource:");
      System.out.println(getResourceHierarchy(r));
      r = new URLResource("myApp", "/MyWebApp", "*.jsp", "GET", (String)null);
      System.out.println("URLResource:");
      System.out.println(getResourceHierarchy(r));
      Resource r = new WebServiceResource("myApp", "/myWebApp", "myWebService", "myMethod", new String[]{"argumentType1", "argumentType2"});
      System.out.println("WebServiceResource:");
      System.out.println(getFieldTypes(r));
      System.out.println(getResourceHierarchy(r));
      Resource r = new EJBResource("myApp", "MyJarFile", "myEJB", "myMethod", "Home", new String[]{"argumentType1", "argumentType2"});
      System.out.println("EJBResource:");
      System.out.println(getFieldTypes(r));
      System.out.println(getResourceHierarchy(r));
      r = new EJBResource("myApp", (String)null, (String)null, (String)null, (String)null, new String[0]);
      System.out.println("EJBResource:");
      System.out.println(getResourceHierarchy(r));
      Resource r = new AdminResource("UserLockout", "myRealm", "unlockuser");
      System.out.println("AdminResource:");
      System.out.println(getFieldTypes(r));
      System.out.println(getResourceHierarchy(r));
      r = new AdminResource("Configuration", (String)null, (String)null);
      System.out.println("AdminResource:");
      System.out.println("Resource to access MBean fields:");
      System.out.println(getFieldTypes(r));
      System.out.println(getResourceHierarchy(r));
      Resource r = new JDBCResource((String)null, (String)null, "connectionPool", "myPool", "admin");
      System.out.println("JDBCResource:");
      System.out.println("Possible actions are reserve, admin, shrink, reset.");
      System.out.println("Application and module are always null.");
      System.out.println(getFieldTypes(r));
      System.out.println(getResourceHierarchy(r));
      Resource r = new JMSResource((String)null, "module", "queue", "myQueue", "receive");
      System.out.println("JMSResource:");
      System.out.println("Possible types are queue and topic.");
      System.out.println("Possible actions are send and receive.");
      System.out.println("Application is always null.");
      System.out.println(getFieldTypes(r));
      System.out.println(getResourceHierarchy(r));
      System.out.println("The transitive field, module, is not visible when it is null");
      r = new JMSResource((String)null, (String)null, "queue", "myQueue", "receive");
      System.out.println(getResourceHierarchy(r));
      Resource r = new JNDIResource((String)null, new String[]{"pathComponent1", "pathComponent2"}, "modify");
      System.out.println("JNDIResource:");
      System.out.println("Application is always null.");
      System.out.println(getFieldTypes(r));
      System.out.println(getResourceHierarchy(r));
      r = new JNDIResource((String)null, (String[])null, "modify");
      System.out.println("JNDIResource:");
      System.out.println("Application is always null.");
      System.out.println(getFieldTypes(r));
      System.out.println(getResourceHierarchy(r));
      r = new JNDIResource((String)null, new String[0], "modify");
      System.out.println("JNDIResource:");
      System.out.println("Application is always null.");
      System.out.println(getFieldTypes(r));
      System.out.println(getResourceHierarchy(r));

      try {
         correct = false;
         r = new JNDIResource((String)null, new String[]{""}, "modify");
         System.out.println("JNDIResource:");
         System.out.println("Application is always null.");
         System.out.println(getFieldTypes(r));
         System.out.println(getResourceHierarchy(r));
      } catch (InvalidParameterException var9) {
         correct = true;
         System.out.println("Correctly received an InvalidParameterException when passing new String[]{emptystring}");
      }

      if (!correct) {
         throw new Exception("JNDIResource constructor missed empty string in context path");
      } else {
         try {
            correct = false;
            r = new JNDIResource((String)null, new String[]{"", "pathComponent2"}, "modify");
            System.out.println("JNDIResource:");
            System.out.println("Application is always null.");
            System.out.println(getFieldTypes(r));
            System.out.println(getResourceHierarchy(r));
         } catch (InvalidParameterException var8) {
            correct = true;
            System.out.println("Correctly received an InvalidParameterException when passing new String[]{emptystring,pathComponent2 }");
         }

         if (!correct) {
            throw new Exception("JNDIResource constructor missed empty string in context path");
         } else {
            try {
               correct = false;
               r = new JNDIResource((String)null, new String[]{"pathComponent1", ""}, "modify");
               System.out.println("JNDIResource:");
               System.out.println("Application is always null.");
               System.out.println(getFieldTypes(r));
               System.out.println(getResourceHierarchy(r));
            } catch (InvalidParameterException var7) {
               correct = true;
               System.out.println("Correctly received an InvalidParameterException when passing new String[]{pathComponent1,emptystring }");
            }

            if (!correct) {
               throw new Exception("JNDIResource constructor missed empty string in context path");
            } else {
               Resource r = new ServerResource((String)null, "MyServer", "shutdown");
               System.out.println("ServerResource:");
               System.out.println("Possible actions are shutdown, lock, boot.");
               System.out.println("Application is always null.");
               System.out.println(getFieldTypes(r));
               System.out.println(getResourceHierarchy(r));
               Resource r = new COMResource((String)null, "my.package.MyClass");
               System.out.println("COMResource:");
               System.out.println("Application is always null.");
               System.out.println(getFieldTypes(r));
               System.out.println(getResourceHierarchy(r));
               Resource r = new EISResource("MyApplication", "My.rar", "myResource");
               System.out.println("EISResource:");
               System.out.println(getFieldTypes(r));
               System.out.println(getResourceHierarchy(r));
               r = new EISResource("MyApplication", "My.rar", "myResource", "myDestination");
               System.out.println("EISResource:");
               System.out.println(getFieldTypes(r));
               System.out.println(getResourceHierarchy(r));
               Resource r = new ApplicationResource("MyApplication");
               System.out.println("ApplicationResource:");
               System.out.println(getResourceHierarchy(r));
               r = new URLResource("myApp", "/my,we\\b-{app}", "/foo/bar/my.jsp", "GET", "confidential");
               System.out.println(getResourceHierarchy(r));
               r = new COMResource("myApp", "com.foo.bar.*");
               System.out.println("ComResource");
               System.out.println(getResourceHierarchy(r));
               Resource r = new RemoteResource("http", "myHost", "7001", "/myWebapp/myWS", "myMethod");
               System.out.println("RemoteResource - WS usage");
               System.out.println(getResourceHierarchy(r));
               r = new RemoteResource("iiop", "myHost", "7001", (String)null, (String)null);
               System.out.println("RemoteResource - IIOP usage");
               System.out.println(getResourceHierarchy(r));
               r = new RemoteResource("iiop", "myHost", "7001", (String)null, "myMethod");
               System.out.println("RemoteResource - IIOP alternate usage");
               System.out.println(getResourceHierarchy(r));
               r = new RemoteResource("myProtocol", "myHost", "7001", "/", "myMethod");
               System.out.println("RemoteResource - Path of '/' - treated as null");
               System.out.println(getResourceHierarchy(r));
               Resource r = new WorkContextResource(new String[]{"pathComponent1", "pathComponent2"}, "modify");
               System.out.println("WorkContextResource:");
               System.out.println(getFieldTypes(r));
               System.out.println(getResourceHierarchy(r));
               r = new WorkContextResource((String[])null, "modify");
               System.out.println("WorkContextResource:");
               System.out.println(getFieldTypes(r));
               System.out.println(getResourceHierarchy(r));
               r = new WorkContextResource(new String[0], "modify");
               System.out.println("WorkContextResource:");
               System.out.println(getFieldTypes(r));
               System.out.println(getResourceHierarchy(r));

               try {
                  correct = false;
                  r = new WorkContextResource(new String[]{""}, "modify");
                  System.out.println("WorkContextResource:");
                  System.out.println(getFieldTypes(r));
                  System.out.println(getResourceHierarchy(r));
               } catch (InvalidParameterException var6) {
                  correct = true;
                  System.out.println("Correctly received an InvalidParameterException when passing new String[]{emptystring}");
               }

               if (!correct) {
                  throw new Exception("WorkContextResource constructor missed empty string in context path");
               } else {
                  try {
                     correct = false;
                     r = new WorkContextResource(new String[]{"", "pathComponent2"}, "modify");
                     System.out.println("WorkContextResource:");
                     System.out.println(getFieldTypes(r));
                     System.out.println(getResourceHierarchy(r));
                  } catch (InvalidParameterException var5) {
                     correct = true;
                     System.out.println("Correctly received an InvalidParameterException when passing new String[]{emptystring,pathComponent2 }");
                  }

                  if (!correct) {
                     throw new Exception("WorkContextResource constructor missed empty string in context path");
                  } else {
                     try {
                        correct = false;
                        r = new WorkContextResource(new String[]{"pathComponent1", ""}, "modify");
                        System.out.println("WorkContextResource:");
                        System.out.println(getFieldTypes(r));
                        System.out.println(getResourceHierarchy(r));
                     } catch (InvalidParameterException var4) {
                        correct = true;
                        System.out.println("Correctly received an InvalidParameterException when passing new String[]{pathComponent1,emptystring }");
                     }

                     if (!correct) {
                        throw new Exception("WorkContextResource constructor missed empty string in context path");
                     } else {
                        Resource r = new ControlResource("myApp", "myControl", "myMethod", new String[]{"argumentType1", "argumentType2"});
                        System.out.println("ControlResource:");
                        System.out.println(getFieldTypes(r));
                        System.out.println(getResourceHierarchy(r));
                        r = new ControlResource("myApp", (String)null, (String)null, new String[0]);
                        System.out.println("ControlResource:");
                        System.out.println(getResourceHierarchy(r));
                        Resource r = new JMXResource("get", "myApp", "weblogic.management.configuration.ServerMBean", "ListenPort");
                        System.out.println("JMXResource:");
                        System.out.println(getFieldTypes(r));
                        System.out.println(getResourceHierarchy(r));
                        r = new JMXResource("get", (String)null, "weblogic.management.configuration.ServerMBean", "ListenPort");
                        System.out.println("JMXResource:");
                        System.out.println(getFieldTypes(r));
                        System.out.println(getResourceHierarchy(r));
                     }
                  }
               }
            }
         }
      }
   }
}
