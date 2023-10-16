import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/setting")
public class SettingsPage extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        Cookie[] color = request.getCookies();
//        for (int i = 0; i < color.length; i++) {
//            System.out.println(color[i].getValue() + " " + color[i].getName());
//        }
        request.getRequestDispatcher("/jsp/settings.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String color = request.getParameter("color");
        System.out.println(color);
        //We generate an uuid and then add it in the Cookie (first one is for the name "whatever the name is", the second is for the uuid)
        Cookie colorCookie = new Cookie("color", color);
        response.addCookie(colorCookie);
        //max age -1 deletes the cookie after closing the browser, 0 delete it right away!
        // the parameter is taken in seconds
        colorCookie.setMaxAge(60);
        response.sendRedirect("/setting");
    }
}
