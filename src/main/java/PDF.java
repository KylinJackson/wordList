import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class PDF {
    private Document doc = null;
    private Font wordFont;
    private Font phoFont;
    private Font transFont;
    private PdfWriter writer = null;

    void createPDF(File file) throws Exception {
        FileOutputStream out = new FileOutputStream(file);
        Rectangle rect = new Rectangle(PageSize.B5);
        doc = new Document(rect);
        writer = PdfWriter.getInstance(doc, out);
        doc.addTitle(file.getName());
        doc.addAuthor("Kylin");
        int marginLeftRight = 50;
        int marginTopBottom = 50;
        doc.setMargins(marginLeftRight, marginLeftRight, marginTopBottom, marginTopBottom);
        doc.open();

        wordFont = new Font(Font.FontFamily.TIMES_ROMAN, 11);

        String font_pho = "C:\\windows\\Fonts\\l_10646.ttf";
        BaseFont bf_pho = BaseFont.createFont(font_pho,
                BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        phoFont = new Font(bf_pho, 8);

        String font_cn = "C:\\windows\\Fonts\\simsun.ttc";
        BaseFont bf_tra = BaseFont.createFont(font_cn + ",1",
                BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        transFont = new Font(bf_tra, 8);
    }

    void close() {
        if (doc != null) {
            doc.close();
        }
        if (writer != null) {
            writer.close();
        }
    }

    void printList(List<Word> wordList) throws Exception {
        for (Word word :
                wordList) {
            Paragraph para = new Paragraph();
            para.add(new Chunk(word.getWord(), wordFont));
            para.add(Chunk.NEWLINE);
            para.add(new Chunk(word.getPhonetic(), phoFont));
            para.add(Chunk.NEWLINE);
            para.add(new Chunk(word.getTrans(), transFont));
            doc.add(para);
        }
    }
}
