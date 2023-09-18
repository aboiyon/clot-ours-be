package sql2o;

import dao.BeveragesDao;
import models.Beverages;
import org.sql2o.Sql2o;

import java.util.List;

public class Sql2oBeverageDao implements BeveragesDao {
    public Sql2oBeverageDao(Sql2o sql2o) {
    }

    @Override
    public void add(Beverages beverages) {

    }

    @Override
    public List<Beverages> getAll() {
        return null;
    }

    @Override
    public Beverages findById(int id) {
        return null;
    }


    @Override
    public void deleteById(int id) {

    }

    @Override
    public void clearAll() {

    }
}
