package jsr166e;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;
import sun.misc.Unsafe;

public class ConcurrentHashMapV8 extends AbstractMap implements ConcurrentMap, Serializable {
   private static final long serialVersionUID = 7249069246763182397L;
   private static final int MAXIMUM_CAPACITY = 1073741824;
   private static final int DEFAULT_CAPACITY = 16;
   static final int MAX_ARRAY_SIZE = 2147483639;
   private static final int DEFAULT_CONCURRENCY_LEVEL = 16;
   private static final float LOAD_FACTOR = 0.75F;
   static final int TREEIFY_THRESHOLD = 8;
   static final int UNTREEIFY_THRESHOLD = 6;
   static final int MIN_TREEIFY_CAPACITY = 64;
   private static final int MIN_TRANSFER_STRIDE = 16;
   private static int RESIZE_STAMP_BITS = 16;
   private static final int MAX_RESIZERS;
   private static final int RESIZE_STAMP_SHIFT;
   static final int MOVED = -1;
   static final int TREEBIN = -2;
   static final int RESERVED = -3;
   static final int HASH_BITS = Integer.MAX_VALUE;
   static final int NCPU;
   private static final ObjectStreamField[] serialPersistentFields;
   transient volatile Node[] table;
   private transient volatile Node[] nextTable;
   private transient volatile long baseCount;
   private transient volatile int sizeCtl;
   private transient volatile int transferIndex;
   private transient volatile int cellsBusy;
   private transient volatile CounterCell[] counterCells;
   private transient KeySetView keySet;
   private transient ValuesView values;
   private transient EntrySetView entrySet;
   static final AtomicInteger counterHashCodeGenerator;
   static final int SEED_INCREMENT = 1640531527;
   static final ThreadLocal threadCounterHashCode;
   private static final Unsafe U;
   private static final long SIZECTL;
   private static final long TRANSFERINDEX;
   private static final long BASECOUNT;
   private static final long CELLSBUSY;
   private static final long CELLVALUE;
   private static final long ABASE;
   private static final int ASHIFT;

   static final int spread(int h) {
      return (h ^ h >>> 16) & Integer.MAX_VALUE;
   }

   private static final int tableSizeFor(int c) {
      int n = c - 1;
      n |= n >>> 1;
      n |= n >>> 2;
      n |= n >>> 4;
      n |= n >>> 8;
      n |= n >>> 16;
      return n < 0 ? 1 : (n >= 1073741824 ? 1073741824 : n + 1);
   }

   static Class comparableClassFor(Object x) {
      if (x instanceof Comparable) {
         Class c;
         if ((c = x.getClass()) == String.class) {
            return c;
         }

         Type[] ts;
         if ((ts = c.getGenericInterfaces()) != null) {
            for(int i = 0; i < ts.length; ++i) {
               Type[] as;
               Type t;
               ParameterizedType p;
               if ((t = ts[i]) instanceof ParameterizedType && (p = (ParameterizedType)t).getRawType() == Comparable.class && (as = p.getActualTypeArguments()) != null && as.length == 1 && as[0] == c) {
                  return c;
               }
            }
         }
      }

      return null;
   }

   static int compareComparables(Class kc, Object k, Object x) {
      return x != null && x.getClass() == kc ? ((Comparable)k).compareTo(x) : 0;
   }

   static final Node tabAt(Node[] tab, int i) {
      return (Node)U.getObjectVolatile(tab, ((long)i << ASHIFT) + ABASE);
   }

   static final boolean casTabAt(Node[] tab, int i, Node c, Node v) {
      return U.compareAndSwapObject(tab, ((long)i << ASHIFT) + ABASE, c, v);
   }

   static final void setTabAt(Node[] tab, int i, Node v) {
      U.putObjectVolatile(tab, ((long)i << ASHIFT) + ABASE, v);
   }

   public ConcurrentHashMapV8() {
   }

   public ConcurrentHashMapV8(int initialCapacity) {
      if (initialCapacity < 0) {
         throw new IllegalArgumentException();
      } else {
         int cap = initialCapacity >= 536870912 ? 1073741824 : tableSizeFor(initialCapacity + (initialCapacity >>> 1) + 1);
         this.sizeCtl = cap;
      }
   }

   public ConcurrentHashMapV8(Map m) {
      this.sizeCtl = 16;
      this.putAll(m);
   }

   public ConcurrentHashMapV8(int initialCapacity, float loadFactor) {
      this(initialCapacity, loadFactor, 1);
   }

   public ConcurrentHashMapV8(int initialCapacity, float loadFactor, int concurrencyLevel) {
      if (loadFactor > 0.0F && initialCapacity >= 0 && concurrencyLevel > 0) {
         if (initialCapacity < concurrencyLevel) {
            initialCapacity = concurrencyLevel;
         }

         long size = (long)(1.0 + (double)((float)((long)initialCapacity) / loadFactor));
         int cap = size >= 1073741824L ? 1073741824 : tableSizeFor((int)size);
         this.sizeCtl = cap;
      } else {
         throw new IllegalArgumentException();
      }
   }

   public int size() {
      long n = this.sumCount();
      return n < 0L ? 0 : (n > 2147483647L ? Integer.MAX_VALUE : (int)n);
   }

   public boolean isEmpty() {
      return this.sumCount() <= 0L;
   }

   public Object get(Object key) {
      int h = spread(key.hashCode());
      Node[] tab;
      Node e;
      int n;
      if ((tab = this.table) != null && (n = tab.length) > 0 && (e = tabAt(tab, n - 1 & h)) != null) {
         int eh;
         Object ek;
         if ((eh = e.hash) == h) {
            if ((ek = e.key) == key || ek != null && key.equals(ek)) {
               return e.val;
            }
         } else if (eh < 0) {
            Node p;
            return (p = e.find(h, key)) != null ? p.val : null;
         }

         while((e = e.next) != null) {
            if (e.hash == h && ((ek = e.key) == key || ek != null && key.equals(ek))) {
               return e.val;
            }
         }
      }

      return null;
   }

   public boolean containsKey(Object key) {
      return this.get(key) != null;
   }

   public boolean containsValue(Object value) {
      if (value == null) {
         throw new NullPointerException();
      } else {
         Node[] t;
         if ((t = this.table) != null) {
            Traverser it = new Traverser(t, t.length, 0, t.length);

            Node p;
            while((p = it.advance()) != null) {
               Object v;
               if ((v = p.val) == value || v != null && value.equals(v)) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   public Object put(Object key, Object value) {
      return this.putVal(key, value, false);
   }

   final Object putVal(Object key, Object value, boolean onlyIfAbsent) {
      if (key != null && value != null) {
         int hash = spread(key.hashCode());
         int binCount = 0;
         Node[] tab = this.table;

         while(true) {
            int n;
            while(tab == null || (n = tab.length) == 0) {
               tab = this.initTable();
            }

            Node f;
            int i;
            if ((f = tabAt(tab, i = n - 1 & hash)) == null) {
               if (casTabAt(tab, i, (Node)null, new Node(hash, key, value, (Node)null))) {
                  break;
               }
            } else {
               int fh;
               if ((fh = f.hash) == -1) {
                  tab = this.helpTransfer(tab, f);
               } else {
                  Object oldVal = null;
                  synchronized(f) {
                     if (tabAt(tab, i) == f) {
                        if (fh < 0) {
                           if (f instanceof TreeBin) {
                              binCount = 2;
                              TreeNode p;
                              if ((p = ((TreeBin)f).putTreeVal(hash, key, value)) != null) {
                                 oldVal = p.val;
                                 if (!onlyIfAbsent) {
                                    p.val = value;
                                 }
                              }
                           }
                        } else {
                           label103: {
                              binCount = 1;

                              Node e;
                              Object ek;
                              for(e = f; e.hash != hash || (ek = e.key) != key && (ek == null || !key.equals(ek)); ++binCount) {
                                 Node pred = e;
                                 if ((e = e.next) == null) {
                                    pred.next = new Node(hash, key, value, (Node)null);
                                    break label103;
                                 }
                              }

                              oldVal = e.val;
                              if (!onlyIfAbsent) {
                                 e.val = value;
                              }
                           }
                        }
                     }
                  }

                  if (binCount != 0) {
                     if (binCount >= 8) {
                        this.treeifyBin(tab, i);
                     }

                     if (oldVal != null) {
                        return oldVal;
                     }
                     break;
                  }
               }
            }
         }

         this.addCount(1L, binCount);
         return null;
      } else {
         throw new NullPointerException();
      }
   }

   public void putAll(Map m) {
      this.tryPresize(m.size());
      Iterator i$ = m.entrySet().iterator();

      while(i$.hasNext()) {
         Map.Entry e = (Map.Entry)i$.next();
         this.putVal(e.getKey(), e.getValue(), false);
      }

   }

   public Object remove(Object key) {
      return this.replaceNode(key, (Object)null, (Object)null);
   }

   final Object replaceNode(Object key, Object value, Object cv) {
      int hash = spread(key.hashCode());
      Node[] tab = this.table;

      Node f;
      int n;
      int i;
      while(tab != null && (n = tab.length) != 0 && (f = tabAt(tab, i = n - 1 & hash)) != null) {
         int fh;
         if ((fh = f.hash) == -1) {
            tab = this.helpTransfer(tab, f);
         } else {
            Object oldVal = null;
            boolean validated = false;
            synchronized(f) {
               if (tabAt(tab, i) == f) {
                  Object pv;
                  if (fh < 0) {
                     if (f instanceof TreeBin) {
                        validated = true;
                        TreeBin t = (TreeBin)f;
                        TreeNode r;
                        TreeNode p;
                        if ((r = t.root) != null && (p = r.findTreeNode(hash, key, (Class)null)) != null) {
                           pv = p.val;
                           if (cv == null || cv == pv || pv != null && cv.equals(pv)) {
                              oldVal = pv;
                              if (value != null) {
                                 p.val = value;
                              } else if (t.removeTreeNode(p)) {
                                 setTabAt(tab, i, untreeify(t.first));
                              }
                           }
                        }
                     }
                  } else {
                     label121: {
                        validated = true;
                        Node e = f;
                        Node pred = null;

                        Object ek;
                        while(e.hash != hash || (ek = e.key) != key && (ek == null || !key.equals(ek))) {
                           pred = e;
                           if ((e = e.next) == null) {
                              break label121;
                           }
                        }

                        pv = e.val;
                        if (cv == null || cv == pv || pv != null && cv.equals(pv)) {
                           oldVal = pv;
                           if (value != null) {
                              e.val = value;
                           } else if (pred != null) {
                              pred.next = e.next;
                           } else {
                              setTabAt(tab, i, e.next);
                           }
                        }
                     }
                  }
               }
            }

            if (validated) {
               if (oldVal != null) {
                  if (value == null) {
                     this.addCount(-1L, -1);
                  }

                  return oldVal;
               }
               break;
            }
         }
      }

      return null;
   }

   public void clear() {
      long delta = 0L;
      int i = 0;
      Node[] tab = this.table;

      while(tab != null && i < tab.length) {
         Node f = tabAt(tab, i);
         if (f == null) {
            ++i;
         } else {
            int fh;
            if ((fh = f.hash) == -1) {
               tab = this.helpTransfer(tab, f);
               i = 0;
            } else {
               synchronized(f) {
                  if (tabAt(tab, i) == f) {
                     for(Node p = fh >= 0 ? f : (f instanceof TreeBin ? ((TreeBin)f).first : null); p != null; p = ((Node)p).next) {
                        --delta;
                     }

                     setTabAt(tab, i++, (Node)null);
                  }
               }
            }
         }
      }

      if (delta != 0L) {
         this.addCount(delta, -1);
      }

   }

   public KeySetView keySet() {
      KeySetView ks;
      return (ks = this.keySet) != null ? ks : (this.keySet = new KeySetView(this, (Object)null));
   }

   public Collection values() {
      ValuesView vs;
      return (vs = this.values) != null ? vs : (this.values = new ValuesView(this));
   }

   public Set entrySet() {
      EntrySetView es;
      return (es = this.entrySet) != null ? es : (this.entrySet = new EntrySetView(this));
   }

   public int hashCode() {
      int h = 0;
      Node[] t;
      Node p;
      if ((t = this.table) != null) {
         for(Traverser it = new Traverser(t, t.length, 0, t.length); (p = it.advance()) != null; h += p.key.hashCode() ^ p.val.hashCode()) {
         }
      }

      return h;
   }

   public String toString() {
      Node[] t;
      int f = (t = this.table) == null ? 0 : t.length;
      Traverser it = new Traverser(t, f, 0, f);
      StringBuilder sb = new StringBuilder();
      sb.append('{');
      Node p;
      if ((p = it.advance()) != null) {
         while(true) {
            Object k = p.key;
            Object v = p.val;
            sb.append(k == this ? "(this Map)" : k);
            sb.append('=');
            sb.append(v == this ? "(this Map)" : v);
            if ((p = it.advance()) == null) {
               break;
            }

            sb.append(',').append(' ');
         }
      }

      return sb.append('}').toString();
   }

   public boolean equals(Object o) {
      if (o != this) {
         if (!(o instanceof Map)) {
            return false;
         } else {
            Map m = (Map)o;
            Node[] t;
            int f = (t = this.table) == null ? 0 : t.length;
            Traverser it = new Traverser(t, f, 0, f);

            Object val;
            Object mk;
            do {
               Node p;
               if ((p = it.advance()) == null) {
                  Iterator i$ = m.entrySet().iterator();

                  Object mv;
                  Object v;
                  Map.Entry e;
                  do {
                     if (!i$.hasNext()) {
                        return true;
                     }

                     e = (Map.Entry)i$.next();
                  } while((mk = e.getKey()) != null && (mv = e.getValue()) != null && (v = this.get(mk)) != null && (mv == v || mv.equals(v)));

                  return false;
               }

               val = p.val;
               mk = m.get(p.key);
            } while(mk != null && (mk == val || mk.equals(val)));

            return false;
         }
      } else {
         return true;
      }
   }

   private void writeObject(ObjectOutputStream s) throws IOException {
      int sshift = 0;

      int ssize;
      for(ssize = 1; ssize < 16; ssize <<= 1) {
         ++sshift;
      }

      int segmentShift = 32 - sshift;
      int segmentMask = ssize - 1;
      Segment[] segments = (Segment[])(new Segment[16]);

      for(int i = 0; i < segments.length; ++i) {
         segments[i] = new Segment(0.75F);
      }

      s.putFields().put("segments", segments);
      s.putFields().put("segmentShift", segmentShift);
      s.putFields().put("segmentMask", segmentMask);
      s.writeFields();
      Node[] t;
      if ((t = this.table) != null) {
         Traverser it = new Traverser(t, t.length, 0, t.length);

         Node p;
         while((p = it.advance()) != null) {
            s.writeObject(p.key);
            s.writeObject(p.val);
         }
      }

      s.writeObject((Object)null);
      s.writeObject((Object)null);
      segments = null;
   }

   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
      this.sizeCtl = -1;
      s.defaultReadObject();
      long size = 0L;
      Node p = null;

      while(true) {
         Object k = s.readObject();
         Object v = s.readObject();
         if (k == null || v == null) {
            if (size == 0L) {
               this.sizeCtl = 0;
            } else {
               int n;
               if (size >= 536870912L) {
                  n = 1073741824;
               } else {
                  int sz = (int)size;
                  n = tableSizeFor(sz + (sz >>> 1) + 1);
               }

               Node[] tab = (Node[])(new Node[n]);
               int mask = n - 1;

               long added;
               Node next;
               for(added = 0L; p != null; p = next) {
                  next = p.next;
                  int h = p.hash;
                  int j = h & mask;
                  boolean insertAtFront;
                  Node first;
                  if ((first = tabAt(tab, j)) == null) {
                     insertAtFront = true;
                  } else {
                     Object k = p.key;
                     if (first.hash < 0) {
                        TreeBin t = (TreeBin)first;
                        if (t.putTreeVal(h, k, p.val) == null) {
                           ++added;
                        }

                        insertAtFront = false;
                     } else {
                        int binCount = 0;
                        insertAtFront = true;

                        Node q;
                        for(q = first; q != null; q = q.next) {
                           Object qk;
                           if (q.hash == h && ((qk = q.key) == k || qk != null && k.equals(qk))) {
                              insertAtFront = false;
                              break;
                           }

                           ++binCount;
                        }

                        if (insertAtFront && binCount >= 8) {
                           insertAtFront = false;
                           ++added;
                           p.next = first;
                           TreeNode hd = null;
                           TreeNode tl = null;

                           for(q = p; q != null; q = q.next) {
                              TreeNode t = new TreeNode(q.hash, q.key, q.val, (Node)null, (TreeNode)null);
                              if ((t.prev = tl) == null) {
                                 hd = t;
                              } else {
                                 tl.next = t;
                              }

                              tl = t;
                           }

                           setTabAt(tab, j, new TreeBin(hd));
                        }
                     }
                  }

                  if (insertAtFront) {
                     ++added;
                     p.next = first;
                     setTabAt(tab, j, p);
                  }
               }

               this.table = tab;
               this.sizeCtl = n - (n >>> 2);
               this.baseCount = added;
            }

            return;
         }

         p = new Node(spread(k.hashCode()), k, v, p);
         ++size;
      }
   }

   public Object putIfAbsent(Object key, Object value) {
      return this.putVal(key, value, true);
   }

   public boolean remove(Object key, Object value) {
      if (key == null) {
         throw new NullPointerException();
      } else {
         return value != null && this.replaceNode(key, (Object)null, value) != null;
      }
   }

   public boolean replace(Object key, Object oldValue, Object newValue) {
      if (key != null && oldValue != null && newValue != null) {
         return this.replaceNode(key, newValue, oldValue) != null;
      } else {
         throw new NullPointerException();
      }
   }

   public Object replace(Object key, Object value) {
      if (key != null && value != null) {
         return this.replaceNode(key, value, (Object)null);
      } else {
         throw new NullPointerException();
      }
   }

   public Object getOrDefault(Object key, Object defaultValue) {
      Object v;
      return (v = this.get(key)) == null ? defaultValue : v;
   }

   public void forEach(BiAction action) {
      if (action == null) {
         throw new NullPointerException();
      } else {
         Node[] t;
         if ((t = this.table) != null) {
            Traverser it = new Traverser(t, t.length, 0, t.length);

            Node p;
            while((p = it.advance()) != null) {
               action.apply(p.key, p.val);
            }
         }

      }
   }

   public void replaceAll(BiFun function) {
      if (function == null) {
         throw new NullPointerException();
      } else {
         Node[] t;
         if ((t = this.table) != null) {
            Traverser it = new Traverser(t, t.length, 0, t.length);

            Node p;
            while((p = it.advance()) != null) {
               Object oldValue = p.val;
               Object key = p.key;

               while(true) {
                  Object newValue = function.apply(key, oldValue);
                  if (newValue == null) {
                     throw new NullPointerException();
                  }

                  if (this.replaceNode(key, newValue, oldValue) != null || (oldValue = this.get(key)) == null) {
                     break;
                  }
               }
            }
         }

      }
   }

   public Object computeIfAbsent(Object key, Fun mappingFunction) {
      if (key != null && mappingFunction != null) {
         int h = spread(key.hashCode());
         Object val = null;
         int binCount = 0;
         Node[] tab = this.table;

         while(true) {
            int n;
            while(tab == null || (n = tab.length) == 0) {
               tab = this.initTable();
            }

            Node f;
            int i;
            Node e;
            if ((f = tabAt(tab, i = n - 1 & h)) == null) {
               Node r = new ReservationNode();
               synchronized(r) {
                  if (casTabAt(tab, i, (Node)null, r)) {
                     binCount = 1;
                     e = null;

                     try {
                        if ((val = mappingFunction.apply(key)) != null) {
                           e = new Node(h, key, val, (Node)null);
                        }
                     } finally {
                        setTabAt(tab, i, e);
                     }
                  }
               }

               if (binCount != 0) {
                  break;
               }
            } else {
               int fh;
               if ((fh = f.hash) == -1) {
                  tab = this.helpTransfer(tab, f);
               } else {
                  boolean added = false;
                  synchronized(f) {
                     if (tabAt(tab, i) == f) {
                        if (fh < 0) {
                           if (f instanceof TreeBin) {
                              binCount = 2;
                              TreeBin t = (TreeBin)f;
                              TreeNode p;
                              TreeNode r;
                              if ((r = t.root) != null && (p = r.findTreeNode(h, key, (Class)null)) != null) {
                                 val = p.val;
                              } else if ((val = mappingFunction.apply(key)) != null) {
                                 added = true;
                                 t.putTreeVal(h, key, val);
                              }
                           }
                        } else {
                           label268: {
                              binCount = 1;

                              Object ek;
                              for(e = f; e.hash != h || (ek = e.key) != key && (ek == null || !key.equals(ek)); ++binCount) {
                                 Node pred = e;
                                 if ((e = e.next) == null) {
                                    if ((val = mappingFunction.apply(key)) != null) {
                                       added = true;
                                       pred.next = new Node(h, key, val, (Node)null);
                                    }
                                    break label268;
                                 }
                              }

                              val = e.val;
                           }
                        }
                     }
                  }

                  if (binCount != 0) {
                     if (binCount >= 8) {
                        this.treeifyBin(tab, i);
                     }

                     if (!added) {
                        return val;
                     }
                     break;
                  }
               }
            }
         }

         if (val != null) {
            this.addCount(1L, binCount);
         }

         return val;
      } else {
         throw new NullPointerException();
      }
   }

   public Object computeIfPresent(Object key, BiFun remappingFunction) {
      if (key != null && remappingFunction != null) {
         int h = spread(key.hashCode());
         Object val = null;
         int delta = 0;
         int binCount = 0;
         Node[] tab = this.table;

         while(true) {
            int n;
            while(tab == null || (n = tab.length) == 0) {
               tab = this.initTable();
            }

            Node f;
            int i;
            if ((f = tabAt(tab, i = n - 1 & h)) == null) {
               break;
            }

            int fh;
            if ((fh = f.hash) == -1) {
               tab = this.helpTransfer(tab, f);
            } else {
               synchronized(f) {
                  if (tabAt(tab, i) == f) {
                     if (fh < 0) {
                        if (f instanceof TreeBin) {
                           binCount = 2;
                           TreeBin t = (TreeBin)f;
                           TreeNode r;
                           TreeNode p;
                           if ((r = t.root) != null && (p = r.findTreeNode(h, key, (Class)null)) != null) {
                              val = remappingFunction.apply(key, p.val);
                              if (val != null) {
                                 p.val = val;
                              } else {
                                 delta = -1;
                                 if (t.removeTreeNode(p)) {
                                    setTabAt(tab, i, untreeify(t.first));
                                 }
                              }
                           }
                        }
                     } else {
                        label107: {
                           binCount = 1;
                           Node e = f;

                           Node pred;
                           Object ek;
                           for(pred = null; e.hash != h || (ek = e.key) != key && (ek == null || !key.equals(ek)); ++binCount) {
                              pred = e;
                              if ((e = e.next) == null) {
                                 break label107;
                              }
                           }

                           val = remappingFunction.apply(key, e.val);
                           if (val != null) {
                              e.val = val;
                           } else {
                              delta = -1;
                              Node en = e.next;
                              if (pred != null) {
                                 pred.next = en;
                              } else {
                                 setTabAt(tab, i, en);
                              }
                           }
                        }
                     }
                  }
               }

               if (binCount != 0) {
                  break;
               }
            }
         }

         if (delta != 0) {
            this.addCount((long)delta, binCount);
         }

         return val;
      } else {
         throw new NullPointerException();
      }
   }

   public Object compute(Object key, BiFun remappingFunction) {
      if (key != null && remappingFunction != null) {
         int h = spread(key.hashCode());
         Object val = null;
         int delta = 0;
         int binCount = 0;
         Node[] tab = this.table;

         while(true) {
            int n;
            while(tab == null || (n = tab.length) == 0) {
               tab = this.initTable();
            }

            Node f;
            int i;
            Node pred;
            if ((f = tabAt(tab, i = n - 1 & h)) == null) {
               Node r = new ReservationNode();
               synchronized(r) {
                  if (casTabAt(tab, i, (Node)null, r)) {
                     binCount = 1;
                     pred = null;

                     try {
                        if ((val = remappingFunction.apply(key, (Object)null)) != null) {
                           delta = 1;
                           pred = new Node(h, key, val, (Node)null);
                        }
                     } finally {
                        setTabAt(tab, i, pred);
                     }
                  }
               }

               if (binCount != 0) {
                  break;
               }
            } else {
               int fh;
               if ((fh = f.hash) == -1) {
                  tab = this.helpTransfer(tab, f);
               } else {
                  synchronized(f) {
                     if (tabAt(tab, i) == f) {
                        if (fh < 0) {
                           if (f instanceof TreeBin) {
                              binCount = 1;
                              TreeBin t = (TreeBin)f;
                              TreeNode r;
                              TreeNode p;
                              if ((r = t.root) != null) {
                                 p = r.findTreeNode(h, key, (Class)null);
                              } else {
                                 p = null;
                              }

                              Object pv = p == null ? null : p.val;
                              val = remappingFunction.apply(key, pv);
                              if (val != null) {
                                 if (p != null) {
                                    p.val = val;
                                 } else {
                                    delta = 1;
                                    t.putTreeVal(h, key, val);
                                 }
                              } else if (p != null) {
                                 delta = -1;
                                 if (t.removeTreeNode(p)) {
                                    setTabAt(tab, i, untreeify(t.first));
                                 }
                              }
                           }
                        } else {
                           label295: {
                              binCount = 1;
                              Node e = f;

                              Object ek;
                              for(pred = null; e.hash != h || (ek = e.key) != key && (ek == null || !key.equals(ek)); ++binCount) {
                                 pred = e;
                                 if ((e = e.next) == null) {
                                    val = remappingFunction.apply(key, (Object)null);
                                    if (val != null) {
                                       delta = 1;
                                       pred.next = new Node(h, key, val, (Node)null);
                                    }
                                    break label295;
                                 }
                              }

                              val = remappingFunction.apply(key, e.val);
                              if (val != null) {
                                 e.val = val;
                              } else {
                                 delta = -1;
                                 Node en = e.next;
                                 if (pred != null) {
                                    pred.next = en;
                                 } else {
                                    setTabAt(tab, i, en);
                                 }
                              }
                           }
                        }
                     }
                  }

                  if (binCount != 0) {
                     if (binCount >= 8) {
                        this.treeifyBin(tab, i);
                     }
                     break;
                  }
               }
            }
         }

         if (delta != 0) {
            this.addCount((long)delta, binCount);
         }

         return val;
      } else {
         throw new NullPointerException();
      }
   }

   public Object merge(Object key, Object value, BiFun remappingFunction) {
      if (key != null && value != null && remappingFunction != null) {
         int h = spread(key.hashCode());
         Object val = null;
         int delta = 0;
         int binCount = 0;
         Node[] tab = this.table;

         while(true) {
            int n;
            while(tab == null || (n = tab.length) == 0) {
               tab = this.initTable();
            }

            Node f;
            int i;
            if ((f = tabAt(tab, i = n - 1 & h)) == null) {
               if (casTabAt(tab, i, (Node)null, new Node(h, key, value, (Node)null))) {
                  delta = 1;
                  val = value;
                  break;
               }
            } else {
               int fh;
               if ((fh = f.hash) == -1) {
                  tab = this.helpTransfer(tab, f);
               } else {
                  synchronized(f) {
                     if (tabAt(tab, i) == f) {
                        if (fh < 0) {
                           if (f instanceof TreeBin) {
                              binCount = 2;
                              TreeBin t = (TreeBin)f;
                              TreeNode r = t.root;
                              TreeNode p = r == null ? null : r.findTreeNode(h, key, (Class)null);
                              val = p == null ? value : remappingFunction.apply(p.val, value);
                              if (val != null) {
                                 if (p != null) {
                                    p.val = val;
                                 } else {
                                    delta = 1;
                                    t.putTreeVal(h, key, val);
                                 }
                              } else if (p != null) {
                                 delta = -1;
                                 if (t.removeTreeNode(p)) {
                                    setTabAt(tab, i, untreeify(t.first));
                                 }
                              }
                           }
                        } else {
                           label128: {
                              binCount = 1;
                              Node e = f;

                              Node pred;
                              Object ek;
                              for(pred = null; e.hash != h || (ek = e.key) != key && (ek == null || !key.equals(ek)); ++binCount) {
                                 pred = e;
                                 if ((e = e.next) == null) {
                                    delta = 1;
                                    val = value;
                                    pred.next = new Node(h, key, value, (Node)null);
                                    break label128;
                                 }
                              }

                              val = remappingFunction.apply(e.val, value);
                              if (val != null) {
                                 e.val = val;
                              } else {
                                 delta = -1;
                                 Node en = e.next;
                                 if (pred != null) {
                                    pred.next = en;
                                 } else {
                                    setTabAt(tab, i, en);
                                 }
                              }
                           }
                        }
                     }
                  }

                  if (binCount != 0) {
                     if (binCount >= 8) {
                        this.treeifyBin(tab, i);
                     }
                     break;
                  }
               }
            }
         }

         if (delta != 0) {
            this.addCount((long)delta, binCount);
         }

         return val;
      } else {
         throw new NullPointerException();
      }
   }

   /** @deprecated */
   @Deprecated
   public boolean contains(Object value) {
      return this.containsValue(value);
   }

   public Enumeration keys() {
      Node[] t;
      int f = (t = this.table) == null ? 0 : t.length;
      return new KeyIterator(t, f, 0, f, this);
   }

   public Enumeration elements() {
      Node[] t;
      int f = (t = this.table) == null ? 0 : t.length;
      return new ValueIterator(t, f, 0, f, this);
   }

   public long mappingCount() {
      long n = this.sumCount();
      return n < 0L ? 0L : n;
   }

   public static KeySetView newKeySet() {
      return new KeySetView(new ConcurrentHashMapV8(), Boolean.TRUE);
   }

   public static KeySetView newKeySet(int initialCapacity) {
      return new KeySetView(new ConcurrentHashMapV8(initialCapacity), Boolean.TRUE);
   }

   public KeySetView keySet(Object mappedValue) {
      if (mappedValue == null) {
         throw new NullPointerException();
      } else {
         return new KeySetView(this, mappedValue);
      }
   }

   static final int resizeStamp(int n) {
      return Integer.numberOfLeadingZeros(n) | 1 << RESIZE_STAMP_BITS - 1;
   }

   private final Node[] initTable() {
      Node[] tab;
      while((tab = this.table) == null || tab.length == 0) {
         int sc;
         if ((sc = this.sizeCtl) < 0) {
            Thread.yield();
         } else if (U.compareAndSwapInt(this, SIZECTL, sc, -1)) {
            try {
               if ((tab = this.table) == null || tab.length == 0) {
                  int n = sc > 0 ? sc : 16;
                  Node[] nt = (Node[])(new Node[n]);
                  tab = nt;
                  this.table = nt;
                  sc = n - (n >>> 2);
               }
               break;
            } finally {
               this.sizeCtl = sc;
            }
         }
      }

      return tab;
   }

   private final void addCount(long x, int check) {
      CounterCell[] as;
      long b;
      long s;
      int rs;
      if ((as = this.counterCells) != null || !U.compareAndSwapLong(this, BASECOUNT, b = this.baseCount, s = b + x)) {
         boolean uncontended = true;
         CounterHashCode hc;
         CounterCell a;
         long v;
         if ((hc = (CounterHashCode)threadCounterHashCode.get()) == null || as == null || (rs = as.length - 1) < 0 || (a = as[rs & hc.code]) == null || !(uncontended = U.compareAndSwapLong(a, CELLVALUE, v = a.value, v + x))) {
            this.fullAddCount(x, hc, uncontended);
            return;
         }

         if (check <= 1) {
            return;
         }

         s = this.sumCount();
      }

      int sc;
      Node[] tab;
      int n;
      if (check >= 0) {
         for(; s >= (long)(sc = this.sizeCtl) && (tab = this.table) != null && (n = tab.length) < 1073741824; s = this.sumCount()) {
            rs = resizeStamp(n);
            if (sc < 0) {
               Node[] nt;
               if (sc >>> RESIZE_STAMP_SHIFT != rs || sc == rs + 1 || sc == rs + MAX_RESIZERS || (nt = this.nextTable) == null || this.transferIndex <= 0) {
                  break;
               }

               if (U.compareAndSwapInt(this, SIZECTL, sc, sc + 1)) {
                  this.transfer(tab, nt);
               }
            } else if (U.compareAndSwapInt(this, SIZECTL, sc, (rs << RESIZE_STAMP_SHIFT) + 2)) {
               this.transfer(tab, (Node[])null);
            }
         }
      }

   }

   final Node[] helpTransfer(Node[] tab, Node f) {
      Node[] nextTab;
      if (tab != null && f instanceof ForwardingNode && (nextTab = ((ForwardingNode)f).nextTable) != null) {
         int rs = resizeStamp(tab.length);

         int sc;
         while(nextTab == this.nextTable && this.table == tab && (sc = this.sizeCtl) < 0 && sc >>> RESIZE_STAMP_SHIFT == rs && sc != rs + 1 && sc != rs + MAX_RESIZERS && this.transferIndex > 0) {
            if (U.compareAndSwapInt(this, SIZECTL, sc, sc + 1)) {
               this.transfer(tab, nextTab);
               break;
            }
         }

         return nextTab;
      } else {
         return this.table;
      }
   }

   private final void tryPresize(int size) {
      int c = size >= 536870912 ? 1073741824 : tableSizeFor(size + (size >>> 1) + 1);

      int sc;
      while((sc = this.sizeCtl) >= 0) {
         Node[] tab = this.table;
         int n;
         if (tab != null && (n = tab.length) != 0) {
            if (c <= sc || n >= 1073741824) {
               break;
            }

            if (tab == this.table) {
               int rs = resizeStamp(n);
               if (sc < 0) {
                  Node[] nt;
                  if (sc >>> RESIZE_STAMP_SHIFT != rs || sc == rs + 1 || sc == rs + MAX_RESIZERS || (nt = this.nextTable) == null || this.transferIndex <= 0) {
                     break;
                  }

                  if (U.compareAndSwapInt(this, SIZECTL, sc, sc + 1)) {
                     this.transfer(tab, nt);
                  }
               } else if (U.compareAndSwapInt(this, SIZECTL, sc, (rs << RESIZE_STAMP_SHIFT) + 2)) {
                  this.transfer(tab, (Node[])null);
               }
            }
         } else {
            n = sc > c ? sc : c;
            if (U.compareAndSwapInt(this, SIZECTL, sc, -1)) {
               try {
                  if (this.table == tab) {
                     Node[] nt = (Node[])(new Node[n]);
                     this.table = nt;
                     sc = n - (n >>> 2);
                  }
               } finally {
                  this.sizeCtl = sc;
               }
            }
         }
      }

   }

   private final void transfer(Node[] tab, Node[] nextTab) {
      int n = tab.length;
      int stride;
      if ((stride = NCPU > 1 ? (n >>> 3) / NCPU : n) < 16) {
         stride = 16;
      }

      if (nextTab == null) {
         try {
            Node[] nt = (Node[])(new Node[n << 1]);
            nextTab = nt;
         } catch (Throwable var27) {
            this.sizeCtl = Integer.MAX_VALUE;
            return;
         }

         this.nextTable = nextTab;
         this.transferIndex = n;
      }

      int nextn = nextTab.length;
      ForwardingNode fwd = new ForwardingNode(nextTab);
      boolean advance = true;
      boolean finishing = false;
      int i = 0;
      int bound = 0;

      while(true) {
         while(true) {
            int sc;
            while(!advance) {
               if (i >= 0 && i < n && i + n < nextn) {
                  Node f;
                  if ((f = tabAt(tab, i)) == null) {
                     advance = casTabAt(tab, i, (Node)null, fwd);
                  } else {
                     int fh;
                     if ((fh = f.hash) == -1) {
                        advance = true;
                     } else {
                        synchronized(f) {
                           if (tabAt(tab, i) == f) {
                              if (fh >= 0) {
                                 int runBit = fh & n;
                                 Node lastRun = f;

                                 Node p;
                                 int ph;
                                 for(p = f.next; p != null; p = p.next) {
                                    ph = p.hash & n;
                                    if (ph != runBit) {
                                       runBit = ph;
                                       lastRun = p;
                                    }
                                 }

                                 Node ln;
                                 Node hn;
                                 if (runBit == 0) {
                                    ln = lastRun;
                                    hn = null;
                                 } else {
                                    hn = lastRun;
                                    ln = null;
                                 }

                                 for(p = f; p != lastRun; p = p.next) {
                                    ph = p.hash;
                                    Object pk = p.key;
                                    Object pv = p.val;
                                    if ((ph & n) == 0) {
                                       ln = new Node(ph, pk, pv, ln);
                                    } else {
                                       hn = new Node(ph, pk, pv, hn);
                                    }
                                 }

                                 setTabAt(nextTab, i, ln);
                                 setTabAt(nextTab, i + n, hn);
                                 setTabAt(tab, i, fwd);
                                 advance = true;
                              } else if (f instanceof TreeBin) {
                                 TreeBin t = (TreeBin)f;
                                 TreeNode lo = null;
                                 TreeNode loTail = null;
                                 TreeNode hi = null;
                                 TreeNode hiTail = null;
                                 int lc = 0;
                                 int hc = 0;

                                 for(Node e = t.first; e != null; e = ((Node)e).next) {
                                    int h = ((Node)e).hash;
                                    TreeNode p = new TreeNode(h, ((Node)e).key, ((Node)e).val, (Node)null, (TreeNode)null);
                                    if ((h & n) == 0) {
                                       if ((p.prev = loTail) == null) {
                                          lo = p;
                                       } else {
                                          loTail.next = p;
                                       }

                                       loTail = p;
                                       ++lc;
                                    } else {
                                       if ((p.prev = hiTail) == null) {
                                          hi = p;
                                       } else {
                                          hiTail.next = p;
                                       }

                                       hiTail = p;
                                       ++hc;
                                    }
                                 }

                                 Node ln = lc <= 6 ? untreeify(lo) : (hc != 0 ? new TreeBin(lo) : t);
                                 Node hn = hc <= 6 ? untreeify(hi) : (lc != 0 ? new TreeBin(hi) : t);
                                 setTabAt(nextTab, i, (Node)ln);
                                 setTabAt(nextTab, i + n, (Node)hn);
                                 setTabAt(tab, i, fwd);
                                 advance = true;
                              }
                           }
                        }
                     }
                  }
               } else {
                  if (finishing) {
                     this.nextTable = null;
                     this.table = nextTab;
                     this.sizeCtl = (n << 1) - (n >>> 1);
                     return;
                  }

                  if (U.compareAndSwapInt(this, SIZECTL, sc = this.sizeCtl, sc - 1)) {
                     if (sc - 2 != resizeStamp(n) << RESIZE_STAMP_SHIFT) {
                        return;
                     }

                     advance = true;
                     finishing = true;
                     i = n;
                  }
               }
            }

            --i;
            if (i < bound && !finishing) {
               if ((sc = this.transferIndex) <= 0) {
                  i = -1;
                  advance = false;
               } else {
                  int nextBound;
                  if (U.compareAndSwapInt(this, TRANSFERINDEX, sc, nextBound = sc > stride ? sc - stride : 0)) {
                     bound = nextBound;
                     i = sc - 1;
                     advance = false;
                  }
               }
            } else {
               advance = false;
            }
         }
      }
   }

   private final void treeifyBin(Node[] tab, int index) {
      if (tab != null) {
         int n;
         if ((n = tab.length) < 64) {
            this.tryPresize(n << 1);
         } else {
            Node b;
            if ((b = tabAt(tab, index)) != null && b.hash >= 0) {
               synchronized(b) {
                  if (tabAt(tab, index) == b) {
                     TreeNode hd = null;
                     TreeNode tl = null;

                     for(Node e = b; e != null; e = e.next) {
                        TreeNode p = new TreeNode(e.hash, e.key, e.val, (Node)null, (TreeNode)null);
                        if ((p.prev = tl) == null) {
                           hd = p;
                        } else {
                           tl.next = p;
                        }

                        tl = p;
                     }

                     setTabAt(tab, index, new TreeBin(hd));
                  }
               }
            }
         }
      }

   }

   static Node untreeify(Node b) {
      Node hd = null;
      Node tl = null;

      for(Node q = b; q != null; q = q.next) {
         Node p = new Node(q.hash, q.key, q.val, (Node)null);
         if (tl == null) {
            hd = p;
         } else {
            tl.next = p;
         }

         tl = p;
      }

      return hd;
   }

   final int batchFor(long b) {
      long n;
      if (b != Long.MAX_VALUE && (n = this.sumCount()) > 1L && n >= b) {
         int sp = ForkJoinPool.getCommonPoolParallelism() << 2;
         return b > 0L && (n /= b) < (long)sp ? (int)n : sp;
      } else {
         return 0;
      }
   }

   public void forEach(long parallelismThreshold, BiAction action) {
      if (action == null) {
         throw new NullPointerException();
      } else {
         (new ForEachMappingTask((BulkTask)null, this.batchFor(parallelismThreshold), 0, 0, this.table, action)).invoke();
      }
   }

   public void forEach(long parallelismThreshold, BiFun transformer, Action action) {
      if (transformer != null && action != null) {
         (new ForEachTransformedMappingTask((BulkTask)null, this.batchFor(parallelismThreshold), 0, 0, this.table, transformer, action)).invoke();
      } else {
         throw new NullPointerException();
      }
   }

   public Object search(long parallelismThreshold, BiFun searchFunction) {
      if (searchFunction == null) {
         throw new NullPointerException();
      } else {
         return (new SearchMappingsTask((BulkTask)null, this.batchFor(parallelismThreshold), 0, 0, this.table, searchFunction, new AtomicReference())).invoke();
      }
   }

   public Object reduce(long parallelismThreshold, BiFun transformer, BiFun reducer) {
      if (transformer != null && reducer != null) {
         return (new MapReduceMappingsTask((BulkTask)null, this.batchFor(parallelismThreshold), 0, 0, this.table, (MapReduceMappingsTask)null, transformer, reducer)).invoke();
      } else {
         throw new NullPointerException();
      }
   }

   public double reduceToDouble(long parallelismThreshold, ObjectByObjectToDouble transformer, double basis, DoubleByDoubleToDouble reducer) {
      if (transformer != null && reducer != null) {
         return (Double)(new MapReduceMappingsToDoubleTask((BulkTask)null, this.batchFor(parallelismThreshold), 0, 0, this.table, (MapReduceMappingsToDoubleTask)null, transformer, basis, reducer)).invoke();
      } else {
         throw new NullPointerException();
      }
   }

   public long reduceToLong(long parallelismThreshold, ObjectByObjectToLong transformer, long basis, LongByLongToLong reducer) {
      if (transformer != null && reducer != null) {
         return (Long)(new MapReduceMappingsToLongTask((BulkTask)null, this.batchFor(parallelismThreshold), 0, 0, this.table, (MapReduceMappingsToLongTask)null, transformer, basis, reducer)).invoke();
      } else {
         throw new NullPointerException();
      }
   }

   public int reduceToInt(long parallelismThreshold, ObjectByObjectToInt transformer, int basis, IntByIntToInt reducer) {
      if (transformer != null && reducer != null) {
         return (Integer)(new MapReduceMappingsToIntTask((BulkTask)null, this.batchFor(parallelismThreshold), 0, 0, this.table, (MapReduceMappingsToIntTask)null, transformer, basis, reducer)).invoke();
      } else {
         throw new NullPointerException();
      }
   }

   public void forEachKey(long parallelismThreshold, Action action) {
      if (action == null) {
         throw new NullPointerException();
      } else {
         (new ForEachKeyTask((BulkTask)null, this.batchFor(parallelismThreshold), 0, 0, this.table, action)).invoke();
      }
   }

   public void forEachKey(long parallelismThreshold, Fun transformer, Action action) {
      if (transformer != null && action != null) {
         (new ForEachTransformedKeyTask((BulkTask)null, this.batchFor(parallelismThreshold), 0, 0, this.table, transformer, action)).invoke();
      } else {
         throw new NullPointerException();
      }
   }

   public Object searchKeys(long parallelismThreshold, Fun searchFunction) {
      if (searchFunction == null) {
         throw new NullPointerException();
      } else {
         return (new SearchKeysTask((BulkTask)null, this.batchFor(parallelismThreshold), 0, 0, this.table, searchFunction, new AtomicReference())).invoke();
      }
   }

   public Object reduceKeys(long parallelismThreshold, BiFun reducer) {
      if (reducer == null) {
         throw new NullPointerException();
      } else {
         return (new ReduceKeysTask((BulkTask)null, this.batchFor(parallelismThreshold), 0, 0, this.table, (ReduceKeysTask)null, reducer)).invoke();
      }
   }

   public Object reduceKeys(long parallelismThreshold, Fun transformer, BiFun reducer) {
      if (transformer != null && reducer != null) {
         return (new MapReduceKeysTask((BulkTask)null, this.batchFor(parallelismThreshold), 0, 0, this.table, (MapReduceKeysTask)null, transformer, reducer)).invoke();
      } else {
         throw new NullPointerException();
      }
   }

   public double reduceKeysToDouble(long parallelismThreshold, ObjectToDouble transformer, double basis, DoubleByDoubleToDouble reducer) {
      if (transformer != null && reducer != null) {
         return (Double)(new MapReduceKeysToDoubleTask((BulkTask)null, this.batchFor(parallelismThreshold), 0, 0, this.table, (MapReduceKeysToDoubleTask)null, transformer, basis, reducer)).invoke();
      } else {
         throw new NullPointerException();
      }
   }

   public long reduceKeysToLong(long parallelismThreshold, ObjectToLong transformer, long basis, LongByLongToLong reducer) {
      if (transformer != null && reducer != null) {
         return (Long)(new MapReduceKeysToLongTask((BulkTask)null, this.batchFor(parallelismThreshold), 0, 0, this.table, (MapReduceKeysToLongTask)null, transformer, basis, reducer)).invoke();
      } else {
         throw new NullPointerException();
      }
   }

   public int reduceKeysToInt(long parallelismThreshold, ObjectToInt transformer, int basis, IntByIntToInt reducer) {
      if (transformer != null && reducer != null) {
         return (Integer)(new MapReduceKeysToIntTask((BulkTask)null, this.batchFor(parallelismThreshold), 0, 0, this.table, (MapReduceKeysToIntTask)null, transformer, basis, reducer)).invoke();
      } else {
         throw new NullPointerException();
      }
   }

   public void forEachValue(long parallelismThreshold, Action action) {
      if (action == null) {
         throw new NullPointerException();
      } else {
         (new ForEachValueTask((BulkTask)null, this.batchFor(parallelismThreshold), 0, 0, this.table, action)).invoke();
      }
   }

   public void forEachValue(long parallelismThreshold, Fun transformer, Action action) {
      if (transformer != null && action != null) {
         (new ForEachTransformedValueTask((BulkTask)null, this.batchFor(parallelismThreshold), 0, 0, this.table, transformer, action)).invoke();
      } else {
         throw new NullPointerException();
      }
   }

   public Object searchValues(long parallelismThreshold, Fun searchFunction) {
      if (searchFunction == null) {
         throw new NullPointerException();
      } else {
         return (new SearchValuesTask((BulkTask)null, this.batchFor(parallelismThreshold), 0, 0, this.table, searchFunction, new AtomicReference())).invoke();
      }
   }

   public Object reduceValues(long parallelismThreshold, BiFun reducer) {
      if (reducer == null) {
         throw new NullPointerException();
      } else {
         return (new ReduceValuesTask((BulkTask)null, this.batchFor(parallelismThreshold), 0, 0, this.table, (ReduceValuesTask)null, reducer)).invoke();
      }
   }

   public Object reduceValues(long parallelismThreshold, Fun transformer, BiFun reducer) {
      if (transformer != null && reducer != null) {
         return (new MapReduceValuesTask((BulkTask)null, this.batchFor(parallelismThreshold), 0, 0, this.table, (MapReduceValuesTask)null, transformer, reducer)).invoke();
      } else {
         throw new NullPointerException();
      }
   }

   public double reduceValuesToDouble(long parallelismThreshold, ObjectToDouble transformer, double basis, DoubleByDoubleToDouble reducer) {
      if (transformer != null && reducer != null) {
         return (Double)(new MapReduceValuesToDoubleTask((BulkTask)null, this.batchFor(parallelismThreshold), 0, 0, this.table, (MapReduceValuesToDoubleTask)null, transformer, basis, reducer)).invoke();
      } else {
         throw new NullPointerException();
      }
   }

   public long reduceValuesToLong(long parallelismThreshold, ObjectToLong transformer, long basis, LongByLongToLong reducer) {
      if (transformer != null && reducer != null) {
         return (Long)(new MapReduceValuesToLongTask((BulkTask)null, this.batchFor(parallelismThreshold), 0, 0, this.table, (MapReduceValuesToLongTask)null, transformer, basis, reducer)).invoke();
      } else {
         throw new NullPointerException();
      }
   }

   public int reduceValuesToInt(long parallelismThreshold, ObjectToInt transformer, int basis, IntByIntToInt reducer) {
      if (transformer != null && reducer != null) {
         return (Integer)(new MapReduceValuesToIntTask((BulkTask)null, this.batchFor(parallelismThreshold), 0, 0, this.table, (MapReduceValuesToIntTask)null, transformer, basis, reducer)).invoke();
      } else {
         throw new NullPointerException();
      }
   }

   public void forEachEntry(long parallelismThreshold, Action action) {
      if (action == null) {
         throw new NullPointerException();
      } else {
         (new ForEachEntryTask((BulkTask)null, this.batchFor(parallelismThreshold), 0, 0, this.table, action)).invoke();
      }
   }

   public void forEachEntry(long parallelismThreshold, Fun transformer, Action action) {
      if (transformer != null && action != null) {
         (new ForEachTransformedEntryTask((BulkTask)null, this.batchFor(parallelismThreshold), 0, 0, this.table, transformer, action)).invoke();
      } else {
         throw new NullPointerException();
      }
   }

   public Object searchEntries(long parallelismThreshold, Fun searchFunction) {
      if (searchFunction == null) {
         throw new NullPointerException();
      } else {
         return (new SearchEntriesTask((BulkTask)null, this.batchFor(parallelismThreshold), 0, 0, this.table, searchFunction, new AtomicReference())).invoke();
      }
   }

   public Map.Entry reduceEntries(long parallelismThreshold, BiFun reducer) {
      if (reducer == null) {
         throw new NullPointerException();
      } else {
         return (Map.Entry)(new ReduceEntriesTask((BulkTask)null, this.batchFor(parallelismThreshold), 0, 0, this.table, (ReduceEntriesTask)null, reducer)).invoke();
      }
   }

   public Object reduceEntries(long parallelismThreshold, Fun transformer, BiFun reducer) {
      if (transformer != null && reducer != null) {
         return (new MapReduceEntriesTask((BulkTask)null, this.batchFor(parallelismThreshold), 0, 0, this.table, (MapReduceEntriesTask)null, transformer, reducer)).invoke();
      } else {
         throw new NullPointerException();
      }
   }

   public double reduceEntriesToDouble(long parallelismThreshold, ObjectToDouble transformer, double basis, DoubleByDoubleToDouble reducer) {
      if (transformer != null && reducer != null) {
         return (Double)(new MapReduceEntriesToDoubleTask((BulkTask)null, this.batchFor(parallelismThreshold), 0, 0, this.table, (MapReduceEntriesToDoubleTask)null, transformer, basis, reducer)).invoke();
      } else {
         throw new NullPointerException();
      }
   }

   public long reduceEntriesToLong(long parallelismThreshold, ObjectToLong transformer, long basis, LongByLongToLong reducer) {
      if (transformer != null && reducer != null) {
         return (Long)(new MapReduceEntriesToLongTask((BulkTask)null, this.batchFor(parallelismThreshold), 0, 0, this.table, (MapReduceEntriesToLongTask)null, transformer, basis, reducer)).invoke();
      } else {
         throw new NullPointerException();
      }
   }

   public int reduceEntriesToInt(long parallelismThreshold, ObjectToInt transformer, int basis, IntByIntToInt reducer) {
      if (transformer != null && reducer != null) {
         return (Integer)(new MapReduceEntriesToIntTask((BulkTask)null, this.batchFor(parallelismThreshold), 0, 0, this.table, (MapReduceEntriesToIntTask)null, transformer, basis, reducer)).invoke();
      } else {
         throw new NullPointerException();
      }
   }

   final long sumCount() {
      CounterCell[] as = this.counterCells;
      long sum = this.baseCount;
      if (as != null) {
         for(int i = 0; i < as.length; ++i) {
            CounterCell a;
            if ((a = as[i]) != null) {
               sum += a.value;
            }
         }
      }

      return sum;
   }

   private final void fullAddCount(long x, CounterHashCode hc, boolean wasUncontended) {
      int h;
      if (hc == null) {
         hc = new CounterHashCode();
         int s = counterHashCodeGenerator.addAndGet(1640531527);
         h = hc.code = s == 0 ? 1 : s;
         threadCounterHashCode.set(hc);
      } else {
         h = hc.code;
      }

      boolean collide = false;

      while(true) {
         CounterCell[] as;
         int n;
         long v;
         if ((as = this.counterCells) != null && (n = as.length) > 0) {
            CounterCell a;
            if ((a = as[n - 1 & h]) == null) {
               if (this.cellsBusy == 0) {
                  CounterCell r = new CounterCell(x);
                  if (this.cellsBusy == 0 && U.compareAndSwapInt(this, CELLSBUSY, 0, 1)) {
                     boolean created = false;

                     try {
                        CounterCell[] rs;
                        int m;
                        int j;
                        if ((rs = this.counterCells) != null && (m = rs.length) > 0 && rs[j = m - 1 & h] == null) {
                           rs[j] = r;
                           created = true;
                        }
                     } finally {
                        this.cellsBusy = 0;
                     }

                     if (created) {
                        break;
                     }
                     continue;
                  }
               }

               collide = false;
            } else if (!wasUncontended) {
               wasUncontended = true;
            } else {
               if (U.compareAndSwapLong(a, CELLVALUE, v = a.value, v + x)) {
                  break;
               }

               if (this.counterCells == as && n < NCPU) {
                  if (!collide) {
                     collide = true;
                  } else if (this.cellsBusy == 0 && U.compareAndSwapInt(this, CELLSBUSY, 0, 1)) {
                     try {
                        if (this.counterCells == as) {
                           CounterCell[] rs = new CounterCell[n << 1];

                           for(int i = 0; i < n; ++i) {
                              rs[i] = as[i];
                           }

                           this.counterCells = rs;
                        }
                     } finally {
                        this.cellsBusy = 0;
                     }

                     collide = false;
                     continue;
                  }
               } else {
                  collide = false;
               }
            }

            h ^= h << 13;
            h ^= h >>> 17;
            h ^= h << 5;
         } else if (this.cellsBusy == 0 && this.counterCells == as && U.compareAndSwapInt(this, CELLSBUSY, 0, 1)) {
            boolean init = false;

            try {
               if (this.counterCells == as) {
                  CounterCell[] rs = new CounterCell[2];
                  rs[h & 1] = new CounterCell(x);
                  this.counterCells = rs;
                  init = true;
               }
            } finally {
               this.cellsBusy = 0;
            }

            if (init) {
               break;
            }
         } else if (U.compareAndSwapLong(this, BASECOUNT, v = this.baseCount, v + x)) {
            break;
         }
      }

      hc.code = h;
   }

   private static Unsafe getUnsafe() {
      try {
         return Unsafe.getUnsafe();
      } catch (SecurityException var2) {
         try {
            return (Unsafe)AccessController.doPrivileged(new PrivilegedExceptionAction() {
               public Unsafe run() throws Exception {
                  Class k = Unsafe.class;
                  Field[] arr$ = k.getDeclaredFields();
                  int len$ = arr$.length;

                  for(int i$ = 0; i$ < len$; ++i$) {
                     Field f = arr$[i$];
                     f.setAccessible(true);
                     Object x = f.get((Object)null);
                     if (k.isInstance(x)) {
                        return (Unsafe)k.cast(x);
                     }
                  }

                  throw new NoSuchFieldError("the Unsafe");
               }
            });
         } catch (PrivilegedActionException var1) {
            throw new RuntimeException("Could not initialize intrinsics", var1.getCause());
         }
      }
   }

   static {
      MAX_RESIZERS = (1 << 32 - RESIZE_STAMP_BITS) - 1;
      RESIZE_STAMP_SHIFT = 32 - RESIZE_STAMP_BITS;
      NCPU = Runtime.getRuntime().availableProcessors();
      serialPersistentFields = new ObjectStreamField[]{new ObjectStreamField("segments", Segment[].class), new ObjectStreamField("segmentMask", Integer.TYPE), new ObjectStreamField("segmentShift", Integer.TYPE)};
      counterHashCodeGenerator = new AtomicInteger();
      threadCounterHashCode = new ThreadLocal();

      try {
         U = getUnsafe();
         Class k = ConcurrentHashMapV8.class;
         SIZECTL = U.objectFieldOffset(k.getDeclaredField("sizeCtl"));
         TRANSFERINDEX = U.objectFieldOffset(k.getDeclaredField("transferIndex"));
         BASECOUNT = U.objectFieldOffset(k.getDeclaredField("baseCount"));
         CELLSBUSY = U.objectFieldOffset(k.getDeclaredField("cellsBusy"));
         Class ck = CounterCell.class;
         CELLVALUE = U.objectFieldOffset(ck.getDeclaredField("value"));
         Class ak = Node[].class;
         ABASE = (long)U.arrayBaseOffset(ak);
         int scale = U.arrayIndexScale(ak);
         if ((scale & scale - 1) != 0) {
            throw new Error("data type scale not a power of two");
         } else {
            ASHIFT = 31 - Integer.numberOfLeadingZeros(scale);
         }
      } catch (Exception var4) {
         throw new Error(var4);
      }
   }

   static final class CounterHashCode {
      int code;
   }

   static final class CounterCell {
      volatile long p0;
      volatile long p1;
      volatile long p2;
      volatile long p3;
      volatile long p4;
      volatile long p5;
      volatile long p6;
      volatile long value;
      volatile long q0;
      volatile long q1;
      volatile long q2;
      volatile long q3;
      volatile long q4;
      volatile long q5;
      volatile long q6;

      CounterCell(long x) {
         this.value = x;
      }
   }

   static final class MapReduceMappingsToIntTask extends BulkTask {
      final ObjectByObjectToInt transformer;
      final IntByIntToInt reducer;
      final int basis;
      int result;
      MapReduceMappingsToIntTask rights;
      MapReduceMappingsToIntTask nextRight;

      MapReduceMappingsToIntTask(BulkTask p, int b, int i, int f, Node[] t, MapReduceMappingsToIntTask nextRight, ObjectByObjectToInt transformer, int basis, IntByIntToInt reducer) {
         super(p, b, i, f, t);
         this.nextRight = nextRight;
         this.transformer = transformer;
         this.basis = basis;
         this.reducer = reducer;
      }

      public final Integer getRawResult() {
         return this.result;
      }

      public final void compute() {
         ObjectByObjectToInt transformer;
         IntByIntToInt reducer;
         if ((transformer = this.transformer) != null && (reducer = this.reducer) != null) {
            int r = this.basis;
            int i = this.baseIndex;

            int f;
            int h;
            while(this.batch > 0 && (h = (f = this.baseLimit) + i >>> 1) > i) {
               this.addToPendingCount(1);
               (this.rights = new MapReduceMappingsToIntTask(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, this.rights, transformer, r, reducer)).fork();
            }

            Node p;
            while((p = this.advance()) != null) {
               r = reducer.apply(r, transformer.apply(p.key, p.val));
            }

            this.result = r;

            for(CountedCompleter c = this.firstComplete(); c != null; c = c.nextComplete()) {
               MapReduceMappingsToIntTask t = (MapReduceMappingsToIntTask)c;

               for(MapReduceMappingsToIntTask s = t.rights; s != null; s = t.rights = s.nextRight) {
                  t.result = reducer.apply(t.result, s.result);
               }
            }
         }

      }
   }

   static final class MapReduceEntriesToIntTask extends BulkTask {
      final ObjectToInt transformer;
      final IntByIntToInt reducer;
      final int basis;
      int result;
      MapReduceEntriesToIntTask rights;
      MapReduceEntriesToIntTask nextRight;

      MapReduceEntriesToIntTask(BulkTask p, int b, int i, int f, Node[] t, MapReduceEntriesToIntTask nextRight, ObjectToInt transformer, int basis, IntByIntToInt reducer) {
         super(p, b, i, f, t);
         this.nextRight = nextRight;
         this.transformer = transformer;
         this.basis = basis;
         this.reducer = reducer;
      }

      public final Integer getRawResult() {
         return this.result;
      }

      public final void compute() {
         ObjectToInt transformer;
         IntByIntToInt reducer;
         if ((transformer = this.transformer) != null && (reducer = this.reducer) != null) {
            int r = this.basis;
            int i = this.baseIndex;

            int f;
            int h;
            while(this.batch > 0 && (h = (f = this.baseLimit) + i >>> 1) > i) {
               this.addToPendingCount(1);
               (this.rights = new MapReduceEntriesToIntTask(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, this.rights, transformer, r, reducer)).fork();
            }

            Node p;
            while((p = this.advance()) != null) {
               r = reducer.apply(r, transformer.apply(p));
            }

            this.result = r;

            for(CountedCompleter c = this.firstComplete(); c != null; c = c.nextComplete()) {
               MapReduceEntriesToIntTask t = (MapReduceEntriesToIntTask)c;

               for(MapReduceEntriesToIntTask s = t.rights; s != null; s = t.rights = s.nextRight) {
                  t.result = reducer.apply(t.result, s.result);
               }
            }
         }

      }
   }

   static final class MapReduceValuesToIntTask extends BulkTask {
      final ObjectToInt transformer;
      final IntByIntToInt reducer;
      final int basis;
      int result;
      MapReduceValuesToIntTask rights;
      MapReduceValuesToIntTask nextRight;

      MapReduceValuesToIntTask(BulkTask p, int b, int i, int f, Node[] t, MapReduceValuesToIntTask nextRight, ObjectToInt transformer, int basis, IntByIntToInt reducer) {
         super(p, b, i, f, t);
         this.nextRight = nextRight;
         this.transformer = transformer;
         this.basis = basis;
         this.reducer = reducer;
      }

      public final Integer getRawResult() {
         return this.result;
      }

      public final void compute() {
         ObjectToInt transformer;
         IntByIntToInt reducer;
         if ((transformer = this.transformer) != null && (reducer = this.reducer) != null) {
            int r = this.basis;
            int i = this.baseIndex;

            int f;
            int h;
            while(this.batch > 0 && (h = (f = this.baseLimit) + i >>> 1) > i) {
               this.addToPendingCount(1);
               (this.rights = new MapReduceValuesToIntTask(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, this.rights, transformer, r, reducer)).fork();
            }

            Node p;
            while((p = this.advance()) != null) {
               r = reducer.apply(r, transformer.apply(p.val));
            }

            this.result = r;

            for(CountedCompleter c = this.firstComplete(); c != null; c = c.nextComplete()) {
               MapReduceValuesToIntTask t = (MapReduceValuesToIntTask)c;

               for(MapReduceValuesToIntTask s = t.rights; s != null; s = t.rights = s.nextRight) {
                  t.result = reducer.apply(t.result, s.result);
               }
            }
         }

      }
   }

   static final class MapReduceKeysToIntTask extends BulkTask {
      final ObjectToInt transformer;
      final IntByIntToInt reducer;
      final int basis;
      int result;
      MapReduceKeysToIntTask rights;
      MapReduceKeysToIntTask nextRight;

      MapReduceKeysToIntTask(BulkTask p, int b, int i, int f, Node[] t, MapReduceKeysToIntTask nextRight, ObjectToInt transformer, int basis, IntByIntToInt reducer) {
         super(p, b, i, f, t);
         this.nextRight = nextRight;
         this.transformer = transformer;
         this.basis = basis;
         this.reducer = reducer;
      }

      public final Integer getRawResult() {
         return this.result;
      }

      public final void compute() {
         ObjectToInt transformer;
         IntByIntToInt reducer;
         if ((transformer = this.transformer) != null && (reducer = this.reducer) != null) {
            int r = this.basis;
            int i = this.baseIndex;

            int f;
            int h;
            while(this.batch > 0 && (h = (f = this.baseLimit) + i >>> 1) > i) {
               this.addToPendingCount(1);
               (this.rights = new MapReduceKeysToIntTask(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, this.rights, transformer, r, reducer)).fork();
            }

            Node p;
            while((p = this.advance()) != null) {
               r = reducer.apply(r, transformer.apply(p.key));
            }

            this.result = r;

            for(CountedCompleter c = this.firstComplete(); c != null; c = c.nextComplete()) {
               MapReduceKeysToIntTask t = (MapReduceKeysToIntTask)c;

               for(MapReduceKeysToIntTask s = t.rights; s != null; s = t.rights = s.nextRight) {
                  t.result = reducer.apply(t.result, s.result);
               }
            }
         }

      }
   }

   static final class MapReduceMappingsToLongTask extends BulkTask {
      final ObjectByObjectToLong transformer;
      final LongByLongToLong reducer;
      final long basis;
      long result;
      MapReduceMappingsToLongTask rights;
      MapReduceMappingsToLongTask nextRight;

      MapReduceMappingsToLongTask(BulkTask p, int b, int i, int f, Node[] t, MapReduceMappingsToLongTask nextRight, ObjectByObjectToLong transformer, long basis, LongByLongToLong reducer) {
         super(p, b, i, f, t);
         this.nextRight = nextRight;
         this.transformer = transformer;
         this.basis = basis;
         this.reducer = reducer;
      }

      public final Long getRawResult() {
         return this.result;
      }

      public final void compute() {
         ObjectByObjectToLong transformer;
         LongByLongToLong reducer;
         if ((transformer = this.transformer) != null && (reducer = this.reducer) != null) {
            long r = this.basis;
            int i = this.baseIndex;

            int f;
            int h;
            while(this.batch > 0 && (h = (f = this.baseLimit) + i >>> 1) > i) {
               this.addToPendingCount(1);
               (this.rights = new MapReduceMappingsToLongTask(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, this.rights, transformer, r, reducer)).fork();
            }

            Node p;
            while((p = this.advance()) != null) {
               r = reducer.apply(r, transformer.apply(p.key, p.val));
            }

            this.result = r;

            for(CountedCompleter c = this.firstComplete(); c != null; c = c.nextComplete()) {
               MapReduceMappingsToLongTask t = (MapReduceMappingsToLongTask)c;

               for(MapReduceMappingsToLongTask s = t.rights; s != null; s = t.rights = s.nextRight) {
                  t.result = reducer.apply(t.result, s.result);
               }
            }
         }

      }
   }

   static final class MapReduceEntriesToLongTask extends BulkTask {
      final ObjectToLong transformer;
      final LongByLongToLong reducer;
      final long basis;
      long result;
      MapReduceEntriesToLongTask rights;
      MapReduceEntriesToLongTask nextRight;

      MapReduceEntriesToLongTask(BulkTask p, int b, int i, int f, Node[] t, MapReduceEntriesToLongTask nextRight, ObjectToLong transformer, long basis, LongByLongToLong reducer) {
         super(p, b, i, f, t);
         this.nextRight = nextRight;
         this.transformer = transformer;
         this.basis = basis;
         this.reducer = reducer;
      }

      public final Long getRawResult() {
         return this.result;
      }

      public final void compute() {
         ObjectToLong transformer;
         LongByLongToLong reducer;
         if ((transformer = this.transformer) != null && (reducer = this.reducer) != null) {
            long r = this.basis;
            int i = this.baseIndex;

            int f;
            int h;
            while(this.batch > 0 && (h = (f = this.baseLimit) + i >>> 1) > i) {
               this.addToPendingCount(1);
               (this.rights = new MapReduceEntriesToLongTask(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, this.rights, transformer, r, reducer)).fork();
            }

            Node p;
            while((p = this.advance()) != null) {
               r = reducer.apply(r, transformer.apply(p));
            }

            this.result = r;

            for(CountedCompleter c = this.firstComplete(); c != null; c = c.nextComplete()) {
               MapReduceEntriesToLongTask t = (MapReduceEntriesToLongTask)c;

               for(MapReduceEntriesToLongTask s = t.rights; s != null; s = t.rights = s.nextRight) {
                  t.result = reducer.apply(t.result, s.result);
               }
            }
         }

      }
   }

   static final class MapReduceValuesToLongTask extends BulkTask {
      final ObjectToLong transformer;
      final LongByLongToLong reducer;
      final long basis;
      long result;
      MapReduceValuesToLongTask rights;
      MapReduceValuesToLongTask nextRight;

      MapReduceValuesToLongTask(BulkTask p, int b, int i, int f, Node[] t, MapReduceValuesToLongTask nextRight, ObjectToLong transformer, long basis, LongByLongToLong reducer) {
         super(p, b, i, f, t);
         this.nextRight = nextRight;
         this.transformer = transformer;
         this.basis = basis;
         this.reducer = reducer;
      }

      public final Long getRawResult() {
         return this.result;
      }

      public final void compute() {
         ObjectToLong transformer;
         LongByLongToLong reducer;
         if ((transformer = this.transformer) != null && (reducer = this.reducer) != null) {
            long r = this.basis;
            int i = this.baseIndex;

            int f;
            int h;
            while(this.batch > 0 && (h = (f = this.baseLimit) + i >>> 1) > i) {
               this.addToPendingCount(1);
               (this.rights = new MapReduceValuesToLongTask(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, this.rights, transformer, r, reducer)).fork();
            }

            Node p;
            while((p = this.advance()) != null) {
               r = reducer.apply(r, transformer.apply(p.val));
            }

            this.result = r;

            for(CountedCompleter c = this.firstComplete(); c != null; c = c.nextComplete()) {
               MapReduceValuesToLongTask t = (MapReduceValuesToLongTask)c;

               for(MapReduceValuesToLongTask s = t.rights; s != null; s = t.rights = s.nextRight) {
                  t.result = reducer.apply(t.result, s.result);
               }
            }
         }

      }
   }

   static final class MapReduceKeysToLongTask extends BulkTask {
      final ObjectToLong transformer;
      final LongByLongToLong reducer;
      final long basis;
      long result;
      MapReduceKeysToLongTask rights;
      MapReduceKeysToLongTask nextRight;

      MapReduceKeysToLongTask(BulkTask p, int b, int i, int f, Node[] t, MapReduceKeysToLongTask nextRight, ObjectToLong transformer, long basis, LongByLongToLong reducer) {
         super(p, b, i, f, t);
         this.nextRight = nextRight;
         this.transformer = transformer;
         this.basis = basis;
         this.reducer = reducer;
      }

      public final Long getRawResult() {
         return this.result;
      }

      public final void compute() {
         ObjectToLong transformer;
         LongByLongToLong reducer;
         if ((transformer = this.transformer) != null && (reducer = this.reducer) != null) {
            long r = this.basis;
            int i = this.baseIndex;

            int f;
            int h;
            while(this.batch > 0 && (h = (f = this.baseLimit) + i >>> 1) > i) {
               this.addToPendingCount(1);
               (this.rights = new MapReduceKeysToLongTask(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, this.rights, transformer, r, reducer)).fork();
            }

            Node p;
            while((p = this.advance()) != null) {
               r = reducer.apply(r, transformer.apply(p.key));
            }

            this.result = r;

            for(CountedCompleter c = this.firstComplete(); c != null; c = c.nextComplete()) {
               MapReduceKeysToLongTask t = (MapReduceKeysToLongTask)c;

               for(MapReduceKeysToLongTask s = t.rights; s != null; s = t.rights = s.nextRight) {
                  t.result = reducer.apply(t.result, s.result);
               }
            }
         }

      }
   }

   static final class MapReduceMappingsToDoubleTask extends BulkTask {
      final ObjectByObjectToDouble transformer;
      final DoubleByDoubleToDouble reducer;
      final double basis;
      double result;
      MapReduceMappingsToDoubleTask rights;
      MapReduceMappingsToDoubleTask nextRight;

      MapReduceMappingsToDoubleTask(BulkTask p, int b, int i, int f, Node[] t, MapReduceMappingsToDoubleTask nextRight, ObjectByObjectToDouble transformer, double basis, DoubleByDoubleToDouble reducer) {
         super(p, b, i, f, t);
         this.nextRight = nextRight;
         this.transformer = transformer;
         this.basis = basis;
         this.reducer = reducer;
      }

      public final Double getRawResult() {
         return this.result;
      }

      public final void compute() {
         ObjectByObjectToDouble transformer;
         DoubleByDoubleToDouble reducer;
         if ((transformer = this.transformer) != null && (reducer = this.reducer) != null) {
            double r = this.basis;
            int i = this.baseIndex;

            int f;
            int h;
            while(this.batch > 0 && (h = (f = this.baseLimit) + i >>> 1) > i) {
               this.addToPendingCount(1);
               (this.rights = new MapReduceMappingsToDoubleTask(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, this.rights, transformer, r, reducer)).fork();
            }

            Node p;
            while((p = this.advance()) != null) {
               r = reducer.apply(r, transformer.apply(p.key, p.val));
            }

            this.result = r;

            for(CountedCompleter c = this.firstComplete(); c != null; c = c.nextComplete()) {
               MapReduceMappingsToDoubleTask t = (MapReduceMappingsToDoubleTask)c;

               for(MapReduceMappingsToDoubleTask s = t.rights; s != null; s = t.rights = s.nextRight) {
                  t.result = reducer.apply(t.result, s.result);
               }
            }
         }

      }
   }

   static final class MapReduceEntriesToDoubleTask extends BulkTask {
      final ObjectToDouble transformer;
      final DoubleByDoubleToDouble reducer;
      final double basis;
      double result;
      MapReduceEntriesToDoubleTask rights;
      MapReduceEntriesToDoubleTask nextRight;

      MapReduceEntriesToDoubleTask(BulkTask p, int b, int i, int f, Node[] t, MapReduceEntriesToDoubleTask nextRight, ObjectToDouble transformer, double basis, DoubleByDoubleToDouble reducer) {
         super(p, b, i, f, t);
         this.nextRight = nextRight;
         this.transformer = transformer;
         this.basis = basis;
         this.reducer = reducer;
      }

      public final Double getRawResult() {
         return this.result;
      }

      public final void compute() {
         ObjectToDouble transformer;
         DoubleByDoubleToDouble reducer;
         if ((transformer = this.transformer) != null && (reducer = this.reducer) != null) {
            double r = this.basis;
            int i = this.baseIndex;

            int f;
            int h;
            while(this.batch > 0 && (h = (f = this.baseLimit) + i >>> 1) > i) {
               this.addToPendingCount(1);
               (this.rights = new MapReduceEntriesToDoubleTask(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, this.rights, transformer, r, reducer)).fork();
            }

            Node p;
            while((p = this.advance()) != null) {
               r = reducer.apply(r, transformer.apply(p));
            }

            this.result = r;

            for(CountedCompleter c = this.firstComplete(); c != null; c = c.nextComplete()) {
               MapReduceEntriesToDoubleTask t = (MapReduceEntriesToDoubleTask)c;

               for(MapReduceEntriesToDoubleTask s = t.rights; s != null; s = t.rights = s.nextRight) {
                  t.result = reducer.apply(t.result, s.result);
               }
            }
         }

      }
   }

   static final class MapReduceValuesToDoubleTask extends BulkTask {
      final ObjectToDouble transformer;
      final DoubleByDoubleToDouble reducer;
      final double basis;
      double result;
      MapReduceValuesToDoubleTask rights;
      MapReduceValuesToDoubleTask nextRight;

      MapReduceValuesToDoubleTask(BulkTask p, int b, int i, int f, Node[] t, MapReduceValuesToDoubleTask nextRight, ObjectToDouble transformer, double basis, DoubleByDoubleToDouble reducer) {
         super(p, b, i, f, t);
         this.nextRight = nextRight;
         this.transformer = transformer;
         this.basis = basis;
         this.reducer = reducer;
      }

      public final Double getRawResult() {
         return this.result;
      }

      public final void compute() {
         ObjectToDouble transformer;
         DoubleByDoubleToDouble reducer;
         if ((transformer = this.transformer) != null && (reducer = this.reducer) != null) {
            double r = this.basis;
            int i = this.baseIndex;

            int f;
            int h;
            while(this.batch > 0 && (h = (f = this.baseLimit) + i >>> 1) > i) {
               this.addToPendingCount(1);
               (this.rights = new MapReduceValuesToDoubleTask(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, this.rights, transformer, r, reducer)).fork();
            }

            Node p;
            while((p = this.advance()) != null) {
               r = reducer.apply(r, transformer.apply(p.val));
            }

            this.result = r;

            for(CountedCompleter c = this.firstComplete(); c != null; c = c.nextComplete()) {
               MapReduceValuesToDoubleTask t = (MapReduceValuesToDoubleTask)c;

               for(MapReduceValuesToDoubleTask s = t.rights; s != null; s = t.rights = s.nextRight) {
                  t.result = reducer.apply(t.result, s.result);
               }
            }
         }

      }
   }

   static final class MapReduceKeysToDoubleTask extends BulkTask {
      final ObjectToDouble transformer;
      final DoubleByDoubleToDouble reducer;
      final double basis;
      double result;
      MapReduceKeysToDoubleTask rights;
      MapReduceKeysToDoubleTask nextRight;

      MapReduceKeysToDoubleTask(BulkTask p, int b, int i, int f, Node[] t, MapReduceKeysToDoubleTask nextRight, ObjectToDouble transformer, double basis, DoubleByDoubleToDouble reducer) {
         super(p, b, i, f, t);
         this.nextRight = nextRight;
         this.transformer = transformer;
         this.basis = basis;
         this.reducer = reducer;
      }

      public final Double getRawResult() {
         return this.result;
      }

      public final void compute() {
         ObjectToDouble transformer;
         DoubleByDoubleToDouble reducer;
         if ((transformer = this.transformer) != null && (reducer = this.reducer) != null) {
            double r = this.basis;
            int i = this.baseIndex;

            int f;
            int h;
            while(this.batch > 0 && (h = (f = this.baseLimit) + i >>> 1) > i) {
               this.addToPendingCount(1);
               (this.rights = new MapReduceKeysToDoubleTask(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, this.rights, transformer, r, reducer)).fork();
            }

            Node p;
            while((p = this.advance()) != null) {
               r = reducer.apply(r, transformer.apply(p.key));
            }

            this.result = r;

            for(CountedCompleter c = this.firstComplete(); c != null; c = c.nextComplete()) {
               MapReduceKeysToDoubleTask t = (MapReduceKeysToDoubleTask)c;

               for(MapReduceKeysToDoubleTask s = t.rights; s != null; s = t.rights = s.nextRight) {
                  t.result = reducer.apply(t.result, s.result);
               }
            }
         }

      }
   }

   static final class MapReduceMappingsTask extends BulkTask {
      final BiFun transformer;
      final BiFun reducer;
      Object result;
      MapReduceMappingsTask rights;
      MapReduceMappingsTask nextRight;

      MapReduceMappingsTask(BulkTask p, int b, int i, int f, Node[] t, MapReduceMappingsTask nextRight, BiFun transformer, BiFun reducer) {
         super(p, b, i, f, t);
         this.nextRight = nextRight;
         this.transformer = transformer;
         this.reducer = reducer;
      }

      public final Object getRawResult() {
         return this.result;
      }

      public final void compute() {
         BiFun transformer;
         BiFun reducer;
         if ((transformer = this.transformer) != null && (reducer = this.reducer) != null) {
            int i = this.baseIndex;

            int f;
            int h;
            while(this.batch > 0 && (h = (f = this.baseLimit) + i >>> 1) > i) {
               this.addToPendingCount(1);
               (this.rights = new MapReduceMappingsTask(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, this.rights, transformer, reducer)).fork();
            }

            Object r = null;

            Node p;
            while((p = this.advance()) != null) {
               Object u;
               if ((u = transformer.apply(p.key, p.val)) != null) {
                  r = r == null ? u : reducer.apply(r, u);
               }
            }

            this.result = r;

            for(CountedCompleter c = this.firstComplete(); c != null; c = c.nextComplete()) {
               MapReduceMappingsTask t = (MapReduceMappingsTask)c;

               for(MapReduceMappingsTask s = t.rights; s != null; s = t.rights = s.nextRight) {
                  Object sr;
                  if ((sr = s.result) != null) {
                     Object tr;
                     t.result = (tr = t.result) == null ? sr : reducer.apply(tr, sr);
                  }
               }
            }
         }

      }
   }

   static final class MapReduceEntriesTask extends BulkTask {
      final Fun transformer;
      final BiFun reducer;
      Object result;
      MapReduceEntriesTask rights;
      MapReduceEntriesTask nextRight;

      MapReduceEntriesTask(BulkTask p, int b, int i, int f, Node[] t, MapReduceEntriesTask nextRight, Fun transformer, BiFun reducer) {
         super(p, b, i, f, t);
         this.nextRight = nextRight;
         this.transformer = transformer;
         this.reducer = reducer;
      }

      public final Object getRawResult() {
         return this.result;
      }

      public final void compute() {
         Fun transformer;
         BiFun reducer;
         if ((transformer = this.transformer) != null && (reducer = this.reducer) != null) {
            int i = this.baseIndex;

            int f;
            int h;
            while(this.batch > 0 && (h = (f = this.baseLimit) + i >>> 1) > i) {
               this.addToPendingCount(1);
               (this.rights = new MapReduceEntriesTask(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, this.rights, transformer, reducer)).fork();
            }

            Object r = null;

            Node p;
            while((p = this.advance()) != null) {
               Object u;
               if ((u = transformer.apply(p)) != null) {
                  r = r == null ? u : reducer.apply(r, u);
               }
            }

            this.result = r;

            for(CountedCompleter c = this.firstComplete(); c != null; c = c.nextComplete()) {
               MapReduceEntriesTask t = (MapReduceEntriesTask)c;

               for(MapReduceEntriesTask s = t.rights; s != null; s = t.rights = s.nextRight) {
                  Object sr;
                  if ((sr = s.result) != null) {
                     Object tr;
                     t.result = (tr = t.result) == null ? sr : reducer.apply(tr, sr);
                  }
               }
            }
         }

      }
   }

   static final class MapReduceValuesTask extends BulkTask {
      final Fun transformer;
      final BiFun reducer;
      Object result;
      MapReduceValuesTask rights;
      MapReduceValuesTask nextRight;

      MapReduceValuesTask(BulkTask p, int b, int i, int f, Node[] t, MapReduceValuesTask nextRight, Fun transformer, BiFun reducer) {
         super(p, b, i, f, t);
         this.nextRight = nextRight;
         this.transformer = transformer;
         this.reducer = reducer;
      }

      public final Object getRawResult() {
         return this.result;
      }

      public final void compute() {
         Fun transformer;
         BiFun reducer;
         if ((transformer = this.transformer) != null && (reducer = this.reducer) != null) {
            int i = this.baseIndex;

            int f;
            int h;
            while(this.batch > 0 && (h = (f = this.baseLimit) + i >>> 1) > i) {
               this.addToPendingCount(1);
               (this.rights = new MapReduceValuesTask(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, this.rights, transformer, reducer)).fork();
            }

            Object r = null;

            Node p;
            while((p = this.advance()) != null) {
               Object u;
               if ((u = transformer.apply(p.val)) != null) {
                  r = r == null ? u : reducer.apply(r, u);
               }
            }

            this.result = r;

            for(CountedCompleter c = this.firstComplete(); c != null; c = c.nextComplete()) {
               MapReduceValuesTask t = (MapReduceValuesTask)c;

               for(MapReduceValuesTask s = t.rights; s != null; s = t.rights = s.nextRight) {
                  Object sr;
                  if ((sr = s.result) != null) {
                     Object tr;
                     t.result = (tr = t.result) == null ? sr : reducer.apply(tr, sr);
                  }
               }
            }
         }

      }
   }

   static final class MapReduceKeysTask extends BulkTask {
      final Fun transformer;
      final BiFun reducer;
      Object result;
      MapReduceKeysTask rights;
      MapReduceKeysTask nextRight;

      MapReduceKeysTask(BulkTask p, int b, int i, int f, Node[] t, MapReduceKeysTask nextRight, Fun transformer, BiFun reducer) {
         super(p, b, i, f, t);
         this.nextRight = nextRight;
         this.transformer = transformer;
         this.reducer = reducer;
      }

      public final Object getRawResult() {
         return this.result;
      }

      public final void compute() {
         Fun transformer;
         BiFun reducer;
         if ((transformer = this.transformer) != null && (reducer = this.reducer) != null) {
            int i = this.baseIndex;

            int f;
            int h;
            while(this.batch > 0 && (h = (f = this.baseLimit) + i >>> 1) > i) {
               this.addToPendingCount(1);
               (this.rights = new MapReduceKeysTask(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, this.rights, transformer, reducer)).fork();
            }

            Object r = null;

            Node p;
            while((p = this.advance()) != null) {
               Object u;
               if ((u = transformer.apply(p.key)) != null) {
                  r = r == null ? u : reducer.apply(r, u);
               }
            }

            this.result = r;

            for(CountedCompleter c = this.firstComplete(); c != null; c = c.nextComplete()) {
               MapReduceKeysTask t = (MapReduceKeysTask)c;

               for(MapReduceKeysTask s = t.rights; s != null; s = t.rights = s.nextRight) {
                  Object sr;
                  if ((sr = s.result) != null) {
                     Object tr;
                     t.result = (tr = t.result) == null ? sr : reducer.apply(tr, sr);
                  }
               }
            }
         }

      }
   }

   static final class ReduceEntriesTask extends BulkTask {
      final BiFun reducer;
      Map.Entry result;
      ReduceEntriesTask rights;
      ReduceEntriesTask nextRight;

      ReduceEntriesTask(BulkTask p, int b, int i, int f, Node[] t, ReduceEntriesTask nextRight, BiFun reducer) {
         super(p, b, i, f, t);
         this.nextRight = nextRight;
         this.reducer = reducer;
      }

      public final Map.Entry getRawResult() {
         return this.result;
      }

      public final void compute() {
         BiFun reducer;
         if ((reducer = this.reducer) != null) {
            int i = this.baseIndex;

            int f;
            int h;
            while(this.batch > 0 && (h = (f = this.baseLimit) + i >>> 1) > i) {
               this.addToPendingCount(1);
               (this.rights = new ReduceEntriesTask(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, this.rights, reducer)).fork();
            }

            Object r;
            Node p;
            for(r = null; (p = this.advance()) != null; r = r == null ? p : (Map.Entry)reducer.apply(r, p)) {
            }

            this.result = (Map.Entry)r;

            for(CountedCompleter c = this.firstComplete(); c != null; c = c.nextComplete()) {
               ReduceEntriesTask t = (ReduceEntriesTask)c;

               for(ReduceEntriesTask s = t.rights; s != null; s = t.rights = s.nextRight) {
                  Map.Entry sr;
                  if ((sr = s.result) != null) {
                     Map.Entry tr;
                     t.result = (tr = t.result) == null ? sr : (Map.Entry)reducer.apply(tr, sr);
                  }
               }
            }
         }

      }
   }

   static final class ReduceValuesTask extends BulkTask {
      final BiFun reducer;
      Object result;
      ReduceValuesTask rights;
      ReduceValuesTask nextRight;

      ReduceValuesTask(BulkTask p, int b, int i, int f, Node[] t, ReduceValuesTask nextRight, BiFun reducer) {
         super(p, b, i, f, t);
         this.nextRight = nextRight;
         this.reducer = reducer;
      }

      public final Object getRawResult() {
         return this.result;
      }

      public final void compute() {
         BiFun reducer;
         if ((reducer = this.reducer) != null) {
            int i = this.baseIndex;

            int f;
            int h;
            while(this.batch > 0 && (h = (f = this.baseLimit) + i >>> 1) > i) {
               this.addToPendingCount(1);
               (this.rights = new ReduceValuesTask(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, this.rights, reducer)).fork();
            }

            Object r;
            Node p;
            Object v;
            for(r = null; (p = this.advance()) != null; r = r == null ? v : reducer.apply(r, v)) {
               v = p.val;
            }

            this.result = r;

            for(CountedCompleter c = this.firstComplete(); c != null; c = c.nextComplete()) {
               ReduceValuesTask t = (ReduceValuesTask)c;

               for(ReduceValuesTask s = t.rights; s != null; s = t.rights = s.nextRight) {
                  Object sr;
                  if ((sr = s.result) != null) {
                     Object tr;
                     t.result = (tr = t.result) == null ? sr : reducer.apply(tr, sr);
                  }
               }
            }
         }

      }
   }

   static final class ReduceKeysTask extends BulkTask {
      final BiFun reducer;
      Object result;
      ReduceKeysTask rights;
      ReduceKeysTask nextRight;

      ReduceKeysTask(BulkTask p, int b, int i, int f, Node[] t, ReduceKeysTask nextRight, BiFun reducer) {
         super(p, b, i, f, t);
         this.nextRight = nextRight;
         this.reducer = reducer;
      }

      public final Object getRawResult() {
         return this.result;
      }

      public final void compute() {
         BiFun reducer;
         if ((reducer = this.reducer) != null) {
            int i = this.baseIndex;

            int f;
            int h;
            while(this.batch > 0 && (h = (f = this.baseLimit) + i >>> 1) > i) {
               this.addToPendingCount(1);
               (this.rights = new ReduceKeysTask(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, this.rights, reducer)).fork();
            }

            Object r;
            Node p;
            Object u;
            for(r = null; (p = this.advance()) != null; r = r == null ? u : (u == null ? r : reducer.apply(r, u))) {
               u = p.key;
            }

            this.result = r;

            for(CountedCompleter c = this.firstComplete(); c != null; c = c.nextComplete()) {
               ReduceKeysTask t = (ReduceKeysTask)c;

               for(ReduceKeysTask s = t.rights; s != null; s = t.rights = s.nextRight) {
                  Object sr;
                  if ((sr = s.result) != null) {
                     Object tr;
                     t.result = (tr = t.result) == null ? sr : reducer.apply(tr, sr);
                  }
               }
            }
         }

      }
   }

   static final class SearchMappingsTask extends BulkTask {
      final BiFun searchFunction;
      final AtomicReference result;

      SearchMappingsTask(BulkTask p, int b, int i, int f, Node[] t, BiFun searchFunction, AtomicReference result) {
         super(p, b, i, f, t);
         this.searchFunction = searchFunction;
         this.result = result;
      }

      public final Object getRawResult() {
         return this.result.get();
      }

      public final void compute() {
         BiFun searchFunction;
         AtomicReference result;
         if ((searchFunction = this.searchFunction) != null && (result = this.result) != null) {
            int i = this.baseIndex;

            int f;
            int h;
            while(this.batch > 0 && (h = (f = this.baseLimit) + i >>> 1) > i) {
               if (result.get() != null) {
                  return;
               }

               this.addToPendingCount(1);
               (new SearchMappingsTask(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, searchFunction, result)).fork();
            }

            while(result.get() == null) {
               Node p;
               if ((p = this.advance()) == null) {
                  this.propagateCompletion();
                  break;
               }

               Object u;
               if ((u = searchFunction.apply(p.key, p.val)) != null) {
                  if (result.compareAndSet((Object)null, u)) {
                     this.quietlyCompleteRoot();
                  }
                  break;
               }
            }
         }

      }
   }

   static final class SearchEntriesTask extends BulkTask {
      final Fun searchFunction;
      final AtomicReference result;

      SearchEntriesTask(BulkTask p, int b, int i, int f, Node[] t, Fun searchFunction, AtomicReference result) {
         super(p, b, i, f, t);
         this.searchFunction = searchFunction;
         this.result = result;
      }

      public final Object getRawResult() {
         return this.result.get();
      }

      public final void compute() {
         Fun searchFunction;
         AtomicReference result;
         if ((searchFunction = this.searchFunction) != null && (result = this.result) != null) {
            int i = this.baseIndex;

            int f;
            int h;
            while(this.batch > 0 && (h = (f = this.baseLimit) + i >>> 1) > i) {
               if (result.get() != null) {
                  return;
               }

               this.addToPendingCount(1);
               (new SearchEntriesTask(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, searchFunction, result)).fork();
            }

            while(result.get() == null) {
               Node p;
               if ((p = this.advance()) == null) {
                  this.propagateCompletion();
                  break;
               }

               Object u;
               if ((u = searchFunction.apply(p)) != null) {
                  if (result.compareAndSet((Object)null, u)) {
                     this.quietlyCompleteRoot();
                  }

                  return;
               }
            }
         }

      }
   }

   static final class SearchValuesTask extends BulkTask {
      final Fun searchFunction;
      final AtomicReference result;

      SearchValuesTask(BulkTask p, int b, int i, int f, Node[] t, Fun searchFunction, AtomicReference result) {
         super(p, b, i, f, t);
         this.searchFunction = searchFunction;
         this.result = result;
      }

      public final Object getRawResult() {
         return this.result.get();
      }

      public final void compute() {
         Fun searchFunction;
         AtomicReference result;
         if ((searchFunction = this.searchFunction) != null && (result = this.result) != null) {
            int i = this.baseIndex;

            int f;
            int h;
            while(this.batch > 0 && (h = (f = this.baseLimit) + i >>> 1) > i) {
               if (result.get() != null) {
                  return;
               }

               this.addToPendingCount(1);
               (new SearchValuesTask(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, searchFunction, result)).fork();
            }

            while(result.get() == null) {
               Node p;
               if ((p = this.advance()) == null) {
                  this.propagateCompletion();
                  break;
               }

               Object u;
               if ((u = searchFunction.apply(p.val)) != null) {
                  if (result.compareAndSet((Object)null, u)) {
                     this.quietlyCompleteRoot();
                  }
                  break;
               }
            }
         }

      }
   }

   static final class SearchKeysTask extends BulkTask {
      final Fun searchFunction;
      final AtomicReference result;

      SearchKeysTask(BulkTask p, int b, int i, int f, Node[] t, Fun searchFunction, AtomicReference result) {
         super(p, b, i, f, t);
         this.searchFunction = searchFunction;
         this.result = result;
      }

      public final Object getRawResult() {
         return this.result.get();
      }

      public final void compute() {
         Fun searchFunction;
         AtomicReference result;
         if ((searchFunction = this.searchFunction) != null && (result = this.result) != null) {
            int i = this.baseIndex;

            int f;
            int h;
            while(this.batch > 0 && (h = (f = this.baseLimit) + i >>> 1) > i) {
               if (result.get() != null) {
                  return;
               }

               this.addToPendingCount(1);
               (new SearchKeysTask(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, searchFunction, result)).fork();
            }

            while(result.get() == null) {
               Node p;
               if ((p = this.advance()) == null) {
                  this.propagateCompletion();
                  break;
               }

               Object u;
               if ((u = searchFunction.apply(p.key)) != null) {
                  if (result.compareAndSet((Object)null, u)) {
                     this.quietlyCompleteRoot();
                  }
                  break;
               }
            }
         }

      }
   }

   static final class ForEachTransformedMappingTask extends BulkTask {
      final BiFun transformer;
      final Action action;

      ForEachTransformedMappingTask(BulkTask p, int b, int i, int f, Node[] t, BiFun transformer, Action action) {
         super(p, b, i, f, t);
         this.transformer = transformer;
         this.action = action;
      }

      public final void compute() {
         BiFun transformer;
         Action action;
         if ((transformer = this.transformer) != null && (action = this.action) != null) {
            int i = this.baseIndex;

            int f;
            int h;
            while(this.batch > 0 && (h = (f = this.baseLimit) + i >>> 1) > i) {
               this.addToPendingCount(1);
               (new ForEachTransformedMappingTask(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, transformer, action)).fork();
            }

            Node p;
            while((p = this.advance()) != null) {
               Object u;
               if ((u = transformer.apply(p.key, p.val)) != null) {
                  action.apply(u);
               }
            }

            this.propagateCompletion();
         }

      }
   }

   static final class ForEachTransformedEntryTask extends BulkTask {
      final Fun transformer;
      final Action action;

      ForEachTransformedEntryTask(BulkTask p, int b, int i, int f, Node[] t, Fun transformer, Action action) {
         super(p, b, i, f, t);
         this.transformer = transformer;
         this.action = action;
      }

      public final void compute() {
         Fun transformer;
         Action action;
         if ((transformer = this.transformer) != null && (action = this.action) != null) {
            int i = this.baseIndex;

            int f;
            int h;
            while(this.batch > 0 && (h = (f = this.baseLimit) + i >>> 1) > i) {
               this.addToPendingCount(1);
               (new ForEachTransformedEntryTask(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, transformer, action)).fork();
            }

            Node p;
            while((p = this.advance()) != null) {
               Object u;
               if ((u = transformer.apply(p)) != null) {
                  action.apply(u);
               }
            }

            this.propagateCompletion();
         }

      }
   }

   static final class ForEachTransformedValueTask extends BulkTask {
      final Fun transformer;
      final Action action;

      ForEachTransformedValueTask(BulkTask p, int b, int i, int f, Node[] t, Fun transformer, Action action) {
         super(p, b, i, f, t);
         this.transformer = transformer;
         this.action = action;
      }

      public final void compute() {
         Fun transformer;
         Action action;
         if ((transformer = this.transformer) != null && (action = this.action) != null) {
            int i = this.baseIndex;

            int f;
            int h;
            while(this.batch > 0 && (h = (f = this.baseLimit) + i >>> 1) > i) {
               this.addToPendingCount(1);
               (new ForEachTransformedValueTask(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, transformer, action)).fork();
            }

            Node p;
            while((p = this.advance()) != null) {
               Object u;
               if ((u = transformer.apply(p.val)) != null) {
                  action.apply(u);
               }
            }

            this.propagateCompletion();
         }

      }
   }

   static final class ForEachTransformedKeyTask extends BulkTask {
      final Fun transformer;
      final Action action;

      ForEachTransformedKeyTask(BulkTask p, int b, int i, int f, Node[] t, Fun transformer, Action action) {
         super(p, b, i, f, t);
         this.transformer = transformer;
         this.action = action;
      }

      public final void compute() {
         Fun transformer;
         Action action;
         if ((transformer = this.transformer) != null && (action = this.action) != null) {
            int i = this.baseIndex;

            int f;
            int h;
            while(this.batch > 0 && (h = (f = this.baseLimit) + i >>> 1) > i) {
               this.addToPendingCount(1);
               (new ForEachTransformedKeyTask(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, transformer, action)).fork();
            }

            Node p;
            while((p = this.advance()) != null) {
               Object u;
               if ((u = transformer.apply(p.key)) != null) {
                  action.apply(u);
               }
            }

            this.propagateCompletion();
         }

      }
   }

   static final class ForEachMappingTask extends BulkTask {
      final BiAction action;

      ForEachMappingTask(BulkTask p, int b, int i, int f, Node[] t, BiAction action) {
         super(p, b, i, f, t);
         this.action = action;
      }

      public final void compute() {
         BiAction action;
         if ((action = this.action) != null) {
            int i = this.baseIndex;

            int f;
            int h;
            while(this.batch > 0 && (h = (f = this.baseLimit) + i >>> 1) > i) {
               this.addToPendingCount(1);
               (new ForEachMappingTask(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, action)).fork();
            }

            Node p;
            while((p = this.advance()) != null) {
               action.apply(p.key, p.val);
            }

            this.propagateCompletion();
         }

      }
   }

   static final class ForEachEntryTask extends BulkTask {
      final Action action;

      ForEachEntryTask(BulkTask p, int b, int i, int f, Node[] t, Action action) {
         super(p, b, i, f, t);
         this.action = action;
      }

      public final void compute() {
         Action action;
         if ((action = this.action) != null) {
            int i = this.baseIndex;

            int f;
            int h;
            while(this.batch > 0 && (h = (f = this.baseLimit) + i >>> 1) > i) {
               this.addToPendingCount(1);
               (new ForEachEntryTask(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, action)).fork();
            }

            Node p;
            while((p = this.advance()) != null) {
               action.apply(p);
            }

            this.propagateCompletion();
         }

      }
   }

   static final class ForEachValueTask extends BulkTask {
      final Action action;

      ForEachValueTask(BulkTask p, int b, int i, int f, Node[] t, Action action) {
         super(p, b, i, f, t);
         this.action = action;
      }

      public final void compute() {
         Action action;
         if ((action = this.action) != null) {
            int i = this.baseIndex;

            int f;
            int h;
            while(this.batch > 0 && (h = (f = this.baseLimit) + i >>> 1) > i) {
               this.addToPendingCount(1);
               (new ForEachValueTask(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, action)).fork();
            }

            Node p;
            while((p = this.advance()) != null) {
               action.apply(p.val);
            }

            this.propagateCompletion();
         }

      }
   }

   static final class ForEachKeyTask extends BulkTask {
      final Action action;

      ForEachKeyTask(BulkTask p, int b, int i, int f, Node[] t, Action action) {
         super(p, b, i, f, t);
         this.action = action;
      }

      public final void compute() {
         Action action;
         if ((action = this.action) != null) {
            int i = this.baseIndex;

            int f;
            int h;
            while(this.batch > 0 && (h = (f = this.baseLimit) + i >>> 1) > i) {
               this.addToPendingCount(1);
               (new ForEachKeyTask(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, action)).fork();
            }

            Node p;
            while((p = this.advance()) != null) {
               action.apply(p.key);
            }

            this.propagateCompletion();
         }

      }
   }

   abstract static class BulkTask extends CountedCompleter {
      Node[] tab;
      Node next;
      int index;
      int baseIndex;
      int baseLimit;
      final int baseSize;
      int batch;

      BulkTask(BulkTask par, int b, int i, int f, Node[] t) {
         super(par);
         this.batch = b;
         this.index = this.baseIndex = i;
         if ((this.tab = t) == null) {
            this.baseSize = this.baseLimit = 0;
         } else if (par == null) {
            this.baseSize = this.baseLimit = t.length;
         } else {
            this.baseLimit = f;
            this.baseSize = par.baseSize;
         }

      }

      final Node advance() {
         Object e;
         if ((e = this.next) != null) {
            e = ((Node)e).next;
         }

         while(e == null) {
            Node[] t;
            int i;
            int n;
            if (this.baseIndex >= this.baseLimit || (t = this.tab) == null || (n = t.length) <= (i = this.index) || i < 0) {
               return this.next = null;
            }

            if ((e = ConcurrentHashMapV8.tabAt(t, this.index)) != null && ((Node)e).hash < 0) {
               if (e instanceof ForwardingNode) {
                  this.tab = ((ForwardingNode)e).nextTable;
                  e = null;
                  continue;
               }

               if (e instanceof TreeBin) {
                  e = ((TreeBin)e).first;
               } else {
                  e = null;
               }
            }

            if ((this.index += this.baseSize) >= n) {
               this.index = ++this.baseIndex;
            }
         }

         return this.next = (Node)e;
      }
   }

   static final class EntrySetView extends CollectionView implements Set, Serializable {
      private static final long serialVersionUID = 2249069246763182397L;

      EntrySetView(ConcurrentHashMapV8 map) {
         super(map);
      }

      public boolean contains(Object o) {
         Object k;
         Object v;
         Object r;
         Map.Entry e;
         return o instanceof Map.Entry && (k = (e = (Map.Entry)o).getKey()) != null && (r = this.map.get(k)) != null && (v = e.getValue()) != null && (v == r || v.equals(r));
      }

      public boolean remove(Object o) {
         Object k;
         Object v;
         Map.Entry e;
         return o instanceof Map.Entry && (k = (e = (Map.Entry)o).getKey()) != null && (v = e.getValue()) != null && this.map.remove(k, v);
      }

      public Iterator iterator() {
         ConcurrentHashMapV8 m = this.map;
         Node[] t;
         int f = (t = m.table) == null ? 0 : t.length;
         return new EntryIterator(t, f, 0, f, m);
      }

      public boolean add(Map.Entry e) {
         return this.map.putVal(e.getKey(), e.getValue(), false) == null;
      }

      public boolean addAll(Collection c) {
         boolean added = false;
         Iterator i$ = c.iterator();

         while(i$.hasNext()) {
            Map.Entry e = (Map.Entry)i$.next();
            if (this.add(e)) {
               added = true;
            }
         }

         return added;
      }

      public final int hashCode() {
         int h = 0;
         Node[] t;
         Node p;
         if ((t = this.map.table) != null) {
            for(Traverser it = new Traverser(t, t.length, 0, t.length); (p = it.advance()) != null; h += p.hashCode()) {
            }
         }

         return h;
      }

      public final boolean equals(Object o) {
         Set c;
         return o instanceof Set && ((c = (Set)o) == this || this.containsAll(c) && c.containsAll(this));
      }

      public ConcurrentHashMapSpliterator spliterator() {
         ConcurrentHashMapV8 m = this.map;
         long n = m.sumCount();
         Node[] t;
         int f = (t = m.table) == null ? 0 : t.length;
         return new EntrySpliterator(t, f, 0, f, n < 0L ? 0L : n, m);
      }

      public void forEach(Action action) {
         if (action == null) {
            throw new NullPointerException();
         } else {
            Node[] t;
            if ((t = this.map.table) != null) {
               Traverser it = new Traverser(t, t.length, 0, t.length);

               Node p;
               while((p = it.advance()) != null) {
                  action.apply(new MapEntry(p.key, p.val, this.map));
               }
            }

         }
      }
   }

   static final class ValuesView extends CollectionView implements Collection, Serializable {
      private static final long serialVersionUID = 2249069246763182397L;

      ValuesView(ConcurrentHashMapV8 map) {
         super(map);
      }

      public final boolean contains(Object o) {
         return this.map.containsValue(o);
      }

      public final boolean remove(Object o) {
         if (o != null) {
            Iterator it = this.iterator();

            while(it.hasNext()) {
               if (o.equals(it.next())) {
                  it.remove();
                  return true;
               }
            }
         }

         return false;
      }

      public final Iterator iterator() {
         ConcurrentHashMapV8 m = this.map;
         Node[] t;
         int f = (t = m.table) == null ? 0 : t.length;
         return new ValueIterator(t, f, 0, f, m);
      }

      public final boolean add(Object e) {
         throw new UnsupportedOperationException();
      }

      public final boolean addAll(Collection c) {
         throw new UnsupportedOperationException();
      }

      public ConcurrentHashMapSpliterator spliterator() {
         ConcurrentHashMapV8 m = this.map;
         long n = m.sumCount();
         Node[] t;
         int f = (t = m.table) == null ? 0 : t.length;
         return new ValueSpliterator(t, f, 0, f, n < 0L ? 0L : n);
      }

      public void forEach(Action action) {
         if (action == null) {
            throw new NullPointerException();
         } else {
            Node[] t;
            if ((t = this.map.table) != null) {
               Traverser it = new Traverser(t, t.length, 0, t.length);

               Node p;
               while((p = it.advance()) != null) {
                  action.apply(p.val);
               }
            }

         }
      }
   }

   public static class KeySetView extends CollectionView implements Set, Serializable {
      private static final long serialVersionUID = 7249069246763182397L;
      private final Object value;

      KeySetView(ConcurrentHashMapV8 map, Object value) {
         super(map);
         this.value = value;
      }

      public Object getMappedValue() {
         return this.value;
      }

      public boolean contains(Object o) {
         return this.map.containsKey(o);
      }

      public boolean remove(Object o) {
         return this.map.remove(o) != null;
      }

      public Iterator iterator() {
         ConcurrentHashMapV8 m = this.map;
         Node[] t;
         int f = (t = m.table) == null ? 0 : t.length;
         return new KeyIterator(t, f, 0, f, m);
      }

      public boolean add(Object e) {
         Object v;
         if ((v = this.value) == null) {
            throw new UnsupportedOperationException();
         } else {
            return this.map.putVal(e, v, true) == null;
         }
      }

      public boolean addAll(Collection c) {
         boolean added = false;
         Object v;
         if ((v = this.value) == null) {
            throw new UnsupportedOperationException();
         } else {
            Iterator i$ = c.iterator();

            while(i$.hasNext()) {
               Object e = i$.next();
               if (this.map.putVal(e, v, true) == null) {
                  added = true;
               }
            }

            return added;
         }
      }

      public int hashCode() {
         int h = 0;

         Object e;
         for(Iterator i$ = this.iterator(); i$.hasNext(); h += e.hashCode()) {
            e = i$.next();
         }

         return h;
      }

      public boolean equals(Object o) {
         Set c;
         return o instanceof Set && ((c = (Set)o) == this || this.containsAll(c) && c.containsAll(this));
      }

      public ConcurrentHashMapSpliterator spliterator() {
         ConcurrentHashMapV8 m = this.map;
         long n = m.sumCount();
         Node[] t;
         int f = (t = m.table) == null ? 0 : t.length;
         return new KeySpliterator(t, f, 0, f, n < 0L ? 0L : n);
      }

      public void forEach(Action action) {
         if (action == null) {
            throw new NullPointerException();
         } else {
            Node[] t;
            if ((t = this.map.table) != null) {
               Traverser it = new Traverser(t, t.length, 0, t.length);

               Node p;
               while((p = it.advance()) != null) {
                  action.apply(p.key);
               }
            }

         }
      }
   }

   abstract static class CollectionView implements Collection, Serializable {
      private static final long serialVersionUID = 7249069246763182397L;
      final ConcurrentHashMapV8 map;
      private static final String oomeMsg = "Required array size too large";

      CollectionView(ConcurrentHashMapV8 map) {
         this.map = map;
      }

      public ConcurrentHashMapV8 getMap() {
         return this.map;
      }

      public final void clear() {
         this.map.clear();
      }

      public final int size() {
         return this.map.size();
      }

      public final boolean isEmpty() {
         return this.map.isEmpty();
      }

      public abstract Iterator iterator();

      public abstract boolean contains(Object var1);

      public abstract boolean remove(Object var1);

      public final Object[] toArray() {
         long sz = this.map.mappingCount();
         if (sz > 2147483639L) {
            throw new OutOfMemoryError("Required array size too large");
         } else {
            int n = (int)sz;
            Object[] r = new Object[n];
            int i = 0;

            Object e;
            for(Iterator i$ = this.iterator(); i$.hasNext(); r[i++] = e) {
               e = i$.next();
               if (i == n) {
                  if (n >= 2147483639) {
                     throw new OutOfMemoryError("Required array size too large");
                  }

                  if (n >= 1073741819) {
                     n = 2147483639;
                  } else {
                     n += (n >>> 1) + 1;
                  }

                  r = Arrays.copyOf(r, n);
               }
            }

            return i == n ? r : Arrays.copyOf(r, i);
         }
      }

      public final Object[] toArray(Object[] a) {
         long sz = this.map.mappingCount();
         if (sz > 2147483639L) {
            throw new OutOfMemoryError("Required array size too large");
         } else {
            int m = (int)sz;
            Object[] r = a.length >= m ? a : (Object[])((Object[])Array.newInstance(a.getClass().getComponentType(), m));
            int n = r.length;
            int i = 0;

            Object e;
            for(Iterator i$ = this.iterator(); i$.hasNext(); r[i++] = e) {
               e = i$.next();
               if (i == n) {
                  if (n >= 2147483639) {
                     throw new OutOfMemoryError("Required array size too large");
                  }

                  if (n >= 1073741819) {
                     n = 2147483639;
                  } else {
                     n += (n >>> 1) + 1;
                  }

                  r = Arrays.copyOf(r, n);
               }
            }

            if (a == r && i < n) {
               r[i] = null;
               return r;
            } else {
               return i == n ? r : Arrays.copyOf(r, i);
            }
         }
      }

      public final String toString() {
         StringBuilder sb = new StringBuilder();
         sb.append('[');
         Iterator it = this.iterator();
         if (it.hasNext()) {
            while(true) {
               Object e = it.next();
               sb.append(e == this ? "(this Collection)" : e);
               if (!it.hasNext()) {
                  break;
               }

               sb.append(',').append(' ');
            }
         }

         return sb.append(']').toString();
      }

      public final boolean containsAll(Collection c) {
         if (c != this) {
            Iterator i$ = c.iterator();

            while(i$.hasNext()) {
               Object e = i$.next();
               if (e == null || !this.contains(e)) {
                  return false;
               }
            }
         }

         return true;
      }

      public final boolean removeAll(Collection c) {
         boolean modified = false;
         Iterator it = this.iterator();

         while(it.hasNext()) {
            if (c.contains(it.next())) {
               it.remove();
               modified = true;
            }
         }

         return modified;
      }

      public final boolean retainAll(Collection c) {
         boolean modified = false;
         Iterator it = this.iterator();

         while(it.hasNext()) {
            if (!c.contains(it.next())) {
               it.remove();
               modified = true;
            }
         }

         return modified;
      }
   }

   static final class EntrySpliterator extends Traverser implements ConcurrentHashMapSpliterator {
      final ConcurrentHashMapV8 map;
      long est;

      EntrySpliterator(Node[] tab, int size, int index, int limit, long est, ConcurrentHashMapV8 map) {
         super(tab, size, index, limit);
         this.map = map;
         this.est = est;
      }

      public ConcurrentHashMapSpliterator trySplit() {
         int i;
         int f;
         int h;
         return (h = (i = this.baseIndex) + (f = this.baseLimit) >>> 1) <= i ? null : new EntrySpliterator(this.tab, this.baseSize, this.baseLimit = h, f, this.est >>>= 1, this.map);
      }

      public void forEachRemaining(Action action) {
         if (action == null) {
            throw new NullPointerException();
         } else {
            Node p;
            while((p = this.advance()) != null) {
               action.apply(new MapEntry(p.key, p.val, this.map));
            }

         }
      }

      public boolean tryAdvance(Action action) {
         if (action == null) {
            throw new NullPointerException();
         } else {
            Node p;
            if ((p = this.advance()) == null) {
               return false;
            } else {
               action.apply(new MapEntry(p.key, p.val, this.map));
               return true;
            }
         }
      }

      public long estimateSize() {
         return this.est;
      }
   }

   static final class ValueSpliterator extends Traverser implements ConcurrentHashMapSpliterator {
      long est;

      ValueSpliterator(Node[] tab, int size, int index, int limit, long est) {
         super(tab, size, index, limit);
         this.est = est;
      }

      public ConcurrentHashMapSpliterator trySplit() {
         int i;
         int f;
         int h;
         return (h = (i = this.baseIndex) + (f = this.baseLimit) >>> 1) <= i ? null : new ValueSpliterator(this.tab, this.baseSize, this.baseLimit = h, f, this.est >>>= 1);
      }

      public void forEachRemaining(Action action) {
         if (action == null) {
            throw new NullPointerException();
         } else {
            Node p;
            while((p = this.advance()) != null) {
               action.apply(p.val);
            }

         }
      }

      public boolean tryAdvance(Action action) {
         if (action == null) {
            throw new NullPointerException();
         } else {
            Node p;
            if ((p = this.advance()) == null) {
               return false;
            } else {
               action.apply(p.val);
               return true;
            }
         }
      }

      public long estimateSize() {
         return this.est;
      }
   }

   static final class KeySpliterator extends Traverser implements ConcurrentHashMapSpliterator {
      long est;

      KeySpliterator(Node[] tab, int size, int index, int limit, long est) {
         super(tab, size, index, limit);
         this.est = est;
      }

      public ConcurrentHashMapSpliterator trySplit() {
         int i;
         int f;
         int h;
         return (h = (i = this.baseIndex) + (f = this.baseLimit) >>> 1) <= i ? null : new KeySpliterator(this.tab, this.baseSize, this.baseLimit = h, f, this.est >>>= 1);
      }

      public void forEachRemaining(Action action) {
         if (action == null) {
            throw new NullPointerException();
         } else {
            Node p;
            while((p = this.advance()) != null) {
               action.apply(p.key);
            }

         }
      }

      public boolean tryAdvance(Action action) {
         if (action == null) {
            throw new NullPointerException();
         } else {
            Node p;
            if ((p = this.advance()) == null) {
               return false;
            } else {
               action.apply(p.key);
               return true;
            }
         }
      }

      public long estimateSize() {
         return this.est;
      }
   }

   static final class MapEntry implements Map.Entry {
      final Object key;
      Object val;
      final ConcurrentHashMapV8 map;

      MapEntry(Object key, Object val, ConcurrentHashMapV8 map) {
         this.key = key;
         this.val = val;
         this.map = map;
      }

      public Object getKey() {
         return this.key;
      }

      public Object getValue() {
         return this.val;
      }

      public int hashCode() {
         return this.key.hashCode() ^ this.val.hashCode();
      }

      public String toString() {
         return this.key + "=" + this.val;
      }

      public boolean equals(Object o) {
         Object k;
         Object v;
         Map.Entry e;
         return o instanceof Map.Entry && (k = (e = (Map.Entry)o).getKey()) != null && (v = e.getValue()) != null && (k == this.key || k.equals(this.key)) && (v == this.val || v.equals(this.val));
      }

      public Object setValue(Object value) {
         if (value == null) {
            throw new NullPointerException();
         } else {
            Object v = this.val;
            this.val = value;
            this.map.put(this.key, value);
            return v;
         }
      }
   }

   static final class EntryIterator extends BaseIterator implements Iterator {
      EntryIterator(Node[] tab, int index, int size, int limit, ConcurrentHashMapV8 map) {
         super(tab, index, size, limit, map);
      }

      public final Map.Entry next() {
         Node p;
         if ((p = this.next) == null) {
            throw new NoSuchElementException();
         } else {
            Object k = p.key;
            Object v = p.val;
            this.lastReturned = p;
            this.advance();
            return new MapEntry(k, v, this.map);
         }
      }
   }

   static final class ValueIterator extends BaseIterator implements Iterator, Enumeration {
      ValueIterator(Node[] tab, int index, int size, int limit, ConcurrentHashMapV8 map) {
         super(tab, index, size, limit, map);
      }

      public final Object next() {
         Node p;
         if ((p = this.next) == null) {
            throw new NoSuchElementException();
         } else {
            Object v = p.val;
            this.lastReturned = p;
            this.advance();
            return v;
         }
      }

      public final Object nextElement() {
         return this.next();
      }
   }

   static final class KeyIterator extends BaseIterator implements Iterator, Enumeration {
      KeyIterator(Node[] tab, int index, int size, int limit, ConcurrentHashMapV8 map) {
         super(tab, index, size, limit, map);
      }

      public final Object next() {
         Node p;
         if ((p = this.next) == null) {
            throw new NoSuchElementException();
         } else {
            Object k = p.key;
            this.lastReturned = p;
            this.advance();
            return k;
         }
      }

      public final Object nextElement() {
         return this.next();
      }
   }

   static class BaseIterator extends Traverser {
      final ConcurrentHashMapV8 map;
      Node lastReturned;

      BaseIterator(Node[] tab, int size, int index, int limit, ConcurrentHashMapV8 map) {
         super(tab, size, index, limit);
         this.map = map;
         this.advance();
      }

      public final boolean hasNext() {
         return this.next != null;
      }

      public final boolean hasMoreElements() {
         return this.next != null;
      }

      public final void remove() {
         Node p;
         if ((p = this.lastReturned) == null) {
            throw new IllegalStateException();
         } else {
            this.lastReturned = null;
            this.map.replaceNode(p.key, (Object)null, (Object)null);
         }
      }
   }

   static class Traverser {
      Node[] tab;
      Node next;
      TableStack stack;
      TableStack spare;
      int index;
      int baseIndex;
      int baseLimit;
      final int baseSize;

      Traverser(Node[] tab, int size, int index, int limit) {
         this.tab = tab;
         this.baseSize = size;
         this.baseIndex = this.index = index;
         this.baseLimit = limit;
         this.next = null;
      }

      final Node advance() {
         Object e;
         if ((e = this.next) != null) {
            e = ((Node)e).next;
         }

         while(e == null) {
            Node[] t;
            int i;
            int n;
            if (this.baseIndex >= this.baseLimit || (t = this.tab) == null || (n = t.length) <= (i = this.index) || i < 0) {
               return this.next = null;
            }

            if ((e = ConcurrentHashMapV8.tabAt(t, i)) != null && ((Node)e).hash < 0) {
               if (e instanceof ForwardingNode) {
                  this.tab = ((ForwardingNode)e).nextTable;
                  e = null;
                  this.pushState(t, i, n);
                  continue;
               }

               if (e instanceof TreeBin) {
                  e = ((TreeBin)e).first;
               } else {
                  e = null;
               }
            }

            if (this.stack != null) {
               this.recoverState(n);
            } else if ((this.index = i + this.baseSize) >= n) {
               this.index = ++this.baseIndex;
            }
         }

         return this.next = (Node)e;
      }

      private void pushState(Node[] t, int i, int n) {
         TableStack s = this.spare;
         if (s != null) {
            this.spare = s.next;
         } else {
            s = new TableStack();
         }

         s.tab = t;
         s.length = n;
         s.index = i;
         s.next = this.stack;
         this.stack = s;
      }

      private void recoverState(int n) {
         TableStack s;
         int len;
         while((s = this.stack) != null && (this.index += len = s.length) >= n) {
            n = len;
            this.index = s.index;
            this.tab = s.tab;
            s.tab = null;
            TableStack next = s.next;
            s.next = this.spare;
            this.stack = next;
            this.spare = s;
         }

         if (s == null && (this.index += this.baseSize) >= n) {
            this.index = ++this.baseIndex;
         }

      }
   }

   static final class TableStack {
      int length;
      int index;
      Node[] tab;
      TableStack next;
   }

   static final class TreeBin extends Node {
      TreeNode root;
      volatile TreeNode first;
      volatile Thread waiter;
      volatile int lockState;
      static final int WRITER = 1;
      static final int WAITER = 2;
      static final int READER = 4;
      private static final Unsafe U;
      private static final long LOCKSTATE;

      static int tieBreakOrder(Object a, Object b) {
         int d;
         if (a == null || b == null || (d = a.getClass().getName().compareTo(b.getClass().getName())) == 0) {
            d = System.identityHashCode(a) <= System.identityHashCode(b) ? -1 : 1;
         }

         return d;
      }

      TreeBin(TreeNode b) {
         super(-2, (Object)null, (Object)null, (Node)null);
         this.first = b;
         TreeNode r = null;

         TreeNode next;
         for(TreeNode x = b; x != null; x = next) {
            next = (TreeNode)x.next;
            x.left = x.right = null;
            if (r == null) {
               x.parent = null;
               x.red = false;
               r = x;
            } else {
               Object k = x.key;
               int h = x.hash;
               Class kc = null;
               TreeNode p = r;

               int dir;
               TreeNode xp;
               do {
                  Object pk = p.key;
                  int ph;
                  if ((ph = p.hash) > h) {
                     dir = -1;
                  } else if (ph < h) {
                     dir = 1;
                  } else if (kc == null && (kc = ConcurrentHashMapV8.comparableClassFor(k)) == null || (dir = ConcurrentHashMapV8.compareComparables(kc, k, pk)) == 0) {
                     dir = tieBreakOrder(k, pk);
                  }

                  xp = p;
               } while((p = dir <= 0 ? p.left : p.right) != null);

               x.parent = xp;
               if (dir <= 0) {
                  xp.left = x;
               } else {
                  xp.right = x;
               }

               r = balanceInsertion(r, x);
            }
         }

         this.root = r;

         assert checkInvariants(this.root);

      }

      private final void lockRoot() {
         if (!U.compareAndSwapInt(this, LOCKSTATE, 0, 1)) {
            this.contendedLock();
         }

      }

      private final void unlockRoot() {
         this.lockState = 0;
      }

      private final void contendedLock() {
         boolean waiting = false;

         int s;
         do {
            while(((s = this.lockState) & -3) != 0) {
               if ((s & 2) == 0) {
                  if (U.compareAndSwapInt(this, LOCKSTATE, s, s | 2)) {
                     waiting = true;
                     this.waiter = Thread.currentThread();
                  }
               } else if (waiting) {
                  LockSupport.park(this);
               }
            }
         } while(!U.compareAndSwapInt(this, LOCKSTATE, s, 1));

         if (waiting) {
            this.waiter = null;
         }

      }

      final Node find(int h, Object k) {
         if (k != null) {
            Node e = this.first;

            while(e != null) {
               int s;
               if (((s = this.lockState) & 3) != 0) {
                  Object ek;
                  if (((Node)e).hash == h && ((ek = ((Node)e).key) == k || ek != null && k.equals(ek))) {
                     return (Node)e;
                  }

                  e = ((Node)e).next;
               } else if (U.compareAndSwapInt(this, LOCKSTATE, s, s + 4)) {
                  boolean var14 = false;

                  TreeNode p;
                  try {
                     var14 = true;
                     TreeNode r;
                     p = (r = this.root) == null ? null : r.findTreeNode(h, k, (Class)null);
                     var14 = false;
                  } finally {
                     if (var14) {
                        int ls;
                        while(!U.compareAndSwapInt(this, LOCKSTATE, ls = this.lockState, ls - 4)) {
                        }

                        Thread w;
                        if (ls == 6 && (w = this.waiter) != null) {
                           LockSupport.unpark(w);
                        }

                     }
                  }

                  int ls;
                  while(!U.compareAndSwapInt(this, LOCKSTATE, ls = this.lockState, ls - 4)) {
                  }

                  Thread w;
                  if (ls == 6 && (w = this.waiter) != null) {
                     LockSupport.unpark(w);
                  }

                  return p;
               }
            }
         }

         return null;
      }

      final TreeNode putTreeVal(int h, Object k, Object v) {
         Class kc = null;
         boolean searched = false;
         TreeNode p = this.root;

         while(true) {
            if (p == null) {
               this.first = this.root = new TreeNode(h, k, v, (Node)null, (TreeNode)null);
               break;
            }

            int dir;
            int ph;
            TreeNode q;
            TreeNode x;
            if ((ph = p.hash) > h) {
               dir = -1;
            } else if (ph < h) {
               dir = 1;
            } else {
               Object pk;
               if ((pk = p.key) == k || pk != null && k.equals(pk)) {
                  return p;
               }

               if (kc == null && (kc = ConcurrentHashMapV8.comparableClassFor(k)) == null || (dir = ConcurrentHashMapV8.compareComparables(kc, k, pk)) == 0) {
                  if (!searched) {
                     searched = true;
                     if ((x = p.left) != null && (q = x.findTreeNode(h, k, kc)) != null || (x = p.right) != null && (q = x.findTreeNode(h, k, kc)) != null) {
                        return q;
                     }
                  }

                  dir = tieBreakOrder(k, pk);
               }
            }

            q = p;
            if ((p = dir <= 0 ? p.left : p.right) == null) {
               TreeNode f = this.first;
               this.first = x = new TreeNode(h, k, v, f, q);
               if (f != null) {
                  f.prev = x;
               }

               if (dir <= 0) {
                  q.left = x;
               } else {
                  q.right = x;
               }

               if (!q.red) {
                  x.red = true;
               } else {
                  this.lockRoot();

                  try {
                     this.root = balanceInsertion(this.root, x);
                  } finally {
                     this.unlockRoot();
                  }
               }
               break;
            }
         }

         assert checkInvariants(this.root);

         return null;
      }

      final boolean removeTreeNode(TreeNode p) {
         TreeNode next = (TreeNode)p.next;
         TreeNode pred = p.prev;
         if (pred == null) {
            this.first = next;
         } else {
            pred.next = next;
         }

         if (next != null) {
            next.prev = pred;
         }

         if (this.first == null) {
            this.root = null;
            return true;
         } else {
            TreeNode r;
            TreeNode rl;
            if ((r = this.root) != null && r.right != null && (rl = r.left) != null && rl.left != null) {
               this.lockRoot();

               try {
                  TreeNode pl = p.left;
                  TreeNode pr = p.right;
                  TreeNode replacement;
                  TreeNode s;
                  if (pl != null && pr != null) {
                     TreeNode sl;
                     for(s = pr; (sl = s.left) != null; s = sl) {
                     }

                     boolean c = s.red;
                     s.red = p.red;
                     p.red = c;
                     TreeNode sr = s.right;
                     TreeNode pp = p.parent;
                     if (s == pr) {
                        p.parent = s;
                        s.right = p;
                     } else {
                        TreeNode sp = s.parent;
                        if ((p.parent = sp) != null) {
                           if (s == sp.left) {
                              sp.left = p;
                           } else {
                              sp.right = p;
                           }
                        }

                        if ((s.right = pr) != null) {
                           pr.parent = s;
                        }
                     }

                     p.left = null;
                     if ((p.right = sr) != null) {
                        sr.parent = p;
                     }

                     if ((s.left = pl) != null) {
                        pl.parent = s;
                     }

                     if ((s.parent = pp) == null) {
                        r = s;
                     } else if (p == pp.left) {
                        pp.left = s;
                     } else {
                        pp.right = s;
                     }

                     if (sr != null) {
                        replacement = sr;
                     } else {
                        replacement = p;
                     }
                  } else if (pl != null) {
                     replacement = pl;
                  } else if (pr != null) {
                     replacement = pr;
                  } else {
                     replacement = p;
                  }

                  if (replacement != p) {
                     s = replacement.parent = p.parent;
                     if (s == null) {
                        r = replacement;
                     } else if (p == s.left) {
                        s.left = replacement;
                     } else {
                        s.right = replacement;
                     }

                     p.left = p.right = p.parent = null;
                  }

                  this.root = p.red ? r : balanceDeletion(r, replacement);
                  if (p == replacement && (s = p.parent) != null) {
                     if (p == s.left) {
                        s.left = null;
                     } else if (p == s.right) {
                        s.right = null;
                     }

                     p.parent = null;
                  }
               } finally {
                  this.unlockRoot();
               }

               assert checkInvariants(this.root);

               return false;
            } else {
               return true;
            }
         }
      }

      static TreeNode rotateLeft(TreeNode root, TreeNode p) {
         TreeNode r;
         if (p != null && (r = p.right) != null) {
            TreeNode rl;
            if ((rl = p.right = r.left) != null) {
               rl.parent = p;
            }

            TreeNode pp;
            if ((pp = r.parent = p.parent) == null) {
               root = r;
               r.red = false;
            } else if (pp.left == p) {
               pp.left = r;
            } else {
               pp.right = r;
            }

            r.left = p;
            p.parent = r;
         }

         return root;
      }

      static TreeNode rotateRight(TreeNode root, TreeNode p) {
         TreeNode l;
         if (p != null && (l = p.left) != null) {
            TreeNode lr;
            if ((lr = p.left = l.right) != null) {
               lr.parent = p;
            }

            TreeNode pp;
            if ((pp = l.parent = p.parent) == null) {
               root = l;
               l.red = false;
            } else if (pp.right == p) {
               pp.right = l;
            } else {
               pp.left = l;
            }

            l.right = p;
            p.parent = l;
         }

         return root;
      }

      static TreeNode balanceInsertion(TreeNode root, TreeNode x) {
         x.red = true;

         TreeNode xp;
         while((xp = x.parent) != null) {
            TreeNode xpp;
            if (!xp.red || (xpp = xp.parent) == null) {
               return root;
            }

            TreeNode xppl;
            if (xp == (xppl = xpp.left)) {
               TreeNode xppr;
               if ((xppr = xpp.right) != null && xppr.red) {
                  xppr.red = false;
                  xp.red = false;
                  xpp.red = true;
                  x = xpp;
               } else {
                  if (x == xp.right) {
                     x = xp;
                     root = rotateLeft(root, xp);
                     xpp = (xp = xp.parent) == null ? null : xp.parent;
                  }

                  if (xp != null) {
                     xp.red = false;
                     if (xpp != null) {
                        xpp.red = true;
                        root = rotateRight(root, xpp);
                     }
                  }
               }
            } else if (xppl != null && xppl.red) {
               xppl.red = false;
               xp.red = false;
               xpp.red = true;
               x = xpp;
            } else {
               if (x == xp.left) {
                  x = xp;
                  root = rotateRight(root, xp);
                  xpp = (xp = xp.parent) == null ? null : xp.parent;
               }

               if (xp != null) {
                  xp.red = false;
                  if (xpp != null) {
                     xpp.red = true;
                     root = rotateLeft(root, xpp);
                  }
               }
            }
         }

         x.red = false;
         return x;
      }

      static TreeNode balanceDeletion(TreeNode root, TreeNode x) {
         while(x != null && x != root) {
            TreeNode xp;
            if ((xp = x.parent) == null) {
               x.red = false;
               return x;
            }

            if (x.red) {
               x.red = false;
               return root;
            }

            TreeNode xpl;
            TreeNode sl;
            TreeNode sr;
            if ((xpl = xp.left) == x) {
               TreeNode xpr;
               if ((xpr = xp.right) != null && xpr.red) {
                  xpr.red = false;
                  xp.red = true;
                  root = rotateLeft(root, xp);
                  xpr = (xp = x.parent) == null ? null : xp.right;
               }

               if (xpr == null) {
                  x = xp;
               } else {
                  sl = xpr.left;
                  sr = xpr.right;
                  if (sr != null && sr.red || sl != null && sl.red) {
                     if (sr == null || !sr.red) {
                        if (sl != null) {
                           sl.red = false;
                        }

                        xpr.red = true;
                        root = rotateRight(root, xpr);
                        xpr = (xp = x.parent) == null ? null : xp.right;
                     }

                     if (xpr != null) {
                        xpr.red = xp == null ? false : xp.red;
                        if ((sr = xpr.right) != null) {
                           sr.red = false;
                        }
                     }

                     if (xp != null) {
                        xp.red = false;
                        root = rotateLeft(root, xp);
                     }

                     x = root;
                  } else {
                     xpr.red = true;
                     x = xp;
                  }
               }
            } else {
               if (xpl != null && xpl.red) {
                  xpl.red = false;
                  xp.red = true;
                  root = rotateRight(root, xp);
                  xpl = (xp = x.parent) == null ? null : xp.left;
               }

               if (xpl == null) {
                  x = xp;
               } else {
                  sl = xpl.left;
                  sr = xpl.right;
                  if ((sl == null || !sl.red) && (sr == null || !sr.red)) {
                     xpl.red = true;
                     x = xp;
                  } else {
                     if (sl == null || !sl.red) {
                        if (sr != null) {
                           sr.red = false;
                        }

                        xpl.red = true;
                        root = rotateLeft(root, xpl);
                        xpl = (xp = x.parent) == null ? null : xp.left;
                     }

                     if (xpl != null) {
                        xpl.red = xp == null ? false : xp.red;
                        if ((sl = xpl.left) != null) {
                           sl.red = false;
                        }
                     }

                     if (xp != null) {
                        xp.red = false;
                        root = rotateRight(root, xp);
                     }

                     x = root;
                  }
               }
            }
         }

         return root;
      }

      static boolean checkInvariants(TreeNode t) {
         TreeNode tp = t.parent;
         TreeNode tl = t.left;
         TreeNode tr = t.right;
         TreeNode tb = t.prev;
         TreeNode tn = (TreeNode)t.next;
         if (tb != null && tb.next != t) {
            return false;
         } else if (tn != null && tn.prev != t) {
            return false;
         } else if (tp != null && t != tp.left && t != tp.right) {
            return false;
         } else if (tl != null && (tl.parent != t || tl.hash > t.hash)) {
            return false;
         } else if (tr == null || tr.parent == t && tr.hash >= t.hash) {
            if (t.red && tl != null && tl.red && tr != null && tr.red) {
               return false;
            } else if (tl != null && !checkInvariants(tl)) {
               return false;
            } else {
               return tr == null || checkInvariants(tr);
            }
         } else {
            return false;
         }
      }

      static {
         try {
            U = ConcurrentHashMapV8.getUnsafe();
            Class k = TreeBin.class;
            LOCKSTATE = U.objectFieldOffset(k.getDeclaredField("lockState"));
         } catch (Exception var1) {
            throw new Error(var1);
         }
      }
   }

   static final class TreeNode extends Node {
      TreeNode parent;
      TreeNode left;
      TreeNode right;
      TreeNode prev;
      boolean red;

      TreeNode(int hash, Object key, Object val, Node next, TreeNode parent) {
         super(hash, key, val, next);
         this.parent = parent;
      }

      Node find(int h, Object k) {
         return this.findTreeNode(h, k, (Class)null);
      }

      final TreeNode findTreeNode(int h, Object k, Class kc) {
         if (k != null) {
            TreeNode p = this;

            do {
               TreeNode pl = p.left;
               TreeNode pr = p.right;
               int ph;
               if ((ph = p.hash) > h) {
                  p = pl;
               } else if (ph < h) {
                  p = pr;
               } else {
                  Object pk;
                  if ((pk = p.key) == k || pk != null && k.equals(pk)) {
                     return p;
                  }

                  if (pl == null) {
                     p = pr;
                  } else if (pr == null) {
                     p = pl;
                  } else {
                     int dir;
                     if ((kc != null || (kc = ConcurrentHashMapV8.comparableClassFor(k)) != null) && (dir = ConcurrentHashMapV8.compareComparables(kc, k, pk)) != 0) {
                        p = dir < 0 ? pl : pr;
                     } else {
                        TreeNode q;
                        if ((q = pr.findTreeNode(h, k, kc)) != null) {
                           return q;
                        }

                        p = pl;
                     }
                  }
               }
            } while(p != null);
         }

         return null;
      }
   }

   static final class ReservationNode extends Node {
      ReservationNode() {
         super(-3, (Object)null, (Object)null, (Node)null);
      }

      Node find(int h, Object k) {
         return null;
      }
   }

   static final class ForwardingNode extends Node {
      final Node[] nextTable;

      ForwardingNode(Node[] tab) {
         super(-1, (Object)null, (Object)null, (Node)null);
         this.nextTable = tab;
      }

      Node find(int h, Object k) {
         Node[] tab = this.nextTable;

         label41:
         while(true) {
            Node e;
            int n;
            if (k != null && tab != null && (n = tab.length) != 0 && (e = ConcurrentHashMapV8.tabAt(tab, n - 1 & h)) != null) {
               int eh;
               Object ek;
               while((eh = e.hash) != h || (ek = e.key) != k && (ek == null || !k.equals(ek))) {
                  if (eh < 0) {
                     if (!(e instanceof ForwardingNode)) {
                        return e.find(h, k);
                     }

                     tab = ((ForwardingNode)e).nextTable;
                     continue label41;
                  }

                  if ((e = e.next) == null) {
                     return null;
                  }
               }

               return e;
            }

            return null;
         }
      }
   }

   static class Segment extends ReentrantLock implements Serializable {
      private static final long serialVersionUID = 2249069246763182397L;
      final float loadFactor;

      Segment(float lf) {
         this.loadFactor = lf;
      }
   }

   static class Node implements Map.Entry {
      final int hash;
      final Object key;
      volatile Object val;
      volatile Node next;

      Node(int hash, Object key, Object val, Node next) {
         this.hash = hash;
         this.key = key;
         this.val = val;
         this.next = next;
      }

      public final Object getKey() {
         return this.key;
      }

      public final Object getValue() {
         return this.val;
      }

      public final int hashCode() {
         return this.key.hashCode() ^ this.val.hashCode();
      }

      public final String toString() {
         return this.key + "=" + this.val;
      }

      public final Object setValue(Object value) {
         throw new UnsupportedOperationException();
      }

      public final boolean equals(Object o) {
         Object k;
         Object v;
         Object u;
         Map.Entry e;
         return o instanceof Map.Entry && (k = (e = (Map.Entry)o).getKey()) != null && (v = e.getValue()) != null && (k == this.key || k.equals(this.key)) && (v == (u = this.val) || v.equals(u));
      }

      Node find(int h, Object k) {
         Node e = this;
         if (k != null) {
            do {
               Object ek;
               if (e.hash == h && ((ek = e.key) == k || ek != null && k.equals(ek))) {
                  return e;
               }
            } while((e = e.next) != null);
         }

         return null;
      }
   }

   public interface IntByIntToInt {
      int apply(int var1, int var2);
   }

   public interface LongByLongToLong {
      long apply(long var1, long var3);
   }

   public interface DoubleByDoubleToDouble {
      double apply(double var1, double var3);
   }

   public interface ObjectByObjectToInt {
      int apply(Object var1, Object var2);
   }

   public interface ObjectByObjectToLong {
      long apply(Object var1, Object var2);
   }

   public interface ObjectByObjectToDouble {
      double apply(Object var1, Object var2);
   }

   public interface ObjectToInt {
      int apply(Object var1);
   }

   public interface ObjectToLong {
      long apply(Object var1);
   }

   public interface ObjectToDouble {
      double apply(Object var1);
   }

   public interface BiFun {
      Object apply(Object var1, Object var2);
   }

   public interface Fun {
      Object apply(Object var1);
   }

   public interface BiAction {
      void apply(Object var1, Object var2);
   }

   public interface Action {
      void apply(Object var1);
   }

   public interface ConcurrentHashMapSpliterator {
      ConcurrentHashMapSpliterator trySplit();

      long estimateSize();

      void forEachRemaining(Action var1);

      boolean tryAdvance(Action var1);
   }
}
