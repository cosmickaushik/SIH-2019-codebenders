package com.example.workspace;

public class Projects {

    private String title,description,printing,frame,pasting,category,painting,Uid;

    public Projects(){

    }

    public Projects(String title, String description,String printing, String frame, String pasting,String category, String painting, String Uid){
        this.title=title;
        this.description=description;
        this.printing=printing;
        this.pasting=pasting;
        this.frame=frame;
        this.category=category;
        this.painting=painting;
        this.Uid=Uid;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String desc) {
        this.description = desc;
    }

    public String getFrame() {
        return frame;
    }

    public String getPasting() {
        return pasting;
    }

    public String getPrinting() {
        return printing;
    }

    public void setFrame(String frame) {
        this.frame = frame;
    }

    public void setPasting(String pasting) {
        this.pasting = pasting;
    }

    public void setPrinting(String printing) {
        this.printing = printing;
    }

    public String getCategory() {
        return category;
    }

    public String getPainting() {
        return painting;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPainting(String painting) {
        this.painting = painting;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getUid() {
        return Uid;
    }
}
