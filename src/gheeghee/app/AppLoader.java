package gheeghee.app;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class AppLoader {
    public AppData loadApp(String appPath, String appDestDirectory) {
        AppData appData = new AppData();

        try {
            UnzipUtility.unzip(appPath, appDestDirectory);

            File xmlFile = new File(appDestDirectory + "/Contents/info.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);

            document.getDocumentElement().normalize();

            String name = getTagValue(document, "Name");
            String version = getTagValue(document, "Version");
            String type = getTagValue(document, "Type");
            String entry = getTagValue(document, "Entry");

            System.out.println(name+version+type+entry);

            appData.setName(name);
            appData.setVersion(version);
            appData.setType(type);
            appData.setEntry(entry);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return appData;
    }

    private String getTagValue(Document doc, String tagName) {
        NodeList list = doc.getElementsByTagName(tagName);
        if (list != null && list.getLength() > 0) {
            return list.item(0).getTextContent();
        }
        return null;
    }
}
