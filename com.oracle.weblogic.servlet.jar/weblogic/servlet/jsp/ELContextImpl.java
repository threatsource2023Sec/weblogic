package weblogic.servlet.jsp;

import javax.el.ELContext;
import javax.el.ELResolver;
import javax.el.FunctionMapper;
import javax.el.ImportHandler;
import javax.el.VariableMapper;

public class ELContextImpl extends ELContext {
   private ELResolver resolver;
   private FunctionMapper functionMapper;
   private VariableMapper variableMapper;
   private static final boolean isStaticReferenceDisabled = Boolean.getBoolean("weblogic.jsp.el.disableStaticReference");

   ELContextImpl() {
   }

   ELContextImpl(ELResolver res, VariableMapper varMapper, FunctionMapper funcMapper) {
      this.resolver = res;
      this.variableMapper = varMapper;
      this.functionMapper = funcMapper;
   }

   public ELResolver getELResolver() {
      return this.resolver;
   }

   public FunctionMapper getFunctionMapper() {
      return this.functionMapper;
   }

   public VariableMapper getVariableMapper() {
      return this.variableMapper;
   }

   public ImportHandler getImportHandler() {
      return isStaticReferenceDisabled ? null : super.getImportHandler();
   }

   void setELResolver(ELResolver res) {
      this.resolver = res;
   }

   void setFunctionMapper(FunctionMapper funcMapper) {
      this.functionMapper = funcMapper;
   }

   void setVariableMapper(VariableMapper varMapper) {
      this.variableMapper = varMapper;
   }
}
