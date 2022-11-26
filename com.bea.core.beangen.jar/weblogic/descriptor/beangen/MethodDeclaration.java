package weblogic.descriptor.beangen;

import com.bea.util.jam.JClass;
import com.bea.util.jam.JMethod;
import com.bea.util.jam.JParameter;
import java.lang.reflect.Modifier;

public class MethodDeclaration extends BeanElement implements Comparable {
   private final MethodType declType;
   private final BeanClass bean;
   private final JMethod jMethod;
   private String paramString;
   private String paramDeclString;
   private String[] paramTypes;
   private String exceptionString;
   private boolean declared = true;
   private MethodImplementation implementation;
   private int order = 500;

   protected MethodDeclaration(BeanClass bean, MethodType declType, JMethod method) {
      super(method);
      this.jMethod = method;
      this.declType = declType;
      this.bean = bean;
      this.initParamStrings(method.getParameters());
      this.initExceptionString(method.getExceptionTypes());
   }

   public BeanClass getBean() {
      return this.bean;
   }

   public boolean isSynthetic() {
      return this.jMethod instanceof SyntheticJMethod;
   }

   public String[] getJavadocAnnotations() {
      return this.implementation != null ? this.implementation.getComments() : new String[0];
   }

   public String getDeclaration() {
      String rt = this.getReturnType();
      String name = this.getName();
      String params = this.getParamDeclList();
      return this.getAccess() + " " + rt + " " + name + "(" + params + ")";
   }

   public String[] getBody() {
      return this.implementation != null ? this.implementation.getBody() : new String[]{"throw new AssertionError(\"not implemented\");"};
   }

   public String getName() {
      return this.jMethod.getSimpleName();
   }

   public String getAccess() {
      return Modifier.toString(this.jMethod.getModifiers());
   }

   public String getParamDeclList() {
      return this.paramDeclString;
   }

   public String[] getParamTypes() {
      return this.paramTypes;
   }

   public String getParamList() {
      return this.paramString;
   }

   public String getExceptionList() {
      return this.exceptionString;
   }

   public String getReturnType() {
      return Context.get().abbreviateClass(this.jMethod.getReturnType().getQualifiedName());
   }

   public boolean isType(MethodType type) {
      return this.declType.isType(type);
   }

   public MethodImplementation getImplementation() {
      return this.implementation;
   }

   /** @deprecated */
   @Deprecated
   public boolean isGenerated() {
      return this.isSynthetic();
   }

   public String toString() {
      return this.getReturnType() + " " + this.getName() + "(" + this.getParamDeclList() + ")";
   }

   public int hashCode() {
      return this.getName().hashCode();
   }

   public boolean equals(Object o) {
      return o instanceof MethodDeclaration ? this.getKey().equals(((MethodDeclaration)o).getKey()) : false;
   }

   public int compareTo(Object o) {
      return !(o instanceof MethodDeclaration) ? -1 : this.getKey().compareTo(((MethodDeclaration)o).getKey());
   }

   protected JMethod getMethod() {
      return this.jMethod;
   }

   protected JClass getReturnJClass() {
      return this.jMethod.getReturnType();
   }

   void setDeclared(boolean declared) {
      this.declared = declared;
   }

   protected boolean isDeclared() {
      return this.declared;
   }

   protected JClass[] getExceptionTypes() {
      return this.jMethod.getExceptionTypes();
   }

   MethodImplementation implement(BeanCustomizer customizer) {
      this.implementation = MethodFactory.SINGLETON.createImplementation(this, customizer);
      return this.implementation;
   }

   private void initParamStrings(JParameter[] params) {
      StringBuffer declBuf = new StringBuffer();
      StringBuffer paramBuf = new StringBuffer();
      this.paramTypes = new String[params.length];

      for(int i = 0; i < params.length; ++i) {
         if (i > 0) {
            declBuf.append(", ");
            paramBuf.append(", ");
         }

         String type = Context.get().abbreviateClass(params[i].getType().getQualifiedName());
         this.paramTypes[i] = type;
         declBuf.append(type + " param" + i);
         paramBuf.append("param" + i);
      }

      this.paramDeclString = declBuf.toString();
      this.paramString = paramBuf.toString();
   }

   private void initExceptionString(JClass[] exceptions) {
      StringBuffer buf = new StringBuffer();

      for(int i = 0; i < exceptions.length; ++i) {
         if (i > 0) {
            buf.append(", ");
         }

         String excpName = Context.get().abbreviateClass(exceptions[i].getQualifiedName());
         if (excpName != null) {
            buf.append(excpName.replace('$', '.'));
         }
      }

      this.exceptionString = buf.toString();
   }

   private String getKey() {
      return this.getName() + this.getParamDeclList();
   }

   public boolean isConstructor() {
      return this.isType(ConstructorType.CONSTRUCTOR);
   }

   public boolean isSetter() {
      return this.isType(PropertyMethodType.SETTER);
   }

   public boolean isStandardBeanPropertyMethod() {
      return this.isType(PropertyMethodType.GETTER) || this.isType(PropertyMethodType.SETTER);
   }

   public boolean isFactory() {
      return this.isType(PropertyMethodType.CREATOR) || this.isType(PropertyMethodType.DESTROYER);
   }

   public boolean isCreator() {
      return this.isType(PropertyMethodType.CREATOR);
   }

   public boolean isCreatorClone() {
      JParameter[] params = this.getMethod().getParameters();
      return params != null && params.length != 0 ? "toClone".equals(params[0].getSimpleName()) : false;
   }

   public boolean isDestroyer() {
      return this.isType(PropertyMethodType.DESTROYER);
   }

   public boolean isCollectionManager() {
      return this.isType(PropertyMethodType.ADDER) || this.isType(PropertyMethodType.REMOVER);
   }

   public boolean isAdder() {
      return this.isType(PropertyMethodType.ADDER);
   }

   public boolean isRemover() {
      return this.isType(PropertyMethodType.REMOVER);
   }

   public boolean isFinder() {
      return this.isType(PropertyMethodType.FINDER);
   }

   public boolean isOperation() {
      return this.isType(MethodType.OPERATION);
   }

   public int getOrder() {
      return this.order;
   }

   public void setOrder(int order) {
      this.order = order;
   }

   public boolean isDeprecated() {
      return this.isAnnotationDefined("deprecated");
   }
}
