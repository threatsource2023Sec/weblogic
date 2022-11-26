package org.python.icu.text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class MessagePatternUtil {
   private MessagePatternUtil() {
   }

   public static MessageNode buildMessageNode(String patternString) {
      return buildMessageNode(new MessagePattern(patternString));
   }

   public static MessageNode buildMessageNode(MessagePattern pattern) {
      int limit = pattern.countParts() - 1;
      if (limit < 0) {
         throw new IllegalArgumentException("The MessagePattern is empty");
      } else if (pattern.getPartType(0) != MessagePattern.Part.Type.MSG_START) {
         throw new IllegalArgumentException("The MessagePattern does not represent a MessageFormat pattern");
      } else {
         return buildMessageNode(pattern, 0, limit);
      }
   }

   private static MessageNode buildMessageNode(MessagePattern pattern, int start, int limit) {
      int prevPatternIndex = pattern.getPart(start).getLimit();
      MessageNode node = new MessageNode();
      int i = start + 1;

      while(true) {
         MessagePattern.Part part = pattern.getPart(i);
         int patternIndex = part.getIndex();
         if (prevPatternIndex < patternIndex) {
            node.addContentsNode(new TextNode(pattern.getPatternString().substring(prevPatternIndex, patternIndex)));
         }

         if (i == limit) {
            return node.freeze();
         }

         MessagePattern.Part.Type partType = part.getType();
         if (partType == MessagePattern.Part.Type.ARG_START) {
            int argLimit = pattern.getLimitPartIndex(i);
            node.addContentsNode(buildArgNode(pattern, i, argLimit));
            i = argLimit;
            part = pattern.getPart(argLimit);
         } else if (partType == MessagePattern.Part.Type.REPLACE_NUMBER) {
            node.addContentsNode(MessagePatternUtil.MessageContentsNode.createReplaceNumberNode());
         }

         prevPatternIndex = part.getLimit();
         ++i;
      }
   }

   private static ArgNode buildArgNode(MessagePattern pattern, int start, int limit) {
      ArgNode node = MessagePatternUtil.ArgNode.createArgNode();
      MessagePattern.Part part = pattern.getPart(start);
      MessagePattern.ArgType argType = node.argType = part.getArgType();
      ++start;
      part = pattern.getPart(start);
      node.name = pattern.getSubstring(part);
      if (part.getType() == MessagePattern.Part.Type.ARG_NUMBER) {
         node.number = part.getValue();
      }

      ++start;
      switch (argType) {
         case SIMPLE:
            node.typeName = pattern.getSubstring(pattern.getPart(start++));
            if (start < limit) {
               node.style = pattern.getSubstring(pattern.getPart(start));
            }
            break;
         case CHOICE:
            node.typeName = "choice";
            node.complexStyle = buildChoiceStyleNode(pattern, start, limit);
            break;
         case PLURAL:
            node.typeName = "plural";
            node.complexStyle = buildPluralStyleNode(pattern, start, limit, argType);
            break;
         case SELECT:
            node.typeName = "select";
            node.complexStyle = buildSelectStyleNode(pattern, start, limit);
            break;
         case SELECTORDINAL:
            node.typeName = "selectordinal";
            node.complexStyle = buildPluralStyleNode(pattern, start, limit, argType);
      }

      return node;
   }

   private static ComplexArgStyleNode buildChoiceStyleNode(MessagePattern pattern, int start, int limit) {
      ComplexArgStyleNode node;
      int msgLimit;
      for(node = new ComplexArgStyleNode(MessagePattern.ArgType.CHOICE); start < limit; start = msgLimit + 1) {
         int valueIndex = start;
         MessagePattern.Part part = pattern.getPart(start);
         double value = pattern.getNumericValue(part);
         start += 2;
         msgLimit = pattern.getLimitPartIndex(start);
         VariantNode variant = new VariantNode();
         variant.selector = pattern.getSubstring(pattern.getPart(valueIndex + 1));
         variant.numericValue = value;
         variant.msgNode = buildMessageNode(pattern, start, msgLimit);
         node.addVariant(variant);
      }

      return node.freeze();
   }

   private static ComplexArgStyleNode buildPluralStyleNode(MessagePattern pattern, int start, int limit, MessagePattern.ArgType argType) {
      ComplexArgStyleNode node = new ComplexArgStyleNode(argType);
      MessagePattern.Part offset = pattern.getPart(start);
      if (offset.getType().hasNumericValue()) {
         node.explicitOffset = true;
         node.offset = pattern.getNumericValue(offset);
         ++start;
      }

      while(start < limit) {
         MessagePattern.Part selector = pattern.getPart(start++);
         double value = -1.23456789E8;
         MessagePattern.Part part = pattern.getPart(start);
         if (part.getType().hasNumericValue()) {
            value = pattern.getNumericValue(part);
            ++start;
         }

         int msgLimit = pattern.getLimitPartIndex(start);
         VariantNode variant = new VariantNode();
         variant.selector = pattern.getSubstring(selector);
         variant.numericValue = value;
         variant.msgNode = buildMessageNode(pattern, start, msgLimit);
         node.addVariant(variant);
         start = msgLimit + 1;
      }

      return node.freeze();
   }

   private static ComplexArgStyleNode buildSelectStyleNode(MessagePattern pattern, int start, int limit) {
      ComplexArgStyleNode node;
      int msgLimit;
      for(node = new ComplexArgStyleNode(MessagePattern.ArgType.SELECT); start < limit; start = msgLimit + 1) {
         MessagePattern.Part selector = pattern.getPart(start++);
         msgLimit = pattern.getLimitPartIndex(start);
         VariantNode variant = new VariantNode();
         variant.selector = pattern.getSubstring(selector);
         variant.msgNode = buildMessageNode(pattern, start, msgLimit);
         node.addVariant(variant);
      }

      return node.freeze();
   }

   public static class VariantNode extends Node {
      private String selector;
      private double numericValue;
      private MessageNode msgNode;

      public String getSelector() {
         return this.selector;
      }

      public boolean isSelectorNumeric() {
         return this.numericValue != -1.23456789E8;
      }

      public double getSelectorValue() {
         return this.numericValue;
      }

      public MessageNode getMessage() {
         return this.msgNode;
      }

      public String toString() {
         StringBuilder sb = new StringBuilder();
         if (this.isSelectorNumeric()) {
            sb.append(this.numericValue).append(" (").append(this.selector).append(") {");
         } else {
            sb.append(this.selector).append(" {");
         }

         return sb.append(this.msgNode.toString()).append('}').toString();
      }

      private VariantNode() {
         super(null);
         this.numericValue = -1.23456789E8;
      }

      // $FF: synthetic method
      VariantNode(Object x0) {
         this();
      }
   }

   public static class ComplexArgStyleNode extends Node {
      private MessagePattern.ArgType argType;
      private double offset;
      private boolean explicitOffset;
      private volatile List list;

      public MessagePattern.ArgType getArgType() {
         return this.argType;
      }

      public boolean hasExplicitOffset() {
         return this.explicitOffset;
      }

      public double getOffset() {
         return this.offset;
      }

      public List getVariants() {
         return this.list;
      }

      public VariantNode getVariantsByType(List numericVariants, List keywordVariants) {
         if (numericVariants != null) {
            numericVariants.clear();
         }

         keywordVariants.clear();
         VariantNode other = null;
         Iterator var4 = this.list.iterator();

         while(var4.hasNext()) {
            VariantNode variant = (VariantNode)var4.next();
            if (variant.isSelectorNumeric()) {
               numericVariants.add(variant);
            } else if ("other".equals(variant.getSelector())) {
               if (other == null) {
                  other = variant;
               }
            } else {
               keywordVariants.add(variant);
            }
         }

         return other;
      }

      public String toString() {
         StringBuilder sb = new StringBuilder();
         sb.append('(').append(this.argType.toString()).append(" style) ");
         if (this.hasExplicitOffset()) {
            sb.append("offset:").append(this.offset).append(' ');
         }

         return sb.append(this.list.toString()).toString();
      }

      private ComplexArgStyleNode(MessagePattern.ArgType argType) {
         super(null);
         this.list = new ArrayList();
         this.argType = argType;
      }

      private void addVariant(VariantNode variant) {
         this.list.add(variant);
      }

      private ComplexArgStyleNode freeze() {
         this.list = Collections.unmodifiableList(this.list);
         return this;
      }

      // $FF: synthetic method
      ComplexArgStyleNode(MessagePattern.ArgType x0, Object x1) {
         this(x0);
      }
   }

   public static class ArgNode extends MessageContentsNode {
      private MessagePattern.ArgType argType;
      private String name;
      private int number = -1;
      private String typeName;
      private String style;
      private ComplexArgStyleNode complexStyle;

      public MessagePattern.ArgType getArgType() {
         return this.argType;
      }

      public String getName() {
         return this.name;
      }

      public int getNumber() {
         return this.number;
      }

      public String getTypeName() {
         return this.typeName;
      }

      public String getSimpleStyle() {
         return this.style;
      }

      public ComplexArgStyleNode getComplexStyle() {
         return this.complexStyle;
      }

      public String toString() {
         StringBuilder sb = new StringBuilder();
         sb.append('{').append(this.name);
         if (this.argType != MessagePattern.ArgType.NONE) {
            sb.append(',').append(this.typeName);
            if (this.argType == MessagePattern.ArgType.SIMPLE) {
               if (this.style != null) {
                  sb.append(',').append(this.style);
               }
            } else {
               sb.append(',').append(this.complexStyle.toString());
            }
         }

         return sb.append('}').toString();
      }

      private ArgNode() {
         super(MessagePatternUtil.MessageContentsNode.Type.ARG, null);
      }

      private static ArgNode createArgNode() {
         return new ArgNode();
      }
   }

   public static class TextNode extends MessageContentsNode {
      private String text;

      public String getText() {
         return this.text;
      }

      public String toString() {
         return "«" + this.text + "»";
      }

      private TextNode(String text) {
         super(MessagePatternUtil.MessageContentsNode.Type.TEXT, null);
         this.text = text;
      }

      // $FF: synthetic method
      TextNode(String x0, Object x1) {
         this(x0);
      }
   }

   public static class MessageContentsNode extends Node {
      private Type type;

      public Type getType() {
         return this.type;
      }

      public String toString() {
         return "{REPLACE_NUMBER}";
      }

      private MessageContentsNode(Type type) {
         super(null);
         this.type = type;
      }

      private static MessageContentsNode createReplaceNumberNode() {
         return new MessageContentsNode(MessagePatternUtil.MessageContentsNode.Type.REPLACE_NUMBER);
      }

      // $FF: synthetic method
      MessageContentsNode(Type x0, Object x1) {
         this(x0);
      }

      public static enum Type {
         TEXT,
         ARG,
         REPLACE_NUMBER;
      }
   }

   public static class MessageNode extends Node {
      private volatile List list;

      public List getContents() {
         return this.list;
      }

      public String toString() {
         return this.list.toString();
      }

      private MessageNode() {
         super(null);
         this.list = new ArrayList();
      }

      private void addContentsNode(MessageContentsNode node) {
         if (node instanceof TextNode && !this.list.isEmpty()) {
            MessageContentsNode lastNode = (MessageContentsNode)this.list.get(this.list.size() - 1);
            if (lastNode instanceof TextNode) {
               TextNode textNode = (TextNode)lastNode;
               textNode.text = textNode.text + ((TextNode)node).text;
               return;
            }
         }

         this.list.add(node);
      }

      private MessageNode freeze() {
         this.list = Collections.unmodifiableList(this.list);
         return this;
      }

      // $FF: synthetic method
      MessageNode(Object x0) {
         this();
      }
   }

   public static class Node {
      private Node() {
      }

      // $FF: synthetic method
      Node(Object x0) {
         this();
      }
   }
}
