package weblogic.xml.process;

public class ProcessorConstants {
   public static final String XML_TO_JAVA_PUBLIC_ID() {
      return "-//BEA Systems, Inc.//DTD XML Processing Instructions//EN";
   }

   public static final String XML_TO_JAVA_LOADER_CLASS() {
      return "weblogic.xml.process.PILoader";
   }

   public static final String JAVA_TO_XML_PUBLIC_ID() {
      return "-//BEA Systems, Inc.//DTD XML Generating Instructions//EN";
   }

   public static final String JAVA_TO_XML_LOADER_CLASS() {
      return "weblogic.xml.process.GILoader";
   }
}
