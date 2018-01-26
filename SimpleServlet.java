package ser422.sneha.web.lab1;

import org.jdom2.Content;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.Arrays;

public class SimpleServlet extends HttpServlet {


    @Override
    public void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws ServletException,IOException {
        String fn = httpServletRequest.getParameter("firstName");
        String ln = httpServletRequest.getParameter("lastName");
        String programming_languages = httpServletRequest.getParameter("Programming languages");
        String weekDays = httpServletRequest.getParameter("week_Days");
        String email = httpServletRequest.getParameter("email");


        httpServletResponse.setContentType("text/html");
        PrintWriter Out = httpServletResponse.getWriter();

        Out.println("<body><H2>Hi " + fn + ln + programming_languages + weekDays + email + "</H2>");

        try {
            new SimpleServlet().createXmlTree(fn, ln, programming_languages, weekDays, email, Out);

        } catch (Exception e) {
            e.printStackTrace();
        }

        Out.println("<b>Xml File Createde Successfully</b>");
        Out.println("<a href=\"" + httpServletRequest.getContextPath() + "\">Landing Page</a>");

        Out.println("</body>");


    }
    @Override
    public void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws IOException, ServletException {
        doGet(httpServletRequest,httpServletResponse);
    }
    @Override
    public void init(ServletConfig servletConfig) {
        try {
            //String absoluteDiskPath = this.getServletContext().getRealPath("/WEB-INF/sneha/People.xml");

            File inputFile = new File("C:\\Users\\gu\\ser422\\apache-tomcat-8.5.24\\webapps\\sneha-lab1-1.0-SNAPSHOT\\WEB-INF\\People.xml");
            inputFile.createNewFile();

            FileWriter writer = new FileWriter(inputFile);
            writer.write("<People>\n");
            writer.write("</People>");
            writer.close();

        }
        catch(Exception e){
            throw new IllegalStateException("Exception occurred during XML file creation - name - " + e.getMessage() + "  \n" + Arrays.toString(e.getStackTrace()));

        }

    }

    public void createXmlTree(String fn, String ln, String programming_languages, String weekDays, String email, PrintWriter Out)
            throws Exception {

        int count = 0;
        try {
            File inputFile = new File("C:\\Users\\gu\\ser422\\apache-tomcat-8.5.24\\webapps\\sneha-lab1-1.0-SNAPSHOT\\WEB-INF\\People.xml");
            SAXBuilder saxBuilder = new SAXBuilder();
            Document document = saxBuilder.build(inputFile);


            Element people = document.getRootElement();

            for (Element person : people.getChildren()) {
                if (person.getChild("firstName").getText().equals(fn) && person.getChild("lastName").getText().equals(ln)) {
                    // person.getChild("languages").setName(programming_languages);
                    person.getChild("languages").setText(programming_languages);
                    person.getChild("availability").setText(weekDays);
                    person.getChild("emailID").setText(email);
                    XMLOutputter xmlOutput = new XMLOutputter();

                    xmlOutput.setFormat(Format.getPrettyFormat());
                    xmlOutput.output(document, new FileWriter("C:\\Users\\gu\\ser422\\apache-tomcat-8.5.24\\webapps\\sneha-lab1-1.0-SNAPSHOT\\WEB-INF\\People.xml"));

                    //add others
                    Out.println("<b>Xml File has </b>" + people.getChildren().size() + "<b> values");
                    return;
                }
            }


            //Create new person

            Element person = new Element("Person");

            Element firstName = new Element("firstName").setText(fn);
            person.addContent(firstName);

            Element lastName = new Element("lastName").setText(ln);
            person.addContent(lastName);

            Element languages = new Element("languages").setText(programming_languages);
            person.addContent(languages);

            Element availability = new Element("availability").setText(weekDays);
            person.addContent(availability);

            Element emailID = new Element("emailID").setText(email);
            person.addContent(emailID);

            people.addContent(person);


            XMLOutputter xmlOutput = new XMLOutputter();

            xmlOutput.setFormat(Format.getPrettyFormat());
            xmlOutput.output(document, new FileWriter("C:\\Users\\gu\\ser422\\apache-tomcat-8.5.24\\webapps\\sneha-lab1-1.0-SNAPSHOT\\WEB-INF\\People.xml"));

            Out.println("<b>Xml File has </b>" + people.getChildren().size() + "<b> values");

            //add to people
            //return


        } catch (Exception tfe) {
            tfe.printStackTrace();

        }

    }
}
