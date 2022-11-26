package org.jboss.weld.bean.builtin;

import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.Set;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.AnnotatedField;
import javax.enterprise.inject.spi.Extension;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedType;
import org.jboss.weld.annotated.slim.SlimAnnotatedType;
import org.jboss.weld.bean.BeanIdentifiers;
import org.jboss.weld.bean.StringBeanIdentifier;
import org.jboss.weld.bootstrap.spi.Metadata;
import org.jboss.weld.logging.BeanLogger;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.util.Proxies;

public class ExtensionBean extends AbstractBuiltInBean {
   private final SlimAnnotatedType annotatedType;
   private final Metadata instance;
   private final boolean passivationCapable;
   private final boolean proxiable;

   public ExtensionBean(BeanManagerImpl manager, EnhancedAnnotatedType enhancedAnnotatedType, Metadata instance) {
      super(new StringBeanIdentifier(BeanIdentifiers.forExtension(enhancedAnnotatedType)), manager, enhancedAnnotatedType.getJavaClass());
      this.annotatedType = enhancedAnnotatedType.slim();
      this.instance = instance;
      this.passivationCapable = enhancedAnnotatedType.isSerializable();
      this.proxiable = Proxies.isTypeProxyable(enhancedAnnotatedType.getBaseType(), manager.getServices());
      this.checkPublicFields(enhancedAnnotatedType);
   }

   private void checkPublicFields(EnhancedAnnotatedType clazz) {
      Iterator var2 = clazz.getFields().iterator();

      while(var2.hasNext()) {
         AnnotatedField field = (AnnotatedField)var2.next();
         Member member = field.getJavaMember();
         if (Modifier.isPublic(member.getModifiers()) && !Modifier.isStatic(member.getModifiers())) {
            BeanLogger.LOG.extensionWithNonStaticPublicField(clazz.getBaseType(), field.getJavaMember());
         }
      }

   }

   public Set getTypes() {
      return this.annotatedType.getTypeClosure();
   }

   public boolean isProxyable() {
      return this.proxiable;
   }

   public boolean isPassivationCapableBean() {
      return this.passivationCapable;
   }

   public Extension create(CreationalContext creationalContext) {
      return (Extension)this.instance.getValue();
   }

   public Class getScope() {
      return ApplicationScoped.class;
   }

   public SlimAnnotatedType getAnnotatedType() {
      return this.annotatedType;
   }

   public String toString() {
      return "Extension [" + this.getType().toString() + "] with qualifiers [@Default]; " + this.instance.getLocation();
   }
}
