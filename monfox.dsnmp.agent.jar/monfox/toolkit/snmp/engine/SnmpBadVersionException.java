package monfox.toolkit.snmp.engine;

public class SnmpBadVersionException extends SnmpCoderException {
   public SnmpBadVersionException(int var1) {
      super("" + var1);
   }

   public SnmpBadVersionException() {
   }
}
