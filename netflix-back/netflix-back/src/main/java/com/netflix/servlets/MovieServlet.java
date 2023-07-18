package com.netflix.servlets;

// Importando classes necessárias
import com.netflix.controllers.MovieController;
import com.netflix.services.ServiceFactory;
import com.netflix.services.UserService;
import com.netflix.auth.SessionManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/movies/*")
public class MovieServlet extends HttpServlet {
    private MovieController movieController;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        this.movieController = new MovieController();
        // Inicializa pela factory
        this.userService = ServiceFactory.createUserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("movieServlet doGet called");

        // Pega o id da sessão
        String sessionId = request.getHeader("X-Session-Id");

        // Verifica o id da sessão
        if (sessionId == null || !SessionManager.getInstance().validateSession(sessionId)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"message\": \"Erro de session\"}");
            return;
        }

        // Se o ID da sessão for valido, pega os dados do usuario a partir do ID da sessao e verifica a idade para
        // saber se é maior de 18 ou não
        int userId = SessionManager.getInstance().getUserId(sessionId);
        int userAge = userService.getUserAge(userId);
        boolean includeAdultContent = userAge >= 18;

        // Pega o path da requisição
        String pathInfo = request.getPathInfo();
        if (pathInfo.startsWith("/")) {
            pathInfo = pathInfo.substring(1);
        }
        // Puxa os filmes solicitados pelo front
        String moviesJson = movieController.getMovies(pathInfo, includeAdultContent);

        // Envia a resposta como JSON dos filmes
        response.setContentType("application/json");
        response.getWriter().write(moviesJson);
    }
}
