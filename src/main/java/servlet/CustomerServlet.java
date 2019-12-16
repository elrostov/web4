package servlet;

import com.google.gson.Gson;
import model.Car;
import service.CarService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CustomerServlet extends HttpServlet {

    // Покупатели могут запросить список имеющихся  машин по url `/customer`GET запросом
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("---CustomerServlet doGet start---");

        Gson gson = new Gson();
        String json = gson.toJson(CarService.getInstance().getAllCars());

        System.out.println("Got all cars");

        resp.getWriter().write(json);
        resp.setStatus(200);
        System.out.println("---CustomerServlet doGet end---");
        System.out.println();
    }



    // Покупатели могут купить с помощью POST запроса на тот же url,
    // передав параметры марки машины, названия и госномера.
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("---CustomerServlet doPost start---");
        System.out.println();
        String brand = req.getParameter("brand");
        String model = req.getParameter("model");
        String licensePlate = req.getParameter("licensePlate");

        System.out.println("Chosen car:");
        System.out.println("Brand: " + brand);
        System.out.println("Model: " + model);
        System.out.println("LicensePlate: " + licensePlate);
        System.out.println();
        Gson gson = new Gson();

        if (brand == null || model == null || licensePlate == null) {
            System.out.println("Smth is null -> SC_BAD_REQUEST");
            System.out.println();
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            if (CarService.getInstance().buyCar(new Car(brand, model, licensePlate))) {
                System.out.println("buyCar is true -> SC_OK");
                resp.getWriter().write("Congratulations! You`ve bought a car!");
                resp.setStatus(200);
                System.out.println("Congratulations! You`ve bought a car!");
                System.out.println();
            } else {
                resp.getWriter().write("Something went wrong!");
                resp.setStatus(500);
                System.out.println("buyCar is false -> SC_INTERNAL_SERVER_ERROR");
                System.out.println("Something went wrong!");
                System.out.println();
            }
        }
        System.out.println("---CustomerServlet doPost end---");
        System.out.println();
    }
}
