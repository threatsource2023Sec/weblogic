package org.glassfish.admin.rest.utils;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import weblogic.utils.XXEUtils;

public class DocCommentParser {
   private static final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
   private static DocumentBuilder builder;
   public static final String PROP_NODES = "nodes";
   public static final String PROP_ERRORS = "errors";
   public static final String PROP_TYPE = "type";
   public static final String PROP_CHILDREN = "children";
   public static final String PROP_ATTRIBUTES = "attributes";
   public static final String PROP_TEXT = "text";
   public static final String PROP_HREF = "href";
   public static final String TYPE_HTML = "html";
   public static final String TYPE_PARA = "p";
   public static final String TYPE_ORDERED_LIST = "ol";
   public static final String TYPE_UNORDERED_LIST = "ul";
   public static final String TYPE_LIST_ITEM = "li";
   public static final String TYPE_DEFINITION_LIST = "dl";
   public static final String TYPE_DEFINITION_TITLE = "dt";
   public static final String TYPE_DEFINITION_DESC = "dd";
   public static final String TYPE_CODE = "code";
   public static final String TYPE_BOLD = "b";
   public static final String TYPE_ITALIC = "i";
   public static final String TYPE_ANCHOR = "a";
   public static final String TYPE_BREAK = "br";
   public static final String TYPE_TEXT = "#text";
   private static final String[] SUPPORTED_CHILDREN_HTML = new String[]{"p", "ol", "ul", "dl", "a", "code", "b", "i", "br", "#text"};
   private static final String[] SUPPORTED_CHILDREN_PARA = new String[]{"a", "code", "b", "i", "br", "#text"};
   private static final String[] SUPPORTED_CHILDREN_ORDERED_LIST = new String[]{"li"};
   private static final String[] SUPPORTED_CHILDREN_UNORDERED_LIST;
   private static final String[] SUPPORTED_CHILDREN_LIST_ITEM;
   private static final String[] SUPPORTED_CHILDREN_DEFINITION_LIST;
   private static final String[] SUPPORTED_CHILDREN_DEFINITION_TITLE;
   private static final String[] SUPPORTED_CHILDREN_DEFINITION_DESC;
   private static final String[] SUPPORTED_CHILDREN_ANCHOR;
   private static final String[] SUPPORTED_CHILDREN_CODE;
   private static final String[] SUPPORTED_CHILDREN_BOLD;
   private static final String[] SUPPORTED_CHILDREN_ITALIC;
   private static final String[] SUPPORTED_CHILDREN_BREAK;
   private static final String[] SUPPORTED_CHILDREN_TEXT;
   private static Map SUPPORTED_CHILDREN;
   private static Map MIN_CHILDREN;
   private static Map MAX_CHILDREN;
   private static int UNBOUNDED;

   private static void registerSupportedChildren(String type, int min, int max, String[] supportedChildren) {
      Set set = new HashSet();
      String[] var5 = supportedChildren;
      int var6 = supportedChildren.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         String sc = var5[var7];
         set.add(sc);
      }

      SUPPORTED_CHILDREN.put(type, set);
      MIN_CHILDREN.put(type, min);
      MAX_CHILDREN.put(type, max);
   }

   public DocCommentParser() {
      try {
         synchronized(factory) {
            if (builder == null) {
               builder = factory.newDocumentBuilder();
            }
         }
      } catch (Throwable var4) {
         ExceptionUtil.rethrowAsUnchecked(var4);
      }

   }

   public JSONObject parse(String text) {
      try {
         return text != null && text.trim().length() >= 1 ? (new TextParser(text.trim())).parse() : null;
      } catch (Throwable var3) {
         ExceptionUtil.rethrowAsUnchecked(var3);
         return null;
      }
   }

   static {
      SUPPORTED_CHILDREN_UNORDERED_LIST = SUPPORTED_CHILDREN_ORDERED_LIST;
      SUPPORTED_CHILDREN_LIST_ITEM = new String[]{"p", "ol", "ul", "dl", "a", "code", "b", "i", "br", "#text"};
      SUPPORTED_CHILDREN_DEFINITION_LIST = new String[]{"dt", "dd"};
      SUPPORTED_CHILDREN_DEFINITION_TITLE = SUPPORTED_CHILDREN_PARA;
      SUPPORTED_CHILDREN_DEFINITION_DESC = SUPPORTED_CHILDREN_LIST_ITEM;
      SUPPORTED_CHILDREN_ANCHOR = SUPPORTED_CHILDREN_PARA;
      SUPPORTED_CHILDREN_CODE = new String[]{"b", "i", "br", "#text"};
      SUPPORTED_CHILDREN_BOLD = new String[]{"i", "#text"};
      SUPPORTED_CHILDREN_ITALIC = new String[]{"#text"};
      SUPPORTED_CHILDREN_BREAK = new String[0];
      SUPPORTED_CHILDREN_TEXT = new String[0];
      SUPPORTED_CHILDREN = new HashMap();
      MIN_CHILDREN = new HashMap();
      MAX_CHILDREN = new HashMap();
      UNBOUNDED = -1;
      registerSupportedChildren("html", 1, UNBOUNDED, SUPPORTED_CHILDREN_HTML);
      registerSupportedChildren("p", 0, UNBOUNDED, SUPPORTED_CHILDREN_PARA);
      registerSupportedChildren("ol", 1, UNBOUNDED, SUPPORTED_CHILDREN_ORDERED_LIST);
      registerSupportedChildren("ul", 1, UNBOUNDED, SUPPORTED_CHILDREN_UNORDERED_LIST);
      registerSupportedChildren("li", 1, UNBOUNDED, SUPPORTED_CHILDREN_LIST_ITEM);
      registerSupportedChildren("dl", 2, UNBOUNDED, SUPPORTED_CHILDREN_DEFINITION_LIST);
      registerSupportedChildren("dt", 1, UNBOUNDED, SUPPORTED_CHILDREN_DEFINITION_TITLE);
      registerSupportedChildren("dd", 1, UNBOUNDED, SUPPORTED_CHILDREN_DEFINITION_DESC);
      registerSupportedChildren("code", 1, UNBOUNDED, SUPPORTED_CHILDREN_CODE);
      registerSupportedChildren("b", 1, 1, SUPPORTED_CHILDREN_BOLD);
      registerSupportedChildren("i", 1, 1, SUPPORTED_CHILDREN_ITALIC);
      registerSupportedChildren("a", 0, UNBOUNDED, SUPPORTED_CHILDREN_ANCHOR);
      registerSupportedChildren("br", 0, 0, SUPPORTED_CHILDREN_BREAK);
      registerSupportedChildren("#text", 0, 0, SUPPORTED_CHILDREN_TEXT);
   }

   private class TextParser {
      private String text;
      private List errors;

      private String getText() {
         return this.text;
      }

      private List getErrors() {
         return this.errors;
      }

      private TextParser(String text) throws Exception {
         this.errors = new ArrayList();
         this.text = text;
      }

      private JSONObject parse() throws Exception {
         JSONArray nodes = null;

         try {
            nodes = this.documentToJSON(this.getDocument());
            this.validateDocument(nodes);
            nodes = this.cleanupDocument(nodes);
         } catch (Exception var3) {
            this.addError(var3.toString());
         }

         JSONObject rtn = new JSONObject();
         if (nodes != null) {
            rtn.put("nodes", nodes);
         }

         this.reportCategory(rtn, "errors", "error", this.getErrors());
         return rtn;
      }

      private Document getDocument() throws Exception {
         if (DocCommentParser.builder == null) {
            DocCommentParser.builder = XXEUtils.createDocumentBuilderFactoryInstance().newDocumentBuilder();
         }

         String html = this.getXMLText();
         Document document = DocCommentParser.builder.parse(new ByteArrayInputStream(html.getBytes()));
         document.getDocumentElement().normalize();
         return document;
      }

      private JSONArray cleanupDocument(JSONArray nodes) throws Exception {
         return this.cleanupNodes("html", nodes);
      }

      private JSONArray cleanupNodes(String parentType, JSONArray nodes) throws Exception {
         if (nodes == null) {
            return null;
         } else {
            nodes = this.wrapStandaloneTextInPara(parentType, nodes);

            for(int i = 0; i < nodes.length(); ++i) {
               JSONObject node = this.getChild(nodes, i);
               this.setChildren(node, this.cleanupNodes(this.getType(node), this.getChildren(node)));
            }

            return nodes;
         }
      }

      private JSONArray wrapStandaloneTextInPara(String parentType, JSONArray nodes) throws Exception {
         if (!"html".equals(parentType) && !"li".equals(parentType) && !"dd".equals(parentType)) {
            return nodes;
         } else {
            JSONArray rtn = new JSONArray();
            JSONArray paraChildren = null;

            for(int i = 0; i < nodes.length(); ++i) {
               JSONObject node = this.getChild(nodes, i);
               String type = this.getType(node);
               if (!"#text".equals(type) && !"code".equals(type) && !"b".equals(type) && !"i".equals(type) && !"a".equals(type)) {
                  rtn.put(node);
                  paraChildren = null;
               } else {
                  if (paraChildren == null) {
                     JSONObject paraNode = this.createNode("p");
                     paraChildren = new JSONArray();
                     this.setChildren(paraNode, paraChildren);
                     rtn.put(paraNode);
                  }

                  paraChildren.put(node);
               }
            }

            return rtn;
         }
      }

      private void validateDocument(JSONArray nodes) throws Exception {
         this.validateChildren("html", nodes);
      }

      private void validateChildren(JSONObject node) throws Exception {
         this.validateChildren(this.getType(node), this.getChildren(node));
      }

      private void validateChildren(String type, JSONArray children) throws Exception {
         Set supportedChildren = (Set)DocCommentParser.SUPPORTED_CHILDREN.get(type);
         int length = children != null ? children.length() : 0;
         if (this.validateNumberOfChildren(type, length)) {
            for(int i = 0; i < length; ++i) {
               this.validateChild(type, this.getChild(children, i), supportedChildren);
            }

            if ("dl".equals(type)) {
               this.validateTypeDefinitionChildren(children);
            }

         }
      }

      private boolean validateNumberOfChildren(String type, int length) throws Exception {
         int min = (Integer)DocCommentParser.MIN_CHILDREN.get(type);
         int max = (Integer)DocCommentParser.MAX_CHILDREN.get(type);
         boolean validLength = true;
         if (length < min) {
            this.addError("Too few children for " + type + " have: " + length + " min: " + min);
            return false;
         } else if (max != DocCommentParser.UNBOUNDED && length > max) {
            this.addError("Too many children for " + type + " have: " + length + " max: " + max);
            return false;
         } else {
            return true;
         }
      }

      private boolean validateTypeDefinitionChildren(JSONArray children) throws Exception {
         boolean valid = true;
         if (children.length() % 2 == 1) {
            valid = false;
         }

         for(int i = 0; valid && i < children.length(); i += 2) {
            if (!"dt".equals(this.getType(this.getChild(children, i)))) {
               valid = false;
            }

            if (!"dd".equals(this.getType(this.getChild(children, i + 1)))) {
               valid = false;
            }
         }

         if (!valid) {
            this.addError("dl must only contain dt dd pairs.");
            return false;
         } else {
            return true;
         }
      }

      private void validateChild(String parentType, JSONObject child, Set supportedChildren) throws Exception {
         String childType = this.getType(child);
         if (supportedChildren.contains(childType)) {
            this.validateChildren(child);
         } else {
            this.addError("Illegal child.  parent: " + parentType + ", child: " + childType + ", allowed: " + supportedChildren);
         }
      }

      private JSONArray documentToJSON(Document document) throws Exception {
         return this.getChildren(document.getFirstChild());
      }

      private void addChildren(JSONObject j, Node node) throws Exception {
         this.setChildren(j, this.getChildren(node));
      }

      private JSONArray getChildren(Node node) throws Exception {
         if (node.hasChildNodes()) {
            JSONArray children = new JSONArray();
            NodeList nodes = node.getChildNodes();

            for(int i = 0; i < nodes.getLength(); ++i) {
               this.addChild(children, nodes.item(i));
            }

            if (children.length() > 0) {
               return children;
            }
         }

         return null;
      }

      private JSONObject getChild(JSONArray children, int i) throws Exception {
         return children.getJSONObject(i);
      }

      private JSONArray getChildren(JSONObject node) throws Exception {
         return node.optJSONArray("children");
      }

      private void setChildren(JSONObject node, JSONArray children) throws Exception {
         if (node.has("children")) {
            node.remove("children");
         }

         if (children != null) {
            node.put("children", children);
         }

      }

      private String getType(JSONObject node) throws Exception {
         return node.getString("type");
      }

      private void addAttributes(JSONObject j, Node node) throws Exception {
         JSONArray attributes = this.getAttributes(node);
         if (attributes != null) {
            j.put("attributes", attributes);
         }

      }

      private JSONArray getAttributes(Node node) throws Exception {
         if (node.hasAttributes()) {
            JSONArray attributes = new JSONArray();
            NodeList nodes = node.getChildNodes();
            NamedNodeMap attrs = node.getAttributes();

            for(int i = 0; i < attrs.getLength(); ++i) {
               this.addChild(attributes, attrs.item(i));
            }

            if (attributes.length() > 0) {
               return attributes;
            }
         }

         return null;
      }

      private void addChild(JSONArray children, Node node) throws Exception {
         String type = node.getNodeName();
         JSONObject j = this.createNode(type);
         String href;
         if (node instanceof Text) {
            href = node.getTextContent();
            if (href == null || href.trim().length() < 1) {
               return;
            }

            j.put("text", href);
         }

         if ("a".equals(this.getType(j))) {
            href = ((Element)node).getAttribute("href");
            j.put("href", href);
         } else {
            this.addAttributes(j, node);
         }

         this.addChildren(j, node);
         children.put(j);
      }

      private void reportCategory(JSONObject json, String category, String prefix, List entries) throws Exception {
         if (entries.size() >= 1) {
            JSONArray a = new JSONArray();
            Iterator var6 = entries.iterator();

            while(var6.hasNext()) {
               String entry = (String)var6.next();
               a.put(prefix + ": " + entry);
            }

            json.put(category, a);
         }
      }

      private JSONObject createNode(String type) throws Exception {
         JSONObject node = new JSONObject();
         node.put("type", type);
         return node;
      }

      private String getXMLText() throws Exception {
         String desc = this.getText().trim();
         desc = "<html>" + desc.replaceAll("<br>", "<br/>") + "</html>";
         return desc;
      }

      private void addError(String msg) throws Exception {
         this.getErrors().add(msg);
      }

      // $FF: synthetic method
      TextParser(String x1, Object x2) throws Exception {
         this(x1);
      }
   }
}
