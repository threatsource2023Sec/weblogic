package weblogic.descriptor.beangen;

import com.bea.util.jam.JClass;
import java.util.List;

public abstract class PropertyMethodImplementation extends MethodImplementation {
   protected PropertyMethodImplementation(MethodDeclaration decl, BeanCustomizer customizer) {
      super(decl, customizer);
   }

   protected PropertyMethodDeclaration getPropertyMethodDeclaration() {
      return (PropertyMethodDeclaration)this.getDeclaration();
   }

   protected BeanField getPropertyField() {
      PropertyDeclaration prop = this.getPropertyMethodDeclaration().getProperty();
      return prop.getImplementation().getField();
   }

   protected PropertyDeclaration prop() {
      return this.getPropertyMethodDeclaration().getProperty();
   }

   protected String pfName() {
      return this.prop().getImplementation().getField().getName();
   }

   protected JClass pfJClass() {
      return this.prop().getImplementation().getField().getJClass();
   }

   protected String pName() {
      return this.prop().getName();
   }

   protected String pType() {
      return this.prop().getType();
   }

   protected String pImpl() {
      return Context.get().interfaceToClassName(this.prop().getJClass());
   }

   protected JClass pJClass() {
      return this.prop().getJClass();
   }

   protected String pcName() {
      return this.prop().getComponentName();
   }

   protected String pcType() {
      return this.prop().getComponentType();
   }

   protected String pcImpl() {
      return Context.get().interfaceToClassName(this.prop().getComponentJClass());
   }

   protected boolean propIsChild() {
      return this.prop().isChild();
   }

   protected void addRemoveValidator(List sl, String paramName, PropertyDeclaration decl) {
      String validator = decl.getRemoveValidator();
      if (validator != null) {
         if (validator.indexOf(40) == -1) {
            validator = validator + "(" + paramName + ");";
         } else {
            validator = this.replaceExpressions(validator, paramName) + ";";
         }

         sl.add(validator);
      }

   }
}
