package weblogic.ejb.container.swap;

import com.oracle.pitchfork.interfaces.intercept.__ProxyControl;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import weblogic.cluster.ClusterServices;
import weblogic.cluster.ClusterServices.Locator;
import weblogic.cluster.replication.ResourceGroupKey;
import weblogic.common.internal.PeerInfoable;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.container.EJBDebugService;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.rmi.cluster.ReplicaVersion;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.io.FilteringObjectInputStream;
import weblogic.utils.io.UnsyncByteArrayInputStream;
import weblogic.utils.io.UnsyncByteArrayOutputStream;

public final class BeanStateDiffChecker {
   private static final DebugLogger debugLogger;
   public static final DebugLogger debugLoggerVerbose;
   private static AuthenticatedSubject KERNEL_ID;
   private static ComponentInvocationContextManager CICM;
   private final ClusterServices clusterServices = Locator.locate();
   private final Map fields = new HashMap();
   private static boolean lazyDeserializationMode;

   public BeanStateDiffChecker(Class clazz) {
      ArrayList classes;
      for(classes = new ArrayList(); clazz != null; clazz = clazz.getSuperclass()) {
         classes.add(clazz);
      }

      Iterator var3 = classes.iterator();

      while(var3.hasNext()) {
         Class c = (Class)var3.next();
         Field[] fieldArray = c.getDeclaredFields();
         AccessibleObject.setAccessible(fieldArray, true);
         Field[] var6 = fieldArray;
         int var7 = fieldArray.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            Field f = var6[var8];
            if (!Modifier.isTransient(f.getModifiers())) {
               Class fieldType = f.getType();
               if (isValidClassForReplication(fieldType)) {
                  this.fields.put(new Key(f), f);
               } else if (debugLogger.isDebugEnabled()) {
                  debug("Field " + f.getName() + " of type " + fieldType.getName() + " is not Serializable. It will not be replicated");
               }
            }
         }
      }

   }

   private static boolean isValidClassForReplication(Class c) {
      return Serializable.class.isAssignableFrom(c) || c.isPrimitive() || Map.class.isAssignableFrom(c) || Set.class.isAssignableFrom(c) || List.class.isAssignableFrom(c);
   }

   public static boolean hasReplicatableState(Class c) {
      while(c != null) {
         Field[] var1 = c.getDeclaredFields();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            Field f = var1[var3];
            if (!Modifier.isTransient(f.getModifiers()) && isValidClassForReplication(f.getType())) {
               return true;
            }
         }

         c = c.getSuperclass();
      }

      return false;
   }

   private boolean isApplicationUpgradeRolloutInProgress(ResourceGroupKey resourceGroupKey) {
      String partitionName = null;
      if (resourceGroupKey == null) {
         ComponentInvocationContext cic = CICM.getCurrentComponentInvocationContext();
         if (cic != null) {
            partitionName = cic.getPartitionName();
         }
      } else {
         partitionName = resourceGroupKey.getPartitionName();
      }

      List failoverServerGroups = partitionName != null ? this.clusterServices.getFailoverServerGroups(partitionName) : this.clusterServices.getFailoverServerGroups();
      return failoverServerGroups != null && !failoverServerGroups.isEmpty();
   }

   private boolean isSessionLazyDeserializationEnabled(ResourceGroupKey resourceGroupKey) {
      String partitionName = null;
      if (resourceGroupKey == null) {
         ComponentInvocationContext cic = CICM.getCurrentComponentInvocationContext();
         if (cic != null) {
            partitionName = cic.getPartitionName();
         }
      } else {
         partitionName = resourceGroupKey.getPartitionName();
      }

      return partitionName != null ? this.clusterServices.isSessionLazyDeserializationEnabled(partitionName) : this.clusterServices.isSessionLazyDeserializationEnabled();
   }

   public ArrayList calculateDiff(Object bean, BeanState state) throws IllegalAccessException, InstantiationException {
      if (bean instanceof __ProxyControl) {
         bean = ((__ProxyControl)bean).__getTarget().getBeanTarget();
      }

      boolean lazyDeserializationEnabled = lazyDeserializationMode || this.isApplicationUpgradeRolloutInProgress((ResourceGroupKey)null) && this.clusterServices.isSessionLazyDeserializationEnabled((String)null);
      ArrayList diffs = new ArrayList();
      Iterator var5 = this.fields.values().iterator();

      while(true) {
         while(var5.hasNext()) {
            Field f = (Field)var5.next();
            Object oldv = state.get(f);
            Object newv = f.get(bean);
            if (oldv != null && newv == null) {
               diffs.add(lazyDeserializationEnabled ? new DiffLazy(f, (Object)null) : new Diff(f, (Object)null));
               state.update(f, (Object)null);
            } else if (lazyDeserializationEnabled) {
               if (oldv instanceof DiffLazy) {
                  throw new IllegalAccessException("Old bean state not deserialized for field " + f);
               }

               if (!eq(oldv, newv)) {
                  diffs.add(new DiffLazy(f, newv));
                  state.update(f, newv);
               }
            } else {
               Diff d = null;
               if (Map.class.isAssignableFrom(f.getType())) {
                  d = BeanStateDiffChecker.MapDiff.calculateDiff(newv, state, f);
               } else if (Set.class.isAssignableFrom(f.getType())) {
                  d = BeanStateDiffChecker.SetDiff.calculateDiff(newv, state, f);
               } else if (List.class.isAssignableFrom(f.getType())) {
                  d = BeanStateDiffChecker.ListDiff.calculateDiff(newv, state, f);
               } else if (!eq(oldv, newv)) {
                  d = new Diff(f, newv);
                  state.update(f, newv);
               }

               if (d != null && !d.isEmpty()) {
                  diffs.add(d);
               }
            }
         }

         if (diffs.isEmpty()) {
            return null;
         }

         return diffs;
      }
   }

   public ArrayList completeDiffFromState(BeanState state, ResourceGroupKey resourceGroupKey) {
      boolean lazyDeserializationEnabled = lazyDeserializationMode || this.isApplicationUpgradeRolloutInProgress(resourceGroupKey) && this.isSessionLazyDeserializationEnabled(resourceGroupKey);
      ArrayList diffs = new ArrayList();
      Iterator var5 = this.fields.values().iterator();

      while(var5.hasNext()) {
         Field f = (Field)var5.next();
         Object v = state.get(f);
         if (v instanceof DiffLazy) {
            diffs.add((Diff)v);
         } else if (lazyDeserializationEnabled) {
            diffs.add(new DiffLazy(f, v));
         } else {
            diffs.add(new Diff(f, v));
         }
      }

      if (diffs.isEmpty()) {
         return null;
      } else {
         return diffs;
      }
   }

   public void mergeDiff(BeanState bs, ArrayList diffs) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
      Key key = new Key();
      Iterator var4 = diffs.iterator();

      while(var4.hasNext()) {
         Diff diff = (Diff)var4.next();
         key.set(diff.getClassName(), diff.getFieldName());
         Field f = (Field)this.fields.get(key);
         Object field = bs.get(f);
         String type = diff.getFieldType();
         if (type == null) {
            bs.update(f, (Object)null);
         } else {
            diff.merge(bs, field, f);
         }
      }

   }

   public void applyBeanState(Object bean, BeanState bs) throws IllegalAccessException, IOException {
      BeanState savebs = bs.cloneBeanState();
      boolean done = false;

      try {
         this.setState(bean, bs);
         done = true;
      } finally {
         if (!done) {
            bs.copyBeanState(savebs);
         }

      }

   }

   private void setState(Object bean, BeanState bs) throws IllegalAccessException, IOException {
      if (bean instanceof __ProxyControl) {
         bean = ((__ProxyControl)bean).__getTarget().getBeanTarget();
      }

      Iterator var3 = this.fields.values().iterator();

      while(var3.hasNext()) {
         Field f = (Field)var3.next();
         Object value = bs.get(f);
         if (value == null) {
            f.set(bean, (Object)null);
         } else if (value instanceof DiffLazy) {
            if (debugLoggerVerbose.isDebugEnabled()) {
               debugLoggerVerbose.debug("BeanStateDiffChecker.setState(bean=" + bean + ", bs=" + bs + ")Field=" + f.getName() + ", need deserialization");
            }

            byte[] val = (byte[])((byte[])((DiffLazy)value).getValue());

            try {
               UnsyncByteArrayInputStream bais = new UnsyncByteArrayInputStream((byte[])val);
               Throwable var8 = null;

               try {
                  ObjectInput out = new FilteringObjectInputStream(bais);
                  Throwable var10 = null;

                  try {
                     Object o = out.readObject();
                     f.set(bean, o);
                     bs.update(f, o);
                  } catch (Throwable var37) {
                     var10 = var37;
                     throw var37;
                  } finally {
                     if (out != null) {
                        if (var10 != null) {
                           try {
                              out.close();
                           } catch (Throwable var36) {
                              var10.addSuppressed(var36);
                           }
                        } else {
                           out.close();
                        }
                     }

                  }
               } catch (Throwable var39) {
                  var8 = var39;
                  throw var39;
               } finally {
                  if (bais != null) {
                     if (var8 != null) {
                        try {
                           bais.close();
                        } catch (Throwable var35) {
                           var8.addSuppressed(var35);
                        }
                     } else {
                        bais.close();
                     }
                  }

               }
            } catch (ClassCastException | IllegalAccessException | IllegalArgumentException | NullPointerException | ExceptionInInitializerError | ClassNotFoundException var41) {
               throw new IOException(var41.getMessage(), var41);
            } catch (IOException var42) {
               throw var42;
            }
         } else {
            f.set(bean, value);
         }
      }

   }

   private static boolean eq(Object o1, Object o2) {
      if (o1 == null && o2 == null) {
         return true;
      } else {
         return o1 != null ? o1.equals(o2) : false;
      }
   }

   private static void debug(String s) {
      debugLogger.debug("[BeanStateDiffChecker] " + s);
   }

   public static void main(String[] args) {
      try {
         TestClass tc = new TestClass();
         BeanStateDiffChecker bsdc = new BeanStateDiffChecker(tc.getClass());
         BeanState bs = new BeanState();
         ArrayList list = bsdc.calculateDiff(tc, bs);
         System.out.println("Diff:");

         for(int i = 0; i < list.size(); ++i) {
            System.out.println(list.get(i));
         }

         System.out.println("Source state:");
         System.out.println(bs);
         BeanState bs2 = new BeanState();
         bsdc.mergeDiff(bs2, list);
         System.out.println("Destination state:");
         System.out.println(bs2);
         tc.update();
         System.out.println("************bean updated**************");
         list = bsdc.calculateDiff(tc, bs);

         for(int i = 0; i < list.size(); ++i) {
            System.out.println(list.get(i));
         }

         System.out.println("Source state:");
         System.out.println(bs);
         bsdc.mergeDiff(bs2, list);
         System.out.println("Destination state:");
         System.out.println(bs);
      } catch (Exception var7) {
         var7.printStackTrace();
      }

   }

   static {
      debugLogger = EJBDebugService.swappingLogger;
      debugLoggerVerbose = EJBDebugService.swappingLoggerVerbose;
      KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
      CICM = ComponentInvocationContextManager.getInstance(KERNEL_ID);
      lazyDeserializationMode = Boolean.getBoolean("weblogic.ejb.container.replication.lazyDeserialization");
   }

   static class MyTest {
      String firstName = "UnknownFN";
      String lastName = "UnknownLN";
      int id = 1000;

      public MyTest() {
      }

      public void setFirstName(String s) {
         this.firstName = s;
      }

      public void setLastName(String s) {
         this.lastName = s;
      }

      public void setId(int i) {
         this.id = i;
      }

      public String toString() {
         return this.firstName + " " + this.lastName + " - " + this.id;
      }
   }

   private static class TestClass {
      private int iprim = 0;
      private String string = "string";
      private Integer iobj = new Integer(500);
      private Map map = new HashMap();
      private String string2;
      private Number d;
      private Set set;
      private List list;
      private MyTest test;

      public TestClass() {
         this.map.put("update", "new");
         this.map.put("remove", "remove");
         this.set = new HashSet();
         this.set.add("FirstOneInSet");
         this.set.add("SecondOneInSet");
         this.list = new ArrayList();
         this.list.add("FirstOneInList");
         this.list.add("SecondOneInList");
         this.string2 = "string2";
         this.d = null;
         this.test = new MyTest();
      }

      public void update() {
         this.iprim = 100;
         this.string = "newstring";
         this.map.put("new", "new");
         this.map.put("update", "updated");
         this.map.remove("remove");
         this.set.remove("FirstOneInSet");
         this.set.remove("SecondOneInSet");
         this.list.remove("SecondOneInList");
         this.list.remove("FirstOneInList");
         this.list = null;
         this.string2 = null;
         this.d = new Double(100.056);
         this.test.setId(2000);
         this.test.setFirstName("MyFirstName");
         this.test.setLastName("MyLastName");
         this.test = null;
      }
   }

   private static class Key {
      private String className;
      private String fieldName;

      public Key() {
      }

      public Key(Field f) {
         this.className = f.getDeclaringClass().getName();
         this.fieldName = f.getName();
      }

      public void set(String c, String f) {
         this.className = c;
         this.fieldName = f;
      }

      public int hashCode() {
         return this.className.hashCode() ^ this.fieldName.hashCode();
      }

      public boolean equals(Object o) {
         if (o != null && o instanceof Key) {
            Key other = (Key)o;
            return other.className.equals(this.className) && other.fieldName.equals(this.fieldName);
         } else {
            return false;
         }
      }
   }

   private static class ListDiff extends Diff implements Serializable {
      private int size;
      private HashMap modifiedEntries;

      public ListDiff(Field f, String fieldType) {
         super(f);
         this.setFieldType(fieldType);
      }

      public int getSize() {
         return this.size;
      }

      public HashMap getModifiedEntries() {
         return this.modifiedEntries;
      }

      public void addModified(int index, Object value) {
         if (this.modifiedEntries == null) {
            this.modifiedEntries = new HashMap();
         }

         this.modifiedEntries.put(new Integer(index), value);
      }

      public void setSize(int s) {
         this.size = s;
      }

      public void addAll(List list) {
         for(int i = 0; i < list.size(); ++i) {
            this.addModified(i, list.get(i));
         }

      }

      public boolean isEmpty() {
         return this.modifiedEntries == null || this.size == 0;
      }

      protected void merge(BeanState bs, Object field, Field f) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
         if (field == null) {
            field = Class.forName(this.getFieldType()).newInstance();
            bs.update(f, field);
         }

         List list = (List)field;
         if (this.modifiedEntries != null) {
            Set set = this.modifiedEntries.entrySet();

            int index;
            Object value;
            for(Iterator it = set.iterator(); it.hasNext(); list.set(index, value)) {
               Map.Entry entry = (Map.Entry)it.next();
               index = (Integer)entry.getKey();
               value = entry.getValue();
               int s = list.size();
               if (s < index + 1) {
                  for(int i = s; i <= index; ++i) {
                     list.add((Object)null);
                  }
               }
            }
         }

         for(int currentSize = list.size(); currentSize > this.size; --currentSize) {
            list.remove(currentSize - 1);
         }

      }

      static Diff calculateDiff(Object newv, BeanState state, Field f) throws InstantiationException, IllegalAccessException {
         Object oldv = state.get(f);
         Diff d = null;
         if (oldv == null && newv != null) {
            int lsize = ((List)newv).size();
            oldv = newv.getClass().newInstance();
            state.update(f, oldv);
            ListDiff ad = new ListDiff(f, newv.getClass().getName());
            ad.addAll((List)newv);
            ad.setSize(lsize);
            ((List)oldv).addAll((List)newv);
            d = ad;
         } else if (oldv != null && newv != null) {
            d = updateFieldStateForList(f, (List)oldv, (List)newv);
         }

         return d;
      }

      private static ListDiff updateFieldStateForList(Field f, List oldv, List newv) {
         ListDiff d = new ListDiff(f, newv.getClass().getName());
         d.setSize(newv.size());
         if (oldv.isEmpty()) {
            d.addAll(newv);
            oldv.addAll(newv);
            return d;
         } else if (newv.isEmpty()) {
            oldv.clear();
            return d;
         } else {
            int sizeToCheck = newv.size();
            boolean newElements = false;
            if (oldv.size() < sizeToCheck) {
               sizeToCheck = oldv.size();
               newElements = true;
            }

            int i;
            for(i = 0; i < sizeToCheck; ++i) {
               if (!BeanStateDiffChecker.eq(oldv.get(i), newv.get(i))) {
                  d.addModified(i, newv.get(i));
                  oldv.set(i, newv.get(i));
               }
            }

            if (newElements) {
               for(i = sizeToCheck; i < newv.size(); ++i) {
                  d.addModified(i, newv.get(i));
                  oldv.add(i, newv.get(i));
               }
            }

            for(i = oldv.size() - newv.size(); i > 0; --i) {
               oldv.remove(oldv.size() - 1);
            }

            return d;
         }
      }

      public String toString() {
         StringBuilder sb = new StringBuilder();
         sb.append("Field: ").append(this.getClassName());
         sb.append(".").append(this.getFieldName()).append("\n");
         sb.append("Size : ").append(this.size).append("\n");
         sb.append("Type:  ").append(this.getFieldType()).append("\n");
         if (this.modifiedEntries != null && this.size > 0) {
            sb.append("New: ");
            Set entries = this.modifiedEntries.entrySet();
            Iterator it = entries.iterator();

            while(it.hasNext()) {
               Map.Entry entry = (Map.Entry)it.next();
               sb.append(entry.getKey() + " : " + entry.getValue() + "\n");
            }
         }

         return sb.toString();
      }
   }

   private static class SetDiff extends Diff implements Serializable {
      private ArrayList newEntries;
      private ArrayList removedEntries;

      public SetDiff(Field f, String fieldType) {
         super(f);
         this.setFieldType(fieldType);
      }

      public ArrayList getNewEntries() {
         return this.newEntries;
      }

      public ArrayList getRemovedEntries() {
         return this.removedEntries;
      }

      public void addNew(Object obj) {
         if (this.newEntries == null) {
            this.newEntries = new ArrayList();
         }

         this.newEntries.add(obj);
      }

      public void addRemoved(Object obj) {
         if (this.removedEntries == null) {
            this.removedEntries = new ArrayList();
         }

         this.removedEntries.add(obj);
      }

      public void addAll(Set set) {
         Iterator iterator = set.iterator();

         while(iterator.hasNext()) {
            this.addNew(iterator.next());
         }

      }

      public void removeAll(Set set) {
         Iterator iterator = set.iterator();

         while(iterator.hasNext()) {
            this.addRemoved(iterator.next());
         }

      }

      public boolean isEmpty() {
         return (this.newEntries == null || this.newEntries.isEmpty()) && (this.removedEntries == null || this.removedEntries.isEmpty());
      }

      protected void merge(BeanState bs, Object field, Field f) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
         if (field == null) {
            field = Class.forName(this.getFieldType()).newInstance();
            bs.update(f, field);
         }

         Set set = (Set)field;
         int j;
         if (this.newEntries != null) {
            for(j = 0; j < this.newEntries.size(); ++j) {
               Object entry = this.newEntries.get(j);
               set.add(entry);
            }
         }

         if (this.removedEntries != null) {
            for(j = 0; j < this.removedEntries.size(); ++j) {
               set.remove(this.removedEntries.get(j));
            }
         }

      }

      static Diff calculateDiff(Object newv, BeanState state, Field f) throws InstantiationException, IllegalAccessException {
         Object oldv = state.get(f);
         Diff d = null;
         if (oldv == null && newv != null) {
            oldv = newv.getClass().newInstance();
            state.update(f, oldv);
            SetDiff sd = new SetDiff(f, newv.getClass().getName());
            sd.addAll((Set)newv);
            ((Set)oldv).addAll((Set)newv);
            d = sd;
         } else if (oldv != null && newv != null) {
            d = updateFieldStateForSet(f, (Set)oldv, (Set)newv);
         }

         return d;
      }

      private static SetDiff updateFieldStateForSet(Field f, Set oldSet, Set newSet) {
         SetDiff d = new SetDiff(f, newSet.getClass().getName());
         if (oldSet.isEmpty()) {
            d.addAll(newSet);
            oldSet.addAll(newSet);
            return d;
         } else if (newSet.isEmpty()) {
            d.removeAll(oldSet);
            oldSet.clear();
            return d;
         } else {
            Iterator iterator = oldSet.iterator();

            Object entry;
            while(iterator.hasNext()) {
               entry = iterator.next();
               if (!newSet.contains(entry)) {
                  d.addRemoved(entry);
                  iterator.remove();
               }
            }

            iterator = newSet.iterator();

            while(iterator.hasNext()) {
               entry = iterator.next();
               if (!oldSet.contains(entry)) {
                  d.addNew(entry);
                  oldSet.add(entry);
               }
            }

            return d;
         }
      }

      public String toString() {
         StringBuilder sb = new StringBuilder();
         sb.append("Field: ").append(this.getClassName());
         sb.append(".").append(this.getFieldName()).append("\n");
         sb.append("Type:  ").append(this.getFieldType()).append("\n");
         int i;
         if (this.newEntries != null && !this.newEntries.isEmpty()) {
            sb.append("New: ");

            for(i = 0; i < this.newEntries.size(); ++i) {
               Object entry = this.newEntries.get(i);
               sb.append(entry).append("\n");
            }
         }

         if (this.removedEntries != null && !this.removedEntries.isEmpty()) {
            sb.append("Removed: ");

            for(i = 0; i < this.removedEntries.size(); ++i) {
               sb.append(this.removedEntries.get(i)).append("\n");
            }
         }

         return sb.toString();
      }
   }

   private static class MapDiff extends Diff implements Serializable {
      private ArrayList newEntries = new ArrayList();
      private ArrayList removedEntries;

      public MapDiff(Field f, String fieldType) {
         super(f);
         this.setFieldType(fieldType);
      }

      public ArrayList getNewEntries() {
         return this.newEntries;
      }

      public ArrayList getRemovedEntries() {
         return this.removedEntries;
      }

      public void addNew(Object key, Object value) {
         if (this.newEntries == null) {
            this.newEntries = new ArrayList();
         }

         this.newEntries.add(new Object[]{key, value});
      }

      public void addRemoved(Object key) {
         if (this.removedEntries == null) {
            this.removedEntries = new ArrayList();
         }

         this.removedEntries.add(key);
      }

      public void addAll(Map m) {
         Iterator iterator = m.entrySet().iterator();

         while(iterator.hasNext()) {
            Map.Entry e = (Map.Entry)iterator.next();
            this.addNew(e.getKey(), e.getValue());
         }

      }

      public void removeAll(Map m) {
         Iterator iterator = m.entrySet().iterator();

         while(iterator.hasNext()) {
            Map.Entry e = (Map.Entry)iterator.next();
            this.addRemoved(e.getKey());
         }

      }

      public boolean isEmpty() {
         return (this.newEntries == null || this.newEntries.isEmpty()) && (this.removedEntries == null || this.removedEntries.isEmpty());
      }

      protected void merge(BeanState bs, Object field, Field f) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
         if (field == null) {
            field = Class.forName(this.getFieldType()).newInstance();
            bs.update(f, field);
         }

         Map map = (Map)field;
         int j;
         if (this.newEntries != null) {
            for(j = 0; j < this.newEntries.size(); ++j) {
               Object[] entry = (Object[])((Object[])this.newEntries.get(j));
               map.put(entry[0], entry[1]);
            }
         }

         if (this.removedEntries != null) {
            for(j = 0; j < this.removedEntries.size(); ++j) {
               map.remove(this.removedEntries.get(j));
            }
         }

      }

      static Diff calculateDiff(Object newv, BeanState state, Field f) throws IllegalAccessException, InstantiationException {
         Object oldv = state.get(f);
         Diff d = null;
         if (oldv == null && newv != null) {
            oldv = newv.getClass().newInstance();
            state.update(f, oldv);
            MapDiff md = new MapDiff(f, newv.getClass().getName());
            md.addAll((Map)newv);
            ((Map)oldv).putAll((Map)newv);
            d = md;
         } else if (oldv != null && newv != null) {
            d = updateFieldStateForMap(f, (Map)oldv, (Map)newv);
         }

         return d;
      }

      private static MapDiff updateFieldStateForMap(Field f, Map oldm, Map newm) {
         MapDiff d = new MapDiff(f, newm.getClass().getName());
         if (oldm.isEmpty()) {
            d.addAll(newm);
            oldm.putAll(newm);
            return d;
         } else if (newm.isEmpty()) {
            d.removeAll(oldm);
            oldm.clear();
            return d;
         } else {
            Iterator iterator = oldm.entrySet().iterator();

            Map.Entry entry;
            Object key;
            while(iterator.hasNext()) {
               entry = (Map.Entry)iterator.next();
               key = entry.getKey();
               if (!newm.containsKey(key)) {
                  d.addRemoved(key);
                  iterator.remove();
               }
            }

            iterator = newm.entrySet().iterator();

            while(true) {
               Object val;
               do {
                  if (!iterator.hasNext()) {
                     return d;
                  }

                  entry = (Map.Entry)iterator.next();
                  key = entry.getKey();
                  val = entry.getValue();
               } while(oldm.containsKey(key) && BeanStateDiffChecker.eq(val, oldm.get(key)));

               d.addNew(entry.getKey(), entry.getValue());
               oldm.put(entry.getKey(), entry.getValue());
            }
         }
      }

      public String toString() {
         StringBuilder sb = new StringBuilder();
         sb.append("Field: ").append(this.getClassName());
         sb.append(".").append(this.getFieldName()).append("\n");
         sb.append("Type:  ").append(this.getFieldType()).append("\n");
         int i;
         if (this.newEntries != null && !this.newEntries.isEmpty()) {
            sb.append("New: ");

            for(i = 0; i < this.newEntries.size(); ++i) {
               Object[] entry = (Object[])((Object[])this.newEntries.get(i));
               sb.append(entry[0]).append("-->").append(entry[1]).append("\n");
            }
         }

         if (this.removedEntries != null && !this.removedEntries.isEmpty()) {
            sb.append("Removed: ");

            for(i = 0; i < this.removedEntries.size(); ++i) {
               sb.append(this.removedEntries.get(i)).append("\n");
            }
         }

         return sb.toString();
      }
   }

   private static class DiffLazy extends Diff implements Externalizable {
      private static final long serialVersionUID = 7897005723805125113L;
      private boolean valueNeedDeserialization = false;

      public DiffLazy() {
      }

      protected DiffLazy(Field f) {
         super(f);
      }

      public DiffLazy(Field f, Object val) {
         super(f, val);
      }

      Object getValue() {
         return this.value;
      }

      protected void merge(BeanState bs, Object field, Field f) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
         if (this.value == null) {
            super.merge(bs, field, f);
         } else {
            bs.update(f, this);
         }

      }

      public void writeExternal(ObjectOutput oo) throws IOException {
         if (BeanStateDiffChecker.debugLoggerVerbose.isDebugEnabled()) {
            BeanStateDiffChecker.debugLoggerVerbose.debug("BeanStateDiffChecker.DiffLazy.writeExternal(): fieldName=" + this.fieldName + ", fieldType=" + this.fieldType + ", value=" + this.value);
         }

         if (!(oo instanceof PeerInfoable)) {
            throw new IOException("Lazy deserialization is not supported for non-PeerInfoable ObjectOutput stream " + oo);
         } else if (!doesPeerSupportDiffLazy(oo)) {
            throw new IOException("Lazy deserialization is not supported for peer version " + ((PeerInfoable)oo).getPeerInfo());
         } else {
            oo.writeUTF(this.className);
            oo.writeUTF(this.fieldName);
            oo.writeUTF(this.fieldType);
            if (this.value == null) {
               oo.writeInt(0);
            } else if (this.valueNeedDeserialization) {
               oo.writeInt(((byte[])((byte[])this.value)).length);
               oo.write((byte[])((byte[])this.value));
            } else {
               UnsyncByteArrayOutputStream baos = new UnsyncByteArrayOutputStream();
               Throwable var3 = null;

               try {
                  ObjectOutput out = new ObjectOutputStream(baos);
                  Throwable var5 = null;

                  try {
                     out.writeObject(this.value);
                     byte[] buf = baos.toRawBytes();
                     oo.writeInt(((byte[])buf).length);
                     oo.write((byte[])buf);
                  } catch (Throwable var28) {
                     var5 = var28;
                     throw var28;
                  } finally {
                     if (out != null) {
                        if (var5 != null) {
                           try {
                              out.close();
                           } catch (Throwable var27) {
                              var5.addSuppressed(var27);
                           }
                        } else {
                           out.close();
                        }
                     }

                  }
               } catch (Throwable var30) {
                  var3 = var30;
                  throw var30;
               } finally {
                  if (baos != null) {
                     if (var3 != null) {
                        try {
                           baos.close();
                        } catch (Throwable var26) {
                           var3.addSuppressed(var26);
                        }
                     } else {
                        baos.close();
                     }
                  }

               }

            }
         }
      }

      public void readExternal(ObjectInput oo) throws IOException, ClassNotFoundException {
         this.className = oo.readUTF();
         this.fieldName = oo.readUTF();
         this.fieldType = oo.readUTF();
         if (BeanStateDiffChecker.debugLoggerVerbose.isDebugEnabled()) {
            BeanStateDiffChecker.debugLoggerVerbose.debug("BeanStateDiffChecker.DiffLazy.readExternal(): fieldName=" + this.fieldName + ", fieldType=" + this.fieldType);
         }

         int len = oo.readInt();
         if ((long)len == 0L) {
            this.value = null;
         } else {
            this.valueNeedDeserialization = true;
            byte[] val = new byte[len];

            int rnum;
            for(int rlen = 0; rlen < len; rlen += rnum) {
               rnum = oo.read(val, rlen, len - rlen);
               if (rnum == -1) {
                  throw new IOException("End of stream reached before read expected bytes " + len + ",  read " + rlen + " bytes for fieldName=" + this.fieldName + ", stream " + oo);
               }
            }

            this.value = val;
         }
      }

      private static boolean doesPeerSupportDiffLazy(Object o) {
         return ReplicaVersion.doesPeerSupportReplicaVersion(o);
      }
   }

   private static class Diff implements Serializable {
      private static final long serialVersionUID = 4494927592218672510L;
      protected String className;
      protected String fieldName;
      protected String fieldType;
      protected Object value;

      public Diff() {
      }

      protected Diff(Field f) {
         this.className = f.getDeclaringClass().getName();
         this.fieldName = f.getName();
      }

      public Diff(Field f, Object val) {
         this(f);
         this.value = val;
         if (val != null) {
            this.setFieldType(val.getClass().getName());
         }

      }

      protected void setFieldType(String s) {
         this.fieldType = s;
      }

      protected void merge(BeanState bs, Object field, Field f) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
         bs.update(f, this.value);
      }

      public Object get() {
         return this.value;
      }

      public String getClassName() {
         return this.className;
      }

      public String getFieldName() {
         return this.fieldName;
      }

      public String getFieldType() {
         return this.fieldType;
      }

      public boolean isEmpty() {
         return false;
      }

      public String toString() {
         StringBuilder sb = new StringBuilder();
         sb.append("Field: ").append(this.getClassName());
         sb.append(".").append(this.getFieldName()).append("\n");
         sb.append("Type:  ").append(this.getFieldType()).append("\n");
         sb.append("Value: ").append(this.get()).append("\n");
         return sb.toString();
      }
   }
}
