package utils;

public class system {
   public static void main(String[] args) {
      String[] propsWeWant = new String[]{"java.version", "java.vendor", "java.class.path", "os.name", "os.arch", "os.version"};

      for(int i = 0; i < propsWeWant.length; ++i) {
         System.out.println("\n* * * * * * * " + propsWeWant[i] + " * * * * * * * ");
         System.out.println(System.getProperty(propsWeWant[i]));
      }

   }
}
