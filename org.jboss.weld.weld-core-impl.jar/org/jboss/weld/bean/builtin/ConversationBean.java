package org.jboss.weld.bean.builtin;

import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.Locale;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.InjectionPoint;
import org.jboss.weld.bootstrap.BeanDeployerEnvironment;
import org.jboss.weld.context.ConversationContext;
import org.jboss.weld.contexts.conversation.ConversationImpl;
import org.jboss.weld.manager.BeanManagerImpl;

public class ConversationBean extends AbstractStaticallyDecorableBuiltInBean {
   public ConversationBean(BeanManagerImpl beanManager) {
      super(beanManager, Conversation.class);
   }

   public void internalInitialize(BeanDeployerEnvironment environment) {
      super.internalInitialize(environment);
   }

   protected Conversation newInstance(InjectionPoint ip, CreationalContext creationalContext) {
      Iterator var3 = this.getBeanManager().instance().select(ConversationContext.class, new Annotation[0]).iterator();

      ConversationContext conversationContext;
      do {
         if (!var3.hasNext()) {
            return new ConversationImpl(this.beanManager);
         }

         conversationContext = (ConversationContext)var3.next();
      } while(!conversationContext.isActive());

      return conversationContext.getCurrentConversation();
   }

   public Class getBeanClass() {
      return ConversationImpl.class;
   }

   public Class getScope() {
      return RequestScoped.class;
   }

   public String getName() {
      return Conversation.class.getName().toLowerCase(Locale.ENGLISH);
   }
}
