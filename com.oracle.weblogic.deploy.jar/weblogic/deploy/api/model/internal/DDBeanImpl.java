package weblogic.deploy.api.model.internal;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.enterprise.deploy.model.DDBean;
import javax.enterprise.deploy.model.DDBeanRoot;
import javax.enterprise.deploy.model.XpathListener;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import weblogic.deploy.api.internal.utils.ConfigHelper;
import weblogic.deploy.api.internal.utils.Debug;
import weblogic.deploy.api.model.WebLogicDDBean;
import weblogic.deploy.api.model.WebLogicDeployableObject;
import weblogic.deploy.api.shared.PlanHelper;
import weblogic.descriptor.DescriptorBean;
import weblogic.xml.xpath.DOMXPath;
import weblogic.xml.xpath.XPathException;

public class DDBeanImpl extends PlanHelper implements WebLogicDDBean {
   private static final boolean debug = Debug.isDebug("model");
   protected Node node;
   protected String xpath = null;
   protected WebLogicDeployableObject dObject = null;
   protected String id = null;
   protected List childBeans = new ArrayList();
   private DDBeanRootImpl ddRoot = null;
   NamedNodeMap attrs = null;
   String[] names = null;
   private DDBeanImpl parent;
   protected boolean modified = false;
   private String text = null;

   public DDBeanImpl(WebLogicDeployableObject dObject) {
      super(true);
      this.dObject = dObject;
   }

   protected void initDD(Node node, String xpath, DDBeanImpl parent) {
      this.node = node;
      this.xpath = xpath;
      if (debug) {
         Debug.say("Creating DDBean with xpath: " + xpath);
      }

      this.parent = parent;
      if (node != null) {
         this.initAttributes();
      }
   }

   private void initAttributes() {
      this.attrs = this.node.getAttributes();
      if (this.attrs != null) {
         for(int i = 0; i < this.attrs.getLength(); ++i) {
            Node attr = this.attrs.item(i);
            if (attr.getNodeName().equals("id")) {
               this.id = attr.getNodeValue();
               break;
            }
         }
      }

   }

   public String getXpath() {
      return this.xpath;
   }

   public String getText() {
      return this.getNodeText();
   }

   public String getId() {
      return this.id;
   }

   public DDBeanRoot getRoot() {
      return this.ddRoot;
   }

   public DDBean[] getChildBean(String xpath) {
      ConfigHelper.checkParam("xpath", xpath);
      if (debug) {
         Debug.say("[" + this.getXpath() + "] getting nodes with xpath: " + xpath);
      }

      DDBean[] ddbs = this.findBean(xpath);
      if (ddbs.length > 0) {
         return ddbs;
      } else {
         try {
            DOMXPath domXpath = new DOMXPath(xpath);
            Node startNode = this.getNode();
            if (startNode == null) {
               return null;
            } else {
               Collection result = domXpath.evaluateAsNodeset(startNode);
               if (result != null && result.size() != 0) {
                  Iterator nodes = result.iterator();
                  List retlist = new ArrayList();

                  while(nodes.hasNext()) {
                     Node nd = (Node)nodes.next();
                     DDBeanImpl bean = new DDBeanImpl(this.dObject);
                     bean.initDD(nd, xpath, this);
                     this.addChild(bean);
                     retlist.add(bean);
                  }

                  return (DDBean[])((DDBean[])retlist.toArray(new DDBean[0]));
               } else {
                  if (debug) {
                     Debug.say("No nodes for xpath: " + xpath);
                  }

                  return null;
               }
            }
         } catch (XPathException var10) {
            if (debug) {
               var10.printStackTrace();
            }

            return null;
         }
      }
   }

   public String[] getText(String path) {
      ConfigHelper.checkParam("path", path);
      DDBean[] beans = this.getChildBean(path);
      if (beans == null) {
         return null;
      } else {
         String[] txt = new String[beans.length];

         for(int i = 0; i < beans.length; ++i) {
            txt[i] = beans[i].getText();
         }

         return txt;
      }
   }

   public void addXpathListener(String s, XpathListener xpathlistener) {
   }

   public void removeXpathListener(String s, XpathListener xpathlistener) {
   }

   public String[] getAttributeNames() {
      if (this.names != null) {
         return this.names;
      } else if (this.attrs == null) {
         return null;
      } else {
         this.names = new String[this.attrs.getLength()];

         for(int i = 0; i < this.attrs.getLength(); ++i) {
            this.names[i] = this.attrs.item(i).getNodeName();
         }

         return this.names;
      }
   }

   public String getAttributeValue(String attrName) {
      ConfigHelper.checkParam("attrName", attrName);
      if (this.attrs == null) {
         return null;
      } else {
         for(int i = 0; i < this.attrs.getLength(); ++i) {
            Node attr = this.attrs.item(i);
            if (attr.getNodeName().equals(attrName)) {
               return attr.getNodeValue();
            }
         }

         return null;
      }
   }

   public Node getNode() {
      if (this.node == null) {
      }

      return this.node;
   }

   public void addChild(DDBeanImpl bean) {
      this.childBeans.add(bean);
      bean.setRoot(this.getRoot());
   }

   protected void setRoot(DDBeanRoot root) {
      this.ddRoot = (DDBeanRootImpl)root;
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append(this.getXpath());
      if (this.getText() != null && this.getText().length() > 0) {
         sb.append(": <");
         sb.append(this.getText());
         sb.append(">");
      }

      if (this.childBeans.size() > 0) {
         Iterator cb = this.childBeans.iterator();

         while(cb.hasNext()) {
            sb.append("\n");
            sb.append(cb.next().toString());
         }
      }

      return sb.toString();
   }

   public int hashCode() {
      return this.getXpath().hashCode();
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o == null) {
         return false;
      } else {
         return o instanceof DDBeanImpl && this.getNode() != null && this.getNode() == ((DDBeanImpl)o).getNode();
      }
   }

   DDBean[] findBean(String xpath) {
      List retlist = new ArrayList();
      Iterator beans = this.childBeans.iterator();

      while(beans.hasNext()) {
         DDBeanImpl bean = (DDBeanImpl)beans.next();
         if (xpath == bean.getXpath()) {
            retlist.add(bean);
         } else if (xpath != null && xpath.equals(bean.getXpath())) {
            retlist.add(bean);
         }
      }

      return (DDBean[])((DDBean[])retlist.toArray(new DDBean[0]));
   }

   private String getNodeText() {
      if (this.text == null && this.getNode() != null) {
         this.text = getTextFromNode(this.node);
      }

      return this.text;
   }

   private static final String getTextFromNode(Node node) {
      try {
         TransformerFactory transformerFactory = TransformerFactory.newInstance();
         Transformer transformer = transformerFactory.newTransformer();
         transformer.setOutputProperty("omit-xml-declaration", "yes");
         StringWriter stringWriter = new StringWriter();
         NodeList childNodes = node.getChildNodes();
         int childIndex = 0;

         for(int childCount = childNodes.getLength(); childIndex < childCount; ++childIndex) {
            Node child = childNodes.item(childIndex);
            transformer.transform(new DOMSource(child), new StreamResult(stringWriter));
         }

         String text = stringWriter.toString();
         return text;
      } catch (TransformerConfigurationException var8) {
         return null;
      } catch (TransformerException var9) {
         return null;
      }
   }

   public boolean isModified() {
      return this.modified;
   }

   public void setModified(boolean propogate) {
      if (!this.modified) {
         if (debug) {
            Debug.say("modified bean: " + this.getClass().getName());
         }

         this.modified = true;
         if (propogate && this.parent != null) {
            this.parent.setModified(true);
         }
      }

   }

   public void setUnmodified() {
      this.modified = false;
   }

   public DescriptorBean getDescriptorBean() {
      return null;
   }
}
