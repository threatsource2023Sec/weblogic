package org.hibernate.validator.internal.engine.groups;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.validation.GroupSequence;
import javax.validation.groups.Default;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

public class ValidationOrderGenerator {
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
   private final ConcurrentMap resolvedSequences = new ConcurrentHashMap();

   public ValidationOrder getValidationOrder(Class group, boolean expand) {
      if (Default.class.equals(group)) {
         return ValidationOrder.DEFAULT_GROUP;
      } else if (expand) {
         return this.getValidationOrder(Collections.singletonList(group));
      } else {
         DefaultValidationOrder validationOrder = new DefaultValidationOrder();
         validationOrder.insertGroup(new Group(group));
         return validationOrder;
      }
   }

   public ValidationOrder getValidationOrder(Collection groups) {
      if (groups != null && groups.size() != 0) {
         if (groups.size() == 1 && groups.contains(Default.class)) {
            return ValidationOrder.DEFAULT_GROUP;
         } else {
            Iterator var2 = groups.iterator();

            Class clazz;
            do {
               if (!var2.hasNext()) {
                  DefaultValidationOrder validationOrder = new DefaultValidationOrder();
                  Iterator var7 = groups.iterator();

                  while(var7.hasNext()) {
                     Class clazz = (Class)var7.next();
                     if (Default.class.equals(clazz)) {
                        validationOrder.insertGroup(Group.DEFAULT_GROUP);
                     } else if (this.isGroupSequence(clazz)) {
                        this.insertSequence(clazz, ((GroupSequence)clazz.getAnnotation(GroupSequence.class)).value(), true, validationOrder);
                     } else {
                        Group group = new Group(clazz);
                        validationOrder.insertGroup(group);
                        this.insertInheritedGroups(clazz, validationOrder);
                     }
                  }

                  return validationOrder;
               }

               clazz = (Class)var2.next();
            } while(clazz.isInterface());

            throw LOG.getGroupHasToBeAnInterfaceException(clazz);
         }
      } else {
         throw LOG.getAtLeastOneGroupHasToBeSpecifiedException();
      }
   }

   public ValidationOrder getDefaultValidationOrder(Class clazz, List defaultGroupSequence) {
      DefaultValidationOrder validationOrder = new DefaultValidationOrder();
      this.insertSequence(clazz, (Class[])defaultGroupSequence.toArray(new Class[defaultGroupSequence.size()]), false, validationOrder);
      return validationOrder;
   }

   private boolean isGroupSequence(Class clazz) {
      return clazz.getAnnotation(GroupSequence.class) != null;
   }

   private void insertInheritedGroups(Class clazz, DefaultValidationOrder chain) {
      Class[] var3 = clazz.getInterfaces();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Class inheritedGroup = var3[var5];
         Group group = new Group(inheritedGroup);
         chain.insertGroup(group);
         this.insertInheritedGroups(inheritedGroup, chain);
      }

   }

   private void insertSequence(Class sequenceClass, Class[] sequenceElements, boolean cache, DefaultValidationOrder validationOrder) {
      Sequence sequence = cache ? (Sequence)this.resolvedSequences.get(sequenceClass) : null;
      if (sequence == null) {
         sequence = this.resolveSequence(sequenceClass, sequenceElements, new ArrayList());
         sequence.expandInheritedGroups();
         if (cache) {
            Sequence cachedResolvedSequence = (Sequence)this.resolvedSequences.putIfAbsent(sequenceClass, sequence);
            if (cachedResolvedSequence != null) {
               sequence = cachedResolvedSequence;
            }
         }
      }

      validationOrder.insertSequence(sequence);
   }

   private Sequence resolveSequence(Class sequenceClass, Class[] sequenceElements, List processedSequences) {
      if (processedSequences.contains(sequenceClass)) {
         throw LOG.getCyclicDependencyInGroupsDefinitionException();
      } else {
         processedSequences.add(sequenceClass);
         List resolvedSequenceGroups = new ArrayList();
         Class[] var5 = sequenceElements;
         int var6 = sequenceElements.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            Class clazz = var5[var7];
            if (this.isGroupSequence(clazz)) {
               Sequence tmpSequence = this.resolveSequence(clazz, ((GroupSequence)clazz.getAnnotation(GroupSequence.class)).value(), processedSequences);
               this.addGroups(resolvedSequenceGroups, tmpSequence.getComposingGroups());
            } else {
               List list = new ArrayList();
               list.add(new Group(clazz));
               this.addGroups(resolvedSequenceGroups, list);
            }
         }

         return new Sequence(sequenceClass, resolvedSequenceGroups);
      }
   }

   private void addGroups(List resolvedGroupSequence, List groups) {
      Iterator var3 = groups.iterator();

      while(var3.hasNext()) {
         Group tmpGroup = (Group)var3.next();
         if (resolvedGroupSequence.contains(tmpGroup) && resolvedGroupSequence.indexOf(tmpGroup) < resolvedGroupSequence.size() - 1) {
            throw LOG.getUnableToExpandGroupSequenceException();
         }

         resolvedGroupSequence.add(tmpGroup);
      }

   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("ValidationOrderGenerator");
      sb.append("{resolvedSequences=").append(this.resolvedSequences);
      sb.append('}');
      return sb.toString();
   }
}
