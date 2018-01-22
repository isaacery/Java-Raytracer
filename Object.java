public class Object {
    private Vector3 position;
    private boolean visible;
    public Object(Vector3 pos) {
        this.position = pos;
        this.visible = true;
    }
    public Object(Vector3 pos, boolean visible) {
        this.position = pos;
        this.visible = visible;
    }
}
