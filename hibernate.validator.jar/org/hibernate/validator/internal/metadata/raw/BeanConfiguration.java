package org.hibernate.validator.internal.metadata.raw;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

public class BeanConfiguration {
   private final ConfigurationSource source;
   private final Class beanClass;
   private final Set constrainedElements;
   private final List defaultGroupSequence;
   private final DefaultGroupSequenceProvider defaultGroupSequenceProvider;

   public BeanConfiguration(ConfigurationSource source, Class beanClass, Set constrainedElements, List defaultGroupSequence, DefaultGroupSequenceProvider defaultGroupSequenceProvider) {
      this.source = source;
      this.beanClass = beanClass;
      this.constrainedElements = CollectionHelper.newHashSet((Collection)constrainedElements);
      this.defaultGroupSequence = defaultGroupSequence;
      this.defaultGroupSequenceProvider = defaultGroupSequenceProvider;
   }

   public ConfigurationSource getSource() {
      return this.source;
   }

   public Class getBeanClass() {
      return this.beanClass;
   }

   public Set getConstrainedElements() {
      return this.constrainedElements;
   }

   public List getDefaultGroupSequence() {
      return this.defaultGroupSequence;
   }

   public DefaultGroupSequenceProvider getDefaultGroupSequenceProvider() {
      return this.defaultGroupSequenceProvider;
   }

   public String toString() {
      return "BeanConfiguration [beanClass=" + this.beanClass.getSimpleName() + ", source=" + this.source + ", constrainedElements=" + this.constrainedElements + ", defaultGroupSequence=" + this.defaultGroupSequence + ", defaultGroupSequenceProvider=" + this.defaultGroupSequenceProvider + "]";
   }

   public int hashCode() {
      int prime = true;
      int result = 1;
      result = 31 * result + (this.beanClass == null ? 0 : this.beanClass.hashCode());
      result = 31 * result + (this.source == null ? 0 : this.source.hashCode());
      return result;
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         BeanConfiguration other = (BeanConfiguration)obj;
         if (this.beanClass == null) {
            if (other.beanClass != null) {
               return false;
            }
         } else if (!this.beanClass.equals(other.beanClass)) {
            return false;
         }

         return this.source == other.source;
      }
   }
}
