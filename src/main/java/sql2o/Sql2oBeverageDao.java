package sql2o;

import dao.BeveragesInterface;
import models.Beverages;
import org.sql2o.Sql2o;

import java.util.List;

public class Sql2oBeverageDao implements BeveragesInterface {
    public Sql2oBeverageDao(Sql2o sql2o) {
    }

    @Override
    public void create(Beverages beverages) {

    }

    @Override
    public List<Beverages> findAll() {
        return null;
    }

    @Override
    public Beverages findById(int id) {
        return null;
    }

    @Override
    public void deleteAll(Beverages beverages) {

    }

    @Override
    public void deleteById(int id) {

    }
}
