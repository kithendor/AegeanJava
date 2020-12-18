package com.example.savetest2;


public class UploadImage {
    String imagename, imageinfo, imageurl;

    public UploadImage(){}

    public UploadImage(String imagename,String imageinfo,String imageurl){
//        if(imagename.trim().equals("")){
//            imagename = "No name";
//        }
//        if(imageinfo.trim().equals("")){
//            imageinfo = "No no info";
//        }

        this.imagename = imagename;
        this.imageinfo = imageinfo;
        this.imageurl = imageurl;
    }

    public String getimagename(){
        return this.imagename;
    }

    public String getimageinfo(){
        return this.imageinfo;
    }

    public String getimageurl(){
        return this.imageurl;
    }

    public void setimagename(String n){
        this.imagename = n;
    }

    public void setimageinfo(String i){
        this.imageinfo = i;
    }

    public void setimageurl(String u){
        this.imageurl = u;
    }

}
