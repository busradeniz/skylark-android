package ostmodern.skylark.model;


public class SetUI {
    private final Set set;
    private final Image image;

    /**
     * Constructor for {@link SetUI}.
     *
     * @param set   instance of set
     * @param image instance of image of set
     */
    public SetUI(Set set, Image image) {
        this.set = set;
        this.image = image;
    }

    public Set getSet() {
        return set;
    }

    public Image getImage() {
        return image;
    }
}
