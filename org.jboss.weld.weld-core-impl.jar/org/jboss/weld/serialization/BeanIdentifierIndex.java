package org.jboss.weld.serialization;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.PassivationCapable;
import org.jboss.weld.bean.CommonBean;
import org.jboss.weld.bean.StringBeanIdentifier;
import org.jboss.weld.bootstrap.api.Service;
import org.jboss.weld.exceptions.IllegalStateException;
import org.jboss.weld.logging.BootstrapLogger;
import org.jboss.weld.logging.SerializationLogger;
import org.jboss.weld.serialization.spi.BeanIdentifier;
import org.jboss.weld.util.Preconditions;
import org.jboss.weld.util.collections.ImmutableMap;

public class BeanIdentifierIndex implements Service {
   private volatile BeanIdentifier[] index;
   private volatile Map reverseIndex;
   private volatile Integer indexHash;
   private final AtomicBoolean indexBuilt = new AtomicBoolean(false);

   public Integer getIndex(BeanIdentifier identifier) {
      this.checkIsBuilt();
      Preconditions.checkArgumentNotNull(identifier, "identifier");
      return (Integer)this.reverseIndex.get(identifier);
   }

   public BeanIdentifier getIdentifier(int idx) {
      this.checkIsBuilt();
      if (idx >= 0 && idx < this.index.length) {
         return this.index[idx];
      } else {
         throw SerializationLogger.LOG.unableToGetBeanIdentifier(idx, this.getDebugInfo());
      }
   }

   public Integer getIndexHash() {
      return this.indexHash;
   }

   public void build(Set beans) {
      if (this.isBuilt()) {
         throw new IllegalStateException("BeanIdentifier index is already built!");
      } else if (beans.isEmpty()) {
         this.index = new BeanIdentifier[0];
         this.reverseIndex = Collections.emptyMap();
         this.indexHash = 0;
         this.indexBuilt.set(true);
      } else {
         List tempIndex = new ArrayList(beans.size());
         Iterator var3 = beans.iterator();

         while(var3.hasNext()) {
            Bean bean = (Bean)var3.next();
            if (bean instanceof CommonBean) {
               tempIndex.add(((CommonBean)bean).getIdentifier());
            } else if (bean instanceof PassivationCapable) {
               tempIndex.add(new StringBeanIdentifier(((PassivationCapable)bean).getId()));
            }
         }

         Collections.sort(tempIndex, new Comparator() {
            public int compare(BeanIdentifier o1, BeanIdentifier o2) {
               return o1.asString().compareTo(o2.asString());
            }
         });
         this.index = (BeanIdentifier[])tempIndex.toArray(new BeanIdentifier[tempIndex.size()]);
         ImmutableMap.Builder builder = ImmutableMap.builder();

         for(int i = 0; i < this.index.length; ++i) {
            builder.put(this.index[i], i);
         }

         this.reverseIndex = builder.build();
         this.indexHash = Arrays.hashCode(this.index);
         if (BootstrapLogger.LOG.isDebugEnabled()) {
            BootstrapLogger.LOG.beanIdentifierIndexBuilt(this.getDebugInfo());
         }

         this.indexBuilt.set(true);
      }
   }

   public boolean isBuilt() {
      return this.indexBuilt.get();
   }

   public boolean isEmpty() {
      return this.index.length == 0;
   }

   public void cleanup() {
      this.index = null;
   }

   private void checkIsBuilt() {
      if (!this.isBuilt()) {
         throw new IllegalStateException("BeanIdentifier index not built!");
      }
   }

   public String toString() {
      return String.format("BeanIdentifierIndex [hash=%s, indexed=%s]", this.indexHash, this.index.length);
   }

   public String getDebugInfo() {
      StringBuilder builder = new StringBuilder();
      builder.append(this.toString());
      builder.append(" \n");

      for(int i = 0; i < this.index.length; ++i) {
         builder.append("  ");
         builder.append(String.format("%4d", i));
         builder.append(": ");
         builder.append(this.index[i]);
         builder.append("\n");
      }

      return builder.toString();
   }
}
