package sql2o;

import dao.ToursInterface;
import models.Tours;
import org.sql2o.Sql2o;

import java.util.List;

public class Sql2oTourDao implements ToursInterface {
    public Sql2oTourDao(Sql2o sql2o) {
    }

    @Override
    public void create(Tours tours) {

    }

    @Override
    public List<Tours> findAll() {
        return null;
    }

    @Override
    public Tours findById(int id) {
        return null;
    }

    @Override
    public void deleteAll(Tours tours) {

    }

    @Override
    public void deleteById(int id) {

    }
}
