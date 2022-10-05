package de.thb.webbaki.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;


@Service
public class ReportService {

    /**
     *
     * @param template Does not need the .html.
     * @param context Contains the variables that we want to be passed to Thymeleaf.
     *                Addable with context.setVariable(key, value);
     * @return the html code as a String
     */
    public String parseThymeleafTemplateToHtml(String template, Context context){
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        return templateEngine.process("templates/" + template, context);
    }

    /**
     * All styles in the html have to be inline.
     * Not closed elements like <br> and <link> have to be closed like this
     * <br></br> and <link></link>
     * @param html
     * @param outputStream
     * @throws IOException
     */
    public void generatePdfFromHtml(String html, OutputStream outputStream) throws IOException {
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(bufferedOutputStream);

        bufferedOutputStream.close();
    }
}
