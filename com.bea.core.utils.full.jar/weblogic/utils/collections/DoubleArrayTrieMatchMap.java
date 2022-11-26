package weblogic.utils.collections;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public final class DoubleArrayTrieMatchMap {
   private static final char TERMINATING_CHAR = '\u0000';
   private static final int FREE_STATE = 0;
   private static final int START_STATE = 1;
   private static final int NEXT_STATE = 2;
   private static final int NO_INPUT = -1;
   private static final boolean DEBUG = false;
   private Entry[] data;
   private int[] base;
   private int[] check;
   private int[] firstInput;
   private int[] nextInput;
   private int size;
   private int nextEntry;
   private boolean ignoreCase;

   private DoubleArrayTrieMatchMap() {
      this(false);
   }

   public DoubleArrayTrieMatchMap(boolean ignoreCase) {
      this.ignoreCase = ignoreCase;
      this.data = new Entry[64];
      this.base = new int[256];
      this.check = new int[256];
      this.firstInput = new int[256];
      this.nextInput = new int[256];
      this.base[1] = 1;

      for(int i = 0; i < this.firstInput.length; ++i) {
         this.firstInput[i] = -1;
      }

   }

   public boolean isIgnoreCase() {
      return this.ignoreCase;
   }

   public Object clone() {
      DoubleArrayTrieMatchMap clone = new DoubleArrayTrieMatchMap();
      clone.ignoreCase = this.ignoreCase;
      clone.data = new Entry[this.data.length];
      System.arraycopy(this.data, 0, clone.data, 0, this.data.length);
      clone.base = clone(this.base);
      clone.check = clone(this.check);
      clone.firstInput = clone(this.firstInput);
      clone.nextInput = clone(this.nextInput);
      clone.size = this.size;
      clone.nextEntry = this.nextEntry;
      return clone;
   }

   private static int[] clone(int[] a) {
      int[] clone = new int[a.length];
      System.arraycopy(a, 0, clone, 0, a.length);
      return clone;
   }

   public Map.Entry match(CharSequence key) {
      if (key.equals("")) {
         return this.data[0];
      } else {
         int len = key.length();
         int state = 1;
         Entry match = null;
         int nextState;
         int bVal;
         int i;
         Entry e;
         if (this.ignoreCase) {
            for(i = 0; i < len; ++i) {
               bVal = this.base[state];
               if (bVal < 0) {
                  e = this.data[-bVal];
                  if (e.tailMatches(key)) {
                     match = e;
                  }

                  return match;
               }

               if (this.check[bVal] == state) {
                  match = this.data[-this.base[bVal]];
               }

               nextState = bVal + Character.toUpperCase(key.charAt(i));
               if (nextState >= this.check.length || this.check[nextState] != state) {
                  return match;
               }

               state = nextState;
            }
         } else {
            for(i = 0; i < len; ++i) {
               bVal = this.base[state];
               if (bVal < 0) {
                  e = this.data[-bVal];
                  if (e.tailMatches(key)) {
                     match = e;
                  }

                  return match;
               }

               if (this.check[bVal] == state) {
                  match = this.data[-this.base[bVal]];
               }

               nextState = bVal + key.charAt(i);
               if (nextState >= this.check.length || this.check[nextState] != state) {
                  return match;
               }

               state = nextState;
            }
         }

         bVal = this.base[state];
         if (bVal < 0) {
            e = this.data[-bVal];
            if (e.tailMatches(key)) {
               match = e;
            }
         } else if (this.check[bVal] == state) {
            e = this.data[-this.base[bVal]];
            if (e.tailMatches(key)) {
               match = e;
            }
         }

         return match;
      }
   }

   public Object get(CharSequence key) {
      if (key.equals("")) {
         Object ret = this.data[0] == null ? null : this.data[0].getValue();
         return ret;
      } else {
         int len = key.length();
         int state = 1;
         int nextState;
         int bVal;
         Entry e;
         int i;
         if (this.ignoreCase) {
            for(i = 0; i < len; ++i) {
               bVal = this.base[state];
               if (bVal < 0) {
                  e = this.data[-bVal];
                  if (e.tailEquals(key)) {
                     return e.getValue();
                  }

                  return null;
               }

               nextState = bVal + Character.toUpperCase(key.charAt(i));
               if (nextState >= this.check.length || this.check[nextState] != state) {
                  return null;
               }

               state = nextState;
            }
         } else {
            for(i = 0; i < len; ++i) {
               bVal = this.base[state];
               if (bVal < 0) {
                  e = this.data[-bVal];
                  if (e.tailEquals(key)) {
                     return e.getValue();
                  }

                  return null;
               }

               nextState = bVal + key.charAt(i);
               if (nextState >= this.check.length || this.check[nextState] != state) {
                  return null;
               }

               state = nextState;
            }
         }

         bVal = this.base[state];
         if (bVal < 0) {
            e = this.data[-bVal];
         } else {
            if (this.check[bVal] != state) {
               return null;
            }

            e = this.data[-this.base[bVal]];
         }

         return e.tailEquals(key) ? e.getValue() : null;
      }
   }

   public Object put(CharSequence cs, Object value) {
      if (cs.equals("")) {
         Object ret = this.data[0] == null ? null : this.data[0].getValue();
         this.data[0] = new Entry(cs, 0, value, this.ignoreCase);
         return ret;
      } else {
         int state = 1;
         int i = 0;

         while(true) {
            int bVal = this.base[state];
            int nextState;
            char c;
            if (bVal < 0) {
               Entry e = this.data[-bVal];
               if (e.tailEquals(cs)) {
                  this.data[-bVal] = new Entry(cs, i, value, this.ignoreCase);
                  return e.getValue();
               }

               while(true) {
                  char chTail = e.incrementTailIndex();
                  if (this.ignoreCase) {
                     chTail = Character.toUpperCase(chTail);
                  }

                  if (i < cs.length()) {
                     c = cs.charAt(i++);
                     if (this.ignoreCase) {
                        c = Character.toUpperCase(c);
                     }
                  } else {
                     c = 0;
                  }

                  int pos;
                  if (chTail != c) {
                     pos = this.xCheck(chTail, c);
                     this.base[state] = pos;
                     nextState = pos + chTail;
                     this.base[nextState] = bVal;
                     this.check[nextState] = state;
                     this.writeFirstAndNextInput(state, nextState, chTail);
                     nextState = pos + c;
                     this.insertTail(new Entry(cs, i, value, this.ignoreCase), state, nextState, c);
                     return null;
                  }

                  pos = this.xCheck(c);
                  this.base[state] = pos;
                  nextState = pos + c;
                  this.check[nextState] = state;
                  this.writeFirstAndNextInput(state, nextState, c);
                  state = nextState;
               }
            }

            if (i == cs.length()) {
               c = 0;
            } else {
               c = cs.charAt(i++);
               if (this.ignoreCase) {
                  c = Character.toUpperCase(c);
               }
            }

            nextState = bVal + c;
            int m = this.safeCheck(nextState);
            if (m != state) {
               if (m != 0) {
                  int nextInputsFromM = this.getAllNextInputsCount(this.base[m], this.firstInput[m]);
                  int nextInputsFromS = this.getAllNextInputsCount(bVal, this.firstInput[state]);
                  if (nextInputsFromM < nextInputsFromS + 1) {
                     state = this.rebase(state, m, -1);
                  } else {
                     state = this.rebase(state, state, c);
                  }

                  nextState = this.base[state] + c;
               }

               this.insertTail(new Entry(cs, i, value, this.ignoreCase), state, nextState, c);
               return null;
            }

            state = nextState;
         }
      }
   }

   public Object remove(CharSequence key) {
      if (key.equals("")) {
         Object ret = this.data[0] == null ? null : this.data[0].getValue();
         this.data[0] = null;
         return ret;
      } else {
         int state = 1;
         int len = key.length();
         int[] visited = new int[len + 1];
         char c = 0;

         int bVal;
         int i;
         Entry e;
         for(i = 0; i < len; ++i) {
            bVal = this.base[state];
            if (bVal < 0) {
               e = this.data[-bVal];
               if (!e.tailEquals(key)) {
                  return null;
               }

               --this.size;
               this.data[-bVal] = null;
               --i;
               this.freeState(key, visited, i, state, c);
               return e.getValue();
            }

            c = key.charAt(i);
            if (this.ignoreCase) {
               c = Character.toUpperCase(c);
            }

            int nextState = bVal + c;
            if (nextState >= this.check.length || this.check[nextState] != state) {
               return null;
            }

            visited[i] = state;
            state = nextState;
         }

         bVal = this.base[state];
         if (bVal > 0) {
            if (this.check[bVal] != state) {
               return null;
            }

            visited[i++] = state;
            state = bVal;
            bVal = this.base[bVal];
            c = 0;
         }

         e = this.data[-bVal];
         if (!e.tailEquals(key)) {
            return null;
         } else {
            --this.size;
            this.data[-bVal] = null;
            --i;
            this.freeState(key, visited, i, state, c);
            return e.getValue();
         }
      }
   }

   private void freeState(CharSequence key, int[] visited, int pos, int state, char c) {
      while(true) {
         if (this.isLeaf(state)) {
            this.base[state] = 0;
            this.check[state] = 0;
            int fromState = visited[pos];
            int b = this.base[fromState];
            int fi = this.firstInput[fromState];
            if (fi == c) {
               this.firstInput[fromState] = this.nextInput[b + fi];
               this.nextInput[b + fi] = 0;
            } else {
               while(true) {
                  int j = this.nextInput[b + fi];
                  if (j == c) {
                     this.nextInput[b + fi] = this.nextInput[b + j];
                     this.nextInput[b + j] = 0;
                     break;
                  }

                  fi = j;
               }
            }

            state = fromState;
            if (pos != 0) {
               --pos;
               c = key.charAt(pos);
               if (this.ignoreCase) {
                  c = Character.toUpperCase(c);
               }
               continue;
            }
         }

         return;
      }
   }

   private boolean isLeaf(int state) {
      return this.firstInput[state] == -1;
   }

   public Set entrySet() {
      return new AbstractSet() {
         public int size() {
            return DoubleArrayTrieMatchMap.this.data[0] == null ? DoubleArrayTrieMatchMap.this.size : DoubleArrayTrieMatchMap.this.size + 1;
         }

         public Iterator iterator() {
            return DoubleArrayTrieMatchMap.this.new EntryIterator();
         }
      };
   }

   private void insertTail(Entry e, int currentState, int finalState, char c) {
      if (this.nextEntry == this.data.length - 1) {
         this.data = grow(this.data);
      }

      this.data[++this.nextEntry] = e;
      ++this.size;
      this.safeCheck(finalState);
      this.base[finalState] = -this.nextEntry;
      this.check[finalState] = currentState;
      this.writeFirstAndNextInput(currentState, finalState, c);
   }

   private int rebase(int current, int s, int c) {
      int oldBaseVal = this.base[s];
      int newBaseVal = this.xCheck(oldBaseVal, this.firstInput[s], c);
      this.base[s] = newBaseVal;

      for(int ch = this.firstInput[s]; ch != -1; ch = this.nextInput[oldBaseVal + ch]) {
         int oldNode = oldBaseVal + ch;
         int newNode = newBaseVal + ch;
         int oldB = this.base[oldNode];
         this.base[newNode] = oldB;
         this.check[newNode] = this.check[oldNode];
         this.firstInput[newNode] = this.firstInput[oldNode];
         this.nextInput[newNode] = this.nextInput[oldNode];
         if (current != s && oldNode == current) {
            current = newNode;
         }

         for(int p = this.firstInput[oldNode]; p != -1; p = this.nextInput[oldB + p]) {
            this.check[oldB + p] = newNode;
         }

         this.base[oldNode] = 0;
         this.check[oldNode] = 0;
         this.firstInput[oldNode] = -1;
      }

      return current;
   }

   private int getAllNextInputsCount(int base, int fi) {
      int i = 0;

      for(int ch = fi; ch != -1; ch = this.nextInput[base + ch]) {
         ++i;
      }

      return i;
   }

   private int xCheck(char chTail) {
      int b1;
      for(b1 = 2; this.safeCheck(b1 + chTail) != 0; ++b1) {
      }

      return b1;
   }

   private int xCheck(char chTail, char c) {
      int b1 = 2;
      int b = 0;

      while(b != b1) {
         for(b = b1; this.safeCheck(b1 + chTail) != 0; ++b1) {
         }

         while(this.safeCheck(b1 + c) != 0) {
            ++b1;
         }
      }

      return b;
   }

   private int xCheck(int baseVal, int fi, int c) {
      int b1 = 2;
      int b = 0;

      int i;
      while(b != b1) {
         b = b1;
         if (c != -1) {
            while(this.safeCheck(b1 + c) != 0) {
               ++b1;
            }
         }

         for(i = fi; i != -1; i = this.nextInput[baseVal + i]) {
            while(this.safeCheck(b1 + i) != 0) {
               ++b1;
            }
         }
      }

      for(i = fi; i != -1; i = this.nextInput[baseVal + i]) {
      }

      return b;
   }

   private void writeFirstAndNextInput(int firstPos, int nextPos, char c) {
      this.nextInput[nextPos] = this.firstInput[firstPos];
      this.firstInput[firstPos] = c;
   }

   private int safeCheck(int index) {
      if (index >= this.check.length) {
         this.base = grow(this.base, index * 2);
         this.check = grow(this.check, index * 2);
         this.firstInput = growAndInitialize(this.firstInput, index * 2, -1);
         this.nextInput = grow(this.nextInput, index * 2);
      }

      return this.check[index];
   }

   private static int[] grow(int[] a, int size) {
      int[] b = new int[size];
      System.arraycopy(a, 0, b, 0, a.length);
      return b;
   }

   private static int[] growAndInitialize(int[] a, int size, int initialVal) {
      int oldLen = a.length;
      int[] b = grow(a, size);

      for(int i = oldLen; i < size; ++i) {
         b[i] = initialVal;
      }

      return b;
   }

   private static Entry[] grow(Entry[] data) {
      Entry[] ret = new Entry[data.length * 2];
      System.arraycopy(data, 0, ret, 0, data.length);
      return ret;
   }

   private static void p(String s) {
   }

   private void dump(String msg) {
      p("<DUMPING BEGIN> : " + msg);
      printArray("BASE", this.base);
      printArray("CHECK", this.check);
      printArray("FIRSTINPUT", this.firstInput);
      printArray("NEXTINPUT", this.nextInput);
      printArray("DATA", this.data);
      p("********* <DUMPING END>*************");
   }

   private static void printArray(String name, int[] data) {
      StringBuilder indexBuffer = new StringBuilder("[");
      StringBuilder valueBuffer = new StringBuilder("[");

      for(int i = 0; i < data.length; ++i) {
         if (data[i] != 0) {
            indexBuffer.append(i).append(", ");
            valueBuffer.append(data[i]).append(", ");
         }
      }

      indexBuffer.append("]");
      valueBuffer.append("]");
      p("Dumping " + name);
      p(indexBuffer.toString());
      p(valueBuffer.toString());
   }

   private static void printArray(String name, Entry[] data) {
      p("Dumping " + name);

      for(int i = 0; i < data.length; ++i) {
         if (data[i] != null) {
            p(i + ". " + data[i].toString());
         }
      }

   }

   private final class EntryIterator implements Iterator {
      private int index;

      EntryIterator() {
         int i;
         for(i = DoubleArrayTrieMatchMap.this.data.length - 1; i >= 0 && DoubleArrayTrieMatchMap.this.data[i] == null; --i) {
         }

         this.index = i;
      }

      public boolean hasNext() {
         return this.index >= 0;
      }

      public Object next() {
         Entry e;
         for(e = DoubleArrayTrieMatchMap.this.data[this.index--]; this.index >= 0 && DoubleArrayTrieMatchMap.this.data[this.index] == null; --this.index) {
         }

         return e;
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }
   }

   private static final class Entry implements Map.Entry {
      private final CharSequence key;
      private Object value;
      private int tailIndex;
      private boolean ignoreCase;

      Entry(CharSequence key, int tailIndex, Object value, boolean ignoreCase) {
         this.key = key;
         if (tailIndex > key.length()) {
            System.err.println("Got invalid tailIndex " + tailIndex + " for key " + key);
            this.tailIndex = key.length();
         } else {
            this.tailIndex = tailIndex;
         }

         this.value = value;
         this.ignoreCase = ignoreCase;
      }

      public Object getKey() {
         return this.key;
      }

      public Object getValue() {
         return this.value;
      }

      public Object setValue(Object v) {
         Object temp = this.value;
         this.value = v;
         return temp;
      }

      private CharSequence getTail() {
         return this.tailIndex == this.key.length() ? null : this.key.subSequence(this.tailIndex, this.key.length());
      }

      private boolean tailEquals(CharSequence other) {
         return this.key.length() == other.length() && this.tailMatches(other);
      }

      private boolean tailMatches(CharSequence other) {
         if (this.key.length() > other.length()) {
            return false;
         } else {
            int i;
            if (this.ignoreCase) {
               for(i = this.tailIndex; i < this.key.length(); ++i) {
                  if (Character.toUpperCase(this.key.charAt(i)) != Character.toUpperCase(other.charAt(i))) {
                     return false;
                  }
               }
            } else {
               for(i = this.tailIndex; i < this.key.length(); ++i) {
                  if (this.key.charAt(i) != other.charAt(i)) {
                     return false;
                  }
               }
            }

            return true;
         }
      }

      public String toString() {
         return "Key: " + this.key + " Value: " + this.value + " Tail: " + (this.getTail() == null ? "<none>" : this.getTail());
      }

      private char incrementTailIndex() {
         return this.tailIndex == this.key.length() ? '\u0000' : this.key.charAt(this.tailIndex++);
      }
   }
}
