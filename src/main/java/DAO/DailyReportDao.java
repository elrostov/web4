package DAO;

import model.Car;
import model.DailyReport;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class DailyReportDao {

    private Session session;

    public DailyReportDao(Session session) {
        this.session = session;
    }

    public List<DailyReport> getAllDailyReportsDAO() {
        List<DailyReport> dailyReports = session.createQuery("FROM DailyReport").list();
        return dailyReports;
    }

    public boolean deleteAllDailyReportsDAO() {
        Transaction t = session.beginTransaction();
        try {
            Query query = session.createQuery("DELETE FROM DailyReport");
            query.executeUpdate();
            t.commit();
            session.close();
            return true;
        } catch (HibernateException e) {
            t.rollback();
            session.close();
            return false;
        }
    }

    public void createEmptyDailyReportDAO() {
        Transaction t = session.beginTransaction();
        try {
            session.save(new DailyReport(0L, 0L));
            t.commit();
            session.close();
        } catch (HibernateException e) {
            t.rollback();
            session.close();
        }
    }

    public DailyReport getLastReportDAO() {
        return (DailyReport) session.createQuery("FROM DailyReport WHERE id = "
                + (getAllDailyReportsDAO().size())).uniqueResult();
    }

    public boolean updateDailyReportDAO(Car purchasedCar) {
        Transaction t = session.beginTransaction();
        try {
            List<DailyReport> dailyReports = getAllDailyReportsDAO();
            DailyReport currentDailyReport = dailyReports.get(dailyReports.size() - 1);
            Query query = session.createQuery("update DailyReport set earnings = :earnings, " +
                    "soldCars = :soldCars where id = :id");
            query.setParameter("earnings", currentDailyReport.getEarnings() + purchasedCar.getPrice());
            query.setParameter("soldCars", currentDailyReport.getSoldCars() + 1L);
            query.setParameter("id", currentDailyReport.getId());
            query.executeUpdate();
            t.commit();
            session.close();
            return true;
        } catch (HibernateException e) {
            t.rollback();
            session.close();
            return false;
        }
    }
}
