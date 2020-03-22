package daos;

import models.CarDTO;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class CarDAO implements DAO<CarDTO> {

    private Connection connection;
    private Statement statement;
    private PreparedStatement preStatement;
    private ResultSet rs;
    private static final Logger LOGGER =
            Logger.getLogger(CarDAO.class.getName());
    private final String FIND_ALL = "SELECT * FROM CAR";

    public void connect(){
        try{
            Class.forName("org.postgresql.Driver");
            connection = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/vle");
            connection.setAutoCommit(false);
        }catch (Exception e){
            LOGGER.info("Error: " + e);
        }
        LOGGER.info("Connected");
    }

    public CarDTO makeCarOnWayOut(ResultSet rs) throws SQLException {
        return new CarDTO(rs.getInt("id"),
                rs.getString("make"),
                rs.getString("model"),
                rs.getString("year"),
                rs.getString("color"),
                rs.getString("vin"));
    }

    public CarDTO findById(int id) {
        String sql = "SELECT * FROM CAR WHERE ID=" + id + ";";
         statement = null;
         rs = null;
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery(sql);
            if(rs.next())
                return makeCarOnWayOut(rs);
        }catch (SQLException e){
            LOGGER.info("Error: " + e);
        }finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {};
            try { if (statement != null) statement.close(); } catch (Exception e) {};
            try { if (connection != null) connection.close(); } catch (Exception e) {};
        }
        return null;
    }

    public List<CarDTO> findAll() {
        List<CarDTO> retList = new LinkedList<>();
        statement = null;
        rs = null;
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery(FIND_ALL);
            while (rs.next()){
                retList.add(makeCarOnWayOut(rs));
            }
            return retList;
        }catch (SQLException e){
            LOGGER.info("Error: " + e);
        }finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {};
            try { if (statement != null) statement.close(); } catch (Exception e) {};
            try { if (connection != null) connection.close(); } catch (Exception e) {};
        }
        return null;
    }

    public Boolean update(CarDTO dto) {
        preStatement = null;
        try {
            preStatement = connection.prepareStatement(
                    "UPDATE CAR SET make=?, model=?, year=?, color=?, vin=? WHERE id=" + dto.getId() + ";");

            preStatement.setString(1, dto.getMake());
            preStatement.setString(2, dto.getModel());
            preStatement.setString(3, dto.getYear());
            preStatement.setString(4, dto.getColor());
            preStatement.setString(5, dto.getVin());
            preStatement.executeUpdate();
            connection.commit();
            return true;
        }catch (SQLException e){
            LOGGER.info("Error: " + e);
        }finally {
            try { if (preStatement != null) preStatement.close(); } catch (Exception e) {};
            try { if (connection != null) connection.close(); } catch (Exception e) {};
        }
        return false;
    }

    public Boolean create(CarDTO dto) {
        preStatement = null;
        try {
            preStatement = connection.prepareStatement(
                    "INSERT INTO CAR(ID, MAKE, MODEL, YEAR, COLOR, VIN) VALUES (?, ?, ?, ?, ?, ?)");

            preStatement.setInt(1, dto.getId());
            preStatement.setString(2, dto.getMake());
            preStatement.setString(3, dto.getModel());
            preStatement.setString(4, dto.getYear());
            preStatement.setString(5, dto.getColor());
            preStatement.setString(6, dto.getVin());
            preStatement.executeUpdate();
            connection.commit();
            return true;
        }catch (SQLException e){
            LOGGER.info("Error: " + e);
        }finally {
            try { if (preStatement != null) preStatement.close(); } catch (Exception e) {};
            try { if (connection != null) connection.close(); } catch (Exception e) {};
        }
        return false;
    }

    public Boolean delete(int id) {
        preStatement = null;
        try {
            if(!findById(id).equals(null)) {
                connect();
                preStatement = connection.prepareStatement("DELETE FROM CAR WHERE ID=?");
                preStatement.setInt(1, id);
                preStatement.execute();
                connection.commit();
                return true;
            }
        }catch (SQLException e){
            LOGGER.info("Error: " + e);
        }finally {
            try { if (preStatement != null) preStatement.close(); } catch (Exception e) {};
            try { if (connection != null) connection.close(); } catch (Exception e) {};
        }
        return false;
    }
}
