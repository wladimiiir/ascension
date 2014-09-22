package sk.wladimiiir.ascension.entity;

/**
 * User: wladimiiir
 * Date: 9/21/14
 * Time: 11:41 AM
 */
public abstract class AbstractDBEntity implements DBEntity {
    private String id;

    @Override
    public String getID() {
        return id;
    }

    @Override
    public void setID(String id) {
        this.id = id;
    }
}
