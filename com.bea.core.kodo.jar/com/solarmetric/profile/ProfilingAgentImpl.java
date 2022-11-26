package com.solarmetric.profile;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.collections.set.ListOrderedSet;
import org.apache.openjpa.lib.conf.Configuration;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Closeable;
import org.apache.openjpa.lib.util.Localizer;
import serp.util.Numbers;

public class ProfilingAgentImpl implements ProfilingAgent, Externalizable, Closeable {
   private static final long serialVersionUID = 1L;
   private static final Localizer s_loc = Localizer.forPackage(ProfilingAgentImpl.class);
   private ListOrderedMap _nodeLists = new ListOrderedMap();
   private Collection _allNodes = new ArrayList();
   private HashMap _stacks = new HashMap();
   private ListOrderedSet _roots = new ListOrderedSet();
   private transient ProfilingAgentListener _listener;
   private transient ConcurrentLinkedQueue _eventQueue = new ConcurrentLinkedQueue();
   private transient Log _log;

   public ProfilingAgentImpl() {
   }

   public ProfilingAgentImpl(Configuration conf) {
      this._log = ProfilingLog.get(conf);
   }

   public void handleEvent(ProfilingEvent ev) {
      this._eventQueue.offer(ev);
   }

   public void close() {
   }

   public void processQueue() {
      ProfilingEvent event;
      while((event = (ProfilingEvent)this._eventQueue.poll()) != null) {
         this.handleEventInternal(event);
      }

   }

   protected void handleEventInternal(ProfilingEvent ev) {
      int env = ev.getProfilingEnvironmentHash();
      RootInfo info;
      ArrayList nodeList;
      if (ev instanceof RootEnterEvent) {
         RootEnterEvent reev = (RootEnterEvent)ev;
         info = reev.getRootInfo();
         nodeList = (ArrayList)this._nodeLists.get(info);
         Node rootNode;
         if (nodeList == null) {
            rootNode = new Node(info, (Node)null);
            nodeList = new ArrayList();
            nodeList.add(rootNode);
            this._nodeLists.put(info, nodeList);
            this._roots.add(rootNode);
            if (this._listener != null) {
               this._listener.rootAdded(new ProfilingAgentEvent(this, rootNode));
            }
         } else {
            rootNode = (Node)nodeList.get(0);
         }

         if (!this._allNodes.contains(rootNode)) {
            this._allNodes.add(rootNode);
         }

         ProfilingStack stack = new ProfilingStack();
         HashMap threadStackMap = new HashMap();
         threadStackMap.put(Numbers.valueOf(reev.getThreadHash()), stack);
         this._stacks.put(Numbers.valueOf(env), threadStackMap);
         stack.pushItem(new ProfilingStackItem(reev, rootNode));
      } else {
         MethodInfo info;
         if (ev instanceof MethodEnterEvent) {
            MethodEnterEvent meev = (MethodEnterEvent)ev;
            info = meev.getMethodInfo();
            ProfilingStack stack = this.getStack(env, meev.getThreadHash(), true);
            if (stack == null) {
               return;
            }

            nodeList = (ArrayList)this._nodeLists.get(info);
            if (nodeList == null) {
               nodeList = new ArrayList();
               this._nodeLists.put(info, nodeList);
            }

            Node node = stack.peekItem().getNode().getChild(info);
            if (node == null) {
               node = stack.peekItem().getNode().addChild(info);
               if (this._listener != null) {
                  this._listener.nodeAdded(new ProfilingAgentEvent(this, node));
               }
            }

            if (!this._allNodes.contains(node)) {
               this._allNodes.add(node);
            }

            if (!nodeList.contains(node)) {
               nodeList.add(node);
            }

            stack.pushItem(new ProfilingStackItem(meev, node));
         } else {
            int tHash;
            ProfilingStack stack;
            ProfilingStackItem item;
            Node node;
            if (ev instanceof RootExitEvent) {
               RootExitEvent reev = (RootExitEvent)ev;
               info = reev.getRootInfo();
               tHash = reev.getThreadHash();
               stack = this.getStack(env, tHash, true);
               if (stack == null) {
                  return;
               }

               this._stacks.remove(Numbers.valueOf(env));
               item = stack.popItem();
               node = item.getNode();
               if (!node.getInfo().equals(info)) {
                  this._log.warn(s_loc.get("stack-imbalance"));
               }

               if (!this._allNodes.contains(node)) {
                  this._allNodes.add(node);
               }

               node.updateStatistic((double)(reev.getTime() - item.getProfilingEvent().getTime()));
            } else if (ev instanceof MethodExitEvent) {
               MethodExitEvent meev = (MethodExitEvent)ev;
               info = meev.getMethodInfo();
               tHash = meev.getThreadHash();
               stack = this.getStack(env, tHash, false);
               if (stack == null) {
                  return;
               }

               item = stack.popItem();
               node = item.getNode();
               if (!node.getInfo().equals(info)) {
                  this._log.warn(s_loc.get("stack-imbalance"));
               }

               if (!this._allNodes.contains(node)) {
                  this._allNodes.add(node);
               }

               node.updateStatistic((double)(meev.getTime() - item.getProfilingEvent().getTime()));
            } else if (ev instanceof TimeEvent) {
               TimeEvent tev = (TimeEvent)ev;
               EventInfo info = tev.getEventInfo();
               tHash = tev.getThreadHash();
               stack = this.getStack(env, tHash, true);
               if (stack == null) {
                  return;
               }

               ArrayList nodeList = (ArrayList)this._nodeLists.get(info);
               if (nodeList == null) {
                  nodeList = new ArrayList();
                  this._nodeLists.put(info, nodeList);
               }

               node = stack.peekItem().getNode().getChild(info);
               if (node == null) {
                  node = stack.peekItem().getNode().addChild(info);
                  if (this._listener != null) {
                     this._listener.nodeAdded(new ProfilingAgentEvent(this, node));
                  }
               }

               if (!this._allNodes.contains(node)) {
                  this._allNodes.add(node);
               }

               if (!nodeList.contains(node)) {
                  nodeList.add(node);
               }

               node.updateStatistic((double)tev.getTime());
            } else {
               this.handleExtendedEvent(ev);
            }
         }
      }

   }

   public ProfilingStack getStack(int env, int threadHash, boolean create) {
      HashMap threadStackMap = (HashMap)this._stacks.get(Numbers.valueOf(env));
      if (threadStackMap == null) {
         return null;
      } else {
         ProfilingStack stack = (ProfilingStack)threadStackMap.get(Numbers.valueOf(threadHash));
         if (stack == null && create) {
            Iterator stacks = threadStackMap.values().iterator();
            if (!stacks.hasNext()) {
               return null;
            }

            ProfilingStack templateStack = (ProfilingStack)stacks.next();
            stack = new ProfilingStack();
            threadStackMap.put(Numbers.valueOf(threadHash), stack);
            ProfilingStackItem stackItem = templateStack.firstElementItem();
            stack.pushItem(stackItem);
         }

         return stack;
      }
   }

   public ListOrderedSet getRoots() {
      return this._roots;
   }

   public List getNodeList(EventInfo info) {
      return (List)this._nodeLists.get(info);
   }

   public Collection getAllNodes() {
      return this._allNodes;
   }

   public void setListener(ProfilingAgentListener listener) {
      this._listener = listener;
   }

   protected void handleExtendedEvent(ProfilingEvent ev) {
   }

   public void reset() {
      this._eventQueue.clear();
      Iterator iter = this._roots.iterator();

      while(iter.hasNext()) {
         ((Node)iter.next()).reset();
      }

   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeObject(this._nodeLists);
      out.writeObject(this._allNodes);
      out.writeObject(this._stacks);
      out.writeObject(this._roots);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      this._nodeLists = (ListOrderedMap)in.readObject();
      this._allNodes = (Collection)in.readObject();
      this._stacks = (HashMap)in.readObject();
      this._roots = (ListOrderedSet)in.readObject();
      this._eventQueue = new ConcurrentLinkedQueue();
   }
}
