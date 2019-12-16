package DAO;

import model.Car;
import model.DailyReport;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class CarDao {

    private Session session;

    public CarDao(Session session) {
        this.session = session;
    }

    public List<Car> getAllCarsInDB() {
        List<Car> carList = (List<Car>) session.createQuery("FROM Car").list();
        session.close();
        return carList;
    }

    public Car deleteAndGetCarFromDB(Car car) {
        Transaction t = session.beginTransaction();
        try {
            Criteria criteria = session.createCriteria(Car.class);
            Car carFromDB = (Car) criteria.add(Restrictions.eq("brand", car.getBrand()))
                    .add(Restrictions.eq("model", car.getModel()))
                    .add(Restrictions.eq("licensePlate", car.getLicensePlate()))
                    .uniqueResult();
            session.delete(carFromDB);
            t.commit();
            session.close();
            return carFromDB;
        } catch (HibernateException e) {
            t.rollback();
            session.close();
            return null;
        }
    }

    public boolean addCarInDB(Car car) {
        Transaction t = session.beginTransaction();
        try {
            session.save(car);
            t.commit();
            session.close();
            return true;
        } catch (HibernateException e) {
            t.rollback();
            session.close();
            return false;
        }
    }

    public boolean deleteAllCarsDAO() {
        Transaction t = session.beginTransaction();
        try {
            Query query = session.createQuery("DELETE FROM Car");
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
