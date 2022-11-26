package weblogic.xml.babel.scanner;

public final class Trie {
   public static final int ALPHA_SIZE = 128;
   private final Node m_Root = new Node();
   private int size = 1;

   public int size() {
      return this.size;
   }

   public Object put(String key, Object value) {
      int len = key.length();
      Node node = this.m_Root;

      label30:
      for(int i = 0; i < len; ++i) {
         try {
            Node nextNode = node.m_nextChar[key.charAt(i)];
            if (nextNode == null) {
               while(true) {
                  if (i >= len) {
                     break label30;
                  }

                  Node newNode = new Node();
                  ++this.size;
                  node.m_nextChar[key.charAt(i)] = newNode;
                  node = newNode;
                  ++i;
               }
            }

            node = nextNode;
         } catch (ArrayIndexOutOfBoundsException var8) {
            return null;
         }
      }

      Object ret = node.m_Value;
      node.m_Value = value;
      return ret;
   }

   public Object get(char[] key, int start, int length) {
      int len = length;
      Node node = this.m_Root;

      for(int i = 0; i < len; ++i) {
         int loc = key[start + i];
         if (loc >= 128) {
            return null;
         }

         node = node.m_nextChar[loc];
         if (node == null) {
            return null;
         }
      }

      return node.m_Value;
   }

   public Object get(String key) {
      int len = key.length();
      Node node = this.m_Root;

      for(int i = 0; i < len; ++i) {
         int loc = key.charAt(i);
         if (loc >= 128) {
            return null;
         }

         node = node.m_nextChar[loc];
         if (node == null) {
            return null;
         }
      }

      return node.m_Value;
   }

   private static class Node {
      final Node[] m_nextChar = new Node[128];
      Object m_Value = null;

      Node() {
      }
   }
}
