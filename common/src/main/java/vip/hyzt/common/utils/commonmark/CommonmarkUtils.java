package vip.hyzt.common.utils.commonmark;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

/**
 * markdown转Html工具类
 *
 * @author hyzt
 * @date 2020-12-09
 */
public class CommonmarkUtils
{
    /**
     * markdown转Html
     *
     * @param markdown markdown内容
     * @return html
     */
    public static String markdownFoHtml(String markdown)
    {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(document);
    }
}
