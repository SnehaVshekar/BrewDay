package ser422.sneha.web.lab1;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

public class xmlServlet extends HttpServlet {
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException {

        String fn = request.getParameter("firstName");
        String ln = request.getParameter("lastName");
        String programming_languages = request.getParameter("Programming languages");
        String weekDays = request.getParameter("week_Days");
        String email = request.getParameter("email");

        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();


        pw.println("<body><H2>Hi " + fn + ln + programming_languages + weekDays + email + "</H2>");
        try {
            new xmlServlet().createXmlTree(fn, ln, programming_languages, weekDays, email);
        } catch (Exception e) {
            e.printStackTrace();
        }

        pw.println("<b>Xml File Created Successfully</b>");
    }


    public void createXmlTree(String fn, String ln, String programming_languages, String weekDays, String email) throws Exception {

        try {
            DocumentBuilderFactory builderFactory =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = builderFactory.newDocumentBuilder();
            //creating a new instance of a DOM to build a DOM tree.
            Document doc = docBuilder.newDocument();

            Element root = doc.createElement("person");
            doc.appendChild(root);

            Element child1 = doc.createElement("firstName");
            root.appendChild(child1);

            Text text1 = doc.createTextNode(fn);
            child1.appendChild(text1);

            Element child2 = doc.createElement("lastName");
            child1.appendChild(child2);

            Text text2 = doc.createTextNode(ln);
            child2.appendChild(text2);

            Element child3 = doc.createElement("languages");
            child2.appendChild(child3);

            Text text3 = doc.createTextNode(programming_languages);
            child3.appendChild(text3);

            Element child4 = doc.createElement("availability");
            child3.appendChild(child4);

            Text text4 = doc.createTextNode(weekDays);
            child4.appendChild(text4);

            Element child5 = doc.createElement("email");
            child4.appendChild(child5);

            Text text5 = doc.createTextNode(email);
            child5.appendChild(text5);

            //TransformerFactory instance is used to create Transformer objects.
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            // create string from xml tree
            StringWriter sw = new StringWriter();
            StreamResult result = new StreamResult(sw);
            DOMSource source = new DOMSource(doc);
            transformer.transform(source, result);
            String xmlString = sw.toString();

            File file = new File("C:/Users/gu/Desktop/ser 422/newxml.xml");
            BufferedWriter bw = new BufferedWriter
                    (new OutputStreamWriter(new FileOutputStream(file)));
            bw.write(xmlString);
            bw.flush();
            bw.close();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();

        }
    }
}