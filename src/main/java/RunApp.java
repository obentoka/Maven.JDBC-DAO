import com.sun.javafx.binding.StringFormatter;
import daos.CarDAO;
import models.CarDTO;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class RunApp {

    public static void main(String[] args) {
        final Logger log = Logger.getLogger(RunApp.class.getName());
        List<CarDTO> retList;
        CarDAO dao = new CarDAO();
        CarDTO car = new CarDTO(15, "Bently", "Contiental Gt", "2007", "Teal", "1FTEW1E8XAF447830");

        dao.create(car);
        CarDTO retCar = dao.findById(15);
        String sf = String.format("id=%1d\nmake=%2s\nmodel=%3s\nyear=%4s\ncolor=%5s\nvin=%6s\n",
                retCar.getId(), retCar.getMake(), retCar.getModel(), retCar.getYear(), retCar.getColor(), retCar.getVin());
        log.info(sf);
        CarDTO first = dao.findById(1);
        sf = String.format("id=%1d\nmake=%2s\nmodel=%3s\nyear=%4s\ncolor=%5s\nvin=%6s\n",
                first.getId(), first.getMake(), first.getModel(), first.getYear(), first.getColor(), first.getVin());
        log.info(sf);
        CarDTO replace = new CarDTO(1, "Bently", "Contiental Gt", "2007", "Teal", "1FTEW1E8XAF447830");
        dao.update(replace);
        CarDTO second = dao.findById(1);
        sf = String.format("id=%1d\nmake=%2s\nmodel=%3s\nyear=%4s\ncolor=%5s\nvin=%6s\n",
                second.getId(), second.getMake(), second.getModel(), second.getYear(), second.getColor(), second.getVin());
        log.info(sf);
        dao.delete(15);
        retList = dao.findAll();
        for(CarDTO c : retList){
            sf = String.format("id=%1d\nmake=%2s\nmodel=%3s\nyear=%4s\ncolor=%5s\nvin=%6s\n",
                    c.getId(), c.getMake(), c.getModel(), c.getYear(), c.getColor(), c.getVin());
            log.info(sf);
        }
    }
}
