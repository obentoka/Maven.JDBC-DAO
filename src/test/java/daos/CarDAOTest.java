package daos;

import models.CarDTO;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CarDAOTest {

    CarDAO test;
    CarDTO expectedCar;
    String expectedMake;
    String expectedModel;
    String expectedYear;
    String expectedColor;
    String expectedVin;

    @Before
    public void init(){
        test = new CarDAO();
        expectedMake = "BMW";
        expectedModel = "X5";
        expectedYear = "2008";
        expectedColor = "Aquamarine";
        expectedVin = "2HNYD286X8H432821";
        expectedCar = new CarDTO(3, expectedMake, expectedModel, expectedYear, expectedColor, expectedVin);
        test.connect();
    }

    @Test
    public void connectTest(){
        test.connect();
    }

    @Test
    public void findById(){
        CarDTO actualCar = test.findById(3);

        assertEquals(expectedColor, actualCar.getColor());
        assertEquals(expectedMake, actualCar.getMake());
        assertEquals(expectedModel, actualCar.getModel());
        assertEquals(expectedYear, actualCar.getYear());
        assertEquals(expectedVin, actualCar.getVin());
    }

    @Test
    public void findAll(){
        test.findAll().stream().forEach(System.out::println);
    }

    @Test
    public void updateTest(){
        CarDTO newCar = new CarDTO(1, "bent", "gt", "2000", "white", "0000");
        test.update(newCar);

        test.connect();
        CarDTO actualCar = test.findById(1);

        assertEquals(newCar.getMake(), actualCar.getMake());
        assertEquals(newCar.getModel(), actualCar.getModel());
        assertEquals(newCar.getYear(), actualCar.getYear());
        assertEquals(newCar.getColor(), actualCar.getColor());
        assertEquals(newCar.getVin(), actualCar.getVin());
    }

    @Test
    public void createTest(){
        CarDTO newCar = new CarDTO(11, "bar", "agt", "20033", "whote", "00100");
        test.create(newCar);

        test.connect();
        CarDTO actualCar = test.findById(11);

        assertEquals(newCar.getMake(), actualCar.getMake());
        assertEquals(newCar.getModel(), actualCar.getModel());
        assertEquals(newCar.getYear(), actualCar.getYear());
        assertEquals(newCar.getColor(), actualCar.getColor());
        assertEquals(newCar.getVin(), actualCar.getVin());
    }

    @Test
    public void deleteTest(){
        test.delete(11);
        test.connect();
        assertNull(test.findById(11));
    }
}