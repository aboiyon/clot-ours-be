package sql2o;

import dao.ToursDao;
import models.Tour;
import org.sql2o.Sql2o;

import java.util.List;

public class Sql2oTourDao implements ToursDao {
    public Sql2oTourDao(Sql2o sql2o) {
    }

    @Override
    public void add(Tour tour) {

    }

    @Override
    public List<Tour> getAll() {
        return null;
    }

    @Override
    public Tour findById(int id) {
        return null;
    }

    @Override
    public void deleteAll(Tour tour) {

    }

    @Override
    public void deleteById(int id) {

    }
}
