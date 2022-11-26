package weblogic.apache.org.apache.log.output.jms;

public class PropertyType {
   public static final int STATIC = 1;
   public static final int CATEGORY = 2;
   public static final int CONTEXT = 3;
   public static final int MESSAGE = 4;
   public static final int TIME = 5;
   public static final int RELATIVE_TIME = 6;
   public static final int THROWABLE = 7;
   public static final int PRIORITY = 8;
   public static final String STATIC_STR = "static";
   public static final String CATEGORY_STR = "category";
   public static final String CONTEXT_STR = "context";
   public static final String MESSAGE_STR = "message";
   public static final String TIME_STR = "time";
   public static final String RELATIVE_TIME_STR = "rtime";
   public static final String THROWABLE_STR = "throwable";
   public static final String PRIORITY_STR = "priority";

   public static int getTypeIdFor(String type) {
      if (type.equalsIgnoreCase("category")) {
         return 2;
      } else if (type.equalsIgnoreCase("static")) {
         return 1;
      } else if (type.equalsIgnoreCase("context")) {
         return 3;
      } else if (type.equalsIgnoreCase("message")) {
         return 4;
      } else if (type.equalsIgnoreCase("priority")) {
         return 8;
      } else if (type.equalsIgnoreCase("time")) {
         return 5;
      } else if (type.equalsIgnoreCase("rtime")) {
         return 6;
      } else if (type.equalsIgnoreCase("throwable")) {
         return 7;
      } else {
         throw new IllegalArgumentException("Unknown Type " + type);
      }
   }
}
