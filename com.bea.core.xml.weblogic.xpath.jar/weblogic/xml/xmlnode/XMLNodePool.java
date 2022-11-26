package weblogic.xml.xmlnode;

import weblogic.xml.stream.XMLName;

public final class XMLNodePool {
   private XMLNode[] nodes;
   private int head = 0;
   private int maxSize = 64;

   public XMLNodePool(int size) {
      this.nodes = new XMLNode[size];
   }

   public XMLNodePool(int size, int maxSize) {
      this.nodes = new XMLNode[size];
      this.maxSize = maxSize;
   }

   public XMLNode getNodeFromPool() {
      XMLNode node = null;
      if (this.head > 0) {
         --this.head;
         node = this.nodes[this.head];
         this.nodes[this.head] = null;
      } else {
         node = new XMLNode();
      }

      return node;
   }

   public XMLNode getNodeFromPool(XMLName name) {
      XMLNode node = this.getNodeFromPool();
      node.setName(name);
      return node;
   }

   public boolean returnNodeToPool(XMLNode node) {
      if (this.nodes.length >= this.maxSize) {
         return false;
      } else {
         if (this.nodes.length > this.head) {
            this.nodes[this.head] = node;
            ++this.head;
         } else {
            this.expandPool();
            this.nodes[this.head] = node;
            ++this.head;
         }

         return true;
      }
   }

   public void expandPool() {
      XMLNode[] newPool = new XMLNode[this.nodes.length * 2];
      System.arraycopy(this.nodes, 0, newPool, 0, this.nodes.length);
      this.nodes = newPool;
   }
}
