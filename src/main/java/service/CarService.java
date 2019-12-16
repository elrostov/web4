package service;

import DAO.CarDao;
import DAO.DailyReportDao;
import model.Car;
import org.hibernate.SessionFactory;
import util.DBHelper;

import java.util.List;

public class CarService {

    private static CarService carService;

    private SessionFactory sessionFactory;

    private CarService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static CarService getInstance() {
        if (carService == null) {
            carService = new CarService(DBHelper.getSessionFactory());
        }
        return carService;
    }

    public List<Car> getAllCars() {
        return new CarDao(sessionFactory.openSession()).getAllCarsInDB();
    }

    public boolean deleteAllCars() { return new CarDao(sessionFactory.openSession()).deleteAllCarsDAO(); }

    public boolean buyCar(Car car) {
        if (new DailyReportDao(sessionFactory.openSession()).getAllDailyReportsDAO().size() == 0) {
            new DailyReportDao(sessionFactory.openSession()).createEmptyDailyReportDAO();
        }
        //Удалили из БД проданную машину:
        Car purchasedCar = new CarDao(sessionFactory.openSession()).deleteAndGetCarFromDB(car);
        if (purchasedCar != null) {
            //Записали сведения в промежуточный отчет:
            new DailyReportDao(sessionFactory.openSession()).updateDailyReportDAO(purchasedCar);
            return true;
        }
        return false;
    }

    public boolean addCar(Car car) {
        return new CarDao(sessionFactory.openSession()).addCarInDB(car);
    }
}
