package org.apache.openjpa.lib.graph;

import java.util.List;

public class Edge {
   public static final int TYPE_TREE = 1;
   public static final int TYPE_BACK = 2;
   public static final int TYPE_FORWARD = 3;
   private final Object _from;
   private final Object _to;
   private final boolean _directed;
   private int _type;
   private double _weight;
   private Object _userObj;
   private List _cycle;
   private boolean _removedFromGraph;

   public Edge(Object from, Object to, boolean directed) {
      this._type = 0;
      this._weight = 0.0;
      this._userObj = null;
      this._cycle = null;
      this._removedFromGraph = false;
      if (from == null) {
         throw new NullPointerException("from == null");
      } else if (to == null) {
         throw new NullPointerException("to == null");
      } else {
         this._from = from;
         this._to = to;
         this._directed = directed;
      }
   }

   public Edge(Object from, Object to, boolean directed, Object userObject) {
      this(from, to, directed);
      this._userObj = userObject;
   }

   public Object getFrom() {
      return this._from;
   }

   public Object getTo() {
      return this._to;
   }

   public Object getOther(Object node) {
      if (this._to.equals(node)) {
         return this._from;
      } else {
         return this._from.equals(node) ? this._to : null;
      }
   }

   public boolean isTo(Object node) {
      return this._to.equals(node) || !this._directed && this._from.equals(node);
   }

   public boolean isFrom(Object node) {
      return this._from.equals(node) || !this._directed && this._to.equals(node);
   }

   public boolean isDirected() {
      return this._directed;
   }

   public double getWeight() {
      return this._weight;
   }

   public void setWeight(double weight) {
      this._weight = weight;
   }

   public Object getUserObject() {
      return this._userObj;
   }

   public void setUserObject(Object obj) {
      this._userObj = obj;
   }

   public int getType() {
      return this._type;
   }

   public void setType(int type) {
      this._type = type;
   }

   public List getCycle() {
      return this._cycle;
   }

   public void setCycle(List cycle) {
      this._cycle = cycle;
   }

   public boolean isRemovedFromGraph() {
      return this._removedFromGraph;
   }

   public void setRemovedFromGraph() {
      this._removedFromGraph = true;
   }

   public void clearTraversal() {
      this._type = 0;
      this._cycle = null;
   }

   public String toString() {
      return super.toString() + "[from=" + this.getFrom() + ";to=" + this.getTo() + ";directed=" + this.isDirected() + ";weight=" + this.getWeight() + "]";
   }
}
