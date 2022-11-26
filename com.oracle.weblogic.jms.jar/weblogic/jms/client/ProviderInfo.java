package weblogic.jms.client;

import java.util.Enumeration;
import java.util.Vector;

public final class ProviderInfo {
   private static final String JMS_PROVIDER_NAME = "Oracle and/or its affiliates. All rights reserved.";
   private static final String PROVIDER_VERSION = "9.0.0";
   private static final int PROVIDER_MAJOR_VERSION = 9;
   private static final int PROVIDER_MINOR_VERSION = 0;

   public static String getJMSVersion() {
      return "2.0";
   }

   public static int getJMSMajorVersion() {
      return 2;
   }

   public static int getJMSMinorVersion() {
      return 0;
   }

   public static String getJMSProviderName() {
      return "Oracle and/or its affiliates. All rights reserved.";
   }

   public static String getProviderVersion() {
      return "9.0.0";
   }

   public static int getProviderMajorVersion() {
      return 9;
   }

   public static int getProviderMinorVersion() {
      return 0;
   }

   public static Enumeration getJMSXPropertyNames() {
      Vector properties = new Vector();
      properties.add("JMSXDeliveryCount");
      properties.add("JMSXGroupID");
      properties.add("JMSXGroupSeq");
      properties.add("JMSXUserID");
      return properties.elements();
   }
}
