package servlet;

import model.Car;
import service.CarService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProducerServlet extends HttpServlet {
    // Новые поступления происходят в течение дня. На Post запрос на `/producer`
    // с параметрами, по названию аналогичными полям класса Car, нужно ответить
    // 200м статусом, если машина принята, и 403м, если нет.
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        System.out.println("---ProducerServlet doPost start---");
        System.out.println();
        String brand = req.getParameter("brand");
        String model = req.getParameter("model");
        String licensePlate = req.getParameter("licensePlate");
        Long price = null;
        try {
            price = Long.parseLong(req.getParameter("price"));
        } catch (NumberFormatException e) {
            System.out.println("Wrong price parameter!");
            System.out.println();
        }

        System.out.println("Adding car:");
        System.out.println("Brand: " + brand);
        System.out.println("Model: " + model);
        System.out.println("LicensePlate: " + licensePlate);
        System.out.println("Price: " + price);
        System.out.println();

        if (brand == null || model == null || licensePlate == null || price == null) {
            System.out.println("Smth is null -> SC_FORBIDDEN");
            System.out.println();
            resp.setStatus(403);
        } else {
            if (CarService.getInstance().addCar(new Car(brand, model, licensePlate, price))) {
                resp.setStatus(200);
                System.out.println("The car was added!");
                System.out.println();
            } else {
                resp.setStatus(403);
                System.out.println("Something went wrong!");
                System.out.println();
            }
        }
        System.out.println("---ProducerServlet doPost end---");
        System.out.println();
    }
}
