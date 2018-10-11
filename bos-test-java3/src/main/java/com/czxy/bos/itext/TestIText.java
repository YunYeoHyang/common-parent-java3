package com.czxy.bos.itext;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;
import org.junit.Test;

import java.io.FileOutputStream;

public class TestIText {

    @Test
    public void printPDF() throws Exception {
        Document document = new Document(); // 一个pdf文件
        PdfWriter.getInstance(document, new FileOutputStream("test1.pdf"));
        document.open();
        document.add(new Paragraph("helloworld"));
        document.close();
    }

    @Test
    public void printCNPDF() throws Exception {
        Document document = new Document(); // 一个pdf文件
        PdfWriter.getInstance(document, new FileOutputStream("test0.pdf"));
        document.open();
        BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        document.add(new Paragraph("你好，传智播客", new Font(bfChinese)));
        document.close();
    }
}
