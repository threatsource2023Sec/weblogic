package com.bea.core.repackaged.aspectj.weaver;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

public final class Iterators {
   private Iterators() {
   }

   public static Filter dupFilter() {
      return new Filter() {
         final Set seen = new HashSet();

         public Iterator filter(final Iterator in) {
            return new Iterator() {
               boolean fresh = false;
               Object peek;

               public boolean hasNext() {
                  if (this.fresh) {
                     return true;
                  } else {
                     while(in.hasNext()) {
                        this.peek = in.next();
                        if (!seen.contains(this.peek)) {
                           this.fresh = true;
                           return true;
                        }

                        this.peek = null;
                     }

                     return false;
                  }
               }

               public Object next() {
                  if (!this.hasNext()) {
                     throw new NoSuchElementException();
                  } else {
                     Object ret = this.peek;
                     seen.add(this.peek);
                     this.peek = null;
                     this.fresh = false;
                     return ret;
                  }
               }

               public void remove() {
                  throw new UnsupportedOperationException();
               }
            };
         }
      };
   }

   public static Iterator array(final Object[] o) {
      return new Iterator() {
         int i = 0;
         int len = o == null ? 0 : o.length;

         public boolean hasNext() {
            return this.i < this.len;
         }

         public Object next() {
            if (this.i < this.len) {
               return o[this.i++];
            } else {
               throw new NoSuchElementException();
            }
         }

         public void remove() {
            throw new UnsupportedOperationException();
         }
      };
   }

   public static Iterator array(final ResolvedType[] o, final boolean genericsAware) {
      return new Iterator() {
         int i = 0;
         int len = o == null ? 0 : o.length;

         public boolean hasNext() {
            return this.i < this.len;
         }

         public ResolvedType next() {
            if (this.i >= this.len) {
               throw new NoSuchElementException();
            } else {
               ResolvedType oo = o[this.i++];
               return genericsAware || !oo.isParameterizedType() && !oo.isGenericType() ? oo : oo.getRawType();
            }
         }

         public void remove() {
            throw new UnsupportedOperationException();
         }
      };
   }

   public static Iterator mapOver(final Iterator a, final Getter g) {
      return new Iterator() {
         Iterator delegate = new Iterator() {
            public boolean hasNext() {
               if (!a.hasNext()) {
                  return false;
               } else {
                  Object o = a.next();
                  delegate = Iterators.append1(g.get(o), this);
                  return delegate.hasNext();
               }
            }

            public Object next() {
               if (!this.hasNext()) {
                  throw new UnsupportedOperationException();
               } else {
                  return delegate.next();
               }
            }

            public void remove() {
               throw new UnsupportedOperationException();
            }
         };

         public boolean hasNext() {
            return this.delegate.hasNext();
         }

         public Object next() {
            return this.delegate.next();
         }

         public void remove() {
            throw new UnsupportedOperationException();
         }
      };
   }

   public static Iterator recur(final Object a, final Getter g) {
      return new Iterator() {
         Iterator delegate = Iterators.one(a);

         public boolean hasNext() {
            return this.delegate.hasNext();
         }

         public Object next() {
            Object next = this.delegate.next();
            this.delegate = Iterators.append(g.get(next), this.delegate);
            return next;
         }

         public void remove() {
            throw new UnsupportedOperationException();
         }
      };
   }

   public static Iterator append(Iterator a, Iterator b) {
      return !b.hasNext() ? a : append1(a, b);
   }

   public static Iterator append1(final Iterator a, final Iterator b) {
      return !a.hasNext() ? b : new Iterator() {
         public boolean hasNext() {
            return a.hasNext() || b.hasNext();
         }

         public Object next() {
            if (a.hasNext()) {
               return a.next();
            } else if (b.hasNext()) {
               return b.next();
            } else {
               throw new NoSuchElementException();
            }
         }

         public void remove() {
            throw new UnsupportedOperationException();
         }
      };
   }

   public static Iterator snoc(final Iterator first, final Object last) {
      return new Iterator() {
         Object last1 = last;

         public boolean hasNext() {
            return first.hasNext() || this.last1 != null;
         }

         public Object next() {
            if (first.hasNext()) {
               return first.next();
            } else if (this.last1 == null) {
               throw new NoSuchElementException();
            } else {
               Object ret = this.last1;
               this.last1 = null;
               return ret;
            }
         }

         public void remove() {
            throw new UnsupportedOperationException();
         }
      };
   }

   public static Iterator one(final Object it) {
      return new Iterator() {
         boolean avail = true;

         public boolean hasNext() {
            return this.avail;
         }

         public Object next() {
            if (!this.avail) {
               throw new NoSuchElementException();
            } else {
               this.avail = false;
               return it;
            }
         }

         public void remove() {
            throw new UnsupportedOperationException();
         }
      };
   }

   public static class ResolvedTypeArrayIterator implements Iterator {
      private ResolvedType[] array;
      private int index;
      private int len;
      private boolean wantGenerics;
      private List alreadySeen;

      public ResolvedTypeArrayIterator(ResolvedType[] array, List alreadySeen, boolean wantGenerics) {
         assert array != null;

         this.array = array;
         this.wantGenerics = wantGenerics;
         this.len = array.length;
         this.index = 0;
         this.alreadySeen = alreadySeen;
         this.moveToNextNewOne();
      }

      private void moveToNextNewOne() {
         while(true) {
            if (this.index < this.len) {
               ResolvedType interfaceType = this.array[this.index];
               if (!this.wantGenerics && interfaceType.isParameterizedOrGenericType()) {
                  interfaceType = interfaceType.getRawType();
               }

               String signature = interfaceType.getSignature();
               if (this.alreadySeen.contains(signature)) {
                  ++this.index;
                  continue;
               }
            }

            return;
         }
      }

      public boolean hasNext() {
         return this.index < this.len;
      }

      public ResolvedType next() {
         if (this.index >= this.len) {
            throw new NoSuchElementException();
         } else {
            ResolvedType oo = this.array[this.index++];
            if (!this.wantGenerics && (oo.isParameterizedType() || oo.isGenericType())) {
               oo = oo.getRawType();
            }

            this.alreadySeen.add(oo.getSignature());
            this.moveToNextNewOne();
            return oo;
         }
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }
   }

   public interface Filter {
      Iterator filter(Iterator var1);
   }

   public interface Getter {
      Iterator get(Object var1);
   }
}
