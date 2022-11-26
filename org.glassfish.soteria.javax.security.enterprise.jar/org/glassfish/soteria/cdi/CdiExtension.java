package org.glassfish.soteria.cdi;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;
import javax.enterprise.inject.spi.CDI;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessBean;
import javax.interceptor.Interceptor;
import javax.security.enterprise.authentication.mechanism.http.AutoApplySession;
import javax.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;
import javax.security.enterprise.authentication.mechanism.http.CustomFormAuthenticationMechanismDefinition;
import javax.security.enterprise.authentication.mechanism.http.FormAuthenticationMechanismDefinition;
import javax.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import javax.security.enterprise.authentication.mechanism.http.LoginToContinue;
import javax.security.enterprise.authentication.mechanism.http.RememberMe;
import javax.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;
import javax.security.enterprise.identitystore.IdentityStore;
import javax.security.enterprise.identitystore.IdentityStoreHandler;
import javax.security.enterprise.identitystore.LdapIdentityStoreDefinition;
import org.glassfish.soteria.SecurityContextImpl;
import org.glassfish.soteria.identitystores.DatabaseIdentityStore;
import org.glassfish.soteria.identitystores.EmbeddedIdentityStore;
import org.glassfish.soteria.identitystores.LdapIdentityStore;
import org.glassfish.soteria.identitystores.annotation.EmbeddedIdentityStoreDefinition;
import org.glassfish.soteria.identitystores.hash.Pbkdf2PasswordHashImpl;
import org.glassfish.soteria.mechanisms.BasicAuthenticationMechanism;
import org.glassfish.soteria.mechanisms.CustomFormAuthenticationMechanism;
import org.glassfish.soteria.mechanisms.FormAuthenticationMechanism;

public class CdiExtension implements Extension {
   private static final Logger LOGGER = Logger.getLogger(CdiExtension.class.getName());
   private List identityStoreBeans = new ArrayList();
   private Bean authenticationMechanismBean;
   private boolean httpAuthenticationMechanismFound;

   public void register(@Observes BeforeBeanDiscovery beforeBean, BeanManager beanManager) {
      CdiUtils.addAnnotatedTypes(beforeBean, beanManager, AutoApplySessionInterceptor.class, RememberMeInterceptor.class, LoginToContinueInterceptor.class, FormAuthenticationMechanism.class, CustomFormAuthenticationMechanism.class, SecurityContextImpl.class, IdentityStoreHandler.class, Pbkdf2PasswordHashImpl.class);
   }

   public void processBean(@Observes ProcessBean eventIn, BeanManager beanManager) {
      Class beanClass = eventIn.getBean().getBeanClass();
      Optional optionalEmbeddedStore = CdiUtils.getAnnotation(beanManager, eventIn.getAnnotated(), EmbeddedIdentityStoreDefinition.class);
      optionalEmbeddedStore.ifPresent((embeddedIdentityStoreDefinition) -> {
         this.logActivatedIdentityStore(EmbeddedIdentityStore.class, beanClass);
         this.identityStoreBeans.add((new CdiProducer()).scope(ApplicationScoped.class).beanClass(IdentityStore.class).types(Object.class, IdentityStore.class, EmbeddedIdentityStore.class).addToId(EmbeddedIdentityStoreDefinition.class).create((e) -> {
            return new EmbeddedIdentityStore(embeddedIdentityStoreDefinition);
         }));
      });
      Optional optionalDBStore = CdiUtils.getAnnotation(beanManager, eventIn.getAnnotated(), DatabaseIdentityStoreDefinition.class);
      optionalDBStore.ifPresent((dataBaseIdentityStoreDefinition) -> {
         this.logActivatedIdentityStore(DatabaseIdentityStoreDefinition.class, beanClass);
         this.identityStoreBeans.add((new CdiProducer()).scope(ApplicationScoped.class).beanClass(IdentityStore.class).types(Object.class, IdentityStore.class, DatabaseIdentityStore.class).addToId(DatabaseIdentityStoreDefinition.class).create((e) -> {
            return new DatabaseIdentityStore(DatabaseIdentityStoreDefinitionAnnotationLiteral.eval(dataBaseIdentityStoreDefinition));
         }));
      });
      Optional optionalLdapStore = CdiUtils.getAnnotation(beanManager, eventIn.getAnnotated(), LdapIdentityStoreDefinition.class);
      optionalLdapStore.ifPresent((ldapIdentityStoreDefinition) -> {
         this.logActivatedIdentityStore(LdapIdentityStoreDefinition.class, beanClass);
         this.identityStoreBeans.add((new CdiProducer()).scope(ApplicationScoped.class).beanClass(IdentityStore.class).types(Object.class, IdentityStore.class, LdapIdentityStore.class).addToId(LdapIdentityStoreDefinition.class).create((e) -> {
            return new LdapIdentityStore(LdapIdentityStoreDefinitionAnnotationLiteral.eval(ldapIdentityStoreDefinition));
         }));
      });
      Optional optionalBasicMechanism = CdiUtils.getAnnotation(beanManager, eventIn.getAnnotated(), BasicAuthenticationMechanismDefinition.class);
      optionalBasicMechanism.ifPresent((basicAuthenticationMechanismDefinition) -> {
         this.logActivatedAuthenticationMechanism(BasicAuthenticationMechanismDefinition.class, beanClass);
         this.authenticationMechanismBean = (new CdiProducer()).scope(ApplicationScoped.class).beanClass(BasicAuthenticationMechanism.class).types(Object.class, HttpAuthenticationMechanism.class, BasicAuthenticationMechanism.class).addToId(BasicAuthenticationMechanismDefinition.class).create((e) -> {
            return new BasicAuthenticationMechanism(BasicAuthenticationMechanismDefinitionAnnotationLiteral.eval(basicAuthenticationMechanismDefinition));
         });
      });
      Optional optionalFormMechanism = CdiUtils.getAnnotation(beanManager, eventIn.getAnnotated(), FormAuthenticationMechanismDefinition.class);
      optionalFormMechanism.ifPresent((formAuthenticationMechanismDefinition) -> {
         this.logActivatedAuthenticationMechanism(FormAuthenticationMechanismDefinition.class, beanClass);
         this.authenticationMechanismBean = (new CdiProducer()).scope(ApplicationScoped.class).beanClass(HttpAuthenticationMechanism.class).types(Object.class, HttpAuthenticationMechanism.class).addToId(FormAuthenticationMechanismDefinition.class).create((e) -> {
            return ((FormAuthenticationMechanism)CDI.current().select(FormAuthenticationMechanism.class, new Annotation[0]).get()).loginToContinue(LoginToContinueAnnotationLiteral.eval(formAuthenticationMechanismDefinition.loginToContinue()));
         });
      });
      Optional optionalCustomFormMechanism = CdiUtils.getAnnotation(beanManager, eventIn.getAnnotated(), CustomFormAuthenticationMechanismDefinition.class);
      optionalCustomFormMechanism.ifPresent((customFormAuthenticationMechanismDefinition) -> {
         this.logActivatedAuthenticationMechanism(CustomFormAuthenticationMechanismDefinition.class, beanClass);
         this.authenticationMechanismBean = (new CdiProducer()).scope(ApplicationScoped.class).beanClass(HttpAuthenticationMechanism.class).types(Object.class, HttpAuthenticationMechanism.class).addToId(CustomFormAuthenticationMechanismDefinition.class).create((e) -> {
            return ((CustomFormAuthenticationMechanism)CDI.current().select(CustomFormAuthenticationMechanism.class, new Annotation[0]).get()).loginToContinue(LoginToContinueAnnotationLiteral.eval(customFormAuthenticationMechanismDefinition.loginToContinue()));
         });
      });
      if (eventIn.getBean().getTypes().contains(HttpAuthenticationMechanism.class)) {
         this.httpAuthenticationMechanismFound = true;
      }

      this.checkForWrongUseOfInterceptors(eventIn.getAnnotated(), beanClass);
   }

   public void afterBean(@Observes AfterBeanDiscovery afterBeanDiscovery, BeanManager beanManager) {
      if (!this.identityStoreBeans.isEmpty()) {
         Iterator var3 = this.identityStoreBeans.iterator();

         while(var3.hasNext()) {
            Bean identityStoreBean = (Bean)var3.next();
            afterBeanDiscovery.addBean(identityStoreBean);
         }
      }

      if (this.authenticationMechanismBean != null) {
         afterBeanDiscovery.addBean(this.authenticationMechanismBean);
      }

      afterBeanDiscovery.addBean((new CdiProducer()).scope(ApplicationScoped.class).beanClass(IdentityStoreHandler.class).types(Object.class, IdentityStoreHandler.class).addToId(IdentityStoreHandler.class).create((e) -> {
         DefaultIdentityStoreHandler defaultIdentityStoreHandler = new DefaultIdentityStoreHandler();
         defaultIdentityStoreHandler.init();
         return defaultIdentityStoreHandler;
      }));
   }

   public boolean isHttpAuthenticationMechanismFound() {
      return this.httpAuthenticationMechanismFound;
   }

   private void logActivatedIdentityStore(Class identityStoreClass, Class beanClass) {
      LOGGER.log(Level.INFO, "Activating {0} identity store from {1} class", new Object[]{identityStoreClass.getName(), beanClass.getName()});
   }

   private void logActivatedAuthenticationMechanism(Class authenticationMechanismClass, Class beanClass) {
      LOGGER.log(Level.INFO, "Activating {0} authentication mechanism from {1} class", new Object[]{authenticationMechanismClass.getName(), beanClass.getName()});
   }

   private void checkForWrongUseOfInterceptors(Annotated annotated, Class beanClass) {
      List annotations = Arrays.asList(AutoApplySession.class, LoginToContinue.class, RememberMe.class);
      Iterator var4 = annotations.iterator();

      while(var4.hasNext()) {
         Class annotation = (Class)var4.next();
         if (annotated.isAnnotationPresent(annotation) && !annotated.isAnnotationPresent(Interceptor.class) && !HttpAuthenticationMechanism.class.isAssignableFrom(beanClass)) {
            LOGGER.log(Level.WARNING, "Only classes implementing {0} may be annotated with {1}. {2} is annotated, but the interceptor won't take effect on it.", new Object[]{HttpAuthenticationMechanism.class.getName(), annotation.getName(), beanClass.getName()});
         }
      }

   }
}
