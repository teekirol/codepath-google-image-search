package codepath.com.googleimagesearch.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Filter implements Parcelable {

    private String size;
    private String color;
    private String type;

    public String getSize() {
        return size;
    }

    public String getColor() {
        return color;
    }

    public String getType() {
        return type;
    }

    public String getSite() {
        return site;
    }

    private String site;

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Filter> CREATOR = new Creator<Filter>() {
        @Override
        public Filter createFromParcel(Parcel source) {
            return new Filter(source);
        }

        @Override
        public Filter[] newArray(int size) {
            return new Filter[0];
        }
    };

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(size);
        out.writeString(color);
        out.writeString(type);
        out.writeString(site);
    }

    private Filter(Parcel in) {
        size = in.readString();
        color = in.readString();
        type = in.readString();
        site = in.readString();
    }

    public Filter(String size, String color, String type, String site) {
        this.size = size;
        this.color = color;
        this.type = type;
        this.site = site;
    }

}
