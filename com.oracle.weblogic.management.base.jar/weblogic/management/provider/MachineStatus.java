package weblogic.management.provider;

public class MachineStatus {
   String name;
   int state;
   Exception exception;

   public MachineStatus(String name, int state, Exception exception) {
      this.name = name;
      this.state = state;
      this.exception = exception;
   }

   public String getName() {
      return this.name;
   }

   public int getState() {
      return this.state;
   }

   public Exception getException() {
      return this.exception;
   }
}
