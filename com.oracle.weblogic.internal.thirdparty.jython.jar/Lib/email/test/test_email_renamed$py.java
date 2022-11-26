package email.test;

import java.util.Arrays;
import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyDictionary;
import org.python.core.PyException;
import org.python.core.PyFloat;
import org.python.core.PyFrame;
import org.python.core.PyFunction;
import org.python.core.PyFunctionTable;
import org.python.core.PyInteger;
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.PyUnicode;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849384000L)
@Filename("email/test/test_email_renamed.py")
public class test_email_renamed$py extends PyFunctionTable implements PyRunnable {
   static test_email_renamed$py self;
   static final PyCode f$0;
   static final PyCode openfile$1;
   static final PyCode TestEmailBase$2;
   static final PyCode ndiffAssertEqual$3;
   static final PyCode _msgobj$4;
   static final PyCode TestMessageAPI$5;
   static final PyCode test_get_all$6;
   static final PyCode test_getset_charset$7;
   static final PyCode test_set_charset_from_string$8;
   static final PyCode test_set_payload_with_charset$9;
   static final PyCode test_get_charsets$10;
   static final PyCode test_get_filename$11;
   static final PyCode test_get_filename_with_name_parameter$12;
   static final PyCode test_get_boundary$13;
   static final PyCode test_set_boundary$14;
   static final PyCode test_get_decoded_payload$15;
   static final PyCode test_get_decoded_uu_payload$16;
   static final PyCode test_decoded_generator$17;
   static final PyCode test__contains__$18;
   static final PyCode test_as_string$19;
   static final PyCode test_bad_param$20;
   static final PyCode test_missing_filename$21;
   static final PyCode test_bogus_filename$22;
   static final PyCode test_missing_boundary$23;
   static final PyCode test_get_params$24;
   static final PyCode test_get_param_liberal$25;
   static final PyCode test_get_param$26;
   static final PyCode test_get_param_funky_continuation_lines$27;
   static final PyCode test_get_param_with_semis_in_quotes$28;
   static final PyCode test_has_key$29;
   static final PyCode test_set_param$30;
   static final PyCode test_del_param$31;
   static final PyCode test_del_param_on_other_header$32;
   static final PyCode test_set_type$33;
   static final PyCode test_set_type_on_other_header$34;
   static final PyCode test_get_content_type_missing$35;
   static final PyCode test_get_content_type_missing_with_default_type$36;
   static final PyCode test_get_content_type_from_message_implicit$37;
   static final PyCode test_get_content_type_from_message_explicit$38;
   static final PyCode test_get_content_type_from_message_text_plain_implicit$39;
   static final PyCode test_get_content_type_from_message_text_plain_explicit$40;
   static final PyCode test_get_content_maintype_missing$41;
   static final PyCode test_get_content_maintype_missing_with_default_type$42;
   static final PyCode test_get_content_maintype_from_message_implicit$43;
   static final PyCode test_get_content_maintype_from_message_explicit$44;
   static final PyCode test_get_content_maintype_from_message_text_plain_implicit$45;
   static final PyCode test_get_content_maintype_from_message_text_plain_explicit$46;
   static final PyCode test_get_content_subtype_missing$47;
   static final PyCode test_get_content_subtype_missing_with_default_type$48;
   static final PyCode test_get_content_subtype_from_message_implicit$49;
   static final PyCode test_get_content_subtype_from_message_explicit$50;
   static final PyCode test_get_content_subtype_from_message_text_plain_implicit$51;
   static final PyCode test_get_content_subtype_from_message_text_plain_explicit$52;
   static final PyCode test_get_content_maintype_error$53;
   static final PyCode test_get_content_subtype_error$54;
   static final PyCode test_replace_header$55;
   static final PyCode test_broken_base64_payload$56;
   static final PyCode TestEncoders$57;
   static final PyCode test_encode_empty_payload$58;
   static final PyCode test_default_cte$59;
   static final PyCode test_default_cte$60;
   static final PyCode TestLongHeaders$61;
   static final PyCode test_split_long_continuation$62;
   static final PyCode test_another_long_almost_unsplittable_header$63;
   static final PyCode test_long_nonstring$64;
   static final PyCode test_long_header_encode$65;
   static final PyCode test_long_header_encode_with_tab_continuation$66;
   static final PyCode test_header_splitter$67;
   static final PyCode test_no_semis_header_splitter$68;
   static final PyCode test_no_split_long_header$69;
   static final PyCode test_splitting_multiple_long_lines$70;
   static final PyCode test_splitting_first_line_only_is_long$71;
   static final PyCode test_long_8bit_header$72;
   static final PyCode test_long_8bit_header_no_charset$73;
   static final PyCode test_long_to_header$74;
   static final PyCode test_long_line_after_append$75;
   static final PyCode test_shorter_line_with_append$76;
   static final PyCode test_long_field_name$77;
   static final PyCode test_long_received_header$78;
   static final PyCode test_string_headerinst_eq$79;
   static final PyCode test_long_unbreakable_lines_with_continuation$80;
   static final PyCode test_another_long_multiline_header$81;
   static final PyCode test_long_lines_with_different_header$82;
   static final PyCode TestFromMangling$83;
   static final PyCode setUp$84;
   static final PyCode test_mangled_from$85;
   static final PyCode test_dont_mangle_from$86;
   static final PyCode TestMIMEAudio$87;
   static final PyCode setUp$88;
   static final PyCode test_guess_minor_type$89;
   static final PyCode test_encoding$90;
   static final PyCode test_checkSetMinor$91;
   static final PyCode test_add_header$92;
   static final PyCode TestMIMEImage$93;
   static final PyCode setUp$94;
   static final PyCode test_guess_minor_type$95;
   static final PyCode test_encoding$96;
   static final PyCode test_checkSetMinor$97;
   static final PyCode test_add_header$98;
   static final PyCode TestMIMEApplication$99;
   static final PyCode test_headers$100;
   static final PyCode test_body$101;
   static final PyCode test_binary_body_with_encode_7or8bit$102;
   static final PyCode test_binary_body_with_encode_noop$103;
   static final PyCode TestMIMEText$104;
   static final PyCode setUp$105;
   static final PyCode test_types$106;
   static final PyCode test_payload$107;
   static final PyCode test_charset$108;
   static final PyCode TestMultipart$109;
   static final PyCode setUp$110;
   static final PyCode test_hierarchy$111;
   static final PyCode test_empty_multipart_idempotent$112;
   static final PyCode test_no_parts_in_a_multipart_with_none_epilogue$113;
   static final PyCode test_no_parts_in_a_multipart_with_empty_epilogue$114;
   static final PyCode test_one_part_in_a_multipart$115;
   static final PyCode test_seq_parts_in_a_multipart_with_empty_preamble$116;
   static final PyCode test_seq_parts_in_a_multipart_with_none_preamble$117;
   static final PyCode test_seq_parts_in_a_multipart_with_none_epilogue$118;
   static final PyCode test_seq_parts_in_a_multipart_with_empty_epilogue$119;
   static final PyCode test_seq_parts_in_a_multipart_with_nl_epilogue$120;
   static final PyCode test_message_external_body$121;
   static final PyCode test_double_boundary$122;
   static final PyCode test_nested_inner_contains_outer_boundary$123;
   static final PyCode test_nested_with_same_boundary$124;
   static final PyCode test_boundary_in_non_multipart$125;
   static final PyCode test_boundary_with_leading_space$126;
   static final PyCode test_boundary_without_trailing_newline$127;
   static final PyCode TestNonConformant$128;
   static final PyCode test_parse_missing_minor_type$129;
   static final PyCode test_same_boundary_inner_outer$130;
   static final PyCode test_multipart_no_boundary$131;
   static final PyCode test_invalid_content_type$132;
   static final PyCode test_no_start_boundary$133;
   static final PyCode test_no_separating_blank_line$134;
   static final PyCode test_lying_multipart$135;
   static final PyCode test_missing_start_boundary$136;
   static final PyCode test_first_line_is_continuation_header$137;
   static final PyCode TestRFC2047$138;
   static final PyCode test_rfc2047_multiline$139;
   static final PyCode test_whitespace_eater_unicode$140;
   static final PyCode test_whitespace_eater_unicode_2$141;
   static final PyCode test_rfc2047_missing_whitespace$142;
   static final PyCode test_rfc2047_with_whitespace$143;
   static final PyCode TestMIMEMessage$144;
   static final PyCode setUp$145;
   static final PyCode test_type_error$146;
   static final PyCode test_valid_argument$147;
   static final PyCode test_bad_multipart$148;
   static final PyCode test_generate$149;
   static final PyCode test_parse_message_rfc822$150;
   static final PyCode test_dsn$151;
   static final PyCode test_epilogue$152;
   static final PyCode test_no_nl_preamble$153;
   static final PyCode test_default_type$154;
   static final PyCode test_default_type_with_explicit_container_type$155;
   static final PyCode test_default_type_non_parsed$156;
   static final PyCode test_mime_attachments_in_constructor$157;
   static final PyCode TestIdempotent$158;
   static final PyCode _msgobj$159;
   static final PyCode _idempotent$160;
   static final PyCode test_parse_text_message$161;
   static final PyCode test_parse_untyped_message$162;
   static final PyCode test_simple_multipart$163;
   static final PyCode test_MIME_digest$164;
   static final PyCode test_long_header$165;
   static final PyCode test_MIME_digest_with_part_headers$166;
   static final PyCode test_mixed_with_image$167;
   static final PyCode test_multipart_report$168;
   static final PyCode test_dsn$169;
   static final PyCode test_preamble_epilogue$170;
   static final PyCode test_multipart_one_part$171;
   static final PyCode test_multipart_no_parts$172;
   static final PyCode test_no_start_boundary$173;
   static final PyCode test_rfc2231_charset$174;
   static final PyCode test_more_rfc2231_parameters$175;
   static final PyCode test_text_plain_in_a_multipart_digest$176;
   static final PyCode test_nested_multipart_mixeds$177;
   static final PyCode test_message_external_body_idempotent$178;
   static final PyCode test_content_type$179;
   static final PyCode test_parser$180;
   static final PyCode TestMiscellaneous$181;
   static final PyCode test_message_from_string$182;
   static final PyCode test_message_from_file$183;
   static final PyCode test_message_from_string_with_class$184;
   static final PyCode MyMessage$185;
   static final PyCode test_message_from_file_with_class$186;
   static final PyCode MyMessage$187;
   static final PyCode test__all__$188;
   static final PyCode test_formatdate$189;
   static final PyCode test_formatdate_localtime$190;
   static final PyCode test_formatdate_usegmt$191;
   static final PyCode test_parsedate_none$192;
   static final PyCode test_parsedate_compact$193;
   static final PyCode test_parsedate_no_dayofweek$194;
   static final PyCode test_parsedate_compact_no_dayofweek$195;
   static final PyCode test_parsedate_acceptable_to_time_functions$196;
   static final PyCode test_parseaddr_empty$197;
   static final PyCode test_noquote_dump$198;
   static final PyCode test_escape_dump$199;
   static final PyCode test_escape_backslashes$200;
   static final PyCode test_name_with_dot$201;
   static final PyCode test_multiline_from_comment$202;
   static final PyCode test_quote_dump$203;
   static final PyCode test_fix_eols$204;
   static final PyCode test_charset_richcomparisons$205;
   static final PyCode test_getaddresses$206;
   static final PyCode test_getaddresses_nasty$207;
   static final PyCode test_getaddresses_embedded_comment$208;
   static final PyCode test_utils_quote_unquote$209;
   static final PyCode test_get_body_encoding_with_bogus_charset$210;
   static final PyCode test_get_body_encoding_with_uppercase_charset$211;
   static final PyCode test_charsets_case_insensitive$212;
   static final PyCode test_partial_falls_inside_message_delivery_status$213;
   static final PyCode TestIterators$214;
   static final PyCode test_body_line_iterator$215;
   static final PyCode test_typed_subpart_iterator$216;
   static final PyCode test_typed_subpart_iterator_default_type$217;
   static final PyCode TestParsers$218;
   static final PyCode test_header_parser$219;
   static final PyCode test_whitespace_continuation$220;
   static final PyCode test_whitespace_continuation_last_header$221;
   static final PyCode test_crlf_separation$222;
   static final PyCode test_multipart_digest_with_extra_mime_headers$223;
   static final PyCode test_three_lines$224;
   static final PyCode test_strip_line_feed_and_carriage_return_in_headers$225;
   static final PyCode test_rfc2822_header_syntax$226;
   static final PyCode test_rfc2822_space_not_allowed_in_header$227;
   static final PyCode test_rfc2822_one_character_header$228;
   static final PyCode TestBase64$229;
   static final PyCode test_len$230;
   static final PyCode test_decode$231;
   static final PyCode test_encode$232;
   static final PyCode test_header_encode$233;
   static final PyCode TestQuopri$234;
   static final PyCode setUp$235;
   static final PyCode test_header_quopri_check$236;
   static final PyCode test_body_quopri_check$237;
   static final PyCode test_header_quopri_len$238;
   static final PyCode test_body_quopri_len$239;
   static final PyCode test_quote_unquote_idempotent$240;
   static final PyCode test_header_encode$241;
   static final PyCode test_decode$242;
   static final PyCode test_encode$243;
   static final PyCode TestCharset$244;
   static final PyCode tearDown$245;
   static final PyCode test_idempotent$246;
   static final PyCode test_body_encode$247;
   static final PyCode test_unicode_charset_name$248;
   static final PyCode TestHeader$249;
   static final PyCode test_simple$250;
   static final PyCode test_simple_surprise$251;
   static final PyCode test_header_needs_no_decoding$252;
   static final PyCode test_long$253;
   static final PyCode test_multilingual$254;
   static final PyCode test_header_ctor_default_args$255;
   static final PyCode test_explicit_maxlinelen$256;
   static final PyCode test_us_ascii_header$257;
   static final PyCode test_string_charset$258;
   static final PyCode test_utf8_shortest$259;
   static final PyCode test_bad_8bit_header$260;
   static final PyCode test_encoded_adjacent_nonencoded$261;
   static final PyCode test_whitespace_eater$262;
   static final PyCode test_broken_base64_header$263;
   static final PyCode TestRFC2231$264;
   static final PyCode test_get_param$265;
   static final PyCode test_set_param$266;
   static final PyCode test_del_param$267;
   static final PyCode test_rfc2231_get_content_charset$268;
   static final PyCode test_rfc2231_no_language_or_charset$269;
   static final PyCode test_rfc2231_no_language_or_charset_in_filename$270;
   static final PyCode test_rfc2231_no_language_or_charset_in_filename_encoded$271;
   static final PyCode test_rfc2231_partly_encoded$272;
   static final PyCode test_rfc2231_partly_nonencoded$273;
   static final PyCode test_rfc2231_no_language_or_charset_in_boundary$274;
   static final PyCode test_rfc2231_no_language_or_charset_in_charset$275;
   static final PyCode test_rfc2231_bad_encoding_in_filename$276;
   static final PyCode test_rfc2231_bad_encoding_in_charset$277;
   static final PyCode test_rfc2231_bad_character_in_charset$278;
   static final PyCode test_rfc2231_bad_character_in_filename$279;
   static final PyCode test_rfc2231_unknown_encoding$280;
   static final PyCode test_rfc2231_single_tick_in_filename_extended$281;
   static final PyCode test_rfc2231_single_tick_in_filename$282;
   static final PyCode test_rfc2231_tick_attack_extended$283;
   static final PyCode test_rfc2231_tick_attack$284;
   static final PyCode test_rfc2231_no_extended_values$285;
   static final PyCode test_rfc2231_encoded_then_unencoded_segments$286;
   static final PyCode test_rfc2231_unencoded_then_encoded_segments$287;
   static final PyCode _testclasses$288;
   static final PyCode suite$289;
   static final PyCode test_main$290;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(5);
      PyObject var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(6);
      var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(7);
      var3 = imp.importOne("time", var1, -1);
      var1.setlocal("time", var3);
      var3 = null;
      var1.setline(8);
      var3 = imp.importOne("base64", var1, -1);
      var1.setlocal("base64", var3);
      var3 = null;
      var1.setline(9);
      var3 = imp.importOne("difflib", var1, -1);
      var1.setlocal("difflib", var3);
      var3 = null;
      var1.setline(10);
      var3 = imp.importOne("unittest", var1, -1);
      var1.setlocal("unittest", var3);
      var3 = null;
      var1.setline(11);
      var3 = imp.importOne("warnings", var1, -1);
      var1.setlocal("warnings", var3);
      var3 = null;
      var1.setline(12);
      String[] var5 = new String[]{"StringIO"};
      PyObject[] var6 = imp.importFrom("cStringIO", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("StringIO", var4);
      var4 = null;
      var1.setline(14);
      var3 = imp.importOne("email", var1, -1);
      var1.setlocal("email", var3);
      var3 = null;
      var1.setline(16);
      var5 = new String[]{"Charset"};
      var6 = imp.importFrom("email.charset", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("Charset", var4);
      var4 = null;
      var1.setline(17);
      var5 = new String[]{"Header", "decode_header", "make_header"};
      var6 = imp.importFrom("email.header", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("Header", var4);
      var4 = null;
      var4 = var6[1];
      var1.setlocal("decode_header", var4);
      var4 = null;
      var4 = var6[2];
      var1.setlocal("make_header", var4);
      var4 = null;
      var1.setline(18);
      var5 = new String[]{"Parser", "HeaderParser"};
      var6 = imp.importFrom("email.parser", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("Parser", var4);
      var4 = null;
      var4 = var6[1];
      var1.setlocal("HeaderParser", var4);
      var4 = null;
      var1.setline(19);
      var5 = new String[]{"Generator", "DecodedGenerator"};
      var6 = imp.importFrom("email.generator", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("Generator", var4);
      var4 = null;
      var4 = var6[1];
      var1.setlocal("DecodedGenerator", var4);
      var4 = null;
      var1.setline(20);
      var5 = new String[]{"Message"};
      var6 = imp.importFrom("email.message", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("Message", var4);
      var4 = null;
      var1.setline(21);
      var5 = new String[]{"MIMEApplication"};
      var6 = imp.importFrom("email.mime.application", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("MIMEApplication", var4);
      var4 = null;
      var1.setline(22);
      var5 = new String[]{"MIMEAudio"};
      var6 = imp.importFrom("email.mime.audio", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("MIMEAudio", var4);
      var4 = null;
      var1.setline(23);
      var5 = new String[]{"MIMEText"};
      var6 = imp.importFrom("email.mime.text", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("MIMEText", var4);
      var4 = null;
      var1.setline(24);
      var5 = new String[]{"MIMEImage"};
      var6 = imp.importFrom("email.mime.image", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("MIMEImage", var4);
      var4 = null;
      var1.setline(25);
      var5 = new String[]{"MIMEBase"};
      var6 = imp.importFrom("email.mime.base", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("MIMEBase", var4);
      var4 = null;
      var1.setline(26);
      var5 = new String[]{"MIMEMessage"};
      var6 = imp.importFrom("email.mime.message", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("MIMEMessage", var4);
      var4 = null;
      var1.setline(27);
      var5 = new String[]{"MIMEMultipart"};
      var6 = imp.importFrom("email.mime.multipart", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("MIMEMultipart", var4);
      var4 = null;
      var1.setline(28);
      var5 = new String[]{"utils"};
      var6 = imp.importFrom("email", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("utils", var4);
      var4 = null;
      var1.setline(29);
      var5 = new String[]{"errors"};
      var6 = imp.importFrom("email", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("errors", var4);
      var4 = null;
      var1.setline(30);
      var5 = new String[]{"encoders"};
      var6 = imp.importFrom("email", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("encoders", var4);
      var4 = null;
      var1.setline(31);
      var5 = new String[]{"iterators"};
      var6 = imp.importFrom("email", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("iterators", var4);
      var4 = null;
      var1.setline(32);
      var5 = new String[]{"base64mime"};
      var6 = imp.importFrom("email", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("base64mime", var4);
      var4 = null;
      var1.setline(33);
      var5 = new String[]{"quoprimime"};
      var6 = imp.importFrom("email", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("quoprimime", var4);
      var4 = null;
      var1.setline(35);
      var5 = new String[]{"findfile", "run_unittest", "is_jython"};
      var6 = imp.importFrom("test.test_support", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("findfile", var4);
      var4 = null;
      var4 = var6[1];
      var1.setlocal("run_unittest", var4);
      var4 = null;
      var4 = var6[2];
      var1.setlocal("is_jython", var4);
      var4 = null;
      var1.setline(36);
      var5 = new String[]{"__file__"};
      var6 = imp.importFrom("email.test", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("landmark", var4);
      var4 = null;
      var1.setline(39);
      PyString var7 = PyString.fromInterned("\n");
      var1.setlocal("NL", var7);
      var3 = null;
      var1.setline(40);
      var7 = PyString.fromInterned("");
      var1.setlocal("EMPTYSTRING", var7);
      var3 = null;
      var1.setline(41);
      var7 = PyString.fromInterned(" ");
      var1.setlocal("SPACE", var7);
      var3 = null;
      var1.setline(45);
      var6 = new PyObject[]{PyString.fromInterned("r")};
      PyFunction var8 = new PyFunction(var1.f_globals, var6, openfile$1, (PyObject)null);
      var1.setlocal("openfile", var8);
      var3 = null;
      var1.setline(52);
      var6 = new PyObject[]{var1.getname("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("TestEmailBase", var6, TestEmailBase$2);
      var1.setlocal("TestEmailBase", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(74);
      var6 = new PyObject[]{var1.getname("TestEmailBase")};
      var4 = Py.makeClass("TestMessageAPI", var6, TestMessageAPI$5);
      var1.setlocal("TestMessageAPI", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(509);
      var6 = new PyObject[]{var1.getname("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("TestEncoders", var6, TestEncoders$57);
      var1.setlocal("TestEncoders", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(536);
      var6 = new PyObject[]{var1.getname("TestEmailBase")};
      var4 = Py.makeClass("TestLongHeaders", var6, TestLongHeaders$61);
      var1.setlocal("TestLongHeaders", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(857);
      var6 = new PyObject[]{var1.getname("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("TestFromMangling", var6, TestFromMangling$83);
      var1.setlocal("TestFromMangling", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(891);
      var6 = new PyObject[]{var1.getname("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("TestMIMEAudio", var6, TestMIMEAudio$87);
      var1.setlocal("TestMIMEAudio", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(940);
      var6 = new PyObject[]{var1.getname("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("TestMIMEImage", var6, TestMIMEImage$93);
      var1.setlocal("TestMIMEImage", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(983);
      var6 = new PyObject[]{var1.getname("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("TestMIMEApplication", var6, TestMIMEApplication$99);
      var1.setlocal("TestMIMEApplication", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(1032);
      var6 = new PyObject[]{var1.getname("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("TestMIMEText", var6, TestMIMEText$104);
      var1.setlocal("TestMIMEText", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(1059);
      var6 = new PyObject[]{var1.getname("TestEmailBase")};
      var4 = Py.makeClass("TestMultipart", var6, TestMultipart$109);
      var1.setlocal("TestMultipart", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(1438);
      var6 = new PyObject[]{var1.getname("TestEmailBase")};
      var4 = Py.makeClass("TestNonConformant", var6, TestNonConformant$128);
      var1.setlocal("TestNonConformant", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(1552);
      var6 = new PyObject[]{var1.getname("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("TestRFC2047", var6, TestRFC2047$138);
      var1.setlocal("TestRFC2047", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(1599);
      var6 = new PyObject[]{var1.getname("TestEmailBase")};
      var4 = Py.makeClass("TestMIMEMessage", var6, TestMIMEMessage$144);
      var1.setlocal("TestMIMEMessage", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(1913);
      var6 = new PyObject[]{var1.getname("TestEmailBase")};
      var4 = Py.makeClass("TestIdempotent", var6, TestIdempotent$158);
      var1.setlocal("TestIdempotent", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(2066);
      var6 = new PyObject[]{var1.getname("TestEmailBase")};
      var4 = Py.makeClass("TestMiscellaneous", var6, TestMiscellaneous$181);
      var1.setlocal("TestMiscellaneous", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(2391);
      var6 = new PyObject[]{var1.getname("TestEmailBase")};
      var4 = Py.makeClass("TestIterators", var6, TestIterators$214);
      var1.setlocal("TestIterators", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(2450);
      var6 = new PyObject[]{var1.getname("TestEmailBase")};
      var4 = Py.makeClass("TestParsers", var6, TestParsers$218);
      var1.setlocal("TestParsers", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(2595);
      var6 = new PyObject[]{var1.getname("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("TestBase64", var6, TestBase64$229);
      var1.setlocal("TestBase64", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(2667);
      var6 = new PyObject[]{var1.getname("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("TestQuopri", var6, TestQuopri$234);
      var1.setlocal("TestQuopri", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(2778);
      var6 = new PyObject[]{var1.getname("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("TestCharset", var6, TestCharset$244);
      var1.setlocal("TestCharset", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(2839);
      var6 = new PyObject[]{var1.getname("TestEmailBase")};
      var4 = Py.makeClass("TestHeader", var6, TestHeader$249);
      var1.setlocal("TestHeader", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(3002);
      var6 = new PyObject[]{var1.getname("TestEmailBase")};
      var4 = Py.makeClass("TestRFC2231", var6, TestRFC2231$264);
      var1.setlocal("TestRFC2231", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(3313);
      var6 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var6, _testclasses$288, (PyObject)null);
      var1.setlocal("_testclasses", var8);
      var3 = null;
      var1.setline(3318);
      var6 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var6, suite$289, (PyObject)null);
      var1.setlocal("suite", var8);
      var3 = null;
      var1.setline(3325);
      var6 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var6, test_main$290, (PyObject)null);
      var1.setlocal("test_main", var8);
      var3 = null;
      var1.setline(3331);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(3332);
         var10000 = var1.getname("unittest").__getattr__("main");
         var6 = new PyObject[]{PyString.fromInterned("suite")};
         String[] var9 = new String[]{"defaultTest"};
         var10000.__call__(var2, var6, var9);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject openfile$1(PyFrame var1, ThreadState var2) {
      var1.setline(46);
      PyObject var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, var1.getglobal("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getglobal("landmark")), (PyObject)PyString.fromInterned("data"), (PyObject)var1.getlocal(0));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(47);
      var3 = var1.getglobal("open").__call__(var2, var1.getlocal(2), var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject TestEmailBase$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(53);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, ndiffAssertEqual$3, PyString.fromInterned("Like assertEqual except use ndiff for readable output."));
      var1.setlocal("ndiffAssertEqual", var4);
      var3 = null;
      var1.setline(63);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _msgobj$4, (PyObject)null);
      var1.setlocal("_msgobj", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject ndiffAssertEqual$3(PyFrame var1, ThreadState var2) {
      var1.setline(54);
      PyString.fromInterned("Like assertEqual except use ndiff for readable output.");
      var1.setline(55);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._ne(var1.getlocal(2));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(56);
         var3 = var1.getglobal("str").__call__(var2, var1.getlocal(1));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(57);
         var3 = var1.getglobal("str").__call__(var2, var1.getlocal(2));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(58);
         var3 = var1.getglobal("difflib").__getattr__("ndiff").__call__(var2, var1.getlocal(3).__getattr__("splitlines").__call__(var2), var1.getlocal(4).__getattr__("splitlines").__call__(var2));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(59);
         var3 = var1.getglobal("StringIO").__call__(var2);
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(60);
         var3 = var1.getlocal(6);
         Py.printComma(var3, var1.getglobal("NL"));
         Py.println(var3, var1.getglobal("NL").__getattr__("join").__call__(var2, var1.getlocal(5)));
         var1.setline(61);
         throw Py.makeException(var1.getlocal(0).__getattr__("failureException"), var1.getlocal(6).__getattr__("getvalue").__call__(var2));
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject _msgobj$4(PyFrame var1, ThreadState var2) {
      var1.setline(64);
      PyObject var3 = var1.getglobal("openfile").__call__(var2, var1.getglobal("findfile").__call__(var2, var1.getlocal(1)));
      var1.setlocal(2, var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(66);
         PyObject var4 = var1.getglobal("email").__getattr__("message_from_file").__call__(var2, var1.getlocal(2));
         var1.setlocal(3, var4);
         var4 = null;
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(68);
         var1.getlocal(2).__getattr__("close").__call__(var2);
         throw (Throwable)var5;
      }

      var1.setline(68);
      var1.getlocal(2).__getattr__("close").__call__(var2);
      var1.setline(69);
      var3 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject TestMessageAPI$5(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(75);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_get_all$6, (PyObject)null);
      var1.setlocal("test_get_all", var4);
      var3 = null;
      var1.setline(81);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_getset_charset$7, (PyObject)null);
      var1.setlocal("test_getset_charset", var4);
      var3 = null;
      var1.setline(107);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_set_charset_from_string$8, (PyObject)null);
      var1.setlocal("test_set_charset_from_string", var4);
      var3 = null;
      var1.setline(114);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_set_payload_with_charset$9, (PyObject)null);
      var1.setlocal("test_set_payload_with_charset", var4);
      var3 = null;
      var1.setline(120);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_get_charsets$10, (PyObject)null);
      var1.setlocal("test_get_charsets", var4);
      var3 = null;
      var1.setline(137);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_get_filename$11, (PyObject)null);
      var1.setlocal("test_get_filename", var4);
      var3 = null;
      var1.setline(148);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_get_filename_with_name_parameter$12, (PyObject)null);
      var1.setlocal("test_get_filename_with_name_parameter", var4);
      var3 = null;
      var1.setline(155);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_get_boundary$13, (PyObject)null);
      var1.setlocal("test_get_boundary", var4);
      var3 = null;
      var1.setline(161);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_set_boundary$14, (PyObject)null);
      var1.setlocal("test_set_boundary", var4);
      var3 = null;
      var1.setline(183);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_get_decoded_payload$15, (PyObject)null);
      var1.setlocal("test_get_decoded_payload", var4);
      var3 = null;
      var1.setline(205);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_get_decoded_uu_payload$16, (PyObject)null);
      var1.setlocal("test_get_decoded_uu_payload", var4);
      var3 = null;
      var1.setline(216);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_decoded_generator$17, (PyObject)null);
      var1.setlocal("test_decoded_generator", var4);
      var3 = null;
      var1.setline(229);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test__contains__$18, (PyObject)null);
      var1.setlocal("test__contains__", var4);
      var3 = null;
      var1.setline(241);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_as_string$19, (PyObject)null);
      var1.setlocal("test_as_string", var4);
      var3 = null;
      var1.setline(264);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_bad_param$20, (PyObject)null);
      var1.setlocal("test_bad_param", var4);
      var3 = null;
      var1.setline(268);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_missing_filename$21, (PyObject)null);
      var1.setlocal("test_missing_filename", var4);
      var3 = null;
      var1.setline(272);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_bogus_filename$22, (PyObject)null);
      var1.setlocal("test_bogus_filename", var4);
      var3 = null;
      var1.setline(277);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_missing_boundary$23, (PyObject)null);
      var1.setlocal("test_missing_boundary", var4);
      var3 = null;
      var1.setline(281);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_get_params$24, (PyObject)null);
      var1.setlocal("test_get_params", var4);
      var3 = null;
      var1.setline(297);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_get_param_liberal$25, (PyObject)null);
      var1.setlocal("test_get_param_liberal", var4);
      var3 = null;
      var1.setline(302);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_get_param$26, (PyObject)null);
      var1.setlocal("test_get_param", var4);
      var3 = null;
      var1.setline(319);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_get_param_funky_continuation_lines$27, (PyObject)null);
      var1.setlocal("test_get_param_funky_continuation_lines", var4);
      var3 = null;
      var1.setline(323);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_get_param_with_semis_in_quotes$28, (PyObject)null);
      var1.setlocal("test_get_param_with_semis_in_quotes", var4);
      var3 = null;
      var1.setline(330);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_has_key$29, (PyObject)null);
      var1.setlocal("test_has_key", var4);
      var3 = null;
      var1.setline(337);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_set_param$30, (PyObject)null);
      var1.setlocal("test_set_param", var4);
      var3 = null;
      var1.setline(354);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_del_param$31, (PyObject)null);
      var1.setlocal("test_del_param", var4);
      var3 = null;
      var1.setline(371);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_del_param_on_other_header$32, (PyObject)null);
      var1.setlocal("test_del_param_on_other_header", var4);
      var3 = null;
      var1.setline(377);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_set_type$33, (PyObject)null);
      var1.setlocal("test_set_type", var4);
      var3 = null;
      var1.setline(388);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_set_type_on_other_header$34, (PyObject)null);
      var1.setlocal("test_set_type_on_other_header", var4);
      var3 = null;
      var1.setline(394);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_get_content_type_missing$35, (PyObject)null);
      var1.setlocal("test_get_content_type_missing", var4);
      var3 = null;
      var1.setline(398);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_get_content_type_missing_with_default_type$36, (PyObject)null);
      var1.setlocal("test_get_content_type_missing_with_default_type", var4);
      var3 = null;
      var1.setline(403);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_get_content_type_from_message_implicit$37, (PyObject)null);
      var1.setlocal("test_get_content_type_from_message_implicit", var4);
      var3 = null;
      var1.setline(408);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_get_content_type_from_message_explicit$38, (PyObject)null);
      var1.setlocal("test_get_content_type_from_message_explicit", var4);
      var3 = null;
      var1.setline(413);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_get_content_type_from_message_text_plain_implicit$39, (PyObject)null);
      var1.setlocal("test_get_content_type_from_message_text_plain_implicit", var4);
      var3 = null;
      var1.setline(417);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_get_content_type_from_message_text_plain_explicit$40, (PyObject)null);
      var1.setlocal("test_get_content_type_from_message_text_plain_explicit", var4);
      var3 = null;
      var1.setline(421);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_get_content_maintype_missing$41, (PyObject)null);
      var1.setlocal("test_get_content_maintype_missing", var4);
      var3 = null;
      var1.setline(425);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_get_content_maintype_missing_with_default_type$42, (PyObject)null);
      var1.setlocal("test_get_content_maintype_missing_with_default_type", var4);
      var3 = null;
      var1.setline(430);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_get_content_maintype_from_message_implicit$43, (PyObject)null);
      var1.setlocal("test_get_content_maintype_from_message_implicit", var4);
      var3 = null;
      var1.setline(434);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_get_content_maintype_from_message_explicit$44, (PyObject)null);
      var1.setlocal("test_get_content_maintype_from_message_explicit", var4);
      var3 = null;
      var1.setline(438);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_get_content_maintype_from_message_text_plain_implicit$45, (PyObject)null);
      var1.setlocal("test_get_content_maintype_from_message_text_plain_implicit", var4);
      var3 = null;
      var1.setline(442);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_get_content_maintype_from_message_text_plain_explicit$46, (PyObject)null);
      var1.setlocal("test_get_content_maintype_from_message_text_plain_explicit", var4);
      var3 = null;
      var1.setline(446);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_get_content_subtype_missing$47, (PyObject)null);
      var1.setlocal("test_get_content_subtype_missing", var4);
      var3 = null;
      var1.setline(450);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_get_content_subtype_missing_with_default_type$48, (PyObject)null);
      var1.setlocal("test_get_content_subtype_missing_with_default_type", var4);
      var3 = null;
      var1.setline(455);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_get_content_subtype_from_message_implicit$49, (PyObject)null);
      var1.setlocal("test_get_content_subtype_from_message_implicit", var4);
      var3 = null;
      var1.setline(459);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_get_content_subtype_from_message_explicit$50, (PyObject)null);
      var1.setlocal("test_get_content_subtype_from_message_explicit", var4);
      var3 = null;
      var1.setline(463);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_get_content_subtype_from_message_text_plain_implicit$51, (PyObject)null);
      var1.setlocal("test_get_content_subtype_from_message_text_plain_implicit", var4);
      var3 = null;
      var1.setline(467);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_get_content_subtype_from_message_text_plain_explicit$52, (PyObject)null);
      var1.setlocal("test_get_content_subtype_from_message_text_plain_explicit", var4);
      var3 = null;
      var1.setline(471);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_get_content_maintype_error$53, (PyObject)null);
      var1.setlocal("test_get_content_maintype_error", var4);
      var3 = null;
      var1.setline(476);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_get_content_subtype_error$54, (PyObject)null);
      var1.setlocal("test_get_content_subtype_error", var4);
      var3 = null;
      var1.setline(481);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_replace_header$55, (PyObject)null);
      var1.setlocal("test_replace_header", var4);
      var3 = null;
      var1.setline(498);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_broken_base64_payload$56, (PyObject)null);
      var1.setlocal("test_broken_base64_payload", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_get_all$6(PyFrame var1, ThreadState var2) {
      var1.setline(76);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(77);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_20.txt"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(78);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_all").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("cc")), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("ccc@zzz.org"), PyString.fromInterned("ddd@zzz.org"), PyString.fromInterned("eee@zzz.org")})));
      var1.setline(79);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_all").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("xx"), (PyObject)PyString.fromInterned("n/a")), (PyObject)PyString.fromInterned("n/a"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_getset_charset$7(PyFrame var1, ThreadState var2) {
      var1.setline(82);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(83);
      var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(84);
      var1.getlocal(1).__call__(var2, var1.getlocal(2).__getattr__("get_charset").__call__(var2), var1.getglobal("None"));
      var1.setline(85);
      var3 = var1.getglobal("Charset").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("iso-8859-1"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(86);
      var1.getlocal(2).__getattr__("set_charset").__call__(var2, var1.getlocal(3));
      var1.setline(87);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getitem__(PyString.fromInterned("mime-version")), (PyObject)PyString.fromInterned("1.0"));
      var1.setline(88);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("text/plain"));
      var1.setline(89);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getitem__(PyString.fromInterned("content-type")), (PyObject)PyString.fromInterned("text/plain; charset=\"iso-8859-1\""));
      var1.setline(90);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_param").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("charset")), (PyObject)PyString.fromInterned("iso-8859-1"));
      var1.setline(91);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getitem__(PyString.fromInterned("content-transfer-encoding")), (PyObject)PyString.fromInterned("quoted-printable"));
      var1.setline(92);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_charset").__call__(var2).__getattr__("input_charset"), (PyObject)PyString.fromInterned("iso-8859-1"));
      var1.setline(94);
      var1.getlocal(2).__getattr__("set_charset").__call__(var2, var1.getglobal("None"));
      var1.setline(95);
      var1.getlocal(1).__call__(var2, var1.getlocal(2).__getattr__("get_charset").__call__(var2), var1.getglobal("None"));
      var1.setline(96);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getitem__(PyString.fromInterned("content-type")), (PyObject)PyString.fromInterned("text/plain"));
      var1.setline(98);
      var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(99);
      PyString var4 = PyString.fromInterned("2.0");
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("MIME-Version"), var4);
      var3 = null;
      var1.setline(100);
      var4 = PyString.fromInterned("text/x-weird");
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("Content-Type"), var4);
      var3 = null;
      var1.setline(101);
      var4 = PyString.fromInterned("quinted-puntable");
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("Content-Transfer-Encoding"), var4);
      var3 = null;
      var1.setline(102);
      var1.getlocal(2).__getattr__("set_charset").__call__(var2, var1.getlocal(3));
      var1.setline(103);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getitem__(PyString.fromInterned("mime-version")), (PyObject)PyString.fromInterned("2.0"));
      var1.setline(104);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getitem__(PyString.fromInterned("content-type")), (PyObject)PyString.fromInterned("text/x-weird; charset=\"iso-8859-1\""));
      var1.setline(105);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getitem__(PyString.fromInterned("content-transfer-encoding")), (PyObject)PyString.fromInterned("quinted-puntable"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_set_charset_from_string$8(PyFrame var1, ThreadState var2) {
      var1.setline(108);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(109);
      var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(110);
      var1.getlocal(2).__getattr__("set_charset").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("us-ascii"));
      var1.setline(111);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_charset").__call__(var2).__getattr__("input_charset"), (PyObject)PyString.fromInterned("us-ascii"));
      var1.setline(112);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getitem__(PyString.fromInterned("content-type")), (PyObject)PyString.fromInterned("text/plain; charset=\"us-ascii\""));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_set_payload_with_charset$9(PyFrame var1, ThreadState var2) {
      var1.setline(115);
      PyObject var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(116);
      var3 = var1.getglobal("Charset").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("iso-8859-1"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(117);
      var1.getlocal(1).__getattr__("set_payload").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("This is a string payload"), (PyObject)var1.getlocal(2));
      var1.setline(118);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("get_charset").__call__(var2).__getattr__("input_charset"), (PyObject)PyString.fromInterned("iso-8859-1"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_get_charsets$10(PyFrame var1, ThreadState var2) {
      var1.setline(121);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(123);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_08.txt"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(124);
      var3 = var1.getlocal(2).__getattr__("get_charsets").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(125);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)(new PyList(new PyObject[]{var1.getglobal("None"), PyString.fromInterned("us-ascii"), PyString.fromInterned("iso-8859-1"), PyString.fromInterned("iso-8859-2"), PyString.fromInterned("koi8-r")})));
      var1.setline(127);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_09.txt"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(128);
      var3 = var1.getlocal(2).__getattr__("get_charsets").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("dingbat"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(129);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("dingbat"), PyString.fromInterned("us-ascii"), PyString.fromInterned("iso-8859-1"), PyString.fromInterned("dingbat"), PyString.fromInterned("koi8-r")})));
      var1.setline(132);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_12.txt"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(133);
      var3 = var1.getlocal(2).__getattr__("get_charsets").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(134);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)(new PyList(new PyObject[]{var1.getglobal("None"), PyString.fromInterned("us-ascii"), PyString.fromInterned("iso-8859-1"), var1.getglobal("None"), PyString.fromInterned("iso-8859-2"), PyString.fromInterned("iso-8859-3"), PyString.fromInterned("us-ascii"), PyString.fromInterned("koi8-r")})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_get_filename$11(PyFrame var1, ThreadState var2) {
      var1.setline(138);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(140);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_04.txt"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(141);
      PyList var10000 = new PyList();
      var3 = var10000.__getattr__("append");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(141);
      var3 = var1.getlocal(2).__getattr__("get_payload").__call__(var2).__iter__();

      while(true) {
         var1.setline(141);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(141);
            var1.dellocal(4);
            PyList var5 = var10000;
            var1.setlocal(3, var5);
            var3 = null;
            var1.setline(142);
            var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("msg.txt"), PyString.fromInterned("msg.txt")})));
            var1.setline(144);
            var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_07.txt"));
            var1.setlocal(2, var3);
            var3 = null;
            var1.setline(145);
            var3 = var1.getlocal(2).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
            var1.setlocal(6, var3);
            var3 = null;
            var1.setline(146);
            var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(6).__getattr__("get_filename").__call__(var2), (PyObject)PyString.fromInterned("dingusfish.gif"));
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(5, var4);
         var1.setline(141);
         var1.getlocal(4).__call__(var2, var1.getlocal(5).__getattr__("get_filename").__call__(var2));
      }
   }

   public PyObject test_get_filename_with_name_parameter$12(PyFrame var1, ThreadState var2) {
      var1.setline(149);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(151);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_44.txt"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(152);
      PyList var10000 = new PyList();
      var3 = var10000.__getattr__("append");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(152);
      var3 = var1.getlocal(2).__getattr__("get_payload").__call__(var2).__iter__();

      while(true) {
         var1.setline(152);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(152);
            var1.dellocal(4);
            PyList var5 = var10000;
            var1.setlocal(3, var5);
            var3 = null;
            var1.setline(153);
            var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("msg.txt"), PyString.fromInterned("msg.txt")})));
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(5, var4);
         var1.setline(152);
         var1.getlocal(4).__call__(var2, var1.getlocal(5).__getattr__("get_filename").__call__(var2));
      }
   }

   public PyObject test_get_boundary$13(PyFrame var1, ThreadState var2) {
      var1.setline(156);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(157);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_07.txt"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(159);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_boundary").__call__(var2), (PyObject)PyString.fromInterned("BOUNDARY"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_set_boundary$14(PyFrame var1, ThreadState var2) {
      var1.setline(162);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(165);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_01.txt"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(166);
      var1.getlocal(2).__getattr__("set_boundary").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("BOUNDARY"));
      var1.setline(167);
      var3 = var1.getlocal(2).__getattr__("items").__call__(var2).__getitem__(Py.newInteger(4));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;
      var1.setline(168);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("lower").__call__(var2), (PyObject)PyString.fromInterned("content-type"));
      var1.setline(169);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)PyString.fromInterned("text/plain; charset=\"us-ascii\"; boundary=\"BOUNDARY\""));
      var1.setline(173);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_04.txt"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(174);
      var1.getlocal(2).__getattr__("set_boundary").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("BOUNDARY"));
      var1.setline(175);
      var3 = var1.getlocal(2).__getattr__("items").__call__(var2).__getitem__(Py.newInteger(4));
      var4 = Py.unpackSequence(var3, 2);
      var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;
      var1.setline(176);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("lower").__call__(var2), (PyObject)PyString.fromInterned("content-type"));
      var1.setline(177);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)PyString.fromInterned("multipart/mixed; boundary=\"BOUNDARY\""));
      var1.setline(179);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_03.txt"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(180);
      var1.getlocal(0).__getattr__("assertRaises").__call__((ThreadState)var2, var1.getglobal("errors").__getattr__("HeaderParseError"), (PyObject)var1.getlocal(2).__getattr__("set_boundary"), (PyObject)PyString.fromInterned("BOUNDARY"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_get_decoded_payload$15(PyFrame var1, ThreadState var2) {
      var1.setline(184);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(185);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_10.txt"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(187);
      PyObject var10000 = var1.getlocal(1);
      PyObject var10002 = var1.getlocal(2).__getattr__("get_payload");
      PyObject[] var5 = new PyObject[]{var1.getglobal("True")};
      String[] var4 = new String[]{"decode"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__(var2, var10002, var1.getglobal("None"));
      var1.setline(189);
      var10000 = var1.getlocal(1);
      var10002 = var1.getlocal(2).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(0)).__getattr__("get_payload");
      var5 = new PyObject[]{var1.getglobal("True")};
      var4 = new String[]{"decode"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("This is a 7bit encoded message.\n"));
      var1.setline(192);
      var10000 = var1.getlocal(1);
      var10002 = var1.getlocal(2).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(1)).__getattr__("get_payload");
      var5 = new PyObject[]{var1.getglobal("True")};
      var4 = new String[]{"decode"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("¡This is a Quoted Printable encoded message!\n"));
      var1.setline(195);
      var10000 = var1.getlocal(1);
      var10002 = var1.getlocal(2).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(2)).__getattr__("get_payload");
      var5 = new PyObject[]{var1.getglobal("True")};
      var4 = new String[]{"decode"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("This is a Base64 encoded message."));
      var1.setline(199);
      var10000 = var1.getlocal(1);
      var10002 = var1.getlocal(2).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(3)).__getattr__("get_payload");
      var5 = new PyObject[]{var1.getglobal("True")};
      var4 = new String[]{"decode"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("This is a Base64 encoded message.\n"));
      var1.setline(202);
      var10000 = var1.getlocal(1);
      var10002 = var1.getlocal(2).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(4)).__getattr__("get_payload");
      var5 = new PyObject[]{var1.getglobal("True")};
      var4 = new String[]{"decode"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("This has no Content-Transfer-Encoding: header.\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_get_decoded_uu_payload$16(PyFrame var1, ThreadState var2) {
      var1.setline(206);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(207);
      var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(208);
      var1.getlocal(2).__getattr__("set_payload").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("begin 666 -\n+:&5L;&\\@=V]R;&0 \n \nend\n"));
      var1.setline(209);
      var3 = (new PyTuple(new PyObject[]{PyString.fromInterned("x-uuencode"), PyString.fromInterned("uuencode"), PyString.fromInterned("uue"), PyString.fromInterned("x-uue")})).__iter__();

      while(true) {
         var1.setline(209);
         PyObject var4 = var3.__iternext__();
         PyObject var10000;
         PyObject var10002;
         if (var4 == null) {
            var1.setline(213);
            var1.getlocal(2).__getattr__("set_payload").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("foo"));
            var1.setline(214);
            var10000 = var1.getlocal(1);
            var10002 = var1.getlocal(2).__getattr__("get_payload");
            PyObject[] var8 = new PyObject[]{var1.getglobal("True")};
            String[] var7 = new String[]{"decode"};
            var10002 = var10002.__call__(var2, var8, var7);
            var3 = null;
            var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("foo"));
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(3, var4);
         var1.setline(210);
         PyObject var5 = var1.getlocal(3);
         var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("content-transfer-encoding"), var5);
         var5 = null;
         var1.setline(211);
         var10000 = var1.getlocal(1);
         var10002 = var1.getlocal(2).__getattr__("get_payload");
         PyObject[] var9 = new PyObject[]{var1.getglobal("True")};
         String[] var6 = new String[]{"decode"};
         var10002 = var10002.__call__(var2, var9, var6);
         var5 = null;
         var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("hello world"));
      }
   }

   public PyObject test_decoded_generator$17(PyFrame var1, ThreadState var2) {
      var1.setline(217);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(218);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_07.txt"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(219);
      var3 = var1.getglobal("openfile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_17.txt"));
      var1.setlocal(3, var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(221);
         PyObject var4 = var1.getlocal(3).__getattr__("read").__call__(var2);
         var1.setlocal(4, var4);
         var4 = null;
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(223);
         var1.getlocal(3).__getattr__("close").__call__(var2);
         throw (Throwable)var5;
      }

      var1.setline(223);
      var1.getlocal(3).__getattr__("close").__call__(var2);
      var1.setline(224);
      var3 = var1.getglobal("StringIO").__call__(var2);
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(225);
      var3 = var1.getglobal("DecodedGenerator").__call__(var2, var1.getlocal(5));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(226);
      var1.getlocal(6).__getattr__("flatten").__call__(var2, var1.getlocal(2));
      var1.setline(227);
      var1.getlocal(1).__call__(var2, var1.getlocal(5).__getattr__("getvalue").__call__(var2), var1.getlocal(4));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test__contains__$18(PyFrame var1, ThreadState var2) {
      var1.setline(230);
      PyObject var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(231);
      PyString var4 = PyString.fromInterned("Me");
      var1.getlocal(1).__setitem__((PyObject)PyString.fromInterned("From"), var4);
      var3 = null;
      var1.setline(232);
      var4 = PyString.fromInterned("You");
      var1.getlocal(1).__setitem__((PyObject)PyString.fromInterned("to"), var4);
      var3 = null;
      var1.setline(234);
      PyObject var10000 = var1.getlocal(0).__getattr__("assertTrue");
      var4 = PyString.fromInterned("from");
      PyObject var10002 = var4._in(var1.getlocal(1));
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.setline(235);
      var10000 = var1.getlocal(0).__getattr__("assertTrue");
      var4 = PyString.fromInterned("From");
      var10002 = var4._in(var1.getlocal(1));
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.setline(236);
      var10000 = var1.getlocal(0).__getattr__("assertTrue");
      var4 = PyString.fromInterned("FROM");
      var10002 = var4._in(var1.getlocal(1));
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.setline(237);
      var10000 = var1.getlocal(0).__getattr__("assertTrue");
      var4 = PyString.fromInterned("to");
      var10002 = var4._in(var1.getlocal(1));
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.setline(238);
      var10000 = var1.getlocal(0).__getattr__("assertTrue");
      var4 = PyString.fromInterned("To");
      var10002 = var4._in(var1.getlocal(1));
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.setline(239);
      var10000 = var1.getlocal(0).__getattr__("assertTrue");
      var4 = PyString.fromInterned("TO");
      var10002 = var4._in(var1.getlocal(1));
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_as_string$19(PyFrame var1, ThreadState var2) {
      var1.setline(242);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(243);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_01.txt"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(244);
      var3 = var1.getglobal("openfile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_01.txt"));
      var1.setlocal(3, var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(255);
         PyObject var4 = var1.getlocal(3).__getattr__("read").__call__(var2).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\t"), (PyObject)PyString.fromInterned(" "));
         var1.setlocal(4, var4);
         var4 = null;
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(257);
         var1.getlocal(3).__getattr__("close").__call__(var2);
         throw (Throwable)var5;
      }

      var1.setline(257);
      var1.getlocal(3).__getattr__("close").__call__(var2);
      var1.setline(258);
      var1.getlocal(0).__getattr__("ndiffAssertEqual").__call__(var2, var1.getlocal(4), var1.getlocal(2).__getattr__("as_string").__call__(var2));
      var1.setline(259);
      var3 = var1.getglobal("str").__call__(var2, var1.getlocal(2));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(260);
      var3 = var1.getlocal(5).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(261);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getlocal(6).__getitem__(Py.newInteger(0)).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("From ")));
      var1.setline(262);
      var1.getlocal(1).__call__(var2, var1.getlocal(4), var1.getglobal("NL").__getattr__("join").__call__(var2, var1.getlocal(6).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_bad_param$20(PyFrame var1, ThreadState var2) {
      var1.setline(265);
      PyObject var3 = var1.getglobal("email").__getattr__("message_from_string").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Content-Type: blarg; baz; boo\n"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(266);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("get_param").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("baz")), (PyObject)PyString.fromInterned(""));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_missing_filename$21(PyFrame var1, ThreadState var2) {
      var1.setline(269);
      PyObject var3 = var1.getglobal("email").__getattr__("message_from_string").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("From: foo\n"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(270);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(1).__getattr__("get_filename").__call__(var2), var1.getglobal("None"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_bogus_filename$22(PyFrame var1, ThreadState var2) {
      var1.setline(273);
      PyObject var3 = var1.getglobal("email").__getattr__("message_from_string").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Content-Disposition: blarg; filename\n"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(275);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("get_filename").__call__(var2), (PyObject)PyString.fromInterned(""));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_missing_boundary$23(PyFrame var1, ThreadState var2) {
      var1.setline(278);
      PyObject var3 = var1.getglobal("email").__getattr__("message_from_string").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("From: foo\n"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(279);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(1).__getattr__("get_boundary").__call__(var2), var1.getglobal("None"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_get_params$24(PyFrame var1, ThreadState var2) {
      var1.setline(282);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(283);
      var3 = var1.getglobal("email").__getattr__("message_from_string").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("X-Header: foo=one; bar=two; baz=three\n"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(285);
      PyObject var10000 = var1.getlocal(1);
      PyObject var10002 = var1.getlocal(2).__getattr__("get_params");
      PyObject[] var5 = new PyObject[]{PyString.fromInterned("x-header")};
      String[] var4 = new String[]{"header"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("foo"), PyString.fromInterned("one")}), new PyTuple(new PyObject[]{PyString.fromInterned("bar"), PyString.fromInterned("two")}), new PyTuple(new PyObject[]{PyString.fromInterned("baz"), PyString.fromInterned("three")})})));
      var1.setline(287);
      var3 = var1.getglobal("email").__getattr__("message_from_string").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("X-Header: foo; bar=one; baz=two\n"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(289);
      var10000 = var1.getlocal(1);
      var10002 = var1.getlocal(2).__getattr__("get_params");
      var5 = new PyObject[]{PyString.fromInterned("x-header")};
      var4 = new String[]{"header"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("foo"), PyString.fromInterned("")}), new PyTuple(new PyObject[]{PyString.fromInterned("bar"), PyString.fromInterned("one")}), new PyTuple(new PyObject[]{PyString.fromInterned("baz"), PyString.fromInterned("two")})})));
      var1.setline(291);
      var1.getlocal(1).__call__(var2, var1.getlocal(2).__getattr__("get_params").__call__(var2), var1.getglobal("None"));
      var1.setline(292);
      var3 = var1.getglobal("email").__getattr__("message_from_string").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("X-Header: foo; bar=\"one\"; baz=two\n"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(294);
      var10000 = var1.getlocal(1);
      var10002 = var1.getlocal(2).__getattr__("get_params");
      var5 = new PyObject[]{PyString.fromInterned("x-header")};
      var4 = new String[]{"header"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("foo"), PyString.fromInterned("")}), new PyTuple(new PyObject[]{PyString.fromInterned("bar"), PyString.fromInterned("one")}), new PyTuple(new PyObject[]{PyString.fromInterned("baz"), PyString.fromInterned("two")})})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_get_param_liberal$25(PyFrame var1, ThreadState var2) {
      var1.setline(298);
      PyObject var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(299);
      PyString var4 = PyString.fromInterned("Content-Type: Multipart/mixed; boundary = \"CPIMSSMTPC06p5f3tG\"");
      var1.getlocal(1).__setitem__((PyObject)PyString.fromInterned("Content-Type"), var4);
      var3 = null;
      var1.setline(300);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("get_param").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("boundary")), (PyObject)PyString.fromInterned("CPIMSSMTPC06p5f3tG"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_get_param$26(PyFrame var1, ThreadState var2) {
      var1.setline(303);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(304);
      var3 = var1.getglobal("email").__getattr__("message_from_string").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("X-Header: foo=one; bar=two; baz=three\n"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(306);
      PyObject var10000 = var1.getlocal(1);
      PyObject var10002 = var1.getlocal(2).__getattr__("get_param");
      PyObject[] var5 = new PyObject[]{PyString.fromInterned("bar"), PyString.fromInterned("x-header")};
      String[] var4 = new String[]{"header"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("two"));
      var1.setline(307);
      var10000 = var1.getlocal(1);
      var10002 = var1.getlocal(2).__getattr__("get_param");
      var5 = new PyObject[]{PyString.fromInterned("quuz"), PyString.fromInterned("x-header")};
      var4 = new String[]{"header"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__(var2, var10002, var1.getglobal("None"));
      var1.setline(308);
      var1.getlocal(1).__call__(var2, var1.getlocal(2).__getattr__("get_param").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("quuz")), var1.getglobal("None"));
      var1.setline(309);
      var3 = var1.getglobal("email").__getattr__("message_from_string").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("X-Header: foo; bar=\"one\"; baz=two\n"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(311);
      var10000 = var1.getlocal(1);
      var10002 = var1.getlocal(2).__getattr__("get_param");
      var5 = new PyObject[]{PyString.fromInterned("foo"), PyString.fromInterned("x-header")};
      var4 = new String[]{"header"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned(""));
      var1.setline(312);
      var10000 = var1.getlocal(1);
      var10002 = var1.getlocal(2).__getattr__("get_param");
      var5 = new PyObject[]{PyString.fromInterned("bar"), PyString.fromInterned("x-header")};
      var4 = new String[]{"header"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("one"));
      var1.setline(313);
      var10000 = var1.getlocal(1);
      var10002 = var1.getlocal(2).__getattr__("get_param");
      var5 = new PyObject[]{PyString.fromInterned("baz"), PyString.fromInterned("x-header")};
      var4 = new String[]{"header"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("two"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_get_param_funky_continuation_lines$27(PyFrame var1, ThreadState var2) {
      var1.setline(320);
      PyObject var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_22.txt"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(321);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(1)).__getattr__("get_param").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("name")), (PyObject)PyString.fromInterned("wibble.JPG"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_get_param_with_semis_in_quotes$28(PyFrame var1, ThreadState var2) {
      var1.setline(324);
      PyObject var3 = var1.getglobal("email").__getattr__("message_from_string").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Content-Type: image/pjpeg; name=\"Jim&amp;&amp;Jill\"\n"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(326);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("get_param").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("name")), (PyObject)PyString.fromInterned("Jim&amp;&amp;Jill"));
      var1.setline(327);
      PyObject var10000 = var1.getlocal(0).__getattr__("assertEqual");
      PyObject var10002 = var1.getlocal(1).__getattr__("get_param");
      PyObject[] var5 = new PyObject[]{PyString.fromInterned("name"), var1.getglobal("False")};
      String[] var4 = new String[]{"unquote"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("\"Jim&amp;&amp;Jill\""));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_has_key$29(PyFrame var1, ThreadState var2) {
      var1.setline(331);
      PyObject var3 = var1.getglobal("email").__getattr__("message_from_string").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Header: exists"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(332);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getlocal(1).__getattr__("has_key").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("header")));
      var1.setline(333);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getlocal(1).__getattr__("has_key").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Header")));
      var1.setline(334);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getlocal(1).__getattr__("has_key").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("HEADER")));
      var1.setline(335);
      var1.getlocal(0).__getattr__("assertFalse").__call__(var2, var1.getlocal(1).__getattr__("has_key").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("headeri")));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_set_param$30(PyFrame var1, ThreadState var2) {
      var1.setline(338);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(339);
      var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(340);
      var1.getlocal(2).__getattr__("set_param").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("charset"), (PyObject)PyString.fromInterned("iso-2022-jp"));
      var1.setline(341);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_param").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("charset")), (PyObject)PyString.fromInterned("iso-2022-jp"));
      var1.setline(342);
      var1.getlocal(2).__getattr__("set_param").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("importance"), (PyObject)PyString.fromInterned("high value"));
      var1.setline(343);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_param").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("importance")), (PyObject)PyString.fromInterned("high value"));
      var1.setline(344);
      PyObject var10000 = var1.getlocal(1);
      PyObject var10002 = var1.getlocal(2).__getattr__("get_param");
      PyObject[] var5 = new PyObject[]{PyString.fromInterned("importance"), var1.getglobal("False")};
      String[] var4 = new String[]{"unquote"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("\"high value\""));
      var1.setline(345);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_params").__call__(var2), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("text/plain"), PyString.fromInterned("")}), new PyTuple(new PyObject[]{PyString.fromInterned("charset"), PyString.fromInterned("iso-2022-jp")}), new PyTuple(new PyObject[]{PyString.fromInterned("importance"), PyString.fromInterned("high value")})})));
      var1.setline(348);
      var10000 = var1.getlocal(1);
      var10002 = var1.getlocal(2).__getattr__("get_params");
      var5 = new PyObject[]{var1.getglobal("False")};
      var4 = new String[]{"unquote"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("text/plain"), PyString.fromInterned("")}), new PyTuple(new PyObject[]{PyString.fromInterned("charset"), PyString.fromInterned("\"iso-2022-jp\"")}), new PyTuple(new PyObject[]{PyString.fromInterned("importance"), PyString.fromInterned("\"high value\"")})})));
      var1.setline(351);
      var10000 = var1.getlocal(2).__getattr__("set_param");
      var5 = new PyObject[]{PyString.fromInterned("charset"), PyString.fromInterned("iso-9999-xx"), PyString.fromInterned("X-Jimmy")};
      var4 = new String[]{"header"};
      var10000.__call__(var2, var5, var4);
      var3 = null;
      var1.setline(352);
      var10000 = var1.getlocal(1);
      var10002 = var1.getlocal(2).__getattr__("get_param");
      var5 = new PyObject[]{PyString.fromInterned("charset"), PyString.fromInterned("X-Jimmy")};
      var4 = new String[]{"header"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("iso-9999-xx"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_del_param$31(PyFrame var1, ThreadState var2) {
      var1.setline(355);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(356);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_05.txt"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(357);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_params").__call__(var2), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("multipart/report"), PyString.fromInterned("")}), new PyTuple(new PyObject[]{PyString.fromInterned("report-type"), PyString.fromInterned("delivery-status")}), new PyTuple(new PyObject[]{PyString.fromInterned("boundary"), PyString.fromInterned("D1690A7AC1.996856090/mail.example.com")})})));
      var1.setline(360);
      var3 = var1.getlocal(2).__getattr__("get_param").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("report-type"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(361);
      var1.getlocal(2).__getattr__("del_param").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("report-type"));
      var1.setline(362);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_params").__call__(var2), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("multipart/report"), PyString.fromInterned("")}), new PyTuple(new PyObject[]{PyString.fromInterned("boundary"), PyString.fromInterned("D1690A7AC1.996856090/mail.example.com")})})));
      var1.setline(365);
      var1.getlocal(2).__getattr__("set_param").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("report-type"), (PyObject)var1.getlocal(3));
      var1.setline(366);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_params").__call__(var2), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("multipart/report"), PyString.fromInterned("")}), new PyTuple(new PyObject[]{PyString.fromInterned("boundary"), PyString.fromInterned("D1690A7AC1.996856090/mail.example.com")}), new PyTuple(new PyObject[]{PyString.fromInterned("report-type"), var1.getlocal(3)})})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_del_param_on_other_header$32(PyFrame var1, ThreadState var2) {
      var1.setline(372);
      PyObject var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(373);
      PyObject var10000 = var1.getlocal(1).__getattr__("add_header");
      PyObject[] var5 = new PyObject[]{PyString.fromInterned("Content-Disposition"), PyString.fromInterned("attachment"), PyString.fromInterned("bud.gif")};
      String[] var4 = new String[]{"filename"};
      var10000.__call__(var2, var5, var4);
      var3 = null;
      var1.setline(374);
      var1.getlocal(1).__getattr__("del_param").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("filename"), (PyObject)PyString.fromInterned("content-disposition"));
      var1.setline(375);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getitem__(PyString.fromInterned("content-disposition")), (PyObject)PyString.fromInterned("attachment"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_set_type$33(PyFrame var1, ThreadState var2) {
      var1.setline(378);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(379);
      var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(380);
      var1.getlocal(0).__getattr__("assertRaises").__call__((ThreadState)var2, var1.getglobal("ValueError"), (PyObject)var1.getlocal(2).__getattr__("set_type"), (PyObject)PyString.fromInterned("text"));
      var1.setline(381);
      var1.getlocal(2).__getattr__("set_type").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("text/plain"));
      var1.setline(382);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getitem__(PyString.fromInterned("content-type")), (PyObject)PyString.fromInterned("text/plain"));
      var1.setline(383);
      var1.getlocal(2).__getattr__("set_param").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("charset"), (PyObject)PyString.fromInterned("us-ascii"));
      var1.setline(384);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getitem__(PyString.fromInterned("content-type")), (PyObject)PyString.fromInterned("text/plain; charset=\"us-ascii\""));
      var1.setline(385);
      var1.getlocal(2).__getattr__("set_type").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("text/html"));
      var1.setline(386);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getitem__(PyString.fromInterned("content-type")), (PyObject)PyString.fromInterned("text/html; charset=\"us-ascii\""));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_set_type_on_other_header$34(PyFrame var1, ThreadState var2) {
      var1.setline(389);
      PyObject var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(390);
      PyString var4 = PyString.fromInterned("text/plain");
      var1.getlocal(1).__setitem__((PyObject)PyString.fromInterned("X-Content-Type"), var4);
      var3 = null;
      var1.setline(391);
      var1.getlocal(1).__getattr__("set_type").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("application/octet-stream"), (PyObject)PyString.fromInterned("X-Content-Type"));
      var1.setline(392);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getitem__(PyString.fromInterned("x-content-type")), (PyObject)PyString.fromInterned("application/octet-stream"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_get_content_type_missing$35(PyFrame var1, ThreadState var2) {
      var1.setline(395);
      PyObject var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(396);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("text/plain"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_get_content_type_missing_with_default_type$36(PyFrame var1, ThreadState var2) {
      var1.setline(399);
      PyObject var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(400);
      var1.getlocal(1).__getattr__("set_default_type").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("message/rfc822"));
      var1.setline(401);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("message/rfc822"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_get_content_type_from_message_implicit$37(PyFrame var1, ThreadState var2) {
      var1.setline(404);
      PyObject var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_30.txt"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(405);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(0)).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("message/rfc822"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_get_content_type_from_message_explicit$38(PyFrame var1, ThreadState var2) {
      var1.setline(409);
      PyObject var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_28.txt"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(410);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(0)).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("message/rfc822"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_get_content_type_from_message_text_plain_implicit$39(PyFrame var1, ThreadState var2) {
      var1.setline(414);
      PyObject var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_03.txt"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(415);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("text/plain"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_get_content_type_from_message_text_plain_explicit$40(PyFrame var1, ThreadState var2) {
      var1.setline(418);
      PyObject var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_01.txt"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(419);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("text/plain"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_get_content_maintype_missing$41(PyFrame var1, ThreadState var2) {
      var1.setline(422);
      PyObject var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(423);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("get_content_maintype").__call__(var2), (PyObject)PyString.fromInterned("text"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_get_content_maintype_missing_with_default_type$42(PyFrame var1, ThreadState var2) {
      var1.setline(426);
      PyObject var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(427);
      var1.getlocal(1).__getattr__("set_default_type").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("message/rfc822"));
      var1.setline(428);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("get_content_maintype").__call__(var2), (PyObject)PyString.fromInterned("message"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_get_content_maintype_from_message_implicit$43(PyFrame var1, ThreadState var2) {
      var1.setline(431);
      PyObject var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_30.txt"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(432);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(0)).__getattr__("get_content_maintype").__call__(var2), (PyObject)PyString.fromInterned("message"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_get_content_maintype_from_message_explicit$44(PyFrame var1, ThreadState var2) {
      var1.setline(435);
      PyObject var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_28.txt"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(436);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(0)).__getattr__("get_content_maintype").__call__(var2), (PyObject)PyString.fromInterned("message"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_get_content_maintype_from_message_text_plain_implicit$45(PyFrame var1, ThreadState var2) {
      var1.setline(439);
      PyObject var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_03.txt"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(440);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("get_content_maintype").__call__(var2), (PyObject)PyString.fromInterned("text"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_get_content_maintype_from_message_text_plain_explicit$46(PyFrame var1, ThreadState var2) {
      var1.setline(443);
      PyObject var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_01.txt"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(444);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("get_content_maintype").__call__(var2), (PyObject)PyString.fromInterned("text"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_get_content_subtype_missing$47(PyFrame var1, ThreadState var2) {
      var1.setline(447);
      PyObject var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(448);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("get_content_subtype").__call__(var2), (PyObject)PyString.fromInterned("plain"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_get_content_subtype_missing_with_default_type$48(PyFrame var1, ThreadState var2) {
      var1.setline(451);
      PyObject var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(452);
      var1.getlocal(1).__getattr__("set_default_type").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("message/rfc822"));
      var1.setline(453);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("get_content_subtype").__call__(var2), (PyObject)PyString.fromInterned("rfc822"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_get_content_subtype_from_message_implicit$49(PyFrame var1, ThreadState var2) {
      var1.setline(456);
      PyObject var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_30.txt"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(457);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(0)).__getattr__("get_content_subtype").__call__(var2), (PyObject)PyString.fromInterned("rfc822"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_get_content_subtype_from_message_explicit$50(PyFrame var1, ThreadState var2) {
      var1.setline(460);
      PyObject var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_28.txt"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(461);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(0)).__getattr__("get_content_subtype").__call__(var2), (PyObject)PyString.fromInterned("rfc822"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_get_content_subtype_from_message_text_plain_implicit$51(PyFrame var1, ThreadState var2) {
      var1.setline(464);
      PyObject var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_03.txt"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(465);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("get_content_subtype").__call__(var2), (PyObject)PyString.fromInterned("plain"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_get_content_subtype_from_message_text_plain_explicit$52(PyFrame var1, ThreadState var2) {
      var1.setline(468);
      PyObject var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_01.txt"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(469);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("get_content_subtype").__call__(var2), (PyObject)PyString.fromInterned("plain"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_get_content_maintype_error$53(PyFrame var1, ThreadState var2) {
      var1.setline(472);
      PyObject var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(473);
      PyString var4 = PyString.fromInterned("no-slash-in-this-string");
      var1.getlocal(1).__setitem__((PyObject)PyString.fromInterned("Content-Type"), var4);
      var3 = null;
      var1.setline(474);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("get_content_maintype").__call__(var2), (PyObject)PyString.fromInterned("text"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_get_content_subtype_error$54(PyFrame var1, ThreadState var2) {
      var1.setline(477);
      PyObject var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(478);
      PyString var4 = PyString.fromInterned("no-slash-in-this-string");
      var1.getlocal(1).__setitem__((PyObject)PyString.fromInterned("Content-Type"), var4);
      var3 = null;
      var1.setline(479);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("get_content_subtype").__call__(var2), (PyObject)PyString.fromInterned("plain"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_replace_header$55(PyFrame var1, ThreadState var2) {
      var1.setline(482);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(483);
      var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(484);
      var1.getlocal(2).__getattr__("add_header").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("First"), (PyObject)PyString.fromInterned("One"));
      var1.setline(485);
      var1.getlocal(2).__getattr__("add_header").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Second"), (PyObject)PyString.fromInterned("Two"));
      var1.setline(486);
      var1.getlocal(2).__getattr__("add_header").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Third"), (PyObject)PyString.fromInterned("Three"));
      var1.setline(487);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("keys").__call__(var2), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("First"), PyString.fromInterned("Second"), PyString.fromInterned("Third")})));
      var1.setline(488);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("values").__call__(var2), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("One"), PyString.fromInterned("Two"), PyString.fromInterned("Three")})));
      var1.setline(489);
      var1.getlocal(2).__getattr__("replace_header").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Second"), (PyObject)PyString.fromInterned("Twenty"));
      var1.setline(490);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("keys").__call__(var2), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("First"), PyString.fromInterned("Second"), PyString.fromInterned("Third")})));
      var1.setline(491);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("values").__call__(var2), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("One"), PyString.fromInterned("Twenty"), PyString.fromInterned("Three")})));
      var1.setline(492);
      var1.getlocal(2).__getattr__("add_header").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("First"), (PyObject)PyString.fromInterned("Eleven"));
      var1.setline(493);
      var1.getlocal(2).__getattr__("replace_header").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("First"), (PyObject)PyString.fromInterned("One Hundred"));
      var1.setline(494);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("keys").__call__(var2), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("First"), PyString.fromInterned("Second"), PyString.fromInterned("Third"), PyString.fromInterned("First")})));
      var1.setline(495);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("values").__call__(var2), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("One Hundred"), PyString.fromInterned("Twenty"), PyString.fromInterned("Three"), PyString.fromInterned("Eleven")})));
      var1.setline(496);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getglobal("KeyError"), var1.getlocal(2).__getattr__("replace_header"), PyString.fromInterned("Fourth"), PyString.fromInterned("Missing"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_broken_base64_payload$56(PyFrame var1, ThreadState var2) {
      var1.setline(499);
      PyString var3 = PyString.fromInterned("AwDp0P7//y6LwKEAcPa/6Q=9");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(500);
      PyObject var5 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(501);
      var3 = PyString.fromInterned("audio/x-midi");
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("content-type"), var3);
      var3 = null;
      var1.setline(502);
      var3 = PyString.fromInterned("base64");
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("content-transfer-encoding"), var3);
      var3 = null;
      var1.setline(503);
      var1.getlocal(2).__getattr__("set_payload").__call__(var2, var1.getlocal(1));
      var1.setline(504);
      PyObject var10000 = var1.getlocal(0).__getattr__("assertEqual");
      PyObject var10002 = var1.getlocal(2).__getattr__("get_payload");
      PyObject[] var6 = new PyObject[]{var1.getglobal("True")};
      String[] var4 = new String[]{"decode"};
      var10002 = var10002.__call__(var2, var6, var4);
      var3 = null;
      var10000.__call__(var2, var10002, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestEncoders$57(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(510);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_encode_empty_payload$58, (PyObject)null);
      var1.setlocal("test_encode_empty_payload", var4);
      var3 = null;
      var1.setline(516);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_default_cte$59, (PyObject)null);
      var1.setlocal("test_default_cte", var4);
      var3 = null;
      var1.setline(521);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_default_cte$60, (PyObject)null);
      var1.setlocal("test_default_cte", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_encode_empty_payload$58(PyFrame var1, ThreadState var2) {
      var1.setline(511);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(512);
      var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(513);
      var1.getlocal(2).__getattr__("set_charset").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("us-ascii"));
      var1.setline(514);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getitem__(PyString.fromInterned("content-transfer-encoding")), (PyObject)PyString.fromInterned("7bit"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_default_cte$59(PyFrame var1, ThreadState var2) {
      var1.setline(517);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(518);
      var3 = var1.getglobal("MIMEText").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hello world"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(519);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getitem__(PyString.fromInterned("content-transfer-encoding")), (PyObject)PyString.fromInterned("7bit"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_default_cte$60(PyFrame var1, ThreadState var2) {
      var1.setline(522);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(524);
      var3 = var1.getglobal("MIMEText").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hello world"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(525);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getitem__(PyString.fromInterned("content-transfer-encoding")), (PyObject)PyString.fromInterned("7bit"));
      var1.setline(527);
      var3 = var1.getglobal("MIMEText").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hello ø world"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(528);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getitem__(PyString.fromInterned("content-transfer-encoding")), (PyObject)PyString.fromInterned("8bit"));
      var1.setline(530);
      PyObject var10000 = var1.getglobal("MIMEText");
      PyObject[] var5 = new PyObject[]{PyString.fromInterned("hello ø world"), PyString.fromInterned("iso-8859-1")};
      String[] var4 = new String[]{"_charset"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(531);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getitem__(PyString.fromInterned("content-transfer-encoding")), (PyObject)PyString.fromInterned("quoted-printable"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestLongHeaders$61(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(537);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_split_long_continuation$62, (PyObject)null);
      var1.setlocal("test_split_long_continuation", var4);
      var3 = null;
      var1.setline(557);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_another_long_almost_unsplittable_header$63, (PyObject)null);
      var1.setlocal("test_another_long_almost_unsplittable_header", var4);
      var3 = null;
      var1.setline(574);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_long_nonstring$64, (PyObject)null);
      var1.setlocal("test_long_nonstring", var4);
      var3 = null;
      var1.setline(617);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_long_header_encode$65, (PyObject)null);
      var1.setlocal("test_long_header_encode", var4);
      var3 = null;
      var1.setline(626);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_long_header_encode_with_tab_continuation$66, (PyObject)null);
      var1.setlocal("test_long_header_encode_with_tab_continuation", var4);
      var3 = null;
      var1.setline(636);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_header_splitter$67, (PyObject)null);
      var1.setlocal("test_header_splitter", var4);
      var3 = null;
      var1.setline(656);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_no_semis_header_splitter$68, (PyObject)null);
      var1.setlocal("test_no_semis_header_splitter", var4);
      var3 = null;
      var1.setline(672);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_no_split_long_header$69, (PyObject)null);
      var1.setlocal("test_no_split_long_header", var4);
      var3 = null;
      var1.setline(679);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_splitting_multiple_long_lines$70, (PyObject)null);
      var1.setlocal("test_splitting_multiple_long_lines", var4);
      var3 = null;
      var1.setline(701);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_splitting_first_line_only_is_long$71, (PyObject)null);
      var1.setlocal("test_splitting_first_line_only_is_long", var4);
      var3 = null;
      var1.setline(717);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_long_8bit_header$72, (PyObject)null);
      var1.setlocal("test_long_8bit_header", var4);
      var3 = null;
      var1.setline(730);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_long_8bit_header_no_charset$73, (PyObject)null);
      var1.setlocal("test_long_8bit_header_no_charset", var4);
      var3 = null;
      var1.setline(739);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_long_to_header$74, (PyObject)null);
      var1.setlocal("test_long_to_header", var4);
      var3 = null;
      var1.setline(752);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_long_line_after_append$75, (PyObject)null);
      var1.setlocal("test_long_line_after_append", var4);
      var3 = null;
      var1.setline(761);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_shorter_line_with_append$76, (PyObject)null);
      var1.setlocal("test_shorter_line_with_append", var4);
      var3 = null;
      var1.setline(769);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_long_field_name$77, (PyObject)null);
      var1.setlocal("test_long_field_name", var4);
      var3 = null;
      var1.setline(781);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_long_received_header$78, (PyObject)null);
      var1.setlocal("test_long_received_header", var4);
      var3 = null;
      var1.setline(796);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_string_headerinst_eq$79, (PyObject)null);
      var1.setlocal("test_string_headerinst_eq", var4);
      var3 = null;
      var1.setline(810);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_long_unbreakable_lines_with_continuation$80, (PyObject)null);
      var1.setlocal("test_long_unbreakable_lines_with_continuation", var4);
      var3 = null;
      var1.setline(826);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_another_long_multiline_header$81, (PyObject)null);
      var1.setlocal("test_another_long_multiline_header", var4);
      var3 = null;
      var1.setline(838);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_long_lines_with_different_header$82, (PyObject)null);
      var1.setlocal("test_long_lines_with_different_header", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_split_long_continuation$62(PyFrame var1, ThreadState var2) {
      var1.setline(538);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(539);
      var3 = var1.getglobal("email").__getattr__("message_from_string").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Subject: bug demonstration\n\t12345678911234567892123456789312345678941234567895123456789612345678971234567898112345678911234567892123456789112345678911234567892123456789\n\tmore text\n\ntest\n"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(546);
      var3 = var1.getglobal("StringIO").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(547);
      var3 = var1.getglobal("Generator").__call__(var2, var1.getlocal(3));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(548);
      var1.getlocal(4).__getattr__("flatten").__call__(var2, var1.getlocal(2));
      var1.setline(549);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("getvalue").__call__(var2), (PyObject)PyString.fromInterned("Subject: bug demonstration\n 12345678911234567892123456789312345678941234567895123456789612345678971234567898112345678911234567892123456789112345678911234567892123456789\n more text\n\ntest\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_another_long_almost_unsplittable_header$63(PyFrame var1, ThreadState var2) {
      var1.setline(558);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(559);
      PyString var5 = PyString.fromInterned("bug demonstration\n\t12345678911234567892123456789312345678941234567895123456789612345678971234567898112345678911234567892123456789112345678911234567892123456789\n\tmore text");
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(563);
      PyObject var10000 = var1.getglobal("Header");
      PyObject[] var6 = new PyObject[]{var1.getlocal(2), PyString.fromInterned("\t")};
      String[] var4 = new String[]{"continuation_ws"};
      var10000 = var10000.__call__(var2, var6, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(564);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("encode").__call__(var2), (PyObject)PyString.fromInterned("bug demonstration\n\t12345678911234567892123456789312345678941234567895123456789612345678971234567898112345678911234567892123456789112345678911234567892123456789\n\tmore text"));
      var1.setline(568);
      var3 = var1.getglobal("Header").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(569);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("encode").__call__(var2), (PyObject)PyString.fromInterned("bug demonstration\n 12345678911234567892123456789312345678941234567895123456789612345678971234567898112345678911234567892123456789112345678911234567892123456789\n more text"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_long_nonstring$64(PyFrame var1, ThreadState var2) {
      var1.setline(575);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(576);
      var3 = var1.getglobal("Charset").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("iso-8859-1"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(577);
      var3 = var1.getglobal("Charset").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("iso-8859-2"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(578);
      var3 = var1.getglobal("Charset").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("utf-8"));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(579);
      PyString var5 = PyString.fromInterned("Die Mieter treten hier ein werden mit einem Foerderband komfortabel den Korridor entlang, an südlündischen Wandgemälden vorbei, gegen die rotierenden Klingen befördert. ");
      var1.setlocal(5, var5);
      var3 = null;
      var1.setline(580);
      var5 = PyString.fromInterned("Finanèni metropole se hroutily pod tlakem jejich dùvtipu.. ");
      var1.setlocal(6, var5);
      var3 = null;
      var1.setline(581);
      var3 = PyUnicode.fromInterned("正確に言うと翻訳はされていません。一部はドイツ語ですが、あとはでたらめです。実際には「Wenn ist das Nunstuck git und Slotermeyer? Ja! Beiherhund das Oder die Flipperwaldt gersput.」と言っています。").__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("utf-8"));
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(582);
      PyObject var10000 = var1.getglobal("Header");
      PyObject[] var6 = new PyObject[]{var1.getlocal(5), var1.getlocal(2), PyString.fromInterned("Subject")};
      String[] var4 = new String[]{"header_name"};
      var10000 = var10000.__call__(var2, var6, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(8, var3);
      var3 = null;
      var1.setline(583);
      var1.getlocal(8).__getattr__("append").__call__(var2, var1.getlocal(6), var1.getlocal(3));
      var1.setline(584);
      var1.getlocal(8).__getattr__("append").__call__(var2, var1.getlocal(7), var1.getlocal(4));
      var1.setline(585);
      var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(9, var3);
      var3 = null;
      var1.setline(586);
      var3 = var1.getlocal(8);
      var1.getlocal(9).__setitem__((PyObject)PyString.fromInterned("Subject"), var3);
      var3 = null;
      var1.setline(587);
      var3 = var1.getglobal("StringIO").__call__(var2);
      var1.setlocal(10, var3);
      var3 = null;
      var1.setline(588);
      var3 = var1.getglobal("Generator").__call__(var2, var1.getlocal(10));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(589);
      var1.getlocal(2).__getattr__("flatten").__call__(var2, var1.getlocal(9));
      var1.setline(590);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(10).__getattr__("getvalue").__call__(var2), (PyObject)PyString.fromInterned("Subject: =?iso-8859-1?q?Die_Mieter_treten_hier_ein_werden_mit_einem_Foerd?=\n =?iso-8859-1?q?erband_komfortabel_den_Korridor_entlang=2C_an_s=FCdl=FCndi?=\n =?iso-8859-1?q?schen_Wandgem=E4lden_vorbei=2C_gegen_die_rotierenden_Kling?=\n =?iso-8859-1?q?en_bef=F6rdert=2E_?= =?iso-8859-2?q?Finan=E8ni_met?=\n =?iso-8859-2?q?ropole_se_hroutily_pod_tlakem_jejich_d=F9vtipu=2E=2E_?=\n =?utf-8?b?5q2j56K644Gr6KiA44GG44Go57+76Kiz44Gv44GV44KM44Gm44GE?=\n =?utf-8?b?44G+44Gb44KT44CC5LiA6YOo44Gv44OJ44Kk44OE6Kqe44Gn44GZ44GM44CB?=\n =?utf-8?b?44GC44Go44Gv44Gn44Gf44KJ44KB44Gn44GZ44CC5a6f6Zqb44Gr44Gv44CM?=\n =?utf-8?q?Wenn_ist_das_Nunstuck_git_und_Slotermeyer=3F_Ja!_Beiherhund_das?=\n =?utf-8?b?IE9kZXIgZGllIEZsaXBwZXJ3YWxkdCBnZXJzcHV0LuOAjeOBqOiogOOBow==?=\n =?utf-8?b?44Gm44GE44G+44GZ44CC?=\n\n"));
      var1.setline(604);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(8).__getattr__("encode").__call__(var2), (PyObject)PyString.fromInterned("=?iso-8859-1?q?Die_Mieter_treten_hier_ein_werden_mit_einem_Foerd?=\n =?iso-8859-1?q?erband_komfortabel_den_Korridor_entlang=2C_an_s=FCdl=FCndi?=\n =?iso-8859-1?q?schen_Wandgem=E4lden_vorbei=2C_gegen_die_rotierenden_Kling?=\n =?iso-8859-1?q?en_bef=F6rdert=2E_?= =?iso-8859-2?q?Finan=E8ni_met?=\n =?iso-8859-2?q?ropole_se_hroutily_pod_tlakem_jejich_d=F9vtipu=2E=2E_?=\n =?utf-8?b?5q2j56K644Gr6KiA44GG44Go57+76Kiz44Gv44GV44KM44Gm44GE?=\n =?utf-8?b?44G+44Gb44KT44CC5LiA6YOo44Gv44OJ44Kk44OE6Kqe44Gn44GZ44GM44CB?=\n =?utf-8?b?44GC44Go44Gv44Gn44Gf44KJ44KB44Gn44GZ44CC5a6f6Zqb44Gr44Gv44CM?=\n =?utf-8?q?Wenn_ist_das_Nunstuck_git_und_Slotermeyer=3F_Ja!_Beiherhund_das?=\n =?utf-8?b?IE9kZXIgZGllIEZsaXBwZXJ3YWxkdCBnZXJzcHV0LuOAjeOBqOiogOOBow==?=\n =?utf-8?b?44Gm44GE44G+44GZ44CC?="));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_long_header_encode$65(PyFrame var1, ThreadState var2) {
      var1.setline(618);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(619);
      PyObject var10000 = var1.getglobal("Header");
      PyObject[] var5 = new PyObject[]{PyString.fromInterned("wasnipoop; giraffes=\"very-long-necked-animals\"; spooge=\"yummy\"; hippos=\"gargantuan\"; marshmallows=\"gooey\""), PyString.fromInterned("X-Foobar-Spoink-Defrobnit")};
      String[] var4 = new String[]{"header_name"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(622);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("encode").__call__(var2), (PyObject)PyString.fromInterned("wasnipoop; giraffes=\"very-long-necked-animals\";\n spooge=\"yummy\"; hippos=\"gargantuan\"; marshmallows=\"gooey\""));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_long_header_encode_with_tab_continuation$66(PyFrame var1, ThreadState var2) {
      var1.setline(627);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(628);
      PyObject var10000 = var1.getglobal("Header");
      PyObject[] var5 = new PyObject[]{PyString.fromInterned("wasnipoop; giraffes=\"very-long-necked-animals\"; spooge=\"yummy\"; hippos=\"gargantuan\"; marshmallows=\"gooey\""), PyString.fromInterned("X-Foobar-Spoink-Defrobnit"), PyString.fromInterned("\t")};
      String[] var4 = new String[]{"header_name", "continuation_ws"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(632);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("encode").__call__(var2), (PyObject)PyString.fromInterned("wasnipoop; giraffes=\"very-long-necked-animals\";\n\tspooge=\"yummy\"; hippos=\"gargantuan\"; marshmallows=\"gooey\""));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_header_splitter$67(PyFrame var1, ThreadState var2) {
      var1.setline(637);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(638);
      var3 = var1.getglobal("MIMEText").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(""));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(641);
      PyString var4 = PyString.fromInterned("wasnipoop; giraffes=\"very-long-necked-animals\"; spooge=\"yummy\"; hippos=\"gargantuan\"; marshmallows=\"gooey\"");
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("X-Foobar-Spoink-Defrobnit"), var4);
      var3 = null;
      var1.setline(644);
      var3 = var1.getglobal("StringIO").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(645);
      var3 = var1.getglobal("Generator").__call__(var2, var1.getlocal(3));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(646);
      var1.getlocal(4).__getattr__("flatten").__call__(var2, var1.getlocal(2));
      var1.setline(647);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("getvalue").__call__(var2), (PyObject)PyString.fromInterned("Content-Type: text/plain; charset=\"us-ascii\"\nMIME-Version: 1.0\nContent-Transfer-Encoding: 7bit\nX-Foobar-Spoink-Defrobnit: wasnipoop; giraffes=\"very-long-necked-animals\";\n spooge=\"yummy\"; hippos=\"gargantuan\"; marshmallows=\"gooey\"\n\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_no_semis_header_splitter$68(PyFrame var1, ThreadState var2) {
      var1.setline(657);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(658);
      var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(659);
      PyString var5 = PyString.fromInterned("test@dom.ain");
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("From"), var5);
      var3 = null;
      var1.setline(660);
      PyObject var10000 = var1.getglobal("SPACE").__getattr__("join");
      PyList var10002 = new PyList();
      var3 = var10002.__getattr__("append");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(660);
      var3 = var1.getglobal("range").__call__((ThreadState)var2, (PyObject)Py.newInteger(10)).__iter__();

      while(true) {
         var1.setline(660);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(660);
            var1.dellocal(3);
            var3 = var10000.__call__((ThreadState)var2, (PyObject)var10002);
            var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("References"), var3);
            var3 = null;
            var1.setline(661);
            var1.getlocal(2).__getattr__("set_payload").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Test"));
            var1.setline(662);
            var3 = var1.getglobal("StringIO").__call__(var2);
            var1.setlocal(5, var3);
            var3 = null;
            var1.setline(663);
            var3 = var1.getglobal("Generator").__call__(var2, var1.getlocal(5));
            var1.setlocal(6, var3);
            var3 = null;
            var1.setline(664);
            var1.getlocal(6).__getattr__("flatten").__call__(var2, var1.getlocal(2));
            var1.setline(665);
            var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(5).__getattr__("getvalue").__call__(var2), (PyObject)PyString.fromInterned("From: test@dom.ain\nReferences: <0@dom.ain> <1@dom.ain> <2@dom.ain> <3@dom.ain> <4@dom.ain>\n <5@dom.ain> <6@dom.ain> <7@dom.ain> <8@dom.ain> <9@dom.ain>\n\nTest"));
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(4, var4);
         var1.setline(660);
         var1.getlocal(3).__call__(var2, PyString.fromInterned("<%d@dom.ain>")._mod(var1.getlocal(4)));
      }
   }

   public PyObject test_no_split_long_header$69(PyFrame var1, ThreadState var2) {
      var1.setline(673);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(674);
      var3 = PyString.fromInterned("References: ")._add(PyString.fromInterned("x")._mul(Py.newInteger(80)));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(675);
      PyObject var10000 = var1.getglobal("Header");
      PyObject[] var5 = new PyObject[]{var1.getlocal(2), PyString.fromInterned("\t")};
      String[] var4 = new String[]{"continuation_ws"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(676);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("encode").__call__(var2), (PyObject)PyString.fromInterned("References: xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_splitting_multiple_long_lines$70(PyFrame var1, ThreadState var2) {
      var1.setline(680);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(681);
      PyString var5 = PyString.fromInterned("from babylon.socal-raves.org (localhost [127.0.0.1]); by babylon.socal-raves.org (Postfix) with ESMTP id B570E51B81; for <mailman-admin@babylon.socal-raves.org>; Sat, 2 Feb 2002 17:00:06 -0800 (PST)\n\tfrom babylon.socal-raves.org (localhost [127.0.0.1]); by babylon.socal-raves.org (Postfix) with ESMTP id B570E51B81; for <mailman-admin@babylon.socal-raves.org>; Sat, 2 Feb 2002 17:00:06 -0800 (PST)\n\tfrom babylon.socal-raves.org (localhost [127.0.0.1]); by babylon.socal-raves.org (Postfix) with ESMTP id B570E51B81; for <mailman-admin@babylon.socal-raves.org>; Sat, 2 Feb 2002 17:00:06 -0800 (PST)\n");
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(686);
      PyObject var10000 = var1.getglobal("Header");
      PyObject[] var6 = new PyObject[]{var1.getlocal(2), PyString.fromInterned("\t")};
      String[] var4 = new String[]{"continuation_ws"};
      var10000 = var10000.__call__(var2, var6, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(687);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("encode").__call__(var2), (PyObject)PyString.fromInterned("from babylon.socal-raves.org (localhost [127.0.0.1]);\n\tby babylon.socal-raves.org (Postfix) with ESMTP id B570E51B81;\n\tfor <mailman-admin@babylon.socal-raves.org>;\n\tSat, 2 Feb 2002 17:00:06 -0800 (PST)\n\tfrom babylon.socal-raves.org (localhost [127.0.0.1]);\n\tby babylon.socal-raves.org (Postfix) with ESMTP id B570E51B81;\n\tfor <mailman-admin@babylon.socal-raves.org>;\n\tSat, 2 Feb 2002 17:00:06 -0800 (PST)\n\tfrom babylon.socal-raves.org (localhost [127.0.0.1]);\n\tby babylon.socal-raves.org (Postfix) with ESMTP id B570E51B81;\n\tfor <mailman-admin@babylon.socal-raves.org>;\n\tSat, 2 Feb 2002 17:00:06 -0800 (PST)"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_splitting_first_line_only_is_long$71(PyFrame var1, ThreadState var2) {
      var1.setline(702);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(703);
      PyString var5 = PyString.fromInterned("from modemcable093.139-201-24.que.mc.videotron.ca ([24.201.139.93] helo=cthulhu.gerg.ca)\n\tby kronos.mems-exchange.org with esmtp (Exim 4.05)\n\tid 17k4h5-00034i-00\n\tfor test@mems-exchange.org; Wed, 28 Aug 2002 11:25:20 -0400");
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(708);
      PyObject var10000 = var1.getglobal("Header");
      PyObject[] var6 = new PyObject[]{var1.getlocal(2), Py.newInteger(78), PyString.fromInterned("Received"), PyString.fromInterned("\t")};
      String[] var4 = new String[]{"maxlinelen", "header_name", "continuation_ws"};
      var10000 = var10000.__call__(var2, var6, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(710);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("encode").__call__(var2), (PyObject)PyString.fromInterned("from modemcable093.139-201-24.que.mc.videotron.ca ([24.201.139.93]\n\thelo=cthulhu.gerg.ca)\n\tby kronos.mems-exchange.org with esmtp (Exim 4.05)\n\tid 17k4h5-00034i-00\n\tfor test@mems-exchange.org; Wed, 28 Aug 2002 11:25:20 -0400"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_long_8bit_header$72(PyFrame var1, ThreadState var2) {
      var1.setline(718);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(719);
      var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(720);
      PyObject var10000 = var1.getglobal("Header");
      PyObject[] var5 = new PyObject[]{PyString.fromInterned("Britische Regierung gibt"), PyString.fromInterned("iso-8859-1"), PyString.fromInterned("Subject")};
      String[] var4 = new String[]{"header_name"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(722);
      var1.getlocal(3).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("grünes Licht für Offshore-Windkraftprojekte"));
      var1.setline(723);
      var3 = var1.getlocal(3);
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("Subject"), var3);
      var3 = null;
      var1.setline(724);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("as_string").__call__(var2), (PyObject)PyString.fromInterned("Subject: =?iso-8859-1?q?Britische_Regierung_gibt?= =?iso-8859-1?q?gr=FCnes?=\n =?iso-8859-1?q?_Licht_f=FCr_Offshore-Windkraftprojekte?=\n\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_long_8bit_header_no_charset$73(PyFrame var1, ThreadState var2) {
      var1.setline(731);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(732);
      var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(733);
      PyString var4 = PyString.fromInterned("Britische Regierung gibt grünes Licht für Offshore-Windkraftprojekte <a-very-long-address@example.com>");
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("Reply-To"), var4);
      var3 = null;
      var1.setline(734);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("as_string").__call__(var2), (PyObject)PyString.fromInterned("Reply-To: Britische Regierung gibt grünes Licht für Offshore-Windkraftprojekte <a-very-long-address@example.com>\n\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_long_to_header$74(PyFrame var1, ThreadState var2) {
      var1.setline(740);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(741);
      PyString var4 = PyString.fromInterned("\"Someone Test #A\" <someone@eecs.umich.edu>,<someone@eecs.umich.edu>,\"Someone Test #B\" <someone@umich.edu>, \"Someone Test #C\" <someone@eecs.umich.edu>, \"Someone Test #D\" <someone@eecs.umich.edu>");
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(742);
      var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(743);
      var3 = var1.getlocal(2);
      var1.getlocal(3).__setitem__((PyObject)PyString.fromInterned("To"), var3);
      var3 = null;
      var1.setline(744);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("as_string").__call__((ThreadState)var2, (PyObject)Py.newInteger(0)), (PyObject)PyString.fromInterned("To: \"Someone Test #A\" <someone@eecs.umich.edu>, <someone@eecs.umich.edu>,\n \"Someone Test #B\" <someone@umich.edu>,\n \"Someone Test #C\" <someone@eecs.umich.edu>,\n \"Someone Test #D\" <someone@eecs.umich.edu>\n\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_long_line_after_append$75(PyFrame var1, ThreadState var2) {
      var1.setline(753);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(754);
      PyString var4 = PyString.fromInterned("This is an example of string which has almost the limit of header length.");
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(755);
      var3 = var1.getglobal("Header").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(756);
      var1.getlocal(3).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Add another line."));
      var1.setline(757);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("encode").__call__(var2), (PyObject)PyString.fromInterned("This is an example of string which has almost the limit of header length.\n Add another line."));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_shorter_line_with_append$76(PyFrame var1, ThreadState var2) {
      var1.setline(762);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(763);
      PyString var4 = PyString.fromInterned("This is a shorter line.");
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(764);
      var3 = var1.getglobal("Header").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(765);
      var1.getlocal(3).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Add another sentence. (Surprise?)"));
      var1.setline(766);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("encode").__call__(var2), (PyObject)PyString.fromInterned("This is a shorter line. Add another sentence. (Surprise?)"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_long_field_name$77(PyFrame var1, ThreadState var2) {
      var1.setline(770);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(771);
      PyString var5 = PyString.fromInterned("X-Very-Very-Very-Long-Header-Name");
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(772);
      var5 = PyString.fromInterned("Die Mieter treten hier ein werden mit einem Foerderband komfortabel den Korridor entlang, an südlündischen Wandgemälden vorbei, gegen die rotierenden Klingen befördert. ");
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(773);
      PyObject var10000 = var1.getglobal("Header");
      PyObject[] var6 = new PyObject[]{var1.getlocal(3), PyString.fromInterned("iso-8859-1"), var1.getlocal(2)};
      String[] var4 = new String[]{"header_name"};
      var10000 = var10000.__call__(var2, var6, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(775);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(4).__getattr__("encode").__call__(var2), (PyObject)PyString.fromInterned("=?iso-8859-1?q?Die_Mieter_treten_hier_?=\n =?iso-8859-1?q?ein_werden_mit_einem_Foerderband_komfortabel_den_Korridor_?=\n =?iso-8859-1?q?entlang=2C_an_s=FCdl=FCndischen_Wandgem=E4lden_vorbei=2C_g?=\n =?iso-8859-1?q?egen_die_rotierenden_Klingen_bef=F6rdert=2E_?="));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_long_received_header$78(PyFrame var1, ThreadState var2) {
      var1.setline(782);
      PyString var3 = PyString.fromInterned("from FOO.TLD (vizworld.acl.foo.tld [123.452.678.9]) by hrothgar.la.mastaler.com (tmda-ofmipd) with ESMTP; Wed, 05 Mar 2003 18:10:18 -0700");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(783);
      PyObject var5 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(784);
      PyObject var10000 = var1.getglobal("Header");
      PyObject[] var6 = new PyObject[]{var1.getlocal(1), PyString.fromInterned("\t")};
      String[] var4 = new String[]{"continuation_ws"};
      var10000 = var10000.__call__(var2, var6, var4);
      var3 = null;
      var5 = var10000;
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("Received-1"), var5);
      var3 = null;
      var1.setline(785);
      var5 = var1.getlocal(1);
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("Received-2"), var5);
      var3 = null;
      var1.setline(786);
      var1.getlocal(0).__getattr__("ndiffAssertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("as_string").__call__(var2), (PyObject)PyString.fromInterned("Received-1: from FOO.TLD (vizworld.acl.foo.tld [123.452.678.9]) by\n\throthgar.la.mastaler.com (tmda-ofmipd) with ESMTP;\n\tWed, 05 Mar 2003 18:10:18 -0700\nReceived-2: from FOO.TLD (vizworld.acl.foo.tld [123.452.678.9]) by\n hrothgar.la.mastaler.com (tmda-ofmipd) with ESMTP;\n Wed, 05 Mar 2003 18:10:18 -0700\n\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_string_headerinst_eq$79(PyFrame var1, ThreadState var2) {
      var1.setline(797);
      PyString var3 = PyString.fromInterned("<15975.17901.207240.414604@sgigritzmann1.mathematik.tu-muenchen.de> (David Bremner's message of \"Thu, 6 Mar 2003 13:58:21 +0100\")");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(798);
      PyObject var5 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(799);
      PyObject var10000 = var1.getglobal("Header");
      PyObject[] var6 = new PyObject[]{var1.getlocal(1), PyString.fromInterned("Received-1"), PyString.fromInterned("\t")};
      String[] var4 = new String[]{"header_name", "continuation_ws"};
      var10000 = var10000.__call__(var2, var6, var4);
      var3 = null;
      var5 = var10000;
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("Received"), var5);
      var3 = null;
      var1.setline(801);
      var5 = var1.getlocal(1);
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("Received"), var5);
      var3 = null;
      var1.setline(802);
      var1.getlocal(0).__getattr__("ndiffAssertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("as_string").__call__(var2), (PyObject)PyString.fromInterned("Received: <15975.17901.207240.414604@sgigritzmann1.mathematik.tu-muenchen.de>\n\t(David Bremner's message of \"Thu, 6 Mar 2003 13:58:21 +0100\")\nReceived: <15975.17901.207240.414604@sgigritzmann1.mathematik.tu-muenchen.de>\n (David Bremner's message of \"Thu, 6 Mar 2003 13:58:21 +0100\")\n\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_long_unbreakable_lines_with_continuation$80(PyFrame var1, ThreadState var2) {
      var1.setline(811);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(812);
      var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(813);
      PyString var5 = PyString.fromInterned(" iVBORw0KGgoAAAANSUhEUgAAADAAAAAwBAMAAAClLOS0AAAAGFBMVEUAAAAkHiJeRUIcGBi9\n locQDQ4zJykFBAXJfWDjAAACYUlEQVR4nF2TQY/jIAyFc6lydlG5x8Nyp1Y69wj1PN2I5gzp");
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(816);
      var3 = var1.getlocal(3);
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("Face-1"), var3);
      var3 = null;
      var1.setline(817);
      PyObject var10000 = var1.getglobal("Header");
      PyObject[] var6 = new PyObject[]{var1.getlocal(3), PyString.fromInterned("Face-2")};
      String[] var4 = new String[]{"header_name"};
      var10000 = var10000.__call__(var2, var6, var4);
      var3 = null;
      var3 = var10000;
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("Face-2"), var3);
      var3 = null;
      var1.setline(818);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("as_string").__call__(var2), (PyObject)PyString.fromInterned("Face-1: iVBORw0KGgoAAAANSUhEUgAAADAAAAAwBAMAAAClLOS0AAAAGFBMVEUAAAAkHiJeRUIcGBi9\n locQDQ4zJykFBAXJfWDjAAACYUlEQVR4nF2TQY/jIAyFc6lydlG5x8Nyp1Y69wj1PN2I5gzp\nFace-2: iVBORw0KGgoAAAANSUhEUgAAADAAAAAwBAMAAAClLOS0AAAAGFBMVEUAAAAkHiJeRUIcGBi9\n locQDQ4zJykFBAXJfWDjAAACYUlEQVR4nF2TQY/jIAyFc6lydlG5x8Nyp1Y69wj1PN2I5gzp\n\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_another_long_multiline_header$81(PyFrame var1, ThreadState var2) {
      var1.setline(827);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(828);
      PyString var4 = PyString.fromInterned("Received: from siimage.com ([172.25.1.3]) by zima.siliconimage.com with Microsoft SMTPSVC(5.0.2195.4905);\n Wed, 16 Oct 2002 07:41:11 -0700");
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(831);
      var3 = var1.getglobal("email").__getattr__("message_from_string").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(832);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("as_string").__call__(var2), (PyObject)PyString.fromInterned("Received: from siimage.com ([172.25.1.3]) by zima.siliconimage.com with\n Microsoft SMTPSVC(5.0.2195.4905); Wed, 16 Oct 2002 07:41:11 -0700\n\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_long_lines_with_different_header$82(PyFrame var1, ThreadState var2) {
      var1.setline(839);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(840);
      PyString var5 = PyString.fromInterned("List-Unsubscribe: <https://lists.sourceforge.net/lists/listinfo/spamassassin-talk>,\n        <mailto:spamassassin-talk-request@lists.sourceforge.net?subject=unsubscribe>");
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(843);
      var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(844);
      var3 = var1.getlocal(2);
      var1.getlocal(3).__setitem__((PyObject)PyString.fromInterned("List"), var3);
      var3 = null;
      var1.setline(845);
      PyObject var10000 = var1.getglobal("Header");
      PyObject[] var6 = new PyObject[]{var1.getlocal(2), PyString.fromInterned("List")};
      String[] var4 = new String[]{"header_name"};
      var10000 = var10000.__call__(var2, var6, var4);
      var3 = null;
      var3 = var10000;
      var1.getlocal(3).__setitem__((PyObject)PyString.fromInterned("List"), var3);
      var3 = null;
      var1.setline(846);
      var1.getlocal(0).__getattr__("ndiffAssertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("as_string").__call__(var2), (PyObject)PyString.fromInterned("List: List-Unsubscribe: <https://lists.sourceforge.net/lists/listinfo/spamassassin-talk>,\n <mailto:spamassassin-talk-request@lists.sourceforge.net?subject=unsubscribe>\nList: List-Unsubscribe: <https://lists.sourceforge.net/lists/listinfo/spamassassin-talk>,\n <mailto:spamassassin-talk-request@lists.sourceforge.net?subject=unsubscribe>\n\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestFromMangling$83(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(858);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, setUp$84, (PyObject)null);
      var1.setlocal("setUp", var4);
      var3 = null;
      var1.setline(866);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_mangled_from$85, (PyObject)null);
      var1.setlocal("test_mangled_from", var4);
      var3 = null;
      var1.setline(877);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_dont_mangle_from$86, (PyObject)null);
      var1.setlocal("test_dont_mangle_from", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject setUp$84(PyFrame var1, ThreadState var2) {
      var1.setline(859);
      PyObject var3 = var1.getglobal("Message").__call__(var2);
      var1.getlocal(0).__setattr__("msg", var3);
      var3 = null;
      var1.setline(860);
      PyString var4 = PyString.fromInterned("aaa@bbb.org");
      var1.getlocal(0).__getattr__("msg").__setitem__((PyObject)PyString.fromInterned("From"), var4);
      var3 = null;
      var1.setline(861);
      var1.getlocal(0).__getattr__("msg").__getattr__("set_payload").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("From the desk of A.A.A.:\nBlah blah blah\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_mangled_from$85(PyFrame var1, ThreadState var2) {
      var1.setline(867);
      PyObject var3 = var1.getglobal("StringIO").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(868);
      PyObject var10000 = var1.getglobal("Generator");
      PyObject[] var5 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"mangle_from_"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(869);
      var1.getlocal(2).__getattr__("flatten").__call__(var2, var1.getlocal(0).__getattr__("msg"));
      var1.setline(870);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("getvalue").__call__(var2), (PyObject)PyString.fromInterned("From: aaa@bbb.org\n\n>From the desk of A.A.A.:\nBlah blah blah\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_dont_mangle_from$86(PyFrame var1, ThreadState var2) {
      var1.setline(878);
      PyObject var3 = var1.getglobal("StringIO").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(879);
      PyObject var10000 = var1.getglobal("Generator");
      PyObject[] var5 = new PyObject[]{var1.getlocal(1), var1.getglobal("False")};
      String[] var4 = new String[]{"mangle_from_"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(880);
      var1.getlocal(2).__getattr__("flatten").__call__(var2, var1.getlocal(0).__getattr__("msg"));
      var1.setline(881);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("getvalue").__call__(var2), (PyObject)PyString.fromInterned("From: aaa@bbb.org\n\nFrom the desk of A.A.A.:\nBlah blah blah\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestMIMEAudio$87(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(892);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, setUp$88, (PyObject)null);
      var1.setlocal("setUp", var4);
      var3 = null;
      var1.setline(906);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_guess_minor_type$89, (PyObject)null);
      var1.setlocal("test_guess_minor_type", var4);
      var3 = null;
      var1.setline(909);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_encoding$90, (PyObject)null);
      var1.setlocal("test_encoding", var4);
      var3 = null;
      var1.setline(913);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_checkSetMinor$91, (PyObject)null);
      var1.setlocal("test_checkSetMinor", var4);
      var3 = null;
      var1.setline(917);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_add_header$92, (PyObject)null);
      var1.setlocal("test_add_header", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject setUp$88(PyFrame var1, ThreadState var2) {
      var1.setline(898);
      PyObject var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, var1.getglobal("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getglobal("landmark")), (PyObject)PyString.fromInterned("data"), (PyObject)PyString.fromInterned(""));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(899);
      var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getglobal("findfile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("audiotest.au"), (PyObject)var1.getlocal(1)), (PyObject)PyString.fromInterned("rb"));
      var1.setlocal(2, var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(901);
         PyObject var4 = var1.getlocal(2).__getattr__("read").__call__(var2);
         var1.getlocal(0).__setattr__("_audiodata", var4);
         var4 = null;
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(903);
         var1.getlocal(2).__getattr__("close").__call__(var2);
         throw (Throwable)var5;
      }

      var1.setline(903);
      var1.getlocal(2).__getattr__("close").__call__(var2);
      var1.setline(904);
      var3 = var1.getglobal("MIMEAudio").__call__(var2, var1.getlocal(0).__getattr__("_audiodata"));
      var1.getlocal(0).__setattr__("_au", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_guess_minor_type$89(PyFrame var1, ThreadState var2) {
      var1.setline(907);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("_au").__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("audio/basic"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_encoding$90(PyFrame var1, ThreadState var2) {
      var1.setline(910);
      PyObject var3 = var1.getlocal(0).__getattr__("_au").__getattr__("get_payload").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(911);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getglobal("base64").__getattr__("decodestring").__call__(var2, var1.getlocal(1)), var1.getlocal(0).__getattr__("_audiodata"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_checkSetMinor$91(PyFrame var1, ThreadState var2) {
      var1.setline(914);
      PyObject var3 = var1.getglobal("MIMEAudio").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("_audiodata"), (PyObject)PyString.fromInterned("fish"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(915);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("audio/fish"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_add_header$92(PyFrame var1, ThreadState var2) {
      var1.setline(918);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(919);
      var3 = var1.getlocal(0).__getattr__("assertTrue");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(920);
      PyObject var10000 = var1.getlocal(0).__getattr__("_au").__getattr__("add_header");
      PyObject[] var7 = new PyObject[]{PyString.fromInterned("Content-Disposition"), PyString.fromInterned("attachment"), PyString.fromInterned("audiotest.au")};
      String[] var4 = new String[]{"filename"};
      var10000.__call__(var2, var7, var4);
      var3 = null;
      var1.setline(922);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("_au").__getitem__(PyString.fromInterned("content-disposition")), (PyObject)PyString.fromInterned("attachment; filename=\"audiotest.au\""));
      var1.setline(924);
      var10000 = var1.getlocal(1);
      PyObject var10002 = var1.getlocal(0).__getattr__("_au").__getattr__("get_params");
      var7 = new PyObject[]{PyString.fromInterned("content-disposition")};
      var4 = new String[]{"header"};
      var10002 = var10002.__call__(var2, var7, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("attachment"), PyString.fromInterned("")}), new PyTuple(new PyObject[]{PyString.fromInterned("filename"), PyString.fromInterned("audiotest.au")})})));
      var1.setline(926);
      var10000 = var1.getlocal(1);
      var10002 = var1.getlocal(0).__getattr__("_au").__getattr__("get_param");
      var7 = new PyObject[]{PyString.fromInterned("filename"), PyString.fromInterned("content-disposition")};
      var4 = new String[]{"header"};
      var10002 = var10002.__call__(var2, var7, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("audiotest.au"));
      var1.setline(928);
      PyList var8 = new PyList(Py.EmptyObjects);
      var1.setlocal(3, var8);
      var3 = null;
      var1.setline(929);
      var10000 = var1.getlocal(1);
      var10002 = var1.getlocal(0).__getattr__("_au").__getattr__("get_param");
      var7 = new PyObject[]{PyString.fromInterned("attachment"), PyString.fromInterned("content-disposition")};
      var4 = new String[]{"header"};
      var10002 = var10002.__call__(var2, var7, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned(""));
      var1.setline(930);
      var10000 = var1.getlocal(2);
      var10002 = var1.getlocal(0).__getattr__("_au").__getattr__("get_param");
      PyObject[] var5 = new PyObject[]{PyString.fromInterned("foo"), var1.getlocal(3), PyString.fromInterned("content-disposition")};
      String[] var6 = new String[]{"failobj", "header"};
      var10002 = var10002.__call__(var2, var5, var6);
      var5 = null;
      var3 = var10002;
      var10002 = var3._is(var1.getlocal(3));
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.setline(933);
      var10000 = var1.getlocal(2);
      var3 = var1.getlocal(0).__getattr__("_au").__getattr__("get_param").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("foobar"), (PyObject)var1.getlocal(3));
      var10002 = var3._is(var1.getlocal(3));
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.setline(934);
      var10000 = var1.getlocal(2);
      var10002 = var1.getlocal(0).__getattr__("_au").__getattr__("get_param");
      var5 = new PyObject[]{PyString.fromInterned("attachment"), var1.getlocal(3), PyString.fromInterned("foobar")};
      var6 = new String[]{"header"};
      var10002 = var10002.__call__(var2, var5, var6);
      var5 = null;
      var3 = var10002;
      var10002 = var3._is(var1.getlocal(3));
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestMIMEImage$93(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(941);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, setUp$94, (PyObject)null);
      var1.setlocal("setUp", var4);
      var3 = null;
      var1.setline(949);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_guess_minor_type$95, (PyObject)null);
      var1.setlocal("test_guess_minor_type", var4);
      var3 = null;
      var1.setline(952);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_encoding$96, (PyObject)null);
      var1.setlocal("test_encoding", var4);
      var3 = null;
      var1.setline(956);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_checkSetMinor$97, (PyObject)null);
      var1.setlocal("test_checkSetMinor", var4);
      var3 = null;
      var1.setline(960);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_add_header$98, (PyObject)null);
      var1.setlocal("test_add_header", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject setUp$94(PyFrame var1, ThreadState var2) {
      var1.setline(942);
      PyObject var3 = var1.getglobal("openfile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("PyBanner048.gif"));
      var1.setlocal(1, var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(944);
         PyObject var4 = var1.getlocal(1).__getattr__("read").__call__(var2);
         var1.getlocal(0).__setattr__("_imgdata", var4);
         var4 = null;
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(946);
         var1.getlocal(1).__getattr__("close").__call__(var2);
         throw (Throwable)var5;
      }

      var1.setline(946);
      var1.getlocal(1).__getattr__("close").__call__(var2);
      var1.setline(947);
      var3 = var1.getglobal("MIMEImage").__call__(var2, var1.getlocal(0).__getattr__("_imgdata"));
      var1.getlocal(0).__setattr__("_im", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_guess_minor_type$95(PyFrame var1, ThreadState var2) {
      var1.setline(950);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("_im").__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("image/gif"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_encoding$96(PyFrame var1, ThreadState var2) {
      var1.setline(953);
      PyObject var3 = var1.getlocal(0).__getattr__("_im").__getattr__("get_payload").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(954);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getglobal("base64").__getattr__("decodestring").__call__(var2, var1.getlocal(1)), var1.getlocal(0).__getattr__("_imgdata"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_checkSetMinor$97(PyFrame var1, ThreadState var2) {
      var1.setline(957);
      PyObject var3 = var1.getglobal("MIMEImage").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("_imgdata"), (PyObject)PyString.fromInterned("fish"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(958);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("image/fish"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_add_header$98(PyFrame var1, ThreadState var2) {
      var1.setline(961);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(962);
      var3 = var1.getlocal(0).__getattr__("assertTrue");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(963);
      PyObject var10000 = var1.getlocal(0).__getattr__("_im").__getattr__("add_header");
      PyObject[] var7 = new PyObject[]{PyString.fromInterned("Content-Disposition"), PyString.fromInterned("attachment"), PyString.fromInterned("dingusfish.gif")};
      String[] var4 = new String[]{"filename"};
      var10000.__call__(var2, var7, var4);
      var3 = null;
      var1.setline(965);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("_im").__getitem__(PyString.fromInterned("content-disposition")), (PyObject)PyString.fromInterned("attachment; filename=\"dingusfish.gif\""));
      var1.setline(967);
      var10000 = var1.getlocal(1);
      PyObject var10002 = var1.getlocal(0).__getattr__("_im").__getattr__("get_params");
      var7 = new PyObject[]{PyString.fromInterned("content-disposition")};
      var4 = new String[]{"header"};
      var10002 = var10002.__call__(var2, var7, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("attachment"), PyString.fromInterned("")}), new PyTuple(new PyObject[]{PyString.fromInterned("filename"), PyString.fromInterned("dingusfish.gif")})})));
      var1.setline(969);
      var10000 = var1.getlocal(1);
      var10002 = var1.getlocal(0).__getattr__("_im").__getattr__("get_param");
      var7 = new PyObject[]{PyString.fromInterned("filename"), PyString.fromInterned("content-disposition")};
      var4 = new String[]{"header"};
      var10002 = var10002.__call__(var2, var7, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("dingusfish.gif"));
      var1.setline(971);
      PyList var8 = new PyList(Py.EmptyObjects);
      var1.setlocal(3, var8);
      var3 = null;
      var1.setline(972);
      var10000 = var1.getlocal(1);
      var10002 = var1.getlocal(0).__getattr__("_im").__getattr__("get_param");
      var7 = new PyObject[]{PyString.fromInterned("attachment"), PyString.fromInterned("content-disposition")};
      var4 = new String[]{"header"};
      var10002 = var10002.__call__(var2, var7, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned(""));
      var1.setline(973);
      var10000 = var1.getlocal(2);
      var10002 = var1.getlocal(0).__getattr__("_im").__getattr__("get_param");
      PyObject[] var5 = new PyObject[]{PyString.fromInterned("foo"), var1.getlocal(3), PyString.fromInterned("content-disposition")};
      String[] var6 = new String[]{"failobj", "header"};
      var10002 = var10002.__call__(var2, var5, var6);
      var5 = null;
      var3 = var10002;
      var10002 = var3._is(var1.getlocal(3));
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.setline(976);
      var10000 = var1.getlocal(2);
      var3 = var1.getlocal(0).__getattr__("_im").__getattr__("get_param").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("foobar"), (PyObject)var1.getlocal(3));
      var10002 = var3._is(var1.getlocal(3));
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.setline(977);
      var10000 = var1.getlocal(2);
      var10002 = var1.getlocal(0).__getattr__("_im").__getattr__("get_param");
      var5 = new PyObject[]{PyString.fromInterned("attachment"), var1.getlocal(3), PyString.fromInterned("foobar")};
      var6 = new String[]{"header"};
      var10002 = var10002.__call__(var2, var5, var6);
      var5 = null;
      var3 = var10002;
      var10002 = var3._is(var1.getlocal(3));
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestMIMEApplication$99(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(984);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_headers$100, (PyObject)null);
      var1.setlocal("test_headers", var4);
      var3 = null;
      var1.setline(990);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_body$101, (PyObject)null);
      var1.setlocal("test_body", var4);
      var3 = null;
      var1.setline(997);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_binary_body_with_encode_7or8bit$102, (PyObject)null);
      var1.setlocal("test_binary_body_with_encode_7or8bit", var4);
      var3 = null;
      var1.setline(1014);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_binary_body_with_encode_noop$103, (PyObject)null);
      var1.setlocal("test_binary_body_with_encode_noop", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_headers$100(PyFrame var1, ThreadState var2) {
      var1.setline(985);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(986);
      var3 = var1.getglobal("MIMEApplication").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("úûüýþÿ"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(987);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("application/octet-stream"));
      var1.setline(988);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getitem__(PyString.fromInterned("content-transfer-encoding")), (PyObject)PyString.fromInterned("base64"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_body$101(PyFrame var1, ThreadState var2) {
      var1.setline(991);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(992);
      PyString var5 = PyString.fromInterned("úûüýþÿ");
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(993);
      var3 = var1.getglobal("MIMEApplication").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(994);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("get_payload").__call__(var2), (PyObject)PyString.fromInterned("+vv8/f7/"));
      var1.setline(995);
      PyObject var10000 = var1.getlocal(1);
      PyObject var10002 = var1.getlocal(3).__getattr__("get_payload");
      PyObject[] var6 = new PyObject[]{var1.getglobal("True")};
      String[] var4 = new String[]{"decode"};
      var10002 = var10002.__call__(var2, var6, var4);
      var3 = null;
      var10000.__call__(var2, var10002, var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_binary_body_with_encode_7or8bit$102(PyFrame var1, ThreadState var2) {
      var1.setline(999);
      PyString var3 = PyString.fromInterned("úûüýþÿ");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1000);
      PyObject var10000 = var1.getglobal("MIMEApplication");
      PyObject[] var5 = new PyObject[]{var1.getlocal(1), var1.getglobal("encoders").__getattr__("encode_7or8bit")};
      String[] var4 = new String[]{"_encoder"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      PyObject var6 = var10000;
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(1002);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(2).__getattr__("get_payload").__call__(var2), var1.getlocal(1));
      var1.setline(1003);
      var10000 = var1.getlocal(0).__getattr__("assertEqual");
      PyObject var10002 = var1.getlocal(2).__getattr__("get_payload");
      var5 = new PyObject[]{var1.getglobal("True")};
      var4 = new String[]{"decode"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__(var2, var10002, var1.getlocal(1));
      var1.setline(1004);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getitem__(PyString.fromInterned("Content-Transfer-Encoding")), (PyObject)PyString.fromInterned("8bit"));
      var1.setline(1005);
      var6 = var1.getglobal("StringIO").__call__(var2);
      var1.setlocal(3, var6);
      var3 = null;
      var1.setline(1006);
      var6 = var1.getglobal("Generator").__call__(var2, var1.getlocal(3));
      var1.setlocal(4, var6);
      var3 = null;
      var1.setline(1007);
      var1.getlocal(4).__getattr__("flatten").__call__(var2, var1.getlocal(2));
      var1.setline(1008);
      var6 = var1.getlocal(3).__getattr__("getvalue").__call__(var2);
      var1.setlocal(5, var6);
      var3 = null;
      var1.setline(1009);
      var6 = var1.getglobal("email").__getattr__("message_from_string").__call__(var2, var1.getlocal(5));
      var1.setlocal(6, var6);
      var3 = null;
      var1.setline(1010);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(2).__getattr__("get_payload").__call__(var2), var1.getlocal(1));
      var1.setline(1011);
      var10000 = var1.getlocal(0).__getattr__("assertEqual");
      var10002 = var1.getlocal(6).__getattr__("get_payload");
      var5 = new PyObject[]{var1.getglobal("True")};
      var4 = new String[]{"decode"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__(var2, var10002, var1.getlocal(1));
      var1.setline(1012);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(6).__getitem__(PyString.fromInterned("Content-Transfer-Encoding")), (PyObject)PyString.fromInterned("8bit"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_binary_body_with_encode_noop$103(PyFrame var1, ThreadState var2) {
      var1.setline(1018);
      PyString var3 = PyString.fromInterned("úûüýþÿ");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1019);
      PyObject var10000 = var1.getglobal("MIMEApplication");
      PyObject[] var5 = new PyObject[]{var1.getlocal(1), var1.getglobal("encoders").__getattr__("encode_noop")};
      String[] var4 = new String[]{"_encoder"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      PyObject var6 = var10000;
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(1020);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(2).__getattr__("get_payload").__call__(var2), var1.getlocal(1));
      var1.setline(1021);
      var10000 = var1.getlocal(0).__getattr__("assertEqual");
      PyObject var10002 = var1.getlocal(2).__getattr__("get_payload");
      var5 = new PyObject[]{var1.getglobal("True")};
      var4 = new String[]{"decode"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__(var2, var10002, var1.getlocal(1));
      var1.setline(1022);
      var6 = var1.getglobal("StringIO").__call__(var2);
      var1.setlocal(3, var6);
      var3 = null;
      var1.setline(1023);
      var6 = var1.getglobal("Generator").__call__(var2, var1.getlocal(3));
      var1.setlocal(4, var6);
      var3 = null;
      var1.setline(1024);
      var1.getlocal(4).__getattr__("flatten").__call__(var2, var1.getlocal(2));
      var1.setline(1025);
      var6 = var1.getlocal(3).__getattr__("getvalue").__call__(var2);
      var1.setlocal(5, var6);
      var3 = null;
      var1.setline(1026);
      var6 = var1.getglobal("email").__getattr__("message_from_string").__call__(var2, var1.getlocal(5));
      var1.setlocal(6, var6);
      var3 = null;
      var1.setline(1027);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(2).__getattr__("get_payload").__call__(var2), var1.getlocal(1));
      var1.setline(1028);
      var10000 = var1.getlocal(0).__getattr__("assertEqual");
      var10002 = var1.getlocal(6).__getattr__("get_payload");
      var5 = new PyObject[]{var1.getglobal("True")};
      var4 = new String[]{"decode"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__(var2, var10002, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestMIMEText$104(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1033);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, setUp$105, (PyObject)null);
      var1.setlocal("setUp", var4);
      var3 = null;
      var1.setline(1036);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_types$106, (PyObject)null);
      var1.setlocal("test_types", var4);
      var3 = null;
      var1.setline(1046);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_payload$107, (PyObject)null);
      var1.setlocal("test_payload", var4);
      var3 = null;
      var1.setline(1050);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_charset$108, (PyObject)null);
      var1.setlocal("test_charset", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject setUp$105(PyFrame var1, ThreadState var2) {
      var1.setline(1034);
      PyObject var3 = var1.getglobal("MIMEText").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hello there"));
      var1.getlocal(0).__setattr__("_msg", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_types$106(PyFrame var1, ThreadState var2) {
      var1.setline(1037);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1038);
      var3 = var1.getlocal(0).__getattr__("assertTrue");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1039);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("_msg").__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("text/plain"));
      var1.setline(1040);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("_msg").__getattr__("get_param").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("charset")), (PyObject)PyString.fromInterned("us-ascii"));
      var1.setline(1041);
      PyList var7 = new PyList(Py.EmptyObjects);
      var1.setlocal(3, var7);
      var3 = null;
      var1.setline(1042);
      PyObject var10000 = var1.getlocal(2);
      var3 = var1.getlocal(0).__getattr__("_msg").__getattr__("get_param").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("foobar"), (PyObject)var1.getlocal(3));
      PyObject var10002 = var3._is(var1.getlocal(3));
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.setline(1043);
      var10000 = var1.getlocal(2);
      var10002 = var1.getlocal(0).__getattr__("_msg").__getattr__("get_param");
      PyObject[] var5 = new PyObject[]{PyString.fromInterned("charset"), var1.getlocal(3), PyString.fromInterned("foobar")};
      String[] var6 = new String[]{"header"};
      var10002 = var10002.__call__(var2, var5, var6);
      var5 = null;
      var3 = var10002;
      var10002 = var3._is(var1.getlocal(3));
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_payload$107(PyFrame var1, ThreadState var2) {
      var1.setline(1047);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("_msg").__getattr__("get_payload").__call__(var2), (PyObject)PyString.fromInterned("hello there"));
      var1.setline(1048);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getlocal(0).__getattr__("_msg").__getattr__("is_multipart").__call__(var2).__not__());
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_charset$108(PyFrame var1, ThreadState var2) {
      var1.setline(1051);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1052);
      PyObject var10000 = var1.getglobal("MIMEText");
      PyObject[] var5 = new PyObject[]{PyString.fromInterned("hello there"), PyString.fromInterned("us-ascii")};
      String[] var4 = new String[]{"_charset"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1053);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_charset").__call__(var2).__getattr__("input_charset"), (PyObject)PyString.fromInterned("us-ascii"));
      var1.setline(1054);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getitem__(PyString.fromInterned("content-type")), (PyObject)PyString.fromInterned("text/plain; charset=\"us-ascii\""));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestMultipart$109(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1060);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, setUp$110, (PyObject)null);
      var1.setlocal("setUp", var4);
      var3 = null;
      var1.setline(1100);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_hierarchy$111, (PyObject)null);
      var1.setlocal("test_hierarchy", var4);
      var3 = null;
      var1.setline(1119);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_empty_multipart_idempotent$112, (PyObject)null);
      var1.setlocal("test_empty_multipart_idempotent", var4);
      var3 = null;
      var1.setline(1136);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_no_parts_in_a_multipart_with_none_epilogue$113, (PyObject)null);
      var1.setlocal("test_no_parts_in_a_multipart_with_none_epilogue", var4);
      var3 = null;
      var1.setline(1153);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_no_parts_in_a_multipart_with_empty_epilogue$114, (PyObject)null);
      var1.setlocal("test_no_parts_in_a_multipart_with_empty_epilogue", var4);
      var3 = null;
      var1.setline(1174);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_one_part_in_a_multipart$115, (PyObject)null);
      var1.setlocal("test_one_part_in_a_multipart", var4);
      var3 = null;
      var1.setline(1198);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_seq_parts_in_a_multipart_with_empty_preamble$116, (PyObject)null);
      var1.setlocal("test_seq_parts_in_a_multipart_with_empty_preamble", var4);
      var3 = null;
      var1.setline(1225);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_seq_parts_in_a_multipart_with_none_preamble$117, (PyObject)null);
      var1.setlocal("test_seq_parts_in_a_multipart_with_none_preamble", var4);
      var3 = null;
      var1.setline(1251);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_seq_parts_in_a_multipart_with_none_epilogue$118, (PyObject)null);
      var1.setlocal("test_seq_parts_in_a_multipart_with_none_epilogue", var4);
      var3 = null;
      var1.setline(1277);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_seq_parts_in_a_multipart_with_empty_epilogue$119, (PyObject)null);
      var1.setlocal("test_seq_parts_in_a_multipart_with_empty_epilogue", var4);
      var3 = null;
      var1.setline(1304);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_seq_parts_in_a_multipart_with_nl_epilogue$120, (PyObject)null);
      var1.setlocal("test_seq_parts_in_a_multipart_with_nl_epilogue", var4);
      var3 = null;
      var1.setline(1331);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_message_external_body$121, (PyObject)null);
      var1.setlocal("test_message_external_body", var4);
      var3 = null;
      var1.setline(1344);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_double_boundary$122, (PyObject)null);
      var1.setlocal("test_double_boundary", var4);
      var3 = null;
      var1.setline(1351);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_nested_inner_contains_outer_boundary$123, (PyObject)null);
      var1.setlocal("test_nested_inner_contains_outer_boundary", var4);
      var3 = null;
      var1.setline(1370);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_nested_with_same_boundary$124, (PyObject)null);
      var1.setlocal("test_nested_with_same_boundary", var4);
      var3 = null;
      var1.setline(1387);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_boundary_in_non_multipart$125, (PyObject)null);
      var1.setlocal("test_boundary_in_non_multipart", var4);
      var3 = null;
      var1.setline(1402);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_boundary_with_leading_space$126, (PyObject)null);
      var1.setlocal("test_boundary_with_leading_space", var4);
      var3 = null;
      var1.setline(1421);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_boundary_without_trailing_newline$127, (PyObject)null);
      var1.setlocal("test_boundary_without_trailing_newline", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject setUp$110(PyFrame var1, ThreadState var2) {
      var1.setline(1061);
      PyObject var3 = var1.getglobal("openfile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("PyBanner048.gif"));
      var1.setlocal(1, var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(1063);
         PyObject var4 = var1.getlocal(1).__getattr__("read").__call__(var2);
         var1.setlocal(2, var4);
         var4 = null;
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(1065);
         var1.getlocal(1).__getattr__("close").__call__(var2);
         throw (Throwable)var5;
      }

      var1.setline(1065);
      var1.getlocal(1).__getattr__("close").__call__(var2);
      var1.setline(1067);
      PyObject var10000 = var1.getglobal("MIMEBase");
      PyObject[] var6 = new PyObject[]{PyString.fromInterned("multipart"), PyString.fromInterned("mixed"), PyString.fromInterned("BOUNDARY")};
      String[] var7 = new String[]{"boundary"};
      var10000 = var10000.__call__(var2, var6, var7);
      var3 = null;
      var3 = var10000;
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1068);
      var10000 = var1.getglobal("MIMEImage");
      var6 = new PyObject[]{var1.getlocal(2), PyString.fromInterned("dingusfish.gif")};
      var7 = new String[]{"name"};
      var10000 = var10000.__call__(var2, var6, var7);
      var3 = null;
      var3 = var10000;
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1069);
      var10000 = var1.getlocal(4).__getattr__("add_header");
      var6 = new PyObject[]{PyString.fromInterned("content-disposition"), PyString.fromInterned("attachment"), PyString.fromInterned("dingusfish.gif")};
      var7 = new String[]{"filename"};
      var10000.__call__(var2, var6, var7);
      var3 = null;
      var1.setline(1071);
      var3 = var1.getglobal("MIMEText").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Hi there,\n\nThis is the dingus fish.\n"));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(1076);
      var1.getlocal(3).__getattr__("attach").__call__(var2, var1.getlocal(5));
      var1.setline(1077);
      var1.getlocal(3).__getattr__("attach").__call__(var2, var1.getlocal(4));
      var1.setline(1078);
      PyString var8 = PyString.fromInterned("Barry <barry@digicool.com>");
      var1.getlocal(3).__setitem__((PyObject)PyString.fromInterned("From"), var8);
      var3 = null;
      var1.setline(1079);
      var8 = PyString.fromInterned("Dingus Lovers <cravindogs@cravindogs.com>");
      var1.getlocal(3).__setitem__((PyObject)PyString.fromInterned("To"), var8);
      var3 = null;
      var1.setline(1080);
      var8 = PyString.fromInterned("Here is your dingus fish");
      var1.getlocal(3).__setitem__((PyObject)PyString.fromInterned("Subject"), var8);
      var3 = null;
      var1.setline(1082);
      PyFloat var9 = Py.newFloat(9.87809702548486E8);
      var1.setlocal(6, var9);
      var3 = null;
      var1.setline(1083);
      var3 = var1.getglobal("time").__getattr__("localtime").__call__(var2, var1.getlocal(6));
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(1084);
      var3 = var1.getlocal(7).__getitem__(Py.newInteger(-1));
      var10000 = var3._eq(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1085);
         var3 = var1.getglobal("time").__getattr__("timezone");
         var1.setlocal(8, var3);
         var3 = null;
      } else {
         var1.setline(1087);
         var3 = var1.getglobal("time").__getattr__("altzone");
         var1.setlocal(8, var3);
         var3 = null;
      }

      var1.setline(1088);
      var3 = var1.getlocal(8);
      var10000 = var3._gt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1089);
         var8 = PyString.fromInterned("-");
         var1.setlocal(9, var8);
         var3 = null;
      } else {
         var1.setline(1091);
         var8 = PyString.fromInterned("+");
         var1.setlocal(9, var8);
         var3 = null;
      }

      var1.setline(1092);
      var3 = PyString.fromInterned(" %s%04d")._mod(new PyTuple(new PyObject[]{var1.getlocal(9), var1.getlocal(8)._floordiv(Py.newInteger(36))}));
      var1.setlocal(10, var3);
      var3 = null;
      var1.setline(1093);
      var3 = var1.getglobal("time").__getattr__("strftime").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("%a, %d %b %Y %H:%M:%S"), (PyObject)var1.getglobal("time").__getattr__("localtime").__call__(var2, var1.getlocal(6)))._add(var1.getlocal(10));
      var1.getlocal(3).__setitem__((PyObject)PyString.fromInterned("Date"), var3);
      var3 = null;
      var1.setline(1096);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("_msg", var3);
      var3 = null;
      var1.setline(1097);
      var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("_im", var3);
      var3 = null;
      var1.setline(1098);
      var3 = var1.getlocal(5);
      var1.getlocal(0).__setattr__("_txt", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_hierarchy$111(PyFrame var1, ThreadState var2) {
      var1.setline(1102);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1103);
      var3 = var1.getlocal(0).__getattr__("assertTrue");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1104);
      var3 = var1.getlocal(0).__getattr__("assertRaises");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1106);
      var3 = var1.getlocal(0).__getattr__("_msg");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1107);
      var1.getlocal(2).__call__(var2, var1.getlocal(4).__getattr__("is_multipart").__call__(var2));
      var1.setline(1108);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(4).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("multipart/mixed"));
      var1.setline(1109);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(4).__getattr__("get_payload").__call__(var2)), (PyObject)Py.newInteger(2));
      var1.setline(1110);
      var1.getlocal(3).__call__((ThreadState)var2, var1.getglobal("IndexError"), (PyObject)var1.getlocal(4).__getattr__("get_payload"), (PyObject)Py.newInteger(2));
      var1.setline(1111);
      var3 = var1.getlocal(4).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(1112);
      var3 = var1.getlocal(4).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(1113);
      PyObject var10000 = var1.getlocal(2);
      var3 = var1.getlocal(5);
      PyObject var10002 = var3._is(var1.getlocal(0).__getattr__("_txt"));
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.setline(1114);
      var10000 = var1.getlocal(2);
      var3 = var1.getlocal(6);
      var10002 = var3._is(var1.getlocal(0).__getattr__("_im"));
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.setline(1115);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(4).__getattr__("get_payload").__call__(var2), (PyObject)(new PyList(new PyObject[]{var1.getlocal(5), var1.getlocal(6)})));
      var1.setline(1116);
      var1.getlocal(2).__call__(var2, var1.getlocal(5).__getattr__("is_multipart").__call__(var2).__not__());
      var1.setline(1117);
      var1.getlocal(2).__call__(var2, var1.getlocal(6).__getattr__("is_multipart").__call__(var2).__not__());
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_empty_multipart_idempotent$112(PyFrame var1, ThreadState var2) {
      var1.setline(1120);
      PyString var3 = PyString.fromInterned("Content-Type: multipart/mixed; boundary=\"BOUNDARY\"\nMIME-Version: 1.0\nSubject: A subject\nTo: aperson@dom.ain\nFrom: bperson@dom.ain\n\n\n--BOUNDARY\n\n\n--BOUNDARY--\n");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1133);
      PyObject var4 = var1.getglobal("Parser").__call__(var2).__getattr__("parsestr").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(1134);
      var1.getlocal(0).__getattr__("ndiffAssertEqual").__call__(var2, var1.getlocal(1), var1.getlocal(2).__getattr__("as_string").__call__(var2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_no_parts_in_a_multipart_with_none_epilogue$113(PyFrame var1, ThreadState var2) {
      var1.setline(1137);
      PyObject var3 = var1.getglobal("MIMEBase").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("multipart"), (PyObject)PyString.fromInterned("mixed"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1138);
      PyString var4 = PyString.fromInterned("A subject");
      var1.getlocal(1).__setitem__((PyObject)PyString.fromInterned("Subject"), var4);
      var3 = null;
      var1.setline(1139);
      var4 = PyString.fromInterned("aperson@dom.ain");
      var1.getlocal(1).__setitem__((PyObject)PyString.fromInterned("To"), var4);
      var3 = null;
      var1.setline(1140);
      var4 = PyString.fromInterned("bperson@dom.ain");
      var1.getlocal(1).__setitem__((PyObject)PyString.fromInterned("From"), var4);
      var3 = null;
      var1.setline(1141);
      var1.getlocal(1).__getattr__("set_boundary").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("BOUNDARY"));
      var1.setline(1142);
      var1.getlocal(0).__getattr__("ndiffAssertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("as_string").__call__(var2), (PyObject)PyString.fromInterned("Content-Type: multipart/mixed; boundary=\"BOUNDARY\"\nMIME-Version: 1.0\nSubject: A subject\nTo: aperson@dom.ain\nFrom: bperson@dom.ain\n\n--BOUNDARY\n\n--BOUNDARY--"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_no_parts_in_a_multipart_with_empty_epilogue$114(PyFrame var1, ThreadState var2) {
      var1.setline(1154);
      PyObject var3 = var1.getglobal("MIMEBase").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("multipart"), (PyObject)PyString.fromInterned("mixed"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1155);
      PyString var4 = PyString.fromInterned("A subject");
      var1.getlocal(1).__setitem__((PyObject)PyString.fromInterned("Subject"), var4);
      var3 = null;
      var1.setline(1156);
      var4 = PyString.fromInterned("aperson@dom.ain");
      var1.getlocal(1).__setitem__((PyObject)PyString.fromInterned("To"), var4);
      var3 = null;
      var1.setline(1157);
      var4 = PyString.fromInterned("bperson@dom.ain");
      var1.getlocal(1).__setitem__((PyObject)PyString.fromInterned("From"), var4);
      var3 = null;
      var1.setline(1158);
      var4 = PyString.fromInterned("");
      var1.getlocal(1).__setattr__((String)"preamble", var4);
      var3 = null;
      var1.setline(1159);
      var4 = PyString.fromInterned("");
      var1.getlocal(1).__setattr__((String)"epilogue", var4);
      var3 = null;
      var1.setline(1160);
      var1.getlocal(1).__getattr__("set_boundary").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("BOUNDARY"));
      var1.setline(1161);
      var1.getlocal(0).__getattr__("ndiffAssertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("as_string").__call__(var2), (PyObject)PyString.fromInterned("Content-Type: multipart/mixed; boundary=\"BOUNDARY\"\nMIME-Version: 1.0\nSubject: A subject\nTo: aperson@dom.ain\nFrom: bperson@dom.ain\n\n\n--BOUNDARY\n\n--BOUNDARY--\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_one_part_in_a_multipart$115(PyFrame var1, ThreadState var2) {
      var1.setline(1175);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1176);
      var3 = var1.getglobal("MIMEBase").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("multipart"), (PyObject)PyString.fromInterned("mixed"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1177);
      PyString var4 = PyString.fromInterned("A subject");
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("Subject"), var4);
      var3 = null;
      var1.setline(1178);
      var4 = PyString.fromInterned("aperson@dom.ain");
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("To"), var4);
      var3 = null;
      var1.setline(1179);
      var4 = PyString.fromInterned("bperson@dom.ain");
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("From"), var4);
      var3 = null;
      var1.setline(1180);
      var1.getlocal(2).__getattr__("set_boundary").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("BOUNDARY"));
      var1.setline(1181);
      var3 = var1.getglobal("MIMEText").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hello world"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1182);
      var1.getlocal(2).__getattr__("attach").__call__(var2, var1.getlocal(3));
      var1.setline(1183);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("as_string").__call__(var2), (PyObject)PyString.fromInterned("Content-Type: multipart/mixed; boundary=\"BOUNDARY\"\nMIME-Version: 1.0\nSubject: A subject\nTo: aperson@dom.ain\nFrom: bperson@dom.ain\n\n--BOUNDARY\nContent-Type: text/plain; charset=\"us-ascii\"\nMIME-Version: 1.0\nContent-Transfer-Encoding: 7bit\n\nhello world\n--BOUNDARY--"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_seq_parts_in_a_multipart_with_empty_preamble$116(PyFrame var1, ThreadState var2) {
      var1.setline(1199);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1200);
      var3 = var1.getglobal("MIMEBase").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("multipart"), (PyObject)PyString.fromInterned("mixed"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1201);
      PyString var4 = PyString.fromInterned("A subject");
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("Subject"), var4);
      var3 = null;
      var1.setline(1202);
      var4 = PyString.fromInterned("aperson@dom.ain");
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("To"), var4);
      var3 = null;
      var1.setline(1203);
      var4 = PyString.fromInterned("bperson@dom.ain");
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("From"), var4);
      var3 = null;
      var1.setline(1204);
      var4 = PyString.fromInterned("");
      var1.getlocal(2).__setattr__((String)"preamble", var4);
      var3 = null;
      var1.setline(1205);
      var3 = var1.getglobal("MIMEText").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hello world"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1206);
      var1.getlocal(2).__getattr__("attach").__call__(var2, var1.getlocal(3));
      var1.setline(1207);
      var1.getlocal(2).__getattr__("set_boundary").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("BOUNDARY"));
      var1.setline(1208);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("as_string").__call__(var2), (PyObject)PyString.fromInterned("Content-Type: multipart/mixed; boundary=\"BOUNDARY\"\nMIME-Version: 1.0\nSubject: A subject\nTo: aperson@dom.ain\nFrom: bperson@dom.ain\n\n\n--BOUNDARY\nContent-Type: text/plain; charset=\"us-ascii\"\nMIME-Version: 1.0\nContent-Transfer-Encoding: 7bit\n\nhello world\n--BOUNDARY--"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_seq_parts_in_a_multipart_with_none_preamble$117(PyFrame var1, ThreadState var2) {
      var1.setline(1226);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1227);
      var3 = var1.getglobal("MIMEBase").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("multipart"), (PyObject)PyString.fromInterned("mixed"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1228);
      PyString var4 = PyString.fromInterned("A subject");
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("Subject"), var4);
      var3 = null;
      var1.setline(1229);
      var4 = PyString.fromInterned("aperson@dom.ain");
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("To"), var4);
      var3 = null;
      var1.setline(1230);
      var4 = PyString.fromInterned("bperson@dom.ain");
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("From"), var4);
      var3 = null;
      var1.setline(1231);
      var3 = var1.getglobal("None");
      var1.getlocal(2).__setattr__("preamble", var3);
      var3 = null;
      var1.setline(1232);
      var3 = var1.getglobal("MIMEText").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hello world"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1233);
      var1.getlocal(2).__getattr__("attach").__call__(var2, var1.getlocal(3));
      var1.setline(1234);
      var1.getlocal(2).__getattr__("set_boundary").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("BOUNDARY"));
      var1.setline(1235);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("as_string").__call__(var2), (PyObject)PyString.fromInterned("Content-Type: multipart/mixed; boundary=\"BOUNDARY\"\nMIME-Version: 1.0\nSubject: A subject\nTo: aperson@dom.ain\nFrom: bperson@dom.ain\n\n--BOUNDARY\nContent-Type: text/plain; charset=\"us-ascii\"\nMIME-Version: 1.0\nContent-Transfer-Encoding: 7bit\n\nhello world\n--BOUNDARY--"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_seq_parts_in_a_multipart_with_none_epilogue$118(PyFrame var1, ThreadState var2) {
      var1.setline(1252);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1253);
      var3 = var1.getglobal("MIMEBase").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("multipart"), (PyObject)PyString.fromInterned("mixed"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1254);
      PyString var4 = PyString.fromInterned("A subject");
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("Subject"), var4);
      var3 = null;
      var1.setline(1255);
      var4 = PyString.fromInterned("aperson@dom.ain");
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("To"), var4);
      var3 = null;
      var1.setline(1256);
      var4 = PyString.fromInterned("bperson@dom.ain");
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("From"), var4);
      var3 = null;
      var1.setline(1257);
      var3 = var1.getglobal("None");
      var1.getlocal(2).__setattr__("epilogue", var3);
      var3 = null;
      var1.setline(1258);
      var3 = var1.getglobal("MIMEText").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hello world"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1259);
      var1.getlocal(2).__getattr__("attach").__call__(var2, var1.getlocal(3));
      var1.setline(1260);
      var1.getlocal(2).__getattr__("set_boundary").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("BOUNDARY"));
      var1.setline(1261);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("as_string").__call__(var2), (PyObject)PyString.fromInterned("Content-Type: multipart/mixed; boundary=\"BOUNDARY\"\nMIME-Version: 1.0\nSubject: A subject\nTo: aperson@dom.ain\nFrom: bperson@dom.ain\n\n--BOUNDARY\nContent-Type: text/plain; charset=\"us-ascii\"\nMIME-Version: 1.0\nContent-Transfer-Encoding: 7bit\n\nhello world\n--BOUNDARY--"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_seq_parts_in_a_multipart_with_empty_epilogue$119(PyFrame var1, ThreadState var2) {
      var1.setline(1278);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1279);
      var3 = var1.getglobal("MIMEBase").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("multipart"), (PyObject)PyString.fromInterned("mixed"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1280);
      PyString var4 = PyString.fromInterned("A subject");
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("Subject"), var4);
      var3 = null;
      var1.setline(1281);
      var4 = PyString.fromInterned("aperson@dom.ain");
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("To"), var4);
      var3 = null;
      var1.setline(1282);
      var4 = PyString.fromInterned("bperson@dom.ain");
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("From"), var4);
      var3 = null;
      var1.setline(1283);
      var4 = PyString.fromInterned("");
      var1.getlocal(2).__setattr__((String)"epilogue", var4);
      var3 = null;
      var1.setline(1284);
      var3 = var1.getglobal("MIMEText").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hello world"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1285);
      var1.getlocal(2).__getattr__("attach").__call__(var2, var1.getlocal(3));
      var1.setline(1286);
      var1.getlocal(2).__getattr__("set_boundary").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("BOUNDARY"));
      var1.setline(1287);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("as_string").__call__(var2), (PyObject)PyString.fromInterned("Content-Type: multipart/mixed; boundary=\"BOUNDARY\"\nMIME-Version: 1.0\nSubject: A subject\nTo: aperson@dom.ain\nFrom: bperson@dom.ain\n\n--BOUNDARY\nContent-Type: text/plain; charset=\"us-ascii\"\nMIME-Version: 1.0\nContent-Transfer-Encoding: 7bit\n\nhello world\n--BOUNDARY--\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_seq_parts_in_a_multipart_with_nl_epilogue$120(PyFrame var1, ThreadState var2) {
      var1.setline(1305);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1306);
      var3 = var1.getglobal("MIMEBase").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("multipart"), (PyObject)PyString.fromInterned("mixed"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1307);
      PyString var4 = PyString.fromInterned("A subject");
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("Subject"), var4);
      var3 = null;
      var1.setline(1308);
      var4 = PyString.fromInterned("aperson@dom.ain");
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("To"), var4);
      var3 = null;
      var1.setline(1309);
      var4 = PyString.fromInterned("bperson@dom.ain");
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("From"), var4);
      var3 = null;
      var1.setline(1310);
      var4 = PyString.fromInterned("\n");
      var1.getlocal(2).__setattr__((String)"epilogue", var4);
      var3 = null;
      var1.setline(1311);
      var3 = var1.getglobal("MIMEText").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hello world"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1312);
      var1.getlocal(2).__getattr__("attach").__call__(var2, var1.getlocal(3));
      var1.setline(1313);
      var1.getlocal(2).__getattr__("set_boundary").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("BOUNDARY"));
      var1.setline(1314);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("as_string").__call__(var2), (PyObject)PyString.fromInterned("Content-Type: multipart/mixed; boundary=\"BOUNDARY\"\nMIME-Version: 1.0\nSubject: A subject\nTo: aperson@dom.ain\nFrom: bperson@dom.ain\n\n--BOUNDARY\nContent-Type: text/plain; charset=\"us-ascii\"\nMIME-Version: 1.0\nContent-Transfer-Encoding: 7bit\n\nhello world\n--BOUNDARY--\n\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_message_external_body$121(PyFrame var1, ThreadState var2) {
      var1.setline(1332);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1333);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_36.txt"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1334);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(2).__getattr__("get_payload").__call__(var2)), (PyObject)Py.newInteger(2));
      var1.setline(1335);
      var3 = var1.getlocal(2).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1336);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("multipart/alternative"));
      var1.setline(1337);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(3).__getattr__("get_payload").__call__(var2)), (PyObject)Py.newInteger(2));
      var1.setline(1338);
      var3 = var1.getlocal(3).__getattr__("get_payload").__call__(var2).__iter__();

      while(true) {
         var1.setline(1338);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(4, var4);
         var1.setline(1339);
         var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(4).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("message/external-body"));
         var1.setline(1340);
         var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(4).__getattr__("get_payload").__call__(var2)), (PyObject)Py.newInteger(1));
         var1.setline(1341);
         PyObject var5 = var1.getlocal(4).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
         var1.setlocal(5, var5);
         var5 = null;
         var1.setline(1342);
         var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(5).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("text/plain"));
      }
   }

   public PyObject test_double_boundary$122(PyFrame var1, ThreadState var2) {
      var1.setline(1348);
      PyObject var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_37.txt"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1349);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(1).__getattr__("get_payload").__call__(var2)), (PyObject)Py.newInteger(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_nested_inner_contains_outer_boundary$123(PyFrame var1, ThreadState var2) {
      var1.setline(1352);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1357);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_38.txt"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1358);
      var3 = var1.getglobal("StringIO").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1359);
      var1.getglobal("iterators").__getattr__("_structure").__call__(var2, var1.getlocal(2), var1.getlocal(3));
      var1.setline(1360);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("getvalue").__call__(var2), (PyObject)PyString.fromInterned("multipart/mixed\n    multipart/mixed\n        multipart/alternative\n            text/plain\n        text/plain\n    text/plain\n    text/plain\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_nested_with_same_boundary$124(PyFrame var1, ThreadState var2) {
      var1.setline(1371);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1375);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_39.txt"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1376);
      var3 = var1.getglobal("StringIO").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1377);
      var1.getglobal("iterators").__getattr__("_structure").__call__(var2, var1.getlocal(2), var1.getlocal(3));
      var1.setline(1378);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("getvalue").__call__(var2), (PyObject)PyString.fromInterned("multipart/mixed\n    multipart/mixed\n        multipart/alternative\n        application/octet-stream\n        application/octet-stream\n    text/plain\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_boundary_in_non_multipart$125(PyFrame var1, ThreadState var2) {
      var1.setline(1388);
      PyObject var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_40.txt"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1389);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("as_string").__call__(var2), (PyObject)PyString.fromInterned("MIME-Version: 1.0\nContent-Type: text/html; boundary=\"--961284236552522269\"\n\n----961284236552522269\nContent-Type: text/html;\nContent-Transfer-Encoding: 7Bit\n\n<html></html>\n\n----961284236552522269--\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_boundary_with_leading_space$126(PyFrame var1, ThreadState var2) {
      var1.setline(1403);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1404);
      var3 = var1.getglobal("email").__getattr__("message_from_string").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("MIME-Version: 1.0\nContent-Type: multipart/mixed; boundary=\"    XXXX\"\n\n--    XXXX\nContent-Type: text/plain\n\n\n--    XXXX\nContent-Type: text/plain\n\n--    XXXX--\n"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1417);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getlocal(2).__getattr__("is_multipart").__call__(var2));
      var1.setline(1418);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_boundary").__call__(var2), (PyObject)PyString.fromInterned("    XXXX"));
      var1.setline(1419);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(2).__getattr__("get_payload").__call__(var2)), (PyObject)Py.newInteger(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_boundary_without_trailing_newline$127(PyFrame var1, ThreadState var2) {
      var1.setline(1422);
      PyObject var3 = var1.getglobal("Parser").__call__(var2).__getattr__("parsestr").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Content-Type: multipart/mixed; boundary=\"===============0012394164==\"\nMIME-Version: 1.0\n\n--===============0012394164==\nContent-Type: image/file1.jpg\nMIME-Version: 1.0\nContent-Transfer-Encoding: base64\n\nYXNkZg==\n--===============0012394164==--"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1433);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(0)).__getattr__("get_payload").__call__(var2), (PyObject)PyString.fromInterned("YXNkZg=="));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestNonConformant$128(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1439);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_parse_missing_minor_type$129, (PyObject)null);
      var1.setlocal("test_parse_missing_minor_type", var4);
      var3 = null;
      var1.setline(1446);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_same_boundary_inner_outer$130, (PyObject)null);
      var1.setlocal("test_same_boundary_inner_outer", var4);
      var3 = null;
      var1.setline(1456);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_multipart_no_boundary$131, (PyObject)null);
      var1.setlocal("test_multipart_no_boundary", var4);
      var3 = null;
      var1.setline(1465);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_invalid_content_type$132, (PyObject)null);
      var1.setlocal("test_invalid_content_type", var4);
      var3 = null;
      var1.setline(1486);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_no_start_boundary$133, (PyObject)null);
      var1.setlocal("test_no_start_boundary", var4);
      var3 = null;
      var1.setline(1503);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_no_separating_blank_line$134, (PyObject)null);
      var1.setlocal("test_no_separating_blank_line", var4);
      var3 = null;
      var1.setline(1514);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_lying_multipart$135, (PyObject)null);
      var1.setlocal("test_lying_multipart", var4);
      var3 = null;
      var1.setline(1523);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_missing_start_boundary$136, (PyObject)null);
      var1.setlocal("test_missing_start_boundary", var4);
      var3 = null;
      var1.setline(1538);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_first_line_is_continuation_header$137, (PyObject)null);
      var1.setlocal("test_first_line_is_continuation_header", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_parse_missing_minor_type$129(PyFrame var1, ThreadState var2) {
      var1.setline(1440);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1441);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_14.txt"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1442);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("text/plain"));
      var1.setline(1443);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_content_maintype").__call__(var2), (PyObject)PyString.fromInterned("text"));
      var1.setline(1444);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_content_subtype").__call__(var2), (PyObject)PyString.fromInterned("plain"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_same_boundary_inner_outer$130(PyFrame var1, ThreadState var2) {
      var1.setline(1447);
      PyObject var3 = var1.getlocal(0).__getattr__("assertTrue");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1448);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_15.txt"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1450);
      var3 = var1.getlocal(2).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1451);
      var1.getlocal(1).__call__(var2, var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("defects")));
      var1.setline(1452);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(3).__getattr__("defects")), (PyObject)Py.newInteger(1));
      var1.setline(1453);
      var1.getlocal(1).__call__(var2, var1.getglobal("isinstance").__call__(var2, var1.getlocal(3).__getattr__("defects").__getitem__(Py.newInteger(0)), var1.getglobal("errors").__getattr__("StartBoundaryNotFoundDefect")));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_multipart_no_boundary$131(PyFrame var1, ThreadState var2) {
      var1.setline(1457);
      PyObject var3 = var1.getlocal(0).__getattr__("assertTrue");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1458);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_25.txt"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1459);
      var1.getlocal(1).__call__(var2, var1.getglobal("isinstance").__call__(var2, var1.getlocal(2).__getattr__("get_payload").__call__(var2), var1.getglobal("str")));
      var1.setline(1460);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(2).__getattr__("defects")), (PyObject)Py.newInteger(2));
      var1.setline(1461);
      var1.getlocal(1).__call__(var2, var1.getglobal("isinstance").__call__(var2, var1.getlocal(2).__getattr__("defects").__getitem__(Py.newInteger(0)), var1.getglobal("errors").__getattr__("NoBoundaryInMultipartDefect")));
      var1.setline(1462);
      var1.getlocal(1).__call__(var2, var1.getglobal("isinstance").__call__(var2, var1.getlocal(2).__getattr__("defects").__getitem__(Py.newInteger(1)), var1.getglobal("errors").__getattr__("MultipartInvariantViolationDefect")));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_invalid_content_type$132(PyFrame var1, ThreadState var2) {
      var1.setline(1466);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1467);
      var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1468);
      var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1470);
      PyString var4 = PyString.fromInterned("text");
      var1.getlocal(3).__setitem__((PyObject)PyString.fromInterned("Content-Type"), var4);
      var3 = null;
      var1.setline(1471);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("get_content_maintype").__call__(var2), (PyObject)PyString.fromInterned("text"));
      var1.setline(1472);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("get_content_subtype").__call__(var2), (PyObject)PyString.fromInterned("plain"));
      var1.setline(1473);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("text/plain"));
      var1.setline(1475);
      var1.getlocal(3).__delitem__((PyObject)PyString.fromInterned("content-type"));
      var1.setline(1476);
      var4 = PyString.fromInterned("foo");
      var1.getlocal(3).__setitem__((PyObject)PyString.fromInterned("Content-Type"), var4);
      var3 = null;
      var1.setline(1477);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("get_content_maintype").__call__(var2), (PyObject)PyString.fromInterned("text"));
      var1.setline(1478);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("get_content_subtype").__call__(var2), (PyObject)PyString.fromInterned("plain"));
      var1.setline(1479);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("text/plain"));
      var1.setline(1481);
      var3 = var1.getglobal("StringIO").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1482);
      var3 = var1.getglobal("Generator").__call__(var2, var1.getlocal(4));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(1483);
      var1.getlocal(5).__getattr__("flatten").__call__(var2, var1.getlocal(3));
      var1.setline(1484);
      var1.getlocal(2).__call__((ThreadState)var2, (PyObject)var1.getlocal(4).__getattr__("getvalue").__call__(var2), (PyObject)PyString.fromInterned("Content-Type: foo\n\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_no_start_boundary$133(PyFrame var1, ThreadState var2) {
      var1.setline(1487);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1488);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_31.txt"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1489);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_payload").__call__(var2), (PyObject)PyString.fromInterned("--BOUNDARY\nContent-Type: text/plain\n\nmessage 1\n\n--BOUNDARY\nContent-Type: text/plain\n\nmessage 2\n\n--BOUNDARY--\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_no_separating_blank_line$134(PyFrame var1, ThreadState var2) {
      var1.setline(1504);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1505);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_35.txt"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1506);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("as_string").__call__(var2), (PyObject)PyString.fromInterned("From: aperson@dom.ain\nTo: bperson@dom.ain\nSubject: here's something interesting\n\ncounter to RFC 2822, there's no separating newline here\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_lying_multipart$135(PyFrame var1, ThreadState var2) {
      var1.setline(1515);
      PyObject var3 = var1.getlocal(0).__getattr__("assertTrue");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1516);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_41.txt"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1517);
      var1.getlocal(1).__call__(var2, var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("defects")));
      var1.setline(1518);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(2).__getattr__("defects")), (PyObject)Py.newInteger(2));
      var1.setline(1519);
      var1.getlocal(1).__call__(var2, var1.getglobal("isinstance").__call__(var2, var1.getlocal(2).__getattr__("defects").__getitem__(Py.newInteger(0)), var1.getglobal("errors").__getattr__("NoBoundaryInMultipartDefect")));
      var1.setline(1520);
      var1.getlocal(1).__call__(var2, var1.getglobal("isinstance").__call__(var2, var1.getlocal(2).__getattr__("defects").__getitem__(Py.newInteger(1)), var1.getglobal("errors").__getattr__("MultipartInvariantViolationDefect")));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_missing_start_boundary$136(PyFrame var1, ThreadState var2) {
      var1.setline(1524);
      PyObject var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_42.txt"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1533);
      var3 = var1.getlocal(1).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(1)).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1534);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(2).__getattr__("defects")), (PyObject)Py.newInteger(1));
      var1.setline(1535);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getglobal("isinstance").__call__(var2, var1.getlocal(2).__getattr__("defects").__getitem__(Py.newInteger(0)), var1.getglobal("errors").__getattr__("StartBoundaryNotFoundDefect")));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_first_line_is_continuation_header$137(PyFrame var1, ThreadState var2) {
      var1.setline(1539);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1540);
      PyString var4 = PyString.fromInterned(" Line 1\nLine 2\nLine 3");
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(1541);
      var3 = var1.getglobal("email").__getattr__("message_from_string").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1542);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("keys").__call__(var2), (PyObject)(new PyList(Py.EmptyObjects)));
      var1.setline(1543);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("get_payload").__call__(var2), (PyObject)PyString.fromInterned("Line 2\nLine 3"));
      var1.setline(1544);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(3).__getattr__("defects")), (PyObject)Py.newInteger(1));
      var1.setline(1545);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getglobal("isinstance").__call__(var2, var1.getlocal(3).__getattr__("defects").__getitem__(Py.newInteger(0)), var1.getglobal("errors").__getattr__("FirstHeaderLineIsContinuationDefect")));
      var1.setline(1547);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("defects").__getitem__(Py.newInteger(0)).__getattr__("line"), (PyObject)PyString.fromInterned(" Line 1\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestRFC2047$138(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1553);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_rfc2047_multiline$139, (PyObject)null);
      var1.setlocal("test_rfc2047_multiline", var4);
      var3 = null;
      var1.setline(1567);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_whitespace_eater_unicode$140, (PyObject)null);
      var1.setlocal("test_whitespace_eater_unicode", var4);
      var3 = null;
      var1.setline(1575);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_whitespace_eater_unicode_2$141, (PyObject)null);
      var1.setlocal("test_whitespace_eater_unicode_2", var4);
      var3 = null;
      var1.setline(1584);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_rfc2047_missing_whitespace$142, (PyObject)null);
      var1.setlocal("test_rfc2047_missing_whitespace", var4);
      var3 = null;
      var1.setline(1589);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_rfc2047_with_whitespace$143, (PyObject)null);
      var1.setlocal("test_rfc2047_with_whitespace", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_rfc2047_multiline$139(PyFrame var1, ThreadState var2) {
      var1.setline(1554);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1555);
      PyString var4 = PyString.fromInterned("Re: =?mac-iceland?q?r=8Aksm=9Arg=8Cs?= baz\n foo bar =?mac-iceland?q?r=8Aksm=9Arg=8Cs?=");
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(1557);
      var3 = var1.getglobal("decode_header").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1558);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("Re:"), var1.getglobal("None")}), new PyTuple(new PyObject[]{PyString.fromInterned("r\u008aksm\u009arg\u008cs"), PyString.fromInterned("mac-iceland")}), new PyTuple(new PyObject[]{PyString.fromInterned("baz foo bar"), var1.getglobal("None")}), new PyTuple(new PyObject[]{PyString.fromInterned("r\u008aksm\u009arg\u008cs"), PyString.fromInterned("mac-iceland")})})));
      var1.setline(1563);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("str").__call__(var2, var1.getglobal("make_header").__call__(var2, var1.getlocal(3))), (PyObject)PyString.fromInterned("Re: =?mac-iceland?q?r=8Aksm=9Arg=8Cs?= baz foo bar\n =?mac-iceland?q?r=8Aksm=9Arg=8Cs?="));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_whitespace_eater_unicode$140(PyFrame var1, ThreadState var2) {
      var1.setline(1568);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1569);
      PyString var4 = PyString.fromInterned("=?ISO-8859-1?Q?Andr=E9?= Pirard <pirard@dom.ain>");
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(1570);
      var3 = var1.getglobal("decode_header").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1571);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("André"), PyString.fromInterned("iso-8859-1")}), new PyTuple(new PyObject[]{PyString.fromInterned("Pirard <pirard@dom.ain>"), var1.getglobal("None")})})));
      var1.setline(1572);
      var3 = var1.getglobal("unicode").__call__(var2, var1.getglobal("make_header").__call__(var2, var1.getlocal(3))).__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("latin-1"));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1573);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)PyString.fromInterned("André Pirard <pirard@dom.ain>"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_whitespace_eater_unicode_2$141(PyFrame var1, ThreadState var2) {
      var1.setline(1576);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1577);
      PyString var4 = PyString.fromInterned("The =?iso-8859-1?b?cXVpY2sgYnJvd24gZm94?= jumped over the =?iso-8859-1?b?bGF6eSBkb2c=?=");
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(1578);
      var3 = var1.getglobal("decode_header").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1579);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("The"), var1.getglobal("None")}), new PyTuple(new PyObject[]{PyString.fromInterned("quick brown fox"), PyString.fromInterned("iso-8859-1")}), new PyTuple(new PyObject[]{PyString.fromInterned("jumped over the"), var1.getglobal("None")}), new PyTuple(new PyObject[]{PyString.fromInterned("lazy dog"), PyString.fromInterned("iso-8859-1")})})));
      var1.setline(1581);
      var3 = var1.getglobal("make_header").__call__(var2, var1.getlocal(3)).__getattr__("__unicode__").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1582);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)PyUnicode.fromInterned("The quick brown fox jumped over the lazy dog"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_rfc2047_missing_whitespace$142(PyFrame var1, ThreadState var2) {
      var1.setline(1585);
      PyString var3 = PyString.fromInterned("Sm=?ISO-8859-1?B?9g==?=rg=?ISO-8859-1?B?5Q==?=sbord");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1586);
      PyObject var4 = var1.getglobal("decode_header").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(1587);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{var1.getlocal(1), var1.getglobal("None")})})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_rfc2047_with_whitespace$143(PyFrame var1, ThreadState var2) {
      var1.setline(1590);
      PyString var3 = PyString.fromInterned("Sm =?ISO-8859-1?B?9g==?= rg =?ISO-8859-1?B?5Q==?= sbord");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1591);
      PyObject var4 = var1.getglobal("decode_header").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(1592);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("Sm"), var1.getglobal("None")}), new PyTuple(new PyObject[]{PyString.fromInterned("ö"), PyString.fromInterned("iso-8859-1")}), new PyTuple(new PyObject[]{PyString.fromInterned("rg"), var1.getglobal("None")}), new PyTuple(new PyObject[]{PyString.fromInterned("å"), PyString.fromInterned("iso-8859-1")}), new PyTuple(new PyObject[]{PyString.fromInterned("sbord"), var1.getglobal("None")})})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestMIMEMessage$144(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1600);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, setUp$145, (PyObject)null);
      var1.setlocal("setUp", var4);
      var3 = null;
      var1.setline(1607);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_type_error$146, (PyObject)null);
      var1.setlocal("test_type_error", var4);
      var3 = null;
      var1.setline(1610);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_valid_argument$147, (PyObject)null);
      var1.setlocal("test_valid_argument", var4);
      var3 = null;
      var1.setline(1625);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_bad_multipart$148, (PyObject)null);
      var1.setlocal("test_bad_multipart", var4);
      var3 = null;
      var1.setline(1634);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_generate$149, (PyObject)null);
      var1.setlocal("test_generate", var4);
      var3 = null;
      var1.setline(1654);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_parse_message_rfc822$150, (PyObject)null);
      var1.setlocal("test_parse_message_rfc822", var4);
      var3 = null;
      var1.setline(1667);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_dsn$151, (PyObject)null);
      var1.setlocal("test_dsn", var4);
      var3 = null;
      var1.setline(1725);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_epilogue$152, (PyObject)null);
      var1.setlocal("test_epilogue", var4);
      var3 = null;
      var1.setline(1748);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_no_nl_preamble$153, (PyObject)null);
      var1.setlocal("test_no_nl_preamble", var4);
      var3 = null;
      var1.setline(1783);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_default_type$154, (PyObject)null);
      var1.setlocal("test_default_type", var4);
      var3 = null;
      var1.setline(1803);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_default_type_with_explicit_container_type$155, (PyObject)null);
      var1.setlocal("test_default_type_with_explicit_container_type", var4);
      var3 = null;
      var1.setline(1823);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_default_type_non_parsed$156, (PyObject)null);
      var1.setlocal("test_default_type_non_parsed", var4);
      var3 = null;
      var1.setline(1897);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_mime_attachments_in_constructor$157, (PyObject)null);
      var1.setlocal("test_mime_attachments_in_constructor", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject setUp$145(PyFrame var1, ThreadState var2) {
      var1.setline(1601);
      PyObject var3 = var1.getglobal("openfile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_11.txt"));
      var1.setlocal(1, var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(1603);
         PyObject var4 = var1.getlocal(1).__getattr__("read").__call__(var2);
         var1.getlocal(0).__setattr__("_text", var4);
         var4 = null;
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(1605);
         var1.getlocal(1).__getattr__("close").__call__(var2);
         throw (Throwable)var5;
      }

      var1.setline(1605);
      var1.getlocal(1).__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_type_error$146(PyFrame var1, ThreadState var2) {
      var1.setline(1608);
      var1.getlocal(0).__getattr__("assertRaises").__call__((ThreadState)var2, var1.getglobal("TypeError"), (PyObject)var1.getglobal("MIMEMessage"), (PyObject)PyString.fromInterned("a plain string"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_valid_argument$147(PyFrame var1, ThreadState var2) {
      var1.setline(1611);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1612);
      var3 = var1.getlocal(0).__getattr__("assertTrue");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1613);
      PyString var4 = PyString.fromInterned("A sub-message");
      var1.setlocal(3, var4);
      var3 = null;
      var1.setline(1614);
      var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1615);
      var3 = var1.getlocal(3);
      var1.getlocal(4).__setitem__((PyObject)PyString.fromInterned("Subject"), var3);
      var3 = null;
      var1.setline(1616);
      var3 = var1.getglobal("MIMEMessage").__call__(var2, var1.getlocal(4));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(1617);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(5).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("message/rfc822"));
      var1.setline(1618);
      var3 = var1.getlocal(5).__getattr__("get_payload").__call__(var2);
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(1619);
      var1.getlocal(2).__call__(var2, var1.getglobal("isinstance").__call__(var2, var1.getlocal(6), var1.getglobal("list")));
      var1.setline(1620);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(6)), (PyObject)Py.newInteger(1));
      var1.setline(1621);
      var3 = var1.getlocal(6).__getitem__(Py.newInteger(0));
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(1622);
      PyObject var10000 = var1.getlocal(2);
      var3 = var1.getlocal(7);
      PyObject var10002 = var3._is(var1.getlocal(4));
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.setline(1623);
      var1.getlocal(1).__call__(var2, var1.getlocal(7).__getitem__(PyString.fromInterned("subject")), var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_bad_multipart$148(PyFrame var1, ThreadState var2) {
      var1.setline(1626);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1627);
      var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1628);
      PyString var4 = PyString.fromInterned("subpart 1");
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("Subject"), var4);
      var3 = null;
      var1.setline(1629);
      var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1630);
      var4 = PyString.fromInterned("subpart 2");
      var1.getlocal(3).__setitem__((PyObject)PyString.fromInterned("Subject"), var4);
      var3 = null;
      var1.setline(1631);
      var3 = var1.getglobal("MIMEMessage").__call__(var2, var1.getlocal(2));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1632);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getglobal("errors").__getattr__("MultipartConversionError"), var1.getlocal(4).__getattr__("attach"), var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_generate$149(PyFrame var1, ThreadState var2) {
      var1.setline(1636);
      PyObject var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1637);
      PyString var4 = PyString.fromInterned("An enclosed message");
      var1.getlocal(1).__setitem__((PyObject)PyString.fromInterned("Subject"), var4);
      var3 = null;
      var1.setline(1638);
      var1.getlocal(1).__getattr__("set_payload").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Here is the body of the message.\n"));
      var1.setline(1639);
      var3 = var1.getglobal("MIMEMessage").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1640);
      var4 = PyString.fromInterned("The enclosing message");
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("Subject"), var4);
      var3 = null;
      var1.setline(1641);
      var3 = var1.getglobal("StringIO").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1642);
      var3 = var1.getglobal("Generator").__call__(var2, var1.getlocal(3));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1643);
      var1.getlocal(4).__getattr__("flatten").__call__(var2, var1.getlocal(2));
      var1.setline(1644);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("getvalue").__call__(var2), (PyObject)PyString.fromInterned("Content-Type: message/rfc822\nMIME-Version: 1.0\nSubject: The enclosing message\n\nSubject: An enclosed message\n\nHere is the body of the message.\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_parse_message_rfc822$150(PyFrame var1, ThreadState var2) {
      var1.setline(1655);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1656);
      var3 = var1.getlocal(0).__getattr__("assertTrue");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1657);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_11.txt"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1658);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("message/rfc822"));
      var1.setline(1659);
      var3 = var1.getlocal(3).__getattr__("get_payload").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1660);
      var1.getlocal(2).__call__(var2, var1.getglobal("isinstance").__call__(var2, var1.getlocal(4), var1.getglobal("list")));
      var1.setline(1661);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(4)), (PyObject)Py.newInteger(1));
      var1.setline(1662);
      var3 = var1.getlocal(4).__getitem__(Py.newInteger(0));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(1663);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getglobal("isinstance").__call__(var2, var1.getlocal(5), var1.getglobal("Message")));
      var1.setline(1664);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(5).__getitem__(PyString.fromInterned("subject")), (PyObject)PyString.fromInterned("An enclosed message"));
      var1.setline(1665);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(5).__getattr__("get_payload").__call__(var2), (PyObject)PyString.fromInterned("Here is the body of the message.\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_dsn$151(PyFrame var1, ThreadState var2) {
      var1.setline(1668);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1669);
      var3 = var1.getlocal(0).__getattr__("assertTrue");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1671);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_16.txt"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1672);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("multipart/report"));
      var1.setline(1673);
      var1.getlocal(2).__call__(var2, var1.getlocal(3).__getattr__("is_multipart").__call__(var2));
      var1.setline(1674);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(3).__getattr__("get_payload").__call__(var2)), (PyObject)Py.newInteger(3));
      var1.setline(1676);
      var3 = var1.getlocal(3).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1677);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(4).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("text/plain"));
      var1.setline(1678);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(4).__getattr__("get_payload").__call__(var2), (PyObject)PyString.fromInterned("This report relates to a message you sent with the following header fields:\n\n  Message-id: <002001c144a6$8752e060$56104586@oxy.edu>\n  Date: Sun, 23 Sep 2001 20:10:55 -0700\n  From: \"Ian T. Henry\" <henryi@oxy.edu>\n  To: SoCal Raves <scr@socal-raves.org>\n  Subject: [scr] yeah for Ians!!\n\nYour message cannot be delivered to the following recipients:\n\n  Recipient address: jangel1@cougar.noc.ucla.edu\n  Reason: recipient reached disk quota\n\n"));
      var1.setline(1696);
      var3 = var1.getlocal(3).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1697);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(4).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("message/delivery-status"));
      var1.setline(1698);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(4).__getattr__("get_payload").__call__(var2)), (PyObject)Py.newInteger(2));
      var1.setline(1701);
      var3 = var1.getlocal(4).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(1702);
      var1.getlocal(2).__call__(var2, var1.getglobal("isinstance").__call__(var2, var1.getlocal(5), var1.getglobal("Message")));
      var1.setline(1703);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(5).__getitem__(PyString.fromInterned("original-envelope-id")), (PyObject)PyString.fromInterned("0GK500B4HD0888@cougar.noc.ucla.edu"));
      var1.setline(1704);
      PyObject var10000 = var1.getlocal(1);
      PyObject var10002 = var1.getlocal(5).__getattr__("get_param");
      PyObject[] var5 = new PyObject[]{PyString.fromInterned("dns"), PyString.fromInterned("reporting-mta")};
      String[] var4 = new String[]{"header"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned(""));
      var1.setline(1706);
      var10000 = var1.getlocal(1);
      var10002 = var1.getlocal(5).__getattr__("get_param");
      var5 = new PyObject[]{PyString.fromInterned("nsd"), PyString.fromInterned("reporting-mta")};
      var4 = new String[]{"header"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__(var2, var10002, var1.getglobal("None"));
      var1.setline(1707);
      var3 = var1.getlocal(4).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(1708);
      var1.getlocal(2).__call__(var2, var1.getglobal("isinstance").__call__(var2, var1.getlocal(6), var1.getglobal("Message")));
      var1.setline(1709);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(6).__getitem__(PyString.fromInterned("action")), (PyObject)PyString.fromInterned("failed"));
      var1.setline(1710);
      var10000 = var1.getlocal(1);
      var10002 = var1.getlocal(6).__getattr__("get_params");
      var5 = new PyObject[]{PyString.fromInterned("original-recipient")};
      var4 = new String[]{"header"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("rfc822"), PyString.fromInterned("")}), new PyTuple(new PyObject[]{PyString.fromInterned("jangel1@cougar.noc.ucla.edu"), PyString.fromInterned("")})})));
      var1.setline(1712);
      var10000 = var1.getlocal(1);
      var10002 = var1.getlocal(6).__getattr__("get_param");
      var5 = new PyObject[]{PyString.fromInterned("rfc822"), PyString.fromInterned("final-recipient")};
      var4 = new String[]{"header"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned(""));
      var1.setline(1714);
      var3 = var1.getlocal(3).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(2));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1715);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(4).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("message/rfc822"));
      var1.setline(1716);
      var3 = var1.getlocal(4).__getattr__("get_payload").__call__(var2);
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(1717);
      var1.getlocal(2).__call__(var2, var1.getglobal("isinstance").__call__(var2, var1.getlocal(7), var1.getglobal("list")));
      var1.setline(1718);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(7)), (PyObject)Py.newInteger(1));
      var1.setline(1719);
      var3 = var1.getlocal(7).__getitem__(Py.newInteger(0));
      var1.setlocal(8, var3);
      var3 = null;
      var1.setline(1720);
      var1.getlocal(2).__call__(var2, var1.getglobal("isinstance").__call__(var2, var1.getlocal(8), var1.getglobal("Message")));
      var1.setline(1721);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(8).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("text/plain"));
      var1.setline(1722);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(8).__getitem__(PyString.fromInterned("message-id")), (PyObject)PyString.fromInterned("<002001c144a6$8752e060$56104586@oxy.edu>"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_epilogue$152(PyFrame var1, ThreadState var2) {
      var1.setline(1726);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1727);
      var3 = var1.getglobal("openfile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_21.txt"));
      var1.setlocal(2, var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(1729);
         PyObject var4 = var1.getlocal(2).__getattr__("read").__call__(var2);
         var1.setlocal(3, var4);
         var4 = null;
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(1731);
         var1.getlocal(2).__getattr__("close").__call__(var2);
         throw (Throwable)var5;
      }

      var1.setline(1731);
      var1.getlocal(2).__getattr__("close").__call__(var2);
      var1.setline(1732);
      var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1733);
      PyString var7 = PyString.fromInterned("aperson@dom.ain");
      var1.getlocal(4).__setitem__((PyObject)PyString.fromInterned("From"), var7);
      var3 = null;
      var1.setline(1734);
      var7 = PyString.fromInterned("bperson@dom.ain");
      var1.getlocal(4).__setitem__((PyObject)PyString.fromInterned("To"), var7);
      var3 = null;
      var1.setline(1735);
      var7 = PyString.fromInterned("Test");
      var1.getlocal(4).__setitem__((PyObject)PyString.fromInterned("Subject"), var7);
      var3 = null;
      var1.setline(1736);
      var7 = PyString.fromInterned("MIME message");
      var1.getlocal(4).__setattr__((String)"preamble", var7);
      var3 = null;
      var1.setline(1737);
      var7 = PyString.fromInterned("End of MIME message\n");
      var1.getlocal(4).__setattr__((String)"epilogue", var7);
      var3 = null;
      var1.setline(1738);
      var3 = var1.getglobal("MIMEText").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("One"));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(1739);
      var3 = var1.getglobal("MIMEText").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Two"));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(1740);
      PyObject var10000 = var1.getlocal(4).__getattr__("add_header");
      PyObject[] var8 = new PyObject[]{PyString.fromInterned("Content-Type"), PyString.fromInterned("multipart/mixed"), PyString.fromInterned("BOUNDARY")};
      String[] var6 = new String[]{"boundary"};
      var10000.__call__(var2, var8, var6);
      var3 = null;
      var1.setline(1741);
      var1.getlocal(4).__getattr__("attach").__call__(var2, var1.getlocal(5));
      var1.setline(1742);
      var1.getlocal(4).__getattr__("attach").__call__(var2, var1.getlocal(6));
      var1.setline(1743);
      var3 = var1.getglobal("StringIO").__call__(var2);
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(1744);
      var3 = var1.getglobal("Generator").__call__(var2, var1.getlocal(7));
      var1.setlocal(8, var3);
      var3 = null;
      var1.setline(1745);
      var1.getlocal(8).__getattr__("flatten").__call__(var2, var1.getlocal(4));
      var1.setline(1746);
      var1.getlocal(1).__call__(var2, var1.getlocal(7).__getattr__("getvalue").__call__(var2), var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_no_nl_preamble$153(PyFrame var1, ThreadState var2) {
      var1.setline(1749);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1750);
      var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1751);
      PyString var5 = PyString.fromInterned("aperson@dom.ain");
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("From"), var5);
      var3 = null;
      var1.setline(1752);
      var5 = PyString.fromInterned("bperson@dom.ain");
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("To"), var5);
      var3 = null;
      var1.setline(1753);
      var5 = PyString.fromInterned("Test");
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("Subject"), var5);
      var3 = null;
      var1.setline(1754);
      var5 = PyString.fromInterned("MIME message");
      var1.getlocal(2).__setattr__((String)"preamble", var5);
      var3 = null;
      var1.setline(1755);
      var5 = PyString.fromInterned("");
      var1.getlocal(2).__setattr__((String)"epilogue", var5);
      var3 = null;
      var1.setline(1756);
      var3 = var1.getglobal("MIMEText").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("One"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1757);
      var3 = var1.getglobal("MIMEText").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Two"));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1758);
      PyObject var10000 = var1.getlocal(2).__getattr__("add_header");
      PyObject[] var6 = new PyObject[]{PyString.fromInterned("Content-Type"), PyString.fromInterned("multipart/mixed"), PyString.fromInterned("BOUNDARY")};
      String[] var4 = new String[]{"boundary"};
      var10000.__call__(var2, var6, var4);
      var3 = null;
      var1.setline(1759);
      var1.getlocal(2).__getattr__("attach").__call__(var2, var1.getlocal(3));
      var1.setline(1760);
      var1.getlocal(2).__getattr__("attach").__call__(var2, var1.getlocal(4));
      var1.setline(1761);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("as_string").__call__(var2), (PyObject)PyString.fromInterned("From: aperson@dom.ain\nTo: bperson@dom.ain\nSubject: Test\nContent-Type: multipart/mixed; boundary=\"BOUNDARY\"\n\nMIME message\n--BOUNDARY\nContent-Type: text/plain; charset=\"us-ascii\"\nMIME-Version: 1.0\nContent-Transfer-Encoding: 7bit\n\nOne\n--BOUNDARY\nContent-Type: text/plain; charset=\"us-ascii\"\nMIME-Version: 1.0\nContent-Transfer-Encoding: 7bit\n\nTwo\n--BOUNDARY--\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_default_type$154(PyFrame var1, ThreadState var2) {
      var1.setline(1784);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1785);
      var3 = var1.getglobal("openfile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_30.txt"));
      var1.setlocal(2, var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(1787);
         PyObject var4 = var1.getglobal("email").__getattr__("message_from_file").__call__(var2, var1.getlocal(2));
         var1.setlocal(3, var4);
         var4 = null;
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(1789);
         var1.getlocal(2).__getattr__("close").__call__(var2);
         throw (Throwable)var5;
      }

      var1.setline(1789);
      var1.getlocal(2).__getattr__("close").__call__(var2);
      var1.setline(1790);
      var3 = var1.getlocal(3).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1791);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(4).__getattr__("get_default_type").__call__(var2), (PyObject)PyString.fromInterned("message/rfc822"));
      var1.setline(1792);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(4).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("message/rfc822"));
      var1.setline(1793);
      var3 = var1.getlocal(3).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(1794);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(5).__getattr__("get_default_type").__call__(var2), (PyObject)PyString.fromInterned("message/rfc822"));
      var1.setline(1795);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(5).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("message/rfc822"));
      var1.setline(1796);
      var3 = var1.getlocal(4).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(1797);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(6).__getattr__("get_default_type").__call__(var2), (PyObject)PyString.fromInterned("text/plain"));
      var1.setline(1798);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(6).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("text/plain"));
      var1.setline(1799);
      var3 = var1.getlocal(5).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(1800);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(7).__getattr__("get_default_type").__call__(var2), (PyObject)PyString.fromInterned("text/plain"));
      var1.setline(1801);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(7).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("text/plain"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_default_type_with_explicit_container_type$155(PyFrame var1, ThreadState var2) {
      var1.setline(1804);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1805);
      var3 = var1.getglobal("openfile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_28.txt"));
      var1.setlocal(2, var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(1807);
         PyObject var4 = var1.getglobal("email").__getattr__("message_from_file").__call__(var2, var1.getlocal(2));
         var1.setlocal(3, var4);
         var4 = null;
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(1809);
         var1.getlocal(2).__getattr__("close").__call__(var2);
         throw (Throwable)var5;
      }

      var1.setline(1809);
      var1.getlocal(2).__getattr__("close").__call__(var2);
      var1.setline(1810);
      var3 = var1.getlocal(3).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1811);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(4).__getattr__("get_default_type").__call__(var2), (PyObject)PyString.fromInterned("message/rfc822"));
      var1.setline(1812);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(4).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("message/rfc822"));
      var1.setline(1813);
      var3 = var1.getlocal(3).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(1814);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(5).__getattr__("get_default_type").__call__(var2), (PyObject)PyString.fromInterned("message/rfc822"));
      var1.setline(1815);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(5).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("message/rfc822"));
      var1.setline(1816);
      var3 = var1.getlocal(4).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(1817);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(6).__getattr__("get_default_type").__call__(var2), (PyObject)PyString.fromInterned("text/plain"));
      var1.setline(1818);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(6).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("text/plain"));
      var1.setline(1819);
      var3 = var1.getlocal(5).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(1820);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(7).__getattr__("get_default_type").__call__(var2), (PyObject)PyString.fromInterned("text/plain"));
      var1.setline(1821);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(7).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("text/plain"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_default_type_non_parsed$156(PyFrame var1, ThreadState var2) {
      var1.setline(1824);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1825);
      var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1827);
      var3 = var1.getglobal("MIMEMultipart").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("digest"), (PyObject)PyString.fromInterned("BOUNDARY"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1828);
      PyString var4 = PyString.fromInterned("");
      var1.getlocal(3).__setattr__((String)"epilogue", var4);
      var3 = null;
      var1.setline(1830);
      var3 = var1.getglobal("MIMEText").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("message 1\n"));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1831);
      var3 = var1.getglobal("MIMEText").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("message 2\n"));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(1832);
      var3 = var1.getglobal("MIMEMessage").__call__(var2, var1.getlocal(4));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(1833);
      var3 = var1.getglobal("MIMEMessage").__call__(var2, var1.getlocal(5));
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(1834);
      var1.getlocal(3).__getattr__("attach").__call__(var2, var1.getlocal(6));
      var1.setline(1835);
      var1.getlocal(3).__getattr__("attach").__call__(var2, var1.getlocal(7));
      var1.setline(1836);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(6).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("message/rfc822"));
      var1.setline(1837);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(6).__getattr__("get_default_type").__call__(var2), (PyObject)PyString.fromInterned("message/rfc822"));
      var1.setline(1838);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(7).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("message/rfc822"));
      var1.setline(1839);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(7).__getattr__("get_default_type").__call__(var2), (PyObject)PyString.fromInterned("message/rfc822"));
      var1.setline(1840);
      var1.getlocal(2).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("as_string").__call__((ThreadState)var2, (PyObject)Py.newInteger(0)), (PyObject)PyString.fromInterned("Content-Type: multipart/digest; boundary=\"BOUNDARY\"\nMIME-Version: 1.0\n\n--BOUNDARY\nContent-Type: message/rfc822\nMIME-Version: 1.0\n\nContent-Type: text/plain; charset=\"us-ascii\"\nMIME-Version: 1.0\nContent-Transfer-Encoding: 7bit\n\nmessage 1\n\n--BOUNDARY\nContent-Type: message/rfc822\nMIME-Version: 1.0\n\nContent-Type: text/plain; charset=\"us-ascii\"\nMIME-Version: 1.0\nContent-Transfer-Encoding: 7bit\n\nmessage 2\n\n--BOUNDARY--\n"));
      var1.setline(1866);
      var1.getlocal(6).__delitem__((PyObject)PyString.fromInterned("content-type"));
      var1.setline(1867);
      var1.getlocal(6).__delitem__((PyObject)PyString.fromInterned("mime-version"));
      var1.setline(1868);
      var1.getlocal(7).__delitem__((PyObject)PyString.fromInterned("content-type"));
      var1.setline(1869);
      var1.getlocal(7).__delitem__((PyObject)PyString.fromInterned("mime-version"));
      var1.setline(1870);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(6).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("message/rfc822"));
      var1.setline(1871);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(6).__getattr__("get_default_type").__call__(var2), (PyObject)PyString.fromInterned("message/rfc822"));
      var1.setline(1872);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(7).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("message/rfc822"));
      var1.setline(1873);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(7).__getattr__("get_default_type").__call__(var2), (PyObject)PyString.fromInterned("message/rfc822"));
      var1.setline(1874);
      var1.getlocal(2).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("as_string").__call__((ThreadState)var2, (PyObject)Py.newInteger(0)), (PyObject)PyString.fromInterned("Content-Type: multipart/digest; boundary=\"BOUNDARY\"\nMIME-Version: 1.0\n\n--BOUNDARY\n\nContent-Type: text/plain; charset=\"us-ascii\"\nMIME-Version: 1.0\nContent-Transfer-Encoding: 7bit\n\nmessage 1\n\n--BOUNDARY\n\nContent-Type: text/plain; charset=\"us-ascii\"\nMIME-Version: 1.0\nContent-Transfer-Encoding: 7bit\n\nmessage 2\n\n--BOUNDARY--\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_mime_attachments_in_constructor$157(PyFrame var1, ThreadState var2) {
      var1.setline(1898);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1899);
      var3 = var1.getglobal("MIMEText").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(""));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1900);
      var3 = var1.getglobal("MIMEText").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(""));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1901);
      PyObject var10000 = var1.getglobal("MIMEMultipart");
      PyObject[] var5 = new PyObject[]{new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3)})};
      String[] var4 = new String[]{"_subparts"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1902);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(4).__getattr__("get_payload").__call__(var2)), (PyObject)Py.newInteger(2));
      var1.setline(1903);
      var1.getlocal(1).__call__(var2, var1.getlocal(4).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(0)), var1.getlocal(2));
      var1.setline(1904);
      var1.getlocal(1).__call__(var2, var1.getlocal(4).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(1)), var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestIdempotent$158(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1914);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, _msgobj$159, (PyObject)null);
      var1.setlocal("_msgobj", var4);
      var3 = null;
      var1.setline(1923);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _idempotent$160, (PyObject)null);
      var1.setlocal("_idempotent", var4);
      var3 = null;
      var1.setline(1930);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_parse_text_message$161, (PyObject)null);
      var1.setlocal("test_parse_text_message", var4);
      var3 = null;
      var1.setline(1942);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_parse_untyped_message$162, (PyObject)null);
      var1.setlocal("test_parse_untyped_message", var4);
      var3 = null;
      var1.setline(1950);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_simple_multipart$163, (PyObject)null);
      var1.setlocal("test_simple_multipart", var4);
      var3 = null;
      var1.setline(1954);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_MIME_digest$164, (PyObject)null);
      var1.setlocal("test_MIME_digest", var4);
      var3 = null;
      var1.setline(1958);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_long_header$165, (PyObject)null);
      var1.setlocal("test_long_header", var4);
      var3 = null;
      var1.setline(1962);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_MIME_digest_with_part_headers$166, (PyObject)null);
      var1.setlocal("test_MIME_digest_with_part_headers", var4);
      var3 = null;
      var1.setline(1966);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_mixed_with_image$167, (PyObject)null);
      var1.setlocal("test_mixed_with_image", var4);
      var3 = null;
      var1.setline(1970);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_multipart_report$168, (PyObject)null);
      var1.setlocal("test_multipart_report", var4);
      var3 = null;
      var1.setline(1974);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_dsn$169, (PyObject)null);
      var1.setlocal("test_dsn", var4);
      var3 = null;
      var1.setline(1978);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_preamble_epilogue$170, (PyObject)null);
      var1.setlocal("test_preamble_epilogue", var4);
      var3 = null;
      var1.setline(1982);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_multipart_one_part$171, (PyObject)null);
      var1.setlocal("test_multipart_one_part", var4);
      var3 = null;
      var1.setline(1986);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_multipart_no_parts$172, (PyObject)null);
      var1.setlocal("test_multipart_no_parts", var4);
      var3 = null;
      var1.setline(1990);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_no_start_boundary$173, (PyObject)null);
      var1.setlocal("test_no_start_boundary", var4);
      var3 = null;
      var1.setline(1994);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_rfc2231_charset$174, (PyObject)null);
      var1.setlocal("test_rfc2231_charset", var4);
      var3 = null;
      var1.setline(1998);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_more_rfc2231_parameters$175, (PyObject)null);
      var1.setlocal("test_more_rfc2231_parameters", var4);
      var3 = null;
      var1.setline(2002);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_text_plain_in_a_multipart_digest$176, (PyObject)null);
      var1.setlocal("test_text_plain_in_a_multipart_digest", var4);
      var3 = null;
      var1.setline(2006);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_nested_multipart_mixeds$177, (PyObject)null);
      var1.setlocal("test_nested_multipart_mixeds", var4);
      var3 = null;
      var1.setline(2010);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_message_external_body_idempotent$178, (PyObject)null);
      var1.setlocal("test_message_external_body_idempotent", var4);
      var3 = null;
      var1.setline(2014);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_content_type$179, (PyObject)null);
      var1.setlocal("test_content_type", var4);
      var3 = null;
      var1.setline(2046);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_parser$180, (PyObject)null);
      var1.setlocal("test_parser", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject _msgobj$159(PyFrame var1, ThreadState var2) {
      var1.setline(1915);
      PyObject var3 = var1.getglobal("openfile").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(1917);
         PyObject var4 = var1.getlocal(2).__getattr__("read").__call__(var2);
         var1.setlocal(3, var4);
         var4 = null;
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(1919);
         var1.getlocal(2).__getattr__("close").__call__(var2);
         throw (Throwable)var5;
      }

      var1.setline(1919);
      var1.getlocal(2).__getattr__("close").__call__(var2);
      var1.setline(1920);
      var3 = var1.getglobal("email").__getattr__("message_from_string").__call__(var2, var1.getlocal(3));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1921);
      PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(3)});
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject _idempotent$160(PyFrame var1, ThreadState var2) {
      var1.setline(1924);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1925);
      var3 = var1.getglobal("StringIO").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1926);
      PyObject var10000 = var1.getglobal("Generator");
      PyObject[] var5 = new PyObject[]{var1.getlocal(4), Py.newInteger(0)};
      String[] var4 = new String[]{"maxheaderlen"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(1927);
      var1.getlocal(5).__getattr__("flatten").__call__(var2, var1.getlocal(1));
      var1.setline(1928);
      var1.getlocal(3).__call__(var2, var1.getlocal(2), var1.getlocal(4).__getattr__("getvalue").__call__(var2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_parse_text_message$161(PyFrame var1, ThreadState var2) {
      var1.setline(1931);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1932);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_01.txt"));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(1933);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("text/plain"));
      var1.setline(1934);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_content_maintype").__call__(var2), (PyObject)PyString.fromInterned("text"));
      var1.setline(1935);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_content_subtype").__call__(var2), (PyObject)PyString.fromInterned("plain"));
      var1.setline(1936);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_params").__call__(var2).__getitem__(Py.newInteger(1)), (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("charset"), PyString.fromInterned("us-ascii")})));
      var1.setline(1937);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_param").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("charset")), (PyObject)PyString.fromInterned("us-ascii"));
      var1.setline(1938);
      var1.getlocal(1).__call__(var2, var1.getlocal(2).__getattr__("preamble"), var1.getglobal("None"));
      var1.setline(1939);
      var1.getlocal(1).__call__(var2, var1.getlocal(2).__getattr__("epilogue"), var1.getglobal("None"));
      var1.setline(1940);
      var1.getlocal(0).__getattr__("_idempotent").__call__(var2, var1.getlocal(2), var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_parse_untyped_message$162(PyFrame var1, ThreadState var2) {
      var1.setline(1943);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1944);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_03.txt"));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(1945);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("text/plain"));
      var1.setline(1946);
      var1.getlocal(1).__call__(var2, var1.getlocal(2).__getattr__("get_params").__call__(var2), var1.getglobal("None"));
      var1.setline(1947);
      var1.getlocal(1).__call__(var2, var1.getlocal(2).__getattr__("get_param").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("charset")), var1.getglobal("None"));
      var1.setline(1948);
      var1.getlocal(0).__getattr__("_idempotent").__call__(var2, var1.getlocal(2), var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_simple_multipart$163(PyFrame var1, ThreadState var2) {
      var1.setline(1951);
      PyObject var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_04.txt"));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(1952);
      var1.getlocal(0).__getattr__("_idempotent").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_MIME_digest$164(PyFrame var1, ThreadState var2) {
      var1.setline(1955);
      PyObject var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_02.txt"));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(1956);
      var1.getlocal(0).__getattr__("_idempotent").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_long_header$165(PyFrame var1, ThreadState var2) {
      var1.setline(1959);
      PyObject var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_27.txt"));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(1960);
      var1.getlocal(0).__getattr__("_idempotent").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_MIME_digest_with_part_headers$166(PyFrame var1, ThreadState var2) {
      var1.setline(1963);
      PyObject var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_28.txt"));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(1964);
      var1.getlocal(0).__getattr__("_idempotent").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_mixed_with_image$167(PyFrame var1, ThreadState var2) {
      var1.setline(1967);
      PyObject var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_06.txt"));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(1968);
      var1.getlocal(0).__getattr__("_idempotent").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_multipart_report$168(PyFrame var1, ThreadState var2) {
      var1.setline(1971);
      PyObject var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_05.txt"));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(1972);
      var1.getlocal(0).__getattr__("_idempotent").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_dsn$169(PyFrame var1, ThreadState var2) {
      var1.setline(1975);
      PyObject var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_16.txt"));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(1976);
      var1.getlocal(0).__getattr__("_idempotent").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_preamble_epilogue$170(PyFrame var1, ThreadState var2) {
      var1.setline(1979);
      PyObject var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_21.txt"));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(1980);
      var1.getlocal(0).__getattr__("_idempotent").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_multipart_one_part$171(PyFrame var1, ThreadState var2) {
      var1.setline(1983);
      PyObject var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_23.txt"));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(1984);
      var1.getlocal(0).__getattr__("_idempotent").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_multipart_no_parts$172(PyFrame var1, ThreadState var2) {
      var1.setline(1987);
      PyObject var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_24.txt"));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(1988);
      var1.getlocal(0).__getattr__("_idempotent").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_no_start_boundary$173(PyFrame var1, ThreadState var2) {
      var1.setline(1991);
      PyObject var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_31.txt"));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(1992);
      var1.getlocal(0).__getattr__("_idempotent").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_rfc2231_charset$174(PyFrame var1, ThreadState var2) {
      var1.setline(1995);
      PyObject var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_32.txt"));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(1996);
      var1.getlocal(0).__getattr__("_idempotent").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_more_rfc2231_parameters$175(PyFrame var1, ThreadState var2) {
      var1.setline(1999);
      PyObject var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_33.txt"));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(2000);
      var1.getlocal(0).__getattr__("_idempotent").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_text_plain_in_a_multipart_digest$176(PyFrame var1, ThreadState var2) {
      var1.setline(2003);
      PyObject var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_34.txt"));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(2004);
      var1.getlocal(0).__getattr__("_idempotent").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_nested_multipart_mixeds$177(PyFrame var1, ThreadState var2) {
      var1.setline(2007);
      PyObject var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_12a.txt"));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(2008);
      var1.getlocal(0).__getattr__("_idempotent").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_message_external_body_idempotent$178(PyFrame var1, ThreadState var2) {
      var1.setline(2011);
      PyObject var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_36.txt"));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(2012);
      var1.getlocal(0).__getattr__("_idempotent").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_content_type$179(PyFrame var1, ThreadState var2) {
      var1.setline(2015);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2016);
      var3 = var1.getlocal(0).__getattr__("assertTrue");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2018);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_05.txt"));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;
      var1.setline(2019);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("multipart/report"));
      var1.setline(2021);
      PyDictionary var8 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(5, var8);
      var3 = null;
      var1.setline(2022);
      var3 = var1.getlocal(3).__getattr__("get_params").__call__(var2).__iter__();

      while(true) {
         var1.setline(2022);
         PyObject var7 = var3.__iternext__();
         if (var7 == null) {
            var1.setline(2024);
            var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(5).__getitem__(PyString.fromInterned("report-type")), (PyObject)PyString.fromInterned("delivery-status"));
            var1.setline(2025);
            var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(5).__getitem__(PyString.fromInterned("boundary")), (PyObject)PyString.fromInterned("D1690A7AC1.996856090/mail.example.com"));
            var1.setline(2026);
            var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("preamble"), (PyObject)PyString.fromInterned("This is a MIME-encapsulated message.\n"));
            var1.setline(2027);
            var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("epilogue"), (PyObject)PyString.fromInterned("\n"));
            var1.setline(2028);
            var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(3).__getattr__("get_payload").__call__(var2)), (PyObject)Py.newInteger(3));
            var1.setline(2030);
            var3 = var1.getlocal(3).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
            var1.setlocal(8, var3);
            var3 = null;
            var1.setline(2031);
            var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(8).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("text/plain"));
            var1.setline(2032);
            var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(8).__getattr__("get_payload").__call__(var2), (PyObject)PyString.fromInterned("Yadda yadda yadda\n"));
            var1.setline(2033);
            var3 = var1.getlocal(3).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
            var1.setlocal(9, var3);
            var3 = null;
            var1.setline(2034);
            var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(9).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("text/plain"));
            var1.setline(2035);
            var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(9).__getattr__("get_payload").__call__(var2), (PyObject)PyString.fromInterned("Yadda yadda yadda\n"));
            var1.setline(2036);
            var3 = var1.getlocal(3).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(2));
            var1.setlocal(10, var3);
            var3 = null;
            var1.setline(2037);
            var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(10).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("message/rfc822"));
            var1.setline(2038);
            var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getglobal("isinstance").__call__(var2, var1.getlocal(10), var1.getglobal("Message")));
            var1.setline(2039);
            var3 = var1.getlocal(10).__getattr__("get_payload").__call__(var2);
            var1.setlocal(11, var3);
            var3 = null;
            var1.setline(2040);
            var1.getlocal(2).__call__(var2, var1.getglobal("isinstance").__call__(var2, var1.getlocal(11), var1.getglobal("list")));
            var1.setline(2041);
            var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(11)), (PyObject)Py.newInteger(1));
            var1.setline(2042);
            var3 = var1.getlocal(11).__getitem__(Py.newInteger(0));
            var1.setlocal(12, var3);
            var3 = null;
            var1.setline(2043);
            var1.getlocal(2).__call__(var2, var1.getglobal("isinstance").__call__(var2, var1.getlocal(12), var1.getglobal("Message")));
            var1.setline(2044);
            var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(12).__getattr__("get_payload").__call__(var2), (PyObject)PyString.fromInterned("Yadda yadda yadda\n"));
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var9 = Py.unpackSequence(var7, 2);
         PyObject var6 = var9[0];
         var1.setlocal(6, var6);
         var6 = null;
         var6 = var9[1];
         var1.setlocal(7, var6);
         var6 = null;
         var1.setline(2023);
         var5 = var1.getlocal(7);
         var1.getlocal(5).__setitem__(var1.getlocal(6), var5);
         var5 = null;
      }
   }

   public PyObject test_parser$180(PyFrame var1, ThreadState var2) {
      var1.setline(2047);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2048);
      var3 = var1.getlocal(0).__getattr__("assertTrue");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2049);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_06.txt"));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;
      var1.setline(2051);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("message/rfc822"));
      var1.setline(2054);
      var3 = var1.getlocal(3).__getattr__("get_payload").__call__(var2);
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(2055);
      var1.getlocal(2).__call__(var2, var1.getglobal("isinstance").__call__(var2, var1.getlocal(5), var1.getglobal("list")));
      var1.setline(2056);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(5)), (PyObject)Py.newInteger(1));
      var1.setline(2057);
      var3 = var1.getlocal(5).__getitem__(Py.newInteger(0));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(2058);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getglobal("isinstance").__call__(var2, var1.getlocal(6), var1.getglobal("Message")));
      var1.setline(2059);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(6).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("text/plain"));
      var1.setline(2060);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getglobal("isinstance").__call__(var2, var1.getlocal(6).__getattr__("get_payload").__call__(var2), var1.getglobal("str")));
      var1.setline(2061);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(6).__getattr__("get_payload").__call__(var2), (PyObject)PyString.fromInterned("\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestMiscellaneous$181(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(2067);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_message_from_string$182, (PyObject)null);
      var1.setlocal("test_message_from_string", var4);
      var3 = null;
      var1.setline(2081);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_message_from_file$183, (PyObject)null);
      var1.setlocal("test_message_from_file", var4);
      var3 = null;
      var1.setline(2096);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_message_from_string_with_class$184, (PyObject)null);
      var1.setlocal("test_message_from_string_with_class", var4);
      var3 = null;
      var1.setline(2119);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_message_from_file_with_class$186, (PyObject)null);
      var1.setlocal("test_message_from_file_with_class", var4);
      var3 = null;
      var1.setline(2140);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test__all__$188, (PyObject)null);
      var1.setlocal("test__all__", var4);
      var3 = null;
      var1.setline(2159);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_formatdate$189, (PyObject)null);
      var1.setlocal("test_formatdate", var4);
      var3 = null;
      var1.setline(2164);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_formatdate_localtime$190, (PyObject)null);
      var1.setlocal("test_formatdate_localtime", var4);
      var3 = null;
      var1.setline(2170);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_formatdate_usegmt$191, (PyObject)null);
      var1.setlocal("test_formatdate_usegmt", var4);
      var3 = null;
      var1.setline(2179);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_parsedate_none$192, (PyObject)null);
      var1.setlocal("test_parsedate_none", var4);
      var3 = null;
      var1.setline(2182);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_parsedate_compact$193, (PyObject)null);
      var1.setlocal("test_parsedate_compact", var4);
      var3 = null;
      var1.setline(2187);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_parsedate_no_dayofweek$194, (PyObject)null);
      var1.setlocal("test_parsedate_no_dayofweek", var4);
      var3 = null;
      var1.setline(2192);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_parsedate_compact_no_dayofweek$195, (PyObject)null);
      var1.setlocal("test_parsedate_compact_no_dayofweek", var4);
      var3 = null;
      var1.setline(2197);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_parsedate_acceptable_to_time_functions$196, (PyObject)null);
      var1.setlocal("test_parsedate_acceptable_to_time_functions", var4);
      var3 = null;
      var1.setline(2208);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_parseaddr_empty$197, (PyObject)null);
      var1.setlocal("test_parseaddr_empty", var4);
      var3 = null;
      var1.setline(2212);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_noquote_dump$198, (PyObject)null);
      var1.setlocal("test_noquote_dump", var4);
      var3 = null;
      var1.setline(2217);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_escape_dump$199, (PyObject)null);
      var1.setlocal("test_escape_dump", var4);
      var3 = null;
      var1.setline(2225);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_escape_backslashes$200, (PyObject)null);
      var1.setlocal("test_escape_backslashes", var4);
      var3 = null;
      var1.setline(2233);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_name_with_dot$201, (PyObject)null);
      var1.setlocal("test_name_with_dot", var4);
      var3 = null;
      var1.setline(2242);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_multiline_from_comment$202, (PyObject)null);
      var1.setlocal("test_multiline_from_comment", var4);
      var3 = null;
      var1.setline(2248);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_quote_dump$203, (PyObject)null);
      var1.setlocal("test_quote_dump", var4);
      var3 = null;
      var1.setline(2253);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_fix_eols$204, (PyObject)null);
      var1.setlocal("test_fix_eols", var4);
      var3 = null;
      var1.setline(2261);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_charset_richcomparisons$205, (PyObject)null);
      var1.setlocal("test_charset_richcomparisons", var4);
      var3 = null;
      var1.setline(2281);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_getaddresses$206, (PyObject)null);
      var1.setlocal("test_getaddresses", var4);
      var3 = null;
      var1.setline(2288);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_getaddresses_nasty$207, (PyObject)null);
      var1.setlocal("test_getaddresses_nasty", var4);
      var3 = null;
      var1.setline(2298);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_getaddresses_embedded_comment$208, PyString.fromInterned("Test proper handling of a nested comment"));
      var1.setlocal("test_getaddresses_embedded_comment", var4);
      var3 = null;
      var1.setline(2304);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_utils_quote_unquote$209, (PyObject)null);
      var1.setlocal("test_utils_quote_unquote", var4);
      var3 = null;
      var1.setline(2311);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_get_body_encoding_with_bogus_charset$210, (PyObject)null);
      var1.setlocal("test_get_body_encoding_with_bogus_charset", var4);
      var3 = null;
      var1.setline(2315);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_get_body_encoding_with_uppercase_charset$211, (PyObject)null);
      var1.setlocal("test_get_body_encoding_with_uppercase_charset", var4);
      var3 = null;
      var1.setline(2341);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_charsets_case_insensitive$212, (PyObject)null);
      var1.setlocal("test_charsets_case_insensitive", var4);
      var3 = null;
      var1.setline(2346);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_partial_falls_inside_message_delivery_status$213, (PyObject)null);
      var1.setlocal("test_partial_falls_inside_message_delivery_status", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_message_from_string$182(PyFrame var1, ThreadState var2) {
      var1.setline(2068);
      PyObject var3 = var1.getglobal("openfile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_01.txt"));
      var1.setlocal(1, var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(2070);
         PyObject var4 = var1.getlocal(1).__getattr__("read").__call__(var2);
         var1.setlocal(2, var4);
         var4 = null;
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(2072);
         var1.getlocal(1).__getattr__("close").__call__(var2);
         throw (Throwable)var5;
      }

      var1.setline(2072);
      var1.getlocal(1).__getattr__("close").__call__(var2);
      var1.setline(2073);
      var3 = var1.getglobal("email").__getattr__("message_from_string").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(2074);
      var3 = var1.getglobal("StringIO").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(2077);
      PyObject var10000 = var1.getglobal("Generator");
      PyObject[] var7 = new PyObject[]{var1.getlocal(4), Py.newInteger(0)};
      String[] var6 = new String[]{"maxheaderlen"};
      var10000 = var10000.__call__(var2, var7, var6);
      var3 = null;
      var3 = var10000;
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(2078);
      var1.getlocal(5).__getattr__("flatten").__call__(var2, var1.getlocal(3));
      var1.setline(2079);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(2), var1.getlocal(4).__getattr__("getvalue").__call__(var2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_message_from_file$183(PyFrame var1, ThreadState var2) {
      var1.setline(2082);
      PyObject var3 = var1.getglobal("openfile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_01.txt"));
      var1.setlocal(1, var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(2084);
         PyObject var4 = var1.getlocal(1).__getattr__("read").__call__(var2);
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(2085);
         var1.getlocal(1).__getattr__("seek").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
         var1.setline(2086);
         var4 = var1.getglobal("email").__getattr__("message_from_file").__call__(var2, var1.getlocal(1));
         var1.setlocal(3, var4);
         var4 = null;
         var1.setline(2087);
         var4 = var1.getglobal("StringIO").__call__(var2);
         var1.setlocal(4, var4);
         var4 = null;
         var1.setline(2090);
         PyObject var10000 = var1.getglobal("Generator");
         PyObject[] var7 = new PyObject[]{var1.getlocal(4), Py.newInteger(0)};
         String[] var5 = new String[]{"maxheaderlen"};
         var10000 = var10000.__call__(var2, var7, var5);
         var4 = null;
         var4 = var10000;
         var1.setlocal(5, var4);
         var4 = null;
         var1.setline(2091);
         var1.getlocal(5).__getattr__("flatten").__call__(var2, var1.getlocal(3));
         var1.setline(2092);
         var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(2), var1.getlocal(4).__getattr__("getvalue").__call__(var2));
      } catch (Throwable var6) {
         Py.addTraceback(var6, var1);
         var1.setline(2094);
         var1.getlocal(1).__getattr__("close").__call__(var2);
         throw (Throwable)var6;
      }

      var1.setline(2094);
      var1.getlocal(1).__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_message_from_string_with_class$184(PyFrame var1, ThreadState var2) {
      var1.setline(2097);
      PyObject var3 = var1.getlocal(0).__getattr__("assertTrue");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2098);
      var3 = var1.getglobal("openfile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_01.txt"));
      var1.setlocal(2, var3);
      var3 = null;
      var3 = null;

      PyObject var4;
      try {
         var1.setline(2100);
         var4 = var1.getlocal(2).__getattr__("read").__call__(var2);
         var1.setlocal(3, var4);
         var4 = null;
      } catch (Throwable var6) {
         Py.addTraceback(var6, var1);
         var1.setline(2102);
         var1.getlocal(2).__getattr__("close").__call__(var2);
         throw (Throwable)var6;
      }

      var1.setline(2102);
      var1.getlocal(2).__getattr__("close").__call__(var2);
      var1.setline(2104);
      PyObject[] var7 = new PyObject[]{var1.getglobal("Message")};
      var4 = Py.makeClass("MyMessage", var7, MyMessage$185);
      var1.setlocal(4, var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(2107);
      var3 = var1.getglobal("email").__getattr__("message_from_string").__call__(var2, var1.getlocal(3), var1.getlocal(4));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(2108);
      var1.getlocal(1).__call__(var2, var1.getglobal("isinstance").__call__(var2, var1.getlocal(5), var1.getlocal(4)));
      var1.setline(2110);
      var3 = var1.getglobal("openfile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_02.txt"));
      var1.setlocal(2, var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(2112);
         var4 = var1.getlocal(2).__getattr__("read").__call__(var2);
         var1.setlocal(3, var4);
         var4 = null;
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(2114);
         var1.getlocal(2).__getattr__("close").__call__(var2);
         throw (Throwable)var5;
      }

      var1.setline(2114);
      var1.getlocal(2).__getattr__("close").__call__(var2);
      var1.setline(2115);
      var3 = var1.getglobal("email").__getattr__("message_from_string").__call__(var2, var1.getlocal(3), var1.getlocal(4));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(2116);
      var3 = var1.getlocal(5).__getattr__("walk").__call__(var2).__iter__();

      while(true) {
         var1.setline(2116);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(6, var4);
         var1.setline(2117);
         var1.getlocal(1).__call__(var2, var1.getglobal("isinstance").__call__(var2, var1.getlocal(6), var1.getlocal(4)));
      }
   }

   public PyObject MyMessage$185(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(2105);
      return var1.getf_locals();
   }

   public PyObject test_message_from_file_with_class$186(PyFrame var1, ThreadState var2) {
      var1.setline(2120);
      PyObject var3 = var1.getlocal(0).__getattr__("assertTrue");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2122);
      PyObject[] var7 = new PyObject[]{var1.getglobal("Message")};
      PyObject var4 = Py.makeClass("MyMessage", var7, MyMessage$187);
      var1.setlocal(2, var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(2125);
      var3 = var1.getglobal("openfile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_01.txt"));
      var1.setlocal(3, var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(2127);
         var4 = var1.getglobal("email").__getattr__("message_from_file").__call__(var2, var1.getlocal(3), var1.getlocal(2));
         var1.setlocal(4, var4);
         var4 = null;
      } catch (Throwable var6) {
         Py.addTraceback(var6, var1);
         var1.setline(2129);
         var1.getlocal(3).__getattr__("close").__call__(var2);
         throw (Throwable)var6;
      }

      var1.setline(2129);
      var1.getlocal(3).__getattr__("close").__call__(var2);
      var1.setline(2130);
      var1.getlocal(1).__call__(var2, var1.getglobal("isinstance").__call__(var2, var1.getlocal(4), var1.getlocal(2)));
      var1.setline(2132);
      var3 = var1.getglobal("openfile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_02.txt"));
      var1.setlocal(3, var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(2134);
         var4 = var1.getglobal("email").__getattr__("message_from_file").__call__(var2, var1.getlocal(3), var1.getlocal(2));
         var1.setlocal(4, var4);
         var4 = null;
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(2136);
         var1.getlocal(3).__getattr__("close").__call__(var2);
         throw (Throwable)var5;
      }

      var1.setline(2136);
      var1.getlocal(3).__getattr__("close").__call__(var2);
      var1.setline(2137);
      var3 = var1.getlocal(4).__getattr__("walk").__call__(var2).__iter__();

      while(true) {
         var1.setline(2137);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(5, var4);
         var1.setline(2138);
         var1.getlocal(1).__call__(var2, var1.getglobal("isinstance").__call__(var2, var1.getlocal(5), var1.getlocal(2)));
      }
   }

   public PyObject MyMessage$187(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(2123);
      return var1.getf_locals();
   }

   public PyObject test__all__$188(PyFrame var1, ThreadState var2) {
      var1.setline(2141);
      PyObject var3 = var1.getglobal("__import__").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("email"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2143);
      var3 = var1.getlocal(1).__getattr__("__all__").__getslice__((PyObject)null, (PyObject)null, (PyObject)null);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2144);
      var1.getlocal(2).__getattr__("sort").__call__(var2);
      var1.setline(2145);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("Charset"), PyString.fromInterned("Encoders"), PyString.fromInterned("Errors"), PyString.fromInterned("Generator"), PyString.fromInterned("Header"), PyString.fromInterned("Iterators"), PyString.fromInterned("MIMEAudio"), PyString.fromInterned("MIMEBase"), PyString.fromInterned("MIMEImage"), PyString.fromInterned("MIMEMessage"), PyString.fromInterned("MIMEMultipart"), PyString.fromInterned("MIMENonMultipart"), PyString.fromInterned("MIMEText"), PyString.fromInterned("Message"), PyString.fromInterned("Parser"), PyString.fromInterned("Utils"), PyString.fromInterned("base64MIME"), PyString.fromInterned("base64mime"), PyString.fromInterned("charset"), PyString.fromInterned("encoders"), PyString.fromInterned("errors"), PyString.fromInterned("generator"), PyString.fromInterned("header"), PyString.fromInterned("iterators"), PyString.fromInterned("message"), PyString.fromInterned("message_from_file"), PyString.fromInterned("message_from_string"), PyString.fromInterned("mime"), PyString.fromInterned("parser"), PyString.fromInterned("quopriMIME"), PyString.fromInterned("quoprimime"), PyString.fromInterned("utils")})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_formatdate$189(PyFrame var1, ThreadState var2) {
      var1.setline(2160);
      PyObject var3 = var1.getglobal("time").__getattr__("time").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2161);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getglobal("utils").__getattr__("parsedate").__call__(var2, var1.getglobal("utils").__getattr__("formatdate").__call__(var2, var1.getlocal(1))).__getslice__((PyObject)null, Py.newInteger(6), (PyObject)null), var1.getglobal("time").__getattr__("gmtime").__call__(var2, var1.getlocal(1)).__getslice__((PyObject)null, Py.newInteger(6), (PyObject)null));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_formatdate_localtime$190(PyFrame var1, ThreadState var2) {
      var1.setline(2165);
      PyObject var3 = var1.getglobal("time").__getattr__("time").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2166);
      PyObject var10000 = var1.getlocal(0).__getattr__("assertEqual");
      PyObject var10002 = var1.getglobal("utils").__getattr__("parsedate");
      PyObject var10004 = var1.getglobal("utils").__getattr__("formatdate");
      PyObject[] var5 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"localtime"};
      var10004 = var10004.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__(var2, var10002.__call__(var2, var10004).__getslice__((PyObject)null, Py.newInteger(6), (PyObject)null), var1.getglobal("time").__getattr__("localtime").__call__(var2, var1.getlocal(1)).__getslice__((PyObject)null, Py.newInteger(6), (PyObject)null));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_formatdate_usegmt$191(PyFrame var1, ThreadState var2) {
      var1.setline(2171);
      PyObject var3 = var1.getglobal("time").__getattr__("time").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2172);
      PyObject var10000 = var1.getlocal(0).__getattr__("assertEqual");
      PyObject var10002 = var1.getglobal("utils").__getattr__("formatdate");
      PyObject[] var5 = new PyObject[]{var1.getlocal(1), var1.getglobal("False")};
      String[] var4 = new String[]{"localtime"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__(var2, var10002, var1.getglobal("time").__getattr__("strftime").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("%a, %d %b %Y %H:%M:%S -0000"), (PyObject)var1.getglobal("time").__getattr__("gmtime").__call__(var2, var1.getlocal(1))));
      var1.setline(2175);
      var10000 = var1.getlocal(0).__getattr__("assertEqual");
      var10002 = var1.getglobal("utils").__getattr__("formatdate");
      var5 = new PyObject[]{var1.getlocal(1), var1.getglobal("False"), var1.getglobal("True")};
      var4 = new String[]{"localtime", "usegmt"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__(var2, var10002, var1.getglobal("time").__getattr__("strftime").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("%a, %d %b %Y %H:%M:%S GMT"), (PyObject)var1.getglobal("time").__getattr__("gmtime").__call__(var2, var1.getlocal(1))));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_parsedate_none$192(PyFrame var1, ThreadState var2) {
      var1.setline(2180);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getglobal("utils").__getattr__("parsedate").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("")), var1.getglobal("None"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_parsedate_compact$193(PyFrame var1, ThreadState var2) {
      var1.setline(2184);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getglobal("utils").__getattr__("parsedate").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Wed,3 Apr 2002 14:58:26 +0800")), var1.getglobal("utils").__getattr__("parsedate").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Wed, 3 Apr 2002 14:58:26 +0800")));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_parsedate_no_dayofweek$194(PyFrame var1, ThreadState var2) {
      var1.setline(2188);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2189);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("utils").__getattr__("parsedate_tz").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("25 Feb 2003 13:47:26 -0800")), (PyObject)(new PyTuple(new PyObject[]{Py.newInteger(2003), Py.newInteger(2), Py.newInteger(25), Py.newInteger(13), Py.newInteger(47), Py.newInteger(26), Py.newInteger(0), Py.newInteger(1), Py.newInteger(-1), Py.newInteger(-28800)})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_parsedate_compact_no_dayofweek$195(PyFrame var1, ThreadState var2) {
      var1.setline(2193);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2194);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("utils").__getattr__("parsedate_tz").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("5 Feb 2003 13:47:26 -0800")), (PyObject)(new PyTuple(new PyObject[]{Py.newInteger(2003), Py.newInteger(2), Py.newInteger(5), Py.newInteger(13), Py.newInteger(47), Py.newInteger(26), Py.newInteger(0), Py.newInteger(1), Py.newInteger(-1), Py.newInteger(-28800)})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_parsedate_acceptable_to_time_functions$196(PyFrame var1, ThreadState var2) {
      var1.setline(2198);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2199);
      var3 = var1.getglobal("utils").__getattr__("parsedate").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("5 Feb 2003 13:47:26 -0800"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2200);
      var3 = var1.getglobal("int").__call__(var2, var1.getglobal("time").__getattr__("mktime").__call__(var2, var1.getlocal(2)));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(2201);
      var1.getlocal(1).__call__(var2, var1.getglobal("time").__getattr__("localtime").__call__(var2, var1.getlocal(3)).__getslice__((PyObject)null, Py.newInteger(6), (PyObject)null), var1.getlocal(2).__getslice__((PyObject)null, Py.newInteger(6), (PyObject)null));
      var1.setline(2202);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("int").__call__(var2, var1.getglobal("time").__getattr__("strftime").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("%Y"), (PyObject)var1.getlocal(2))), (PyObject)Py.newInteger(2003));
      var1.setline(2203);
      var3 = var1.getglobal("utils").__getattr__("parsedate_tz").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("5 Feb 2003 13:47:26 -0800"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2204);
      var3 = var1.getglobal("int").__call__(var2, var1.getglobal("time").__getattr__("mktime").__call__(var2, var1.getlocal(2).__getslice__((PyObject)null, Py.newInteger(9), (PyObject)null)));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(2205);
      var1.getlocal(1).__call__(var2, var1.getglobal("time").__getattr__("localtime").__call__(var2, var1.getlocal(3)).__getslice__((PyObject)null, Py.newInteger(6), (PyObject)null), var1.getlocal(2).__getslice__((PyObject)null, Py.newInteger(6), (PyObject)null));
      var1.setline(2206);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("int").__call__(var2, var1.getglobal("time").__getattr__("strftime").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("%Y"), (PyObject)var1.getlocal(2).__getslice__((PyObject)null, Py.newInteger(9), (PyObject)null))), (PyObject)Py.newInteger(2003));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_parseaddr_empty$197(PyFrame var1, ThreadState var2) {
      var1.setline(2209);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("utils").__getattr__("parseaddr").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<>")), (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned(""), PyString.fromInterned("")})));
      var1.setline(2210);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("utils").__getattr__("formataddr").__call__(var2, var1.getglobal("utils").__getattr__("parseaddr").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<>"))), (PyObject)PyString.fromInterned(""));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_noquote_dump$198(PyFrame var1, ThreadState var2) {
      var1.setline(2213);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("utils").__getattr__("formataddr").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("A Silly Person"), PyString.fromInterned("person@dom.ain")}))), (PyObject)PyString.fromInterned("A Silly Person <person@dom.ain>"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_escape_dump$199(PyFrame var1, ThreadState var2) {
      var1.setline(2218);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("utils").__getattr__("formataddr").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("A (Very) Silly Person"), PyString.fromInterned("person@dom.ain")}))), (PyObject)PyString.fromInterned("\"A \\(Very\\) Silly Person\" <person@dom.ain>"));
      var1.setline(2221);
      PyString var3 = PyString.fromInterned("A \\(Special\\) Person");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2222);
      var3 = PyString.fromInterned("person@dom.ain");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2223);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("utils").__getattr__("parseaddr").__call__(var2, var1.getglobal("utils").__getattr__("formataddr").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)})))), (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_escape_backslashes$200(PyFrame var1, ThreadState var2) {
      var1.setline(2226);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("utils").__getattr__("formataddr").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("Arthur \\Backslash\\ Foobar"), PyString.fromInterned("person@dom.ain")}))), (PyObject)PyString.fromInterned("\"Arthur \\\\Backslash\\\\ Foobar\" <person@dom.ain>"));
      var1.setline(2229);
      PyString var3 = PyString.fromInterned("Arthur \\Backslash\\ Foobar");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2230);
      var3 = PyString.fromInterned("person@dom.ain");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2231);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("utils").__getattr__("parseaddr").__call__(var2, var1.getglobal("utils").__getattr__("formataddr").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)})))), (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_name_with_dot$201(PyFrame var1, ThreadState var2) {
      var1.setline(2234);
      PyString var3 = PyString.fromInterned("John X. Doe <jxd@example.com>");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2235);
      var3 = PyString.fromInterned("\"John X. Doe\" <jxd@example.com>");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2236);
      PyTuple var6 = new PyTuple(new PyObject[]{PyString.fromInterned("John X. Doe"), PyString.fromInterned("jxd@example.com")});
      PyObject[] var4 = Py.unpackSequence(var6, 2);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;
      var1.setline(2237);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("utils").__getattr__("parseaddr").__call__(var2, var1.getlocal(1)), (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(4)})));
      var1.setline(2238);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("utils").__getattr__("parseaddr").__call__(var2, var1.getlocal(2)), (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(4)})));
      var1.setline(2240);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getglobal("utils").__getattr__("formataddr").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(4)}))), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_multiline_from_comment$202(PyFrame var1, ThreadState var2) {
      var1.setline(2243);
      PyString var3 = PyString.fromInterned("Foo\n\tBar <foo@example.com>");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2246);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("utils").__getattr__("parseaddr").__call__(var2, var1.getlocal(1)), (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("Foo Bar"), PyString.fromInterned("foo@example.com")})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_quote_dump$203(PyFrame var1, ThreadState var2) {
      var1.setline(2249);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("utils").__getattr__("formataddr").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("A Silly; Person"), PyString.fromInterned("person@dom.ain")}))), (PyObject)PyString.fromInterned("\"A Silly; Person\" <person@dom.ain>"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_fix_eols$204(PyFrame var1, ThreadState var2) {
      var1.setline(2254);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2255);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("utils").__getattr__("fix_eols").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hello")), (PyObject)PyString.fromInterned("hello"));
      var1.setline(2256);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("utils").__getattr__("fix_eols").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hello\n")), (PyObject)PyString.fromInterned("hello\r\n"));
      var1.setline(2257);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("utils").__getattr__("fix_eols").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hello\r")), (PyObject)PyString.fromInterned("hello\r\n"));
      var1.setline(2258);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("utils").__getattr__("fix_eols").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hello\r\n")), (PyObject)PyString.fromInterned("hello\r\n"));
      var1.setline(2259);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("utils").__getattr__("fix_eols").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hello\n\r")), (PyObject)PyString.fromInterned("hello\r\n\r\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_charset_richcomparisons$205(PyFrame var1, ThreadState var2) {
      var1.setline(2262);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2263);
      var3 = var1.getlocal(0).__getattr__("assertNotEqual");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2264);
      var3 = var1.getglobal("Charset").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(2265);
      var3 = var1.getglobal("Charset").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(2266);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("us-ascii"));
      var1.setline(2267);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("US-ASCII"));
      var1.setline(2268);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("Us-AsCiI"));
      var1.setline(2269);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("us-ascii"), (PyObject)var1.getlocal(3));
      var1.setline(2270);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("US-ASCII"), (PyObject)var1.getlocal(3));
      var1.setline(2271);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Us-AsCiI"), (PyObject)var1.getlocal(3));
      var1.setline(2272);
      var1.getlocal(2).__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("usascii"));
      var1.setline(2273);
      var1.getlocal(2).__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("USASCII"));
      var1.setline(2274);
      var1.getlocal(2).__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("UsAsCiI"));
      var1.setline(2275);
      var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("usascii"), (PyObject)var1.getlocal(3));
      var1.setline(2276);
      var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("USASCII"), (PyObject)var1.getlocal(3));
      var1.setline(2277);
      var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("UsAsCiI"), (PyObject)var1.getlocal(3));
      var1.setline(2278);
      var1.getlocal(1).__call__(var2, var1.getlocal(3), var1.getlocal(4));
      var1.setline(2279);
      var1.getlocal(1).__call__(var2, var1.getlocal(4), var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_getaddresses$206(PyFrame var1, ThreadState var2) {
      var1.setline(2282);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2283);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("utils").__getattr__("getaddresses").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("aperson@dom.ain (Al Person)"), PyString.fromInterned("Bud Person <bperson@dom.ain>")}))), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("Al Person"), PyString.fromInterned("aperson@dom.ain")}), new PyTuple(new PyObject[]{PyString.fromInterned("Bud Person"), PyString.fromInterned("bperson@dom.ain")})})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_getaddresses_nasty$207(PyFrame var1, ThreadState var2) {
      var1.setline(2289);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2290);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("utils").__getattr__("getaddresses").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("foo: ;")}))), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned(""), PyString.fromInterned("")})})));
      var1.setline(2291);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("utils").__getattr__("getaddresses").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("[]*-- =~$")}))), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned(""), PyString.fromInterned("")}), new PyTuple(new PyObject[]{PyString.fromInterned(""), PyString.fromInterned("")}), new PyTuple(new PyObject[]{PyString.fromInterned(""), PyString.fromInterned("*--")})})));
      var1.setline(2294);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("utils").__getattr__("getaddresses").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("foo: ;"), PyString.fromInterned("\"Jason R. Mastaler\" <jason@dom.ain>")}))), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned(""), PyString.fromInterned("")}), new PyTuple(new PyObject[]{PyString.fromInterned("Jason R. Mastaler"), PyString.fromInterned("jason@dom.ain")})})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_getaddresses_embedded_comment$208(PyFrame var1, ThreadState var2) {
      var1.setline(2299);
      PyString.fromInterned("Test proper handling of a nested comment");
      var1.setline(2300);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2301);
      var3 = var1.getglobal("utils").__getattr__("getaddresses").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("User ((nested comment)) <foo@bar.com>")})));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2302);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(1)), (PyObject)PyString.fromInterned("foo@bar.com"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_utils_quote_unquote$209(PyFrame var1, ThreadState var2) {
      var1.setline(2305);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2306);
      var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2307);
      PyObject var10000 = var1.getlocal(2).__getattr__("add_header");
      PyObject[] var5 = new PyObject[]{PyString.fromInterned("content-disposition"), PyString.fromInterned("attachment"), PyString.fromInterned("foo\\wacky\"name")};
      String[] var4 = new String[]{"filename"};
      var10000.__call__(var2, var5, var4);
      var3 = null;
      var1.setline(2309);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_filename").__call__(var2), (PyObject)PyString.fromInterned("foo\\wacky\"name"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_get_body_encoding_with_bogus_charset$210(PyFrame var1, ThreadState var2) {
      var1.setline(2312);
      PyObject var3 = var1.getglobal("Charset").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("not a charset"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2313);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("get_body_encoding").__call__(var2), (PyObject)PyString.fromInterned("base64"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_get_body_encoding_with_uppercase_charset$211(PyFrame var1, ThreadState var2) {
      var1.setline(2316);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2317);
      var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2318);
      PyString var5 = PyString.fromInterned("text/plain; charset=UTF-8");
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("Content-Type"), var5);
      var3 = null;
      var1.setline(2319);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getitem__(PyString.fromInterned("content-type")), (PyObject)PyString.fromInterned("text/plain; charset=UTF-8"));
      var1.setline(2320);
      var3 = var1.getlocal(2).__getattr__("get_charsets").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(2321);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(3)), (PyObject)Py.newInteger(1));
      var1.setline(2322);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getitem__(Py.newInteger(0)), (PyObject)PyString.fromInterned("utf-8"));
      var1.setline(2323);
      var3 = var1.getglobal("Charset").__call__(var2, var1.getlocal(3).__getitem__(Py.newInteger(0)));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(2324);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(4).__getattr__("get_body_encoding").__call__(var2), (PyObject)PyString.fromInterned("base64"));
      var1.setline(2325);
      PyObject var10000 = var1.getlocal(2).__getattr__("set_payload");
      PyObject[] var6 = new PyObject[]{PyString.fromInterned("hello world"), var1.getlocal(4)};
      String[] var4 = new String[]{"charset"};
      var10000.__call__(var2, var6, var4);
      var3 = null;
      var1.setline(2326);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_payload").__call__(var2), (PyObject)PyString.fromInterned("aGVsbG8gd29ybGQ=\n"));
      var1.setline(2327);
      var10000 = var1.getlocal(1);
      PyObject var10002 = var1.getlocal(2).__getattr__("get_payload");
      var6 = new PyObject[]{var1.getglobal("True")};
      var4 = new String[]{"decode"};
      var10002 = var10002.__call__(var2, var6, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("hello world"));
      var1.setline(2328);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getitem__(PyString.fromInterned("content-transfer-encoding")), (PyObject)PyString.fromInterned("base64"));
      var1.setline(2330);
      var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2331);
      var5 = PyString.fromInterned("text/plain; charset=\"US-ASCII\"");
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("Content-Type"), var5);
      var3 = null;
      var1.setline(2332);
      var3 = var1.getlocal(2).__getattr__("get_charsets").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(2333);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(3)), (PyObject)Py.newInteger(1));
      var1.setline(2334);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getitem__(Py.newInteger(0)), (PyObject)PyString.fromInterned("us-ascii"));
      var1.setline(2335);
      var3 = var1.getglobal("Charset").__call__(var2, var1.getlocal(3).__getitem__(Py.newInteger(0)));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(2336);
      var1.getlocal(1).__call__(var2, var1.getlocal(4).__getattr__("get_body_encoding").__call__(var2), var1.getglobal("encoders").__getattr__("encode_7or8bit"));
      var1.setline(2337);
      var10000 = var1.getlocal(2).__getattr__("set_payload");
      var6 = new PyObject[]{PyString.fromInterned("hello world"), var1.getlocal(4)};
      var4 = new String[]{"charset"};
      var10000.__call__(var2, var6, var4);
      var3 = null;
      var1.setline(2338);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_payload").__call__(var2), (PyObject)PyString.fromInterned("hello world"));
      var1.setline(2339);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getitem__(PyString.fromInterned("content-transfer-encoding")), (PyObject)PyString.fromInterned("7bit"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_charsets_case_insensitive$212(PyFrame var1, ThreadState var2) {
      var1.setline(2342);
      PyObject var3 = var1.getglobal("Charset").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("us-ascii"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2343);
      var3 = var1.getglobal("Charset").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("US-ASCII"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2344);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(1).__getattr__("get_body_encoding").__call__(var2), var1.getlocal(2).__getattr__("get_body_encoding").__call__(var2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_partial_falls_inside_message_delivery_status$213(PyFrame var1, ThreadState var2) {
      var1.setline(2347);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2352);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_43.txt"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2353);
      var3 = var1.getglobal("StringIO").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(2354);
      var1.getglobal("iterators").__getattr__("_structure").__call__(var2, var1.getlocal(2), var1.getlocal(3));
      var1.setline(2355);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("getvalue").__call__(var2), (PyObject)PyString.fromInterned("multipart/report\n    text/plain\n    message/delivery-status\n        text/plain\n        text/plain\n        text/plain\n        text/plain\n        text/plain\n        text/plain\n        text/plain\n        text/plain\n        text/plain\n        text/plain\n        text/plain\n        text/plain\n        text/plain\n        text/plain\n        text/plain\n        text/plain\n        text/plain\n        text/plain\n        text/plain\n        text/plain\n        text/plain\n        text/plain\n        text/plain\n        text/plain\n        text/plain\n        text/plain\n    text/rfc822-headers\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestIterators$214(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(2392);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_body_line_iterator$215, (PyObject)null);
      var1.setlocal("test_body_line_iterator", var4);
      var3 = null;
      var1.setline(2412);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_typed_subpart_iterator$216, (PyObject)null);
      var1.setlocal("test_typed_subpart_iterator", var4);
      var3 = null;
      var1.setline(2429);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_typed_subpart_iterator_default_type$217, (PyObject)null);
      var1.setlocal("test_typed_subpart_iterator_default_type", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_body_line_iterator$215(PyFrame var1, ThreadState var2) {
      var1.setline(2393);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2394);
      var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2396);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_01.txt"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(2397);
      var3 = var1.getglobal("iterators").__getattr__("body_line_iterator").__call__(var2, var1.getlocal(3));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(2398);
      var3 = var1.getglobal("list").__call__(var2, var1.getlocal(4));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(2399);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(5)), (PyObject)Py.newInteger(6));
      var1.setline(2400);
      var1.getlocal(2).__call__(var2, var1.getglobal("EMPTYSTRING").__getattr__("join").__call__(var2, var1.getlocal(5)), var1.getlocal(3).__getattr__("get_payload").__call__(var2));
      var1.setline(2402);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_02.txt"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(2403);
      var3 = var1.getglobal("iterators").__getattr__("body_line_iterator").__call__(var2, var1.getlocal(3));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(2404);
      var3 = var1.getglobal("list").__call__(var2, var1.getlocal(4));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(2405);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(5)), (PyObject)Py.newInteger(43));
      var1.setline(2406);
      var3 = var1.getglobal("openfile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_19.txt"));
      var1.setlocal(6, var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(2408);
         var1.getlocal(2).__call__(var2, var1.getglobal("EMPTYSTRING").__getattr__("join").__call__(var2, var1.getlocal(5)), var1.getlocal(6).__getattr__("read").__call__(var2));
      } catch (Throwable var4) {
         Py.addTraceback(var4, var1);
         var1.setline(2410);
         var1.getlocal(6).__getattr__("close").__call__(var2);
         throw (Throwable)var4;
      }

      var1.setline(2410);
      var1.getlocal(6).__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_typed_subpart_iterator$216(PyFrame var1, ThreadState var2) {
      var1.setline(2413);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2414);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_04.txt"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2415);
      var3 = var1.getglobal("iterators").__getattr__("typed_subpart_iterator").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("text"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(2416);
      PyList var6 = new PyList(Py.EmptyObjects);
      var1.setlocal(4, var6);
      var3 = null;
      var1.setline(2417);
      PyInteger var7 = Py.newInteger(0);
      var1.setlocal(5, var7);
      var3 = null;
      var1.setline(2418);
      var3 = var1.getlocal(3).__iter__();

      while(true) {
         var1.setline(2418);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(2421);
            var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(5), (PyObject)Py.newInteger(2));
            var1.setline(2422);
            var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("EMPTYSTRING").__getattr__("join").__call__(var2, var1.getlocal(4)), (PyObject)PyString.fromInterned("a simple kind of mirror\nto reflect upon our own\na simple kind of mirror\nto reflect upon our own\n"));
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(6, var4);
         var1.setline(2419);
         PyObject var5 = var1.getlocal(5);
         var5 = var5._iadd(Py.newInteger(1));
         var1.setlocal(5, var5);
         var1.setline(2420);
         var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(6).__getattr__("get_payload").__call__(var2));
      }
   }

   public PyObject test_typed_subpart_iterator_default_type$217(PyFrame var1, ThreadState var2) {
      var1.setline(2430);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2431);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_03.txt"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2432);
      var3 = var1.getglobal("iterators").__getattr__("typed_subpart_iterator").__call__((ThreadState)var2, var1.getlocal(2), (PyObject)PyString.fromInterned("text"), (PyObject)PyString.fromInterned("plain"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(2433);
      PyList var6 = new PyList(Py.EmptyObjects);
      var1.setlocal(4, var6);
      var3 = null;
      var1.setline(2434);
      PyInteger var7 = Py.newInteger(0);
      var1.setlocal(5, var7);
      var3 = null;
      var1.setline(2435);
      var3 = var1.getlocal(3).__iter__();

      while(true) {
         var1.setline(2435);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(2438);
            var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(5), (PyObject)Py.newInteger(1));
            var1.setline(2439);
            var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("EMPTYSTRING").__getattr__("join").__call__(var2, var1.getlocal(4)), (PyObject)PyString.fromInterned("\nHi,\n\nDo you like this message?\n\n-Me\n"));
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(6, var4);
         var1.setline(2436);
         PyObject var5 = var1.getlocal(5);
         var5 = var5._iadd(Py.newInteger(1));
         var1.setlocal(5, var5);
         var1.setline(2437);
         var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(6).__getattr__("get_payload").__call__(var2));
      }
   }

   public PyObject TestParsers$218(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(2451);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_header_parser$219, (PyObject)null);
      var1.setlocal("test_header_parser", var4);
      var3 = null;
      var1.setline(2465);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_whitespace_continuation$220, (PyObject)null);
      var1.setlocal("test_whitespace_continuation", var4);
      var3 = null;
      var1.setline(2483);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_whitespace_continuation_last_header$221, (PyObject)null);
      var1.setlocal("test_whitespace_continuation_last_header", var4);
      var3 = null;
      var1.setline(2501);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_crlf_separation$222, (PyObject)null);
      var1.setlocal("test_crlf_separation", var4);
      var3 = null;
      var1.setline(2515);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_multipart_digest_with_extra_mime_headers$223, (PyObject)null);
      var1.setlocal("test_multipart_digest_with_extra_mime_headers", var4);
      var3 = null;
      var1.setline(2549);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_three_lines$224, (PyObject)null);
      var1.setlocal("test_three_lines", var4);
      var3 = null;
      var1.setline(2557);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_strip_line_feed_and_carriage_return_in_headers$225, (PyObject)null);
      var1.setlocal("test_strip_line_feed_and_carriage_return_in_headers", var4);
      var3 = null;
      var1.setline(2568);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_rfc2822_header_syntax$226, (PyObject)null);
      var1.setlocal("test_rfc2822_header_syntax", var4);
      var3 = null;
      var1.setline(2578);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_rfc2822_space_not_allowed_in_header$227, (PyObject)null);
      var1.setlocal("test_rfc2822_space_not_allowed_in_header", var4);
      var3 = null;
      var1.setline(2584);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_rfc2822_one_character_header$228, (PyObject)null);
      var1.setlocal("test_rfc2822_one_character_header", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_header_parser$219(PyFrame var1, ThreadState var2) {
      var1.setline(2452);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2454);
      var3 = var1.getglobal("openfile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_02.txt"));
      var1.setlocal(2, var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(2456);
         PyObject var4 = var1.getglobal("HeaderParser").__call__(var2).__getattr__("parse").__call__(var2, var1.getlocal(2));
         var1.setlocal(3, var4);
         var4 = null;
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(2458);
         var1.getlocal(2).__getattr__("close").__call__(var2);
         throw (Throwable)var5;
      }

      var1.setline(2458);
      var1.getlocal(2).__getattr__("close").__call__(var2);
      var1.setline(2459);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getitem__(PyString.fromInterned("from")), (PyObject)PyString.fromInterned("ppp-request@zzz.org"));
      var1.setline(2460);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getitem__(PyString.fromInterned("to")), (PyObject)PyString.fromInterned("ppp@zzz.org"));
      var1.setline(2461);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("multipart/mixed"));
      var1.setline(2462);
      var1.getlocal(0).__getattr__("assertFalse").__call__(var2, var1.getlocal(3).__getattr__("is_multipart").__call__(var2));
      var1.setline(2463);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getglobal("isinstance").__call__(var2, var1.getlocal(3).__getattr__("get_payload").__call__(var2), var1.getglobal("str")));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_whitespace_continuation$220(PyFrame var1, ThreadState var2) {
      var1.setline(2466);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2469);
      var3 = var1.getglobal("email").__getattr__("message_from_string").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("From: aperson@dom.ain\nTo: bperson@dom.ain\nSubject: the next line has a space on it\n \nDate: Mon, 8 Apr 2002 15:09:19 -0400\nMessage-ID: spam\n\nHere's the message body\n"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2479);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getitem__(PyString.fromInterned("subject")), (PyObject)PyString.fromInterned("the next line has a space on it\n "));
      var1.setline(2480);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getitem__(PyString.fromInterned("message-id")), (PyObject)PyString.fromInterned("spam"));
      var1.setline(2481);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_payload").__call__(var2), (PyObject)PyString.fromInterned("Here's the message body\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_whitespace_continuation_last_header$221(PyFrame var1, ThreadState var2) {
      var1.setline(2484);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2487);
      var3 = var1.getglobal("email").__getattr__("message_from_string").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("From: aperson@dom.ain\nTo: bperson@dom.ain\nDate: Mon, 8 Apr 2002 15:09:19 -0400\nMessage-ID: spam\nSubject: the next line has a space on it\n \n\nHere's the message body\n"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2497);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getitem__(PyString.fromInterned("subject")), (PyObject)PyString.fromInterned("the next line has a space on it\n "));
      var1.setline(2498);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getitem__(PyString.fromInterned("message-id")), (PyObject)PyString.fromInterned("spam"));
      var1.setline(2499);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_payload").__call__(var2), (PyObject)PyString.fromInterned("Here's the message body\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_crlf_separation$222(PyFrame var1, ThreadState var2) {
      var1.setline(2502);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2503);
      PyObject var10000 = var1.getglobal("openfile");
      PyObject[] var6 = new PyObject[]{PyString.fromInterned("msg_26.txt"), PyString.fromInterned("rb")};
      String[] var4 = new String[]{"mode"};
      var10000 = var10000.__call__(var2, var6, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(2, var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(2505);
         PyObject var7 = var1.getglobal("Parser").__call__(var2).__getattr__("parse").__call__(var2, var1.getlocal(2));
         var1.setlocal(3, var7);
         var4 = null;
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(2507);
         var1.getlocal(2).__getattr__("close").__call__(var2);
         throw (Throwable)var5;
      }

      var1.setline(2507);
      var1.getlocal(2).__getattr__("close").__call__(var2);
      var1.setline(2508);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(3).__getattr__("get_payload").__call__(var2)), (PyObject)Py.newInteger(2));
      var1.setline(2509);
      var3 = var1.getlocal(3).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(2510);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(4).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("text/plain"));
      var1.setline(2511);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(4).__getattr__("get_payload").__call__(var2), (PyObject)PyString.fromInterned("Simple email with attachment.\r\n\r\n"));
      var1.setline(2512);
      var3 = var1.getlocal(3).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(2513);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(5).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("application/riscos"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_multipart_digest_with_extra_mime_headers$223(PyFrame var1, ThreadState var2) {
      var1.setline(2516);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2517);
      var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2518);
      var3 = var1.getglobal("openfile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_28.txt"));
      var1.setlocal(3, var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(2520);
         PyObject var4 = var1.getglobal("email").__getattr__("message_from_file").__call__(var2, var1.getlocal(3));
         var1.setlocal(4, var4);
         var4 = null;
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(2522);
         var1.getlocal(3).__getattr__("close").__call__(var2);
         throw (Throwable)var5;
      }

      var1.setline(2522);
      var1.getlocal(3).__getattr__("close").__call__(var2);
      var1.setline(2529);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(4).__getattr__("is_multipart").__call__(var2), (PyObject)Py.newInteger(1));
      var1.setline(2530);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(4).__getattr__("get_payload").__call__(var2)), (PyObject)Py.newInteger(2));
      var1.setline(2531);
      var3 = var1.getlocal(4).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(2532);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(5).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("message/rfc822"));
      var1.setline(2533);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(5).__getattr__("is_multipart").__call__(var2), (PyObject)Py.newInteger(1));
      var1.setline(2534);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(5).__getattr__("get_payload").__call__(var2)), (PyObject)Py.newInteger(1));
      var1.setline(2535);
      var3 = var1.getlocal(5).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(2536);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(6).__getattr__("is_multipart").__call__(var2), (PyObject)Py.newInteger(0));
      var1.setline(2537);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(6).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("text/plain"));
      var1.setline(2538);
      var1.getlocal(2).__call__((ThreadState)var2, (PyObject)var1.getlocal(6).__getattr__("get_payload").__call__(var2), (PyObject)PyString.fromInterned("message 1\n"));
      var1.setline(2540);
      var3 = var1.getlocal(4).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(2541);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(7).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("message/rfc822"));
      var1.setline(2542);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(7).__getattr__("is_multipart").__call__(var2), (PyObject)Py.newInteger(1));
      var1.setline(2543);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(7).__getattr__("get_payload").__call__(var2)), (PyObject)Py.newInteger(1));
      var1.setline(2544);
      var3 = var1.getlocal(7).__getattr__("get_payload").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
      var1.setlocal(8, var3);
      var3 = null;
      var1.setline(2545);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(8).__getattr__("is_multipart").__call__(var2), (PyObject)Py.newInteger(0));
      var1.setline(2546);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(8).__getattr__("get_content_type").__call__(var2), (PyObject)PyString.fromInterned("text/plain"));
      var1.setline(2547);
      var1.getlocal(2).__call__((ThreadState)var2, (PyObject)var1.getlocal(8).__getattr__("get_payload").__call__(var2), (PyObject)PyString.fromInterned("message 2\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_three_lines$224(PyFrame var1, ThreadState var2) {
      var1.setline(2551);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned("From: Andrew Person <aperson@dom.ain"), PyString.fromInterned("Subject: Test"), PyString.fromInterned("Date: Tue, 20 Aug 2002 16:43:45 +1000")});
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2554);
      PyObject var4 = var1.getglobal("email").__getattr__("message_from_string").__call__(var2, var1.getglobal("NL").__getattr__("join").__call__(var2, var1.getlocal(1)));
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(2555);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getitem__(PyString.fromInterned("date")), (PyObject)PyString.fromInterned("Tue, 20 Aug 2002 16:43:45 +1000"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_strip_line_feed_and_carriage_return_in_headers$225(PyFrame var1, ThreadState var2) {
      var1.setline(2558);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2560);
      PyString var4 = PyString.fromInterned("text");
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(2561);
      var4 = PyString.fromInterned("more text");
      var1.setlocal(3, var4);
      var3 = null;
      var1.setline(2562);
      var3 = PyString.fromInterned("Header: %s\r\nNext-Header: %s\r\n\r\nBody\r\n\r\n")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3)}));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(2564);
      var3 = var1.getglobal("email").__getattr__("message_from_string").__call__(var2, var1.getlocal(4));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(2565);
      var1.getlocal(1).__call__(var2, var1.getlocal(5).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Header")), var1.getlocal(2));
      var1.setline(2566);
      var1.getlocal(1).__call__(var2, var1.getlocal(5).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Next-Header")), var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_rfc2822_header_syntax$226(PyFrame var1, ThreadState var2) {
      var1.setline(2569);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2570);
      PyString var4 = PyString.fromInterned(">From: foo\nFrom: bar\n!\"#QUX;~: zoo\n\nbody");
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(2571);
      var3 = var1.getglobal("email").__getattr__("message_from_string").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(2572);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(3).__getattr__("keys").__call__(var2)), (PyObject)Py.newInteger(3));
      var1.setline(2573);
      var3 = var1.getlocal(3).__getattr__("keys").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(2574);
      var1.getlocal(4).__getattr__("sort").__call__(var2);
      var1.setline(2575);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("!\"#QUX;~"), PyString.fromInterned(">From"), PyString.fromInterned("From")})));
      var1.setline(2576);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("get_payload").__call__(var2), (PyObject)PyString.fromInterned("body"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_rfc2822_space_not_allowed_in_header$227(PyFrame var1, ThreadState var2) {
      var1.setline(2579);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2580);
      PyString var4 = PyString.fromInterned(">From foo@example.com 11:25:53\nFrom: bar\n!\"#QUX;~: zoo\n\nbody");
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(2581);
      var3 = var1.getglobal("email").__getattr__("message_from_string").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(2582);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(3).__getattr__("keys").__call__(var2)), (PyObject)Py.newInteger(0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_rfc2822_one_character_header$228(PyFrame var1, ThreadState var2) {
      var1.setline(2585);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2586);
      PyString var4 = PyString.fromInterned("A: first header\nB: second header\nCC: third header\n\nbody");
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(2587);
      var3 = var1.getglobal("email").__getattr__("message_from_string").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(2588);
      var3 = var1.getlocal(3).__getattr__("keys").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(2589);
      var1.getlocal(4).__getattr__("sort").__call__(var2);
      var1.setline(2590);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("A"), PyString.fromInterned("B"), PyString.fromInterned("CC")})));
      var1.setline(2591);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("get_payload").__call__(var2), (PyObject)PyString.fromInterned("body"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestBase64$229(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(2596);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_len$230, (PyObject)null);
      var1.setlocal("test_len", var4);
      var3 = null;
      var1.setline(2609);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_decode$231, (PyObject)null);
      var1.setlocal("test_decode", var4);
      var3 = null;
      var1.setline(2616);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_encode$232, (PyObject)null);
      var1.setlocal("test_encode", var4);
      var3 = null;
      var1.setline(2638);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_header_encode$233, (PyObject)null);
      var1.setlocal("test_header_encode", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_len$230(PyFrame var1, ThreadState var2) {
      var1.setline(2597);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2598);
      PyObject var10000 = var1.getlocal(1);
      PyObject var10002 = var1.getglobal("base64mime").__getattr__("base64_len").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hello"));
      PyObject var10003 = var1.getglobal("len");
      PyObject var10005 = var1.getglobal("base64mime").__getattr__("encode");
      PyObject[] var6 = new PyObject[]{PyString.fromInterned("hello"), PyString.fromInterned("")};
      String[] var4 = new String[]{"eol"};
      var10005 = var10005.__call__(var2, var6, var4);
      var3 = null;
      var10000.__call__(var2, var10002, var10003.__call__(var2, var10005));
      var1.setline(2600);
      var3 = var1.getglobal("range").__call__((ThreadState)var2, (PyObject)Py.newInteger(15)).__iter__();

      while(true) {
         var1.setline(2600);
         PyObject var7 = var3.__iternext__();
         if (var7 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var7);
         var1.setline(2601);
         PyObject var5 = var1.getlocal(2);
         var10000 = var5._eq(Py.newInteger(0));
         var5 = null;
         PyInteger var8;
         if (var10000.__nonzero__()) {
            var1.setline(2601);
            var8 = Py.newInteger(0);
            var1.setlocal(3, var8);
            var5 = null;
         } else {
            var1.setline(2602);
            var5 = var1.getlocal(2);
            var10000 = var5._le(Py.newInteger(3));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(2602);
               var8 = Py.newInteger(4);
               var1.setlocal(3, var8);
               var5 = null;
            } else {
               var1.setline(2603);
               var5 = var1.getlocal(2);
               var10000 = var5._le(Py.newInteger(6));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(2603);
                  var8 = Py.newInteger(8);
                  var1.setlocal(3, var8);
                  var5 = null;
               } else {
                  var1.setline(2604);
                  var5 = var1.getlocal(2);
                  var10000 = var5._le(Py.newInteger(9));
                  var5 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(2604);
                     var8 = Py.newInteger(12);
                     var1.setlocal(3, var8);
                     var5 = null;
                  } else {
                     var1.setline(2605);
                     var5 = var1.getlocal(2);
                     var10000 = var5._le(Py.newInteger(12));
                     var5 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(2605);
                        var8 = Py.newInteger(16);
                        var1.setlocal(3, var8);
                        var5 = null;
                     } else {
                        var1.setline(2606);
                        var8 = Py.newInteger(20);
                        var1.setlocal(3, var8);
                        var5 = null;
                     }
                  }
               }
            }
         }

         var1.setline(2607);
         var1.getlocal(1).__call__(var2, var1.getglobal("base64mime").__getattr__("base64_len").__call__(var2, PyString.fromInterned("x")._mul(var1.getlocal(2))), var1.getlocal(3));
      }
   }

   public PyObject test_decode$231(PyFrame var1, ThreadState var2) {
      var1.setline(2610);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2611);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("base64mime").__getattr__("decode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("")), (PyObject)PyString.fromInterned(""));
      var1.setline(2612);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("base64mime").__getattr__("decode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("aGVsbG8=")), (PyObject)PyString.fromInterned("hello"));
      var1.setline(2613);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("base64mime").__getattr__("decode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("aGVsbG8="), (PyObject)PyString.fromInterned("X")), (PyObject)PyString.fromInterned("hello"));
      var1.setline(2614);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("base64mime").__getattr__("decode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("aGVsbG8NCndvcmxk\n"), (PyObject)PyString.fromInterned("X")), (PyObject)PyString.fromInterned("helloXworld"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_encode$232(PyFrame var1, ThreadState var2) {
      var1.setline(2617);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2618);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("base64mime").__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("")), (PyObject)PyString.fromInterned(""));
      var1.setline(2619);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("base64mime").__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hello")), (PyObject)PyString.fromInterned("aGVsbG8=\n"));
      var1.setline(2621);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("base64mime").__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hello\n")), (PyObject)PyString.fromInterned("aGVsbG8K\n"));
      var1.setline(2622);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("base64mime").__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hello\n"), (PyObject)Py.newInteger(0)), (PyObject)PyString.fromInterned("aGVsbG8NCg==\n"));
      var1.setline(2624);
      PyObject var10000 = var1.getlocal(1);
      PyObject var10002 = var1.getglobal("base64mime").__getattr__("encode");
      PyObject[] var5 = new PyObject[]{PyString.fromInterned("xxxx ")._mul(Py.newInteger(20)), Py.newInteger(40)};
      String[] var4 = new String[]{"maxlinelen"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("eHh4eCB4eHh4IHh4eHggeHh4eCB4eHh4IHh4eHgg\neHh4eCB4eHh4IHh4eHggeHh4eCB4eHh4IHh4eHgg\neHh4eCB4eHh4IHh4eHggeHh4eCB4eHh4IHh4eHgg\neHh4eCB4eHh4IA==\n"));
      var1.setline(2631);
      var10000 = var1.getlocal(1);
      var10002 = var1.getglobal("base64mime").__getattr__("encode");
      var5 = new PyObject[]{PyString.fromInterned("xxxx ")._mul(Py.newInteger(20)), Py.newInteger(40), PyString.fromInterned("\r\n")};
      var4 = new String[]{"maxlinelen", "eol"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("eHh4eCB4eHh4IHh4eHggeHh4eCB4eHh4IHh4eHgg\r\neHh4eCB4eHh4IHh4eHggeHh4eCB4eHh4IHh4eHgg\r\neHh4eCB4eHh4IHh4eHggeHh4eCB4eHh4IHh4eHgg\r\neHh4eCB4eHh4IA==\r\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_header_encode$233(PyFrame var1, ThreadState var2) {
      var1.setline(2639);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2640);
      var3 = var1.getglobal("base64mime").__getattr__("header_encode");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2641);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hello")), (PyObject)PyString.fromInterned("=?iso-8859-1?b?aGVsbG8=?="));
      var1.setline(2642);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hello\nworld")), (PyObject)PyString.fromInterned("=?iso-8859-1?b?aGVsbG8NCndvcmxk?="));
      var1.setline(2644);
      PyObject var10000 = var1.getlocal(1);
      PyObject var10002 = var1.getlocal(2);
      PyObject[] var5 = new PyObject[]{PyString.fromInterned("hello"), PyString.fromInterned("iso-8859-2")};
      String[] var4 = new String[]{"charset"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("=?iso-8859-2?b?aGVsbG8=?="));
      var1.setline(2646);
      var10000 = var1.getlocal(1);
      var10002 = var1.getlocal(2);
      var5 = new PyObject[]{PyString.fromInterned("hello\nworld"), var1.getglobal("True")};
      var4 = new String[]{"keep_eols"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("=?iso-8859-1?b?aGVsbG8Kd29ybGQ=?="));
      var1.setline(2649);
      var10000 = var1.getlocal(1);
      var10002 = var1.getlocal(2);
      var5 = new PyObject[]{PyString.fromInterned("xxxx ")._mul(Py.newInteger(20)), Py.newInteger(40)};
      var4 = new String[]{"maxlinelen"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("=?iso-8859-1?b?eHh4eCB4eHh4IHh4eHggeHg=?=\n =?iso-8859-1?b?eHggeHh4eCB4eHh4IHh4eHg=?=\n =?iso-8859-1?b?IHh4eHggeHh4eCB4eHh4IHg=?=\n =?iso-8859-1?b?eHh4IHh4eHggeHh4eCB4eHg=?=\n =?iso-8859-1?b?eCB4eHh4IHh4eHggeHh4eCA=?=\n =?iso-8859-1?b?eHh4eCB4eHh4IHh4eHgg?="));
      var1.setline(2657);
      var10000 = var1.getlocal(1);
      var10002 = var1.getlocal(2);
      var5 = new PyObject[]{PyString.fromInterned("xxxx ")._mul(Py.newInteger(20)), Py.newInteger(40), PyString.fromInterned("\r\n")};
      var4 = new String[]{"maxlinelen", "eol"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("=?iso-8859-1?b?eHh4eCB4eHh4IHh4eHggeHg=?=\r\n =?iso-8859-1?b?eHggeHh4eCB4eHh4IHh4eHg=?=\r\n =?iso-8859-1?b?IHh4eHggeHh4eCB4eHh4IHg=?=\r\n =?iso-8859-1?b?eHh4IHh4eHggeHh4eCB4eHg=?=\r\n =?iso-8859-1?b?eCB4eHh4IHh4eHggeHh4eCA=?=\r\n =?iso-8859-1?b?eHh4eCB4eHh4IHh4eHgg?="));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestQuopri$234(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(2668);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, setUp$235, (PyObject)null);
      var1.setlocal("setUp", var4);
      var3 = null;
      var1.setline(2680);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_header_quopri_check$236, (PyObject)null);
      var1.setlocal("test_header_quopri_check", var4);
      var3 = null;
      var1.setline(2686);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_body_quopri_check$237, (PyObject)null);
      var1.setlocal("test_body_quopri_check", var4);
      var3 = null;
      var1.setline(2692);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_header_quopri_len$238, (PyObject)null);
      var1.setlocal("test_header_quopri_len", var4);
      var3 = null;
      var1.setline(2704);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_body_quopri_len$239, (PyObject)null);
      var1.setlocal("test_body_quopri_len", var4);
      var3 = null;
      var1.setline(2712);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_quote_unquote_idempotent$240, (PyObject)null);
      var1.setlocal("test_quote_unquote_idempotent", var4);
      var3 = null;
      var1.setline(2717);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_header_encode$241, (PyObject)null);
      var1.setlocal("test_header_encode", var4);
      var3 = null;
      var1.setline(2743);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_decode$242, (PyObject)null);
      var1.setlocal("test_decode", var4);
      var3 = null;
      var1.setline(2750);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_encode$243, (PyObject)null);
      var1.setlocal("test_encode", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject setUp$235(PyFrame var1, ThreadState var2) {
      var1.setline(2669);
      PyList var10000 = new PyList();
      PyObject var3 = var10000.__getattr__("append");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2669);
      var3 = var1.getglobal("range").__call__(var2, var1.getglobal("ord").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("a")), var1.getglobal("ord").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("z"))._add(Py.newInteger(1))).__iter__();

      while(true) {
         var1.setline(2669);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(2669);
            var1.dellocal(1);
            PyList var10001 = new PyList();
            var3 = var10001.__getattr__("append");
            var1.setlocal(3, var3);
            var3 = null;
            var1.setline(2670);
            var3 = var1.getglobal("range").__call__(var2, var1.getglobal("ord").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("A")), var1.getglobal("ord").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Z"))._add(Py.newInteger(1))).__iter__();

            while(true) {
               var1.setline(2670);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(2670);
                  var1.dellocal(3);
                  PyObject var7 = var10000._add(var10001);
                  var10001 = new PyList();
                  var3 = var10001.__getattr__("append");
                  var1.setlocal(4, var3);
                  var3 = null;
                  var1.setline(2671);
                  var3 = var1.getglobal("range").__call__(var2, var1.getglobal("ord").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("0")), var1.getglobal("ord").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("9"))._add(Py.newInteger(1))).__iter__();

                  while(true) {
                     var1.setline(2671);
                     var4 = var3.__iternext__();
                     if (var4 == null) {
                        var1.setline(2671);
                        var1.dellocal(4);
                        var3 = var7._add(var10001)._add(new PyList(new PyObject[]{PyString.fromInterned("!"), PyString.fromInterned("*"), PyString.fromInterned("+"), PyString.fromInterned("-"), PyString.fromInterned("/"), PyString.fromInterned(" ")}));
                        var1.getlocal(0).__setattr__("hlit", var3);
                        var3 = null;
                        var1.setline(2673);
                        var10000 = new PyList();
                        var3 = var10000.__getattr__("append");
                        var1.setlocal(5, var3);
                        var3 = null;
                        var1.setline(2673);
                        var3 = var1.getglobal("range").__call__((ThreadState)var2, (PyObject)Py.newInteger(256)).__iter__();

                        while(true) {
                           var1.setline(2673);
                           var4 = var3.__iternext__();
                           PyObject var5;
                           PyObject var8;
                           if (var4 == null) {
                              var1.setline(2673);
                              var1.dellocal(5);
                              PyList var6 = var10000;
                              var1.getlocal(0).__setattr__((String)"hnon", var6);
                              var3 = null;
                              var1.setline(2674);
                              if (var1.getglobal("__debug__").__nonzero__()) {
                                 var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("hlit"))._add(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("hnon")));
                                 var7 = var3._eq(Py.newInteger(256));
                                 var3 = null;
                                 if (!var7.__nonzero__()) {
                                    var7 = Py.None;
                                    throw Py.makeException(var1.getglobal("AssertionError"), var7);
                                 }
                              }

                              var1.setline(2675);
                              var10000 = new PyList();
                              var3 = var10000.__getattr__("append");
                              var1.setlocal(6, var3);
                              var3 = null;
                              var1.setline(2675);
                              var3 = var1.getglobal("range").__call__(var2, var1.getglobal("ord").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(" ")), var1.getglobal("ord").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("~"))._add(Py.newInteger(1))).__iter__();

                              while(true) {
                                 var1.setline(2675);
                                 var4 = var3.__iternext__();
                                 if (var4 == null) {
                                    var1.setline(2675);
                                    var1.dellocal(6);
                                    var3 = var10000._add(new PyList(new PyObject[]{PyString.fromInterned("\t")}));
                                    var1.getlocal(0).__setattr__("blit", var3);
                                    var3 = null;
                                    var1.setline(2676);
                                    var1.getlocal(0).__getattr__("blit").__getattr__("remove").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("="));
                                    var1.setline(2677);
                                    var10000 = new PyList();
                                    var3 = var10000.__getattr__("append");
                                    var1.setlocal(7, var3);
                                    var3 = null;
                                    var1.setline(2677);
                                    var3 = var1.getglobal("range").__call__((ThreadState)var2, (PyObject)Py.newInteger(256)).__iter__();

                                    while(true) {
                                       var1.setline(2677);
                                       var4 = var3.__iternext__();
                                       if (var4 == null) {
                                          var1.setline(2677);
                                          var1.dellocal(7);
                                          var6 = var10000;
                                          var1.getlocal(0).__setattr__((String)"bnon", var6);
                                          var3 = null;
                                          var1.setline(2678);
                                          if (var1.getglobal("__debug__").__nonzero__()) {
                                             var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("blit"))._add(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("bnon")));
                                             var7 = var3._eq(Py.newInteger(256));
                                             var3 = null;
                                             if (!var7.__nonzero__()) {
                                                var7 = Py.None;
                                                throw Py.makeException(var1.getglobal("AssertionError"), var7);
                                             }
                                          }

                                          var1.f_lasti = -1;
                                          return Py.None;
                                       }

                                       var1.setlocal(2, var4);
                                       var1.setline(2677);
                                       var5 = var1.getglobal("chr").__call__(var2, var1.getlocal(2));
                                       var8 = var5._notin(var1.getlocal(0).__getattr__("blit"));
                                       var5 = null;
                                       if (var8.__nonzero__()) {
                                          var1.setline(2677);
                                          var1.getlocal(7).__call__(var2, var1.getglobal("chr").__call__(var2, var1.getlocal(2)));
                                       }
                                    }
                                 }

                                 var1.setlocal(2, var4);
                                 var1.setline(2675);
                                 var1.getlocal(6).__call__(var2, var1.getglobal("chr").__call__(var2, var1.getlocal(2)));
                              }
                           }

                           var1.setlocal(2, var4);
                           var1.setline(2673);
                           var5 = var1.getglobal("chr").__call__(var2, var1.getlocal(2));
                           var8 = var5._notin(var1.getlocal(0).__getattr__("hlit"));
                           var5 = null;
                           if (var8.__nonzero__()) {
                              var1.setline(2673);
                              var1.getlocal(5).__call__(var2, var1.getglobal("chr").__call__(var2, var1.getlocal(2)));
                           }
                        }
                     }

                     var1.setlocal(2, var4);
                     var1.setline(2671);
                     var1.getlocal(4).__call__(var2, var1.getglobal("chr").__call__(var2, var1.getlocal(2)));
                  }
               }

               var1.setlocal(2, var4);
               var1.setline(2670);
               var1.getlocal(3).__call__(var2, var1.getglobal("chr").__call__(var2, var1.getlocal(2)));
            }
         }

         var1.setlocal(2, var4);
         var1.setline(2669);
         var1.getlocal(1).__call__(var2, var1.getglobal("chr").__call__(var2, var1.getlocal(2)));
      }
   }

   public PyObject test_header_quopri_check$236(PyFrame var1, ThreadState var2) {
      var1.setline(2681);
      PyObject var3 = var1.getlocal(0).__getattr__("hlit").__iter__();

      while(true) {
         var1.setline(2681);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(2683);
            var3 = var1.getlocal(0).__getattr__("hnon").__iter__();

            while(true) {
               var1.setline(2683);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setlocal(1, var4);
               var1.setline(2684);
               var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getglobal("quoprimime").__getattr__("header_quopri_check").__call__(var2, var1.getlocal(1)));
            }
         }

         var1.setlocal(1, var4);
         var1.setline(2682);
         var1.getlocal(0).__getattr__("assertFalse").__call__(var2, var1.getglobal("quoprimime").__getattr__("header_quopri_check").__call__(var2, var1.getlocal(1)));
      }
   }

   public PyObject test_body_quopri_check$237(PyFrame var1, ThreadState var2) {
      var1.setline(2687);
      PyObject var3 = var1.getlocal(0).__getattr__("blit").__iter__();

      while(true) {
         var1.setline(2687);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(2689);
            var3 = var1.getlocal(0).__getattr__("bnon").__iter__();

            while(true) {
               var1.setline(2689);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setlocal(1, var4);
               var1.setline(2690);
               var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getglobal("quoprimime").__getattr__("body_quopri_check").__call__(var2, var1.getlocal(1)));
            }
         }

         var1.setlocal(1, var4);
         var1.setline(2688);
         var1.getlocal(0).__getattr__("assertFalse").__call__(var2, var1.getglobal("quoprimime").__getattr__("body_quopri_check").__call__(var2, var1.getlocal(1)));
      }
   }

   public PyObject test_header_quopri_len$238(PyFrame var1, ThreadState var2) {
      var1.setline(2693);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2694);
      var3 = var1.getglobal("quoprimime").__getattr__("header_quopri_len");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2695);
      var3 = var1.getglobal("quoprimime").__getattr__("header_encode");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(2696);
      var3 = (new PyTuple(new PyObject[]{PyString.fromInterned("hello"), PyString.fromInterned("h@e@l@l@o@")})).__iter__();

      while(true) {
         var1.setline(2696);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(2699);
            var3 = var1.getlocal(0).__getattr__("hlit").__iter__();

            while(true) {
               var1.setline(2699);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(2701);
                  var3 = var1.getlocal(0).__getattr__("hnon").__iter__();

                  while(true) {
                     var1.setline(2701);
                     var4 = var3.__iternext__();
                     if (var4 == null) {
                        var1.f_lasti = -1;
                        return Py.None;
                     }

                     var1.setlocal(5, var4);
                     var1.setline(2702);
                     var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__call__(var2, var1.getlocal(5)), (PyObject)Py.newInteger(3));
                  }
               }

               var1.setlocal(5, var4);
               var1.setline(2700);
               var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__call__(var2, var1.getlocal(5)), (PyObject)Py.newInteger(1));
            }
         }

         var1.setlocal(4, var4);
         var1.setline(2698);
         PyObject var10000 = var1.getlocal(1);
         PyObject var10002 = var1.getlocal(2).__call__(var2, var1.getlocal(4));
         PyObject var10003 = var1.getglobal("len");
         PyObject var10005 = var1.getlocal(3);
         PyObject[] var5 = new PyObject[]{var1.getlocal(4), PyString.fromInterned(""), PyString.fromInterned("")};
         String[] var6 = new String[]{"charset", "eol"};
         var10005 = var10005.__call__(var2, var5, var6);
         var5 = null;
         var10000.__call__(var2, var10002, var10003.__call__(var2, var10005)._sub(Py.newInteger(7)));
      }
   }

   public PyObject test_body_quopri_len$239(PyFrame var1, ThreadState var2) {
      var1.setline(2705);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2706);
      var3 = var1.getglobal("quoprimime").__getattr__("body_quopri_len");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2707);
      var3 = var1.getlocal(0).__getattr__("blit").__iter__();

      while(true) {
         var1.setline(2707);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(2709);
            var3 = var1.getlocal(0).__getattr__("bnon").__iter__();

            while(true) {
               var1.setline(2709);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setlocal(3, var4);
               var1.setline(2710);
               var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__call__(var2, var1.getlocal(3)), (PyObject)Py.newInteger(3));
            }
         }

         var1.setlocal(3, var4);
         var1.setline(2708);
         var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__call__(var2, var1.getlocal(3)), (PyObject)Py.newInteger(1));
      }
   }

   public PyObject test_quote_unquote_idempotent$240(PyFrame var1, ThreadState var2) {
      var1.setline(2713);
      PyObject var3 = var1.getglobal("range").__call__((ThreadState)var2, (PyObject)Py.newInteger(256)).__iter__();

      while(true) {
         var1.setline(2713);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(1, var4);
         var1.setline(2714);
         PyObject var5 = var1.getglobal("chr").__call__(var2, var1.getlocal(1));
         var1.setlocal(2, var5);
         var5 = null;
         var1.setline(2715);
         var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getglobal("quoprimime").__getattr__("unquote").__call__(var2, var1.getglobal("quoprimime").__getattr__("quote").__call__(var2, var1.getlocal(2))), var1.getlocal(2));
      }
   }

   public PyObject test_header_encode$241(PyFrame var1, ThreadState var2) {
      var1.setline(2718);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2719);
      var3 = var1.getglobal("quoprimime").__getattr__("header_encode");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2720);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hello")), (PyObject)PyString.fromInterned("=?iso-8859-1?q?hello?="));
      var1.setline(2721);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hello\nworld")), (PyObject)PyString.fromInterned("=?iso-8859-1?q?hello=0D=0Aworld?="));
      var1.setline(2723);
      PyObject var10000 = var1.getlocal(1);
      PyObject var10002 = var1.getlocal(2);
      PyObject[] var5 = new PyObject[]{PyString.fromInterned("hello"), PyString.fromInterned("iso-8859-2")};
      String[] var4 = new String[]{"charset"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("=?iso-8859-2?q?hello?="));
      var1.setline(2725);
      var10000 = var1.getlocal(1);
      var10002 = var1.getlocal(2);
      var5 = new PyObject[]{PyString.fromInterned("hello\nworld"), var1.getglobal("True")};
      var4 = new String[]{"keep_eols"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("=?iso-8859-1?q?hello=0Aworld?="));
      var1.setline(2727);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("helloÇthere")), (PyObject)PyString.fromInterned("=?iso-8859-1?q?hello=C7there?="));
      var1.setline(2729);
      var10000 = var1.getlocal(1);
      var10002 = var1.getlocal(2);
      var5 = new PyObject[]{PyString.fromInterned("xxxx ")._mul(Py.newInteger(20)), Py.newInteger(40)};
      var4 = new String[]{"maxlinelen"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("=?iso-8859-1?q?xxxx_xxxx_xxxx_xxxx_xx?=\n =?iso-8859-1?q?xx_xxxx_xxxx_xxxx_xxxx?=\n =?iso-8859-1?q?_xxxx_xxxx_xxxx_xxxx_x?=\n =?iso-8859-1?q?xxx_xxxx_xxxx_xxxx_xxx?=\n =?iso-8859-1?q?x_xxxx_xxxx_?="));
      var1.setline(2736);
      var10000 = var1.getlocal(1);
      var10002 = var1.getlocal(2);
      var5 = new PyObject[]{PyString.fromInterned("xxxx ")._mul(Py.newInteger(20)), Py.newInteger(40), PyString.fromInterned("\r\n")};
      var4 = new String[]{"maxlinelen", "eol"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("=?iso-8859-1?q?xxxx_xxxx_xxxx_xxxx_xx?=\r\n =?iso-8859-1?q?xx_xxxx_xxxx_xxxx_xxxx?=\r\n =?iso-8859-1?q?_xxxx_xxxx_xxxx_xxxx_x?=\r\n =?iso-8859-1?q?xxx_xxxx_xxxx_xxxx_xxx?=\r\n =?iso-8859-1?q?x_xxxx_xxxx_?="));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_decode$242(PyFrame var1, ThreadState var2) {
      var1.setline(2744);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2745);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("quoprimime").__getattr__("decode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("")), (PyObject)PyString.fromInterned(""));
      var1.setline(2746);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("quoprimime").__getattr__("decode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hello")), (PyObject)PyString.fromInterned("hello"));
      var1.setline(2747);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("quoprimime").__getattr__("decode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hello"), (PyObject)PyString.fromInterned("X")), (PyObject)PyString.fromInterned("hello"));
      var1.setline(2748);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("quoprimime").__getattr__("decode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hello\nworld"), (PyObject)PyString.fromInterned("X")), (PyObject)PyString.fromInterned("helloXworld"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_encode$243(PyFrame var1, ThreadState var2) {
      var1.setline(2751);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2752);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("quoprimime").__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("")), (PyObject)PyString.fromInterned(""));
      var1.setline(2753);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("quoprimime").__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hello")), (PyObject)PyString.fromInterned("hello"));
      var1.setline(2755);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("quoprimime").__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hello\r\nworld")), (PyObject)PyString.fromInterned("hello\nworld"));
      var1.setline(2756);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("quoprimime").__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hello\r\nworld"), (PyObject)Py.newInteger(0)), (PyObject)PyString.fromInterned("hello\nworld"));
      var1.setline(2758);
      PyObject var10000 = var1.getlocal(1);
      PyObject var10002 = var1.getglobal("quoprimime").__getattr__("encode");
      PyObject[] var5 = new PyObject[]{PyString.fromInterned("xxxx ")._mul(Py.newInteger(20)), Py.newInteger(40)};
      String[] var4 = new String[]{"maxlinelen"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("xxxx xxxx xxxx xxxx xxxx xxxx xxxx xxxx=\n xxxx xxxx xxxx xxxx xxxx xxxx xxxx xxx=\nx xxxx xxxx xxxx xxxx=20"));
      var1.setline(2763);
      var10000 = var1.getlocal(1);
      var10002 = var1.getglobal("quoprimime").__getattr__("encode");
      var5 = new PyObject[]{PyString.fromInterned("xxxx ")._mul(Py.newInteger(20)), Py.newInteger(40), PyString.fromInterned("\r\n")};
      var4 = new String[]{"maxlinelen", "eol"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("xxxx xxxx xxxx xxxx xxxx xxxx xxxx xxxx=\r\n xxxx xxxx xxxx xxxx xxxx xxxx xxxx xxx=\r\nx xxxx xxxx xxxx xxxx=20"));
      var1.setline(2767);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("quoprimime").__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("one line\n\ntwo line")), (PyObject)PyString.fromInterned("one line\n\ntwo line"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestCharset$244(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(2779);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, tearDown$245, (PyObject)null);
      var1.setlocal("tearDown", var4);
      var3 = null;
      var1.setline(2786);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_idempotent$246, (PyObject)null);
      var1.setlocal("test_idempotent", var4);
      var3 = null;
      var1.setline(2798);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_body_encode$247, (PyObject)null);
      var1.setlocal("test_body_encode", var4);
      var3 = null;
      var1.setline(2831);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_unicode_charset_name$248, (PyObject)null);
      var1.setlocal("test_unicode_charset_name", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject tearDown$245(PyFrame var1, ThreadState var2) {
      var1.setline(2780);
      String[] var3 = new String[]{"charset"};
      PyObject[] var6 = imp.importFrom("email", var3, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal(1, var4);
      var4 = null;

      try {
         var1.setline(2782);
         var1.getlocal(1).__getattr__("CHARSETS").__delitem__((PyObject)PyString.fromInterned("fake"));
      } catch (Throwable var5) {
         PyException var7 = Py.setException(var5, var1);
         if (!var7.match(var1.getglobal("KeyError"))) {
            throw var7;
         }

         var1.setline(2784);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_idempotent$246(PyFrame var1, ThreadState var2) {
      var1.setline(2787);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2789);
      var3 = var1.getglobal("Charset").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("us-ascii"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2790);
      PyString var4 = PyString.fromInterned("Hello World!");
      var1.setlocal(3, var4);
      var3 = null;
      var1.setline(2791);
      var3 = var1.getlocal(2).__getattr__("to_splittable").__call__(var2, var1.getlocal(3));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(2792);
      var1.getlocal(1).__call__(var2, var1.getlocal(3), var1.getlocal(2).__getattr__("from_splittable").__call__(var2, var1.getlocal(4)));
      var1.setline(2794);
      var4 = PyString.fromInterned("¤¢¤¤¤¦¤¨¤ª");
      var1.setlocal(3, var4);
      var3 = null;
      var1.setline(2795);
      var3 = var1.getlocal(2).__getattr__("to_splittable").__call__(var2, var1.getlocal(3));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(2796);
      var1.getlocal(1).__call__(var2, var1.getlocal(3), var1.getlocal(2).__getattr__("from_splittable").__call__(var2, var1.getlocal(4)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_body_encode$247(PyFrame var1, ThreadState var2) {
      var1.setline(2799);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2801);
      var3 = var1.getglobal("Charset").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("iso-8859-1"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2802);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hello w=F6rld"), (PyObject)var1.getlocal(2).__getattr__("body_encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hello wörld")));
      var1.setline(2804);
      var3 = var1.getglobal("Charset").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("utf-8"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2805);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("aGVsbG8gd29ybGQ=\n"), (PyObject)var1.getlocal(2).__getattr__("body_encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hello world")));
      var1.setline(2807);
      var3 = var1.getglobal("Charset").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("us-ascii"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2808);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hello world"), (PyObject)var1.getlocal(2).__getattr__("body_encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hello world")));
      var1.setline(2810);
      var3 = var1.getglobal("Charset").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("euc-jp"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2812);
      if (var1.getglobal("is_jython").__not__().__nonzero__()) {
         try {
            var1.setline(2816);
            var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\u001b$B5FCO;~IW\u001b(B"), (PyObject)var1.getlocal(2).__getattr__("body_encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("µÆÃÏ»þÉ×")));
            var1.setline(2818);
            var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("µÆÃÏ»þÉ×"), (PyObject)var1.getlocal(2).__getattr__("body_encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("µÆÃÏ»þÉ×"), (PyObject)var1.getglobal("False")));
         } catch (Throwable var5) {
            PyException var6 = Py.setException(var5, var1);
            if (!var6.match(var1.getglobal("LookupError"))) {
               throw var6;
            }

            var1.setline(2822);
         }
      }

      var1.setline(2826);
      String[] var7 = new String[]{"charset"};
      PyObject[] var8 = imp.importFrom("email", var7, var1, -1);
      PyObject var4 = var8[0];
      var1.setlocal(3, var4);
      var4 = null;
      var1.setline(2827);
      var1.getlocal(3).__getattr__("add_charset").__call__((ThreadState)var2, PyString.fromInterned("fake"), (PyObject)var1.getlocal(3).__getattr__("QP"), (PyObject)var1.getglobal("None"));
      var1.setline(2828);
      var3 = var1.getglobal("Charset").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("fake"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2829);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hello wörld"), (PyObject)var1.getlocal(2).__getattr__("body_encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hello wörld")));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_unicode_charset_name$248(PyFrame var1, ThreadState var2) {
      var1.setline(2832);
      PyObject var3 = var1.getglobal("Charset").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("us-ascii"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2833);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("str").__call__(var2, var1.getlocal(1)), (PyObject)PyString.fromInterned("us-ascii"));
      var1.setline(2834);
      var1.getlocal(0).__getattr__("assertRaises").__call__((ThreadState)var2, var1.getglobal("errors").__getattr__("CharsetError"), (PyObject)var1.getglobal("Charset"), (PyObject)PyString.fromInterned("ascÿii"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestHeader$249(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(2840);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_simple$250, (PyObject)null);
      var1.setlocal("test_simple", var4);
      var3 = null;
      var1.setline(2847);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_simple_surprise$251, (PyObject)null);
      var1.setlocal("test_simple_surprise", var4);
      var3 = null;
      var1.setline(2854);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_header_needs_no_decoding$252, (PyObject)null);
      var1.setlocal("test_header_needs_no_decoding", var4);
      var3 = null;
      var1.setline(2858);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_long$253, (PyObject)null);
      var1.setlocal("test_long", var4);
      var3 = null;
      var1.setline(2864);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_multilingual$254, (PyObject)null);
      var1.setlocal("test_multilingual", var4);
      var3 = null;
      var1.setline(2913);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_header_ctor_default_args$255, (PyObject)null);
      var1.setlocal("test_header_ctor_default_args", var4);
      var3 = null;
      var1.setline(2920);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_explicit_maxlinelen$256, (PyObject)null);
      var1.setlocal("test_explicit_maxlinelen", var4);
      var3 = null;
      var1.setline(2934);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_us_ascii_header$257, (PyObject)null);
      var1.setlocal("test_us_ascii_header", var4);
      var3 = null;
      var1.setline(2942);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_string_charset$258, (PyObject)null);
      var1.setlocal("test_string_charset", var4);
      var3 = null;
      var1.setline(2957);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_utf8_shortest$259, (PyObject)null);
      var1.setlocal("test_utf8_shortest", var4);
      var3 = null;
      var1.setline(2964);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_bad_8bit_header$260, (PyObject)null);
      var1.setlocal("test_bad_8bit_header", var4);
      var3 = null;
      var1.setline(2975);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_encoded_adjacent_nonencoded$261, (PyObject)null);
      var1.setlocal("test_encoded_adjacent_nonencoded", var4);
      var3 = null;
      var1.setline(2985);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_whitespace_eater$262, (PyObject)null);
      var1.setlocal("test_whitespace_eater", var4);
      var3 = null;
      var1.setline(2994);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_broken_base64_header$263, (PyObject)null);
      var1.setlocal("test_broken_base64_header", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_simple$250(PyFrame var1, ThreadState var2) {
      var1.setline(2841);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2842);
      var3 = var1.getglobal("Header").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Hello World!"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2843);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("encode").__call__(var2), (PyObject)PyString.fromInterned("Hello World!"));
      var1.setline(2844);
      var1.getlocal(2).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(" Goodbye World!"));
      var1.setline(2845);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("encode").__call__(var2), (PyObject)PyString.fromInterned("Hello World!  Goodbye World!"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_simple_surprise$251(PyFrame var1, ThreadState var2) {
      var1.setline(2848);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2849);
      var3 = var1.getglobal("Header").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Hello World!"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2850);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("encode").__call__(var2), (PyObject)PyString.fromInterned("Hello World!"));
      var1.setline(2851);
      var1.getlocal(2).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Goodbye World!"));
      var1.setline(2852);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("encode").__call__(var2), (PyObject)PyString.fromInterned("Hello World! Goodbye World!"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_header_needs_no_decoding$252(PyFrame var1, ThreadState var2) {
      var1.setline(2855);
      PyString var3 = PyString.fromInterned("no decoding needed");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2856);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("decode_header").__call__(var2, var1.getlocal(1)), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{var1.getlocal(1), var1.getglobal("None")})})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_long$253(PyFrame var1, ThreadState var2) {
      var1.setline(2859);
      PyObject var10000 = var1.getglobal("Header");
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("I am the very model of a modern Major-General; I've information vegetable, animal, and mineral; I know the kings of England, and I quote the fights historical from Marathon to Waterloo, in order categorical; I'm very well acquainted, too, with matters mathematical; I understand equations, both the simple and quadratical; about binomial theorem I'm teeming with a lot o' news, with many cheerful facts about the square of the hypotenuse."), Py.newInteger(76)};
      String[] var4 = new String[]{"maxlinelen"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var6 = var10000;
      var1.setlocal(1, var6);
      var3 = null;
      var1.setline(2861);
      var10000 = var1.getlocal(1).__getattr__("encode");
      var3 = new PyObject[]{PyString.fromInterned(" ")};
      var4 = new String[]{"splitchars"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      var6 = var10000.__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n ")).__iter__();

      while(true) {
         var1.setline(2861);
         PyObject var7 = var6.__iternext__();
         if (var7 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var7);
         var1.setline(2862);
         var10000 = var1.getlocal(0).__getattr__("assertTrue");
         PyObject var5 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
         PyObject var10002 = var5._le(Py.newInteger(76));
         var5 = null;
         var10000.__call__(var2, var10002);
      }
   }

   public PyObject test_multilingual$254(PyFrame var1, ThreadState var2) {
      var1.setline(2865);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2866);
      var3 = var1.getglobal("Charset").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("iso-8859-1"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2867);
      var3 = var1.getglobal("Charset").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("iso-8859-2"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(2868);
      var3 = var1.getglobal("Charset").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("utf-8"));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(2869);
      PyString var4 = PyString.fromInterned("Die Mieter treten hier ein werden mit einem Foerderband komfortabel den Korridor entlang, an südlündischen Wandgemälden vorbei, gegen die rotierenden Klingen befördert. ");
      var1.setlocal(5, var4);
      var3 = null;
      var1.setline(2870);
      var4 = PyString.fromInterned("Finanèni metropole se hroutily pod tlakem jejich dùvtipu.. ");
      var1.setlocal(6, var4);
      var3 = null;
      var1.setline(2871);
      var3 = PyUnicode.fromInterned("正確に言うと翻訳はされていません。一部はドイツ語ですが、あとはでたらめです。実際には「Wenn ist das Nunstuck git und Slotermeyer? Ja! Beiherhund das Oder die Flipperwaldt gersput.」と言っています。").__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("utf-8"));
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(2872);
      var3 = var1.getglobal("Header").__call__(var2, var1.getlocal(5), var1.getlocal(2));
      var1.setlocal(8, var3);
      var3 = null;
      var1.setline(2873);
      var1.getlocal(8).__getattr__("append").__call__(var2, var1.getlocal(6), var1.getlocal(3));
      var1.setline(2874);
      var1.getlocal(8).__getattr__("append").__call__(var2, var1.getlocal(7), var1.getlocal(4));
      var1.setline(2875);
      var3 = var1.getlocal(8).__getattr__("encode").__call__(var2);
      var1.setlocal(9, var3);
      var3 = null;
      var1.setline(2876);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(9), (PyObject)PyString.fromInterned("=?iso-8859-1?q?Die_Mieter_treten_hier_ein_werden_mit_einem_Foerderband_ko?=\n =?iso-8859-1?q?mfortabel_den_Korridor_entlang=2C_an_s=FCdl=FCndischen_Wan?=\n =?iso-8859-1?q?dgem=E4lden_vorbei=2C_gegen_die_rotierenden_Klingen_bef=F6?=\n =?iso-8859-1?q?rdert=2E_?= =?iso-8859-2?q?Finan=E8ni_metropole_se_hroutily?=\n =?iso-8859-2?q?_pod_tlakem_jejich_d=F9vtipu=2E=2E_?= =?utf-8?b?5q2j56K6?=\n =?utf-8?b?44Gr6KiA44GG44Go57+76Kiz44Gv44GV44KM44Gm44GE44G+44Gb44KT44CC?=\n =?utf-8?b?5LiA6YOo44Gv44OJ44Kk44OE6Kqe44Gn44GZ44GM44CB44GC44Go44Gv44Gn?=\n =?utf-8?b?44Gf44KJ44KB44Gn44GZ44CC5a6f6Zqb44Gr44Gv44CMV2VubiBpc3QgZGFz?=\n =?utf-8?q?_Nunstuck_git_und_Slotermeyer=3F_Ja!_Beiherhund_das_Oder_die_Fl?=\n =?utf-8?b?aXBwZXJ3YWxkdCBnZXJzcHV0LuOAjeOBqOiogOOBo+OBpuOBhOOBvuOBmQ==?=\n =?utf-8?b?44CC?="));
      var1.setline(2888);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("decode_header").__call__(var2, var1.getlocal(9)), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{var1.getlocal(5), PyString.fromInterned("iso-8859-1")}), new PyTuple(new PyObject[]{var1.getlocal(6), PyString.fromInterned("iso-8859-2")}), new PyTuple(new PyObject[]{var1.getlocal(7), PyString.fromInterned("utf-8")})})));
      var1.setline(2891);
      var3 = var1.getglobal("unicode").__call__(var2, var1.getlocal(8));
      var1.setlocal(10, var3);
      var3 = null;
      var1.setline(2892);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(10).__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("utf-8")), (PyObject)PyString.fromInterned("Die Mieter treten hier ein werden mit einem Foerderband komfortabel den Korridor entlang, an sÃ¼dlÃ¼ndischen WandgemÃ¤lden vorbei, gegen die rotierenden Klingen befÃ¶rdert. FinanÄ\u008dni metropole se hroutily pod tlakem jejich dÅ¯vtipu.. æ\u00ad£ç¢ºã\u0081«è¨\u0080ã\u0081\u0086ã\u0081¨ç¿»è¨³ã\u0081¯ã\u0081\u0095ã\u0082\u008cã\u0081¦ã\u0081\u0084ã\u0081¾ã\u0081\u009bã\u0082\u0093ã\u0080\u0082ä¸\u0080é\u0083¨ã\u0081¯ã\u0083\u0089ã\u0082¤ã\u0083\u0084èª\u009eã\u0081§ã\u0081\u0099ã\u0081\u008cã\u0080\u0081ã\u0081\u0082ã\u0081¨ã\u0081¯ã\u0081§ã\u0081\u009fã\u0082\u0089ã\u0082\u0081ã\u0081§ã\u0081\u0099ã\u0080\u0082å®\u009fé\u009a\u009bã\u0081«ã\u0081¯ã\u0080\u008cWenn ist das Nunstuck git und Slotermeyer? Ja! Beiherhund das Oder die Flipperwaldt gersput.ã\u0080\u008dã\u0081¨è¨\u0080ã\u0081£ã\u0081¦ã\u0081\u0084ã\u0081¾ã\u0081\u0099ã\u0080\u0082"));
      var1.setline(2910);
      var3 = var1.getglobal("make_header").__call__(var2, var1.getglobal("decode_header").__call__(var2, var1.getlocal(9)));
      var1.setlocal(11, var3);
      var3 = null;
      var1.setline(2911);
      var1.getlocal(1).__call__(var2, var1.getlocal(11), var1.getlocal(9));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_header_ctor_default_args$255(PyFrame var1, ThreadState var2) {
      var1.setline(2914);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2915);
      var3 = var1.getglobal("Header").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2916);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned(""));
      var1.setline(2917);
      var1.getlocal(2).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("foo"), (PyObject)var1.getglobal("Charset").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("iso-8859-1")));
      var1.setline(2918);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("=?iso-8859-1?q?foo?="));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_explicit_maxlinelen$256(PyFrame var1, ThreadState var2) {
      var1.setline(2921);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2922);
      PyString var5 = PyString.fromInterned("A very long line that must get split to something other than at the 76th character boundary to test the non-default behavior");
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(2923);
      var3 = var1.getglobal("Header").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(2924);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("encode").__call__(var2), (PyObject)PyString.fromInterned("A very long line that must get split to something other than at the 76th\n character boundary to test the non-default behavior"));
      var1.setline(2927);
      PyObject var10000 = var1.getglobal("Header");
      PyObject[] var6 = new PyObject[]{var1.getlocal(2), PyString.fromInterned("Subject")};
      String[] var4 = new String[]{"header_name"};
      var10000 = var10000.__call__(var2, var6, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(2928);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("encode").__call__(var2), (PyObject)PyString.fromInterned("A very long line that must get split to something other than at the\n 76th character boundary to test the non-default behavior"));
      var1.setline(2931);
      var10000 = var1.getglobal("Header");
      var6 = new PyObject[]{var1.getlocal(2), Py.newInteger(1024), PyString.fromInterned("Subject")};
      var4 = new String[]{"maxlinelen", "header_name"};
      var10000 = var10000.__call__(var2, var6, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(2932);
      var1.getlocal(1).__call__(var2, var1.getlocal(3).__getattr__("encode").__call__(var2), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_us_ascii_header$257(PyFrame var1, ThreadState var2) {
      var1.setline(2935);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2936);
      PyString var4 = PyString.fromInterned("hello");
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(2937);
      var3 = var1.getglobal("decode_header").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(2938);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("hello"), var1.getglobal("None")})})));
      var1.setline(2939);
      var3 = var1.getglobal("make_header").__call__(var2, var1.getlocal(3));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(2940);
      var1.getlocal(1).__call__(var2, var1.getlocal(2), var1.getlocal(4).__getattr__("encode").__call__(var2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_string_charset$258(PyFrame var1, ThreadState var2) {
      var1.setline(2943);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2944);
      var3 = var1.getglobal("Header").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2945);
      var1.getlocal(2).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hello"), (PyObject)PyString.fromInterned("iso-8859-1"));
      var1.setline(2946);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("=?iso-8859-1?q?hello?="));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_utf8_shortest$259(PyFrame var1, ThreadState var2) {
      var1.setline(2958);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2959);
      var3 = var1.getglobal("Header").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("pöstal"), (PyObject)PyString.fromInterned("utf-8"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2960);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("encode").__call__(var2), (PyObject)PyString.fromInterned("=?utf-8?q?p=C3=B6stal?="));
      var1.setline(2961);
      var3 = var1.getglobal("Header").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("菊地時夫"), (PyObject)PyString.fromInterned("utf-8"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2962);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("encode").__call__(var2), (PyObject)PyString.fromInterned("=?utf-8?b?6I+K5Zyw5pmC5aSr?="));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_bad_8bit_header$260(PyFrame var1, ThreadState var2) {
      var1.setline(2965);
      PyObject var3 = var1.getlocal(0).__getattr__("assertRaises");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2966);
      var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2967);
      PyString var5 = PyString.fromInterned("Ynwp4dUEbay Auction Semiar- No Charge \u0096 Earn Big");
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(2968);
      var1.getlocal(1).__call__(var2, var1.getglobal("UnicodeError"), var1.getglobal("Header"), var1.getlocal(3));
      var1.setline(2969);
      var3 = var1.getglobal("Header").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(2970);
      var1.getlocal(1).__call__(var2, var1.getglobal("UnicodeError"), var1.getlocal(4).__getattr__("append"), var1.getlocal(3));
      var1.setline(2971);
      PyObject var10000 = var1.getlocal(2);
      PyObject var10002 = var1.getglobal("str");
      PyObject var10004 = var1.getglobal("Header");
      PyObject[] var6 = new PyObject[]{var1.getlocal(3), PyString.fromInterned("replace")};
      String[] var4 = new String[]{"errors"};
      var10004 = var10004.__call__(var2, var6, var4);
      var3 = null;
      var10000.__call__(var2, var10002.__call__(var2, var10004), var1.getlocal(3));
      var1.setline(2972);
      var10000 = var1.getlocal(4).__getattr__("append");
      var6 = new PyObject[]{var1.getlocal(3), PyString.fromInterned("replace")};
      var4 = new String[]{"errors"};
      var10000.__call__(var2, var6, var4);
      var3 = null;
      var1.setline(2973);
      var1.getlocal(2).__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(4)), var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_encoded_adjacent_nonencoded$261(PyFrame var1, ThreadState var2) {
      var1.setline(2976);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2977);
      var3 = var1.getglobal("Header").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2978);
      var1.getlocal(2).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hello"), (PyObject)PyString.fromInterned("iso-8859-1"));
      var1.setline(2979);
      var1.getlocal(2).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("world"));
      var1.setline(2980);
      var3 = var1.getlocal(2).__getattr__("encode").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(2981);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("=?iso-8859-1?q?hello?= world"));
      var1.setline(2982);
      var3 = var1.getglobal("make_header").__call__(var2, var1.getglobal("decode_header").__call__(var2, var1.getlocal(3)));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2983);
      var1.getlocal(1).__call__(var2, var1.getlocal(2).__getattr__("encode").__call__(var2), var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_whitespace_eater$262(PyFrame var1, ThreadState var2) {
      var1.setline(2986);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2987);
      PyString var4 = PyString.fromInterned("Subject: =?koi8-r?b?8NLP18XSy8EgzsEgxsnOwczYztk=?= =?koi8-r?q?=CA?= zz.");
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(2988);
      var3 = var1.getglobal("decode_header").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(2989);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("Subject:"), var1.getglobal("None")}), new PyTuple(new PyObject[]{PyString.fromInterned("ðÒÏ×ÅÒËÁ ÎÁ ÆÉÎÁÌØÎÙÊ"), PyString.fromInterned("koi8-r")}), new PyTuple(new PyObject[]{PyString.fromInterned("zz."), var1.getglobal("None")})})));
      var1.setline(2990);
      var3 = var1.getglobal("make_header").__call__(var2, var1.getlocal(3));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(2991);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(4).__getattr__("encode").__call__(var2), (PyObject)PyString.fromInterned("Subject: =?koi8-r?b?8NLP18XSy8EgzsEgxsnOwczYztnK?= zz."));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_broken_base64_header$263(PyFrame var1, ThreadState var2) {
      var1.setline(2995);
      PyObject var3 = var1.getlocal(0).__getattr__("assertRaises");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2996);
      PyString var4 = PyString.fromInterned("Subject: =?EUC-KR?B?CSixpLDtKSC/7Liuvsax4iC6uLmwMcijIKHaILzSwd/H0SC8+LCjwLsgv7W/+Mj3I ?=");
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(2997);
      var1.getlocal(1).__call__(var2, var1.getglobal("errors").__getattr__("HeaderParseError"), var1.getglobal("decode_header"), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestRFC2231$264(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(3003);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_get_param$265, (PyObject)null);
      var1.setlocal("test_get_param", var4);
      var3 = null;
      var1.setline(3011);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_set_param$266, (PyObject)null);
      var1.setlocal("test_set_param", var4);
      var3 = null;
      var1.setline(3048);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_del_param$267, (PyObject)null);
      var1.setlocal("test_del_param", var4);
      var3 = null;
      var1.setline(3078);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_rfc2231_get_content_charset$268, (PyObject)null);
      var1.setlocal("test_rfc2231_get_content_charset", var4);
      var3 = null;
      var1.setline(3083);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_rfc2231_no_language_or_charset$269, (PyObject)null);
      var1.setlocal("test_rfc2231_no_language_or_charset", var4);
      var3 = null;
      var1.setline(3097);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_rfc2231_no_language_or_charset_in_filename$270, (PyObject)null);
      var1.setlocal("test_rfc2231_no_language_or_charset_in_filename", var4);
      var3 = null;
      var1.setline(3109);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_rfc2231_no_language_or_charset_in_filename_encoded$271, (PyObject)null);
      var1.setlocal("test_rfc2231_no_language_or_charset_in_filename_encoded", var4);
      var3 = null;
      var1.setline(3121);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_rfc2231_partly_encoded$272, (PyObject)null);
      var1.setlocal("test_rfc2231_partly_encoded", var4);
      var3 = null;
      var1.setline(3134);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_rfc2231_partly_nonencoded$273, (PyObject)null);
      var1.setlocal("test_rfc2231_partly_nonencoded", var4);
      var3 = null;
      var1.setline(3147);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_rfc2231_no_language_or_charset_in_boundary$274, (PyObject)null);
      var1.setlocal("test_rfc2231_no_language_or_charset_in_boundary", var4);
      var3 = null;
      var1.setline(3159);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_rfc2231_no_language_or_charset_in_charset$275, (PyObject)null);
      var1.setlocal("test_rfc2231_no_language_or_charset_in_charset", var4);
      var3 = null;
      var1.setline(3172);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_rfc2231_bad_encoding_in_filename$276, (PyObject)null);
      var1.setlocal("test_rfc2231_bad_encoding_in_filename", var4);
      var3 = null;
      var1.setline(3184);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_rfc2231_bad_encoding_in_charset$277, (PyObject)null);
      var1.setlocal("test_rfc2231_bad_encoding_in_charset", var4);
      var3 = null;
      var1.setline(3194);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_rfc2231_bad_character_in_charset$278, (PyObject)null);
      var1.setlocal("test_rfc2231_bad_character_in_charset", var4);
      var3 = null;
      var1.setline(3204);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_rfc2231_bad_character_in_filename$279, (PyObject)null);
      var1.setlocal("test_rfc2231_bad_character_in_filename", var4);
      var3 = null;
      var1.setline(3216);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_rfc2231_unknown_encoding$280, (PyObject)null);
      var1.setlocal("test_rfc2231_unknown_encoding", var4);
      var3 = null;
      var1.setline(3225);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_rfc2231_single_tick_in_filename_extended$281, (PyObject)null);
      var1.setlocal("test_rfc2231_single_tick_in_filename_extended", var4);
      var3 = null;
      var1.setline(3238);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_rfc2231_single_tick_in_filename$282, (PyObject)null);
      var1.setlocal("test_rfc2231_single_tick_in_filename", var4);
      var3 = null;
      var1.setline(3248);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_rfc2231_tick_attack_extended$283, (PyObject)null);
      var1.setlocal("test_rfc2231_tick_attack_extended", var4);
      var3 = null;
      var1.setline(3261);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_rfc2231_tick_attack$284, (PyObject)null);
      var1.setlocal("test_rfc2231_tick_attack", var4);
      var3 = null;
      var1.setline(3272);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_rfc2231_no_extended_values$285, (PyObject)null);
      var1.setlocal("test_rfc2231_no_extended_values", var4);
      var3 = null;
      var1.setline(3281);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_rfc2231_encoded_then_unencoded_segments$286, (PyObject)null);
      var1.setlocal("test_rfc2231_encoded_then_unencoded_segments", var4);
      var3 = null;
      var1.setline(3296);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_rfc2231_unencoded_then_encoded_segments$287, (PyObject)null);
      var1.setlocal("test_rfc2231_unencoded_then_encoded_segments", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_get_param$265(PyFrame var1, ThreadState var2) {
      var1.setline(3004);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(3005);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_29.txt"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(3006);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_param").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("title")), (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("us-ascii"), PyString.fromInterned("en"), PyString.fromInterned("This is even more ***fun*** isn't it!")})));
      var1.setline(3008);
      PyObject var10000 = var1.getlocal(1);
      PyObject var10002 = var1.getlocal(2).__getattr__("get_param");
      PyObject[] var5 = new PyObject[]{PyString.fromInterned("title"), var1.getglobal("False")};
      String[] var4 = new String[]{"unquote"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("us-ascii"), PyString.fromInterned("en"), PyString.fromInterned("\"This is even more ***fun*** isn't it!\"")})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_set_param$266(PyFrame var1, ThreadState var2) {
      var1.setline(3012);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(3013);
      var3 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(3014);
      PyObject var10000 = var1.getlocal(2).__getattr__("set_param");
      PyObject[] var5 = new PyObject[]{PyString.fromInterned("title"), PyString.fromInterned("This is even more ***fun*** isn't it!"), PyString.fromInterned("us-ascii")};
      String[] var4 = new String[]{"charset"};
      var10000.__call__(var2, var5, var4);
      var3 = null;
      var1.setline(3016);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_param").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("title")), (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("us-ascii"), PyString.fromInterned(""), PyString.fromInterned("This is even more ***fun*** isn't it!")})));
      var1.setline(3018);
      var10000 = var1.getlocal(2).__getattr__("set_param");
      var5 = new PyObject[]{PyString.fromInterned("title"), PyString.fromInterned("This is even more ***fun*** isn't it!"), PyString.fromInterned("us-ascii"), PyString.fromInterned("en")};
      var4 = new String[]{"charset", "language"};
      var10000.__call__(var2, var5, var4);
      var3 = null;
      var1.setline(3020);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_param").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("title")), (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("us-ascii"), PyString.fromInterned("en"), PyString.fromInterned("This is even more ***fun*** isn't it!")})));
      var1.setline(3022);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_01.txt"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(3023);
      var10000 = var1.getlocal(2).__getattr__("set_param");
      var5 = new PyObject[]{PyString.fromInterned("title"), PyString.fromInterned("This is even more ***fun*** isn't it!"), PyString.fromInterned("us-ascii"), PyString.fromInterned("en")};
      var4 = new String[]{"charset", "language"};
      var10000.__call__(var2, var5, var4);
      var3 = null;
      var1.setline(3025);
      var1.getlocal(0).__getattr__("ndiffAssertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("as_string").__call__(var2), (PyObject)PyString.fromInterned("Return-Path: <bbb@zzz.org>\nDelivered-To: bbb@zzz.org\nReceived: by mail.zzz.org (Postfix, from userid 889)\n id 27CEAD38CC; Fri,  4 May 2001 14:05:44 -0400 (EDT)\nMIME-Version: 1.0\nContent-Transfer-Encoding: 7bit\nMessage-ID: <15090.61304.110929.45684@aaa.zzz.org>\nFrom: bbb@ddd.com (John X. Doe)\nTo: bbb@zzz.org\nSubject: This is a test message\nDate: Fri, 4 May 2001 14:05:44 -0400\nContent-Type: text/plain; charset=us-ascii;\n title*=\"us-ascii'en'This%20is%20even%20more%20%2A%2A%2Afun%2A%2A%2A%20isn%27t%20it%21\"\n\n\nHi,\n\nDo you like this message?\n\n-Me\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_del_param$267(PyFrame var1, ThreadState var2) {
      var1.setline(3049);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(3050);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_01.txt"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(3051);
      PyObject var10000 = var1.getlocal(2).__getattr__("set_param");
      PyObject[] var5 = new PyObject[]{PyString.fromInterned("foo"), PyString.fromInterned("bar"), PyString.fromInterned("us-ascii"), PyString.fromInterned("en")};
      String[] var4 = new String[]{"charset", "language"};
      var10000.__call__(var2, var5, var4);
      var3 = null;
      var1.setline(3052);
      var10000 = var1.getlocal(2).__getattr__("set_param");
      var5 = new PyObject[]{PyString.fromInterned("title"), PyString.fromInterned("This is even more ***fun*** isn't it!"), PyString.fromInterned("us-ascii"), PyString.fromInterned("en")};
      var4 = new String[]{"charset", "language"};
      var10000.__call__(var2, var5, var4);
      var3 = null;
      var1.setline(3054);
      var10000 = var1.getlocal(2).__getattr__("del_param");
      var5 = new PyObject[]{PyString.fromInterned("foo"), PyString.fromInterned("Content-Type")};
      var4 = new String[]{"header"};
      var10000.__call__(var2, var5, var4);
      var3 = null;
      var1.setline(3055);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("as_string").__call__(var2), (PyObject)PyString.fromInterned("Return-Path: <bbb@zzz.org>\nDelivered-To: bbb@zzz.org\nReceived: by mail.zzz.org (Postfix, from userid 889)\n id 27CEAD38CC; Fri,  4 May 2001 14:05:44 -0400 (EDT)\nMIME-Version: 1.0\nContent-Transfer-Encoding: 7bit\nMessage-ID: <15090.61304.110929.45684@aaa.zzz.org>\nFrom: bbb@ddd.com (John X. Doe)\nTo: bbb@zzz.org\nSubject: This is a test message\nDate: Fri, 4 May 2001 14:05:44 -0400\nContent-Type: text/plain; charset=\"us-ascii\";\n title*=\"us-ascii'en'This%20is%20even%20more%20%2A%2A%2Afun%2A%2A%2A%20isn%27t%20it%21\"\n\n\nHi,\n\nDo you like this message?\n\n-Me\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_rfc2231_get_content_charset$268(PyFrame var1, ThreadState var2) {
      var1.setline(3079);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(3080);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg_32.txt"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(3081);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_content_charset").__call__(var2), (PyObject)PyString.fromInterned("us-ascii"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_rfc2231_no_language_or_charset$269(PyFrame var1, ThreadState var2) {
      var1.setline(3084);
      PyString var3 = PyString.fromInterned("Content-Transfer-Encoding: 8bit\nContent-Disposition: inline; filename=\"file____C__DOCUMENTS_20AND_20SETTINGS_FABIEN_LOCAL_20SETTINGS_TEMP_nsmail.htm\"\nContent-Type: text/html; NAME*0=file____C__DOCUMENTS_20AND_20SETTINGS_FABIEN_LOCAL_20SETTINGS_TEM; NAME*1=P_nsmail.htm\n\n");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(3090);
      PyObject var4 = var1.getglobal("email").__getattr__("message_from_string").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(3091);
      var4 = var1.getlocal(2).__getattr__("get_param").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("NAME"));
      var1.setlocal(3, var4);
      var3 = null;
      var1.setline(3092);
      var1.getlocal(0).__getattr__("assertFalse").__call__(var2, var1.getglobal("isinstance").__call__(var2, var1.getlocal(3), var1.getglobal("tuple")));
      var1.setline(3093);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("file____C__DOCUMENTS_20AND_20SETTINGS_FABIEN_LOCAL_20SETTINGS_TEMP_nsmail.htm"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_rfc2231_no_language_or_charset_in_filename$270(PyFrame var1, ThreadState var2) {
      var1.setline(3098);
      PyString var3 = PyString.fromInterned("Content-Disposition: inline;\n\tfilename*0*=\"''This%20is%20even%20more%20\";\n\tfilename*1*=\"%2A%2A%2Afun%2A%2A%2A%20\";\n\tfilename*2=\"is it not.pdf\"\n\n");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(3105);
      PyObject var4 = var1.getglobal("email").__getattr__("message_from_string").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(3106);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_filename").__call__(var2), (PyObject)PyString.fromInterned("This is even more ***fun*** is it not.pdf"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_rfc2231_no_language_or_charset_in_filename_encoded$271(PyFrame var1, ThreadState var2) {
      var1.setline(3110);
      PyString var3 = PyString.fromInterned("Content-Disposition: inline;\n\tfilename*0*=\"''This%20is%20even%20more%20\";\n\tfilename*1*=\"%2A%2A%2Afun%2A%2A%2A%20\";\n\tfilename*2=\"is it not.pdf\"\n\n");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(3117);
      PyObject var4 = var1.getglobal("email").__getattr__("message_from_string").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(3118);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_filename").__call__(var2), (PyObject)PyString.fromInterned("This is even more ***fun*** is it not.pdf"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_rfc2231_partly_encoded$272(PyFrame var1, ThreadState var2) {
      var1.setline(3122);
      PyString var3 = PyString.fromInterned("Content-Disposition: inline;\n\tfilename*0=\"''This%20is%20even%20more%20\";\n\tfilename*1*=\"%2A%2A%2Afun%2A%2A%2A%20\";\n\tfilename*2=\"is it not.pdf\"\n\n");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(3129);
      PyObject var4 = var1.getglobal("email").__getattr__("message_from_string").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(3130);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_filename").__call__(var2), (PyObject)PyString.fromInterned("This%20is%20even%20more%20***fun*** is it not.pdf"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_rfc2231_partly_nonencoded$273(PyFrame var1, ThreadState var2) {
      var1.setline(3135);
      PyString var3 = PyString.fromInterned("Content-Disposition: inline;\n\tfilename*0=\"This%20is%20even%20more%20\";\n\tfilename*1=\"%2A%2A%2Afun%2A%2A%2A%20\";\n\tfilename*2=\"is it not.pdf\"\n\n");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(3142);
      PyObject var4 = var1.getglobal("email").__getattr__("message_from_string").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(3143);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_filename").__call__(var2), (PyObject)PyString.fromInterned("This%20is%20even%20more%20%2A%2A%2Afun%2A%2A%2A%20is it not.pdf"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_rfc2231_no_language_or_charset_in_boundary$274(PyFrame var1, ThreadState var2) {
      var1.setline(3148);
      PyString var3 = PyString.fromInterned("Content-Type: multipart/alternative;\n\tboundary*0*=\"''This%20is%20even%20more%20\";\n\tboundary*1*=\"%2A%2A%2Afun%2A%2A%2A%20\";\n\tboundary*2=\"is it not.pdf\"\n\n");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(3155);
      PyObject var4 = var1.getglobal("email").__getattr__("message_from_string").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(3156);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_boundary").__call__(var2), (PyObject)PyString.fromInterned("This is even more ***fun*** is it not.pdf"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_rfc2231_no_language_or_charset_in_charset$275(PyFrame var1, ThreadState var2) {
      var1.setline(3161);
      PyString var3 = PyString.fromInterned("Content-Type: text/plain;\n\tcharset*0*=\"This%20is%20even%20more%20\";\n\tcharset*1*=\"%2A%2A%2Afun%2A%2A%2A%20\";\n\tcharset*2=\"is it not.pdf\"\n\n");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(3168);
      PyObject var4 = var1.getglobal("email").__getattr__("message_from_string").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(3169);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_content_charset").__call__(var2), (PyObject)PyString.fromInterned("this is even more ***fun*** is it not.pdf"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_rfc2231_bad_encoding_in_filename$276(PyFrame var1, ThreadState var2) {
      var1.setline(3173);
      PyString var3 = PyString.fromInterned("Content-Disposition: inline;\n\tfilename*0*=\"bogus'xx'This%20is%20even%20more%20\";\n\tfilename*1*=\"%2A%2A%2Afun%2A%2A%2A%20\";\n\tfilename*2=\"is it not.pdf\"\n\n");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(3180);
      PyObject var4 = var1.getglobal("email").__getattr__("message_from_string").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(3181);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_filename").__call__(var2), (PyObject)PyString.fromInterned("This is even more ***fun*** is it not.pdf"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_rfc2231_bad_encoding_in_charset$277(PyFrame var1, ThreadState var2) {
      var1.setline(3185);
      PyString var3 = PyString.fromInterned("Content-Type: text/plain; charset*=bogus''utf-8%E2%80%9D\n\n");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(3189);
      PyObject var4 = var1.getglobal("email").__getattr__("message_from_string").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(3192);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(2).__getattr__("get_content_charset").__call__(var2), var1.getglobal("None"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_rfc2231_bad_character_in_charset$278(PyFrame var1, ThreadState var2) {
      var1.setline(3195);
      PyString var3 = PyString.fromInterned("Content-Type: text/plain; charset*=ascii''utf-8%E2%80%9D\n\n");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(3199);
      PyObject var4 = var1.getglobal("email").__getattr__("message_from_string").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(3202);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(2).__getattr__("get_content_charset").__call__(var2), var1.getglobal("None"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_rfc2231_bad_character_in_filename$279(PyFrame var1, ThreadState var2) {
      var1.setline(3205);
      PyString var3 = PyString.fromInterned("Content-Disposition: inline;\n\tfilename*0*=\"ascii'xx'This%20is%20even%20more%20\";\n\tfilename*1*=\"%2A%2A%2Afun%2A%2A%2A%20\";\n\tfilename*2*=\"is it not.pdf%E2\"\n\n");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(3212);
      PyObject var4 = var1.getglobal("email").__getattr__("message_from_string").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(3213);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_filename").__call__(var2), (PyObject)PyUnicode.fromInterned("This is even more ***fun*** is it not.pdf�"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_rfc2231_unknown_encoding$280(PyFrame var1, ThreadState var2) {
      var1.setline(3217);
      PyString var3 = PyString.fromInterned("Content-Transfer-Encoding: 8bit\nContent-Disposition: inline; filename*=X-UNKNOWN''myfile.txt\n\n");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(3222);
      PyObject var4 = var1.getglobal("email").__getattr__("message_from_string").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(3223);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_filename").__call__(var2), (PyObject)PyString.fromInterned("myfile.txt"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_rfc2231_single_tick_in_filename_extended$281(PyFrame var1, ThreadState var2) {
      var1.setline(3226);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(3227);
      PyString var6 = PyString.fromInterned("Content-Type: application/x-foo;\n\tname*0*=\"Frank's\"; name*1*=\" Document\"\n\n");
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(3232);
      var3 = var1.getglobal("email").__getattr__("message_from_string").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(3233);
      var3 = var1.getlocal(3).__getattr__("get_param").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("name"));
      PyObject[] var4 = Py.unpackSequence(var3, 3);
      PyObject var5 = var4[0];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(5, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(6, var5);
      var5 = null;
      var3 = null;
      var1.setline(3234);
      var1.getlocal(1).__call__(var2, var1.getlocal(4), var1.getglobal("None"));
      var1.setline(3235);
      var1.getlocal(1).__call__(var2, var1.getlocal(5), var1.getglobal("None"));
      var1.setline(3236);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(6), (PyObject)PyString.fromInterned("Frank's Document"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_rfc2231_single_tick_in_filename$282(PyFrame var1, ThreadState var2) {
      var1.setline(3239);
      PyString var3 = PyString.fromInterned("Content-Type: application/x-foo; name*0=\"Frank's\"; name*1=\" Document\"\n\n");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(3243);
      PyObject var4 = var1.getglobal("email").__getattr__("message_from_string").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(3244);
      var4 = var1.getlocal(2).__getattr__("get_param").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("name"));
      var1.setlocal(3, var4);
      var3 = null;
      var1.setline(3245);
      var1.getlocal(0).__getattr__("assertFalse").__call__(var2, var1.getglobal("isinstance").__call__(var2, var1.getlocal(3), var1.getglobal("tuple")));
      var1.setline(3246);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("Frank's Document"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_rfc2231_tick_attack_extended$283(PyFrame var1, ThreadState var2) {
      var1.setline(3249);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(3250);
      PyString var6 = PyString.fromInterned("Content-Type: application/x-foo;\n\tname*0*=\"us-ascii'en-us'Frank's\"; name*1*=\" Document\"\n\n");
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(3255);
      var3 = var1.getglobal("email").__getattr__("message_from_string").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(3256);
      var3 = var1.getlocal(3).__getattr__("get_param").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("name"));
      PyObject[] var4 = Py.unpackSequence(var3, 3);
      PyObject var5 = var4[0];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(5, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(6, var5);
      var5 = null;
      var3 = null;
      var1.setline(3257);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)PyString.fromInterned("us-ascii"));
      var1.setline(3258);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(5), (PyObject)PyString.fromInterned("en-us"));
      var1.setline(3259);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(6), (PyObject)PyString.fromInterned("Frank's Document"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_rfc2231_tick_attack$284(PyFrame var1, ThreadState var2) {
      var1.setline(3262);
      PyString var3 = PyString.fromInterned("Content-Type: application/x-foo;\n\tname*0=\"us-ascii'en-us'Frank's\"; name*1=\" Document\"\n\n");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(3267);
      PyObject var4 = var1.getglobal("email").__getattr__("message_from_string").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(3268);
      var4 = var1.getlocal(2).__getattr__("get_param").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("name"));
      var1.setlocal(3, var4);
      var3 = null;
      var1.setline(3269);
      var1.getlocal(0).__getattr__("assertFalse").__call__(var2, var1.getglobal("isinstance").__call__(var2, var1.getlocal(3), var1.getglobal("tuple")));
      var1.setline(3270);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("us-ascii'en-us'Frank's Document"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_rfc2231_no_extended_values$285(PyFrame var1, ThreadState var2) {
      var1.setline(3273);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(3274);
      PyString var4 = PyString.fromInterned("Content-Type: application/x-foo; name=\"Frank's Document\"\n\n");
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(3278);
      var3 = var1.getglobal("email").__getattr__("message_from_string").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(3279);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("get_param").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("name")), (PyObject)PyString.fromInterned("Frank's Document"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_rfc2231_encoded_then_unencoded_segments$286(PyFrame var1, ThreadState var2) {
      var1.setline(3282);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(3283);
      PyString var6 = PyString.fromInterned("Content-Type: application/x-foo;\n\tname*0*=\"us-ascii'en-us'My\";\n\tname*1=\" Document\";\n\tname*2*=\" For You\"\n\n");
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(3290);
      var3 = var1.getglobal("email").__getattr__("message_from_string").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(3291);
      var3 = var1.getlocal(3).__getattr__("get_param").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("name"));
      PyObject[] var4 = Py.unpackSequence(var3, 3);
      PyObject var5 = var4[0];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(5, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(6, var5);
      var5 = null;
      var3 = null;
      var1.setline(3292);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)PyString.fromInterned("us-ascii"));
      var1.setline(3293);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(5), (PyObject)PyString.fromInterned("en-us"));
      var1.setline(3294);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(6), (PyObject)PyString.fromInterned("My Document For You"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_rfc2231_unencoded_then_encoded_segments$287(PyFrame var1, ThreadState var2) {
      var1.setline(3297);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(3298);
      PyString var6 = PyString.fromInterned("Content-Type: application/x-foo;\n\tname*0=\"us-ascii'en-us'My\";\n\tname*1*=\" Document\";\n\tname*2*=\" For You\"\n\n");
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(3305);
      var3 = var1.getglobal("email").__getattr__("message_from_string").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(3306);
      var3 = var1.getlocal(3).__getattr__("get_param").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("name"));
      PyObject[] var4 = Py.unpackSequence(var3, 3);
      PyObject var5 = var4[0];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(5, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(6, var5);
      var5 = null;
      var3 = null;
      var1.setline(3307);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)PyString.fromInterned("us-ascii"));
      var1.setline(3308);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(5), (PyObject)PyString.fromInterned("en-us"));
      var1.setline(3309);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(6), (PyObject)PyString.fromInterned("My Document For You"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _testclasses$288(PyFrame var1, ThreadState var2) {
      var1.setline(3314);
      PyObject var3 = var1.getglobal("sys").__getattr__("modules").__getitem__(var1.getglobal("__name__"));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(3315);
      PyList var10000 = new PyList();
      var3 = var10000.__getattr__("append");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(3315);
      var3 = var1.getglobal("dir").__call__(var2, var1.getlocal(0)).__iter__();

      while(true) {
         var1.setline(3315);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(3315);
            var1.dellocal(1);
            PyList var5 = var10000;
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(2, var4);
         var1.setline(3315);
         if (var1.getlocal(2).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Test")).__nonzero__()) {
            var1.setline(3315);
            var1.getlocal(1).__call__(var2, var1.getglobal("getattr").__call__(var2, var1.getlocal(0), var1.getlocal(2)));
         }
      }
   }

   public PyObject suite$289(PyFrame var1, ThreadState var2) {
      var1.setline(3319);
      PyObject var3 = var1.getglobal("unittest").__getattr__("TestSuite").__call__(var2);
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(3320);
      var3 = var1.getglobal("_testclasses").__call__(var2).__iter__();

      while(true) {
         var1.setline(3320);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(3322);
            var3 = var1.getlocal(0);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(1, var4);
         var1.setline(3321);
         var1.getlocal(0).__getattr__("addTest").__call__(var2, var1.getglobal("unittest").__getattr__("makeSuite").__call__(var2, var1.getlocal(1)));
      }
   }

   public PyObject test_main$290(PyFrame var1, ThreadState var2) {
      var1.setline(3326);
      PyObject var3 = var1.getglobal("_testclasses").__call__(var2).__iter__();

      while(true) {
         var1.setline(3326);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(0, var4);
         var1.setline(3327);
         var1.getglobal("run_unittest").__call__(var2, var1.getlocal(0));
      }
   }

   public test_email_renamed$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"filename", "mode", "path"};
      openfile$1 = Py.newCode(2, var2, var1, "openfile", 45, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TestEmailBase$2 = Py.newCode(0, var2, var1, "TestEmailBase", 52, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "first", "second", "sfirst", "ssecond", "diff", "fp"};
      ndiffAssertEqual$3 = Py.newCode(3, var2, var1, "ndiffAssertEqual", 53, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "filename", "fp", "msg"};
      _msgobj$4 = Py.newCode(2, var2, var1, "_msgobj", 63, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TestMessageAPI$5 = Py.newCode(0, var2, var1, "TestMessageAPI", 74, false, false, self, 5, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "eq", "msg"};
      test_get_all$6 = Py.newCode(1, var2, var1, "test_get_all", 75, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg", "charset"};
      test_getset_charset$7 = Py.newCode(1, var2, var1, "test_getset_charset", 81, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg"};
      test_set_charset_from_string$8 = Py.newCode(1, var2, var1, "test_set_charset_from_string", 107, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "charset"};
      test_set_payload_with_charset$9 = Py.newCode(1, var2, var1, "test_set_payload_with_charset", 114, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg", "charsets"};
      test_get_charsets$10 = Py.newCode(1, var2, var1, "test_get_charsets", 120, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg", "filenames", "_[141_21]", "p", "subpart"};
      test_get_filename$11 = Py.newCode(1, var2, var1, "test_get_filename", 137, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg", "filenames", "_[152_21]", "p"};
      test_get_filename_with_name_parameter$12 = Py.newCode(1, var2, var1, "test_get_filename_with_name_parameter", 148, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg"};
      test_get_boundary$13 = Py.newCode(1, var2, var1, "test_get_boundary", 155, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg", "header", "value"};
      test_set_boundary$14 = Py.newCode(1, var2, var1, "test_set_boundary", 161, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg"};
      test_get_decoded_payload$15 = Py.newCode(1, var2, var1, "test_get_decoded_payload", 183, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg", "cte"};
      test_get_decoded_uu_payload$16 = Py.newCode(1, var2, var1, "test_get_decoded_uu_payload", 205, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg", "fp", "text", "s", "g"};
      test_decoded_generator$17 = Py.newCode(1, var2, var1, "test_decoded_generator", 216, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      test__contains__$18 = Py.newCode(1, var2, var1, "test__contains__", 229, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg", "fp", "text", "fullrepr", "lines"};
      test_as_string$19 = Py.newCode(1, var2, var1, "test_as_string", 241, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      test_bad_param$20 = Py.newCode(1, var2, var1, "test_bad_param", 264, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      test_missing_filename$21 = Py.newCode(1, var2, var1, "test_missing_filename", 268, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      test_bogus_filename$22 = Py.newCode(1, var2, var1, "test_bogus_filename", 272, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      test_missing_boundary$23 = Py.newCode(1, var2, var1, "test_missing_boundary", 277, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg"};
      test_get_params$24 = Py.newCode(1, var2, var1, "test_get_params", 281, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      test_get_param_liberal$25 = Py.newCode(1, var2, var1, "test_get_param_liberal", 297, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg"};
      test_get_param$26 = Py.newCode(1, var2, var1, "test_get_param", 302, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      test_get_param_funky_continuation_lines$27 = Py.newCode(1, var2, var1, "test_get_param_funky_continuation_lines", 319, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      test_get_param_with_semis_in_quotes$28 = Py.newCode(1, var2, var1, "test_get_param_with_semis_in_quotes", 323, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      test_has_key$29 = Py.newCode(1, var2, var1, "test_has_key", 330, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg"};
      test_set_param$30 = Py.newCode(1, var2, var1, "test_set_param", 337, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg", "old_val"};
      test_del_param$31 = Py.newCode(1, var2, var1, "test_del_param", 354, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      test_del_param_on_other_header$32 = Py.newCode(1, var2, var1, "test_del_param_on_other_header", 371, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg"};
      test_set_type$33 = Py.newCode(1, var2, var1, "test_set_type", 377, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      test_set_type_on_other_header$34 = Py.newCode(1, var2, var1, "test_set_type_on_other_header", 388, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      test_get_content_type_missing$35 = Py.newCode(1, var2, var1, "test_get_content_type_missing", 394, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      test_get_content_type_missing_with_default_type$36 = Py.newCode(1, var2, var1, "test_get_content_type_missing_with_default_type", 398, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      test_get_content_type_from_message_implicit$37 = Py.newCode(1, var2, var1, "test_get_content_type_from_message_implicit", 403, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      test_get_content_type_from_message_explicit$38 = Py.newCode(1, var2, var1, "test_get_content_type_from_message_explicit", 408, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      test_get_content_type_from_message_text_plain_implicit$39 = Py.newCode(1, var2, var1, "test_get_content_type_from_message_text_plain_implicit", 413, false, false, self, 39, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      test_get_content_type_from_message_text_plain_explicit$40 = Py.newCode(1, var2, var1, "test_get_content_type_from_message_text_plain_explicit", 417, false, false, self, 40, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      test_get_content_maintype_missing$41 = Py.newCode(1, var2, var1, "test_get_content_maintype_missing", 421, false, false, self, 41, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      test_get_content_maintype_missing_with_default_type$42 = Py.newCode(1, var2, var1, "test_get_content_maintype_missing_with_default_type", 425, false, false, self, 42, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      test_get_content_maintype_from_message_implicit$43 = Py.newCode(1, var2, var1, "test_get_content_maintype_from_message_implicit", 430, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      test_get_content_maintype_from_message_explicit$44 = Py.newCode(1, var2, var1, "test_get_content_maintype_from_message_explicit", 434, false, false, self, 44, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      test_get_content_maintype_from_message_text_plain_implicit$45 = Py.newCode(1, var2, var1, "test_get_content_maintype_from_message_text_plain_implicit", 438, false, false, self, 45, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      test_get_content_maintype_from_message_text_plain_explicit$46 = Py.newCode(1, var2, var1, "test_get_content_maintype_from_message_text_plain_explicit", 442, false, false, self, 46, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      test_get_content_subtype_missing$47 = Py.newCode(1, var2, var1, "test_get_content_subtype_missing", 446, false, false, self, 47, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      test_get_content_subtype_missing_with_default_type$48 = Py.newCode(1, var2, var1, "test_get_content_subtype_missing_with_default_type", 450, false, false, self, 48, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      test_get_content_subtype_from_message_implicit$49 = Py.newCode(1, var2, var1, "test_get_content_subtype_from_message_implicit", 455, false, false, self, 49, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      test_get_content_subtype_from_message_explicit$50 = Py.newCode(1, var2, var1, "test_get_content_subtype_from_message_explicit", 459, false, false, self, 50, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      test_get_content_subtype_from_message_text_plain_implicit$51 = Py.newCode(1, var2, var1, "test_get_content_subtype_from_message_text_plain_implicit", 463, false, false, self, 51, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      test_get_content_subtype_from_message_text_plain_explicit$52 = Py.newCode(1, var2, var1, "test_get_content_subtype_from_message_text_plain_explicit", 467, false, false, self, 52, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      test_get_content_maintype_error$53 = Py.newCode(1, var2, var1, "test_get_content_maintype_error", 471, false, false, self, 53, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      test_get_content_subtype_error$54 = Py.newCode(1, var2, var1, "test_get_content_subtype_error", 476, false, false, self, 54, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg"};
      test_replace_header$55 = Py.newCode(1, var2, var1, "test_replace_header", 481, false, false, self, 55, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "x", "msg"};
      test_broken_base64_payload$56 = Py.newCode(1, var2, var1, "test_broken_base64_payload", 498, false, false, self, 56, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TestEncoders$57 = Py.newCode(0, var2, var1, "TestEncoders", 509, false, false, self, 57, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "eq", "msg"};
      test_encode_empty_payload$58 = Py.newCode(1, var2, var1, "test_encode_empty_payload", 510, false, false, self, 58, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg"};
      test_default_cte$59 = Py.newCode(1, var2, var1, "test_default_cte", 516, false, false, self, 59, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg"};
      test_default_cte$60 = Py.newCode(1, var2, var1, "test_default_cte", 521, false, false, self, 60, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TestLongHeaders$61 = Py.newCode(0, var2, var1, "TestLongHeaders", 536, false, false, self, 61, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "eq", "msg", "sfp", "g"};
      test_split_long_continuation$62 = Py.newCode(1, var2, var1, "test_split_long_continuation", 537, false, false, self, 62, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "hstr", "h"};
      test_another_long_almost_unsplittable_header$63 = Py.newCode(1, var2, var1, "test_another_long_almost_unsplittable_header", 557, false, false, self, 63, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "g", "cz", "utf8", "g_head", "cz_head", "utf8_head", "h", "msg", "sfp"};
      test_long_nonstring$64 = Py.newCode(1, var2, var1, "test_long_nonstring", 574, false, false, self, 64, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "h"};
      test_long_header_encode$65 = Py.newCode(1, var2, var1, "test_long_header_encode", 617, false, false, self, 65, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "h"};
      test_long_header_encode_with_tab_continuation$66 = Py.newCode(1, var2, var1, "test_long_header_encode_with_tab_continuation", 626, false, false, self, 66, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg", "sfp", "g"};
      test_header_splitter$67 = Py.newCode(1, var2, var1, "test_header_splitter", 636, false, false, self, 67, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg", "_[660_40]", "i", "sfp", "g"};
      test_no_semis_header_splitter$68 = Py.newCode(1, var2, var1, "test_no_semis_header_splitter", 656, false, false, self, 68, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "hstr", "h"};
      test_no_split_long_header$69 = Py.newCode(1, var2, var1, "test_no_split_long_header", 672, false, false, self, 69, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "hstr", "h"};
      test_splitting_multiple_long_lines$70 = Py.newCode(1, var2, var1, "test_splitting_multiple_long_lines", 679, false, false, self, 70, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "hstr", "h"};
      test_splitting_first_line_only_is_long$71 = Py.newCode(1, var2, var1, "test_splitting_first_line_only_is_long", 701, false, false, self, 71, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg", "h"};
      test_long_8bit_header$72 = Py.newCode(1, var2, var1, "test_long_8bit_header", 717, false, false, self, 72, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg"};
      test_long_8bit_header_no_charset$73 = Py.newCode(1, var2, var1, "test_long_8bit_header_no_charset", 730, false, false, self, 73, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "to", "msg"};
      test_long_to_header$74 = Py.newCode(1, var2, var1, "test_long_to_header", 739, false, false, self, 74, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "s", "h"};
      test_long_line_after_append$75 = Py.newCode(1, var2, var1, "test_long_line_after_append", 752, false, false, self, 75, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "s", "h"};
      test_shorter_line_with_append$76 = Py.newCode(1, var2, var1, "test_shorter_line_with_append", 761, false, false, self, 76, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "fn", "gs", "h"};
      test_long_field_name$77 = Py.newCode(1, var2, var1, "test_long_field_name", 769, false, false, self, 77, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "h", "msg"};
      test_long_received_header$78 = Py.newCode(1, var2, var1, "test_long_received_header", 781, false, false, self, 78, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "h", "msg"};
      test_string_headerinst_eq$79 = Py.newCode(1, var2, var1, "test_string_headerinst_eq", 796, false, false, self, 79, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg", "t"};
      test_long_unbreakable_lines_with_continuation$80 = Py.newCode(1, var2, var1, "test_long_unbreakable_lines_with_continuation", 810, false, false, self, 80, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "m", "msg"};
      test_another_long_multiline_header$81 = Py.newCode(1, var2, var1, "test_another_long_multiline_header", 826, false, false, self, 81, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "h", "msg"};
      test_long_lines_with_different_header$82 = Py.newCode(1, var2, var1, "test_long_lines_with_different_header", 838, false, false, self, 82, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TestFromMangling$83 = Py.newCode(0, var2, var1, "TestFromMangling", 857, false, false, self, 83, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      setUp$84 = Py.newCode(1, var2, var1, "setUp", 858, false, false, self, 84, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "s", "g"};
      test_mangled_from$85 = Py.newCode(1, var2, var1, "test_mangled_from", 866, false, false, self, 85, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "s", "g"};
      test_dont_mangle_from$86 = Py.newCode(1, var2, var1, "test_dont_mangle_from", 877, false, false, self, 86, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TestMIMEAudio$87 = Py.newCode(0, var2, var1, "TestMIMEAudio", 891, false, false, self, 87, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "datadir", "fp"};
      setUp$88 = Py.newCode(1, var2, var1, "setUp", 892, false, false, self, 88, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_guess_minor_type$89 = Py.newCode(1, var2, var1, "test_guess_minor_type", 906, false, false, self, 89, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "payload"};
      test_encoding$90 = Py.newCode(1, var2, var1, "test_encoding", 909, false, false, self, 90, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "au"};
      test_checkSetMinor$91 = Py.newCode(1, var2, var1, "test_checkSetMinor", 913, false, false, self, 91, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "unless", "missing"};
      test_add_header$92 = Py.newCode(1, var2, var1, "test_add_header", 917, false, false, self, 92, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TestMIMEImage$93 = Py.newCode(0, var2, var1, "TestMIMEImage", 940, false, false, self, 93, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "fp"};
      setUp$94 = Py.newCode(1, var2, var1, "setUp", 941, false, false, self, 94, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_guess_minor_type$95 = Py.newCode(1, var2, var1, "test_guess_minor_type", 949, false, false, self, 95, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "payload"};
      test_encoding$96 = Py.newCode(1, var2, var1, "test_encoding", 952, false, false, self, 96, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "im"};
      test_checkSetMinor$97 = Py.newCode(1, var2, var1, "test_checkSetMinor", 956, false, false, self, 97, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "unless", "missing"};
      test_add_header$98 = Py.newCode(1, var2, var1, "test_add_header", 960, false, false, self, 98, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TestMIMEApplication$99 = Py.newCode(0, var2, var1, "TestMIMEApplication", 983, false, false, self, 99, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "eq", "msg"};
      test_headers$100 = Py.newCode(1, var2, var1, "test_headers", 984, false, false, self, 100, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "bytes", "msg"};
      test_body$101 = Py.newCode(1, var2, var1, "test_body", 990, false, false, self, 101, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "bytesdata", "msg", "s", "g", "wireform", "msg2"};
      test_binary_body_with_encode_7or8bit$102 = Py.newCode(1, var2, var1, "test_binary_body_with_encode_7or8bit", 997, false, false, self, 102, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "bytesdata", "msg", "s", "g", "wireform", "msg2"};
      test_binary_body_with_encode_noop$103 = Py.newCode(1, var2, var1, "test_binary_body_with_encode_noop", 1014, false, false, self, 103, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TestMIMEText$104 = Py.newCode(0, var2, var1, "TestMIMEText", 1032, false, false, self, 104, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      setUp$105 = Py.newCode(1, var2, var1, "setUp", 1033, false, false, self, 105, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "unless", "missing"};
      test_types$106 = Py.newCode(1, var2, var1, "test_types", 1036, false, false, self, 106, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_payload$107 = Py.newCode(1, var2, var1, "test_payload", 1046, false, false, self, 107, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg"};
      test_charset$108 = Py.newCode(1, var2, var1, "test_charset", 1050, false, false, self, 108, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TestMultipart$109 = Py.newCode(0, var2, var1, "TestMultipart", 1059, false, false, self, 109, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "fp", "data", "container", "image", "intro", "now", "timetuple", "tzsecs", "sign", "tzoffset"};
      setUp$110 = Py.newCode(1, var2, var1, "setUp", 1060, false, false, self, 110, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "unless", "raises", "m", "m0", "m1"};
      test_hierarchy$111 = Py.newCode(1, var2, var1, "test_hierarchy", 1100, false, false, self, 111, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "text", "msg"};
      test_empty_multipart_idempotent$112 = Py.newCode(1, var2, var1, "test_empty_multipart_idempotent", 1119, false, false, self, 112, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "outer"};
      test_no_parts_in_a_multipart_with_none_epilogue$113 = Py.newCode(1, var2, var1, "test_no_parts_in_a_multipart_with_none_epilogue", 1136, false, false, self, 113, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "outer"};
      test_no_parts_in_a_multipart_with_empty_epilogue$114 = Py.newCode(1, var2, var1, "test_no_parts_in_a_multipart_with_empty_epilogue", 1153, false, false, self, 114, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "outer", "msg"};
      test_one_part_in_a_multipart$115 = Py.newCode(1, var2, var1, "test_one_part_in_a_multipart", 1174, false, false, self, 115, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "outer", "msg"};
      test_seq_parts_in_a_multipart_with_empty_preamble$116 = Py.newCode(1, var2, var1, "test_seq_parts_in_a_multipart_with_empty_preamble", 1198, false, false, self, 116, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "outer", "msg"};
      test_seq_parts_in_a_multipart_with_none_preamble$117 = Py.newCode(1, var2, var1, "test_seq_parts_in_a_multipart_with_none_preamble", 1225, false, false, self, 117, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "outer", "msg"};
      test_seq_parts_in_a_multipart_with_none_epilogue$118 = Py.newCode(1, var2, var1, "test_seq_parts_in_a_multipart_with_none_epilogue", 1251, false, false, self, 118, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "outer", "msg"};
      test_seq_parts_in_a_multipart_with_empty_epilogue$119 = Py.newCode(1, var2, var1, "test_seq_parts_in_a_multipart_with_empty_epilogue", 1277, false, false, self, 119, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "outer", "msg"};
      test_seq_parts_in_a_multipart_with_nl_epilogue$120 = Py.newCode(1, var2, var1, "test_seq_parts_in_a_multipart_with_nl_epilogue", 1304, false, false, self, 120, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg", "msg1", "subpart", "subsubpart"};
      test_message_external_body$121 = Py.newCode(1, var2, var1, "test_message_external_body", 1331, false, false, self, 121, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      test_double_boundary$122 = Py.newCode(1, var2, var1, "test_double_boundary", 1344, false, false, self, 122, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg", "sfp"};
      test_nested_inner_contains_outer_boundary$123 = Py.newCode(1, var2, var1, "test_nested_inner_contains_outer_boundary", 1351, false, false, self, 123, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg", "sfp"};
      test_nested_with_same_boundary$124 = Py.newCode(1, var2, var1, "test_nested_with_same_boundary", 1370, false, false, self, 124, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      test_boundary_in_non_multipart$125 = Py.newCode(1, var2, var1, "test_boundary_in_non_multipart", 1387, false, false, self, 125, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg"};
      test_boundary_with_leading_space$126 = Py.newCode(1, var2, var1, "test_boundary_with_leading_space", 1402, false, false, self, 126, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "m"};
      test_boundary_without_trailing_newline$127 = Py.newCode(1, var2, var1, "test_boundary_without_trailing_newline", 1421, false, false, self, 127, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TestNonConformant$128 = Py.newCode(0, var2, var1, "TestNonConformant", 1438, false, false, self, 128, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "eq", "msg"};
      test_parse_missing_minor_type$129 = Py.newCode(1, var2, var1, "test_parse_missing_minor_type", 1439, false, false, self, 129, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "unless", "msg", "inner"};
      test_same_boundary_inner_outer$130 = Py.newCode(1, var2, var1, "test_same_boundary_inner_outer", 1446, false, false, self, 130, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "unless", "msg"};
      test_multipart_no_boundary$131 = Py.newCode(1, var2, var1, "test_multipart_no_boundary", 1456, false, false, self, 131, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "neq", "msg", "s", "g"};
      test_invalid_content_type$132 = Py.newCode(1, var2, var1, "test_invalid_content_type", 1465, false, false, self, 132, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg"};
      test_no_start_boundary$133 = Py.newCode(1, var2, var1, "test_no_start_boundary", 1486, false, false, self, 133, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg"};
      test_no_separating_blank_line$134 = Py.newCode(1, var2, var1, "test_no_separating_blank_line", 1503, false, false, self, 134, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "unless", "msg"};
      test_lying_multipart$135 = Py.newCode(1, var2, var1, "test_lying_multipart", 1514, false, false, self, 135, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "outer", "bad"};
      test_missing_start_boundary$136 = Py.newCode(1, var2, var1, "test_missing_start_boundary", 1523, false, false, self, 136, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "m", "msg"};
      test_first_line_is_continuation_header$137 = Py.newCode(1, var2, var1, "test_first_line_is_continuation_header", 1538, false, false, self, 137, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TestRFC2047$138 = Py.newCode(0, var2, var1, "TestRFC2047", 1552, false, false, self, 138, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "eq", "s", "dh"};
      test_rfc2047_multiline$139 = Py.newCode(1, var2, var1, "test_rfc2047_multiline", 1553, false, false, self, 139, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "s", "dh", "hu"};
      test_whitespace_eater_unicode$140 = Py.newCode(1, var2, var1, "test_whitespace_eater_unicode", 1567, false, false, self, 140, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "s", "dh", "hu"};
      test_whitespace_eater_unicode_2$141 = Py.newCode(1, var2, var1, "test_whitespace_eater_unicode_2", 1575, false, false, self, 141, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "s", "dh"};
      test_rfc2047_missing_whitespace$142 = Py.newCode(1, var2, var1, "test_rfc2047_missing_whitespace", 1584, false, false, self, 142, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "s", "dh"};
      test_rfc2047_with_whitespace$143 = Py.newCode(1, var2, var1, "test_rfc2047_with_whitespace", 1589, false, false, self, 143, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TestMIMEMessage$144 = Py.newCode(0, var2, var1, "TestMIMEMessage", 1599, false, false, self, 144, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "fp"};
      setUp$145 = Py.newCode(1, var2, var1, "setUp", 1600, false, false, self, 145, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_type_error$146 = Py.newCode(1, var2, var1, "test_type_error", 1607, false, false, self, 146, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "unless", "subject", "m", "r", "payload", "subpart"};
      test_valid_argument$147 = Py.newCode(1, var2, var1, "test_valid_argument", 1610, false, false, self, 147, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg1", "msg2", "r"};
      test_bad_multipart$148 = Py.newCode(1, var2, var1, "test_bad_multipart", 1625, false, false, self, 148, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "m", "r", "s", "g"};
      test_generate$149 = Py.newCode(1, var2, var1, "test_generate", 1634, false, false, self, 149, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "unless", "msg", "payload", "submsg"};
      test_parse_message_rfc822$150 = Py.newCode(1, var2, var1, "test_parse_message_rfc822", 1654, false, false, self, 150, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "unless", "msg", "subpart", "dsn1", "dsn2", "payload", "subsubpart"};
      test_dsn$151 = Py.newCode(1, var2, var1, "test_dsn", 1667, false, false, self, 151, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "fp", "text", "msg", "msg1", "msg2", "sfp", "g"};
      test_epilogue$152 = Py.newCode(1, var2, var1, "test_epilogue", 1725, false, false, self, 152, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg", "msg1", "msg2"};
      test_no_nl_preamble$153 = Py.newCode(1, var2, var1, "test_no_nl_preamble", 1748, false, false, self, 153, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "fp", "msg", "container1", "container2", "container1a", "container2a"};
      test_default_type$154 = Py.newCode(1, var2, var1, "test_default_type", 1783, false, false, self, 154, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "fp", "msg", "container1", "container2", "container1a", "container2a"};
      test_default_type_with_explicit_container_type$155 = Py.newCode(1, var2, var1, "test_default_type_with_explicit_container_type", 1803, false, false, self, 155, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "neq", "container", "subpart1a", "subpart2a", "subpart1", "subpart2"};
      test_default_type_non_parsed$156 = Py.newCode(1, var2, var1, "test_default_type_non_parsed", 1823, false, false, self, 156, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "text1", "text2", "msg"};
      test_mime_attachments_in_constructor$157 = Py.newCode(1, var2, var1, "test_mime_attachments_in_constructor", 1897, false, false, self, 157, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TestIdempotent$158 = Py.newCode(0, var2, var1, "TestIdempotent", 1913, false, false, self, 158, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "filename", "fp", "data", "msg"};
      _msgobj$159 = Py.newCode(2, var2, var1, "_msgobj", 1914, false, false, self, 159, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "text", "eq", "s", "g"};
      _idempotent$160 = Py.newCode(3, var2, var1, "_idempotent", 1923, false, false, self, 160, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg", "text"};
      test_parse_text_message$161 = Py.newCode(1, var2, var1, "test_parse_text_message", 1930, false, false, self, 161, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg", "text"};
      test_parse_untyped_message$162 = Py.newCode(1, var2, var1, "test_parse_untyped_message", 1942, false, false, self, 162, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "text"};
      test_simple_multipart$163 = Py.newCode(1, var2, var1, "test_simple_multipart", 1950, false, false, self, 163, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "text"};
      test_MIME_digest$164 = Py.newCode(1, var2, var1, "test_MIME_digest", 1954, false, false, self, 164, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "text"};
      test_long_header$165 = Py.newCode(1, var2, var1, "test_long_header", 1958, false, false, self, 165, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "text"};
      test_MIME_digest_with_part_headers$166 = Py.newCode(1, var2, var1, "test_MIME_digest_with_part_headers", 1962, false, false, self, 166, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "text"};
      test_mixed_with_image$167 = Py.newCode(1, var2, var1, "test_mixed_with_image", 1966, false, false, self, 167, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "text"};
      test_multipart_report$168 = Py.newCode(1, var2, var1, "test_multipart_report", 1970, false, false, self, 168, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "text"};
      test_dsn$169 = Py.newCode(1, var2, var1, "test_dsn", 1974, false, false, self, 169, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "text"};
      test_preamble_epilogue$170 = Py.newCode(1, var2, var1, "test_preamble_epilogue", 1978, false, false, self, 170, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "text"};
      test_multipart_one_part$171 = Py.newCode(1, var2, var1, "test_multipart_one_part", 1982, false, false, self, 171, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "text"};
      test_multipart_no_parts$172 = Py.newCode(1, var2, var1, "test_multipart_no_parts", 1986, false, false, self, 172, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "text"};
      test_no_start_boundary$173 = Py.newCode(1, var2, var1, "test_no_start_boundary", 1990, false, false, self, 173, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "text"};
      test_rfc2231_charset$174 = Py.newCode(1, var2, var1, "test_rfc2231_charset", 1994, false, false, self, 174, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "text"};
      test_more_rfc2231_parameters$175 = Py.newCode(1, var2, var1, "test_more_rfc2231_parameters", 1998, false, false, self, 175, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "text"};
      test_text_plain_in_a_multipart_digest$176 = Py.newCode(1, var2, var1, "test_text_plain_in_a_multipart_digest", 2002, false, false, self, 176, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "text"};
      test_nested_multipart_mixeds$177 = Py.newCode(1, var2, var1, "test_nested_multipart_mixeds", 2006, false, false, self, 177, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "text"};
      test_message_external_body_idempotent$178 = Py.newCode(1, var2, var1, "test_message_external_body_idempotent", 2010, false, false, self, 178, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "unless", "msg", "text", "params", "pk", "pv", "msg1", "msg2", "msg3", "payload", "msg4"};
      test_content_type$179 = Py.newCode(1, var2, var1, "test_content_type", 2014, false, false, self, 179, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "unless", "msg", "text", "payload", "msg1"};
      test_parser$180 = Py.newCode(1, var2, var1, "test_parser", 2046, false, false, self, 180, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TestMiscellaneous$181 = Py.newCode(0, var2, var1, "TestMiscellaneous", 2066, false, false, self, 181, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "fp", "text", "msg", "s", "g"};
      test_message_from_string$182 = Py.newCode(1, var2, var1, "test_message_from_string", 2067, false, false, self, 182, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "fp", "text", "msg", "s", "g"};
      test_message_from_file$183 = Py.newCode(1, var2, var1, "test_message_from_file", 2081, false, false, self, 183, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "unless", "fp", "text", "MyMessage", "msg", "subpart"};
      test_message_from_string_with_class$184 = Py.newCode(1, var2, var1, "test_message_from_string_with_class", 2096, false, false, self, 184, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      MyMessage$185 = Py.newCode(0, var2, var1, "MyMessage", 2104, false, false, self, 185, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "unless", "MyMessage", "fp", "msg", "subpart"};
      test_message_from_file_with_class$186 = Py.newCode(1, var2, var1, "test_message_from_file_with_class", 2119, false, false, self, 186, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      MyMessage$187 = Py.newCode(0, var2, var1, "MyMessage", 2122, false, false, self, 187, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "module", "all"};
      test__all__$188 = Py.newCode(1, var2, var1, "test__all__", 2140, false, false, self, 188, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "now"};
      test_formatdate$189 = Py.newCode(1, var2, var1, "test_formatdate", 2159, false, false, self, 189, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "now"};
      test_formatdate_localtime$190 = Py.newCode(1, var2, var1, "test_formatdate_localtime", 2164, false, false, self, 190, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "now"};
      test_formatdate_usegmt$191 = Py.newCode(1, var2, var1, "test_formatdate_usegmt", 2170, false, false, self, 191, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_parsedate_none$192 = Py.newCode(1, var2, var1, "test_parsedate_none", 2179, false, false, self, 192, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_parsedate_compact$193 = Py.newCode(1, var2, var1, "test_parsedate_compact", 2182, false, false, self, 193, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq"};
      test_parsedate_no_dayofweek$194 = Py.newCode(1, var2, var1, "test_parsedate_no_dayofweek", 2187, false, false, self, 194, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq"};
      test_parsedate_compact_no_dayofweek$195 = Py.newCode(1, var2, var1, "test_parsedate_compact_no_dayofweek", 2192, false, false, self, 195, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "timetup", "t"};
      test_parsedate_acceptable_to_time_functions$196 = Py.newCode(1, var2, var1, "test_parsedate_acceptable_to_time_functions", 2197, false, false, self, 196, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_parseaddr_empty$197 = Py.newCode(1, var2, var1, "test_parseaddr_empty", 2208, false, false, self, 197, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_noquote_dump$198 = Py.newCode(1, var2, var1, "test_noquote_dump", 2212, false, false, self, 198, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a", "b"};
      test_escape_dump$199 = Py.newCode(1, var2, var1, "test_escape_dump", 2217, false, false, self, 199, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a", "b"};
      test_escape_backslashes$200 = Py.newCode(1, var2, var1, "test_escape_backslashes", 2225, false, false, self, 200, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "x", "y", "a", "b"};
      test_name_with_dot$201 = Py.newCode(1, var2, var1, "test_name_with_dot", 2233, false, false, self, 201, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "x"};
      test_multiline_from_comment$202 = Py.newCode(1, var2, var1, "test_multiline_from_comment", 2242, false, false, self, 202, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_quote_dump$203 = Py.newCode(1, var2, var1, "test_quote_dump", 2248, false, false, self, 203, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq"};
      test_fix_eols$204 = Py.newCode(1, var2, var1, "test_fix_eols", 2253, false, false, self, 204, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "ne", "cset1", "cset2"};
      test_charset_richcomparisons$205 = Py.newCode(1, var2, var1, "test_charset_richcomparisons", 2261, false, false, self, 205, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq"};
      test_getaddresses$206 = Py.newCode(1, var2, var1, "test_getaddresses", 2281, false, false, self, 206, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq"};
      test_getaddresses_nasty$207 = Py.newCode(1, var2, var1, "test_getaddresses_nasty", 2288, false, false, self, 207, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "addrs"};
      test_getaddresses_embedded_comment$208 = Py.newCode(1, var2, var1, "test_getaddresses_embedded_comment", 2298, false, false, self, 208, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg"};
      test_utils_quote_unquote$209 = Py.newCode(1, var2, var1, "test_utils_quote_unquote", 2304, false, false, self, 209, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "charset"};
      test_get_body_encoding_with_bogus_charset$210 = Py.newCode(1, var2, var1, "test_get_body_encoding_with_bogus_charset", 2311, false, false, self, 210, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg", "charsets", "charset"};
      test_get_body_encoding_with_uppercase_charset$211 = Py.newCode(1, var2, var1, "test_get_body_encoding_with_uppercase_charset", 2315, false, false, self, 211, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "lc", "uc"};
      test_charsets_case_insensitive$212 = Py.newCode(1, var2, var1, "test_charsets_case_insensitive", 2341, false, false, self, 212, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg", "sfp"};
      test_partial_falls_inside_message_delivery_status$213 = Py.newCode(1, var2, var1, "test_partial_falls_inside_message_delivery_status", 2346, false, false, self, 213, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TestIterators$214 = Py.newCode(0, var2, var1, "TestIterators", 2391, false, false, self, 214, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "eq", "neq", "msg", "it", "lines", "fp"};
      test_body_line_iterator$215 = Py.newCode(1, var2, var1, "test_body_line_iterator", 2392, false, false, self, 215, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg", "it", "lines", "subparts", "subpart"};
      test_typed_subpart_iterator$216 = Py.newCode(1, var2, var1, "test_typed_subpart_iterator", 2412, false, false, self, 216, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg", "it", "lines", "subparts", "subpart"};
      test_typed_subpart_iterator_default_type$217 = Py.newCode(1, var2, var1, "test_typed_subpart_iterator_default_type", 2429, false, false, self, 217, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TestParsers$218 = Py.newCode(0, var2, var1, "TestParsers", 2450, false, false, self, 218, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "eq", "fp", "msg"};
      test_header_parser$219 = Py.newCode(1, var2, var1, "test_header_parser", 2451, false, false, self, 219, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg"};
      test_whitespace_continuation$220 = Py.newCode(1, var2, var1, "test_whitespace_continuation", 2465, false, false, self, 220, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg"};
      test_whitespace_continuation_last_header$221 = Py.newCode(1, var2, var1, "test_whitespace_continuation_last_header", 2483, false, false, self, 221, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "fp", "msg", "part1", "part2"};
      test_crlf_separation$222 = Py.newCode(1, var2, var1, "test_crlf_separation", 2501, false, false, self, 222, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "neq", "fp", "msg", "part1", "part1a", "part2", "part2a"};
      test_multipart_digest_with_extra_mime_headers$223 = Py.newCode(1, var2, var1, "test_multipart_digest_with_extra_mime_headers", 2515, false, false, self, 223, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "lines", "msg"};
      test_three_lines$224 = Py.newCode(1, var2, var1, "test_three_lines", 2549, false, false, self, 224, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "value1", "value2", "m", "msg"};
      test_strip_line_feed_and_carriage_return_in_headers$225 = Py.newCode(1, var2, var1, "test_strip_line_feed_and_carriage_return_in_headers", 2557, false, false, self, 225, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "m", "msg", "keys"};
      test_rfc2822_header_syntax$226 = Py.newCode(1, var2, var1, "test_rfc2822_header_syntax", 2568, false, false, self, 226, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "m", "msg"};
      test_rfc2822_space_not_allowed_in_header$227 = Py.newCode(1, var2, var1, "test_rfc2822_space_not_allowed_in_header", 2578, false, false, self, 227, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "m", "msg", "headers"};
      test_rfc2822_one_character_header$228 = Py.newCode(1, var2, var1, "test_rfc2822_one_character_header", 2584, false, false, self, 228, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TestBase64$229 = Py.newCode(0, var2, var1, "TestBase64", 2595, false, false, self, 229, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "eq", "size", "bsize"};
      test_len$230 = Py.newCode(1, var2, var1, "test_len", 2596, false, false, self, 230, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq"};
      test_decode$231 = Py.newCode(1, var2, var1, "test_decode", 2609, false, false, self, 231, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq"};
      test_encode$232 = Py.newCode(1, var2, var1, "test_encode", 2616, false, false, self, 232, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "he"};
      test_header_encode$233 = Py.newCode(1, var2, var1, "test_header_encode", 2638, false, false, self, 233, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TestQuopri$234 = Py.newCode(0, var2, var1, "TestQuopri", 2667, false, false, self, 234, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "_[2669_21]", "x", "_[2670_21]", "_[2671_21]", "_[2673_21]", "_[2675_21]", "_[2677_21]"};
      setUp$235 = Py.newCode(1, var2, var1, "setUp", 2668, false, false, self, 235, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "c"};
      test_header_quopri_check$236 = Py.newCode(1, var2, var1, "test_header_quopri_check", 2680, false, false, self, 236, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "c"};
      test_body_quopri_check$237 = Py.newCode(1, var2, var1, "test_body_quopri_check", 2686, false, false, self, 237, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "hql", "enc", "s", "c"};
      test_header_quopri_len$238 = Py.newCode(1, var2, var1, "test_header_quopri_len", 2692, false, false, self, 238, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "bql", "c"};
      test_body_quopri_len$239 = Py.newCode(1, var2, var1, "test_body_quopri_len", 2704, false, false, self, 239, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "x", "c"};
      test_quote_unquote_idempotent$240 = Py.newCode(1, var2, var1, "test_quote_unquote_idempotent", 2712, false, false, self, 240, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "he"};
      test_header_encode$241 = Py.newCode(1, var2, var1, "test_header_encode", 2717, false, false, self, 241, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq"};
      test_decode$242 = Py.newCode(1, var2, var1, "test_decode", 2743, false, false, self, 242, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq"};
      test_encode$243 = Py.newCode(1, var2, var1, "test_encode", 2750, false, false, self, 243, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TestCharset$244 = Py.newCode(0, var2, var1, "TestCharset", 2778, false, false, self, 244, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "CharsetModule"};
      tearDown$245 = Py.newCode(1, var2, var1, "tearDown", 2779, false, false, self, 245, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "c", "s", "sp"};
      test_idempotent$246 = Py.newCode(1, var2, var1, "test_idempotent", 2786, false, false, self, 246, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "c", "CharsetModule"};
      test_body_encode$247 = Py.newCode(1, var2, var1, "test_body_encode", 2798, false, false, self, 247, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "charset"};
      test_unicode_charset_name$248 = Py.newCode(1, var2, var1, "test_unicode_charset_name", 2831, false, false, self, 248, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TestHeader$249 = Py.newCode(0, var2, var1, "TestHeader", 2839, false, false, self, 249, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "eq", "h"};
      test_simple$250 = Py.newCode(1, var2, var1, "test_simple", 2840, false, false, self, 250, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "h"};
      test_simple_surprise$251 = Py.newCode(1, var2, var1, "test_simple_surprise", 2847, false, false, self, 251, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "h"};
      test_header_needs_no_decoding$252 = Py.newCode(1, var2, var1, "test_header_needs_no_decoding", 2854, false, false, self, 252, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "h", "l"};
      test_long$253 = Py.newCode(1, var2, var1, "test_long", 2858, false, false, self, 253, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "g", "cz", "utf8", "g_head", "cz_head", "utf8_head", "h", "enc", "ustr", "newh"};
      test_multilingual$254 = Py.newCode(1, var2, var1, "test_multilingual", 2864, false, false, self, 254, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "h"};
      test_header_ctor_default_args$255 = Py.newCode(1, var2, var1, "test_header_ctor_default_args", 2913, false, false, self, 255, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "hstr", "h"};
      test_explicit_maxlinelen$256 = Py.newCode(1, var2, var1, "test_explicit_maxlinelen", 2920, false, false, self, 256, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "s", "x", "h"};
      test_us_ascii_header$257 = Py.newCode(1, var2, var1, "test_us_ascii_header", 2934, false, false, self, 257, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "h"};
      test_string_charset$258 = Py.newCode(1, var2, var1, "test_string_charset", 2942, false, false, self, 258, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "h"};
      test_utf8_shortest$259 = Py.newCode(1, var2, var1, "test_utf8_shortest", 2957, false, false, self, 259, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "raises", "eq", "x", "h"};
      test_bad_8bit_header$260 = Py.newCode(1, var2, var1, "test_bad_8bit_header", 2964, false, false, self, 260, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "h", "s"};
      test_encoded_adjacent_nonencoded$261 = Py.newCode(1, var2, var1, "test_encoded_adjacent_nonencoded", 2975, false, false, self, 261, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "s", "parts", "hdr"};
      test_whitespace_eater$262 = Py.newCode(1, var2, var1, "test_whitespace_eater", 2985, false, false, self, 262, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "raises", "s"};
      test_broken_base64_header$263 = Py.newCode(1, var2, var1, "test_broken_base64_header", 2994, false, false, self, 263, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TestRFC2231$264 = Py.newCode(0, var2, var1, "TestRFC2231", 3002, false, false, self, 264, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "eq", "msg"};
      test_get_param$265 = Py.newCode(1, var2, var1, "test_get_param", 3003, false, false, self, 265, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg"};
      test_set_param$266 = Py.newCode(1, var2, var1, "test_set_param", 3011, false, false, self, 266, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg"};
      test_del_param$267 = Py.newCode(1, var2, var1, "test_del_param", 3048, false, false, self, 267, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "msg"};
      test_rfc2231_get_content_charset$268 = Py.newCode(1, var2, var1, "test_rfc2231_get_content_charset", 3078, false, false, self, 268, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "m", "msg", "param"};
      test_rfc2231_no_language_or_charset$269 = Py.newCode(1, var2, var1, "test_rfc2231_no_language_or_charset", 3083, false, false, self, 269, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "m", "msg"};
      test_rfc2231_no_language_or_charset_in_filename$270 = Py.newCode(1, var2, var1, "test_rfc2231_no_language_or_charset_in_filename", 3097, false, false, self, 270, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "m", "msg"};
      test_rfc2231_no_language_or_charset_in_filename_encoded$271 = Py.newCode(1, var2, var1, "test_rfc2231_no_language_or_charset_in_filename_encoded", 3109, false, false, self, 271, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "m", "msg"};
      test_rfc2231_partly_encoded$272 = Py.newCode(1, var2, var1, "test_rfc2231_partly_encoded", 3121, false, false, self, 272, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "m", "msg"};
      test_rfc2231_partly_nonencoded$273 = Py.newCode(1, var2, var1, "test_rfc2231_partly_nonencoded", 3134, false, false, self, 273, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "m", "msg"};
      test_rfc2231_no_language_or_charset_in_boundary$274 = Py.newCode(1, var2, var1, "test_rfc2231_no_language_or_charset_in_boundary", 3147, false, false, self, 274, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "m", "msg"};
      test_rfc2231_no_language_or_charset_in_charset$275 = Py.newCode(1, var2, var1, "test_rfc2231_no_language_or_charset_in_charset", 3159, false, false, self, 275, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "m", "msg"};
      test_rfc2231_bad_encoding_in_filename$276 = Py.newCode(1, var2, var1, "test_rfc2231_bad_encoding_in_filename", 3172, false, false, self, 276, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "m", "msg"};
      test_rfc2231_bad_encoding_in_charset$277 = Py.newCode(1, var2, var1, "test_rfc2231_bad_encoding_in_charset", 3184, false, false, self, 277, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "m", "msg"};
      test_rfc2231_bad_character_in_charset$278 = Py.newCode(1, var2, var1, "test_rfc2231_bad_character_in_charset", 3194, false, false, self, 278, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "m", "msg"};
      test_rfc2231_bad_character_in_filename$279 = Py.newCode(1, var2, var1, "test_rfc2231_bad_character_in_filename", 3204, false, false, self, 279, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "m", "msg"};
      test_rfc2231_unknown_encoding$280 = Py.newCode(1, var2, var1, "test_rfc2231_unknown_encoding", 3216, false, false, self, 280, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "m", "msg", "charset", "language", "s"};
      test_rfc2231_single_tick_in_filename_extended$281 = Py.newCode(1, var2, var1, "test_rfc2231_single_tick_in_filename_extended", 3225, false, false, self, 281, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "m", "msg", "param"};
      test_rfc2231_single_tick_in_filename$282 = Py.newCode(1, var2, var1, "test_rfc2231_single_tick_in_filename", 3238, false, false, self, 282, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "m", "msg", "charset", "language", "s"};
      test_rfc2231_tick_attack_extended$283 = Py.newCode(1, var2, var1, "test_rfc2231_tick_attack_extended", 3248, false, false, self, 283, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "m", "msg", "param"};
      test_rfc2231_tick_attack$284 = Py.newCode(1, var2, var1, "test_rfc2231_tick_attack", 3261, false, false, self, 284, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "m", "msg"};
      test_rfc2231_no_extended_values$285 = Py.newCode(1, var2, var1, "test_rfc2231_no_extended_values", 3272, false, false, self, 285, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "m", "msg", "charset", "language", "s"};
      test_rfc2231_encoded_then_unencoded_segments$286 = Py.newCode(1, var2, var1, "test_rfc2231_encoded_then_unencoded_segments", 3281, false, false, self, 286, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eq", "m", "msg", "charset", "language", "s"};
      test_rfc2231_unencoded_then_encoded_segments$287 = Py.newCode(1, var2, var1, "test_rfc2231_unencoded_then_encoded_segments", 3296, false, false, self, 287, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"mod", "_[3315_12]", "name"};
      _testclasses$288 = Py.newCode(0, var2, var1, "_testclasses", 3313, false, false, self, 288, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"suite", "testclass"};
      suite$289 = Py.newCode(0, var2, var1, "suite", 3318, false, false, self, 289, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"testclass"};
      test_main$290 = Py.newCode(0, var2, var1, "test_main", 3325, false, false, self, 290, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new test_email_renamed$py("email/test/test_email_renamed$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(test_email_renamed$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.openfile$1(var2, var3);
         case 2:
            return this.TestEmailBase$2(var2, var3);
         case 3:
            return this.ndiffAssertEqual$3(var2, var3);
         case 4:
            return this._msgobj$4(var2, var3);
         case 5:
            return this.TestMessageAPI$5(var2, var3);
         case 6:
            return this.test_get_all$6(var2, var3);
         case 7:
            return this.test_getset_charset$7(var2, var3);
         case 8:
            return this.test_set_charset_from_string$8(var2, var3);
         case 9:
            return this.test_set_payload_with_charset$9(var2, var3);
         case 10:
            return this.test_get_charsets$10(var2, var3);
         case 11:
            return this.test_get_filename$11(var2, var3);
         case 12:
            return this.test_get_filename_with_name_parameter$12(var2, var3);
         case 13:
            return this.test_get_boundary$13(var2, var3);
         case 14:
            return this.test_set_boundary$14(var2, var3);
         case 15:
            return this.test_get_decoded_payload$15(var2, var3);
         case 16:
            return this.test_get_decoded_uu_payload$16(var2, var3);
         case 17:
            return this.test_decoded_generator$17(var2, var3);
         case 18:
            return this.test__contains__$18(var2, var3);
         case 19:
            return this.test_as_string$19(var2, var3);
         case 20:
            return this.test_bad_param$20(var2, var3);
         case 21:
            return this.test_missing_filename$21(var2, var3);
         case 22:
            return this.test_bogus_filename$22(var2, var3);
         case 23:
            return this.test_missing_boundary$23(var2, var3);
         case 24:
            return this.test_get_params$24(var2, var3);
         case 25:
            return this.test_get_param_liberal$25(var2, var3);
         case 26:
            return this.test_get_param$26(var2, var3);
         case 27:
            return this.test_get_param_funky_continuation_lines$27(var2, var3);
         case 28:
            return this.test_get_param_with_semis_in_quotes$28(var2, var3);
         case 29:
            return this.test_has_key$29(var2, var3);
         case 30:
            return this.test_set_param$30(var2, var3);
         case 31:
            return this.test_del_param$31(var2, var3);
         case 32:
            return this.test_del_param_on_other_header$32(var2, var3);
         case 33:
            return this.test_set_type$33(var2, var3);
         case 34:
            return this.test_set_type_on_other_header$34(var2, var3);
         case 35:
            return this.test_get_content_type_missing$35(var2, var3);
         case 36:
            return this.test_get_content_type_missing_with_default_type$36(var2, var3);
         case 37:
            return this.test_get_content_type_from_message_implicit$37(var2, var3);
         case 38:
            return this.test_get_content_type_from_message_explicit$38(var2, var3);
         case 39:
            return this.test_get_content_type_from_message_text_plain_implicit$39(var2, var3);
         case 40:
            return this.test_get_content_type_from_message_text_plain_explicit$40(var2, var3);
         case 41:
            return this.test_get_content_maintype_missing$41(var2, var3);
         case 42:
            return this.test_get_content_maintype_missing_with_default_type$42(var2, var3);
         case 43:
            return this.test_get_content_maintype_from_message_implicit$43(var2, var3);
         case 44:
            return this.test_get_content_maintype_from_message_explicit$44(var2, var3);
         case 45:
            return this.test_get_content_maintype_from_message_text_plain_implicit$45(var2, var3);
         case 46:
            return this.test_get_content_maintype_from_message_text_plain_explicit$46(var2, var3);
         case 47:
            return this.test_get_content_subtype_missing$47(var2, var3);
         case 48:
            return this.test_get_content_subtype_missing_with_default_type$48(var2, var3);
         case 49:
            return this.test_get_content_subtype_from_message_implicit$49(var2, var3);
         case 50:
            return this.test_get_content_subtype_from_message_explicit$50(var2, var3);
         case 51:
            return this.test_get_content_subtype_from_message_text_plain_implicit$51(var2, var3);
         case 52:
            return this.test_get_content_subtype_from_message_text_plain_explicit$52(var2, var3);
         case 53:
            return this.test_get_content_maintype_error$53(var2, var3);
         case 54:
            return this.test_get_content_subtype_error$54(var2, var3);
         case 55:
            return this.test_replace_header$55(var2, var3);
         case 56:
            return this.test_broken_base64_payload$56(var2, var3);
         case 57:
            return this.TestEncoders$57(var2, var3);
         case 58:
            return this.test_encode_empty_payload$58(var2, var3);
         case 59:
            return this.test_default_cte$59(var2, var3);
         case 60:
            return this.test_default_cte$60(var2, var3);
         case 61:
            return this.TestLongHeaders$61(var2, var3);
         case 62:
            return this.test_split_long_continuation$62(var2, var3);
         case 63:
            return this.test_another_long_almost_unsplittable_header$63(var2, var3);
         case 64:
            return this.test_long_nonstring$64(var2, var3);
         case 65:
            return this.test_long_header_encode$65(var2, var3);
         case 66:
            return this.test_long_header_encode_with_tab_continuation$66(var2, var3);
         case 67:
            return this.test_header_splitter$67(var2, var3);
         case 68:
            return this.test_no_semis_header_splitter$68(var2, var3);
         case 69:
            return this.test_no_split_long_header$69(var2, var3);
         case 70:
            return this.test_splitting_multiple_long_lines$70(var2, var3);
         case 71:
            return this.test_splitting_first_line_only_is_long$71(var2, var3);
         case 72:
            return this.test_long_8bit_header$72(var2, var3);
         case 73:
            return this.test_long_8bit_header_no_charset$73(var2, var3);
         case 74:
            return this.test_long_to_header$74(var2, var3);
         case 75:
            return this.test_long_line_after_append$75(var2, var3);
         case 76:
            return this.test_shorter_line_with_append$76(var2, var3);
         case 77:
            return this.test_long_field_name$77(var2, var3);
         case 78:
            return this.test_long_received_header$78(var2, var3);
         case 79:
            return this.test_string_headerinst_eq$79(var2, var3);
         case 80:
            return this.test_long_unbreakable_lines_with_continuation$80(var2, var3);
         case 81:
            return this.test_another_long_multiline_header$81(var2, var3);
         case 82:
            return this.test_long_lines_with_different_header$82(var2, var3);
         case 83:
            return this.TestFromMangling$83(var2, var3);
         case 84:
            return this.setUp$84(var2, var3);
         case 85:
            return this.test_mangled_from$85(var2, var3);
         case 86:
            return this.test_dont_mangle_from$86(var2, var3);
         case 87:
            return this.TestMIMEAudio$87(var2, var3);
         case 88:
            return this.setUp$88(var2, var3);
         case 89:
            return this.test_guess_minor_type$89(var2, var3);
         case 90:
            return this.test_encoding$90(var2, var3);
         case 91:
            return this.test_checkSetMinor$91(var2, var3);
         case 92:
            return this.test_add_header$92(var2, var3);
         case 93:
            return this.TestMIMEImage$93(var2, var3);
         case 94:
            return this.setUp$94(var2, var3);
         case 95:
            return this.test_guess_minor_type$95(var2, var3);
         case 96:
            return this.test_encoding$96(var2, var3);
         case 97:
            return this.test_checkSetMinor$97(var2, var3);
         case 98:
            return this.test_add_header$98(var2, var3);
         case 99:
            return this.TestMIMEApplication$99(var2, var3);
         case 100:
            return this.test_headers$100(var2, var3);
         case 101:
            return this.test_body$101(var2, var3);
         case 102:
            return this.test_binary_body_with_encode_7or8bit$102(var2, var3);
         case 103:
            return this.test_binary_body_with_encode_noop$103(var2, var3);
         case 104:
            return this.TestMIMEText$104(var2, var3);
         case 105:
            return this.setUp$105(var2, var3);
         case 106:
            return this.test_types$106(var2, var3);
         case 107:
            return this.test_payload$107(var2, var3);
         case 108:
            return this.test_charset$108(var2, var3);
         case 109:
            return this.TestMultipart$109(var2, var3);
         case 110:
            return this.setUp$110(var2, var3);
         case 111:
            return this.test_hierarchy$111(var2, var3);
         case 112:
            return this.test_empty_multipart_idempotent$112(var2, var3);
         case 113:
            return this.test_no_parts_in_a_multipart_with_none_epilogue$113(var2, var3);
         case 114:
            return this.test_no_parts_in_a_multipart_with_empty_epilogue$114(var2, var3);
         case 115:
            return this.test_one_part_in_a_multipart$115(var2, var3);
         case 116:
            return this.test_seq_parts_in_a_multipart_with_empty_preamble$116(var2, var3);
         case 117:
            return this.test_seq_parts_in_a_multipart_with_none_preamble$117(var2, var3);
         case 118:
            return this.test_seq_parts_in_a_multipart_with_none_epilogue$118(var2, var3);
         case 119:
            return this.test_seq_parts_in_a_multipart_with_empty_epilogue$119(var2, var3);
         case 120:
            return this.test_seq_parts_in_a_multipart_with_nl_epilogue$120(var2, var3);
         case 121:
            return this.test_message_external_body$121(var2, var3);
         case 122:
            return this.test_double_boundary$122(var2, var3);
         case 123:
            return this.test_nested_inner_contains_outer_boundary$123(var2, var3);
         case 124:
            return this.test_nested_with_same_boundary$124(var2, var3);
         case 125:
            return this.test_boundary_in_non_multipart$125(var2, var3);
         case 126:
            return this.test_boundary_with_leading_space$126(var2, var3);
         case 127:
            return this.test_boundary_without_trailing_newline$127(var2, var3);
         case 128:
            return this.TestNonConformant$128(var2, var3);
         case 129:
            return this.test_parse_missing_minor_type$129(var2, var3);
         case 130:
            return this.test_same_boundary_inner_outer$130(var2, var3);
         case 131:
            return this.test_multipart_no_boundary$131(var2, var3);
         case 132:
            return this.test_invalid_content_type$132(var2, var3);
         case 133:
            return this.test_no_start_boundary$133(var2, var3);
         case 134:
            return this.test_no_separating_blank_line$134(var2, var3);
         case 135:
            return this.test_lying_multipart$135(var2, var3);
         case 136:
            return this.test_missing_start_boundary$136(var2, var3);
         case 137:
            return this.test_first_line_is_continuation_header$137(var2, var3);
         case 138:
            return this.TestRFC2047$138(var2, var3);
         case 139:
            return this.test_rfc2047_multiline$139(var2, var3);
         case 140:
            return this.test_whitespace_eater_unicode$140(var2, var3);
         case 141:
            return this.test_whitespace_eater_unicode_2$141(var2, var3);
         case 142:
            return this.test_rfc2047_missing_whitespace$142(var2, var3);
         case 143:
            return this.test_rfc2047_with_whitespace$143(var2, var3);
         case 144:
            return this.TestMIMEMessage$144(var2, var3);
         case 145:
            return this.setUp$145(var2, var3);
         case 146:
            return this.test_type_error$146(var2, var3);
         case 147:
            return this.test_valid_argument$147(var2, var3);
         case 148:
            return this.test_bad_multipart$148(var2, var3);
         case 149:
            return this.test_generate$149(var2, var3);
         case 150:
            return this.test_parse_message_rfc822$150(var2, var3);
         case 151:
            return this.test_dsn$151(var2, var3);
         case 152:
            return this.test_epilogue$152(var2, var3);
         case 153:
            return this.test_no_nl_preamble$153(var2, var3);
         case 154:
            return this.test_default_type$154(var2, var3);
         case 155:
            return this.test_default_type_with_explicit_container_type$155(var2, var3);
         case 156:
            return this.test_default_type_non_parsed$156(var2, var3);
         case 157:
            return this.test_mime_attachments_in_constructor$157(var2, var3);
         case 158:
            return this.TestIdempotent$158(var2, var3);
         case 159:
            return this._msgobj$159(var2, var3);
         case 160:
            return this._idempotent$160(var2, var3);
         case 161:
            return this.test_parse_text_message$161(var2, var3);
         case 162:
            return this.test_parse_untyped_message$162(var2, var3);
         case 163:
            return this.test_simple_multipart$163(var2, var3);
         case 164:
            return this.test_MIME_digest$164(var2, var3);
         case 165:
            return this.test_long_header$165(var2, var3);
         case 166:
            return this.test_MIME_digest_with_part_headers$166(var2, var3);
         case 167:
            return this.test_mixed_with_image$167(var2, var3);
         case 168:
            return this.test_multipart_report$168(var2, var3);
         case 169:
            return this.test_dsn$169(var2, var3);
         case 170:
            return this.test_preamble_epilogue$170(var2, var3);
         case 171:
            return this.test_multipart_one_part$171(var2, var3);
         case 172:
            return this.test_multipart_no_parts$172(var2, var3);
         case 173:
            return this.test_no_start_boundary$173(var2, var3);
         case 174:
            return this.test_rfc2231_charset$174(var2, var3);
         case 175:
            return this.test_more_rfc2231_parameters$175(var2, var3);
         case 176:
            return this.test_text_plain_in_a_multipart_digest$176(var2, var3);
         case 177:
            return this.test_nested_multipart_mixeds$177(var2, var3);
         case 178:
            return this.test_message_external_body_idempotent$178(var2, var3);
         case 179:
            return this.test_content_type$179(var2, var3);
         case 180:
            return this.test_parser$180(var2, var3);
         case 181:
            return this.TestMiscellaneous$181(var2, var3);
         case 182:
            return this.test_message_from_string$182(var2, var3);
         case 183:
            return this.test_message_from_file$183(var2, var3);
         case 184:
            return this.test_message_from_string_with_class$184(var2, var3);
         case 185:
            return this.MyMessage$185(var2, var3);
         case 186:
            return this.test_message_from_file_with_class$186(var2, var3);
         case 187:
            return this.MyMessage$187(var2, var3);
         case 188:
            return this.test__all__$188(var2, var3);
         case 189:
            return this.test_formatdate$189(var2, var3);
         case 190:
            return this.test_formatdate_localtime$190(var2, var3);
         case 191:
            return this.test_formatdate_usegmt$191(var2, var3);
         case 192:
            return this.test_parsedate_none$192(var2, var3);
         case 193:
            return this.test_parsedate_compact$193(var2, var3);
         case 194:
            return this.test_parsedate_no_dayofweek$194(var2, var3);
         case 195:
            return this.test_parsedate_compact_no_dayofweek$195(var2, var3);
         case 196:
            return this.test_parsedate_acceptable_to_time_functions$196(var2, var3);
         case 197:
            return this.test_parseaddr_empty$197(var2, var3);
         case 198:
            return this.test_noquote_dump$198(var2, var3);
         case 199:
            return this.test_escape_dump$199(var2, var3);
         case 200:
            return this.test_escape_backslashes$200(var2, var3);
         case 201:
            return this.test_name_with_dot$201(var2, var3);
         case 202:
            return this.test_multiline_from_comment$202(var2, var3);
         case 203:
            return this.test_quote_dump$203(var2, var3);
         case 204:
            return this.test_fix_eols$204(var2, var3);
         case 205:
            return this.test_charset_richcomparisons$205(var2, var3);
         case 206:
            return this.test_getaddresses$206(var2, var3);
         case 207:
            return this.test_getaddresses_nasty$207(var2, var3);
         case 208:
            return this.test_getaddresses_embedded_comment$208(var2, var3);
         case 209:
            return this.test_utils_quote_unquote$209(var2, var3);
         case 210:
            return this.test_get_body_encoding_with_bogus_charset$210(var2, var3);
         case 211:
            return this.test_get_body_encoding_with_uppercase_charset$211(var2, var3);
         case 212:
            return this.test_charsets_case_insensitive$212(var2, var3);
         case 213:
            return this.test_partial_falls_inside_message_delivery_status$213(var2, var3);
         case 214:
            return this.TestIterators$214(var2, var3);
         case 215:
            return this.test_body_line_iterator$215(var2, var3);
         case 216:
            return this.test_typed_subpart_iterator$216(var2, var3);
         case 217:
            return this.test_typed_subpart_iterator_default_type$217(var2, var3);
         case 218:
            return this.TestParsers$218(var2, var3);
         case 219:
            return this.test_header_parser$219(var2, var3);
         case 220:
            return this.test_whitespace_continuation$220(var2, var3);
         case 221:
            return this.test_whitespace_continuation_last_header$221(var2, var3);
         case 222:
            return this.test_crlf_separation$222(var2, var3);
         case 223:
            return this.test_multipart_digest_with_extra_mime_headers$223(var2, var3);
         case 224:
            return this.test_three_lines$224(var2, var3);
         case 225:
            return this.test_strip_line_feed_and_carriage_return_in_headers$225(var2, var3);
         case 226:
            return this.test_rfc2822_header_syntax$226(var2, var3);
         case 227:
            return this.test_rfc2822_space_not_allowed_in_header$227(var2, var3);
         case 228:
            return this.test_rfc2822_one_character_header$228(var2, var3);
         case 229:
            return this.TestBase64$229(var2, var3);
         case 230:
            return this.test_len$230(var2, var3);
         case 231:
            return this.test_decode$231(var2, var3);
         case 232:
            return this.test_encode$232(var2, var3);
         case 233:
            return this.test_header_encode$233(var2, var3);
         case 234:
            return this.TestQuopri$234(var2, var3);
         case 235:
            return this.setUp$235(var2, var3);
         case 236:
            return this.test_header_quopri_check$236(var2, var3);
         case 237:
            return this.test_body_quopri_check$237(var2, var3);
         case 238:
            return this.test_header_quopri_len$238(var2, var3);
         case 239:
            return this.test_body_quopri_len$239(var2, var3);
         case 240:
            return this.test_quote_unquote_idempotent$240(var2, var3);
         case 241:
            return this.test_header_encode$241(var2, var3);
         case 242:
            return this.test_decode$242(var2, var3);
         case 243:
            return this.test_encode$243(var2, var3);
         case 244:
            return this.TestCharset$244(var2, var3);
         case 245:
            return this.tearDown$245(var2, var3);
         case 246:
            return this.test_idempotent$246(var2, var3);
         case 247:
            return this.test_body_encode$247(var2, var3);
         case 248:
            return this.test_unicode_charset_name$248(var2, var3);
         case 249:
            return this.TestHeader$249(var2, var3);
         case 250:
            return this.test_simple$250(var2, var3);
         case 251:
            return this.test_simple_surprise$251(var2, var3);
         case 252:
            return this.test_header_needs_no_decoding$252(var2, var3);
         case 253:
            return this.test_long$253(var2, var3);
         case 254:
            return this.test_multilingual$254(var2, var3);
         case 255:
            return this.test_header_ctor_default_args$255(var2, var3);
         case 256:
            return this.test_explicit_maxlinelen$256(var2, var3);
         case 257:
            return this.test_us_ascii_header$257(var2, var3);
         case 258:
            return this.test_string_charset$258(var2, var3);
         case 259:
            return this.test_utf8_shortest$259(var2, var3);
         case 260:
            return this.test_bad_8bit_header$260(var2, var3);
         case 261:
            return this.test_encoded_adjacent_nonencoded$261(var2, var3);
         case 262:
            return this.test_whitespace_eater$262(var2, var3);
         case 263:
            return this.test_broken_base64_header$263(var2, var3);
         case 264:
            return this.TestRFC2231$264(var2, var3);
         case 265:
            return this.test_get_param$265(var2, var3);
         case 266:
            return this.test_set_param$266(var2, var3);
         case 267:
            return this.test_del_param$267(var2, var3);
         case 268:
            return this.test_rfc2231_get_content_charset$268(var2, var3);
         case 269:
            return this.test_rfc2231_no_language_or_charset$269(var2, var3);
         case 270:
            return this.test_rfc2231_no_language_or_charset_in_filename$270(var2, var3);
         case 271:
            return this.test_rfc2231_no_language_or_charset_in_filename_encoded$271(var2, var3);
         case 272:
            return this.test_rfc2231_partly_encoded$272(var2, var3);
         case 273:
            return this.test_rfc2231_partly_nonencoded$273(var2, var3);
         case 274:
            return this.test_rfc2231_no_language_or_charset_in_boundary$274(var2, var3);
         case 275:
            return this.test_rfc2231_no_language_or_charset_in_charset$275(var2, var3);
         case 276:
            return this.test_rfc2231_bad_encoding_in_filename$276(var2, var3);
         case 277:
            return this.test_rfc2231_bad_encoding_in_charset$277(var2, var3);
         case 278:
            return this.test_rfc2231_bad_character_in_charset$278(var2, var3);
         case 279:
            return this.test_rfc2231_bad_character_in_filename$279(var2, var3);
         case 280:
            return this.test_rfc2231_unknown_encoding$280(var2, var3);
         case 281:
            return this.test_rfc2231_single_tick_in_filename_extended$281(var2, var3);
         case 282:
            return this.test_rfc2231_single_tick_in_filename$282(var2, var3);
         case 283:
            return this.test_rfc2231_tick_attack_extended$283(var2, var3);
         case 284:
            return this.test_rfc2231_tick_attack$284(var2, var3);
         case 285:
            return this.test_rfc2231_no_extended_values$285(var2, var3);
         case 286:
            return this.test_rfc2231_encoded_then_unencoded_segments$286(var2, var3);
         case 287:
            return this.test_rfc2231_unencoded_then_encoded_segments$287(var2, var3);
         case 288:
            return this._testclasses$288(var2, var3);
         case 289:
            return this.suite$289(var2, var3);
         case 290:
            return this.test_main$290(var2, var3);
         default:
            return null;
      }
   }
}
