package weblogic.diagnostics.query;

import java.util.Map;
import weblogic.diagnostics.i18n.DiagnosticsTextTextFormatter;

public class VariableResolverImpl implements VariableResolver {
   private static final DiagnosticsTextTextFormatter DTF = DiagnosticsTextTextFormatter.getInstance();
   private Map valueMap;

   public VariableResolverImpl(Map keyValuePairs) {
      this.valueMap = keyValuePairs;
   }

   public Object resolveVariable(String varName) throws UnknownVariableException {
      if (this.valueMap.containsKey(varName)) {
         return this.valueMap.get(varName);
      } else {
         throw new UnknownVariableException(DTF.getUnknownVariableMsg(varName));
      }
   }

   public Object resolveVariable(int index) throws UnknownVariableException {
      throw new UnknownVariableException(DTF.getUnknownVariableMsg("" + index));
   }

   public int resolveInteger(int index) throws UnknownVariableException {
      throw new UnknownVariableException(DTF.getUnknownVariableMsg("" + index));
   }

   public long resolveLong(int index) throws UnknownVariableException {
      throw new UnknownVariableException(DTF.getUnknownVariableMsg("" + index));
   }

   public float resolveFloat(int index) throws UnknownVariableException {
      throw new UnknownVariableException(DTF.getUnknownVariableMsg("" + index));
   }

   public double resolveDouble(int index) throws UnknownVariableException {
      throw new UnknownVariableException(DTF.getUnknownVariableMsg("" + index));
   }

   public String resolveString(int index) throws UnknownVariableException {
      throw new UnknownVariableException(DTF.getUnknownVariableMsg("" + index));
   }

   public int resolveInteger(String varName) throws UnknownVariableException {
      Object o = this.resolveVariable(varName);
      return ((Number)o).intValue();
   }

   public long resolveLong(String varName) throws UnknownVariableException {
      Object o = this.resolveVariable(varName);
      return ((Number)o).longValue();
   }

   public float resolveFloat(String varName) throws UnknownVariableException {
      Object o = this.resolveVariable(varName);
      return ((Number)o).floatValue();
   }

   public double resolveDouble(String varName) throws UnknownVariableException {
      Object o = this.resolveVariable(varName);
      return ((Number)o).doubleValue();
   }

   public String resolveString(String varName) throws UnknownVariableException {
      Object o = this.resolveVariable(varName);
      return o.toString();
   }
}
