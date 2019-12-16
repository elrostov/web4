package servlet;

import com.google.gson.Gson;
import service.DailyReportService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NewDayServlet extends HttpServlet {
    // Смена дней происходит запросом на `/newday`. После смены дня, салон
    // обязан сформировать отчет, в котором содержится суммарная выручка
    // за день и количество проданных машин.

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        System.out.println("---NewDayServlet doGet start---");
        System.out.println();
        Gson gson = new Gson();
//        DailyReportService.getInstance().createEmptyDailyReport();
        resp.getWriter().write(gson.toJson(DailyReportService.getInstance().getLastReport()));
        resp.setStatus(200);
        System.out.println("---NewDayServlet doGet end---");
        System.out.println();
    }
}
