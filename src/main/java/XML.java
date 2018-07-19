import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


class XML {
    static List<Word> readXML(File file) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document doc = reader.read(file);
        Element root = doc.getRootElement();
        List<Element> bookList = root.elements("item");
        List<Word> wordList = new ArrayList<Word>();
        for (Element e :
                bookList) {
            Word word = new Word();

            word.setWord(e.element("word").getText());
            word.setTrans(e.element("trans").getText().replaceAll("\n\n","\n"));
            word.setPhonetic(e.element("phonetic").getText());

            wordList.add(word);
        }
        return wordList;
    }
}
