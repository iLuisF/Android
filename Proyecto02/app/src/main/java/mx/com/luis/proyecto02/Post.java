package mx.com.luis.proyecto02;

/**
 * Modela un item.
 */
public class Post {

    private int albumId;
    private int id;
    private String title;
    private String url;
    private String thumbnailUrl;

    /**
     * Constructor por omision.
     */
    public Post() {

    }

    /**
     * Constructor con solo el identificador.
     * @param id identificador.
     */
    public Post(int id) {
        this.id = id;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
}

