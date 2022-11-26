package weblogic.jdbc.common.internal;

import com.googlecode.cqengine.ConcurrentIndexedCollection;
import com.googlecode.cqengine.IndexedCollection;
import com.googlecode.cqengine.attribute.Attribute;
import com.googlecode.cqengine.attribute.SimpleNullableAttribute;
import com.googlecode.cqengine.index.hash.HashIndex;
import com.googlecode.cqengine.query.Query;
import com.googlecode.cqengine.query.QueryFactory;
import com.googlecode.cqengine.query.option.QueryOptions;
import com.googlecode.cqengine.resultset.ResultSet;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import weblogic.common.resourcepool.IGroupingPooledResourceSet;
import weblogic.common.resourcepool.PooledResource;
import weblogic.common.resourcepool.PooledResourceInfo;
import weblogic.common.resourcepool.ResourcePoolGroup;

public class IndexedGroupingPooledResourceSet extends AbstractSet implements IGroupingPooledResourceSet {
   int numEntriesHigh;
   IndexedCollection indexedCollection = new ConcurrentIndexedCollection();
   private static Attribute ATT_INSTANCE_NAME = new SimpleNullableAttribute("instanceName") {
      public String getValue(PooledResource ce, QueryOptions queryOptions) {
         ResourcePoolGroup instanceGroup = ((ConnectionEnv)ce).getGroup("instance");
         return instanceGroup != null ? instanceGroup.getName() : null;
      }
   };
   private static Attribute ATT_PDB_NAME = new SimpleNullableAttribute("pdbName") {
      public String getValue(PooledResource ce, QueryOptions queryOptions) {
         SwitchingContext sc = ((ConnectionEnv)ce).getSwitchingContext();
         return sc == null ? null : sc.getPDBName();
      }
   };
   private static Attribute ATT_SERVICE_NAME = new SimpleNullableAttribute("serviceName") {
      public String getValue(PooledResource ce, QueryOptions queryOptions) {
         SwitchingContext sc = ((ConnectionEnv)ce).getSwitchingContext();
         return sc == null ? null : sc.getPDBServiceName();
      }
   };

   public IndexedGroupingPooledResourceSet() {
      this.indexedCollection.addIndex(HashIndex.onAttribute(ATT_INSTANCE_NAME));
      this.indexedCollection.addIndex(HashIndex.onAttribute(ATT_PDB_NAME));
      this.indexedCollection.addIndex(HashIndex.onAttribute(ATT_SERVICE_NAME));
   }

   private Query getIndexedQuery(PooledResourceInfo pri) {
      Query queryPDBName = null;
      Query queryServiceName = null;
      Query queryInstance = null;
      Query query = null;
      String serviceName;
      if (pri instanceof HAPooledResourceInfo) {
         HAPooledResourceInfo hapri = (HAPooledResourceInfo)pri;
         Set serviceInstances = hapri.getServiceInstances();
         if (serviceInstances != null) {
            Iterator var8 = serviceInstances.iterator();

            while(var8.hasNext()) {
               String serviceInstance = (String)var8.next();
               Query qi = QueryFactory.equal(ATT_INSTANCE_NAME, serviceInstance);
               if (queryInstance == null) {
                  queryInstance = qi;
               } else {
                  queryInstance = QueryFactory.or((Query)queryInstance, qi);
               }
            }
         } else {
            serviceName = hapri.getRACInstance().getInstance();
            if (serviceName != null) {
               queryInstance = QueryFactory.equal(ATT_INSTANCE_NAME, serviceName);
            }
         }
      }

      if (pri instanceof LabelingConnectionInfo) {
         LabelingConnectionInfo lci = (LabelingConnectionInfo)pri;
         String pdbName = lci.getPDBName();
         if (pdbName != null) {
            queryPDBName = QueryFactory.equal(ATT_PDB_NAME, pdbName);
            serviceName = lci.getServiceName();
            if (serviceName != null) {
               queryServiceName = QueryFactory.equal(ATT_SERVICE_NAME, serviceName);
            }
         }
      }

      if (queryInstance != null) {
         query = queryInstance;
      }

      if (queryPDBName != null) {
         if (query == null) {
            query = queryPDBName;
         } else {
            query = QueryFactory.and((Query)query, queryPDBName);
         }
      }

      if (queryServiceName != null) {
         if (query == null) {
            query = queryServiceName;
         } else {
            query = QueryFactory.and((Query)query, queryServiceName);
         }
      }

      return (Query)query;
   }

   public PooledResource removeMatching(PooledResourceInfo pri) {
      Query query = this.getIndexedQuery(pri);
      ResultSet rs = null;

      PooledResource var6;
      try {
         rs = this.indexedCollection.retrieve(query);
         Iterator var4 = rs.iterator();
         if (!var4.hasNext()) {
            var4 = null;
            return var4;
         }

         PooledResource match = (PooledResource)var4.next();
         this.indexedCollection.remove(match);
         var6 = match;
      } finally {
         if (rs != null) {
            rs.close();
         }

      }

      return var6;
   }

   public int sizeHigh() {
      return this.numEntriesHigh;
   }

   public int getSize(PooledResourceInfo pri) {
      Query query = this.getIndexedQuery(pri);
      ResultSet rs = null;

      int var4;
      try {
         rs = this.indexedCollection.retrieve(query);
         var4 = rs.size();
      } finally {
         if (rs != null) {
            rs.close();
         }

      }

      return var4;
   }

   public List getSubList(PooledResourceInfo pri) {
      List list = null;
      Query query = this.getIndexedQuery(pri);
      ResultSet rs = this.indexedCollection.retrieve(query);
      if (rs.isNotEmpty()) {
         list = new ArrayList();
         Iterator var5 = rs.iterator();

         while(var5.hasNext()) {
            PooledResource ce = (PooledResource)var5.next();
            list.add(ce);
         }

         rs.close();
      }

      return list;
   }

   public boolean add(PooledResource pr) {
      boolean r = this.indexedCollection.add(pr);
      if (this.numEntriesHigh < this.indexedCollection.size()) {
         this.numEntriesHigh = this.indexedCollection.size();
      }

      return r;
   }

   public int size() {
      return this.indexedCollection.size();
   }

   public boolean isEmpty() {
      return this.indexedCollection.isEmpty();
   }

   public boolean contains(Object o) {
      return this.indexedCollection.contains(o);
   }

   public Iterator iterator() {
      return this.indexedCollection.iterator();
   }

   public Object[] toArray() {
      return this.indexedCollection.toArray();
   }

   public Object[] toArray(Object[] a) {
      return this.indexedCollection.toArray(a);
   }

   public boolean remove(Object o) {
      return this.indexedCollection.remove(o);
   }

   public boolean containsAll(Collection c) {
      return this.indexedCollection.containsAll(c);
   }

   public boolean addAll(Collection c) {
      return this.indexedCollection.addAll(c);
   }

   public boolean retainAll(Collection c) {
      return this.indexedCollection.retainAll(c);
   }

   public boolean removeAll(Collection c) {
      return this.indexedCollection.removeAll(c);
   }

   public void clear() {
      this.indexedCollection.clear();
   }

   public boolean equals(Object o) {
      return this.indexedCollection.equals(o);
   }

   public int hashCode() {
      return this.indexedCollection.hashCode();
   }

   public void resetStatistics() {
      this.numEntriesHigh = this.size();
   }
}
