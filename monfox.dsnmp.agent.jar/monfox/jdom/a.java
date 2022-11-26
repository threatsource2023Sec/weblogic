package monfox.jdom;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

class a extends LinkedList {
   protected List backingList;
   protected Element parent;

   public a(List var1, Element var2) {
      this.backingList = var1;
      this.parent = var2;
   }

   public a(List var1) {
      this(var1, (Element)null);
   }

   public Object removeFirst() {
      Object var1 = super.removeFirst();
      this.backingList.remove(var1);
      if (var1 instanceof Element) {
         ((Element)var1).setParent((Element)null);
      }

      return var1;
   }

   public Object removeLast() {
      Object var1 = super.removeLast();
      this.backingList.remove(var1);
      if (var1 instanceof Element) {
         ((Element)var1).setParent((Element)null);
      }

      return var1;
   }

   public void addFirst(Object var1) {
      label15: {
         int var2 = this.backingList.indexOf(this.getFirst());
         super.addFirst(var1);
         if (var2 != -1) {
            this.backingList.add(var2, var1);
            if (!Element.b) {
               break label15;
            }
         }

         this.backingList.add(var1);
      }

      if (var1 instanceof Element) {
         ((Element)var1).setParent(this.parent);
      }

   }

   public void addLast(Object var1) {
      label17: {
         int var2 = this.backingList.indexOf(this.getLast());
         super.addLast(var1);
         if (var2 != -1 && var2 < this.backingList.size()) {
            this.backingList.add(var2 + 1, var1);
            if (!Element.b) {
               break label17;
            }
         }

         this.backingList.add(var1);
      }

      if (var1 instanceof Element) {
         ((Element)var1).setParent(this.parent);
      }

   }

   public boolean add(Object var1) {
      this.backingList.add(var1);
      if (var1 instanceof Element) {
         ((Element)var1).setParent(this.parent);
      }

      return super.add(var1);
   }

   public boolean remove(Object var1) {
      this.backingList.remove(var1);
      if (var1 instanceof Element) {
         ((Element)var1).setParent((Element)null);
      }

      return super.remove(var1);
   }

   public boolean addAll(Collection var1) {
      return this.backingList.isEmpty() ? this.addAll(0, var1) : this.addAll(this.backingList.indexOf(this.getLast()), var1);
   }

   public boolean addAll(int var1, Collection var2) {
      boolean var5;
      label29: {
         var5 = Element.b;
         if (this.backingList.isEmpty()) {
            this.backingList.addAll(var2);
            if (!var5) {
               break label29;
            }
         }

         this.backingList.addAll(var1, var2);
      }

      Iterator var3 = var2.iterator();

      boolean var10000;
      while(true) {
         if (var3.hasNext()) {
            Object var4 = var3.next();
            var10000 = var4 instanceof Element;
            if (var5) {
               break;
            }

            if (var10000) {
               ((Element)var4).setParent(this.parent);
            }

            if (!var5) {
               continue;
            }
         }

         var10000 = super.addAll(var1, var2);
         break;
      }

      return var10000;
   }

   public void clear() {
      boolean var3 = Element.b;
      Iterator var1 = this.iterator();

      while(true) {
         if (var1.hasNext()) {
            Object var2 = var1.next();
            this.backingList.remove(var2);
            if (var3) {
               break;
            }

            if (var2 instanceof Element) {
               ((Element)var2).setParent((Element)null);
            }

            if (!var3) {
               continue;
            }
         }

         super.clear();
         break;
      }

   }

   public Object set(int var1, Object var2) {
      Object var3 = this.get(var1);
      int var4 = this.backingList.indexOf(var3);
      if (var4 != -1) {
         this.backingList.set(var4, var2);
      }

      if (var3 instanceof Element) {
         ((Element)var3).setParent((Element)null);
      }

      if (var2 instanceof Element) {
         ((Element)var2).setParent(this.parent);
      }

      return super.set(var1, var2);
   }

   public void add(int var1, Object var2) {
      if (var1 == this.size()) {
         this.addLast(var2);
         if (!Element.b) {
            return;
         }
      }

      this.backingList.add(this.backingList.indexOf(this.get(var1)), var2);
      if (var2 instanceof Element) {
         ((Element)var2).setParent(this.parent);
      }

      super.add(var1, var2);
   }

   public Object remove(int var1) {
      Object var2 = super.remove(var1);
      this.backingList.remove(var2);
      if (var2 instanceof Element) {
         ((Element)var2).setParent((Element)null);
      }

      return var2;
   }

   protected void addPartial(Object var1) {
      super.add(var1);
   }

   protected boolean addAllPartial(Collection var1) {
      Iterator var2 = var1.iterator();

      while(var2.hasNext()) {
         this.addPartial(var2.next());
         if (Element.b) {
            break;
         }
      }

      return true;
   }
}
