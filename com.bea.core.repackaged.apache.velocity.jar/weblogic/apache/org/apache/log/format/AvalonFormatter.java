package weblogic.apache.org.apache.log.format;

/** @deprecated */
public class AvalonFormatter extends weblogic.apache.org.apache.avalon.framework.logger.AvalonFormatter {
   public AvalonFormatter() {
      super("%{time} [%7.7{priority}] (%{category}): %{message}\\n%{throwable}");
   }

   public AvalonFormatter(String pattern) {
      super(pattern);
   }
}
