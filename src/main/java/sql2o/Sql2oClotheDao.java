package sql2o;

import dao.ClothesInterface;
import models.Clothes;
import org.sql2o.Sql2o;

import java.util.List;

public class Sql2oClotheDao implements ClothesInterface {
    public Sql2oClotheDao(Sql2o sql2o) {
    }

    @Override
    public void create(Clothes clothes) {

    }

    @Override
    public List<Clothes> findAll() {
        return null;
    }

    @Override
    public Clothes findById(int id) {
        return null;
    }

    @Override
    public void deleteAll(Clothes clothes) {

    }

    @Override
    public void deleteById(int id) {

    }
}
