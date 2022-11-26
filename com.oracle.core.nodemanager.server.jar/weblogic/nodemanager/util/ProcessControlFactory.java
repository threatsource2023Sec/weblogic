package weblogic.nodemanager.util;

public class ProcessControlFactory {
   public static ProcessControl processControl;

   public static synchronized ProcessControl getProcessControl() {
      if (processControl == null) {
         if (Platform.isUnix()) {
            processControl = new UnixProcessControl();
         } else if (Platform.isWindows()) {
            processControl = new WindowsProcessControl();
         }
      }

      return processControl;
   }
}
