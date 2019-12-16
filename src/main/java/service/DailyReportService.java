package service;

import DAO.CarDao;
import DAO.DailyReportDao;
import model.DailyReport;
import org.hibernate.SessionFactory;
import util.DBHelper;

import java.util.List;

public class DailyReportService {

    private Long earnings = 0L;

    private Long soldCars = 0L;

    public Long getSoldCars() {
        return soldCars;
    }

    public void setSoldCars(Long soldCars) {
        this.soldCars += soldCars;
    }

    public Long getEarnings() {
        return earnings;
    }

    public void setEarnings(Long earnings) {
        this.earnings += earnings;
    }

    private static DailyReportService dailyReportService;

    private SessionFactory sessionFactory;

    private DailyReportService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static DailyReportService getInstance() {
        if (dailyReportService == null) {
            dailyReportService = new DailyReportService(DBHelper.getSessionFactory());
        }
        return dailyReportService;
    }

    public List<DailyReport> getAllDailyReports() {
        return new DailyReportDao(sessionFactory.openSession()).getAllDailyReportsDAO();
    }

    public DailyReport getLastReport() {
        return new DailyReportDao(sessionFactory.openSession())
                .getLastReportDAO();
    }

    public void createEmptyDailyReport() {
        new DailyReportDao(sessionFactory.openSession()).createEmptyDailyReportDAO();
    }

    public boolean deleteAllDailyReports() {
        return new DailyReportDao(sessionFactory.openSession()).deleteAllDailyReportsDAO();
    }
}
