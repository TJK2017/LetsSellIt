package tk.seller.trevor.letssellit;


public class User {
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getDownloadurl() {
        return downloadurl;
    }

    public String name;
    public String email;
    public String downloadurl;


    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public User() {
    }

    public User(String name, String email,String downloadurl) {
        this.name = name;
        this.email = email;
        this.downloadurl = downloadurl;

    }
}
