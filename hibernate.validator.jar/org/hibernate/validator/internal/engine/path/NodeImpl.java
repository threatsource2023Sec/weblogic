package org.hibernate.validator.internal.engine.path;

import java.io.Serializable;
import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.validation.ElementKind;
import javax.validation.Path;
import org.hibernate.validator.internal.util.Contracts;
import org.hibernate.validator.internal.util.TypeVariables;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.hibernate.validator.path.ContainerElementNode;
import org.hibernate.validator.path.PropertyNode;

public class NodeImpl implements Path.PropertyNode, Path.MethodNode, Path.ConstructorNode, Path.BeanNode, Path.ParameterNode, Path.ReturnValueNode, Path.CrossParameterNode, Path.ContainerElementNode, PropertyNode, ContainerElementNode, Serializable {
   private static final long serialVersionUID = 2075466571633860499L;
   private static final Class[] EMPTY_CLASS_ARRAY = new Class[0];
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
   private static final String INDEX_OPEN = "[";
   private static final String INDEX_CLOSE = "]";
   private static final String TYPE_PARAMETER_OPEN = "<";
   private static final String TYPE_PARAMETER_CLOSE = ">";
   public static final String RETURN_VALUE_NODE_NAME = "<return value>";
   public static final String CROSS_PARAMETER_NODE_NAME = "<cross-parameter>";
   public static final String ITERABLE_ELEMENT_NODE_NAME = "<iterable element>";
   public static final String LIST_ELEMENT_NODE_NAME = "<list element>";
   public static final String MAP_KEY_NODE_NAME = "<map key>";
   public static final String MAP_VALUE_NODE_NAME = "<map value>";
   private final String name;
   private final NodeImpl parent;
   private final boolean isIterable;
   private final Integer index;
   private final Object key;
   private final ElementKind kind;
   private final Class[] parameterTypes;
   private final Integer parameterIndex;
   private final Object value;
   private final Class containerClass;
   private final Integer typeArgumentIndex;
   private int hashCode = -1;
   private String asString;

   private NodeImpl(String name, NodeImpl parent, boolean isIterable, Integer index, Object key, ElementKind kind, Class[] parameterTypes, Integer parameterIndex, Object value, Class containerClass, Integer typeArgumentIndex) {
      this.name = name;
      this.parent = parent;
      this.index = index;
      this.key = key;
      this.value = value;
      this.isIterable = isIterable;
      this.kind = kind;
      this.parameterTypes = parameterTypes;
      this.parameterIndex = parameterIndex;
      this.containerClass = containerClass;
      this.typeArgumentIndex = typeArgumentIndex;
   }

   public static NodeImpl createPropertyNode(String name, NodeImpl parent) {
      return new NodeImpl(name, parent, false, (Integer)null, (Object)null, ElementKind.PROPERTY, EMPTY_CLASS_ARRAY, (Integer)null, (Object)null, (Class)null, (Integer)null);
   }

   public static NodeImpl createContainerElementNode(String name, NodeImpl parent) {
      return new NodeImpl(name, parent, false, (Integer)null, (Object)null, ElementKind.CONTAINER_ELEMENT, EMPTY_CLASS_ARRAY, (Integer)null, (Object)null, (Class)null, (Integer)null);
   }

   public static NodeImpl createParameterNode(String name, NodeImpl parent, int parameterIndex) {
      return new NodeImpl(name, parent, false, (Integer)null, (Object)null, ElementKind.PARAMETER, EMPTY_CLASS_ARRAY, parameterIndex, (Object)null, (Class)null, (Integer)null);
   }

   public static NodeImpl createCrossParameterNode(NodeImpl parent) {
      return new NodeImpl("<cross-parameter>", parent, false, (Integer)null, (Object)null, ElementKind.CROSS_PARAMETER, EMPTY_CLASS_ARRAY, (Integer)null, (Object)null, (Class)null, (Integer)null);
   }

   public static NodeImpl createMethodNode(String name, NodeImpl parent, Class[] parameterTypes) {
      return new NodeImpl(name, parent, false, (Integer)null, (Object)null, ElementKind.METHOD, parameterTypes, (Integer)null, (Object)null, (Class)null, (Integer)null);
   }

   public static NodeImpl createConstructorNode(String name, NodeImpl parent, Class[] parameterTypes) {
      return new NodeImpl(name, parent, false, (Integer)null, (Object)null, ElementKind.CONSTRUCTOR, parameterTypes, (Integer)null, (Object)null, (Class)null, (Integer)null);
   }

   public static NodeImpl createBeanNode(NodeImpl parent) {
      return new NodeImpl((String)null, parent, false, (Integer)null, (Object)null, ElementKind.BEAN, EMPTY_CLASS_ARRAY, (Integer)null, (Object)null, (Class)null, (Integer)null);
   }

   public static NodeImpl createReturnValue(NodeImpl parent) {
      return new NodeImpl("<return value>", parent, false, (Integer)null, (Object)null, ElementKind.RETURN_VALUE, EMPTY_CLASS_ARRAY, (Integer)null, (Object)null, (Class)null, (Integer)null);
   }

   public static NodeImpl makeIterable(NodeImpl node) {
      return new NodeImpl(node.name, node.parent, true, (Integer)null, (Object)null, node.kind, node.parameterTypes, node.parameterIndex, node.value, node.containerClass, node.typeArgumentIndex);
   }

   public static NodeImpl makeIterableAndSetIndex(NodeImpl node, Integer index) {
      return new NodeImpl(node.name, node.parent, true, index, (Object)null, node.kind, node.parameterTypes, node.parameterIndex, node.value, node.containerClass, node.typeArgumentIndex);
   }

   public static NodeImpl makeIterableAndSetMapKey(NodeImpl node, Object key) {
      return new NodeImpl(node.name, node.parent, true, (Integer)null, key, node.kind, node.parameterTypes, node.parameterIndex, node.value, node.containerClass, node.typeArgumentIndex);
   }

   public static NodeImpl setPropertyValue(NodeImpl node, Object value) {
      return new NodeImpl(node.name, node.parent, node.isIterable, node.index, node.key, node.kind, node.parameterTypes, node.parameterIndex, value, node.containerClass, node.typeArgumentIndex);
   }

   public static NodeImpl setTypeParameter(NodeImpl node, Class containerClass, Integer typeArgumentIndex) {
      return new NodeImpl(node.name, node.parent, node.isIterable, node.index, node.key, node.kind, node.parameterTypes, node.parameterIndex, node.value, containerClass, typeArgumentIndex);
   }

   public final String getName() {
      return this.name;
   }

   public final boolean isInIterable() {
      return this.parent != null && this.parent.isIterable();
   }

   public final boolean isIterable() {
      return this.isIterable;
   }

   public final Integer getIndex() {
      return this.parent == null ? null : this.parent.index;
   }

   public final Object getKey() {
      return this.parent == null ? null : this.parent.key;
   }

   public Class getContainerClass() {
      Contracts.assertTrue(this.kind == ElementKind.BEAN || this.kind == ElementKind.PROPERTY || this.kind == ElementKind.CONTAINER_ELEMENT, "getContainerClass() may only be invoked for nodes of type ElementKind.BEAN, ElementKind.PROPERTY or ElementKind.CONTAINER_ELEMENT.");
      return this.parent == null ? null : this.parent.containerClass;
   }

   public Integer getTypeArgumentIndex() {
      Contracts.assertTrue(this.kind == ElementKind.BEAN || this.kind == ElementKind.PROPERTY || this.kind == ElementKind.CONTAINER_ELEMENT, "getTypeArgumentIndex() may only be invoked for nodes of type ElementKind.BEAN, ElementKind.PROPERTY or ElementKind.CONTAINER_ELEMENT.");
      return this.parent == null ? null : this.parent.typeArgumentIndex;
   }

   public final NodeImpl getParent() {
      return this.parent;
   }

   public ElementKind getKind() {
      return this.kind;
   }

   public Path.Node as(Class nodeType) {
      if ((this.kind != ElementKind.BEAN || nodeType != Path.BeanNode.class) && (this.kind != ElementKind.CONSTRUCTOR || nodeType != Path.ConstructorNode.class) && (this.kind != ElementKind.CROSS_PARAMETER || nodeType != Path.CrossParameterNode.class) && (this.kind != ElementKind.METHOD || nodeType != Path.MethodNode.class) && (this.kind != ElementKind.PARAMETER || nodeType != Path.ParameterNode.class) && (this.kind != ElementKind.PROPERTY || nodeType != Path.PropertyNode.class && nodeType != PropertyNode.class) && (this.kind != ElementKind.RETURN_VALUE || nodeType != Path.ReturnValueNode.class) && (this.kind != ElementKind.CONTAINER_ELEMENT || nodeType != Path.ContainerElementNode.class && nodeType != ContainerElementNode.class)) {
         throw LOG.getUnableToNarrowNodeTypeException(this.getClass(), this.kind, nodeType);
      } else {
         return (Path.Node)nodeType.cast(this);
      }
   }

   public List getParameterTypes() {
      return Arrays.asList(this.parameterTypes);
   }

   public int getParameterIndex() {
      Contracts.assertTrue(this.kind == ElementKind.PARAMETER, "getParameterIndex() may only be invoked for nodes of type ElementKind.PARAMETER.");
      return this.parameterIndex;
   }

   public Object getValue() {
      return this.value;
   }

   public String toString() {
      return this.asString();
   }

   public final String asString() {
      if (this.asString == null) {
         this.asString = this.buildToString();
      }

      return this.asString;
   }

   private String buildToString() {
      StringBuilder builder = new StringBuilder();
      if (this.getName() != null) {
         builder.append(this.getName());
      }

      if (includeTypeParameterInformation(this.containerClass, this.typeArgumentIndex)) {
         builder.append("<");
         builder.append(TypeVariables.getTypeParameterName(this.containerClass, this.typeArgumentIndex));
         builder.append(">");
      }

      if (this.isIterable()) {
         builder.append("[");
         if (this.index != null) {
            builder.append(this.index);
         } else if (this.key != null) {
            builder.append(this.key);
         }

         builder.append("]");
      }

      return builder.toString();
   }

   private static boolean includeTypeParameterInformation(Class containerClass, Integer typeArgumentIndex) {
      if (containerClass != null && typeArgumentIndex != null) {
         if (containerClass.getTypeParameters().length < 2) {
            return false;
         } else {
            return !Map.class.isAssignableFrom(containerClass) || typeArgumentIndex != 1;
         }
      } else {
         return false;
      }
   }

   public final int buildHashCode() {
      int prime = true;
      int result = 1;
      result = 31 * result + (this.index == null ? 0 : this.index.hashCode());
      result = 31 * result + (this.isIterable ? 1231 : 1237);
      result = 31 * result + (this.key == null ? 0 : this.key.hashCode());
      result = 31 * result + (this.kind == null ? 0 : this.kind.hashCode());
      result = 31 * result + (this.name == null ? 0 : this.name.hashCode());
      result = 31 * result + (this.parameterIndex == null ? 0 : this.parameterIndex.hashCode());
      result = 31 * result + (this.parameterTypes == null ? 0 : Arrays.hashCode(this.parameterTypes));
      result = 31 * result + (this.containerClass == null ? 0 : this.containerClass.hashCode());
      result = 31 * result + (this.typeArgumentIndex == null ? 0 : this.typeArgumentIndex.hashCode());
      return result;
   }

   public int hashCode() {
      if (this.hashCode == -1) {
         this.hashCode = this.buildHashCode();
      }

      return this.hashCode;
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         NodeImpl other = (NodeImpl)obj;
         if (this.index == null) {
            if (other.index != null) {
               return false;
            }
         } else if (!this.index.equals(other.index)) {
            return false;
         }

         if (this.isIterable != other.isIterable) {
            return false;
         } else {
            if (this.key == null) {
               if (other.key != null) {
                  return false;
               }
            } else if (!this.key.equals(other.key)) {
               return false;
            }

            if (this.containerClass == null) {
               if (other.containerClass != null) {
                  return false;
               }
            } else if (!this.containerClass.equals(other.containerClass)) {
               return false;
            }

            if (this.typeArgumentIndex == null) {
               if (other.typeArgumentIndex != null) {
                  return false;
               }
            } else if (!this.typeArgumentIndex.equals(other.typeArgumentIndex)) {
               return false;
            }

            if (this.kind != other.kind) {
               return false;
            } else {
               if (this.name == null) {
                  if (other.name != null) {
                     return false;
                  }
               } else if (!this.name.equals(other.name)) {
                  return false;
               }

               if (this.parameterIndex == null) {
                  if (other.parameterIndex != null) {
                     return false;
                  }
               } else if (!this.parameterIndex.equals(other.parameterIndex)) {
                  return false;
               }

               if (this.parameterTypes == null) {
                  if (other.parameterTypes != null) {
                     return false;
                  }
               } else if (!Arrays.equals(this.parameterTypes, other.parameterTypes)) {
                  return false;
               }

               return true;
            }
         }
      }
   }
}
