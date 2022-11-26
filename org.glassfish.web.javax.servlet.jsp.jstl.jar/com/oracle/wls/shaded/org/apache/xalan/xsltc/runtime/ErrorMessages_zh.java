package com.oracle.wls.shaded.org.apache.xalan.xsltc.runtime;

import java.util.ListResourceBundle;

public class ErrorMessages_zh extends ListResourceBundle {
   public Object[][] getContents() {
      return new Object[][]{{"RUN_TIME_INTERNAL_ERR", "“{0}”中出现运行时内部错误"}, {"RUN_TIME_COPY_ERR", "在执行 <xsl:copy> 时发生运行时错误。"}, {"DATA_CONVERSION_ERR", "从“{0}”到“{1}”的转换无效。"}, {"EXTERNAL_FUNC_ERR", "XSLTC 不支持外部函数“{0}”。"}, {"EQUALITY_EXPR_ERR", "等式表达式中的自变量类型未知。"}, {"INVALID_ARGUMENT_ERR", "调用“{1}”时使用的参数类型“{0}”无效"}, {"FORMAT_NUMBER_ERR", "试图使用模式“{1}”为数字“{0}”编排格式。"}, {"ITERATOR_CLONE_ERR", "无法克隆迭代器“{0}”。"}, {"AXIS_SUPPORT_ERR", "不支持轴“{0}”的迭代器。"}, {"TYPED_AXIS_SUPPORT_ERR", "不支持输入的轴“{0}”的迭代器。"}, {"STRAY_ATTRIBUTE_ERR", "属性“{0}”在元素外。"}, {"STRAY_NAMESPACE_ERR", "名称空间声明“{0}”=“{1}”在元素外。"}, {"NAMESPACE_PREFIX_ERR", "尚未声明前缀“{0}”的名称空间。"}, {"DOM_ADAPTER_INIT_ERR", "使用错误类型的源 DOM 创建了 DOMAdapter。"}, {"PARSER_DTD_SUPPORT_ERR", "正在使用的 SAX 解析器不处理 DTD 声明事件。"}, {"NAMESPACES_SUPPORT_ERR", "正在使用的 SAX 解析器不支持 XML 名称空间。"}, {"CANT_RESOLVE_RELATIVE_URI_ERR", "无法解析 URI 引用“{0}”。"}, {"UNSUPPORTED_XSL_ERR", "不受支持的 XSL 元素“{0}”"}, {"UNSUPPORTED_EXT_ERR", "未被识别的 XSLTC 扩展名“{0}”"}, {"UNKNOWN_TRANSLET_VERSION_ERR", "创建指定的 translet“{0}”时，使用的 XSLTC 版本比正在使用的 XSLTC 运行时版本更新。您必须重新编译样式表或使用更新的 XSLTC 版本来运行此 translet。"}, {"INVALID_QNAME_ERR", "值必须为 QName 的属性具有值“{0}”"}, {"INVALID_NCNAME_ERR", "值必须为 NCName 的属性具有值“{0}”"}, {"UNALLOWED_EXTENSION_FUNCTION_ERR", "当安全处理功能设置为 true 时，不允许使用扩展函数“{0}”。"}, {"UNALLOWED_EXTENSION_ELEMENT_ERR", "当安全处理功能设置为 true 时，不允许使用扩展元素“{0}”。"}};
   }
}
