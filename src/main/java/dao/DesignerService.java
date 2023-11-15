package dao;

import models.Designer;

import java.math.BigDecimal;
import java.util.List;

public interface DesignerService {
    //    add
    void add(Designer designer);

    //    read
    List<Designer> getAll();
    Designer findById(int id);

    //    update
    void update(int id, String name, String description, String imageUrl01, String imageUrl02, BigDecimal price);

    //    delete
    void  deleteAll();
    void deleteById(int id);
}
