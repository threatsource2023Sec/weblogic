package monfox.toolkit.snmp.metadata;

import monfox.toolkit.snmp.SnmpException;

public class SnmpMetadataException extends SnmpException {
   private Result _result;
   private static final String _ident = "$Id: SnmpMetadataException.java,v 1.2 2003/09/16 19:59:21 sking Exp $";

   public SnmpMetadataException(String var1) {
      super(var1);
   }

   public SnmpMetadataException() {
   }

   public SnmpMetadataException(Result var1) {
      super(var1.toString());
      this._result = var1;
   }

   public Result getResult() {
      return this._result;
   }
}
