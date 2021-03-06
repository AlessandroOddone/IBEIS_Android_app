package edu.uic.ibeis_tourist.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.GregorianCalendar;

import edu.uic.ibeis_java_api.api.annotation.BoundingBox;
import edu.uic.ibeis_java_api.values.Sex;
import edu.uic.ibeis_java_api.values.Species;

public class PictureInfo implements Parcelable {
    private String fileName;
    private GregorianCalendar dateTime;
    private Position position;
    private String individualName;
    private Species individualSpecies;
    private Sex individualSex;
    private Location location;
    private BoundingBox annotationBbox;

    public PictureInfo() {}

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public GregorianCalendar getDateTime() {
        return dateTime;
    }

    public void setDateTime(GregorianCalendar dateTime) {
        this.dateTime = dateTime;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getIndividualName() {
        return individualName;
    }

    public void setIndividualName(String individualName) {
        this.individualName = individualName;
    }

    public Species getIndividualSpecies() {
        return individualSpecies;
    }

    public void setIndividualSpecies(Species individualSpecies) {
        this.individualSpecies = individualSpecies;
    }

    public Sex getIndividualSex() {
        return individualSex;
    }

    public void setIndividualSex(Sex individualSex) {
        this.individualSex = individualSex;
    }

    public BoundingBox getAnnotationBbox() {
        return annotationBbox;
    }

    public void setAnnotationBbox(BoundingBox annotationBbox) {
        this.annotationBbox = annotationBbox;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    // Implementation of Parcelable interface

    public PictureInfo(Parcel in){
        fileName = in.readString();
        dateTime = new GregorianCalendar();
        dateTime.setTimeInMillis(in.readLong());
        position = new Position(in.readDouble(), in.readDouble());
        individualName = in.readString();
        try {
            individualSex = Sex.fromValue(in.readInt());
        } catch (edu.uic.ibeis_java_api.exceptions.InvalidSexException e) {
            individualSex = Sex.UNKNOWN;
        }
        try {
            individualSpecies = Species.fromValue(in.readString());
        } catch (edu.uic.ibeis_java_api.exceptions.InvalidSpeciesException e) {
            individualSpecies = Species.UNKNOWN;
        }
        location = in.readParcelable(Location.class.getClassLoader());

        int x = in.readInt();
        int y = in.readInt();
        int w = in.readInt();
        int h = in.readInt();
        if (x!=-1 && y!=-1 && w!=-1 && h!=-1) {
            annotationBbox = new BoundingBox(x, y, w, h);
        }
        else {
            annotationBbox = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fileName);
        dest.writeLong(dateTime.getTimeInMillis());
        dest.writeDouble(position.getLatitude());
        dest.writeDouble(position.getLongitude());
        dest.writeString(individualName);
        dest.writeInt(individualSex.getValue());
        dest.writeString(individualSpecies.getValue());
        dest.writeParcelable(location, 0);
        dest.writeInt(annotationBbox != null ? annotationBbox.getX() : -1);
        dest.writeInt(annotationBbox != null ? annotationBbox.getY() : -1);
        dest.writeInt(annotationBbox != null ? annotationBbox.getW() : -1);
        dest.writeInt(annotationBbox != null ? annotationBbox.getH() : -1);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public PictureInfo createFromParcel(Parcel in) {
            return new PictureInfo(in);
        }
        public PictureInfo[] newArray(int size) {
            return new PictureInfo[size];
        }
    };
}