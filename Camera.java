public class Camera extends Object {
    private Vector3 direction;

    public Camera() {
        super(Vector3.ZERO, false);
        direction = Vector3.FORWARD;
    }

    public Camera(Vector3 dir) {
        super(Vector3.ZERO, false);
        direction = dir;
    }
}
