package com.oracle.wls.shaded.org.apache.xalan.xsltc.runtime;

import java.util.ListResourceBundle;

public class ErrorMessages_zh_TW extends ListResourceBundle {
   public Object[][] getContents() {
      return new Object[][]{{"RUN_TIME_INTERNAL_ERR", "''{0}'' 發生執行時期內部錯誤"}, {"RUN_TIME_COPY_ERR", "執行 <xsl:copy> 時，發生執行時期錯誤。"}, {"DATA_CONVERSION_ERR", "從 ''{0}'' 成為 ''{1}'' 的轉換無效。"}, {"EXTERNAL_FUNC_ERR", "XSLTC 不支援外部函數 ''{0}''。"}, {"EQUALITY_EXPR_ERR", "相等表示式中包含不明的引數類型。"}, {"INVALID_ARGUMENT_ERR", "呼叫 ''{1}'' 所使用的引數類型 ''{0}'' 無效。"}, {"FORMAT_NUMBER_ERR", "嘗試使用型樣 ''{1}'' 格式化數字 ''{0}''。"}, {"ITERATOR_CLONE_ERR", "無法複製重複項目 ''{0}''。"}, {"AXIS_SUPPORT_ERR", "不支援軸 ''{0}'' 的重複項目。"}, {"TYPED_AXIS_SUPPORT_ERR", "不支援所鍵入軸 ''{0}'' 的重複項目。"}, {"STRAY_ATTRIBUTE_ERR", "屬性 ''{0}'' 超出元素外。"}, {"STRAY_NAMESPACE_ERR", "名稱空間宣告 ''{0}''=''{1}'' 超出元素外。"}, {"NAMESPACE_PREFIX_ERR", "字首 ''{0}'' 的名稱空間尚未宣告。"}, {"DOM_ADAPTER_INIT_ERR", "建立 DOMAdapter 時使用的原始檔 DOM 類型錯誤。"}, {"PARSER_DTD_SUPPORT_ERR", "您使用的 SAX 剖析器無法處理 DTD 宣告事件。"}, {"NAMESPACES_SUPPORT_ERR", "您使用的 SAX 剖析器不支援 XML 名稱空間。"}, {"CANT_RESOLVE_RELATIVE_URI_ERR", "無法解析 URI 參照 ''{0}''。"}, {"UNSUPPORTED_XSL_ERR", "XSL 元素 ''{0}'' 不受支援"}, {"UNSUPPORTED_EXT_ERR", "XSLTC 延伸項目 ''{0}'' 無法辨識"}, {"UNKNOWN_TRANSLET_VERSION_ERR", "指定的 translet ''{0}'' 是以比使用中 XSLTC 執行時期版本更新的 XSLTC 版本所建立。您必須重新編譯樣式表或使用更新的 XSLTC 版本來執行此 translet。"}, {"INVALID_QNAME_ERR", "一個值必須是 QName 的屬性，具有值 ''{0}''"}, {"INVALID_NCNAME_ERR", "一個值必須是 NCName 的屬性，具有值 ''{0}''"}, {"UNALLOWED_EXTENSION_FUNCTION_ERR", "當安全處理特性設為 true 時，不接受使用延伸函數 ''{0}''。"}, {"UNALLOWED_EXTENSION_ELEMENT_ERR", "當安全處理特性設為 true 時，不接受使用延伸元素 ''{0}''。"}};
   }
}
