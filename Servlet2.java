package ser422.sneha.web.lab1;

        import org.xml.sax.SAXException;
        import ser422.sneha.web.utils.XMLParserClass;

        import javax.servlet.ServletException;
        import javax.servlet.http.HttpServlet;
        import javax.servlet.http.HttpServletRequest;
        import javax.servlet.http.HttpServletResponse;
        import java.io.*;

public class Servlet2 extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws ServletException, IOException {

       try {
           String fn = httpServletRequest.getParameter("firstName");
           String ln = httpServletRequest.getParameter("lastName");
           String programming_languages = httpServletRequest.getParameter("Programming languages");
           String weekDays = httpServletRequest.getParameter("week_Days");
           String email = httpServletRequest.getParameter("email");

           try {
               XMLParserClass.readXML(fn, ln, programming_languages, weekDays, email, httpServletResponse);
           } catch (SAXException e) {
               //Set error code 510
               //error message "Server faced an exceptio while parsing the XML datastore"
               //e.printstacktrace
           } catch(IOException e){
               httpServletResponse.sendError(400, "I/O Exception occured");
           }
       }catch(Exception e){
           httpServletResponse.sendError(500, "Unknown Server Exception Occured");
       }
    }


}

