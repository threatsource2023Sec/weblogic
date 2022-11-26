package weblogic.management.configuration.util;

public class ReplicationPorts {
   public static int[] parseReplicationPorts(String ports) throws NumberFormatException {
      int[] parsedPorts = null;
      String[] range = ports.split("-");
      int[] parsedPorts;
      switch (range.length) {
         case 1:
            parsedPorts = new int[]{Integer.parseInt(range[0].trim())};
            return parsedPorts;
         case 2:
            int start = Integer.parseInt(range[0].trim());
            int end = Integer.parseInt(range[1].trim());
            if (end - start > 0) {
               parsedPorts = new int[end - start + 1];
               int i = start;

               for(int k = 0; i <= end; ++k) {
                  parsedPorts[k] = i++;
               }

               return parsedPorts;
            }
         default:
            throw new NumberFormatException();
      }
   }
}
