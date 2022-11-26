package utils;

public class getProperty {
   public static void main(String[] argv) {
      if (argv.length > 0) {
         for(int i = 0; i < argv.length; ++i) {
            System.out.println(argv[i] + "=" + System.getProperty(argv[i]));
         }
      } else {
         System.getProperties().list(System.out);
      }

   }
}
