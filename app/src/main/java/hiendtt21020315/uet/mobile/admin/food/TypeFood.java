package hiendtt21020315.uet.mobile.admin.food;

public class TypeFood {
    private int id;
    private String typeName;

    public TypeFood() {
    }

    public TypeFood(int id, String typeName) {
        this.id = id;
        this.typeName = typeName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
