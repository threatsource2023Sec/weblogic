package javax.enterprise.inject.spi.configurator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Set;
import javax.enterprise.util.TypeLiteral;

public interface BeanAttributesConfigurator {
   BeanAttributesConfigurator addType(Type var1);

   BeanAttributesConfigurator addType(TypeLiteral var1);

   BeanAttributesConfigurator addTypes(Type... var1);

   BeanAttributesConfigurator addTypes(Set var1);

   BeanAttributesConfigurator addTransitiveTypeClosure(Type var1);

   BeanAttributesConfigurator types(Type... var1);

   BeanAttributesConfigurator types(Set var1);

   BeanAttributesConfigurator scope(Class var1);

   BeanAttributesConfigurator addQualifier(Annotation var1);

   BeanAttributesConfigurator addQualifiers(Annotation... var1);

   BeanAttributesConfigurator addQualifiers(Set var1);

   BeanAttributesConfigurator qualifiers(Annotation... var1);

   BeanAttributesConfigurator qualifiers(Set var1);

   BeanAttributesConfigurator addStereotype(Class var1);

   BeanAttributesConfigurator addStereotypes(Set var1);

   BeanAttributesConfigurator stereotypes(Set var1);

   BeanAttributesConfigurator name(String var1);

   BeanAttributesConfigurator alternative(boolean var1);
}
