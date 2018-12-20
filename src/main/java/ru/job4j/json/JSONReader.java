package ru.job4j.json;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Reads JSON object coming in http request and adds object to store.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
public class JSONReader extends HttpServlet {
    /**
     * UserStorage of users.
     */
    private UserStorage userStorage;
    /**
     * JSON mapper to convert objects to json and vice versa.
     */
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void init() {
        ServletContext context = this.getServletContext();
        SingleHolder holder = (SingleHolder) context.getAttribute("singleHolder");
        this.userStorage = holder.getUserStorage();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> users = this.userStorage.getAll();
        String result = this.mapper.writeValueAsString(users);
        this.writeResultToResponse(resp, result);
    }

    /**
     * Handles "POST" htt requests.
     * <p>
     * Processes JSON string, forms object and stores it into the userStorage.
     *
     * @param req  Http request.
     * @param resp Http response.
     * @throws IOException If some I/O error occurs.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final String json = this.readerToString(req.getReader());
        User user = this.mapper.readValue(json, User.class);
        this.userStorage.add(user);
    }

    /**
     * Reads data from reader and returns it as a single object.
     *
     * @param reader Reader where to get data from.
     * @return Data from reader if reader != null, empty data if reader == null.
     * @throws IOException If an I/O error occurs.
     */
    private String readerToString(final BufferedReader reader) throws IOException {
        final StringBuilder result = new StringBuilder();
        if (reader != null) {
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
        }
        return result.toString();
    }

    /**
     * Writes servlet method result to response.
     *
     * @param resp   Response object.
     * @param result Result to write.
     */
    private void writeResultToResponse(HttpServletResponse resp, String result) throws IOException {
        try (PrintWriter writer = resp.getWriter()) {
            writer.write(result);
            writer.flush();
        }
    }

}
