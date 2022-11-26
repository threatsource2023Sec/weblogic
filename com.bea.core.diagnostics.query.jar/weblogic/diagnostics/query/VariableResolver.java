package weblogic.diagnostics.query;

public interface VariableResolver {
   Object resolveVariable(String var1) throws UnknownVariableException;

   Object resolveVariable(int var1) throws UnknownVariableException;

   int resolveInteger(int var1) throws UnknownVariableException;

   long resolveLong(int var1) throws UnknownVariableException;

   float resolveFloat(int var1) throws UnknownVariableException;

   double resolveDouble(int var1) throws UnknownVariableException;

   String resolveString(int var1) throws UnknownVariableException;

   int resolveInteger(String var1) throws UnknownVariableException;

   long resolveLong(String var1) throws UnknownVariableException;

   float resolveFloat(String var1) throws UnknownVariableException;

   double resolveDouble(String var1) throws UnknownVariableException;

   String resolveString(String var1) throws UnknownVariableException;
}
