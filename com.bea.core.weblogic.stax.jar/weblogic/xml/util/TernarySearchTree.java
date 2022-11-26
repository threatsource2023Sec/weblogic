package weblogic.xml.util;

public final class TernarySearchTree {
   private Node head;
   private int size;

   public int size() {
      return this.size;
   }

   public final void put(CharSequence key, Object value) {
      if (key == null) {
         throw new IllegalArgumentException("null sequence");
      } else if (value == null) {
         throw new IllegalArgumentException("null values not allowed");
      } else {
         int key_len = key.length();
         if (key_len < 1) {
            throw new IllegalArgumentException("must have non zero length");
         } else {
            Node curr = this.head;
            if (curr == null) {
               curr = new Node(key.charAt(0));
               this.head = curr;
            }

            Node prev = null;
            int index = 0;

            char i;
            while(true) {
               prev = curr;
               i = key.charAt(index);
               if (i < curr.splitchar) {
                  curr = curr.left;
                  if (curr == null) {
                     prev.left = new Node(i);
                     curr = prev.left;
                     ++index;
                     break;
                  }
               } else if (i > curr.splitchar) {
                  curr = curr.right;
                  if (curr == null) {
                     prev.right = new Node(i);
                     curr = prev.right;
                     ++index;
                     break;
                  }
               } else {
                  assert i == curr.splitchar;

                  ++index;
                  if (index == key_len) {
                     if (curr.item == null) {
                        ++this.size;
                     }

                     curr.item = value;
                     return;
                  }

                  curr = curr.middle;
                  if (curr == null) {
                     curr = prev;
                     break;
                  }
               }
            }

            assert curr != null;

            while(index != key_len) {
               i = key.charAt(index);
               Node new_middle = new Node(i);
               curr.middle = new_middle;
               curr = new_middle;
               ++index;
            }

            curr.item = value;
            ++this.size;
         }
      }
   }

   public final void put(char[] key, int begin, int length, Object value) {
      if (key == null) {
         throw new IllegalArgumentException("null sequence");
      } else if (value == null) {
         throw new IllegalArgumentException("null values not allowed");
      } else if (length < 1) {
         throw new IllegalArgumentException("must have non zero length");
      } else {
         Node curr = this.head;
         if (curr == null) {
            curr = new Node(key[begin]);
            this.head = curr;
         }

         Node prev = null;
         int index = begin;

         char i;
         while(true) {
            prev = curr;
            i = key[index];
            if (i < curr.splitchar) {
               curr = curr.left;
               if (curr == null) {
                  prev.left = new Node(i);
                  curr = prev.left;
                  ++index;
                  break;
               }
            } else if (i > curr.splitchar) {
               curr = curr.right;
               if (curr == null) {
                  prev.right = new Node(i);
                  curr = prev.right;
                  ++index;
                  break;
               }
            } else {
               assert i == curr.splitchar;

               ++index;
               if (index == length) {
                  if (curr.item == null) {
                     ++this.size;
                  }

                  curr.item = value;
                  return;
               }

               curr = curr.middle;
               if (curr == null) {
                  curr = prev;
                  break;
               }
            }
         }

         assert curr != null;

         while(index != length) {
            i = key[index];
            Node new_middle = new Node(i);
            curr.middle = new_middle;
            curr = new_middle;
            ++index;
         }

         curr.item = value;
         ++this.size;
      }
   }

   public Object get(CharSequence key) {
      assert key != null;

      int key_len = key.length();

      assert key_len > 0;

      Node curr = this.head;
      int index = 0;

      while(curr != null) {
         char i = key.charAt(index);
         if (i < curr.splitchar) {
            curr = curr.left;
         } else if (i > curr.splitchar) {
            curr = curr.right;
         } else {
            assert i == curr.splitchar;

            ++index;
            if (index == key_len) {
               return curr.item;
            }

            curr = curr.middle;
         }
      }

      return null;
   }

   public Object get(char[] key, int begin, int length) {
      assert key != null;

      assert length > 0;

      Node curr = this.head;
      int index = begin;
      int end_index = begin + length;

      while(curr != null) {
         char i = key[index];
         if (i < curr.splitchar) {
            curr = curr.left;
         } else if (i > curr.splitchar) {
            curr = curr.right;
         } else {
            assert i == curr.splitchar;

            ++index;
            if (index == end_index) {
               return curr.item;
            }

            curr = curr.middle;
         }
      }

      return null;
   }

   private static final class Node {
      final char splitchar;
      Node left;
      Node middle;
      Node right;
      Object item;

      Node(char splitchar) {
         this.splitchar = splitchar;
      }

      boolean internal() {
         return this.left != null || this.right != null || this.middle != null;
      }

      public String toString() {
         return "c=" + this.splitchar + "|L=" + (this.left == null ? "" : String.valueOf(this.left.splitchar)) + "|M=" + (this.middle == null ? "" : String.valueOf(this.middle.splitchar)) + "|R=" + (this.right == null ? "" : String.valueOf(this.right.splitchar));
      }
   }
}
