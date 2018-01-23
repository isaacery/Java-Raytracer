public class Object {
    protected Vector3 position;
    protected boolean visible; //TODO decide if I need this.
    
    public Object(Vector3 pos) {
        this.position = pos;
        this.visible = true;
    }
    public Object(Vector3 pos, boolean visible) {
        this.position = pos;
        this.visible = visible;
    }

    public Vector3 getPosition() {
        return position;
    }
}
