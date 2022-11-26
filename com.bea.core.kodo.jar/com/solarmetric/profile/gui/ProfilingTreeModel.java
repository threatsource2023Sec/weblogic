package com.solarmetric.profile.gui;

import com.solarmetric.profile.Node;
import com.solarmetric.profile.ProfilingAgentEvent;
import com.solarmetric.profile.ProfilingAgentImpl;
import com.solarmetric.profile.ProfilingAgentListener;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import org.apache.commons.lang.ObjectUtils;
import org.apache.openjpa.lib.util.Localizer;

public class ProfilingTreeModel implements TreeModel, ProfilingAgentListener {
   private static final Localizer s_loc = Localizer.forPackage(ProfilingTreeModel.class);
   private boolean _showAncestors = false;
   private Vector _treeModelListeners = new Vector();
   private Object _rootNode;
   private ProfilingAgentImpl _agent;

   public ProfilingTreeModel(ProfilingAgentImpl agent) {
      this._rootNode = agent;
      this._agent = agent;
      this._agent.setListener(this);
   }

   public boolean showAncestor(boolean ancestors, Object newRoot) {
      this._showAncestors = ancestors;
      if (newRoot == this._agent) {
         this._showAncestors = false;
      }

      if (!ancestors) {
         newRoot = this._agent;
      }

      if (newRoot == null) {
         return false;
      } else {
         if (newRoot instanceof Node) {
            newRoot = this._agent.getNodeList(((Node)newRoot).getInfo());
         }

         Object oldRoot = this._rootNode;
         this._rootNode = newRoot;
         if (this._rootNode == this._agent) {
            this._showAncestors = false;
         }

         this.fireTreeStructureChanged(oldRoot);
         return true;
      }
   }

   public boolean getShowAncestors() {
      return this._showAncestors;
   }

   protected void fireTreeStructureChanged(Object oldRoot) {
      int len = this._treeModelListeners.size();
      TreeModelEvent e = new TreeModelEvent(this, new Object[]{oldRoot});

      for(int i = 0; i < len; ++i) {
         ((TreeModelListener)this._treeModelListeners.elementAt(i)).treeStructureChanged(e);
      }

   }

   protected void fireTreeNodesInserted(Object n) {
      if (!this._showAncestors) {
         if (this._rootNode == this._agent) {
            Node node = (Node)n;
            TreeModelEvent e;
            if (node.getParent() == null) {
               e = new TreeModelEvent(this, new Object[]{this._agent}, new int[]{this._agent.getRoots().indexOf(node)}, new Object[]{node});
            } else {
               e = new TreeModelEvent(this, this.getPathToNode(node.getParent()), new int[]{indexOfValue(node.getParent().getChildren(), node)}, new Object[]{node});
            }

            int len = this._treeModelListeners.size();

            for(int i = 0; i < len; ++i) {
               ((TreeModelListener)this._treeModelListeners.elementAt(i)).treeNodesInserted(e);
            }

         }
      }
   }

   public void fireTreeNodesChangedEvent(TreeModelEvent e) {
      int len = this._treeModelListeners.size();

      for(int i = 0; i < len; ++i) {
         ((TreeModelListener)this._treeModelListeners.elementAt(i)).treeNodesChanged(e);
      }

   }

   private Object[] getPathToNode(Node node) {
      if (node == null) {
         return new Object[]{this._agent};
      } else {
         LinkedList list = new LinkedList();
         list.addFirst(node);

         while(node.getParent() != null) {
            node = node.getParent();
            list.addFirst(node);
         }

         list.addFirst(this._agent);
         return list.toArray();
      }
   }

   public void rootAdded(ProfilingAgentEvent ev) {
      this.fireTreeNodesInserted(ev.getNode());
   }

   public void nodeAdded(ProfilingAgentEvent ev) {
      this.fireTreeNodesInserted(ev.getNode());
   }

   public void addTreeModelListener(TreeModelListener l) {
      this._treeModelListeners.addElement(l);
   }

   public Object getChild(Object parent, int index) {
      if (parent instanceof Node) {
         Node p = (Node)parent;
         return this._showAncestors ? p.getParent() : (Node)p.getChildren().getValue(index);
      } else if (parent instanceof ProfilingAgentImpl) {
         if (this._showAncestors) {
            return null;
         } else {
            ProfilingAgentImpl p = (ProfilingAgentImpl)parent;
            return (Node)p.getRoots().get(index);
         }
      } else if (parent instanceof List) {
         if (!this._showAncestors) {
            return null;
         } else {
            List p = (List)parent;
            return p.get(index);
         }
      } else {
         throw new IllegalStateException(s_loc.get("invalid-node-type", parent.getClass().getName()).getMessage());
      }
   }

   public int getChildCount(Object parent) {
      if (parent instanceof Node) {
         Node p = (Node)parent;
         if (this._showAncestors) {
            return p.getParent() == null ? 0 : 1;
         } else {
            return p.getChildren().size();
         }
      } else if (parent instanceof ProfilingAgentImpl) {
         if (this._showAncestors) {
            return 0;
         } else {
            ProfilingAgentImpl p = (ProfilingAgentImpl)parent;
            return p.getRoots().size();
         }
      } else if (parent instanceof List) {
         if (!this._showAncestors) {
            return 0;
         } else {
            List p = (List)parent;
            return p.size();
         }
      } else {
         throw new IllegalStateException(s_loc.get("invalid-node-type", parent.getClass().getName()).getMessage());
      }
   }

   public int getIndexOfChild(Object parent, Object child) {
      if (parent instanceof Node) {
         Node p = (Node)parent;
         if (this._showAncestors) {
            return child == p.getParent() ? 0 : -1;
         } else {
            return indexOfValue(p.getChildren(), child);
         }
      } else if (parent instanceof ProfilingAgentImpl) {
         if (this._showAncestors) {
            return -1;
         } else {
            ProfilingAgentImpl p = (ProfilingAgentImpl)parent;
            return p.getRoots().indexOf(child);
         }
      } else if (parent instanceof List) {
         if (!this._showAncestors) {
            return -1;
         } else {
            List p = (List)parent;
            return p.indexOf(child);
         }
      } else {
         throw new IllegalStateException(s_loc.get("invalid-node-type", parent.getClass().getName()).getMessage());
      }
   }

   public Object getRoot() {
      return this._rootNode;
   }

   public boolean isLeaf(Object node) {
      if (node instanceof Node && !this._showAncestors) {
         return ((Node)node).getChildren().size() == 0;
      } else {
         return false;
      }
   }

   public void removeTreeModelListener(TreeModelListener l) {
      this._treeModelListeners.removeElement(l);
   }

   public void valueForPathChanged(TreePath path, Object newValue) {
      System.out.println("*** valueForPathChanged : " + path + " --> " + newValue);
   }

   private static int indexOfValue(Map map, Object value) {
      int i = 0;

      for(Iterator itr = map.values().iterator(); itr.hasNext(); ++i) {
         if (ObjectUtils.equals(value, itr.next())) {
            return i;
         }
      }

      return -1;
   }
}
