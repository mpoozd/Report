package app.xda.report;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Post")
public class Post extends ParseObject {

    private String build;
    private String park;
    private String plate;
    private String picture;

    public String getBuild() {
        return build;
    }

    public void setBuild(String build) {
        this.build = build;
    }

    public String getPark() {
        return park;
    }

    public void setPark(String park) {
        this.park = park;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}