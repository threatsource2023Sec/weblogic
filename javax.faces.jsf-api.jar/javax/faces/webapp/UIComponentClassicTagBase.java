package javax.faces.webapp;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.RandomAccess;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import javax.faces.FacesException;
import javax.faces.application.Application;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTag;
import javax.servlet.jsp.tagext.JspIdConsumer;
import javax.servlet.jsp.tagext.Tag;

public abstract class UIComponentClassicTagBase extends UIComponentTagBase implements JspIdConsumer, BodyTag {
   private static final String COMPONENT_TAG_STACK_ATTR = "javax.faces.webapp.COMPONENT_TAG_STACK";
   private static final String CONTEXT_MAP_ATTR = "com.sun.faces.CONTEXT_MAP";
   private static final String JSP_CREATED_COMPONENT_IDS = "javax.faces.webapp.COMPONENT_IDS";
   private static final String JSP_CREATED_FACET_NAMES = "javax.faces.webapp.FACET_NAMES";
   private static final String CURRENT_FACES_CONTEXT = "javax.faces.webapp.CURRENT_FACES_CONTEXT";
   private static final String CURRENT_VIEW_ROOT = "javax.faces.webapp.CURRENT_VIEW_ROOT";
   protected static final String UNIQUE_ID_PREFIX = "j_id_";
   private static final String PREVIOUS_JSP_ID_SET = "javax.faces.webapp.PREVIOUS_JSP_ID_SET";
   private static final String JAVAX_FACES_PAGECONTEXT_MARKER = "javax.faces.webapp.PAGECONTEXT_MARKER";
   private static final String JAVAX_FACES_PAGECONTEXT_COUNTER = "javax.faces.webapp.PAGECONTEXT_COUNTER";
   protected BodyContent bodyContent = null;
   private UIComponent component = null;
   private FacesContext context = null;
   private boolean created = false;
   private List createdComponents = null;
   private List createdFacets = null;
   protected PageContext pageContext = null;
   private Tag parent = null;
   private String jspId = null;
   private String facesJspId = null;
   private String id = null;
   private UIComponentClassicTagBase parentTag = null;
   private boolean isNestedInIterator = false;
   private int _nextChildIndex = 0;
   Map contextMap = null;
   Map namingContainerChildIds = null;

   public UIComponentClassicTagBase() {
   }

   UIComponentClassicTagBase(PageContext pageContext, FacesContext facesContext) {
      this.pageContext = pageContext;
      this.context = facesContext;
   }

   protected int getDoStartValue() throws JspException {
      int result = 2;
      return result;
   }

   protected int getDoEndValue() throws JspException {
      return 6;
   }

   /** @deprecated */
   protected void encodeBegin() throws IOException {
      this.component.encodeBegin(this.context);
   }

   /** @deprecated */
   protected void encodeChildren() throws IOException {
      this.component.encodeChildren(this.context);
   }

   /** @deprecated */
   protected void encodeEnd() throws IOException {
      this.component.encodeEnd(this.context);
   }

   public void setPageContext(PageContext pageContext) {
      this.pageContext = pageContext;
   }

   public Tag getParent() {
      return this.parent;
   }

   public void setParent(Tag parent) {
      this.parent = parent;
   }

   protected void setupResponseWriter() {
   }

   private UIComponent createChild(FacesContext context, UIComponent parent, UIComponentClassicTagBase parentTag, String componentId) throws JspException {
      UIComponent component = this.createComponent(context, componentId);
      int indexOfNextChildTag = parentTag.getIndexOfNextChildTag();
      if (indexOfNextChildTag > parent.getChildCount()) {
         indexOfNextChildTag = parent.getChildCount();
      }

      parent.getChildren().add(indexOfNextChildTag, component);
      this.created = true;
      return component;
   }

   private UIComponent createFacet(FacesContext context, UIComponent parent, String name, String newId) throws JspException {
      UIComponent component = this.createComponent(context, newId);
      parent.getFacets().put(name, component);
      this.created = true;
      return component;
   }

   private static UIComponent getChild(UIComponentClassicTagBase tag, UIComponent component, String componentId) {
      int childCount = component.getChildCount();
      if (childCount > 0) {
         List children = component.getChildren();
         if (children instanceof RandomAccess) {
            int startIndex;
            if (tag != null) {
               startIndex = tag._nextChildIndex;
            } else {
               startIndex = 0;
            }

            int i;
            UIComponent child;
            for(i = startIndex; i < childCount; ++i) {
               child = (UIComponent)children.get(i);
               if (componentId.equals(child.getId())) {
                  ++i;
                  tag._nextChildIndex = i < childCount ? i : 0;
                  return child;
               }
            }

            if (startIndex > 0) {
               for(i = 0; i < startIndex; ++i) {
                  child = (UIComponent)children.get(i);
                  if (componentId.equals(child.getId())) {
                     ++i;
                     tag._nextChildIndex = i;
                     return child;
                  }
               }
            }
         } else {
            Iterator var8 = children.iterator();

            while(var8.hasNext()) {
               UIComponent child = (UIComponent)var8.next();
               if (componentId.equals(child.getId())) {
                  return child;
               }
            }
         }
      }

      return null;
   }

   protected UIComponent findComponent(FacesContext context) throws JspException {
      if (this.component != null) {
         return this.component;
      } else {
         UIComponentClassicTagBase parentTag = _getParentUIComponentClassicTagBase(this.contextMap);
         if (parentTag != null) {
            UIComponent parentComponent = parentTag.getComponentInstance();
            String newId = this.createId(context);
            String facetName = this.getFacetName();
            boolean var6 = parentTag.getCreated();
            if (facetName != null) {
               this.component = (UIComponent)parentComponent.getFacets().get(facetName);
               if (this.component == null) {
                  this.component = this.createFacet(context, parentComponent, facetName, newId);
               }

               return this.component;
            } else {
               this.component = getChild(parentTag, parentComponent, newId);
               if (this.component == null) {
                  this.component = this.createChild(context, parentComponent, parentTag, newId);
               }

               return this.component;
            }
         } else {
            UIComponent parentComponent = context.getViewRoot();
            if (null == parentComponent.getAttributes().get("javax.faces.webapp.CURRENT_VIEW_ROOT")) {
               try {
                  this.setProperties(parentComponent);
               } catch (FacesException var8) {
                  if (var8.getCause() instanceof JspException) {
                     throw (JspException)var8.getCause();
                  }

                  throw var8;
               }

               if (null != this.id) {
                  parentComponent.setId(this.id);
               } else {
                  assert null != this.getFacesJspId();

                  parentComponent.setId(this.getFacesJspId());
               }

               parentComponent.getAttributes().put("javax.faces.webapp.CURRENT_VIEW_ROOT", "javax.faces.webapp.CURRENT_VIEW_ROOT");
               this.created = true;
            } else if (this.hasBinding()) {
               try {
                  this.setProperties(parentComponent);
               } catch (FacesException var7) {
                  if (var7.getCause() instanceof JspException) {
                     throw (JspException)var7.getCause();
                  }

                  throw var7;
               }
            }

            this.component = parentComponent;
            return this.component;
         }
      }
   }

   public static UIComponentClassicTagBase getParentUIComponentClassicTagBase(PageContext context) {
      return _getParentUIComponentClassicTagBase(getFacesContext(context));
   }

   private static UIComponentClassicTagBase _getParentUIComponentClassicTagBase(FacesContext facesContext) {
      return _getParentUIComponentClassicTagBase(_getContextMap(facesContext));
   }

   private static UIComponentClassicTagBase _getParentUIComponentClassicTagBase(Map cMap) {
      List list = null;
      if (cMap != null) {
         list = (List)cMap.get("javax.faces.webapp.COMPONENT_TAG_STACK");
      }

      return list != null ? (UIComponentClassicTagBase)list.get(list.size() - 1) : null;
   }

   protected int getIndexOfNextChildTag() {
      return this.createdComponents != null ? this.createdComponents.size() : 0;
   }

   protected void addChild(UIComponent child) {
      if (this.createdComponents == null) {
         this.createdComponents = new ArrayList(6);
      }

      this.createdComponents.add(child.getId());
   }

   void addChildToComponentAndTag(UIComponent child) {
      UIComponent myComponent = this.getComponentInstance();
      int indexOfNextChildTag = this.getIndexOfNextChildTag();
      if (indexOfNextChildTag > myComponent.getChildCount()) {
         indexOfNextChildTag = myComponent.getChildCount();
      }

      myComponent.getChildren().add(indexOfNextChildTag, child);
      this.addChild(child);
   }

   protected void addFacet(String name) {
      if (this.createdFacets == null) {
         this.createdFacets = new ArrayList(3);
      }

      this.createdFacets.add(name);
   }

   private void popUIComponentClassicTagBase() {
      assert null != this.contextMap;

      List list = (List)this.contextMap.get("javax.faces.webapp.COMPONENT_TAG_STACK");
      UIComponentClassicTagBase uic = null;

      while(list != null && uic != this) {
         int idx = list.size() - 1;
         uic = (UIComponentClassicTagBase)list.get(idx);
         list.remove(idx);
         if (idx < 1) {
            this.contextMap.remove("javax.faces.webapp.COMPONENT_TAG_STACK");
            list = null;
         }
      }

   }

   private void pushUIComponentClassicTagBase() {
      assert null != this.contextMap;

      List list = TypedCollections.dynamicallyCastList((List)this.contextMap.get("javax.faces.webapp.COMPONENT_TAG_STACK"), UIComponentClassicTagBase.class);
      if (list == null) {
         list = new ArrayList();
         this.contextMap.put("javax.faces.webapp.COMPONENT_TAG_STACK", list);
      }

      ((List)list).add(this);
   }

   private static int _indexOfStartingFrom(List list, int startIndex, Object searchValue) {
      int itemCount = list.size();
      boolean found = false;

      int currIndex;
      Object currId;
      for(currIndex = startIndex; currIndex < itemCount; ++currIndex) {
         currId = list.get(currIndex);
         if (searchValue == currId || searchValue != null && searchValue.equals(currId)) {
            return currIndex;
         }
      }

      if (startIndex > 0) {
         for(currIndex = 0; currIndex < startIndex; ++currIndex) {
            currId = list.get(currIndex);
            if (searchValue == currId || searchValue != null && searchValue.equals(currId)) {
               return currIndex;
            }
         }
      }

      return -1;
   }

   private void removeOldChildren() {
      Map attributes = this.component.getAttributes();
      List currentComponents = this.createdComponents;
      Object oldValue;
      if (currentComponents != null) {
         oldValue = attributes.put("javax.faces.webapp.COMPONENT_IDS", currentComponents);
         this.createdComponents = null;
      } else {
         oldValue = attributes.remove("javax.faces.webapp.COMPONENT_IDS");
      }

      if (oldValue != null) {
         List oldList = TypedCollections.dynamicallyCastList((List)oldValue, String.class);
         int oldCount = oldList.size();
         if (oldCount > 0) {
            String oldId;
            if (currentComponents != null) {
               int currStartIndex = 0;

               for(int oldIndex = 0; oldIndex < oldCount; ++oldIndex) {
                  oldId = (String)oldList.get(oldIndex);
                  int foundIndex = _indexOfStartingFrom(currentComponents, currStartIndex, oldId);
                  if (foundIndex != -1) {
                     currStartIndex = foundIndex + 1;
                  } else {
                     UIComponent child = this.component.findComponent(oldId);
                     if (child != null) {
                        this.component.getChildren().remove(child);
                     }
                  }
               }
            } else {
               List children = this.component.getChildren();
               Iterator var12 = oldList.iterator();

               while(var12.hasNext()) {
                  oldId = (String)var12.next();
                  UIComponent child = this.component.findComponent(oldId);
                  if (child != null) {
                     children.remove(child);
                  }
               }
            }
         }
      }

   }

   private void removeOldFacets() {
      Map attributes = this.component.getAttributes();
      List currentComponents = this.createdFacets;
      Object oldValue;
      if (currentComponents != null) {
         oldValue = attributes.put("javax.faces.webapp.FACET_NAMES", currentComponents);
         this.createdFacets = null;
      } else {
         oldValue = attributes.remove("javax.faces.webapp.FACET_NAMES");
      }

      if (oldValue != null) {
         List oldList = TypedCollections.dynamicallyCastList((List)oldValue, String.class);
         int oldCount = oldList.size();
         if (oldCount > 0) {
            String oldId;
            if (currentComponents != null) {
               int currStartIndex = 0;

               for(int oldIndex = 0; oldIndex < oldCount; ++oldIndex) {
                  oldId = (String)oldList.get(oldIndex);
                  int foundIndex = _indexOfStartingFrom(currentComponents, currStartIndex, oldId);
                  if (foundIndex != -1) {
                     currStartIndex = foundIndex + 1;
                  } else {
                     this.component.getFacets().remove(oldId);
                  }
               }
            } else {
               Map facets = this.component.getFacets();
               Iterator var11 = oldList.iterator();

               while(var11.hasNext()) {
                  oldId = (String)var11.next();
                  facets.remove(oldId);
               }
            }
         }
      }

   }

   protected UIComponent createVerbatimComponentFromBodyContent() {
      UIOutput verbatim = null;
      String bodyContentString;
      String trimString;
      if (null != this.bodyContent && null != (bodyContentString = this.bodyContent.getString()) && 0 < (trimString = this.bodyContent.getString().trim()).length()) {
         if (trimString.startsWith("<!--") && trimString.endsWith("-->")) {
            StringBuilder content = new StringBuilder(trimString.length());
            int sIdx = trimString.indexOf("<!--");

            for(int eIdx = trimString.indexOf("-->", sIdx); sIdx >= 0 && eIdx >= 0; eIdx = trimString.indexOf("-->", sIdx)) {
               if (sIdx == 0) {
                  trimString = trimString.substring(eIdx + 3);
               } else {
                  content.append(trimString.substring(0, sIdx));
                  trimString = trimString.substring(eIdx + 3);
               }

               sIdx = trimString.indexOf("<!--");
            }

            content.append(trimString);
            String result = content.toString();
            if (result.trim().length() > 0) {
               verbatim = this.createVerbatimComponent();
               verbatim.setValue(content.toString());
            }

            this.bodyContent.clearBody();
         } else {
            verbatim = this.createVerbatimComponent();
            verbatim.setValue(bodyContentString);
            this.bodyContent.clearBody();
         }
      }

      return verbatim;
   }

   protected UIOutput createVerbatimComponent() {
      assert null != this.getFacesContext();

      Application application = this.getFacesContext().getApplication();
      UIOutput verbatim = (UIOutput)application.createComponent("javax.faces.HtmlOutputText");
      verbatim.setTransient(true);
      verbatim.getAttributes().put("escape", Boolean.FALSE);
      verbatim.setId(this.getFacesContext().getViewRoot().createUniqueId());
      return verbatim;
   }

   protected void addVerbatimBeforeComponent(UIComponentClassicTagBase parentTag, UIComponent verbatim, UIComponent component) {
      UIComponent parent = component.getParent();
      if (null != parent) {
         List children = parent.getChildren();
         List createdIds = (List)parent.getAttributes().get("javax.faces.webapp.COMPONENT_IDS");
         int indexOfComponentInParent = children.indexOf(component);
         boolean replace = indexOfComponentInParent > 0 && createdIds != null && createdIds.size() == children.size();
         if (replace) {
            UIComponent oldVerbatim = (UIComponent)children.get(indexOfComponentInParent - 1);
            if (oldVerbatim instanceof UIOutput && oldVerbatim.isTransient()) {
               children.set(indexOfComponentInParent - 1, verbatim);
            } else {
               children.add(indexOfComponentInParent, verbatim);
            }
         } else {
            children.add(indexOfComponentInParent, verbatim);
         }

         parentTag.addChild(verbatim);
      }
   }

   protected void addVerbatimAfterComponent(UIComponentClassicTagBase parentTag, UIComponent verbatim, UIComponent component) {
      UIComponent parent = component.getParent();
      if (null != parent) {
         List children = parent.getChildren();
         int indexOfComponentInParent = children.indexOf(component);
         if (children.size() - 1 == indexOfComponentInParent) {
            children.add(verbatim);
         } else {
            children.add(indexOfComponentInParent + 1, verbatim);
         }

         parentTag.addChild(verbatim);
      }
   }

   public int doStartTag() throws JspException {
      this.createdComponents = null;
      this.createdFacets = null;
      UIComponent verbatim = null;
      this.context = this.getFacesContext();
      if (null == this.context) {
         throw new JspException("Can't find FacesContext");
      } else {
         this.getContextMap(this.context);

         assert null != this.contextMap;

         List list = (List)this.contextMap.get("javax.faces.webapp.COMPONENT_TAG_STACK");
         if (list != null) {
            this.parentTag = (UIComponentClassicTagBase)list.get(list.size() - 1);
         } else {
            this.parentTag = null;
         }

         if (null == this.getFacetName() && null != this.parentTag) {
            Tag p = this.getParent();
            if (null == p || !(p instanceof UIComponentTagBase)) {
               JspWriter out = this.pageContext.getOut();
               if (!(out instanceof BodyContent)) {
                  try {
                     out.flush();
                  } catch (IOException var8) {
                     throw new JspException(var8);
                  }
               }
            }

            verbatim = this.parentTag.createVerbatimComponentFromBodyContent();
         }

         this.component = this.findComponent(this.context);
         if (null != verbatim) {
            this.addVerbatimBeforeComponent(this.parentTag, verbatim, this.component);
         }

         Object tagInstance = null;
         String clientId = null;
         if (this.component instanceof NamingContainer || this.parentTag == null) {
            this.namingContainerChildIds = new HashMap();
         }

         if (this.id != null) {
            clientId = this.getId();
            UIComponentClassicTagBase temp = (UIComponentClassicTagBase)this.getParentNamingContainerTag().getNamingContainerChildIds().get(clientId);
            if (temp == this && !this.getJspId().equals(temp.getJspId())) {
               tagInstance = this;
            } else if (temp != null && temp != this && this.getJspId().equals(temp.getJspId())) {
               tagInstance = temp;
            }
         }

         if (tagInstance == null) {
            if (null != this.id && clientId != null) {
               if (this.getParentNamingContainerTag().getNamingContainerChildIds().containsKey(clientId)) {
                  StringWriter writer = new StringWriter(128);
                  printTree(this.context.getViewRoot(), clientId, writer, 0);
                  String msg = "Duplicate component id: '" + clientId + "', first used in tag: '" + this.getParentNamingContainerTag().getNamingContainerChildIds().get(clientId).getClass().getName() + "'\n" + writer.toString();
                  throw new JspException(new IllegalStateException(msg));
               }

               this.getParentNamingContainerTag().getNamingContainerChildIds().put(clientId, this);
            }

            if (this.parentTag != null) {
               if (this.getFacetName() == null) {
                  this.parentTag.addChild(this.component);
               } else {
                  this.parentTag.addFacet(this.getFacetName());
               }
            }
         }

         this.pushUIComponentClassicTagBase();
         return this.getDoStartValue();
      }
   }

   private Map getContextMap(FacesContext context) {
      if (null == this.contextMap) {
         Map requestMap = context.getExternalContext().getRequestMap();
         this.contextMap = (Map)requestMap.get("com.sun.faces.CONTEXT_MAP");
         if (null == this.contextMap) {
            this.contextMap = new HashMap(5);
            requestMap.put("com.sun.faces.CONTEXT_MAP", this.contextMap);
         }
      }

      return this.contextMap;
   }

   private static Map _getContextMap(FacesContext context) {
      Map rMap = context.getExternalContext().getRequestMap();
      return (Map)rMap.get("com.sun.faces.CONTEXT_MAP");
   }

   private void releaseContextMap(FacesContext context) {
      Map requestMap = context.getExternalContext().getRequestMap();
      if (null != this.contextMap) {
         this.contextMap.clear();
      }

      requestMap.remove("com.sun.faces.CONTEXT_MAP");
   }

   public int doEndTag() throws JspException {
      this.popUIComponentClassicTagBase();
      this.removeOldChildren();
      this.removeOldFacets();
      if (this.namingContainerChildIds != null) {
         this.namingContainerChildIds = null;
      }

      try {
         UIComponentClassicTagBase parentTag = _getParentUIComponentClassicTagBase(this.contextMap);
         UIComponent verbatim;
         if (null != (verbatim = this.createVerbatimComponentFromBodyContent())) {
            this.component.getChildren().add(verbatim);
            if (null != parentTag) {
               parentTag.addChild(verbatim);
            }
         }
      } catch (Throwable var6) {
         throw new JspException(var6);
      } finally {
         this.component = null;
         this.context = null;
      }

      this.release();
      return this.getDoEndValue();
   }

   public void release() {
      this.parent = null;
      this.id = null;
      this.facesJspId = null;
      this.created = false;
      this.bodyContent = null;
      this.isNestedInIterator = false;
      this._nextChildIndex = 0;
   }

   protected int getDoAfterBodyValue() throws JspException {
      return 0;
   }

   public void setBodyContent(BodyContent bodyContent) {
      this.bodyContent = bodyContent;
   }

   public JspWriter getPreviousOut() {
      return this.bodyContent.getEnclosingWriter();
   }

   public BodyContent getBodyContent() {
      return this.bodyContent;
   }

   public void doInitBody() throws JspException {
   }

   public int doAfterBody() throws JspException {
      UIComponentClassicTagBase parentTag = _getParentUIComponentClassicTagBase(this.contextMap);
      UIComponent verbatim;
      if ((this == parentTag || null != parentTag && parentTag.getComponentInstance().getRendersChildren()) && null != (verbatim = this.createVerbatimComponentFromBodyContent())) {
         List createdIds = (List)this.component.getAttributes().get("javax.faces.webapp.COMPONENT_IDS");
         if (createdIds != null) {
            int listIdx = this.component.getChildCount();
            if (createdIds.size() == listIdx) {
               this.component.getChildren().set(listIdx - 1, verbatim);
            } else {
               this.component.getChildren().add(verbatim);
            }
         } else {
            this.component.getChildren().add(verbatim);
         }

         parentTag.addChild(verbatim);
      }

      return this.getDoAfterBodyValue();
   }

   public void setId(String id) {
      if (null != id && id.startsWith("j_id")) {
         throw new IllegalArgumentException();
      } else {
         this.id = id;
      }
   }

   protected String getId() {
      return this.id;
   }

   protected String getFacesJspId() {
      if (null == this.facesJspId) {
         if (null != this.jspId) {
            this.facesJspId = "j_id_" + this.jspId;
            if (this.isDuplicateId(this.facesJspId)) {
               this.facesJspId = this.generateIncrementedId(this.facesJspId);
            }
         } else {
            this.facesJspId = this.getFacesContext().getViewRoot().createUniqueId();
         }
      }

      return this.facesJspId;
   }

   private boolean isDuplicateId(String componentId) {
      boolean result = false;
      if (this.parentTag != null) {
         if (this.parentTag.isNestedInIterator) {
            return true;
         }

         List childComponents = this.parentTag.createdComponents;
         if (childComponents != null) {
            result = childComponents.contains(componentId);
            if (result && !this.isNestedInIterator) {
               return true;
            }
         }
      }

      return result;
   }

   private String generateIncrementedId(String componentId) {
      assert null != this.contextMap;

      Integer serialNum = (Integer)this.contextMap.get(componentId);
      if (null == serialNum) {
         serialNum = 1;
      } else {
         serialNum = serialNum + 1;
      }

      this.contextMap.put(componentId, serialNum);
      componentId = componentId + "j_id_" + serialNum;
      return componentId;
   }

   protected List getCreatedComponents() {
      return this.createdComponents;
   }

   private String createId(FacesContext context) throws JspException {
      if (this.id == null) {
         return this.getFacesJspId();
      } else {
         if (this.isDuplicateId(this.id) && !this.isSpecifiedIdUnique(this.id)) {
            if (!this.isNestedInIterator) {
               StringWriter writer = new StringWriter(128);
               printTree(context.getViewRoot(), this.id, writer, 0);
               String msg = "Component ID '" + this.id + "' has already been used in the view.\nSee below for the view up to the point of the detected error.\n" + writer.toString();
               throw new JspException(msg);
            }

            this.id = this.generateIncrementedId(this.id);
         }

         return this.id;
      }
   }

   private boolean isSpecifiedIdUnique(String id) {
      UIComponentClassicTagBase containerTag = this.getParentNamingContainerTag();
      UIComponent c = containerTag.component.findComponent(id);
      if (c == null) {
         return true;
      } else {
         UIComponent parent = c.getParent();
         if (!parent.equals(this.parentTag.component)) {
            return false;
         } else {
            List created = this.parentTag.createdComponents;
            return created == null || !created.contains(id);
         }
      }
   }

   private UIComponentClassicTagBase getParentNamingContainerTag() {
      if (this.parentTag == null) {
         return this;
      } else {
         for(UIComponentClassicTagBase parent = this.parentTag; parent != null; parent = parent.parentTag) {
            if (parent.component instanceof NamingContainer || parent.parentTag == null && parent.component instanceof UIViewRoot) {
               return parent;
            }
         }

         return null;
      }
   }

   public void setJspId(String id) {
      this.jspId = null;
      Integer pcId = (Integer)this.pageContext.getAttribute("javax.faces.webapp.PAGECONTEXT_MARKER", 1);
      if (pcId == null) {
         this.getContextMap(this.getFacesContext());
         AtomicInteger aInt = (AtomicInteger)this.contextMap.get("javax.faces.webapp.PAGECONTEXT_COUNTER");
         if (aInt == null) {
            aInt = new AtomicInteger();
            this.contextMap.put("javax.faces.webapp.PAGECONTEXT_COUNTER", aInt);
         }

         pcId = aInt.incrementAndGet();
         this.pageContext.setAttribute("javax.faces.webapp.PAGECONTEXT_MARKER", pcId);
      }

      if (pcId > 1) {
         StringBuilder builder = new StringBuilder(id.length() + 3);
         builder.append(id).append("pc").append(pcId);
         this.jspId = builder.toString();
      } else {
         this.jspId = id;
      }

      this.facesJspId = null;
      this.updatePreviousJspIdAndIteratorStatus(this.jspId);
   }

   private void updatePreviousJspIdAndIteratorStatus(String id) {
      Set previousJspIdSet = TypedCollections.dynamicallyCastSet((Set)this.pageContext.getAttribute("javax.faces.webapp.PREVIOUS_JSP_ID_SET", 1), String.class);
      if (null == previousJspIdSet) {
         previousJspIdSet = new HashSet();
         this.pageContext.setAttribute("javax.faces.webapp.PREVIOUS_JSP_ID_SET", previousJspIdSet, 1);
      }

      if (((Set)previousJspIdSet).add(id)) {
         this.isNestedInIterator = false;
      } else {
         if (log.isLoggable(Level.FINEST)) {
            log.log(Level.FINEST, "Id " + id + " is nested within an iterating tag.");
         }

         this.isNestedInIterator = true;
      }

   }

   public String getJspId() {
      return this.jspId;
   }

   protected abstract void setProperties(UIComponent var1);

   protected abstract UIComponent createComponent(FacesContext var1, String var2) throws JspException;

   protected abstract boolean hasBinding();

   public UIComponent getComponentInstance() {
      return this.component;
   }

   public boolean getCreated() {
      return this.created;
   }

   private Map getNamingContainerChildIds() {
      return this.namingContainerChildIds;
   }

   protected FacesContext getFacesContext() {
      if (this.context == null && null == (this.context = (FacesContext)this.pageContext.getAttribute("javax.faces.webapp.CURRENT_FACES_CONTEXT"))) {
         this.context = FacesContext.getCurrentInstance();
         if (this.context == null) {
            throw new RuntimeException("Cannot find FacesContext");
         }

         this.pageContext.setAttribute("javax.faces.webapp.CURRENT_FACES_CONTEXT", this.context);
      }

      return this.context;
   }

   protected String getFacetName() {
      Tag parent = this.getParent();
      return parent instanceof FacetTag ? ((FacetTag)parent).getName() : null;
   }

   private static FacesContext getFacesContext(PageContext pageContext) {
      FacesContext context = (FacesContext)pageContext.getAttribute("javax.faces.webapp.CURRENT_FACES_CONTEXT");
      if (context == null) {
         context = FacesContext.getCurrentInstance();
         if (context == null) {
            throw new RuntimeException("Cannot find FacesContext");
         }

         pageContext.setAttribute("javax.faces.webapp.CURRENT_FACES_CONTEXT", context);
      }

      return context;
   }

   private static void printTree(UIComponent root, String duplicateId, Writer out, int curDepth) {
      if (null != root) {
         if (duplicateId.equals(root.getId())) {
            indentPrintln(out, "+id: " + root.getId() + "  <===============", curDepth);
         } else {
            indentPrintln(out, "+id: " + root.getId(), curDepth);
         }

         indentPrintln(out, " type: " + root.toString(), curDepth);
         ++curDepth;
         Iterator var4 = root.getFacets().values().iterator();

         UIComponent uiComponent;
         while(var4.hasNext()) {
            uiComponent = (UIComponent)var4.next();
            printTree(uiComponent, duplicateId, out, curDepth);
         }

         var4 = root.getChildren().iterator();

         while(var4.hasNext()) {
            uiComponent = (UIComponent)var4.next();
            printTree(uiComponent, duplicateId, out, curDepth);
         }

      }
   }

   private static void indentPrintln(Writer out, String str, int curDepth) {
      try {
         for(int i = 0; i < curDepth; ++i) {
            out.write("  ");
         }

         out.write(str + '\n');
      } catch (IOException var4) {
      }

   }
}
