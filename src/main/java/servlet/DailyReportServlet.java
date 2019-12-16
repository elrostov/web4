package servlet;

import com.google.gson.Gson;
import service.CarService;
import service.DailyReportService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DailyReportServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("---DailyReportServlet doGet start---");
        System.out.println();
        Gson gson = new Gson();

        if (req.getPathInfo().contains("all")) {
            // Начальство может потребовать отчет за все дни, отправив запрос на `/report/all`.
            resp.getWriter().write(gson.toJson(DailyReportService.getInstance().getAllDailyReports()));
            resp.setStatus(200);
            System.out.println("Report for all days created.");
            System.out.println();
        } else if (req.getPathInfo().contains("last")) {
            // Начальство может потребовать отчет по юрл `/report/last` за прошедший день,
            resp.getWriter().write(gson.toJson(DailyReportService.getInstance().getLastReport()));
            resp.setStatus(200);
            System.out.println("Report for last day created.");
            System.out.println();
        }
        System.out.println("---DailyReportServlet doGet end---");
        System.out.println();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Так же есть возможность удалить все данные об отчетах и машинах DELETE
        // запросом на /report.
        System.out.println("---DailyReportServlet doDelete start---");
        System.out.println("Deleting all daily reports...");
        DailyReportService.getInstance().deleteAllDailyReports();
        System.out.println("All daily reports deleted.");
        System.out.println("Deleting all cars...");
        CarService.getInstance().deleteAllCars();
        System.out.println("All cars deleted.");
        System.out.println("---DailyReportServlet doDelete end---");
        System.out.println();
    }
}
