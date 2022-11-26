package com.sun.faces.config.manager.documents;

import com.sun.faces.config.ConfigurationException;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.Timer;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DocumentOrderingWrapper {
   private static final Logger LOGGER;
   private static Comparator COMPARATOR;
   private static final int MAX_SORT_PASSED = 1000;
   private static final String ORDERING = "ordering";
   private static final String BEFORE = "before";
   private static final String AFTER = "after";
   private static final String NAME = "name";
   private static final String OTHERS = "others";
   private static final String OTHERS_KEY;
   private static final int SWAP = -1;
   private static final int DO_NOT_SWAP = 0;
   private DocumentInfo documentInfo;
   private String id;
   private String[] beforeIds;
   private String[] afterIds;

   public DocumentOrderingWrapper(DocumentInfo document) {
      this.documentInfo = document;
      this.init();
   }

   public DocumentInfo getDocument() {
      return this.documentInfo;
   }

   public String getDocumentId() {
      return this.id;
   }

   public String[] getBeforeIds() {
      return this.beforeIds;
   }

   public String[] getAfterIds() {
      return this.afterIds;
   }

   public boolean isBeforeOrdered() {
      return this.beforeIds.length != 0;
   }

   public boolean isAfterOrdered() {
      return this.afterIds.length != 0;
   }

   public boolean isOrdered() {
      return this.isBeforeOrdered() || this.isAfterOrdered();
   }

   public boolean isBefore(String id) {
      return search(this.beforeIds, id);
   }

   public boolean isAfter(String id) {
      return search(this.afterIds, id);
   }

   public boolean isAfterOthers() {
      return search(this.afterIds, OTHERS_KEY);
   }

   public boolean isBeforeOthers() {
      return search(this.beforeIds, OTHERS_KEY);
   }

   public String toString() {
      return "Document{id='" + this.id + '\'' + ", beforeIds=" + (this.beforeIds == null ? null : Arrays.asList(this.beforeIds)) + ", afterIds=" + (this.afterIds == null ? null : Arrays.asList(this.afterIds)) + '}';
   }

   public static DocumentOrderingWrapper[] sort(DocumentOrderingWrapper[] documents, List absoluteOrder) {
      List sourceList = new CopyOnWriteArrayList();
      sourceList.addAll(Arrays.asList(documents));
      List targetList = new ArrayList();
      Iterator var4 = absoluteOrder.iterator();

      while(true) {
         String name;
         do {
            if (!var4.hasNext()) {
               int othersIndex = absoluteOrder.indexOf("others");
               if (othersIndex != -1) {
                  Iterator var10 = sourceList.iterator();

                  while(var10.hasNext()) {
                     DocumentOrderingWrapper wrapper = (DocumentOrderingWrapper)var10.next();
                     targetList.add(othersIndex, wrapper);
                  }
               }

               return (DocumentOrderingWrapper[])targetList.toArray(new DocumentOrderingWrapper[targetList.size()]);
            }

            name = (String)var4.next();
         } while("others".equals(name));

         boolean found = false;
         Iterator var7 = sourceList.iterator();

         label54:
         while(true) {
            while(true) {
               if (!var7.hasNext()) {
                  break label54;
               }

               DocumentOrderingWrapper wrapper = (DocumentOrderingWrapper)var7.next();
               if (!found && name.equals(wrapper.getDocumentId())) {
                  found = true;
                  targetList.add(wrapper);
                  sourceList.remove(wrapper);
               } else if (found && name.equals(wrapper.getDocumentId())) {
                  if (LOGGER.isLoggable(Level.WARNING)) {
                     LOGGER.log(Level.WARNING, "jsf.configuration.absolute.order.duplicate.document", new Object[]{name});
                  }
                  break label54;
               }
            }
         }

         if (!found && LOGGER.isLoggable(Level.WARNING)) {
            LOGGER.log(Level.WARNING, "jsf.configuration.absolute.order.missing.document", new Object[]{name});
         }
      }
   }

   public static void sort(DocumentOrderingWrapper[] documents) {
      Timer t = Timer.getInstance();
      if (t != null) {
         t.startTiming();
      }

      try {
         enhanceOrderingData(documents);
      } catch (CircularDependencyException var8) {
         String msg = "Circular dependencies detected!\nDocument Info\n==================\n";
         DocumentOrderingWrapper[] var4 = documents;
         int var5 = documents.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            DocumentOrderingWrapper w = var4[var6];
            msg = msg + "  " + w.toString() + '\n';
         }

         throw new ConfigurationException(msg);
      }

      preSort(documents);
      int numberOfPasses = innerSort(documents);

      for(int i = 0; i < documents.length; ++i) {
         LinkedList ids = getIds(documents);
         if (done(documents, ids)) {
            break;
         }
      }

      if (t != null) {
         t.stopTiming();
         t.logResult("\"faces-config\" document sorting complete in " + numberOfPasses + '.');
      }

   }

   public static boolean done(DocumentOrderingWrapper[] documents, LinkedList ids) {
      for(int i = 0; i < documents.length; ++i) {
         int ii = 0;

         for(Iterator var4 = ids.iterator(); var4.hasNext(); ++ii) {
            String documentId = (String)var4.next();
            if (documents[i].getDocumentId().equals(documentId)) {
               break;
            }

            if (documents[i].isBefore(documentId)) {
               if (LOGGER.isLoggable(Level.FINE)) {
                  LOGGER.log(Level.FINE, "done: " + documentId + " should be after " + documents[i].getDocumentId() + " given that it should be before: " + Arrays.asList(documents[i].getBeforeIds()));
               }

               DocumentOrderingWrapper temp = null;

               for(int j = 0; j < documents.length; ++j) {
                  if (j == ii) {
                     temp = documents[j];
                  }

                  if (temp != null && j != i) {
                     documents[j] = documents[j + 1];
                  }

                  if (j == i) {
                     documents[j] = temp;
                     return false;
                  }
               }
            }
         }
      }

      return true;
   }

   public static LinkedList getIds(DocumentOrderingWrapper[] documents) {
      LinkedList ids = new LinkedList();

      for(int i = 0; i < documents.length; ++i) {
         ids.add(documents[i].getDocumentId());
      }

      return ids;
   }

   public static int innerSort(DocumentOrderingWrapper[] documents) {
      int numberOfPasses = 0;
      boolean doMore = true;

      while(doMore) {
         ++numberOfPasses;
         if (numberOfPasses == 1000) {
            if (LOGGER.isLoggable(Level.SEVERE)) {
               String msg = "Exceeded maximum number of attempts to sort the application's faces-config documents.\nDocument Info\n==================";
               DocumentOrderingWrapper[] var10 = documents;
               int var5 = documents.length;

               for(int var6 = 0; var6 < var5; ++var6) {
                  DocumentOrderingWrapper w = var10[var6];
                  msg = msg + "  " + w.toString() + '\n';
               }

               LOGGER.severe(msg);
            }

            throw new ConfigurationException("Exceeded maximum number of attempts to sort the faces-config documents.");
         }

         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, "Starting sort pass number {0}...", numberOfPasses);
         }

         doMore = false;

         for(int i = 0; i < documents.length - 1; ++i) {
            if (LOGGER.isLoggable(Level.FINE)) {
               LOGGER.log(Level.FINE, "Comparing {0}, {1}", new Object[]{documents[i].id, documents[i + 1].id});
            }

            if (COMPARATOR.compare(documents[i], documents[i + 1]) != 0) {
               if (LOGGER.isLoggable(Level.FINE)) {
                  LOGGER.log(Level.FINE, "Swapping {0} with {1}", new Object[]{documents[i].id, documents[i + 1].id});
               }

               DocumentOrderingWrapper temp = documents[i];
               documents[i] = documents[i + 1];
               documents[i + 1] = temp;
               doMore = true;
            }
         }

         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, "Comparing {0}, {1}", new Object[]{documents[0].id, documents[documents.length - 1].id});
         }

         if (COMPARATOR.compare(documents[0], documents[documents.length - 1]) != 0) {
            if (LOGGER.isLoggable(Level.FINE)) {
               LOGGER.log(Level.FINE, "Swapping {0} with {1}", new Object[]{documents[0].id, documents[documents.length - 1].id});
            }

            DocumentOrderingWrapper temp = documents[0];
            documents[0] = documents[documents.length - 1];
            documents[documents.length - 1] = temp;
            doMore = true;
         }
      }

      return numberOfPasses;
   }

   private static void enhanceOrderingData(DocumentOrderingWrapper[] wrappers) throws CircularDependencyException {
      for(int i = 0; i < wrappers.length; ++i) {
         DocumentOrderingWrapper w = wrappers[i];
         String[] var3 = w.getBeforeIds();
         int var4 = var3.length;

         int var5;
         String id;
         int ii;
         DocumentOrderingWrapper other;
         String[] beforeIds;
         HashSet newBeforeIds;
         String[] currentAfterIds;
         HashSet newAfterIds;
         String[] temp;
         int var14;
         int var15;
         String bid;
         String[] otherAfterIds;
         for(var5 = 0; var5 < var4; ++var5) {
            id = var3[var5];
            if (!OTHERS_KEY.equals(id)) {
               for(ii = 0; ii < wrappers.length; ++ii) {
                  other = wrappers[ii];
                  if (id.equals(other.id)) {
                     beforeIds = other.getAfterIds();
                     if (Arrays.binarySearch(beforeIds, w.id) < 0) {
                        newBeforeIds = new HashSet(beforeIds.length + 1);
                        newBeforeIds.addAll(Arrays.asList(beforeIds));
                        newBeforeIds.add(w.id);
                        other.afterIds = (String[])newBeforeIds.toArray(new String[newBeforeIds.size()]);
                        Arrays.sort(other.afterIds);
                     }

                     otherAfterIds = other.getBeforeIds();
                     if (otherAfterIds.length > 0) {
                        currentAfterIds = w.getBeforeIds();
                        newAfterIds = new HashSet();
                        newAfterIds.addAll(Arrays.asList(currentAfterIds));
                        temp = otherAfterIds;
                        var14 = otherAfterIds.length;

                        for(var15 = 0; var15 < var14; ++var15) {
                           bid = temp[var15];
                           if (!OTHERS_KEY.equals(bid)) {
                              newAfterIds.add(bid);
                           }
                        }

                        temp = (String[])newAfterIds.toArray(new String[newAfterIds.size()]);
                        Arrays.sort(temp);
                        if (search(temp, w.id)) {
                           throw new CircularDependencyException();
                        }

                        w.beforeIds = temp;
                     }
                  }
               }
            }
         }

         var3 = w.getAfterIds();
         var4 = var3.length;

         for(var5 = 0; var5 < var4; ++var5) {
            id = var3[var5];
            if (!OTHERS_KEY.equals(id)) {
               for(ii = 0; ii < wrappers.length; ++ii) {
                  other = wrappers[ii];
                  if (id.equals(other.id)) {
                     beforeIds = other.getBeforeIds();
                     if (Arrays.binarySearch(beforeIds, w.id) < 0) {
                        newBeforeIds = new HashSet(beforeIds.length + 1);
                        newBeforeIds.addAll(Arrays.asList(beforeIds));
                        newBeforeIds.add(w.id);
                        other.beforeIds = (String[])newBeforeIds.toArray(new String[newBeforeIds.size()]);
                        Arrays.sort(other.beforeIds);
                     }

                     otherAfterIds = other.getAfterIds();
                     if (otherAfterIds.length > 0) {
                        currentAfterIds = w.getAfterIds();
                        newAfterIds = new HashSet();
                        newAfterIds.addAll(Arrays.asList(currentAfterIds));
                        temp = otherAfterIds;
                        var14 = otherAfterIds.length;

                        for(var15 = 0; var15 < var14; ++var15) {
                           bid = temp[var15];
                           if (!OTHERS_KEY.equals(bid)) {
                              newAfterIds.add(bid);
                           }
                        }

                        temp = (String[])newAfterIds.toArray(new String[newAfterIds.size()]);
                        Arrays.sort(temp);
                        if (search(temp, w.id)) {
                           throw new CircularDependencyException();
                        }

                        w.afterIds = temp;
                     }
                  }
               }
            }
         }
      }

   }

   private static boolean search(String[] ids, String id) {
      return Arrays.binarySearch(ids, id) >= 0;
   }

   private void init() {
      Element documentElement = this.documentInfo.getDocument().getDocumentElement();
      String namespace = documentElement.getNamespaceURI();
      this.id = this.getDocumentName(documentElement);
      NodeList orderingElements = documentElement.getElementsByTagNameNS(namespace, "ordering");
      Set beforeIds = null;
      Set afterIds = null;
      if (orderingElements.getLength() > 0) {
         int i = 0;

         for(int len = orderingElements.getLength(); i < len; ++i) {
            Node orderingNode = orderingElements.item(i);
            NodeList children = orderingNode.getChildNodes();
            int j = 0;

            for(int jlen = children.getLength(); j < jlen; ++j) {
               Node n = children.item(j);
               if (beforeIds == null) {
                  beforeIds = this.extractIds(n, "before");
               }

               if (afterIds == null) {
                  afterIds = this.extractIds(n, "after");
               }
            }
         }
      }

      this.beforeIds = beforeIds != null ? (String[])beforeIds.toArray(new String[beforeIds.size()]) : new String[0];
      this.afterIds = afterIds != null ? (String[])afterIds.toArray(new String[afterIds.size()]) : new String[0];
      Arrays.sort(this.beforeIds);
      Arrays.sort(this.afterIds);
      this.checkDuplicates(this.beforeIds, this.afterIds);
      this.checkDuplicates(this.afterIds, this.beforeIds);
   }

   private String getDocumentName(Element documentElement) {
      NodeList children = documentElement.getChildNodes();
      String documentName = "";
      if (children != null && children.getLength() > 0) {
         int i = 0;

         for(int len = children.getLength(); i < len; ++i) {
            Node n = children.item(i);
            if ("name".equals(n.getLocalName())) {
               documentName = this.getNodeText(n);
               break;
            }
         }
      }

      return documentName;
   }

   private void checkDuplicates(String[] source, String[] searchTarget) {
      String[] var3 = source;
      int var4 = source.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String id = var3[var5];
         if (search(searchTarget, id)) {
            String msg = MessageFormat.format("Document {0} is specified to come before and after {1}.", this.documentInfo.getDocument().getDocumentURI(), id);
            throw new ConfigurationException(msg);
         }
      }

   }

   private Set extractIds(Node n, String nodeName) {
      Set idsList = null;
      if (nodeName.equals(n.getLocalName())) {
         idsList = new HashSet();
         NodeList ids = n.getChildNodes();
         int k = 0;

         for(int klen = ids.getLength(); k < klen; ++k) {
            Node idNode = ids.item(k);
            if ("name".equals(idNode.getLocalName())) {
               String id = this.getNodeText(idNode);
               if (id != null) {
                  idsList.add(id);
               }
            }

            if ("others".equals(idNode.getLocalName()) && this.id != null) {
               idsList.add(OTHERS_KEY);
            }
         }
      }

      return idsList;
   }

   private String getNodeText(Node node) {
      String res = null;
      if (node != null) {
         res = node.getTextContent();
         if (res != null) {
            res = res.trim();
         }
      }

      return res != null && res.length() != 0 ? res : null;
   }

   public static HashMap getDocumentHashMap(DocumentOrderingWrapper[] documents) {
      HashMap configMap = new HashMap();
      DocumentOrderingWrapper[] var2 = documents;
      int var3 = documents.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         DocumentOrderingWrapper document = var2[var4];
         String name = document.id;
         if (name != null && !"".equals(name)) {
            configMap.put(name, document);
         }
      }

      return configMap;
   }

   public static void preSort(DocumentOrderingWrapper[] documents) {
      List anonymousAndUnorderedList = new ArrayList();
      Map linkedMap = new LinkedHashMap();
      DocumentOrderingWrapper[] copyOfDocuments = new DocumentOrderingWrapper[documents.length];
      System.arraycopy(documents, 0, copyOfDocuments, 0, documents.length);
      int i = 0;
      DocumentOrderingWrapper[] var5 = documents;
      int var6 = documents.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         DocumentOrderingWrapper w = var5[var7];
         String[] bfs = w.getBeforeIds();
         String[] afs = w.getAfterIds();
         int knowledge = bfs.length + afs.length;
         if ((w.id == null || "".equals(w.id)) && !w.isOrdered()) {
            anonymousAndUnorderedList.add(w);
         } else {
            linkedMap.put(i, knowledge);
         }

         ++i;
      }

      Map linkedMap = descendingByValue(linkedMap);
      i = 0;

      Iterator var13;
      for(var13 = linkedMap.entrySet().iterator(); var13.hasNext(); ++i) {
         Map.Entry entry = (Map.Entry)var13.next();
         Integer index = (Integer)entry.getKey();
         documents[i] = copyOfDocuments[index];
      }

      for(var13 = anonymousAndUnorderedList.iterator(); var13.hasNext(); ++i) {
         DocumentOrderingWrapper w = (DocumentOrderingWrapper)var13.next();
         documents[i] = w;
      }

   }

   public static Map descendingByValue(Map map) {
      List list = new LinkedList(map.entrySet());
      Collections.sort(list, new Comparator() {
         public int compare(Map.Entry a, Map.Entry b) {
            return ((Comparable)b.getValue()).compareTo(a.getValue());
         }
      });
      Map result = new LinkedHashMap();
      Iterator var3 = list.iterator();

      while(var3.hasNext()) {
         Map.Entry entry = (Map.Entry)var3.next();
         result.put(entry.getKey(), entry.getValue());
      }

      return result;
   }

   static {
      LOGGER = FacesLogger.CONFIG.getLogger();
      COMPARATOR = new DocumentOrderingComparator();
      OTHERS_KEY = DocumentOrderingWrapper.class.getName() + ".OTHERS_KEY";
   }

   private static final class CircularDependencyException extends Exception {
      public CircularDependencyException() {
      }
   }

   private static final class DocumentOrderingComparator implements Comparator {
      private DocumentOrderingComparator() {
      }

      public int compare(DocumentOrderingWrapper wrapper1, DocumentOrderingWrapper wrapper2) {
         String w1Id = wrapper1.id;
         String w2Id = wrapper2.id;
         boolean w1IsOrdered = wrapper1.isOrdered();
         boolean w2IsOrdered = wrapper2.isOrdered();
         if (w1IsOrdered && !w2IsOrdered && wrapper1.isAfterOrdered() && !wrapper1.isBeforeOthers()) {
            return -1;
         } else {
            boolean w2IsBeforeW1 = wrapper2.isBefore(w1Id);
            boolean w1IsAfterW2 = wrapper1.isAfter(w2Id);
            if (!w2IsBeforeW1 && !w1IsAfterW2) {
               boolean w1IsAfterOthers = wrapper1.isAfterOthers();
               if (w1IsAfterOthers && !wrapper1.isBefore(w2Id) && (!wrapper1.isAfterOthers() || !wrapper2.isAfterOthers())) {
                  return -1;
               } else {
                  boolean w2IsBeforeOthers = wrapper2.isBeforeOthers();
                  return !w2IsBeforeOthers || wrapper2.isAfter(w1Id) || wrapper1.isBeforeOthers() && wrapper2.isBeforeOthers() ? 0 : -1;
               }
            } else {
               return -1;
            }
         }
      }

      // $FF: synthetic method
      DocumentOrderingComparator(Object x0) {
         this();
      }
   }
}
